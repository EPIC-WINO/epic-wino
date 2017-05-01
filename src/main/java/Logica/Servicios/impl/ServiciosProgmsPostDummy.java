/*
 */
package Logica.Servicios.impl;

import Logica.Entidades.Asignatura;
import Logica.Entidades.Clase;
import Logica.Entidades.Materia;
import Logica.Entidades.Profesor;
import Logica.Entidades.Programa;
import Logica.Entidades.Recurso;
import Logica.Servicios.ExcepcionServiciosProgmsPost;
import Logica.Servicios.ServiciosProgmsPost;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

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
    public void registrarMateria(Materia materia) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Programa> consultarProgramas(int periodo) throws ExcepcionServiciosProgmsPost {
        return programas;
    }

    @Override
    public void agregarClase(Materia materia, Clase clase) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Materia> consultarMaterias(int periodo) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Recurso> consultarRecursos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean consultarDisponibilidadRecurso(Recurso recurso, Date fecha, Time horaInicio, Time horaFin) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Profesor> consultarProfesores(int periodo) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean consultarDisponibilidadProfesor(Profesor profesor, Date fecha, Time horaInicio, Time horaFin) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Asignatura> consultarAsignaturas(int periodo) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registrarAsignatura(Asignatura asignatura) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Materia> consultarPrerrequisitos(Materia materia) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Materia> consultarCorrequisitos(Materia materia) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Integer> consultarPeriodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registrarRecurso(Recurso recurso) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registrarPrestamoRecursoClase(Recurso recurso, Clase clase) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
