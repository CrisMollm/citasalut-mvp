package com.citasalut.backend.repository;

import com.citasalut.backend.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita,Long> {

    //Buscar citas entre una hora de inicio y fin
    List<Cita> findByDataHoraBetween(LocalDateTime start, LocalDateTime end);
    //historial del paciente usando su id
    List<Cita> findByUsuario_Id(Long id);

    Boolean existsByDataHora(LocalDateTime dataHora);
}
