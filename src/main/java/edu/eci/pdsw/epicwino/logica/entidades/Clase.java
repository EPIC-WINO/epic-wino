package edu.eci.pdsw.epicwino.logica.entidades;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

/**
 *
 * @author Esteban
 */
public class Clase implements Serializable, Comparable {
    private int id;
    private Date fecha;
    private Time horaInicio;
    private Time horaFin;
    private ArrayList<Recurso> recursos;

    public Clase() {
        recursos = new ArrayList<>();
    }
    
    public Clase(int id, Date fecha, Time horaInicio, Time horaFin) {
        this();
        this.id = id;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
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
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the horaInicio
     */
    public Time getHoraInicio() {
        return horaInicio;
    }

    /**
     * @param horaInicio the horaInicio to set
     */
    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * @return the horaFin
     */
    public Time getHoraFin() {
        return horaFin;
    }

    /**
     * @param horaFin the horaFin to set
     */
    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    /**
     * @return the recursos
     */
    public ArrayList<Recurso> getRecursos() {
        return recursos;
    }

    /**
     * @param recursos the recursos to set
     */
    public void setRecursos(ArrayList<Recurso> recursos) {
        this.recursos = recursos;
    }

    @Override
    public String toString() {
        return "Clase{" + "id=" + id + ", fecha=" + fecha + ", horaInicio=" + horaInicio + ", horaFin=" + horaFin + '}';
    }

    @Override
    public int compareTo(Object o) {
        Clase m = (Clase) o;
        return m.getId() < this.getId() ? -1 : (m.getId() == this.getId() ? 0 : 1);
    }
}
