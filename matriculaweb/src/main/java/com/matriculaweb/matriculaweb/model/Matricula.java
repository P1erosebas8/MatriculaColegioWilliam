package com.matriculaweb.matriculaweb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "matriculas", uniqueConstraints = @UniqueConstraint(columnNames = { "alumno_id", "seccion_id" }))
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "seccion_id")
    private Seccion seccion;

    private LocalDateTime fechaMatricula = LocalDateTime.now();

    public Matricula() {

    }

    public LocalDateTime getFechaMatricula() {
        return this.fechaMatricula;
    }

    public void setFechaMatricula(LocalDateTime fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public Seccion getSeccion() {
        return seccion;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }
}
