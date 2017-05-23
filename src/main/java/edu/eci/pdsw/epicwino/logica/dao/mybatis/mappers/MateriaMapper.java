package edu.eci.pdsw.epicwino.logica.dao.mybatis.mappers;

import edu.eci.pdsw.epicwino.logica.entidades.Materia;
import edu.eci.pdsw.epicwino.logica.entidades.Profesor;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author Alejandro Anzola email: alejandro.anzola@mail.escuelaing.edu.co
 */
public interface MateriaMapper {
    /**
     * @param idPrograma id del programa
     * @param idMateria id de la materia
     * @param numCohorte numero del cohorte
     * @param periodo del cohorte
     */
    void agregarCohorte(@Param("idPrograma") int idPrograma, 
            @Param("idMateria") String idMateria, @Param("numCohorte") int numCohorte,
            @Param("periodo") int periodo);
    
    /**
     * @return lista no nula de enteros
     */
    List<Integer> loadCohortes();
    
    /**
     * @param idMateria id de la materia
     * @return lista no nula de materias que son prerrequisito de la materia
     */
    List<Materia> loadPrerrequisitos(@Param("materia") String idMateria);
    
    /**
     * @param idMateria id de la materia
     * @return lista no nula de materias que son correquisito de la materia
     */
    List<Materia> loadCorrequisitos(@Param("materia") String idMateria);
    
    /**
     * @param materia a guardar
     * @param idAsignatura id de la asignatura
     */
    void saveMateria(@Param("m") Materia materia, @Param("idAsignatura")int idAsignatura);
    
    /**
     * @return lista no nula de materias
     */
    List<Materia> consultarMaterias();
    
    void agregarProfesor(@Param("idProfesor") int idProfesor, @Param("nombre") String nombre, 
            @Param("tipo") String tipo, @Param("correo") String correo, @Param("telefono") String telefono);
    
    int consultarDisponibilidadProfesor(@Param("idProf") int idProf, 
            @Param("fecha") Date fecha, @Param("horaInicio") Time horaInicio, @Param("horaFin") Time horaFin);
    
    void agregarRequisito(@Param("idMateria") String idMateria, @Param("requisito") int idRequisito, @Param("prerrequisito") String prerrequisito);
    
    Profesor loadProfesoresEnPeriodoYMateria(@Param("idMateria") String idMateria, @Param("periodo") int periodo);
    
    List<Profesor> loadProfesoresEnPeriodo(@Param("periodo") int periodo);
}
