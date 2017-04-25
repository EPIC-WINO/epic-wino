package Logica.Entidades;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 */
public class Asignatura {
    private int id;
    private String nombre;
    private Programa programa;
    private List<Materia> materias;

    public Asignatura(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        materias = new ArrayList<>();
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the programa
     */
    public Programa getPrograma() {
        return programa;
    }

    /**
     * @param programa the programa to set
     */
    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    /**
     * @return the materias
     */
    public List<Materia> getMaterias() {
        return materias;
    }

    /**
     * @param materias the materias to set
     */
    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
        
        for(Materia m : materias) {
            m.setAsignatura(this);
        }
    }

    @Override
    public String toString() {
        return "Asignatura{" + "id=" + id + ", nombre=" + nombre + ", programa=" + programa + '}';
    }
}
