package com.matriculaweb.matriculaweb.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

@GetMapping("/inicio")
public String inicio(Model model, Authentication auth) {
    model.addAttribute("usuario", auth.getName()); // nombre del usuario autenticado
    return "inicio";
}

}
