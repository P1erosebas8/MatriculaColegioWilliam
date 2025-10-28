package com.matriculaweb.matriculaweb.controller;

import com.matriculaweb.matriculaweb.model.Usuario;
import com.matriculaweb.matriculaweb.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final UsuarioRepository repo;

    public LoginController(UsuarioRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String usuario,
            @RequestParam String clave,
            HttpSession session,
            Model model) {

        Usuario u = repo.findByUsuario(usuario);

        if (u != null && u.getClave().equals(clave)) {
            session.setAttribute("usuario", u.getUsuario());
            return "inicio";
        } else {
            model.addAttribute("error", "Usuario o clave incorrectos");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
