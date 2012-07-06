package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.ProveedorProducto;
import com.pmarlen.model.beans.ProveedorProductoPK;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import com.pmarlen.model.controller.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.Proveedor;
import com.pmarlen.model.beans.Producto;

/**
 * ProveedorProductoJpaController
 */

@Repository("proveedorProductoJpaController")

public class ProveedorProductoJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProveedorProducto proveedorProducto) throws PreexistingEntityException, Exception {
        if (proveedorProducto.getProveedorProductoPK() == null) {
            proveedorProducto.setProveedorProductoPK(new ProveedorProductoPK());
        }
        proveedorProducto.getProveedorProductoPK().setProductoId(proveedorProducto.getProducto().getId());
        proveedorProducto.getProveedorProductoPK().setProveedorId(proveedorProducto.getProveedor().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor proveedor = proveedorProducto.getProveedor();
            if (proveedor != null) {
                proveedor = em.getReference(proveedor.getClass(), proveedor.getId());
                proveedorProducto.setProveedor(proveedor);
            }
            Producto producto = proveedorProducto.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getId());
                proveedorProducto.setProducto(producto);
            }
            em.persist(proveedorProducto);
            if (proveedor != null) {
                proveedor.getProveedorProductoCollection().add(proveedorProducto);
                proveedor = em.merge(proveedor);
            }
            if (producto != null) {
                producto.getProveedorProductoCollection().add(proveedorProducto);
                producto = em.merge(producto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProveedorProducto(proveedorProducto.getProveedorProductoPK()) != null) {
                throw new PreexistingEntityException("ProveedorProducto " + proveedorProducto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProveedorProducto proveedorProducto) throws NonexistentEntityException, Exception {
        proveedorProducto.getProveedorProductoPK().setProductoId(proveedorProducto.getProducto().getId());
        proveedorProducto.getProveedorProductoPK().setProveedorId(proveedorProducto.getProveedor().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProveedorProducto persistentProveedorProducto = em.find(ProveedorProducto.class, proveedorProducto.getProveedorProductoPK());
            Proveedor proveedorOld = persistentProveedorProducto.getProveedor();
            Proveedor proveedorNew = proveedorProducto.getProveedor();
            Producto productoOld = persistentProveedorProducto.getProducto();
            Producto productoNew = proveedorProducto.getProducto();
            if (proveedorNew != null) {
                proveedorNew = em.getReference(proveedorNew.getClass(), proveedorNew.getId());
                proveedorProducto.setProveedor(proveedorNew);
            }
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getId());
                proveedorProducto.setProducto(productoNew);
            }
            proveedorProducto = em.merge(proveedorProducto);
            if (proveedorOld != null && !proveedorOld.equals(proveedorNew)) {
                proveedorOld.getProveedorProductoCollection().remove(proveedorProducto);
                proveedorOld = em.merge(proveedorOld);
            }
            if (proveedorNew != null && !proveedorNew.equals(proveedorOld)) {
                proveedorNew.getProveedorProductoCollection().add(proveedorProducto);
                proveedorNew = em.merge(proveedorNew);
            }
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getProveedorProductoCollection().remove(proveedorProducto);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getProveedorProductoCollection().add(proveedorProducto);
                productoNew = em.merge(productoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ProveedorProductoPK id = proveedorProducto.getProveedorProductoPK();
                if (findProveedorProducto(id) == null) {
                    throw new NonexistentEntityException("The proveedorProducto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ProveedorProductoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProveedorProducto proveedorProducto;
            try {
                proveedorProducto = em.getReference(ProveedorProducto.class, id);
                proveedorProducto.getProveedorProductoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedorProducto with id " + id + " no longer exists.", enfe);
            }
            Proveedor proveedor = proveedorProducto.getProveedor();
            if (proveedor != null) {
                proveedor.getProveedorProductoCollection().remove(proveedorProducto);
                proveedor = em.merge(proveedor);
            }
            Producto producto = proveedorProducto.getProducto();
            if (producto != null) {
                producto.getProveedorProductoCollection().remove(proveedorProducto);
                producto = em.merge(producto);
            }
            em.remove(proveedorProducto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProveedorProducto> findProveedorProductoEntities() {
        return findProveedorProductoEntities(true, -1, -1);
    }

    public List<ProveedorProducto> findProveedorProductoEntities(int maxResults, int firstResult) {
        return findProveedorProductoEntities(false, maxResults, firstResult);
    }

    private List<ProveedorProducto> findProveedorProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from ProveedorProducto as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ProveedorProducto findProveedorProducto(ProveedorProductoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProveedorProducto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProveedorProductoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from ProveedorProducto as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
