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
import java.util.Iterator;

/**
 *
 * @author Esteban
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
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

    @Override
    public void registrarMateria(Materia materia, int idAsignatura) throws ExcepcionServiciosProgmsPost {
        if (materia == null) {
            throw new NullPointerException("La materia es null");
        }

        /*if (materia.getGruposDeMateria() == null) {
            throw new ExcepcionServiciosProgmsPost(
                    MessageFormat.format("Atributo de materia mal definido : "
                            + "Materia({0})", materia));
        }*/

        try {
            daoMateria.saveMateria(materia, idAsignatura);
        } catch (PersistenceException ex) {
            LOGGER.error("Error guardando la materia " + materia, ex);
        }
    }

    @Override
    public List<Programa> consultarProgramas(int periodo) throws ExcepcionServiciosProgmsPost {
        LOGGER.info("Se consultan los programas en el periodo " + periodo);

        if (!(0 <= periodo && periodo <= 99999)) {
            throw new ExcepcionServiciosProgmsPost(MessageFormat.format("El periodo es invalido ({0})", periodo));
        }

        List<Programa> programas = null;
        try {
            programas = daoPrograma.loadProgramas(periodo);
        } catch (PersistenceException ex) {
            LOGGER.error("Error consultando los programas", ex);
            throw new ExcepcionServiciosProgmsPost("Error consultando los programas", ex);
        }

        return programas;
    }

    @Override
    public void agregarClase(int idMateria, Clase clase) throws ExcepcionServiciosProgmsPost {
        LOGGER.info(MessageFormat.format("Se agrega la clase {0} a la materia con ID: {1}", clase, idMateria));

        if (!this.materiaExiste(idMateria)) {
            throw new ExcepcionServiciosProgmsPost("La materia no existe");
        }
        
        if (clase == null) {
            throw new NullPointerException("La clase es null");
        }

        if (clase.getFecha() == null) {
            throw new ExcepcionServiciosProgmsPost("Atributo de clase no definido: fecha");
        }

        if (clase.getHoraInicio() == null) {
            throw new ExcepcionServiciosProgmsPost("Atributo de clase no definido: horaInicio");
        }

        if (clase.getHoraFin() == null) {
            throw new ExcepcionServiciosProgmsPost("Atributo de clase no definido: horaFin");
        }

        if (clase.getRecursos() == null) {
            throw new ExcepcionServiciosProgmsPost("Atributo de clase no definido: recursos");
        }

        int periodo;
        Calendar cal = Calendar.getInstance();
        cal.setTime(clase.getFecha());
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        periodo = year * 10 + (month < Calendar.JULY ? 1 : 2);
        LOGGER.debug("El periodo calculado es " + periodo);

        try {
            daoClase.saveClase(clase, idMateria, periodo);
        } catch (PersistenceException ex) {
            LOGGER.error("Error al guardar la clase", ex);
        }
    }

    @Override
    public List<Materia> consultarMaterias() {
        LOGGER.info("Consulta todas las materias");

        List<Integer> periodos = this.consultarPeriodos();

        Set<Materia> materias = new TreeSet<>();
        for (Integer periodo : periodos) {
            try {
                materias.addAll(this.consultarMaterias(periodo));
            } catch (ExcepcionServiciosProgmsPost ex) {
            }
        }

        return new ArrayList<>(materias);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean consultarDisponibilidadRecurso(int idRecurso, Date fecha, Time horaInicio, Time horaFin) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Profesor> consultarProfesores(int periodo) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean consultarDisponibilidadProfesor(int idProfesor, Date fecha, Time horaInicio, Time horaFin) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            throw new ExcepcionServiciosProgmsPost("La materia no existe");
        }

        return new ArrayList<>(asignaturas);

    }

    private List<Asignatura> consultarAsignaturas() {
        LOGGER.debug("Consulta todas las asignaturas");

        List<Integer> periodos = this.consultarPeriodos();

        Set<Programa> progs = new TreeSet<>();
        for (Integer i : periodos) {
            try {
                progs.addAll(this.consultarProgramas(i));
            } catch (ExcepcionServiciosProgmsPost ex) {
                // se ignora
            }
        }

        Set<Asignatura> asignaturas = new TreeSet<>();

        for (Programa programa : progs) {
            asignaturas.addAll(programa.getAsignaturas());
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
            throw new ExcepcionServiciosProgmsPost("El programa no existe");
        }

        try {
            daoAsignatura.saveAsignatura(asignatura, idPrograma);
        } catch (PersistenceException ex) {
            LOGGER.error("Error al guardar la asignatura " + asignatura);
        }
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
    public void agregarCohorte(int idPrograma, int idMateria, int numCohorte) throws ExcepcionServiciosProgmsPost {
        LOGGER.info(MessageFormat.format("Registra nuevo cohorte "
                + "({2}) en el programa ({0}), materia ({1})", idPrograma, idMateria, numCohorte));

        try {
            daoMateria.agregarCohorte(idPrograma, idMateria, numCohorte);
        } catch (PersistenceException ex) {
            LOGGER.error(MessageFormat.format("Error agregando el cohorte "
                    + "({2}) en el programa ({0}), materia ({1})", idPrograma, idMateria, numCohorte));
        }
    }

    @Override
    public Profesor consultarProfesor(int periodo, int idMateria) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public int consultarCohorte(int idMateria, int idAsignatura, int periodo) throws ExcepcionServiciosProgmsPost {
        if (!this.materiaExiste(idMateria)) {
            throw new ExcepcionServiciosProgmsPost("La materia no existe");
        }
        
        if (!this.asignaturaExiste(idAsignatura)) {
            throw new ExcepcionServiciosProgmsPost("La asignatura no existe");
        }
        
        if (!this.periodoExiste(periodo)) {
            throw new ExcepcionServiciosProgmsPost("El periodo no existe");
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
        List<Materia> materias = this.consultarMaterias();

        boolean found = false;
        for (int i = 0; i < materias.size() && !found; i++) {
            found = materias.get(i).getId() == idMateria;
        }

        return found;
    }

    private boolean asignaturaExiste(int idAsignatura) {
        List<Asignatura> asignaturas = this.consultarAsignaturas();

        boolean found = false;
        for (int i = 0; i < asignaturas.size() && !found; i++) {
            found = asignaturas.get(i).getId() == idAsignatura;
        }

        return found;
    }

    private boolean programaExiste(int idPrograma) {
        List<Integer> periodos = this.consultarPeriodos();

        Set<Programa> programas = new TreeSet<>();
        for (Integer periodo : periodos) {
            try {
                programas.addAll(this.consultarProgramas(periodo));
            } catch (ExcepcionServiciosProgmsPost ex) {
                // lo ignora
            }
        }

        boolean found = false;
        for (Iterator<Programa> iterator = programas.iterator(); iterator.hasNext() && !found;) {
            Programa next = iterator.next();
            found = next.getId() == idPrograma;
        }

        return found;
    }
    
    private boolean periodoExiste(int periodo) {
        return this.consultarPeriodos().contains(periodo);
    }
}
