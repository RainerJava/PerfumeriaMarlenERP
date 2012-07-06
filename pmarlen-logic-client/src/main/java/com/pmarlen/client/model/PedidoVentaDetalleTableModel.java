/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.client.model;

import com.pmarlen.client.ApplicationInfo;
import com.pmarlen.client.ApplicationLogic;
import com.pmarlen.client.controller.PrincipalControl;
import com.pmarlen.model.beans.PedidoVentaDetalle;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;


/**
 *
 * @author alfred
 */

class PedidoVentaDetalleWithDecAplyer extends PedidoVentaDetalle{
	private boolean aplicarDesc;

	/**
	 * @return the aplicarDesc
	 */
	public boolean isAplicarDesc() {
		return aplicarDesc;
	}

	/**
	 * @param aplicarDesc the aplicarDesc to set
	 */
	public void setAplicarDesc(boolean aplicarDesc) {
		this.aplicarDesc = aplicarDesc;
	}
}

public class PedidoVentaDetalleTableModel implements TableModel{
    private ArrayList<PedidoVentaDetalle> detallePedidoList;
    String[] colNames;
    private ApplicationLogic applicationLogic;

    private PrincipalControl principalControl;
    
    public PedidoVentaDetalleTableModel(Collection<PedidoVentaDetalle> detallePedidoList) {
        this.detallePedidoList = (ArrayList<PedidoVentaDetalle>)detallePedidoList;
        
        colNames = new String[]{
                "Cantidad","[Cajas]+Pzs","Nombre(Presentacion)", "Precio","% Desc.","Aplicar","Importe","Importe - Descuento"};
    }
    public int getRowCount() {
        return this.detallePedidoList.size();
    }

    public int getColumnCount() {
        return colNames.length;
    }

    public String getColumnName(int columnIndex) {
        return colNames[columnIndex];
    }

    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex==0){
            return Integer.class;
        } if(columnIndex==5){
            return Boolean.class;
			//return Double.class;
        } else {
            return String.class;
        }
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex==0 || columnIndex==5;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        
        if(columnIndex==0){
            return detallePedidoList.get(rowIndex).getCantidad();
        } else if(columnIndex==1){
            return  "["+
                    String.valueOf(detallePedidoList.get(rowIndex).getCantidad() /
                    detallePedidoList.get(rowIndex).getProducto().getUnidadesPorCaja()) + "] + "+
                    String.valueOf(detallePedidoList.get(rowIndex).getCantidad() %
                    detallePedidoList.get(rowIndex).getProducto().getUnidadesPorCaja());
        } else if(columnIndex==2){
            return  detallePedidoList.get(rowIndex).getProducto().getNombre()+"("+
                    detallePedidoList.get(rowIndex).getProducto().getPresentacion()+")";
        } else if(columnIndex==3){
			double precioBase = detallePedidoList.get(rowIndex).getProducto().getPrecioBase();
			if(this.applicationLogic.getSession().getPedidoVenta().getEsFiscal()==0){
				precioBase = precioBase * (1.0+this.applicationLogic.getFactorIVA());
			}
            return ApplicationInfo.formatToCurrency( precioBase );
        } else if(columnIndex==4){
            return ApplicationInfo.formatToPercentage(detallePedidoList.get(
                    rowIndex).getDescuentoAplicado());
        } else if(columnIndex==5){
			return detallePedidoList.get(rowIndex).getDescuentoAplicado() > 0.0;
			//return detallePedidoList.get(rowIndex).getDescuentoAplicado();
        } else if(columnIndex==6){
			double precioBase = detallePedidoList.get(rowIndex).getProducto().getPrecioBase();
			if(this.applicationLogic.getSession().getPedidoVenta().getEsFiscal()==0){
				precioBase = precioBase * (1.0+this.applicationLogic.getFactorIVA());
			}
			double importe = precioBase * detallePedidoList.get(rowIndex).getCantidad();
            return ApplicationInfo.formatToCurrency(importe);
        } else if(columnIndex==7){
            double precioBase = detallePedidoList.get(rowIndex).getProducto().getPrecioBase();
			if(this.applicationLogic.getSession().getPedidoVenta().getEsFiscal()==0){
				precioBase = precioBase * (1.0+this.applicationLogic.getFactorIVA());
			}
			double importeDesc = precioBase * detallePedidoList.get(rowIndex).getCantidad() *
					(1-detallePedidoList.get(rowIndex).getDescuentoAplicado());
            return ApplicationInfo.formatToCurrency(importeDesc);
        } else {
            return null;
        }

    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        if(columnIndex==0){
            try {
                int intVal = Integer.parseInt(aValue.toString().trim());
                if( intVal > 0) {
                    detallePedidoList.get(rowIndex).setCantidad(new Integer(aValue.toString()));
                } else if( intVal ==  0) {
                    this.applicationLogic.deleteProductoFromCurrentPedidoVenta(rowIndex);                    
                }
                principalControl.updateDetallePedidoTable();
            }catch(NumberFormatException nfe){
                
            }
        } else if(columnIndex==5){
            //System.out.println("->PedidoVentaDetalleTableModel.setValueAt("+aValue+","+rowIndex+","+columnIndex+")");
            boolean bv = (Boolean)aValue;
			//Double dv = (Double)aValue;
            double  maxDescVal  = detallePedidoList.get(rowIndex).getProducto().getFactorMaxDesc();
            if( bv ) {
                detallePedidoList.get(rowIndex).setDescuentoAplicado(maxDescVal);
            } else {
                detallePedidoList.get(rowIndex).setDescuentoAplicado(0.0);
            }

			//detallePedidoList.get(rowIndex).setDescuentoAplicado((Double)aValue);
			
            principalControl.updateDetallePedidoTable();

        }

    }

    public void addTableModelListener(TableModelListener l) {        
    }

    public void removeTableModelListener(TableModelListener l) {
        
    }

    /**
     * @param applicationLogic the applicationLogic to set
     */
    public void setApplicationLogic(ApplicationLogic applicationLogic) {
        this.applicationLogic = applicationLogic;
    }

    /**
     * @param PrincipalControl the PrincipalControl to set
     */
    public void setPrincipalControl(PrincipalControl principalControl) {
        this.principalControl = principalControl;
    }
}
