package Logica.Dao.MyBatis;

import Logica.Dao.MyBatis.Mappers.ProgramaMapper;
import Logica.Dao.PersistenceException;
import Logica.Dao.ProgramaDAO;
import Logica.Entidades.Programa;
import com.google.inject.Inject;
import java.util.List;

/**
 *
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 */
public class MyBatisProgramaDAO implements ProgramaDAO{

    @Inject
    private ProgramaMapper programaMapper;
    
    @Override
    public void save(Programa programa) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); // TODO implementar
    }

    @Override
    public List<Programa> loadProgramas(int anio, int semestre) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); // TODO implementar
    }
    
}
