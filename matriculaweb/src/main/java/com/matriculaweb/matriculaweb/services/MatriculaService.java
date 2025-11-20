package com.matriculaweb.matriculaweb.services;

import com.matriculaweb.matriculaweb.model.Alumno;
import com.matriculaweb.matriculaweb.model.Matricula;
import com.matriculaweb.matriculaweb.model.Seccion;
import com.matriculaweb.matriculaweb.repository.AlumnoRepository;
import com.matriculaweb.matriculaweb.repository.MatriculaRepository;
import com.matriculaweb.matriculaweb.repository.SeccionRepository;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final AlumnoRepository alumnoRepository;
    private final SeccionRepository seccionRepository;

    public MatriculaService(MatriculaRepository matriculaRepository,
            AlumnoRepository alumnoRepository,
            SeccionRepository seccionRepository) {
        this.matriculaRepository = matriculaRepository;
        this.alumnoRepository = alumnoRepository;
        this.seccionRepository = seccionRepository;
    }

    public boolean yaMatriculado(Long alumnoId, Long seccionId) {
        return matriculaRepository.existsByAlumnoIdAndSeccionId(alumnoId, seccionId);
    }

    public void matricularAlumno(String username, Long seccionId) {

        Alumno alumno = alumnoRepository.findByCorreo(username)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        Seccion seccion = seccionRepository.findById(seccionId)
                .orElseThrow(() -> new RuntimeException("Sección no encontrada"));

        if (seccion.isCuposCerrados()) {
            throw new RuntimeException("La sección ya cerró cupos");
        }

        Matricula m = new Matricula();
        m.setAlumno(alumno);
        m.setSeccion(seccion);
        m.setFechaMatricula(LocalDateTime.now());
        matriculaRepository.save(m);
    }

    public List<Matricula> listarPorAlumno(Long alumnoId) {
        return matriculaRepository.findByAlumnoId(alumnoId);
    }
}
