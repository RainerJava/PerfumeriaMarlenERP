/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.client.model;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author alfred
 */
public class ImageCellRender  implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel)value;

        if(isSelected){
            label.setBackground(table.getSelectionBackground());
        } else {
            label.setBackground(Color.WHITE);
        }

        return label;
    }

}
