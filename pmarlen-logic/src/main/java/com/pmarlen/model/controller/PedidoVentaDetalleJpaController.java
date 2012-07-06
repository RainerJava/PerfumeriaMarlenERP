package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.PedidoVentaDetalle;
import com.pmarlen.model.beans.PedidoVentaDetallePK;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import com.pmarlen.model.controller.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.beans.Producto;

/**
 * PedidoVentaDetalleJpaController
 */

@Repository("pedidoVentaDetalleJpaController")

public class PedidoVentaDetalleJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PedidoVentaDetalle pedidoVentaDetalle) throws PreexistingEntityException, Exception {
        if (pedidoVentaDetalle.getPedidoVentaDetallePK() == null) {
            pedidoVentaDetalle.setPedidoVentaDetallePK(new PedidoVentaDetallePK());
        }
        pedidoVentaDetalle.getPedidoVentaDetallePK().setPedidoVentaId(pedidoVentaDetalle.getPedidoVenta().getId());
        pedidoVentaDetalle.getPedidoVentaDetallePK().setProductoId(pedidoVentaDetalle.getProducto().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PedidoVenta pedidoVenta = pedidoVentaDetalle.getPedidoVenta();
            if (pedidoVenta != null) {
                pedidoVenta = em.getReference(pedidoVenta.getClass(), pedidoVenta.getId());
                pedidoVentaDetalle.setPedidoVenta(pedidoVenta);
            }
            Producto producto = pedidoVentaDetalle.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getId());
                pedidoVentaDetalle.setProducto(producto);
            }
            em.persist(pedidoVentaDetalle);
            if (pedidoVenta != null) {
                pedidoVenta.getPedidoVentaDetalleCollection().add(pedidoVentaDetalle);
                pedidoVenta = em.merge(pedidoVenta);
            }
            if (producto != null) {
                producto.getPedidoVentaDetalleCollection().add(pedidoVentaDetalle);
                producto = em.merge(producto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPedidoVentaDetalle(pedidoVentaDetalle.getPedidoVentaDetallePK()) != null) {
                throw new PreexistingEntityException("PedidoVentaDetalle " + pedidoVentaDetalle + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PedidoVentaDetalle pedidoVentaDetalle) throws NonexistentEntityException, Exception {
        pedidoVentaDetalle.getPedidoVentaDetallePK().setPedidoVentaId(pedidoVentaDetalle.getPedidoVenta().getId());
        pedidoVentaDetalle.getPedidoVentaDetallePK().setProductoId(pedidoVentaDetalle.getProducto().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PedidoVentaDetalle persistentPedidoVentaDetalle = em.find(PedidoVentaDetalle.class, pedidoVentaDetalle.getPedidoVentaDetallePK());
            PedidoVenta pedidoVentaOld = persistentPedidoVentaDetalle.getPedidoVenta();
            PedidoVenta pedidoVentaNew = pedidoVentaDetalle.getPedidoVenta();
            Producto productoOld = persistentPedidoVentaDetalle.getProducto();
            Producto productoNew = pedidoVentaDetalle.getProducto();
            if (pedidoVentaNew != null) {
                pedidoVentaNew = em.getReference(pedidoVentaNew.getClass(), pedidoVentaNew.getId());
                pedidoVentaDetalle.setPedidoVenta(pedidoVentaNew);
            }
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getId());
                pedidoVentaDetalle.setProducto(productoNew);
            }
            pedidoVentaDetalle = em.merge(pedidoVentaDetalle);
            if (pedidoVentaOld != null && !pedidoVentaOld.equals(pedidoVentaNew)) {
                pedidoVentaOld.getPedidoVentaDetalleCollection().remove(pedidoVentaDetalle);
                pedidoVentaOld = em.merge(pedidoVentaOld);
            }
            if (pedidoVentaNew != null && !pedidoVentaNew.equals(pedidoVentaOld)) {
                pedidoVentaNew.getPedidoVentaDetalleCollection().add(pedidoVentaDetalle);
                pedidoVentaNew = em.merge(pedidoVentaNew);
            }
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getPedidoVentaDetalleCollection().remove(pedidoVentaDetalle);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getPedidoVentaDetalleCollection().add(pedidoVentaDetalle);
                productoNew = em.merge(productoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PedidoVentaDetallePK id = pedidoVentaDetalle.getPedidoVentaDetallePK();
                if (findPedidoVentaDetalle(id) == null) {
                    throw new NonexistentEntityException("The pedidoVentaDetalle with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PedidoVentaDetallePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PedidoVentaDetalle pedidoVentaDetalle;
            try {
                pedidoVentaDetalle = em.getReference(PedidoVentaDetalle.class, id);
                pedidoVentaDetalle.getPedidoVentaDetallePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedidoVentaDetalle with id " + id + " no longer exists.", enfe);
            }
            PedidoVenta pedidoVenta = pedidoVentaDetalle.getPedidoVenta();
            if (pedidoVenta != null) {
                pedidoVenta.getPedidoVentaDetalleCollection().remove(pedidoVentaDetalle);
                pedidoVenta = em.merge(pedidoVenta);
            }
            Producto producto = pedidoVentaDetalle.getProducto();
            if (producto != null) {
                producto.getPedidoVentaDetalleCollection().remove(pedidoVentaDetalle);
                producto = em.merge(producto);
            }
            em.remove(pedidoVentaDetalle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PedidoVentaDetalle> findPedidoVentaDetalleEntities() {
        return findPedidoVentaDetalleEntities(true, -1, -1);
    }

    public List<PedidoVentaDetalle> findPedidoVentaDetalleEntities(int maxResults, int firstResult) {
        return findPedidoVentaDetalleEntities(false, maxResults, firstResult);
    }

    private List<PedidoVentaDetalle> findPedidoVentaDetalleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from PedidoVentaDetalle as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public PedidoVentaDetalle findPedidoVentaDetalle(PedidoVentaDetallePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PedidoVentaDetalle.class, id);
        } finally {
            em.close();
        }
    }

    public int getPedidoVentaDetalleCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from PedidoVentaDetalle as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
