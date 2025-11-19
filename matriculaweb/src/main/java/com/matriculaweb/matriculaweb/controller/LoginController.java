package com.matriculaweb.matriculaweb.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/redirect")
    public String redirectAfterLogin(Authentication auth) {
        if (auth == null) {
            return "redirect:/login";
        }

        for (GrantedAuthority authority : auth.getAuthorities()) {
            String rol = authority.getAuthority();
            System.out.println(">>> Rol autenticado: " + rol);

            if (rol.equals("ROLE_ADMIN")) {
                return "redirect:/inicio";
            } else if (rol.equals("ROLE_ALUMNO")) {
                return "redirect:/matricula";
            }
        }

        return "redirect:/login?error=sinrol";
    }
}
