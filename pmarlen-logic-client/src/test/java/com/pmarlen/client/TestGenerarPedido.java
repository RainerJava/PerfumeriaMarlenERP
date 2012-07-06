/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.client;

import com.pmarlen.client.model.ApplicationSession;
import com.pmarlen.model.beans.Cliente;
import com.pmarlen.model.beans.FormaDePago;
import com.pmarlen.model.beans.Perfil;
import com.pmarlen.model.beans.Producto;
import com.pmarlen.model.beans.Usuario;
import com.pmarlen.model.controller.GetListDataBusinesJpaController;
import com.pmarlen.model.controller.UsuarioJpaController;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
@Ignore
public class TestGenerarPedido {

	final static Logger logger = LoggerFactory.getLogger(TestGenerarPedido.class);
	private static boolean serverIsUp = false;
	private static boolean refreshFromServer = false;

	public TestGenerarPedido() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		if (refreshFromServer) {
			FirstDataSynchronizer fds = new FirstDataSynchronizer();
			try {
				fds.checkServerVersion();
				serverIsUp = true;
			} catch (Exception ex) {
				serverIsUp = false;
			}
			Properties prop = new Properties();

			prop.load(TestGenerarPedido.class.getResourceAsStream("/jdbc.properties"));

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

	private static boolean deleteDirectory(File path) {
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
		} catch (java.awt.HeadlessException e) {
			return false;
		}
	}

	@Test
	public void testGenerarPedido() {
		if (!displayExist()) {
			logger.info("==> 0: Test aborted DISPLAY NOT EXIST!");
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
		Date currDate = new Date();
		logger.info("==> 1:start:" + sdf.format(currDate));
		if (refreshFromServer && !serverIsUp) {
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

        GetListDataBusinesJpaController getListDataBusinesJpaController = context.getBean("getListDataBusinesJpaController", GetListDataBusinesJpaController.class);
        		
		List<Producto> productoList = getListDataBusinesJpaController.findProductoEntities(true,100,0);
		List<Producto> productoPedidoList = new ArrayList<Producto>();

		for (Producto p : productoList) {
			if (Math.random() > 0.5) {
				productoPedidoList.add(p);
			}
		}
		if (productoPedidoList.size() == 0 && productoList.size() >= 3) {
			productoPedidoList.add(productoList.get(0));
			productoPedidoList.add(productoList.get(1));
			productoPedidoList.add(productoList.get(2));
		}

		ApplicationSession applicationSession = context.getBean("applicationSession", ApplicationSession.class);
		assertNotNull("applicationSession is " + applicationSession, applicationSession);

		applicationSession.setUsuario(usuarioAuthenticated);

		ApplicationLogic applicationLogic = context.getBean("applicationLogic", ApplicationLogic.class);
		assertNotNull("applicationLogic is " + applicationLogic, applicationLogic);

		logger.info("==>> 7: applicationLogic.startNewPedidoVentaSession:");
		applicationLogic.startNewPedidoVentaSession();

		logger.info("==>> 8: applicationLogic.addProductoNToCurrentPedidoVenta:");
		for (Producto p : productoPedidoList) {
			logger.info("\t==>> 8.1: applicationLogic.addProductoNToCurrentPedidoVenta:" + p);
			applicationLogic.addProductoNToCurrentPedidoVenta(p, 1);
		}

		List<Cliente> clienteList = getListDataBusinesJpaController.findClienteEntities(true,100,0);
		Cliente clientePedido = null;
		int clienteElegido = (int) Math.random() * clienteList.size();
		int ic = 0;
		for (Cliente c : clienteList) {
			logger.info("==>> 9: cliente[" + ic + "]:" + c);
			if (ic == clienteElegido) {
				clientePedido = c;
			}
			ic++;
		}
		if (clientePedido == null && clienteList.size() >= 1) {
			clientePedido = clienteList.get(0);
		}
		logger.info("==>> 10: setClienteToCurrentPedidoVenta:" + clientePedido);
		applicationLogic.setClienteToCurrentPedidoVenta(clientePedido);

		List<FormaDePago> formaDePagoList = getListDataBusinesJpaController.findFormaDePagoEntities(true,100,0);
		for (FormaDePago fp : formaDePagoList) {
			logger.info("==>> 11: formaDePago=" + fp);
		}

		FormaDePago formaDePago = formaDePagoList.get(0);

		applicationLogic.setFormaDePagoToCurrentPedidoVenta(formaDePago);
		applicationLogic.getSession().getPedidoVenta().setComentarios("Prueba en " + sdf.format(currDate));

		try {
			logger.info("==>> 12: persistCurrentPedidoVenta():");
			applicationLogic.persistCurrentPedidoVenta();
			logger.info("==>> 13: OK");
		} catch (BusinessException ex) {
			logger.error("==>> persistCurrentPedidoVenta:", ex);
			Assert.fail("persistCurrentPedidoVenta");
		}

	}
}
