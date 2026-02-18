package com.citasalut.backend.service;

import com.citasalut.backend.model.Medico;
import com.citasalut.backend.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final MedicoRepository medicoRepository;

    //Nos dara los medicos de 1 especialidad seleccionda
    public List<Medico> listarPorEspecialidad(Integer especialidadId) {
        return medicoRepository.findByEspecialidadId(especialidadId);
    }
}