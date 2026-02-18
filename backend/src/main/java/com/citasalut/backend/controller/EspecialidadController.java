package com.citasalut.backend.controller;

import com.citasalut.backend.dto.EspecialidadResponse;
import com.citasalut.backend.service.EspecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/especialidad")
@RequiredArgsConstructor
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    @GetMapping
    public ResponseEntity<List<EspecialidadResponse>> obtenerTodas() {
        return ResponseEntity.ok(especialidadService.listarTodas());
    }
}
