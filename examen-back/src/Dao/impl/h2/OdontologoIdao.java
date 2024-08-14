package Dao.impl.h2;

import Dao.impl.Idao;
import model.Odontologo;
import org.apache.log4j.Logger;
import db.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdontologoIdao implements Idao<Odontologo> {
    public static final Logger logger = Logger.getLogger(OdontologoIdao.class);
    public static final String INSERT = "INSERT INTO ODONTOLOGO VALUES (DEFAULT,?,?,?)";
    public static final String SELECT_ID = "SELECT * FROM ODONTOLOGO WHERE ID = ?";
    public static final String SELECT_ALL = "SELECT * FROM ODONTOLOGO";

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        Connection connection = null;
        Odontologo OdontologoNew = null;
        try {
            connection = DbConnection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, odontologo.getNUMERO_MATRICULA());
            preparedStatement.setString(2, odontologo.getNOMBRE());
            preparedStatement.setString(3, odontologo.getAPELLIDO());
            preparedStatement.executeUpdate();
            connection.commit();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                OdontologoNew = new Odontologo(id, odontologo.getNUMERO_MATRICULA(), odontologo.getNOMBRE(), odontologo.getAPELLIDO());
            }
            logger.info("ODONTOLOGO AGREGADO A LA BASE DE DATOS H2 " + OdontologoNew);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    logger.error(ex.getMessage());
                    e.printStackTrace();
                }
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return OdontologoNew;
    }

    @Override
    public Odontologo buscarXID(Integer id) {
        Connection connection = null;
        Odontologo OdontologoMatch = null;
        try {
            connection = DbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Integer idDB = resultSet.getInt(1);
                String NUMERO_MATRICULA = resultSet.getNString(2);
                String NOMBRE = resultSet.getNString(3);
                String APELLIDO = resultSet.getNString(4);
                OdontologoMatch = new Odontologo(id, NUMERO_MATRICULA, NOMBRE, APELLIDO);
            }
            if (OdontologoMatch != null) {
                logger.info("Se encontro el siguiente Odontologo con el id: " + id + " " + OdontologoMatch);
            } else logger.info("No se encontro ningun odontologo con el id " + id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return OdontologoMatch;
    }

    @Override
    public List<Odontologo> buscarTodos() {
        List<Odontologo> allOdontologos = new ArrayList<>();
        Connection connection = null;
        Odontologo OdontologoAdd = null;
        try {
            connection = DbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer idDB = resultSet.getInt(1);
                String NUMERO_MATRICULA = resultSet.getNString(2);
                String NOMBRE = resultSet.getNString(3);
                String APELLIDO = resultSet.getNString(4);
                OdontologoAdd = new Odontologo(idDB, NUMERO_MATRICULA, NOMBRE, APELLIDO);
                allOdontologos.add(OdontologoAdd);
            }
            if (!allOdontologos.isEmpty()) {
                logger.info("Se encontraron los siguientes odontologos " + "El total es de " + allOdontologos.size() + " " + allOdontologos);
            } else logger.info("No hay odontologos en la DB");
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }

        return allOdontologos;
    }
}
