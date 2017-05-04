package edu.eci.pdsw.epicwino.logica.managedbeans;

import edu.eci.pdsw.epicwino.logica.entidades.Asignatura;
import edu.eci.pdsw.epicwino.logica.entidades.Clase;
import edu.eci.pdsw.epicwino.logica.entidades.Materia;
import edu.eci.pdsw.epicwino.logica.entidades.Profesor;
import edu.eci.pdsw.epicwino.logica.entidades.Programa;
import edu.eci.pdsw.epicwino.logica.servicios.ExcepcionServiciosProgmsPost;
import edu.eci.pdsw.epicwino.logica.servicios.ServiciosProgmsPost;
import edu.eci.pdsw.epicwino.logica.servicios.ServiciosProgmsPostFactory;
import java.io.Serializable;
import java.sql.Time;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

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
        LOGGER.debug(MessageFormat.format("Se instancia {0}", this.getClass().getName()));
        
        servProg = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostDummy();
    }
    
    @PostConstruct
    public void init() {
        
    }
    
    public List<Materia> getMaterias() { // TODO loggers
        List<Materia> m = new ArrayList<>();
        
        /*for(Programa p : this.getProgramas()) {
            if (p.getNombre() == this.programa) {
                for(Asignatura a: p.getAsignaturas()) {
                    m.addAll(a.getMaterias());
                }
            }
        }*/
        
        return m;
    }
    
    public Asignatura getAsignatura(Materia m) { // TODO loggers
        return null; // TODO implementar
    }
    
    public Map<Integer,Integer> getAnios(){
        LOGGER.debug("Se obtienen los anios");
        Map<Integer,Integer> periodos  = new HashMap<>();
        List<Integer> periods = servProg.consultarPeriodos();
        for (Integer i : periods) {
            i /= 10;
            periodos.put(i, i);
        }
        return periodos;
    }
    
    public Map<Integer, Integer> getSemestres() {
        LOGGER.debug("Se obtienen los semestres");
        Map<Integer, Integer> m = new HashMap<>();
        m.put(1, 1);
        m.put(2, 2);
        return m;
    }
    
    public Map<String,String> getProgramas() {
        LOGGER.debug(MessageFormat.format("Se intenta obtener los programas (anio: {0}, "
                + "semestre: {1})", anio, semestre));
        List<Programa> r = null; 
        Map<String,String> programs  = new HashMap<>();
        try {
            r = servProg.consultarProgramas(anio*10 + semestre);
            for (Programa p:r){
                String n=p.getNombre();
                programs.put(n,n);
            }
        } catch (ExcepcionServiciosProgmsPost ex) {
            LOGGER.error("Error consultando programas", ex);
        }
        
        return programs;
    }
    
    public List<Asignatura> getAsignaturas(Programa programa) {
        LOGGER.debug(MessageFormat.format("Se intenta obtener las asignaturas del programa"
                + "({0})", programa));
        
        List<Asignatura> r = null;
        if (programa != null) {
            r = programa.getAsignaturas();
        } else {
            LOGGER.error("Error consultando asignaturas: el programa es null.");
        }
        
        return r;
    }
    
    public List<Materia> getMaterias(Asignatura asignatura) {
        LOGGER.debug(MessageFormat.format("Se intenta obtener las materias de la asignatura"
                + "({0})", asignatura));
        List<Materia> r = null;
        if (asignatura != null) {
            r = asignatura.getMaterias();
        } else {
             LOGGER.error("Error consultando materias: la asignatura es null.");
        }
        
        return r;
    }
    
    public int getCohorte(Materia materia) {
        return 0; // TODO implementar
    }
    
    public Profesor getProfesor(Materia materia) {
        return null; // TODO implementar
    }
    
    public List<Clase> getClases(Materia materia) {
        return null; // TODO implementar
    }
    
    public Time getHoraInicio(Clase clase) {
        return null; // TODO implementar
    }
    
    public Time getHoraFin(Clase clase) {
        return null; // TODO implementar
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
        LOGGER.debug(MessageFormat.format("Se obtiene el anio ({0})", anio));
        
        return anio;
    }

    /**
     * @param anio the anio to set
     */
    public void setAnio(int anio) {
        LOGGER.debug(MessageFormat.format("Se establece el anio (Antes: {0} | Despues: {1})", this.anio, anio));
        this.anio = anio;
    }

    /**
     * @return the semestre
     */
    public int getSemestre() {
        LOGGER.debug(MessageFormat.format("Se obtiene el semestre ({0})", semestre));
        return semestre;
    }

    /**
     * @param semestre the semestre to set
     */
    public void setSemestre(int semestre) {
        LOGGER.debug(MessageFormat.format("Se establece el semestre (Antes: {0} | "
                + "Despues {1})", this.semestre, semestre));
        this.semestre = semestre;
    }  
    
    public void actualizarReporte(){
        
    } 

}
