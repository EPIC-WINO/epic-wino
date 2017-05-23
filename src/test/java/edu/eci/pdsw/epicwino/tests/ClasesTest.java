/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.epicwino.tests;

import edu.eci.pdsw.epicwino.logica.entidades.Clase;
import edu.eci.pdsw.epicwino.logica.entidades.Materia;
import edu.eci.pdsw.epicwino.logica.entidades.Asignatura;
import edu.eci.pdsw.epicwino.logica.entidades.Programa;
import edu.eci.pdsw.epicwino.logica.servicios.ExcepcionServiciosProgmsPost;
import edu.eci.pdsw.epicwino.logica.servicios.ServiciosProgmsPost;
import edu.eci.pdsw.epicwino.logica.servicios.ServiciosProgmsPostFactory;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.h2.jdbcx.JdbcDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import org.junit.AfterClass;
import org.junit.BeforeClass;


/**
 * Agregar y consultar Clases a una materia existente
 * 
 * Clases de Equivalencia
 * CE1: agregar satisfactoriamente varias clases a una materia (2)
 * CE2: agregar satisfactoriamente una misma clase a dos materias distintas (1)
 * 
 * Clases de Frontera
 * CF1: intentar agregar una clase a una materia inexistente (1)
 * CF2: intentar agregar la misma clase dos veces a una materia (1)
 * CF3: intentar agregar dos clases que se cruzan en horario a una materia (2)
 * 
 * 
 * Agregar y Consultar Clases que se daran en un periodo academico
 * 
 * Clases de Equivalencia
 * CE1: vincular satisfactoriamente varias clases y consultar que todas se dictan en el mismo periodo (2)
 * CE2: agregar satisfactoriamente las clases y consultar que solo una se da en el periodo(1)
 * 
 * Clases de Frontera
 * CF1: intentar consultar clases para un periodo que no existe (0)
 */
public class ClasesTest {

    public ClasesTest() {
    }
    
    @BeforeClass
    public static void setUp() throws ExcepcionServiciosProgmsPost {
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Programa prg1 = new Programa(20,"Gerencia de Proyectos", "Especializacion");
        Asignatura as1 = new Asignatura(20,"Ejecucion");
        sp.registrarPrograma(prg1);
        sp.registrarAsignatura(as1, 20);
    }
    
    @Test
    public void CE1() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Materia m1 = new Materia(20,"Gerencia Financiera");

        sp.registrarMateria(m1,20);
        
