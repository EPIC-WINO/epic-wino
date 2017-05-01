package Logica.ManagedBeans;

import Logica.Entidades.Asignatura;
import Logica.Entidades.Clase;
import Logica.Entidades.Materia;
import Logica.Entidades.Profesor;
import Logica.Entidades.Programa;
import Logica.Servicios.ExcepcionServiciosProgmsPost;
import Logica.Servicios.ServiciosProgmsPost;
import Logica.Servicios.ServiciosProgmsPostFactory;
import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 * @author Camila Gomez <maria.gomez-ra@mail.escuelaing.edu.co>
 */
@ManagedBean(name = "reporteBean")
@SessionScoped
public class ReporteProgramacionBean implements Serializable { // FIXME logica cambio
    
    private static final Logger LOGGER = Logger.getLogger(ReporteProgramacionBean.class.getName());
    private static final long serialVersionUID = 1L;
    
    private final ServiciosProgmsPost servProg;
    
    private String programa;
    private int anio;
    private int semestre;
    
    public ReporteProgramacionBean() {
        LOGGER.log(Level.FINEST, "Se instancia {0}", this.getClass().getName());
        
        servProg = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostDummy();
    }
    
    @PostConstruct
    public void init() {
        
    }
    
    public List<Materia> getMaterias() { // TODO loggers
        List<Materia> m = new ArrayList<>();
        
        for(Programa p : this.getProgramas()) {
            if (p.getNombre() == this.programa) {
                for(Asignatura a: p.getAsignaturas()) {
                    m.addAll(a.getMaterias());
                }
            }
        }
        
        return m;
    }
    
    public Asignatura getAsignatura(Materia m) { // TODO loggers
        return m.getAsignatura();
    }
    
    public List<Programa> getProgramas() {
        LOGGER.log(Level.FINEST, "Se intenta obtener los programas (anio: {0}, "
                + "semestre: {1})", new int[]{anio, semestre});
        List<Programa> r = null;
        try {
            r = servProg.consultarProgramas(anio*10 + semestre);
        } catch (ExcepcionServiciosProgmsPost ex) {
            LOGGER.log(Level.SEVERE, "Error consultando programas", ex);
        }
        
        return r;
    }
    
    public List<Asignatura> getAsignaturas(Programa programa) {
        LOGGER.log(Level.FINEST, "Se intenta obtener las asignaturas del programa"
                + "({0})", programa);
        
        List<Asignatura> r = null;
        if (programa != null) {
            r = programa.getAsignaturas();
        } else {
            LOGGER.log(Level.WARNING, "Error consultando asignaturas: el programa es null.");
        }
        
        return r;
    }
    
    public List<Materia> getMaterias(Asignatura asignatura) {
        LOGGER.log(Level.FINEST, "Se intenta obtener las materias de la asignatura"
                + "({0})", asignatura);
        
        List<Materia> r = null;
        if (asignatura != null) {
            r = asignatura.getMaterias();
        } else {
            LOGGER.log(Level.WARNING, "Error consultando materias: la asignatura es null.");
        }
        
        return r;
    }
    
    public int getCohorte(Materia materia) {
        LOGGER.log(Level.FINEST, "Se intenta obtener el cohorte de la materia"
                + "({0})", materia);
        
        int r = 0;
        if (materia != null) {
            r = materia.getCohorte();
        } else {
            LOGGER.log(Level.WARNING, "Error consultando cohorte: la materia es null.");
        }
        
        return r;
    }
    
    public Profesor getProfesor(Materia materia) {
        LOGGER.log(Level.FINEST, "Se intenta obtener el profesor de la materia"
                + "({0})", materia);
        
        Profesor r = null;
        if (materia != null) {
            r = materia.getProfesor();
        } else {
            LOGGER.log(Level.WARNING, "Error consultando profesor: la materia es null.");
        }
        
        return r;
    }
    
    public List<Clase> getClases(Materia materia) {
        LOGGER.log(Level.FINEST, "Se intenta obtener las clase de la materia"
                + "({0})", materia);
        
        List<Clase> r = null;
        if (materia != null) {
            r = materia.getClases();
        } else {
            LOGGER.log(Level.WARNING, "Error consultando clases: la materia es null.");
        }
        
        return r;
    }
    
    public Time getHoraInicio(Clase clase) {
        LOGGER.log(Level.FINEST, "Se intenta obtener la hora de inicio de la clase"
                + "({0})", clase);
        
        Time r = null;
        if (clase != null) {
            r = clase.getHora_inicio();
        } else {
            LOGGER.log(Level.WARNING, "Error consultando la hora de inicio de "
                    + "la clase: la clase es null.");
        }
        
        return r;
    }
    
    public Time getHoraFin(Clase clase) {
        LOGGER.log(Level.FINEST, "Se intenta obtener la hora de inicio de la clase"
                + "({0})", clase);
        
        Time r = null;
        if (clase != null) {
            r = clase.getHora_fin();
        } else {
            LOGGER.log(Level.WARNING, "Error consultando la hora de inicio de "
                    + "la clase: la clase es null.");
        }
        
        return r;
    }
    
    public void setPrograma(String prog) {
        this.programa = prog;
    }
    
    public String getPrograma() {
        return this.programa;
    }

    /**
     * @return the anio
     */
    public int getAnio() {
        LOGGER.log(Level.FINEST, "Se obtiene el anio ({0})", anio);
        return anio;
    }

    /**
     * @param anio the anio to set
     */
    public void setAnio(int anio) {
        LOGGER.log(Level.FINEST, "Se establece el anio (Antes: {0} | Despues: {1})", new int[]{this.anio, anio});
        this.anio = anio;
    }

    /**
     * @return the semestre
     */
    public int getSemestre() {
        LOGGER.log(Level.FINEST, "Se obtiene el semestre ({0})", semestre);
        return semestre;
    }

    /**
     * @param semestre the semestre to set
     */
    public void setSemestre(int semestre) {
        LOGGER.log(Level.FINEST, "Se establece el semestre (Antes: {0} | "
                + "Despues {1})", new int[]{this.semestre, semestre});
        this.semestre = semestre;
    }
    
    public String cambiarVista() {
        return "ReporteProgramacion.xhtml";
    }
    
    
    
}
