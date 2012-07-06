
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
 * Class for mapping JPA Entity of Table Pedido_Venta_Estado.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2012/04/24 05:24
 */



@Entity
@Table(name = "PEDIDO_VENTA_ESTADO")
public class PedidoVentaEstado implements java.io.Serializable {
    private static final long serialVersionUID = 2018812712;
    
    /**
    * pedido venta estado p k
    */
    @EmbeddedId
    private PedidoVentaEstadoPK pedidoVentaEstadoPK;
    
    /**
    * fecha
    */
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date fecha;
    
    /**
    * usuario modifico
    */
    @JoinColumn(name = "USUARIO_MODIFICO" , referencedColumnName = "USUARIO_ID")
    @ManyToOne(optional = false)
    private Usuario usuario;
    
    /**
    * comentarios
    */
    @Basic(optional = true)
    @Column(name = "COMENTARIOS")
    private String comentarios;
    
    /**
    * pedido venta id
    */
    @JoinColumn(name = "PEDIDO_VENTA_ID" , referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PedidoVenta pedidoVenta;
    
    /**
    * estado id
    */
    @JoinColumn(name = "ESTADO_ID" , referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Estado estado;

    /** 
     * Default Constructor
     */
    public PedidoVentaEstado() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public PedidoVentaEstado( PedidoVentaEstadoPK pedidoVentaEstadoPK, PedidoVenta pedidoVenta, Estado estado ) {
        this.pedidoVentaEstadoPK 	= 	pedidoVentaEstadoPK;
        this.pedidoVenta 	= 	pedidoVenta;
        this.estado 	= 	estado;

    }
    
    /**
     * Getters and Setters
     */
    public PedidoVentaEstadoPK getPedidoVentaEstadoPK() {
        return this.pedidoVentaEstadoPK;
    }

    public void setPedidoVentaEstadoPK(PedidoVentaEstadoPK v) {
        this.pedidoVentaEstadoPK = v;
    }

    public java.util.Date getFecha() {
        return this.fecha;
    }

    public void setFecha(java.util.Date v) {
        this.fecha = v;
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

    public PedidoVenta getPedidoVenta() {
        return this.pedidoVenta;
    }

    public void setPedidoVenta(PedidoVenta v) {
        this.pedidoVenta = v;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado v) {
        this.estado = v;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash = (pedidoVentaEstadoPK != null ? pedidoVentaEstadoPK.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof PedidoVentaEstado)) {
            return false;
        }

    	PedidoVentaEstado other = (PedidoVentaEstado ) o;
        if ( (this.pedidoVentaEstadoPK == null && other.pedidoVentaEstadoPK != null) || (this.pedidoVentaEstadoPK != null && !this.pedidoVentaEstadoPK.equals(other.pedidoVentaEstadoPK))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.model.beans.PedidoVentaEstado[pedidoVentaEstadoPK = "+pedidoVentaEstadoPK+ "]";
    }

}
