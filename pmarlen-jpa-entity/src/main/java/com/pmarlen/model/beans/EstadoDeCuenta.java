
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
 * Class for mapping JPA Entity of Table Estado_de_cuenta.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2012/04/24 05:24
 */



@Entity
@Table(name = "ESTADO_DE_CUENTA")
public class EstadoDeCuenta implements java.io.Serializable {
    private static final long serialVersionUID = 1840511017;
    
    /**
    * id
    */
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    /**
    * es fiscal
    */
    @Basic(optional = false)
    @Column(name = "ES_FISCAL")
    private int esFiscal;
    
    /**
    * fecha
    */
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date fecha;
    
    /**
    * egreso
    */
    @JoinColumn(name = "EGRESO" , referencedColumnName = "ID")
    @ManyToOne(optional = true)
    private EstadoDeCuentaProveedores estadoDeCuentaProveedores;
    
    /**
    * ingreso
    */
    @JoinColumn(name = "INGRESO" , referencedColumnName = "ID")
    @ManyToOne(optional = true)
    private EstadoDeCuentaClientes estadoDeCuentaClientes;
    
    /**
    * importe
    */
    @Basic(optional = false)
    @Column(name = "IMPORTE")
    private double importe;
    
    /**
    * concepto
    */
    @Basic(optional = true)
    @Column(name = "CONCEPTO")
    private String concepto;
    
    /**
    * usuario id
    */
    @JoinColumn(name = "USUARIO_ID" , referencedColumnName = "USUARIO_ID")
    @ManyToOne(optional = false)
    private Usuario usuario;

    /** 
     * Default Constructor
     */
    public EstadoDeCuenta() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public EstadoDeCuenta( Integer id ) {
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

    public int getEsFiscal() {
        return this.esFiscal;
    }

    public void setEsFiscal(int v) {
        this.esFiscal = v;
    }

    public java.util.Date getFecha() {
        return this.fecha;
    }

    public void setFecha(java.util.Date v) {
        this.fecha = v;
    }

    public EstadoDeCuentaProveedores getEstadoDeCuentaProveedores() {
        return this.estadoDeCuentaProveedores;
    }

    public void setEstadoDeCuentaProveedores(EstadoDeCuentaProveedores v) {
        this.estadoDeCuentaProveedores = v;
    }

    public EstadoDeCuentaClientes getEstadoDeCuentaClientes() {
        return this.estadoDeCuentaClientes;
    }

    public void setEstadoDeCuentaClientes(EstadoDeCuentaClientes v) {
        this.estadoDeCuentaClientes = v;
    }

    public double getImporte() {
        return this.importe;
    }

    public void setImporte(double v) {
        this.importe = v;
    }

    public String getConcepto() {
        return this.concepto;
    }

    public void setConcepto(String v) {
        this.concepto = v;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario v) {
        this.usuario = v;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof EstadoDeCuenta)) {
            return false;
        }

    	EstadoDeCuenta other = (EstadoDeCuenta ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.model.beans.EstadoDeCuenta[id = "+id+ "]";
    }

}
