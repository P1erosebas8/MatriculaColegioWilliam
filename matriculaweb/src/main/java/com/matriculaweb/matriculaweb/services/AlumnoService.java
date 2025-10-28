package com.matriculaweb.matriculaweb.services;

import com.matriculaweb.matriculaweb.model.Alumno;
import com.matriculaweb.matriculaweb.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository repo;

    public List<Alumno> listarTodos() {
        return repo.findAll();
    }

    public void guardar(Alumno alumno) {
        repo.save(alumno);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public Optional<Alumno> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public List<Alumno> buscarPorNombreODni(String query) {
        return repo.findByNombresContainingIgnoreCaseOrDniContaining(query, query);
    }
}
