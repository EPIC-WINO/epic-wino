package edu.eci.pdsw.epicwino.logica.servicios.impl;

import edu.eci.pdsw.epicwino.logica.dao.ProgramaDAO;
import edu.eci.pdsw.epicwino.logica.entidades.Asignatura;
import edu.eci.pdsw.epicwino.logica.entidades.Clase;
import edu.eci.pdsw.epicwino.logica.entidades.Materia;
import edu.eci.pdsw.epicwino.logica.entidades.Profesor;
import edu.eci.pdsw.epicwino.logica.entidades.Programa;
import edu.eci.pdsw.epicwino.logica.entidades.Recurso;
import edu.eci.pdsw.epicwino.logica.servicios.ExcepcionServiciosProgmsPost;
import edu.eci.pdsw.epicwino.logica.servicios.ServiciosProgmsPost;
import com.google.inject.Inject;
import edu.eci.pdsw.epicwino.logica.dao.AsignaturaDAO;
import edu.eci.pdsw.epicwino.logica.dao.PersistenceException;
import edu.eci.pdsw.epicwino.logica.entidades.GrupoDeMateria;
import java.sql.Date;
import java.sql.Time;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.log4j.Logger;
import edu.eci.pdsw.epicwino.logica.dao.ClaseDAO;
import edu.eci.pdsw.epicwino.logica.dao.MateriaDAO;
import edu.eci.pdsw.epicwino.logica.dao.RecursoDAO;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Esteban
 * @author Alejandro Anzola email: alejandro.anzola@mail.escuelaing.edu.co
 */
public class ServiciosProgmsPostImpl implements ServiciosProgmsPost {

    private static final Logger LOGGER = Logger.getLogger(ServiciosProgmsPostImpl.class);

    @Inject
    private RecursoDAO daoRecurso;

    @Inject
    private ClaseDAO daoClase;

    @Inject
    private ProgramaDAO daoPrograma;

    @Inject
    private AsignaturaDAO daoAsignatura;

    @Inject
    private MateriaDAO daoMateria;

    private boolean materiaEsValida(Materia materia) {
        LOGGER.debug(MessageFormat.format("Se consulta si una materia es valida ({0})", materia));

        boolean r = materia != null;
        r = r && materia.getDescripcion() != null;
        r = r && materia.getGruposDeMateria() != null;
        r = r && materia.getNombre() != null;

        return r;
    }

    @Override
    public void registrarMateria(Materia materia, int idAsignatura) throws ExcepcionServiciosProgmsPost {
        LOGGER.info(MessageFormat.format("Se intenta registrar un materia "
                + "(materia: {0}, idAsignatura: {1})", materia, idAsignatura));

        if (materia == null) {
            throw new NullPointerException("La materia es null");
        }

        if (!materiaEsValida(materia)) {
            throw new ExcepcionServiciosProgmsPost("La materia no es valida " + materia);
        }

        if (!this.asignaturaExiste(idAsignatura)) {
            throw new ExcepcionServiciosProgmsPost("La asignatura " + idAsignatura + " no existe");
        }

        if (this.materiaExiste(materia.getId())) {
            throw new ExcepcionServiciosProgmsPost("La materia " + materia.getId() + " ya existe");
        }

        try {
            daoMateria.saveMateria(materia, idAsignatura);
        } catch (PersistenceException ex) {
            LOGGER.error("Error guardando la materia " + materia, ex);
        }
    }

    @Override
    public List<Programa> consultarProgramas(int periodo) throws ExcepcionServiciosProgmsPost {
        LOGGER.info("Se consultan los programas en el periodo " + periodo);

        if (!this.periodoEsValido(periodo) && periodo != 0) {
            throw new ExcepcionServiciosProgmsPost(MessageFormat.format("El periodo es invalido ({0})", periodo));
        }

        List<Programa> programas = null;
        try {
            LOGGER.debug("Se realiza la consulta de los programas a ProgramaDAO");
            programas = daoPrograma.loadProgramas(periodo);
        } catch (PersistenceException ex) {
            LOGGER.error("Error consultando los programas", ex);
            throw new ExcepcionServiciosProgmsPost("Error consultando los programas", ex);
        }

        LOGGER.debug("Los programas consultados son: " + programas);

        return programas;
    }

