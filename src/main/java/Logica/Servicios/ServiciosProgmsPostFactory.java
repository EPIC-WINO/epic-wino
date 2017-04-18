/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Servicios;

import Logica.Dao.ClaseDAO;
import Logica.Dao.MyBatis.MyBatisClaseDAO;
import Logica.Dao.MyBatis.MyBatisRecursoDAO;
import Logica.Dao.RecursoDAO;
import static com.google.inject.Guice.createInjector;
import com.google.inject.Injector;
import org.mybatis.guice.XMLMyBatisModule;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import Logica.Servicios.impl.ServiciosProgmsPostImpl;

/**
 *
 * @author Esteban
 */
public class ServiciosProgmsPostFactory {
    
    private static ServiciosProgmsPostFactory instance = new ServiciosProgmsPostFactory();
    private static Injector injector;
    private static Injector testInjector;

    private ServiciosProgmsPostFactory() {
        
        injector = createInjector(new XMLMyBatisModule() {
                
                @Override
                protected void initialize() {
                        install(JdbcHelper.MySQL);                        
                        setClassPathResource("mybatis-config.xml");                        
                        bind(ServiciosProgmsPost.class).to(ServiciosProgmsPostImpl.class);
                        bind(RecursoDAO.class).to(MyBatisRecursoDAO.class);
                        bind(ClaseDAO.class).to(MyBatisClaseDAO.class);
                }
        
            }
        );
        
        testInjector = createInjector(new XMLMyBatisModule() {
                
                @Override
                protected void initialize() {
                        install(JdbcHelper.MySQL);                        
                        setClassPathResource("mybatis-config-h2.xml");                        
                        bind(ServiciosProgmsPost.class).to(ServiciosProgmsPostImpl.class);
                        bind(RecursoDAO.class).to(MyBatisRecursoDAO.class);
                        bind(ClaseDAO.class).to(MyBatisClaseDAO.class);
                }
        
            }
        );
    }
    
    public ServiciosProgmsPost getServiciosProgmsPost(){
        return injector.getInstance(ServiciosProgmsPost.class);   
    }


    public ServiciosProgmsPost getServiciosProgmsPostTesting(){
        return testInjector.getInstance(ServiciosProgmsPost.class);   
    }


    
    public static ServiciosProgmsPostFactory getInstance(){
        return instance;
    }
    
}
