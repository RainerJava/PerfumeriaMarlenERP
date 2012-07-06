/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.client.model;

import com.pmarlen.model.beans.Marca;

/**
 *
 * @author alfred
 */
public class MarcaTreeNode extends EntityDisplayableItem{
    private Marca marca;

    public MarcaTreeNode(Marca marca){
        super(marca.getId(),marca.getNombre());
        this.marca = marca;
    }

    /**
     * @return the marca
     */
    public Marca getMarca() {
        return marca;
    }

}
