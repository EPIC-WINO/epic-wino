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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Consultar todos los periodos registrados o asociados a un programa
 * 
 * Clases de Equivalencia
 * CE1: consultar adecuadamente todos los periodos que han sido registrados (2)
 * CE2: consultar adecuadamente todos los periodos asociados a un programa (1)
 * CE3: consultar adecuadamente el periodo asociado a un programa, cuando hay varias materias en ese periodo (1)
 * 
 * Clases de Frontera
 * CF1: no consultar los periodos de un programa que no existe (0)
 */
public class PeriodosTest {

    public PeriodosTest() {
    }
    
    @BeforeClass
    public static void setUp() {
    }
    
    @Test
    public void CE1() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Materia m1 = new Materia(60,"Gerencia Financiera");
        Programa prg1 = new Programa(40,"Gerencia de Proyectos", "Maestria");
        Asignatura as1 = new Asignatura(40,"Ejecucion");

        sp.registrarPrograma(prg1);
        sp.registrarAsignatura(as1, 40);
        
        List<GrupoDeMateria> gruposMateria = new ArrayList<>();
        GrupoDeMateria grupo1 = new GrupoDeMateria();
        GrupoDeMateria grupo2 = new GrupoDeMateria();
        grupo1.setPeriodo(20071);grupo2.setPeriodo(20072);
        gruposMateria.add(grupo1);gruposMateria.add(grupo2);
        m1.setGruposDeMateria(gruposMateria);
        
        Collection<Integer> periodosBef = sp.consultarPeriodos();
        sp.registrarMateria(m1,40);
        Collection<Integer> periodosAft = sp.consultarPeriodos();
        
        assertEquals("Se consulta inadecuadamente todos los periodos inscritos"
                    + "cuando esta se debe mostrar los periodos : "
                    ,2,periodosAft.size()-periodosBef.size());
    }
    
    @Test
    public void CE2() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Programa prg1 = new Programa(41,"Gerencia de Proyectos", "Especializacion");
        Asignatura as1 = new Asignatura(41,"Ejecucion");
        Materia m1 = new Materia(61,"Gerencia Financiera");
        
        List<GrupoDeMateria> gruposMateria = new ArrayList<>();
        GrupoDeMateria grupo1 = new GrupoDeMateria();
        grupo1.setPeriodo(20081);gruposMateria.add(grupo1);
        m1.setGruposDeMateria(gruposMateria);
        
        sp.registrarPrograma(prg1);
        sp.registrarAsignatura(as1, 41);
        sp.registrarMateria(m1, 41);
        
        Iterator<Integer> periodos = sp.consultarPeriodos(41).iterator();
        assertEquals("Se consulta inadecuadamente todos los periodos inscritos"
                    + "cuando esta se debe mostrar el periodo : "
                    ,(Integer)20181,periodos.next());
    }
    
    @Test
    public void CE3() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Programa prg1 = new Programa(42,"Gerencia de Proyectos", "Especializacion");
        Asignatura as1 = new Asignatura(42,"Ejecucion");
        Materia m1 = new Materia(62,"Gerencia Financiera");
        Materia m2 = new Materia(63,"Analisis de Riesgos");
        
        List<GrupoDeMateria> gruposMateria1 = new ArrayList<>();
        GrupoDeMateria grupo1 = new GrupoDeMateria();
        grupo1.setPeriodo(20091);gruposMateria1.add(grupo1);
        m1.setGruposDeMateria(gruposMateria1);
        
        List<GrupoDeMateria> gruposMateria2 = new ArrayList<>();
        GrupoDeMateria grupo2 = new GrupoDeMateria();
        grupo2.setPeriodo(20091);gruposMateria2.add(grupo2);
        m2.setGruposDeMateria(gruposMateria2);
        
        sp.registrarPrograma(prg1);
        sp.registrarAsignatura(as1, 42);
        sp.registrarMateria(m1, 42);
        sp.registrarMateria(m2, 42);
        
        Collection<Integer> periodos = sp.consultarPeriodos(42);
        assertEquals("Se consulta inadecuadamente el periodo inscrito para dos materias de un mismo programa"
                    + "cuando esta se debe mostrar solo un periodo : "
                    ,1,periodos.size());
    }
    
    @Test
    public void CF1(){
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        boolean thrown = false;
        Iterator<Integer> periodos;
        try {
            periodos = sp.consultarPeriodos(500).iterator();
        } catch (ExcepcionServiciosProgmsPost ex) {
            thrown = true;
        }
        assertTrue("Se consulta inadecuadamente los periodos de un programa inexistente"
                + ", cuando esta debe lanzar ExcepcionServiciosProgmsPost",thrown);
    }
}
