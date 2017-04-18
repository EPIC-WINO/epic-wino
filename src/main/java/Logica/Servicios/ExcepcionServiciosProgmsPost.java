/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Servicios;

/**
 *
 * @author Esteban
 */
public class ExcepcionServiciosProgmsPost extends Exception {
    
    public ExcepcionServiciosProgmsPost() {
    }

    public ExcepcionServiciosProgmsPost(String message) {
        super(message);
    }

    public ExcepcionServiciosProgmsPost(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcepcionServiciosProgmsPost(Throwable cause) {
        super(cause);
    }
}
