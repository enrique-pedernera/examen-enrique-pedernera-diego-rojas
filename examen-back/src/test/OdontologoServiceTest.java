package test;

import Dao.impl.h2.OdontologoIdao;
import model.Odontologo;
import org.junit.jupiter.api.BeforeAll;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.OdontologoService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OdontologoServiceTest {
    static Logger logger =  Logger.getLogger(OdontologoServiceTest.class);
    OdontologoService odontologoService = new OdontologoService(new OdontologoIdao());
    @BeforeAll
    static void cargarTablas(){
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:./Odontologo;INIT=RUNSCRIPT FROM 'CREATE.sql'", "sa", "sa");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
    }


    @Test
    @DisplayName("#1 Test de guardado de Odontologo")
    void caso1(){
        Odontologo odontologo = new Odontologo("123","Juan","Perez");
        Odontologo guardarOdontologoDB = odontologoService.guardarOdontologo(odontologo);
        assertNotNull(guardarOdontologoDB.getID());
    }

    @Test
    @DisplayName("#2 Test Buscar por id el odontologo")
    void caso2(){
        Integer id = 1;
        Odontologo odontologoAbuscar = odontologoService.buscarXid(id);
        assertEquals(id,odontologoAbuscar.getID());
    }
    @Test
    @DisplayName("#3 Test buscar a todos los odontologos")
    void caso3(){
        Odontologo odontologo = new Odontologo("123","Juan","Perez");
        Odontologo guardarOdontologoDB = odontologoService.guardarOdontologo(odontologo);
       List<Odontologo> listaOdontologos = odontologoService.buscarTodosLosOdontologos();
        assertEquals(2,listaOdontologos.size());
    }

}