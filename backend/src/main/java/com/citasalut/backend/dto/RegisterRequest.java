package com.citasalut.backend.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min=3,max=50,message="El nombre debe tener minimo 3 caracteres")
    private String nombre;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min=6,message = "La contraseña tiene que tener como minimo 6 caracteres")
    private String password;

    @NotBlank(message = "El email es bobligatorio")
    @Email(message = "Debe ser un formato de emial valido")
    private String email;
}
