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
import edu.eci.pdsw.epicwino.logica.entidades.Recurso;
import edu.eci.pdsw.epicwino.logica.servicios.ExcepcionServiciosProgmsPost;
import edu.eci.pdsw.epicwino.logica.servicios.ServiciosProgmsPost;
import edu.eci.pdsw.epicwino.logica.servicios.ServiciosProgmsPostFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 * Registrar y Consultar Categoria Recursos
 * 
 * Clases de Equivalencia
 * CE1: se registra y consulta adecuadamente unos recursos dependiendo de su categoria (2)
 * CE2: se registran los recursos y se consulta adecuadamente categorias totalmente diferentes (3)
 * 
 * Clases de Frontera
 * CF1: no debe permitir registrar un recurso que ya existe (1)
 * 
 * 
 * ----------------------------------------------------------------------------------------------------
 * Registrar y Consultar disponibilidad de nuevos recursos
 * 
 * Clases de Equivalencia
 * CE1: registrar y consultar adecuadamente los recursos en categoria correspondiente y rectificar
 * CE2: registrar y consultar adecuadamente ningun recurso porque no pertenecen a la categoria
 * 
 * Clases de Frontera
 * CF1: no consultar la disponibilidad cuando el recurso no existe
 * 
 * 
 * ----------------------------------------------------------------------------------------------------
 * Registrar Prestamo y Consultar Recursos concedidos para un periodo academico
 * 
 * Clases de Equivalencia:
 * CE1: Recursos concedidos en un periodo diferente al que se consultará y rectificar(0 recursos)
 * CE2: Recursos concedidos para un periodo igual al que se consultará y rectificar (Todos los asignados)
 * 
 * Clases de Frontera:
 * CF1: Un mismo recurso asignado a 2 diferentes clases en diferente horario(2 recursos)
 * CF2: Mismo Recursos asigados a diferentes clases en el mismo horario (1 recursos)
 * 
 * Rectificar : Consultar la disponibilidad de un recurso dentro de un horario
 */
public class RecursosTest {
    
    public RecursosTest() {
    }
    
    @BeforeClass
    public static void setUp() throws ExcepcionServiciosProgmsPost {
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Programa prg1 = new Programa(70,"Gerencia de Proyectos", "Especializacion");
        Asignatura as1 = new Asignatura(70,"Ejecucion");
        sp.registrarPrograma(prg1);
        sp.registrarAsignatura(as1, 70);
    }
    
    //Registrar y Consultar Categoria Recursos
    
