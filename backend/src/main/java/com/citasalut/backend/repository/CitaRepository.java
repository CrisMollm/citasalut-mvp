package com.citasalut.backend.repository;

import com.citasalut.backend.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita,Long> {

    //Buscar citas entre una hora de inicio y fin
    List<Cita> findByDataHoraBetweenAndEspecialidad(LocalDateTime inicio, LocalDateTime fin, String especialidad);
    //historial del paciente usando su id
    List<Cita> findByUsuario_IdOrderByDataHoraAsc(Long id);

    boolean existsByDataHoraAndEspecialidad(LocalDateTime dataHora, String especialidad);
}

