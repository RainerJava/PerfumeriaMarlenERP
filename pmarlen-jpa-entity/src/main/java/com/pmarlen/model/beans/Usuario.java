
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
 * Class for mapping JPA Entity of Table Usuario.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2012/04/24 05:24
 */



@Entity
@Table(name = "USUARIO")
public class Usuario implements java.io.Serializable {
    private static final long serialVersionUID = 645440018;
    
    /**
    * usuario id
    */
    @Id
    @Basic(optional = false)
    @Column(name = "USUARIO_ID")
    private String usuarioId;
    
    /**
    * habilitado
    */
    @Basic(optional = false)
    @Column(name = "HABILITADO")
    private int habilitado;
    
    /**
    * nombre completo
    */
    @Basic(optional = false)
    @Column(name = "NOMBRE_COMPLETO")
    private String nombreCompleto;
    
    /**
    * password
    */
    @Basic(optional = false)
    @Column(name = "PASSWORD")
    private String password;
    
    /**
    * email
    */
    @Basic(optional = true)
    @Column(name = "EMAIL")
    private String email;
    
    /**
    * factor comision venta
    */
    @Basic(optional = true)
    @Column(name = "FACTOR_COMISION_VENTA")
    private Double factorComisionVenta;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<PedidoVentaEstado> pedidoVentaEstadoCollection;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<PedidoVenta> pedidoVentaCollection;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<MensajesParaPortal> mensajesParaPortalCollection;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<PedidoCompra> pedidoCompraCollection;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<PedidoCompraEstado> pedidoCompraEstadoCollection;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollection;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<EstadoDeCuenta> estadoDeCuentaCollection;
    
    
    @ManyToMany(mappedBy = "usuarioCollection")
    private Collection<Perfil> perfilCollection;
    

    /** 
     * Default Constructor
     */
    public Usuario() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public Usuario( String usuarioId ) {
        this.usuarioId 	= 	usuarioId;

    }
    
    /**
     * Getters and Setters
     */
    public String getUsuarioId() {
        return this.usuarioId;
    }

    public void setUsuarioId(String v) {
        this.usuarioId = v;
    }

    public int getHabilitado() {
        return this.habilitado;
    }

    public void setHabilitado(int v) {
        this.habilitado = v;
    }

    public String getNombreCompleto() {
        return this.nombreCompleto;
    }

    public void setNombreCompleto(String v) {
        this.nombreCompleto = v;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String v) {
        this.password = v;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String v) {
        this.email = v;
    }

    public Double getFactorComisionVenta() {
        return this.factorComisionVenta;
    }

    public void setFactorComisionVenta(Double v) {
        this.factorComisionVenta = v;
    }

    
    public Collection<PedidoVentaEstado> getPedidoVentaEstadoCollection() {
        return this.pedidoVentaEstadoCollection;
    }
    
    
    public void setPedidoVentaEstadoCollection(Collection<PedidoVentaEstado>  v) {
        this.pedidoVentaEstadoCollection = v;
    }
    
    public Collection<PedidoVenta> getPedidoVentaCollection() {
        return this.pedidoVentaCollection;
    }
    
    
    public void setPedidoVentaCollection(Collection<PedidoVenta>  v) {
        this.pedidoVentaCollection = v;
    }
    
    public Collection<MensajesParaPortal> getMensajesParaPortalCollection() {
        return this.mensajesParaPortalCollection;
    }
    
    
    public void setMensajesParaPortalCollection(Collection<MensajesParaPortal>  v) {
        this.mensajesParaPortalCollection = v;
    }
    
    public Collection<PedidoCompra> getPedidoCompraCollection() {
        return this.pedidoCompraCollection;
    }
    
    
    public void setPedidoCompraCollection(Collection<PedidoCompra>  v) {
        this.pedidoCompraCollection = v;
    }
    
    public Collection<PedidoCompraEstado> getPedidoCompraEstadoCollection() {
        return this.pedidoCompraEstadoCollection;
    }
    
    
    public void setPedidoCompraEstadoCollection(Collection<PedidoCompraEstado>  v) {
        this.pedidoCompraEstadoCollection = v;
    }
    
    public Collection<MovimientoHistoricoProducto> getMovimientoHistoricoProductoCollection() {
        return this.movimientoHistoricoProductoCollection;
    }
    
    
    public void setMovimientoHistoricoProductoCollection(Collection<MovimientoHistoricoProducto>  v) {
        this.movimientoHistoricoProductoCollection = v;
    }
    
    public Collection<EstadoDeCuenta> getEstadoDeCuentaCollection() {
        return this.estadoDeCuentaCollection;
    }
    
    
    public void setEstadoDeCuentaCollection(Collection<EstadoDeCuenta>  v) {
        this.estadoDeCuentaCollection = v;
    }
    // Getter and Setters @ManyToMany Collection<Perfil>
    
    public Collection<Perfil> getPerfilCollection() {
        return this.perfilCollection;
    }
    
    
    public void setPerfilCollection(Collection<Perfil>  v) {
        this.perfilCollection = v;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (usuarioId != null ? usuarioId.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof Usuario)) {
            return false;
        }

    	Usuario other = (Usuario ) o;
        if ( (this.usuarioId == null && other.usuarioId != null) || (this.usuarioId != null && !this.usuarioId.equals(other.usuarioId))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.model.beans.Usuario[usuarioId = "+usuarioId+ "]";
    }

}
