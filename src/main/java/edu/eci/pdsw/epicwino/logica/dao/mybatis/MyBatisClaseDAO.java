/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.epicwino.logica.dao.mybatis;

import edu.eci.pdsw.epicwino.logica.dao.ClaseDAO;
import edu.eci.pdsw.epicwino.logica.dao.mybatis.mappers.ClaseMapper;
import edu.eci.pdsw.epicwino.logica.dao.PersistenceException;
import edu.eci.pdsw.epicwino.logica.entidades.Clase;
import edu.eci.pdsw.epicwino.logica.entidades.Recurso;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Esteban
 */
public class MyBatisClaseDAO implements ClaseDAO {
    
    @Inject
    private ClaseMapper claseMapper;

    @Override
    public void save(Clase c) throws PersistenceException {
        claseMapper.agregarClase(c);
    }

    @Override
    public Clase load(int id) throws PersistenceException {
        return null; // TODO implementar
    }

    @Override
    public List<Clase> loadClasesPA(int anio, int semestre) throws PersistenceException {
        return claseMapper.consultarClases(anio, semestre);
    }

    @Override
    public List<Recurso> loadRecursosConcedidos(int idCl) throws PersistenceException { // FIXME logica cambio
        return new ArrayList<>(); // TODO implementar
    }

    @Override
    public void saveRecursoConcedido(int idCl, int idRe) throws PersistenceException {
        claseMapper.agregarRecursoConcedido(idCl, idRe);
    }
    
}
