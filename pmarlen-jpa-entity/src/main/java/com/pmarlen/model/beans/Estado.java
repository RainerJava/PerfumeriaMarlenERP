
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
 * Class for mapping JPA Entity of Table Estado.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2012/04/24 05:24
 */



@Entity
@Table(name = "ESTADO")
public class Estado implements java.io.Serializable {
    private static final long serialVersionUID = 932143669;
    
    /**
    * id
    */
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    /**
    * descripcion
    */
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado")
    private Collection<PedidoVentaEstado> pedidoVentaEstadoCollection;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado")
    private Collection<PedidoCompraEstado> pedidoCompraEstadoCollection;
    

    /** 
     * Default Constructor
     */
    public Estado() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public Estado( Integer id ) {
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

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String v) {
        this.descripcion = v;
    }

    
    public Collection<PedidoVentaEstado> getPedidoVentaEstadoCollection() {
        return this.pedidoVentaEstadoCollection;
    }
    
    
    public void setPedidoVentaEstadoCollection(Collection<PedidoVentaEstado>  v) {
        this.pedidoVentaEstadoCollection = v;
    }
    
    public Collection<PedidoCompraEstado> getPedidoCompraEstadoCollection() {
        return this.pedidoCompraEstadoCollection;
    }
    
    
    public void setPedidoCompraEstadoCollection(Collection<PedidoCompraEstado>  v) {
        this.pedidoCompraEstadoCollection = v;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof Estado)) {
            return false;
        }

    	Estado other = (Estado ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.model.beans.Estado[id = "+id+ "]";
    }

}
