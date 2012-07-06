/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.operation;

import com.pmarlen.model.beans.Empresa;
import com.pmarlen.model.beans.Marca;
import com.pmarlen.model.beans.Producto;
import com.pmarlen.model.controller.EmpresaJpaController;
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
public class VisorProductosEmpresaMarcaMB {

    @Autowired
    private ProductoJpaController productoJpaController;
    @Autowired
    private MarcaJpaController marcaJpaController;
    @Autowired
    private EmpresaJpaController empresaJpaController;
    @Autowired
    private PedidoNuevoMB pedidoNuevoMB;

    private Integer empresaSelectedId;
    private Integer marcaSelectedId;
    private Integer productoSelectedId;

    private List<Marca> marcaFilteredList;
    private List<Producto> productoFilteredList;

    private final Logger logger = LoggerFactory.getLogger(VisorProductosEmpresaMarcaMB.class);

    public VisorProductosEmpresaMarcaMB() {
        resetViewPage();
    }

    private void resetViewPage() {
        resetSlectionsBeans();
    }

    private void resetSlectionsBeans() {        
        empresaSelectedId       = null;
        marcaSelectedId         = null;
        productoSelectedId      = null;
        productoFilteredList    = new ArrayList<Producto>();
        marcaFilteredList       = new ArrayList<Marca>();
    }
    
    public void empresaChanged(ValueChangeEvent event) {
        Integer empresaNewValue = (Integer) event.getNewValue();
        logger.debug("-->> empresaChanged: empresaNewValue="+empresaNewValue);
        
        marcaSelectedId         = null;
        productoSelectedId      = null;
        productoFilteredList    = new ArrayList<Producto>();
        if(empresaNewValue != null){
            marcaFilteredList = marcaJpaController.findMarcaEntitiesByEmpresa(new Empresa(empresaNewValue));
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

    public List<SelectItem> getEmpresaList() {

        List<SelectItem> itemsList = new ArrayList<SelectItem>();
        List<Empresa> list = empresaJpaController.findEmpresaEntities();
        logger.debug("-->> getEmpresaList: ");

        itemsList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));

        for (Empresa empresa : list) {
            itemsList.add(new SelectItem(empresa.getId(), empresa.getNombre()));
        }
        return itemsList;
    }

    public List<SelectItem> getMarcaList() {
        List<SelectItem> itemsList = new ArrayList<SelectItem>();
        
        logger.debug("-->> getMarcaList: empresaSelectedId=" + empresaSelectedId);
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

    public void setEmpresaJpaController(EmpresaJpaController empresaJpaController) {
        this.empresaJpaController = empresaJpaController;
    }

    public void setPedidoNuevoMB(PedidoNuevoMB pedidoNuevoMB) {
        this.pedidoNuevoMB = pedidoNuevoMB;
    }
    //--------------------------------------------------------------------------
    public Integer getEmpresaSelectedId() {
        logger.debug("-->> getEmpresaSelectedId: empresaSelectedId=" + empresaSelectedId);
        return empresaSelectedId;
    }

    public void setEmpresaSelectedId(Integer empresaSelectedId) {
        logger.debug("-->> setEmpresaSelectedId: this.empresaSelectedId( "+this.empresaSelectedId+") = " + empresaSelectedId);
        this.empresaSelectedId = empresaSelectedId;
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
