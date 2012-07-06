/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.client;

import com.pmarlen.model.beans.*;
import com.pmarlen.model.controller.GetListDataBusinesJpaController;
import com.pmarlen.model.controller.PersistEntityWithTransactionDAO;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author aestrada
 */
//@Ignore
public class TestPersistenceCliente {

    final static Logger logger = LoggerFactory.getLogger(TestPersistenceCliente.class);
    
	static boolean deleteDBDir4CreateDB = true;
	
    public TestPersistenceCliente() {
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
    public void testCrearEditarYBorrarCliente() {
        if (!displayExist()) {
            logger.info("==> 0: Test aborted DISPLAY NOT EXIST!");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        Date currDate = new Date();
        logger.info("==> 1:start:" + sdf.format(currDate));

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

        ClassPathXmlApplicationContext context = applicationStarter.getContext();

        logger.info("==>> 3: context is null ?" + (context == null));
        assertNotNull("Context is null" + (context == null), context);

        PersistEntityWithTransactionDAO persistEntityWithTransactionDAO = context.getBean("persistEntityWithTransactionDAO", PersistEntityWithTransactionDAO.class);

        logger.info("==>> 4: persistEntityWithTransactionDAO=" + persistEntityWithTransactionDAO);

        GetListDataBusinesJpaController getListDataBusinesJpaController = context.getBean("getListDataBusinesJpaController", GetListDataBusinesJpaController.class);


        List<Cliente> findClienteEntities = getListDataBusinesJpaController.findClienteEntities(true, 100, 0);

        for (Cliente ci : findClienteEntities) {
            try {
                logger.info("\t==>> 5: findClienteEntities[]:" + ci.getId() + "," + ci.getRazonSocial());
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                Assert.fail();
            }
        }

        Cliente clienteNuevo = new Cliente();

        clienteNuevo.setCalle("CALLE");
        clienteNuevo.setClasificacionFiscal(1);
        clienteNuevo.setDescripcionRuta("DESCRIPCION RUTA");
        clienteNuevo.setEmail("email@mail.com");
        clienteNuevo.setFaxes("12345678");
        clienteNuevo.setFechaCreacion(new Date());
        clienteNuevo.setNombreEstablecimiento("NOMBRE ESTABLECIMIENTO");
        clienteNuevo.setNumExterior("NEXT");
        clienteNuevo.setNumInterior("NINT");
        clienteNuevo.setObservaciones("OBSERVACINES");
        clienteNuevo.setPlazoDePago(1);
        clienteNuevo.setRfc("EAGA8012254X2");
        clienteNuevo.setRazonSocial("RAZON SOCIAL XXX");
        clienteNuevo.setPoblacion(new Poblacion(1));
	
        logger.info("\t==>> 6 fill Cliente: ok, before insert: CLiente.Id="+clienteNuevo.getId());

        try {
            //persistEntityWithTransactionDAO.persistCliente(clienteNuevo);
			persistEntityWithTransactionDAO.create(clienteNuevo);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            Assert.fail();
        }

        List<Cliente> findClienteAfterEntities = getListDataBusinesJpaController.findClienteEntities(true, 100, 0);

        logger.info("\t==>> 7 after insert");
        for (Cliente ci : findClienteAfterEntities) {
            try {
                logger.info("\t==>> 7.1: findClienteAfterEntities[]:" + ci.getId() + "," + ci.getRazonSocial());
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                Assert.fail();
            }
        }

        Assert.assertEquals("No son ==", findClienteEntities.size() + 1, findClienteAfterEntities.size());



    }
}
