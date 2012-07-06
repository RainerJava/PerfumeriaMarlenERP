package com.pmarlen.businesslogic;

import com.pmarlen.model.Constants;
import com.pmarlen.model.beans.AlmacenProducto;
import com.pmarlen.model.beans.Contacto;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.Collection;
import java.util.Date;

import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.beans.FormaDePago;
import com.pmarlen.model.beans.Cliente;
import com.pmarlen.model.beans.Usuario;
import com.pmarlen.model.beans.PedidoVentaDetalle;
import com.pmarlen.model.beans.Estado;
import com.pmarlen.model.beans.MovimientoHistoricoProducto;
import com.pmarlen.model.beans.MovimientoHistoricoProductoPK;
import com.pmarlen.model.beans.PedidoVentaDetallePK;
import com.pmarlen.model.beans.PedidoVentaEstado;
import com.pmarlen.model.beans.PedidoVentaEstadoPK;
import com.pmarlen.model.beans.Producto;
import com.pmarlen.model.beans.TipoMovimiento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;


/**
 * PedidoVentaBusinessLogic
 */

@Repository("pedidoVentaBusinessLogic")
public class PedidoVentaBusinessLogic {
	
	private Logger logger;

    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

	public PedidoVentaBusinessLogic() {
		logger = LoggerFactory.getLogger(PedidoVentaBusinessLogic.class);        
        logger.debug("->PedidoVentaBusinessLogic, created");
	}
	
    public void crearPedidoCapturado(PedidoVenta pedidoVenta,Usuario usuarioModifico) {
        Collection<PedidoVentaDetalle> detalleVentaPedidoCollection = pedidoVenta.getPedidoVentaDetalleCollection();
        
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            FormaDePago formaDePago = pedidoVenta.getFormaDePago();
            if (formaDePago != null) {
                formaDePago = em.getReference(FormaDePago.class, formaDePago.getId());
                pedidoVenta.setFormaDePago(formaDePago);
            }
            Cliente cliente = pedidoVenta.getCliente();
            if (cliente != null) {
                cliente = em.getReference(Cliente.class, cliente.getId());
                
                pedidoVenta.setCliente(cliente);
            }
            Usuario usuario = pedidoVenta.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(Usuario.class, usuario.getUsuarioId());
                pedidoVenta.setUsuario(usuario);
            }
            
            for(PedidoVentaDetalle pvd: detalleVentaPedidoCollection){
                PedidoVentaDetallePK pedidoVentaDetallePK = new PedidoVentaDetallePK();
                pedidoVentaDetallePK.setPedidoVentaId(pedidoVenta.getId());
                pedidoVentaDetallePK.setProductoId(pvd.getProducto().getId());
                pvd.setPedidoVentaDetallePK(pedidoVentaDetallePK);
            }

            em.persist(pedidoVenta);

            if(detalleVentaPedidoCollection != null ){
                for(PedidoVentaDetalle detalleVentaPedido: detalleVentaPedidoCollection ) {
                    detalleVentaPedido.getPedidoVentaDetallePK().setPedidoVentaId(pedidoVenta.getId());
                    em.persist(detalleVentaPedido);                    
                }
            }
            
            pedidoVenta.setFacturaClienteCollection(null);
            
            PedidoVentaEstado pedidoVentaEstado = new PedidoVentaEstado();
            PedidoVentaEstadoPK pvePK = new PedidoVentaEstadoPK();
            //pvePK = new PedidoVentaEstadoPK(pedidoVenta.getId(), Constants.ESTADO_CAPTURADO);
            pvePK.setPedidoVentaId(pedidoVenta.getId());
            pvePK.setEstadoId(Constants.ESTADO_CAPTURADO);
            pedidoVentaEstado.setPedidoVentaEstadoPK(pvePK);
            Usuario usuarioModificoRefreshed = em.getReference(Usuario.class, usuarioModifico.getUsuarioId());
            pedidoVentaEstado.setEstado(new Estado(Constants.ESTADO_CAPTURADO));
            final Date dateCaptura = new Date();
            logger.debug("==>>crearPedidoCapturado():"+dateCaptura);
            pedidoVentaEstado.setFecha(dateCaptura);
            pedidoVentaEstado.setPedidoVenta(pedidoVenta);
            pedidoVentaEstado.setUsuario(usuarioModificoRefreshed);
            em.persist(pedidoVentaEstado);
            
