/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.epicwino.logica.dao.mybatis.mappers;

import edu.eci.pdsw.epicwino.logica.entidades.Clase;
import edu.eci.pdsw.epicwino.logica.entidades.Recurso;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author Esteban
 */
public interface ClaseMapper {
    /**
     * @obj agregar a la base de datos una nueva clase
     * @param c clase a agregar
     */
    public void agregarClase(@Param("clase") Clase c);
    
    /**
     * @obj consultar en la base de datos la clase asociada con la id
     * @param id identificador de la clase
     * @return clase asociada al identificador
     */
    public Clase consultarClase(@Param("idclase") int id);
    
    /**
     * @param anio
     * @obj consultar en la base de datos todas las clases asociadas a un año y un semestre
     * @return consulta de todas las clases
     * @param semestre semestre del periodo academico 1 o 2
     */
    public List<Clase> consultarClases(@Param("anio") int anio,
            @Param("semestre") int semestre);
    
    /**
     * @obj consultar en la base de datos los recursos que requiere la clase para realizarse
     * @param idCl identificador de la clase
     * @return recursos prestados a la clase
     */
    public List<Recurso> consultarRecursosConcedidos(@Param("idclase") int idCl); // FIXME logica cambio
    
    /**
     * @obj agregar a la base de datos un recurso requerido por una clase para realizarse
     * @param idCl identificador de la clase
     * @param idRe identificador del recurso
     */
    public void agregarRecursoConcedido(@Param("idclase") int idCl,
            @Param("recurso") int idRe);
}
