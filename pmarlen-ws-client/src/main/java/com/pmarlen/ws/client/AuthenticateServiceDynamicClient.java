package com.pmarlen.ws.client;

import com.pmarlen.model.beans.Marca;
import com.pmarlen.model.beans.Usuario;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.pmarlen.wscommons.services.AuthenticateService;
import java.util.List;


public class AuthenticateServiceDynamicClient {

    private AuthenticateServiceDynamicClient () {
    }

    public static void main(String args[]) throws Exception {
        // START SNIPPET: client
        //System.out.println("==>> 1: init contex");
        //ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"client-beans.xml"});
        //System.out.println("==>> 2: contex ok");

        AuthenticateService client = null;
        JaxWsProxyFactoryBean factory = null;
        try {
            System.out.println("==>> 3:Creating Factory");
            factory = new JaxWsProxyFactoryBean();
            
            factory.setServiceClass(AuthenticateService.class);
            factory.setAddress("http://localhost:8020/pmarlen-web/services/AuthenticateService");
            
            System.out.println("==>> 4:Creating Client");
            
            client = (AuthenticateService)factory.create();
            
            System.out.println("==>> 5:Invoking service");
            String user="test1";
            String password="temporalx";

            Usuario usuarioAuthenticated = client.authenticateUsuario(user,password);
            System.out.println("OK: usuarioAuthenticated : " + usuarioAuthenticated );

            List<Marca> lista = client.getFirstData();

            for(Marca marca: lista) {
                System.out.println("\t-> Marca:" + marca.getId()+","+marca.getNombre()+", Linea:"+marca.getLinea().getId()+", "+marca.getLinea().getNombre());
            }

        } catch(Exception e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }
        
        // END SNIPPET: client
    }
}
