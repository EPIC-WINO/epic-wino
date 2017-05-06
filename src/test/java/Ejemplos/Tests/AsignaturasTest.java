/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ejemplos.Tests;

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
}
