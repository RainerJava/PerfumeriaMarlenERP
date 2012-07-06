/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.client.model;

import com.pmarlen.model.beans.Poblacion;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author alfred
 */
public class PoblacionComboBoxModel implements ComboBoxModel{
    ArrayList<PoblacionItem> elements;
    PoblacionItem selected;
    
    public PoblacionComboBoxModel(Collection<Poblacion> list){
        elements = new ArrayList<PoblacionItem>();
        PoblacionItem p = new PoblacionItem(null);
        elements.add(p);
        
		for(Poblacion pl: list){
			elements.add(new PoblacionItem(pl));
		}
		
        selected = elements.get(0);
    }

    public void setSelectedItem(Object anItem) {
        selected = (PoblacionItem )anItem;
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
