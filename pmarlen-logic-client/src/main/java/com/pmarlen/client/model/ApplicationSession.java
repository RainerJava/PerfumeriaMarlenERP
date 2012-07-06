/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.client.model;

import com.pmarlen.model.beans.Marca;
import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.beans.Producto;
import com.pmarlen.model.beans.Usuario;
import org.springframework.stereotype.Component;


/**
 *
 * @author alfred
 */
@Component("applicationSession")
public class ApplicationSession {
    
    private Usuario usuario;

    private PedidoVenta pedidoVenta;

    private Marca marcaPorLinea;

    private Marca marcaPorEmpresa;

    private Producto productoBuscadoActual;
    
    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the pedidoVenta
     */
    public PedidoVenta getPedidoVenta() {
        return pedidoVenta;
    }

    /**
     * @param pedidoVenta the pedidoVenta to set
     */
    public void setPedidoVenta(PedidoVenta pedidoVenta) {
        this.pedidoVenta = pedidoVenta;
    }

    /**
     * @return the marcaPorEmpresa
     */
    public Marca getMarcaPorEmpresa() {
        return marcaPorEmpresa;
    }

    /**
     * @param marcaPorEmpresa the marcaPorEmpresa to set
     */
    public void setMarcaPorEmpresa(Marca marcaPorEmpresa) {
        this.marcaPorEmpresa = marcaPorEmpresa;
    }

    /**
     * @return the marcaPorLinea
     */
    public Marca getMarcaPorLinea() {
        return marcaPorLinea;
    }

    /**
     * @param marcaPorLinea the marcaPorLinea to set
     */
    public void setMarcaPorLinea(Marca marcaPorLinea) {
        this.marcaPorLinea = marcaPorLinea;
    }

    /**
     * @return the productoBuscadoActual
     */
    public Producto getProductoBuscadoActual() {
        return productoBuscadoActual;
    }

    /**
     * @param productoBuscadoActual the productoBuscadoActual to set
     */
    public void setProductoBuscadoActual(Producto productoBuscadoActual) {
        this.productoBuscadoActual = productoBuscadoActual;
    }
    
}
