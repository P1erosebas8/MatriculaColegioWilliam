package com.matriculaweb.matriculaweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.matriculaweb.matriculaweb.repository.AlumnoRepository;
import com.matriculaweb.matriculaweb.repository.UsuarioRepository;

@Controller
public class LoginController {
    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/redirect")
    public String redirectAfterLogin(Authentication auth) {
        if (auth == null) {
            return "redirect:/login";
        }
        String username = auth.getName();
        for (GrantedAuthority authority : auth.getAuthorities()) {
            String rol = authority.getAuthority();
            System.out.println(">>> Rol autenticado: " + rol);

            if (rol.equals("ROLE_ADMIN")) {
                return "redirect:/inicio";
            } else if (rol.equals("ROLE_ALUMNO")) {
                boolean tienePerfil = alumnoRepository.findByCorreo(username).isPresent();

                if (!tienePerfil) {
                    System.out.println(">>> Alumno sin perfil, redirigiendo a /perfil");
                    return "redirect:/perfil";
                }

                System.out.println(">>> Alumno con perfil, redirigiendo a panel alumno");
                return "redirect:/alumno/matricula";
            }
        }

        return "redirect:/login?error=sinrol";
    }
}
