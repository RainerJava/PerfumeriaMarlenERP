package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.Estado;
import com.pmarlen.model.controller.exceptions.IllegalOrphanException;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.PedidoCompraEstado;
import java.util.ArrayList;
import java.util.Collection;
import com.pmarlen.model.beans.PedidoVentaEstado;

/**
 * EstadoJpaController
 */

@Repository("estadoJpaController")

public class EstadoJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estado estado) {
        if (estado.getPedidoCompraEstadoCollection() == null) {
            estado.setPedidoCompraEstadoCollection(new ArrayList<PedidoCompraEstado>());
        }
        if (estado.getPedidoVentaEstadoCollection() == null) {
            estado.setPedidoVentaEstadoCollection(new ArrayList<PedidoVentaEstado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<PedidoCompraEstado> attachedPedidoCompraEstadoCollection = new ArrayList<PedidoCompraEstado>();
            for (PedidoCompraEstado pedidoCompraEstadoCollectionPedidoCompraEstadoToAttach : estado.getPedidoCompraEstadoCollection()) {
                pedidoCompraEstadoCollectionPedidoCompraEstadoToAttach = em.getReference(pedidoCompraEstadoCollectionPedidoCompraEstadoToAttach.getClass(), pedidoCompraEstadoCollectionPedidoCompraEstadoToAttach.getPedidoCompraEstadoPK());
                attachedPedidoCompraEstadoCollection.add(pedidoCompraEstadoCollectionPedidoCompraEstadoToAttach);
            }
            estado.setPedidoCompraEstadoCollection(attachedPedidoCompraEstadoCollection);
            Collection<PedidoVentaEstado> attachedPedidoVentaEstadoCollection = new ArrayList<PedidoVentaEstado>();
            for (PedidoVentaEstado pedidoVentaEstadoCollectionPedidoVentaEstadoToAttach : estado.getPedidoVentaEstadoCollection()) {
                pedidoVentaEstadoCollectionPedidoVentaEstadoToAttach = em.getReference(pedidoVentaEstadoCollectionPedidoVentaEstadoToAttach.getClass(), pedidoVentaEstadoCollectionPedidoVentaEstadoToAttach.getPedidoVentaEstadoPK());
                attachedPedidoVentaEstadoCollection.add(pedidoVentaEstadoCollectionPedidoVentaEstadoToAttach);
            }
            estado.setPedidoVentaEstadoCollection(attachedPedidoVentaEstadoCollection);
            em.persist(estado);
            for (PedidoCompraEstado pedidoCompraEstadoCollectionPedidoCompraEstado : estado.getPedidoCompraEstadoCollection()) {
                Estado oldEstadoOfPedidoCompraEstadoCollectionPedidoCompraEstado = pedidoCompraEstadoCollectionPedidoCompraEstado.getEstado();
                pedidoCompraEstadoCollectionPedidoCompraEstado.setEstado(estado);
                pedidoCompraEstadoCollectionPedidoCompraEstado = em.merge(pedidoCompraEstadoCollectionPedidoCompraEstado);
                if (oldEstadoOfPedidoCompraEstadoCollectionPedidoCompraEstado != null) {
                    oldEstadoOfPedidoCompraEstadoCollectionPedidoCompraEstado.getPedidoCompraEstadoCollection().remove(pedidoCompraEstadoCollectionPedidoCompraEstado);
                    oldEstadoOfPedidoCompraEstadoCollectionPedidoCompraEstado = em.merge(oldEstadoOfPedidoCompraEstadoCollectionPedidoCompraEstado);
                }
            }
            for (PedidoVentaEstado pedidoVentaEstadoCollectionPedidoVentaEstado : estado.getPedidoVentaEstadoCollection()) {
                Estado oldEstadoOfPedidoVentaEstadoCollectionPedidoVentaEstado = pedidoVentaEstadoCollectionPedidoVentaEstado.getEstado();
                pedidoVentaEstadoCollectionPedidoVentaEstado.setEstado(estado);
                pedidoVentaEstadoCollectionPedidoVentaEstado = em.merge(pedidoVentaEstadoCollectionPedidoVentaEstado);
                if (oldEstadoOfPedidoVentaEstadoCollectionPedidoVentaEstado != null) {
                    oldEstadoOfPedidoVentaEstadoCollectionPedidoVentaEstado.getPedidoVentaEstadoCollection().remove(pedidoVentaEstadoCollectionPedidoVentaEstado);
                    oldEstadoOfPedidoVentaEstadoCollectionPedidoVentaEstado = em.merge(oldEstadoOfPedidoVentaEstadoCollectionPedidoVentaEstado);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estado estado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado persistentEstado = em.find(Estado.class, estado.getId());
            Collection<PedidoCompraEstado> pedidoCompraEstadoCollectionOld = persistentEstado.getPedidoCompraEstadoCollection();
            Collection<PedidoCompraEstado> pedidoCompraEstadoCollectionNew = estado.getPedidoCompraEstadoCollection();
            Collection<PedidoVentaEstado> pedidoVentaEstadoCollectionOld = persistentEstado.getPedidoVentaEstadoCollection();
            Collection<PedidoVentaEstado> pedidoVentaEstadoCollectionNew = estado.getPedidoVentaEstadoCollection();
            List<String> illegalOrphanMessages = null;
            for (PedidoCompraEstado pedidoCompraEstadoCollectionOldPedidoCompraEstado : pedidoCompraEstadoCollectionOld) {
                if (!pedidoCompraEstadoCollectionNew.contains(pedidoCompraEstadoCollectionOldPedidoCompraEstado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PedidoCompraEstado " + pedidoCompraEstadoCollectionOldPedidoCompraEstado + " since its estado field is not nullable.");
                }
            }
            for (PedidoVentaEstado pedidoVentaEstadoCollectionOldPedidoVentaEstado : pedidoVentaEstadoCollectionOld) {
                if (!pedidoVentaEstadoCollectionNew.contains(pedidoVentaEstadoCollectionOldPedidoVentaEstado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PedidoVentaEstado " + pedidoVentaEstadoCollectionOldPedidoVentaEstado + " since its estado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<PedidoCompraEstado> attachedPedidoCompraEstadoCollectionNew = new ArrayList<PedidoCompraEstado>();
            for (PedidoCompraEstado pedidoCompraEstadoCollectionNewPedidoCompraEstadoToAttach : pedidoCompraEstadoCollectionNew) {
                pedidoCompraEstadoCollectionNewPedidoCompraEstadoToAttach = em.getReference(pedidoCompraEstadoCollectionNewPedidoCompraEstadoToAttach.getClass(), pedidoCompraEstadoCollectionNewPedidoCompraEstadoToAttach.getPedidoCompraEstadoPK());
                attachedPedidoCompraEstadoCollectionNew.add(pedidoCompraEstadoCollectionNewPedidoCompraEstadoToAttach);
            }
            pedidoCompraEstadoCollectionNew = attachedPedidoCompraEstadoCollectionNew;
            estado.setPedidoCompraEstadoCollection(pedidoCompraEstadoCollectionNew);
            Collection<PedidoVentaEstado> attachedPedidoVentaEstadoCollectionNew = new ArrayList<PedidoVentaEstado>();
            for (PedidoVentaEstado pedidoVentaEstadoCollectionNewPedidoVentaEstadoToAttach : pedidoVentaEstadoCollectionNew) {
                pedidoVentaEstadoCollectionNewPedidoVentaEstadoToAttach = em.getReference(pedidoVentaEstadoCollectionNewPedidoVentaEstadoToAttach.getClass(), pedidoVentaEstadoCollectionNewPedidoVentaEstadoToAttach.getPedidoVentaEstadoPK());
                attachedPedidoVentaEstadoCollectionNew.add(pedidoVentaEstadoCollectionNewPedidoVentaEstadoToAttach);
            }
            pedidoVentaEstadoCollectionNew = attachedPedidoVentaEstadoCollectionNew;
            estado.setPedidoVentaEstadoCollection(pedidoVentaEstadoCollectionNew);
            estado = em.merge(estado);
            for (PedidoCompraEstado pedidoCompraEstadoCollectionNewPedidoCompraEstado : pedidoCompraEstadoCollectionNew) {
                if (!pedidoCompraEstadoCollectionOld.contains(pedidoCompraEstadoCollectionNewPedidoCompraEstado)) {
                    Estado oldEstadoOfPedidoCompraEstadoCollectionNewPedidoCompraEstado = pedidoCompraEstadoCollectionNewPedidoCompraEstado.getEstado();
                    pedidoCompraEstadoCollectionNewPedidoCompraEstado.setEstado(estado);
                    pedidoCompraEstadoCollectionNewPedidoCompraEstado = em.merge(pedidoCompraEstadoCollectionNewPedidoCompraEstado);
                    if (oldEstadoOfPedidoCompraEstadoCollectionNewPedidoCompraEstado != null && !oldEstadoOfPedidoCompraEstadoCollectionNewPedidoCompraEstado.equals(estado)) {
                        oldEstadoOfPedidoCompraEstadoCollectionNewPedidoCompraEstado.getPedidoCompraEstadoCollection().remove(pedidoCompraEstadoCollectionNewPedidoCompraEstado);
                        oldEstadoOfPedidoCompraEstadoCollectionNewPedidoCompraEstado = em.merge(oldEstadoOfPedidoCompraEstadoCollectionNewPedidoCompraEstado);
                    }
                }
            }
            for (PedidoVentaEstado pedidoVentaEstadoCollectionNewPedidoVentaEstado : pedidoVentaEstadoCollectionNew) {
                if (!pedidoVentaEstadoCollectionOld.contains(pedidoVentaEstadoCollectionNewPedidoVentaEstado)) {
                    Estado oldEstadoOfPedidoVentaEstadoCollectionNewPedidoVentaEstado = pedidoVentaEstadoCollectionNewPedidoVentaEstado.getEstado();
                    pedidoVentaEstadoCollectionNewPedidoVentaEstado.setEstado(estado);
                    pedidoVentaEstadoCollectionNewPedidoVentaEstado = em.merge(pedidoVentaEstadoCollectionNewPedidoVentaEstado);
                    if (oldEstadoOfPedidoVentaEstadoCollectionNewPedidoVentaEstado != null && !oldEstadoOfPedidoVentaEstadoCollectionNewPedidoVentaEstado.equals(estado)) {
                        oldEstadoOfPedidoVentaEstadoCollectionNewPedidoVentaEstado.getPedidoVentaEstadoCollection().remove(pedidoVentaEstadoCollectionNewPedidoVentaEstado);
                        oldEstadoOfPedidoVentaEstadoCollectionNewPedidoVentaEstado = em.merge(oldEstadoOfPedidoVentaEstadoCollectionNewPedidoVentaEstado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estado.getId();
                if (findEstado(id) == null) {
                    throw new NonexistentEntityException("The estado with id " + id + " no longer exists.");
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
            Estado estado;
            try {
                estado = em.getReference(Estado.class, id);
                estado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PedidoCompraEstado> pedidoCompraEstadoCollectionOrphanCheck = estado.getPedidoCompraEstadoCollection();
            for (PedidoCompraEstado pedidoCompraEstadoCollectionOrphanCheckPedidoCompraEstado : pedidoCompraEstadoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estado (" + estado + ") cannot be destroyed since the PedidoCompraEstado " + pedidoCompraEstadoCollectionOrphanCheckPedidoCompraEstado + " in its pedidoCompraEstadoCollection field has a non-nullable estado field.");
            }
            Collection<PedidoVentaEstado> pedidoVentaEstadoCollectionOrphanCheck = estado.getPedidoVentaEstadoCollection();
            for (PedidoVentaEstado pedidoVentaEstadoCollectionOrphanCheckPedidoVentaEstado : pedidoVentaEstadoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estado (" + estado + ") cannot be destroyed since the PedidoVentaEstado " + pedidoVentaEstadoCollectionOrphanCheckPedidoVentaEstado + " in its pedidoVentaEstadoCollection field has a non-nullable estado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estado> findEstadoEntities() {
        return findEstadoEntities(true, -1, -1);
    }

    public List<Estado> findEstadoEntities(int maxResults, int firstResult) {
        return findEstadoEntities(false, maxResults, firstResult);
    }

    private List<Estado> findEstadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Estado as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Estado findEstado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Estado as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
