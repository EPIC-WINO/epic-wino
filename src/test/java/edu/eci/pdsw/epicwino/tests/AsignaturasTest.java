/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.epicwino.tests;

import edu.eci.pdsw.epicwino.logica.entidades.Asignatura;
import edu.eci.pdsw.epicwino.logica.entidades.GrupoDeMateria;
import edu.eci.pdsw.epicwino.logica.entidades.Materia;
import edu.eci.pdsw.epicwino.logica.entidades.Programa;
import edu.eci.pdsw.epicwino.logica.servicios.ExcepcionServiciosProgmsPost;
import edu.eci.pdsw.epicwino.logica.servicios.ServiciosProgmsPost;
import edu.eci.pdsw.epicwino.logica.servicios.ServiciosProgmsPostFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Registrar y consultar asignaturas nuevas dada la materia o el programa y el periodo
 * 
 * Clases de Equivalencia
 * CE1: registrar y consultar adecuadamente varias asignaturas que no existian, a varios programas, en el periodo correcto (2)
 * CE2: registrar y consultar adecuadamente una asignatura para un periodo que no esta inscrita(0)
 * CE3: registrar y consultar adecuadamente las asignaturas relacionadas a una materia (2)
 * 
 * Clases de Frontera
 * CF1: no registrar una asignatura que ya existie (0)
 * CF2: no registrar una asignatura a dos programas distintos (0)
 * CF3: no consultar una asignatura en un periodo inconsistente (0)
 * CF4: no consultar ninguna asignatura relacionada a una materia (0)
 */
public class AsignaturasTest {

    public AsignaturasTest() {
    }
    
    @BeforeClass
    public static void setUp() {
    }
    
    @Test
    public void CE1() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Programa prg1 = new Programa(1,"Gerencia de Proyectos", "Especializacion");
        Asignatura as1 = new Asignatura(1,"Ejecucion");
        Materia m1 = new Materia(1,"Gerencia Financiera");
        
        List<GrupoDeMateria> gruposMateria = new ArrayList<>();
        GrupoDeMateria grupo1 = new GrupoDeMateria();
        grupo1.setPeriodo(20001);gruposMateria.add(grupo1);
        m1.setGruposDeMateria(gruposMateria);
        
        sp.registrarPrograma(prg1);
        sp.registrarAsignatura(as1, 1);
        sp.registrarMateria(m1, 1);
        
        Iterator<Asignatura> asigPeriodo = sp.consultarAsignaturas(20001, 1).iterator();
        assertEquals("Se registra o consulta inadecuadamente la asignatura para el periodo 2000-1"
                    + "cuando esta se debe mostrar la asignatura : "
                    ,"Ejecucion",asigPeriodo.next().getNombre());
    }
    
    @Test
    public void CE2() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Programa prg1 = new Programa(2,"Gerencia de Proyectos", "Especializacion");
        Asignatura as1 = new Asignatura(2,"Ejecucion");
        Materia m1 = new Materia(2,"Gerencia Financiera");
        
        sp.registrarPrograma(prg1);
        sp.registrarAsignatura(as1, 2);
        sp.registrarMateria(m1, 2);
        
        Collection<Asignatura> asigPeriodo = sp.consultarAsignaturas(19991, 2);
        assertEquals("Se registra o consulta inadecuadamente las asignaturas para el periodo 1999-1"
                    + "cuando esta no debe mostrar ninguna asignatura : "
                    ,0,asigPeriodo.size());
    }
    
    @Test
    public void CE3() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Programa prg1 = new Programa(3,"Gerencia de Proyectos", "Especializacion");
        Programa prg2 = new Programa(4,"Gerencia de Produccion Industrial", "Especializacion");
        Asignatura as1 = new Asignatura(3,"Ejecucion");
        Asignatura as2 = new Asignatura(4,"Operacion");
        Materia m1 = new Materia(3,"Gerencia Financiera");
        
        sp.registrarPrograma(prg1);sp.registrarPrograma(prg2);
        sp.registrarAsignatura(as1, 3);sp.registrarAsignatura(as2, 4);
        sp.registrarMateria(m1, 3);sp.registrarMateria(m1, 4);
        
        Collection<Asignatura> asigMateria = sp.consultarAsignaturas("3");
        assertEquals("Se registra o consulta inadecuadamente las asignaturas relacionadas a una materias"
                    + "cuando esta se debe mostrar las asignaturas : "
                    ,2,asigMateria.size());
    }
    
    @Test
    public void CF1(){
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Programa prg1 = new Programa(5,"Gerencia de Proyectos", "Especializacion");
        Asignatura as1 = new Asignatura(5,"Ejecucion");
        
        boolean thrown = false;
        try{
            sp.registrarPrograma(prg1);
            sp.registrarAsignatura(as1, 5);
            sp.registrarAsignatura(as1, 5);
        } catch(ExcepcionServiciosProgmsPost e) {
            thrown = true;
        }
        assertTrue("Se registra inadecuadamente una asignatura existente"
                + ", cuando esta debe lanzar ExcepcionServiciosProgmsPost",thrown);
    }
    
    @Test
    public void CF2(){
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Programa prg1 = new Programa(6,"Gerencia de Proyectos", "Especializacion");
        Programa prg2 = new Programa(7,"Gerencia de Produccion Industrial", "Especializacion");
        Asignatura as1 = new Asignatura(6,"Ejecucion");
        
        boolean thrown = false;
        try{
            sp.registrarPrograma(prg1);sp.registrarPrograma(prg2);
            sp.registrarAsignatura(as1, 6);
            sp.registrarAsignatura(as1, 7);
        } catch(ExcepcionServiciosProgmsPost e) {
            thrown = true;
        }
        assertTrue("Se registra inadecuadamente una asignatura a dos programas distintos"
                + ", cuando esta debe lanzar ExcepcionServiciosProgmsPost",thrown);
    }
    
    @Test
    public void CF3(){
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Programa prg1 = new Programa(8,"Gerencia de Proyectos", "Especializacion");
        Asignatura as1 = new Asignatura(7,"Ejecucion");
        
        boolean thrown = false;
        try{
            sp.registrarPrograma(prg1);
            sp.registrarAsignatura(as1, 8);
            sp.consultarAsignaturas(-2, 8);
        } catch(ExcepcionServiciosProgmsPost e) {
            thrown = true;
        }
        assertTrue("Se consulta inadecuadamente una asignatura con un periodo inconsistente"
                + ", cuando esta debe lanzar ExcepcionServiciosProgmsPost",thrown);
    }
    
    @Test
    public void CF4(){
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Programa prg1 = new Programa(9,"Gerencia de Proyectos", "Especializacion");
        Asignatura as1 = new Asignatura(8,"Ejecucion");
        Materia m1 = new Materia(4,"Gerencia Financiera");
        
        boolean thrown = false;
        try{
            sp.registrarPrograma(prg1);
            sp.registrarAsignatura(as1, 9);
            sp.consultarAsignaturas("4");
        } catch(ExcepcionServiciosProgmsPost e) {
            thrown = true;
        }
        assertTrue("Se consulta inadecuadamente una asignatura con una materia que no esta registrada"
                + ", cuando esta debe lanzar ExcepcionServiciosProgmsPost",thrown);
    }
}
