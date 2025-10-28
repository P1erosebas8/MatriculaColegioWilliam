package com.matriculaweb.matriculaweb.repository;

import com.matriculaweb.matriculaweb.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    List<Alumno> findByNombresContainingIgnoreCaseOrDniContaining(String nombres, String dni);

}
