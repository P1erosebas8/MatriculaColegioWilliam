package com.matriculaweb.matriculaweb.services;

import com.matriculaweb.matriculaweb.model.Seccion;
import com.matriculaweb.matriculaweb.repository.SeccionRepository;
import com.matriculaweb.matriculaweb.repository.MatriculaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeccionService {

    private final SeccionRepository seccionRepository;
    private final MatriculaRepository matriculaRepository;

    public SeccionService(SeccionRepository seccionRepository, MatriculaRepository matriculaRepository) {
        this.seccionRepository = seccionRepository;
        this.matriculaRepository = matriculaRepository;
    }

    public List<Seccion> listarSecciones() {
        return seccionRepository.findAll();
    }
    public List<Seccion> listarSeccionesActivas() {
    return seccionRepository.findByActivoTrue();
}
    public Seccion obtener(Long id) {
        return seccionRepository.findById(id).orElse(null);
    }

    public void guardar(Seccion s) {
        seccionRepository.save(s);
    }

    public boolean hayCapacidadDisponible(Long seccionId) {
        Seccion sec = seccionRepository.findById(seccionId).orElseThrow();
        int inscritos = matriculaRepository.countBySeccionId(seccionId);
        if (inscritos >= sec.getCapacidad()) {
            sec.setCuposCerrados(true);
            seccionRepository.save(sec);
            return false;
        }

        if (sec.isCuposCerrados()) {
            return false;
        }

        return true;
    }

    public void cerrarCupos(Long seccionId) {
        Seccion sec = obtener(seccionId);
        if (sec != null) {
            sec.setCuposCerrados(true);
            seccionRepository.save(sec);
        }
    }

    public void cerrarSeccionSiLlena(Long seccionId) {
        Seccion sec = seccionRepository.findById(seccionId).orElseThrow();
        int inscritos = matriculaRepository.countBySeccionId(seccionId);

        if (inscritos >= sec.getCapacidad()) {
            sec.setCuposCerrados(true);
            seccionRepository.save(sec);
        }
    }
}
