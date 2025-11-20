package com.matriculaweb.matriculaweb.controller;

import com.matriculaweb.matriculaweb.model.Profesor;
import com.matriculaweb.matriculaweb.repository.ProfesorRepository;
import com.matriculaweb.matriculaweb.services.ProfesorService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/profesores")
public class ProfesorController {

    private final ProfesorRepository repo;
    private final ProfesorService profesorService;

    public ProfesorController(ProfesorRepository repo, ProfesorService profesorService) {
        this.repo = repo;
        this.profesorService = profesorService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("profesores", repo.findAll());
        model.addAttribute("profesor", new Profesor());
        return "profesor";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Profesor profesor) {
        repo.save(profesor);
        return "redirect:/profesores";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/profesores";
    }

    @GetMapping("/json/{id}")
    @ResponseBody
    public Profesor obtenerProfesorJson(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @GetMapping("/buscar")
    public String buscar(@RequestParam("query") String query, Model model) {
        List<Profesor> resultados = repo.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(query, query);
        model.addAttribute("profesores", resultados);
        model.addAttribute("profesor", new Profesor());
        return "profesor";
    }

    @PostMapping("/deshabilitar/{id}")
    public String deshabilitar(@PathVariable Long id) {
        Profesor profesor = profesorService.buscarPorId(id).orElse(null);

        if (profesor != null) {
            profesor.setActivo(false);
            profesorService.guardar(profesor);
        }

        return "redirect:/profesores";
    }

    @PostMapping("/habilitar/{id}")
    public String habilitar(@PathVariable Long id) {
        Profesor profesor = profesorService.buscarPorId(id).orElse(null);

        if (profesor != null) {
            profesor.setActivo(true);
            profesorService.guardar(profesor);
        }

        return "redirect:/profesores";
    }
}
