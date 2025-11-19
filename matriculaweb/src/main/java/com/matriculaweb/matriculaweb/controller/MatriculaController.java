package com.matriculaweb.matriculaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MatriculaController {

    @GetMapping("/matricula")
    public String matricula() {
        return "matricula";
    }
}
