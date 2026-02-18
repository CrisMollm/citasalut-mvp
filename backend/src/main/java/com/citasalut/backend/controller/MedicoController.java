package com.citasalut.backend.controller;

import com.citasalut.backend.dto.MedicoResponse;
import com.citasalut.backend.service.MedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService medicoService;

    @GetMapping("/especialidad/{id}")
    public ResponseEntity<List<MedicoResponse>> obtenerPorEspecialidad(@PathVariable Integer id) {
        return ResponseEntity.ok(medicoService.listarPorEspecialidad(id));
    }
}