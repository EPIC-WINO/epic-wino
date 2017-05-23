/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.epicwino.tests;

import edu.eci.pdsw.epicwino.logica.entidades.Asignatura;
import edu.eci.pdsw.epicwino.logica.entidades.Clase;
import edu.eci.pdsw.epicwino.logica.entidades.Materia;
import edu.eci.pdsw.epicwino.logica.entidades.Programa;
import edu.eci.pdsw.epicwino.logica.servicios.ExcepcionServiciosProgmsPost;
import edu.eci.pdsw.epicwino.logica.servicios.ServiciosProgmsPost;
import edu.eci.pdsw.epicwino.logica.servicios.ServiciosProgmsPostFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Registrar y Consultar los programas que se ofrecen en un periodo o una asignatura
 * 
 * Clases de Equivalencia
 * CE1: registrar adecuadamente varios programas y consultarlos para un periodo (2)
 * CE2: registrar adecuadamente un programa y consultarlo en base a una asignatura (1)
 * CE3: registrar adecuadamente varios programsa y consultarlos en base a una asignatura que tienen inscritas; asignatura perteneciente a dos programas (1)
 * 
 * Clases de Frontera
 * CF1: no registrar un programa que ya existie (1)
 * CF2: no consultar un programa para un periodo negativo, registrado
 * CF3: no consultar un programa asociado a una asignatura que no existe
 */
public class ProgramasTest {

    public ProgramasTest() {
    }
    
    @BeforeClass
    public static void setUp() {
    }
    
    @Test
    public void CE1() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Programa prg1 = new Programa(60,"Gerencia de Proyectos", "Especializacion");
        Programa prg2 = new Programa(61,"Gestion de Informacion", "Maestria");
        Asignatura as1 = new Asignatura(60,"Ejecucion");
        Asignatura as2 = new Asignatura(61,"Operacion");
        Materia m1 = new Materia(100,"Gerencia Financiera");
        Materia m2 = new Materia(101,"Analisis de Riesgos");
        Clase cl1 = new Clase(40,java.sql.Date.valueOf("2015-04-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        Clase cl2 = new Clase(41,java.sql.Date.valueOf("2015-04-13"),java.sql.Time.valueOf("08:00:00"),java.sql.Time.valueOf("11:00:00"));
        
        sp.registrarPrograma(prg1);
        sp.registrarPrograma(prg2);
        sp.registrarAsignatura(as1, 60);
        sp.registrarAsignatura(as2, 61);
        sp.registrarMateria(m1, 60);
        sp.registrarMateria(m2, 61);
        sp.agregarClase("100", cl1);
        sp.agregarClase("101", cl2);
        
        Collection<Programa> progPorPer = sp.consultarProgramas(20151);
        assertEquals("Se registra o consulta inadecuadamente los programas programados para un periodo academico"
                    + "cuando esta se debe mostrar : "
                    ,2,progPorPer.size());
    }
    /*
    @Test
    public void CE2() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Programa prg1 = new Programa(62,"Gerencia de Proyectos", "Especializacion");
        Asignatura as1 = new Asignatura(62,"Ejecucion");
        
        sp.registrarPrograma(prg1);
        sp.registrarAsignatura(as1, 62);
        
        Collection<Programa> progPorAsignatura = sp.consultarPrograma(62);
        assertEquals("Se registra o consulta inadecuadamente el programa asociado a una asignatura"
                    + "cuando esta se debe mostrar : "
                    ,1,progPorAsignatura.size());
    }
    
    @Test
    public void CE3() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Programa prg1 = new Programa(63,"Gerencia de Proyectos", "Especializacion");
        Programa prg2 = new Programa(64,"Gestion de Informacion", "Maestria");
        Asignatura as1 = new Asignatura(63,"Ejecucion");
        
        sp.registrarPrograma(prg1);
        sp.registrarPrograma(prg2);
        sp.registrarAsignatura(as1, 63);
        sp.registrarAsignatura(as1, 64);
        
        Collection<Programa> progPorAsignatura = sp.consultarPrograma(63);
        assertEquals("Se registra o consulta inadecuadamente los programas asociados a una asignatura"
                    + "cuando esta se debe mostrar : "
                    ,2,progPorAsignatura.size());
    }*/
    
    @Test
    public void CF1(){
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Programa prg1 = new Programa(65,"Gerencia de Proyectos", "Especializacion");
        boolean thrown = false;
        try {
            sp.registrarPrograma(prg1);
            sp.registrarPrograma(prg1);
        } catch (ExcepcionServiciosProgmsPost ex) {
            thrown = true;
        }
        assertTrue("Se registra inadecuadamente un programa que ya existe"
                + ", cuando esta debe lanzar ExcepcionServiciosProgmsPost",thrown); 
    }
    
    @Test
    public void CF2(){
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Programa prg1 = new Programa(66,"Gerencia de Proyectos", "Especializacion");
        boolean thrown = false;
        try {
            sp.registrarPrograma(prg1);
            Collection<Programa> progPorPer = sp.consultarProgramas(-20151);
        } catch (ExcepcionServiciosProgmsPost ex) {
            thrown = true;
        }
        assertTrue("Se consulta inadecuadamente un programa para un periodo inconsistente"
                + ", cuando esta debe lanzar ExcepcionServiciosProgmsPost",thrown); 
    }
    
    @Test
    public void CF3(){
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Programa prg1 = new Programa(67,"Gerencia de Proyectos", "Especializacion");
        Asignatura as1 = new Asignatura(64,"Ejecucion");
        boolean thrown = false;
        try {
            sp.registrarPrograma(prg1);
            sp.registrarAsignatura(as1, 67);
            sp.consultarPrograma(700);
        } catch (ExcepcionServiciosProgmsPost ex) {
            thrown = true;
        }
        assertTrue("Se consulta inadecuadamente un programa consultado con una asignatura que no existe"
                + ", cuando esta debe lanzar ExcepcionServiciosProgmsPost",thrown); 
    }
}
