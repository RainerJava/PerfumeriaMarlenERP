/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.client;

import com.pmarlen.model.beans.Cliente;
import com.pmarlen.model.beans.Empresa;
import com.pmarlen.model.beans.Estado;
import com.pmarlen.model.beans.FormaDePago;
import com.pmarlen.model.beans.Linea;
import com.pmarlen.model.beans.Marca;
import com.pmarlen.model.beans.Multimedio;
import com.pmarlen.model.beans.Producto;
import com.pmarlen.model.beans.Usuario;
import com.pmarlen.model.controller.PersistEntityWithTransactionDAO;
import com.pmarlen.wscommons.services.GetListDataBusiness;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author alfred
 */
@Repository("firstDataSynchronizer")
public class FirstDataSynchronizer {

    private Logger logger;

    private PersistEntityWithTransactionDAO persistEntityWithTransactionDAO;

    private SynchronizationWithServerRegistryController synchronizationWithServerRegistryController;

        
    public FirstDataSynchronizer() {
        logger = LoggerFactory.getLogger(FirstDataSynchronizer.class);
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

    public void checkServerVersion() throws Exception {

        String serverVersion = null;

        GetListDataBusiness getListDataBusinessServiceClient = WebServiceConnectionConfig.getInstance().getGetListDataBusiness();
        logger.debug("-> getListDataBusinessServiceClient=" + getListDataBusinessServiceClient);
        serverVersion = getListDataBusinessServiceClient.getServerVersion();

        logger.debug("checkServerVersion():serverVersion=" + serverVersion);

        String[] serverVersionPart = serverVersion.split("\\.");
        String[] mainVersionPart = ApplicationInfo.getInstance().getVersion().split("\\.");
        if (!serverVersionPart[0].equalsIgnoreCase(mainVersionPart[0])
                || !serverVersionPart[1].equalsIgnoreCase(mainVersionPart[1])) {
            throw new IllegalStateException("No se tiene una version compatible con servidor.\n"
                    + " Instale una version compatible con la Version:" + serverVersionPart[0] + "." + serverVersionPart[1] + "@xxx");
        }

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

            //persistEntityWithTransactionDAO.reloadEMF();

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

    boolean needSyncronization() {
        return synchronizationWithServerRegistryController.needSyncronization();
    }

    public boolean isSyncronizatedInThisSession() {
        return synchronizationWithServerRegistryController.isSyncronizatedInThisSession();
    }

}
