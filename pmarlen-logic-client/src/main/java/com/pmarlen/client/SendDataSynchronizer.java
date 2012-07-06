/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.client;

import com.pmarlen.businesslogic.exception.AuthenticationException;
import com.pmarlen.businesslogic.exception.PedidoVentaException;
import com.pmarlen.model.beans.Cliente;
import com.pmarlen.model.beans.Empresa;
import com.pmarlen.model.beans.Estado;
import com.pmarlen.model.beans.FormaDePago;
import com.pmarlen.model.beans.Linea;
import com.pmarlen.model.beans.Marca;
import com.pmarlen.model.beans.Multimedio;
import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.beans.PedidoVentaDetalle;
import com.pmarlen.model.beans.PedidoVentaEstado;
import com.pmarlen.model.beans.Producto;
import com.pmarlen.model.beans.Usuario;
import com.pmarlen.model.controller.GetListDataBusinesJpaController;
import com.pmarlen.model.controller.PedidoVentaJpaController;
import com.pmarlen.model.controller.PersistEntityWithTransactionDAO;
import com.pmarlen.wscommons.services.GetListDataBusiness;
import com.pmarlen.wscommons.services.SincronizadorDePedidos;

import com.pmarlen.wscommons.services.dto.PedidoVentaEstadoPK;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author alfred
 */
@Repository("sendDataSynchronizer")
public class SendDataSynchronizer {
    private Logger logger;

    private PersistEntityWithTransactionDAO persistEntityWithTransactionDAO;   
    
    private ApplicationLogic applicationLogic;
    
    GetListDataBusinesJpaController getListDataBusinesJpaController;
    
    private SynchronizationWithServerRegistryController synchronizationWithServerRegistryController;

    public SendDataSynchronizer() {
        logger = LoggerFactory.getLogger(SendDataSynchronizer.class);
    }

    /**
     * @param persistEntityWithTransactionDAO the persistEntityWithTransactionDAO to set
     */
    @Autowired
    public void setPersistEntityWithTransactionDAO(PersistEntityWithTransactionDAO persistEntityWithTransactionDAO) {
        this.persistEntityWithTransactionDAO = persistEntityWithTransactionDAO;
    }


    /**
     * @param synchronizationWithServerRegistryController the synchronizationWithServerRegistryController to set
     */
    @Autowired
    public void setSynchronizationWithServerRegistryController(SynchronizationWithServerRegistryController synchronizationWithServerRegistryController) {
        this.synchronizationWithServerRegistryController = synchronizationWithServerRegistryController;
    }
        
