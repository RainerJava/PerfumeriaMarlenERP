/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.client.controller;

import com.pmarlen.client.model.ValidatorHelper;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author alfred
 */
public class JComponentValidator {

    ArrayList<ValidatorHelper> validate;
    Component parent;
    String title;

    JComponentValidator(Component parent,String title) {
        this.parent = parent;
        this.title  = title;
        validate = new ArrayList<ValidatorHelper>();
    }

    void add(ValidatorHelper vh){
        validate.add(vh);
    }

    public boolean validate() {
        JComponent validated = null;
        ValidatorHelper validateI;
        JComponent componentToFocus =  null;
        try {
            for (ValidatorHelper vh : validate) {
                componentToFocus = vh.getComponent();
                if (vh.isRequired()) {
                    if (vh.getComponent() instanceof JTextField &&
                            ((JTextField) vh.getComponent()).getText().trim().length() == 0) {
                        throw new Exception(vh.getRequiredMessage());

                    } else if (vh.getComponent() instanceof JComboBox &&
                            ((JComboBox) vh.getComponent()).getSelectedIndex() == 0) {
                        throw new Exception(vh.getRequiredMessage());
                    }
                }
                if (vh.getComponent() instanceof JTextField &&
                        ((JTextField) vh.getComponent()).getText().trim().length() > 0 &&
                        !((JTextField) vh.getComponent()).getText().trim().matches(vh.getRegexp()) ) {
                    throw new Exception(vh.getValidationMessage());
                }
            }

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this.parent,
                    ex.getMessage(),
                    title,
                    JOptionPane.ERROR_MESSAGE);
            componentToFocus.requestFocus();
            return false;
        }
    }
}
