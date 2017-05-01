/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ejemplos.Tests;

import edu.eci.pdsw.epicwino.logica.entidades.Clase;
import edu.eci.pdsw.epicwino.logica.entidades.Recurso;
import edu.eci.pdsw.epicwino.logica.servicios.ExcepcionServiciosProgmsPost;
import edu.eci.pdsw.epicwino.logica.servicios.ServiciosProgmsPost;
import edu.eci.pdsw.epicwino.logica.servicios.ServiciosProgmsPostFactory;
import java.util.Collection;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *Consultar Recursos concedidos para un periodo academico
 * 
 * Clases de Equivalencia:
 * CE1: Recursos concedidos en un periodo diferente al que se consultará (0 recursos)
 * CE2: Recursos concedidos para un periodo igual al que se consultará (Todos los asignados)
 * 
 * Clases de Frontera:
 * CF1: Un mismo recurso asignado a 2 diferentes clases (2 recursos)
 * CF2: Recursos Diferentes asigados a una misma clase (2 recursos)
 * 
 */
public class RecursosTest {
    
    public RecursosTest() {
    }
    
    @Before
    public void setUp() {
    }
    /**
    @Test
    public void CE1() throws ExcepcionServiciosProgmsPost {
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Recurso rc1 = new Recurso(50, "Computadores Portatiles", "Ordenador pequeño personal transportable",48, "");
        Recurso rc2 = new Recurso(51, "Equipo Teleconferencia", "Permite el intercambio de informacion entre maquinas",9, "");
        sp.registrarRecurso(rc1);sp.registrarRecurso(rc2);
        
        Clase cl = new Clase(40,java.sql.Date.valueOf("2015-04-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        sp.registrarClase(cl);
        
        sp.registrarPrestamoClase(40, rc1);
        sp.registrarPrestamoClase(40, rc2);
        Collection<RecursoConcedido> rccs = sp.ConsultarRecursosConcedidos(2015, 2);
        
        assertEquals("Se consulta inadecuadamente los recursos a la clase"
                    + "cuando esta no se da en la fecha indicada."
                    ,3,rccs.size());
    }
    @Test
    public void CE2() throws ExcepcionServiciosProgmsPost {
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Recurso rc1 = new Recurso(50, "Computadores Portatiles", "Ordenador pequeño personal transportable",48);
        Recurso rc2 = new Recurso(51, "Equipo Teleconferencia", "Permite el intercambio de informacion entre maquinas",9);
        sp.registrarRecurso(rc1);sp.registrarRecurso(rc2);
        
        Clase cl = new Clase(40,java.sql.Date.valueOf("2015-04-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        sp.registrarClase(cl);
        
        sp.registrarPrestamoClase(40, rc1);
        sp.registrarPrestamoClase(40, rc2);
        Collection<RecursoConcedido> rccs = sp.ConsultarRecursosConcedidos(2015, 1);
        
        assertEquals("Se consulta inadecuadamente los recursos a la clase"
                    + "cuando esta se debe dar en la fecha indicada."
                    ,3,rccs.size());
    }
    @Test
    public void CF1() throws ExcepcionServiciosProgmsPost {
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Recurso rc1 = new Recurso(50, "Computadores Portatiles", "Ordenador pequeño personal transportable",48);
        sp.registrarRecurso(rc1);
        
        Clase cl1 = new Clase(40,java.sql.Date.valueOf("2015-04-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        Clase cl2 = new Clase(41,java.sql.Date.valueOf("2015-04-13"),java.sql.Time.valueOf("08:00:00"),java.sql.Time.valueOf("11:00:00"));
        sp.registrarClase(cl1);sp.registrarClase(cl2);
        
        sp.registrarPrestamoClase(40, rc1);
        sp.registrarPrestamoClase(41, rc1);
        Iterator<RecursoConcedido> rccs = sp.ConsultarRecursosConcedidos(2015, 1).iterator();
        
        assertEquals("Se consulta inadecuadamente los recursos a las clases"
                    + "cuando esta se debe dar el mismo recurso igual al numero de clases (2)."
                    ,rccs.next().getRecurso().getId(),rccs.next().getRecurso().getId());
    }
    @Test
    public void CF2() throws ExcepcionServiciosProgmsPost {
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Recurso rc1 = new Recurso(50, "Computadores Portatiles", "Ordenador pequeño personal transportable",48);
        Recurso rc2 = new Recurso(51, "Equipo Teleconferencia", "Permite el intercambio de informacion entre maquinas",9);
        sp.registrarRecurso(rc1);sp.registrarRecurso(rc2);
        
        Clase cl = new Clase(40,java.sql.Date.valueOf("2015-04-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        sp.registrarClase(cl);
        
        sp.registrarPrestamoClase(40, rc1);
        sp.registrarPrestamoClase(40, rc2);
        Iterator<RecursoConcedido> rccs = sp.ConsultarRecursosConcedidos(2015, 1).iterator();
        
        assertEquals("Se consulta inadecuadamente los recursos a la clase"
                    + "cuando esta se debe los diferentes recursos a la misma clase."
                    ,rccs.next().getRecurso().getId()!=rccs.next().getRecurso().getId());
    }
    **/
}
