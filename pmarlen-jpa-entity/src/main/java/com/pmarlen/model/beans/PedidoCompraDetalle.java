
package com.pmarlen.model.beans;

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
 * Class for mapping JPA Entity of Table Pedido_Compra_Detalle.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2012/04/24 05:24
 */



@Entity
@Table(name = "PEDIDO_COMPRA_DETALLE")
public class PedidoCompraDetalle implements java.io.Serializable {
    private static final long serialVersionUID = 1287645725;
    @EmbeddedId
    private PedidoCompraDetallePK pedidoCompraDetallePK;
    @Basic(optional = false)
    @Column(name = "CANTIDAD")
    private int cantidad;
    @Basic(optional = false)
    @Column(name = "PRECIO_BASE")
    private double precioBase;
    @Basic(optional = false)
    @Column(name = "DESCUENTO_APLICADO")
    private double descuentoAplicado;
    @JoinColumn(name = "PEDIDO_COMPRA_ID" , referencedColumnName = "ID",  insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PedidoCompra pedidoCompra;
    @JoinColumn(name = "PRODUCTO_ID" , referencedColumnName = "ID",  insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;

    /** 
     * Default Constructor
     */
    public PedidoCompraDetalle() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public PedidoCompraDetalle( PedidoCompraDetallePK pedidoCompraDetallePK, PedidoCompra pedidoCompra, Producto producto ) {
        this.pedidoCompraDetallePK 	= 	pedidoCompraDetallePK;
        this.pedidoCompra 	= 	pedidoCompra;
        this.producto 	= 	producto;

    }
    
    /**
     * Getters and Setters
     */
    public PedidoCompraDetallePK getPedidoCompraDetallePK() {
        return this.pedidoCompraDetallePK;
    }

    public void setPedidoCompraDetallePK(PedidoCompraDetallePK v) {
        this.pedidoCompraDetallePK = v;
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

    public PedidoCompra getPedidoCompra() {
        return this.pedidoCompra;
    }

    public void setPedidoCompra(PedidoCompra v) {
        this.pedidoCompra = v;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public void setProducto(Producto v) {
        this.producto = v;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash = (pedidoCompraDetallePK != null ? pedidoCompraDetallePK.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof PedidoCompraDetalle)) {
            return false;
        }

    	PedidoCompraDetalle other = (PedidoCompraDetalle ) o;
        if ( (this.pedidoCompraDetallePK == null && other.pedidoCompraDetallePK != null) || (this.pedidoCompraDetallePK != null && !this.pedidoCompraDetallePK.equals(other.pedidoCompraDetallePK))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.model.beans.PedidoCompraDetalle[pedidoCompraDetallePK = "+pedidoCompraDetallePK+ "]";
    }

}
