
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
 * Class for mapping JPA Entity of Table Pedido_Compra_Estado_P_K.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2012/04/24 05:24
 */



@Embeddable

public class PedidoCompraEstadoPK implements java.io.Serializable {
    private static final long serialVersionUID = 901833818;
    
    /**
    * pedido compra id
    */
    @Basic(optional = false)
    @Column(name = "PEDIDO_COMPRA_ID")
    private Integer pedidoCompraId;
    
    /**
    * estado id
    */
    @Basic(optional = false)
    @Column(name = "ESTADO_ID")
    private Integer estadoId;

    /** 
     * Default Constructor
     */
    public PedidoCompraEstadoPK() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public PedidoCompraEstadoPK( Integer pedidoCompraId, Integer estadoId ) {
        this.pedidoCompraId 	= 	pedidoCompraId;
        this.estadoId 	= 	estadoId;

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

    public Integer getEstadoId() {
        return this.estadoId;
    }

    public void setEstadoId(Integer v) {
        this.estadoId = v;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash = ( (pedidoCompraId != null ? pedidoCompraId.hashCode() : 0 ) + 
			 (estadoId != null ? estadoId.hashCode() : 0 ) );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof PedidoCompraEstadoPK)) {
            return false;
        }

    	PedidoCompraEstadoPK other = (PedidoCompraEstadoPK ) o;
        if ( (this.pedidoCompraId == null && other.pedidoCompraId != null) || (this.pedidoCompraId != null && !this.pedidoCompraId.equals(other.pedidoCompraId)) || 
             (this.estadoId == null && other.estadoId != null) || (this.estadoId != null && !this.estadoId.equals(other.estadoId))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.model.beans.PedidoCompraEstadoPK[pedidoCompraId = "+pedidoCompraId+" , estadoId = "+estadoId+ "]";
    }

}
