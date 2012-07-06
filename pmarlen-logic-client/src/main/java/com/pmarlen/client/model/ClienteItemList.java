/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.client.model;

import com.pmarlen.model.beans.Cliente;

/**
 *
 * @author alfred
 */
public class ClienteItemList extends EntityDisplayableItem{
    private Cliente cliente;

    public ClienteItemList(Cliente cliente){
        super(cliente.getId(),cliente.getRazonSocial());
        this.cliente = cliente;
    }

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

}
