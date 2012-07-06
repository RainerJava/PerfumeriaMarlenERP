
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
 * Class for mapping JPA Entity of Table Cliente.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2011/06/09 05:47
 */



public class Cliente implements java.io.Serializable {
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * clasificacion fiscal
    */
    private int clasificacionFiscal;
    
    /**
    * rfc
    */
    private String rfc;
    
    /**
    * fecha creacion
    */
    private java.util.Date fechaCreacion;
    
    /**
    * razon social
    */
    private String razonSocial;
    
    /**
    * nombre establecimiento
    */
    private String nombreEstablecimiento;
    
    /**
    * calle
    */
    private String calle;
    
    /**
    * num interior
    */
    private String numInterior;
    
    /**
    * num exterior
    */
    private String numExterior;
    
    /**
    * poblacion id
    */
    private Poblacion poblacion;
    
    /**
    * telefonos
    */
    private String telefonos;
    
    /**
    * faxes
    */
    private String faxes;
    
    /**
    * telefonos moviles
    */
    private String telefonosMoviles;
    
    /**
    * email
    */
    private String email;
    
    /**
    * plazo de pago
    */
    private Integer plazoDePago;
    
    /**
    * url
    */
    private String url;
    
    /**
    * observaciones
    */
    private String observaciones;
    
    /**
    * descripcion ruta
    */
    private String descripcionRuta;
    
    private Collection<PedidoVenta> pedidoVentaCollection;
    
    
    private Collection<Contacto> contactoCollection;
    
    
    private Collection<CuentaBancaria> cuentaBancariaCollection;
    

    /** 
     * Default Constructor
     */
    public Cliente() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public Cliente( Integer id ) {
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

    public int getClasificacionFiscal() {
        return this.clasificacionFiscal;
    }

    public void setClasificacionFiscal(int v) {
        this.clasificacionFiscal = v;
    }

    public String getRfc() {
        return this.rfc;
    }

    public void setRfc(String v) {
        this.rfc = v;
    }

    public java.util.Date getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(java.util.Date v) {
        this.fechaCreacion = v;
    }

    public String getRazonSocial() {
        return this.razonSocial;
    }

    public void setRazonSocial(String v) {
        this.razonSocial = v;
    }

    public String getNombreEstablecimiento() {
        return this.nombreEstablecimiento;
    }

    public void setNombreEstablecimiento(String v) {
        this.nombreEstablecimiento = v;
    }

    public String getCalle() {
        return this.calle;
    }

    public void setCalle(String v) {
        this.calle = v;
    }

    public String getNumInterior() {
        return this.numInterior;
    }

    public void setNumInterior(String v) {
        this.numInterior = v;
    }

    public String getNumExterior() {
        return this.numExterior;
    }

    public void setNumExterior(String v) {
        this.numExterior = v;
    }

    public Poblacion getPoblacion() {
        return this.poblacion;
    }

    public void setPoblacion(Poblacion v) {
        this.poblacion = v;
    }

    public String getTelefonos() {
        return this.telefonos;
    }

    public void setTelefonos(String v) {
        this.telefonos = v;
    }

    public String getFaxes() {
        return this.faxes;
    }

    public void setFaxes(String v) {
        this.faxes = v;
    }

    public String getTelefonosMoviles() {
        return this.telefonosMoviles;
    }

    public void setTelefonosMoviles(String v) {
        this.telefonosMoviles = v;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String v) {
        this.email = v;
    }

    public Integer getPlazoDePago() {
        return this.plazoDePago;
    }

    public void setPlazoDePago(Integer v) {
        this.plazoDePago = v;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String v) {
        this.url = v;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public void setObservaciones(String v) {
        this.observaciones = v;
    }

    public String getDescripcionRuta() {
        return this.descripcionRuta;
    }

    public void setDescripcionRuta(String v) {
        this.descripcionRuta = v;
    }

    
    public Collection<PedidoVenta> getPedidoVentaCollection() {
        return this.pedidoVentaCollection;
    }
    
    
    public void setPedidoVentaCollection(Collection<PedidoVenta>  v) {
        this.pedidoVentaCollection = v;
    }
    // Getter and Setters @ManyToMany Collection<Contacto>
    
    public Collection<Contacto> getContactoCollection() {
        return this.contactoCollection;
    }
    
    
    public void setContactoCollection(Collection<Contacto>  v) {
        this.contactoCollection = v;
    }
    // Getter and Setters @ManyToMany Collection<CuentaBancaria>
    
    public Collection<CuentaBancaria> getCuentaBancariaCollection() {
        return this.cuentaBancariaCollection;
    }
    
    
    public void setCuentaBancariaCollection(Collection<CuentaBancaria>  v) {
        this.cuentaBancariaCollection = v;
    }

    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof Cliente)) {
            return false;
        }

    	Cliente other = (Cliente ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    public String toString() {
        return "com.pmarlen.model.beans.Cliente[id = "+id+ "]";
    }

}
