/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.operation;

import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.beans.Producto;
import com.pmarlen.model.controller.PedidoVentaJpaController;
import com.pmarlen.model.controller.ProductoJpaController;
import com.pmarlen.web.security.managedbean.SessionUserMB;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author VEAXX9M
 */
public class OperacionesEnAlmacenesMB {

    @Autowired
    private ProductoJpaController productoJpaController;
    @Autowired
    private PedidoVentaJpaController pedidoVentaJpaController;

    @Autowired
    private SessionUserMB sessionUserMB;

    private Logger logger;

    public OperacionesEnAlmacenesMB() {
        logger = LoggerFactory.getLogger(PedidosPreVisorMB.class);
    }


    public List<Producto> getProductoConMovimientos(){
        List<Producto> productoList = productoJpaController.findAllProductoConMovimientosEntities();
        logger.debug("==>> getProductoConMovimientos: ");
        for(Producto productoConMovimientos: productoList){
            logger.debug("\t==>> productoConMovimientos: "+productoConMovimientos+" -> AlmacenProductoCollection:"+productoConMovimientos.getAlmacenProductoCollection());
        }

        return productoList;
    }

    public void setPedidoVentaJpaController(PedidoVentaJpaController pedidoVentaJpaController) {
        this.pedidoVentaJpaController = pedidoVentaJpaController;
    }

    public void setSessionUserMB(SessionUserMB sessionUserMB) {
        this.sessionUserMB = sessionUserMB;
    }

    public void setProductoJpaController(ProductoJpaController productoJpaController) {
        this.productoJpaController = productoJpaController;
    }
    
}