    /**
     * un periodo dado es valido
     *
     * @param periodo a validar
     * @return el periodo es valido
     */
    private boolean periodoEsValido(int periodo) {
        int anio = periodo / 10;
        int semestre = periodo % 10;
        return (1970 <= anio && anio <= 9999) && (1 <= semestre && semestre <= 3);
    }

    /**
     * una clase dada es valida (tiene todos sus atributos definidos)
     *
     * @param clase a comprobar
     * @return es valida
     */
    private boolean claseEsValida(Clase clase) {
        LOGGER.debug("Se verifica si la clase " + clase + " es valida");
        boolean r = clase != null;

        r = r && clase.getFecha() != null;
        r = r && clase.getHoraInicio() != null;
        r = r && clase.getHoraFin() != null;
        r = r && clase.getRecursos() != null;

        return r;
    }

    @Override
    public void agregarClase(int idMateria, Clase clase) throws ExcepcionServiciosProgmsPost {
        LOGGER.info(MessageFormat.format("Se agrega la clase {0} a la materia con ID: {1}", clase, idMateria));

        if (!this.materiaExiste(idMateria)) {
            throw new ExcepcionServiciosProgmsPost("La materia " + idMateria + " no existe");
        }

        if (clase == null) {
            throw new NullPointerException("La clase es null");
        }

        if (!claseEsValida(clase)) {
            throw new ExcepcionServiciosProgmsPost("Atributo de clase no definido " + clase);
        }

        int periodo = periodoDeFecha(clase.getFecha());
        LOGGER.debug("El periodo calculado es " + periodo);

        try {
            if (!this.grupoDeMateriaExiste(idMateria, periodo)) {
                throw new ExcepcionServiciosProgmsPost("No existe un grupo de "
                        + "materia que relacione la materia con el periodo Materia: " + idMateria + " Periodo: " + periodo);
            }

            daoClase.saveClase(clase, idMateria, periodo);
        } catch (PersistenceException ex) {
            LOGGER.error("Error al guardar la clase", ex);
        }
    }

    /**
     * calcular el periodo a partir de una fecha
     *
     * @param fecha
     * @return entero que representa el periodo
     */
    private int periodoDeFecha(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        return year * 10 + (month <= Calendar.MAY ? 1
                : (month >= Calendar.AUGUST ? 2 : 3));
    }

    @Override
    public List<Materia> consultarMaterias() {
        LOGGER.info("Consulta todas las materias");

        List<Materia> p = null;
        try {
            p = daoMateria.consultarMaterias();
        } catch (PersistenceException ex) {
            LOGGER.error("Error consultando materias", ex);
        }

        return p;
    }

    @Override
    public List<Materia> consultarMaterias(int periodo) throws ExcepcionServiciosProgmsPost {
        LOGGER.info("Consulta todas las materias en el periodo " + periodo);

        List<Programa> programas = this.consultarProgramas(periodo);

        Set<Materia> materias = new TreeSet<>();
        for (Programa programa : programas) {
            for (Asignatura asignatura : programa.getAsignaturas()) {
                materias.addAll(asignatura.getMaterias());
            }
        }

        return new ArrayList<>(materias);
    }

    @Override
    public List<Materia> consultarMaterias(int periodo, int idAsignatura) throws ExcepcionServiciosProgmsPost {
        LOGGER.info(MessageFormat.format("Consulta todas las materias de la "
                + "asignatura ({0}) en el periodo {1}", idAsignatura, periodo));

        List<Programa> programas = this.consultarProgramas(periodo);

        boolean found = false;
        Set<Materia> materias = new TreeSet<>();

        for (Programa programa : programas) {
            List<Asignatura> asignaturas = programa.getAsignaturas();
            for (Asignatura asignatura : asignaturas) {
                if (asignatura.getId() == idAsignatura) {
                    found = true;
                    materias.addAll(asignatura.getMaterias());
                }
            }
        }

        if (!found) {
            throw new ExcepcionServiciosProgmsPost("La asignatura con ID: " + idAsignatura + " no existe");
        }

        return new ArrayList<>(materias);
    }

