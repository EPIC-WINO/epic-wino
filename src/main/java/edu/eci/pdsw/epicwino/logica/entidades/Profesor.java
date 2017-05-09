/*
 */
package edu.eci.pdsw.epicwino.logica.entidades;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 */
public class Profesor {
    private int id;
    private String nombre;
    private String tipoID;
    private String correo;
    private String telefono;
    private List<Comite> comites;

    public Profesor() {
        nombre = "";
        tipoID = "";
        correo = "";
        telefono = "";
    }
    
    public Profesor(int id, String nombre) {
        this.id = id;
        this.nombre  = nombre;
        comites = new ArrayList<>();
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
     * @return the tipoID
     */
    public String getTipoID() {
        return tipoID;
    }

    /**
     * @param tipoID the tipoID to set
     */
    public void setTipoID(String tipoID) {
        this.tipoID = tipoID;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the comites
     */
    public List<Comite> getComites() {
        return comites;
    }

    /**
     * @param comites the comites to set
     */
    public void setComites(List<Comite> comites) {
        this.comites = comites;
    }

    @Override
    public String toString() {
        return "Profesor{" + "id=" + id + ", nombre=" + nombre + ", tipoID=" + tipoID + ", correo=" + correo + ", telefono=" + telefono + '}';
    }
}
