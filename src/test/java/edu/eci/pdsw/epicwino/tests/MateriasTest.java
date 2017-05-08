/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.epicwino.tests;

import edu.eci.pdsw.epicwino.logica.entidades.Materia;
import edu.eci.pdsw.epicwino.logica.entidades.GrupoDeMateria;
import edu.eci.pdsw.epicwino.logica.entidades.Asignatura;
import edu.eci.pdsw.epicwino.logica.entidades.Programa;
import edu.eci.pdsw.epicwino.logica.servicios.ExcepcionServiciosProgmsPost;
import edu.eci.pdsw.epicwino.logica.servicios.ServiciosProgmsPost;
import edu.eci.pdsw.epicwino.logica.servicios.ServiciosProgmsPostFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Registrar y consultar materias nuevas
 * 
 * Clases de Equivalencia
 * CE1: registrar adecuadamente varias materias que no existian (2)
 * CE2: registrar adecuadamente una materias para un periodo que no existian(1)
 * CE3: registrar adecuadamente una materia para un periodo relacionada a una asignatura (1)
 * 
 * Clases de Frontera
 * CF1: no registrar una materia que ya existie (1)
 * 
 * ------------------------------------------------------------------------------------
 * Consultar Prerrequisitos y Correquisitos de una materia
 * 
 * Clases de Equivalencia
 * CE1: Registrar y Consultar adecuadamente las materias registradas como prerrequisitos de una materia (2)
 * CE2: Registrar y Consultar adecuadamente las materias registradas como Correquisitos de una materia (2)
 * CE3: Registrar adecuadamente una materia como correquisito/prerrequisito a dos materias distintas (1)
 * 
 * Clases de Frontera
 * CF1: No registrar una misma materia como correquisito y prerrequisito a otra materia (0)
 * CF2: no consultar los prerrequisitos y correquisitos a una materia que no existe (0)
 * 
 * ------------------------------------------------------------------------------------
 * Agregar y consultar un cohorte, agregando por programa y materia, consultando por materia y periodo 
 * 
 * Clases de Equivalencia
 * CE1: Se registra adecuadamente un cohorte a una materia nueva relacionada con un programa (1)
 * CE2: Se consulta adecuadamente los cohortes de una materia para un periodo academico (2)
 * CE3: Se registra adecuadamente un cohorte a una materia que ya tiene un cohorte, pero en otro programa (1)
 * 
 * Clases de Frontera
 * CF1: No se registra un cohorte a una materia que no pertenece al programa (0)
 * CF2: No consultar los cohortes de una materia cuando esta no se desarrolla en el periodo academico (0)
 * CF3: No se registra un cohorte a una materia con un programa, cuando este ya esta vinculado a la misma (0)
 * 
 */
public class MateriasTest {

    public MateriasTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    //Registrar y consultar materias nuevas
    
    @Test
    public void CE1() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Materia m1 = new Materia(50,"Gerencia Financiera");
        Materia m2 = new Materia(51,"Analisis de Riesgos");
        Programa prg1 = new Programa(20,"Gerencia de Proyectos");
        Asignatura as1 = new Asignatura(30,"Ejecucion");

        sp.registrarPrograma(prg1);
        sp.registrarAsignatura(as1, 20);
        sp.registrarMateria(m1,30);sp.registrarMateria(m2,30);
        
        Iterator<Materia> matPorPeriodo = sp.consultarMaterias().iterator();
        assertEquals("Se registra o consulta inadecuadamente las materias creadas"
                    + "cuando esta se debe mostrar las dos materias : "
                    ,"Gerencia Financiera,Analisis de Riesgos",matPorPeriodo.next().getNombre()+","+matPorPeriodo.next().getNombre());
    }
    
    @Test
    public void CE2() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Materia m1 = new Materia(52,"Gerencia Financiera");
        
        List<GrupoDeMateria> gruposMateria = new ArrayList<>();
        GrupoDeMateria grupo1 = new GrupoDeMateria();
        grupo1.setPeriodo(20171);gruposMateria.add(grupo1);
        m1.setGruposDeMateria(gruposMateria);

        sp.registrarMateria(m1,30);
        Iterator<Materia> matPorPeriodo = sp.consultarMaterias(20171).iterator();
        
        assertEquals("Se registra o consulta inadecuadamente la materia para el periodo 2017-1"
                    + "cuando esta se debe mostrar la materia : "
                    ,"Gerencia Financiera",matPorPeriodo.next().getNombre());
    }

    @Test
    public void CE3() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Materia m1 = new Materia(53,"Gerencia Financiera");
        
        List<GrupoDeMateria> gruposMateria = new ArrayList<>();
        GrupoDeMateria grupo1 = new GrupoDeMateria();
        grupo1.setPeriodo(20172);gruposMateria.add(grupo1);
        m1.setGruposDeMateria(gruposMateria);
        
        sp.registrarMateria(m1,30);
        Iterator<Materia> matPorPeriodo = sp.consultarMaterias(20172,40).iterator();
        
        assertEquals("Se registra o consulta inadecuadamente la materia para el periodo 2017-1 asignada a una Asignatura"
                    + "cuando esta se debe mostrar la materia : "
                    ,"Gerencia Financiera",matPorPeriodo.next().getNombre());
    }
    
    @Test
    public void CF1(){
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Materia m1 = new Materia(54,"Gerencia Financiera");
        
        boolean thrown = false;
        try{
            sp.registrarMateria(m1,30);
            sp.registrarMateria(m1,30);
        } catch(ExcepcionServiciosProgmsPost e) {
            thrown = true;
        }
        assertTrue("Se registra inadecuadamente una materia existente"
                + ", cuando esta debe lanzar ExcepcionServiciosProgmsPost",thrown);
    }
    
    //Consultar Prerrequisitos y Correquisitos de una materia
    
    @Test
    public void CE1CoYRe(){}
    
    @Test
    public void CE2CoYRe(){}
    
    @Test
    public void CE3CoYRe(){}
    
    @Test
    public void CF1CoYRe(){}
    
    @Test
    public void CF2CoYRe(){}
    
    //Agregar y consultar un cohorte, agregando por programa y materia, consultando por materia y periodo
    
    @Test
    public void CE1Cohorte(){}
    
    @Test
    public void CE2Cohorte(){}
    
    @Test
    public void CE3Cohorte(){}
    
    @Test
    public void CF1Cohorte(){}
    
    @Test
    public void CF2Cohorte(){}
    
    @Test
    public void CF3Cohorte(){}
    
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
