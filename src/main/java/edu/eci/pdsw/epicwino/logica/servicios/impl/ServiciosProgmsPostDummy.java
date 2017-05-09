package edu.eci.pdsw.epicwino.logica.servicios.impl;

import edu.eci.pdsw.epicwino.logica.entidades.Asignatura;
import edu.eci.pdsw.epicwino.logica.entidades.Clase;
import edu.eci.pdsw.epicwino.logica.entidades.Materia;
import edu.eci.pdsw.epicwino.logica.entidades.Profesor;
import edu.eci.pdsw.epicwino.logica.entidades.Programa;
import edu.eci.pdsw.epicwino.logica.entidades.Recurso;
import edu.eci.pdsw.epicwino.logica.servicios.ExcepcionServiciosProgmsPost;
import edu.eci.pdsw.epicwino.logica.servicios.ServiciosProgmsPost;
import java.sql.Date;
import java.sql.Time;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 */
public final class ServiciosProgmsPostDummy implements ServiciosProgmsPost {
    
    public static Logger LOGGER  = Logger.getLogger(ServiciosProgmsPostDummy.class);

    private List<Programa> programas;
    private List<Recurso> recursos;
    private List<Clase> clases;
    
    public ServiciosProgmsPostDummy() {
        programas = new ArrayList<>();
        recursos = new ArrayList<>();
        clases = new ArrayList<>();
        poblar();
    }
    
    public void poblar() {
        Asignatura a;
        Materia m;
        
        Programa p1=new Programa(0,"Especializacion en gerencia de proyectos", "Especializacion");
        Programa p2=new Programa(1,"Maestria en gestion de informacion", "Maestria");
        
        List<Asignatura> asignaturas1=new ArrayList<>();
        List<Asignatura> asignaturas2=new ArrayList<>();
        
        List<Materia> materias11=new ArrayList<>();
        List<Materia> materias12=new ArrayList<>();
        List<Materia> materias21=new ArrayList<>();
        List<Materia> materias22=new ArrayList<>();
        
        a=new Asignatura(0,"Fundamentos de gerencia de proyectos");
        
        m=new Materia(0,"Adminitrar un proyecto");
        materias11.add(m);
        m=new Materia(1,"Fundamentos de gerencia");
        materias11.add(m);
        m=new Materia(2,"Tipos de proyectos");
        materias11.add(m);
        
        a.setMaterias(materias11);
        asignaturas1.add(a);
        
        a=new Asignatura(1,"Teorias gerenciales");
        
        m=new Materia(3,"Teoria de la gerencia");
        materias12.add(m);
        m=new Materia(4,"Teoria de la organizacion");
        materias12.add(m);
        m=new Materia(5,"Teoria administrativa");
        materias12.add(m);
        
        a.setMaterias(materias12);
        asignaturas1.add(a);
        
        p1.setAsignaturas(asignaturas1);
        
        a=new Asignatura(2,"Fundamentos de la informacion");
        
        m=new Materia(6,"Organizar informacion");
        materias21.add(m);
        m=new Materia(7,"Informacion en proyectos");
        materias21.add(m);
        m=new Materia(8,"Teorias de la informacion");
        materias21.add(m);
        
        a.setMaterias(materias21);
        asignaturas2.add(a);
        
        a=new Asignatura(3,"Organizacion gerencial");
        asignaturas2.add(a);
        
        m=new Materia(9,"Teorias de la Organizacion");
        materias22.add(m);
        m=new Materia(10,"La gerencia y las organizaciones");
        materias22.add(m);
        m=new Materia(11,"Administracion organizacional");
        materias22.add(m);
        
        a.setMaterias(materias22);
        asignaturas2.add(a);
       
        p2.setAsignaturas(asignaturas2);
        
        p1.setNivel("Especializacion");
        p2.setNivel("Maestria");
        
        //Crea recursos-------------------------
        Recurso r1=new Recurso(1,"Computadores","Computadores dell para uso de las clases",5,"Multimedia");
        Recurso r2=new Recurso(2,"Video Beam","Video Beam para usar en clases",6,"Multimedia");
        Recurso r3=new Recurso(3,"Salones de computo","Salones con 20 computadores para uso de estudiantes",3,"Multimedia");
        recursos.add(r1);
        recursos.add(r2);
        recursos.add(r3);
        //--------------------------------------
        //Crea clases---------------------------
        Clase cla1 = new Clase(1, new Date(2017-1900, 2, 15), new Time(11, 30, 0), new Time(1, 0, 0));
        Clase cla2 = new Clase(2, new Date(2017-1900, 3, 5), new Time(8, 30, 0), new Time(10, 0, 0));
        Clase cla3 = new Clase(3, new Date(2017-1900, 3, 29), new Time(1, 0, 0), new Time(11, 30, 0));
        List<Recurso> recurs1 = new ArrayList<Recurso>();
        recurs1.add(r1);
        cla1.setRecursos((ArrayList<Recurso>) recurs1);
        
        List<Recurso> recurs2 = new ArrayList<Recurso>();
        recurs2.add(r2);
        cla2.setRecursos((ArrayList<Recurso>) recurs2);
        
        List<Recurso> recurs3 = new ArrayList<Recurso>();
        recurs3.add(r3);
        cla3.setRecursos((ArrayList<Recurso>) recurs3);
        
        clases.add(cla1);
        clases.add(cla2);
        clases.add(cla3);
        //--------------------------------------
        programas.add(p1);
        programas.add(p2);
     
    }

