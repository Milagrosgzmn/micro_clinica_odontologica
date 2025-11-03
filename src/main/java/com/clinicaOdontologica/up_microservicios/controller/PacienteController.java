package com.clinicaOdontologica.up_microservicios.controller;

import com.clinicaOdontologica.up_microservicios.entity.Paciente;
import com.clinicaOdontologica.up_microservicios.service.PacienteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RestController //Los request - no view
@RequestMapping("/api/paciente") //lo que venga  a este endpoint
public class PacienteController {
    private final Logger log = LogManager.getLogger(PacienteController.class);

    private final PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService){

        this.pacienteService = pacienteService;
    }

    @GetMapping("")
    public ResponseEntity<List<Paciente>> getAllPacientes(){
        List<Paciente> listaPac = pacienteService.obtenerPacientes();
        if(listaPac.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(listaPac);
    }

    @PostMapping("")
    public ResponseEntity<Paciente> insertPaciente(@RequestBody Paciente paciente){
        Optional<Paciente> pacienteExistente = pacienteService.obtenerPacientePorMail(paciente.getEmail());
        if (pacienteExistente.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Paciente existente");
        }

        Paciente pacienteCreado = pacienteService.guardarPaciente(paciente);

        return new ResponseEntity<>(pacienteCreado, HttpStatus.CREATED);
    }

    //los metodos que conectan al service
    @GetMapping("/{id}") //aclaramos el metodo y la variable que llega por el path
    public ResponseEntity<Optional<Paciente>> getPacientePorId(@PathVariable Long id) {
        Optional<Paciente> found = pacienteService.obtenerPaciente(id);
        if (found.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado");
        }
        return ResponseEntity.ok(found);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePaciente(@PathVariable Long id, @RequestBody Paciente paciente) {
        Optional<Paciente> found = pacienteService.obtenerPaciente(id);
        if (found.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado");
        }
        pacienteService.actualizarPaciente(paciente);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deletePaciente(@PathVariable Long id) {
        Optional<Paciente> found = pacienteService.obtenerPaciente(id);
        if (found.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado");
        }
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.ok(id);
    }

}
