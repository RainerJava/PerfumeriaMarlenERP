/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.operation;

import com.pmarlen.model.beans.Linea;
import com.pmarlen.model.beans.Marca;
import com.pmarlen.model.beans.Producto;
import com.pmarlen.model.controller.LineaJpaController;
import com.pmarlen.model.controller.MarcaJpaController;
import com.pmarlen.model.controller.ProductoJpaController;
import com.pmarlen.web.common.view.messages.Messages;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author VEAXX9M
 */
public class VisorProductosLineaMarcaMB {

    @Autowired
    private ProductoJpaController productoJpaController;
    @Autowired
    private MarcaJpaController marcaJpaController;
    @Autowired
    private LineaJpaController lineaJpaController;
    @Autowired
    private PedidoNuevoMB pedidoNuevoMB;

    private Integer lineaSelectedId;
    private Integer marcaSelectedId;
    private Integer productoSelectedId;

    private List<Marca> marcaFilteredList;
    private List<Producto> productoFilteredList;

    private final Logger logger = LoggerFactory.getLogger(VisorProductosLineaMarcaMB.class);

    public VisorProductosLineaMarcaMB() {
        resetViewPage();
    }

    private void resetViewPage() {
        resetSlectionsBeans();
    }

    private void resetSlectionsBeans() {        
        lineaSelectedId       = null;
        marcaSelectedId         = null;
        productoSelectedId      = null;
        productoFilteredList    = new ArrayList<Producto>();
        marcaFilteredList       = new ArrayList<Marca>();
    }
    
    public void lineaChanged(ValueChangeEvent event) {
        Integer lineaNewValue = (Integer) event.getNewValue();
        logger.debug("-->> lineaChanged: lineaNewValue="+lineaNewValue);
        
        marcaSelectedId         = null;
        productoSelectedId      = null;
        productoFilteredList    = new ArrayList<Producto>();
        if(lineaNewValue != null){
            marcaFilteredList = marcaJpaController.findMarcaEntitiesByLinea(new Linea(lineaNewValue));
        } else {
            marcaFilteredList = new ArrayList<Marca>();
        }
    }
    
    public void marcaChanged(ValueChangeEvent event) {
        Integer marcaNewValue = (Integer) event.getNewValue();
        logger.debug("-->> marcaChanged: marcaNewValue="+marcaNewValue);
        if(marcaNewValue != null){
            productoFilteredList = productoJpaController.findProductoEntitiesByMarca(new Marca(marcaNewValue));
        } else {
            productoFilteredList = new ArrayList<Producto>();
        }
        productoSelectedId      = null;
    }

    public List<SelectItem> getLineaList() {

        List<SelectItem> itemsList = new ArrayList<SelectItem>();
        List<Linea> list = lineaJpaController.findLineaEntities();
        logger.debug("-->> getLineaList: ");

        itemsList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));

        for (Linea linea : list) {
            itemsList.add(new SelectItem(linea.getId(), linea.getNombre()));
        }
        return itemsList;
    }

    public List<SelectItem> getMarcaList() {
        List<SelectItem> itemsList = new ArrayList<SelectItem>();
        
        logger.debug("-->> getMarcaList: lineaSelectedId=" + lineaSelectedId);
        itemsList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));

        for (Marca marca : marcaFilteredList) {
            itemsList.add(new SelectItem(marca.getId(), marca.getNombre()));
        }
        return itemsList;
    }

    public List<Producto> getProductoList() {
        logger.debug("-->> getProductoList: marcaSelectedId=" + marcaSelectedId+"m productoFilteredList="+productoFilteredList);
        return productoFilteredList;
    }

    //--------------------------------------------------------------------------
    public void setProductoJpaController(ProductoJpaController productoJpaController) {
        this.productoJpaController = productoJpaController;
    }

    public void setMarcaJpaController(MarcaJpaController marcaJpaController) {
        this.marcaJpaController = marcaJpaController;
    }

    public void setLineaJpaController(LineaJpaController lineaJpaController) {
        this.lineaJpaController = lineaJpaController;
    }

    public void setPedidoNuevoMB(PedidoNuevoMB pedidoNuevoMB) {
        this.pedidoNuevoMB = pedidoNuevoMB;
    }
    //--------------------------------------------------------------------------
    public Integer getLineaSelectedId() {
        logger.debug("-->> getLineaSelectedId: lineaSelectedId=" + lineaSelectedId);
        return lineaSelectedId;
    }

    public void setLineaSelectedId(Integer lineaSelectedId) {
        logger.debug("-->> setLineaSelectedId: this.lineaSelectedId( "+this.lineaSelectedId+") = " + lineaSelectedId);
        this.lineaSelectedId = lineaSelectedId;
    }

    public Integer getMarcaSelectedId() {
        logger.debug("-->> getMarcaSelectedId: marcaSelectedId=" + marcaSelectedId);
        return marcaSelectedId;
    }

    public void setMarcaSelectedId(Integer marcaSelectedId) {
        logger.debug("-->> setMarcaSelectedId: this.marcaSelectedId("+this.marcaSelectedId+") = " + marcaSelectedId);
        this.marcaSelectedId = marcaSelectedId;
    }

    public Integer getProductoSelectedId() {
        return productoSelectedId;
    }

    public void setProductoSelectedId(Integer productoSelectedId) {
        this.productoSelectedId = productoSelectedId;
    }

    //--------------------------------------------------------------------------
}
