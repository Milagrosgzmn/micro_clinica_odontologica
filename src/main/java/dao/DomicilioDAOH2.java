package dao;

import model.Domicilio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DomicilioDAOH2 implements iDao<Domicilio> {
    private static final String SQL_DOMICILIOS="SELECT * FROM DOMICILIOS WHERE ID=?";
    private static final String SQL_INSERT = "INSERT INTO DOMICILIOS(CALLE, NUMERO, LOCALIDAD,PROVINCIA) VALUES(?,?,?,?)";
    private static final String SQL_SELECT_ALL = "SELECT * FROM DOMICILIOS";
    private static final String SQL_UPDATE = "UPDATE DOMICILIOS SET CALLE=?, NUMERO=?, LOCALIDAD=?, PROVINCIA=? WHERE ID=?";
    private static final String SQL_ELIMINAR = "DELETE FROM DOMICILIOS WHERE ID=?";
    private static final String SQL_SELECT_BY_STRING = "SELECT * FROM DOMICILIOS WHERE CALLE LIKE ? OR LOCALIDAD LIKE ? OR PROVINCIA LIKE ?";

    @Override
    public Domicilio guardar(Domicilio domicilio) {
        Domicilio domGuardado = null;
        try (Connection conn = BD.getConnection();
             PreparedStatement ps_insert = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps_insert.setString(1, domicilio.getCalle());
            ps_insert.setInt(2, domicilio.getNumero());
            ps_insert.setString(3, domicilio.getLocalidad());
            ps_insert.setString(4, domicilio.getProvincia());
            ps_insert.executeUpdate();
            try (ResultSet rs = ps_insert.getGeneratedKeys()) {
                if (rs.next()) {
                    domGuardado = new Domicilio(rs.getInt(1),
                            domicilio.getCalle(), domicilio.getNumero(), domicilio.getLocalidad(), domicilio.getProvincia());
                    System.out.println("Domicilio guardado con exito");
                }
            }

        } catch (Exception e) {
            System.out.println("Error al insertar domicilio");
        }

        return domGuardado;
    }

    @Override
    public Domicilio buscar(Integer id) {
        Connection connection= null;
        Domicilio domicilio= null;
        try{
            connection= BD.getConnection();
            PreparedStatement ps_select_one= connection.prepareStatement(SQL_DOMICILIOS);
            ps_select_one.setInt(1,id);
            ResultSet rs= ps_select_one.executeQuery();
            while(rs.next()){

                domicilio= new Domicilio(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getString(4),rs.getString(5));
            }

        }catch (Exception e){
            e.getMessage();
        }
        System.out.println("domicilio encontrado");
        return domicilio;
    }

    @Override
    public void eliminar(Integer id) {
        try (Connection conn = BD.getConnection();
             PreparedStatement ps_delete = conn.prepareStatement(SQL_ELIMINAR);
        ) {
            ps_delete.setInt(1, id);
            ps_delete.executeUpdate();
        } catch (Exception e) {
            System.out.println("error eliminando domicilio: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Domicilio domicilio) {
        try (Connection conn = BD.getConnection();
             PreparedStatement ps_update = conn.prepareStatement(SQL_UPDATE);) {
            ps_update.setString(1, domicilio.getCalle());
            ps_update.setInt(2, domicilio.getNumero());
            ps_update.setString(3, domicilio.getLocalidad());
            ps_update.setString(4, domicilio.getProvincia());
            ps_update.setInt(5, domicilio.getId());
            ps_update.executeUpdate();
        } catch (Exception e) {
            System.out.println("error actualizando domicilio: " + e.getMessage());
        }
    }

    @Override
    public Domicilio buscarGenerico(String parametro) {
        Domicilio domicilio = null;
        String likeParam = "%" + parametro + "%";
        try (Connection conn = BD.getConnection();
             PreparedStatement ps_select = conn.prepareStatement(SQL_SELECT_BY_STRING)
        ) {
            ps_select.setString(1, likeParam);
            ps_select.setString(2, likeParam);
            ps_select.setString(3, likeParam);
            try (ResultSet rs = ps_select.executeQuery()) {
                while (rs.next()) {
                    domicilio = new Domicilio(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5));
                }
            }
        } catch (Exception e) {
            System.out.println("error buscando domicilio: " + e.getMessage());
        }
        return domicilio;
    }

    @Override
    public List<Domicilio> buscarTodos() {
        List<Domicilio> domicilios = new ArrayList<>();
        Domicilio domicilio = null;
        try (Connection conn = BD.getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs_all = statement.executeQuery(SQL_SELECT_ALL)) {
            while (rs_all.next()) {
                domicilio = new Domicilio(rs_all.getInt(1), rs_all.getString(2), rs_all.getInt(3), rs_all.getString(4), rs_all.getString(5));
                domicilios.add(domicilio);
            }
        } catch (Exception e) {
            System.out.println("error buscando domicilios: " + e.getMessage());
        }

        return domicilios;
    }
}
