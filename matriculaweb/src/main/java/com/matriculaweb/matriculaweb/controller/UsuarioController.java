package com.matriculaweb.matriculaweb.controller;

import com.matriculaweb.matriculaweb.model.Usuario;
import com.matriculaweb.matriculaweb.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    private final List<String> ROLES = List.of("ADMIN", "DOCENTE", "ALUMNO");

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listar());
        model.addAttribute("usuarioForm", new Usuario());
        model.addAttribute("roles", ROLES);
        return "usuarios";
    }

    @GetMapping("/json/{id}")
    @ResponseBody
    public Usuario obtenerUsuario(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("usuarioForm") Usuario usuario) {
        usuarioService.guardar(usuario);
        return "redirect:/usuarios";
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute("usuarioForm") Usuario usuario) {

        Usuario original = usuarioService.buscarPorId(usuario.getId());
        if (original == null)
            return "redirect:/usuarios";

        original.setUsuario(usuario.getUsuario());
        original.setNombre(usuario.getNombre());
        original.setRol(usuario.getRol());

        if (usuario.getClave() != null && !usuario.getClave().isBlank()) {
            original.setClave(usuario.getClave());
        }

        usuarioService.guardar(original);

        return "redirect:/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return "redirect:/usuarios";
    }

    @GetMapping("/buscar")
    public String buscar(@RequestParam String query, Model model) {
        model.addAttribute("usuarios", usuarioService.buscarPorNombre(query));
        model.addAttribute("usuarioForm", new Usuario());
        model.addAttribute("roles", ROLES);
        return "usuarios";
    }
}
