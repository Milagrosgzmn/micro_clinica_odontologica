package com.clinicaOdontologica.up_microservicios;

import com.clinicaOdontologica.up_microservicios.dao.BD;
import com.clinicaOdontologica.up_microservicios.dao.OdontologoDAOH2;
import com.clinicaOdontologica.up_microservicios.model.Odontologo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.clinicaOdontologica.up_microservicios.service.OdontologoService;

import java.util.List;
import java.util.Optional;

public class TestOdontologoService {
    @Test
    public void buscarOdontologo() {
        BD.crearTablas();
        OdontologoService odontoService = new OdontologoService(new OdontologoDAOH2());

        //Cuando
        Optional<Odontologo> odonto = odontoService.buscar(1);

        //Entonces

        Assertions.assertTrue(odonto != null);
    }

    @Test
    public void crearOdontologo() {
        BD.crearTablas();
        OdontologoService odontoService = new OdontologoService(new OdontologoDAOH2());

        //CUando
        Odontologo odonto1 = new Odontologo("PEPE", "PEREZ", "ABC1298");
        Odontologo odontoCreado = odontoService.guardar(odonto1);

        //Entonces
        Assertions.assertTrue(odontoCreado.getId() != null);
    }

    @Test
    public void buscarGenerico() {
        BD.crearTablas();
        OdontologoService odontoService = new OdontologoService(new OdontologoDAOH2());
        //Cuando
        Odontologo encontrado = odontoService.buscarGenerico("Meredit");

        //Entonces

        Assertions.assertTrue(encontrado.getId() != null);
        Assertions.assertEquals("Meredith", encontrado.getNombre());

    }

    @Test
    public void listarOdontologos() {
        BD.crearTablas();
        OdontologoService odontoService = new OdontologoService(new OdontologoDAOH2());
        //Cuando
        Odontologo odonto1 = new Odontologo("PEPE", "PEREZ", "ABC1298");
        Odontologo odonto2 = new Odontologo("Franco", "Gerbez", "gdhf8899");

        odonto1 = odontoService.guardar(odonto1);
        odonto2 = odontoService.guardar(odonto2);

        List<Odontologo> odontoslist = odontoService.buscarTodos();

        //Entonces
        Assertions.assertTrue(odontoslist.size() != 0);
        Assertions.assertTrue(odontoslist.size() > 2);

    }

    @Test
    public void eliminarOdontologo() {
        BD.crearTablas();
        OdontologoService odontoService = new OdontologoService(new OdontologoDAOH2());
        //Cuando
        odontoService.eliminar(1);

        List<Odontologo> lista = odontoService.buscarTodos();
        Odontologo buscamosEliminado = odontoService.buscarGenerico("Meredit");

        //Entonces
        Assertions.assertTrue(lista.size() == 1);
        Assertions.assertTrue(buscamosEliminado == null);

    }

    @Test
    public void actualizarOdontologo() {
        BD.crearTablas();
        OdontologoService odontoService = new OdontologoService(new OdontologoDAOH2());
        //Cuando
        Odontologo odoActualizar = odontoService.buscarGenerico("Meredit");

        odoActualizar.setApellido("Gerbez");

        odontoService.actualizar(odoActualizar);
        //Entonces
        Assertions.assertTrue(odontoService.buscarGenerico("Meredit") != null);
        Assertions.assertEquals(odontoService.buscarGenerico("Meredit").getApellido(), "Gerbez");

    }

}
