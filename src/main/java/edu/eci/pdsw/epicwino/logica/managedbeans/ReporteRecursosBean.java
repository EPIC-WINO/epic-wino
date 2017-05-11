package edu.eci.pdsw.epicwino.logica.managedbeans;

import edu.eci.pdsw.epicwino.logica.entidades.Clase;
import edu.eci.pdsw.epicwino.logica.entidades.Recurso;
import edu.eci.pdsw.epicwino.logica.servicios.ExcepcionServiciosProgmsPost;
import edu.eci.pdsw.epicwino.logica.servicios.ServiciosProgmsPost;
import edu.eci.pdsw.epicwino.logica.servicios.ServiciosProgmsPostFactory;
import java.io.Serializable;
import java.sql.Date;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;

/**
 *
 * @author Juan Andrade
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 */
@ManagedBean(name = "reporteRecursoBean")
@SessionScoped
public class ReporteRecursosBean implements Serializable { // FIXME logica cambio
    
    private static final Logger LOGGER = Logger.getLogger(ReporteProgramacionBean.class);
    private static final long serialVersionUID = 1L;
    
    private final ServiciosProgmsPost servProg = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostDummy();
    
    private int anio = 0;
    private int semestre = 0;
    private List<Recurso> recursos = new ArrayList<>();
    
    public ReporteRecursosBean() {
        LOGGER.debug("Se instancia " + this.getClass().getName());
    }
   
    public List<Recurso> getRecursos(){
        LOGGER.debug("Se obtiene la lista de recursos");
        return recursos;
    }
    
    public void setRecursos(List<Recurso> recursos){
        LOGGER.debug("Se establecen los recursos");
        this.recursos=recursos;
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
        LOGGER.debug(MessageFormat.format("Se establece el anio (Antes {0} | Despues {1})", this.anio, anio));
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
        LOGGER.debug(MessageFormat.format("Se establece el semestre (Antes: {0} "
                + "| Despues: {1})", this.semestre, semestre));
        this.semestre = semestre;
    }
    
    public void actualizarReporte() {
        LOGGER.info("Se actualiza el reporte de la vista");
        try {
            if (anio != 0 && semestre != 0) {
                recursos = servProg.consultarRecursosProgramados((anio * 10) + semestre);
            }
        } catch (ExcepcionServiciosProgmsPost ex) {
            LOGGER.error("Error consultando recursos programados.",ex);
        }
    }
    
    public Date fechaUsoRecurso(int idRecurso) {
        List<Clase> clases = new ArrayList<>();
        Date fecha = null;
        LOGGER.debug("Busca fecha del uso del recurso.");
        try {
            clases = servProg.consultarClasesDeUnPeriodo((anio * 10) + semestre);
            LOGGER.debug("Comienza iteración sobre las clases del respectivo periodo.");
            for (Clase cla : clases) {
                LOGGER.debug("Comienza busqueda en la clase "+cla.getId());
                List<Recurso> recur = new ArrayList<>();
                recur = cla.getRecursos();
                LOGGER.debug("RECURSOS: "+recur.toString());
                for (Recurso rec : recur) {
                    LOGGER.debug("Busca recurso "+idRecurso+" y encuentra el recurso "+rec.getId());
                    if (rec.getId() == idRecurso) {
                        LOGGER.debug("Encuentra el recurso "+rec.getId());
                        fecha = cla.getFecha();
                    }
                }
            }
        } catch (ExcepcionServiciosProgmsPost ex) {
            LOGGER.error("Error al consultar las clases de un periodo especifico", ex);
        }
        return fecha;
    }
    
    public String horaUsoRecurso(int idRecurso) {
        List<Clase> clases = new ArrayList<>();
        String hora = null;
        LOGGER.debug("Busca hora del uso del recurso.");
        try {
            clases = servProg.consultarClasesDeUnPeriodo((anio * 10) + semestre);
            LOGGER.debug("Comienza iteración sobre las clases del respectivo periodo.");
            for (Clase cla : clases) {
                LOGGER.debug("Comienza busqueda en la clase "+cla.getId());
                List<Recurso> recur = new ArrayList<>();
                recur = cla.getRecursos();
                LOGGER.debug("RECURSOS: "+recur.toString());
                for (Recurso rec : recur) {
                    LOGGER.debug("Busca recurso "+idRecurso+" y encuentra el recurso "+rec.getId());
                    if (rec.getId() == idRecurso) {
                        LOGGER.debug("Encuentra el recurso "+rec.getId());
                        hora = cla.getHoraInicio().toString()+" - "+cla.getHoraFin().toString();
                    }
                }
            }
        } catch (ExcepcionServiciosProgmsPost ex) {
            LOGGER.error("Error al consultar las clases de un periodo especifico", ex);
        }
        return hora;
    }
}
