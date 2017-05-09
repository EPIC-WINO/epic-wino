package edu.eci.pdsw.epicwino.logica.dao;

import edu.eci.pdsw.epicwino.logica.entidades.Programa;
import java.util.List;

/**
 *
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 */
public interface ProgramaDAO {

    /**
     * @obj guarda un programa a persistencia
     * @param programa a guardar
     * @throws PersistenceException falla en persistencia
     * @throws NullPointerException el programa es null
     */
    public void save(Programa programa) throws PersistenceException;

    /**
     * @obj carga todos los programas que estan en un periodo
     * @param periodo
     * @return lista no nula de programas en el periodo
     * @throws PersistenceException falla en persistencia
     */
    public List<Programa> loadProgramas(int periodo) throws PersistenceException;

    /**
     * @obj carga un programa en un periodo
     * @param programId id del programa a consultar
     * @param periodo del programa
     * @return programa en el periodo
     * @throws PersistenceException falla en persistencia
     */
    public Programa loadProgram(int programId, int periodo) throws PersistenceException;

    /**
     * @obj carga todos los periodos presentes en persistencia
     * @return lista no nula de enteros
     * @throws PersistenceException falla en persistencia
     */
    public List<Integer> loadPeriodos() throws PersistenceException;
    
    /**
     * @obj consultar el cohorte correspondiente a la materia en la asignatura del un periodo
     * @param idMateria id de la materia
     * @param idAsignatura id de la asignatura
     * @param periodo
     * @return numero de cohorte
     * @throws PersistenceException falla en persistencia
     */
    public int loadCohorte(int idMateria, int idAsignatura, int periodo) throws PersistenceException;
    
    /**
     * @obj consulat todos los programas presentes
     * @return lista no nula de programas
     * @throws PersistenceException falla en persistencia
     */
    public List<Programa> consultarProgramas() throws PersistenceException;
}
