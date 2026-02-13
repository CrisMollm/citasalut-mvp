package com.citasalut.backend.service;


import com.citasalut.backend.dto.*;
import com.citasalut.backend.model.Cita;
import com.citasalut.backend.model.Usuario;
import com.citasalut.backend.repository.UsuarioRepository;
import com.citasalut.backend.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    //LOGIN
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtTokenUtil.generateToken(userDetails);

        Usuario user = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new AuthResponse(token, user.getNombre(), user.getEmail(), user.getRol());
    }

    //REGISTER
    public MessageResponse register(RegisterRequest request) {
        // Comprobamos si el email ya existe
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Error: El email ya está en uso");
        }

        // Creamos el usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword())); // contrseña encriptada
        usuario.setRol("PACIENTE");

        // Guardamos en la base de datos
        usuarioRepository.save(usuario);

        // Devolvemos el mensaje de exito usando el DTO para el mensaje
        return new MessageResponse("Usuario registrado exitosamente");
    }

}
