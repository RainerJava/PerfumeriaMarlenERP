/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.client;

import com.pmarlen.client.controller.LoginControl;
import com.pmarlen.client.view.SplashWindow;
import com.pmarlen.model.beans.Perfil;
import com.pmarlen.model.beans.Usuario;
import com.pmarlen.model.controller.UsuarioJpaController;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author praxis
 */
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);   
    private static SplashWindow splashWindow;
    
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        Date currDate = new Date();
        logger.info("==> 1:start:" + sdf.format(currDate));

        ApplicationStarter applicationStarter = ApplicationStarter.getInstance();

        try {
            splashWindow = new SplashWindow(ApplicationInfo.getInstance().getVersion());
            boolean hideSplash = false;
			int countArgs=0;
			for(String arg: args){
				logger.debug("=>> 1.1: args["+(countArgs++)+"]="+arg);
				if( arg.equalsIgnoreCase("-noSplash")){
					hideSplash = true;
				}
			}
			
            if(!hideSplash) {	            
		        splashWindow.centerInScreenAndSetVisible();
            } else {
				logger.info("\t=>>we don't display Splash");
			}
			
            applicationStarter.beginProcess(splashWindow);
        } catch (BusinessException e) {
            logger.error("Error: lm=" + e.getLocalizedMessage(), ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_TITLE"), e);
            System.exit(1);
        } catch (Exception e) {
            logger.error("Error: lm2=" + e.getLocalizedMessage(), ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_TITLE"), e);
            System.exit(1);
        }
        logger.info("==>> 2: Ready to login.");

        ClassPathXmlApplicationContext context = applicationStarter.getContext();
        logger.info("==>> 3: context is null ?"+(context == null));
        UsuarioJpaController usuarioJpaController = context.getBean("usuarioJpaController", UsuarioJpaController.class);        
        logger.info("==>> 4: usuarioJpaController=" + usuarioJpaController);
        
        List<Usuario> findUsuarioEntities = usuarioJpaController.findUsuarioEntities();

        for (Usuario usuario : findUsuarioEntities) {
            try {
                //logger.info("\t==>> 5: findUsuarioEntities[]:" + BeanUtils.describe(usuario));
                //logger.info("\t==>> 5: findUsuarioEntities[]:" + usuario+", perfiles="+usuario.getPerfilCollection()+", pedidoVentaCollection="+usuario.getPedidoVentaCollection());
                logger.info("\t=>> 1. For Usuario:"+usuario);
                Collection<Perfil> perfilCollection = usuario.getPerfilCollection();
                for(Perfil perfil: perfilCollection){
                    logger.info("\t\t=>> 1. fecthing Perfil:"+perfil);
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage());
            }
        }
//        
//        if(args.length == 2) {
//            
//            Usuario usuarioAuthenticated = null;
//
//            try {
//                usuarioAuthenticated = usuarioJpaController.authenticate(args[0], args[1]);
//                
//                logger.info("==>> 6: usuarioAuthenticated=" + BeanUtils.describe(usuarioAuthenticated));
//                
//            } catch (Exception ex) {
//                logger.error(ex.getMessage());
//            }
//        }
        splashWindow.setVisible(false);
        splashWindow.dispose();
        
        LoginControl loginControl = context.getBean("loginControl", LoginControl.class);        

        loginControl.estadoInicial();
    }
}
