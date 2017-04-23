package Logica.Servicios.impl;

import Logica.Dao.ClaseDAO;
import Logica.Dao.PersistenceException;
import Logica.Dao.ProgramaDAO;
import Logica.Dao.RecursoDAO;
import Logica.Entidades.Clase;
import Logica.Entidades.Programa;
import Logica.Entidades.Recurso;
import Logica.Entidades.RecursoConcedido;
import Logica.Servicios.ExcepcionServiciosProgmsPost;
import Logica.Servicios.ServiciosProgmsPost;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Esteban
 * @author Alejandro Anzola <alejandro.anzola@mail.escuelaing.edu.co>
 */
public class ServiciosProgmsPostImpl implements ServiciosProgmsPost{
    
    private static final Logger LOGGER = Logger.getLogger(ServiciosProgmsPostImpl.class.getName());

    @Inject
    private RecursoDAO daoRecurso;
    
    @Inject 
    private ClaseDAO daoClase;
    
    @Inject 
    private ProgramaDAO daoPrograma;
    
    @Override
    public List<RecursoConcedido> consultarRecursosConcedidos(int anio, int semestre) throws ExcepcionServiciosProgmsPost {
        try {
            List<RecursoConcedido> rcs = new ArrayList<>();
            List<Clase> clases = daoClase.loadClasesPA(anio, semestre);
            for(Clase cl : clases){
                rcs.addAll(cl.getRecursos());
            }
            return rcs;
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosProgmsPost("Error al consultar los recursos asociados al periodo "+anio+"-"+semestre,ex);
        }
    }

    @Override
    public void registrarClase(Clase c) throws ExcepcionServiciosProgmsPost {
        try {
            daoClase.save(c);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosProgmsPost("Error al consultar la clase con identificador "+c.getId(),ex);
        }
    }

    @Override
    public void registrarRecurso(Recurso rec) throws ExcepcionServiciosProgmsPost {
        try {
            daoRecurso.save(rec);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosProgmsPost("Error al registrar el recurso: "+rec.getNombre(),ex);
        }
    }

    @Override
    public void registrarPrestamoClase(int clase, Recurso rec) throws ExcepcionServiciosProgmsPost {
        try {
            daoClase.saveRecursoConcedido(clase, rec.getId());
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosProgmsPost("Error al prestar el recurso: "+rec.getNombre(),ex);
        }
    }

    @Override
    public List<Programa> consultarProgramas(int anio, int semestre) throws ExcepcionServiciosProgmsPost {
        try {
            return daoPrograma.loadProgramas(anio, semestre);
        } catch (PersistenceException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            throw new ExcepcionServiciosProgmsPost("Error obteniendo los programas", ex);
        }
    }
}
