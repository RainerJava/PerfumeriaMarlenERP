/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.client.model;

/**
 *
 * @author alfredo
 */
public class EntidadFederativa {
    private String id;
    private String nombre;

    public EntidadFederativa() {
    }

    public EntidadFederativa(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

	@Override
	public String toString() {
		return nombre;
	}
	
	
}
