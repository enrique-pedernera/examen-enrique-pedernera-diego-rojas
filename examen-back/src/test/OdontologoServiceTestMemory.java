package test;

import Dao.impl.h2.OdontologoIdao;
import Dao.impl.memory.OdontologoIdaoMemory;
import model.Odontologo;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.OdontologoService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OdontologoServiceTestMemory {
    static Logger logger =  Logger.getLogger(OdontologoServiceTestMemory.class);
    OdontologoService odontologoService = new OdontologoService(new OdontologoIdaoMemory());

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
        Odontologo odontologo = new Odontologo("123","Juan","Perez");
        odontologoService.guardarOdontologo(odontologo);
        Odontologo odontologo2 = new Odontologo("123","Juan","Perez");
        odontologoService.guardarOdontologo(odontologo2);
        Odontologo buscarOdontologoPorId = odontologoService.buscarXid(id);
        assertEquals(id,buscarOdontologoPorId.getID());
    }
    @Test
    @DisplayName("#3 Test buscar a todos los odontologos")
    void caso3(){
        Odontologo odontologo = new Odontologo("123","Juan","Perez");
        odontologoService.guardarOdontologo(odontologo);
        Odontologo odontologo2 = new Odontologo("123","Juan","Perez");
        odontologoService.guardarOdontologo(odontologo2);
        List<Odontologo> listaOdontologos = odontologoService.buscarTodosLosOdontologos();
        assertEquals(2,listaOdontologos.size());
    }

}