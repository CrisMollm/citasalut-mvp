package com.citasalut.backend.repository;

import com.citasalut.backend.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    // Filtramos médicos por el ID de la especialidad
    List<Medico> findByEspecialidadId(Integer especialidadId);
}