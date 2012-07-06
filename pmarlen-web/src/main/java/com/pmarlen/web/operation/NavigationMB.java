package com.pmarlen.web.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author alfredo
 */
public class NavigationMB {

    private final Logger logger = LoggerFactory.getLogger(NavigationMB.class);

    //--------------------------------------------
    public String verProductosXLineaMarca() {
        logger.debug("===>>verProductosXLineaMarca");
        return "verProductosXLineaMarca";
    }

    public String verProductosXEmpresaMarca() {
        logger.debug("===>>verProductosXEmpresaMarca");
        return "verProductosXEmpresaMarca";
    }

    public String pedidoSimple() {
        logger.debug("===>>pedidoSimple");
        return "pedidoSimple";
    }

    public String verProductosEnPedidoSimple() {
        logger.debug("===>>verProductosEnPedidoSimple");
        return "verProductosEnPedidoSimple";
    }

    public String listarPedidosVentas() {
        logger.debug("===>>listarPedidosVentas");
        return "listarPedidosVentas";
    }

    public String pedidoNuevo() {
        logger.debug("===>>pedidoNuevo");
        return "pedidoNuevo";
    }

    public String editarPedido() {
        logger.debug("===>>editarPedido");
        return "editarPedido";
    }

    public String productosEnAlmacenes() {
        logger.debug("===>>productosEnAlmacenes");
        return "productosEnAlmacenes";
    }
}
