package com.matriculaweb.matriculaweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matriculaweb.matriculaweb.model.Seccion;

public interface SeccionRepository extends JpaRepository<Seccion, Long> {
    List<Seccion> findByCursoId(Long cursoId);
}
