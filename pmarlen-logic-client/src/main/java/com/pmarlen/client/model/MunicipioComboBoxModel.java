/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.client.model;


import java.util.ArrayList;
import java.util.Collection;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author alfred
 */
public class MunicipioComboBoxModel implements ComboBoxModel{
    ArrayList<MunicipioODelegacion> elements;
    MunicipioODelegacion selected;
    
    public MunicipioComboBoxModel(Collection<MunicipioODelegacion> list){
        elements = new ArrayList<MunicipioODelegacion>();
        MunicipioODelegacion mod = new MunicipioODelegacion(null);
        mod.setNombre("--");
        elements.add(mod);
        elements.addAll(list);
        selected = elements.get(0);
    }

    public MunicipioComboBoxModel(){
        elements = new ArrayList<MunicipioODelegacion>();
        MunicipioODelegacion mod = new MunicipioODelegacion(null);
        mod.setNombre("--");
        elements.add(mod);
        selected = elements.get(0);
    }
    
    public void setSelectedItem(Object anItem) {
        selected = (MunicipioODelegacion )anItem;
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
