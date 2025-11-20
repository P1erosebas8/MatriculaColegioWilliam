package com.matriculaweb.matriculaweb.services;

import com.matriculaweb.matriculaweb.model.Curso;
import com.matriculaweb.matriculaweb.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository repo;

    public List<Curso> listarTodos() {
        return repo.findAll();
    }

    public List<Curso> findActivos() {
        return repo.findByActivoTrue();
    }

    public void guardar(Curso curso) {
        repo.save(curso);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public Optional<Curso> buscarPorId(Long id) {
        return repo.findById(id);
    }
}
