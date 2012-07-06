
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
 * Class for mapping JPA Entity of Table Empresa.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2012/04/24 05:24
 */



@Entity
@Table(name = "EMPRESA")
public class Empresa implements java.io.Serializable {
    private static final long serialVersionUID = 391717236;
    
    /**
    * id
    */
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    /**
    * nombre
    */
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    
    /**
    * nombre fiscal
    */
    @Basic(optional = false)
    @Column(name = "NOMBRE_FISCAL")
    private String nombreFiscal;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresa")
    private Collection<Marca> marcaCollection;
    
    
    @ManyToMany(mappedBy = "empresaCollection")
    private Collection<Multimedio> multimedioCollection;
    

    /** 
     * Default Constructor
     */
    public Empresa() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public Empresa( Integer id ) {
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

    public String getNombreFiscal() {
        return this.nombreFiscal;
    }

    public void setNombreFiscal(String v) {
        this.nombreFiscal = v;
    }

    
    public Collection<Marca> getMarcaCollection() {
        return this.marcaCollection;
    }
    
    
    public void setMarcaCollection(Collection<Marca>  v) {
        this.marcaCollection = v;
    }
    // Getter and Setters @ManyToMany Collection<Multimedio>
    
    public Collection<Multimedio> getMultimedioCollection() {
        return this.multimedioCollection;
    }
    
    
    public void setMultimedioCollection(Collection<Multimedio>  v) {
        this.multimedioCollection = v;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof Empresa)) {
            return false;
        }

    	Empresa other = (Empresa ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.model.beans.Empresa[id = "+id+ "]";
    }

}
