package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.EstadoDeCuentaClientes;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.TipoMovimiento;
import com.pmarlen.model.beans.FacturaCliente;
import com.pmarlen.model.beans.EstadoDeCuenta;
import java.util.ArrayList;
import java.util.Collection;

/**
 * EstadoDeCuentaClientesJpaController
 */

@Repository("estadoDeCuentaClientesJpaController")

public class EstadoDeCuentaClientesJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoDeCuentaClientes estadoDeCuentaClientes) {
        if (estadoDeCuentaClientes.getEstadoDeCuentaCollection() == null) {
            estadoDeCuentaClientes.setEstadoDeCuentaCollection(new ArrayList<EstadoDeCuenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoMovimiento tipoMovimiento = estadoDeCuentaClientes.getTipoMovimiento();
            if (tipoMovimiento != null) {
                tipoMovimiento = em.getReference(tipoMovimiento.getClass(), tipoMovimiento.getId());
                estadoDeCuentaClientes.setTipoMovimiento(tipoMovimiento);
            }
            FacturaCliente facturaCliente = estadoDeCuentaClientes.getFacturaCliente();
            if (facturaCliente != null) {
                facturaCliente = em.getReference(facturaCliente.getClass(), facturaCliente.getId());
                estadoDeCuentaClientes.setFacturaCliente(facturaCliente);
            }
            Collection<EstadoDeCuenta> attachedEstadoDeCuentaCollection = new ArrayList<EstadoDeCuenta>();
            for (EstadoDeCuenta estadoDeCuentaCollectionEstadoDeCuentaToAttach : estadoDeCuentaClientes.getEstadoDeCuentaCollection()) {
                estadoDeCuentaCollectionEstadoDeCuentaToAttach = em.getReference(estadoDeCuentaCollectionEstadoDeCuentaToAttach.getClass(), estadoDeCuentaCollectionEstadoDeCuentaToAttach.getId());
                attachedEstadoDeCuentaCollection.add(estadoDeCuentaCollectionEstadoDeCuentaToAttach);
            }
            estadoDeCuentaClientes.setEstadoDeCuentaCollection(attachedEstadoDeCuentaCollection);
            em.persist(estadoDeCuentaClientes);
            if (tipoMovimiento != null) {
                tipoMovimiento.getEstadoDeCuentaClientesCollection().add(estadoDeCuentaClientes);
                tipoMovimiento = em.merge(tipoMovimiento);
            }
            if (facturaCliente != null) {
                facturaCliente.getEstadoDeCuentaClientesCollection().add(estadoDeCuentaClientes);
                facturaCliente = em.merge(facturaCliente);
            }
            for (EstadoDeCuenta estadoDeCuentaCollectionEstadoDeCuenta : estadoDeCuentaClientes.getEstadoDeCuentaCollection()) {
                EstadoDeCuentaClientes oldEstadoDeCuentaClientesOfEstadoDeCuentaCollectionEstadoDeCuenta = estadoDeCuentaCollectionEstadoDeCuenta.getEstadoDeCuentaClientes();
                estadoDeCuentaCollectionEstadoDeCuenta.setEstadoDeCuentaClientes(estadoDeCuentaClientes);
                estadoDeCuentaCollectionEstadoDeCuenta = em.merge(estadoDeCuentaCollectionEstadoDeCuenta);
                if (oldEstadoDeCuentaClientesOfEstadoDeCuentaCollectionEstadoDeCuenta != null) {
                    oldEstadoDeCuentaClientesOfEstadoDeCuentaCollectionEstadoDeCuenta.getEstadoDeCuentaCollection().remove(estadoDeCuentaCollectionEstadoDeCuenta);
                    oldEstadoDeCuentaClientesOfEstadoDeCuentaCollectionEstadoDeCuenta = em.merge(oldEstadoDeCuentaClientesOfEstadoDeCuentaCollectionEstadoDeCuenta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoDeCuentaClientes estadoDeCuentaClientes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoDeCuentaClientes persistentEstadoDeCuentaClientes = em.find(EstadoDeCuentaClientes.class, estadoDeCuentaClientes.getId());
            TipoMovimiento tipoMovimientoOld = persistentEstadoDeCuentaClientes.getTipoMovimiento();
            TipoMovimiento tipoMovimientoNew = estadoDeCuentaClientes.getTipoMovimiento();
            FacturaCliente facturaClienteOld = persistentEstadoDeCuentaClientes.getFacturaCliente();
            FacturaCliente facturaClienteNew = estadoDeCuentaClientes.getFacturaCliente();
            Collection<EstadoDeCuenta> estadoDeCuentaCollectionOld = persistentEstadoDeCuentaClientes.getEstadoDeCuentaCollection();
            Collection<EstadoDeCuenta> estadoDeCuentaCollectionNew = estadoDeCuentaClientes.getEstadoDeCuentaCollection();
            if (tipoMovimientoNew != null) {
                tipoMovimientoNew = em.getReference(tipoMovimientoNew.getClass(), tipoMovimientoNew.getId());
                estadoDeCuentaClientes.setTipoMovimiento(tipoMovimientoNew);
            }
            if (facturaClienteNew != null) {
                facturaClienteNew = em.getReference(facturaClienteNew.getClass(), facturaClienteNew.getId());
                estadoDeCuentaClientes.setFacturaCliente(facturaClienteNew);
            }
            Collection<EstadoDeCuenta> attachedEstadoDeCuentaCollectionNew = new ArrayList<EstadoDeCuenta>();
            for (EstadoDeCuenta estadoDeCuentaCollectionNewEstadoDeCuentaToAttach : estadoDeCuentaCollectionNew) {
                estadoDeCuentaCollectionNewEstadoDeCuentaToAttach = em.getReference(estadoDeCuentaCollectionNewEstadoDeCuentaToAttach.getClass(), estadoDeCuentaCollectionNewEstadoDeCuentaToAttach.getId());
                attachedEstadoDeCuentaCollectionNew.add(estadoDeCuentaCollectionNewEstadoDeCuentaToAttach);
            }
            estadoDeCuentaCollectionNew = attachedEstadoDeCuentaCollectionNew;
            estadoDeCuentaClientes.setEstadoDeCuentaCollection(estadoDeCuentaCollectionNew);
            estadoDeCuentaClientes = em.merge(estadoDeCuentaClientes);
            if (tipoMovimientoOld != null && !tipoMovimientoOld.equals(tipoMovimientoNew)) {
                tipoMovimientoOld.getEstadoDeCuentaClientesCollection().remove(estadoDeCuentaClientes);
                tipoMovimientoOld = em.merge(tipoMovimientoOld);
            }
            if (tipoMovimientoNew != null && !tipoMovimientoNew.equals(tipoMovimientoOld)) {
                tipoMovimientoNew.getEstadoDeCuentaClientesCollection().add(estadoDeCuentaClientes);
                tipoMovimientoNew = em.merge(tipoMovimientoNew);
            }
            if (facturaClienteOld != null && !facturaClienteOld.equals(facturaClienteNew)) {
                facturaClienteOld.getEstadoDeCuentaClientesCollection().remove(estadoDeCuentaClientes);
                facturaClienteOld = em.merge(facturaClienteOld);
            }
            if (facturaClienteNew != null && !facturaClienteNew.equals(facturaClienteOld)) {
                facturaClienteNew.getEstadoDeCuentaClientesCollection().add(estadoDeCuentaClientes);
                facturaClienteNew = em.merge(facturaClienteNew);
            }
            for (EstadoDeCuenta estadoDeCuentaCollectionOldEstadoDeCuenta : estadoDeCuentaCollectionOld) {
                if (!estadoDeCuentaCollectionNew.contains(estadoDeCuentaCollectionOldEstadoDeCuenta)) {
                    estadoDeCuentaCollectionOldEstadoDeCuenta.setEstadoDeCuentaClientes(null);
                    estadoDeCuentaCollectionOldEstadoDeCuenta = em.merge(estadoDeCuentaCollectionOldEstadoDeCuenta);
                }
            }
            for (EstadoDeCuenta estadoDeCuentaCollectionNewEstadoDeCuenta : estadoDeCuentaCollectionNew) {
                if (!estadoDeCuentaCollectionOld.contains(estadoDeCuentaCollectionNewEstadoDeCuenta)) {
                    EstadoDeCuentaClientes oldEstadoDeCuentaClientesOfEstadoDeCuentaCollectionNewEstadoDeCuenta = estadoDeCuentaCollectionNewEstadoDeCuenta.getEstadoDeCuentaClientes();
                    estadoDeCuentaCollectionNewEstadoDeCuenta.setEstadoDeCuentaClientes(estadoDeCuentaClientes);
                    estadoDeCuentaCollectionNewEstadoDeCuenta = em.merge(estadoDeCuentaCollectionNewEstadoDeCuenta);
                    if (oldEstadoDeCuentaClientesOfEstadoDeCuentaCollectionNewEstadoDeCuenta != null && !oldEstadoDeCuentaClientesOfEstadoDeCuentaCollectionNewEstadoDeCuenta.equals(estadoDeCuentaClientes)) {
                        oldEstadoDeCuentaClientesOfEstadoDeCuentaCollectionNewEstadoDeCuenta.getEstadoDeCuentaCollection().remove(estadoDeCuentaCollectionNewEstadoDeCuenta);
                        oldEstadoDeCuentaClientesOfEstadoDeCuentaCollectionNewEstadoDeCuenta = em.merge(oldEstadoDeCuentaClientesOfEstadoDeCuentaCollectionNewEstadoDeCuenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estadoDeCuentaClientes.getId();
                if (findEstadoDeCuentaClientes(id) == null) {
                    throw new NonexistentEntityException("The estadoDeCuentaClientes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoDeCuentaClientes estadoDeCuentaClientes;
            try {
                estadoDeCuentaClientes = em.getReference(EstadoDeCuentaClientes.class, id);
                estadoDeCuentaClientes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoDeCuentaClientes with id " + id + " no longer exists.", enfe);
            }
            TipoMovimiento tipoMovimiento = estadoDeCuentaClientes.getTipoMovimiento();
            if (tipoMovimiento != null) {
                tipoMovimiento.getEstadoDeCuentaClientesCollection().remove(estadoDeCuentaClientes);
                tipoMovimiento = em.merge(tipoMovimiento);
            }
            FacturaCliente facturaCliente = estadoDeCuentaClientes.getFacturaCliente();
            if (facturaCliente != null) {
                facturaCliente.getEstadoDeCuentaClientesCollection().remove(estadoDeCuentaClientes);
                facturaCliente = em.merge(facturaCliente);
            }
            Collection<EstadoDeCuenta> estadoDeCuentaCollection = estadoDeCuentaClientes.getEstadoDeCuentaCollection();
            for (EstadoDeCuenta estadoDeCuentaCollectionEstadoDeCuenta : estadoDeCuentaCollection) {
                estadoDeCuentaCollectionEstadoDeCuenta.setEstadoDeCuentaClientes(null);
                estadoDeCuentaCollectionEstadoDeCuenta = em.merge(estadoDeCuentaCollectionEstadoDeCuenta);
            }
            em.remove(estadoDeCuentaClientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoDeCuentaClientes> findEstadoDeCuentaClientesEntities() {
        return findEstadoDeCuentaClientesEntities(true, -1, -1);
    }

    public List<EstadoDeCuentaClientes> findEstadoDeCuentaClientesEntities(int maxResults, int firstResult) {
        return findEstadoDeCuentaClientesEntities(false, maxResults, firstResult);
    }

    private List<EstadoDeCuentaClientes> findEstadoDeCuentaClientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from EstadoDeCuentaClientes as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public EstadoDeCuentaClientes findEstadoDeCuentaClientes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoDeCuentaClientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoDeCuentaClientesCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from EstadoDeCuentaClientes as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
