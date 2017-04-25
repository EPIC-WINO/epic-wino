package Logica.Entidades;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 */
public class Programa {
    private int id;
    private String nombre;
    private String nivel;
    private int duracion;
    private String jornada;
    private String modalidad;
    private int codigoSNIES;
    private List<Asignatura> asignaturas;

    
    public Programa(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        asignaturas = new ArrayList<>();
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
     * @return the nivel
     */
    public String getNivel() {
        return nivel;
    }

    /**
     * @param nivel the nivel to set
     */
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    /**
     * @return the duracion
     */
    public int getDuracion() {
        return duracion;
    }

    /**
     * @param duracion the duracion to set
     */
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    /**
     * @return the jornada
     */
    public String getJornada() {
        return jornada;
    }

    /**
     * @param jornada the jornada to set
     */
    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    /**
     * @return the modalidad
     */
    public String getModalidad() {
        return modalidad;
    }

    /**
     * @param modalidad the modalidad to set
     */
    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    /**
     * @return the codigoSNIES
     */
    public int getCodigoSNIES() {
        return codigoSNIES;
    }

    /**
     * @param codigoSNIES the codigoSNIES to set
     */
    public void setCodigoSNIES(int codigoSNIES) {
        this.codigoSNIES = codigoSNIES;
    }

    /**
     * @return the asignaturas
     */
    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    /**
     * @param asignaturas the asignaturas to set
     */
    public void setAsignaturas(List<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
        
        for(Asignatura a : asignaturas) {
            a.setPrograma(this);
        }
    }

    @Override
    public String toString() {
        return "Programa{" + "id=" + id + ", nombre=" + nombre + ", nivel=" + nivel + ", duracion=" + duracion + ", jornada=" + jornada + ", modalidad=" + modalidad + ", codigoSNIES=" + codigoSNIES + '}';
    }
    
}
