package com.matriculaweb.matriculaweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.matriculaweb.matriculaweb.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    List<Curso> findByNombreContainingIgnoreCaseOrDescripcionContaining(String nombre, String descripcion);

    List<Curso> findByActivoTrue();

}
