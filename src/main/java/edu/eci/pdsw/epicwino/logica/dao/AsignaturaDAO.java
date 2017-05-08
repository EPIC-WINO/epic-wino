package edu.eci.pdsw.epicwino.logica.dao;

import edu.eci.pdsw.epicwino.logica.entidades.Asignatura;

/**
 *
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 */
public interface AsignaturaDAO {
    
    /**
     * @obj guardar una asignatura
     * @param asignatura a guardar
     * @param idPrograma id del programa
     * @throws PersistenceException falla en persistencia
     */
    void saveAsignatura(Asignatura asignatura, int idPrograma) throws PersistenceException;
}
