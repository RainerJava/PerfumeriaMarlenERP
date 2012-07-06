
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
 * Class for mapping JPA Entity of Table Estado_de_cuenta_proveedores.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2012/04/24 05:24
 */



@Entity
@Table(name = "ESTADO_DE_CUENTA_PROVEEDORES")
public class EstadoDeCuentaProveedores implements java.io.Serializable {
    private static final long serialVersionUID = 1943435117;
    
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
    * importe
    */
    @Basic(optional = false)
    @Column(name = "IMPORTE")
    private double importe;
    
    /**
    * mes contable
    */
    @Basic(optional = true)
    @Column(name = "MES_CONTABLE")
    private Integer mesContable;
    
    /**
    * factura proveedor id
    */
    @JoinColumn(name = "FACTURA_PROVEEDOR_ID" , referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private FacturaProveedor facturaProveedor;
    
    /**
    * tipo movimiento id
    */
    @JoinColumn(name = "TIPO_MOVIMIENTO_ID" , referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TipoMovimiento tipoMovimiento;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoDeCuentaProveedores")
    private Collection<EstadoDeCuenta> estadoDeCuentaCollection;
    

    /** 
     * Default Constructor
     */
    public EstadoDeCuentaProveedores() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public EstadoDeCuentaProveedores( Integer id ) {
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

    public double getImporte() {
        return this.importe;
    }

    public void setImporte(double v) {
        this.importe = v;
    }

    public Integer getMesContable() {
        return this.mesContable;
    }

    public void setMesContable(Integer v) {
        this.mesContable = v;
    }

    public FacturaProveedor getFacturaProveedor() {
        return this.facturaProveedor;
    }

    public void setFacturaProveedor(FacturaProveedor v) {
        this.facturaProveedor = v;
    }

    public TipoMovimiento getTipoMovimiento() {
        return this.tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento v) {
        this.tipoMovimiento = v;
    }

    
    public Collection<EstadoDeCuenta> getEstadoDeCuentaCollection() {
        return this.estadoDeCuentaCollection;
    }
    
    
    public void setEstadoDeCuentaCollection(Collection<EstadoDeCuenta>  v) {
        this.estadoDeCuentaCollection = v;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof EstadoDeCuentaProveedores)) {
            return false;
        }

    	EstadoDeCuentaProveedores other = (EstadoDeCuentaProveedores ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.model.beans.EstadoDeCuentaProveedores[id = "+id+ "]";
    }

}
