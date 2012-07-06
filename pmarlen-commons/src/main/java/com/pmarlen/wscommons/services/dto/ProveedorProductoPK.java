
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
 * Class for mapping JPA Entity of Table Proveedor_Producto_P_K.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2011/06/09 05:47
 */




public class ProveedorProductoPK implements java.io.Serializable {
    
    /**
    * proveedor id
    */
    private Integer proveedorId;
    
    /**
    * producto id
    */
    private Integer productoId;

    /** 
     * Default Constructor
     */
    public ProveedorProductoPK() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public ProveedorProductoPK( Integer proveedorId, Integer productoId ) {
        this.proveedorId 	= 	proveedorId;
        this.productoId 	= 	productoId;

    }
    
    /**
     * Getters and Setters
     */
    public Integer getProveedorId() {
        return this.proveedorId;
    }

    public void setProveedorId(Integer v) {
        this.proveedorId = v;
    }

    public Integer getProductoId() {
        return this.productoId;
    }

    public void setProductoId(Integer v) {
        this.productoId = v;
    }


    public int hashCode() {
        int hash = 0;
        hash = ( (proveedorId != null ? proveedorId.hashCode() : 0 ) + 
		 (productoId != null ? productoId.hashCode() : 0 ) );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof ProveedorProductoPK)) {
            return false;
        }

    	ProveedorProductoPK other = (ProveedorProductoPK ) o;
        if ( (this.proveedorId == null && other.proveedorId != null) || (this.proveedorId != null && !this.proveedorId.equals(other.proveedorId)) || 
             (this.productoId == null && other.productoId != null) || (this.productoId != null && !this.productoId.equals(other.productoId))) {
            return false;
        }


    	return true;
    }

    public String toString() {
        return "com.pmarlen.model.beans.ProveedorProductoPK[proveedorId = "+proveedorId+" , productoId = "+productoId+ "]";
    }

}
