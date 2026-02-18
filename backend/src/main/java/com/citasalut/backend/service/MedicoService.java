package com.citasalut.backend.service;

import com.citasalut.backend.dto.MedicoResponse;
import com.citasalut.backend.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final MedicoRepository medicoRepository;

    public List<MedicoResponse> listarPorEspecialidad(Integer especialidadId) {
        return medicoRepository.findByEspecialidadId(especialidadId).stream()
                .map(m -> new MedicoResponse(m.getId(), m.getNombre(), m.getEspecialidad().getId()))
                .toList();
    }
}