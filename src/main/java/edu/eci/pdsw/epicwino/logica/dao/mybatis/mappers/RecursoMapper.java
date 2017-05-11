package edu.eci.pdsw.epicwino.logica.dao.mybatis.mappers;

import edu.eci.pdsw.epicwino.logica.entidades.Recurso;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author Esteban
 */
public interface RecursoMapper {
    
    /**
     * @obj agregar a la base de datos un nuevo recurso para su disposicion futura
     * @param rec recurso nuevo para agregar
     */
    public void agregarRecurso(@Param("recurso") Recurso rec);
    
    /**
     * @obj consultar en la base de datos el recurso asociado
     * @param idRec identificador del recurso
     * @return recurso asociado
     */
    public Recurso consultarRecurso(@Param("idrec") int idRec);
    
    /**
     * @obj consultar en la base de datos todos los recursos con los que dispone la universidad
     * @return recursos con los que se dispone
     */
    public List<Recurso> consultarRecursos();
    
    public Integer consultarDisponibilidadRecurso(@Param("idRecurso") int idRecurso, 
            @Param("fecha") Date fecha, @Param("horaInicio") Time horaInicio, @Param("horaFin") Time horaFin);
}
