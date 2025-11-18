package com.matriculaweb.matriculaweb.services;

import com.matriculaweb.matriculaweb.model.Usuario;
import com.matriculaweb.matriculaweb.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        String rol = usuario.getRol();
        if (!rol.startsWith("ROLE_")) {
            rol = "ROLE_" + rol;
        }

        System.out.println(">>> Usuario autenticado: " + usuario.getUsuario());
        System.out.println(">>> Rol asignado: " + rol);

        return new User(
                usuario.getUsuario(),
                usuario.getClave(),
                List.of(new SimpleGrantedAuthority(rol))
        );
    }
}
