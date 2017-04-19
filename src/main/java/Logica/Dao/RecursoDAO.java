/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Dao;

import Logica.Entidades.Recurso;
import java.util.List;

/**
 *
 * @author Esteban
 */
public interface RecursoDAO {
    
    public void save(Recurso rec) throws PersistenceException;
    
    public Recurso load(int idRec) throws PersistenceException;
    
     public List<Recurso> loadRecursos() throws PersistenceException;
}
