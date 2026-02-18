package com.citasalut.backend.service;

import com.citasalut.backend.dto.EspecialidadResponse;
import com.citasalut.backend.repository.EspecialidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EspecialidadService {

    private final EspecialidadRepository especialidadRepository;

    public List<EspecialidadResponse> listarTodas() {
        return especialidadRepository.findAll().stream()
                .map(e -> new EspecialidadResponse(e.getId(), e.getNombre()))
                .toList();
    }
}