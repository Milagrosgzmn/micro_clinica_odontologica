package com.clinicaOdontologica.up_microservicios.service;

import java.util.List;
import java.util.Optional;

public interface iService<T> {
    T guardar(T t);

    Optional<T> buscar(Integer id);

    void eliminar(Integer id);

    void actualizar(T t);

    T buscarGenerico(String parametro);

    List<T> buscarTodos();
}
