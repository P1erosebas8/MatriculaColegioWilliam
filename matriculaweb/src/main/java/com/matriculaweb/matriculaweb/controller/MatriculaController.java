package com.matriculaweb.matriculaweb.controller;

import com.matriculaweb.matriculaweb.services.SeccionService;
import com.matriculaweb.matriculaweb.services.MatriculaService;
import com.matriculaweb.matriculaweb.repository.MatriculaRepository;
import com.matriculaweb.matriculaweb.repository.AlumnoRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/alumno")
public class MatriculaController {

    private final SeccionService seccionService;
    private final MatriculaService matriculaService;
    private final MatriculaRepository matriculaRepository;
    private final AlumnoRepository alumnoRepository;

    public MatriculaController(
            SeccionService seccionService,
            MatriculaService matriculaService,
            MatriculaRepository matriculaRepository,
            AlumnoRepository alumnoRepository) {
        this.seccionService = seccionService;
        this.matriculaService = matriculaService;
        this.matriculaRepository = matriculaRepository;
        this.alumnoRepository = alumnoRepository;
    }

    @GetMapping("/matricula")
    public String matricula(Model model, Principal principal) {

        var secciones = seccionService.listarSeccionesActivas();

        Map<Long, Integer> inscritos = new HashMap<>();
        for (var sec : secciones) {
            inscritos.put(sec.getId(), matriculaRepository.countBySeccionId(sec.getId()));
        }

        model.addAttribute("secciones", secciones);
        model.addAttribute("inscritosMap", inscritos);

        return "matricula";
    }

    @GetMapping("/misMatriculas")
    public String misMatriculas(Model model, Principal principal) {
        System.out.println("hola");
        String username = principal.getName();

        Long alumnoId = alumnoRepository.findAlumnoIdByUsername(username);

        var misMatriculas = matriculaService.listarPorAlumno(alumnoId);

        model.addAttribute("matriculas", misMatriculas);

        return "misMatriculas";
    }

    @PostMapping("/matricular/{id}")
    public String matricular(
            @PathVariable Long id,
            Principal principal,
            RedirectAttributes redirect) {

        String username = principal.getName();
        Long alumnoId = alumnoRepository.findAlumnoIdByUsername(username);

        if (matriculaService.yaMatriculado(alumnoId, id)) {
            redirect.addFlashAttribute("error", "Ya estás matriculado en esta sección.");
            return "redirect:/alumno/matricula";
        }

        if (!seccionService.hayCapacidadDisponible(id)) {
            redirect.addFlashAttribute("error", "La sección ya no tiene cupos disponibles.");
            return "redirect:/alumno/matricula";
        }

        matriculaService.matricularAlumno(username, id);

        seccionService.cerrarSeccionSiLlena(id);

        redirect.addFlashAttribute("success", "¡Te matriculaste correctamente!");

        return "redirect:/alumno/matricula";
    }
}