    @Test
    public void CE1() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Recurso rc1 = new Recurso(1, "Computadores Portatiles", "Ordenador pequeño personal transportable",48, "EquiposComputo");
        Recurso rc2 = new Recurso(2, "Distanciometro", "Calcula la distancia hasta un punto al que se apunte",13, "InstrumentosMedicion");
        Collection<String> categoriasBef = sp.consultarCategoriasRecursos();
        sp.registrarRecurso(rc1);sp.registrarRecurso(rc2);
        Collection<String> categoriasAft = sp.consultarCategoriasRecursos();
        assertEquals("Se registra o consulta inadecuadamente las categorias de los recursos registrados"
                    + "cuando esta deberia mostrar : "
                    ,categoriasBef.size()!=categoriasAft.size());
    }
    
    @Test
    public void CE2() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Recurso rc1 = new Recurso(3, "Computadores Portatiles", "Ordenador pequeño personal transportable",48, "EquiposComputo");
        Recurso rc2 = new Recurso(4, "Equipo Teleconferencia", "Permite el intercambio de informacion entre maquinas",9, "EquiposComputo");
        Recurso rc3 = new Recurso(5, "Distanciometro", "Calcula la distancia hasta un punto al que se apunte",13, "InstrumentosMedicion");
        Collection<String> categoriasBef = sp.consultarCategoriasRecursos();
        sp.registrarRecurso(rc1);sp.registrarRecurso(rc2);sp.registrarRecurso(rc3);
        Collection<String> categoriasAft = sp.consultarCategoriasRecursos();
        assertEquals("Se registra o consulta inadecuadamente las categorias de los recursos registrados"
                    + "cuando esta deberia mostrar : "
                    ,categoriasBef.size()!=categoriasAft.size());
    }
    
    @Test
    public void CF1() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Recurso rc1 = new Recurso(6, "Computadores Portatiles", "Ordenador pequeño personal transportable",48, "EquiposComputo");
        boolean thrown = false;
        try{
            sp.registrarRecurso(rc1);
            sp.registrarRecurso(rc1);
        } catch(ExcepcionServiciosProgmsPost e) {
            thrown = true;
        }
        assertTrue("Se registra inadecuadamente un recurso existente"
                + ", cuando esta debe lanzar ExcepcionServiciosProgmsPost",thrown);
    }
    
    
    //Registrar y Consultar disponibilidad de nuevos recursos
    
    @Test
    public void CE1Disponibilidad() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Recurso rc1 = new Recurso(7, "Computadores Portatiles", "Ordenador pequeño personal transportable",48, "EquiposComputoApple");
        Recurso rc2 = new Recurso(8, "Distanciometro", "Calcula la distancia hasta un punto al que se apunte",13, "InstrumentosMedicion");
        sp.registrarRecurso(rc1);sp.registrarRecurso(rc2);
        
        Iterator<Recurso> recursosDisp = sp.consultarRecursos("EquiposComputoApple", java.sql.Date.valueOf("2011-04-08"), java.sql.Time.valueOf("07:00:00"), java.sql.Time.valueOf("10:00:00")).iterator();
        assertEquals("Se registra o consulta inadecuadamente la disponibilidad de recursos para una fecha de la Categoria "
                    + "EquiposComputo, cuando esta deberia mostrar el recurso : "
                    ,"Computadores Portatiles",recursosDisp.next().getNombre());
        assertEquals("Se registra o consulta inadecuadamente la disponibilidad del recurso Distanciometro para una fecha"
                    + "cuando esta deberia mostrar el recurso : "
                    ,true,sp.consultarDisponibilidadRecurso(7, java.sql.Date.valueOf("2011-04-08"), java.sql.Time.valueOf("07:00:00"), java.sql.Time.valueOf("10:00:00")));
    }
    
    @Test
    public void CE2Disponibilidad() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Recurso rc1 = new Recurso(9, "Computadores Portatiles", "Ordenador pequeño personal transportable",48, "EquiposComputo");
        Recurso rc2 = new Recurso(10, "Distanciometro", "Calcula la distancia hasta un punto al que se apunte",13, "InstrumentosMedicion");
        sp.registrarRecurso(rc1);sp.registrarRecurso(rc2);
        
        Collection<Recurso> recursosDisp = sp.consultarRecursos("Control", java.sql.Date.valueOf("2010-04-08"), java.sql.Time.valueOf("07:00:00"), java.sql.Time.valueOf("10:00:00"));
        assertEquals("Se registra o consulta inadecuadamente la disponibilidad de recursos para una fecha de la Categoria "
                    + "EquiposComputo, cuando esta deberia mostrar el recurso : "
                    ,0,recursosDisp.size());
    }
    
    @Test
    public void CF1Disponibilidad() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();

        boolean thrown = false;
        try{
            sp.consultarDisponibilidadRecurso(500, java.sql.Date.valueOf("2009-04-08"), java.sql.Time.valueOf("07:00:00"), java.sql.Time.valueOf("10:00:00"));
        } catch(ExcepcionServiciosProgmsPost e) {
            thrown = true;
        }
        assertTrue("Se consulta inadecuadamente la disponibilidad de un recurso que no existe"
                + ", cuando esta debe lanzar ExcepcionServiciosProgmsPost",thrown);
    }
    
    //Registrar Prestamo y Consultar Recursos concedidos para un periodo academico
    
    
    @Test
    public void CE1Prestamo() throws ExcepcionServiciosProgmsPost {
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Recurso rc1 = new Recurso(11, "Computadores Portatiles", "Ordenador pequeño personal transportable",48, "EquiposComputo");
        Recurso rc2 = new Recurso(12, "Equipo Teleconferencia", "Permite el intercambio de informacion entre maquinas",9, "EquiposComputo");
        sp.registrarRecurso(rc1);sp.registrarRecurso(rc2);
        
        Clase cl = new Clase(60,java.sql.Date.valueOf("2020-04-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        Materia m1 = new Materia(120,"Gerencia Financiera");

        sp.registrarMateria(m1,70);
        sp.agregarClase(120, cl);
        
        sp.registrarPrestamoRecursoClase(11, cl);
        sp.registrarPrestamoRecursoClase(12, cl);
        Collection<Recurso> rccs = sp.consultarRecursosProgramados(20202);
        assertEquals("Se consulta inadecuadamente la disponibilidad de un recurso"
                    + "cuando este ya esta prestado en ese horario."
                    ,false,sp.consultarDisponibilidadRecurso(11, java.sql.Date.valueOf("2020-04-08"), java.sql.Time.valueOf("07:00:00"), java.sql.Time.valueOf("10:00:00")));
        assertEquals("Se conceden o consulta inadecuadamente los recursos a la clase"
                    + "cuando esta no se da en la fecha indicada."
                    ,0,rccs.size());
    }
    @Test
    public void CE2Prestamo() throws ExcepcionServiciosProgmsPost {
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Recurso rc1 = new Recurso(13, "Computadores Portatiles", "Ordenador pequeño personal transportable",48, "EquiposComputo");
        Recurso rc2 = new Recurso(14, "Equipo Teleconferencia", "Permite el intercambio de informacion entre maquinas",9, "EquiposComputo");
        sp.registrarRecurso(rc1);sp.registrarRecurso(rc2);
        
        Clase cl = new Clase(61,java.sql.Date.valueOf("2021-04-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        Materia m1 = new Materia(121,"Gerencia Financiera");
        sp.registrarMateria(m1,70);
        sp.agregarClase(121, cl);
        
        sp.registrarPrestamoRecursoClase(13, cl);
        sp.registrarPrestamoRecursoClase(14, cl);
        Collection<Recurso> rccs = sp.consultarRecursosProgramados(20211);
        assertEquals("Se consulta inadecuadamente la disponibilidad de un recurso"
                    + "cuando este ya esta disponible en ese horario."
                    ,true,sp.consultarDisponibilidadRecurso(13, java.sql.Date.valueOf("2021-04-08"), java.sql.Time.valueOf("11:00:00"), java.sql.Time.valueOf("11:30:00")));
        assertEquals("Se conceden o consulta inadecuadamente los recursos a la clase"
                    + "cuando esta se debe dar en la fecha indicada."
                    ,2,rccs.size());
    }
    @Test
    public void CF1Prestamo() throws ExcepcionServiciosProgmsPost {
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Recurso rc1 = new Recurso(15, "Computadores Portatiles", "Ordenador pequeño personal transportable",48, "EquiposComputo");
        sp.registrarRecurso(rc1);
        
        Clase cl1 = new Clase(62,java.sql.Date.valueOf("2022-04-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        Clase cl2 = new Clase(63,java.sql.Date.valueOf("2022-04-13"),java.sql.Time.valueOf("08:00:00"),java.sql.Time.valueOf("11:00:00"));
        Materia m1 = new Materia(122,"Gerencia Financiera");
        sp.registrarMateria(m1,70);
        sp.agregarClase(122, cl1);sp.agregarClase(122, cl2);
        
        sp.registrarPrestamoRecursoClase(15, cl1);
        sp.registrarPrestamoRecursoClase(15, cl2);
        Iterator<Recurso> rccs = sp.consultarRecursosProgramados(20221).iterator();
        
        assertEquals("Se conceden o consulta inadecuadamente los recursos a las clases"
                    + "cuando esta se debe dar el mismo recurso igual al numero de clases (2)."
                    ,rccs.next().getId(),rccs.next().getId());
    }
    @Test
    public void CF2Prestamo() {
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Recurso rc1 = new Recurso(16, "Computadores Portatiles", "Ordenador pequeño personal transportable",48, "EquiposComputo");
        Clase cl1 = new Clase(64,java.sql.Date.valueOf("2012-04-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        Clase cl2 = new Clase(65,java.sql.Date.valueOf("2012-04-08"),java.sql.Time.valueOf("08:00:00"),java.sql.Time.valueOf("11:00:00"));
        Materia m1 = new Materia(123,"Gerencia Financiera");
        Materia m2 = new Materia(124,"Analisis de Riesgos");
        boolean thrown = false;
        try {
            sp.registrarRecurso(rc1);
            sp.registrarMateria(m1,70);sp.registrarMateria(m2,70);
            sp.agregarClase(123, cl1);sp.agregarClase(124, cl2);
            sp.registrarPrestamoRecursoClase(16, cl1);
            sp.registrarPrestamoRecursoClase(16, cl2);
        } catch (ExcepcionServiciosProgmsPost ex) {
            thrown = true;
        }
        assertTrue("Se conceden o consulta inadecuadamente los recursos a la clase"
                    + "cuando esta no debe conceder el mismo recurso a la clases que se dan al tiempo.",thrown);
    }
}
