package com.clinicaOdontologica.up_microservicios.dao;

import com.clinicaOdontologica.up_microservicios.model.Odontologo;

import javax.swing.text.html.Option;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OdontologoDAOH2 implements iDao<Odontologo> {

    private static final String SQL_SELECT_ONE = " SELECT * FROM ODONTOLOGOS WHERE ID=?";
    private static final String SQL_INSERT = "INSERT INTO ODONTOLOGOS(NOMBRE, APELLIDO, MATRICULA) VALUES(?,?,?)";
    private static final String SQL_SELECT_ALL = "SELECT * FROM ODONTOLOGOS";
    private static final String SQL_SELECT_BY_STRING = "SELECT  * FROM ODONTOLOGOS WHERE NOMBRE LIKE ? OR APELLIDO LIKE ? OR MATRICULA LIKE ?";
    private static final String SQL_DELETE_ONE = "DELETE FROM ODONTOLOGOS WHERE ID=?";
    private static final String SQL_UPDATE = "UPDATE ODONTOLOGOS SET NOMBRE=?, APELLIDO=? WHERE ID=?";

// TODO: Probamos el try con resources - autoliberacion, o refactorizar el resto o volver al cierre manual.

    /**
     * El metodo guarda un objeto odontologo recibido por parametro
     * Sin embargo de haber un error se puede enviar null, por lo que se debe revisar por NullPointers
     *
     * @param odontologo
     * @return Odontologo o NULL
     */
    @Override
    public Odontologo guardar(Odontologo odontologo) {
        Odontologo odoGuardado = null;
        try (Connection connection = BD.getConnection();
             PreparedStatement ps_insert = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps_insert.setString(1, odontologo.getNombre());
            ps_insert.setString(2, odontologo.getApellido());
            ps_insert.setString(3, odontologo.getMatricula());
            ps_insert.executeUpdate();

            try (ResultSet generatedKeys = ps_insert.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    odoGuardado = new Odontologo(generatedKeys.getInt(1), odontologo.getNombre(), odontologo.getApellido(), odontologo.getMatricula());
                } else {
                    System.out.println("El odontologo no pudo almacenarse.");
                }
            }
        } catch (Exception e) {
            System.out.println("error guardando odontologo: " + e.getMessage());
        }
        return odoGuardado;
    }

    @Override
    public Optional<Odontologo> buscar(Integer id) {
        Odontologo odonto = null;

        try (Connection conn = BD.getConnection();
             PreparedStatement ps_select = conn.prepareStatement(SQL_SELECT_ONE);) {
            ps_select.setInt(1, id);
            try (ResultSet rs = ps_select.executeQuery()) {
                if (rs.next()) {
                    odonto = new Odontologo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                }
            }
        } catch (Exception e) {
            System.out.println("error buscando odontologo: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        try (Connection conn = BD.getConnection();
             PreparedStatement ps_delete = conn.prepareStatement(SQL_DELETE_ONE);
        ) {
            ps_delete.setInt(1, id);
            ps_delete.executeUpdate();
        } catch (Exception e) {
            System.out.println("error eliminando odontologo: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Odontologo odontologo) {
        try (Connection conn = BD.getConnection();
             PreparedStatement ps_update = conn.prepareStatement(SQL_UPDATE);) {
            ps_update.setString(1, odontologo.getNombre());
            ps_update.setString(2, odontologo.getApellido());
            ps_update.setInt(3, odontologo.getId());
            ps_update.executeUpdate();
        } catch (Exception e) {
            System.out.println("error actualizando odontologo: " + e.getMessage());
        }
    }

    @Override
    public Odontologo buscarGenerico(String parametro) {
        Odontologo odonto = null;
        String likeParam = "%" + parametro + "%";
        try (Connection conn = BD.getConnection();
             PreparedStatement ps_select = conn.prepareStatement(SQL_SELECT_BY_STRING);
        ) {
            ps_select.setString(1, likeParam);
            ps_select.setString(2, likeParam);
            ps_select.setString(3, likeParam);

            try (ResultSet rs = ps_select.executeQuery()) {
                while (rs.next()) {
                    odonto = new Odontologo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                }
            }

        } catch (Exception e) {
            System.out.println("error buscando odontologo: " + e.getMessage());
        }
        return odonto;
    }

    @Override
    public List<Odontologo> buscarTodos() {
        List<Odontologo> odontologos = new ArrayList<>();
        Odontologo odonto = null;
        try (Connection conn = BD.getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(SQL_SELECT_ALL)) {
            while (rs.next()) {
                odonto = new Odontologo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                odontologos.add(odonto);
            }
        } catch (Exception e) {
            System.out.println("error buscando odontologos: " + e.getMessage());
        }
        return odontologos;
    }
}
