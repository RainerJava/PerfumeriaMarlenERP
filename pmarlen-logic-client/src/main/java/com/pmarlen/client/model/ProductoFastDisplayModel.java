package com.pmarlen.client.model;

import com.pmarlen.model.beans.Producto;

public class ProductoFastDisplayModel {
    private Producto producto;
    private Integer id;
    private String forDisplay;

    public ProductoFastDisplayModel(Integer id, String forDisplay,Producto producto) {
        super();
        this.id = id;
        this.forDisplay = forDisplay;
        this.producto = producto;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the forDisplay
     */
    public String getForDisplay() {
        return forDisplay;
    }

    /**
     * @param forDisplay the forDisplay to set
     */
    public void setForDisplay(String forDisplay) {
        this.forDisplay = forDisplay;
    }

    public String toString() {
        return forDisplay;
    }

    /**
     * @return the producto
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
