/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.client.model;


import com.pmarlen.model.beans.Cliente;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author alfred
 */
public class ClienteComboBoxModel implements ComboBoxModel{
    ArrayList<ClienteItemList> elements;
    ClienteItemList selected;
    
    public ClienteComboBoxModel(Collection<Cliente> list){
        elements = new ArrayList<ClienteItemList>();
        Cliente c = new Cliente();
        c.setRazonSocial("--");
        c.setRfc("RFC");
        elements.add(new ClienteItemList(c));
        for(Cliente cl: list){
            elements.add(new ClienteItemList(cl));
        }
        
        selected = elements.get(0);
    }
    
    public void setSelectedItem(Object anItem) {
        
        Cliente cliente4Select = ((ClienteItemList)anItem).getCliente();
        if( cliente4Select.getId() == null) {
            selected = null;
            return;
        }
        for(ClienteItemList cil: elements){
            if(cliente4Select.getId().equals(cil.getCliente().getId())){
                selected = cil;
                break;
            }
        }
    }

    public Object getSelectedItem() {
        return selected;
    }

    public int getSize() {
        return elements.size();
    }

    public Object getElementAt(int index) {
        return elements.get(index);
    }

    public void addListDataListener(ListDataListener l) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeListDataListener(ListDataListener l) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

}
