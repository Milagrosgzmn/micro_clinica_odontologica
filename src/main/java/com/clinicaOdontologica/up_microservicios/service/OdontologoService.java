package com.clinicaOdontologica.up_microservicios.service;

import com.clinicaOdontologica.up_microservicios.entity.Odontologo;
import com.clinicaOdontologica.up_microservicios.repository.OdontologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {
    private final OdontologoRepository odontologoRepository;

    @Autowired
    public OdontologoService(OdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    public Odontologo guardarOdontologo(Odontologo odontologo) {
        return odontologoRepository.save(odontologo);
    }

    public Optional<Odontologo> obtenerOdontologoPorId(Long id) {
        return odontologoRepository.findById(id);
    }

    public List<Odontologo> obtenerOdontologos() {
        return odontologoRepository.findAll();
    }

    public Optional<Odontologo> obtenerOdontologoPorMatricula(String matricula) {
        return odontologoRepository.findByMatricula(matricula);
    }

    public void actualizarOdontologo(Odontologo odontologo) {
        // validar
        odontologoRepository.save(odontologo);
    }

    public void eliminarOdontologo(Long id) {
        odontologoRepository.deleteById(id);
    }
}