/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.operation;

import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.controller.PedidoVentaJpaController;
import com.pmarlen.web.security.managedbean.SessionUserMB;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author VEAXX9M
 */
public class PedidosPreVisorMB {

    @Autowired
    private PedidoVentaJpaController pedidoVentaJpaController;
    @Autowired
    private SessionUserMB sessionUserMB;

    private final Logger logger = LoggerFactory.getLogger(PedidosPreVisorMB.class);

    public PedidosPreVisorMB() {

    }


    public List<PedidoVenta> getPedidoVentaList(){
        List<PedidoVenta> pedidoVentaList = pedidoVentaJpaController.findPedidoVentaEntities();
        logger.info("-->>> getPedidoVentaList: pedidoVentaList="+pedidoVentaList);
        return pedidoVentaList;
    }

    public void setPedidoVentaJpaController(PedidoVentaJpaController pedidoVentaJpaController) {
        this.pedidoVentaJpaController = pedidoVentaJpaController;
    }

    public void setSessionUserMB(SessionUserMB sessionUserMB) {
        this.sessionUserMB = sessionUserMB;
    }
    
}
