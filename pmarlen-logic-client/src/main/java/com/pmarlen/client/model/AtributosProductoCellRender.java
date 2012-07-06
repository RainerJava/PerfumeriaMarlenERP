/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.client.model;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author alfred
 */
public class AtributosProductoCellRender  implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JPanel jPanel = (JPanel)value;

        if(isSelected){
            jPanel.setBackground(table.getSelectionBackground());
            for(Component c:jPanel.getComponents()){
                c.setBackground(table.getSelectionBackground());
            }
        } else {
            jPanel.setBackground(Color.WHITE);
            for(Component c:jPanel.getComponents()){
                c.setBackground(Color.WHITE);
            }
        }

        return jPanel;
    }

}
