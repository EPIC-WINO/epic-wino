/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Entidades;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

/**
 *
 * @author Esteban
 */
public class Clase implements Serializable{
    private int id;
    private Date fecha;
    private Time hora_inicio;
    private Time hora_fin;
    private ArrayList<RecursoConcedido> recursos; 

    public Clase(int id, Date fecha, Time hora_inicio, Time hora_fin) {
        this.id = id;
        this.fecha = fecha;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.recursos = new ArrayList<>();
    }

    public Clase(int id, Date fecha, Time hora_inicio, Time hora_fin, ArrayList<RecursoConcedido> recursos) {
        this.id = id;
        this.fecha = fecha;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.recursos = recursos;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(Time hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public Time getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(Time hora_fin) {
        this.hora_fin = hora_fin;
    }

    public ArrayList<RecursoConcedido> getRecursos() {
        return recursos;
    }

    public void setRecursos(ArrayList<RecursoConcedido> recursos) {
        this.recursos = recursos;
    }
    
    @Override
    public String toString() {
        return "Clase{" + "id=" + id + ", fecha=" + fecha + ", hora_inicio=" + hora_inicio+ ", hora_fin=" + hora_fin + ", recursos=" + recursos +"}\n";
    }
}
