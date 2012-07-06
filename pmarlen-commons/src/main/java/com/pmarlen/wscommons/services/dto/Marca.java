
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
 * Class for mapping JPA Entity of Table Marca.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2011/06/09 05:47
 */



public class Marca implements java.io.Serializable {
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * linea id
    */
    private Linea linea;
    
    /**
    * empresa id
    */
    private Empresa empresa;
    
    /**
    * nombre
    */
    private String nombre;
    
    private Collection<Producto> productoCollection;
    
    
    private Collection<Multimedio> multimedioCollection;
    

    /** 
     * Default Constructor
     */
    public Marca() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public Marca( Integer id ) {
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

    public Linea getLinea() {
        return this.linea;
    }

    public void setLinea(Linea v) {
        this.linea = v;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa v) {
        this.empresa = v;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String v) {
        this.nombre = v;
    }

    
    public Collection<Producto> getProductoCollection() {
        return this.productoCollection;
    }
    
    
    public void setProductoCollection(Collection<Producto>  v) {
        this.productoCollection = v;
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
        if (!(o instanceof Marca)) {
            return false;
        }

    	Marca other = (Marca ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    public String toString() {
        return "com.pmarlen.model.beans.Marca[id = "+id+ "]";
    }

}
