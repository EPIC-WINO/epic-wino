/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.epicwino.logica.managedbeans;

import edu.eci.pdsw.epicwino.logica.entidades.Asignatura;
import edu.eci.pdsw.epicwino.logica.entidades.Materia;
import edu.eci.pdsw.epicwino.logica.entidades.Programa;
import edu.eci.pdsw.epicwino.logica.servicios.ExcepcionServiciosProgmsPost;
import edu.eci.pdsw.epicwino.logica.servicios.ServiciosProgmsPost;
import edu.eci.pdsw.epicwino.logica.servicios.ServiciosProgmsPostFactory;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
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

@ManagedBean(name = "registrarMateriaBean")
@SessionScoped
public class RegistrarMateriaBean implements Serializable{
    
    private static final Logger LOGGER = Logger.getLogger(MateriasRegistradasBean.class);
    private static final long serialVersionUID = 1L;
    
    private final ServiciosProgmsPost servProg = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostDummy();;
    
    private String programa=null;
    private String asignatura=null;
    private int anio=0;
    private int semestre=0;
    private int programa_id;
    private String nivel;

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public String getPrograma() {
        return programa;
    }

    public String getAsignatura() {
        return asignatura;
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
    
    public void getAsignaturas(){
        
    }
    
    public void getAnios(){
        LOGGER.debug("Se obtienen los anios");
        List<Integer> p=new ArrayList<>();
        List<Integer> periods = servProg.consultarPeriodos();
        for (Integer i : periods) {
            i /= 10;
            p.add(i);
        }
        int mayor=0;
        for(Integer m:p){
            if(m>mayor){
                mayor=m;
            }
        }
        LOGGER.debug(MessageFormat.format("Se obtiene el año mayor)",mayor));
        anio=mayor;
    }
     
    public void getSemestres(){
        LOGGER.debug("Se intenta obtener la fecha");
        Date fecha=new Date();
        int mes=fecha.getMonth();
        LOGGER.debug(MessageFormat.format("Se obtiene el mes",mes));
        if (mes>6){
            semestre=2;
        }else{
            semestre=1;
        }
        
    }
    
    
    
}
