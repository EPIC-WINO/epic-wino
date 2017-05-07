package edu.eci.pdsw.epicwino.logica.dao;

import edu.eci.pdsw.epicwino.logica.entidades.Recurso;
import java.util.List;

/**
 *
 * @author Esteban
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 */
public interface RecursoDAO {

    /**
     * @obj registra un nuevo recurso
     * @param recurso
     * @throws PersistenceException falla de persistencia
     */
    public void save(Recurso recurso) throws PersistenceException;

    /**
     * @obj carga todos los recursos presentes en persistencia
     * @return lista no nula de recursos
     * @throws PersistenceException falla de persistencia
     */
    public List<Recurso> loadRecursos() throws PersistenceException;
}
