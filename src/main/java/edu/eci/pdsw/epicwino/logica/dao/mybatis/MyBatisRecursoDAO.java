/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.epicwino.logica.dao.mybatis;

import edu.eci.pdsw.epicwino.logica.dao.mybatis.mappers.RecursoMapper;
import edu.eci.pdsw.epicwino.logica.dao.PersistenceException;
import edu.eci.pdsw.epicwino.logica.dao.RecursoDAO;
import edu.eci.pdsw.epicwino.logica.entidades.Recurso;
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
