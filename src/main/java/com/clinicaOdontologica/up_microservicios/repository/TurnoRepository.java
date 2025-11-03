package com.clinicaOdontologica.up_microservicios.repository;

import com.clinicaOdontologica.up_microservicios.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TurnoRepository extends JpaRepository<Turno, Long> {
    Optional<List<Turno>> findByPacienteId(Long id);

    Optional<List<Turno>> findByOdontologoId(Long id);

    Optional<List<Turno>> findByFechaCita(LocalDateTime fechaCita);
}
