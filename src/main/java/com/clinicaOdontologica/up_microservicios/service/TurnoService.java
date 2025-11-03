package com.clinicaOdontologica.up_microservicios.service;

import com.clinicaOdontologica.up_microservicios.dto.TurnoDTO;
import com.clinicaOdontologica.up_microservicios.entity.Turno;
import com.clinicaOdontologica.up_microservicios.repository.TurnoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    private final Logger logger = LogManager.getLogger(TurnoService.class);
    @Autowired
    private final TurnoRepository turnoRepository;

    @Autowired
    public TurnoService(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    public List<TurnoDTO> obtenerTurnos() {
        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoDTO> turnosDTO = new ArrayList<>();
        for (Turno turno : turnos) {
            turnosDTO.add(mapTurnoATurnoDTO(turno));
        }
        return turnosDTO;
    }

    public Optional<TurnoDTO> obtenerTurno(Long id) {
        logger.debug("Obteniendo turno id: " + id);
        Optional<Turno> turno = turnoRepository.findById(id);

        return Optional.ofNullable(mapTurnoATurnoDTO(turno.get()));
    }

    public TurnoDTO guardarTurno(Turno turno) {
        return mapTurnoATurnoDTO(turnoRepository.save(turno));
    }

    public void eliminarTurno(Long id) {
        turnoRepository.deleteById(id);
    }

    public void actualizarTurno(Turno turno) {
        turnoRepository.save(turno);
    }

    public TurnoDTO mapTurnoATurnoDTO(Turno turno) {
        TurnoDTO turnoDTO = new TurnoDTO();
        turnoDTO.setId(turno.getId());
        turnoDTO.setFechaCita(turno.getFechaCita());
        return turnoDTO;
    }
}
