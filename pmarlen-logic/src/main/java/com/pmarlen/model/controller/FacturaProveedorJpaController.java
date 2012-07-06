package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.FacturaProveedor;
import com.pmarlen.model.controller.exceptions.IllegalOrphanException;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import com.pmarlen.model.controller.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.PedidoCompra;
import com.pmarlen.model.beans.EstadoDeCuentaProveedores;
import java.util.ArrayList;
import java.util.Collection;

/**
 * FacturaProveedorJpaController
 */

@Repository("facturaProveedorJpaController")

public class FacturaProveedorJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FacturaProveedor facturaProveedor) throws PreexistingEntityException, Exception {
        if (facturaProveedor.getEstadoDeCuentaProveedoresCollection() == null) {
            facturaProveedor.setEstadoDeCuentaProveedoresCollection(new ArrayList<EstadoDeCuentaProveedores>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PedidoCompra pedidoCompra = facturaProveedor.getPedidoCompra();
            if (pedidoCompra != null) {
                pedidoCompra = em.getReference(pedidoCompra.getClass(), pedidoCompra.getId());
                facturaProveedor.setPedidoCompra(pedidoCompra);
            }
            Collection<EstadoDeCuentaProveedores> attachedEstadoDeCuentaProveedoresCollection = new ArrayList<EstadoDeCuentaProveedores>();
            for (EstadoDeCuentaProveedores estadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedoresToAttach : facturaProveedor.getEstadoDeCuentaProveedoresCollection()) {
                estadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedoresToAttach = em.getReference(estadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedoresToAttach.getClass(), estadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedoresToAttach.getId());
                attachedEstadoDeCuentaProveedoresCollection.add(estadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedoresToAttach);
            }
            facturaProveedor.setEstadoDeCuentaProveedoresCollection(attachedEstadoDeCuentaProveedoresCollection);
            em.persist(facturaProveedor);
            if (pedidoCompra != null) {
                pedidoCompra.getFacturaProveedorCollection().add(facturaProveedor);
                pedidoCompra = em.merge(pedidoCompra);
            }
            for (EstadoDeCuentaProveedores estadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedores : facturaProveedor.getEstadoDeCuentaProveedoresCollection()) {
                FacturaProveedor oldFacturaProveedorOfEstadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedores = estadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedores.getFacturaProveedor();
                estadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedores.setFacturaProveedor(facturaProveedor);
                estadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedores = em.merge(estadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedores);
                if (oldFacturaProveedorOfEstadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedores != null) {
                    oldFacturaProveedorOfEstadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedores.getEstadoDeCuentaProveedoresCollection().remove(estadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedores);
                    oldFacturaProveedorOfEstadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedores = em.merge(oldFacturaProveedorOfEstadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedores);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFacturaProveedor(facturaProveedor.getId()) != null) {
                throw new PreexistingEntityException("FacturaProveedor " + facturaProveedor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FacturaProveedor facturaProveedor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FacturaProveedor persistentFacturaProveedor = em.find(FacturaProveedor.class, facturaProveedor.getId());
            PedidoCompra pedidoCompraOld = persistentFacturaProveedor.getPedidoCompra();
            PedidoCompra pedidoCompraNew = facturaProveedor.getPedidoCompra();
            Collection<EstadoDeCuentaProveedores> estadoDeCuentaProveedoresCollectionOld = persistentFacturaProveedor.getEstadoDeCuentaProveedoresCollection();
            Collection<EstadoDeCuentaProveedores> estadoDeCuentaProveedoresCollectionNew = facturaProveedor.getEstadoDeCuentaProveedoresCollection();
            List<String> illegalOrphanMessages = null;
            for (EstadoDeCuentaProveedores estadoDeCuentaProveedoresCollectionOldEstadoDeCuentaProveedores : estadoDeCuentaProveedoresCollectionOld) {
                if (!estadoDeCuentaProveedoresCollectionNew.contains(estadoDeCuentaProveedoresCollectionOldEstadoDeCuentaProveedores)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EstadoDeCuentaProveedores " + estadoDeCuentaProveedoresCollectionOldEstadoDeCuentaProveedores + " since its facturaProveedor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pedidoCompraNew != null) {
                pedidoCompraNew = em.getReference(pedidoCompraNew.getClass(), pedidoCompraNew.getId());
                facturaProveedor.setPedidoCompra(pedidoCompraNew);
            }
            Collection<EstadoDeCuentaProveedores> attachedEstadoDeCuentaProveedoresCollectionNew = new ArrayList<EstadoDeCuentaProveedores>();
            for (EstadoDeCuentaProveedores estadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedoresToAttach : estadoDeCuentaProveedoresCollectionNew) {
                estadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedoresToAttach = em.getReference(estadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedoresToAttach.getClass(), estadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedoresToAttach.getId());
                attachedEstadoDeCuentaProveedoresCollectionNew.add(estadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedoresToAttach);
            }
            estadoDeCuentaProveedoresCollectionNew = attachedEstadoDeCuentaProveedoresCollectionNew;
            facturaProveedor.setEstadoDeCuentaProveedoresCollection(estadoDeCuentaProveedoresCollectionNew);
            facturaProveedor = em.merge(facturaProveedor);
            if (pedidoCompraOld != null && !pedidoCompraOld.equals(pedidoCompraNew)) {
                pedidoCompraOld.getFacturaProveedorCollection().remove(facturaProveedor);
                pedidoCompraOld = em.merge(pedidoCompraOld);
            }
            if (pedidoCompraNew != null && !pedidoCompraNew.equals(pedidoCompraOld)) {
                pedidoCompraNew.getFacturaProveedorCollection().add(facturaProveedor);
                pedidoCompraNew = em.merge(pedidoCompraNew);
            }
            for (EstadoDeCuentaProveedores estadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores : estadoDeCuentaProveedoresCollectionNew) {
                if (!estadoDeCuentaProveedoresCollectionOld.contains(estadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores)) {
                    FacturaProveedor oldFacturaProveedorOfEstadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores = estadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores.getFacturaProveedor();
                    estadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores.setFacturaProveedor(facturaProveedor);
                    estadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores = em.merge(estadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores);
                    if (oldFacturaProveedorOfEstadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores != null && !oldFacturaProveedorOfEstadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores.equals(facturaProveedor)) {
                        oldFacturaProveedorOfEstadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores.getEstadoDeCuentaProveedoresCollection().remove(estadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores);
                        oldFacturaProveedorOfEstadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores = em.merge(oldFacturaProveedorOfEstadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = facturaProveedor.getId();
                if (findFacturaProveedor(id) == null) {
                    throw new NonexistentEntityException("The facturaProveedor with id " + id + " no longer exists.");
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
            FacturaProveedor facturaProveedor;
            try {
                facturaProveedor = em.getReference(FacturaProveedor.class, id);
                facturaProveedor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facturaProveedor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<EstadoDeCuentaProveedores> estadoDeCuentaProveedoresCollectionOrphanCheck = facturaProveedor.getEstadoDeCuentaProveedoresCollection();
            for (EstadoDeCuentaProveedores estadoDeCuentaProveedoresCollectionOrphanCheckEstadoDeCuentaProveedores : estadoDeCuentaProveedoresCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This FacturaProveedor (" + facturaProveedor + ") cannot be destroyed since the EstadoDeCuentaProveedores " + estadoDeCuentaProveedoresCollectionOrphanCheckEstadoDeCuentaProveedores + " in its estadoDeCuentaProveedoresCollection field has a non-nullable facturaProveedor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            PedidoCompra pedidoCompra = facturaProveedor.getPedidoCompra();
            if (pedidoCompra != null) {
                pedidoCompra.getFacturaProveedorCollection().remove(facturaProveedor);
                pedidoCompra = em.merge(pedidoCompra);
            }
            em.remove(facturaProveedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FacturaProveedor> findFacturaProveedorEntities() {
        return findFacturaProveedorEntities(true, -1, -1);
    }

    public List<FacturaProveedor> findFacturaProveedorEntities(int maxResults, int firstResult) {
        return findFacturaProveedorEntities(false, maxResults, firstResult);
    }

    private List<FacturaProveedor> findFacturaProveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from FacturaProveedor as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public FacturaProveedor findFacturaProveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FacturaProveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaProveedorCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from FacturaProveedor as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
