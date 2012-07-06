
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
 * Class for mapping JPA Entity of Table Proveedor_Producto.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2011/06/09 05:47
 */



public class ProveedorProducto implements java.io.Serializable {
    private ProveedorProductoPK proveedorProductoPK;
    private double precioCompra;
    private double factorDesc1;
    private Double factorDesc2;
    private Double factorDesc3;
    private Double factorDesc4;
    private Double factorDesc5;
    private Proveedor proveedor;
    private Producto producto;

    /** 
     * Default Constructor
     */
    public ProveedorProducto() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public ProveedorProducto( ProveedorProductoPK proveedorProductoPK ) {
        this.proveedorProductoPK 	= 	proveedorProductoPK;

    }
    
    /**
     * Getters and Setters
     */
    public ProveedorProductoPK getProveedorProductoPK() {
        return this.proveedorProductoPK;
    }

    public void setProveedorProductoPK(ProveedorProductoPK v) {
        this.proveedorProductoPK = v;
    }

    public double getPrecioCompra() {
        return this.precioCompra;
    }

    public void setPrecioCompra(double v) {
        this.precioCompra = v;
    }

    public double getFactorDesc1() {
        return this.factorDesc1;
    }

    public void setFactorDesc1(double v) {
        this.factorDesc1 = v;
    }

    public Double getFactorDesc2() {
        return this.factorDesc2;
    }

    public void setFactorDesc2(Double v) {
        this.factorDesc2 = v;
    }

    public Double getFactorDesc3() {
        return this.factorDesc3;
    }

    public void setFactorDesc3(Double v) {
        this.factorDesc3 = v;
    }

    public Double getFactorDesc4() {
        return this.factorDesc4;
    }

    public void setFactorDesc4(Double v) {
        this.factorDesc4 = v;
    }

    public Double getFactorDesc5() {
        return this.factorDesc5;
    }

    public void setFactorDesc5(Double v) {
        this.factorDesc5 = v;
    }

    public Proveedor getProveedor() {
        return this.proveedor;
    }

    public void setProveedor(Proveedor v) {
        this.proveedor = v;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public void setProducto(Producto v) {
        this.producto = v;
    }


    public int hashCode() {
        int hash = 0;
        hash = (proveedorProductoPK != null ? proveedorProductoPK.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof ProveedorProducto)) {
            return false;
        }

    	ProveedorProducto other = (ProveedorProducto ) o;
        if ( (this.proveedorProductoPK == null && other.proveedorProductoPK != null) || (this.proveedorProductoPK != null && !this.proveedorProductoPK.equals(other.proveedorProductoPK))) {
            return false;
        }


    	return true;
    }

    public String toString() {
        return "com.pmarlen.model.beans.ProveedorProducto[proveedorProductoPK = "+proveedorProductoPK+ "]";
    }

}
