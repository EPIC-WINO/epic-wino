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
        Asignatura as1 = new Asignatura(30,"Ejecucion");
        sp.registrarPrograma(prg1);
        sp.registrarAsignatura(as1, 20);
    }
    
    @Test
    public void CE1() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Materia m1 = new Materia(50,"Gerencia Financiera");

        sp.registrarMateria(m1,30);
        
        Clase cl1 = new Clase(40,java.sql.Date.valueOf("2015-04-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        Clase cl2 = new Clase(41,java.sql.Date.valueOf("2015-04-13"),java.sql.Time.valueOf("08:00:00"),java.sql.Time.valueOf("11:00:00"));
        
        sp.agregarClase(50, cl1);
        sp.agregarClase(50, cl2);
        
        Collection<Clase> clasePorMat = sp.consultarClases(20151, 50);
        assertEquals("Se agrega o consulta inadecuadamente las clases a una materia"
                    + "cuando esta se debe mostrar : "
                    ,2,clasePorMat.size());
    }
    
    @Test
    public void CE2() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Materia m1 = new Materia(51,"Gerencia Financiera");
        Materia m2 = new Materia(52,"Analisis de Riesgos");
        sp.registrarMateria(m1,30);sp.registrarMateria(m2,30);
        
        Clase cl1 = new Clase(42,java.sql.Date.valueOf("2015-04-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
                
        sp.agregarClase(51, cl1);
        sp.agregarClase(52, cl1);
        
        Collection<Clase> clasePorMat = sp.consultarClases(20151, 51);
        assertEquals("Se agrega o consulta inadecuadamente la clase a dos materias"
                    + "cuando esta se debe mostrar : "
                    ,1,clasePorMat.size());
        clasePorMat = sp.consultarClases(20151, 52);
        assertEquals("Se agrega o consulta inadecuadamente laa clasea a dos materias"
                    + "cuando esta se debe mostrar : "
                    ,1,clasePorMat.size());
    }
    
    @Test
    public void CF1(){
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Clase cl1 = new Clase(43,java.sql.Date.valueOf("2015-04-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        
        boolean thrown = false;
        try {
            sp.agregarClase(80, cl1);
        } catch (ExcepcionServiciosProgmsPost ex) {
            thrown = true;
        }
        assertTrue("Se agrega inadecuadamente una clase a una materia que no existe"
                + ", cuando esta debe lanzar ExcepcionServiciosProgmsPost",thrown);        
    }
    
    @Test
    public void CF2(){
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Materia m1 = new Materia(53,"Gerencia Financiera");
        Clase cl1 = new Clase(44,java.sql.Date.valueOf("2015-04-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        
        boolean thrown = false;
        try {
            sp.registrarMateria(m1,30);
            sp.agregarClase(53, cl1);
            sp.agregarClase(53, cl1);
        } catch (ExcepcionServiciosProgmsPost ex) {
            thrown = true;
        }
        assertTrue("Se agrega inadecuadamente una clase dos veces a una materia"
                + ", cuando esta debe lanzar ExcepcionServiciosProgmsPost",thrown); 
    }
    
    @Test
    public void CF3(){
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Materia m1 = new Materia(54,"Gerencia Financiera");
        Clase cl1 = new Clase(45,java.sql.Date.valueOf("2015-04-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        Clase cl2 = new Clase(46,java.sql.Date.valueOf("2015-04-08"),java.sql.Time.valueOf("08:00:00"),java.sql.Time.valueOf("11:00:00"));
        boolean thrown = false;
        try {
            sp.registrarMateria(m1,30);
            sp.agregarClase(54, cl1);
            sp.agregarClase(54, cl2);
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
        Materia m1 = new Materia(55,"Gerencia Financiera");

        sp.registrarMateria(m1,30);
        
        Clase cl1 = new Clase(47,java.sql.Date.valueOf("2017-04-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        Clase cl2 = new Clase(48,java.sql.Date.valueOf("2017-04-13"),java.sql.Time.valueOf("08:00:00"),java.sql.Time.valueOf("11:00:00"));
        
        sp.agregarClase(55, cl1);
        sp.agregarClase(55, cl2);
        
        Collection<Clase> clasePorPeriodo = sp.consultarClasesDeUnPeriodo(20171);
        assertEquals("Se consulta inadecuadamente las clases asociadas a un periodo academico"
                    + "cuando esta se debe mostrar : "
                    ,2,clasePorPeriodo.size());
    }
    
    @Test
    public void CE2Periodo() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Materia m1 = new Materia(56,"Gerencia Financiera");

        sp.registrarMateria(m1,30);
        
        Clase cl1 = new Clase(49,java.sql.Date.valueOf("2018-03-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        Clase cl2 = new Clase(50,java.sql.Date.valueOf("2018-08-13"),java.sql.Time.valueOf("08:00:00"),java.sql.Time.valueOf("11:00:00"));
        
        sp.agregarClase(56, cl1);
        sp.agregarClase(56, cl2);
        
        Collection<Clase> clasePorPeriodo = sp.consultarClasesDeUnPeriodo(20181);
        assertEquals("Se consulta inadecuadamente las clase asociadas a un periodo academico"
                    + "cuando esta se debe mostrar : "
                    ,1,clasePorPeriodo.size());
    }
    
    @Test
    public void CF1Periodo(){
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Materia m1 = new Materia(57,"Gerencia Financiera");
        Clase cl1 = new Clase(51,java.sql.Date.valueOf("2019-03-08"),java.sql.Time.valueOf("07:00:00"),java.sql.Time.valueOf("10:00:00"));
        Clase cl2 = new Clase(52,java.sql.Date.valueOf("2019-08-08"),java.sql.Time.valueOf("08:00:00"),java.sql.Time.valueOf("11:00:00"));
        boolean thrown = false;
        try {
            sp.registrarMateria(m1,30);
            sp.agregarClase(57, cl1);
            sp.agregarClase(57, cl2);
            sp.consultarClasesDeUnPeriodo(20201);
        } catch (ExcepcionServiciosProgmsPost ex) {
            thrown = true;
        }
        assertTrue("Se consulta inadecuadamente las clases para un periodo que no estan vinculadas"
                + ", cuando esta debe lanzar ExcepcionServiciosProgmsPost",thrown); 
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
