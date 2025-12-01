package com.matriculaweb.matriculaweb.controller;

import com.matriculaweb.matriculaweb.services.SeccionService;

import jakarta.servlet.http.HttpServletResponse;

import com.matriculaweb.matriculaweb.services.MatriculaService;
import com.matriculaweb.matriculaweb.repository.MatriculaRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
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

        Document document = new Document(PageSize.A4, 40, 40, 40, 40);
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        try {
            Image logo = Image.getInstance("matriculaweb/src/main/resources/static/assets/img/prescot.png");
            logo.scaleToFit(80, 80);
            logo.setAlignment(Image.ALIGN_LEFT);
            document.add(logo);
        } catch (Exception e) {
            System.out.println("No se pudo cargar el logo: " + e.getMessage());
        }

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLACK);

        Paragraph title = new Paragraph("Horario del Alumno", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingBefore(10);
        title.setSpacingAfter(15);
        document.add(title);

        LineSeparator separator = new LineSeparator();
        separator.setOffset(-2);
        separator.setLineColor(BaseColor.GRAY);
        document.add(new Chunk(separator));

        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(15);
        table.setWidths(new float[] { 3, 3, 3, 2 });

        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
        BaseColor headerColor = new BaseColor(52, 73, 94);
        Stream.of("Curso", "Profesor", "Horario", "Horas Semanales")
                .forEach(col -> {
                    PdfPCell header = new PdfPCell(new Phrase(col, headerFont));
                    header.setBackgroundColor(headerColor);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setPadding(10);
                    header.setBorderColor(BaseColor.WHITE);
                    table.addCell(header);
                });

        Font bodyFont = new Font(Font.FontFamily.HELVETICA, 11);

        for (var m : matriculas) {
            table.addCell(new PdfPCell(new Phrase(m.getSeccion().getCurso().getNombre(), bodyFont)));
            table.addCell(new PdfPCell(new Phrase(
                    m.getSeccion().getProfesor().getNombre() + " " + m.getSeccion().getProfesor().getApellido(),
                    bodyFont)));
            table.addCell(new PdfPCell(new Phrase(m.getSeccion().getHorario(), bodyFont)));
            table.addCell(new PdfPCell(new Phrase(
                    m.getSeccion().getCurso().getHorasSemanales() + " hrs", bodyFont)));
        }

        document.add(table);


        Paragraph footer = new Paragraph("Generado por el Sistema de Matrícula",
                new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, BaseColor.GRAY));

        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingBefore(20);

        document.add(footer);

        document.close();
    }

}
