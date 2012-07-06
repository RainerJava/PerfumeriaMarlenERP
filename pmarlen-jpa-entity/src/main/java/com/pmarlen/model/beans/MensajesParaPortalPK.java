
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
 * Class for mapping JPA Entity of Table Mensajes_para_portal_P_K.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2012/04/24 05:24
 */



@Embeddable

public class MensajesParaPortalPK implements java.io.Serializable {
    private static final long serialVersionUID = 1030138392;
    
    /**
    * fecha
    */
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date fecha;
    
    /**
    * usuariousuario id
    */
    @Basic(optional = false)
    @Column(name = "USUARIOUSUARIO_ID")
    private String usuariousuarioId;

    /** 
     * Default Constructor
     */
    public MensajesParaPortalPK() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public MensajesParaPortalPK( java.util.Date fecha, String usuariousuarioId ) {
        this.fecha 	= 	fecha;
        this.usuariousuarioId 	= 	usuariousuarioId;

    }
    
    /**
     * Getters and Setters
     */
    public java.util.Date getFecha() {
        return this.fecha;
    }

    public void setFecha(java.util.Date v) {
        this.fecha = v;
    }

    public String getUsuariousuarioId() {
        return this.usuariousuarioId;
    }

    public void setUsuariousuarioId(String v) {
        this.usuariousuarioId = v;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash = ( (fecha != null ? fecha.hashCode() : 0 ) + 
			 (usuariousuarioId != null ? usuariousuarioId.hashCode() : 0 ) );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof MensajesParaPortalPK)) {
            return false;
        }

    	MensajesParaPortalPK other = (MensajesParaPortalPK ) o;
        if ( (this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha)) || 
             (this.usuariousuarioId == null && other.usuariousuarioId != null) || (this.usuariousuarioId != null && !this.usuariousuarioId.equals(other.usuariousuarioId))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.model.beans.MensajesParaPortalPK[fecha = "+fecha+" , usuariousuarioId = "+usuariousuarioId+ "]";
    }

}
