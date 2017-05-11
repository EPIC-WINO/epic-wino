package edu.eci.pdsw.epicwino.logica.dao.mybatis;

import edu.eci.pdsw.epicwino.logica.dao.mybatis.mappers.RecursoMapper;
import edu.eci.pdsw.epicwino.logica.dao.PersistenceException;
import edu.eci.pdsw.epicwino.logica.dao.RecursoDAO;
import edu.eci.pdsw.epicwino.logica.entidades.Recurso;
import com.google.inject.Inject;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 *
 * @author Esteban
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 */
public class MyBatisRecursoDAO implements RecursoDAO {
    
    @Inject
    private RecursoMapper recursoMapper;
    
    @Override
    public void save(Recurso rec) throws PersistenceException {
        recursoMapper.agregarRecurso(rec);
    }

    @Override
    public List<Recurso> loadRecursos() throws PersistenceException {
        return recursoMapper.consultarRecursos();
    }

    @Override
    public int consultarDisponibilidadRecurso(int idRecurso, Date fecha, Time horaInicio, Time horaFin) throws PersistenceException {
        return recursoMapper.consultarDisponibilidadRecurso(idRecurso, fecha, horaInicio, horaFin);
    }
    
}
