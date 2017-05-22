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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author Alejandro Anzola email: alejandro.anzola@mail.escuelaing.edu.co
 */
public final class ServiciosProgmsPostDummy implements ServiciosProgmsPost {
    
    public static Logger LOGGER  = Logger.getLogger(ServiciosProgmsPostDummy.class);

    private List<Programa> programas;
    private List<Recurso> recursos;
    private List<Clase> clases1;
    private List<Clase> clases2;
    private List<Clase> clases3;
    private List<Clase> clases4;
    private List<Clase> clases5;
    private List<Clase> clases6;
    private List<Asignatura> asignaturas;
    private List<Materia> materias;
    
    public ServiciosProgmsPostDummy() {
        programas = new ArrayList<>();
        recursos = new ArrayList<>();
        clases1 = new ArrayList<>();
        clases2 = new ArrayList<>();
        clases3 = new ArrayList<>();
        clases4 = new ArrayList<>();
        clases5 = new ArrayList<>();
        clases6 = new ArrayList<>();
        asignaturas = new ArrayList<>();
        materias = new ArrayList<>();
        poblar();
    }
    
    public void poblar() {
        Asignatura a;
        Materia m;
        
        Programa p1=new Programa(0,"Especializacion en gerencia de proyectos", "Especializacion");
        Programa p2=new Programa(1,"Maestria en gestion de informacion", "Maestria");
        
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
        materias.addAll(materias11);
        asignaturas.add(a);
        
        a=new Asignatura(1,"Teorias gerenciales");
        
        m=new Materia(3,"Teoria de la gerencia");
        materias12.add(m);
        m=new Materia(4,"Teoria de la organizacion");
        materias12.add(m);
        m=new Materia(5,"Teoria administrativa");
        materias12.add(m);
        
        a.setMaterias(materias12);
        materias.addAll(materias12);
        asignaturas.add(a);
        
        p1.setAsignaturas(asignaturas);
        
        a=new Asignatura(2,"Fundamentos de la informacion");
        
        m=new Materia(6,"Organizar informacion");
        materias21.add(m);
        m=new Materia(7,"Informacion en proyectos");
        materias21.add(m);
        m=new Materia(8,"Teorias de la informacion");
        materias21.add(m);
        
        a.setMaterias(materias21);
        materias.addAll(materias21);
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
        materias.addAll(materias22);
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
        //Crea clases1---------------------------
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
        
        clases1.add(cla1);
        clases1.add(cla2);
        clases1.add(cla3);
        
        Clase cla4 = new Clase(1, new Date(2017-1900, 2, 15), new Time(11, 30, 0), new Time(1, 0, 0));//cambiar datos
        
        clases2.add(cla4);
        
        Clase cla5 = new Clase(2, new Date(2017-1900, 3, 5), new Time(8, 30, 0), new Time(10, 0, 0));
        Clase cla6 = new Clase(3, new Date(2017-1900, 3, 29), new Time(1, 0, 0), new Time(11, 30, 0));
        
        clases3.add(cla5);
        clases3.add(cla6);
        
        Clase cla10 = new Clase(1, new Date(2017-1900, 2, 15), new Time(11, 30, 0), new Time(1, 0, 0));//cambiar datos
        Clase cla11 = new Clase(2, new Date(2017-1900, 3, 5), new Time(8, 30, 0), new Time(10, 0, 0));
        Clase cla12 = new Clase(3, new Date(2017-1900, 3, 29), new Time(1, 0, 0), new Time(11, 30, 0));
        
        clases4.add(cla10);
        clases4.add(cla11);
        clases4.add(cla12);
        
        Clase cla13 = new Clase(1, new Date(2017-1900, 2, 15), new Time(11, 30, 0), new Time(1, 0, 0));//cambiar datos
        Clase cla14 = new Clase(2, new Date(2017-1900, 3, 5), new Time(8, 30, 0), new Time(10, 0, 0));
        Clase cla15 = new Clase(3, new Date(2017-1900, 3, 29), new Time(1, 0, 0), new Time(11, 30, 0));
        
        clases5.add(cla13);
        clases5.add(cla14);
        clases5.add(cla15);
        
        Clase cla7 = new Clase(1, new Date(2017-1900, 2, 15), new Time(11, 30, 0), new Time(1, 0, 0));//cambiar datos
        Clase cla8 = new Clase(2, new Date(2017-1900, 3, 5), new Time(8, 30, 0), new Time(10, 0, 0));
        Clase cla9 = new Clase(3, new Date(2017-1900, 3, 29), new Time(1, 0, 0), new Time(11, 30, 0));
        Clase cla16 = new Clase(1, new Date(2017-1900, 2, 15), new Time(11, 30, 0), new Time(1, 0, 0));//cambiar datos
        Clase cla17 = new Clase(2, new Date(2017-1900, 3, 5), new Time(8, 30, 0), new Time(10, 0, 0));
        Clase cla18 = new Clase(3, new Date(2017-1900, 3, 29), new Time(1, 0, 0), new Time(11, 30, 0));
        
        clases6.add(cla7);
        clases6.add(cla8);
        clases6.add(cla9);
        clases6.add(cla16);
        clases6.add(cla17);
        clases6.add(cla18);
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
        LOGGER.debug("Se retornas "+programas.size()+" programas -> "+programas);
        System.out.println("Se retornas "+programas.size()+" programas -> "+programas);
        return programas;
    }

