package edu.eci.pdsw.epicwino.logica.dao;

import edu.eci.pdsw.epicwino.logica.entidades.Recurso;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 *
 * @author Esteban
 * @author Alejandro Anzola email: alejandro.anzola@mail.escuelaing.edu.co
 */
public interface RecursoDAO {

    /**
     * registra un nuevo recurso
     * @param recurso a ser registrado
     * @throws PersistenceException falla de persistencia
     */
    public void save(Recurso recurso) throws PersistenceException;

    /**
     * carga todos los recursos presentes en persistencia
     * @return lista no nula de recursos
     * @throws PersistenceException falla de persistencia
     */
    public List<Recurso> loadRecursos() throws PersistenceException;
    
    public int consultarDisponibilidadRecurso(int idRecurso, Date fecha, 
            Time horaInicio, Time horaFin) throws PersistenceException;
}
