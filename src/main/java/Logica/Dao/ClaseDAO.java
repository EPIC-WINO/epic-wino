package Logica.Dao;

import Logica.Entidades.Clase;
import Logica.Entidades.RecursoConcedido;
import java.util.List;

/**
 *
 * @author Esteban
 */
public interface ClaseDAO {
    
    public void save(Clase c) throws PersistenceException;
    
    public Clase load(int id) throws PersistenceException;
    
    public List<Clase> loadClasesPA(int anio, int semestre) throws PersistenceException;
    
    public List<RecursoConcedido> loadRecursosConcedidos(int idCl) throws PersistenceException;
    
    public void saveRecursoConcedido(int idCl, int idRe) throws PersistenceException;
}