            em.getTransaction().commit();            
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
/*
    private void edit(PedidoVenta pedidoVenta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        Collection<PedidoVentaDetalle> detalleVentaPedidoCollection = null;
        Collection<FacturaCliente>     facturaClienteCollection     = null;
        Collection<PedidoVentaEstado>  pedidoVentaEstadoCollection  = null;

        detalleVentaPedidoCollection    = pedidoVenta.getPedidoVentaDetalleCollection();
        facturaClienteCollection        = pedidoVenta.getFacturaClienteCollection();
        pedidoVentaEstadoCollection     = pedidoVenta.getPedidoVentaEstadoCollection();

        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            PedidoVenta pedidoVentaOld = em.find(PedidoVenta.class, pedidoVenta.getId());
            
            pedidoVentaOld.setCliente(pedidoVenta.getCliente());
            pedidoVentaOld.setComentarios(pedidoVenta.getComentarios());
            pedidoVentaOld.setEsFiscal(pedidoVenta.getEsFiscal());
            pedidoVentaOld.setFormaDePago(pedidoVenta.getFormaDePago());
            pedidoVentaOld.setUsuario(pedidoVenta.getUsuario());
            
            Collection<PedidoVentaDetalle> detalleVentaPedidoOldCollection = pedidoVentaOld.getPedidoVentaDetalleCollection();
            Collection<FacturaCliente>     facturaClienteOldCollection     = pedidoVentaOld.getFacturaClienteCollection();
            Collection<PedidoVentaEstado>  pedidoVentaEstadoOldCollection  = pedidoVentaOld.getPedidoVentaEstadoCollection();

            if(detalleVentaPedidoOldCollection != null && detalleVentaPedidoOldCollection.size()>0){
                pedidoVentaOld.setPedidoVentaDetalleCollection(null);
                em.merge(pedidoVentaOld);

                for(PedidoVentaDetalle detalleVentaPedidoOld: detalleVentaPedidoOldCollection ) {
                    logger.debug("\t=>em.remove("+detalleVentaPedidoOld+");");
                    em.remove(detalleVentaPedidoOld);
                }
            }
            if(facturaClienteOldCollection != null && facturaClienteOldCollection.size()>0) {
                pedidoVentaOld.setFacturaClienteCollection(null);
                em.merge(pedidoVentaOld);

                for(FacturaCliente facturaClienteOld: facturaClienteOldCollection) {
                    logger.debug("\t=>em.remove("+facturaClienteOld+");");
                    em.remove(facturaClienteOld);
                }
            }

            if(pedidoVentaEstadoOldCollection != null && pedidoVentaEstadoOldCollection.size()>0) {
                pedidoVentaOld.setPedidoVentaEstadoCollection(null);
                em.merge(pedidoVentaOld);

                for(PedidoVentaEstado pedidoVentaEstadoOld: pedidoVentaEstadoOldCollection) {
                    logger.debug("\t=>em.remove("+pedidoVentaEstadoOld+");");
                    em.remove(pedidoVentaEstadoOld);
                }
            }

            em.flush();

            if(detalleVentaPedidoCollection != null && detalleVentaPedidoCollection.size()>0){
                pedidoVentaOld.setPedidoVentaDetalleCollection(detalleVentaPedidoCollection);
                em.merge(pedidoVentaOld);

                for(PedidoVentaDetalle detalleVentaPedido: detalleVentaPedidoCollection ) {
                    detalleVentaPedido.getPedidoVentaDetallePK().setPedidoVentaId(pedidoVenta.getId());
                    logger.debug("\t=>em.persist("+detalleVentaPedido+");");
                    em.persist(detalleVentaPedido);
                }
            }
            
            if(facturaClienteCollection != null && facturaClienteCollection.size()>0) {
                pedidoVentaOld.setFacturaClienteCollection(facturaClienteCollection);
                em.merge(pedidoVentaOld);

                for(FacturaCliente facturaCliente: facturaClienteCollection) {
                    facturaCliente.setPedidoVenta(pedidoVenta);
                    logger.debug("\t=>em.persist("+facturaCliente+");");
                    em.persist(facturaCliente);
                }
            }

            if(pedidoVentaEstadoCollection != null && pedidoVentaEstadoCollection.size()>0) {
                pedidoVentaOld.setPedidoVentaEstadoCollection(pedidoVentaEstadoCollection);
                em.merge(pedidoVentaOld);

                for(PedidoVentaEstado pedidoVentaEstado: pedidoVentaEstadoCollection) {
                    pedidoVentaEstado.setPedidoVenta(pedidoVenta);
                    pedidoVentaEstado.getPedidoVentaEstadoPK().setPedidoVentaId(pedidoVenta.getId());
                    logger.debug("\t=>em.persist("+pedidoVentaEstado+");");
                    em.persist(pedidoVentaEstado);
                }
            }

            logger.debug("Ok, prepared to save");
            em.getTransaction().commit();
            logger.debug("===================>>> commited !!!");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
*/
    public void sincronizarPedido(PedidoVenta pedidoVenta,Usuario usuarioModifico) throws IllegalStateException {
        modificarPedidoNoSurtido(pedidoVenta,
                usuarioModifico,
                new Estado(Constants.ESTADO_CAPTURADO),
                new Estado(Constants.ESTADO_SINCRONIZADO));
    }

    public void verificarPedido(PedidoVenta pedidoVenta,Usuario usuarioModifico) throws IllegalStateException {
        modificarPedidoNoSurtido(pedidoVenta,
                usuarioModifico,
                new Estado(Constants.ESTADO_VERIFICADO),
                new Estado(Constants.ESTADO_VERIFICADO));
    }

