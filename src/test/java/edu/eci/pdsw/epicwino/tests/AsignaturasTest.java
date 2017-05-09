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
        Programa prg1 = new Programa(20,"Gerencia de Proyectos", "Especializacion");
        Asignatura as1 = new Asignatura(30,"Ejecucion");
        Materia m1 = new Materia(50,"Gerencia Financiera");
        
        List<GrupoDeMateria> gruposMateria = new ArrayList<>();
        GrupoDeMateria grupo1 = new GrupoDeMateria();
        grupo1.setPeriodo(20171);gruposMateria.add(grupo1);
        m1.setGruposDeMateria(gruposMateria);
        
        sp.registrarPrograma(prg1);
        sp.registrarAsignatura(as1, 20);
        sp.registrarMateria(m1, 30);
        
        Iterator<Asignatura> asigPeriodo = sp.consultarAsignaturas(20171, 20).iterator();
        assertEquals("Se registra o consulta inadecuadamente la asignatura para el periodo 2017-1"
                    + "cuando esta se debe mostrar la asignatura : "
                    ,"Ejecucion",asigPeriodo.next().getNombre());
    }
    
    @Test
    public void CE2() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Programa prg1 = new Programa(21,"Gerencia de Proyectos", "Especializacion");
        Asignatura as1 = new Asignatura(31,"Ejecucion");
        Materia m1 = new Materia(51,"Gerencia Financiera");
        
        sp.registrarPrograma(prg1);
        sp.registrarAsignatura(as1, 21);
        sp.registrarMateria(m1, 31);
        
        Collection<Asignatura> asigPeriodo = sp.consultarAsignaturas(20181, 21);
        assertEquals("Se registra o consulta inadecuadamente las asignaturas para el periodo 2018-1"
                    + "cuando esta no debe mostrar ninguna asignatura : "
                    ,0,asigPeriodo.size());
    }
    
    @Test
    public void CE3() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Programa prg1 = new Programa(22,"Gerencia de Proyectos", "Especializacion");
        Programa prg2 = new Programa(23,"Gerencia de Produccion Industrial", "Especializacion");
        Asignatura as1 = new Asignatura(32,"Ejecucion");
        Asignatura as2 = new Asignatura(33,"Operacion");
        Materia m1 = new Materia(52,"Gerencia Financiera");
        
        sp.registrarPrograma(prg1);sp.registrarPrograma(prg2);
        sp.registrarAsignatura(as1, 22);sp.registrarAsignatura(as2, 23);
        sp.registrarMateria(m1, 32);sp.registrarMateria(m1, 33);
        
        Collection<Asignatura> asigMateria = sp.consultarAsignaturas(52);
        assertEquals("Se registra o consulta inadecuadamente las asignaturas relacionadas a una materias"
                    + "cuando esta se debe mostrar las asignaturas : "
                    ,2,asigMateria.size());
    }
    
    @Test
    public void CF1(){
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Programa prg1 = new Programa(24,"Gerencia de Proyectos", "Especializacion");
        Asignatura as1 = new Asignatura(34,"Ejecucion");
        
        boolean thrown = false;
        try{
            sp.registrarPrograma(prg1);
            sp.registrarAsignatura(as1, 24);
            sp.registrarAsignatura(as1, 24);
        } catch(ExcepcionServiciosProgmsPost e) {
            thrown = true;
        }
        assertTrue("Se registra inadecuadamente una asignatura existente"
                + ", cuando esta debe lanzar ExcepcionServiciosProgmsPost",thrown);
    }
    
    @Test
    public void CF2(){
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Programa prg1 = new Programa(25,"Gerencia de Proyectos", "Especializacion");
        Programa prg2 = new Programa(26,"Gerencia de Produccion Industrial", "Especializacion");
        Asignatura as1 = new Asignatura(35,"Ejecucion");
        
        boolean thrown = false;
        try{
            sp.registrarPrograma(prg1);sp.registrarPrograma(prg2);
            sp.registrarAsignatura(as1, 25);
            sp.registrarAsignatura(as1, 26);
        } catch(ExcepcionServiciosProgmsPost e) {
            thrown = true;
        }
        assertTrue("Se registra inadecuadamente una asignatura a dos programas distintos"
                + ", cuando esta debe lanzar ExcepcionServiciosProgmsPost",thrown);
    }
    
    @Test
    public void CF3(){
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Programa prg1 = new Programa(27,"Gerencia de Proyectos", "Especializacion");
        Asignatura as1 = new Asignatura(36,"Ejecucion");
        
        boolean thrown = false;
        try{
            sp.registrarPrograma(prg1);
            sp.registrarAsignatura(as1, 27);
            sp.consultarAsignaturas(-2, 27);
        } catch(ExcepcionServiciosProgmsPost e) {
            thrown = true;
        }
        assertTrue("Se consulta inadecuadamente una asignatura con un periodo inconsistente"
                + ", cuando esta debe lanzar ExcepcionServiciosProgmsPost",thrown);
    }
    
    @Test
    public void CF4(){
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Programa prg1 = new Programa(28,"Gerencia de Proyectos", "Especializacion");
        Asignatura as1 = new Asignatura(37,"Ejecucion");
        Materia m1 = new Materia(53,"Gerencia Financiera");
        
        boolean thrown = false;
        try{
            sp.registrarPrograma(prg1);
            sp.registrarAsignatura(as1, 28);
            sp.consultarAsignaturas(53);
        } catch(ExcepcionServiciosProgmsPost e) {
            thrown = true;
        }
        assertTrue("Se consulta inadecuadamente una asignatura con un periodo inconsistente"
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
