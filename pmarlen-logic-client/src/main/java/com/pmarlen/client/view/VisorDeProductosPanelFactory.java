/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.client.view;

import javax.swing.JPanel;

/**
 *
 * @author alfred
 */
public class VisorDeProductosPanelFactory {
    public static JPanel getVisorDeProductosPanel() {
        return new VisorDeProductosDefaultPanel();
    }
}