    public void firstSyncronization(ProgressProcessListener progressListener) throws Exception {
        logger.debug("====================>> firstSyncronization():");

        progressListener.updateProgress(30, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_CREATING_WS"));
        GetListDataBusiness getListDataBusinessServiceClient = WebServiceConnectionConfig.getInstance().getGetListDataBusiness();

        try {
            logger.debug("firstSyncronization():delete all objs");
            progressListener.updateProgress(32, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_DELETE_ALL_OBJECTESS"));
            persistEntityWithTransactionDAO.deleteAllObjects();
            // =====================================================================
            progressListener.updateProgress(35, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_WS_GET_USUARIOLIST"));
            List<Usuario> usuarioReceivedList = getListDataBusinessServiceClient.getUsuarioList();
            progressListener.updateProgress(38, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_WS_UPDATE_USUARIOLIST"));
            logger.debug("firstSyncronization():WebService:getUsuarioList():Result = " + usuarioReceivedList);
            persistEntityWithTransactionDAO.inicializarUsuarios(usuarioReceivedList, progressListener);
            usuarioReceivedList = null;
            // =====================================================================
            progressListener.updateProgress(40, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_WS_GET_LINEALIST"));
            List<Linea> lineaReceivedList = getListDataBusinessServiceClient.getLineaList();
            logger.debug("firstSyncronization():WebService:getLineaList():Result = " + lineaReceivedList);
            progressListener.updateProgress(43, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_WS_UPDATE_LINEALIST"));
            persistEntityWithTransactionDAO.inicializarLinea(lineaReceivedList, progressListener);
            lineaReceivedList = null;
            // =====================================================================
            progressListener.updateProgress(45, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_WS_UPDATE_EMPRESALIST"));
            List<Empresa> empresaRecivedList = getListDataBusinessServiceClient.getEmpresaList();
            logger.debug("firstSyncronization():WebService:getEmpresaList():Result = " + empresaRecivedList);
            progressListener.updateProgress(48, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_WS_UPDATE_EMPRESALIST"));
            persistEntityWithTransactionDAO.inicializarEmpresas(empresaRecivedList, progressListener);
            empresaRecivedList = null;

            // =====================================================================
            progressListener.updateProgress(50, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_WS_UPDATE_MARCALIST"));
            List<Marca> marcaReceivedList = getListDataBusinessServiceClient.getMarcaList();
            logger.debug("firstSyncronization():WebService:getMarcaList():Result = " + marcaReceivedList);
            progressListener.updateProgress(53, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_WS_UPDATE_MARCALIST"));
            persistEntityWithTransactionDAO.inicializarMarcas(marcaReceivedList, progressListener);
            marcaReceivedList = null;
            // =====================================================================            
            progressListener.updateProgress(55, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_WS_UPDATE_PRODUCTOLIST"));
            List<Producto> productoReceivedList = getListDataBusinessServiceClient.getProductoList();
            logger.debug("firstSyncronization():WebService:getProductoList():Result size = " + productoReceivedList.size());
            progressListener.updateProgress(58, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_WS_UPDATE_PRODUCTOLIST"));
            persistEntityWithTransactionDAO.inicializarProductos(productoReceivedList, progressListener);
            productoReceivedList = null;
            // =====================================================================

            progressListener.updateProgress(60, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_WS_UPDATE_FORMADEPAGOLIST"));
            List<FormaDePago> formaDePagoReceivedList = getListDataBusinessServiceClient.getFormaDePagoList();
            logger.debug("firstSyncronization():WebService:getFormaDePagoList():Result size = " + formaDePagoReceivedList.size());
            progressListener.updateProgress(63, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_WS_UPDATE_FORMADEPAGOLIST"));
            persistEntityWithTransactionDAO.inicializarFormaDePago(formaDePagoReceivedList, progressListener);
            formaDePagoReceivedList = null;
            // =====================================================================
            progressListener.updateProgress(65, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_WS_UPDATE_ESTADOLIST"));
            List<Estado> estadoReceivedList = getListDataBusinessServiceClient.getEstadoList();
            logger.debug("firstSyncronization():WebService:getEstadoList():Result size = " + estadoReceivedList.size());
            progressListener.updateProgress(68, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_WS_UPDATE_ESTADOLIST"));
            persistEntityWithTransactionDAO.inicializarEstado(estadoReceivedList, progressListener);
            estadoReceivedList = null;
            // =====================================================================

            progressListener.updateProgress(70, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_WS_UPDATE_CLIENTELIST"));
            List<Cliente> resultClienteList = getListDataBusinessServiceClient.getClienteList();
            logger.debug("firstSyncronization():WebService:getClienteList():Result size = " + resultClienteList.size());
            progressListener.updateProgress(73, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_WS_UPDATE_CLIENTELIST"));
            persistEntityWithTransactionDAO.inicializarCliente(resultClienteList, progressListener);
            resultClienteList = null;
            // =====================================================================
            progressListener.updateProgress(75, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_WS_UPDATE_MULTIMEDIOLIST"));

            List<Multimedio> resultMultimedioList = getListDataBusinessServiceClient.getProductoMultimedioList();
            progressListener.updateProgress(78, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_WS_UPDATE_MULTIMEDIOLIST"));
            persistEntityWithTransactionDAO.inicializarMultimedio(resultMultimedioList, progressListener);
            resultMultimedioList = null;
            // =====================================================================
            progressListener.updateProgress(80, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_UPDATE_LASTSYNC"));
            // =====================================================================

            synchronizationWithServerRegistryController.updateLastSyncronization();

            logger.debug("firstSyncronization():end OK");
            progressListener.updateProgress(92, ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_OK"));

        } catch (Exception ex) {
            throw ex;
        } finally {
            getListDataBusinessServiceClient = null;
            //logger.debug("firstSyncronization():gc");
            //System.gc();
        }
    }

    public void sendAndDeletePedidos() throws AuthenticationException, PedidoVentaException {
        List<PedidoVenta> pedidoVentaList = getListDataBusinesJpaController.findPedidoVentaEntities();
        
        for(PedidoVenta pv: pedidoVentaList) {
            logger.error("=>>enviarPedido: PedidoVenta: id="+pv.getId()+", cliente="+pv.getCliente()+", PedidoVentaDetalleCollection.size="+pv.getPedidoVentaDetalleCollection().size());            
        }
        
        SincronizadorDePedidos sincronizadorDePedidos = WebServiceConnectionConfig.getInstance().getSincronizadorDePedidos();
        
        List<com.pmarlen.wscommons.services.dto.PedidoVenta> pedidoVentaDTOList = new ArrayList<com.pmarlen.wscommons.services.dto.PedidoVenta> ();

        for(PedidoVenta pvJPA: pedidoVentaList) {
            com.pmarlen.wscommons.services.dto.PedidoVenta pv = new com.pmarlen.wscommons.services.dto.PedidoVenta();


            com.pmarlen.wscommons.services.dto.Cliente cliente = new com.pmarlen.wscommons.services.dto.Cliente(pvJPA.getCliente().getId());

            cliente.setCalle(pvJPA.getCliente().getCalle());
            cliente.setClasificacionFiscal(pvJPA.getCliente().getClasificacionFiscal());
            cliente.setDescripcionRuta(pvJPA.getCliente().getDescripcionRuta());
            cliente.setEmail(pvJPA.getCliente().getEmail());
            cliente.setFaxes(pvJPA.getCliente().getFaxes());
            cliente.setTelefonos(pvJPA.getCliente().getTelefonos());
            cliente.setTelefonosMoviles(pvJPA.getCliente().getTelefonosMoviles());
            cliente.setFechaCreacion(pvJPA.getCliente().getFechaCreacion());
            cliente.setNombreEstablecimiento(pvJPA.getCliente().getNombreEstablecimiento());
            cliente.setNumExterior(pvJPA.getCliente().getNumExterior());
            cliente.setNumInterior(pvJPA.getCliente().getNumInterior());
            cliente.setObservaciones(pvJPA.getCliente().getObservaciones());
            cliente.setPlazoDePago(pvJPA.getCliente().getPlazoDePago());
            cliente.setPoblacion(new com.pmarlen.wscommons.services.dto.Poblacion(pvJPA.getCliente().getPoblacion().getId()));
            cliente.setRazonSocial(pvJPA.getCliente().getRazonSocial());
            cliente.setRfc(pvJPA.getCliente().getRfc());

            pv.setCliente(cliente);
            //----------------------------------------------------------
            com.pmarlen.wscommons.services.dto.FormaDePago formaDePago = new com.pmarlen.wscommons.services.dto.FormaDePago(pvJPA.getFormaDePago().getId());

            pv.setFormaDePago(formaDePago);
            //----------------------------------------------------------
            pv.setComentarios(pvJPA.getComentarios());
            pv.setEsFiscal(pvJPA.getEsFiscal());
            pv.setFactoriva(pvJPA.getFactoriva());
            pv.setUsuario(new com.pmarlen.wscommons.services.dto.Usuario(pvJPA.getUsuario().getUsuarioId()));
            //----------------------------------------------------------
            List<com.pmarlen.wscommons.services.dto.PedidoVentaDetalle> pvdList =  new ArrayList<com.pmarlen.wscommons.services.dto.PedidoVentaDetalle>();
            Collection<PedidoVentaDetalle> pedidoVentaJPADetalleList = pvJPA.getPedidoVentaDetalleCollection();

            for(PedidoVentaDetalle pvdJPA: pedidoVentaJPADetalleList) {
                com.pmarlen.wscommons.services.dto.PedidoVentaDetalle pvd = new com.pmarlen.wscommons.services.dto.PedidoVentaDetalle();
                pvd.setPedidoVentaDetallePK(new com.pmarlen.wscommons.services.dto.PedidoVentaDetallePK(pvdJPA.getPedidoVentaDetallePK().getPedidoVentaId(), pvdJPA.getPedidoVentaDetallePK().getProductoId()));
                pvd.setCantidad(pvdJPA.getCantidad());
                pvd.setDescuentoAplicado(pvdJPA.getDescuentoAplicado());
                pvd.setPrecioBase(pvdJPA.getPrecioBase());
                pvd.setProducto(new com.pmarlen.wscommons.services.dto.Producto(pvdJPA.getProducto().getId()));                        

                pvdList.add(pvd);
            }

            pv.setPedidoVentaDetalleCollection(pvdList);

            //----------------------------------------------------------
            ArrayList<com.pmarlen.wscommons.services.dto.PedidoVentaEstado> pveList = new ArrayList<com.pmarlen.wscommons.services.dto.PedidoVentaEstado>();
            Collection<PedidoVentaEstado> pedidoVentaEstadoList = pvJPA.getPedidoVentaEstadoCollection();

            for(PedidoVentaEstado pveJPA: pedidoVentaEstadoList) {
                com.pmarlen.wscommons.services.dto.PedidoVentaEstado pve = new com.pmarlen.wscommons.services.dto.PedidoVentaEstado(
                        new PedidoVentaEstadoPK(pveJPA.getPedidoVentaEstadoPK().getPedidoVentaId(),
                                                pveJPA.getPedidoVentaEstadoPK().getEstadoId()));

                pve.setEstado(new com.pmarlen.wscommons.services.dto.Estado(pveJPA.getEstado().getId()));
                pve.setComentarios(pveJPA.getComentarios());
                pve.setFecha(pveJPA.getFecha());
                pve.setUsuario(new com.pmarlen.wscommons.services.dto.Usuario(pveJPA.getUsuario().getUsuarioId()));                                                

                pveList.add(pve);
            }

            pv.setPedidoVentaEstadoCollection(pveList);
            //----------------------------------------------------------


            pedidoVentaDTOList.add(pv);
        }

        sincronizadorDePedidos.enviarPedido(
                this.applicationLogic.getSession().getUsuario().getUsuarioId(), 
                this.applicationLogic.getSession().getUsuario().getPassword(), 
                pedidoVentaDTOList);
        try {
            persistEntityWithTransactionDAO.deletePedidos();
        } catch (Exception ex) {            
            throw new PedidoVentaException(PedidoVentaException.ERROR_PERSISTENCIA, ex.getMessage());
        }
        
    }

    /**
     * @param applicationLogic the applicationLogic to set
     */
    @Autowired
    public void setApplicationLogic(ApplicationLogic applicationLogic) {
        this.applicationLogic = applicationLogic;
    }
    
    /**
     * @param applicationLogic the applicationLogic to set
     */
    @Autowired
    public void setGetListDataBusinesJpaController(GetListDataBusinesJpaController getListDataBusinesJpaController) {
        this.getListDataBusinesJpaController = getListDataBusinesJpaController;
    }
    
    
}
