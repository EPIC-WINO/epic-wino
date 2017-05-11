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
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 */
public class MyBatisMateriaDAO implements MateriaDAO{

    @Inject
    MateriaMapper materiaMapper;
    
    @Override
    public void agregarCohorte(int idPrograma, int idMateria, int numCohorte) throws PersistenceException {
        materiaMapper.agregarCohorte(idPrograma, idMateria, numCohorte);
    }

    @Override
    public List<Integer> loadCohortes() throws PersistenceException {
        return materiaMapper.loadCohortes();
    }

    @Override
    public List<Materia> loadPrerrequisitos(int idMateria) throws PersistenceException {
        return materiaMapper.loadPrerrequisitos(idMateria);
    }

    @Override
    public List<Materia> loadCorrequisitos(int idMateria) throws PersistenceException {
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
    public void registrarRequisito(int idMateria, int idRequisito, boolean prerrequisito) throws PersistenceException {
        materiaMapper.agregarRequisito(idMateria, idRequisito, prerrequisito ? "TRUE" : "FALSE");
    }
    
}
