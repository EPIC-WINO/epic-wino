/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ejemplos.Tests;

import org.junit.Before;
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
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void CE1(){}
    
    @Test
    public void CE2(){}
    
    @Test
    public void CF1(){}
    
    @Test
    public void CF2(){}
}
