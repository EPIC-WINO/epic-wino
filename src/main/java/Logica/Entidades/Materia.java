package Logica.Entidades;

import java.util.List;

/**
 *
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 */
public class Materia {
    private int id;
    private String nombre;
    private Asignatura asignatura;
    private int creditos;
    private String descripcion;
    private Profesor profesor;
    private int periodo;
    private int cohorte;
    private List<Materia> prerequisitos;
    private List<Materia> corequisitos;
    private List<Clase> clases;

    public Materia(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
     * @return the asignatura
     */
    public Asignatura getAsignatura() {
        return asignatura;
    }

    /**
     * @param asignatura the asignatura to set
     */
    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    /**
     * @return the creditos
     */
    public int getCreditos() {
        return creditos;
    }

    /**
     * @param creditos the creditos to set
     */
    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the profesor
     */
    public Profesor getProfesor() {
        return profesor;
    }

    /**
     * @param profesor the profesor to set
     */
    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    /**
     * @return the periodo
     */
    public int getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    /**
     * @return the cohorte
     */
    public int getCohorte() {
        return cohorte;
    }

    /**
     * @param cohorte the cohorte to set
     */
    public void setCohorte(int cohorte) {
        this.cohorte = cohorte;
    }

    /**
     * @return the prerequisitos
     */
    public List<Materia> getPrerequisitos() {
        return prerequisitos;
    }

    /**
     * @param prerequisitos the prerequisitos to set
     */
    public void setPrerequisitos(List<Materia> prerequisitos) {
        this.prerequisitos = prerequisitos;
    }

    /**
     * @return the correquisitos
     */
    public List<Materia> getCorrequisitos() {
        return corequisitos;
    }

    /**
     * @param correquisitos the correquisitos to set
     */
    public void setCorrequisitos(List<Materia> correquisitos) {
        this.corequisitos = correquisitos;
    }

    /**
     * @return the clases
     */
    public List<Clase> getClases() {
        return clases;
    }

    /**
     * @param clases the clases to set
     */
    public void setClases(List<Clase> clases) {
        this.clases = clases;
        
        for(Clase c : clases) {
            c.setMateria(this);
        }
    }

    @Override
    public String toString() {
        return "Materia{" + "id=" + id + ", nombre=" + nombre + ", asignatura=" + asignatura + ", creditos=" + creditos + ", descripcion=" + descripcion + ", profesor=" + profesor + ", periodo=" + periodo + ", cohorte=" + cohorte + '}';
    }
}
