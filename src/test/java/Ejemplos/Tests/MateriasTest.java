/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ejemplos.Tests;

import Logica.Entidades.Materia;
import Logica.Servicios.ExcepcionServiciosProgmsPost;
import Logica.Servicios.ServiciosProgmsPost;
import Logica.Servicios.ServiciosProgmsPostFactory;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Registrar materias nuevas
 * 
 * Clases de Equivalencia
 * CE1: registrar adecuadamente varias materias que no existian (2)
 * CE2: no registrar una materia que ya existie (1)
 * 
 * Clases de Frontera
 * CF1: 
 */
public class MateriasTest {

    public MateriasTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void CE1() throws ExcepcionServiciosProgmsPost{
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Materia m1 = new Materia(50,"Gerencia Financiera");
        Materia m2 = new Materia(51,"Analisis de Riesgos");
        m1.setPeriodo(20171);m1.setPeriodo(20171);
        sp.registrarMateria(m1);sp.registrarMateria(m2);
        
        Iterator<Materia> matPorPeriodo = sp.consultarMaterias(20171).iterator();
        assertEquals("Se registra o consulta inadecuadamente las materias para el periodo 2017-1"
                    + "cuando esta se debe mostrar las dos materias : "
                    ,"Gerencia Financiera,Analisis de Riesgos",matPorPeriodo.next().getNombre()+","+matPorPeriodo.next().getNombre());
    }
    
    @Test
    public void CE2(){
        ServiciosProgmsPost sp = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPostTesting();
        
        Materia m1 = new Materia(50,"Gerencia Financiera");
        m1.setPeriodo(20171);m1.setPeriodo(20171);
        
        boolean thrown = false;
        try{
            sp.registrarMateria(m1);
            sp.registrarMateria(m1);
        } catch(ExcepcionServiciosProgmsPost e) {
            thrown = true;
        }
        assertTrue("Se registra inadecuadamente una materia existente"
                + ", cuando esta debe lanzar ExcepcionServiciosProgmsPost",thrown);
    }
    
}
