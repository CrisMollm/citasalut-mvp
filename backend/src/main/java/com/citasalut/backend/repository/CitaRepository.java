package com.citasalut.backend.repository;

import com.citasalut.backend.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepository extends JpaRepository<Cita,Long> {
}
