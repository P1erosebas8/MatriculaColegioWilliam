package com.matriculaweb.matriculaweb.services;

import com.matriculaweb.matriculaweb.model.Profesor;
import com.matriculaweb.matriculaweb.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfesorService {

    @Autowired
    private ProfesorRepository repo;

    public List<Profesor> listarTodos() {
        return repo.findAll();
    }

    public void guardar(Profesor profesor) {
        repo.save(profesor);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public Optional<Profesor> buscarPorId(Long id) {
        return repo.findById(id);
    }
}
