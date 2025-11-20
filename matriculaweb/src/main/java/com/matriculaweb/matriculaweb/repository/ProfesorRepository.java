package com.matriculaweb.matriculaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.matriculaweb.matriculaweb.model.Profesor;
import java.util.List;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    List<Profesor> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido);

    List<Profesor> findByActivoTrue();

}
