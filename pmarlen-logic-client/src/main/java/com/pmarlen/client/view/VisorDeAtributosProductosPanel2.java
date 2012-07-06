/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * VisorDeAtributosProductosPanel.java
 *
 * Created on 23/05/2009, 10:03:05 PM
 */

package com.pmarlen.client.view;

import com.pmarlen.client.ApplicationInfo;
import com.pmarlen.model.beans.Producto;


/**
 *
 * @author alfred
 */
public class VisorDeAtributosProductosPanel2 extends javax.swing.JPanel {
    
    /** Creates new form VisorDeAtributosProductosPanel */
    public VisorDeAtributosProductosPanel2() {
        initComponents();
        updateLabels(null);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        precio = new javax.swing.JLabel();
        precio5 = new javax.swing.JLabel();
        precioCaja = new javax.swing.JLabel();
        precioCaja5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        nombre = new javax.swing.JLabel();
        contenido = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(300, 40));
        setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.GridLayout(2, 3));

        precio.setFont(new java.awt.Font("Tahoma", 1, 11));
        precio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        precio.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(precio);

        precio5.setFont(new java.awt.Font("Tahoma", 1, 11));
        precio5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        precio5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(precio5);

        precioCaja.setFont(new java.awt.Font("Tahoma", 1, 11));
        precioCaja.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        precioCaja.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(precioCaja);

        precioCaja5.setFont(new java.awt.Font("Tahoma", 1, 11));
        precioCaja5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        precioCaja5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(precioCaja5);

        add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        nombre.setFont(new java.awt.Font("Tahoma", 1, 11));
        nombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel4.add(nombre);

        contenido.setFont(new java.awt.Font("Tahoma", 1, 11));
        contenido.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel4.add(contenido);

        add(jPanel4, java.awt.BorderLayout.WEST);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel contenido;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel nombre;
    private javax.swing.JLabel precio;
    private javax.swing.JLabel precio5;
    private javax.swing.JLabel precioCaja;
    private javax.swing.JLabel precioCaja5;
    // End of variables declaration//GEN-END:variables


    public void updateLabels(Producto producto) {
        if(producto == null){
            nombre.setText("");
            contenido.setText("");

            precio.setText("");
            precioCaja.setText("");

            precio5.setText("");
            precioCaja5.setText("");
        } else {
            nombre.setText(producto.getNombre()+"("+producto.getPresentacion()+")");
            contenido.setText(producto.getUnidadesPorCaja()+" x "+producto.getContenido()+" "+producto.getUnidadMedida());
            
            precio.setText(ApplicationInfo.formatToCurrency(producto.getPrecioBase()));
            precioCaja.setText(ApplicationInfo.formatToCurrency(producto.getPrecioBase()*producto.getUnidadesPorCaja()));

            precio5.setText(ApplicationInfo.formatToCurrency(producto.getPrecioBase()*0.95));
            precioCaja5.setText(ApplicationInfo.formatToCurrency(producto.getPrecioBase()*producto.getUnidadesPorCaja()*0.95));
        }
    }

}
