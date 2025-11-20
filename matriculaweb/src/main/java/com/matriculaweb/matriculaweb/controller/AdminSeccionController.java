package com.matriculaweb.matriculaweb.controller;

import com.matriculaweb.matriculaweb.model.Seccion;
import com.matriculaweb.matriculaweb.repository.CursoRepository;
import com.matriculaweb.matriculaweb.repository.ProfesorRepository;
import com.matriculaweb.matriculaweb.repository.SeccionRepository;
import com.matriculaweb.matriculaweb.services.CursoService;
import com.matriculaweb.matriculaweb.services.ProfesorService;
import com.matriculaweb.matriculaweb.services.SeccionService;
import com.matriculaweb.matriculaweb.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/secciones")
public class AdminSeccionController {

    @Autowired
    private SeccionService seccionService;

    @Autowired
    private SeccionRepository seccionRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private ProfesorService profesorService;

    @Autowired
    private ProfesorRepository profesorRepository;

    @GetMapping("")
    public String listar(Model model) {
        model.addAttribute("secciones", seccionRepository.findAll());
        model.addAttribute("cursos", cursoService.findActivos());
        model.addAttribute("profesores", profesorService.findActivos());
        model.addAttribute("seccion", new Seccion());
        return "secciones";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Seccion seccion) {
        seccionRepository.save(seccion);
        return "redirect:/secciones";
    }

    @GetMapping("/json/{id}")
    @ResponseBody
    public Seccion obtener(@PathVariable Long id) {
        return seccionRepository.findById(id).orElse(null);
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        seccionRepository.deleteById(id);
        return "redirect:/secciones";
    }

    @GetMapping("/inscritos/{idSeccion}")
    public String verInscritos(@PathVariable Long idSeccion, Model model) {

        var seccion = seccionRepository.findById(idSeccion).orElse(null);
        if (seccion == null)
            return "redirect:/secciones?error";

        var inscritos = matriculaRepository.findBySeccionId(idSeccion);

        model.addAttribute("seccion", seccion);
        model.addAttribute("inscritos", inscritos);

        return "seccion_inscritos";
    }

    @GetMapping("/cerrarCupos/{id}")
    public String cerrarCupos(@PathVariable Long id) {

        seccionService.cerrarCupos(id);

        return "redirect:/secciones?cuposCerrados";
    }

    @PostMapping("/deshabilitar/{id}")
    public String deshabilitar(@PathVariable Long id) {
        Seccion s = seccionRepository.findById(id).orElseThrow();
        s.setActivo(false);
        seccionRepository.save(s);
        return "redirect:/secciones";
    }

    @PostMapping("/habilitar/{id}")
    public String habilitar(@PathVariable Long id) {
        var sec = seccionService.obtener(id);
        sec.setActivo(true);
        seccionService.guardar(sec);
        return "redirect:/secciones";
    }

}
