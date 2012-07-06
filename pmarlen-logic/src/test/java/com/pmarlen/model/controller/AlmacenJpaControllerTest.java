package com.pmarlen.model.controller;

import com.pmarlen.model.Constants;
import com.pmarlen.model.beans.*;

import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Ignore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class AlmacenJpaControllerTest {

    final Logger logger = LoggerFactory.getLogger(AlmacenJpaControllerTest.class);
    @Autowired
    private AlmacenJpaController almacenJpaController;

    @Autowired(required=true)
    private AlmacenJpaDao almacenJpaDao;

    @Test
    //@Ignore
    public void testJpaController() throws SQLException {
        logger.info("============================== INITIALIZED CONTEXT ============================");

        assertNotNull("almacenJpaController is null.", almacenJpaController);

        List<Almacen> almacenBeforeList = almacenJpaController.findAlmacenEntities();
        logger.info("-->> BeforeList:");
        for (Almacen almacen : almacenBeforeList) {
            logger.info("-->> almacen:" + almacen);
        }
        Almacen almacenNuevo = null;
        try {

            int maxId = 0;
            for (Almacen l : almacenBeforeList) {
                maxId = l.getId() > maxId ? l.getId() : maxId;
            }

            almacenNuevo = new Almacen();
            
            almacenNuevo.setEsFiscal(Constants.PEDIDO_FISCAL);
            almacenNuevo.setCalle("calle");
            almacenNuevo.setNombre("Almacen nuevo max");
            almacenNuevo.setNumExterior("NEXT");
            almacenNuevo.setNumInterior("NINT");
            almacenNuevo.setPoblacion(new Poblacion(100));
            almacenNuevo.setTelefonos("123456789.12345678");
            almacenNuevo.setComentarios("comentarios");

            almacenJpaController.create(almacenNuevo);

        } catch (Exception ex) {
            logger.info("Test Failed: ", ex);
        }

        logger.info("-->> AfterList:");

        List<Almacen> almacenAfterList = almacenJpaController.findAlmacenEntities();
        for (Almacen almacen : almacenAfterList) {
            logger.info("-->> almacen:" + almacen);
        }

        assertEquals("The # of beans in List: " + (almacenBeforeList.size() + 1) + " != " + almacenAfterList.size(),
                almacenBeforeList.size() + 1, almacenAfterList.size());

    }

    @Test
    @Ignore
    public void testJpaDao() throws SQLException {
        logger.info("============================== INITIALIZED CONTEXT ============================");

        assertNotNull("almacenJpaDao is null.", almacenJpaDao);

        List<Almacen> almacenBeforeList = almacenJpaController.findAlmacenEntities();
        logger.info("-->> BeforeList:");
        for (Almacen almacen : almacenBeforeList) {
            logger.info("-->> almacen:" + almacen);
        }
        Almacen almacenNuevo = null;
        try {

            int maxId = 0;
            for (Almacen l : almacenBeforeList) {
                maxId = l.getId() > maxId ? l.getId() : maxId;
            }

            almacenNuevo = new Almacen();

            almacenNuevo.setId(maxId + 2);

            almacenNuevo.setCalle("calle");
            almacenNuevo.setNombre("Almacen nuevo max");
            almacenNuevo.setNumExterior("NEXT");
            almacenNuevo.setNumInterior("NINT");
            almacenNuevo.setPoblacion(new Poblacion(100));
            almacenNuevo.setTelefonos("123456789.12345678");
            almacenNuevo.setComentarios("comentarios");

            almacenJpaDao.save(almacenNuevo);

        } catch (Exception ex) {
            logger.info("Test Failed: ", ex);
        }

        logger.info("-->> AfterList:");

        List<Almacen> almacenAfterList = almacenJpaController.findAlmacenEntities();
        for (Almacen almacen : almacenAfterList) {
            logger.info("-->> almacen:" + almacen);
        }

        assertEquals("The # of beans in List: " + (almacenBeforeList.size() + 1) + " != " + almacenAfterList.size(),
                almacenBeforeList.size() + 1, almacenAfterList.size());

    }

    public void setAlmacenJpaController(AlmacenJpaController almacenJpaController) {
        this.almacenJpaController = almacenJpaController;
    }

    public void setAlmacenJpaDao(AlmacenJpaDao almacenJpaDao) {
        this.almacenJpaDao = almacenJpaDao;
    }
}
