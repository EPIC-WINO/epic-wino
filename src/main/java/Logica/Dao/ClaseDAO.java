/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Dao;

import Logica.Entidades.Clase;
import Logica.Entidades.RecursoConcedido;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Esteban
 */
public interface ClaseDAO {
    
    public void save(Clase c) throws PersistenceException;
    
    public Clase load(int id) throws PersistenceException;
    
    public List<Clase> loadClasesPA(int año, int semestre) throws PersistenceException;
    
    public List<RecursoConcedido> loadRecursosConcedidos(int idCl) throws PersistenceException;
    
    public void saveRecursoConcedido(int idCl, int idRe) throws PersistenceException;
}
