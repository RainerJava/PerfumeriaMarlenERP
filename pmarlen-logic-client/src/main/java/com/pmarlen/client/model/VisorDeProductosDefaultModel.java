/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.client.model;

import com.pmarlen.client.ApplicationInfo;
import com.pmarlen.client.ApplicationLogic;
import com.pmarlen.client.FirstDataSynchronizer;
import com.pmarlen.model.beans.Multimedio;
import com.pmarlen.model.beans.Producto;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author alfred
 */
@Repository("visorDeProductosDefaultModel")
public class VisorDeProductosDefaultModel {
    private List<Producto> productoList;
    private BufferedImage[] productoFirstImageArray;
    
    DefaultTableModel productoTableModel;
    
    @Autowired
    private ApplicationLogic applicationLogic;
    
    public static final int DEFAULT_MIN_IMAGESIZE = 50;

    private FirstDataSynchronizer firstDataSynchronizer;
    
    private Logger logger;

    public VisorDeProductosDefaultModel() {
        logger = LoggerFactory.getLogger(VisorDeProductosDefaultModel.class.getName());
    }

    public JLabel createLabel(Image img) {
        JLabel label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);

        if (img != null) {
            int iw = img.getWidth(null);
            int ih = img.getHeight(null);

            label.setIcon(new ImageIcon(img.getScaledInstance(
                    DEFAULT_MIN_IMAGESIZE,
                    DEFAULT_MIN_IMAGESIZE,
                    BufferedImage.SCALE_SMOOTH)));
        } else {
            label.setText("-");
        }
        return label;
    }

    public BufferedImage getImageForDisplay(int index) {
        return productoFirstImageArray[index];
    }

    public BufferedImage[] getAllImageForDisplay() {
        return productoFirstImageArray;
    }

    public void setProductoList(List<Producto> productoList) {
        this.productoList = productoList;
        Object[][] prmv;
        Object[] names = null;
        names = new String[]{
                        ApplicationInfo.getLocalizedMessage(""),
                        ApplicationInfo.getLocalizedMessage("NAME")};
        
        prmv = new Object[productoList.size()][names.length];
        productoFirstImageArray = new BufferedImage[productoList.size()];
        Image image = null;
        for (int i = 0; i < productoList.size(); i++) {
            Collection<Multimedio> listMultimedio = null;
            Producto p = productoList.get(i);
            listMultimedio = p.getMultimedioCollection();
            
            if(firstDataSynchronizer.isSyncronizatedInThisSession()){
                Multimedio m = null;//BasicInfoDAO.getInstance().getFirstMultimedioByProducto(p.getId());
                if(m != null){
                    try {
                        image = ImageIO.read(new ByteArrayInputStream((byte[]) m.getContenido()));
                        logger.info("==>>> Ok image for Producto !!");
                    } catch (IOException ex) {
                        image = applicationLogic.getDefaultImageForProducto();
                        logger.info("==>>> creating default Image for Producto (0)");
                        logger.error("Valio", ex);
                    }
                    productoFirstImageArray[i]= (BufferedImage)image;
                } else{                    
                    image = applicationLogic.getDefaultImageForProducto();
                    logger.info("==>>> creating default Image for Producto (1)");
                    productoFirstImageArray[i]= (BufferedImage)image;

                }
            } else if (listMultimedio.size() > 0) {                
                try {
                    image = ImageIO.read(new ByteArrayInputStream((byte[]) listMultimedio.iterator().next().getContenido()));
                } catch (IOException ex) {
                    image = applicationLogic.getDefaultImageForProducto();                    
                    logger.info("==>>> creating default Image for Producto (2)");
                }
                productoFirstImageArray[i]= (BufferedImage)image;                
            } else {
                image = applicationLogic.getDefaultImageForProducto();
                logger.info("==>>> creating default Image for Producto (3)");
                productoFirstImageArray[i]= (BufferedImage)image;                
            }
            prmv[i][0] = createLabel(image);            
            prmv[i][1] = productoList.get(i).getNombre()+"("+productoList.get(i).getPresentacion()+")";
            
        }
        productoTableModel = new DefaultTableModel(prmv, names) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public TableModel getProductoTableModel() {
        return productoTableModel;
    }

    public Producto getSelected(int i) {
        return productoList.get(i);
    }

    /**
     * @return the productoList
     */
    public List<Producto> getProductoList() {
        return productoList;
    }

    public String[] getLabelsTextForProductoSelected(int index) {
        Producto p = getSelected(index);
        String[] textLabels = new String[6];

        textLabels[0] = p.getNombre();
        textLabels[1] = p.getPresentacion();
        textLabels[2] = String.valueOf(p.getUnidadesPorCaja());
        textLabels[3] = String.valueOf(p.getContenido());
        textLabels[4] = p.getUnidadMedida();
        textLabels[5] = ApplicationInfo.formatToCurrency(p.getPrecioBase());
        
        return textLabels;
    }

    /**
     * @param firstDataSynchronizer the firstDataSynchronizer to set
     */
    @Autowired
    public void setFirstDataSynchronizer(FirstDataSynchronizer firstDataSynchronizer) {
        logger.info("==>>> setFirstDataSynchronizer("+firstDataSynchronizer+");");
        this.firstDataSynchronizer = firstDataSynchronizer;
    }

    /**
     * @param applicationLogic the applicationLogic to set
     */
    public void setApplicationLogic(ApplicationLogic applicationLogic) {
        this.applicationLogic = applicationLogic;
    }

}
