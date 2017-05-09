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
        Programa prg1 = new Programa(20,"Gerencia de Proyectos", "Especializacion");
        Asignatura as1 = new Asignatura(30,"Ejecucion");
        sp.registrarPrograma(prg1);
        sp.registrarAsignatura(as1, 20);
    }
    
    //Registrar y Consultar Categoria Recursos
    
    @Test
    public void CE1() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Recurso rc1 = new Recurso(50, "Computadores Portatiles", "Ordenador pequeño personal transportable",48, "EquiposComputo");
        Recurso rc2 = new Recurso(51, "Distanciometro", "Calcula la distancia hasta un punto al que se apunte",13, "InstrumentosMedicion");
        sp.registrarRecurso(rc1);sp.registrarRecurso(rc2);
        Collection<String> categorias = sp.consultarCategoriasRecursos();
        assertEquals("Se registra o consulta inadecuadamente las categorias de los recursos registrados"
                    + "cuando esta deberia mostrar : "
                    ,2,categorias.size());
    }
    
    @Test
    public void CE2() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Recurso rc1 = new Recurso(52, "Computadores Portatiles", "Ordenador pequeño personal transportable",48, "EquiposComputo");
        Recurso rc2 = new Recurso(53, "Equipo Teleconferencia", "Permite el intercambio de informacion entre maquinas",9, "EquiposComputo");
        Recurso rc3 = new Recurso(54, "Distanciometro", "Calcula la distancia hasta un punto al que se apunte",13, "InstrumentosMedicion");
        sp.registrarRecurso(rc1);sp.registrarRecurso(rc2);sp.registrarRecurso(rc3);
        Collection<String> categorias = sp.consultarCategoriasRecursos();
        assertEquals("Se registra o consulta inadecuadamente las categorias de los recursos registrados"
                    + "cuando esta deberia mostrar : "
                    ,2,categorias.size());
    }
    
    @Test
    public void CF1() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Recurso rc1 = new Recurso(55, "Computadores Portatiles", "Ordenador pequeño personal transportable",48, "EquiposComputo");
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
        
        Recurso rc1 = new Recurso(56, "Computadores Portatiles", "Ordenador pequeño personal transportable",48, "EquiposComputo");
        Recurso rc2 = new Recurso(57, "Distanciometro", "Calcula la distancia hasta un punto al que se apunte",13, "InstrumentosMedicion");
        sp.registrarRecurso(rc1);sp.registrarRecurso(rc2);
        
        Iterator<Recurso> recursosDisp = sp.consultarRecursos("EquiposComputo", java.sql.Date.valueOf("2011-04-08"), java.sql.Time.valueOf("07:00:00"), java.sql.Time.valueOf("10:00:00")).iterator();
        assertEquals("Se registra o consulta inadecuadamente la disponibilidad de recursos para una fecha de la Categoria "
                    + "EquiposComputo, cuando esta deberia mostrar el recurso : "
                    ,"Computadores Portatiles",recursosDisp.next().getNombre());
        assertEquals("Se registra o consulta inadecuadamente la disponibilidad del recurso Distanciometro para una fecha"
                    + "cuando esta deberia mostrar el recurso : "
                    ,true,sp.consultarDisponibilidadRecurso(57, java.sql.Date.valueOf("2011-04-08"), java.sql.Time.valueOf("07:00:00"), java.sql.Time.valueOf("10:00:00")));
    }
    
    @Test
    public void CE2Disponibilidad() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Recurso rc1 = new Recurso(58, "Computadores Portatiles", "Ordenador pequeño personal transportable",48, "EquiposComputo");
        Recurso rc2 = new Recurso(59, "Distanciometro", "Calcula la distancia hasta un punto al que se apunte",13, "InstrumentosMedicion");
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
            sp.consultarDisponibilidadRecurso(100, java.sql.Date.valueOf("2009-04-08"), java.sql.Time.valueOf("07:00:00"), java.sql.Time.valueOf("10:00:00"));
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
        
        Recurso rc1 = new Recurso(60, "Computadores Portatiles", "Ordenador pequeño personal transportable",48, "EquiposComputo");
        Recurso rc2 = new Recurso(61, "Equipo Teleconferencia", "Permite el intercambio de informacion entre maquinas",9, "EquiposComputo");
        sp.registrarRecurso(rc1);sp.registrarRecurso(rc2);
        
        Clase cl = new Clase(40,java.sql.Date.valueOf("2015-04-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        Materia m1 = new Materia(50,"Gerencia Financiera");

        sp.registrarMateria(m1,30);
        sp.agregarClase(50, cl);
        
        sp.registrarPrestamoRecursoClase(60, cl);
        sp.registrarPrestamoRecursoClase(61, cl);
        Collection<Recurso> rccs = sp.consultarRecursosProgramados(20152);
        assertEquals("Se consulta inadecuadamente la disponibilidad de un recurso"
                    + "cuando este ya esta prestado en ese horario."
                    ,false,sp.consultarDisponibilidadRecurso(60, java.sql.Date.valueOf("2015-04-08"), java.sql.Time.valueOf("07:00:00"), java.sql.Time.valueOf("10:00:00")));
        assertEquals("Se conceden o consulta inadecuadamente los recursos a la clase"
                    + "cuando esta no se da en la fecha indicada."
                    ,0,rccs.size());
    }
    @Test
    public void CE2Prestamo() throws ExcepcionServiciosProgmsPost {
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Recurso rc1 = new Recurso(62, "Computadores Portatiles", "Ordenador pequeño personal transportable",48, "EquiposComputo");
        Recurso rc2 = new Recurso(63, "Equipo Teleconferencia", "Permite el intercambio de informacion entre maquinas",9, "EquiposComputo");
        sp.registrarRecurso(rc1);sp.registrarRecurso(rc2);
        
        Clase cl = new Clase(41,java.sql.Date.valueOf("2017-04-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        Materia m1 = new Materia(51,"Gerencia Financiera");
        sp.registrarMateria(m1,30);
        sp.agregarClase(51, cl);
        
        sp.registrarPrestamoRecursoClase(62, cl);
        sp.registrarPrestamoRecursoClase(63, cl);
        Collection<Recurso> rccs = sp.consultarRecursosProgramados(20171);
        assertEquals("Se consulta inadecuadamente la disponibilidad de un recurso"
                    + "cuando este ya esta disponible en ese horario."
                    ,true,sp.consultarDisponibilidadRecurso(62, java.sql.Date.valueOf("2017-04-08"), java.sql.Time.valueOf("11:00:00"), java.sql.Time.valueOf("11:30:00")));
        assertEquals("Se conceden o consulta inadecuadamente los recursos a la clase"
                    + "cuando esta se debe dar en la fecha indicada."
                    ,2,rccs.size());
    }
    @Test
    public void CF1Prestamo() throws ExcepcionServiciosProgmsPost {
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Recurso rc1 = new Recurso(64, "Computadores Portatiles", "Ordenador pequeño personal transportable",48, "EquiposComputo");
        sp.registrarRecurso(rc1);
        
        Clase cl1 = new Clase(42,java.sql.Date.valueOf("2016-04-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        Clase cl2 = new Clase(43,java.sql.Date.valueOf("2016-04-13"),java.sql.Time.valueOf("08:00:00"),java.sql.Time.valueOf("11:00:00"));
        Materia m1 = new Materia(52,"Gerencia Financiera");
        sp.registrarMateria(m1,30);
        sp.agregarClase(52, cl1);sp.agregarClase(52, cl2);
        
        sp.registrarPrestamoRecursoClase(64, cl1);
        sp.registrarPrestamoRecursoClase(64, cl2);
        Iterator<Recurso> rccs = sp.consultarRecursosProgramados(20161).iterator();
        
        assertEquals("Se conceden o consulta inadecuadamente los recursos a las clases"
                    + "cuando esta se debe dar el mismo recurso igual al numero de clases (2)."
                    ,rccs.next().getId(),rccs.next().getId());
    }
    @Test
    public void CF2Prestamo() {
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Recurso rc1 = new Recurso(65, "Computadores Portatiles", "Ordenador pequeño personal transportable",48, "EquiposComputo");
        Clase cl1 = new Clase(44,java.sql.Date.valueOf("2012-04-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        Clase cl2 = new Clase(45,java.sql.Date.valueOf("2012-04-08"),java.sql.Time.valueOf("08:00:00"),java.sql.Time.valueOf("11:00:00"));
        Materia m1 = new Materia(53,"Gerencia Financiera");
        Materia m2 = new Materia(54,"Analisis de Riesgos");
        boolean thrown = false;
        try {
            sp.registrarRecurso(rc1);
            sp.registrarMateria(m1,30);sp.registrarMateria(m2,30);
            sp.agregarClase(53, cl1);sp.agregarClase(54, cl2);
            sp.registrarPrestamoRecursoClase(65, cl1);
            sp.registrarPrestamoRecursoClase(65, cl2);
        } catch (ExcepcionServiciosProgmsPost ex) {
            thrown = true;
        }
        assertTrue("Se conceden o consulta inadecuadamente los recursos a la clase"
                    + "cuando esta no debe conceder el mismo recurso a la clases que se dan al tiempo.",thrown);
    }
    
    @AfterClass
    public static void tearDown() {
        JdbcDataSource ds= new JdbcDataSource();
        ds.setURL("jdbc:h2:file:./target/db/testdb;MODE=PostgreSQL");
        ds.setUser("anonymous");
        ds.setPassword("");
        try {
            Connection conn = ds.getConnection();
            Statement s = conn.createStatement();
            s.execute("SET REFERENTIAL_INTEGRITY FALSE");
            Set<String> tables = new HashSet<String>();
            ResultSet rs = s.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA='PUBLIC'");
            while (rs.next()) {
                tables.add(rs.getString(1));
            }
            rs.close();
            for (String table : tables){
                s.executeUpdate("TRUNCATE TABLE " + table);
            }
            s.execute("SET REFERENTIAL_INTEGRITY TRUE");
            s.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
