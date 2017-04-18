/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Dao.MyBatis;

import Logica.Dao.PersistenceException;
import Logica.Dao.RecursoDAO;
import Logica.Entidades.Recurso;
import java.util.List;

/**
 *
 * @author Esteban
 */
public class MyBatisRecursoDAO implements RecursoDAO {

    @Override
    public void save(Recurso rec) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Recurso load(int idRec) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Recurso> loadRecursos() throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
