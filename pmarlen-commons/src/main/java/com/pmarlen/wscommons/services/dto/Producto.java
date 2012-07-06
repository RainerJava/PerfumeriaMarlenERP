
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
 * Class for mapping JPA Entity of Table Producto.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2011/06/09 05:47
 */



public class Producto implements java.io.Serializable {
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * marca id
    */
    private Marca marca;
    
    /**
    * nombre
    */
    private String nombre;
    
    /**
    * presentacion
    */
    private String presentacion;
    
    /**
    * contenido
    */
    private int contenido;
    
    /**
    * unidades por caja
    */
    private int unidadesPorCaja;
    
    /**
    * factor max desc
    */
    private double factorMaxDesc;
    
    /**
    * costo
    */
    private double costo;
    
    /**
    * factor precio
    */
    private double factorPrecio;
    
    /**
    * precio base
    */
    private double precioBase;
    
    /**
    * unidad medida
    */
    private String unidadMedida;
    
    private Collection<CodigoDeBarras> codigoDeBarrasCollection;
    
    
    private Collection<PedidoVentaDetalle> pedidoVentaDetalleCollection;
    
    
    private Collection<PedidoCompraDetalle> pedidoCompraDetalleCollection;
    
    
    private Collection<ProveedorProducto> proveedorProductoCollection;
    
    
    private Collection<AlmacenProducto> almacenProductoCollection;
    
    
    private Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollection;
    
    
    private Collection<Multimedio> multimedioCollection;
    

    /** 
     * Default Constructor
     */
    public Producto() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public Producto( Integer id ) {
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

    public Marca getMarca() {
        return this.marca;
    }

    public void setMarca(Marca v) {
        this.marca = v;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String v) {
        this.nombre = v;
    }

    public String getPresentacion() {
        return this.presentacion;
    }

    public void setPresentacion(String v) {
        this.presentacion = v;
    }

    public int getContenido() {
        return this.contenido;
    }

    public void setContenido(int v) {
        this.contenido = v;
    }

    public int getUnidadesPorCaja() {
        return this.unidadesPorCaja;
    }

    public void setUnidadesPorCaja(int v) {
        this.unidadesPorCaja = v;
    }

    public double getFactorMaxDesc() {
        return this.factorMaxDesc;
    }

    public void setFactorMaxDesc(double v) {
        this.factorMaxDesc = v;
    }

    public double getCosto() {
        return this.costo;
    }

    public void setCosto(double v) {
        this.costo = v;
    }

    public double getFactorPrecio() {
        return this.factorPrecio;
    }

    public void setFactorPrecio(double v) {
        this.factorPrecio = v;
    }

    public double getPrecioBase() {
        return this.precioBase;
    }

    public void setPrecioBase(double v) {
        this.precioBase = v;
    }

    public String getUnidadMedida() {
        return this.unidadMedida;
    }

    public void setUnidadMedida(String v) {
        this.unidadMedida = v;
    }

    
    public Collection<CodigoDeBarras> getCodigoDeBarrasCollection() {
        return this.codigoDeBarrasCollection;
    }
    
    
    public void setCodigoDeBarrasCollection(Collection<CodigoDeBarras>  v) {
        this.codigoDeBarrasCollection = v;
    }
    
    public Collection<PedidoVentaDetalle> getPedidoVentaDetalleCollection() {
        return this.pedidoVentaDetalleCollection;
    }
    
    
    public void setPedidoVentaDetalleCollection(Collection<PedidoVentaDetalle>  v) {
        this.pedidoVentaDetalleCollection = v;
    }
    
    public Collection<PedidoCompraDetalle> getPedidoCompraDetalleCollection() {
        return this.pedidoCompraDetalleCollection;
    }
    
    
    public void setPedidoCompraDetalleCollection(Collection<PedidoCompraDetalle>  v) {
        this.pedidoCompraDetalleCollection = v;
    }
    
    public Collection<ProveedorProducto> getProveedorProductoCollection() {
        return this.proveedorProductoCollection;
    }
    
    
    public void setProveedorProductoCollection(Collection<ProveedorProducto>  v) {
        this.proveedorProductoCollection = v;
    }
    
    public Collection<AlmacenProducto> getAlmacenProductoCollection() {
        return this.almacenProductoCollection;
    }
    
    
    public void setAlmacenProductoCollection(Collection<AlmacenProducto>  v) {
        this.almacenProductoCollection = v;
    }
    
    public Collection<MovimientoHistoricoProducto> getMovimientoHistoricoProductoCollection() {
        return this.movimientoHistoricoProductoCollection;
    }
    
    
    public void setMovimientoHistoricoProductoCollection(Collection<MovimientoHistoricoProducto>  v) {
        this.movimientoHistoricoProductoCollection = v;
    }
    // Getter and Setters @ManyToMany Collection<Multimedio>
    
    public Collection<Multimedio> getMultimedioCollection() {
        return this.multimedioCollection;
    }
    
    
    public void setMultimedioCollection(Collection<Multimedio>  v) {
        this.multimedioCollection = v;
    }

    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof Producto)) {
            return false;
        }

    	Producto other = (Producto ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    public String toString() {
        return "com.pmarlen.model.beans.Producto[id = "+id+ "]";
    }

}
