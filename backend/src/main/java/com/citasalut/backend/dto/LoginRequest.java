package com.citasalut.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un formato de emial valido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

}
