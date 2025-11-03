package com.clinicaOdontologica.up_microservicios.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@Entity
@Table(name = "turno")
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", referencedColumnName = "id")
    private Paciente paciente;

    @JoinColumn(name = "odontologo_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Odontologo odontologo;

    private LocalDateTime fechaCita;

    public Turno() {
    }

    public Turno(Odontologo odontologo, Paciente paciente, LocalDateTime fechaCita) {
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fechaCita = fechaCita;
    }
}
