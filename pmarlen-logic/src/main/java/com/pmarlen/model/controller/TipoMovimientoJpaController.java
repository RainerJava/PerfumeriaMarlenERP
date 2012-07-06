package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.TipoMovimiento;
import com.pmarlen.model.controller.exceptions.IllegalOrphanException;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.EstadoDeCuentaClientes;
import java.util.ArrayList;
import java.util.Collection;
import com.pmarlen.model.beans.EstadoDeCuentaProveedores;
import com.pmarlen.model.beans.MovimientoHistoricoProducto;

/**
 * TipoMovimientoJpaController
 */

@Repository("tipoMovimientoJpaController")

public class TipoMovimientoJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoMovimiento tipoMovimiento) {
        if (tipoMovimiento.getEstadoDeCuentaClientesCollection() == null) {
            tipoMovimiento.setEstadoDeCuentaClientesCollection(new ArrayList<EstadoDeCuentaClientes>());
        }
        if (tipoMovimiento.getEstadoDeCuentaProveedoresCollection() == null) {
            tipoMovimiento.setEstadoDeCuentaProveedoresCollection(new ArrayList<EstadoDeCuentaProveedores>());
        }
        if (tipoMovimiento.getMovimientoHistoricoProductoCollection() == null) {
            tipoMovimiento.setMovimientoHistoricoProductoCollection(new ArrayList<MovimientoHistoricoProducto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<EstadoDeCuentaClientes> attachedEstadoDeCuentaClientesCollection = new ArrayList<EstadoDeCuentaClientes>();
            for (EstadoDeCuentaClientes estadoDeCuentaClientesCollectionEstadoDeCuentaClientesToAttach : tipoMovimiento.getEstadoDeCuentaClientesCollection()) {
                estadoDeCuentaClientesCollectionEstadoDeCuentaClientesToAttach = em.getReference(estadoDeCuentaClientesCollectionEstadoDeCuentaClientesToAttach.getClass(), estadoDeCuentaClientesCollectionEstadoDeCuentaClientesToAttach.getId());
                attachedEstadoDeCuentaClientesCollection.add(estadoDeCuentaClientesCollectionEstadoDeCuentaClientesToAttach);
            }
            tipoMovimiento.setEstadoDeCuentaClientesCollection(attachedEstadoDeCuentaClientesCollection);
            Collection<EstadoDeCuentaProveedores> attachedEstadoDeCuentaProveedoresCollection = new ArrayList<EstadoDeCuentaProveedores>();
            for (EstadoDeCuentaProveedores estadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedoresToAttach : tipoMovimiento.getEstadoDeCuentaProveedoresCollection()) {
                estadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedoresToAttach = em.getReference(estadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedoresToAttach.getClass(), estadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedoresToAttach.getId());
                attachedEstadoDeCuentaProveedoresCollection.add(estadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedoresToAttach);
            }
            tipoMovimiento.setEstadoDeCuentaProveedoresCollection(attachedEstadoDeCuentaProveedoresCollection);
            Collection<MovimientoHistoricoProducto> attachedMovimientoHistoricoProductoCollection = new ArrayList<MovimientoHistoricoProducto>();
            for (MovimientoHistoricoProducto movimientoHistoricoProductoCollectionMovimientoHistoricoProductoToAttach : tipoMovimiento.getMovimientoHistoricoProductoCollection()) {
                movimientoHistoricoProductoCollectionMovimientoHistoricoProductoToAttach = em.getReference(movimientoHistoricoProductoCollectionMovimientoHistoricoProductoToAttach.getClass(), movimientoHistoricoProductoCollectionMovimientoHistoricoProductoToAttach.getMovimientoHistoricoProductoPK());
                attachedMovimientoHistoricoProductoCollection.add(movimientoHistoricoProductoCollectionMovimientoHistoricoProductoToAttach);
            }
            tipoMovimiento.setMovimientoHistoricoProductoCollection(attachedMovimientoHistoricoProductoCollection);
            em.persist(tipoMovimiento);
            for (EstadoDeCuentaClientes estadoDeCuentaClientesCollectionEstadoDeCuentaClientes : tipoMovimiento.getEstadoDeCuentaClientesCollection()) {
                TipoMovimiento oldTipoMovimientoOfEstadoDeCuentaClientesCollectionEstadoDeCuentaClientes = estadoDeCuentaClientesCollectionEstadoDeCuentaClientes.getTipoMovimiento();
                estadoDeCuentaClientesCollectionEstadoDeCuentaClientes.setTipoMovimiento(tipoMovimiento);
                estadoDeCuentaClientesCollectionEstadoDeCuentaClientes = em.merge(estadoDeCuentaClientesCollectionEstadoDeCuentaClientes);
                if (oldTipoMovimientoOfEstadoDeCuentaClientesCollectionEstadoDeCuentaClientes != null) {
                    oldTipoMovimientoOfEstadoDeCuentaClientesCollectionEstadoDeCuentaClientes.getEstadoDeCuentaClientesCollection().remove(estadoDeCuentaClientesCollectionEstadoDeCuentaClientes);
                    oldTipoMovimientoOfEstadoDeCuentaClientesCollectionEstadoDeCuentaClientes = em.merge(oldTipoMovimientoOfEstadoDeCuentaClientesCollectionEstadoDeCuentaClientes);
                }
            }
            for (EstadoDeCuentaProveedores estadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedores : tipoMovimiento.getEstadoDeCuentaProveedoresCollection()) {
                TipoMovimiento oldTipoMovimientoOfEstadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedores = estadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedores.getTipoMovimiento();
                estadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedores.setTipoMovimiento(tipoMovimiento);
                estadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedores = em.merge(estadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedores);
                if (oldTipoMovimientoOfEstadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedores != null) {
                    oldTipoMovimientoOfEstadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedores.getEstadoDeCuentaProveedoresCollection().remove(estadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedores);
                    oldTipoMovimientoOfEstadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedores = em.merge(oldTipoMovimientoOfEstadoDeCuentaProveedoresCollectionEstadoDeCuentaProveedores);
                }
            }
            for (MovimientoHistoricoProducto movimientoHistoricoProductoCollectionMovimientoHistoricoProducto : tipoMovimiento.getMovimientoHistoricoProductoCollection()) {
                TipoMovimiento oldTipoMovimientoOfMovimientoHistoricoProductoCollectionMovimientoHistoricoProducto = movimientoHistoricoProductoCollectionMovimientoHistoricoProducto.getTipoMovimiento();
                movimientoHistoricoProductoCollectionMovimientoHistoricoProducto.setTipoMovimiento(tipoMovimiento);
                movimientoHistoricoProductoCollectionMovimientoHistoricoProducto = em.merge(movimientoHistoricoProductoCollectionMovimientoHistoricoProducto);
                if (oldTipoMovimientoOfMovimientoHistoricoProductoCollectionMovimientoHistoricoProducto != null) {
                    oldTipoMovimientoOfMovimientoHistoricoProductoCollectionMovimientoHistoricoProducto.getMovimientoHistoricoProductoCollection().remove(movimientoHistoricoProductoCollectionMovimientoHistoricoProducto);
                    oldTipoMovimientoOfMovimientoHistoricoProductoCollectionMovimientoHistoricoProducto = em.merge(oldTipoMovimientoOfMovimientoHistoricoProductoCollectionMovimientoHistoricoProducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoMovimiento tipoMovimiento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoMovimiento persistentTipoMovimiento = em.find(TipoMovimiento.class, tipoMovimiento.getId());
            Collection<EstadoDeCuentaClientes> estadoDeCuentaClientesCollectionOld = persistentTipoMovimiento.getEstadoDeCuentaClientesCollection();
            Collection<EstadoDeCuentaClientes> estadoDeCuentaClientesCollectionNew = tipoMovimiento.getEstadoDeCuentaClientesCollection();
            Collection<EstadoDeCuentaProveedores> estadoDeCuentaProveedoresCollectionOld = persistentTipoMovimiento.getEstadoDeCuentaProveedoresCollection();
            Collection<EstadoDeCuentaProveedores> estadoDeCuentaProveedoresCollectionNew = tipoMovimiento.getEstadoDeCuentaProveedoresCollection();
            Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollectionOld = persistentTipoMovimiento.getMovimientoHistoricoProductoCollection();
            Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollectionNew = tipoMovimiento.getMovimientoHistoricoProductoCollection();
            List<String> illegalOrphanMessages = null;
            for (EstadoDeCuentaClientes estadoDeCuentaClientesCollectionOldEstadoDeCuentaClientes : estadoDeCuentaClientesCollectionOld) {
                if (!estadoDeCuentaClientesCollectionNew.contains(estadoDeCuentaClientesCollectionOldEstadoDeCuentaClientes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EstadoDeCuentaClientes " + estadoDeCuentaClientesCollectionOldEstadoDeCuentaClientes + " since its tipoMovimiento field is not nullable.");
                }
            }
            for (EstadoDeCuentaProveedores estadoDeCuentaProveedoresCollectionOldEstadoDeCuentaProveedores : estadoDeCuentaProveedoresCollectionOld) {
                if (!estadoDeCuentaProveedoresCollectionNew.contains(estadoDeCuentaProveedoresCollectionOldEstadoDeCuentaProveedores)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EstadoDeCuentaProveedores " + estadoDeCuentaProveedoresCollectionOldEstadoDeCuentaProveedores + " since its tipoMovimiento field is not nullable.");
                }
            }
            for (MovimientoHistoricoProducto movimientoHistoricoProductoCollectionOldMovimientoHistoricoProducto : movimientoHistoricoProductoCollectionOld) {
                if (!movimientoHistoricoProductoCollectionNew.contains(movimientoHistoricoProductoCollectionOldMovimientoHistoricoProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MovimientoHistoricoProducto " + movimientoHistoricoProductoCollectionOldMovimientoHistoricoProducto + " since its tipoMovimiento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<EstadoDeCuentaClientes> attachedEstadoDeCuentaClientesCollectionNew = new ArrayList<EstadoDeCuentaClientes>();
            for (EstadoDeCuentaClientes estadoDeCuentaClientesCollectionNewEstadoDeCuentaClientesToAttach : estadoDeCuentaClientesCollectionNew) {
                estadoDeCuentaClientesCollectionNewEstadoDeCuentaClientesToAttach = em.getReference(estadoDeCuentaClientesCollectionNewEstadoDeCuentaClientesToAttach.getClass(), estadoDeCuentaClientesCollectionNewEstadoDeCuentaClientesToAttach.getId());
                attachedEstadoDeCuentaClientesCollectionNew.add(estadoDeCuentaClientesCollectionNewEstadoDeCuentaClientesToAttach);
            }
            estadoDeCuentaClientesCollectionNew = attachedEstadoDeCuentaClientesCollectionNew;
            tipoMovimiento.setEstadoDeCuentaClientesCollection(estadoDeCuentaClientesCollectionNew);
            Collection<EstadoDeCuentaProveedores> attachedEstadoDeCuentaProveedoresCollectionNew = new ArrayList<EstadoDeCuentaProveedores>();
            for (EstadoDeCuentaProveedores estadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedoresToAttach : estadoDeCuentaProveedoresCollectionNew) {
                estadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedoresToAttach = em.getReference(estadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedoresToAttach.getClass(), estadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedoresToAttach.getId());
                attachedEstadoDeCuentaProveedoresCollectionNew.add(estadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedoresToAttach);
            }
            estadoDeCuentaProveedoresCollectionNew = attachedEstadoDeCuentaProveedoresCollectionNew;
            tipoMovimiento.setEstadoDeCuentaProveedoresCollection(estadoDeCuentaProveedoresCollectionNew);
            Collection<MovimientoHistoricoProducto> attachedMovimientoHistoricoProductoCollectionNew = new ArrayList<MovimientoHistoricoProducto>();
            for (MovimientoHistoricoProducto movimientoHistoricoProductoCollectionNewMovimientoHistoricoProductoToAttach : movimientoHistoricoProductoCollectionNew) {
                movimientoHistoricoProductoCollectionNewMovimientoHistoricoProductoToAttach = em.getReference(movimientoHistoricoProductoCollectionNewMovimientoHistoricoProductoToAttach.getClass(), movimientoHistoricoProductoCollectionNewMovimientoHistoricoProductoToAttach.getMovimientoHistoricoProductoPK());
                attachedMovimientoHistoricoProductoCollectionNew.add(movimientoHistoricoProductoCollectionNewMovimientoHistoricoProductoToAttach);
            }
            movimientoHistoricoProductoCollectionNew = attachedMovimientoHistoricoProductoCollectionNew;
            tipoMovimiento.setMovimientoHistoricoProductoCollection(movimientoHistoricoProductoCollectionNew);
            tipoMovimiento = em.merge(tipoMovimiento);
            for (EstadoDeCuentaClientes estadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes : estadoDeCuentaClientesCollectionNew) {
                if (!estadoDeCuentaClientesCollectionOld.contains(estadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes)) {
                    TipoMovimiento oldTipoMovimientoOfEstadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes = estadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes.getTipoMovimiento();
                    estadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes.setTipoMovimiento(tipoMovimiento);
                    estadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes = em.merge(estadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes);
                    if (oldTipoMovimientoOfEstadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes != null && !oldTipoMovimientoOfEstadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes.equals(tipoMovimiento)) {
                        oldTipoMovimientoOfEstadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes.getEstadoDeCuentaClientesCollection().remove(estadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes);
                        oldTipoMovimientoOfEstadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes = em.merge(oldTipoMovimientoOfEstadoDeCuentaClientesCollectionNewEstadoDeCuentaClientes);
                    }
                }
            }
            for (EstadoDeCuentaProveedores estadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores : estadoDeCuentaProveedoresCollectionNew) {
                if (!estadoDeCuentaProveedoresCollectionOld.contains(estadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores)) {
                    TipoMovimiento oldTipoMovimientoOfEstadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores = estadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores.getTipoMovimiento();
                    estadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores.setTipoMovimiento(tipoMovimiento);
                    estadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores = em.merge(estadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores);
                    if (oldTipoMovimientoOfEstadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores != null && !oldTipoMovimientoOfEstadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores.equals(tipoMovimiento)) {
                        oldTipoMovimientoOfEstadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores.getEstadoDeCuentaProveedoresCollection().remove(estadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores);
                        oldTipoMovimientoOfEstadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores = em.merge(oldTipoMovimientoOfEstadoDeCuentaProveedoresCollectionNewEstadoDeCuentaProveedores);
                    }
                }
            }
            for (MovimientoHistoricoProducto movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto : movimientoHistoricoProductoCollectionNew) {
                if (!movimientoHistoricoProductoCollectionOld.contains(movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto)) {
                    TipoMovimiento oldTipoMovimientoOfMovimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto = movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto.getTipoMovimiento();
                    movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto.setTipoMovimiento(tipoMovimiento);
                    movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto = em.merge(movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto);
                    if (oldTipoMovimientoOfMovimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto != null && !oldTipoMovimientoOfMovimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto.equals(tipoMovimiento)) {
                        oldTipoMovimientoOfMovimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto.getMovimientoHistoricoProductoCollection().remove(movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto);
                        oldTipoMovimientoOfMovimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto = em.merge(oldTipoMovimientoOfMovimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoMovimiento.getId();
                if (findTipoMovimiento(id) == null) {
                    throw new NonexistentEntityException("The tipoMovimiento with id " + id + " no longer exists.");
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
            TipoMovimiento tipoMovimiento;
            try {
                tipoMovimiento = em.getReference(TipoMovimiento.class, id);
                tipoMovimiento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoMovimiento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<EstadoDeCuentaClientes> estadoDeCuentaClientesCollectionOrphanCheck = tipoMovimiento.getEstadoDeCuentaClientesCollection();
            for (EstadoDeCuentaClientes estadoDeCuentaClientesCollectionOrphanCheckEstadoDeCuentaClientes : estadoDeCuentaClientesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoMovimiento (" + tipoMovimiento + ") cannot be destroyed since the EstadoDeCuentaClientes " + estadoDeCuentaClientesCollectionOrphanCheckEstadoDeCuentaClientes + " in its estadoDeCuentaClientesCollection field has a non-nullable tipoMovimiento field.");
            }
            Collection<EstadoDeCuentaProveedores> estadoDeCuentaProveedoresCollectionOrphanCheck = tipoMovimiento.getEstadoDeCuentaProveedoresCollection();
            for (EstadoDeCuentaProveedores estadoDeCuentaProveedoresCollectionOrphanCheckEstadoDeCuentaProveedores : estadoDeCuentaProveedoresCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoMovimiento (" + tipoMovimiento + ") cannot be destroyed since the EstadoDeCuentaProveedores " + estadoDeCuentaProveedoresCollectionOrphanCheckEstadoDeCuentaProveedores + " in its estadoDeCuentaProveedoresCollection field has a non-nullable tipoMovimiento field.");
            }
            Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollectionOrphanCheck = tipoMovimiento.getMovimientoHistoricoProductoCollection();
            for (MovimientoHistoricoProducto movimientoHistoricoProductoCollectionOrphanCheckMovimientoHistoricoProducto : movimientoHistoricoProductoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoMovimiento (" + tipoMovimiento + ") cannot be destroyed since the MovimientoHistoricoProducto " + movimientoHistoricoProductoCollectionOrphanCheckMovimientoHistoricoProducto + " in its movimientoHistoricoProductoCollection field has a non-nullable tipoMovimiento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoMovimiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoMovimiento> findTipoMovimientoEntities() {
        return findTipoMovimientoEntities(true, -1, -1);
    }

    public List<TipoMovimiento> findTipoMovimientoEntities(int maxResults, int firstResult) {
        return findTipoMovimientoEntities(false, maxResults, firstResult);
    }

    private List<TipoMovimiento> findTipoMovimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TipoMovimiento as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TipoMovimiento findTipoMovimiento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoMovimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoMovimientoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from TipoMovimiento as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
