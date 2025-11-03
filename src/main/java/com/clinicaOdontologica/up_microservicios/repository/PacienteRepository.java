package com.clinicaOdontologica.up_microservicios.repository;

import com.clinicaOdontologica.up_microservicios.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByEmail(String email);

    Optional<Paciente> findByNumeroContacto(Integer numero);
}
