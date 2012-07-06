
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
 * Class for mapping JPA Entity of Table Pedido_Compra_Estado.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2011/06/09 05:47
 */



public class PedidoCompraEstado implements java.io.Serializable {
    
    /**
    * pedido compra estado p k
    */
    private PedidoCompraEstadoPK pedidoCompraEstadoPK;
    
    /**
    * usuario modifico
    */
    private Usuario usuario;
    
    /**
    * comentarios
    */
    private String comentarios;
    
    /**
    * pedido compra id
    */
    private PedidoCompra pedidoCompra;
    
    /**
    * estado id
    */
    private Estado estado;

    /** 
     * Default Constructor
     */
    public PedidoCompraEstado() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public PedidoCompraEstado( PedidoCompraEstadoPK pedidoCompraEstadoPK ) {
        this.pedidoCompraEstadoPK 	= 	pedidoCompraEstadoPK;

    }
    
    /**
     * Getters and Setters
     */
    public PedidoCompraEstadoPK getPedidoCompraEstadoPK() {
        return this.pedidoCompraEstadoPK;
    }

    public void setPedidoCompraEstadoPK(PedidoCompraEstadoPK v) {
        this.pedidoCompraEstadoPK = v;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario v) {
        this.usuario = v;
    }

    public String getComentarios() {
        return this.comentarios;
    }

    public void setComentarios(String v) {
        this.comentarios = v;
    }

    public PedidoCompra getPedidoCompra() {
        return this.pedidoCompra;
    }

    public void setPedidoCompra(PedidoCompra v) {
        this.pedidoCompra = v;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado v) {
        this.estado = v;
    }


    public int hashCode() {
        int hash = 0;
        hash = (pedidoCompraEstadoPK != null ? pedidoCompraEstadoPK.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof PedidoCompraEstado)) {
            return false;
        }

    	PedidoCompraEstado other = (PedidoCompraEstado ) o;
        if ( (this.pedidoCompraEstadoPK == null && other.pedidoCompraEstadoPK != null) || (this.pedidoCompraEstadoPK != null && !this.pedidoCompraEstadoPK.equals(other.pedidoCompraEstadoPK))) {
            return false;
        }


    	return true;
    }

    public String toString() {
        return "com.pmarlen.model.beans.PedidoCompraEstado[pedidoCompraEstadoPK = "+pedidoCompraEstadoPK+ "]";
    }

}
