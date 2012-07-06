
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
 * Class for mapping JPA Entity of Table Codigo_de_barras.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2011/06/09 05:47
 */



public class CodigoDeBarras implements java.io.Serializable {
    
    /**
    * codigo de barras p k
    */
    private CodigoDeBarrasPK codigoDeBarrasPK;
    
    /**
    * producto id
    */
    private Producto producto;

    /** 
     * Default Constructor
     */
    public CodigoDeBarras() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public CodigoDeBarras( CodigoDeBarrasPK codigoDeBarrasPK ) {
        this.codigoDeBarrasPK 	= 	codigoDeBarrasPK;

    }
    
    /**
     * Getters and Setters
     */
    public CodigoDeBarrasPK getCodigoDeBarrasPK() {
        return this.codigoDeBarrasPK;
    }

    public void setCodigoDeBarrasPK(CodigoDeBarrasPK v) {
        this.codigoDeBarrasPK = v;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public void setProducto(Producto v) {
        this.producto = v;
    }


    public int hashCode() {
        int hash = 0;
        hash = (codigoDeBarrasPK != null ? codigoDeBarrasPK.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof CodigoDeBarras)) {
            return false;
        }

    	CodigoDeBarras other = (CodigoDeBarras ) o;
        if ( (this.codigoDeBarrasPK == null && other.codigoDeBarrasPK != null) || (this.codigoDeBarrasPK != null && !this.codigoDeBarrasPK.equals(other.codigoDeBarrasPK))) {
            return false;
        }


    	return true;
    }

    public String toString() {
        return "com.pmarlen.model.beans.CodigoDeBarras[codigoDeBarrasPK = "+codigoDeBarrasPK+ "]";
    }

}
