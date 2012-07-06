/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.client;

import com.pmarlen.wscommons.services.GetListDataBusiness;
import com.pmarlen.wscommons.services.SincronizadorDePedidos;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author alfred
 */
public class WebServiceConnectionConfig {

    public static final String WEBSERVICE_CONNECTION_PROPERTIES_FILE     = "/config/WebServiceConnection.properties";
    public static final String WEBSERVICE_CONNECTION_PROPERTIES_RESOURCE = "/config/WebServiceConnection.properties";
    private String developmentHost;
    private String productionHost;
    private int runningMode = DEVELOPMENT_MODE;
    private static int DEVELOPMENT_MODE = 0;
    private static int PRODUCTION_MODE = 1;
    private static Logger logger;
    private static WebServiceConnectionConfig instance;

    private WebServiceConnectionConfig() {
        developmentHost = "localhost:8080";
        productionHost = "perfumeriamarlen.dyndns.org:80";
        runningMode = DEVELOPMENT_MODE;
    }

    private static String replaceEndPoint(String url, String host) {
        int i1 = url.indexOf("://") + 3;
        int i2 = url.indexOf("/", i1);

        return url.substring(0, i1) + host + url.substring(i2);
    }

    public GetListDataBusiness getGetListDataBusiness() {
        GetListDataBusiness client = null;
        JaxWsProxyFactoryBean factory = null;
        factory = new JaxWsProxyFactoryBean();

        factory.setServiceClass(GetListDataBusiness.class);
        if (runningMode == DEVELOPMENT_MODE) {
            factory.setAddress("http://" + developmentHost + "/pmarlen-web/services/GetListDataBusiness");
        } else {
            factory.setAddress("http://" + productionHost + "/pmarlen-web/services/GetListDataBusiness");
        }

        client = (GetListDataBusiness) factory.create();

        return client;
    }

    public SincronizadorDePedidos getSincronizadorDePedidos() {
        SincronizadorDePedidos client = null;
        JaxWsProxyFactoryBean factory = null;
        factory = new JaxWsProxyFactoryBean();

        factory.setServiceClass(SincronizadorDePedidos.class);
        if (runningMode == DEVELOPMENT_MODE) {
            factory.setAddress("http://" + developmentHost + "/pmarlen-web/services/SincronizadorDePedidos");
        } else {
            factory.setAddress("http://" + productionHost + "/pmarlen-web/services/SincronizadorDePedidos");
        }

        client = (SincronizadorDePedidos) factory.create();

        return client;
    }

    
    public static WebServiceConnectionConfig getInstance() {
        if (logger == null) {
            logger = LoggerFactory.getLogger(WebServiceConnectionConfig.class);
        }

        if (instance == null) {
            logger.info("Creating the properties for read the config");
            InputStream is = null;
            instance = new WebServiceConnectionConfig();
            Properties properties = new Properties();
            try {                
                Properties prop = new Properties();				
				prop.load(WebServiceConnectionConfig.class.getResourceAsStream("/jdbc.properties"));				
                //String userHomeDir = System.getProperty("user.home", ".");
                //String systemDir = userHomeDir + File.separator + "." + prop.getProperty("app.dataDirectory", "app.dataDirectory");
				String systemDir = ".";
				String fileToRead = systemDir + WEBSERVICE_CONNECTION_PROPERTIES_FILE;
                is = new FileInputStream(fileToRead);
                logger.info("Ok, then Reading from local File: " + fileToRead);
            } catch (FileNotFoundException ex) {
                logger.info("OK, now try to read from Resoruce in Classpath: " + WEBSERVICE_CONNECTION_PROPERTIES_RESOURCE);
                is = WebServiceConnectionConfig.class.getResourceAsStream(WEBSERVICE_CONNECTION_PROPERTIES_RESOURCE);
            } catch (IOException ex) {
                logger.info("OK, now try to read from Resoruce in Classpath: " + WEBSERVICE_CONNECTION_PROPERTIES_RESOURCE);
                is = WebServiceConnectionConfig.class.getResourceAsStream(WEBSERVICE_CONNECTION_PROPERTIES_RESOURCE);
            }

            try {
                if (is == null) {
                    throw new IOException("File and Resource for WebService Configuration, not found");
                }
                properties.load(is);
                instance.developmentHost = properties.getProperty("development.host", "localhost:80");
                instance.productionHost = properties.getProperty("production.host", "localhost:80");
                instance.runningMode = properties.getProperty("running.mode", "production").
                        equalsIgnoreCase("production") ? PRODUCTION_MODE
                        : DEVELOPMENT_MODE;
                logger.info("Ok, configured :instance.developmentHost=" + instance.developmentHost + ", instance.productionHost=" + instance.productionHost + ", instance.runningMode=" + (instance.runningMode == PRODUCTION_MODE ? "PRODUCTION_MODE" : "DEVELOPMENT_MODE"));
            } catch (IOException ex) {
                logger.debug("loading WebServiceConnection.properties:", ex);
            }
        }
        return instance;
    }

    /**
     * @param aDEVELOPMENT_MODE the DEVELOPMENT_MODE to set
     */
    public static void setDEVELOPMENT_MODE(int aDEVELOPMENT_MODE) {
        DEVELOPMENT_MODE = aDEVELOPMENT_MODE;
    }

    /**
     * @param aPRODUCTION_MODE the PRODUCTION_MODE to set
     */
    public static void setPRODUCTION_MODE(int aPRODUCTION_MODE) {
        PRODUCTION_MODE = aPRODUCTION_MODE;
    }

    /**
     * @return the developmentHost
     */
    public String getDevelopmentHost() {
        return developmentHost;
    }

    /**
     * @param developmentHost the developmentHost to set
     */
    public void setDevelopmentHost(String developmentHost) {
        this.developmentHost = developmentHost;
    }

    /**
     * @return the productionHost
     */
    public String getProductionHost() {
        return productionHost;
    }

    /**
     * @param productionHost the productionHost to set
     */
    public void setProductionHost(String productionHost) {
        this.productionHost = productionHost;
    }

    /**
     * @return the runningMode
     */
    public int getRunningMode() {
        return runningMode;
    }

    /**
     * @param runningMode the runningMode to set
     */
    public void setRunningMode(int runningMode) {
        this.runningMode = runningMode;
    }

    public String getGetListDataBusinessDelegateServiceWSDL() {
        if (runningMode == DEVELOPMENT_MODE) {
            return "http://" + this.developmentHost + "/EnterpriseServices/GetListDataBusinessDelegateService?WSDL";
        } else if (runningMode == PRODUCTION_MODE) {
            return "http://" + this.productionHost + "/EnterpriseServices/GetListDataBusinessDelegateService?WSDL";
        } else {
            throw new IllegalStateException();
        }
    }

    public String getSetDataFromRemoteClientBusinessDelegateServiceWSDL() {
        if (runningMode == DEVELOPMENT_MODE) {
            return "http://" + this.developmentHost + "/EnterpriseServices/SetDataFromRemoteClientBusinessDelegateService?WSDL";
        } else if (runningMode == PRODUCTION_MODE) {
            return "http://" + this.productionHost + "/EnterpriseServices/SetDataFromRemoteClientBusinessDelegateService?WSDL";
        } else {
            throw new IllegalStateException();
        }
    }
}
