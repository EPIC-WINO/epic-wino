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
    private Clase clase;
    private Recurso recurso;

    public RecursoConcedido(Clase clase, Recurso recurso) {
        this.clase = clase;
        this.recurso = recurso;
    }

    public Clase getClase() {
        return clase;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
    }
    
    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }
    
    @Override
    public String toString() {
        return "RecursoConcedido{" + ", recurso=" + recurso + "}\n";
    }
}
