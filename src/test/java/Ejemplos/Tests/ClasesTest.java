/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ejemplos.Tests;

import edu.eci.pdsw.epicwino.logica.entidades.Clase;
import edu.eci.pdsw.epicwino.logica.entidades.Materia;
import edu.eci.pdsw.epicwino.logica.servicios.ExcepcionServiciosProgmsPost;
import edu.eci.pdsw.epicwino.logica.servicios.ServiciosProgmsPost;
import edu.eci.pdsw.epicwino.logica.servicios.ServiciosProgmsPostFactory;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

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
 */
public class ClasesTest {

    public ClasesTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void CE1() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        Materia m1 = new Materia(50,"Gerencia Financiera");
        sp.registrarMateria(m1);
        
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
        sp.registrarMateria(m1);sp.registrarMateria(m2);
        
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
            sp.agregarClase(54, cl1);
            sp.agregarClase(54, cl2);
        } catch (ExcepcionServiciosProgmsPost ex) {
            thrown = true;
        }
        assertTrue("Se agrega inadecuadamente clases que se cruzan a una materia"
                + ", cuando esta debe lanzar ExcepcionServiciosProgmsPost",thrown); 
    }
}
