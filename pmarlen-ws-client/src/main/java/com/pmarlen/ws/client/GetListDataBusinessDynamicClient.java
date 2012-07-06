package com.pmarlen.ws.client;

import com.pmarlen.model.beans.Cliente;
import com.pmarlen.model.beans.Empresa;
import com.pmarlen.model.beans.Estado;
import com.pmarlen.model.beans.FormaDePago;
import com.pmarlen.model.beans.Linea;
import com.pmarlen.model.beans.Marca;
import com.pmarlen.model.beans.Multimedio;
import com.pmarlen.model.beans.Producto;
import com.pmarlen.model.beans.Usuario;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.pmarlen.wscommons.services.GetListDataBusiness;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetListDataBusinessDynamicClient {

    private static Logger logger = LoggerFactory.getLogger(GetListDataBusinessDynamicClient.class);

    private GetListDataBusinessDynamicClient () {
    }

    public static void main(String args[]) throws Exception {
        // START SNIPPET: client
        //logger.info("==>> 1: init contex");
        //ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"client-beans.xml"});
        //logger.info("==>> 2: contex ok");

        GetListDataBusiness client = null;
        JaxWsProxyFactoryBean factory = null;
        try {
            logger.info("==>> 3:Creating Factory");
            factory = new JaxWsProxyFactoryBean();
            
            factory.setServiceClass(GetListDataBusiness.class);
            logger.info("==>> 3.5:factory.adress ="+factory.getAddress());
            
            factory.setAddress("http://localhost:8020/pmarlen-web/services/GetListDataBusiness");
            
            logger.info("==>> 4:Creating Client");

            Object obj = factory.create();

            logger.info("==>> 4.1:obj.class="+obj.getClass());
            logger.info("==>> 4.2:obj.super class="+obj.getClass().getSuperclass());
            logger.info("==>> 4.3:obj.interfaces="+Arrays.asList(obj.getClass().getInterfaces()));

            client = (GetListDataBusiness)obj;
            logger.info("==>> 5:Invoking services");
            logger.info("==>> 5.1:Invoking service getUsuarioList()");

            List<Usuario> listaUsuarios = client.getUsuarioList();

            for(Usuario x: listaUsuarios) {
                logger.info("\t-> Usuario:" + ReflectionToStringBuilder.toString(x, ToStringStyle.SIMPLE_STYLE));
            }

            logger.info("==>> 5.2:Invoking service getEmpresaList()");

            List<Empresa> listaEmpresa = client.getEmpresaList();

            for(Empresa x: listaEmpresa) {
                logger.info("\t-> Empresa:" + ReflectionToStringBuilder.toString(x, ToStringStyle.SIMPLE_STYLE));
            }

            logger.info("==>> 5.3:Invoking service getLineaList()");

            List<Linea> listaLinea = client.getLineaList();

            for(Linea x: listaLinea) {
                logger.info("\t-> Linea:" + ReflectionToStringBuilder.toString(x, ToStringStyle.SIMPLE_STYLE));
            }

            logger.info("==>> 5.4:Invoking service getMarcaList()");

            List<Marca> listaMarca = client.getMarcaList();

            for(Marca x: listaMarca) {
                logger.info("\t-> Marca:" + ReflectionToStringBuilder.toString(x, ToStringStyle.SIMPLE_STYLE));
            }

            logger.info("==>> 5.5:Invoking service getProductoList()");

            List<Producto> listaProducto = client.getProductoList();

            for(Producto x: listaProducto) {
                logger.info("\t-> Producto:" + ReflectionToStringBuilder.toString(x, ToStringStyle.SIMPLE_STYLE));
            }

            logger.info("==>> 5.6:Invoking service getProductoMultimedioList()");

            List<Multimedio> listaMultimedio = client.getProductoMultimedioList();
            logger.info("==>> 5.6.1:listaMultimedio is null? "+(listaMultimedio == null));
            if(listaMultimedio!= null){
                File outputDir=new File("./outputImeges");
                outputDir.mkdirs();
                for(Multimedio x: listaMultimedio) {
                    logger.info("\t-> Multimedio: NombreArchivo=" + x.getNombreArchivo()+", MimeType="+x.getMimeType());
                    FileOutputStream fosImage = new FileOutputStream(outputDir.getAbsolutePath()+"/"+x.getNombreArchivo());
                    logger.info("\t\t-> Serializando Multimedio:" );
                    fosImage.write(x.getContenido());
                    fosImage.close();
                }
            }

            logger.info("==>> 5.7:Invoking service getFormaDePagoList()");

            List<FormaDePago> listaFormaDePago = client.getFormaDePagoList();
            if(listaFormaDePago != null){
                for(FormaDePago x: listaFormaDePago) {
                    logger.info("\t-> FormaDePago:" + ReflectionToStringBuilder.toString(x, ToStringStyle.SIMPLE_STYLE));
                }
            }

            logger.info("==>> 5.8:Invoking service getEstadoList()");

            List<Estado> listaEstado = client.getEstadoList();
            if(listaEstado != null){
                for(Estado x: listaEstado) {
                    logger.info("\t-> Estado:" + ReflectionToStringBuilder.toString(x, ToStringStyle.SIMPLE_STYLE));
                }
            }

            logger.info("==>> 5.9:Invoking service getClienteList()");

            List<Cliente> listaCliente = client.getClienteList();
            if(listaCliente != null){
                for(Cliente x: listaCliente) {
                    logger.info("\t-> Cliente:" + ReflectionToStringBuilder.toString(x, ToStringStyle.SIMPLE_STYLE));
                }
            }

            logger.info("==>> 5.9:Invoking service getClienteList()");

            String serverVersion = client.getServerVersion();
            logger.info("\t-> serverVersion:"+serverVersion);

        } catch(Exception e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }        
    }
}
