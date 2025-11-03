package com.clinicaOdontologica.up_microservicios;

import com.clinicaOdontologica.up_microservicios.dao.BD;
import com.clinicaOdontologica.up_microservicios.dao.PacienteDAOH2;
import com.clinicaOdontologica.up_microservicios.model.Domicilio;
import com.clinicaOdontologica.up_microservicios.model.Paciente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.clinicaOdontologica.up_microservicios.service.PacienteService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PacienteTestService {
    @Test
    public void buscarPaciente(){
        //DADO
        BD.crearTablas();
        PacienteService pacienteService= new PacienteService(new PacienteDAOH2());
        //CUANDO
        Optional<Paciente> paciente = pacienteService.buscar(2);
        //ENTONCES
        Assertions.assertTrue(paciente!=null);
    }

    @Test
    public void crearPaciente() {
        BD.crearTablas();
        PacienteService pacienteService = new PacienteService(new PacienteDAOH2());

        //CUANDO
        Domicilio domicilio = new Domicilio("Calle Nueva", 55, "Springfield", "USA");
        Paciente nuevo = new Paciente("Lisa", "Simpson", 555555, LocalDate.of(2025, 10, 10), domicilio, "lisa@school.com");
        Paciente guardado = pacienteService.guardar(nuevo);

        //ENTONCES
        Assertions.assertNotNull(guardado);
        Assertions.assertNotNull(guardado.getId());
        Assertions.assertEquals("Lisa", guardado.getNombre());
    }

    @Test
    public void actualizarPaciente() {
        BD.crearTablas();
        PacienteService pacienteService = new PacienteService(new PacienteDAOH2());
        //Cuando
        Paciente paciente = pacienteService.buscarGenerico("Homero");
        paciente.setApellido("Thompson");
        pacienteService.actualizar(paciente);

        Optional<Paciente> actualizado = pacienteService.buscar(paciente.getId());

        //ENTONCES
        Assertions.assertEquals("Thompson", actualizado.get().getApellido());
    }

    @Test
    public void eliminarPaciente() {
        BD.crearTablas();
        PacienteService pacienteService = new PacienteService(new PacienteDAOH2());
        //Cuando
        pacienteService.eliminar(1);
        Optional<Paciente> eliminado = pacienteService.buscar(1);

        //ENTONCES
        Assertions.assertNull(eliminado);
        Assertions.assertTrue(pacienteService.buscarTodos().size() == 1);
    }

    @Test
    public void listarPacientes() {
        BD.crearTablas();
        PacienteService pacienteService = new PacienteService(new PacienteDAOH2());
        //Cuando
        List<Paciente> pacientes = pacienteService.buscarTodos();

        //ENTONCES
        Assertions.assertNotNull(pacientes);
        Assertions.assertTrue(pacientes.size() >= 2);
    }

    @Test
    public void buscarGenerico() {
        BD.crearTablas();
        PacienteService pacienteService = new PacienteService(new PacienteDAOH2());
        //Cuando
        Paciente encontrado = pacienteService.buscarGenerico("marge");

        //ENTONCES
        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals("Marge", encontrado.getNombre());
    }
}
