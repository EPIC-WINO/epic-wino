package Logica.Dao.MyBatis.Mappers;

import Logica.Entidades.Programa;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 * @author Fabian Ardila
 */
public interface ProgramaMapper {
    
    public Programa cosultarProgramaPorPeriodo (@Param("programa_id") int programaId, @Param("periodo") int periodo);
    
}