    private void modificarPedidoNoSurtido(PedidoVenta pedidoVenta,Usuario usuarioModifico,Estado ultimoEstado, Estado sigEstado) throws IllegalStateException {
        Collection<PedidoVentaDetalle> PedidoVentaDetalleUpdatedCollection = null;
    
        PedidoVentaDetalleUpdatedCollection    = pedidoVenta.getPedidoVentaDetalleCollection();
    
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            PedidoVenta pedidoVentaOld = em.find(PedidoVenta.class, pedidoVenta.getId());
            Collection<PedidoVentaEstado> pedidoVentaEstadoCollectionOld = pedidoVentaOld.getPedidoVentaEstadoCollection();

            for(PedidoVentaEstado pedidoVentaEstadoOld: pedidoVentaEstadoCollectionOld) {
                if(pedidoVentaEstadoOld.getEstado().getId() > ultimoEstado.getId()) {
                    throw new IllegalStateException("El pedido esta en estado Illegal");
                }
            }

            pedidoVentaOld.setCliente(pedidoVenta.getCliente());
            pedidoVentaOld.setComentarios(pedidoVenta.getComentarios());
            pedidoVentaOld.setEsFiscal(pedidoVenta.getEsFiscal());
            pedidoVentaOld.setFormaDePago(pedidoVenta.getFormaDePago());
            

            Collection<PedidoVentaDetalle> pedidoVentaDetalleCollection = pedidoVentaOld.getPedidoVentaDetalleCollection();
            
            if(pedidoVentaDetalleCollection != null && pedidoVentaDetalleCollection.size()>0){
                pedidoVentaOld.setPedidoVentaDetalleCollection(null);
                em.merge(pedidoVentaOld);

                for(PedidoVentaDetalle detalleVentaPedidoOld: pedidoVentaDetalleCollection ) {
                    em.remove(detalleVentaPedidoOld);
                }
            }

            em.flush();

            pedidoVentaOld.setFacturaClienteCollection(null);
            em.merge(pedidoVentaOld);

            if(PedidoVentaDetalleUpdatedCollection != null && PedidoVentaDetalleUpdatedCollection.size()>0){
                
                for(PedidoVentaDetalle detalleVentaPedido: PedidoVentaDetalleUpdatedCollection ) {
                    PedidoVentaDetallePK pvdPK = new PedidoVentaDetallePK();
                    
                    pvdPK.setProductoId(detalleVentaPedido.getProducto().getId());
                    pvdPK.setPedidoVentaId(pedidoVenta.getId());
                    detalleVentaPedido.setPedidoVentaDetallePK(pvdPK);
                }
                pedidoVentaOld.setPedidoVentaDetalleCollection(PedidoVentaDetalleUpdatedCollection);

                em.merge(pedidoVentaOld);

//                for(PedidoVentaDetalle pedidoVentaDetalle: detalleVentaPedidoCollection ) {
//                    pedidoVentaDetalle.getPedidoVentaDetallePK().setPedidoVentaId(pedidoVenta.getId());
//                    em.persist(pedidoVentaDetalle);
//                }
            }

            PedidoVentaEstado pedidoVentaEstado = new PedidoVentaEstado();
            PedidoVentaEstadoPK pvePK = new PedidoVentaEstadoPK(pedidoVentaOld.getId(), sigEstado.getId());
            pedidoVentaEstado.setPedidoVentaEstadoPK(pvePK);
            Usuario usuarioModificoRefreshed = em.getReference(Usuario.class, usuarioModifico.getUsuarioId());
            pedidoVentaEstado.setEstado(sigEstado);
            pedidoVentaEstado.setFecha(new Date());
            pedidoVentaEstado.setPedidoVenta(pedidoVentaOld);
            pedidoVentaEstado.setUsuario(usuarioModificoRefreshed);
            em.persist(pedidoVentaEstado);

            em.getTransaction().commit();            
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void surtirPedido(PedidoVenta pedidoVenta,Usuario usuarioModifico) throws IllegalStateException {
        Estado ultimoEstado = new Estado(Constants.ESTADO_VERIFICADO);
        
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Date fechaTransaccion = new Date();;
            PedidoVenta pedidoVentaOld = em.find(PedidoVenta.class, pedidoVenta.getId());
            Collection<PedidoVentaEstado> pedidoVentaEstadoCollectionOld = pedidoVentaOld.getPedidoVentaEstadoCollection();

            for(PedidoVentaEstado pedidoVentaEstadoOld: pedidoVentaEstadoCollectionOld) {
                if(pedidoVentaEstadoOld.getEstado().getId() > ultimoEstado.getId()) {
                    throw new IllegalStateException("El pedido esta en estado Ilegal");
                }
            }

            Collection<PedidoVentaDetalle> detalleVentaPedidoOldCollection = pedidoVentaOld.getPedidoVentaDetalleCollection();
            for(PedidoVentaDetalle detalleVentaPedido: detalleVentaPedidoOldCollection ) {
                AlmacenProducto almacenFiscal   = null;
                AlmacenProducto almacenNoFiscal = null;
                Producto productoASurtir = em.find(Producto.class, detalleVentaPedido.getProducto().getId());
                Collection<AlmacenProducto> almacenProductoCollection = productoASurtir.getAlmacenProductoCollection();
                for(AlmacenProducto almacenProducto: almacenProductoCollection){
                    if(almacenProducto.getAlmacen().getEsFiscal() == Constants.PEDIDO_FISCAL  ){
                        almacenFiscal = almacenProducto;
                    } else if(almacenProducto.getAlmacen().getEsFiscal() == Constants.PEDIDO_NOFISCAL  ){
                        almacenNoFiscal = almacenProducto;
                    }
                }

                if(almacenFiscal == null && almacenNoFiscal == null) {
                    throw new IllegalStateException("No hay registro de almacen para el producto:"+productoASurtir.getId());
                }
                
                int cantEnAlmacenTotal = almacenFiscal.getCantidadActual() + almacenNoFiscal.getCantidadActual();
                logger.debug("-->> producto="+productoASurtir.getId()+", cantidadPedida="+detalleVentaPedido.getCantidad()+", almacenFiscal + almacenNoFiscal = "+almacenFiscal.getCantidadActual()+" + "+almacenNoFiscal.getCantidadActual()+" = "+cantEnAlmacenTotal);
                
                if(cantEnAlmacenTotal <= 0) {
                    continue;
                }
                if(detalleVentaPedido.getCantidad() > cantEnAlmacenTotal ) {
                    detalleVentaPedido.setCantidad(cantEnAlmacenTotal);
                }


                if( pedidoVentaOld.getEsFiscal() == Constants.PEDIDO_FISCAL){
                    logger.debug("\t-->> PEDIDO FISCAL");
                    if (detalleVentaPedido.getCantidad() <= almacenFiscal.getCantidadActual()){
                        logger.debug("\t-->> Cantidad, homogenea.");
                        almacenFiscal.setCantidadActual(almacenFiscal.getCantidadActual() - detalleVentaPedido.getCantidad());
                        //------------------------------------------------------
                        MovimientoHistoricoProducto mhpFis = new MovimientoHistoricoProducto();

                        mhpFis.setAlmacen(almacenFiscal.getAlmacen());
                        //mhpFis.setCantidad(almacenFiscal.getCantidadActual());
                        mhpFis.setCantidad(detalleVentaPedido.getCantidad());
                        mhpFis.setCosto(productoASurtir.getCosto());
                        mhpFis.setPrecio(productoASurtir.getPrecioBase());
                        mhpFis.setProducto(productoASurtir);
                        mhpFis.setTipoMovimiento(new TipoMovimiento(Constants.EXTRACCION_A_FIS));
                        mhpFis.setUsuario(usuarioModifico);

                        MovimientoHistoricoProductoPK mhpPKFis = new MovimientoHistoricoProductoPK();
                        mhpPKFis.setAlmacenId(almacenFiscal.getAlmacen().getId());
                        mhpPKFis.setFecha(fechaTransaccion);
                        mhpPKFis.setProductoId(productoASurtir.getId());
                        mhpPKFis.setTipoMovimientoId(Constants.EXTRACCION_A_FIS);

                        mhpFis.setMovimientoHistoricoProductoPK(mhpPKFis);

                        em.persist(mhpFis);
                    } else {
                        logger.debug("\t-->> Cantidad, heterogenea");
                        int cantDif       = detalleVentaPedido.getCantidad() - almacenFiscal.getCantidadActual();
                        int cantExtFis    = detalleVentaPedido.getCantidad() - cantDif ;
                        int cantExtNof    = cantDif ;
                        int cantExtFisBal = cantDif ;
                        almacenFiscal  .setCantidadActual(almacenFiscal.getCantidadActual()  - cantExtFis) ;
                        almacenNoFiscal.setCantidadActual(almacenNoFiscal.getCantidadActual()- cantExtNof);
                        //------------------------------------------------------
                        MovimientoHistoricoProducto mhpFis = new MovimientoHistoricoProducto();

                        mhpFis.setAlmacen(almacenFiscal.getAlmacen());
                        mhpFis.setCantidad(cantExtFis);
                        mhpFis.setCosto(productoASurtir.getCosto());
                        mhpFis.setPrecio(productoASurtir.getPrecioBase());
                        mhpFis.setProducto(productoASurtir);
                        mhpFis.setTipoMovimiento(new TipoMovimiento(Constants.EXTRACCION_A_FIS));
                        mhpFis.setUsuario(usuarioModifico);

                        MovimientoHistoricoProductoPK mhpPKFis = new MovimientoHistoricoProductoPK();
                        mhpPKFis.setAlmacenId(almacenFiscal.getAlmacen().getId());
                        mhpPKFis.setFecha(fechaTransaccion);
                        mhpPKFis.setProductoId(productoASurtir.getId());
                        mhpPKFis.setTipoMovimientoId(Constants.EXTRACCION_A_FIS);

                        mhpFis.setMovimientoHistoricoProductoPK(mhpPKFis);

                        em.persist(mhpFis);
                        //------------------------------------------------------
                        MovimientoHistoricoProducto mhpFisBal = new MovimientoHistoricoProducto();

                        mhpFisBal.setAlmacen(almacenFiscal.getAlmacen());
                        mhpFisBal.setCantidad(cantExtFisBal);
                        mhpFisBal.setCosto(productoASurtir.getCosto());
                        mhpFisBal.setPrecio(productoASurtir.getPrecioBase());
                        mhpFisBal.setProducto(productoASurtir);
                        mhpFisBal.setTipoMovimiento(new TipoMovimiento(Constants.EXTRACCION_A_FIS_BAL));
                        mhpFisBal.setUsuario(usuarioModifico);

                        MovimientoHistoricoProductoPK mhpPKFisBal = new MovimientoHistoricoProductoPK();
                        mhpPKFisBal.setAlmacenId(almacenFiscal.getAlmacen().getId());
                        mhpPKFisBal.setFecha(fechaTransaccion);
                        mhpPKFisBal.setProductoId(productoASurtir.getId());
                        mhpPKFisBal.setTipoMovimientoId(Constants.EXTRACCION_A_FIS_BAL);

                        mhpFisBal.setMovimientoHistoricoProductoPK(mhpPKFisBal);

                        em.persist(mhpFisBal);
                        //------------------------------------------------------
                        MovimientoHistoricoProducto mhpNof = new MovimientoHistoricoProducto();

                        mhpNof.setAlmacen(almacenNoFiscal.getAlmacen());
                        mhpNof.setCantidad(cantExtNof);
                        mhpNof.setCosto(productoASurtir.getCosto());
                        mhpNof.setPrecio(productoASurtir.getPrecioBase());
                        mhpNof.setProducto(productoASurtir);
                        mhpNof.setTipoMovimiento(new TipoMovimiento(Constants.EXTRACCION_A_NOF));
                        mhpNof.setUsuario(usuarioModifico);

                        MovimientoHistoricoProductoPK mhpPKNof = new MovimientoHistoricoProductoPK();
                        mhpPKNof.setAlmacenId(almacenNoFiscal.getAlmacen().getId());
                        mhpPKNof.setFecha(fechaTransaccion);
                        mhpPKNof.setProductoId(productoASurtir.getId());
                        mhpPKNof.setTipoMovimientoId(Constants.EXTRACCION_A_NOF);

                        mhpNof.setMovimientoHistoricoProductoPK(mhpPKNof);

                        em.persist(mhpNof);

                    }
                } else if( pedidoVentaOld.getEsFiscal() == Constants.PEDIDO_NOFISCAL){
                    logger.debug("\t-->> PEDIDO NO_FISCAL");

                    if (detalleVentaPedido.getCantidad() <= almacenNoFiscal.getCantidadActual()){
                        logger.debug("\t-->> Cantidad, homogenea.");
                        almacenNoFiscal.setCantidadActual(almacenNoFiscal.getCantidadActual() - detalleVentaPedido.getCantidad());
                        //------------------------------------------------------
                        MovimientoHistoricoProducto mhpNof = new MovimientoHistoricoProducto();

                        mhpNof.setAlmacen(almacenNoFiscal.getAlmacen());
                        //mhpNof.setCantidad(almacenNoFiscal.getCantidadActual());
                        mhpNof.setCantidad(detalleVentaPedido.getCantidad());
                        mhpNof.setCosto(productoASurtir.getCosto());
                        mhpNof.setPrecio(productoASurtir.getPrecioBase());
                        mhpNof.setProducto(productoASurtir);
                        mhpNof.setTipoMovimiento(new TipoMovimiento(Constants.EXTRACCION_A_NOF));
                        mhpNof.setUsuario(usuarioModifico);

                        MovimientoHistoricoProductoPK mhpPKNof = new MovimientoHistoricoProductoPK();
                        mhpPKNof.setAlmacenId(almacenNoFiscal.getAlmacen().getId());
                        mhpPKNof.setFecha(fechaTransaccion);
                        mhpPKNof.setProductoId(productoASurtir.getId());
                        mhpPKNof.setTipoMovimientoId(Constants.EXTRACCION_A_NOF);

                        mhpNof.setMovimientoHistoricoProductoPK(mhpPKNof);

                        em.persist(mhpNof);
                    } else {
                        logger.debug("\t-->> Cantidad, heterogenea");
                        int cantDif       = detalleVentaPedido.getCantidad() - almacenNoFiscal.getCantidadActual();
                        int cantExtNof    = detalleVentaPedido.getCantidad() - cantDif ;
                        int cantExtFis    = cantDif ;
                        int cantExtNofBal = cantDif ;
                        almacenNoFiscal  .setCantidadActual(almacenNoFiscal.getCantidadActual() - cantExtNof) ;
                        almacenFiscal    .setCantidadActual(almacenFiscal.getCantidadActual()   - cantExtFis);
                        //------------------------------------------------------
                        MovimientoHistoricoProducto mhpNof = new MovimientoHistoricoProducto();

                        mhpNof.setAlmacen(almacenNoFiscal.getAlmacen());
                        mhpNof.setCantidad(cantExtNof);
                        mhpNof.setCosto(productoASurtir.getCosto());
                        mhpNof.setPrecio(productoASurtir.getPrecioBase());
                        mhpNof.setProducto(productoASurtir);
                        mhpNof.setTipoMovimiento(new TipoMovimiento(Constants.EXTRACCION_A_NOF));
                        mhpNof.setUsuario(usuarioModifico);

                        MovimientoHistoricoProductoPK mhpPKNof = new MovimientoHistoricoProductoPK();
                        mhpPKNof.setAlmacenId(almacenNoFiscal.getAlmacen().getId());
                        mhpPKNof.setFecha(fechaTransaccion);
                        mhpPKNof.setProductoId(productoASurtir.getId());
                        mhpPKNof.setTipoMovimientoId(Constants.EXTRACCION_A_NOF);

                        mhpNof.setMovimientoHistoricoProductoPK(mhpPKNof);

                        em.persist(mhpNof);
                        //------------------------------------------------------
                        MovimientoHistoricoProducto mhpNofBal = new MovimientoHistoricoProducto();

                        mhpNofBal.setAlmacen(almacenNoFiscal.getAlmacen());
                        mhpNofBal.setCantidad(cantExtNofBal);
                        mhpNofBal.setCosto(productoASurtir.getCosto());
                        mhpNofBal.setPrecio(productoASurtir.getPrecioBase());
                        mhpNofBal.setProducto(productoASurtir);
                        mhpNofBal.setTipoMovimiento(new TipoMovimiento(Constants.EXTRACCION_A_NOF_BAL));
                        mhpNofBal.setUsuario(usuarioModifico);

                        MovimientoHistoricoProductoPK mhpPKNofBal = new MovimientoHistoricoProductoPK();
                        mhpPKNofBal.setAlmacenId(almacenNoFiscal.getAlmacen().getId());
                        mhpPKNofBal.setFecha(fechaTransaccion);
                        mhpPKNofBal.setProductoId(productoASurtir.getId());
                        mhpPKNofBal.setTipoMovimientoId(Constants.EXTRACCION_A_NOF_BAL);

                        mhpNofBal.setMovimientoHistoricoProductoPK(mhpPKNofBal);

                        em.persist(mhpNofBal);
                        //------------------------------------------------------
                        MovimientoHistoricoProducto mhpFis = new MovimientoHistoricoProducto();

                        mhpFis.setAlmacen(almacenFiscal.getAlmacen());
                        mhpFis.setCantidad(cantExtFis);
                        mhpFis.setCosto(productoASurtir.getCosto());
                        mhpFis.setPrecio(productoASurtir.getPrecioBase());
                        mhpFis.setProducto(productoASurtir);
                        mhpFis.setTipoMovimiento(new TipoMovimiento(Constants.EXTRACCION_A_FIS));
                        mhpFis.setUsuario(usuarioModifico);

                        MovimientoHistoricoProductoPK mhpPKFis = new MovimientoHistoricoProductoPK();
                        mhpPKFis.setAlmacenId(almacenFiscal.getAlmacen().getId());
                        mhpPKFis.setFecha(fechaTransaccion);
                        mhpPKFis.setProductoId(productoASurtir.getId());
                        mhpPKFis.setTipoMovimientoId(Constants.EXTRACCION_A_FIS);

                        mhpFis.setMovimientoHistoricoProductoPK(mhpPKFis);

                        em.persist(mhpFis);
                    }
                }
            }

            PedidoVentaEstado pedidoVentaEstado = new PedidoVentaEstado();
            PedidoVentaEstadoPK pvePK = new PedidoVentaEstadoPK(pedidoVentaOld.getId(), Constants.ESTADO_SURTIDO);
            pedidoVentaEstado.setPedidoVentaEstadoPK(pvePK);
            Usuario usuarioModificoRefreshed = em.getReference(Usuario.class, usuarioModifico.getUsuarioId());
            pedidoVentaEstado.setEstado(new Estado(Constants.ESTADO_SURTIDO));
            pedidoVentaEstado.setFecha(new Date());
            pedidoVentaEstado.setPedidoVenta(pedidoVentaOld);
            pedidoVentaEstado.setUsuario(usuarioModificoRefreshed);
            em.persist(pedidoVentaEstado);

            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
	

    public List<Producto> findProductoForReport() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT p FROM Producto p");

            List<Producto> resultList = q.getResultList();

            for (Producto x: resultList) {
                Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollection = x.getMovimientoHistoricoProductoCollection();
                for(MovimientoHistoricoProducto mhp: movimientoHistoricoProductoCollection) {
                }
                Collection<AlmacenProducto> almacenProductoCollection = x.getAlmacenProductoCollection();
                for(AlmacenProducto ap: almacenProductoCollection) {
                }
            }

            return resultList;
        } finally {
            em.close();
        }
    }

