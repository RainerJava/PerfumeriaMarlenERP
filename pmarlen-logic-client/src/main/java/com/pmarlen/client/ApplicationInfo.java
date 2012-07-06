/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.client;

import com.pmarlen.model.Constants;
import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.beans.PedidoVentaEstado;
import java.io.IOException;
import java.net.ServerSocket;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author alfred
 */

public class ApplicationInfo {

    private String version;
    private Logger logger;
    private final int APPLICATION_PORT = 5001;
    private ServerSocket serverSocket;
    private static ApplicationInfo instance;
    private static ResourceBundle resourceBoundleMesages;    

    private ApplicationInfo() throws BusinessException {
        logger = LoggerFactory.getLogger(ApplicationInfo.class);

        version = "-.-@-";
        ensureSingletonInstanceInSystem();
        loadVersionNubmer();
        logger.debug("Ok created.");
    }

    public static ApplicationInfo getInstance() throws BusinessException {
        if (instance == null) {
            instance = new ApplicationInfo();
        }
        return instance;
    }

    private void loadVersionNubmer() {
        version = Constants.getServerVersion();
    }

    private void ensureSingletonInstanceInSystem() throws BusinessException {
        try {
            serverSocket = new ServerSocket(APPLICATION_PORT);
            logger.info("the socket to ensure unique instance, it's started");
        } catch (IOException ex) {
            logger.error("Error:", "Can't start the socket to ensure unique instance");
            throw new BusinessException(getClass().getSimpleName(), "ANOTHER_CLASS_IS_RUNNING");
        }
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    public static String getLocalizedMessage(String key) {
        if (resourceBoundleMesages == null) {
            resourceBoundleMesages = ResourceBundle.getBundle("messages.ViewMessages");
            /*
            resourceBoundleMesages = ResourceBundle.getBundle("messages.ViewMessages",
            Locale.getDefault(), new ResourceBundle.Control() {
            @Override
            public List<Locale> getCandidateLocales(String baseName, Locale locale) {
            if (baseName == null) {
            throw new NullPointerException();
            }
            if(locale.getLanguage().equals("es")){
            return Arrays.asList(new Locale("es", "MX"));
            } else if(locale.getLanguage().equals("en")){
            return Arrays.asList(new Locale("en", "US"));
            }
            return super.getCandidateLocales(baseName, locale);
            }
            });
             */
        }

        try {
            return resourceBoundleMesages.getString(key);
        } catch (MissingResourceException mre) {
            return key;
        }
    }
    static DecimalFormat df;
    static DecimalFormat dfp;

    public static String formatToCurrency(double c) {
        if (df == null) {
            df = new DecimalFormat("$ ###,###,###,##0.00");
        }
        return df.format(c);
    }
    static SimpleDateFormat sdf;

    public static String formatToPercentage(double c) {
        if (dfp == null) {
            dfp = new DecimalFormat("##0");
        }
        return dfp.format(c * 100.0);
    }
    static SimpleDateFormat sdfLongDate = new SimpleDateFormat("dd/MMM/yy hh:mm");

    public static String formatToLongDate(Date d) {
        return sdfLongDate.format(d);
    }

    public static String formatToSimpeDate(Date d) {
        if (sdf == null) {
            sdf = new SimpleDateFormat("dd/MMM/yyyy");
        }
        return sdf.format(d);
    }
    static DecimalFormat dfpc;

    public static String formatToPostalCode(int codigoPostal) {
        if (dfpc == null) {
            dfpc = new DecimalFormat("00000");
        }
        return dfpc.format(codigoPostal);
    }
    static SimpleDateFormat sdfFechaHora = new SimpleDateFormat("yyyyMMddhhmmss");
    
    static DecimalFormat dfUsuario = new DecimalFormat("0000");
    
    public static String getNumPseudoPedido(PedidoVenta pedidoVenta) {

        String pseudoNumeroPedido = Long.toHexString(
                Long.parseLong(
                sdfFechaHora.format(new Date()) +
                dfUsuario.format(pedidoVenta.getCliente().getId()))).toUpperCase();
        return pseudoNumeroPedido;

    }
}
