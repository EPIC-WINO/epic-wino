/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.ManagedBeans;


/**
 *
 * @author juan
 */

import Logica.Entidades.Asignatura;
import Logica.Entidades.Clase;
import Logica.Entidades.Materia;
import Logica.Entidades.Profesor;
import Logica.Entidades.Programa;
import Logica.Entidades.RecursoConcedido;
import Logica.Servicios.ExcepcionServiciosProgmsPost;
import Logica.Servicios.ServiciosProgmsPost;
import Logica.Servicios.ServiciosProgmsPostFactory;
import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 */
@ManagedBean(name = "reporterecursosbean")
@SessionScoped
public class ReporteRecursosBean implements Serializable {
    
    private static final Logger LOGGER = Logger.getLogger(ReporteProgramacionBean.class.getName());
    
    private final ServiciosProgmsPost servProg;
    
    private int anio;
    private int semestre;
    private List<RecursoConcedido> recursos;
    
    
    public ReporteRecursosBean() {
        LOGGER.log(Level.FINEST, "Se instancia {0}", this.getClass().getName());
        servProg = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostDummy();
    }
    
   
    public List<RecursoConcedido> getRecursos(int anio,int semestre){
        ArrayList<RecursoConcedido> x=new ArrayList<RecursoConcedido>();
        try {
            x=(ArrayList<RecursoConcedido>)servProg.consultarRecursosConcedidos( anio, semestre);
        } catch (ExcepcionServiciosProgmsPost ex) {
            LOGGER.log(Level.SEVERE, "Error consultando programas", ex);
        }
        return x;
        
    }
    public void setRecursos(List<RecursoConcedido> recursos){
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
    
    
    
}
