package com.clinicaOdontologica.up_microservicios.model;

import java.time.LocalDate;
import java.util.Optional;


public class Paciente {
    private Integer id;
    private String  nombre;
    private String apellido;
    private Integer numeroContacto;
    private LocalDate fechaIngreso;
    private Optional<Domicilio> domicilio;
    private String email;

    public Paciente(Integer id, String nombre, String apellido, Integer numeroContacto, LocalDate fechaIngreso, Domicilio domicilio, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroContacto = numeroContacto;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = Optional.ofNullable(domicilio);
        this.email = email;
        this.id = id;
    }

    public Paciente(String nombre, String apellido, Integer numeroContacto, LocalDate fechaIngreso, Domicilio domicilio, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroContacto = numeroContacto;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = Optional.ofNullable(domicilio);
        this.email = email;
    }

    public Paciente(int anInt, String string, String string1, int anInt1, LocalDate localDate, Optional<Domicilio> domicilio, String string2) {
        this.id = anInt;
        this.nombre = string;
        this.apellido = string1;
        this.numeroContacto = anInt1;
        this.fechaIngreso = localDate;
        this.domicilio = Optional.of(domicilio.get());
        this.email = string2;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getNumeroContacto() {
        return numeroContacto;
    }

    public void setNumeroContacto(Integer numeroContacto) {
        this.numeroContacto = numeroContacto;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Domicilio getDomicilio() {
        return domicilio.get();
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = Optional.ofNullable(domicilio);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
