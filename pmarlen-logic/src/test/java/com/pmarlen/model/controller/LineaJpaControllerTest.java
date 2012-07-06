package com.pmarlen.model.controller;

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
public class LineaJpaControllerTest {

    final Logger logger = LoggerFactory.getLogger(LineaJpaControllerTest.class);
    @Autowired
    private LineaJpaController lineaJpaController;

    @Test
    public void testHibernateTemplate() throws SQLException {
        logger.info("============================== INITIALIZED CONTEXT ============================");

        assertNotNull("lineaJpaController is null.", lineaJpaController);

        List<Linea> lineaBeforeList = lineaJpaController.findLineaEntities();
        logger.info("-->> BeforeList:");
        for (Linea linea : lineaBeforeList) {
            logger.info("-->> linea:" + linea);
        }
        Linea lineaNueva = null;
        try {
            lineaNueva = new Linea();

            lineaNueva.setNombre("Linea nueva, id auto");

            lineaJpaController.create(lineaNueva);
            
        } catch (Exception ex) {
            logger.info("Test Failed: ", ex);
        }

        logger.info("-->> AfterList:");

        List<Linea> lineaAfterList = lineaJpaController.findLineaEntities();
        for (Linea linea : lineaAfterList) {
            logger.info("-->> linea:" + linea);
        }

        assertEquals("The # of beans in List: " + (lineaBeforeList.size() + 1) + " != " + lineaAfterList.size(),
                lineaBeforeList.size() + 1, lineaAfterList.size());

    }

    public void setLineaJpaController(LineaJpaController lineaJpaController) {
        this.lineaJpaController = lineaJpaController;
    }
}
