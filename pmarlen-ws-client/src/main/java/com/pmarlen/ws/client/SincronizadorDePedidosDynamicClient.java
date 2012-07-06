package com.pmarlen.ws.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.pmarlen.wscommons.services.SincronizadorDePedidos;
import com.pmarlen.wscommons.services.dto.PedidoVenta;
import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SincronizadorDePedidosDynamicClient {

    private static Logger logger = LoggerFactory.getLogger(SincronizadorDePedidosDynamicClient.class);

    private SincronizadorDePedidosDynamicClient () {
    }

    public static void main(String args[]) throws Exception {
        // START SNIPPET: client
        //logger.info("==>> 1: init contex");
        //ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"client-beans.xml"});
        //logger.info("==>> 2: contex ok");

        SincronizadorDePedidos client = null;
        JaxWsProxyFactoryBean factory = null;
        try {
            logger.info("==>> 3:Creating Factory");
            factory = new JaxWsProxyFactoryBean();
            
            factory.setServiceClass(SincronizadorDePedidos.class);
            logger.info("==>> 3.5:factory.adress ="+factory.getAddress());
            
            factory.setAddress("http://localhost:8020/pmarlen-web/services/SincronizadorDePedidos");
            
            logger.info("==>> 4:Creating Client");

            Object obj = factory.create();

            logger.info("==>> 4.1:obj.class="+obj.getClass());
            logger.info("==>> 4.2:obj.super class="+obj.getClass().getSuperclass());
            logger.info("==>> 4.3:obj.interfaces="+Arrays.asList(obj.getClass().getInterfaces()));

            client = (SincronizadorDePedidos)obj;
            logger.info("==>> 5:Invoking services");
            logger.info("==>> 5.1:Invoking service getUsuarioList()");

            client.enviarPedido("test1", "temporal", new ArrayList<PedidoVenta>());

            logger.info("==>> 5.9:Invoking service getClienteList()");

        } catch(Exception e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }        
    }
}
