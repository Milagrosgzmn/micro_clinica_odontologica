package com.clinicaOdontologica.up_microservicios.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TurnoDTORequest {
    private Long odontologoId;
    private Long pacienteId;
    private LocalDateTime FechaCita;
}
