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
        
        Materia m1 = new Materia(52,"Gerencia Financiera");
        Programa prg1 = new Programa(20,"Gerencia de Proyectos", "Maestria");
        Asignatura as1 = new Asignatura(30,"Ejecucion");

        sp.registrarPrograma(prg1);
        sp.registrarAsignatura(as1, 20);
        
        List<GrupoDeMateria> gruposMateria = new ArrayList<>();
        GrupoDeMateria grupo1 = new GrupoDeMateria();
        GrupoDeMateria grupo2 = new GrupoDeMateria();
        grupo1.setPeriodo(20171);grupo2.setPeriodo(20172);
        gruposMateria.add(grupo1);gruposMateria.add(grupo2);
        m1.setGruposDeMateria(gruposMateria);
        
        sp.registrarMateria(m1,30);
        Collection<Integer> periodos = sp.consultarPeriodos();
        
        assertEquals("Se consulta inadecuadamente todos los periodos inscritos"
                    + "cuando esta se debe mostrar los periodos : "
                    ,2,periodos.size());
    }
    
    @Test
    public void CE2() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Programa prg1 = new Programa(21,"Gerencia de Proyectos", "Especializacion");
        Asignatura as1 = new Asignatura(31,"Ejecucion");
        Materia m1 = new Materia(51,"Gerencia Financiera");
        
        List<GrupoDeMateria> gruposMateria = new ArrayList<>();
        GrupoDeMateria grupo1 = new GrupoDeMateria();
        grupo1.setPeriodo(20181);gruposMateria.add(grupo1);
        m1.setGruposDeMateria(gruposMateria);
        
        sp.registrarPrograma(prg1);
        sp.registrarAsignatura(as1, 21);
        sp.registrarMateria(m1, 31);
        
        Iterator<Integer> periodos = sp.consultarPeriodos(21).iterator();
        assertEquals("Se consulta inadecuadamente todos los periodos inscritos"
                    + "cuando esta se debe mostrar el periodo : "
                    ,(Integer)20181,periodos.next());
    }
    
    @Test
    public void CE3() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Programa prg1 = new Programa(22,"Gerencia de Proyectos", "Especializacion");
        Asignatura as1 = new Asignatura(32,"Ejecucion");
        Materia m1 = new Materia(52,"Gerencia Financiera");
        Materia m2 = new Materia(53,"Analisis de Riesgos");
        
        List<GrupoDeMateria> gruposMateria1 = new ArrayList<>();
        GrupoDeMateria grupo1 = new GrupoDeMateria();
        grupo1.setPeriodo(20191);gruposMateria1.add(grupo1);
        m1.setGruposDeMateria(gruposMateria1);
        
        List<GrupoDeMateria> gruposMateria2 = new ArrayList<>();
        GrupoDeMateria grupo2 = new GrupoDeMateria();
        grupo2.setPeriodo(20191);gruposMateria2.add(grupo2);
        m2.setGruposDeMateria(gruposMateria2);
        
        sp.registrarPrograma(prg1);
        sp.registrarAsignatura(as1, 22);
        sp.registrarMateria(m1, 32);
        sp.registrarMateria(m2, 32);
        
        Collection<Integer> periodos = sp.consultarPeriodos(22);
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
            periodos = sp.consultarPeriodos(100).iterator();
        } catch (ExcepcionServiciosProgmsPost ex) {
            thrown = true;
        }
        assertTrue("Se consulta inadecuadamente los periodos de un programa inexistente"
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
