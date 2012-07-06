package com.pmarlen.ws.services;

import com.pmarlen.model.beans.Cliente;
import com.pmarlen.model.beans.Empresa;
import com.pmarlen.model.beans.Estado;
import com.pmarlen.model.beans.FormaDePago;
import com.pmarlen.model.beans.Linea;
import com.pmarlen.model.beans.Marca;
import com.pmarlen.model.beans.Multimedio;
import com.pmarlen.model.beans.Producto;
import com.pmarlen.model.beans.Usuario;
import com.pmarlen.model.Constants;
import com.pmarlen.model.controller.GetListDataBusinesJpaController;
import com.pmarlen.wscommons.services.GetListDataBusiness;

import java.util.List;
import javax.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

@WebService(endpointInterface = "com.pmarlen.wscommons.services.GetListDataBusiness")
@Repository("getListDataBusiness")
public class GetListDataBusinessImpl implements GetListDataBusiness{
    @Autowired
    private GetListDataBusinesJpaController getListDataBusinesJpaController;
    
    public List<Usuario> getUsuarioList() {
        return getListDataBusinesJpaController.findUsuarioEntities(true,-1,-1);
    }


    public List<Empresa> getEmpresaList() {
        return getListDataBusinesJpaController.findEmpresaEntities(true,-1,-1);
    }


    public List<Linea> getLineaList() {
        return getListDataBusinesJpaController.findLineaEntities(true,-1,-1);
    }


    public List<Marca> getMarcaList() {
        return getListDataBusinesJpaController.findMarcaEntities(true,-1,-1);
    }


    public List<Producto> getProductoList() {
        return getListDataBusinesJpaController.findProductoEntities(true,-1,-1);
    }


    public List<Multimedio> getProductoMultimedioList() {
        return getListDataBusinesJpaController.findMultimedioEntities(true,-1,-1);
    }


    public List<FormaDePago> getFormaDePagoList() {
        return getListDataBusinesJpaController.findFormaDePagoEntities(true,-1,-1);
    }


    public List<Estado> getEstadoList() {
        return getListDataBusinesJpaController.findEstadoEntities(true,-1,-1);
    }


    public List<Cliente> getClienteList() {
        return getListDataBusinesJpaController.findClienteEntities(true,-1,-1);
    }


    public String getServerVersion() {
        return Constants.getServerVersion();        
    }

    /**
     * @param getListDataBusinesJpaController the getListDataBusinesJpaController to set
     */
    public void setGetListDataBusinesJpaController(GetListDataBusinesJpaController getListDataBusinesJpaController) {
        this.getListDataBusinesJpaController = getListDataBusinesJpaController;
    }

}
