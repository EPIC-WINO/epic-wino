package edu.eci.pdsw.epicwino.logica.dao.mybatis;

import com.google.inject.Inject;
import edu.eci.pdsw.epicwino.logica.dao.MateriaDAO;
import edu.eci.pdsw.epicwino.logica.dao.PersistenceException;
import edu.eci.pdsw.epicwino.logica.dao.mybatis.mappers.MateriaMapper;
import edu.eci.pdsw.epicwino.logica.entidades.Materia;
import edu.eci.pdsw.epicwino.logica.entidades.Profesor;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 *
 * @author Alejandro Anzola email: alejandro.anzola@mail.escuelaing.edu.co
 */
public class MyBatisMateriaDAO implements MateriaDAO{

    @Inject
    MateriaMapper materiaMapper;
    
    @Override
    public void agregarCohorte(int idPrograma, String idMateria, int numCohorte, int periodo) throws PersistenceException {
        materiaMapper.agregarCohorte(idPrograma, idMateria, numCohorte, periodo);
    }

    @Override
    public List<Integer> loadCohortes() throws PersistenceException {
        return materiaMapper.loadCohortes();
    }

    @Override
    public List<Materia> loadPrerrequisitos(String idMateria) throws PersistenceException {
        return materiaMapper.loadPrerrequisitos(idMateria);
    }

    @Override
    public List<Materia> loadCorrequisitos(String idMateria) throws PersistenceException {
        return materiaMapper.loadCorrequisitos(idMateria);
    }

    @Override
    public void saveMateria(Materia materia, int idAsignatura) throws PersistenceException {
        materiaMapper.saveMateria(materia, idAsignatura);
    }

    @Override
    public List<Materia> consultarMaterias() throws PersistenceException {
        return materiaMapper.consultarMaterias();
    }

    @Override
    public void agregarProfesor(Profesor profesor) throws PersistenceException {
        materiaMapper.agregarProfesor(profesor.getId(), profesor.getNombre(), 
                profesor.getTipoID(), profesor.getCorreo(), profesor.getTelefono());
    }

    @Override
    public boolean consultarDisponibilidadProfesor(int idProf, Date fecha, Time horaInicio, Time horaFin) {
        return materiaMapper.consultarDisponibilidadProfesor(idProf, fecha, horaInicio, horaFin) == 1;
    }

    @Override
    public void registrarRequisito(String idMateria, int idRequisito, boolean prerrequisito) throws PersistenceException {
        materiaMapper.agregarRequisito(idMateria, idRequisito, prerrequisito ? "TRUE" : "FALSE");
    }

    @Override
    public Profesor consultarProfesoresEnPeriodoYMateria(String idMateria, int periodo) throws PersistenceException {
        return materiaMapper.loadProfesoresEnPeriodoYMateria(idMateria, periodo);
    }

    @Override
    public List<Profesor> consultarProfesoresEnPeriodo(int periodo) throws PersistenceException {
        return materiaMapper.loadProfesoresEnPeriodo(periodo);
    }
    
}
