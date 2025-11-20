package com.matriculaweb.matriculaweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.matriculaweb.matriculaweb.model.Matricula;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    int countBySeccionId(Long seccionId);

    boolean existsByAlumnoIdAndSeccionId(Long alumnoId, Long seccionId);

    List<Matricula> findBySeccionId(Long idSeccion);

    List<Matricula> findByAlumnoId(Long alumnoId);

}
