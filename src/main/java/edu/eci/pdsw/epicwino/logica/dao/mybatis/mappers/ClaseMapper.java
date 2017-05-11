package edu.eci.pdsw.epicwino.logica.dao.mybatis.mappers;

import edu.eci.pdsw.epicwino.logica.entidades.Clase;
import edu.eci.pdsw.epicwino.logica.entidades.Recurso;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author Esteban
 */
public interface ClaseMapper {

    /**
     * @obj agregar a la base de datos una nueva clase en una materia
     * @param c clase a agregar
     * @param periodo
     * @param idMateria id de la materia
     */
    public void agregarClase(@Param("clase") Clase c,
            @Param("materia") int idMateria, @Param("periodo") int periodo);

    /**
     * @obj consultar en la base de datos la clase asociada con la id
     * @param id identificador de la clase
     * @return clase asociada al identificador
     */
    public Clase consultarClase(@Param("idclase") int id);

    /**
     * @obj consultar en la base de datos todas las clases asociadas a un año y
     * un semestre
     * @param periodo
     * @return consulta de todas las clases
     */
    public List<Clase> consultarClases(@Param("periodo") int periodo);

    /**
     * @obj consultar en la base de datos los recursos que requiere la clase
     * para realizarse
     * @param idCl identificador de la clase
     * @return recursos prestados a la clase
     */
    public List<Recurso> consultarRecursosConcedidos(@Param("idclase") int idCl);

    /**
     * @obj agregar a la base de datos un recurso requerido por una clase para
     * realizarse
     * @param idCl identificador de la clase
     * @param idRe identificador del recurso
     */
    public void agregarRecursoConcedido(@Param("idclase") int idCl,
            @Param("recurso") int idRe);
    
    /**
     * @obj agrega un grupo de materia
     * @param idMateria id de la materia
     * @param periodo periodo
     * @param idProfesor id del profesor
     */
    void agregarGrupoDeMateria(@Param("idMateria") int idMateria, @Param("periodo") int periodo, @Param("idProfesor") int idProfesor);
}
