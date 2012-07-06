
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
 * Class for mapping JPA Entity of Table Mensajes_para_portal.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2011/06/09 05:47
 */



public class MensajesParaPortal implements java.io.Serializable {
    
    /**
    * mensajes para portal p k
    */
    private MensajesParaPortalPK mensajesParaPortalPK;
    
    /**
    * contenido mensaje
    */
    private String contenidoMensaje;
    
    /**
    * usuariousuario id
    */
    private Usuario usuario;

    /** 
     * Default Constructor
     */
    public MensajesParaPortal() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public MensajesParaPortal( MensajesParaPortalPK mensajesParaPortalPK ) {
        this.mensajesParaPortalPK 	= 	mensajesParaPortalPK;

    }
    
    /**
     * Getters and Setters
     */
    public MensajesParaPortalPK getMensajesParaPortalPK() {
        return this.mensajesParaPortalPK;
    }

    public void setMensajesParaPortalPK(MensajesParaPortalPK v) {
        this.mensajesParaPortalPK = v;
    }

    public String getContenidoMensaje() {
        return this.contenidoMensaje;
    }

    public void setContenidoMensaje(String v) {
        this.contenidoMensaje = v;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario v) {
        this.usuario = v;
    }


    public int hashCode() {
        int hash = 0;
        hash = (mensajesParaPortalPK != null ? mensajesParaPortalPK.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof MensajesParaPortal)) {
            return false;
        }

    	MensajesParaPortal other = (MensajesParaPortal ) o;
        if ( (this.mensajesParaPortalPK == null && other.mensajesParaPortalPK != null) || (this.mensajesParaPortalPK != null && !this.mensajesParaPortalPK.equals(other.mensajesParaPortalPK))) {
            return false;
        }


    	return true;
    }

    public String toString() {
        return "com.pmarlen.model.beans.MensajesParaPortal[mensajesParaPortalPK = "+mensajesParaPortalPK+ "]";
    }

}
