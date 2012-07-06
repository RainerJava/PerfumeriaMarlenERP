package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.AlmacenProducto;
import com.pmarlen.model.beans.AlmacenProductoPK;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import com.pmarlen.model.controller.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.Almacen;
import com.pmarlen.model.beans.Producto;

/**
 * AlmacenProductoJpaController
 */

@Repository("almacenProductoJpaController")

public class AlmacenProductoJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AlmacenProducto almacenProducto) throws PreexistingEntityException, Exception {
        if (almacenProducto.getAlmacenProductoPK() == null) {
            almacenProducto.setAlmacenProductoPK(new AlmacenProductoPK());
        }
        almacenProducto.getAlmacenProductoPK().setAlmacenId(almacenProducto.getAlmacen().getId());
        almacenProducto.getAlmacenProductoPK().setProductoId(almacenProducto.getProducto().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Almacen almacen = almacenProducto.getAlmacen();
            if (almacen != null) {
                almacen = em.getReference(almacen.getClass(), almacen.getId());
                almacenProducto.setAlmacen(almacen);
            }
            Producto producto = almacenProducto.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getId());
                almacenProducto.setProducto(producto);
            }
            em.persist(almacenProducto);
            if (almacen != null) {
                almacen.getAlmacenProductoCollection().add(almacenProducto);
                almacen = em.merge(almacen);
            }
            if (producto != null) {
                producto.getAlmacenProductoCollection().add(almacenProducto);
                producto = em.merge(producto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAlmacenProducto(almacenProducto.getAlmacenProductoPK()) != null) {
                throw new PreexistingEntityException("AlmacenProducto " + almacenProducto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AlmacenProducto almacenProducto) throws NonexistentEntityException, Exception {
        almacenProducto.getAlmacenProductoPK().setAlmacenId(almacenProducto.getAlmacen().getId());
        almacenProducto.getAlmacenProductoPK().setProductoId(almacenProducto.getProducto().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AlmacenProducto persistentAlmacenProducto = em.find(AlmacenProducto.class, almacenProducto.getAlmacenProductoPK());
            Almacen almacenOld = persistentAlmacenProducto.getAlmacen();
            Almacen almacenNew = almacenProducto.getAlmacen();
            Producto productoOld = persistentAlmacenProducto.getProducto();
            Producto productoNew = almacenProducto.getProducto();
            if (almacenNew != null) {
                almacenNew = em.getReference(almacenNew.getClass(), almacenNew.getId());
                almacenProducto.setAlmacen(almacenNew);
            }
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getId());
                almacenProducto.setProducto(productoNew);
            }
            almacenProducto = em.merge(almacenProducto);
            if (almacenOld != null && !almacenOld.equals(almacenNew)) {
                almacenOld.getAlmacenProductoCollection().remove(almacenProducto);
                almacenOld = em.merge(almacenOld);
            }
            if (almacenNew != null && !almacenNew.equals(almacenOld)) {
                almacenNew.getAlmacenProductoCollection().add(almacenProducto);
                almacenNew = em.merge(almacenNew);
            }
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getAlmacenProductoCollection().remove(almacenProducto);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getAlmacenProductoCollection().add(almacenProducto);
                productoNew = em.merge(productoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                AlmacenProductoPK id = almacenProducto.getAlmacenProductoPK();
                if (findAlmacenProducto(id) == null) {
                    throw new NonexistentEntityException("The almacenProducto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(AlmacenProductoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AlmacenProducto almacenProducto;
            try {
                almacenProducto = em.getReference(AlmacenProducto.class, id);
                almacenProducto.getAlmacenProductoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The almacenProducto with id " + id + " no longer exists.", enfe);
            }
            Almacen almacen = almacenProducto.getAlmacen();
            if (almacen != null) {
                almacen.getAlmacenProductoCollection().remove(almacenProducto);
                almacen = em.merge(almacen);
            }
            Producto producto = almacenProducto.getProducto();
            if (producto != null) {
                producto.getAlmacenProductoCollection().remove(almacenProducto);
                producto = em.merge(producto);
            }
            em.remove(almacenProducto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AlmacenProducto> findAlmacenProductoEntities() {
        return findAlmacenProductoEntities(true, -1, -1);
    }

    public List<AlmacenProducto> findAlmacenProductoEntities(int maxResults, int firstResult) {
        return findAlmacenProductoEntities(false, maxResults, firstResult);
    }

    private List<AlmacenProducto> findAlmacenProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from AlmacenProducto as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public AlmacenProducto findAlmacenProducto(AlmacenProductoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AlmacenProducto.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlmacenProductoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from AlmacenProducto as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