    @Override
    public List<Recurso> consultarRecursos(String nombreCategoria, Date fecha, Time horaInicio, Time horaFin) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); // TODO implementar
    }

    @Override
    public int consultarDisponibilidadRecurso(int idRecurso, Date fecha, Time horaInicio, Time horaFin) throws ExcepcionServiciosProgmsPost {
        LOGGER.info(MessageFormat.format("Consultado disponibilidad recurso ({0}) en dia {1}, de {2} a {3}", idRecurso, fecha, horaInicio, horaFin));
        int r = 0;

        if (fecha == null || horaInicio == null || horaFin == null) {
            throw new NullPointerException("Algun parametro es null");
        }

        try {
            r = daoRecurso.consultarDisponibilidadRecurso(idRecurso, fecha, horaInicio, horaFin);
        } catch (PersistenceException ex) {
            LOGGER.error("Error consultando disponibilidad de recurso " + idRecurso, ex);
        }

        return r;
    }

    @Override
    public List<Profesor> consultarProfesores(int periodo) throws ExcepcionServiciosProgmsPost {
        LOGGER.info("Se consultan los profesores en el periodo " + periodo);

        if (!this.periodoEsValido(periodo)) {
            throw new ExcepcionServiciosProgmsPost("El periodo " + periodo + " es invalido");
        }

        if (!this.periodoExiste(periodo)) {
            throw new ExcepcionServiciosProgmsPost("El periodo " + periodo + " no existe");
        }

        try {
            return daoMateria.consultarProfesoresEnPeriodo(periodo);
        } catch (PersistenceException ex) {
            LOGGER.error("Error al consultar profesores en el periodo " + periodo, ex);
            throw new ExcepcionServiciosProgmsPost("Error consultando profesores en el periodo " + periodo, ex);
        }
    }

    @Override
    public boolean consultarDisponibilidadProfesor(int idProfesor, Date fecha, Time horaInicio, Time horaFin) throws ExcepcionServiciosProgmsPost {
        LOGGER.info(MessageFormat.format("Se consulta la disponibilidad "
                + "de un profesor (idProfesor: {0}, fecha: {1}, horaInicio: {2}"
                + ", horaFin: {3})", idProfesor, fecha, horaInicio, horaFin));

        try {
            return daoMateria.consultarDisponibilidadProfesor(idProfesor, fecha, horaInicio, horaFin);
        } catch (PersistenceException ex) {
            LOGGER.error("Error al consultar disponibilidad de profesor", ex);
        }

        return false;
    }

    @Override
    public List<Asignatura> consultarAsignaturas(int periodo, int idPrograma) throws ExcepcionServiciosProgmsPost {
        LOGGER.info(MessageFormat.format("Consulta las asignaturas del "
                + "programa ({0}) en el periodo {1}", idPrograma, periodo));

        List<Programa> programas = this.consultarProgramas(periodo);

        boolean found = false;
        Set<Asignatura> asignaturas = new TreeSet<>();
        for (int i = 0; i < programas.size() && !found; i++) {
            Programa p = programas.get(i);
            if (p.getId() == idPrograma) {
                found = true;
                asignaturas.addAll(p.getAsignaturas());
            }
        }

        if (!found) {
            throw new ExcepcionServiciosProgmsPost("El programa con ID: "
                    + idPrograma + " no existe");
        }

        return new ArrayList<>(asignaturas);
    }

    @Override
    public List<Asignatura> consultarAsignaturas(int idMateria) throws ExcepcionServiciosProgmsPost {
        LOGGER.info(MessageFormat.format("Consulta las asignaturas que tiene la materia ({0})", idMateria));

        List<Asignatura> as = this.consultarAsignaturas();

        boolean found = false;
        Set<Asignatura> asignaturas = new TreeSet<>();
        for (Asignatura asignatura : asignaturas) {
            List<Materia> materias = asignatura.getMaterias();
            boolean f = false;
            for (int i = 0; i < materias.size() && !f; i++) {
                if (materias.get(i).getId() == idMateria) {
                    found = true;
                    f = true;
                    asignaturas.add(asignatura);
                }
            }
        }

        if (!found) {
            throw new ExcepcionServiciosProgmsPost("La materia " + idMateria + " no existe");
        }

        return new ArrayList<>(asignaturas);

    }

    @Override
    public void registrarAsignatura(Asignatura asignatura, int idPrograma) throws ExcepcionServiciosProgmsPost {
        LOGGER.info(MessageFormat.format("Se registra la asignatura {0} en el programa ({1})", asignatura, idPrograma));

        if (asignatura == null) {
            throw new NullPointerException("La asignatura es null");
        }

        if (!this.programaExiste(idPrograma)) {
            throw new ExcepcionServiciosProgmsPost("El programa " + idPrograma + " no existe");
        }

        if (this.asignaturaExiste(asignatura.getId()) || this.asignaturaExisteEnPrograma(asignatura.getId(), idPrograma)) {
            throw new ExcepcionServiciosProgmsPost("La asignatura " + asignatura.getId() + "ya existe");
        }

        try {
            daoAsignatura.saveAsignatura(asignatura, idPrograma);
        } catch (PersistenceException ex) {
            LOGGER.error("Error al guardar la asignatura " + asignatura);
        }
    }

    private boolean asignaturaExisteEnPrograma(int idAsignatura, int idPrograma) {
        boolean found = false;

        try {
            List<Asignatura> asignaturas = this.consultarAsignaturasPorPrograma(idPrograma);

            for (int i = 0; i < asignaturas.size() && !found; i++) {
                found = asignaturas.get(i).getId() == idAsignatura;
            }
        } catch (ExcepcionServiciosProgmsPost ex) {
            // lo ignora
        }

        return found;
    }

    @Override
    public List<Materia> consultarPrerrequisitos(int idMateria) throws ExcepcionServiciosProgmsPost {
        LOGGER.info("Consultar los prerrequisitos de la materia " + idMateria);

        List<Materia> materias = null;
        try {
            materias = daoMateria.loadPrerrequisitos(idMateria);
        } catch (PersistenceException ex) {
            LOGGER.error("Error consultando los prerrequisitos de la materia " + idMateria, ex);
        }

        return materias;
    }

    @Override
    public List<Materia> consultarCorrequisitos(int idMateria) throws ExcepcionServiciosProgmsPost {
        LOGGER.info("Consultar los correquisitos de la materia " + idMateria);

        List<Materia> materias = null;
        try {
            materias = daoMateria.loadCorrequisitos(idMateria);
        } catch (PersistenceException ex) {
            LOGGER.error("Error consultando los correquisitos de la materia " + idMateria, ex);
        }

        return materias;
    }

    @Override
    public List<Integer> consultarPeriodos() {
        LOGGER.info("Se consultan todos los periodos");

        List<Integer> p = null;
        try {
            p = daoPrograma.loadPeriodos();
        } catch (PersistenceException ex) {
            LOGGER.error("Error consultando los periodos", ex);
        }

        LOGGER.info(MessageFormat.format("Los periodos consultados son: {0}", p));

        return p;
    }

    @Override
    public List<Integer> consultarPeriodos(int idPrograma) throws ExcepcionServiciosProgmsPost {
        LOGGER.info("Se consultan todos los periodos en los que esta el programa con ID: " + idPrograma);

        List<Integer> per = this.consultarPeriodos();

        boolean found = false;
        Set<Integer> periodos = new TreeSet<>();

        for (Integer i : per) {
            List<Programa> progs = this.consultarProgramas(i);
            for (int j = 0; j < progs.size(); j++) {
                Programa pro = progs.get(j);
                if (idPrograma == pro.getId()) {
                    found = true;
                    List<Asignatura> asignaturas = pro.getAsignaturas();
                    for (Asignatura asignatura : asignaturas) {
                        periodos.addAll(this.consultarPeriodosPorAsignatura(asignatura));
                    }
                }
            }
        }

        if (found == false) {
            throw new ExcepcionServiciosProgmsPost("No existe el programa " + idPrograma);
        }

        LOGGER.info(MessageFormat.format("Los periodos consultados son: {0}", periodos));

        return new ArrayList<>(periodos);
    }

    private List<Integer> consultarPeriodosPorAsignatura(Asignatura a) {
        LOGGER.debug(MessageFormat.format("Se consultan los periodos en una asignatura (ID: {0})", a.getId()));

        Set<Integer> set = new TreeSet<>();

        for (Materia m : a.getMaterias()) {
            for (GrupoDeMateria grupo : m.getGruposDeMateria()) {
                set.add(grupo.getPeriodo());
            }
        }

        return new ArrayList<>(set);
    }

    @Override
    public List<Clase> consultarClases(int periodo, int idMateria) throws ExcepcionServiciosProgmsPost {
        LOGGER.info(MessageFormat.format("Se consultan las clases de la materia"
                + "({0}) en el periodo ({1})", idMateria, periodo));

        List<Materia> materias = this.consultarMaterias(periodo);

        boolean found = false;
        Set<Clase> clases = new TreeSet<>();
        for (int i = 0; i < materias.size() && !found; i++) {
            Materia materia = materias.get(i);
            if (materia.getId() == idMateria) {
                found = true;
                for (GrupoDeMateria grupo : materia.getGruposDeMateria()) {
                    clases.addAll(grupo.getClases());
                }
            }
        }

        if (!found) {
            throw new ExcepcionServiciosProgmsPost(MessageFormat.format("La "
                    + "materia ({1}) no existe en el periodo {0}", idMateria, periodo));
        }

        return new ArrayList<>(clases);
    }

    @Override
    public List<String> consultarCategoriasRecursos() {
        LOGGER.info("Se consultan las categorias de todos los recursos");

        List<Recurso> recursos = new ArrayList<>();
        try {
            recursos = daoRecurso.loadRecursos();
        } catch (PersistenceException ex) {
            LOGGER.error("Error consultando recursos", ex);
        }

        Set<String> categorias = new TreeSet<>();
        for (Recurso recurso : recursos) {
            categorias.add(recurso.getCategoria());
        }

        return new ArrayList<>(categorias);
    }

    @Override
    public Map<Asignatura, Integer> consultarCohortesPorAsignatura(int idMateria, int periodo) throws ExcepcionServiciosProgmsPost {
        LOGGER.info(MessageFormat.format("Se consultan los cohortes por asignatura a partir"
                + "de la materia ({0}) en el periodo {1}", idMateria, periodo));

        List<Asignatura> asignaturas = this.consultarAsignaturas();

        Map<Asignatura, Integer> cohortes = new HashMap<>();
        for (Asignatura asignatura : asignaturas) {
            cohortes.put(asignatura, this.consultarCohorte(idMateria, asignatura.getId(), periodo));
        }

        return cohortes;
    }

    @Override
    public Programa consultarPrograma(int idAsignatura) throws ExcepcionServiciosProgmsPost {
        LOGGER.info("Se consulta el programa de la asignatura con ID: " + idAsignatura);

        List<Programa> programas = new ArrayList<>();
        for (Integer periodo : this.consultarPeriodos()) {
            programas.addAll(this.consultarProgramas(periodo));
        }

        Programa pro = null;
        for (int i = 0; i < programas.size() && pro == null; i++) {
            List<Asignatura> p = programas.get(i).getAsignaturas();
            for (int j = 0; j < p.size() && pro == null; j++) {
                Asignatura a = p.get(i);
                if (a.getId() == idAsignatura) {
                    pro = programas.get(i);
                }
            }
        }

        if (pro == null) {
            throw new ExcepcionServiciosProgmsPost("La asignatura no existe");
        }

        return pro;
    }

    @Override
    public void agregarCohorte(int idPrograma, int idMateria, int numCohorte, int periodo) throws ExcepcionServiciosProgmsPost {
        LOGGER.info(MessageFormat.format("Registra nuevo cohorte "
                + "({2}) en el programa ({0}), materia ({1})", idPrograma, idMateria, numCohorte));

        try {
            daoMateria.agregarCohorte(idPrograma, idMateria, numCohorte, periodo);
        } catch (PersistenceException ex) {
            LOGGER.error(MessageFormat.format("Error agregando el cohorte "
                    + "({2}) en el programa ({0}), materia ({1})", idPrograma, idMateria, numCohorte));
        }
    }

    @Override
    public Profesor consultarProfesor(int periodo, int idMateria) throws ExcepcionServiciosProgmsPost {
        LOGGER.info("Se consulta el profesor del periodo " + periodo + " de la materia " + idMateria);

        if (!this.periodoEsValido(periodo)) {
            throw new ExcepcionServiciosProgmsPost("El periodo " + periodo + " es invalido");
        }

        if (!this.periodoExiste(periodo)) {
            throw new ExcepcionServiciosProgmsPost("El periodo " + periodo + " no existe");
        }

        if (!this.materiaExiste(idMateria)) {
            throw new ExcepcionServiciosProgmsPost("La materia ID: " + idMateria + " no existe");
        }

        try {
            return daoMateria.consultarProfesoresEnPeriodoYMateria(idMateria, periodo);
        } catch (PersistenceException ex) {
            LOGGER.error(MessageFormat.format("Error consultando profesor en "
                    + "periodo {0} y materia {1}", periodo, idMateria), ex);
            throw new ExcepcionServiciosProgmsPost("Error consultando profesor", ex);
        }
    }

    @Override
    public void registrarRecurso(Recurso recurso) throws ExcepcionServiciosProgmsPost {
        LOGGER.info("Registra recurso " + recurso);

        if (recurso.getCantidad() <= 0) {
            throw new ExcepcionServiciosProgmsPost(
                    MessageFormat.format("Atributo de recurso mal definido: cantidad ({0})",
                            recurso.getCantidad()));
        }

        try {
            daoRecurso.save(recurso);
        } catch (PersistenceException ex) {
            LOGGER.error("Error registrando recurso " + recurso, ex);
        }
    }

    @Override
    public void registrarPrestamoRecursoClase(int idRecurso, Clase clase) throws ExcepcionServiciosProgmsPost {
        LOGGER.info(MessageFormat.format("Se registra el prestamo del recurso "
                + "({0}) a la clase ({1})", idRecurso, clase));

        if (clase == null) {
            throw new NullPointerException("La clase es null");
        }

        try {
            daoClase.saveRecursoConcedido(clase.getId(), idRecurso);
        } catch (PersistenceException ex) {
            LOGGER.error("Error en persistencia", ex);
        }
    }

    @Override
    public List<Recurso> consultarRecursosProgramados(int periodo) throws ExcepcionServiciosProgmsPost {
        LOGGER.info("Se consulta los recursos programados en el periodo " + periodo);

        List<Materia> materias = this.consultarMaterias(periodo);

        Set<Recurso> recursos = new TreeSet<>();
        for (Materia materia : materias) {
            for (GrupoDeMateria grupo : materia.getGruposDeMateria()) {
                if (grupo.getPeriodo() == periodo) {
                    for (Clase clase : grupo.getClases()) {
                        recursos.addAll(clase.getRecursos());
                    }
                }
            }
        }

        return new ArrayList<>(recursos);
    }

    @Override
    public int consultarCohorte(int idMateria, int periodo, int idAsignatura) throws ExcepcionServiciosProgmsPost {
        LOGGER.info(MessageFormat.format("Se consulta el cohorte de la materia "
                + "({0}) en la asignatura ({1}), en el periodo {2}", idMateria, idAsignatura, periodo));

        if (!this.materiaExiste(idMateria)) {
            throw new ExcepcionServiciosProgmsPost("La materia " + idMateria + " no existe");
        }

        if (!this.asignaturaExiste(idAsignatura)) {
            throw new ExcepcionServiciosProgmsPost("La asignatura " + idAsignatura + " no existe");
        }

        if (!this.periodoExiste(periodo)) {
            throw new ExcepcionServiciosProgmsPost("El periodo " + periodo + " no existe");
        }

        try {
            return daoPrograma.loadCohorte(idMateria, idAsignatura, periodo);
        } catch (PersistenceException ex) {
            LOGGER.error("Error al consultar los cohortes", ex);
            throw new ExcepcionServiciosProgmsPost("Error al consultar los cohortes", ex);
        }
    }

    @Override
    public List<String> consultarNiveles() {
        LOGGER.info("Se consultan los niveles de los programas");

        List<Integer> periodos = this.consultarPeriodos();

        Set<String> niveles = new TreeSet<>();
        for (Integer periodo : periodos) {
            try {
                for (Programa programa : this.consultarProgramas(periodo)) {
                    niveles.add(programa.getNivel());
                }
            } catch (ExcepcionServiciosProgmsPost ex) {
                // lo ignora
            }
        }

        return new ArrayList<>(niveles);
    }

    private boolean materiaExiste(int idMateria) {
        LOGGER.debug("Se consulta si la materia " + idMateria + " existe");

        List<Materia> materias = this.consultarMaterias();

        boolean found = false;
        for (int i = 0; i < materias.size() && !found; i++) {
            found = materias.get(i).getId() == idMateria;
        }

        return found;
    }

    private boolean asignaturaExiste(int idAsignatura) {
        LOGGER.debug("Se consulta si la asignatura " + idAsignatura + " existe");

        List<Asignatura> p = this.consultarAsignaturas();
        boolean found = false;
        for (int i = 0; i < p.size() && !found; i++) {
            found = p.get(i).getId() == idAsignatura;
        }

        return found;
    }

    List<Asignatura> consultarAsignaturas() {
        LOGGER.debug("Consulta todas las asignaturas");

        List<Programa> programas = this.consultarProgramas();

        Set<Asignatura> asignaturas = new HashSet<>();
        for (Programa programa : programas) {
            asignaturas.addAll(programa.getAsignaturas());
        }

        return new ArrayList<>(asignaturas);
    }

    private boolean programaExiste(int idPrograma) {
        LOGGER.debug("Se consulta si el programa " + idPrograma + " existe");

        List<Programa> p = this.consultarProgramas();
        boolean found = false;
        for (int i = 0; i < p.size() && !found; i++) {
            found = p.get(i).getId() == idPrograma;
        }

        return found;
    }

    @Override
    public List<Programa> consultarProgramas() {
        LOGGER.info("Consulta todas los programas");

        List<Programa> p = null;
        try {
            p = daoPrograma.consultarProgramas();
        } catch (PersistenceException ex) {
            LOGGER.error("Error consultando programas", ex);
        }

        return p == null ? new ArrayList<Programa>() : p;
    }

    private boolean periodoExiste(int periodo) {
        LOGGER.debug("Se consulta si el periodo " + periodo + " existe");
        return this.consultarPeriodos().contains(periodo);
    }

    private boolean grupoDeMateriaExiste(int idMateria, int periodo) {
        LOGGER.debug(MessageFormat.format("Se consulta si el grupo de Materia "
                + "(idMateria: {0}, periodo: {1})", idMateria, periodo));
        boolean f = false;

        List<Materia> materias;
        try {
            materias = this.consultarMaterias(periodo);
        } catch (ExcepcionServiciosProgmsPost ex) {
            LOGGER.error("Error consultando las materias en el periodo " + periodo, ex);
            return false;
        }

        for (int i = 0; i < materias.size() && !f; i++) {
            if (materias.get(i).getId() == idMateria) {
                List<GrupoDeMateria> grupos = materias.get(i).getGruposDeMateria();
                for (int j = 0; j < grupos.size() && !f; j++) {
                    if (grupos.get(j).getPeriodo() == periodo) {
                        f = true;
                    }
                }
            }
        }

        return f;
    }

    @Override
    public void registrarPrograma(Programa programa) throws ExcepcionServiciosProgmsPost {
        LOGGER.info(MessageFormat.format("Registra programa ({0})", programa));

        if (programa == null) {
            throw new NullPointerException("El programa es null");
        }

        if (programa.getAsignaturas() == null) {
            throw new ExcepcionServiciosProgmsPost("Atributo de programa mal definido: asignaturas");
        }

        if (this.programaExiste(programa.getId())) {
            throw new ExcepcionServiciosProgmsPost("El programa" + programa.getId() + " ya existe");
        }

        try {
            daoPrograma.save(programa);
        } catch (PersistenceException ex) {
            LOGGER.error("Error en persistencia", ex);
        }
    }

    @Override
    public List<Clase> consultarClasesDeUnPeriodo(int periodo) throws ExcepcionServiciosProgmsPost {
        LOGGER.info("Se consultan las clases del periodo " + periodo);

        if (!this.periodoExiste(periodo)) {
            throw new ExcepcionServiciosProgmsPost("Error al consultar las "
                    + "clases, el periodo " + periodo + " no existe");
        }

        List<Clase> clases = new ArrayList<>();

        try {
            clases = daoClase.consultarClasesDeUnPeriodo(periodo);
        } catch (PersistenceException ex) {
            LOGGER.error("Error en persistencia al consultar la clases del periodo " + periodo, ex);
        }

        return clases;
    }

    @Override
    public void registrarProfesor(Profesor profesor) throws ExcepcionServiciosProgmsPost {
        LOGGER.info("Registra profesor " + profesor);

        if (profesorExiste(profesor.getId())) {
            throw new ExcepcionServiciosProgmsPost("El profesor " + profesor + " ya existe");
        }

        try {
            daoMateria.agregarProfesor(profesor);
        } catch (PersistenceException ex) {
            LOGGER.error("Error registrando profesor", ex);
        }
    }

    /**
     * un profesor ya existe
     *
     * @param idProfesor id del profesor
     * @return el profesor con ese ID ya existe
     */
    private boolean profesorExiste(int idProfesor) {
        boolean f = false;

        List<Integer> periodos = this.consultarPeriodos();

        try {
            for (int i = 0; i < periodos.size() && !f; i++) {
                List<Profesor> profesores = this.consultarProfesores(periodos.get(i));

                for (int j = 0; j < profesores.size() && !f; j++) {
                    f = profesores.get(j).getId() == idProfesor;
                }
            }
        } catch (ExcepcionServiciosProgmsPost e) {
            // lo ignora
        }

        return f;
    }

    @Override
    public void registrarRequisito(int idMateria, int idPrerequisito, boolean prerrequisito) throws ExcepcionServiciosProgmsPost {
        LOGGER.info(MessageFormat.format("Se intenta registrar un nuevo "
                + "requisito (idMateria: {0}, idPrerequisito: {1}, "
                + "Completo: {2})", idMateria, idPrerequisito, prerrequisito));
        try {
            daoMateria.registrarRequisito(idMateria, idPrerequisito, prerrequisito);
        } catch (PersistenceException ex) {
            LOGGER.error(MessageFormat.format("Error al registrar requisito de la materia ({0}) y del requisito ({1})", idMateria, idPrerequisito), ex);
        }
    }

    @Override
    public List<Clase> consultarClasesConRecursos(int periodo) throws ExcepcionServiciosProgmsPost {
        LOGGER.info("Se consultarn las clases con recursos del periodo " + periodo);

        List<Clase> clases = this.consultarClasesDeUnPeriodo(periodo);
        List<Clase> clasesConRecursos = new ArrayList<>();

        for (Clase clase : clases) {
            if (!clase.getRecursos().isEmpty()) {
                clasesConRecursos.add(clase);
            }
        }

        return clasesConRecursos;
    }

    @Override
    public List<Asignatura> consultarAsignaturasPorPrograma(int idPrograma) throws ExcepcionServiciosProgmsPost {
        LOGGER.info("Se consultan las asignaturas del programa " + idPrograma);

        if (!this.programaExiste(idPrograma)) {
            throw new ExcepcionServiciosProgmsPost("El programa con id: " + idPrograma + " no existe");
        }

        List<Programa> programas = this.consultarProgramas();
        List<Asignatura> asignaturas = null;

        for (int i = 0; i < programas.size() && asignaturas == null; i++) {
            if (programas.get(i).getId() == idPrograma) {
                asignaturas = programas.get(i).getAsignaturas();
                if (asignaturas == null) {
                    LOGGER.error("El programa " + idPrograma + " no tiene definidas sus asignaturas (null)");
                }
            }
        }

        assert asignaturas != null;

        return asignaturas;
    }
}
