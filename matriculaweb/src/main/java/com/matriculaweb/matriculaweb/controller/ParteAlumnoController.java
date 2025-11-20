package com.matriculaweb.matriculaweb.controller;

import com.matriculaweb.matriculaweb.model.Alumno;
import com.matriculaweb.matriculaweb.repository.AlumnoRepository;
import com.matriculaweb.matriculaweb.services.CursoService;
import com.matriculaweb.matriculaweb.services.ProfesorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/alumno")
public class ParteAlumnoController {

    @Autowired
    private AlumnoRepository alumnoRepository;


    @Autowired
    private CursoService cursoService;

    @Autowired
    private ProfesorService profesorService;

    @GetMapping("/cursosA")
    public String cursos(Model model) {
        model.addAttribute("cursos", cursoService.findActivos());
        return "cursosA";
    }

    @GetMapping("/profesoresA")
    public String profesores(Model model) {
        model.addAttribute("profesores", profesorService.findActivos());
        return "profesoresA";
    }

    @GetMapping("/perfilA")
    public String perfil(Model model, Authentication auth) {
        String correo = auth.getName();
        Alumno alumno = alumnoRepository.findByCorreo(correo).orElse(null);

        model.addAttribute("alumno", alumno);
        return "perfilA";
    }

    @PostMapping("/actualizarPerfil")
    public String actualizarPerfil(@ModelAttribute Alumno alumnoForm,
            Authentication auth) {

        String correo = auth.getName();
        Alumno alumnoBD = alumnoRepository.findByCorreo(correo).orElse(null);

        if (alumnoBD == null)
            return "redirect:/alumno/perfilA?error";

        alumnoBD.setNombres(alumnoForm.getNombres());
        alumnoBD.setApellidos(alumnoForm.getApellidos());

        alumnoRepository.save(alumnoBD);

        return "redirect:/alumno/perfilA?success";
    }
}
