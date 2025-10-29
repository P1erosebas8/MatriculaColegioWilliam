package com.matriculaweb.matriculaweb.controller;

import com.matriculaweb.matriculaweb.model.Curso;
import com.matriculaweb.matriculaweb.repository.CursoRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    private final CursoRepository repo;

    public CursoController(CursoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("cursos", repo.findAll());
        model.addAttribute("curso", new Curso());
        return "curso";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Curso curso) {
        repo.save(curso);
        return "redirect:/cursos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/cursos";
    }

    @GetMapping("/json/{id}")
    @ResponseBody
    public Curso obtenerCursoJson(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @GetMapping("/buscar")
    public String buscar(@RequestParam("query") String query, Model model) {
        List<Curso> resultados = repo.findByNombreContainingIgnoreCaseOrDescripcionContaining(query, query);
        model.addAttribute("cursos", resultados);
        model.addAttribute("curso", new Curso());
        return "curso";
    }
}
