
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
 * Class for mapping JPA Entity of Table Factura_Proveedor.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2012/04/24 05:24
 */



@Entity
@Table(name = "FACTURA_PROVEEDOR")
public class FacturaProveedor implements java.io.Serializable {
    private static final long serialVersionUID = 1949571424;
    
    /**
    * id
    */
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    /**
    * folio
    */
    @Basic(optional = false)
    @Column(name = "FOLIO")
    private String folio;
    
    /**
    * pedido compra id
    */
    @JoinColumn(name = "PEDIDO_COMPRA_ID" , referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private PedidoCompra pedidoCompra;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facturaProveedor")
    private Collection<EstadoDeCuentaProveedores> estadoDeCuentaProveedoresCollection;
    

    /** 
     * Default Constructor
     */
    public FacturaProveedor() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public FacturaProveedor( Integer id ) {
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

    public String getFolio() {
        return this.folio;
    }

    public void setFolio(String v) {
        this.folio = v;
    }

    public PedidoCompra getPedidoCompra() {
        return this.pedidoCompra;
    }

    public void setPedidoCompra(PedidoCompra v) {
        this.pedidoCompra = v;
    }

    
    public Collection<EstadoDeCuentaProveedores> getEstadoDeCuentaProveedoresCollection() {
        return this.estadoDeCuentaProveedoresCollection;
    }
    
    
    public void setEstadoDeCuentaProveedoresCollection(Collection<EstadoDeCuentaProveedores>  v) {
        this.estadoDeCuentaProveedoresCollection = v;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof FacturaProveedor)) {
            return false;
        }

    	FacturaProveedor other = (FacturaProveedor ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.model.beans.FacturaProveedor[id = "+id+ "]";
    }

}
