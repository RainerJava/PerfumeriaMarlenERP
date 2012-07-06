package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.Producto;
import com.pmarlen.model.controller.exceptions.IllegalOrphanException;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.Marca;
import com.pmarlen.model.beans.Multimedio;
import java.util.ArrayList;
import java.util.Collection;
import com.pmarlen.model.beans.CodigoDeBarras;
import com.pmarlen.model.beans.MovimientoHistoricoProducto;
import com.pmarlen.model.beans.PedidoVentaDetalle;
import com.pmarlen.model.beans.AlmacenProducto;
import com.pmarlen.model.beans.ProveedorProducto;
import com.pmarlen.model.beans.PedidoCompraDetalle;

/**
 * ProductoJpaController
 */

@Repository("productoJpaController")

public class ProductoJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        if (producto.getMultimedioCollection() == null) {
            producto.setMultimedioCollection(new ArrayList<Multimedio>());
        }
        if (producto.getCodigoDeBarrasCollection() == null) {
            producto.setCodigoDeBarrasCollection(new ArrayList<CodigoDeBarras>());
        }
        if (producto.getMovimientoHistoricoProductoCollection() == null) {
            producto.setMovimientoHistoricoProductoCollection(new ArrayList<MovimientoHistoricoProducto>());
        }
        if (producto.getPedidoVentaDetalleCollection() == null) {
            producto.setPedidoVentaDetalleCollection(new ArrayList<PedidoVentaDetalle>());
        }
        if (producto.getAlmacenProductoCollection() == null) {
            producto.setAlmacenProductoCollection(new ArrayList<AlmacenProducto>());
        }
        if (producto.getProveedorProductoCollection() == null) {
            producto.setProveedorProductoCollection(new ArrayList<ProveedorProducto>());
        }
        if (producto.getPedidoCompraDetalleCollection() == null) {
            producto.setPedidoCompraDetalleCollection(new ArrayList<PedidoCompraDetalle>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Marca marca = producto.getMarca();
            if (marca != null) {
                marca = em.getReference(marca.getClass(), marca.getId());
                producto.setMarca(marca);
            }
            Collection<Multimedio> attachedMultimedioCollection = new ArrayList<Multimedio>();
            for (Multimedio multimedioCollectionMultimedioToAttach : producto.getMultimedioCollection()) {
                multimedioCollectionMultimedioToAttach = em.getReference(multimedioCollectionMultimedioToAttach.getClass(), multimedioCollectionMultimedioToAttach.getId());
                attachedMultimedioCollection.add(multimedioCollectionMultimedioToAttach);
            }
            producto.setMultimedioCollection(attachedMultimedioCollection);
            Collection<CodigoDeBarras> attachedCodigoDeBarrasCollection = new ArrayList<CodigoDeBarras>();
            for (CodigoDeBarras codigoDeBarrasCollectionCodigoDeBarrasToAttach : producto.getCodigoDeBarrasCollection()) {
                codigoDeBarrasCollectionCodigoDeBarrasToAttach = em.getReference(codigoDeBarrasCollectionCodigoDeBarrasToAttach.getClass(), codigoDeBarrasCollectionCodigoDeBarrasToAttach.getCodigoDeBarrasPK());
                attachedCodigoDeBarrasCollection.add(codigoDeBarrasCollectionCodigoDeBarrasToAttach);
            }
            producto.setCodigoDeBarrasCollection(attachedCodigoDeBarrasCollection);
            Collection<MovimientoHistoricoProducto> attachedMovimientoHistoricoProductoCollection = new ArrayList<MovimientoHistoricoProducto>();
            for (MovimientoHistoricoProducto movimientoHistoricoProductoCollectionMovimientoHistoricoProductoToAttach : producto.getMovimientoHistoricoProductoCollection()) {
                movimientoHistoricoProductoCollectionMovimientoHistoricoProductoToAttach = em.getReference(movimientoHistoricoProductoCollectionMovimientoHistoricoProductoToAttach.getClass(), movimientoHistoricoProductoCollectionMovimientoHistoricoProductoToAttach.getMovimientoHistoricoProductoPK());
                attachedMovimientoHistoricoProductoCollection.add(movimientoHistoricoProductoCollectionMovimientoHistoricoProductoToAttach);
            }
            producto.setMovimientoHistoricoProductoCollection(attachedMovimientoHistoricoProductoCollection);
            Collection<PedidoVentaDetalle> attachedPedidoVentaDetalleCollection = new ArrayList<PedidoVentaDetalle>();
            for (PedidoVentaDetalle pedidoVentaDetalleCollectionPedidoVentaDetalleToAttach : producto.getPedidoVentaDetalleCollection()) {
                pedidoVentaDetalleCollectionPedidoVentaDetalleToAttach = em.getReference(pedidoVentaDetalleCollectionPedidoVentaDetalleToAttach.getClass(), pedidoVentaDetalleCollectionPedidoVentaDetalleToAttach.getPedidoVentaDetallePK());
                attachedPedidoVentaDetalleCollection.add(pedidoVentaDetalleCollectionPedidoVentaDetalleToAttach);
            }
            producto.setPedidoVentaDetalleCollection(attachedPedidoVentaDetalleCollection);
            Collection<AlmacenProducto> attachedAlmacenProductoCollection = new ArrayList<AlmacenProducto>();
            for (AlmacenProducto almacenProductoCollectionAlmacenProductoToAttach : producto.getAlmacenProductoCollection()) {
                almacenProductoCollectionAlmacenProductoToAttach = em.getReference(almacenProductoCollectionAlmacenProductoToAttach.getClass(), almacenProductoCollectionAlmacenProductoToAttach.getAlmacenProductoPK());
                attachedAlmacenProductoCollection.add(almacenProductoCollectionAlmacenProductoToAttach);
            }
            producto.setAlmacenProductoCollection(attachedAlmacenProductoCollection);
            Collection<ProveedorProducto> attachedProveedorProductoCollection = new ArrayList<ProveedorProducto>();
            for (ProveedorProducto proveedorProductoCollectionProveedorProductoToAttach : producto.getProveedorProductoCollection()) {
                proveedorProductoCollectionProveedorProductoToAttach = em.getReference(proveedorProductoCollectionProveedorProductoToAttach.getClass(), proveedorProductoCollectionProveedorProductoToAttach.getProveedorProductoPK());
                attachedProveedorProductoCollection.add(proveedorProductoCollectionProveedorProductoToAttach);
            }
            producto.setProveedorProductoCollection(attachedProveedorProductoCollection);
            Collection<PedidoCompraDetalle> attachedPedidoCompraDetalleCollection = new ArrayList<PedidoCompraDetalle>();
            for (PedidoCompraDetalle pedidoCompraDetalleCollectionPedidoCompraDetalleToAttach : producto.getPedidoCompraDetalleCollection()) {
                pedidoCompraDetalleCollectionPedidoCompraDetalleToAttach = em.getReference(pedidoCompraDetalleCollectionPedidoCompraDetalleToAttach.getClass(), pedidoCompraDetalleCollectionPedidoCompraDetalleToAttach.getPedidoCompraDetallePK());
                attachedPedidoCompraDetalleCollection.add(pedidoCompraDetalleCollectionPedidoCompraDetalleToAttach);
            }
            producto.setPedidoCompraDetalleCollection(attachedPedidoCompraDetalleCollection);
            em.persist(producto);
            if (marca != null) {
                marca.getProductoCollection().add(producto);
                marca = em.merge(marca);
            }
            for (Multimedio multimedioCollectionMultimedio : producto.getMultimedioCollection()) {
                multimedioCollectionMultimedio.getProductoCollection().add(producto);
                multimedioCollectionMultimedio = em.merge(multimedioCollectionMultimedio);
            }
            for (CodigoDeBarras codigoDeBarrasCollectionCodigoDeBarras : producto.getCodigoDeBarrasCollection()) {
                Producto oldProductoOfCodigoDeBarrasCollectionCodigoDeBarras = codigoDeBarrasCollectionCodigoDeBarras.getProducto();
                codigoDeBarrasCollectionCodigoDeBarras.setProducto(producto);
                codigoDeBarrasCollectionCodigoDeBarras = em.merge(codigoDeBarrasCollectionCodigoDeBarras);
                if (oldProductoOfCodigoDeBarrasCollectionCodigoDeBarras != null) {
                    oldProductoOfCodigoDeBarrasCollectionCodigoDeBarras.getCodigoDeBarrasCollection().remove(codigoDeBarrasCollectionCodigoDeBarras);
                    oldProductoOfCodigoDeBarrasCollectionCodigoDeBarras = em.merge(oldProductoOfCodigoDeBarrasCollectionCodigoDeBarras);
                }
            }
            for (MovimientoHistoricoProducto movimientoHistoricoProductoCollectionMovimientoHistoricoProducto : producto.getMovimientoHistoricoProductoCollection()) {
                Producto oldProductoOfMovimientoHistoricoProductoCollectionMovimientoHistoricoProducto = movimientoHistoricoProductoCollectionMovimientoHistoricoProducto.getProducto();
                movimientoHistoricoProductoCollectionMovimientoHistoricoProducto.setProducto(producto);
                movimientoHistoricoProductoCollectionMovimientoHistoricoProducto = em.merge(movimientoHistoricoProductoCollectionMovimientoHistoricoProducto);
                if (oldProductoOfMovimientoHistoricoProductoCollectionMovimientoHistoricoProducto != null) {
                    oldProductoOfMovimientoHistoricoProductoCollectionMovimientoHistoricoProducto.getMovimientoHistoricoProductoCollection().remove(movimientoHistoricoProductoCollectionMovimientoHistoricoProducto);
                    oldProductoOfMovimientoHistoricoProductoCollectionMovimientoHistoricoProducto = em.merge(oldProductoOfMovimientoHistoricoProductoCollectionMovimientoHistoricoProducto);
                }
            }
            for (PedidoVentaDetalle pedidoVentaDetalleCollectionPedidoVentaDetalle : producto.getPedidoVentaDetalleCollection()) {
                Producto oldProductoOfPedidoVentaDetalleCollectionPedidoVentaDetalle = pedidoVentaDetalleCollectionPedidoVentaDetalle.getProducto();
                pedidoVentaDetalleCollectionPedidoVentaDetalle.setProducto(producto);
                pedidoVentaDetalleCollectionPedidoVentaDetalle = em.merge(pedidoVentaDetalleCollectionPedidoVentaDetalle);
                if (oldProductoOfPedidoVentaDetalleCollectionPedidoVentaDetalle != null) {
                    oldProductoOfPedidoVentaDetalleCollectionPedidoVentaDetalle.getPedidoVentaDetalleCollection().remove(pedidoVentaDetalleCollectionPedidoVentaDetalle);
                    oldProductoOfPedidoVentaDetalleCollectionPedidoVentaDetalle = em.merge(oldProductoOfPedidoVentaDetalleCollectionPedidoVentaDetalle);
                }
            }
            for (AlmacenProducto almacenProductoCollectionAlmacenProducto : producto.getAlmacenProductoCollection()) {
                Producto oldProductoOfAlmacenProductoCollectionAlmacenProducto = almacenProductoCollectionAlmacenProducto.getProducto();
                almacenProductoCollectionAlmacenProducto.setProducto(producto);
                almacenProductoCollectionAlmacenProducto = em.merge(almacenProductoCollectionAlmacenProducto);
                if (oldProductoOfAlmacenProductoCollectionAlmacenProducto != null) {
                    oldProductoOfAlmacenProductoCollectionAlmacenProducto.getAlmacenProductoCollection().remove(almacenProductoCollectionAlmacenProducto);
                    oldProductoOfAlmacenProductoCollectionAlmacenProducto = em.merge(oldProductoOfAlmacenProductoCollectionAlmacenProducto);
                }
            }
            for (ProveedorProducto proveedorProductoCollectionProveedorProducto : producto.getProveedorProductoCollection()) {
                Producto oldProductoOfProveedorProductoCollectionProveedorProducto = proveedorProductoCollectionProveedorProducto.getProducto();
                proveedorProductoCollectionProveedorProducto.setProducto(producto);
                proveedorProductoCollectionProveedorProducto = em.merge(proveedorProductoCollectionProveedorProducto);
                if (oldProductoOfProveedorProductoCollectionProveedorProducto != null) {
                    oldProductoOfProveedorProductoCollectionProveedorProducto.getProveedorProductoCollection().remove(proveedorProductoCollectionProveedorProducto);
                    oldProductoOfProveedorProductoCollectionProveedorProducto = em.merge(oldProductoOfProveedorProductoCollectionProveedorProducto);
                }
            }
            for (PedidoCompraDetalle pedidoCompraDetalleCollectionPedidoCompraDetalle : producto.getPedidoCompraDetalleCollection()) {
                Producto oldProductoOfPedidoCompraDetalleCollectionPedidoCompraDetalle = pedidoCompraDetalleCollectionPedidoCompraDetalle.getProducto();
                pedidoCompraDetalleCollectionPedidoCompraDetalle.setProducto(producto);
                pedidoCompraDetalleCollectionPedidoCompraDetalle = em.merge(pedidoCompraDetalleCollectionPedidoCompraDetalle);
                if (oldProductoOfPedidoCompraDetalleCollectionPedidoCompraDetalle != null) {
                    oldProductoOfPedidoCompraDetalleCollectionPedidoCompraDetalle.getPedidoCompraDetalleCollection().remove(pedidoCompraDetalleCollectionPedidoCompraDetalle);
                    oldProductoOfPedidoCompraDetalleCollectionPedidoCompraDetalle = em.merge(oldProductoOfPedidoCompraDetalleCollectionPedidoCompraDetalle);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getId());
            Marca marcaOld = persistentProducto.getMarca();
            Marca marcaNew = producto.getMarca();
            Collection<Multimedio> multimedioCollectionOld = persistentProducto.getMultimedioCollection();
            Collection<Multimedio> multimedioCollectionNew = producto.getMultimedioCollection();
            Collection<CodigoDeBarras> codigoDeBarrasCollectionOld = persistentProducto.getCodigoDeBarrasCollection();
            Collection<CodigoDeBarras> codigoDeBarrasCollectionNew = producto.getCodigoDeBarrasCollection();
            Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollectionOld = persistentProducto.getMovimientoHistoricoProductoCollection();
            Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollectionNew = producto.getMovimientoHistoricoProductoCollection();
            Collection<PedidoVentaDetalle> pedidoVentaDetalleCollectionOld = persistentProducto.getPedidoVentaDetalleCollection();
            Collection<PedidoVentaDetalle> pedidoVentaDetalleCollectionNew = producto.getPedidoVentaDetalleCollection();
            Collection<AlmacenProducto> almacenProductoCollectionOld = persistentProducto.getAlmacenProductoCollection();
            Collection<AlmacenProducto> almacenProductoCollectionNew = producto.getAlmacenProductoCollection();
            Collection<ProveedorProducto> proveedorProductoCollectionOld = persistentProducto.getProveedorProductoCollection();
            Collection<ProveedorProducto> proveedorProductoCollectionNew = producto.getProveedorProductoCollection();
            Collection<PedidoCompraDetalle> pedidoCompraDetalleCollectionOld = persistentProducto.getPedidoCompraDetalleCollection();
            Collection<PedidoCompraDetalle> pedidoCompraDetalleCollectionNew = producto.getPedidoCompraDetalleCollection();
            List<String> illegalOrphanMessages = null;
            for (CodigoDeBarras codigoDeBarrasCollectionOldCodigoDeBarras : codigoDeBarrasCollectionOld) {
                if (!codigoDeBarrasCollectionNew.contains(codigoDeBarrasCollectionOldCodigoDeBarras)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CodigoDeBarras " + codigoDeBarrasCollectionOldCodigoDeBarras + " since its producto field is not nullable.");
                }
            }
            for (MovimientoHistoricoProducto movimientoHistoricoProductoCollectionOldMovimientoHistoricoProducto : movimientoHistoricoProductoCollectionOld) {
                if (!movimientoHistoricoProductoCollectionNew.contains(movimientoHistoricoProductoCollectionOldMovimientoHistoricoProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MovimientoHistoricoProducto " + movimientoHistoricoProductoCollectionOldMovimientoHistoricoProducto + " since its producto field is not nullable.");
                }
            }
            for (PedidoVentaDetalle pedidoVentaDetalleCollectionOldPedidoVentaDetalle : pedidoVentaDetalleCollectionOld) {
                if (!pedidoVentaDetalleCollectionNew.contains(pedidoVentaDetalleCollectionOldPedidoVentaDetalle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PedidoVentaDetalle " + pedidoVentaDetalleCollectionOldPedidoVentaDetalle + " since its producto field is not nullable.");
                }
            }
            for (AlmacenProducto almacenProductoCollectionOldAlmacenProducto : almacenProductoCollectionOld) {
                if (!almacenProductoCollectionNew.contains(almacenProductoCollectionOldAlmacenProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AlmacenProducto " + almacenProductoCollectionOldAlmacenProducto + " since its producto field is not nullable.");
                }
            }
            for (ProveedorProducto proveedorProductoCollectionOldProveedorProducto : proveedorProductoCollectionOld) {
                if (!proveedorProductoCollectionNew.contains(proveedorProductoCollectionOldProveedorProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProveedorProducto " + proveedorProductoCollectionOldProveedorProducto + " since its producto field is not nullable.");
                }
            }
            for (PedidoCompraDetalle pedidoCompraDetalleCollectionOldPedidoCompraDetalle : pedidoCompraDetalleCollectionOld) {
                if (!pedidoCompraDetalleCollectionNew.contains(pedidoCompraDetalleCollectionOldPedidoCompraDetalle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PedidoCompraDetalle " + pedidoCompraDetalleCollectionOldPedidoCompraDetalle + " since its producto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (marcaNew != null) {
                marcaNew = em.getReference(marcaNew.getClass(), marcaNew.getId());
                producto.setMarca(marcaNew);
            }
            Collection<Multimedio> attachedMultimedioCollectionNew = new ArrayList<Multimedio>();
            for (Multimedio multimedioCollectionNewMultimedioToAttach : multimedioCollectionNew) {
                multimedioCollectionNewMultimedioToAttach = em.getReference(multimedioCollectionNewMultimedioToAttach.getClass(), multimedioCollectionNewMultimedioToAttach.getId());
                attachedMultimedioCollectionNew.add(multimedioCollectionNewMultimedioToAttach);
            }
            multimedioCollectionNew = attachedMultimedioCollectionNew;
            producto.setMultimedioCollection(multimedioCollectionNew);
            Collection<CodigoDeBarras> attachedCodigoDeBarrasCollectionNew = new ArrayList<CodigoDeBarras>();
            for (CodigoDeBarras codigoDeBarrasCollectionNewCodigoDeBarrasToAttach : codigoDeBarrasCollectionNew) {
                codigoDeBarrasCollectionNewCodigoDeBarrasToAttach = em.getReference(codigoDeBarrasCollectionNewCodigoDeBarrasToAttach.getClass(), codigoDeBarrasCollectionNewCodigoDeBarrasToAttach.getCodigoDeBarrasPK());
                attachedCodigoDeBarrasCollectionNew.add(codigoDeBarrasCollectionNewCodigoDeBarrasToAttach);
            }
            codigoDeBarrasCollectionNew = attachedCodigoDeBarrasCollectionNew;
            producto.setCodigoDeBarrasCollection(codigoDeBarrasCollectionNew);
            Collection<MovimientoHistoricoProducto> attachedMovimientoHistoricoProductoCollectionNew = new ArrayList<MovimientoHistoricoProducto>();
            for (MovimientoHistoricoProducto movimientoHistoricoProductoCollectionNewMovimientoHistoricoProductoToAttach : movimientoHistoricoProductoCollectionNew) {
                movimientoHistoricoProductoCollectionNewMovimientoHistoricoProductoToAttach = em.getReference(movimientoHistoricoProductoCollectionNewMovimientoHistoricoProductoToAttach.getClass(), movimientoHistoricoProductoCollectionNewMovimientoHistoricoProductoToAttach.getMovimientoHistoricoProductoPK());
                attachedMovimientoHistoricoProductoCollectionNew.add(movimientoHistoricoProductoCollectionNewMovimientoHistoricoProductoToAttach);
            }
            movimientoHistoricoProductoCollectionNew = attachedMovimientoHistoricoProductoCollectionNew;
            producto.setMovimientoHistoricoProductoCollection(movimientoHistoricoProductoCollectionNew);
            Collection<PedidoVentaDetalle> attachedPedidoVentaDetalleCollectionNew = new ArrayList<PedidoVentaDetalle>();
            for (PedidoVentaDetalle pedidoVentaDetalleCollectionNewPedidoVentaDetalleToAttach : pedidoVentaDetalleCollectionNew) {
                pedidoVentaDetalleCollectionNewPedidoVentaDetalleToAttach = em.getReference(pedidoVentaDetalleCollectionNewPedidoVentaDetalleToAttach.getClass(), pedidoVentaDetalleCollectionNewPedidoVentaDetalleToAttach.getPedidoVentaDetallePK());
                attachedPedidoVentaDetalleCollectionNew.add(pedidoVentaDetalleCollectionNewPedidoVentaDetalleToAttach);
            }
            pedidoVentaDetalleCollectionNew = attachedPedidoVentaDetalleCollectionNew;
            producto.setPedidoVentaDetalleCollection(pedidoVentaDetalleCollectionNew);
            Collection<AlmacenProducto> attachedAlmacenProductoCollectionNew = new ArrayList<AlmacenProducto>();
            for (AlmacenProducto almacenProductoCollectionNewAlmacenProductoToAttach : almacenProductoCollectionNew) {
                almacenProductoCollectionNewAlmacenProductoToAttach = em.getReference(almacenProductoCollectionNewAlmacenProductoToAttach.getClass(), almacenProductoCollectionNewAlmacenProductoToAttach.getAlmacenProductoPK());
                attachedAlmacenProductoCollectionNew.add(almacenProductoCollectionNewAlmacenProductoToAttach);
            }
            almacenProductoCollectionNew = attachedAlmacenProductoCollectionNew;
            producto.setAlmacenProductoCollection(almacenProductoCollectionNew);
            Collection<ProveedorProducto> attachedProveedorProductoCollectionNew = new ArrayList<ProveedorProducto>();
            for (ProveedorProducto proveedorProductoCollectionNewProveedorProductoToAttach : proveedorProductoCollectionNew) {
                proveedorProductoCollectionNewProveedorProductoToAttach = em.getReference(proveedorProductoCollectionNewProveedorProductoToAttach.getClass(), proveedorProductoCollectionNewProveedorProductoToAttach.getProveedorProductoPK());
                attachedProveedorProductoCollectionNew.add(proveedorProductoCollectionNewProveedorProductoToAttach);
            }
            proveedorProductoCollectionNew = attachedProveedorProductoCollectionNew;
            producto.setProveedorProductoCollection(proveedorProductoCollectionNew);
            Collection<PedidoCompraDetalle> attachedPedidoCompraDetalleCollectionNew = new ArrayList<PedidoCompraDetalle>();
            for (PedidoCompraDetalle pedidoCompraDetalleCollectionNewPedidoCompraDetalleToAttach : pedidoCompraDetalleCollectionNew) {
                pedidoCompraDetalleCollectionNewPedidoCompraDetalleToAttach = em.getReference(pedidoCompraDetalleCollectionNewPedidoCompraDetalleToAttach.getClass(), pedidoCompraDetalleCollectionNewPedidoCompraDetalleToAttach.getPedidoCompraDetallePK());
                attachedPedidoCompraDetalleCollectionNew.add(pedidoCompraDetalleCollectionNewPedidoCompraDetalleToAttach);
            }
            pedidoCompraDetalleCollectionNew = attachedPedidoCompraDetalleCollectionNew;
            producto.setPedidoCompraDetalleCollection(pedidoCompraDetalleCollectionNew);
            producto = em.merge(producto);
            if (marcaOld != null && !marcaOld.equals(marcaNew)) {
                marcaOld.getProductoCollection().remove(producto);
                marcaOld = em.merge(marcaOld);
            }
            if (marcaNew != null && !marcaNew.equals(marcaOld)) {
                marcaNew.getProductoCollection().add(producto);
                marcaNew = em.merge(marcaNew);
            }
            for (Multimedio multimedioCollectionOldMultimedio : multimedioCollectionOld) {
                if (!multimedioCollectionNew.contains(multimedioCollectionOldMultimedio)) {
                    multimedioCollectionOldMultimedio.getProductoCollection().remove(producto);
                    multimedioCollectionOldMultimedio = em.merge(multimedioCollectionOldMultimedio);
                }
            }
            for (Multimedio multimedioCollectionNewMultimedio : multimedioCollectionNew) {
                if (!multimedioCollectionOld.contains(multimedioCollectionNewMultimedio)) {
                    multimedioCollectionNewMultimedio.getProductoCollection().add(producto);
                    multimedioCollectionNewMultimedio = em.merge(multimedioCollectionNewMultimedio);
                }
            }
            for (CodigoDeBarras codigoDeBarrasCollectionNewCodigoDeBarras : codigoDeBarrasCollectionNew) {
                if (!codigoDeBarrasCollectionOld.contains(codigoDeBarrasCollectionNewCodigoDeBarras)) {
                    Producto oldProductoOfCodigoDeBarrasCollectionNewCodigoDeBarras = codigoDeBarrasCollectionNewCodigoDeBarras.getProducto();
                    codigoDeBarrasCollectionNewCodigoDeBarras.setProducto(producto);
                    codigoDeBarrasCollectionNewCodigoDeBarras = em.merge(codigoDeBarrasCollectionNewCodigoDeBarras);
                    if (oldProductoOfCodigoDeBarrasCollectionNewCodigoDeBarras != null && !oldProductoOfCodigoDeBarrasCollectionNewCodigoDeBarras.equals(producto)) {
                        oldProductoOfCodigoDeBarrasCollectionNewCodigoDeBarras.getCodigoDeBarrasCollection().remove(codigoDeBarrasCollectionNewCodigoDeBarras);
                        oldProductoOfCodigoDeBarrasCollectionNewCodigoDeBarras = em.merge(oldProductoOfCodigoDeBarrasCollectionNewCodigoDeBarras);
                    }
                }
            }
            for (MovimientoHistoricoProducto movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto : movimientoHistoricoProductoCollectionNew) {
                if (!movimientoHistoricoProductoCollectionOld.contains(movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto)) {
                    Producto oldProductoOfMovimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto = movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto.getProducto();
                    movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto.setProducto(producto);
                    movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto = em.merge(movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto);
                    if (oldProductoOfMovimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto != null && !oldProductoOfMovimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto.equals(producto)) {
                        oldProductoOfMovimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto.getMovimientoHistoricoProductoCollection().remove(movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto);
                        oldProductoOfMovimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto = em.merge(oldProductoOfMovimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto);
                    }
                }
            }
            for (PedidoVentaDetalle pedidoVentaDetalleCollectionNewPedidoVentaDetalle : pedidoVentaDetalleCollectionNew) {
                if (!pedidoVentaDetalleCollectionOld.contains(pedidoVentaDetalleCollectionNewPedidoVentaDetalle)) {
                    Producto oldProductoOfPedidoVentaDetalleCollectionNewPedidoVentaDetalle = pedidoVentaDetalleCollectionNewPedidoVentaDetalle.getProducto();
                    pedidoVentaDetalleCollectionNewPedidoVentaDetalle.setProducto(producto);
                    pedidoVentaDetalleCollectionNewPedidoVentaDetalle = em.merge(pedidoVentaDetalleCollectionNewPedidoVentaDetalle);
                    if (oldProductoOfPedidoVentaDetalleCollectionNewPedidoVentaDetalle != null && !oldProductoOfPedidoVentaDetalleCollectionNewPedidoVentaDetalle.equals(producto)) {
                        oldProductoOfPedidoVentaDetalleCollectionNewPedidoVentaDetalle.getPedidoVentaDetalleCollection().remove(pedidoVentaDetalleCollectionNewPedidoVentaDetalle);
                        oldProductoOfPedidoVentaDetalleCollectionNewPedidoVentaDetalle = em.merge(oldProductoOfPedidoVentaDetalleCollectionNewPedidoVentaDetalle);
                    }
                }
            }
            for (AlmacenProducto almacenProductoCollectionNewAlmacenProducto : almacenProductoCollectionNew) {
                if (!almacenProductoCollectionOld.contains(almacenProductoCollectionNewAlmacenProducto)) {
                    Producto oldProductoOfAlmacenProductoCollectionNewAlmacenProducto = almacenProductoCollectionNewAlmacenProducto.getProducto();
                    almacenProductoCollectionNewAlmacenProducto.setProducto(producto);
                    almacenProductoCollectionNewAlmacenProducto = em.merge(almacenProductoCollectionNewAlmacenProducto);
                    if (oldProductoOfAlmacenProductoCollectionNewAlmacenProducto != null && !oldProductoOfAlmacenProductoCollectionNewAlmacenProducto.equals(producto)) {
                        oldProductoOfAlmacenProductoCollectionNewAlmacenProducto.getAlmacenProductoCollection().remove(almacenProductoCollectionNewAlmacenProducto);
                        oldProductoOfAlmacenProductoCollectionNewAlmacenProducto = em.merge(oldProductoOfAlmacenProductoCollectionNewAlmacenProducto);
                    }
                }
            }
            for (ProveedorProducto proveedorProductoCollectionNewProveedorProducto : proveedorProductoCollectionNew) {
                if (!proveedorProductoCollectionOld.contains(proveedorProductoCollectionNewProveedorProducto)) {
                    Producto oldProductoOfProveedorProductoCollectionNewProveedorProducto = proveedorProductoCollectionNewProveedorProducto.getProducto();
                    proveedorProductoCollectionNewProveedorProducto.setProducto(producto);
                    proveedorProductoCollectionNewProveedorProducto = em.merge(proveedorProductoCollectionNewProveedorProducto);
                    if (oldProductoOfProveedorProductoCollectionNewProveedorProducto != null && !oldProductoOfProveedorProductoCollectionNewProveedorProducto.equals(producto)) {
                        oldProductoOfProveedorProductoCollectionNewProveedorProducto.getProveedorProductoCollection().remove(proveedorProductoCollectionNewProveedorProducto);
                        oldProductoOfProveedorProductoCollectionNewProveedorProducto = em.merge(oldProductoOfProveedorProductoCollectionNewProveedorProducto);
                    }
                }
            }
            for (PedidoCompraDetalle pedidoCompraDetalleCollectionNewPedidoCompraDetalle : pedidoCompraDetalleCollectionNew) {
                if (!pedidoCompraDetalleCollectionOld.contains(pedidoCompraDetalleCollectionNewPedidoCompraDetalle)) {
                    Producto oldProductoOfPedidoCompraDetalleCollectionNewPedidoCompraDetalle = pedidoCompraDetalleCollectionNewPedidoCompraDetalle.getProducto();
                    pedidoCompraDetalleCollectionNewPedidoCompraDetalle.setProducto(producto);
                    pedidoCompraDetalleCollectionNewPedidoCompraDetalle = em.merge(pedidoCompraDetalleCollectionNewPedidoCompraDetalle);
                    if (oldProductoOfPedidoCompraDetalleCollectionNewPedidoCompraDetalle != null && !oldProductoOfPedidoCompraDetalleCollectionNewPedidoCompraDetalle.equals(producto)) {
                        oldProductoOfPedidoCompraDetalleCollectionNewPedidoCompraDetalle.getPedidoCompraDetalleCollection().remove(pedidoCompraDetalleCollectionNewPedidoCompraDetalle);
                        oldProductoOfPedidoCompraDetalleCollectionNewPedidoCompraDetalle = em.merge(oldProductoOfPedidoCompraDetalleCollectionNewPedidoCompraDetalle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = producto.getId();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CodigoDeBarras> codigoDeBarrasCollectionOrphanCheck = producto.getCodigoDeBarrasCollection();
            for (CodigoDeBarras codigoDeBarrasCollectionOrphanCheckCodigoDeBarras : codigoDeBarrasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the CodigoDeBarras " + codigoDeBarrasCollectionOrphanCheckCodigoDeBarras + " in its codigoDeBarrasCollection field has a non-nullable producto field.");
            }
            Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollectionOrphanCheck = producto.getMovimientoHistoricoProductoCollection();
            for (MovimientoHistoricoProducto movimientoHistoricoProductoCollectionOrphanCheckMovimientoHistoricoProducto : movimientoHistoricoProductoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the MovimientoHistoricoProducto " + movimientoHistoricoProductoCollectionOrphanCheckMovimientoHistoricoProducto + " in its movimientoHistoricoProductoCollection field has a non-nullable producto field.");
            }
            Collection<PedidoVentaDetalle> pedidoVentaDetalleCollectionOrphanCheck = producto.getPedidoVentaDetalleCollection();
            for (PedidoVentaDetalle pedidoVentaDetalleCollectionOrphanCheckPedidoVentaDetalle : pedidoVentaDetalleCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the PedidoVentaDetalle " + pedidoVentaDetalleCollectionOrphanCheckPedidoVentaDetalle + " in its pedidoVentaDetalleCollection field has a non-nullable producto field.");
            }
            Collection<AlmacenProducto> almacenProductoCollectionOrphanCheck = producto.getAlmacenProductoCollection();
            for (AlmacenProducto almacenProductoCollectionOrphanCheckAlmacenProducto : almacenProductoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the AlmacenProducto " + almacenProductoCollectionOrphanCheckAlmacenProducto + " in its almacenProductoCollection field has a non-nullable producto field.");
            }
            Collection<ProveedorProducto> proveedorProductoCollectionOrphanCheck = producto.getProveedorProductoCollection();
            for (ProveedorProducto proveedorProductoCollectionOrphanCheckProveedorProducto : proveedorProductoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the ProveedorProducto " + proveedorProductoCollectionOrphanCheckProveedorProducto + " in its proveedorProductoCollection field has a non-nullable producto field.");
            }
            Collection<PedidoCompraDetalle> pedidoCompraDetalleCollectionOrphanCheck = producto.getPedidoCompraDetalleCollection();
            for (PedidoCompraDetalle pedidoCompraDetalleCollectionOrphanCheckPedidoCompraDetalle : pedidoCompraDetalleCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the PedidoCompraDetalle " + pedidoCompraDetalleCollectionOrphanCheckPedidoCompraDetalle + " in its pedidoCompraDetalleCollection field has a non-nullable producto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Marca marca = producto.getMarca();
            if (marca != null) {
                marca.getProductoCollection().remove(producto);
                marca = em.merge(marca);
            }
            Collection<Multimedio> multimedioCollection = producto.getMultimedioCollection();
            for (Multimedio multimedioCollectionMultimedio : multimedioCollection) {
                multimedioCollectionMultimedio.getProductoCollection().remove(producto);
                multimedioCollectionMultimedio = em.merge(multimedioCollectionMultimedio);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Producto as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Producto> resultList = q.getResultList();

            for (Producto x: resultList) {
//                Collection<Multimedio> multimedioCollection = x.getMultimedioCollection();
//                for (Multimedio multimedio: multimedioCollection){
//                }
//                Collection<CodigoDeBarras> codigoDeBarrasCollection = x.getCodigoDeBarrasCollection();
//                for(CodigoDeBarras codigoDeBarras: codigoDeBarrasCollection) {
//                }
            }

            return resultList;
        } finally {
            em.close();
        }
    }

    public List<Producto> findAllProductoConMovimientosEntities() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Producto as o");
            List<Producto> resultList = q.getResultList();

            for (Producto x: resultList) {

                Collection<Multimedio> multimedioCollection = x.getMultimedioCollection();
                for (Multimedio multimedio: multimedioCollection){
                }

                Collection<CodigoDeBarras> codigoDeBarrasCollection = x.getCodigoDeBarrasCollection();
                for(CodigoDeBarras codigoDeBarras: codigoDeBarrasCollection) {
                }

                Collection<AlmacenProducto> almacenProductoCollection = x.getAlmacenProductoCollection();
                for(AlmacenProducto almacenProducto: almacenProductoCollection){
                    almacenProducto.getCantidadActual();
                }

                Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollection = x.getMovimientoHistoricoProductoCollection();
                for(MovimientoHistoricoProducto movimientoHistoricoProducto: movimientoHistoricoProductoCollection){
                }
            }

            return resultList;
        } finally {
            em.close();
        }
    }


    public List<Producto> findProductoEntitiesByMarca(Marca marca) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Producto as o where o.marca.id = :marcaId");
            q.setParameter("marcaId",marca.getId());

            List<Producto> resultList = q.getResultList();

            for (Producto x: resultList) {
                Collection<Multimedio> multimedioCollection = x.getMultimedioCollection();
                for (Multimedio multimedio: multimedioCollection){
                }
                Collection<CodigoDeBarras> codigoDeBarrasCollection = x.getCodigoDeBarrasCollection();
                for(CodigoDeBarras codigoDeBarras: codigoDeBarrasCollection) {
                }
            }

            return resultList;
        } finally {
            em.close();
        }
    }


    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            Producto x = em.find(Producto.class, id);
            if( x != null) {
                Collection<Multimedio> multimedioCollection = x.getMultimedioCollection();
                for (Multimedio multimedio: multimedioCollection){
                }

                Collection<CodigoDeBarras> codigoDeBarrasCollection = x.getCodigoDeBarrasCollection();
                for(CodigoDeBarras codigoDeBarras: codigoDeBarrasCollection) {
                }

                Collection<AlmacenProducto> almacenProductoCollection = x.getAlmacenProductoCollection();
                for(AlmacenProducto almacenProducto:almacenProductoCollection){
                }

                Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollection = x.getMovimientoHistoricoProductoCollection();
                for(MovimientoHistoricoProducto movimientoHistoricoProducto: movimientoHistoricoProductoCollection){
                }
            }
            return x;
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Producto as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
