
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
 * Class for mapping JPA Entity of Table Pedido_Venta_Estado_P_K.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2011/06/09 05:47
 */




public class PedidoVentaEstadoPK implements java.io.Serializable {
    
    /**
    * pedido venta id
    */
    private Integer pedidoVentaId;
    
    /**
    * estado id
    */
    private Integer estadoId;

    /** 
     * Default Constructor
     */
    public PedidoVentaEstadoPK() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public PedidoVentaEstadoPK( Integer pedidoVentaId, Integer estadoId ) {
        this.pedidoVentaId 	= 	pedidoVentaId;
        this.estadoId 	= 	estadoId;

    }
    
    /**
     * Getters and Setters
     */
    public Integer getPedidoVentaId() {
        return this.pedidoVentaId;
    }

    public void setPedidoVentaId(Integer v) {
        this.pedidoVentaId = v;
    }

    public Integer getEstadoId() {
        return this.estadoId;
    }

    public void setEstadoId(Integer v) {
        this.estadoId = v;
    }


    public int hashCode() {
        int hash = 0;
        hash = ( (pedidoVentaId != null ? pedidoVentaId.hashCode() : 0 ) + 
		 (estadoId != null ? estadoId.hashCode() : 0 ) );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof PedidoVentaEstadoPK)) {
            return false;
        }

    	PedidoVentaEstadoPK other = (PedidoVentaEstadoPK ) o;
        if ( (this.pedidoVentaId == null && other.pedidoVentaId != null) || (this.pedidoVentaId != null && !this.pedidoVentaId.equals(other.pedidoVentaId)) || 
             (this.estadoId == null && other.estadoId != null) || (this.estadoId != null && !this.estadoId.equals(other.estadoId))) {
            return false;
        }


    	return true;
    }

    public String toString() {
        return "com.pmarlen.model.beans.PedidoVentaEstadoPK[pedidoVentaId = "+pedidoVentaId+" , estadoId = "+estadoId+ "]";
    }

}
