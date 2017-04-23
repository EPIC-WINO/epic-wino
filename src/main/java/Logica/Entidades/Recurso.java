package Logica.Entidades;

import java.io.Serializable;

/**
 *
 * @author Esteban
 */
public class Recurso implements Serializable{
    private int id;
    private String nombre;
    private String descripcion;
    private int cantidad;

    public Recurso(int id, String nombre, String descripcion, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
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
}
