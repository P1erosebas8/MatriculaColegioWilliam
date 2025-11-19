package com.matriculaweb.matriculaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matriculaweb.matriculaweb.model.Matricula;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
}
