package Logica.ManagedBeans;

import Logica.Entidades.Recurso;
import Logica.Servicios.ServiciosProgmsPost;
import Logica.Servicios.ServiciosProgmsPostFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Juan Andrade
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 */
@ManagedBean(name = "reporteRecursoBean")
@SessionScoped
public class ReporteRecursosBean implements Serializable { // FIXME logica cambio
    
    private static final Logger LOGGER = Logger.getLogger(ReporteProgramacionBean.class.getName());
    private static final long serialVersionUID = 1L;
    
    private final ServiciosProgmsPost servProg = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostDummy();
    
    private int anio;
    private int semestre;
    private List<Recurso> recursos;
    
    
    public ReporteRecursosBean() {
        LOGGER.log(Level.FINEST, "Se instancia {0}", this.getClass().getName());
    }
   
    public List<Recurso> getRecursos(){
        return servProg.consultarRecursos();
    }
    
    public Map<Integer,Integer> getAnios(){
        Map<Integer,Integer> periodos  = new HashMap<>();
        List<Integer> periods = servProg.consultarPeriodos();
        for (Integer i : periods) {
            i /= 10;
            periodos.put(i, i);
        }
        return periodos;
    }
    
    public Map<Integer, Integer> getSemestres() {
        Map<Integer, Integer> m = new HashMap<>();
        m.put(1, 1);
        m.put(2, 2);
        return m;
    }
  
    
    public void setRecursos(List<Recurso> recursos){
        this.recursos=recursos;
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
    
    
    
    public void actualizarReporte() {
        // TODO implementar
    }
    
}
