/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.client;

import com.pmarlen.model.beans.Perfil;
import com.pmarlen.model.beans.Usuario;
import com.pmarlen.model.controller.UsuarioJpaController;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author aestrada
 */
//@Ignore
public class TestMainCanStart {

	final static Logger logger = LoggerFactory.getLogger(TestMainCanStart.class);
	private static boolean serverIsUp = false;
	
	static boolean deleteDBDir4CreateDB = true;
	
	public TestMainCanStart() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		if(deleteDBDir4CreateDB) {
			Properties prop = new Properties();

			prop.load(TestPersistenceCliente.class.getResourceAsStream("/jdbc.properties"));

			String userHomeDir = System.getProperty("user.home", ".");
			String systemDir = userHomeDir + File.separator + "." + prop.getProperty("app.dataDirectory", "app.dataDirectory");

			File fileAppDir = new File(systemDir);

			if (fileAppDir.exists() && fileAppDir.isDirectory() && fileAppDir.canWrite()) {
				logger.info("try to delete dir: " + fileAppDir.getAbsolutePath());
				boolean result = deleteDirectory(fileAppDir);
				logger.info("Ok, delete dir " + fileAppDir + ", result:" + result);
			}
		}
	}

	static private boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}
        
	private static boolean displayExist() {
                try {
                        java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
                        java.awt.Dimension scrnsize = toolkit.getScreenSize();
                        return true;
                } catch(java.awt.HeadlessException e) {
                        return false;
                }
        }

	@Test
	public void testMainCanStart() {
		if( ! displayExist() ){
			logger.info("==> 0: Test aborted DISPLAY NOT EXIST!");
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
		Date currDate = new Date();
		logger.info("==> 1:start:" + sdf.format(currDate));
		if (!serverIsUp) {
			logger.info("==>1.1 Can continue !! serverIsUp=" + serverIsUp);
			return;
		}


		ApplicationStarter applicationStarter = ApplicationStarter.getInstance();

		try {
			applicationStarter.beginProcess(new ProgressProcessListener() {

				int internalProgress;

				@Override
				public void updateProgress(int prog, String msg) {
					internalProgress = prog;
					logger.info("==> updateProgress:" + prog + " : " + msg);
				}

				@Override
				public int getProgress() {
					return internalProgress;
				}
			});
		} catch (BusinessException e) {
			logger.error("Error: lm=" + e.getLocalizedMessage(), ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_TITLE"), e);
			Assert.fail();
		} catch (Exception e) {
			logger.error("Error: lm2=" + e.getLocalizedMessage(), ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_TITLE"), e);
			Assert.fail();
		}
		logger.info("==>> 2: Ready to login.");

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"/com/pmarlen/client/TestMainCanStart-context.xml"});

		logger.info("==>> 3: context is null ?" + (context == null));
		assertNotNull("Context is null" + (context == null), context);

		UsuarioJpaController usuarioJpaController = context.getBean("usuarioJpaController", UsuarioJpaController.class);
		logger.info("==>> 4: usuarioJpaController=" + usuarioJpaController);

		List<Usuario> findUsuarioEntities = usuarioJpaController.findUsuarioEntities();

		for (Usuario usuario : findUsuarioEntities) {
			try {
				logger.info("\t==>> 5: findUsuarioEntities[]:" + BeanUtils.describe(usuario));
				Collection<Perfil> perfilCollection = usuario.getPerfilCollection();
				for (Perfil perfil : perfilCollection) {
					logger.info("\t\t=>> 5.1. fecthing Perfil:" + perfil);
				}
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				Assert.fail();
			}
		}

		Usuario usuarioAuthenticated = null;
		String claveUsurioTest = "test1";
		String passwordUsurioTest = "temporal";
		try {
			usuarioAuthenticated = usuarioJpaController.authenticate(claveUsurioTest, passwordUsurioTest);

			logger.info("==>> 6: usuarioAuthenticated=" + BeanUtils.describe(usuarioAuthenticated));

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			Assert.fail();
		}

		assertNotNull("usuarioAuthenticated is " + usuarioAuthenticated, usuarioAuthenticated);

	}
}
