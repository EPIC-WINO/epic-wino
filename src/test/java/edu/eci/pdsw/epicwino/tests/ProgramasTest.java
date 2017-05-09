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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Registrar y Consultar los programas que se ofrecen en un periodo o una asignatura
 * 
 * Clases de Equivalencia
 * CE1: registrar adecuadamente varios programas y consultarlos para un periodo (2)
 * CE2: registrar adecuadamente un programa y consultarlo para asignaturas que tiene inscritas (1)
 * 
 * Clases de Frontera
 * CF1: no registrar un programa que ya existie (1)
 * CF2: no consultar un programa para un periodo negativo, registrado
 */
public class ProgramasTest {

    public ProgramasTest() {
    }
    
    @BeforeClass
    public static void setUp() {
    }
    /*
    @Test
    public void CE1(){}
    
    @Test
    public void CE2(){}
    
    @Test
    public void CF1(){}
    
    @Test
    public void CF2(){}
    */
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
