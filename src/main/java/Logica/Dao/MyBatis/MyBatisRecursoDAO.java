/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Dao.MyBatis;

import Logica.Dao.MyBatis.Mappers.RecursoMapper;
import Logica.Dao.PersistenceException;
import Logica.Dao.RecursoDAO;
import Logica.Entidades.Recurso;
import com.google.inject.Inject;
import java.util.List;

/**
 *
 * @author Esteban
 */
public class MyBatisRecursoDAO implements RecursoDAO {
    
    @Inject
    private RecursoMapper recursoMapper;
    
    @Override
    public void save(Recurso rec) throws PersistenceException {
        recursoMapper.agregarRecurso(rec);
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
