/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.client.model;

import com.pmarlen.model.beans.Poblacion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author alfred
 */
public class CodigoPostalComboBoxModel implements ComboBoxModel{
    ArrayList<CPDePoblacion> elements;
    CPDePoblacion selected;
    
    public CodigoPostalComboBoxModel(List<CPDePoblacion> list){
        elements = new ArrayList<CPDePoblacion>();
        elements.add(new CPDePoblacion("<TODOS>"));
        for(CPDePoblacion pl: list){
            elements.add(new CPDePoblacion(pl.toString()));                        
        }
        
        selected = elements.get(0);
    }

    public CodigoPostalComboBoxModel(){
        elements = new ArrayList<CPDePoblacion>();
        elements.add(new CPDePoblacion(null));
        selected = elements.get(0);
    }
    
    public void setSelectedItem(Object anItem) {
        selected = (CPDePoblacion )anItem;
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
