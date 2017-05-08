package edu.eci.pdsw.epicwino.logica.dao.mybatis;

import com.google.inject.Inject;
import edu.eci.pdsw.epicwino.logica.dao.AsignaturaDAO;
import edu.eci.pdsw.epicwino.logica.dao.PersistenceException;
import edu.eci.pdsw.epicwino.logica.dao.mybatis.mappers.AsignaturaMapper;
import edu.eci.pdsw.epicwino.logica.entidades.Asignatura;

/**
 *
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 */
public class MyBatisAsignaturaDAO implements AsignaturaDAO{

    @Inject
    AsignaturaMapper asignaturaMapper;
    
    @Override
    public void saveAsignatura(Asignatura asignatura, int idPrograma) throws PersistenceException {
        asignaturaMapper.saveAsignatura(asignatura, idPrograma);
    }
    
}
