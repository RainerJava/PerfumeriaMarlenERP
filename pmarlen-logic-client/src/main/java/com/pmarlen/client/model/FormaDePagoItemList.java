/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.client.model;

import com.pmarlen.model.beans.FormaDePago;

/**
 *
 * @author alfred
 */
public class FormaDePagoItemList extends EntityDisplayableItem{
    private FormaDePago formaDePago;

    public FormaDePagoItemList(FormaDePago formaDePago){
        super(formaDePago.getId(),formaDePago.getDescripcion());
        this.formaDePago = formaDePago;
    }

    /**
     * @return the formaDePago
     */
    public FormaDePago getFormaDePago() {
        return formaDePago;
    }

}
