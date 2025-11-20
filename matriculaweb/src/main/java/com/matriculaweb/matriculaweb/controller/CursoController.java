package com.matriculaweb.matriculaweb.controller;

import com.matriculaweb.matriculaweb.model.Curso;
import com.matriculaweb.matriculaweb.repository.CursoRepository;
import com.matriculaweb.matriculaweb.services.CursoService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    private final CursoRepository repo;
    private final CursoService cursoService;

    public CursoController(CursoRepository repo, CursoService cursoService) {
        this.repo = repo;
        this.cursoService = cursoService;
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

    @PostMapping("/deshabilitar/{id}")
    public String deshabilitar(@PathVariable Long id) {
        Curso curso = cursoService.buscarPorId(id).orElse(null);

        if (curso != null) {
            curso.setActivo(false);
            cursoService.guardar(curso);
        }

        return "redirect:/cursos";
    }

    @PostMapping("/habilitar/{id}")
    public String habilitar(@PathVariable Long id) {
        Curso curso = cursoService.buscarPorId(id).orElse(null);

        if (curso != null) {
            curso.setActivo(true);
            cursoService.guardar(curso);
        }

        return "redirect:/cursos";
    }

}
