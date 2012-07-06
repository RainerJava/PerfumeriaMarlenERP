package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.PedidoCompraEstado;
import com.pmarlen.model.beans.PedidoCompraEstadoPK;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import com.pmarlen.model.controller.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.Estado;
import com.pmarlen.model.beans.PedidoCompra;
import com.pmarlen.model.beans.Usuario;

/**
 * PedidoCompraEstadoJpaController
 */

@Repository("pedidoCompraEstadoJpaController")

public class PedidoCompraEstadoJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PedidoCompraEstado pedidoCompraEstado) throws PreexistingEntityException, Exception {
        if (pedidoCompraEstado.getPedidoCompraEstadoPK() == null) {
            pedidoCompraEstado.setPedidoCompraEstadoPK(new PedidoCompraEstadoPK());
        }
        pedidoCompraEstado.getPedidoCompraEstadoPK().setEstadoId(pedidoCompraEstado.getEstado().getId());
        pedidoCompraEstado.getPedidoCompraEstadoPK().setPedidoCompraId(pedidoCompraEstado.getPedidoCompra().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado estado = pedidoCompraEstado.getEstado();
            if (estado != null) {
                estado = em.getReference(estado.getClass(), estado.getId());
                pedidoCompraEstado.setEstado(estado);
            }
            PedidoCompra pedidoCompra = pedidoCompraEstado.getPedidoCompra();
            if (pedidoCompra != null) {
                pedidoCompra = em.getReference(pedidoCompra.getClass(), pedidoCompra.getId());
                pedidoCompraEstado.setPedidoCompra(pedidoCompra);
            }
            Usuario usuario = pedidoCompraEstado.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getUsuarioId());
                pedidoCompraEstado.setUsuario(usuario);
            }
            em.persist(pedidoCompraEstado);
            if (estado != null) {
                estado.getPedidoCompraEstadoCollection().add(pedidoCompraEstado);
                estado = em.merge(estado);
            }
            if (pedidoCompra != null) {
                pedidoCompra.getPedidoCompraEstadoCollection().add(pedidoCompraEstado);
                pedidoCompra = em.merge(pedidoCompra);
            }
            if (usuario != null) {
                usuario.getPedidoCompraEstadoCollection().add(pedidoCompraEstado);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPedidoCompraEstado(pedidoCompraEstado.getPedidoCompraEstadoPK()) != null) {
                throw new PreexistingEntityException("PedidoCompraEstado " + pedidoCompraEstado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PedidoCompraEstado pedidoCompraEstado) throws NonexistentEntityException, Exception {
        pedidoCompraEstado.getPedidoCompraEstadoPK().setEstadoId(pedidoCompraEstado.getEstado().getId());
        pedidoCompraEstado.getPedidoCompraEstadoPK().setPedidoCompraId(pedidoCompraEstado.getPedidoCompra().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PedidoCompraEstado persistentPedidoCompraEstado = em.find(PedidoCompraEstado.class, pedidoCompraEstado.getPedidoCompraEstadoPK());
            Estado estadoOld = persistentPedidoCompraEstado.getEstado();
            Estado estadoNew = pedidoCompraEstado.getEstado();
            PedidoCompra pedidoCompraOld = persistentPedidoCompraEstado.getPedidoCompra();
            PedidoCompra pedidoCompraNew = pedidoCompraEstado.getPedidoCompra();
            Usuario usuarioOld = persistentPedidoCompraEstado.getUsuario();
            Usuario usuarioNew = pedidoCompraEstado.getUsuario();
            if (estadoNew != null) {
                estadoNew = em.getReference(estadoNew.getClass(), estadoNew.getId());
                pedidoCompraEstado.setEstado(estadoNew);
            }
            if (pedidoCompraNew != null) {
                pedidoCompraNew = em.getReference(pedidoCompraNew.getClass(), pedidoCompraNew.getId());
                pedidoCompraEstado.setPedidoCompra(pedidoCompraNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getUsuarioId());
                pedidoCompraEstado.setUsuario(usuarioNew);
            }
            pedidoCompraEstado = em.merge(pedidoCompraEstado);
            if (estadoOld != null && !estadoOld.equals(estadoNew)) {
                estadoOld.getPedidoCompraEstadoCollection().remove(pedidoCompraEstado);
                estadoOld = em.merge(estadoOld);
            }
            if (estadoNew != null && !estadoNew.equals(estadoOld)) {
                estadoNew.getPedidoCompraEstadoCollection().add(pedidoCompraEstado);
                estadoNew = em.merge(estadoNew);
            }
            if (pedidoCompraOld != null && !pedidoCompraOld.equals(pedidoCompraNew)) {
                pedidoCompraOld.getPedidoCompraEstadoCollection().remove(pedidoCompraEstado);
                pedidoCompraOld = em.merge(pedidoCompraOld);
            }
            if (pedidoCompraNew != null && !pedidoCompraNew.equals(pedidoCompraOld)) {
                pedidoCompraNew.getPedidoCompraEstadoCollection().add(pedidoCompraEstado);
                pedidoCompraNew = em.merge(pedidoCompraNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getPedidoCompraEstadoCollection().remove(pedidoCompraEstado);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getPedidoCompraEstadoCollection().add(pedidoCompraEstado);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PedidoCompraEstadoPK id = pedidoCompraEstado.getPedidoCompraEstadoPK();
                if (findPedidoCompraEstado(id) == null) {
                    throw new NonexistentEntityException("The pedidoCompraEstado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PedidoCompraEstadoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PedidoCompraEstado pedidoCompraEstado;
            try {
                pedidoCompraEstado = em.getReference(PedidoCompraEstado.class, id);
                pedidoCompraEstado.getPedidoCompraEstadoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedidoCompraEstado with id " + id + " no longer exists.", enfe);
            }
            Estado estado = pedidoCompraEstado.getEstado();
            if (estado != null) {
                estado.getPedidoCompraEstadoCollection().remove(pedidoCompraEstado);
                estado = em.merge(estado);
            }
            PedidoCompra pedidoCompra = pedidoCompraEstado.getPedidoCompra();
            if (pedidoCompra != null) {
                pedidoCompra.getPedidoCompraEstadoCollection().remove(pedidoCompraEstado);
                pedidoCompra = em.merge(pedidoCompra);
            }
            Usuario usuario = pedidoCompraEstado.getUsuario();
            if (usuario != null) {
                usuario.getPedidoCompraEstadoCollection().remove(pedidoCompraEstado);
                usuario = em.merge(usuario);
            }
            em.remove(pedidoCompraEstado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PedidoCompraEstado> findPedidoCompraEstadoEntities() {
        return findPedidoCompraEstadoEntities(true, -1, -1);
    }

    public List<PedidoCompraEstado> findPedidoCompraEstadoEntities(int maxResults, int firstResult) {
        return findPedidoCompraEstadoEntities(false, maxResults, firstResult);
    }

    private List<PedidoCompraEstado> findPedidoCompraEstadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from PedidoCompraEstado as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public PedidoCompraEstado findPedidoCompraEstado(PedidoCompraEstadoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PedidoCompraEstado.class, id);
        } finally {
            em.close();
        }
    }

    public int getPedidoCompraEstadoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from PedidoCompraEstado as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
