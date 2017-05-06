package edu.eci.pdsw.epicwino.logica.servicios.impl;

import edu.eci.pdsw.epicwino.logica.dao.ClaseDAO;
import edu.eci.pdsw.epicwino.logica.dao.ProgramaDAO;
import edu.eci.pdsw.epicwino.logica.dao.RecursoDAO;
import edu.eci.pdsw.epicwino.logica.entidades.Asignatura;
import edu.eci.pdsw.epicwino.logica.entidades.Clase;
import edu.eci.pdsw.epicwino.logica.entidades.Materia;
import edu.eci.pdsw.epicwino.logica.entidades.Profesor;
import edu.eci.pdsw.epicwino.logica.entidades.Programa;
import edu.eci.pdsw.epicwino.logica.entidades.Recurso;
import edu.eci.pdsw.epicwino.logica.servicios.ExcepcionServiciosProgmsPost;
import edu.eci.pdsw.epicwino.logica.servicios.ServiciosProgmsPost;
import com.google.inject.Inject;
import edu.eci.pdsw.epicwino.logica.dao.PersistenceException;
import edu.eci.pdsw.epicwino.logica.entidades.GrupoDeMateria;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.log4j.Logger;

/**
 *
 * @author Esteban
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 */
public class ServiciosProgmsPostImpl implements ServiciosProgmsPost {

    private static final Logger LOGGER = Logger.getLogger(ServiciosProgmsPostImpl.class.getName());

    @Inject
    private RecursoDAO daoRecurso;

    @Inject
    private ClaseDAO daoClase;

    @Inject
    private ProgramaDAO daoPrograma;

    @Override
    public void registrarMateria(Materia materia) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Programa> consultarProgramas(int periodo) throws ExcepcionServiciosProgmsPost {
        LOGGER.info("Se consultan los programas con periodo " + periodo);

        if (!(0 <= periodo && periodo <= 99999)) {
            throw new ExcepcionServiciosProgmsPost("El periodo es invalido");
        }

        List<Programa> p = null;
        try {
            p = daoPrograma.loadProgramas(periodo);
        } catch (PersistenceException ex) {
            LOGGER.error("Error consultando los programas", ex);
            throw new ExcepcionServiciosProgmsPost("Error consultando los programas", ex);
        }

        return p;
    }

    @Override
    public void agregarClase(int idMateria, Clase clase) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Materia> consultarMaterias() {
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Asignatura> consultarAsignaturas(int idMateria) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registrarAsignatura(Asignatura asignatura, int idPrograma) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Materia> consultarPrerrequisitos(int idMateria) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Materia> consultarCorrequisitos(int idMateria) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Integer> consultarPeriodos() {
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
        List<Integer> per = this.consultarPeriodos();

        boolean found = false;
        Set<Integer> periodos = new TreeSet<>();

        for (Integer i : per) {
            List<Programa> programas = this.consultarProgramas(i);
            for (int j = 0; j < programas.size(); j++) {
                Programa pro = programas.get(j);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> consultarCategoriasRecursos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Asignatura, Integer> consultarCohortesPorAsignatura(int idMateria, int periodo) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Profesor consultarProfesor(int periodo, int idMateria) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> consultarCategorias() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registrarRecurso(Recurso recurso) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registrarPrestamoRecursoClase(int idRecurso, Clase clase) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Recurso> consultarRecursosProgramados(int periodo) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
