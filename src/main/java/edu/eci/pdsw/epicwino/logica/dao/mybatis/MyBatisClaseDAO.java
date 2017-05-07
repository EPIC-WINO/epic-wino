package edu.eci.pdsw.epicwino.logica.dao.mybatis;

import edu.eci.pdsw.epicwino.logica.dao.ClaseDAO;
import edu.eci.pdsw.epicwino.logica.dao.mybatis.mappers.ClaseMapper;
import edu.eci.pdsw.epicwino.logica.dao.PersistenceException;
import edu.eci.pdsw.epicwino.logica.entidades.Clase;
import edu.eci.pdsw.epicwino.logica.entidades.Recurso;
import com.google.inject.Inject;
import java.util.List;

/**
 *
 * @author Esteban
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 */
public class MyBatisClaseDAO implements ClaseDAO {
    
    @Inject
    private ClaseMapper claseMapper;

    @Override
    public void saveClase(Clase c, int idMateria) throws PersistenceException {
        claseMapper.agregarClase(c, idMateria);
    }

    @Override
    public List<Clase> loadClases(int periodo) throws PersistenceException {
        return claseMapper.consultarClases(periodo / 10, periodo % 10); // XXX refactorizar
    }

    @Override
    public List<Recurso> loadRecursosConcedidos(int idCl) throws PersistenceException {
        return claseMapper.consultarRecursosConcedidos(idCl);
    }

    @Override
    public void saveRecursoConcedido(int idCl, int idRe) throws PersistenceException {
        claseMapper.agregarRecursoConcedido(idCl, idRe);
    }
    
}