    @Override
    public List<Materia> consultarMaterias(int periodo) throws ExcepcionServiciosProgmsPost {
        return materias;
    }

    @Override
    public List<Profesor> consultarProfesores(int periodo) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Asignatura> consultarAsignaturas(int periodo) throws ExcepcionServiciosProgmsPost {
        List<Asignatura> asignaturas=new ArrayList();
        Asignatura a0=new Asignatura(1,"Fundamentos de gerencia de proyectos");
        Asignatura a1=new Asignatura(2,"Teorias gerenciales");
        Asignatura a2=new Asignatura(3,"Fundamentos de la informacion");
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
        int id=idAsignatura;
        List<Materia> materias= new ArrayList<>();
        Materia m1=new Materia(0,"Adminitrar un proyecto");
        Materia m2=new Materia(1,"Fundamentos de gerencia");
        Materia m3=new Materia(3,"Teoria de la gerencia");
        Materia m4=new Materia(4,"Teoria de la organizacion");
        Materia m5=new Materia(6,"Organizacion de la informacion");
        Materia m6=new Materia(7,"Informacion en proyectos");
        if(id==1){
            materias.add(m1);
            materias.add(m2);
        }else if(id==2){
            materias.add(m3);      
            materias.add(m4);
        }else if(id==3){
           materias.add(m5);
            materias.add(m6); 
        }
        return materias;
    }
 
    @Override
    public List<Recurso> consultarRecursos(String nombreCategoria, Date fecha, Time horaInicio, Time horaFin) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int consultarDisponibilidadRecurso(int idRecurso, Date fecha, Time horaInicio, Time horaFin) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean consultarDisponibilidadProfesor(int idProfesor, Date fecha, Time horaInicio, Time horaFin) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Asignatura> consultarAsignaturas(int periodo, int idPrograma) throws ExcepcionServiciosProgmsPost {
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
        Map<Integer, List<Clase>> clases = new HashMap<>();
        clases.put(0, clases1);
        clases.put(1, clases2);
        clases.put(2, clases3);
        clases.put(3, clases4);
        clases.put(4, clases5);
        clases.put(5, clases6);
        return clases.get(idMateria);
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
    public Profesor consultarProfesor(int periodo, int idMateria) throws ExcepcionServiciosProgmsPost {
        Map<Integer, Profesor> profesores = new HashMap<>();
        profesores.put(0, new Profesor(1, "Juan Andrade"));
        profesores.put(1, new Profesor(2, "Carlos Martinez"));
        profesores.put(2, new Profesor(3, "Maria Perez"));
        profesores.put(3, new Profesor(4, "Carla Castillo"));
        profesores.put(4, new Profesor(5, "Camila Castrillon"));
        profesores.put(5, new Profesor(6, "Jorge Muñoz"));
        return profesores.get(idMateria);
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
        Map<Integer, Integer> cohortes = new HashMap<>();
        cohortes.put(0, 10);
        cohortes.put(1, 20);
        cohortes.put(2, 5);
        cohortes.put(3, 96);
        cohortes.put(4, 12);
        cohortes.put(5, 18);
        return cohortes.get(idMateria);
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
        return clases1;
    }

    @Override
    public void registrarProfesor(Profesor profesor) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registrarRequisito(int idMateria, int idPrerequisito, boolean prerrequisito) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Programa> consultarProgramas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Clase> consultarClasesConRecursos(int periodo) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void agregarCohorte(int idPrograma, int idMateria, int numCohorte, int periodo) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Asignatura> consultarAsignaturasPorPrograma(int idPrograma) throws ExcepcionServiciosProgmsPost {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
