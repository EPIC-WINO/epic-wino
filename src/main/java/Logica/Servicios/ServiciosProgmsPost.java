package Logica.Servicios;

import Logica.Entidades.Asignatura;
import Logica.Entidades.Clase;
import Logica.Entidades.Materia;
import Logica.Entidades.Profesor;
import Logica.Entidades.Recurso;
import Logica.Entidades.Programa;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 *
 * @author Esteban
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 */
public interface ServiciosProgmsPost {
    
    /**
     * @obj registrar una materia
     * @param materia a registrar
     * @throws ExcepcionServiciosProgmsPost si ya existe, 
     * tiene algun conflicto con otra materia en el horario ;
     * existe algun atributo mal definido o no esta definido
     * @throws NullPointerException si la materia es null
     */
    void registrarMateria(Materia materia) throws ExcepcionServiciosProgmsPost;
    
    /**
     * @obj consultar todos los programas en un periodo determinado
     * @param periodo de los programas a consultar, Ej: 20171
     * @return lista no nula de los programas presentes en ese periodo
     * @throws ExcepcionServiciosProgmsPost el periodo es invalido, ej: es negativo
     */
    List<Programa> consultarProgramas(int periodo) throws ExcepcionServiciosProgmsPost;
    
    /**
     * @obj agregar una clase a una materia determinada
     * @param materia a agregarle la clase
     * @param clase a ser agregada
     * @throws ExcepcionServiciosProgmsPost algun atributo de la clase esta mal definido
     * o no esta definido en primer lugar ; la materia no existe
     * @throws NullPointerException si algun parametro es nulo
     */
    void agregarClase(Materia materia, Clase clase) throws ExcepcionServiciosProgmsPost;
    
    /**
     * @obj consulta las materias que estan presentes en un periodo determinado
     * @param periodo de donde estan presentes las materias a consultar, Ej: 20171
     * @return lista no nula de materias registradas en un periodo
     * @throws ExcepcionServiciosProgmsPost el periodo es invalido, ej: es negativo
     */
    List<Materia> consultarMaterias(int periodo) throws ExcepcionServiciosProgmsPost;
    
    /**
     * @obj consular los recursos presentes para prestamo
     * @return lista no nula de recursos presentes para prestamo
     */
    List<Recurso> consultarRecursos();
    
    /**
     * @obj consultar la disponibilidad de un recurso para prestamo en un dia y hora especificos
     * @param recurso a consultarle la disponibilidad
     * @param fecha a consultar la disponibilidad
     * @param horaInicio hora de inicio del bloque a consultar la disponibilidad
     * @param horaFin hora de finalizacion del bloque a consultar la disponibilidad
     * @return el recurso esta disponible
     * @throws ExcepcionServiciosProgmsPost el recurso no existe ; 
     * @throws NullPointerException si algun parametro es null
     */
    boolean consultarDisponibilidadRecurso(Recurso recurso, Date fecha, Time horaInicio, Time horaFin) throws ExcepcionServiciosProgmsPost;
    
    /**
     * @obj consultar los profesores presentes en un periodo
     * @param periodo donde estan presentes los profesores
     * @return lista no nula de profesores presentes en el periodo
     * @throws ExcepcionServiciosProgmsPost el periodo es invalido, Ej: es negativo
     */
    List<Profesor> consultarProfesores(int periodo) throws ExcepcionServiciosProgmsPost;
    
    /**
     * @obj consultar la disponibilidad de un profesor en una fecha y bloque de tiempo
     * @param profesor a consultar disponibilidad
     * @param fecha de la consulta
     * @param horaInicio hora de inicio del bloque a consultar la disponibilidad
     * @param horaFin hora de finalizacion del bloque a consultar la disponibilidad
     * @return el profesor esta diponible
     * @throws ExcepcionServiciosProgmsPost el profesor no existe
     * @throws NullPointerException algun parametro es null
     */
    boolean consultarDisponibilidadProfesor(Profesor profesor, Date fecha, Time horaInicio, Time horaFin) throws ExcepcionServiciosProgmsPost;
    
    /**
     * @obj consultar las asignaturas en un periodo de tiempo
     * @param periodo donde estan presentes las asignaturas
     * @return lista no nula de asignaturas presentes en el periodo
     * @throws ExcepcionServiciosProgmsPost el periodo es invalido, Ej: es negativo
     */
    List<Asignatura> consultarAsignaturas(int periodo) throws ExcepcionServiciosProgmsPost;
    
    /**
     * @obj registrar una nueva asignatura
     * @param asignatura a registrar
     * @throws ExcepcionServiciosProgmsPost la asignatura ya esta registrada ;
     * algun atributo esta mal definido o no esta definido en primer lugar
     * @throws NullPointerException la asignatura es null
     */
    void registrarAsignatura(Asignatura asignatura) throws ExcepcionServiciosProgmsPost;
    
    /**
     * @obj consultar los prerrequisitos de una materia
     * @param materia a consultar los prerrequisitos
     * @return lista no nula de materias que son prerequisitos de la materia
     * @throws ExcepcionServiciosProgmsPost la materia no existe
     * @throws NullPointerException la materia es null
     */
    List<Materia> consultarPrerrequisitos(Materia materia) throws ExcepcionServiciosProgmsPost;
    
    /**
     * @obj consultar los correquisitos de una materia
     * @param materia a consultar los correquisitos
     * @return lista no nula de materias que son correquisitos de la materia
     * @throws ExcepcionServiciosProgmsPost la materia no existe
     * @throws NullPointerException la materia es null
     */
    List<Materia> consultarCorrequisitos(Materia materia) throws ExcepcionServiciosProgmsPost;
    
    /**
     * @obj consultar los periodos registrados
     * @return lista no nula de los periodos registrados
     */
    List<Integer> consultarPeriodos();
}
