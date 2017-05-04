package edu.eci.pdsw.epicwino.logica.entidades;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 */
public class Reunion {
    private int id;
    private Comite comite;
    private Date fecha;
    private Time horaInicio;
    private Time horaFin;

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
     * @return the comite
     */
    public Comite getComite() {
        return comite;
    }

    /**
     * @param comite the comite to set
     */
    public void setComite(Comite comite) {
        this.comite = comite;
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

    @Override
    public String toString() {
        return "Reunion{" + "id=" + id + ", comite=" + comite + ", fecha=" + fecha + ", horaInicio=" + horaInicio + ", horaFin=" + horaFin + '}';
    }
}
