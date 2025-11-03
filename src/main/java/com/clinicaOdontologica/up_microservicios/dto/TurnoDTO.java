package com.clinicaOdontologica.up_microservicios.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TurnoDTO {
    Long id;
    LocalDateTime fechaCita;
    PacienteDTO pacienteDTO;
    OdontologoDTO odontologoDTO;
}
