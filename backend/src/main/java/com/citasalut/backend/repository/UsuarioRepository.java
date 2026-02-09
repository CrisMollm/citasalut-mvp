package com.citasalut.backend.repository;

import com.citasalut.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //añadir metodo para buscar por email (futuro)
    public Optional<Usuario> findByEmail(String email);
}
