/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Servicios.impl;

import Logica.Dao.ClaseDAO;
import Logica.Dao.RecursoDAO;
import Logica.Entidades.Clase;
import Logica.Entidades.Recurso;
import Logica.Entidades.RecursoConcedido;
import Logica.Servicios.ExcepcionServiciosProgmsPost;
import Logica.Servicios.ServiciosProgmsPost;
import com.google.inject.Inject;
import java.util.List;

/**
 *
 * @author Esteban
 */
public class ServiciosProgmsPostImpl implements ServiciosProgmsPost{

    @Inject
    private RecursoDAO daoRecurso;
    
    @Inject 
    private ClaseDAO daoCliente;
    
    @Override
    public List<RecursoConcedido> ConsultarRecursosConcedidos(int año, int semestre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registrarClase(Clase c) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registrarRecurso(Recurso rec) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registrarPrestamoClase(int clase, Recurso rec) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
