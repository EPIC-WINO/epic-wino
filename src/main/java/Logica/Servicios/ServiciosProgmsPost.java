/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Servicios;

import Logica.Entidades.Clase;
import Logica.Entidades.Recurso;
import Logica.Entidades.RecursoConcedido;
import java.util.List;
import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Esteban
 */
public interface ServiciosProgmsPost {
    
    /**
     * @obj consultar los recursos concedidos a clases para un periodo academico
     * @pre el semestre debe ser 1 o 2 unicamente
     * @param año el año donde se realiza el programa
     * @param semestre el año donde se realiza el programa (1 o 2)
     * @return el listado de recursos prestados para el programa en determinado periodo academico
     */
    public abstract List<RecursoConcedido> ConsultarRecursosConcedidos(int año, int semestre);
    
    /**
     * @obj registrar una clase para un programa
     * @param c la clase a registrar
     * @throws ExcepcionServiciosProgmsPost si la clase ya existe
     */
    public abstract void registrarClase(Clase c) throws ExcepcionServiciosProgmsPost;
    
    /**
     * @obj registrar un recurso para un programa
     * @param rec el recurso a registrar
     * @throws ExcepcionServiciosProgmsPost si el recurso ya existe
     */
    public abstract void registrarRecurso(Recurso rec) throws ExcepcionServiciosProgmsPost;
    
    /**
     * @obj registrar el prestamo de un recurso a una clase
     * @param clase el identificador de la clase
     * @param rec el recurso a conceder a la clase
     * @pos el recurso ahora tiene una cantidad menos disponible y esta asociado a la clase
     * @throws ExcepcionServiciosProgmsPost si no existe la clase o 
     * el recurso no esta disponible para esa clase (hoario y fecha)
     */
    public abstract void registrarPrestamoClase(int clase, Recurso rec) throws ExcepcionServiciosProgmsPost;
}
