package edu.eci.pdsw.epicwino.logica.entidades;

import java.util.List;

/**
 *
 * @author 2110137
 */
public class GrupoDeMateria {
    private int periodo;
    private Profesor profesor;
    private List<Clase> clases;
    
    public GrupoDeMateria() {
        
    }
    
    public GrupoDeMateria(Profesor profesor, List<Clase> clases) {
        this.profesor = profesor;
        this.clases = clases;
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

    @Override
    public String toString() {
        return "GrupoDeMateria{" + "periodo=" + periodo + ", profesor=" + profesor + '}';
    }
}
