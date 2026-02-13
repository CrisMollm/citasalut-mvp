package com.citasalut.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CitaRequest {

    @NotNull(message = "La fecha y hora son obligatorias")
    private LocalDateTime dataHora;

    @NotNull(message = "La especialidad es obligatoria")
    private String especialidad;

    @NotNull(message = "El nombre del médico es obligatorio")
    private String nombreMedico;

}
