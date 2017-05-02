package Logica.Servicios;

import Logica.Dao.ClaseDAO;
import Logica.Dao.MyBatis.MyBatisClaseDAO;
import Logica.Dao.MyBatis.MyBatisProgramaDAO;
import Logica.Dao.MyBatis.MyBatisRecursoDAO;
import Logica.Dao.ProgramaDAO;
import Logica.Dao.RecursoDAO;
import Logica.Servicios.impl.ServiciosProgmsPostDummy;
import static com.google.inject.Guice.createInjector;
import com.google.inject.Injector;
import org.mybatis.guice.XMLMyBatisModule;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import Logica.Servicios.impl.ServiciosProgmsPostImpl;
import org.apache.log4j.Category;

/**
 *
 * @author Esteban
 */
@SuppressWarnings("deprecation")
public class ServiciosProgmsPostFactory {
    
    private static final Category LOG = Category.getInstance(ServiciosProgmsPostFactory.class.getName());
    private static final ServiciosProgmsPostFactory INSTANCE = new ServiciosProgmsPostFactory();
    private static Injector injector;
    private static Injector testInjector;

    private ServiciosProgmsPostFactory() {
        LOG.debug("Se instancia " + ServiciosProgmsPostFactory.class.getName());
        
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
        
        LOG.debug("Se instancian los injectors correctamente");
    }
    
    public ServiciosProgmsPost getServiciosProgmsPost(){
        return null; //injector.getInstance(ServiciosProgmsPost.class);   
    }


    public ServiciosProgmsPost getServiciosProgmsPostTesting(){
        return null; //testInjector.getInstance(ServiciosProgmsPost.class);   
    }
    
    public ServiciosProgmsPost getServiciosProgmsPostDummy() {
        return new ServiciosProgmsPostDummy();
    }

    public static ServiciosProgmsPostFactory getInstance(){
        return INSTANCE;
    }
    
}
