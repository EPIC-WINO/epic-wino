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
import java.util.TreeMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;

/**
 *
 * @author MariaCamila
 */
@ManagedBean(name = "registrarMateriaBean")
@SessionScoped
public class RegistrarMateriaBean implements Serializable {
    
    private static final Logger LOGGER = Logger.getLogger(MateriasRegistradasBean.class);
    private static final long serialVersionUID = 1L;
    
    private final ServiciosProgmsPost servProg = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPost();
    
    private String programa = "";
    private String asignatura;
    private int programa_id;
    private String nivel;
    private int asignatura_id = 0;
    private String nombremateria;
    private String codigo;
    private String descripcion;
    private int creditos;

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getNombremateria() {
        return nombremateria;
    }
    
    public void setNombremateria(String nombremateria) {
        this.nombremateria = nombremateria;
    }
    
    public String getNivel() {
        return nivel;
    }
    
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
    
    public void setPrograma(String programa) {
        LOGGER.debug("Se establece el nombre del programa '" + programa + "'");
        this.programa = programa;
        Programa_id();
    }
    
    public String getPrograma() {
        return programa;
    }
    
    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
        Asignatura_id();
    }
    
    public String getAsignatura() {
        return asignatura;
    }
    
    public List<Integer> getPeriodos() {
        return servProg.consultarPeriodos();
    }
    
    public void Asignatura_id() {
        LOGGER.debug("Se intentan obtener id de la asignatura");
        Programa_id();
        List<Asignatura> asignaturas;
        try {
            asignaturas = servProg.consultarAsignaturasPorPrograma(programa_id);
            for (Asignatura a : asignaturas) {
                if (a.getNombre().equals(asignatura)) {
                    asignatura_id = a.getId();
                }
            }
            LOGGER.debug(MessageFormat.format("Se obtiene el id de asignatura)", asignatura_id));
        } catch (ExcepcionServiciosProgmsPost ex) {
            LOGGER.error("Error consultando id de asignatura", ex);
        }
    }
    
    public Map<String, String> getProgramas() {
        List<Programa> r;
        Map<String, String> programs = new HashMap<>();
        r = servProg.consultarProgramas();
        for (Programa p : r) {
            String n = p.getNombre();
            programs.put(n, n);
        }
        
        return programs;
    }
    
    public Map<String, String> getNiveles() {
        List<String> r;
        Map<String, String> niveles = new HashMap<>();
        r = servProg.consultarNiveles();
        for (String p : r) {
            niveles.put(p, p);
        }
        
        return niveles;
    }
    
    public void Programa_id() {
        List<Programa> r;
        r = servProg.consultarProgramas();
        for (Programa p : r) {
            if (p.getNombre().equals(programa) && p.getNivel().equals(nivel)) {
                programa_id = p.getId();
            }
        }
    }
    
    public Map<String, String> getAsignaturas() {
        LOGGER.debug("Se intentan obtener las asignaturas");
        Map<String, String> asig = new HashMap<>();
        List<Asignatura> a;
        Programa_id();
        try {
            LOGGER.debug("Se intentan obtener las asignaturas del programa "+programa+" con ID: "+programa_id);
            a = servProg.consultarAsignaturasPorPrograma(programa_id);
            for (Asignatura as : a) {
                String n = as.getNombre();
                asig.put(n, n);
            }
        } catch (ExcepcionServiciosProgmsPost ex) {
            LOGGER.error("Error consultando asignaturas", ex);
        }
        return asig;
    }
    
    public void registrarMateria() {
        Materia materia = new Materia();
        materia.setNombre(nombremateria);
        materia.setDescripcion(descripcion);
        materia.setCreditos(creditos);
    }
}
