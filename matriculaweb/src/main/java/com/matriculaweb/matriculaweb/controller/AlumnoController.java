package com.matriculaweb.matriculaweb.controller;

import com.matriculaweb.matriculaweb.model.Alumno;
import com.matriculaweb.matriculaweb.repository.AlumnoRepository;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/alumnos")
public class AlumnoController {

    private final AlumnoRepository repo;

    public AlumnoController(AlumnoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("alumnos", repo.findAll());
        model.addAttribute("nuevoAlumno", new Alumno());
        return "alumno";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Alumno alumno) {
        repo.save(alumno);
        return "redirect:/alumnos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/alumnos";
    }

    @GetMapping("/json/{id}")
    @ResponseBody
    public Alumno obtenerAlumno(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @GetMapping("/buscar")
    public String buscarAlumno(@RequestParam("query") String query, Model model) {
        List<Alumno> resultados = repo.findByNombresContainingIgnoreCaseOrDniContaining(query, query);
        model.addAttribute("alumnos", resultados);
        model.addAttribute("nuevoAlumno", new Alumno());
        return "alumno";
    }

}
