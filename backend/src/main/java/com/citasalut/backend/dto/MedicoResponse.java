package com.citasalut.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicoResponse {
    private Integer id;
    private String nombre;
    private Integer especialidadId;
}