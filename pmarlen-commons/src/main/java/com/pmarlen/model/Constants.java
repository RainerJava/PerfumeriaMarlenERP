package com.pmarlen.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Constants
 */
public class Constants {

    public static final String PERFIL_ROOT = "root";
    public static final String PERFIL_ITHD_USER = "ithd_user";
    public static final String PERFIL_FINAL_USER = "final_user";
    public static final String PERFIL_ADMINISTRATOR = "administrator";
    public static final String PERFIL_ANALYST = "analyst";
    public static final String PERFIL_REGISTER = "register";
    public static final String PERFIL_GUEST = "guest";
    public static final int CREATE_ACTION = 1;
    public static final int UPDATE_ACTION = 2;
    public static final int PEDIDO_NOFISCAL = 0;
    public static final int PEDIDO_FISCAL = 1;
    
    public static final int ESTADO_CAPTURADO = 1;
    public static final int ESTADO_SINCRONIZADO = 2;
    public static final int ESTADO_VERIFICADO = 3;  
    public static final int ESTADO_SURTIDO = 4;
    public static final int ESTADO_FACTURADO = 5;    
    public static final int ESTADO_ENVIADO = 6;
    public static final int ESTADO_ENTREGADO = 7;
    public static final int ESTADO_DEVUELTO = 8;
    public static final int ESTADO_CANCELADO = 9;
    
    public static final int CREACION = 10;
    public static final int EXTRACCION_A_FIS = 21;
    public static final int EXTRACCION_A_FIS_BAL = 22;
    public static final int EXTRACCION_A_NOF = 23;
    public static final int EXTRACCION_A_NOF_BAL = 24;
    public static final int AGREGACION_A_FIS = 31;
    public static final int AGREGACION_A_FIS_BAL = 32;
    public static final int AGREGACION_A_NOF = 33;
    public static final int AGREGACION_A_NOF_BAL = 34;
    public static final int TIPO_MOV_MERMA = 40;
    public static final int TIPO_MOV_MODIFICACION_COSTO_O_PRECIO = 50;
    private static final String VERSION_FILE_RESOURCE = "/com/tracktopell/util/version/file/Version.properties";

    private static Logger logger = LoggerFactory.getLogger(Constants.class);

    public static String getServerVersion() {
        Properties pro = new Properties();
        String version = null;
        try {
            InputStream resourceAsStream = Constants.class.getResourceAsStream(VERSION_FILE_RESOURCE);
            if(resourceAsStream == null){
                throw new IOException("The resource:"+VERSION_FILE_RESOURCE+" doesn't exist!");
            }
            pro.load(resourceAsStream);
            logger.info("->version=" + pro);
            version = pro.getProperty("version.major") + "."
                    + pro.getProperty("version.minor") + "@"
                    + pro.getProperty("version.revision");

        } catch (IOException ex) {
            logger.error("Can't load Version properties:", ex);
            version = "-.-.-";
        }
        return version;
    }
}
