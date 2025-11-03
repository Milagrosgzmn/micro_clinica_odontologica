package com.clinicaOdontologica.up_microservicios.controller;

import com.clinicaOdontologica.up_microservicios.model.Paciente;
import com.clinicaOdontologica.up_microservicios.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController //Los request - no view
@RequestMapping("/api/paciente") //lo que venga  a este endpoint
public class PacienteController {
    //
    private PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService){
        this.pacienteService = pacienteService;
    }
    @GetMapping()
    public ResponseEntity<List<Paciente>> getAllPacientes(){
        List<Paciente> listaPac = pacienteService.buscarTodos();
        if(listaPac.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(listaPac);
    }

    @PostMapping()
    public ResponseEntity<Paciente> insertPaciente(@RequestBody Paciente paciente){
        Paciente pacCreado =  pacienteService.guardar(paciente);

        if(pacCreado == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(pacCreado, HttpStatus.CREATED);
    }

    //los metodos que conectan al service
    @GetMapping("/{id}") //aclaramos el metodo y la variable que llega por el path
    public ResponseEntity<Optional<Paciente>> buscarPacientePorId(@PathVariable Integer id){
        Optional<Paciente> found = pacienteService.buscar(id);
        if(found == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado");
        }
        return ResponseEntity.ok(found);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer>  updatePaciente(@PathVariable Integer id, @RequestBody Paciente paciente){
        Optional<Paciente> found = pacienteService.buscar(id);
        if(found == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado");
        }
        pacienteService.actualizar(paciente);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer>  deletePaciente(@PathVariable Integer id){
        Optional<Paciente> found = pacienteService.buscar(id);
        if(found == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado");
        }
        pacienteService.eliminar(id);
        return ResponseEntity.ok(id);
    }

}
