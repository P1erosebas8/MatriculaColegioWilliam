package com.matriculaweb.matriculaweb.services;

import com.matriculaweb.matriculaweb.model.Usuario;
import com.matriculaweb.matriculaweb.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repo;

    public Optional<Usuario> buscarPorUsuario(String usuario) {
        return Optional.empty();
    }

    public void guardar(Usuario usuario) {
        repo.save(usuario);
    }
}
