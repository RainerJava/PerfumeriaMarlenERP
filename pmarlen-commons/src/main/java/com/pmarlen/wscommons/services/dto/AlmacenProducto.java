
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
 * Class for mapping JPA Entity of Table Almacen_Producto.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2011/06/09 05:47
 */



public class AlmacenProducto implements java.io.Serializable {
    private AlmacenProductoPK almacenProductoPK;
    private int cantidadActual;
    private int cantidadMinima;
    private Almacen almacen;
    private Producto producto;

    /** 
     * Default Constructor
     */
    public AlmacenProducto() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public AlmacenProducto( AlmacenProductoPK almacenProductoPK ) {
        this.almacenProductoPK 	= 	almacenProductoPK;

    }
    
    /**
     * Getters and Setters
     */
    public AlmacenProductoPK getAlmacenProductoPK() {
        return this.almacenProductoPK;
    }

    public void setAlmacenProductoPK(AlmacenProductoPK v) {
        this.almacenProductoPK = v;
    }

    public int getCantidadActual() {
        return this.cantidadActual;
    }

    public void setCantidadActual(int v) {
        this.cantidadActual = v;
    }

    public int getCantidadMinima() {
        return this.cantidadMinima;
    }

    public void setCantidadMinima(int v) {
        this.cantidadMinima = v;
    }

    public Almacen getAlmacen() {
        return this.almacen;
    }

    public void setAlmacen(Almacen v) {
        this.almacen = v;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public void setProducto(Producto v) {
        this.producto = v;
    }


    public int hashCode() {
        int hash = 0;
        hash = (almacenProductoPK != null ? almacenProductoPK.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof AlmacenProducto)) {
            return false;
        }

    	AlmacenProducto other = (AlmacenProducto ) o;
        if ( (this.almacenProductoPK == null && other.almacenProductoPK != null) || (this.almacenProductoPK != null && !this.almacenProductoPK.equals(other.almacenProductoPK))) {
            return false;
        }


    	return true;
    }

    public String toString() {
        return "com.pmarlen.model.beans.AlmacenProducto[almacenProductoPK = "+almacenProductoPK+ "]";
    }

}
