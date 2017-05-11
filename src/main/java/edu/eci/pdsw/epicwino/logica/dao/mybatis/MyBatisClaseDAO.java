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
    public void saveClase(Clase c, int idMateria, int periodo) throws PersistenceException {
        claseMapper.agregarClase(c, idMateria, periodo);
    }

    @Override
    public List<Clase> loadClases(int periodo) throws PersistenceException {
        return claseMapper.consultarClases(periodo);
    }

    @Override
    public List<Recurso> loadRecursosConcedidos(int idCl) throws PersistenceException {
        return claseMapper.consultarRecursosConcedidos(idCl);
    }

    @Override
    public void saveRecursoConcedido(int idCl, int idRe) throws PersistenceException {
        claseMapper.agregarRecursoConcedido(idCl, idRe);
    }

    @Override
    public void agregarGrupoDeMateria(int idMateria, int periodo, int idProfesor) throws PersistenceException {
        claseMapper.agregarGrupoDeMateria(idMateria, periodo, idProfesor);
    }
    
}
