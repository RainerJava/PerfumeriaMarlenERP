
package com.pmarlen.wscommons.services;

import com.pmarlen.model.beans.Cliente;
import com.pmarlen.model.beans.Empresa;
import com.pmarlen.model.beans.Estado;
import com.pmarlen.model.beans.FormaDePago;
import com.pmarlen.model.beans.Linea;
import com.pmarlen.model.beans.Marca;
import com.pmarlen.model.beans.Multimedio;
import com.pmarlen.model.beans.Producto;
import com.pmarlen.model.beans.Usuario;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author alfred
 */
@WebService
public interface GetListDataBusiness {
    @WebMethod(operationName = "getUsuarioList")
    public List<Usuario> getUsuarioList();

    @WebMethod(operationName = "getEmpresaList")
    public List<Empresa> getEmpresaList();

    @WebMethod(operationName = "getLineaList")
    public List<Linea> getLineaList();

    @WebMethod(operationName = "getMarcaList")
    public List<Marca> getMarcaList();

    @WebMethod(operationName = "getProductoList")
    public List<Producto> getProductoList();

    @WebMethod(operationName = "getProductoMultimedioList")
    public List<Multimedio> getProductoMultimedioList();

    @WebMethod(operationName = "getFormaDePagoList")
    public List<FormaDePago> getFormaDePagoList();

    @WebMethod(operationName = "getEstadoList")
    public List<Estado> getEstadoList();

    @WebMethod(operationName = "getClienteList")
    public List<Cliente> getClienteList();

    @WebMethod(operationName = "getServerVersion")
    public String getServerVersion();
}
