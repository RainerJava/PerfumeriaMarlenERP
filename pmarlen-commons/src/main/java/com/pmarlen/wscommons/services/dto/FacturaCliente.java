
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
 * Class for mapping JPA Entity of Table Factura_Cliente.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2011/06/09 05:47
 */



public class FacturaCliente implements java.io.Serializable {
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * pedido venta id
    */
    private PedidoVenta pedidoVenta;
    
    /**
    * nota de credito
    */
    private int notaDeCredito;
    
    /**
    * fecha emision
    */
    private java.util.Date fechaEmision;
    
    /**
    * folio
    */
    private Integer folio;
    
    /**
    * serie
    */
    private String serie;
    
    /**
    * cadena original
    */
    private String cadenaOriginal;
    
    /**
    * fecha timbrado sat
    */
    private java.util.Date fechaTimbradoSat;
    
    /**
    * folio fiscal
    */
    private String folioFiscal;
    
    private Collection<EstadoDeCuentaClientes> estadoDeCuentaClientesCollection;
    

    /** 
     * Default Constructor
     */
    public FacturaCliente() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public FacturaCliente( Integer id ) {
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

    public PedidoVenta getPedidoVenta() {
        return this.pedidoVenta;
    }

    public void setPedidoVenta(PedidoVenta v) {
        this.pedidoVenta = v;
    }

    public int getNotaDeCredito() {
        return this.notaDeCredito;
    }

    public void setNotaDeCredito(int v) {
        this.notaDeCredito = v;
    }

    public java.util.Date getFechaEmision() {
        return this.fechaEmision;
    }

    public void setFechaEmision(java.util.Date v) {
        this.fechaEmision = v;
    }

    public Integer getFolio() {
        return this.folio;
    }

    public void setFolio(Integer v) {
        this.folio = v;
    }

    public String getSerie() {
        return this.serie;
    }

    public void setSerie(String v) {
        this.serie = v;
    }

    public String getCadenaOriginal() {
        return this.cadenaOriginal;
    }

    public void setCadenaOriginal(String v) {
        this.cadenaOriginal = v;
    }

    public java.util.Date getFechaTimbradoSat() {
        return this.fechaTimbradoSat;
    }

    public void setFechaTimbradoSat(java.util.Date v) {
        this.fechaTimbradoSat = v;
    }

    public String getFolioFiscal() {
        return this.folioFiscal;
    }

    public void setFolioFiscal(String v) {
        this.folioFiscal = v;
    }

    
    public Collection<EstadoDeCuentaClientes> getEstadoDeCuentaClientesCollection() {
        return this.estadoDeCuentaClientesCollection;
    }
    
    
    public void setEstadoDeCuentaClientesCollection(Collection<EstadoDeCuentaClientes>  v) {
        this.estadoDeCuentaClientesCollection = v;
    }

    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof FacturaCliente)) {
            return false;
        }

    	FacturaCliente other = (FacturaCliente ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    public String toString() {
        return "com.pmarlen.model.beans.FacturaCliente[id = "+id+ "]";
    }

}