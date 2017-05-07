package edu.eci.pdsw.epicwino.logica.dao.mybatis;

import com.google.inject.Inject;
import edu.eci.pdsw.epicwino.logica.dao.MateriaDAO;
import edu.eci.pdsw.epicwino.logica.dao.PersistenceException;
import edu.eci.pdsw.epicwino.logica.dao.mybatis.mappers.MateriaMapper;
import edu.eci.pdsw.epicwino.logica.entidades.Materia;
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
    public void saveMateria(Materia materia) throws PersistenceException {
        materiaMapper.saveMateria(materia);
    }
    
}
