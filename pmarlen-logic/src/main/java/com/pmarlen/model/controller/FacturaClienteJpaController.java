package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.FacturaCliente;
import com.pmarlen.model.controller.exceptions.IllegalOrphanException;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import com.pmarlen.model.controller.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.beans.EstadoDeCuentaClientes;
import java.util.ArrayList;
import java.util.Collection;

/**
 * FacturaClienteJpaController
 */

@Repository("facturaClienteJpaController")

public class FacturaClienteJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FacturaCliente facturaCliente) throws PreexistingEntityException, Exception {
        if (facturaCliente.getEstadoDeCuentaClientesCollection() == null) {
            facturaCliente.setEstadoDeCuentaClientesCollection(new ArrayList<EstadoDeCuentaClientes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PedidoVenta pedidoVenta = facturaCliente.getPedidoVenta();
            if (pedidoVenta != null) {
                pedidoVenta = em.getReference(pedidoVenta.getClass(), pedidoVenta.getId());
                facturaCliente.setPedidoVenta(pedidoVenta);
            }
            Collection<EstadoDeCuentaClientes> attachedEstadoDeCuentaClientesCollection = new ArrayList<EstadoDeCuentaClientes>();
            for (EstadoDeCuentaClientes estadoDeCuentaClientesCollectionEstadoDeCuentaClientesToAttach : facturaCliente.getEstadoDeCuentaClientesCollection()) {
                estadoDeCuentaClientesCollectionEstadoDeCuentaClientesToAttach = em.getReference(estadoDeCuentaClientesCollectionEstadoDeCuentaClientesToAttach.getClass(), estadoDeCuentaClientesCollectionEstadoDeCuentaClientesToAttach.getId());
                attachedEstadoDeCuentaClientesCollection.add(estadoDeCuentaClientesCollectionEstadoDeCuentaClientesToAttach);
            }
            facturaCliente.setEstadoDeCuentaClientesCollection(attachedEstadoDeCuentaClientesCollection);
            em.persist(facturaCliente);
            if (pedidoVenta != null) {
                pedidoVenta.getFacturaClienteCollection().add(facturaCliente);
                pedidoVenta = em.merge(pedidoVenta);
            }
            for (EstadoDeCuentaClientes estadoDeCuentaClientesCollectionEstadoDeCuentaClientes : facturaCliente.getEstadoDeCuentaClientesCollection()) {
                FacturaCliente oldFacturaClienteOfEstadoDeCuentaClientesCollectionEstadoDeCuentaClientes = estadoDeCuentaClientesCollectionEstadoDeCuentaClientes.getFacturaCliente();
                estadoDeCuentaClientesCollectionEstadoDeCuentaClientes.setFacturaCliente(facturaCliente);
                estadoDeCuentaClientesCollectionEstadoDeCuentaClientes = em.merge(estadoDeCuentaClientesCollectionEstadoDeCuentaClientes);
                if (oldFacturaClienteOfEstadoDeCuentaClientesCollectionEstadoDeCuentaClientes != null) {
                    oldFacturaClienteOfEstadoDeCuentaClientesCollectionEstadoDeCuentaClientes.getEstadoDeCuentaClientesCollection().remove(estadoDeCuentaClientesCollectionEstadoDeCuentaClientes);
                    oldFacturaClienteOfEstadoDeCuentaClientesCollectionEstadoDeCuentaClientes = em.merge(oldFacturaClienteOfEstadoDeCuentaClientesCollectionEstadoDeCuentaClientes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFacturaCliente(facturaCliente.getId()) != null) {
                throw new PreexistingEntityException("FacturaCliente " + facturaCliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FacturaCliente facturaCliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FacturaCliente persistentFacturaCliente = em.find(FacturaCliente.class, facturaCliente.getId());
            PedidoVenta pedidoVentaOld = persistentFacturaCliente.getPedidoVenta();
            PedidoVenta pedidoVentaNew = facturaCliente.getPedidoVenta();
            Collection<EstadoDeCuentaClientes> estadoDeCuentaClientesCollectionOld = persistentFacturaCliente.getEstadoDeCuentaClientesCollection();
            Collection<EstadoDeCuentaClientes> estadoDeCuentaClientesCollectionNew = facturaCliente.getEstadoDeCuentaClientesCollection();
            List<String> illegalOrphanMessages = null;
            for (EstadoDeCuentaClientes estadoDeCuentaClientesCollectionOldEstadoDeCuentaClientes : estadoDeCuentaClientesCollectionOld) {
                if (!estadoDeCuentaClientesCollectionNew.contains(estadoDeCuentaClientesCollectionOldEstadoDeCuentaClientes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EstadoDeCuentaClientes " + estadoDeCuentaClientesCollectionOldEstadoDeCuentaClientes + " since its facturaCliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pedidoVentaNew != null) {
                pedidoVentaNew = em.getReference(pedidoVentaNew.getClass(), pedidoVentaNew.getId());
                facturaCliente.setPedidoVenta(pedidoVentaNew);
            }
            Collection<EstadoDeCuentaClientes> attachedEstadoDeCuentaClientesCollectionNew = new ArrayList<EstadoDeCuentaClientes>();
            for (EstadoDeCuentaClientes estadoDeCuentaClientesCollectionNewEstadoDeCuentaClientesToAttach : estadoDeCuentaClientesCollectionNew) {
                estadoDeCuentaClientesCollectionNewEstadoDeCuentaClientesToAttach = em.getReference(estadoDeCuentaClientesCollectionNewEstadoDeCuentaClientesToAttach.getClass(), estadoDeCuentaClientesCollectionNewEstadoDeCuentaClientesToAttach.getId());
                attachedEstadoDeCuentaClientesCollectionNew.add(estadoDeCuentaClientesCollectionNewEstadoDeCuentaClientesToAttach);
            }
            estadoDeCuentaClientesCollectionNew = attachedEstadoDeCuentaClientesCollectionNew;
            facturaCliente.setEstadoDeCuentaClientesCollection(estadoDeCuentaClientesCollectionNew);
            facturaCliente = em.merge(facturaCliente);
            if (pedidoVentaOld != null && !pedidoVentaOld.equals(pedidoVentaNew)) {
                pedidoVentaOld.getFacturaClienteCollection().remove(facturaCliente);
                pedidoVentaOld = em.merge(pedidoVentaOld);
            }
            if (pedidoVentaNew != null && !pedidoVentaNew.equals(pedidoVentaOld)) {
                pedidoVentaNew.getFacturaClienteCollection().add(facturaCliente);
                pedidoVentaNew = em.merge(pedidoVentaNew);
            }
            for (EstadoDeCuentaClientes estadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes : estadoDeCuentaClientesCollectionNew) {
                if (!estadoDeCuentaClientesCollectionOld.contains(estadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes)) {
                    FacturaCliente oldFacturaClienteOfEstadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes = estadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes.getFacturaCliente();
                    estadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes.setFacturaCliente(facturaCliente);
                    estadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes = em.merge(estadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes);
                    if (oldFacturaClienteOfEstadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes != null && !oldFacturaClienteOfEstadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes.equals(facturaCliente)) {
                        oldFacturaClienteOfEstadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes.getEstadoDeCuentaClientesCollection().remove(estadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes);
                        oldFacturaClienteOfEstadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes = em.merge(oldFacturaClienteOfEstadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = facturaCliente.getId();
                if (findFacturaCliente(id) == null) {
                    throw new NonexistentEntityException("The facturaCliente with id " + id + " no longer exists.");
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
            FacturaCliente facturaCliente;
            try {
                facturaCliente = em.getReference(FacturaCliente.class, id);
                facturaCliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facturaCliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<EstadoDeCuentaClientes> estadoDeCuentaClientesCollectionOrphanCheck = facturaCliente.getEstadoDeCuentaClientesCollection();
            for (EstadoDeCuentaClientes estadoDeCuentaClientesCollectionOrphanCheckEstadoDeCuentaClientes : estadoDeCuentaClientesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This FacturaCliente (" + facturaCliente + ") cannot be destroyed since the EstadoDeCuentaClientes " + estadoDeCuentaClientesCollectionOrphanCheckEstadoDeCuentaClientes + " in its estadoDeCuentaClientesCollection field has a non-nullable facturaCliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            PedidoVenta pedidoVenta = facturaCliente.getPedidoVenta();
            if (pedidoVenta != null) {
                pedidoVenta.getFacturaClienteCollection().remove(facturaCliente);
                pedidoVenta = em.merge(pedidoVenta);
            }
            em.remove(facturaCliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FacturaCliente> findFacturaClienteEntities() {
        return findFacturaClienteEntities(true, -1, -1);
    }

    public List<FacturaCliente> findFacturaClienteEntities(int maxResults, int firstResult) {
        return findFacturaClienteEntities(false, maxResults, firstResult);
    }

    private List<FacturaCliente> findFacturaClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from FacturaCliente as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public FacturaCliente findFacturaCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FacturaCliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaClienteCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from FacturaCliente as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
