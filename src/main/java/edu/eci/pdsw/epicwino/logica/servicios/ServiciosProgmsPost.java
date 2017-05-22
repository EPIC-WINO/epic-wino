package edu.eci.pdsw.epicwino.logica.servicios;

import edu.eci.pdsw.epicwino.logica.entidades.Asignatura;
import edu.eci.pdsw.epicwino.logica.entidades.Clase;
import edu.eci.pdsw.epicwino.logica.entidades.Materia;
import edu.eci.pdsw.epicwino.logica.entidades.Profesor;
import edu.eci.pdsw.epicwino.logica.entidades.Recurso;
import edu.eci.pdsw.epicwino.logica.entidades.Programa;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Esteban Murcia
 * @author Alejandro Anzola email: alejandro.anzola@mail.escuelaing.edu.co
 */
public interface ServiciosProgmsPost {

    /**
     * registrar una programa
     *
     * @param programa a registrar
     * @throws ExcepcionServiciosProgmsPost el programa ya existe; algun
     * atributo esta mal definido o no esta definido en primer lugar
     * @throws NullPointerException el programa es null
     */
    void registrarPrograma(Programa programa) throws ExcepcionServiciosProgmsPost;

    /**
     * registrar una materia
     *
     * @param materia a registrar
     * @param idAsignatura id de la asignatura en donde estara la materia
     * @throws ExcepcionServiciosProgmsPost si ya existe, tiene algun conflicto
     * con otra materia en el horario ; existe algun atributo mal definido o no
     * esta definido; la asignatura no existe
     * @throws NullPointerException si la materia es null
     */
    void registrarMateria(Materia materia, int idAsignatura) throws ExcepcionServiciosProgmsPost;

    /**
     * consultar todos los programas en un periodo determinado
     *
     * @param periodo de los programas a consultar, Ej: 20171
     * @return lista no nula de los programas presentes en ese periodo
     * @throws ExcepcionServiciosProgmsPost el periodo es invalido, ej: es
     * negativo
     */
    List<Programa> consultarProgramas(int periodo) throws ExcepcionServiciosProgmsPost;

    /**
     * agregar una clase a una materia determinada
     *
     * @param idMateria id de materia a agregarle la clase
     * @param clase a ser agregada
     * @throws ExcepcionServiciosProgmsPost algun atributo de la clase esta mal
     * definido o no esta definido en primer lugar ; la materia no existe; no
     * existe un grupo de la materia que tiene relacionada la materia con el
     * periodo
     * @throws NullPointerException si algun parametro es nulo
     */
    void agregarClase(int idMateria, Clase clase) throws ExcepcionServiciosProgmsPost;

    /**
     * consulta todas las materias registradas
     *
     * @return lista no nula de las materias registradas
     */
    List<Materia> consultarMaterias();

    /**
     * consulta las materias que estan presentes en un periodo determinado
     *
     * @param periodo de donde estan presentes las materias a consultar, Ej:
     * 20171
     * @return lista no nula de materias registradas en un periodo
     * @throws ExcepcionServiciosProgmsPost el periodo es invalido, ej: es
     * negativo
     */
    List<Materia> consultarMaterias(int periodo) throws ExcepcionServiciosProgmsPost;

    /**
     * consultar las materias que estan dentro de una asignatura en un periodo
     * determinado
     *
     * @param periodo de las materias
     * @param idAsignatura id de la asignatura que contiene las materias
     * @return lista no nula de materias
     * @throws ExcepcionServiciosProgmsPost no existe el periodo; la asignatura
     * no existe
     */
    List<Materia> consultarMaterias(int periodo, int idAsignatura) throws ExcepcionServiciosProgmsPost;

    /**
     * consultar los recursos disponibles que tienen una categoria, en una fecha
     * y horas especificas
     *
     * @param nombreCategoria nombre de la categoria de los recursos
     * @param fecha en la que son usados los recursos
     * @param horaInicio hora de inicio del uso de los recursos
     * @param horaFin hora de finalizacion del uso de los recursos
     * @return lista no nula de recursos presentes para prestamo
     * @throws ExcepcionServiciosProgmsPost no existe recurso con la categoria
     * @throws NullPointerException algun parametro es null
     */
    List<Recurso> consultarRecursos(String nombreCategoria, Date fecha, Time horaInicio, Time horaFin) throws ExcepcionServiciosProgmsPost;

    /**
     * consultar la disponibilidad de un recurso para prestamo en un dia y hora
     * especificos
     *
     * @param idRecurso a consultarle la disponibilidad
     * @param fecha a consultar la disponibilidad
     * @param horaInicio hora de inicio del bloque a consultar la disponibilidad
     * @param horaFin hora de finalizacion del bloque a consultar la
     * disponibilidad
     * @return el recurso esta disponible
     * @throws ExcepcionServiciosProgmsPost el recurso no existe ;
     * @throws NullPointerException si algun parametro es null
     */
    int consultarDisponibilidadRecurso(int idRecurso, Date fecha, Time horaInicio, Time horaFin) throws ExcepcionServiciosProgmsPost;