	public void guardarPedidosEnviados(Usuario usuarioAuthenticated, List<PedidoVenta> pedidoVentaList) throws Exception {
		
		EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
			logger.debug("\t-->> [1 ]guardarPedidosEnviados: "+usuarioAuthenticated+", pedidoVentaList.size()="+pedidoVentaList.size()+", em="+em);
			for(PedidoVenta pedidoVentaIter: pedidoVentaList) {
				
				PedidoVenta pedidoVenta = new PedidoVenta();
				
				//BeanUtils.copyProperties(pedidoVentaIter, pedidoVenta, new String[]{"id","pedidoVentaEstadoCollection","pedidoVentaDetalleCollection","facturaClienteCollection"});
                pedidoVenta.setComentarios(pedidoVentaIter.getComentarios());
                pedidoVenta.setEsFiscal(pedidoVentaIter.getEsFiscal());
				pedidoVenta.setFactoriva(pedidoVentaIter.getFactoriva());
                FormaDePago formaDePago = pedidoVenta.getFormaDePago();
                logger.debug("\t\t-->> [1 ]guardarPedidosEnviados: formaDePago="+pedidoVentaIter.getFormaDePago());                
				formaDePago = em.getReference(FormaDePago.class, pedidoVentaIter.getFormaDePago().getId());
				pedidoVenta.setFormaDePago(formaDePago);

                Collection<PedidoVentaDetalle> detalleVentaPedidoCollection = pedidoVentaIter.getPedidoVentaDetalleCollection();
				
				Cliente clientePedido = pedidoVentaIter.getCliente();
				Cliente clienteFound = null;
				boolean clienteFoundBean = false;
				try {
					logger.debug("\t\t-->> [1.0.1 ] EntityNotFoundException ==>> em.find(Cliente.class, "+clientePedido.getId()+" ) ?");					
					clienteFound = em.find(Cliente.class, clientePedido.getId());
					logger.debug("\t\t-->> [1.0.2 ] EntityNotFoundException ==>> OK clienteFound="+clienteFound);
					
					if(clienteFound != null) {
						clienteFound = em.getReference(Cliente.class, clientePedido.getId());
						clienteFoundBean = true;
						logger.debug("\t\t-->> [1.0.2.1 ] EntityNotFoundException ==>> get Reference="+clienteFound);
					}
				} catch(Exception enfe){
					logger.error("\t\t-->> [1.1 ] EntityNotFoundException ==>> "+enfe.getMessage()+" catched, but :)");
					clienteFound = null;
				}
				
				logger.error("\t\t-->> [1.1.1. ] EntityNotFoundException ==>> clienteFoundBean="+clienteFoundBean);
				
				if( ! clienteFoundBean ) {					
					Cliente clienteNuevo = new Cliente();
					
					BeanUtils.copyProperties(clientePedido,clienteNuevo,new String[]{"id","pedidoVentaCollection","contactoCollection"});
					
					Collection<Contacto> contactoCollection = clientePedido.getContactoCollection();
					Collection<Contacto> contactoToInsertCollection = new ArrayList<Contacto>();
					
					logger.debug("\t\t\t-->> [1.3.1 ]guardarPedidosEnviados: clienteNuevo="+clienteNuevo);
					if(contactoCollection != null) {
						for(Contacto contactoCliente :contactoCollection) {
							Contacto contactoRef = em.getReference(Contacto.class, contactoCliente.getId());

							if(contactoRef == null) {
								Contacto contactoNuevo = new Contacto();
								BeanUtils.copyProperties(contactoCliente, contactoNuevo, new String[]{"clienteCollection","proveedorCollection"});
								logger.debug("\t\t\t\t-->> [1.3.1.3 ]guardarPedidosEnviados: em.persist(contactoNuevo) ?");
								em.persist(contactoNuevo);
								logger.debug("\t\t\t\t-->> [1.3.1.4 ]guardarPedidosEnviados: em.persist(contactoNuevo.id="+contactoNuevo.getId()+"); -->> OK");
							} else {							
								contactoToInsertCollection.add(contactoRef);
							}					
						}
						clienteNuevo.setContactoCollection(contactoToInsertCollection);
					}
					
					logger.debug("\t\t\t-->> [1.3.5]guardarPedidosEnviados: em.persist(clienteNuevo) ?");
					em.persist(clienteNuevo);				
					logger.debug("\t\t\t-->> [1.3.7]guardarPedidosEnviados: OK, em.persist(clienteNuevo.id="+clienteNuevo.getId()+")");
					em.refresh(clienteNuevo);
					
					pedidoVenta.setCliente(clienteNuevo);
				} else {
					logger.debug("\t\t\t-->> [1.2.1 ]guardarPedidosEnviados: clienteFound="+clienteFound.getId());
					pedidoVenta.setCliente(clienteFound);
				}
				logger.debug("\t\t-->> [1.4]guardarPedidosEnviados: ");
				Usuario usuario = pedidoVentaIter.getUsuario();

				Usuario usuarioRef = em.getReference(Usuario.class, usuario.getUsuarioId());
				pedidoVenta.setUsuario(usuarioRef);				
                
				logger.debug("\t\t-->> [1.5]guardarPedidosEnviados: pedidoVentaEstadoCollection="+pedidoVenta.getPedidoVentaEstadoCollection());
				pedidoVenta.setPedidoVentaEstadoCollection(null);                
                logger.debug("\t\t-->> [1.6]guardarPedidosEnviados: PedidoVenta id="+pedidoVenta.getId());
				em.persist(pedidoVenta);
				logger.debug("\t\t-->> [1.7]guardarPedidosEnviados: PedidoVenta id="+pedidoVenta.getId());
				//==============================================================
				if(detalleVentaPedidoCollection != null ){
					for(PedidoVentaDetalle pvd: detalleVentaPedidoCollection ) {
						
						PedidoVentaDetalle pvdInsert = new PedidoVentaDetalle();
						
						BeanUtils.copyProperties(pvd, pvdInsert, new String[]{"pedidoVentaDetallePK"});
						
						PedidoVentaDetallePK pedidoVentaDetallePK = new PedidoVentaDetallePK();
						pedidoVentaDetallePK.setPedidoVentaId(pedidoVenta.getId());
						pedidoVentaDetallePK.setProductoId(pvd.getProducto().getId());
						
						pvdInsert.setPedidoVentaDetallePK(pedidoVentaDetallePK);						
						logger.debug("\t\t\t-->> [1.7.1]guardarPedidosEnviados: persist PedidoVentaDetalle: pvdInsert="+pvdInsert);
						em.persist(pvdInsert);                    
                        logger.debug("\t\t\t-->> [1.7.2]guardarPedidosEnviados: OK persist PedidoVentaDetalle");
					}
				}
				//==============================================================
				Collection<PedidoVentaEstado> pedidoVentaEstadoCollection = pedidoVentaIter.getPedidoVentaEstadoCollection();
                PedidoVentaEstado pedidoVentaEstadoNuevo = new PedidoVentaEstado();
				PedidoVentaEstadoPK pvePK = new PedidoVentaEstadoPK();				
				pvePK.setPedidoVentaId(pedidoVenta.getId());
				pvePK.setEstadoId(Constants.ESTADO_ENVIADO);
				pedidoVentaEstadoNuevo.setPedidoVentaEstadoPK(pvePK);
				Usuario usuarioModificoRefreshed = em.getReference(Usuario.class, usuarioAuthenticated.getUsuarioId());
				pedidoVentaEstadoNuevo.setEstado(new Estado(Constants.ESTADO_CAPTURADO));
				pedidoVentaEstadoNuevo.setFecha(new Date());
				pedidoVentaEstadoNuevo.setPedidoVenta(pedidoVenta);
				pedidoVentaEstadoNuevo.setUsuario(usuarioModificoRefreshed);
				
				if(pedidoVentaEstadoCollection != null) {
                    pedidoVentaEstadoCollection.add(pedidoVentaEstadoNuevo);
                } else {
                    pedidoVentaEstadoCollection = new ArrayList<PedidoVentaEstado>();
                    pedidoVentaEstadoCollection.add(pedidoVentaEstadoNuevo);
                }
                logger.debug("\t\t-->> [1.8]guardarPedidosEnviados: pedidoVentaEstadoCollection.size()="+pedidoVentaEstadoCollection.size());
                
                for(PedidoVentaEstado pedidoVentaEstadoIter: pedidoVentaEstadoCollection) {

                    PedidoVentaEstado pedidoVentaEstadoInsert = new PedidoVentaEstado();

                    BeanUtils.copyProperties(pedidoVentaEstadoIter, pedidoVentaEstadoInsert);
                    pedidoVentaEstadoInsert.getPedidoVentaEstadoPK().setPedidoVentaId(pedidoVenta.getId());
                    em.persist(pedidoVentaEstadoInsert);
                    logger.debug("\t\t\t-->> [1.8.1]guardarPedidosEnviados: OK persist PedidoVentaEstado: pedioId="+pedidoVentaEstadoInsert);
                }
				logger.debug("\t\t-->> [1.15]guardarPedidosEnviados: OK Pedido Guardado");
			}
			logger.debug("\t-->> guardarPedidosEnviados: commit ?");
            em.getTransaction().commit();            
			logger.debug("\t-->> guardarPedidosEnviados: OK");
        } catch (Exception e) {
			//logger.error("Exception caught:", e);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
				//logger.debug("\t-->> guardarPedidosEnviados: ROLLBACK :(");
            }
            throw e;
        } finally {
            if (em != null) {
                em.close();                
            }
        } 
	}
}