    @Override
    public void registrarMateria(Materia materia, int idAsignatura) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Programa> consultarProgramas(int periodo) throws ExcepcionServiciosProgmsPost {
        return programas;
    }

    @Override
    public List<Materia> consultarMaterias(int periodo) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Profesor> consultarProfesores(int periodo) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Asignatura> consultarAsignaturas(int periodo) throws ExcepcionServiciosProgmsPost {
        List<Asignatura> asignaturas=new ArrayList();
        Asignatura a0=new Asignatura(0,"Fundamentos de gerencia de proyectos");
        Asignatura a1=new Asignatura(1,"Teorias gerenciales");
        Asignatura a2=new Asignatura(2,"Fundamentos de la informacion");
        asignaturas.add(a0);
        asignaturas.add(a1);
        asignaturas.add(a2);
        LOGGER.debug(MessageFormat.format("Se retorna lista de asignaturas {0}", asignaturas));
        return asignaturas;
    }

    @Override
    public List<Integer> consultarPeriodos() {
        List<Integer> a = new ArrayList<>();
        a.add(20171);
        a.add(20172);
        a.add(20181);
        return a;
    }

    @Override
    public void registrarRecurso(Recurso recurso) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void agregarClase(int idMateria, Clase clase) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Materia> consultarMaterias() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Materia> consultarMaterias(int periodo, int idAsignatura) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Recurso> consultarRecursos(String nombreCategoria, Date fecha, Time horaInicio, Time horaFin) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean consultarDisponibilidadRecurso(int idRecurso, Date fecha, Time horaInicio, Time horaFin) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean consultarDisponibilidadProfesor(int idProfesor, Date fecha, Time horaInicio, Time horaFin) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Asignatura> consultarAsignaturas(int periodo, int idPrograma) throws ExcepcionServiciosProgmsPost {
        List<Asignatura> asignaturas = new ArrayList<>();
        Asignatura a0=new Asignatura(0,"Fundamentos de gerencia de proyectos");
        Asignatura a1=new Asignatura(1,"Teorias gerenciales");
        Asignatura a2=new Asignatura(2,"Fundamentos de la informacion");
        asignaturas.add(a0);
        asignaturas.add(a1);
        asignaturas.add(a2);
        LOGGER.debug(MessageFormat.format("Se retorna lista de asignaturas {0}", asignaturas));
        return asignaturas;
    }

    @Override
    public void registrarAsignatura(Asignatura asignatura, int idPrograma) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Materia> consultarPrerrequisitos(int idMateria) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Materia> consultarCorrequisitos(int idMateria) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Integer> consultarPeriodos(int idPrograma) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Clase> consultarClases(int periodo, int idMateria) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> consultarCategoriasRecursos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Asignatura, Integer> consultarCohortesPorAsignatura(int idMateria, int periodo) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Programa consultarPrograma(int idAsignatura) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void agregarCohorte(int idPrograma, int idMateria, int numCohorte) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Profesor consultarProfesor(int periodo, int idMateria) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registrarPrestamoRecursoClase(int idRecurso, Clase clase) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Recurso> consultarRecursosProgramados(int periodo) throws ExcepcionServiciosProgmsPost {
        return recursos; 
    }

    @Override
    public int consultarCohorte(int idMateria, int idAsignatura, int periodo) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> consultarNiveles() {
        List<String> n=new ArrayList();
        n.add("Especializacion");
        n.add("Maestria");
        return n;
    }

    @Override
    public void registrarPrograma(Programa programa) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Clase> consultarClasesDeUnPeriodo(int periodo) throws ExcepcionServiciosProgmsPost {
        return clases;
    }
    
}
