package com.clinicaOdontologica.up_microservicios.service;

import com.clinicaOdontologica.up_microservicios.dao.iDao;
import com.clinicaOdontologica.up_microservicios.model.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service //Indicamos a spring que es un service
public class PacienteService implements iService<Paciente> {
    private final iDao<Paciente> pacienteiDao;

    @Autowired
    public PacienteService(iDao<Paciente> pacienteiDao) {
        this.pacienteiDao = pacienteiDao;
    }

    @Override
    public Paciente guardar(Paciente paciente) {
        return pacienteiDao.guardar(paciente);
    }

    @Override
    public Optional<Paciente> buscar(Integer id) {
        return pacienteiDao.buscar(id);
    }

    @Override
    public void eliminar(Integer id) {
        pacienteiDao.eliminar(id);
        return;
    }

    @Override
    public void actualizar(Paciente paciente) {
        pacienteiDao.actualizar(paciente);
        return;
    }

    @Override
    public Paciente buscarGenerico(String parametro) {
        return pacienteiDao.buscarGenerico(parametro);
    }

    @Override
    public List<Paciente> buscarTodos() {
        return pacienteiDao.buscarTodos();
    }
}
