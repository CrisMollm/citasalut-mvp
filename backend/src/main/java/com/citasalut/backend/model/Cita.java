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
    @JoinColumn(name = "pacient_id", nullable = false)
    private Usuario usuario;

    @Column(name = "metge_nom", nullable = false)
    private String nombreMedico;

    @Column(nullable = false)
    private String especialidad;

    @Column(name="data_hora",nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private String estat = "PENDENTE";
}
