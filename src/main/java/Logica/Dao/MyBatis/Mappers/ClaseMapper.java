/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Dao.MyBatis.Mappers;

import Logica.Entidades.Clase;
import Logica.Entidades.RecursoConcedido;
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
     * @obj consultar en la base de datos todas las clases asociadas a un año y un semestre
     * @return consulta de todas las clases
     * @param año año del periodod academico
     * @param semestre semestre del periodo academico 1 o 2
     */
    public List<Clase> consultarClases(@Param("año") int año,
            @Param("semestre") int semestre);
    
    /**
     * @obj consultar en la base de datos los recursos que requiere la clase para realizarse
     * @param idCl identificador de la clase
     * @return recursos prestados a la clase
     */
    public List<RecursoConcedido> consultarRecursosConcedidos(@Param("idclase") int idCl);
    
    /**
     * @obj agregar a la base de datos un recurso requerido por una clase para realizarse
     * @param idCl identificador de la clase
     * @param idRe identificador del recurso
     */
    public void agregarRecursoConcedido(@Param("idclase") int idCl,
            @Param("recurso") int idRe);
}
