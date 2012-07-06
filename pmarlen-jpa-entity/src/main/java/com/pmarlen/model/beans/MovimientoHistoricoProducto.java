
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
 * Class for mapping JPA Entity of Table Movimiento_Historico_Producto.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2012/04/24 05:24
 */



@Entity
@Table(name = "MOVIMIENTO_HISTORICO_PRODUCTO")
public class MovimientoHistoricoProducto implements java.io.Serializable {
    private static final long serialVersionUID = 1323423210;
    
    /**
    * movimiento historico producto p k
    */
    @EmbeddedId
    private MovimientoHistoricoProductoPK movimientoHistoricoProductoPK;
    
    /**
    * cantidad
    */
    @Basic(optional = false)
    @Column(name = "CANTIDAD")
    private int cantidad;
    
    /**
    * costo
    */
    @Basic(optional = true)
    @Column(name = "COSTO")
    private Double costo;
    
    /**
    * precio
    */
    @Basic(optional = true)
    @Column(name = "PRECIO")
    private Double precio;
    
    /**
    * usuario id
    */
    @JoinColumn(name = "USUARIO_ID" , referencedColumnName = "USUARIO_ID")
    @ManyToOne(optional = false)
    private Usuario usuario;
    
    /**
    * almacen id
    */
    @JoinColumn(name = "ALMACEN_ID" , referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Almacen almacen;
    
    /**
    * producto id
    */
    @JoinColumn(name = "PRODUCTO_ID" , referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;
    
    /**
    * tipo movimiento id
    */
    @JoinColumn(name = "TIPO_MOVIMIENTO_ID" , referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TipoMovimiento tipoMovimiento;

    /** 
     * Default Constructor
     */
    public MovimientoHistoricoProducto() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public MovimientoHistoricoProducto( MovimientoHistoricoProductoPK movimientoHistoricoProductoPK, Almacen almacen, Producto producto, TipoMovimiento tipoMovimiento ) {
        this.movimientoHistoricoProductoPK 	= 	movimientoHistoricoProductoPK;
        this.almacen 	= 	almacen;
        this.producto 	= 	producto;
        this.tipoMovimiento 	= 	tipoMovimiento;

    }
    
    /**
     * Getters and Setters
     */
    public MovimientoHistoricoProductoPK getMovimientoHistoricoProductoPK() {
        return this.movimientoHistoricoProductoPK;
    }

    public void setMovimientoHistoricoProductoPK(MovimientoHistoricoProductoPK v) {
        this.movimientoHistoricoProductoPK = v;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int v) {
        this.cantidad = v;
    }

    public Double getCosto() {
        return this.costo;
    }

    public void setCosto(Double v) {
        this.costo = v;
    }

    public Double getPrecio() {
        return this.precio;
    }

    public void setPrecio(Double v) {
        this.precio = v;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario v) {
        this.usuario = v;
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

    public TipoMovimiento getTipoMovimiento() {
        return this.tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento v) {
        this.tipoMovimiento = v;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash = (movimientoHistoricoProductoPK != null ? movimientoHistoricoProductoPK.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof MovimientoHistoricoProducto)) {
            return false;
        }

    	MovimientoHistoricoProducto other = (MovimientoHistoricoProducto ) o;
        if ( (this.movimientoHistoricoProductoPK == null && other.movimientoHistoricoProductoPK != null) || (this.movimientoHistoricoProductoPK != null && !this.movimientoHistoricoProductoPK.equals(other.movimientoHistoricoProductoPK))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.model.beans.MovimientoHistoricoProducto[movimientoHistoricoProductoPK = "+movimientoHistoricoProductoPK+ "]";
    }

}
