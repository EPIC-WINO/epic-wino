package edu.eci.pdsw.epicwino.logica.managedbeans;

import edu.eci.pdsw.epicwino.logica.entidades.Asignatura;
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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;
/**
 *
 * @author MariaCamila
 */

@ManagedBean(name = "materiasRegistradasBean")
@SessionScoped
public class MateriasRegistradasBean implements Serializable{
    
    private static final Logger LOGGER = Logger.getLogger(MateriasRegistradasBean.class);
    private static final long serialVersionUID = 1L;
    
    private final ServiciosProgmsPost servProg = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostDummy();;
    
    private String programa="";
    private int anio=0;
    private int semestre=0;
    private List<Asignatura> asignaturas=new ArrayList<Asignatura>();
    private int programa_id=0;
    private String nivel="";
    
    
    public MateriasRegistradasBean() {
        LOGGER.debug(MessageFormat.format("Se instancia {0}", this.getClass().getName())); 
    }
    
    /*public List<Materia> getMaterias(){
        
    }*/
    
    public List<Asignatura> getAsignaturas(){
        LOGGER.debug(MessageFormat.format("Se obtiene la lista de asignaturas -> {0}", asignaturas.size()));
        return asignaturas;
    }
    
    public void setAsignaturas(List<Asignatura> asignaturas){
        LOGGER.debug("Se establecen las asignaturas");
        this.asignaturas=asignaturas;
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
    
    public void Programa_id(){
        LOGGER.debug(MessageFormat.format("Se intenta obtener los programas y su id (anio: {0}, "
                + "semestre: {1})", anio, semestre));
        List<Programa> r = null; 
        try {
            r = servProg.consultarProgramas(anio*10 + semestre);
            for (Programa p:r){
                if (p.getNombre().equals(programa)&&p.getNivel().equals(nivel)){
                    programa_id=p.getId();
                }
            }
        } catch (ExcepcionServiciosProgmsPost ex) {
            LOGGER.error("Error consultando id de programas", ex);
        }
    }
    
    public void setPrograma(String prog) {
        LOGGER.debug(MessageFormat.format("Se establece el programa (Antes: {0} | Despues: {1})", this.programa, prog));
        this.programa = prog;
    }
    
    public String getPrograma() {
        LOGGER.debug(MessageFormat.format("Se obtiene el programa: {0}", this.programa));
        return this.programa;
    }
    
    public void setNivel(String nivel) {
        LOGGER.debug(MessageFormat.format("Se establece el nive del programa (Antes: {0} | Despues: {1})", this.nivel, nivel));
        this.nivel = nivel;
    }
    
    public String getNivel() {
        LOGGER.debug(MessageFormat.format("Se obtiene el nivel del programa: {0}", this.nivel));
        return this.nivel;
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
    
    public void actualizarMaterias(){
        LOGGER.info("Se actualiza el vista de las materias registradas");
        try {
            asignaturas = servProg.consultarAsignaturas((anio * 10) + semestre,programa_id);
            //LOGGER.debug(MessageFormat.format(""));
        } catch (ExcepcionServiciosProgmsPost ex) {
            LOGGER.error("Error al consultar las asignaturas", ex);
        }
    }
    
}


