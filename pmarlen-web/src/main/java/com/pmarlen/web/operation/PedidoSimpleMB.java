/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.operation;

import com.pmarlen.businesslogic.LogicaFinaciera;
import com.pmarlen.businesslogic.PedidoVentaBusinessLogic;
import com.pmarlen.model.beans.AlmacenProducto;
import com.pmarlen.model.beans.Cliente;
import com.pmarlen.model.beans.PedidoVentaDetalle;
import com.pmarlen.model.beans.FormaDePago;
import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.beans.Producto;
import com.pmarlen.model.controller.ClienteJpaController;
import com.pmarlen.model.controller.EmpresaJpaController;
import com.pmarlen.model.controller.FormaDePagoJpaController;
import com.pmarlen.model.controller.LineaJpaController;
import com.pmarlen.model.controller.MarcaJpaController;
import com.pmarlen.model.controller.PedidoVentaEstadoJpaController;
import com.pmarlen.model.controller.PedidoVentaJpaController;
import com.pmarlen.model.controller.ProductoJpaController;
import com.pmarlen.web.common.view.messages.Messages;
import com.pmarlen.web.security.managedbean.SessionUserMB;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author VEAXX9M
 */
public class PedidoSimpleMB {

    @Autowired
    private ProductoJpaController productoJpaController;
    @Autowired
    private MarcaJpaController marcaJpaController;
    @Autowired
    private EmpresaJpaController empresaJpaController;
    @Autowired
    private LineaJpaController lineaJpaController;
    @Autowired
    private ClienteJpaController clienteJpaController;
    @Autowired
    private FormaDePagoJpaController formaDePagoJpaController;
    @Autowired
    private PedidoVentaJpaController pedidoVentaJpaController;
    @Autowired
    private PedidoVentaEstadoJpaController pedidoVentaEstadoJpaController;
    @Autowired
    private SessionUserMB sessionUserMB;

    
    @Autowired
    private PedidoVentaBusinessLogic pedidoVentaBusinessLogic;

    private List<PedidoVentaDetalleWrapper> pedidoVentaDetalleList;

    private PedidoVenta              pedidoVenta;

    private PedidoVentaDetalleWrapper detalleVentaPedidoSeleccionado;

    private String  nombreDescripcion;

    private Integer productoSelected;

    private List<SelectItem> productoConNombreDescripcion;

    private Integer clienteId;

    private Integer formaDePagoId;

    private final Logger logger = LoggerFactory.getLogger(PedidoSimpleMB.class);

    public PedidoSimpleMB() {
        reiniciarPedido();
    }

    private void reiniciarPedido() {
        detalleVentaPedidoSeleccionado = new PedidoVentaDetalleWrapper(new PedidoVentaDetalle());
        pedidoVentaDetalleList= new ArrayList<PedidoVentaDetalleWrapper>();
        pedidoVenta = new PedidoVenta();        
        productoConNombreDescripcion = new ArrayList<SelectItem> ();
    }

    public void actualizarEstatusFiscal(ActionEvent e) {
        logger.debug("################################ >> actualizarEstatusFiscal: ");
        pedidoVenta.setEsFiscal(pedidoVenta.getEsFiscal()==0?1:0);
        for(PedidoVentaDetalleWrapper dvp: pedidoVentaDetalleList) {
            if(pedidoVenta.getEsFiscal() != 0 ){
                dvp.setPrecioBase(dvp.getProducto().getPrecioBase() );
            } else {
                dvp.setPrecioBase(dvp.getProducto().getPrecioBase() * ( 1.0 + LogicaFinaciera.getImpuestoIVA()) );
            }
        }
    }

    public void agregarProductoBuscado(ActionEvent e) {
        logger.debug("################################ >> agregarProductoBuscado: productoSelected="+productoSelected);
        agregarProductoADetalle(productoSelected);
        productoSelected = null;
        productoConNombreDescripcion = new ArrayList<SelectItem> ();
        nombreDescripcion = null;
    }

    public void agregar1Producto(ActionEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        Integer productoId = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("productoId"));

        logger.debug("################################ >> agregar1Producto: productoId="+productoId);

