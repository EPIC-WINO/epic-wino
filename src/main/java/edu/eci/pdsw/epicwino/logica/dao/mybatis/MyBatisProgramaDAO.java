package edu.eci.pdsw.epicwino.logica.dao.mybatis;

import edu.eci.pdsw.epicwino.logica.dao.mybatis.mappers.ProgramaMapper;
import edu.eci.pdsw.epicwino.logica.dao.PersistenceException;
import edu.eci.pdsw.epicwino.logica.dao.ProgramaDAO;
import edu.eci.pdsw.epicwino.logica.entidades.Programa;
import com.google.inject.Inject;
import java.util.List;

/**
 *
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 * @author Fabian Ardila
 */
public class MyBatisProgramaDAO implements ProgramaDAO{

    @Inject
    private ProgramaMapper programaMapper;
    
    @Override
    public void save(Programa programa) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); // TODO implementar
    }

    @Override
    public List<Programa> loadProgramas(int periodo) throws PersistenceException {
        try {
            return programaMapper.consultarProgramas(periodo);
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al consultar los programas en el periodo " + periodo);
        }
    }

    @Override
    public Programa loadProgram(int id_program,int periodo) throws PersistenceException {
        try {
            return programaMapper.cosultarProgramaPorPeriodo(id_program, periodo);
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al consultar el programa con id: "+id_program+" en el periodo: "+periodo);
        }
    }

    @Override
    public List<Integer> loadPeriodos() throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); // TODO implementar
    }
    
}
