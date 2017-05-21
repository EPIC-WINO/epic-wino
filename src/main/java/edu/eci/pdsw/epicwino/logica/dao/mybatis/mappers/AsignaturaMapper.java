package edu.eci.pdsw.epicwino.logica.dao.mybatis.mappers;

import edu.eci.pdsw.epicwino.logica.entidades.Asignatura;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author Alejandro Anzola email: alejandro.anzola@mail.escuelaing.edu.co
 */
public interface AsignaturaMapper {
    void saveAsignatura(@Param("asignatura") Asignatura asignatura,
            @Param("idPrograma") int idPrograma);
    
    List<Asignatura> consultarAsignaturas();
}
