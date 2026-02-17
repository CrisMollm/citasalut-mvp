package com.citasalut.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CitaResponse {

    private Long id;
    private Long pacientId;
    private String nombreMedico;
    private String especialidad;
    private LocalDateTime dataHora;
    private String estat;

    public CitaResponse(Long id, Long pacientId, String nombreMedico, String especialidad, LocalDateTime dataHora, String estat) {
        this.id = id;
        this.pacientId = pacientId;
        this.nombreMedico = nombreMedico;
        this.especialidad = especialidad;
        this.dataHora = dataHora;
        this.estat = estat;
    }
}
