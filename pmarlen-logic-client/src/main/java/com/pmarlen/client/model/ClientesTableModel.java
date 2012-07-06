/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.client.model;

import com.pmarlen.client.ApplicationInfo;
import com.pmarlen.model.beans.Cliente;
import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;


/**
 *
 * @author alfred
 */
public class ClientesTableModel implements TableModel{
    private List<Cliente> clienteList;
    String[] colNames;
    public ClientesTableModel(List<Cliente> clienteList) {
        this.clienteList = (List<Cliente>)clienteList;
        colNames = new String[]{"No.", "Nombre o Ras\u00F3n Social", "R.F.C.", "Calle",
            "No. Ext.", "No. Int.", "Direcci\u00F3n.", "Telefono",
            "e-Mail", "Observaciones","Fecha Alta"};
    }
    public int getRowCount() {
        return this.clienteList.size();
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
            return String.valueOf(clienteList.get(rowIndex).getId());
        } else if(columnIndex==1){
            return clienteList.get(rowIndex).getRazonSocial();
        } else if(columnIndex==2){
            return clienteList.get(rowIndex).getRfc();
        } else if(columnIndex==3){
            return clienteList.get(rowIndex).getCalle();
        } else if(columnIndex==4){
            return clienteList.get(rowIndex).getNumExterior();
        } else if(columnIndex==5){
            return clienteList.get(rowIndex).getNumInterior();
        } else if(columnIndex==6){
			String dir = "";
			if(clienteList.get(rowIndex).getObservaciones()!=null) {
                String[] observaciones = clienteList.get(rowIndex).getObservaciones().split("\\~");
				dir = observaciones.length>1?observaciones[1]:observaciones[0];
			}

            //return clienteList.get(rowIndex).getPoblacion().getMunicipioODelegacion().getEntidadFederativa().getNombre();
		/*
        } else if(columnIndex==7){
            return clienteList.get(rowIndex).getPoblacion().getMunicipioODelegacion().getNombre();
        } else if(columnIndex==8){
            return clienteList.get(rowIndex).getPoblacion().getNombre();
		} else if(columnIndex==7){
            return ApplicationInfo.formatToPostalCode(clienteList.get(rowIndex).getPoblacion().getCodigoPostal());
        }
		*/
			return dir;
        } else if(columnIndex==7){
            return clienteList.get(rowIndex).getTelefonos()!=null?clienteList.get(rowIndex).getTelefonos():"";
        } else if(columnIndex==8){
            return clienteList.get(rowIndex).getEmail()!=null?clienteList.get(rowIndex).getEmail():"";
        } else if(columnIndex==9){
			String obs = "";
			if(clienteList.get(rowIndex).getObservaciones()!=null) {
				obs = clienteList.get(rowIndex).getObservaciones().split("\\~")[0];
			}
            //return clienteList.get(rowIndex).getObservaciones()!=null?clienteList.get(rowIndex).getObservaciones():"";
			return obs;
        } else if(columnIndex==10){
            return ApplicationInfo.formatToSimpeDate(clienteList.get(rowIndex).getFechaCreacion());
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
	
	public Cliente getAt(int selectedIndex){
		return clienteList.get(selectedIndex);
	}
}
