package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.FormaDePago;
import com.pmarlen.model.controller.exceptions.IllegalOrphanException;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.PedidoVenta;
import java.util.ArrayList;
import java.util.Collection;
import com.pmarlen.model.beans.PedidoCompra;

/**
 * FormaDePagoJpaController
 */

@Repository("formaDePagoJpaController")

public class FormaDePagoJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FormaDePago formaDePago) {
        if (formaDePago.getPedidoVentaCollection() == null) {
            formaDePago.setPedidoVentaCollection(new ArrayList<PedidoVenta>());
        }
        if (formaDePago.getPedidoCompraCollection() == null) {
            formaDePago.setPedidoCompraCollection(new ArrayList<PedidoCompra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<PedidoVenta> attachedPedidoVentaCollection = new ArrayList<PedidoVenta>();
            for (PedidoVenta pedidoVentaCollectionPedidoVentaToAttach : formaDePago.getPedidoVentaCollection()) {
                pedidoVentaCollectionPedidoVentaToAttach = em.getReference(pedidoVentaCollectionPedidoVentaToAttach.getClass(), pedidoVentaCollectionPedidoVentaToAttach.getId());
                attachedPedidoVentaCollection.add(pedidoVentaCollectionPedidoVentaToAttach);
            }
            formaDePago.setPedidoVentaCollection(attachedPedidoVentaCollection);
            Collection<PedidoCompra> attachedPedidoCompraCollection = new ArrayList<PedidoCompra>();
            for (PedidoCompra pedidoCompraCollectionPedidoCompraToAttach : formaDePago.getPedidoCompraCollection()) {
                pedidoCompraCollectionPedidoCompraToAttach = em.getReference(pedidoCompraCollectionPedidoCompraToAttach.getClass(), pedidoCompraCollectionPedidoCompraToAttach.getId());
                attachedPedidoCompraCollection.add(pedidoCompraCollectionPedidoCompraToAttach);
            }
            formaDePago.setPedidoCompraCollection(attachedPedidoCompraCollection);
            em.persist(formaDePago);
            for (PedidoVenta pedidoVentaCollectionPedidoVenta : formaDePago.getPedidoVentaCollection()) {
                FormaDePago oldFormaDePagoOfPedidoVentaCollectionPedidoVenta = pedidoVentaCollectionPedidoVenta.getFormaDePago();
                pedidoVentaCollectionPedidoVenta.setFormaDePago(formaDePago);
                pedidoVentaCollectionPedidoVenta = em.merge(pedidoVentaCollectionPedidoVenta);
                if (oldFormaDePagoOfPedidoVentaCollectionPedidoVenta != null) {
                    oldFormaDePagoOfPedidoVentaCollectionPedidoVenta.getPedidoVentaCollection().remove(pedidoVentaCollectionPedidoVenta);
                    oldFormaDePagoOfPedidoVentaCollectionPedidoVenta = em.merge(oldFormaDePagoOfPedidoVentaCollectionPedidoVenta);
                }
            }
            for (PedidoCompra pedidoCompraCollectionPedidoCompra : formaDePago.getPedidoCompraCollection()) {
                FormaDePago oldFormaDePagoOfPedidoCompraCollectionPedidoCompra = pedidoCompraCollectionPedidoCompra.getFormaDePago();
                pedidoCompraCollectionPedidoCompra.setFormaDePago(formaDePago);
                pedidoCompraCollectionPedidoCompra = em.merge(pedidoCompraCollectionPedidoCompra);
                if (oldFormaDePagoOfPedidoCompraCollectionPedidoCompra != null) {
                    oldFormaDePagoOfPedidoCompraCollectionPedidoCompra.getPedidoCompraCollection().remove(pedidoCompraCollectionPedidoCompra);
                    oldFormaDePagoOfPedidoCompraCollectionPedidoCompra = em.merge(oldFormaDePagoOfPedidoCompraCollectionPedidoCompra);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FormaDePago formaDePago) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FormaDePago persistentFormaDePago = em.find(FormaDePago.class, formaDePago.getId());
            Collection<PedidoVenta> pedidoVentaCollectionOld = persistentFormaDePago.getPedidoVentaCollection();
            Collection<PedidoVenta> pedidoVentaCollectionNew = formaDePago.getPedidoVentaCollection();
            Collection<PedidoCompra> pedidoCompraCollectionOld = persistentFormaDePago.getPedidoCompraCollection();
            Collection<PedidoCompra> pedidoCompraCollectionNew = formaDePago.getPedidoCompraCollection();
            List<String> illegalOrphanMessages = null;
            for (PedidoVenta pedidoVentaCollectionOldPedidoVenta : pedidoVentaCollectionOld) {
                if (!pedidoVentaCollectionNew.contains(pedidoVentaCollectionOldPedidoVenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PedidoVenta " + pedidoVentaCollectionOldPedidoVenta + " since its formaDePago field is not nullable.");
                }
            }
            for (PedidoCompra pedidoCompraCollectionOldPedidoCompra : pedidoCompraCollectionOld) {
                if (!pedidoCompraCollectionNew.contains(pedidoCompraCollectionOldPedidoCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PedidoCompra " + pedidoCompraCollectionOldPedidoCompra + " since its formaDePago field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<PedidoVenta> attachedPedidoVentaCollectionNew = new ArrayList<PedidoVenta>();
            for (PedidoVenta pedidoVentaCollectionNewPedidoVentaToAttach : pedidoVentaCollectionNew) {
                pedidoVentaCollectionNewPedidoVentaToAttach = em.getReference(pedidoVentaCollectionNewPedidoVentaToAttach.getClass(), pedidoVentaCollectionNewPedidoVentaToAttach.getId());
                attachedPedidoVentaCollectionNew.add(pedidoVentaCollectionNewPedidoVentaToAttach);
            }
            pedidoVentaCollectionNew = attachedPedidoVentaCollectionNew;
            formaDePago.setPedidoVentaCollection(pedidoVentaCollectionNew);
            Collection<PedidoCompra> attachedPedidoCompraCollectionNew = new ArrayList<PedidoCompra>();
            for (PedidoCompra pedidoCompraCollectionNewPedidoCompraToAttach : pedidoCompraCollectionNew) {
                pedidoCompraCollectionNewPedidoCompraToAttach = em.getReference(pedidoCompraCollectionNewPedidoCompraToAttach.getClass(), pedidoCompraCollectionNewPedidoCompraToAttach.getId());
                attachedPedidoCompraCollectionNew.add(pedidoCompraCollectionNewPedidoCompraToAttach);
            }
            pedidoCompraCollectionNew = attachedPedidoCompraCollectionNew;
            formaDePago.setPedidoCompraCollection(pedidoCompraCollectionNew);
            formaDePago = em.merge(formaDePago);
            for (PedidoVenta pedidoVentaCollectionNewPedidoVenta : pedidoVentaCollectionNew) {
                if (!pedidoVentaCollectionOld.contains(pedidoVentaCollectionNewPedidoVenta)) {
                    FormaDePago oldFormaDePagoOfPedidoVentaCollectionNewPedidoVenta = pedidoVentaCollectionNewPedidoVenta.getFormaDePago();
                    pedidoVentaCollectionNewPedidoVenta.setFormaDePago(formaDePago);
                    pedidoVentaCollectionNewPedidoVenta = em.merge(pedidoVentaCollectionNewPedidoVenta);
                    if (oldFormaDePagoOfPedidoVentaCollectionNewPedidoVenta != null && !oldFormaDePagoOfPedidoVentaCollectionNewPedidoVenta.equals(formaDePago)) {
                        oldFormaDePagoOfPedidoVentaCollectionNewPedidoVenta.getPedidoVentaCollection().remove(pedidoVentaCollectionNewPedidoVenta);
                        oldFormaDePagoOfPedidoVentaCollectionNewPedidoVenta = em.merge(oldFormaDePagoOfPedidoVentaCollectionNewPedidoVenta);
                    }
                }
            }
            for (PedidoCompra pedidoCompraCollectionNewPedidoCompra : pedidoCompraCollectionNew) {
                if (!pedidoCompraCollectionOld.contains(pedidoCompraCollectionNewPedidoCompra)) {
                    FormaDePago oldFormaDePagoOfPedidoCompraCollectionNewPedidoCompra = pedidoCompraCollectionNewPedidoCompra.getFormaDePago();
                    pedidoCompraCollectionNewPedidoCompra.setFormaDePago(formaDePago);
                    pedidoCompraCollectionNewPedidoCompra = em.merge(pedidoCompraCollectionNewPedidoCompra);
                    if (oldFormaDePagoOfPedidoCompraCollectionNewPedidoCompra != null && !oldFormaDePagoOfPedidoCompraCollectionNewPedidoCompra.equals(formaDePago)) {
                        oldFormaDePagoOfPedidoCompraCollectionNewPedidoCompra.getPedidoCompraCollection().remove(pedidoCompraCollectionNewPedidoCompra);
                        oldFormaDePagoOfPedidoCompraCollectionNewPedidoCompra = em.merge(oldFormaDePagoOfPedidoCompraCollectionNewPedidoCompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = formaDePago.getId();
                if (findFormaDePago(id) == null) {
                    throw new NonexistentEntityException("The formaDePago with id " + id + " no longer exists.");
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
            FormaDePago formaDePago;
            try {
                formaDePago = em.getReference(FormaDePago.class, id);
                formaDePago.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The formaDePago with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PedidoVenta> pedidoVentaCollectionOrphanCheck = formaDePago.getPedidoVentaCollection();
            for (PedidoVenta pedidoVentaCollectionOrphanCheckPedidoVenta : pedidoVentaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This FormaDePago (" + formaDePago + ") cannot be destroyed since the PedidoVenta " + pedidoVentaCollectionOrphanCheckPedidoVenta + " in its pedidoVentaCollection field has a non-nullable formaDePago field.");
            }
            Collection<PedidoCompra> pedidoCompraCollectionOrphanCheck = formaDePago.getPedidoCompraCollection();
            for (PedidoCompra pedidoCompraCollectionOrphanCheckPedidoCompra : pedidoCompraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This FormaDePago (" + formaDePago + ") cannot be destroyed since the PedidoCompra " + pedidoCompraCollectionOrphanCheckPedidoCompra + " in its pedidoCompraCollection field has a non-nullable formaDePago field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(formaDePago);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FormaDePago> findFormaDePagoEntities() {
        return findFormaDePagoEntities(true, -1, -1);
    }

    public List<FormaDePago> findFormaDePagoEntities(int maxResults, int firstResult) {
        return findFormaDePagoEntities(false, maxResults, firstResult);
    }

    private List<FormaDePago> findFormaDePagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from FormaDePago as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public FormaDePago findFormaDePago(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FormaDePago.class, id);
        } finally {
            em.close();
        }
    }

    public int getFormaDePagoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from FormaDePago as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
