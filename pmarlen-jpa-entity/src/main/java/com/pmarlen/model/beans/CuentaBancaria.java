
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
 * Class for mapping JPA Entity of Table Cuenta_Bancaria.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2012/04/24 05:24
 */



@Entity
@Table(name = "CUENTA_BANCARIA")
public class CuentaBancaria implements java.io.Serializable {
    private static final long serialVersionUID = 141437242;
    
    /**
    * id
    */
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;
    
    /**
    * banco id
    */
    @JoinColumn(name = "BANCO_ID" , referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Banco banco;
    
    /**
    * clabe
    */
    @Basic(optional = false)
    @Column(name = "CLABE")
    private String clabe;
    
    /**
    * sucursal
    */
    @Basic(optional = true)
    @Column(name = "SUCURSAL")
    private String sucursal;
    
    /**
    * referecia
    */
    @Basic(optional = true)
    @Column(name = "REFERECIA")
    private String referecia;
    
    /**
    * beneficiario
    */
    @Basic(optional = false)
    @Column(name = "BENEFICIARIO")
    private String beneficiario;
    
    @JoinTable(name               = "CLIENTE_CUENTA_BANCARIA",
               joinColumns        = {@JoinColumn(name = "CUENTA_BANCARIA_ID", referencedColumnName ="ID")},
               inverseJoinColumns = {@JoinColumn(name = "CLIENTE_ID", referencedColumnName ="ID")}
               )
    @ManyToMany
    private Collection<Cliente> clienteCollection;
    
    
    @JoinTable(name               = "PROVEEDOR_CUENTA_BANCARIA",
               joinColumns        = {@JoinColumn(name = "CUENTA_BANCARIA_ID", referencedColumnName ="ID")},
               inverseJoinColumns = {@JoinColumn(name = "PROVEEDOR_ID", referencedColumnName ="ID")}
               )
    @ManyToMany
    private Collection<Proveedor> proveedorCollection;
    

    /** 
     * Default Constructor
     */
    public CuentaBancaria() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public CuentaBancaria( String id ) {
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

    public Banco getBanco() {
        return this.banco;
    }

    public void setBanco(Banco v) {
        this.banco = v;
    }

    public String getClabe() {
        return this.clabe;
    }

    public void setClabe(String v) {
        this.clabe = v;
    }

    public String getSucursal() {
        return this.sucursal;
    }

    public void setSucursal(String v) {
        this.sucursal = v;
    }

    public String getReferecia() {
        return this.referecia;
    }

    public void setReferecia(String v) {
        this.referecia = v;
    }

    public String getBeneficiario() {
        return this.beneficiario;
    }

    public void setBeneficiario(String v) {
        this.beneficiario = v;
    }

    // Getter and Setters @ManyToMany Collection<Cliente>
    
    public Collection<Cliente> getClienteCollection() {
        return this.clienteCollection;
    }
    
    
    public void setClienteCollection(Collection<Cliente>  v) {
        this.clienteCollection = v;
    }
    // Getter and Setters @ManyToMany Collection<Proveedor>
    
    public Collection<Proveedor> getProveedorCollection() {
        return this.proveedorCollection;
    }
    
    
    public void setProveedorCollection(Collection<Proveedor>  v) {
        this.proveedorCollection = v;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof CuentaBancaria)) {
            return false;
        }

    	CuentaBancaria other = (CuentaBancaria ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.model.beans.CuentaBancaria[id = "+id+ "]";
    }

}