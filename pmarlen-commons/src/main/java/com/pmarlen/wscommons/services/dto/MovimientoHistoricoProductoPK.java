
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
 * Class for mapping JPA Entity of Table Movimiento_Historico_Producto_P_K.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2011/06/09 05:47
 */




public class MovimientoHistoricoProductoPK implements java.io.Serializable {
    
    /**
    * almacen id
    */
    private Integer almacenId;
    
    /**
    * producto id
    */
    private Integer productoId;
    
    /**
    * fecha
    */
    private java.util.Date fecha;
    
    /**
    * tipo movimiento id
    */
    private Integer tipoMovimientoId;

    /** 
     * Default Constructor
     */
    public MovimientoHistoricoProductoPK() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public MovimientoHistoricoProductoPK( Integer almacenId, Integer productoId, java.util.Date fecha, Integer tipoMovimientoId ) {
        this.almacenId 	= 	almacenId;
        this.productoId 	= 	productoId;
        this.fecha 	= 	fecha;
        this.tipoMovimientoId 	= 	tipoMovimientoId;

    }
    
    /**
     * Getters and Setters
     */
    public Integer getAlmacenId() {
        return this.almacenId;
    }

    public void setAlmacenId(Integer v) {
        this.almacenId = v;
    }

    public Integer getProductoId() {
        return this.productoId;
    }

    public void setProductoId(Integer v) {
        this.productoId = v;
    }

    public java.util.Date getFecha() {
        return this.fecha;
    }

    public void setFecha(java.util.Date v) {
        this.fecha = v;
    }

    public Integer getTipoMovimientoId() {
        return this.tipoMovimientoId;
    }

    public void setTipoMovimientoId(Integer v) {
        this.tipoMovimientoId = v;
    }


    public int hashCode() {
        int hash = 0;
        hash = ( (almacenId != null ? almacenId.hashCode() : 0 ) + 
		 (productoId != null ? productoId.hashCode() : 0 ) + 
		 (fecha != null ? fecha.hashCode() : 0 ) + 
		 (tipoMovimientoId != null ? tipoMovimientoId.hashCode() : 0 ) );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof MovimientoHistoricoProductoPK)) {
            return false;
        }

    	MovimientoHistoricoProductoPK other = (MovimientoHistoricoProductoPK ) o;
        if ( (this.almacenId == null && other.almacenId != null) || (this.almacenId != null && !this.almacenId.equals(other.almacenId)) || 
             (this.productoId == null && other.productoId != null) || (this.productoId != null && !this.productoId.equals(other.productoId)) || 
             (this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha)) || 
             (this.tipoMovimientoId == null && other.tipoMovimientoId != null) || (this.tipoMovimientoId != null && !this.tipoMovimientoId.equals(other.tipoMovimientoId))) {
            return false;
        }


    	return true;
    }

    public String toString() {
        return "com.pmarlen.model.beans.MovimientoHistoricoProductoPK[almacenId = "+almacenId+" , productoId = "+productoId+" , fecha = "+fecha+" , tipoMovimientoId = "+tipoMovimientoId+ "]";
    }

}
