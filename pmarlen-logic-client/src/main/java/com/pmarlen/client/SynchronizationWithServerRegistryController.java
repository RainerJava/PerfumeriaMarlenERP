/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author alfred
 */
@Repository("synchronizationWithServerRegistryController")
public class SynchronizationWithServerRegistryController {

    private Logger logger;
    //public static final long MAX_PERIOD_OFLINE = 60000 * 60 * 24 * 3; // 3 dias.
    public static final long MIN_PERIOD_OFLINE = 60000 * 60 * 72;     // 72 hrs.
    
    private static boolean syncronizatedInThisSession = false;

    /**
     * Creates a new instance of <code>SynchronizationWithServerRegistryController</code> without detail message.
     */
    public SynchronizationWithServerRegistryController() {
        logger = LoggerFactory.getLogger(SynchronizationWithServerRegistryController.class);
    }

    long getLastSyncronization() throws Exception {
        logger.debug("->getLastSyncronization():");

        FileInputStream fis = null;

        DataInputStream dis = null;


        long last = System.currentTimeMillis();

        Properties prop = new Properties();
        try {
            prop.load(this.getClass().getResourceAsStream("/jdbc.properties"));
            String userHomeDir = System.getProperty("user.home", ".");
            //String systemDir = userHomeDir + File.separator + "." + prop.getProperty("app.dataDirectory", "app.dataDirectory");
            String systemDir = new java.io.File(".").getAbsolutePath();

            fis = new FileInputStream(systemDir + File.separator + "syncronization.bin");
            dis = new DataInputStream(fis);

            last = dis.readLong();

            logger.debug("->getLastSyncronization(): ok readed last =" + new Date(last));
            fis.close();

        } catch (FileNotFoundException ex1) {
            //updateLastSyncronization();
            throw new IllegalStateException("No habia nada:" + ex1.getMessage());
        } catch (IOException ex3) {
            logger.error("No debe de pasar:", ex3);
            throw ex3;
        } finally {
            if (fis != null) {
                fis.close();
            }
        }

        return last;

    }

    public void updateLastSyncronization() {
        logger.debug("->updateLastSyncronization():");
        syncronizatedInThisSession = true;

        FileOutputStream fos = null;

        DataOutputStream dos = null;

        long last = System.currentTimeMillis();
        Properties prop = new Properties();
        try {
            prop.load(this.getClass().getResourceAsStream("/jdbc.properties"));
            String userHomeDir = System.getProperty("user.home", ".");
            //String systemDir = userHomeDir + File.separator + "." + prop.getProperty("app.dataDirectory", "app.dataDirectory");
            String systemDir = new java.io.File(".").getAbsolutePath();
            
            fos = new FileOutputStream(systemDir + File.separator + "syncronization.bin");
            dos = new DataOutputStream(fos);

            dos.writeLong(last);
            dos.writeLong(last);
            dos.writeLong(last);

            dos.close();
            fos.close();
            fos = null;
            logger.debug("->updateLastSyncronization(): ok last <- today =" + new Date(last));

        } catch (FileNotFoundException ex5) {
            logger.error("No se pudo crear 5", ex5);
        } catch (IOException ex6) {
            logger.error("No se pudo cerrar, nuca debio haber pasado !", ex6);
        }
    }
    
    public boolean needSyncronization() {
        long today = System.currentTimeMillis();
        long lastSync;
        try {
            lastSync = getLastSyncronization();
        } catch (Exception ex) {
            logger.debug("-> needSyncronization(): forced (" + ex + ")!");
            return true;
        }
        return ((today - lastSync) >= MIN_PERIOD_OFLINE);
    }
    
    /**
     * @return the syncronizatedInThisSession
     */
    public boolean isSyncronizatedInThisSession() {
        return syncronizatedInThisSession;
    }
}
