/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Entidades;

import java.io.Serializable;

/**
 *
 * @author Esteban
 */
public class RecursoConcedido implements Serializable{
    private int id;
    private Recurso recurso;

    public RecursoConcedido(int id, Recurso recurso) {
        this.id = id;
        this.recurso = recurso;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }
    
    @Override
    public String toString() {
        return "RecursoConcedido{" + "id=" + id + ", recurso=" + recurso + "}\n";
    }
}
