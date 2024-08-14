package service;

import Dao.impl.Idao;
import Dao.impl.h2.OdontologoIdao;
import model.Odontologo;

import java.util.List;

public class OdontologoService {
    private Idao<Odontologo> OdontologoIdao;

    public OdontologoService(Idao<Odontologo> odontologoIdao) {
        OdontologoIdao = odontologoIdao;
    }

    public Odontologo guardarOdontologo(Odontologo odontologo){
        return OdontologoIdao.guardar(odontologo);
    }
    public Odontologo buscarXid(Integer id){
        return OdontologoIdao.buscarXID(id);
    }
    public List<Odontologo> buscarTodosLosOdontologos(){
        return OdontologoIdao.buscarTodos();
    }
}
