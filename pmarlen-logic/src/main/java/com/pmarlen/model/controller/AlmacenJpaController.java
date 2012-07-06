package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.Almacen;
import com.pmarlen.model.controller.exceptions.IllegalOrphanException;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.Poblacion;
import com.pmarlen.model.beans.MovimientoHistoricoProducto;
import java.util.ArrayList;
import java.util.Collection;
import com.pmarlen.model.beans.AlmacenProducto;

/**
 * AlmacenJpaController
 */

@Repository("almacenJpaController")

public class AlmacenJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Almacen almacen) {
        if (almacen.getMovimientoHistoricoProductoCollection() == null) {
            almacen.setMovimientoHistoricoProductoCollection(new ArrayList<MovimientoHistoricoProducto>());
        }
        if (almacen.getAlmacenProductoCollection() == null) {
            almacen.setAlmacenProductoCollection(new ArrayList<AlmacenProducto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Poblacion poblacion = almacen.getPoblacion();
            if (poblacion != null) {
                poblacion = em.getReference(poblacion.getClass(), poblacion.getId());
                almacen.setPoblacion(poblacion);
            }
            Collection<MovimientoHistoricoProducto> attachedMovimientoHistoricoProductoCollection = new ArrayList<MovimientoHistoricoProducto>();
            for (MovimientoHistoricoProducto movimientoHistoricoProductoCollectionMovimientoHistoricoProductoToAttach : almacen.getMovimientoHistoricoProductoCollection()) {
                movimientoHistoricoProductoCollectionMovimientoHistoricoProductoToAttach = em.getReference(movimientoHistoricoProductoCollectionMovimientoHistoricoProductoToAttach.getClass(), movimientoHistoricoProductoCollectionMovimientoHistoricoProductoToAttach.getMovimientoHistoricoProductoPK());
                attachedMovimientoHistoricoProductoCollection.add(movimientoHistoricoProductoCollectionMovimientoHistoricoProductoToAttach);
            }
            almacen.setMovimientoHistoricoProductoCollection(attachedMovimientoHistoricoProductoCollection);
            Collection<AlmacenProducto> attachedAlmacenProductoCollection = new ArrayList<AlmacenProducto>();
            for (AlmacenProducto almacenProductoCollectionAlmacenProductoToAttach : almacen.getAlmacenProductoCollection()) {
                almacenProductoCollectionAlmacenProductoToAttach = em.getReference(almacenProductoCollectionAlmacenProductoToAttach.getClass(), almacenProductoCollectionAlmacenProductoToAttach.getAlmacenProductoPK());
                attachedAlmacenProductoCollection.add(almacenProductoCollectionAlmacenProductoToAttach);
            }
            almacen.setAlmacenProductoCollection(attachedAlmacenProductoCollection);
            em.persist(almacen);
            if (poblacion != null) {
                poblacion.getAlmacenCollection().add(almacen);
                poblacion = em.merge(poblacion);
            }
            for (MovimientoHistoricoProducto movimientoHistoricoProductoCollectionMovimientoHistoricoProducto : almacen.getMovimientoHistoricoProductoCollection()) {
                Almacen oldAlmacenOfMovimientoHistoricoProductoCollectionMovimientoHistoricoProducto = movimientoHistoricoProductoCollectionMovimientoHistoricoProducto.getAlmacen();
                movimientoHistoricoProductoCollectionMovimientoHistoricoProducto.setAlmacen(almacen);
                movimientoHistoricoProductoCollectionMovimientoHistoricoProducto = em.merge(movimientoHistoricoProductoCollectionMovimientoHistoricoProducto);
                if (oldAlmacenOfMovimientoHistoricoProductoCollectionMovimientoHistoricoProducto != null) {
                    oldAlmacenOfMovimientoHistoricoProductoCollectionMovimientoHistoricoProducto.getMovimientoHistoricoProductoCollection().remove(movimientoHistoricoProductoCollectionMovimientoHistoricoProducto);
                    oldAlmacenOfMovimientoHistoricoProductoCollectionMovimientoHistoricoProducto = em.merge(oldAlmacenOfMovimientoHistoricoProductoCollectionMovimientoHistoricoProducto);
                }
            }
            for (AlmacenProducto almacenProductoCollectionAlmacenProducto : almacen.getAlmacenProductoCollection()) {
                Almacen oldAlmacenOfAlmacenProductoCollectionAlmacenProducto = almacenProductoCollectionAlmacenProducto.getAlmacen();
                almacenProductoCollectionAlmacenProducto.setAlmacen(almacen);
                almacenProductoCollectionAlmacenProducto = em.merge(almacenProductoCollectionAlmacenProducto);
                if (oldAlmacenOfAlmacenProductoCollectionAlmacenProducto != null) {
                    oldAlmacenOfAlmacenProductoCollectionAlmacenProducto.getAlmacenProductoCollection().remove(almacenProductoCollectionAlmacenProducto);
                    oldAlmacenOfAlmacenProductoCollectionAlmacenProducto = em.merge(oldAlmacenOfAlmacenProductoCollectionAlmacenProducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Almacen almacen) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Almacen persistentAlmacen = em.find(Almacen.class, almacen.getId());
            Poblacion poblacionOld = persistentAlmacen.getPoblacion();
            Poblacion poblacionNew = almacen.getPoblacion();
            Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollectionOld = persistentAlmacen.getMovimientoHistoricoProductoCollection();
            Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollectionNew = almacen.getMovimientoHistoricoProductoCollection();
            Collection<AlmacenProducto> almacenProductoCollectionOld = persistentAlmacen.getAlmacenProductoCollection();
            Collection<AlmacenProducto> almacenProductoCollectionNew = almacen.getAlmacenProductoCollection();
            List<String> illegalOrphanMessages = null;
            for (MovimientoHistoricoProducto movimientoHistoricoProductoCollectionOldMovimientoHistoricoProducto : movimientoHistoricoProductoCollectionOld) {
                if (!movimientoHistoricoProductoCollectionNew.contains(movimientoHistoricoProductoCollectionOldMovimientoHistoricoProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MovimientoHistoricoProducto " + movimientoHistoricoProductoCollectionOldMovimientoHistoricoProducto + " since its almacen field is not nullable.");
                }
            }
            for (AlmacenProducto almacenProductoCollectionOldAlmacenProducto : almacenProductoCollectionOld) {
                if (!almacenProductoCollectionNew.contains(almacenProductoCollectionOldAlmacenProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AlmacenProducto " + almacenProductoCollectionOldAlmacenProducto + " since its almacen field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (poblacionNew != null) {
                poblacionNew = em.getReference(poblacionNew.getClass(), poblacionNew.getId());
                almacen.setPoblacion(poblacionNew);
            }
            Collection<MovimientoHistoricoProducto> attachedMovimientoHistoricoProductoCollectionNew = new ArrayList<MovimientoHistoricoProducto>();
            for (MovimientoHistoricoProducto movimientoHistoricoProductoCollectionNewMovimientoHistoricoProductoToAttach : movimientoHistoricoProductoCollectionNew) {
                movimientoHistoricoProductoCollectionNewMovimientoHistoricoProductoToAttach = em.getReference(movimientoHistoricoProductoCollectionNewMovimientoHistoricoProductoToAttach.getClass(), movimientoHistoricoProductoCollectionNewMovimientoHistoricoProductoToAttach.getMovimientoHistoricoProductoPK());
                attachedMovimientoHistoricoProductoCollectionNew.add(movimientoHistoricoProductoCollectionNewMovimientoHistoricoProductoToAttach);
            }
            movimientoHistoricoProductoCollectionNew = attachedMovimientoHistoricoProductoCollectionNew;
            almacen.setMovimientoHistoricoProductoCollection(movimientoHistoricoProductoCollectionNew);
            Collection<AlmacenProducto> attachedAlmacenProductoCollectionNew = new ArrayList<AlmacenProducto>();
            for (AlmacenProducto almacenProductoCollectionNewAlmacenProductoToAttach : almacenProductoCollectionNew) {
                almacenProductoCollectionNewAlmacenProductoToAttach = em.getReference(almacenProductoCollectionNewAlmacenProductoToAttach.getClass(), almacenProductoCollectionNewAlmacenProductoToAttach.getAlmacenProductoPK());
                attachedAlmacenProductoCollectionNew.add(almacenProductoCollectionNewAlmacenProductoToAttach);
            }
            almacenProductoCollectionNew = attachedAlmacenProductoCollectionNew;
            almacen.setAlmacenProductoCollection(almacenProductoCollectionNew);
            almacen = em.merge(almacen);
            if (poblacionOld != null && !poblacionOld.equals(poblacionNew)) {
                poblacionOld.getAlmacenCollection().remove(almacen);
                poblacionOld = em.merge(poblacionOld);
            }
            if (poblacionNew != null && !poblacionNew.equals(poblacionOld)) {
                poblacionNew.getAlmacenCollection().add(almacen);
                poblacionNew = em.merge(poblacionNew);
            }
            for (MovimientoHistoricoProducto movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto : movimientoHistoricoProductoCollectionNew) {
                if (!movimientoHistoricoProductoCollectionOld.contains(movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto)) {
                    Almacen oldAlmacenOfMovimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto = movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto.getAlmacen();
                    movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto.setAlmacen(almacen);
                    movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto = em.merge(movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto);
                    if (oldAlmacenOfMovimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto != null && !oldAlmacenOfMovimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto.equals(almacen)) {
                        oldAlmacenOfMovimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto.getMovimientoHistoricoProductoCollection().remove(movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto);
                        oldAlmacenOfMovimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto = em.merge(oldAlmacenOfMovimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto);
                    }
                }
            }
            for (AlmacenProducto almacenProductoCollectionNewAlmacenProducto : almacenProductoCollectionNew) {
                if (!almacenProductoCollectionOld.contains(almacenProductoCollectionNewAlmacenProducto)) {
                    Almacen oldAlmacenOfAlmacenProductoCollectionNewAlmacenProducto = almacenProductoCollectionNewAlmacenProducto.getAlmacen();
                    almacenProductoCollectionNewAlmacenProducto.setAlmacen(almacen);
                    almacenProductoCollectionNewAlmacenProducto = em.merge(almacenProductoCollectionNewAlmacenProducto);
                    if (oldAlmacenOfAlmacenProductoCollectionNewAlmacenProducto != null && !oldAlmacenOfAlmacenProductoCollectionNewAlmacenProducto.equals(almacen)) {
                        oldAlmacenOfAlmacenProductoCollectionNewAlmacenProducto.getAlmacenProductoCollection().remove(almacenProductoCollectionNewAlmacenProducto);
                        oldAlmacenOfAlmacenProductoCollectionNewAlmacenProducto = em.merge(oldAlmacenOfAlmacenProductoCollectionNewAlmacenProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = almacen.getId();
                if (findAlmacen(id) == null) {
                    throw new NonexistentEntityException("The almacen with id " + id + " no longer exists.");
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
            Almacen almacen;
            try {
                almacen = em.getReference(Almacen.class, id);
                almacen.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The almacen with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollectionOrphanCheck = almacen.getMovimientoHistoricoProductoCollection();
            for (MovimientoHistoricoProducto movimientoHistoricoProductoCollectionOrphanCheckMovimientoHistoricoProducto : movimientoHistoricoProductoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Almacen (" + almacen + ") cannot be destroyed since the MovimientoHistoricoProducto " + movimientoHistoricoProductoCollectionOrphanCheckMovimientoHistoricoProducto + " in its movimientoHistoricoProductoCollection field has a non-nullable almacen field.");
            }
            Collection<AlmacenProducto> almacenProductoCollectionOrphanCheck = almacen.getAlmacenProductoCollection();
            for (AlmacenProducto almacenProductoCollectionOrphanCheckAlmacenProducto : almacenProductoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Almacen (" + almacen + ") cannot be destroyed since the AlmacenProducto " + almacenProductoCollectionOrphanCheckAlmacenProducto + " in its almacenProductoCollection field has a non-nullable almacen field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Poblacion poblacion = almacen.getPoblacion();
            if (poblacion != null) {
                poblacion.getAlmacenCollection().remove(almacen);
                poblacion = em.merge(poblacion);
            }
            em.remove(almacen);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Almacen> findAlmacenEntities() {
        return findAlmacenEntities(true, -1, -1);
    }

    public List<Almacen> findAlmacenEntities(int maxResults, int firstResult) {
        return findAlmacenEntities(false, maxResults, firstResult);
    }

    private List<Almacen> findAlmacenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Almacen as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Almacen findAlmacen(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Almacen.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlmacenCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Almacen as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
