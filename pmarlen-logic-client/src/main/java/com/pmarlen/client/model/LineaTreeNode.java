/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.client.model;

import com.pmarlen.model.beans.Linea;

/**
 *
 * @author alfred
 */
public class LineaTreeNode extends EntityDisplayableItem{
    private Linea linea;

    public LineaTreeNode(Linea linea){
        super(linea.getId(),linea.getNombre());
        this.linea = linea;
    }

    /**
     * @return the linea
     */
    public Linea getLinea() {
        return linea;
    }

}
