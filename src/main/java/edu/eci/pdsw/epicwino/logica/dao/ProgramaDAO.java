package edu.eci.pdsw.epicwino.logica.dao;

import edu.eci.pdsw.epicwino.logica.entidades.Programa;
import java.util.List;

/**
 *
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 */
public interface ProgramaDAO {
    public void save(Programa programa) throws PersistenceException;
    
    public List<Programa> loadProgramas(int anio, int semestre) throws PersistenceException;
    
    public Programa loadProgram(int programId,int periodo) throws PersistenceException;
}
