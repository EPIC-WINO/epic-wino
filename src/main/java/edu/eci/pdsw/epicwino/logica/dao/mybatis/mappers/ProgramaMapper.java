package edu.eci.pdsw.epicwino.logica.dao.mybatis.mappers;

import edu.eci.pdsw.epicwino.logica.entidades.Programa;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 * @author Fabian Ardila
 */
public interface ProgramaMapper {
    
    public Programa cosultarProgramaPorPeriodo (@Param("programa_id") int programaId, @Param("periodo") int periodo);
    
    public List<Programa> consultarProgramas (@Param("periodo") int periodo);
    
    public List<Integer> loadPeriodos();
    
    public int consultarCohorte(@Param("idMateria") int idMateria, 
            @Param("idAsignatura") int idAsignatura, 
            @Param("periodo") int periodo);
            
}
