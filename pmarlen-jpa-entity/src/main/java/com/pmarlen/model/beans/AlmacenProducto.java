
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
 * Class for mapping JPA Entity of Table Almacen_Producto.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2012/04/24 05:24
 */



@Entity
@Table(name = "ALMACEN_PRODUCTO")
public class AlmacenProducto implements java.io.Serializable {
    private static final long serialVersionUID = 161174419;
    @EmbeddedId
    private AlmacenProductoPK almacenProductoPK;
    @Basic(optional = false)
    @Column(name = "CANTIDAD_ACTUAL")
    private int cantidadActual;
    @Basic(optional = false)
    @Column(name = "CANTIDAD_MINIMA")
    private int cantidadMinima;
    @JoinColumn(name = "ALMACEN_ID" , referencedColumnName = "ID",  insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Almacen almacen;
    @JoinColumn(name = "PRODUCTO_ID" , referencedColumnName = "ID",  insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;

    /** 
     * Default Constructor
     */
    public AlmacenProducto() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public AlmacenProducto( AlmacenProductoPK almacenProductoPK, Almacen almacen, Producto producto ) {
        this.almacenProductoPK 	= 	almacenProductoPK;
        this.almacen 	= 	almacen;
        this.producto 	= 	producto;

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


    @Override
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

    @Override
    public String toString() {
        return "com.pmarlen.model.beans.AlmacenProducto[almacenProductoPK = "+almacenProductoPK+ "]";
    }

}