    /**
     * consultar los profesores presentes en un periodo
     *
     * @param periodo donde estan presentes los profesores
     * @return lista no nula de profesores presentes en el periodo
     * @throws ExcepcionServiciosProgmsPost el periodo es invalido, Ej: es
     * negativo
     */
    List<Profesor> consultarProfesores(int periodo) throws ExcepcionServiciosProgmsPost;

    /**
     * consultar la disponibilidad de un profesor en una fecha y bloque de
     * tiempo
     *
     * @param idProfesor id del profesor a consultar disponibilidad
     * @param fecha de la consulta
     * @param horaInicio hora de inicio del bloque a consultar la disponibilidad
     * @param horaFin hora de finalizacion del bloque a consultar la
     * disponibilidad
     * @return el profesor esta diponible
     * @throws ExcepcionServiciosProgmsPost el profesor no existe
     * @throws NullPointerException algun parametro es null
     */
    boolean consultarDisponibilidadProfesor(int idProfesor, Date fecha, Time horaInicio, Time horaFin) throws ExcepcionServiciosProgmsPost;

    /**
     * consultar las asignaturas en un periodo de tiempo
     *
     * @param idPrograma id del programa
     * @param periodo donde estan presentes las asignaturas
     * @return lista no nula de asignaturas presentes en el periodo
     * @throws ExcepcionServiciosProgmsPost el periodo es invalido, Ej: es
     * negativo
     */
    List<Asignatura> consultarAsignaturas(int periodo, int idPrograma) throws ExcepcionServiciosProgmsPost;

    /**
     * consultar las asignaturas que tienen una materia especifica
     *
     * @param idMateria id de la materia
     * @return lista no nula de asignaturas (al menos una asignatura) que tienen
     * entre sus materias a la materia especificada
     * @throws ExcepcionServiciosProgmsPost la materia no existe ; no hay
     * ninguna asignatura con la materia (inconsistencia)
     */
    List<Asignatura> consultarAsignaturas(int idMateria) throws ExcepcionServiciosProgmsPost;

    /**
     * registrar una nueva asignatura
     *
     * @param idPrograma id del programa que tiene a la asignatura
     * @param asignatura a registrar
     * @throws ExcepcionServiciosProgmsPost la asignatura ya esta registrada ;
     * algun atributo esta mal definido o no esta definido en primer lugar
     * @throws NullPointerException la asignatura es null
     */
    void registrarAsignatura(Asignatura asignatura, int idPrograma) throws ExcepcionServiciosProgmsPost;

    /**
     * consultar los prerrequisitos de una materia
     *
     * @param idMateria id de materia a consultar los prerrequisitos
     * @return lista no nula de materias que son prerequisitos de la materia
     * @throws ExcepcionServiciosProgmsPost la materia no existe
     */
    List<Materia> consultarPrerrequisitos(int idMateria) throws ExcepcionServiciosProgmsPost;

    /**
     * consultar los correquisitos de una materia
     *
     * @param idMateria id de materia a consultar los correquisitos
     * @return lista no nula de materias que son correquisitos de la materia
     * @throws ExcepcionServiciosProgmsPost la materia no existe
     */
    List<Materia> consultarCorrequisitos(int idMateria) throws ExcepcionServiciosProgmsPost;

    /**
     * consultar los periodos registrados
     *
     * @return lista no nula de los periodos registrados
     */
    List<Integer> consultarPeriodos();

    /**
     * consultar los periodos de los que se tiene registro un programa
     *
     * @param idPrograma id del programa
     * @return lista no nula de periodos
     * @throws ExcepcionServiciosProgmsPost el programa no existe
     */
    List<Integer> consultarPeriodos(int idPrograma) throws ExcepcionServiciosProgmsPost;

    /**
     * consultar las clases de una materia en un periodo
     *
     * @param periodo de las clases
     * @param idMateria id de la materia a consultarle sus clases
     * @return lista no nula de clases
     * @throws ExcepcionServiciosProgmsPost la materia no existe
     */
    List<Clase> consultarClases(int periodo, int idMateria) throws ExcepcionServiciosProgmsPost;

    /**
     * consultar las categorias de todos los recursos presentes
     *
     * @return lista no nula de nombre de las categorias de los recursos
     */
    List<String> consultarCategoriasRecursos();

    /**
     * consultar los cohortes por asignatura a partir de una materia y su
     * periodo
     *
     * @param idMateria id de la materia
     * @param periodo de consulta
     * @return mapa de asignaturas con sus cohortes respectivos en donde se
     * tiene a la materia
     * @throws ExcepcionServiciosProgmsPost la materia no existe en ese periodo
     */
    Map<Asignatura, Integer> consultarCohortesPorAsignatura(int idMateria, int periodo) throws ExcepcionServiciosProgmsPost;

