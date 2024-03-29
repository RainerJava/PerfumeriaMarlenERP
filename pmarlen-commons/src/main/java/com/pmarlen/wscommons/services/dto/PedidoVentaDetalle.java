
package com.pmarlen.wscommons.services.dto;

import java.io.Serializable;
import java.util.Set;
import java.util.Collection;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Embeddable;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.EmbeddedId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class for mapping JPA Entity of Table Pedido_Venta_Detalle.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2011/06/09 05:47
 */



public class PedidoVentaDetalle implements java.io.Serializable {
    private PedidoVentaDetallePK pedidoVentaDetallePK;
    private int cantidad;
    private double precioBase;
    private double descuentoAplicado;
    private PedidoVenta pedidoVenta;
    private Producto producto;

    /** 
     * Default Constructor
     */
    public PedidoVentaDetalle() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public PedidoVentaDetalle( PedidoVentaDetallePK pedidoVentaDetallePK ) {
        this.pedidoVentaDetallePK 	= 	pedidoVentaDetallePK;

    }
    
    /**
     * Getters and Setters
     */
    public PedidoVentaDetallePK getPedidoVentaDetallePK() {
        return this.pedidoVentaDetallePK;
    }

    public void setPedidoVentaDetallePK(PedidoVentaDetallePK v) {
        this.pedidoVentaDetallePK = v;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int v) {
        this.cantidad = v;
    }

    public double getPrecioBase() {
        return this.precioBase;
    }

    public void setPrecioBase(double v) {
        this.precioBase = v;
    }

    public double getDescuentoAplicado() {
        return this.descuentoAplicado;
    }

    public void setDescuentoAplicado(double v) {
        this.descuentoAplicado = v;
    }

    public PedidoVenta getPedidoVenta() {
        return this.pedidoVenta;
    }

    public void setPedidoVenta(PedidoVenta v) {
        this.pedidoVenta = v;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public void setProducto(Producto v) {
        this.producto = v;
    }


    public int hashCode() {
        int hash = 0;
        hash = (pedidoVentaDetallePK != null ? pedidoVentaDetallePK.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof PedidoVentaDetalle)) {
            return false;
        }

    	PedidoVentaDetalle other = (PedidoVentaDetalle ) o;
        if ( (this.pedidoVentaDetallePK == null && other.pedidoVentaDetallePK != null) || (this.pedidoVentaDetallePK != null && !this.pedidoVentaDetallePK.equals(other.pedidoVentaDetallePK))) {
            return false;
        }


    	return true;
    }

    public String toString() {
        return "com.pmarlen.model.beans.PedidoVentaDetalle[pedidoVentaDetallePK = "+pedidoVentaDetallePK+ "]";
    }

}
