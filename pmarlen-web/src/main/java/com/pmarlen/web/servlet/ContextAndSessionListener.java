/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.servlet;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web application lifecycle listener.
 * @author aestrada
 */
public class ContextAndSessionListener implements ServletContextListener, HttpSessionListener {
    
    Logger logger = LoggerFactory.getLogger(ContextAndSessionListener.class);
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SimpleDateFormat sdfDefault  = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
        SimpleDateFormat sdfExtended = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS zzzzzz (Z)");
        Date dateSystem = new Date();
        logger.debug("-->>contextInitialized: TimeZone.getDefault()="+TimeZone.getDefault().getDisplayName()+
                ", Time=defaultformat:"+sdfDefault.format(dateSystem)+", ExtendedFormat:"+sdfExtended.format(dateSystem));  
        final String AmericaMexico_City_TZ = "America/Mexico_City";
        logger.debug("----->>> Setting the TimeZone: for "+AmericaMexico_City_TZ+", avialables:"+Arrays.asList(TimeZone.getAvailableIDs()));        
        TimeZone.setDefault(TimeZone.getTimeZone(AmericaMexico_City_TZ));
        logger.debug("----->>> Changed TimeZone ?"+TimeZone.getDefault());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.debug("-->>contextDestroyed");
    }

    @Override
    public void sessionCreated(HttpSessionEvent hse) {
        logger.debug("-->>["+hse.getSession().getId()+"]sessionCreated");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent hse) {
        logger.debug("-->>["+hse.getSession().getId()+"]sessionDestroyed");
    }
}