    /**
     * consultar un programa a partir de la asignatura que esta contenida en
     * ella
     *
     * @param idAsignatura id de la asignatura
     * @return programa de la asignatura
     * @throws ExcepcionServiciosProgmsPost la asignatura no existe
     */
    Programa consultarPrograma(int idAsignatura) throws ExcepcionServiciosProgmsPost;

    /**
     * agrega un nuevo cohorte
     *
     * @param idPrograma id del programa de la materia del nuevo cohorte
     * @param idMateria id de la materia con el nuevo cohorte
     * @param numCohorte numero del cohorte
     * @param periodo del cohorte
     * @throws ExcepcionServiciosProgmsPost el cohorte ya existe; la materia no
     * existe; el programa no existe
     */
    void agregarCohorte(int idPrograma, int idMateria, int numCohorte, int periodo) throws ExcepcionServiciosProgmsPost;

    /**
     * consultar un profesor a partir de su materia y su periodo
     *
     * @param periodo de la materia
     * @param idMateria id de la materia
     * @return profesor que dicta la materia en el periodo
     * @throws ExcepcionServiciosProgmsPost la materia no existe en ese periodo
     */
    Profesor consultarProfesor(int periodo, int idMateria) throws ExcepcionServiciosProgmsPost;

    /**
     * registrar un nuevo recurso
     *
     * @param recurso a ser registrado
     * @throws ExcepcionServiciosProgmsPost el recurso ya existe ; esta mal
     * definido un atributo o no esta definido en primer lugar
     * @throws NullPointerException el recurso es null
     */
    void registrarRecurso(Recurso recurso) throws ExcepcionServiciosProgmsPost;

    /**
     * registrar el uso de un recurso a una clase
     *
     * @param idRecurso id del recurso a ser prestado
     * @param clase a la cual va a ser prestado el recurso
     * @throws ExcepcionServiciosProgmsPost el recurso ya esta asignado a la
     * clase ; no esta disponible el recurso para la hora de la clase ; el
     * recurso no existe ; la clase no existe ; algun atributo de la clase o el
     * recurso no esta bien definido o no esta definido en primer lugar ;
     * @throws NullPointerException algun parametro es null
     */
    void registrarPrestamoRecursoClase(int idRecurso, Clase clase) throws ExcepcionServiciosProgmsPost;

    /**
     * consultar los recursos que estan programados para su uso en un periodo
     *
     * @param periodo de los recursos
     * @return lista no nula de recursos
     * @throws ExcepcionServiciosProgmsPost no se encuentra registrado el
     * periodo
     */
    List<Recurso> consultarRecursosProgramados(int periodo) throws ExcepcionServiciosProgmsPost;

    /**
     * consultar el cohorte correspondiente a una materia en una asignatura en
     * un periodo
     *
     * @param idMateria id de la materia
     * @param idAsignatura id de la asignatura
     * @param periodo de la materia
     * @return numero del cohorte
     * @throws ExcepcionServiciosProgmsPost la materia no existe; la asignatura
     * no existe; no existe la materia en el periodo
     */
    int consultarCohorte(int idMateria, int periodo, int idAsignatura) throws ExcepcionServiciosProgmsPost;

    /**
     * consultar todos los niveles presentes
     *
     * @return lista no nula de strings
     */
    List<String> consultarNiveles();

    /**
     * consultar las clases que se dictan en un periodo
     *
     * @param periodo de las clases
     * @return lista no nula de clases
     * @throws ExcepcionServiciosProgmsPost no existe el periodo
     */
    List<Clase> consultarClasesDeUnPeriodo(int periodo) throws ExcepcionServiciosProgmsPost;

    /**
     * registrar un profesor
     *
     * @param profesor a registrar
     * @throws ExcepcionServiciosProgmsPost el profesor ya existe;
     */
    void registrarProfesor(Profesor profesor) throws ExcepcionServiciosProgmsPost;

    /**
     * registrar una materia requisito a una materia
     *
     * @param idMateria id de la materia
     * @param idPrerequisito id de la materia requisito
     * @param prerrequisito es prerrequisito
     * @throws ExcepcionServiciosProgmsPost la materia o el requisito no existe
     */
    void registrarRequisito(int idMateria, int idPrerequisito, boolean prerrequisito) throws ExcepcionServiciosProgmsPost;

    /**
     * consultar todos los programas registrados
     *
     * @return lista no nula de programas
     */
    List<Programa> consultarProgramas();

    /**
     * consulta todas las clases en un periodo que tienen recursos
     *
     * @param periodo de las clases
     * @return lista no nula de clases
     * @throws ExcepcionServiciosProgmsPost no existe el periodo
     */
    List<Clase> consultarClasesConRecursos(int periodo) throws ExcepcionServiciosProgmsPost;
}
