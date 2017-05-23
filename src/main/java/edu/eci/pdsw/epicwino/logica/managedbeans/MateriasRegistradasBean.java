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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;

/**
 *
 * @author MariaCamila
 */
@ManagedBean(name = "materiasRegistradasBean")
@SessionScoped
public class MateriasRegistradasBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(MateriasRegistradasBean.class);
    private static final long serialVersionUID = 1L;

    private final ServiciosProgmsPost servProg = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPost();
    
    private String programa = "";
    private int anio = 0;
    private int semestre = 0;
    private List<Asignatura> asignaturas = new ArrayList<>();
    private int programa_id = 0;
    private String nivel = "";
    private List<Materia> materias;
    private int asignatura_id;

    public MateriasRegistradasBean() {
        LOGGER.debug(MessageFormat.format("Se instancia {0}", this.getClass().getName()));
    }

    public List<Materia> getMaterias(Asignatura a) {
        LOGGER.debug(MessageFormat.format("Se obtiene la lista de materias -> {0}", materias.size()));
        return a.getMaterias();
    }

    public void setMaterias(List<Materia> materias) {
        LOGGER.debug("Se establecen las materias");
        this.materias = materias;
    }

    public List<Asignatura> getAsignaturas() {
        LOGGER.debug("Se obtiene la lista de asignaturas");
        return asignaturas;
    }

    public void setAsignaturas(List<Asignatura> asignaturas) {
        LOGGER.debug("Se establecen las asignaturas");
        this.asignaturas = asignaturas;
    }

    public Map<Integer, Integer> getAnios() {
        LOGGER.debug("Se obtienen los anios");
        Map<Integer, Integer> periodos = new HashMap<>();
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

    public Map<String, String> getNiveles() {
        LOGGER.debug(MessageFormat.format("Se intenta obtener los niveles (anio: {0}, "
                + "semestre: {1})", anio, semestre));
        List<String> r = null;
        Map<String, String> niveles = new HashMap<>();
        r = servProg.consultarNiveles();
        for (String p : r) {
            niveles.put(p, p);
        }

        return niveles;
    }

    public Map<String, String> getProgramas() {
        LOGGER.debug(MessageFormat.format("Se intenta obtener los programas (anio: {0}, "
                + "semestre: {1})", anio, semestre));
        List<Programa> r = null;
        Map<String, String> programs = new HashMap<>();

        r = servProg.consultarProgramas();
        for (Programa p : r) {
            String n = p.getNombre();
            programs.put(n, n);
        }

        return programs;
    }

    public void obtenerIdDePrograma() {
        LOGGER.debug(MessageFormat.format("Se intenta obtener los programas y su id (anio: {0}, "
                + "semestre: {1})", anio, semestre));
        List<Programa> r;
        try {
            r = servProg.consultarProgramas((anio * 10) + semestre);
            boolean found = false;
            for (Programa p : r) {
                if (p.getNombre().equals(programa) && p.getNivel().equals(nivel)) {
                    LOGGER.debug("Se encuentra el programa ID: " + p.getId());
                    programa_id = p.getId();
                    found = true;
                }
            }
            if (!found) {
                LOGGER.error("No se encontro el programa correspondiente Nombre: " + programa);
            }
        } catch (ExcepcionServiciosProgmsPost ex) {
            LOGGER.error("Error al consultar id del programa", ex);
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

    public void actualizarMaterias() {
        LOGGER.info("Se actualiza el vista de las materias registradas");
        
        obtenerIdDePrograma();
        if (programa != null && anio != 0 && semestre != 0 && nivel != null) {
            try {
                asignaturas = servProg.consultarAsignaturas((anio * 10) + semestre, programa_id);
                for (Asignatura a : asignaturas) {
                    asignatura_id = a.getId();
                    materias = servProg.consultarMaterias((anio * 10) + semestre, asignatura_id);
                }

            } catch (ExcepcionServiciosProgmsPost ex) {
                LOGGER.error("Error al consultar las asignaturas", ex);
            }
        }
    }

}
