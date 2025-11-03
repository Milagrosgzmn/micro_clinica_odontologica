package com.clinicaOdontologica.up_microservicios.dao;

import java.util.List;
import java.util.Optional;

public interface iDao<T> {
    T guardar(T t);
    Optional<T> buscar(Integer id);
    void eliminar(Integer id);
    void actualizar(T t);
    T buscarGenerico(String parametro);
    List<T> buscarTodos();
}
