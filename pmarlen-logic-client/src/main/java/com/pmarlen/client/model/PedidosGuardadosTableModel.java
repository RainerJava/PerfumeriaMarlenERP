/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.client.model;

import com.pmarlen.client.ApplicationInfo;
import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.beans.PedidoVentaEstado;
import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;


/**
 *
 * @author alfred
 */
public class PedidosGuardadosTableModel implements TableModel{
    private List<PedidoVenta> pedidosList;
    String[] colNames;
    public PedidosGuardadosTableModel(List<PedidoVenta> pedidosList) {
        this.pedidosList = (List<PedidoVenta>)pedidosList;
        colNames = new String[]{"Pre Pedido", "Nombre o Raz\u00F3n Social", "Forma de pago","Fecha"};
    }
    public int getRowCount() {
        return this.pedidosList.size();
    }

    public int getColumnCount() {
        return colNames.length;
    }

    public String getColumnName(int columnIndex) {
        return colNames[columnIndex];
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if(columnIndex==0){
            return "-1"; //String.valueOf(ApplicationInfo.getNumPseudoPedido(pedidosList.get(rowIndex)));
        } else if(columnIndex==1){
            return pedidosList.get(rowIndex).getCliente().getRazonSocial();
        } else if(columnIndex==2){
            return pedidosList.get(rowIndex).getFormaDePago().getDescripcion();
        } else if(columnIndex==3){
            PedidoVentaEstado pve = pedidosList.get(rowIndex).getPedidoVentaEstadoCollection().iterator().next();
            return ApplicationInfo.formatToLongDate(pve.getFecha());
        } else {
            return null;
        }
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        
    }

    public void addTableModelListener(TableModelListener l) {        
    }

    public void removeTableModelListener(TableModelListener l) {
        
    }
}