        agregarProductoADetalle(productoId);
    }

    private void agregarProductoADetalle(Integer productoIdAgregar) {
        PedidoVentaDetalleWrapper detalleVentaPedidoAgregar = null;
        logger.debug("################################ >> agregarProductoADetalle: productoIdAgregar="+productoIdAgregar);
        for(PedidoVentaDetalleWrapper dvp: pedidoVentaDetalleList) {
            if(dvp.getProducto().getId() == productoIdAgregar ) {
                detalleVentaPedidoAgregar = dvp;
                break;
            }
        }

        if(detalleVentaPedidoAgregar != null) {
            if(detalleVentaPedidoAgregar.getCantidad()==detalleVentaPedidoAgregar.getCantMax()){
                logger.warn("################################ >> agregarProductoADetalle: Cantidad Exedida, no se agregara");
            } else {
                detalleVentaPedidoAgregar.setCantidad(detalleVentaPedidoAgregar.getCantidad()+1);
                logger.debug("################################ >> agregarProductoADetalle: \t Ok, edit");
            }
        } else {
            detalleVentaPedidoAgregar = new PedidoVentaDetalleWrapper(new PedidoVentaDetalle());
            detalleVentaPedidoAgregar.setCantidad(1);

            Producto producto = productoJpaController.findProducto(productoIdAgregar);
            Collection<AlmacenProducto> almacenProductoCollection = producto.getAlmacenProductoCollection();
            int cantMaxAlmacenes = 0;
            for (AlmacenProducto almacenProducto: almacenProductoCollection) {
                cantMaxAlmacenes += almacenProducto.getCantidadActual();
            }
            detalleVentaPedidoAgregar.setCantMax(cantMaxAlmacenes);
            detalleVentaPedidoAgregar.setProducto(producto);
            detalleVentaPedidoAgregar.setDescuentoAplicado(producto.getId()%2==0?producto.getFactorMaxDesc():0.0);
            detalleVentaPedidoAgregar.setDescuentoAplicado(0.0);

            if(pedidoVenta.getEsFiscal() != 0 ){
                detalleVentaPedidoAgregar.setPrecioBase(detalleVentaPedidoAgregar.getProducto().getPrecioBase() );
            } else {
                detalleVentaPedidoAgregar.setPrecioBase(detalleVentaPedidoAgregar.getProducto().getPrecioBase() * ( 1.0 + LogicaFinaciera.getImpuestoIVA()) );
            }

            pedidoVentaDetalleList.add(detalleVentaPedidoAgregar);
            logger.debug("################################ >> agregarProductoADetalle: \t Ok, Add new");
        }
    }

    public String confirmarPedido() {
        logger.debug("========================================================>>");
        logger.debug("-->>confirmarPedido():");
        logger.debug("========================================================>>");
        try{

            dataValidation();
            try {
                pedidoVenta.setCliente(new Cliente(clienteId));
                pedidoVenta.setFormaDePago(new FormaDePago(formaDePagoId));
                pedidoVenta.setUsuario(sessionUserMB.getUsuarioAuthenticated());
                pedidoVenta.setComentarios("Prueba de Pedido @"+new Date());
                
                pedidoVentaJpaController.create(pedidoVenta);
                logger.debug("<<===================== OK CREATED =======================");
                return "pedidoCreado";
            } catch(Exception ex){
                logger.debug("<<++++++++++++++++++++++++++++++++++++++++++++++++++");
                ex.printStackTrace(System.err);
                logger.debug("Error in MB to create pedido:",ex);
                
                throw new ValidatorException(
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,ex.toString(),ex.toString()));
            } finally {
                reiniciarPedido();
            }
        } catch(ValidatorException ve) {
            logger.debug("<<!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    ve.getFacesMessage());
            return null;
        }
    }

    private void dataValidation() throws ValidatorException{
        logger.debug("\t################################ >> dataValidation: clienteId="+clienteId+", formaDePagoId="+formaDePagoId);

        if( clienteId == null || (clienteId != null && clienteId.intValue()==0)){
            logger.debug("\t\t################################ >> throw new ValidatorException Cliente!");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Debe seleccionar el Cliente !!","Debe seleccionalr el Cliente !!"));
        }
        if( formaDePagoId == null || (formaDePagoId != null && formaDePagoId.intValue()==0)){
            logger.debug("\t\t################################ >> throw new ValidatorException FormaDePago!");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Debe seleccionar la Forma de Pago!!","Debe seleccionalr la Forma de Pago!!"));
        }
    }


    public void seleccionarProducto(ActionEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        Integer productoId = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("productoId"));

        logger.debug("################################ >> seleccionarProducto: productoId="+productoId);
        boolean selectedFromDetalle = false;
        for(PedidoVentaDetalleWrapper dvp: pedidoVentaDetalleList) {
            if(dvp.getProducto().getId() == productoId ) {                
                detalleVentaPedidoSeleccionado.setProducto(dvp.getProducto());
                detalleVentaPedidoSeleccionado.setCantidad(dvp.getCantidad());
                selectedFromDetalle = true;
                break;
            }
        }
        if(! selectedFromDetalle) {
            logger.warn("\t################################ >> productoId="+productoId+" => detalleVentaPedidoSeleccionado is null");
        }
        logger.debug("################################ >> end: seleccionarProducto");
    }

    public void activarDescuento(ActionEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        Integer productoId = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("productoId"));

        logger.debug("################################ >> activarDescuento: productoId="+productoId);
        boolean selectedFromDetalle = false;
        for(PedidoVentaDetalleWrapper dvp: pedidoVentaDetalleList) {
            if(dvp.getProducto().getId() == productoId) {
                dvp.setDescuentoAplicado(dvp.getProducto().getFactorMaxDesc());
                selectedFromDetalle = true;
                break;
            }
        }
        if(! selectedFromDetalle) {
            logger.warn("\t################################ >> productoId="+productoId+" => selectedFromDetalle="+selectedFromDetalle);
        }
        logger.debug("################################ >> end: activarDescuento");
    }

    public void desactivarDescuento(ActionEvent e) {
                FacesContext context = FacesContext.getCurrentInstance();
        Integer productoId = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("productoId"));

        logger.debug("################################ >> desactivarDescuento: productoId="+productoId);
        boolean selectedFromDetalle = false;
        for(PedidoVentaDetalleWrapper dvp: pedidoVentaDetalleList) {
            if(dvp.getProducto().getId() == productoId) {
                dvp.setDescuentoAplicado(0.0);
                selectedFromDetalle = true;
                break;
            }
        }
        if(! selectedFromDetalle) {
            logger.warn("\t################################ >> productoId="+productoId+" => selectedFromDetalle="+selectedFromDetalle);
        }
        logger.debug("################################ >> end: desactivarDescuento");
    }

    
    public void guardarCantidadPedidoVentaDetalleSeleccionado(ActionEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        
        logger.debug("################################ >> guardarCantidadPedidoVentaDetalleSeleccionado: productoId="+detalleVentaPedidoSeleccionado.getProducto().getId()+", cantidad="+detalleVentaPedidoSeleccionado.getCantidad());
        for(PedidoVentaDetalleWrapper dvp: pedidoVentaDetalleList) {
            if(dvp.getProducto().getId() == detalleVentaPedidoSeleccionado.getProducto().getId()) {
                dvp.setCantidad(detalleVentaPedidoSeleccionado.getCantidad());
                logger.debug("\t################################ >> ok, edited ");
                break;
            }
        }
        detalleVentaPedidoSeleccionado = new PedidoVentaDetalleWrapper(new PedidoVentaDetalle());
        logger.debug("################################ >> end: guardarCantidadPedidoVentaDetalleSeleccionado");
    }

    public void eliminarProducto(ActionEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        Integer productoId = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("productoId"));

        logger.debug("################################ >> eliminarProducto: productoId="+productoId);
        int indexToDelete = -1, i=0;
        for(PedidoVentaDetalleWrapper dvp: pedidoVentaDetalleList) {
            if(dvp.getProducto().getId() == productoId) {
                indexToDelete = i;
                logger.debug("\t################################ >> indexToDelete="+indexToDelete);
                break;
            }
            i++;
        }
        if(indexToDelete != -1) {
            PedidoVentaDetalleWrapper dvpDeleted = pedidoVentaDetalleList.remove(indexToDelete);
            logger.debug("\t\t################################ >> dvpDeleted = "+dvpDeleted);
        }
        logger.debug("################################ >> end: eliminarProducto");
    }

    public PedidoVentaDetalleFooter getPedidoFooter() {

        PedidoVentaDetalleFooter dvpf = new PedidoVentaDetalleFooter();
        
        int totalPiezas = 0;
        dvpf.setCantTotal(totalPiezas);
        dvpf.setDescuento(0.0);
        double subtotal  = 0.0;
        double descuento = 0.0;
        double descuentoRegistro = 0.0;
        double impuesto  = 0.0;
        double impuestoRegistro  = 0.0;
        double subTotalRegistro  = 0.0;
        for(PedidoVentaDetalleWrapper dvp: pedidoVentaDetalleList) {
            totalPiezas += dvp.getCantidad();
            subTotalRegistro = dvp.getCantidad() * dvp.getPrecioBase();

            if(pedidoVenta.getEsFiscal() != 0) {
                impuestoRegistro = subTotalRegistro * LogicaFinaciera.getImpuestoIVA();
            } else {
                impuestoRegistro = 0.0;
            }
            
            if(dvp.getDescuentoAplicado() != 0.0 ){
                descuentoRegistro = dvp.getDescuentoAplicado() * (subTotalRegistro + impuestoRegistro);
            } else {
                descuentoRegistro = 0;
            }

            impuesto  += impuestoRegistro;
            descuento += descuentoRegistro;
            subtotal  += subTotalRegistro;
        }

        dvpf.setNumItems(totalPiezas);

        dvpf.setSubtotal(subtotal);
        dvpf.setImpuesto(impuesto);
        dvpf.setDescuento(descuento);

        dvpf.setTotal(subtotal + impuesto - descuento);

        return dvpf;
    }

    public void nombreDescripcionChanged(ValueChangeEvent e) {
        String newValue = (String)e.getNewValue();
        logger.debug("################################ >> nombreDescripcionChanged: newValue="+newValue);
        productoConNombreDescripcion = new ArrayList<SelectItem>();
        if(newValue.trim().length() >= 3) {
            List<Producto> listResultOriginal =productoJpaController.findProductoEntities();
            for(Producto productoOriginal: listResultOriginal) {
                String nombreDescripcionOriginal = productoOriginal.getNombre()+"/"+productoOriginal.getPresentacion();
                if(nombreDescripcionOriginal.toLowerCase().contains(newValue.toLowerCase())) {
                    SelectItem productoSI = new SelectItem(productoOriginal.getId(),nombreDescripcionOriginal);
                    productoConNombreDescripcion.add(productoSI);
                }
            }
        }
    }

    public void buscarProductoConNombreDescripcion(ActionEvent e) {
        logger.debug("################################ >> productoConNombreDescripcion: nombreDescripcion="+nombreDescripcion);
        productoConNombreDescripcion = new ArrayList<SelectItem>();
        if(nombreDescripcion.trim().length() >= 3) {
            List<Producto> listResultOriginal =productoJpaController.findProductoEntities();
            for(Producto productoOriginal: listResultOriginal) {
                String nombreDescripcionOriginal = productoOriginal.getNombre()+"/"+productoOriginal.getPresentacion();
                if(nombreDescripcionOriginal.toLowerCase().contains(nombreDescripcion.toLowerCase())) {
                    SelectItem productoSI = new SelectItem(productoOriginal.getId(),nombreDescripcionOriginal);
                    productoConNombreDescripcion.add(productoSI);
                }
            }
        }
        
    }

    private Producto productoSearchedAndSelected;

    public  List<Producto> autocompletarProductoConNombreDescripcion(Object suggest) {
        List<Producto> listResult = null;
        nombreDescripcion = (String)suggest;
        logger.debug("################################ >> autocompletarProductoConNombreDescripcion: nombreDescripcion="+nombreDescripcion);

        if(nombreDescripcion.trim().length() >= 3) {
            listResult = new ArrayList<Producto>();
            List<Producto> listResultOriginal =productoJpaController.findProductoEntities();
            for(Producto productoOriginal: listResultOriginal) {
                String nombreDescripcionOriginal = productoOriginal.getNombre()+"/"+productoOriginal.getPresentacion();
                if(nombreDescripcionOriginal.toLowerCase().contains(nombreDescripcion.toLowerCase())) {
                    listResult.add(productoOriginal);
                }
            }
        }
        return listResult;
    }


    public List<SelectItem> getProductoConNombreDescripcion() {
        return productoConNombreDescripcion;
    }

    public List<SelectItem> getClienteList() {
        List<Cliente> clienteList = clienteJpaController.findClienteEntities();
        List<SelectItem> resultList = new ArrayList<SelectItem> ();
        resultList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));

        for(Cliente cliente: clienteList) {
            resultList.add(new SelectItem(cliente.getId(), cliente.getRazonSocial()));
        }
        return resultList;
    }

    public List<SelectItem> getFormaDePagoList() {
        List<FormaDePago> formaDePagoList = formaDePagoJpaController.findFormaDePagoEntities();
        List<SelectItem> resultList = new ArrayList<SelectItem> ();
        resultList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));

        for(FormaDePago formaDePago: formaDePagoList) {
            resultList.add(new SelectItem(formaDePago.getId(), formaDePago.getDescripcion()));
        }
        return resultList;
    }

    public List<PedidoVenta> getPedidoVentaList(){
        List<PedidoVenta> pedidoVentaList = pedidoVentaJpaController.findPedidoVentaEntities();
        return pedidoVentaList;
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

    public void setLineaJpaController(LineaJpaController lineaJpaController) {
        this.lineaJpaController = lineaJpaController;
    }

    public List<PedidoVentaDetalleWrapper> getPedidoVentaDetalleList() {
        return pedidoVentaDetalleList;
    }

    public PedidoVentaDetalleWrapper getPedidoVentaDetalleSeleccionado() {
        return detalleVentaPedidoSeleccionado;
    }

    public void setPedidoVentaDetalleSeleccionado(PedidoVentaDetalleWrapper detalleVentaPedidoSeleccionado) {
        this.detalleVentaPedidoSeleccionado = detalleVentaPedidoSeleccionado;
    }

    public PedidoVenta getPedidoVenta() {
        return pedidoVenta;
    }

    public void setPedidoVenta(PedidoVenta pedidoVenta) {
        this.pedidoVenta = pedidoVenta;
    }

    public String getNombreDescripcion() {
        return nombreDescripcion;
    }

    public void setNombreDescripcion(String nombreDescripcion) {
        this.nombreDescripcion = nombreDescripcion;
    }

    public Integer getProductoSelected() {
        return productoSelected;
    }

    public void setProductoSelected(Integer productoSelected) {
        this.productoSelected = productoSelected;
    }

    /**
     * @return the productoJpaController
     */
    public ProductoJpaController getProductoJpaController() {
        return productoJpaController;
    }

    /**
     * @return the productoSearchedAndSelected
     */
    public Producto getProductoSearchedAndSelected() {
        return productoSearchedAndSelected;
    }

    /**
     * @param productoSearchedAndSelected the productoSearchedAndSelected to set
     */
    public void setProductoSearchedAndSelected(Producto productoSearchedAndSelected) {
        logger.debug(">> setProductoSearchedAndSelected: ="+productoSearchedAndSelected);
        this.productoSearchedAndSelected = productoSearchedAndSelected;
    }

    public void setClienteJpaController(ClienteJpaController clienteJpaController) {
        this.clienteJpaController = clienteJpaController;
    }

    public void setFormaDePagoJpaController(FormaDePagoJpaController formaDePagoJpaController) {
        this.formaDePagoJpaController = formaDePagoJpaController;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getFormaDePagoId() {
        return formaDePagoId;
    }

    public void setFormaDePagoId(Integer formaDePagoId) {
        this.formaDePagoId = formaDePagoId;
    }

    public void setPedidoVentaJpaController(PedidoVentaJpaController pedidoVentaJpaController) {
        this.pedidoVentaJpaController = pedidoVentaJpaController;
    }

    public void setPedidoVentaEstadoJpaController(PedidoVentaEstadoJpaController pedidoVentaEstadoJpaController) {
        this.pedidoVentaEstadoJpaController = pedidoVentaEstadoJpaController;
    }

    public void setSessionUserMB(SessionUserMB sessionUserMB) {
        this.sessionUserMB = sessionUserMB;
    }
    //--------------------------------------------------------------------------
    
}
