/*
 */
package Logica.Servicios.impl;

import Logica.Entidades.Asignatura;
import Logica.Entidades.Clase;
import Logica.Entidades.Materia;
import Logica.Entidades.Profesor;
import Logica.Entidades.Programa;
import Logica.Entidades.Recurso;
import Logica.Entidades.RecursoConcedido;
import Logica.Servicios.ExcepcionServiciosProgmsPost;
import Logica.Servicios.ServiciosProgmsPost;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 */
public final class ServiciosProgmsPostDummy implements ServiciosProgmsPost {

    private List<Programa> programas;
    
    public ServiciosProgmsPostDummy() {
        programas = new ArrayList<>();
        poblar();
    }
    
    public void poblar() {
        List<Materia> materias = new ArrayList<>();
        List<Asignatura> asignaturas = new ArrayList<>();
        
        int pro = 1;
        
        Programa p = new Programa(0, "Prueba");
        
        Asignatura a = new Asignatura(0, "TDGP");
        
        Materia m = new Materia(0, "Gerencia de Riesgos");
        m.setProfesor(new Profesor(pro++, "Profesor" + pro));
        materias.add(m);
        m = new Materia(1, "Taller de trabajo de grado");
        m.setProfesor(new Profesor(pro++, "Profesor" + pro));
        materias.add(m);
        m = new Materia(2, "Sustentacion Propuesta");
        m.setProfesor(new Profesor(pro++, "Profesor" + pro));
        materias.add(m);
        
        int c = 15;
        for(Materia ma : materias) {
            ma.setCohorte(c++);
        }
        
        a.setMaterias(materias);
        
        asignaturas.add(a);

        a = new Asignatura(1, "SEMI");
        materias = new ArrayList<>();
        
        m = new Materia(3, "Proyecto de vida");
        m.setProfesor(new Profesor(pro++, "Profesor" + pro));
        materias.add(m);
        m = new Materia(4, "Introduccion a la ejecucion");
        m.setProfesor(new Profesor(pro++, "Profesor" + pro));
        materias.add(m);
        m = new Materia(5, "Construccion");
        m.setProfesor(new Profesor(pro++, "Profesor" + pro));
        materias.add(m);
        
        c = 50;
        for(Materia ma : materias) {
            ma.setCohorte(c++);
        }
        
        
        a.setMaterias(materias);
        
        asignaturas.add(a);
        
        p.setAsignaturas(asignaturas);
        
        programas.add(p);
    }
    
    @Override
    public List<RecursoConcedido> consultarRecursosConcedidos(int anio, int semestre) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registrarClase(Clase c) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registrarRecurso(Recurso rec) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registrarPrestamoClase(int clase, Recurso rec) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Programa> consultarProgramas(int anio, int semestre) throws ExcepcionServiciosProgmsPost {
        return programas;
    }
    
}
