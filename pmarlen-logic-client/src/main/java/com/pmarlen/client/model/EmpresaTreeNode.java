/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.client.model;

import com.pmarlen.model.beans.Empresa;

/**
 *
 * @author alfred
 */
public class EmpresaTreeNode extends EntityDisplayableItem{
    private Empresa empresa;

    public EmpresaTreeNode(Empresa empresa){
        super(empresa.getId(),empresa.getNombre());
        this.empresa = empresa;
    }

    /**
     * @return the marca
     */
    public Empresa getEmpresa() {
        return empresa;
    }

}
