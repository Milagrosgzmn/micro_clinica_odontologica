package com.clinicaOdontologica.up_microservicios.service;

import com.clinicaOdontologica.up_microservicios.dao.iDao;
import com.clinicaOdontologica.up_microservicios.model.Odontologo;

import java.util.List;
import java.util.Optional;

public class OdontologoService implements iService<Odontologo> {
    private final iDao<Odontologo> odontologoiDao;

    public OdontologoService(iDao<Odontologo> odontologoiDao) {
        this.odontologoiDao = odontologoiDao;
    }

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        return odontologoiDao.guardar(odontologo);
    }

    @Override
    public Optional<Odontologo> buscar(Integer id) {
        return odontologoiDao.buscar(id);
    }

    @Override
    public void eliminar(Integer id) {
        odontologoiDao.eliminar(id);
    }

    @Override
    public void actualizar(Odontologo odontologo) {
        odontologoiDao.actualizar(odontologo);
    }

    @Override
    public Odontologo buscarGenerico(String parametro) {
        return odontologoiDao.buscarGenerico(parametro);
    }

    @Override
    public List<Odontologo> buscarTodos() {
        return odontologoiDao.buscarTodos();
    }
}
