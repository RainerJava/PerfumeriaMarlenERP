package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.CodigoDeBarras;
import com.pmarlen.model.beans.CodigoDeBarrasPK;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import com.pmarlen.model.controller.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.Producto;

/**
 * CodigoDeBarrasJpaController
 */

@Repository("codigoDeBarrasJpaController")

public class CodigoDeBarrasJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CodigoDeBarras codigoDeBarras) throws PreexistingEntityException, Exception {
        if (codigoDeBarras.getCodigoDeBarrasPK() == null) {
            codigoDeBarras.setCodigoDeBarrasPK(new CodigoDeBarrasPK());
        }
        codigoDeBarras.getCodigoDeBarrasPK().setProductoId(codigoDeBarras.getProducto().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto producto = codigoDeBarras.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getId());
                codigoDeBarras.setProducto(producto);
            }
            em.persist(codigoDeBarras);
            if (producto != null) {
                producto.getCodigoDeBarrasCollection().add(codigoDeBarras);
                producto = em.merge(producto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCodigoDeBarras(codigoDeBarras.getCodigoDeBarrasPK()) != null) {
                throw new PreexistingEntityException("CodigoDeBarras " + codigoDeBarras + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CodigoDeBarras codigoDeBarras) throws NonexistentEntityException, Exception {
        codigoDeBarras.getCodigoDeBarrasPK().setProductoId(codigoDeBarras.getProducto().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CodigoDeBarras persistentCodigoDeBarras = em.find(CodigoDeBarras.class, codigoDeBarras.getCodigoDeBarrasPK());
            Producto productoOld = persistentCodigoDeBarras.getProducto();
            Producto productoNew = codigoDeBarras.getProducto();
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getId());
                codigoDeBarras.setProducto(productoNew);
            }
            codigoDeBarras = em.merge(codigoDeBarras);
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getCodigoDeBarrasCollection().remove(codigoDeBarras);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getCodigoDeBarrasCollection().add(codigoDeBarras);
                productoNew = em.merge(productoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CodigoDeBarrasPK id = codigoDeBarras.getCodigoDeBarrasPK();
                if (findCodigoDeBarras(id) == null) {
                    throw new NonexistentEntityException("The codigoDeBarras with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CodigoDeBarrasPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CodigoDeBarras codigoDeBarras;
            try {
                codigoDeBarras = em.getReference(CodigoDeBarras.class, id);
                codigoDeBarras.getCodigoDeBarrasPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The codigoDeBarras with id " + id + " no longer exists.", enfe);
            }
            Producto producto = codigoDeBarras.getProducto();
            if (producto != null) {
                producto.getCodigoDeBarrasCollection().remove(codigoDeBarras);
                producto = em.merge(producto);
            }
            em.remove(codigoDeBarras);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CodigoDeBarras> findCodigoDeBarrasEntities() {
        return findCodigoDeBarrasEntities(true, -1, -1);
    }

    public List<CodigoDeBarras> findCodigoDeBarrasEntities(int maxResults, int firstResult) {
        return findCodigoDeBarrasEntities(false, maxResults, firstResult);
    }

    private List<CodigoDeBarras> findCodigoDeBarrasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from CodigoDeBarras as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CodigoDeBarras findCodigoDeBarras(CodigoDeBarrasPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CodigoDeBarras.class, id);
        } finally {
            em.close();
        }
    }

    public int getCodigoDeBarrasCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from CodigoDeBarras as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
