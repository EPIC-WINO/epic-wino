/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.epicwino.tests;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

/**
 * Registrar y consultar asignaturas nuevas dada la materia o el programa y el periodo
 * 
 * Clases de Equivalencia
 * CE1: registrar y consultar adecuadamente varias asignaturas que no existian, a un programa, en el periodo correcto (2)
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
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void CE1(){}
    
    @Test
    public void CE2(){}
    
    @Test
    public void CE3(){}
    
    @Test
    public void CF1(){}
    
    @Test
    public void CF2(){}
    
    @Test
    public void CF3(){}
    
    @Test
    public void CF4(){}
    
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
