package com.matriculaweb.matriculaweb.controller;

import com.matriculaweb.matriculaweb.model.Alumno;
import com.matriculaweb.matriculaweb.repository.AlumnoRepository;
import com.matriculaweb.matriculaweb.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class AlumnoPerfilController {

    private final AlumnoRepository alumnoRepo;
    private final UsuarioRepository usuarioRepo;

    public AlumnoPerfilController(AlumnoRepository alumnoRepo, UsuarioRepository usuarioRepo) {
        this.alumnoRepo = alumnoRepo;
        this.usuarioRepo = usuarioRepo;
    }

    @GetMapping("/perfil")
    public String mostrarPerfil(Model model, Principal principal) {

        String username = principal.getName();

        Alumno alumno = alumnoRepo.findByCorreo(username).orElse(new Alumno());
        alumno.setCorreo(username);

        model.addAttribute("alumno", alumno);

        return "perfil";
    }

    @PostMapping("/perfil/guardar")
    public String guardarPerfil(Alumno alumno, Principal principal) {

        alumno.setCorreo(principal.getName());
        alumnoRepo.save(alumno);

        return "redirect:/alumno/matricula";
    }
}
