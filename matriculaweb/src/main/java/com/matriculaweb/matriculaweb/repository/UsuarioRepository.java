package com.matriculaweb.matriculaweb.repository;

import com.matriculaweb.matriculaweb.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsuario(String usuario);
    List<Usuario> findByUsuarioContainingIgnoreCase(String usuario);
}
