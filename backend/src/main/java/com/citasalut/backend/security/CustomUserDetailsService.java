package com.citasalut.backend.security;

import com.citasalut.backend.model.Usuario;
import com.citasalut.backend.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //Buscamos al usuario en la base de datos por email
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        // Construimos el usuario de Spring Security
        return User.builder()
                .username(usuario.getEmail())          // Ponemos el email donde el Useername
                .password(usuario.getPassword())   // Ponemos la contraseña
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()))) //
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}