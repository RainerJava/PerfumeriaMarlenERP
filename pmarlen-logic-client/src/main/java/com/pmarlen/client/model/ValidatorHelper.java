/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.client.model;

import javax.swing.JComponent;

/**
 *
 * @author alfred
 */
public class ValidatorHelper {

    private JComponent component;
    private String regexp;
    private boolean required;
    private String validationMessage;
    private String requiredMessage;

    public ValidatorHelper(
            JComponent component, String regexp, boolean required,
            String validationMessage, String requiredMessage) {
        this.component = component;
        this.regexp = regexp;
        this.required = required;
        this.validationMessage = validationMessage;
        this.requiredMessage = requiredMessage;
    }

    /**
     * @return the component
     */
    public JComponent getComponent() {
        return component;
    }

    /**
     * @param component the component to set
     */
    public void setComponent(JComponent component) {
        this.component = component;
    }

    /**
     * @return the regexp
     */
    public String getRegexp() {
        return regexp;
    }

    /**
     * @param regexp the regexp to set
     */
    public void setRegexp(String regexp) {
        this.regexp = regexp;
    }

    /**
     * @return the required
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * @param required the required to set
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * @return the validationMessage
     */
    public String getValidationMessage() {
        return validationMessage;
    }

    /**
     * @param validationMessage the validationMessage to set
     */
    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    /**
     * @return the requiredMessage
     */
    public String getRequiredMessage() {
        return requiredMessage;
    }

    /**
     * @param requiredMessage the requiredMessage to set
     */
    public void setRequiredMessage(String requiredMessage) {
        this.requiredMessage = requiredMessage;
    }

}
