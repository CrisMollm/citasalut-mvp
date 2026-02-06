package com.citasalut.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name="cita")
@Data
@NoArgsConstructor
public class Cita {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "nombre_medico", nullable = false)
    private String nombreMedico;

    @Column(nullable = false)
    private String especialidad;

    @Column(name="fecha_hora",nullable = false)
    private LocalDateTime fechaHora;

    @Column(nullable = false)
    private String estado = "PENDENTE";
}
