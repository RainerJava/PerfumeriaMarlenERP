/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.client.model;

import com.pmarlen.model.beans.FormaDePago;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author alfred
 */
public class FormaDePagoComboBoxModel implements ComboBoxModel{
    ArrayList<FormaDePagoItemList> elements;
    FormaDePagoItemList selected;
    
    public FormaDePagoComboBoxModel(Collection<FormaDePago> list){
        elements = new ArrayList<FormaDePagoItemList>();
        FormaDePago fp = new FormaDePago();
        fp.setDescripcion("--");
        elements.add(new FormaDePagoItemList(fp));
        for(FormaDePago fpl: list){
            elements.add(new FormaDePagoItemList(fpl));
        }
        selected = elements.get(0);
    }
    
    public void setSelectedItem(Object anItem) {
        FormaDePago formaDePago4Select = ((FormaDePagoItemList)anItem).getFormaDePago();
        if( formaDePago4Select.getId() == null) {
            selected = null;
            return;
        }
        for(FormaDePagoItemList fpl: elements){
            if(formaDePago4Select.getId().equals(fpl.getFormaDePago().getId())){
                selected = fpl;
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
