
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
 * Class for mapping JPA Entity of Table Pedido_compra.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2011/06/09 05:47
 */



public class PedidoCompra implements java.io.Serializable {
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * proveedor id
    */
    private Proveedor proveedor;
    
    /**
    * forma de pago id
    */
    private FormaDePago formaDePago;
    
    /**
    * usuario id
    */
    private Usuario usuario;
    
    /**
    * es fiscal
    */
    private int esFiscal;
    
    /**
    * caomentarios
    */
    private String caomentarios;
    
    /**
    * factoriva
    */
    private Double factoriva;
    
    private Collection<FacturaProveedor> facturaProveedorCollection;
    
    
    private Collection<PedidoCompraDetalle> pedidoCompraDetalleCollection;
    
    
    private Collection<PedidoCompraEstado> pedidoCompraEstadoCollection;
    

    /** 
     * Default Constructor
     */
    public PedidoCompra() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public PedidoCompra( Integer id ) {
        this.id 	= 	id;

    }
    
    /**
     * Getters and Setters
     */
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer v) {
        this.id = v;
    }

    public Proveedor getProveedor() {
        return this.proveedor;
    }

    public void setProveedor(Proveedor v) {
        this.proveedor = v;
    }

    public FormaDePago getFormaDePago() {
        return this.formaDePago;
    }

    public void setFormaDePago(FormaDePago v) {
        this.formaDePago = v;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario v) {
        this.usuario = v;
    }

    public int getEsFiscal() {
        return this.esFiscal;
    }

    public void setEsFiscal(int v) {
        this.esFiscal = v;
    }

    public String getCaomentarios() {
        return this.caomentarios;
    }

    public void setCaomentarios(String v) {
        this.caomentarios = v;
    }

    public Double getFactoriva() {
        return this.factoriva;
    }

    public void setFactoriva(Double v) {
        this.factoriva = v;
    }

    
    public Collection<FacturaProveedor> getFacturaProveedorCollection() {
        return this.facturaProveedorCollection;
    }
    
    
    public void setFacturaProveedorCollection(Collection<FacturaProveedor>  v) {
        this.facturaProveedorCollection = v;
    }
    
    public Collection<PedidoCompraDetalle> getPedidoCompraDetalleCollection() {
        return this.pedidoCompraDetalleCollection;
    }
    
    
    public void setPedidoCompraDetalleCollection(Collection<PedidoCompraDetalle>  v) {
        this.pedidoCompraDetalleCollection = v;
    }
    
    public Collection<PedidoCompraEstado> getPedidoCompraEstadoCollection() {
        return this.pedidoCompraEstadoCollection;
    }
    
    
    public void setPedidoCompraEstadoCollection(Collection<PedidoCompraEstado>  v) {
        this.pedidoCompraEstadoCollection = v;
    }

    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof PedidoCompra)) {
            return false;
        }

    	PedidoCompra other = (PedidoCompra ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    public String toString() {
        return "com.pmarlen.model.beans.PedidoCompra[id = "+id+ "]";
    }

}