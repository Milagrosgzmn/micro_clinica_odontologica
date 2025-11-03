package com.clinicaOdontologica.up_microservicios.controller;

import com.clinicaOdontologica.up_microservicios.entity.Odontologo;
import com.clinicaOdontologica.up_microservicios.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/odontologo")
public class OdontologoController {

    @Autowired
    private OdontologoService odontologoService;

    @GetMapping("")
    public ResponseEntity<List<Odontologo>> getOdontologos() {
        return ResponseEntity.ok(odontologoService.obtenerOdontologos());
    }

    @PostMapping("")
    public ResponseEntity<Odontologo> saveOdontologo(@RequestBody Odontologo odontologo) {
        Odontologo guardado = odontologoService.guardarOdontologo(odontologo);
        //si no hay problema al guardar  -- logica validacion.

        return new ResponseEntity<>(guardado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> putOdontologo(@PathVariable Long id, @RequestBody Odontologo odontologo) {
        Optional<Odontologo> odonto = odontologoService.obtenerOdontologoPorId(id);
        if (odonto.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Peticion incorrecta");
        }
        odontologoService.actualizarOdontologo(odontologo);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOdontologo(@PathVariable Long id) {
        Optional<Odontologo> odonto = odontologoService.obtenerOdontologoPorId(id);
        if (odonto.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Peticion incorrecta");
        }
        odontologoService.eliminarOdontologo(id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> getOdontologo(@PathVariable Long id) {
        Optional<Odontologo> foundOdonto = odontologoService.obtenerOdontologoPorId(id);
        if (foundOdonto.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Odontologo no encontrado");
        }
        return ResponseEntity.ok(foundOdonto.get());
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<Odontologo> getOdontologoByMatriculo(@PathVariable String matricula) {
        Optional<Odontologo> foundOdonto = odontologoService.obtenerOdontologoPorMatricula(matricula);
        if (foundOdonto.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Odontologo no encontrado");
        }
        return ResponseEntity.ok(foundOdonto.get());
    }
}
