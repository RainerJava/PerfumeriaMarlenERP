
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
 * Class for mapping JPA Entity of Table Perfil.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2012/04/24 05:24
 */



@Entity
@Table(name = "PERFIL")
public class Perfil implements java.io.Serializable {
    private static final long serialVersionUID = 246826037;
    
    /**
    * id
    */
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;
    
    /**
    * descripcion
    */
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @JoinTable(name               = "USUARIO_PERFIL",
               joinColumns        = {@JoinColumn(name = "PERFIL_ID", referencedColumnName ="ID")},
               inverseJoinColumns = {@JoinColumn(name = "USUARIO_ID", referencedColumnName ="USUARIO_ID")}
               )
    @ManyToMany
    private Collection<Usuario> usuarioCollection;
    

    /** 
     * Default Constructor
     */
    public Perfil() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public Perfil( String id ) {
        this.id 	= 	id;

    }
    
    /**
     * Getters and Setters
     */
    public String getId() {
        return this.id;
    }

    public void setId(String v) {
        this.id = v;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String v) {
        this.descripcion = v;
    }

    // Getter and Setters @ManyToMany Collection<Usuario>
    
    public Collection<Usuario> getUsuarioCollection() {
        return this.usuarioCollection;
    }
    
    
    public void setUsuarioCollection(Collection<Usuario>  v) {
        this.usuarioCollection = v;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof Perfil)) {
            return false;
        }

    	Perfil other = (Perfil ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.model.beans.Perfil[id = "+id+ "]";
    }

}
