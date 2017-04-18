/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Dao.MyBatis;

import Logica.Dao.ClaseDAO;
import Logica.Dao.PersistenceException;
import Logica.Entidades.Clase;
import Logica.Entidades.RecursoConcedido;
import java.util.List;

/**
 *
 * @author Esteban
 */
public class MyBatisClaseDAO implements ClaseDAO {

    @Override
    public void save(Clase c) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Clase load(int id) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Clase> loadClases() throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<RecursoConcedido> loadRecursosConcedidos(int idCl) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveRecursoConcedido(int idCl, int idRe) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