        Clase cl1 = new Clase(1,java.sql.Date.valueOf("2001-04-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        Clase cl2 = new Clase(2,java.sql.Date.valueOf("2001-04-13"),java.sql.Time.valueOf("08:00:00"),java.sql.Time.valueOf("11:00:00"));
        
        sp.agregarClase("20", cl1);
        sp.agregarClase("20", cl2);
        
        Collection<Clase> clasePorMat = sp.consultarClases(20011, "20");
        assertEquals("Se agrega o consulta inadecuadamente las clases a una materia"
                    + "cuando esta se debe mostrar : "
                    ,2,clasePorMat.size());
    }
    
    @Test
    public void CE2() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Materia m1 = new Materia(21,"Gerencia Financiera");
        Materia m2 = new Materia(22,"Analisis de Riesgos");
        sp.registrarMateria(m1,20);sp.registrarMateria(m2,20);
        
        Clase cl1 = new Clase(3,java.sql.Date.valueOf("2001-04-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
                
        sp.agregarClase("21", cl1);
        sp.agregarClase("22", cl1);
        
        Collection<Clase> clasePorMat = sp.consultarClases(20011, "21");
        assertEquals("Se agrega o consulta inadecuadamente la clase a dos materias"
                    + "cuando esta se debe mostrar : "
                    ,1,clasePorMat.size());
        clasePorMat = sp.consultarClases(20011, "22");
        assertEquals("Se agrega o consulta inadecuadamente laa clasea a dos materias"
                    + "cuando esta se debe mostrar : "
                    ,1,clasePorMat.size());
    }
    
    @Test
    public void CF1(){
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Clase cl1 = new Clase(4,java.sql.Date.valueOf("2001-04-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        
        boolean thrown = false;
        try {
            sp.agregarClase("500", cl1);
        } catch (ExcepcionServiciosProgmsPost ex) {
            thrown = true;
        }
        assertTrue("Se agrega inadecuadamente una clase a una materia que no existe"
                + ", cuando esta debe lanzar ExcepcionServiciosProgmsPost",thrown);        
    }
    
    @Test
    public void CF2(){
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Materia m1 = new Materia(23,"Gerencia Financiera");
        Clase cl1 = new Clase(5,java.sql.Date.valueOf("2001-04-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        
        boolean thrown = false;
        try {
            sp.registrarMateria(m1,20);
            sp.agregarClase("23", cl1);
            sp.agregarClase("23", cl1);
        } catch (ExcepcionServiciosProgmsPost ex) {
            thrown = true;
        }
        assertTrue("Se agrega inadecuadamente una clase dos veces a una materia"
                + ", cuando esta debe lanzar ExcepcionServiciosProgmsPost",thrown); 
    }
    
    @Test
    public void CF3(){
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Materia m1 = new Materia(24,"Gerencia Financiera");
        Clase cl1 = new Clase(6,java.sql.Date.valueOf("2001-04-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        Clase cl2 = new Clase(7,java.sql.Date.valueOf("2001-04-08"),java.sql.Time.valueOf("08:00:00"),java.sql.Time.valueOf("11:00:00"));
        boolean thrown = false;
        try {
            sp.registrarMateria(m1,20);
            sp.agregarClase("24", cl1);
            sp.agregarClase("24", cl2);
        } catch (ExcepcionServiciosProgmsPost ex) {
            thrown = true;
        }
        assertTrue("Se agrega inadecuadamente clases que se cruzan a una materia"
                + ", cuando esta debe lanzar ExcepcionServiciosProgmsPost",thrown); 
    }
    
    
    //Registrar y Consultar Clases que se daran en un periodo Academico
    
    @Test
    public void CE1Periodo() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Materia m1 = new Materia(25,"Gerencia Financiera");

        sp.registrarMateria(m1,20);
        
        Clase cl1 = new Clase(8,java.sql.Date.valueOf("2002-04-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        Clase cl2 = new Clase(9,java.sql.Date.valueOf("2002-04-13"),java.sql.Time.valueOf("08:00:00"),java.sql.Time.valueOf("11:00:00"));
        
        sp.agregarClase("25", cl1);
        sp.agregarClase("25", cl2);
        
        Collection<Clase> clasePorPeriodo = sp.consultarClasesDeUnPeriodo(20021);
        assertEquals("Se consulta inadecuadamente las clases asociadas a un periodo academico"
                    + "cuando esta se debe mostrar : "
                    ,2,clasePorPeriodo.size());
    }
    
    @Test
    public void CE2Periodo() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Materia m1 = new Materia(26,"Gerencia Financiera");

        sp.registrarMateria(m1,20);
        
        Clase cl1 = new Clase(10,java.sql.Date.valueOf("2003-03-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        Clase cl2 = new Clase(11,java.sql.Date.valueOf("2003-08-13"),java.sql.Time.valueOf("08:00:00"),java.sql.Time.valueOf("11:00:00"));
        
        sp.agregarClase("26", cl1);
        sp.agregarClase("26", cl2);
        
        Collection<Clase> clasePorPeriodo = sp.consultarClasesDeUnPeriodo(20031);
        assertEquals("Se consulta inadecuadamente las clase asociadas a un periodo academico"
                    + "cuando esta se debe mostrar : "
                    ,1,clasePorPeriodo.size());
    }
    
    @Test
    public void CF1Periodo(){
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Materia m1 = new Materia(27,"Gerencia Financiera");
        Clase cl1 = new Clase(12,java.sql.Date.valueOf("2019-03-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        Clase cl2 = new Clase(13,java.sql.Date.valueOf("2019-08-08"),java.sql.Time.valueOf("08:00:00"),java.sql.Time.valueOf("11:00:00"));
        boolean thrown = false;
        try {
            sp.registrarMateria(m1,20);
            sp.agregarClase("27", cl1);
            sp.agregarClase("27", cl2);
            sp.consultarClasesDeUnPeriodo(19981);
        } catch (ExcepcionServiciosProgmsPost ex) {
            thrown = true;
        }
        assertTrue("Se consulta inadecuadamente las clases para un periodo que no estan vinculadas"
                + ", cuando esta debe lanzar ExcepcionServiciosProgmsPost",thrown); 
    }
}
