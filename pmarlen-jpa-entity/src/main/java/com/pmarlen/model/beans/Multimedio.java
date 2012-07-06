
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
 * Class for mapping JPA Entity of Table Multimedio.
 * 
 * @author Tracktopell::jpa-builder @see  http://tracktopell.dyndns.org/svn/xpressosystems/jpa-builder
 * @version 0.8.1
 * @date 2012/04/24 05:24
 */



@Entity
@Table(name = "MULTIMEDIO")
public class Multimedio implements java.io.Serializable {
    private static final long serialVersionUID = 569616903;
    
    /**
    * id
    */
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    /**
    * contenido
    */
    @Basic(optional = false)
    @Column(name = "CONTENIDO")
    private byte[] contenido;
    
    /**
    * mime type
    */
    @Basic(optional = false)
    @Column(name = "MIME_TYPE")
    private String mimeType;
    
    /**
    * nombre archivo
    */
    @Basic(optional = true)
    @Column(name = "NOMBRE_ARCHIVO")
    private String nombreArchivo;
    
    @ManyToMany(mappedBy = "multimedioCollection")
    private Collection<Marca> marcaCollection;
    
    
    @JoinTable(name               = "LINEA_MULTIMEDIO",
               joinColumns        = {@JoinColumn(name = "MULTIMEDIO_ID", referencedColumnName ="ID")},
               inverseJoinColumns = {@JoinColumn(name = "LINEA_ID", referencedColumnName ="ID")}
               )
    @ManyToMany
    private Collection<Linea> lineaCollection;
    
    
    @ManyToMany(mappedBy = "multimedioCollection")
    private Collection<Producto> productoCollection;
    
    
    @JoinTable(name               = "EMPRESA_MULTIMEDIO",
               joinColumns        = {@JoinColumn(name = "MULTIMEDIO_ID", referencedColumnName ="ID")},
               inverseJoinColumns = {@JoinColumn(name = "EMPRESA_ID", referencedColumnName ="ID")}
               )
    @ManyToMany
    private Collection<Empresa> empresaCollection;
    

    /** 
     * Default Constructor
     */
    public Multimedio() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public Multimedio( Integer id ) {
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

    public byte[] getContenido() {
        return this.contenido;
    }

    public void setContenido(byte[] v) {
        this.contenido = v;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String v) {
        this.mimeType = v;
    }

    public String getNombreArchivo() {
        return this.nombreArchivo;
    }

    public void setNombreArchivo(String v) {
        this.nombreArchivo = v;
    }

    // Getter and Setters @ManyToMany Collection<Marca>
    
    public Collection<Marca> getMarcaCollection() {
        return this.marcaCollection;
    }
    
    
    public void setMarcaCollection(Collection<Marca>  v) {
        this.marcaCollection = v;
    }
    // Getter and Setters @ManyToMany Collection<Linea>
    
    public Collection<Linea> getLineaCollection() {
        return this.lineaCollection;
    }
    
    
    public void setLineaCollection(Collection<Linea>  v) {
        this.lineaCollection = v;
    }
    // Getter and Setters @ManyToMany Collection<Producto>
    
    public Collection<Producto> getProductoCollection() {
        return this.productoCollection;
    }
    
    
    public void setProductoCollection(Collection<Producto>  v) {
        this.productoCollection = v;
    }
    // Getter and Setters @ManyToMany Collection<Empresa>
    
    public Collection<Empresa> getEmpresaCollection() {
        return this.empresaCollection;
    }
    
    
    public void setEmpresaCollection(Collection<Empresa>  v) {
        this.empresaCollection = v;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof Multimedio)) {
            return false;
        }

    	Multimedio other = (Multimedio ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.model.beans.Multimedio[id = "+id+ "]";
    }

}
