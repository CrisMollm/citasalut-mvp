package com.citasalut.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="especialidad")
@Data
@NoArgsConstructor
public class Especialidad {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nombre;
}