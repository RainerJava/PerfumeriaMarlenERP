package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.EstadoDeCuentaProveedores;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import com.pmarlen.model.controller.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.FacturaProveedor;
import com.pmarlen.model.beans.TipoMovimiento;
import com.pmarlen.model.beans.EstadoDeCuenta;
import java.util.ArrayList;
import java.util.Collection;

/**
 * EstadoDeCuentaProveedoresJpaController
 */

@Repository("estadoDeCuentaProveedoresJpaController")

public class EstadoDeCuentaProveedoresJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoDeCuentaProveedores estadoDeCuentaProveedores) throws PreexistingEntityException, Exception {
        if (estadoDeCuentaProveedores.getEstadoDeCuentaCollection() == null) {
            estadoDeCuentaProveedores.setEstadoDeCuentaCollection(new ArrayList<EstadoDeCuenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FacturaProveedor facturaProveedor = estadoDeCuentaProveedores.getFacturaProveedor();
            if (facturaProveedor != null) {
                facturaProveedor = em.getReference(facturaProveedor.getClass(), facturaProveedor.getId());
                estadoDeCuentaProveedores.setFacturaProveedor(facturaProveedor);
            }
            TipoMovimiento tipoMovimiento = estadoDeCuentaProveedores.getTipoMovimiento();
            if (tipoMovimiento != null) {
                tipoMovimiento = em.getReference(tipoMovimiento.getClass(), tipoMovimiento.getId());
                estadoDeCuentaProveedores.setTipoMovimiento(tipoMovimiento);
            }
            Collection<EstadoDeCuenta> attachedEstadoDeCuentaCollection = new ArrayList<EstadoDeCuenta>();
            for (EstadoDeCuenta estadoDeCuentaCollectionEstadoDeCuentaToAttach : estadoDeCuentaProveedores.getEstadoDeCuentaCollection()) {
                estadoDeCuentaCollectionEstadoDeCuentaToAttach = em.getReference(estadoDeCuentaCollectionEstadoDeCuentaToAttach.getClass(), estadoDeCuentaCollectionEstadoDeCuentaToAttach.getId());
                attachedEstadoDeCuentaCollection.add(estadoDeCuentaCollectionEstadoDeCuentaToAttach);
            }
            estadoDeCuentaProveedores.setEstadoDeCuentaCollection(attachedEstadoDeCuentaCollection);
            em.persist(estadoDeCuentaProveedores);
            if (facturaProveedor != null) {
                facturaProveedor.getEstadoDeCuentaProveedoresCollection().add(estadoDeCuentaProveedores);
                facturaProveedor = em.merge(facturaProveedor);
            }
            if (tipoMovimiento != null) {
                tipoMovimiento.getEstadoDeCuentaProveedoresCollection().add(estadoDeCuentaProveedores);
                tipoMovimiento = em.merge(tipoMovimiento);
            }
            for (EstadoDeCuenta estadoDeCuentaCollectionEstadoDeCuenta : estadoDeCuentaProveedores.getEstadoDeCuentaCollection()) {
                EstadoDeCuentaProveedores oldEstadoDeCuentaProveedoresOfEstadoDeCuentaCollectionEstadoDeCuenta = estadoDeCuentaCollectionEstadoDeCuenta.getEstadoDeCuentaProveedores();
                estadoDeCuentaCollectionEstadoDeCuenta.setEstadoDeCuentaProveedores(estadoDeCuentaProveedores);
                estadoDeCuentaCollectionEstadoDeCuenta = em.merge(estadoDeCuentaCollectionEstadoDeCuenta);
                if (oldEstadoDeCuentaProveedoresOfEstadoDeCuentaCollectionEstadoDeCuenta != null) {
                    oldEstadoDeCuentaProveedoresOfEstadoDeCuentaCollectionEstadoDeCuenta.getEstadoDeCuentaCollection().remove(estadoDeCuentaCollectionEstadoDeCuenta);
                    oldEstadoDeCuentaProveedoresOfEstadoDeCuentaCollectionEstadoDeCuenta = em.merge(oldEstadoDeCuentaProveedoresOfEstadoDeCuentaCollectionEstadoDeCuenta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstadoDeCuentaProveedores(estadoDeCuentaProveedores.getId()) != null) {
                throw new PreexistingEntityException("EstadoDeCuentaProveedores " + estadoDeCuentaProveedores + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoDeCuentaProveedores estadoDeCuentaProveedores) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoDeCuentaProveedores persistentEstadoDeCuentaProveedores = em.find(EstadoDeCuentaProveedores.class, estadoDeCuentaProveedores.getId());
            FacturaProveedor facturaProveedorOld = persistentEstadoDeCuentaProveedores.getFacturaProveedor();
            FacturaProveedor facturaProveedorNew = estadoDeCuentaProveedores.getFacturaProveedor();
            TipoMovimiento tipoMovimientoOld = persistentEstadoDeCuentaProveedores.getTipoMovimiento();
            TipoMovimiento tipoMovimientoNew = estadoDeCuentaProveedores.getTipoMovimiento();
            Collection<EstadoDeCuenta> estadoDeCuentaCollectionOld = persistentEstadoDeCuentaProveedores.getEstadoDeCuentaCollection();
            Collection<EstadoDeCuenta> estadoDeCuentaCollectionNew = estadoDeCuentaProveedores.getEstadoDeCuentaCollection();
            if (facturaProveedorNew != null) {
                facturaProveedorNew = em.getReference(facturaProveedorNew.getClass(), facturaProveedorNew.getId());
                estadoDeCuentaProveedores.setFacturaProveedor(facturaProveedorNew);
            }
            if (tipoMovimientoNew != null) {
                tipoMovimientoNew = em.getReference(tipoMovimientoNew.getClass(), tipoMovimientoNew.getId());
                estadoDeCuentaProveedores.setTipoMovimiento(tipoMovimientoNew);
            }
            Collection<EstadoDeCuenta> attachedEstadoDeCuentaCollectionNew = new ArrayList<EstadoDeCuenta>();
            for (EstadoDeCuenta estadoDeCuentaCollectionNewEstadoDeCuentaToAttach : estadoDeCuentaCollectionNew) {
                estadoDeCuentaCollectionNewEstadoDeCuentaToAttach = em.getReference(estadoDeCuentaCollectionNewEstadoDeCuentaToAttach.getClass(), estadoDeCuentaCollectionNewEstadoDeCuentaToAttach.getId());
                attachedEstadoDeCuentaCollectionNew.add(estadoDeCuentaCollectionNewEstadoDeCuentaToAttach);
            }
            estadoDeCuentaCollectionNew = attachedEstadoDeCuentaCollectionNew;
            estadoDeCuentaProveedores.setEstadoDeCuentaCollection(estadoDeCuentaCollectionNew);
            estadoDeCuentaProveedores = em.merge(estadoDeCuentaProveedores);
            if (facturaProveedorOld != null && !facturaProveedorOld.equals(facturaProveedorNew)) {
                facturaProveedorOld.getEstadoDeCuentaProveedoresCollection().remove(estadoDeCuentaProveedores);
                facturaProveedorOld = em.merge(facturaProveedorOld);
            }
            if (facturaProveedorNew != null && !facturaProveedorNew.equals(facturaProveedorOld)) {
                facturaProveedorNew.getEstadoDeCuentaProveedoresCollection().add(estadoDeCuentaProveedores);
                facturaProveedorNew = em.merge(facturaProveedorNew);
            }
            if (tipoMovimientoOld != null && !tipoMovimientoOld.equals(tipoMovimientoNew)) {
                tipoMovimientoOld.getEstadoDeCuentaProveedoresCollection().remove(estadoDeCuentaProveedores);
                tipoMovimientoOld = em.merge(tipoMovimientoOld);
            }
            if (tipoMovimientoNew != null && !tipoMovimientoNew.equals(tipoMovimientoOld)) {
                tipoMovimientoNew.getEstadoDeCuentaProveedoresCollection().add(estadoDeCuentaProveedores);
                tipoMovimientoNew = em.merge(tipoMovimientoNew);
            }
            for (EstadoDeCuenta estadoDeCuentaCollectionOldEstadoDeCuenta : estadoDeCuentaCollectionOld) {
                if (!estadoDeCuentaCollectionNew.contains(estadoDeCuentaCollectionOldEstadoDeCuenta)) {
                    estadoDeCuentaCollectionOldEstadoDeCuenta.setEstadoDeCuentaProveedores(null);
                    estadoDeCuentaCollectionOldEstadoDeCuenta = em.merge(estadoDeCuentaCollectionOldEstadoDeCuenta);
                }
            }
            for (EstadoDeCuenta estadoDeCuentaCollectionNewEstadoDeCuenta : estadoDeCuentaCollectionNew) {
                if (!estadoDeCuentaCollectionOld.contains(estadoDeCuentaCollectionNewEstadoDeCuenta)) {
                    EstadoDeCuentaProveedores oldEstadoDeCuentaProveedoresOfEstadoDeCuentaCollectionNewEstadoDeCuenta = estadoDeCuentaCollectionNewEstadoDeCuenta.getEstadoDeCuentaProveedores();
                    estadoDeCuentaCollectionNewEstadoDeCuenta.setEstadoDeCuentaProveedores(estadoDeCuentaProveedores);
                    estadoDeCuentaCollectionNewEstadoDeCuenta = em.merge(estadoDeCuentaCollectionNewEstadoDeCuenta);
                    if (oldEstadoDeCuentaProveedoresOfEstadoDeCuentaCollectionNewEstadoDeCuenta != null && !oldEstadoDeCuentaProveedoresOfEstadoDeCuentaCollectionNewEstadoDeCuenta.equals(estadoDeCuentaProveedores)) {
                        oldEstadoDeCuentaProveedoresOfEstadoDeCuentaCollectionNewEstadoDeCuenta.getEstadoDeCuentaCollection().remove(estadoDeCuentaCollectionNewEstadoDeCuenta);
                        oldEstadoDeCuentaProveedoresOfEstadoDeCuentaCollectionNewEstadoDeCuenta = em.merge(oldEstadoDeCuentaProveedoresOfEstadoDeCuentaCollectionNewEstadoDeCuenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estadoDeCuentaProveedores.getId();
                if (findEstadoDeCuentaProveedores(id) == null) {
                    throw new NonexistentEntityException("The estadoDeCuentaProveedores with id " + id + " no longer exists.");
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
            EstadoDeCuentaProveedores estadoDeCuentaProveedores;
            try {
                estadoDeCuentaProveedores = em.getReference(EstadoDeCuentaProveedores.class, id);
                estadoDeCuentaProveedores.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoDeCuentaProveedores with id " + id + " no longer exists.", enfe);
            }
            FacturaProveedor facturaProveedor = estadoDeCuentaProveedores.getFacturaProveedor();
            if (facturaProveedor != null) {
                facturaProveedor.getEstadoDeCuentaProveedoresCollection().remove(estadoDeCuentaProveedores);
                facturaProveedor = em.merge(facturaProveedor);
            }
            TipoMovimiento tipoMovimiento = estadoDeCuentaProveedores.getTipoMovimiento();
            if (tipoMovimiento != null) {
                tipoMovimiento.getEstadoDeCuentaProveedoresCollection().remove(estadoDeCuentaProveedores);
                tipoMovimiento = em.merge(tipoMovimiento);
            }
            Collection<EstadoDeCuenta> estadoDeCuentaCollection = estadoDeCuentaProveedores.getEstadoDeCuentaCollection();
            for (EstadoDeCuenta estadoDeCuentaCollectionEstadoDeCuenta : estadoDeCuentaCollection) {
                estadoDeCuentaCollectionEstadoDeCuenta.setEstadoDeCuentaProveedores(null);
                estadoDeCuentaCollectionEstadoDeCuenta = em.merge(estadoDeCuentaCollectionEstadoDeCuenta);
            }
            em.remove(estadoDeCuentaProveedores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoDeCuentaProveedores> findEstadoDeCuentaProveedoresEntities() {
        return findEstadoDeCuentaProveedoresEntities(true, -1, -1);
    }

    public List<EstadoDeCuentaProveedores> findEstadoDeCuentaProveedoresEntities(int maxResults, int firstResult) {
        return findEstadoDeCuentaProveedoresEntities(false, maxResults, firstResult);
    }

    private List<EstadoDeCuentaProveedores> findEstadoDeCuentaProveedoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from EstadoDeCuentaProveedores as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public EstadoDeCuentaProveedores findEstadoDeCuentaProveedores(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoDeCuentaProveedores.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoDeCuentaProveedoresCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from EstadoDeCuentaProveedores as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
