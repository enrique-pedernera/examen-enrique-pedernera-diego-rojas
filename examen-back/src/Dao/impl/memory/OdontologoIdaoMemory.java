package Dao.impl.memory;

import Dao.impl.Idao;
import model.Odontologo;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class OdontologoIdaoMemory implements Idao<Odontologo> {
    public static final Logger logger = Logger.getLogger(OdontologoIdaoMemory.class);
    private List<Odontologo> odontologos = new ArrayList<>();
    @Override
    public Odontologo guardar(Odontologo odontologo) {
        odontologo.setID(odontologos.size()+ 1);
        odontologos.add(odontologo);
        logger.info("En memoria se agrego al odontologo " + odontologo);
        return odontologo;
    }

    @Override
    public Odontologo buscarXID(Integer id) {
        Odontologo odontologoMatch = null;
        for (Odontologo odontologo : odontologos){
            if (odontologo.getID().equals(id)){
                odontologoMatch = odontologo;
                break;
            }
        }
        if (odontologoMatch != null){
            logger.info("Se encontro en Memoria al siguiente odontologo " + odontologoMatch);
        }else logger.info("En memoria no se encontro ningun Odontologo con este id " + id);
        return odontologoMatch;
    }

    @Override
    public List<Odontologo> buscarTodos() {
        logger.info(odontologos);
        return odontologos;
    }
}
