package com.citasalut.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CitaResponse {

    private Long id;
    private Long pacientId;
    private String nombreMedico;
    private String especialidad;
    private LocalDateTime dataHora;
    private String estat;

    //AllArgsConstructor();
}
