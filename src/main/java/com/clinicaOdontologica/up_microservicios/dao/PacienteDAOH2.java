package com.clinicaOdontologica.up_microservicios.dao;

import com.clinicaOdontologica.up_microservicios.model.Domicilio;
import com.clinicaOdontologica.up_microservicios.model.Paciente;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PacienteDAOH2 implements iDao<Paciente>{

    private static  final Logger LOGGER = LogManager.getLogger(PacienteDAOH2.class);

    private static final String SQL_SELECT_ONE=" SELECT * FROM PACIENTES WHERE ID=?";
    private static final String SQL_INSERT_PACIENTE = "INSERT INTO PACIENTES(NOMBRE, APELLIDO, NUMEROCONTACTO, FECHAINGRESO, DOMICILIO_ID, EMAIL) VALUES(?,?,?,?,?,?)";
    private static final String SQL_SELECT_ALL = "SELECT * FROM PACIENTES";
    private static final String SQL_SELECT_BY_STRING = "SELECT  * FROM PACIENTES WHERE NOMBRE LIKE ? OR APELLIDO LIKE ? OR EMAIL LIKE ?";
    private static final String SQL_DELETE_ONE = "DELETE FROM PACIENTES WHERE ID=?";
    private static final String SQL_UPDATE = "UPDATE PACIENTES SET NOMBRE=?, APELLIDO=?, NUMEROCONTACTO=?, FECHAINGRESO=?, DOMICILIO_ID=?,EMAIL=? WHERE ID=?";

    @Override
    public Paciente guardar(Paciente paciente) {
        Connection connection = null;
        Domicilio domicilio = null;
        Paciente pacienteGuardado = null;
        try {
            connection = BD.getConnection();
            //primero guardamos el domicilio
            DomicilioDAOH2 daoAux = new DomicilioDAOH2();
            domicilio = daoAux.guardar(paciente.getDomicilio());

            //preparamos el statement
            PreparedStatement ps_insert = connection.prepareStatement(SQL_INSERT_PACIENTE, Statement.RETURN_GENERATED_KEYS);
            ps_insert.setString(1, paciente.getNombre());
            ps_insert.setString(2, paciente.getApellido());
            ps_insert.setInt(3, paciente.getNumeroContacto());
            ps_insert.setDate(4, Date.valueOf(paciente.getFechaIngreso()));
            ps_insert.setInt(5, domicilio.getId());
            ps_insert.setString(6, paciente.getEmail());

            // Ejecutamos el insert
            ps_insert.executeUpdate();

            // Obtenemos el id generado
            ResultSet rs_keys = ps_insert.getGeneratedKeys();
            if (rs_keys.next()) {
                pacienteGuardado = new Paciente(
                        rs_keys.getInt(1),
                        paciente.getNombre(),
                        paciente.getApellido(),
                        paciente.getNumeroContacto(),
                        paciente.getFechaIngreso(),
                        domicilio,
                        paciente.getEmail()
                );
            }
        } catch (Exception e) {
            System.out.println("Error al guardar datos: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("error: " + e.getMessage());
                }
            }
        }
        return pacienteGuardado;
    }

    @Override
    public Optional<Paciente> buscar(Integer id) {
        LOGGER.info("Iniciando buscar Paciente por ID: " + id);
        Connection connection=null;
        Optional<Paciente> paciente= null;
        Optional<Domicilio> domicilio= null;
        try{
            connection=BD.getConnection();
            //statement mundo java a sql
            Statement statement= connection.createStatement();
            PreparedStatement ps_select_one= connection.prepareStatement(SQL_SELECT_ONE);
            ps_select_one.setInt(1,id);
            //ResultSet mundo bdd a java
            ResultSet rs= ps_select_one.executeQuery();
            DomicilioDAOH2 daoAux= new DomicilioDAOH2();
            while(rs.next()){
                domicilio=daoAux.buscar(rs.getInt(6));
                paciente= Optional.of(new Paciente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5).toLocalDate(), domicilio, rs.getString(7)));
            }
        }catch (Exception e){
            System.out.println("Error mientras se buscaba al paciente: " + e.getMessage());
        }
        return paciente;
    }

    @Override
    public void eliminar(Integer id) {
        Connection connection = null;
        try {
            //busco el paciente para poder eliminar domicilio
            Optional<Paciente> pac = this.buscar(id);

            //elimino domicilio
            DomicilioDAOH2 daoAux = new DomicilioDAOH2();
            daoAux.eliminar(pac.get().getDomicilio().getId());

            //obtengo conexion y elimino paciente finalmente
            connection = BD.getConnection();

            PreparedStatement ps_delete = connection.prepareStatement(SQL_DELETE_ONE);
            ps_delete.setInt(1, id);
            ps_delete.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error mientras se eliminaba al paciente: " + e.getMessage());
        }
        System.out.println("paciente eliminado");
    }

    @Override
    public void actualizar(Paciente paciente) {
        Connection connection = null;
        try {
            connection = BD.getConnection();
            //actualizamos domicilio
            // TODO: consultar esto, tal vez deberia verificar con get y calcular diff, previo al update
            DomicilioDAOH2 daoAux = new DomicilioDAOH2();
            daoAux.actualizar(paciente.getDomicilio());

            //preparamos el statement
            PreparedStatement ps_update = connection.prepareStatement(SQL_UPDATE);
            ps_update.setString(1, paciente.getNombre());
            ps_update.setString(2, paciente.getApellido());
            ps_update.setInt(3, paciente.getNumeroContacto());
            ps_update.setDate(4, Date.valueOf(paciente.getFechaIngreso()));
            ps_update.setInt(5, paciente.getDomicilio().getId());
            ps_update.setString(6, paciente.getEmail());
            ps_update.setInt(7, paciente.getId());
            ps_update.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error al guardar datos: " + e.getMessage());
        }
    }

    @Override
    public Paciente buscarGenerico(String parametro) {
        Connection connection = null;
        Paciente paciente = null;
        Optional<Domicilio> domicilio = null;
        String likeParam = "%" + parametro + "%";
        try {
            connection = BD.getConnection();

            PreparedStatement ps_select_string = connection.prepareStatement(SQL_SELECT_BY_STRING);

            ps_select_string.setString(1, likeParam);
            ps_select_string.setString(2, likeParam);
            ps_select_string.setString(3, likeParam);

            ResultSet rs = ps_select_string.executeQuery();
            DomicilioDAOH2 daoAux = new DomicilioDAOH2();
            while (rs.next()) {
                domicilio = daoAux.buscar(rs.getInt(6));
                paciente = new Paciente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5).toLocalDate(), domicilio, rs.getString(7));
            }
        } catch (Exception e) {
            System.out.println("Error mientras se buscaba al paciente: " + e.getMessage());
        }
        return paciente;
    }

    @Override
    public List<Paciente> buscarTodos() {
        List<Paciente> pacientes = new ArrayList<>();
        Optional<Domicilio> domicilio = null;
        Paciente paciente = null;
        try (Connection connection = BD.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL);

            DomicilioDAOH2 daoAux = new DomicilioDAOH2();

            while (rs.next()) {
                domicilio = daoAux.buscar(rs.getInt(6));
                paciente = new Paciente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5).toLocalDate(), domicilio, rs.getString(7));
                pacientes.add(paciente);
            }
        } catch (Exception e) {
            System.out.println("Error mientras se lista a los paciente: " + e.getMessage());
        }
        return pacientes;
    }
}
