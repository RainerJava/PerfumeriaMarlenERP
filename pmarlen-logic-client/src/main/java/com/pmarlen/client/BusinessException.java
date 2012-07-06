/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.client;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author alfred
 */
public class BusinessException extends Exception{
    static ResourceBundle resourceBoundleMesages;

    private String title;

    BusinessException(String title,String message){
        super(getLocalizedMessage(message));
        this.title = title;
    }

    public static String getLocalizedMessage(String key){
        if(resourceBoundleMesages == null){
            resourceBoundleMesages = ResourceBundle.getBundle("messages.BusinessException");            
        }
        
        try {
            return resourceBoundleMesages.getString(key);
        } catch(MissingResourceException mre){
            return key;
        }
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }    
}
