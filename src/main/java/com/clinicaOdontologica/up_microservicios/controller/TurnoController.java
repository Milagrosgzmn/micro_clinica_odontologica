package com.clinicaOdontologica.up_microservicios.controller;

import com.clinicaOdontologica.up_microservicios.dto.TurnoDTO;
import com.clinicaOdontologica.up_microservicios.dto.TurnoDTORequest;
import com.clinicaOdontologica.up_microservicios.entity.Odontologo;
import com.clinicaOdontologica.up_microservicios.entity.Paciente;
import com.clinicaOdontologica.up_microservicios.entity.Turno;
import com.clinicaOdontologica.up_microservicios.service.OdontologoService;
import com.clinicaOdontologica.up_microservicios.service.PacienteService;
import com.clinicaOdontologica.up_microservicios.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/turno")
public class TurnoController {

    //private service de turno
    private final OdontologoService odontologoService;
    private final PacienteService pacienteService;
    private final TurnoService turnoService;

    @Autowired
    public TurnoController(TurnoService turnoService, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoService = turnoService;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    //turnos pertenecientes a un odontologo
    //turnos de un paciente
    //turnos del dia - fecha hoy
    // etc

    @GetMapping("")
    public ResponseEntity<List<TurnoDTO>> getTurnos() {
        List<TurnoDTO> turnos = turnoService.obtenerTurnos();
        return ResponseEntity.ok(turnos);
    }

    @PostMapping("")
    public ResponseEntity<TurnoDTO> saveTurno(@RequestBody TurnoDTORequest turno) {

        Optional<Paciente> paciente = pacienteService.obtenerPaciente(turno.getPacienteId());
        Optional<Odontologo> odontologo = odontologoService.obtenerOdontologoPorId(turno.getOdontologoId());
        if (paciente.isEmpty() || odontologo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Turno nuevoTurno = new Turno(odontologo.get(), paciente.get(), turno.getFechaCita());

        return new ResponseEntity<>(turnoService.guardarTurno(nuevoTurno), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteTurno(@PathVariable("id") Long id) {
        Optional<TurnoDTO> turno = turnoService.obtenerTurno(id);
        if (turno.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Peticion incorrecta");
        }
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> putTurno(@PathVariable("id") Long id, @RequestBody Turno turno) {
        Optional<TurnoDTO> turnoExiste = turnoService.obtenerTurno(id);
        if (turnoExiste.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Peticion incorrecta");
        }
        turnoService.actualizarTurno(turno);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoDTO> getTurno(@PathVariable("id") Long id) {
        Optional<TurnoDTO> turno = turnoService.obtenerTurno(id);
        if (turno.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Turno no encontrado");
        }
        return ResponseEntity.ok(turno.get());
    }
}
