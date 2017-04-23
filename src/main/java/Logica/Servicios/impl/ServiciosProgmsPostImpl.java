package Logica.Servicios.impl;

import Logica.Dao.ClaseDAO;
import Logica.Dao.PersistenceException;
import Logica.Dao.RecursoDAO;
import Logica.Entidades.Clase;
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
 */
public class ServiciosProgmsPostImpl implements ServiciosProgmsPost{

    @Inject
    private RecursoDAO daoRecurso;
    
    @Inject 
    private ClaseDAO daoClase;
    
    @Override
    public List<RecursoConcedido> ConsultarRecursosConcedidos(int año, int semestre) throws ExcepcionServiciosProgmsPost {
        try {
            List<RecursoConcedido> rcs = new ArrayList<>();
            List<Clase> clases = daoClase.loadClasesPA(año, semestre);
            for(Clase cl : clases){
                rcs.addAll(cl.getRecursos());
            }
            return rcs;
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosProgmsPost("Error al consultar los recursos asociados al periodo "+año+"-"+semestre,ex);
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
    
}
