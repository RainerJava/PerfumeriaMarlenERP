package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.PedidoCompraDetalle;
import com.pmarlen.model.beans.PedidoCompraDetallePK;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import com.pmarlen.model.controller.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.PedidoCompra;
import com.pmarlen.model.beans.Producto;

/**
 * PedidoCompraDetalleJpaController
 */

@Repository("pedidoCompraDetalleJpaController")

public class PedidoCompraDetalleJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PedidoCompraDetalle pedidoCompraDetalle) throws PreexistingEntityException, Exception {
        if (pedidoCompraDetalle.getPedidoCompraDetallePK() == null) {
            pedidoCompraDetalle.setPedidoCompraDetallePK(new PedidoCompraDetallePK());
        }
        pedidoCompraDetalle.getPedidoCompraDetallePK().setProductoId(pedidoCompraDetalle.getProducto().getId());
        pedidoCompraDetalle.getPedidoCompraDetallePK().setPedidoCompraId(pedidoCompraDetalle.getPedidoCompra().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PedidoCompra pedidoCompra = pedidoCompraDetalle.getPedidoCompra();
            if (pedidoCompra != null) {
                pedidoCompra = em.getReference(pedidoCompra.getClass(), pedidoCompra.getId());
                pedidoCompraDetalle.setPedidoCompra(pedidoCompra);
            }
            Producto producto = pedidoCompraDetalle.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getId());
                pedidoCompraDetalle.setProducto(producto);
            }
            em.persist(pedidoCompraDetalle);
            if (pedidoCompra != null) {
                pedidoCompra.getPedidoCompraDetalleCollection().add(pedidoCompraDetalle);
                pedidoCompra = em.merge(pedidoCompra);
            }
            if (producto != null) {
                producto.getPedidoCompraDetalleCollection().add(pedidoCompraDetalle);
                producto = em.merge(producto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPedidoCompraDetalle(pedidoCompraDetalle.getPedidoCompraDetallePK()) != null) {
                throw new PreexistingEntityException("PedidoCompraDetalle " + pedidoCompraDetalle + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PedidoCompraDetalle pedidoCompraDetalle) throws NonexistentEntityException, Exception {
        pedidoCompraDetalle.getPedidoCompraDetallePK().setProductoId(pedidoCompraDetalle.getProducto().getId());
        pedidoCompraDetalle.getPedidoCompraDetallePK().setPedidoCompraId(pedidoCompraDetalle.getPedidoCompra().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PedidoCompraDetalle persistentPedidoCompraDetalle = em.find(PedidoCompraDetalle.class, pedidoCompraDetalle.getPedidoCompraDetallePK());
            PedidoCompra pedidoCompraOld = persistentPedidoCompraDetalle.getPedidoCompra();
            PedidoCompra pedidoCompraNew = pedidoCompraDetalle.getPedidoCompra();
            Producto productoOld = persistentPedidoCompraDetalle.getProducto();
            Producto productoNew = pedidoCompraDetalle.getProducto();
            if (pedidoCompraNew != null) {
                pedidoCompraNew = em.getReference(pedidoCompraNew.getClass(), pedidoCompraNew.getId());
                pedidoCompraDetalle.setPedidoCompra(pedidoCompraNew);
            }
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getId());
                pedidoCompraDetalle.setProducto(productoNew);
            }
            pedidoCompraDetalle = em.merge(pedidoCompraDetalle);
            if (pedidoCompraOld != null && !pedidoCompraOld.equals(pedidoCompraNew)) {
                pedidoCompraOld.getPedidoCompraDetalleCollection().remove(pedidoCompraDetalle);
                pedidoCompraOld = em.merge(pedidoCompraOld);
            }
            if (pedidoCompraNew != null && !pedidoCompraNew.equals(pedidoCompraOld)) {
                pedidoCompraNew.getPedidoCompraDetalleCollection().add(pedidoCompraDetalle);
                pedidoCompraNew = em.merge(pedidoCompraNew);
            }
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getPedidoCompraDetalleCollection().remove(pedidoCompraDetalle);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getPedidoCompraDetalleCollection().add(pedidoCompraDetalle);
                productoNew = em.merge(productoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PedidoCompraDetallePK id = pedidoCompraDetalle.getPedidoCompraDetallePK();
                if (findPedidoCompraDetalle(id) == null) {
                    throw new NonexistentEntityException("The pedidoCompraDetalle with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PedidoCompraDetallePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PedidoCompraDetalle pedidoCompraDetalle;
            try {
                pedidoCompraDetalle = em.getReference(PedidoCompraDetalle.class, id);
                pedidoCompraDetalle.getPedidoCompraDetallePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedidoCompraDetalle with id " + id + " no longer exists.", enfe);
            }
            PedidoCompra pedidoCompra = pedidoCompraDetalle.getPedidoCompra();
            if (pedidoCompra != null) {
                pedidoCompra.getPedidoCompraDetalleCollection().remove(pedidoCompraDetalle);
                pedidoCompra = em.merge(pedidoCompra);
            }
            Producto producto = pedidoCompraDetalle.getProducto();
            if (producto != null) {
                producto.getPedidoCompraDetalleCollection().remove(pedidoCompraDetalle);
                producto = em.merge(producto);
            }
            em.remove(pedidoCompraDetalle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PedidoCompraDetalle> findPedidoCompraDetalleEntities() {
        return findPedidoCompraDetalleEntities(true, -1, -1);
    }

    public List<PedidoCompraDetalle> findPedidoCompraDetalleEntities(int maxResults, int firstResult) {
        return findPedidoCompraDetalleEntities(false, maxResults, firstResult);
    }

    private List<PedidoCompraDetalle> findPedidoCompraDetalleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from PedidoCompraDetalle as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public PedidoCompraDetalle findPedidoCompraDetalle(PedidoCompraDetallePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PedidoCompraDetalle.class, id);
        } finally {
            em.close();
        }
    }

    public int getPedidoCompraDetalleCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from PedidoCompraDetalle as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
