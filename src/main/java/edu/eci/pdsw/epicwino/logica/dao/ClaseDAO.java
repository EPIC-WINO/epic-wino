package edu.eci.pdsw.epicwino.logica.dao;

import edu.eci.pdsw.epicwino.logica.entidades.Clase;
import edu.eci.pdsw.epicwino.logica.entidades.Recurso;
import java.util.List;

/**
 *
 * @author Esteban
 */
public interface ClaseDAO {

    /**
     * guarda una clase en una materia
     * @param clase a guardar
     * @param idMateria id de la materia en donde guardar
     * @param periodo de la clase
     * @throws PersistenceException falla en persistencia
     */
    public void saveClase(Clase clase, int idMateria, int periodo) throws PersistenceException;

    /**
     * consultar todas las clases en un periodo
     * @param periodo de la clase
     * @return lista no nula de clases
     * @throws PersistenceException falla en persistencia
     */
    public List<Clase> loadClases(int periodo) throws PersistenceException;

    /**
     * consultar los recursos usados por una clase
     * @param idClase id de la clase
     * @return lista no nula de recursos
     * @throws PersistenceException falla en persistencia
     */
    public List<Recurso> loadRecursosConcedidos(int idClase) throws PersistenceException;

    /**
     * guarda el uso de un recurso en una clase en persistencia
     * @param idClase id de la clase
     * @param idRecurso id del recurso
     * @throws PersistenceException falla en persistencia
     */
    public void saveRecursoConcedido(int idClase, int idRecurso) throws PersistenceException;
    
    public void agregarGrupoDeMateria(int idMateria, int periodo, int idProfesor) throws PersistenceException;
}
