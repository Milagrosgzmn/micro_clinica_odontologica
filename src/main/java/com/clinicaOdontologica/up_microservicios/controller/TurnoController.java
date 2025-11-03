package com.clinicaOdontologica.up_microservicios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/turnos")
public class TurnoController {

    //private service de turnos

    //tiene que inyectarse al constructor
    @Autowired
    public TurnoController() {

    }

    //put, delete, post, get all, get by id, get all turnos pertenecientes a un odontologo id.
}
