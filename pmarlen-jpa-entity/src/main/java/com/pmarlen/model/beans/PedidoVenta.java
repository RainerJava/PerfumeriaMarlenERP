
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
 * Class for mapping JPA Entity of Table Pedido_Venta.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2012/04/24 05:24
 */



@Entity
@Table(name = "PEDIDO_VENTA")
public class PedidoVenta implements java.io.Serializable {
    private static final long serialVersionUID = 305035296;
    
    /**
    * id
    */
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    /**
    * cliente id
    */
    @JoinColumn(name = "CLIENTE_ID" , referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Cliente cliente;
    
    /**
    * forma de pago id
    */
    @JoinColumn(name = "FORMA_DE_PAGO_ID" , referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private FormaDePago formaDePago;
    
    /**
    * usuario id
    */
    @JoinColumn(name = "USUARIO_ID" , referencedColumnName = "USUARIO_ID")
    @ManyToOne(optional = false)
    private Usuario usuario;
    
    /**
    * es fiscal
    */
    @Basic(optional = false)
    @Column(name = "ES_FISCAL")
    private int esFiscal;
    
    /**
    * comentarios
    */
    @Basic(optional = true)
    @Column(name = "COMENTARIOS")
    private String comentarios;
    
    /**
    * factoriva
    */
    @Basic(optional = true)
    @Column(name = "FACTORIVA")
    private Double factoriva;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedidoVenta")
    private Collection<PedidoVentaEstado> pedidoVentaEstadoCollection;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedidoVenta")
    private Collection<PedidoVentaDetalle> pedidoVentaDetalleCollection;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedidoVenta")
    private Collection<FacturaCliente> facturaClienteCollection;
    

    /** 
     * Default Constructor
     */
    public PedidoVenta() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public PedidoVenta( Integer id ) {
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

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente v) {
        this.cliente = v;
    }

    public FormaDePago getFormaDePago() {
        return this.formaDePago;
    }

    public void setFormaDePago(FormaDePago v) {
        this.formaDePago = v;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario v) {
        this.usuario = v;
    }

    public int getEsFiscal() {
        return this.esFiscal;
    }

    public void setEsFiscal(int v) {
        this.esFiscal = v;
    }

    public String getComentarios() {
        return this.comentarios;
    }

    public void setComentarios(String v) {
        this.comentarios = v;
    }

    public Double getFactoriva() {
        return this.factoriva;
    }

    public void setFactoriva(Double v) {
        this.factoriva = v;
    }

    
    public Collection<PedidoVentaEstado> getPedidoVentaEstadoCollection() {
        return this.pedidoVentaEstadoCollection;
    }
    
    
    public void setPedidoVentaEstadoCollection(Collection<PedidoVentaEstado>  v) {
        this.pedidoVentaEstadoCollection = v;
    }
    
    public Collection<PedidoVentaDetalle> getPedidoVentaDetalleCollection() {
        return this.pedidoVentaDetalleCollection;
    }
    
    
    public void setPedidoVentaDetalleCollection(Collection<PedidoVentaDetalle>  v) {
        this.pedidoVentaDetalleCollection = v;
    }
    
    public Collection<FacturaCliente> getFacturaClienteCollection() {
        return this.facturaClienteCollection;
    }
    
    
    public void setFacturaClienteCollection(Collection<FacturaCliente>  v) {
        this.facturaClienteCollection = v;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof PedidoVenta)) {
            return false;
        }

    	PedidoVenta other = (PedidoVenta ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.model.beans.PedidoVenta[id = "+id+ "]";
    }

}
