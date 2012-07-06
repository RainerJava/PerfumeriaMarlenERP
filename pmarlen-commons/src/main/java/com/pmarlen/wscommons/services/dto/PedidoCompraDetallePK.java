
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
 * Class for mapping JPA Entity of Table Pedido_Compra_Detalle_P_K.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2011/06/09 05:47
 */




public class PedidoCompraDetallePK implements java.io.Serializable {
    
    /**
    * pedido compra id
    */
    private Integer pedidoCompraId;
    
    /**
    * producto id
    */
    private Integer productoId;

    /** 
     * Default Constructor
     */
    public PedidoCompraDetallePK() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public PedidoCompraDetallePK( Integer pedidoCompraId, Integer productoId ) {
        this.pedidoCompraId 	= 	pedidoCompraId;
        this.productoId 	= 	productoId;

    }
    
    /**
     * Getters and Setters
     */
    public Integer getPedidoCompraId() {
        return this.pedidoCompraId;
    }

    public void setPedidoCompraId(Integer v) {
        this.pedidoCompraId = v;
    }

    public Integer getProductoId() {
        return this.productoId;
    }

    public void setProductoId(Integer v) {
        this.productoId = v;
    }


    public int hashCode() {
        int hash = 0;
        hash = ( (pedidoCompraId != null ? pedidoCompraId.hashCode() : 0 ) + 
		 (productoId != null ? productoId.hashCode() : 0 ) );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof PedidoCompraDetallePK)) {
            return false;
        }

    	PedidoCompraDetallePK other = (PedidoCompraDetallePK ) o;
        if ( (this.pedidoCompraId == null && other.pedidoCompraId != null) || (this.pedidoCompraId != null && !this.pedidoCompraId.equals(other.pedidoCompraId)) || 
             (this.productoId == null && other.productoId != null) || (this.productoId != null && !this.productoId.equals(other.productoId))) {
            return false;
        }


    	return true;
    }

    public String toString() {
        return "com.pmarlen.model.beans.PedidoCompraDetallePK[pedidoCompraId = "+pedidoCompraId+" , productoId = "+productoId+ "]";
    }

}
