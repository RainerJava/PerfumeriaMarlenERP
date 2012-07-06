/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.client.model;

import com.pmarlen.client.controller.PoblacionFinderControl;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author alfred
 */
public class EntidadComboBoxModel implements ComboBoxModel{
    ArrayList<EntidadFederativa> elements;
    EntidadFederativa selected;
	private Logger logger;

    public EntidadComboBoxModel(Collection<EntidadFederativa> list){
		logger = LoggerFactory.getLogger(EntidadComboBoxModel.class);		

        elements = new ArrayList<EntidadFederativa>();
        EntidadFederativa ef=new EntidadFederativa(null,"--");
        elements.add(ef);
        elements.addAll(list);
        selected = elements.get(0);
    }
    
    public void setSelectedItem(Object anItem) {
		logger.debug("--->> setSelectedItem("+anItem+")");
        selected = (EntidadFederativa)anItem;
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
