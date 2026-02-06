package com.citasalut.backend.repository;

import com.citasalut.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //añadir metodo para buscar por email (futuro)
}
