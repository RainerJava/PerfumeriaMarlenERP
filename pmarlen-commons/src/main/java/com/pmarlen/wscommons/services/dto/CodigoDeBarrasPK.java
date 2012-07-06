
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
 * Class for mapping JPA Entity of Table Codigo_de_barras_P_K.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2011/06/09 05:47
 */




public class CodigoDeBarrasPK implements java.io.Serializable {
    
    /**
    * codigo
    */
    private String codigo;
    
    /**
    * producto id
    */
    private Integer productoId;

    /** 
     * Default Constructor
     */
    public CodigoDeBarrasPK() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public CodigoDeBarrasPK( String codigo, Integer productoId ) {
        this.codigo 	= 	codigo;
        this.productoId 	= 	productoId;

    }
    
    /**
     * Getters and Setters
     */
    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String v) {
        this.codigo = v;
    }

    public Integer getProductoId() {
        return this.productoId;
    }

    public void setProductoId(Integer v) {
        this.productoId = v;
    }


    public int hashCode() {
        int hash = 0;
        hash = ( (codigo != null ? codigo.hashCode() : 0 ) + 
		 (productoId != null ? productoId.hashCode() : 0 ) );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof CodigoDeBarrasPK)) {
            return false;
        }

    	CodigoDeBarrasPK other = (CodigoDeBarrasPK ) o;
        if ( (this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo)) || 
             (this.productoId == null && other.productoId != null) || (this.productoId != null && !this.productoId.equals(other.productoId))) {
            return false;
        }


    	return true;
    }

    public String toString() {
        return "com.pmarlen.model.beans.CodigoDeBarrasPK[codigo = "+codigo+" , productoId = "+productoId+ "]";
    }

}
