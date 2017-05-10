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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;

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
    
    private String programaName = "";
    private Programa programObject = new Programa();
    private String nivel = "";
    private int anio = 0;
    private int semestre = 0;
    private List<Materia> materias = new ArrayList<>();
    private List<Clase> clases = new ArrayList<>();
    
    public ReporteProgramacionBean() {
        LOGGER.debug(MessageFormat.format("Se instancia {0}", this.getClass().getName()));
        
        servProg = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostDummy();
    }
    
    @PostConstruct
    public void init() {
        
    }
    
    public Asignatura getAsignatura(Materia materia) { // TODO loggers
        Asignatura asignatura = new Asignatura();
        try {
            List<Asignatura> asignaturas;
            asignaturas = servProg.consultarAsignaturas((anio*10)+semestre, programObject.getId());
            for (Asignatura s : asignaturas) {
                List<Materia> materias = s.getMaterias();
                for (Materia m : materias) {
                    if (m.getId() == materia.getId()) {
                        asignatura = s;
                    }
                }
            }
        } catch (ExcepcionServiciosProgmsPost ex) {
            LOGGER.error("Error obteniendo la asignatura.");
        }
        return asignatura;
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
    
    public Map<String,String> getNiveles(){
        LOGGER.debug(MessageFormat.format("Se intenta obtener los niveles (anio: {0}, "
                + "semestre: {1})", anio, semestre));
        List<String> r = null; 
        Map<String,String> niveles = new HashMap<>();
        r = servProg.consultarNiveles();
        for (String p:r){
            niveles.put(p,p);
        }
        
        return niveles;
    }
    
    public void setNivel(String nivel) {
        LOGGER.debug(MessageFormat.format("Se establece el nive del programa (Antes: {0} | Despues: {1})", this.nivel, nivel));
        this.nivel = nivel;
    }
    
    public String getNivel() {
        LOGGER.debug(MessageFormat.format("Se obtiene el nivel del programa: {0}", this.nivel));
        return this.nivel;
    }
    
    public List<Materia> getMaterias() {
        return materias;
    }
    
    public int getCohorte(Materia materia) {
        Asignatura asignatura = new Asignatura();
        int cohorte = 0;
        try {
            List<Asignatura> asignaturas = servProg.consultarAsignaturas((anio*10)+semestre, programObject.getId());
            for (Asignatura s : asignaturas) {
                List<Materia> materias = s.getMaterias();
                for (Materia m : materias) {
                    if (m.getId() == materia.getId()) {
                        asignatura = s;
                    }
                }
            }
            cohorte = servProg.consultarCohorte(materia.getId(), (anio*10)+semestre, asignatura.getId());
        } catch (ExcepcionServiciosProgmsPost ex) {
            LOGGER.error("Error obteniendo el cohorte.");
        }
        return cohorte;
    }
    
    public Profesor getProfesor(Materia materia) { //TODO Logger
        Profesor profesor = new Profesor();
        try {
            profesor = servProg.consultarProfesor((anio*10)+semestre, materia.getId());
        } catch (ExcepcionServiciosProgmsPost ex) {
            LOGGER.error("Error obteneindo el profesor.");
        }
        return profesor;
    }
    
    public void setPrograma(String prog) {
        this.programaName = prog;
    }
    
    public String getPrograma() {
        return this.programaName;
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
        materias.clear();
        try {
            List<Programa> programas = servProg.consultarProgramas((anio*10)+semestre);
            boolean flag = false;
            Programa program = null;
            for (int i = 0; i < programas.size() && !flag; i++) {
                program = programas.get(i);
                if (program.getNombre() == programaName && program.getNivel() == nivel) {
                    flag = true;
                    programObject = program;
                }
            }
            List<Asignatura> asignaturas = servProg.consultarAsignaturas((anio*10)+semestre, (program.getId()));
            for (int i = 0; i < asignaturas.size(); i++) {
                materias.addAll(asignaturas.get(i).getMaterias());
            }
        } catch (ExcepcionServiciosProgmsPost ex) {
            LOGGER.error("Error obteniendo las materias.");
        }
    } 
    
    /*public List<Asignatura> getAsignaturas(Programa programa) {
        LOGGER.debug(MessageFormat.format("Se intenta obtener las asignaturas del programa"
                + "({0})", programa));
        
        List<Asignatura> r = null;
        if (programa != null) {
            r = programa.getAsignaturas();
        } else {
            LOGGER.error("Error consultando asignaturas: el programa es null.");
        }
        
        return r;
    }*/
    
    /*public List<Clase> getClases(Materia materia) {
        return null; // TODO implementar
    }
    
    public Time getHoraInicio(Clase clase) {
        return null; // TODO implementar
    }
    
    public Time getHoraFin(Clase clase) {
        return null; // TODO implementar
    }*/
    
}
