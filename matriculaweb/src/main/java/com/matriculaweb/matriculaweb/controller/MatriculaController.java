package com.matriculaweb.matriculaweb.controller;

import com.matriculaweb.matriculaweb.services.SeccionService;

import jakarta.servlet.http.HttpServletResponse;

import com.matriculaweb.matriculaweb.services.MatriculaService;
import com.matriculaweb.matriculaweb.repository.MatriculaRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.matriculaweb.matriculaweb.repository.AlumnoRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

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
        String username = principal.getName();

        Long alumnoId = alumnoRepository.findAlumnoIdByUsername(username);

        var misMatriculas = matriculaService.listarPorAlumno(alumnoId);

        model.addAttribute("matriculas", misMatriculas);

        return "misMatriculas";
    }

    @GetMapping("/miHorario")
    public String miHorario(Model model, Principal principal) {
        String username = principal.getName();

        Long alumnoId = alumnoRepository.findAlumnoIdByUsername(username);

        var misMatriculas = matriculaService.listarPorAlumno(alumnoId);

        model.addAttribute("matriculas", misMatriculas);

        return "horario";
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

    @GetMapping("/descargarHorarioPdf")
    public void descargarHorarioPdf(HttpServletResponse response, Principal principal) throws Exception {

        String username = principal.getName();
        Long alumnoId = alumnoRepository.findAlumnoIdByUsername(username);

        var matriculas = matriculaService.listarPorAlumno(alumnoId);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=horario.pdf");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        // Título
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Paragraph title = new Paragraph("Horario del Alumno", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // Tabla
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setSpacingAfter(10);

        // Encabezados
        Stream.of("Curso", "Profesor", "Horario", "Horas Semanales")
                .forEach(col -> {
                    PdfPCell header = new PdfPCell(new Phrase(col));
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setPadding(8);
                    table.addCell(header);
                });

        // Filas con datos
        for (var m : matriculas) {
            table.addCell(m.getSeccion().getCurso().getNombre());
            table.addCell(m.getSeccion().getProfesor().getNombre() + " "
                    + m.getSeccion().getProfesor().getApellido());
            table.addCell(m.getSeccion().getHorario());
            table.addCell(m.getSeccion().getCurso().getHorasSemanales() + " hrs");
        }

        document.add(table);
        document.close();
    }

}
