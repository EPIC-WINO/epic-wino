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
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;

/**
 *
 * @author Alejandro Anzola email: alejandro.anzola@mail.escuelaing.edu.co
 * @author Camila Gomez email: maria.gomez-ra@mail.escuelaing.edu.co
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
    private Materia materia = new Materia();
    
    public ReporteProgramacionBean() {
        LOGGER.debug(MessageFormat.format("Se instancia {0}", this.getClass().getName()));
        
        servProg = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostDummy();
    }
    
    @PostConstruct
    public void init() {
        
    }
    
    public Materia getMateria() {
        LOGGER.debug("Retorna la materia: "+materia);
        return this.materia;
    }
    
    public void setMateria(Materia materia) {
        this.materia = materia;
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
            LOGGER.error("Error obteniendo la asignatura.", ex);
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
    
    public int convertirSemestre(String sem){
        int s; 
        if("1".equals(sem)){
            s=1;
        } else if("2".equals(sem)){
            s=2;
        } else {
            s=3;
        }
        return s;
    }

    public Map<String, String> getSemestres() {
        LOGGER.debug("Se obtienen los semestres");
        Map<String, String> m = new HashMap<>();
        m.put("1", "1");
        m.put("2", "2");
        m.put("I", "I");
        return m;
    }
    
    public Map<String,String> getProgramas() {
        LOGGER.debug(MessageFormat.format("Se intenta obtener los programas (anio: {0}, "
                + "semestre: {1})", anio, semestre));
        List<Programa> r = null; 
        Map<String,String> programs  = new HashMap<>();
        r = servProg.consultarProgramas();
        LOGGER.debug("Se consultan "+r.size()+" programas -> "+r);
        for (Programa p:r){
            String n=p.getNombre();
            programs.put(n,n);
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
            LOGGER.debug("Se consultan asignaturas en el año: "+anio+" y el semestre: "+semestre+". Del programa con ID: "+programObject.getId());
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
            LOGGER.error("Error obteniendo el cohorte.", ex);
        }
        return cohorte;
    }
    
    public Profesor getProfesor(Materia materia) { //TODO Logger
        Profesor profesor = new Profesor();
        try {
            profesor = servProg.consultarProfesor((anio*10)+semestre, materia.getId());
        } catch (ExcepcionServiciosProgmsPost ex) {
            LOGGER.error("Error obteneindo el profesor.", ex);
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
    public String getSemestre() {
        LOGGER.debug(MessageFormat.format("Se obtiene el semestre ({0})", semestre));
        String s = "";
        switch(semestre) {
            case 1:
                s = "1";
                break;
            case 2:
                s = "2";
                break;
            case 3:
                s = "I";
        }
        return s;
    }

    /**
     * @param semestre the semestre to set
     */
    public void setSemestre(String semestre) {
        LOGGER.debug(MessageFormat.format("Se establece el semestre (Antes: {0} | "
                + "Despues {1})", this.semestre, semestre));
        this.semestre = convertirSemestre(semestre);
    }  
    
    public void actualizarReporte(){
        materias.clear();
        try {
            List<Programa> programas = servProg.consultarProgramas();
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
            LOGGER.error("Error obteniendo las materias.", ex);
        }
    } 
    
    public int getSesiones(Materia materia) {
        List<Clase> clases = new ArrayList<>();
        try {
             clases = servProg.consultarClases((anio*10)+semestre, materia.getId());
        } catch (ExcepcionServiciosProgmsPost ex) {
            LOGGER.error("Error consultando las sesiones de una materia. ",ex);
        }
        return clases.size(); //Tener en cuenta que utiliza este metodo para calcular las horas
    }
    
    public int getHoras(Materia materia) {
        return this.getSesiones(materia)*3; 
    }
    
    public String getPeriodo() {
        return anio + "-" + semestre;
    }
    
    public List<Clase> getClases(Materia materia) {
        List<Clase> clases = new ArrayList<>();
        try {
            LOGGER.debug("Se consultan las clases en el periodo: "+anio+"-"+semestre+" para la materia con ID: "+materia.getId());
            clases = servProg.consultarClases((anio*10)+semestre, materia.getId());
        } catch (ExcepcionServiciosProgmsPost ex) {
            LOGGER.error("Error consultando las clases ", ex);
        }
        return clases;
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
    
    /*public Time getHoraInicio(Clase clase) {
        return null; // TODO implementar
    }
    
    public Time getHoraFin(Clase clase) {
        return null; // TODO implementar
    }*/
    
}
