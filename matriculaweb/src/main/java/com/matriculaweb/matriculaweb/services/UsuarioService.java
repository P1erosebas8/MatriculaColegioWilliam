package com.matriculaweb.matriculaweb.services;

import com.matriculaweb.matriculaweb.model.Usuario;
import com.matriculaweb.matriculaweb.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository repo;

    public List<Usuario> listar() {
        return repo.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Optional<Usuario> buscarPorUsuario(String usuario) {
        return repo.findByUsuario(usuario);
    }

    public List<Usuario> buscarPorNombre(String usuario) {
        return repo.findByUsuarioContainingIgnoreCase(usuario);
    }

    public void guardar(Usuario usuario) {

        if (usuario.getClave() != null && !usuario.getClave().isBlank()) {

            if (!usuario.getClave().startsWith("$2a$")) {
                usuario.setClave(passwordEncoder.encode(usuario.getClave()));
            }
        }

        repo.save(usuario);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public void actualizar(Usuario usuarioForm) {
        Usuario usuarioBD = repo.findById(usuarioForm.getId()).orElse(null);

        if (usuarioBD == null) return;

        usuarioBD.setUsuario(usuarioForm.getUsuario());
        usuarioBD.setNombre(usuarioForm.getNombre());
        usuarioBD.setRol(usuarioForm.getRol());

        if (usuarioForm.getClave() != null && !usuarioForm.getClave().isBlank()) {

            if (!usuarioForm.getClave().startsWith("$2a$")) {
                usuarioBD.setClave(passwordEncoder.encode(usuarioForm.getClave()));
            } else {
                usuarioBD.setClave(usuarioForm.getClave());
            }
        }


        repo.save(usuarioBD);
    }
}
