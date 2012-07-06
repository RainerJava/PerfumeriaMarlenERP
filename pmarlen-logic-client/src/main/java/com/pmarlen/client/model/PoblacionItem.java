
package com.pmarlen.client.model;

import com.pmarlen.model.beans.Poblacion;

public class PoblacionItem {
    private Poblacion poblacion;
    
    public PoblacionItem(Poblacion poblacion) {
        this.poblacion = poblacion;    
    }

    public String toString() {
        return poblacion!=null?("["+poblacion.getCodigoPostal()+"]"+poblacion.getNombre()):"--";
    }

	/**
	 * @return the poblacion
	 */
	public Poblacion getPoblacion() {
		return poblacion;
	}
   
}