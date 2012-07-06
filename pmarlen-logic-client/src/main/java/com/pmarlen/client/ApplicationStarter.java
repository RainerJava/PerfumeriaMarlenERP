/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.client;

import com.pmarlen.client.model.ApplicationSession;
import com.pmarlen.model.beans.PedidoVentaDetalle;
import com.pmarlen.model.beans.Usuario;
import com.pmarlen.model.controller.UsuarioJpaController;
import com.tracktopell.dbutil.DBInstaller;
import com.tracktopell.dbutil.DerbyDBInstaller;
import java.io.IOException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author alfred
 */
public class ApplicationStarter {

    private ClassPathXmlApplicationContext context;

    public static ApplicationStarter getInstance() {
        if (instance == null) {
            instance = new ApplicationStarter();
        }
        return instance;
    }
    private Logger logger;
    
    private static ApplicationStarter instance;
    
    private int loginAttempt;

    private ApplicationStarter() {
        logger = LoggerFactory.getLogger(ApplicationStarter.class);
        logger.debug("->ApplicationStarter, created");
        loginAttempt = 0;
    }

    public void beginProcess(ProgressProcessListener pl) throws BusinessException {
        logger.debug("->beginProcess()");

        logger.debug("->Application Version=" + ApplicationInfo.getInstance().getVersion());

        pl.updateProgress(10, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_LOAD"));

        DBInstaller dbInstaller = null;
        try {
            dbInstaller = new DerbyDBInstaller("classpath:/jdbc.properties");
        } catch (IOException e) {
            throw new BusinessException("beginProcess", "Can't crerate DB:" + e.getMessage());
        }

        if (!dbInstaller.existDB()) {
            pl.updateProgress(15, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_BUILD_DB"));
            dbInstaller.installDBfromScratch();
			logger.debug("=================>After install: load contex to update Hibernate extra tables");
			// TO-DO: 
			// context = new ClassPathXmlApplicationContext(new String[]{"application-context_update.xml"});
			ClassPathXmlApplicationContext context_update = new ClassPathXmlApplicationContext(new String[]{"application-context_update.xml"});
			logger.debug("=================>After install: shutdown Context");
			context_update.registerShutdownHook();			
			context_update.close();
			logger.debug("=================>After install: OK close context ?");
			context_update = null;
			logger.debug("=================>After install: OK load the normal context");
			
        }

        pl.updateProgress(20, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_START_PERSISTENCE"));

        logger.debug("->prepared to load the Spring Context");

        context = new ClassPathXmlApplicationContext(new String[]{"application-context.xml"});

        logger.debug("->Ok, Context Loaded.");

        PedidoVentaDetalle pvd = null;

        FirstDataSynchronizer firstDataSynchronizer = (FirstDataSynchronizer) context.getBean("firstDataSynchronizer", FirstDataSynchronizer.class);

        pl.updateProgress(23, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_TEST_SYNC"));
        if (firstDataSynchronizer.needSyncronization()) {
            try {
                firstDataSynchronizer.checkServerVersion();
            } catch (IllegalStateException ex) {
                logger.error("main:beginProcess", ex);
                throw new BusinessException(getClass().getSimpleName(), "FAIL_CHECK_SERVER_VERSION");
            } catch (Exception ex) {
                logger.error("main:beginProcess", ex);
                throw new BusinessException(getClass().getSimpleName(), "FAIL_WEBSERVICE_CONNECTION");
            }

            try {
                firstDataSynchronizer.firstSyncronization(pl);
            } catch (Exception ex) {
                logger.error("main:beginProcess", ex);
                throw new BusinessException(getClass().getSimpleName(), "FAIL_FIRST_SINCHRONIZATION");
            }
        } else {
            pl.updateProgress(30, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_STARTING"));
        }
    }

    /**
     * @return the context
     */
    public ClassPathXmlApplicationContext getContext() {
        return context;
    }

    public ApplicationSession login(String id, String password) throws Exception {

        logger.debug("->login: ");
        Query q = null;
        int r = -1;
        ApplicationSession as = null;
        try {
            if (++loginAttempt > 3) {
                throw new IllegalStateException("Mouch Attempts to login");
            }

            UsuarioJpaController usuarioJpaController = context.getBean("usuarioJpaController", UsuarioJpaController.class);        

            logger.debug("->asigned user&password: "+id+", "+password);
            Usuario u = usuarioJpaController.authenticate(id, password);            
            logger.debug("--->authenticated: u="+u);
            if( u == null) {
                throw new IllegalStateException("Usuario o password incorrecto");
            }
            
            as = (ApplicationSession) context.getBean("applicationSession", ApplicationSession.class);

            as.setUsuario(u);
            
            ApplicationLogic applicationLogic = context.getBean("applicationLogic", ApplicationLogic.class);        

            applicationLogic.setApplicationSession(as);

            logger.debug("->login, ok");
        } catch (Exception ex) {
            logger.error("login:", ex);
            throw ex;
        } 
        return as;
    }
}
