/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.epicwino.tests;

import edu.eci.pdsw.epicwino.logica.entidades.Asignatura;
import edu.eci.pdsw.epicwino.logica.entidades.Clase;
import edu.eci.pdsw.epicwino.logica.entidades.GrupoDeMateria;
import edu.eci.pdsw.epicwino.logica.entidades.Materia;
import edu.eci.pdsw.epicwino.logica.entidades.Profesor;
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
 * Registrar y Consultar los profesores dados en un periodo o por periodo y materia
 * 
 * Clases de Equivalencia
 * CE1: registrar y consultar adecuadamente los profesores dados en un periodo academico y rectificar(1)
 * CE2: registrar y consultar adecuadamente ningun profesor dado en un periodo academico y rectificar(0)
 * CE3: registrar y consultar adecuadamente el profesor que desarrollan una materia en un periodo (1)
 * 
 * Clases de Frontera
 * CF1: No consultar un profesor para un periodo inconsistente, previamente registrado (1)
 * CF2: No consultar el profesor de la materia, cuando no esta inscrita para es periodo (0)
 * 
 * Rectificar : Consultar la disponibilidad de un profesor en un horario
 */
public class ProfesoresTest {

    public ProfesoresTest() {
    }
    
    @BeforeClass
    public static void setUp() throws ExcepcionServiciosProgmsPost {
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Programa prg1 = new Programa(50,"Gerencia de Proyectos", "Especializacion");
        Asignatura as1 = new Asignatura(50,"Ejecucion");
        sp.registrarPrograma(prg1);
        sp.registrarAsignatura(as1, 50);
    }
    
    @Test
    public void CE1() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Materia m1 = new Materia(80,"Gerencia Financiera");
        Profesor pf1 = new Profesor(1,"Juan Perez Rodriguez");
        
        List<GrupoDeMateria> gruposMateria = new ArrayList<>();
        GrupoDeMateria grupo1 = new GrupoDeMateria();
        grupo1.setPeriodo(20101);
        grupo1.setProfesor(pf1);
        gruposMateria.add(grupo1);
        m1.setGruposDeMateria(gruposMateria);
        
        sp.registrarProfesor(pf1);
        List<Clase> clasesGrupo = new ArrayList<>();
        Clase cl = new Clase(20,java.sql.Date.valueOf("2010-03-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        
        grupo1.setClases(clasesGrupo);
        sp.registrarMateria(m1,50);
        Iterator<Profesor> profesores = sp.consultarProfesores(20101).iterator();
        
        assertEquals("Se consulta inadecuadamente los profesores para el periodo 2010-1"
                    + "cuando esta debe mostrar los profesores : "
                    ,"Juan Perez Rodriguez",profesores.next().getNombre());
        assertEquals("Se consulta inadecuadamente la disponibilidad del profesor en el horario"
                    + "cuando este ya esta inscrito en ese horario "
                    ,false,sp.consultarDisponibilidadProfesor(1, java.sql.Date.valueOf("2010-03-08"), java.sql.Time.valueOf("08:00:00"), java.sql.Time.valueOf("11:00:00")));
    }
    
    @Test
    public void CE2() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Materia m1 = new Materia(81,"Gerencia Financiera");
        Profesor pf1 = new Profesor(2,"Juan Perez Rodriguez");
        
        List<GrupoDeMateria> gruposMateria = new ArrayList<>();
        GrupoDeMateria grupo1 = new GrupoDeMateria();
        grupo1.setPeriodo(20111);
        grupo1.setProfesor(pf1);
        gruposMateria.add(grupo1);
        m1.setGruposDeMateria(gruposMateria);
        
        sp.registrarProfesor(pf1);
        List<Clase> clasesGrupo = new ArrayList<>();
        Clase cl = new Clase(21,java.sql.Date.valueOf("2011-03-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        
        grupo1.setClases(clasesGrupo);
        sp.registrarMateria(m1,50);
        Collection<Profesor> profesores = sp.consultarProfesores(20112);
        
        assertEquals("Se consulta inadecuadamente los profesores para el periodo 2013-2"
                    + "cuando esta no debe mostrar ningun profesor"
                    ,0,profesores.size());
        assertEquals("Se consulta inadecuadamente la disponibilidad del profesor en el horario"
                    + "cuando este no esta inscrito en ese horario "
                    ,true,sp.consultarDisponibilidadProfesor(2, java.sql.Date.valueOf("2011-05-08"), java.sql.Time.valueOf("08:00:00"), java.sql.Time.valueOf("11:00:00")));
    }
    
    @Test
    public void CE3() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Materia m1 = new Materia(82,"Gerencia Financiera");
        Profesor pf1 = new Profesor(3,"Oscar Robledo Cruz");
        
        List<GrupoDeMateria> gruposMateria = new ArrayList<>();
        GrupoDeMateria grupo1 = new GrupoDeMateria();
        grupo1.setPeriodo(20121);
        grupo1.setProfesor(pf1);
        gruposMateria.add(grupo1);
        m1.setGruposDeMateria(gruposMateria);
        
        sp.registrarProfesor(pf1);
        List<Clase> clasesGrupo = new ArrayList<>();
        Clase cl = new Clase(22,java.sql.Date.valueOf("2012-03-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        
        grupo1.setClases(clasesGrupo);
        sp.registrarMateria(m1,50);
        Profesor profesorMat = sp.consultarProfesor(20121,82);
        
        assertEquals("Se consulta inadecuadamente el profesor para el periodo 2012-1 y materia Gerencia Financiera"
                    + "cuando esta debe mostrar el profesor : "
                    ,"Oscar Robledo Cruz",profesorMat.getNombre());
    }
    
    @Test
    public void CF1(){
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Materia m1 = new Materia(83,"Gerencia Financiera");
        Profesor pf1 = new Profesor(4,"Oscar Robledo Cruz");
        
        List<GrupoDeMateria> gruposMateria = new ArrayList<>();
        GrupoDeMateria grupo1 = new GrupoDeMateria();
        grupo1.setPeriodo(20131);
        grupo1.setProfesor(pf1);
        gruposMateria.add(grupo1);
        m1.setGruposDeMateria(gruposMateria);
        
        List<Clase> clasesGrupo = new ArrayList<>();
        Clase cl = new Clase(23,java.sql.Date.valueOf("2013-03-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        
        grupo1.setClases(clasesGrupo);
        boolean thrown = false;
        try {
            sp.registrarProfesor(pf1);
            sp.registrarMateria(m1,50);
            Collection<Profesor> profesores = sp.consultarProfesores(20134);
        } catch (ExcepcionServiciosProgmsPost ex) {
            thrown = true;
        }
        assertTrue("Se consulta inadecuadamente los profesores para una periodo academico inconsistente"
                    + "cuando esta debe lanzar ExcepcionServiciosProgmsPost",thrown);
    }
    
    @Test
    public void CF2(){
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        boolean thrown = false;
        try {
            Profesor profesorMat = sp.consultarProfesor(20132,600);
        } catch (ExcepcionServiciosProgmsPost ex) {
            thrown = true;
        }
        assertTrue("Se consulta inadecuadamente el profesores para una materia que no existe"
                    + "cuando esta debe lanzar ExcepcionServiciosProgmsPost",thrown);
    }
}
