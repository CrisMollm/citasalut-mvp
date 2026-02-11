package com.citasalut.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AuthResponse {

    private String token;
    private String type = "Bearer";
    private String nombre;
    private String email;
    private String role;

    public AuthResponse(String token, String nombre, String email, String role) {
        this.token = token;
        this.nombre = nombre;
        this.email = email;
        this.role = role;
    }

}
