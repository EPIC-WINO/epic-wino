package edu.eci.pdsw.epicwino.logica.entidades;

import java.io.Serializable;

/**
 *
 * @author Esteban
 */
public class Recurso implements Serializable, Comparable{
    private int id;
    private String nombre;
    private String descripcion;
    private int cantidad;
    private String categoria;

    public Recurso() {
        nombre = "";
        descripcion = "";
    }
    
    public Recurso(int id, String nombre, String descripcion, int cantidad,String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.categoria=categoria;
    }
    public String getCategoria(){
        return categoria;
    }
    public void setCategoria(String categoria){
        this.categoria=categoria;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    @Override
    public String toString() {
        return "Recurso{" + "id=" + id + ", nombre=" + nombre + ", cantidad=" + cantidad + "}\n";
    }

    @Override
    public int compareTo(Object o) {
        Recurso m = (Recurso) o;
        return m.getId() < this.getId() ? -1 : (m.getId() == this.getId() ? 0 : 1);
    }
}
