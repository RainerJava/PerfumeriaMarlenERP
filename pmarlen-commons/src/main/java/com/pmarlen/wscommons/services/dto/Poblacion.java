
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
 * Class for mapping JPA Entity of Table Poblacion.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2011/06/09 05:47
 */



public class Poblacion implements java.io.Serializable {
    
    /**
    * id
    */
    private Integer id;
        
    /**
    * nombre
    */
    private String nombre;
    
    /**
    * codigo postal
    */
    private int codigoPostal;
    
    /**
    * tipo asentamiento
    */
    private String tipoAsentamiento;
    
    /**
    * descripcion zona
    */
    private String descripcionZona;
    
    private Collection<Almacen> almacenCollection;
    
    
    private Collection<Contacto> contactoCollection;
    
    
    private Collection<Cliente> clienteCollection;
    
    
    private Collection<Proveedor> proveedorCollection;
    

    /** 
     * Default Constructor
     */
    public Poblacion() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public Poblacion( Integer id ) {
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

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String v) {
        this.nombre = v;
    }

    public int getCodigoPostal() {
        return this.codigoPostal;
    }

    public void setCodigoPostal(int v) {
        this.codigoPostal = v;
    }

    public String getTipoAsentamiento() {
        return this.tipoAsentamiento;
    }

    public void setTipoAsentamiento(String v) {
        this.tipoAsentamiento = v;
    }

    public String getDescripcionZona() {
        return this.descripcionZona;
    }

    public void setDescripcionZona(String v) {
        this.descripcionZona = v;
    }

    
    public Collection<Almacen> getAlmacenCollection() {
        return this.almacenCollection;
    }
    
    
    public void setAlmacenCollection(Collection<Almacen>  v) {
        this.almacenCollection = v;
    }
    
    public Collection<Contacto> getContactoCollection() {
        return this.contactoCollection;
    }
    
    
    public void setContactoCollection(Collection<Contacto>  v) {
        this.contactoCollection = v;
    }
    
    public Collection<Cliente> getClienteCollection() {
        return this.clienteCollection;
    }
    
    
    public void setClienteCollection(Collection<Cliente>  v) {
        this.clienteCollection = v;
    }
    
    public Collection<Proveedor> getProveedorCollection() {
        return this.proveedorCollection;
    }
    
    
    public void setProveedorCollection(Collection<Proveedor>  v) {
        this.proveedorCollection = v;
    }

    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof Poblacion)) {
            return false;
        }

    	Poblacion other = (Poblacion ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    public String toString() {
        return "com.pmarlen.model.beans.Poblacion[id = "+id+ "]";
    }

}
