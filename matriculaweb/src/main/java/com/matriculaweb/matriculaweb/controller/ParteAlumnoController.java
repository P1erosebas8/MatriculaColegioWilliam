package com.matriculaweb.matriculaweb.controller;

import com.matriculaweb.matriculaweb.model.Alumno;
import com.matriculaweb.matriculaweb.repository.AlumnoRepository;
import com.matriculaweb.matriculaweb.repository.CursoRepository;
import com.matriculaweb.matriculaweb.repository.MatriculaRepository;
import com.matriculaweb.matriculaweb.repository.ProfesorRepository;
import com.matriculaweb.matriculaweb.repository.SeccionRepository;
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
    private CursoRepository cursoRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    @GetMapping("/matricula")
    public String matricula(Model model, Authentication auth) {
        model.addAttribute("username", auth.getName());
        return "matricula";
    }

    // LISTA DE CURSOS
    @GetMapping("/cursosA")
    public String cursos(Model model) {
        model.addAttribute("cursos", cursoRepository.findAll());
        return "cursosA";
    }

    // LISTA DE PROFESORES
    @GetMapping("/profesoresA")
    public String profesores(Model model) {
        model.addAttribute("profesores", profesorRepository.findAll());
        return "profesoresA";
    }

    // PERFIL DEL ALUMNO
    @GetMapping("/perfilA")
    public String perfil(Model model, Authentication auth) {
        String correo = auth.getName();
        Alumno alumno = alumnoRepository.findByCorreo(correo).orElse(null);

        model.addAttribute("alumno", alumno);
        return "perfilA";
    }

    // ACTUALIZAR PERFIL
    @PostMapping("/actualizarPerfil")
    public String actualizarPerfil(@ModelAttribute Alumno alumnoForm,
            Authentication auth) {

        String correo = auth.getName();
        Alumno alumnoBD = alumnoRepository.findByCorreo(correo).orElse(null);

        if (alumnoBD == null)
            return "redirect:/alumno/perfil?error";

        // Aseguramos que se actualiza el mismo registro
        alumnoForm.setId(alumnoBD.getId());

        // Copiar los datos del formulario
        alumnoBD.setNombres(alumnoForm.getNombres());
        alumnoBD.setApellidos(alumnoForm.getApellidos());

        alumnoRepository.save(alumnoBD);

        return "redirect:/alumno/matricula?success";
    }

}
