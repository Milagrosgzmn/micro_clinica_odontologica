package com.clinicaOdontologica.up_microservicios.service;

import com.clinicaOdontologica.up_microservicios.entity.Paciente;
import com.clinicaOdontologica.up_microservicios.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service //Indicamos a spring que es un service
public class PacienteService {
    private final PacienteRepository pacienteRepository;

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente guardarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public List<Paciente> obtenerPacientes() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> obtenerPaciente(Long id) {
        return pacienteRepository.findById(id);
    }

    public Optional<Paciente> obtenerPacientePorMail(String email) {
        return pacienteRepository.findByEmail(email);
    }

    public Optional<Paciente> obtenerPacientePorContacto(Integer contacto) {
        return pacienteRepository.findByNumeroContacto(contacto);
    }

    public void actualizarPaciente(Paciente paciente) {
        //deberia validar campos y actualizar en base a eso - o es tan abstraido?
        pacienteRepository.save(paciente);
    }

    public void eliminarPaciente(Long id) {
        pacienteRepository.deleteById(id);
    }

}