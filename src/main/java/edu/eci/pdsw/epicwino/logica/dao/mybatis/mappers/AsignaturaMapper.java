package edu.eci.pdsw.epicwino.logica.dao.mybatis.mappers;

import edu.eci.pdsw.epicwino.logica.entidades.Asignatura;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 */
public interface AsignaturaMapper {
    void saveAsignatura(@Param("asignatura") Asignatura asignatura,
            @Param("programa") int idPrograma);
}
