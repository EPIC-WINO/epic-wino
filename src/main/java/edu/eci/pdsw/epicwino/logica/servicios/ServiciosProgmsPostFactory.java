package edu.eci.pdsw.epicwino.logica.servicios;

import edu.eci.pdsw.epicwino.logica.dao.ClaseDAO;
import edu.eci.pdsw.epicwino.logica.dao.mybatis.MyBatisClaseDAO;
import edu.eci.pdsw.epicwino.logica.dao.mybatis.MyBatisProgramaDAO;
import edu.eci.pdsw.epicwino.logica.dao.mybatis.MyBatisRecursoDAO;
import edu.eci.pdsw.epicwino.logica.dao.ProgramaDAO;
import edu.eci.pdsw.epicwino.logica.dao.RecursoDAO;
import edu.eci.pdsw.epicwino.logica.servicios.impl.ServiciosProgmsPostDummy;
import static com.google.inject.Guice.createInjector;
import com.google.inject.Injector;
import org.mybatis.guice.XMLMyBatisModule;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import edu.eci.pdsw.epicwino.logica.servicios.impl.ServiciosProgmsPostImpl;
import org.apache.log4j.Category;
import org.apache.log4j.Logger;

/**
 *
 * @author Esteban
 */
public class ServiciosProgmsPostFactory {
    
    private static final Logger LOGGER = Logger.getLogger(ServiciosProgmsPostFactory.class);
    private static final ServiciosProgmsPostFactory INSTANCE = new ServiciosProgmsPostFactory();
    private static Injector injector;
    private static Injector testInjector;

    private ServiciosProgmsPostFactory() {
        LOGGER.debug("Se instancia " + ServiciosProgmsPostFactory.class.getName());
        
        /*injector = createInjector(new XMLMyBatisModule() {
                
                @Override
                protected void initialize() {
                        install(JdbcHelper.PostgreSQL);                        
                        setClassPathResource("mybatis-config.xml");                        
                        bind(ServiciosProgmsPost.class).to(ServiciosProgmsPostImpl.class);
                        bind(ProgramaDAO.class).to(MyBatisProgramaDAO.class);
                        bind(RecursoDAO.class).to(MyBatisRecursoDAO.class);
                        bind(ClaseDAO.class).to(MyBatisClaseDAO.class);
                }
        
            }
        );
        
        testInjector = createInjector(new XMLMyBatisModule() {
                
                @Override
                protected void initialize() {
                        install(JdbcHelper.PostgreSQL);                        
                        setClassPathResource("mybatis-config-h2.xml");                        
                        bind(ServiciosProgmsPost.class).to(ServiciosProgmsPostImpl.class);
                        bind(ProgramaDAO.class).to(MyBatisProgramaDAO.class);
                        bind(RecursoDAO.class).to(MyBatisRecursoDAO.class);
                        bind(ClaseDAO.class).to(MyBatisClaseDAO.class);
                }
        
            }
        );*/
    }
    
    public ServiciosProgmsPost getServiciosProgmsPost(){
        LOGGER.debug("Se obtiene concreta de servicios");
        return null; //injector.getInstance(ServiciosProgmsPost.class);   
    }


    public ServiciosProgmsPost getServiciosProgmsPostTesting(){
        LOGGER.debug("Se obtiene de pruebas de servicios");
        return null; //testInjector.getInstance(ServiciosProgmsPost.class);   
    }
    
    public ServiciosProgmsPost getServiciosProgmsPostDummy() {
        LOGGER.debug("Se obtiene DUMMY de servicios");
        return new ServiciosProgmsPostDummy();
    }

    public static ServiciosProgmsPostFactory getInstance(){
        return INSTANCE;
    }
    
}
