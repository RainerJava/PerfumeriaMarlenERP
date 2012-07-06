package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.MovimientoHistoricoProducto;
import com.pmarlen.model.beans.MovimientoHistoricoProductoPK;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import com.pmarlen.model.controller.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.TipoMovimiento;
import com.pmarlen.model.beans.Producto;
import com.pmarlen.model.beans.Almacen;
import com.pmarlen.model.beans.Usuario;

/**
 * MovimientoHistoricoProductoJpaController
 */

@Repository("movimientoHistoricoProductoJpaController")

public class MovimientoHistoricoProductoJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MovimientoHistoricoProducto movimientoHistoricoProducto) throws PreexistingEntityException, Exception {
        if (movimientoHistoricoProducto.getMovimientoHistoricoProductoPK() == null) {
            movimientoHistoricoProducto.setMovimientoHistoricoProductoPK(new MovimientoHistoricoProductoPK());
        }
        movimientoHistoricoProducto.getMovimientoHistoricoProductoPK().setTipoMovimientoId(movimientoHistoricoProducto.getTipoMovimiento().getId());
        movimientoHistoricoProducto.getMovimientoHistoricoProductoPK().setAlmacenId(movimientoHistoricoProducto.getAlmacen().getId());
        movimientoHistoricoProducto.getMovimientoHistoricoProductoPK().setProductoId(movimientoHistoricoProducto.getProducto().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoMovimiento tipoMovimiento = movimientoHistoricoProducto.getTipoMovimiento();
            if (tipoMovimiento != null) {
                tipoMovimiento = em.getReference(tipoMovimiento.getClass(), tipoMovimiento.getId());
                movimientoHistoricoProducto.setTipoMovimiento(tipoMovimiento);
            }
            Producto producto = movimientoHistoricoProducto.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getId());
                movimientoHistoricoProducto.setProducto(producto);
            }
            Almacen almacen = movimientoHistoricoProducto.getAlmacen();
            if (almacen != null) {
                almacen = em.getReference(almacen.getClass(), almacen.getId());
                movimientoHistoricoProducto.setAlmacen(almacen);
            }
            Usuario usuario = movimientoHistoricoProducto.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getUsuarioId());
                movimientoHistoricoProducto.setUsuario(usuario);
            }
            em.persist(movimientoHistoricoProducto);
            if (tipoMovimiento != null) {
                tipoMovimiento.getMovimientoHistoricoProductoCollection().add(movimientoHistoricoProducto);
                tipoMovimiento = em.merge(tipoMovimiento);
            }
            if (producto != null) {
                producto.getMovimientoHistoricoProductoCollection().add(movimientoHistoricoProducto);
                producto = em.merge(producto);
            }
            if (almacen != null) {
                almacen.getMovimientoHistoricoProductoCollection().add(movimientoHistoricoProducto);
                almacen = em.merge(almacen);
            }
            if (usuario != null) {
                usuario.getMovimientoHistoricoProductoCollection().add(movimientoHistoricoProducto);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMovimientoHistoricoProducto(movimientoHistoricoProducto.getMovimientoHistoricoProductoPK()) != null) {
                throw new PreexistingEntityException("MovimientoHistoricoProducto " + movimientoHistoricoProducto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MovimientoHistoricoProducto movimientoHistoricoProducto) throws NonexistentEntityException, Exception {
        movimientoHistoricoProducto.getMovimientoHistoricoProductoPK().setTipoMovimientoId(movimientoHistoricoProducto.getTipoMovimiento().getId());
        movimientoHistoricoProducto.getMovimientoHistoricoProductoPK().setAlmacenId(movimientoHistoricoProducto.getAlmacen().getId());
        movimientoHistoricoProducto.getMovimientoHistoricoProductoPK().setProductoId(movimientoHistoricoProducto.getProducto().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MovimientoHistoricoProducto persistentMovimientoHistoricoProducto = em.find(MovimientoHistoricoProducto.class, movimientoHistoricoProducto.getMovimientoHistoricoProductoPK());
            TipoMovimiento tipoMovimientoOld = persistentMovimientoHistoricoProducto.getTipoMovimiento();
            TipoMovimiento tipoMovimientoNew = movimientoHistoricoProducto.getTipoMovimiento();
            Producto productoOld = persistentMovimientoHistoricoProducto.getProducto();
            Producto productoNew = movimientoHistoricoProducto.getProducto();
            Almacen almacenOld = persistentMovimientoHistoricoProducto.getAlmacen();
            Almacen almacenNew = movimientoHistoricoProducto.getAlmacen();
            Usuario usuarioOld = persistentMovimientoHistoricoProducto.getUsuario();
            Usuario usuarioNew = movimientoHistoricoProducto.getUsuario();
            if (tipoMovimientoNew != null) {
                tipoMovimientoNew = em.getReference(tipoMovimientoNew.getClass(), tipoMovimientoNew.getId());
                movimientoHistoricoProducto.setTipoMovimiento(tipoMovimientoNew);
            }
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getId());
                movimientoHistoricoProducto.setProducto(productoNew);
            }
            if (almacenNew != null) {
                almacenNew = em.getReference(almacenNew.getClass(), almacenNew.getId());
                movimientoHistoricoProducto.setAlmacen(almacenNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getUsuarioId());
                movimientoHistoricoProducto.setUsuario(usuarioNew);
            }
            movimientoHistoricoProducto = em.merge(movimientoHistoricoProducto);
            if (tipoMovimientoOld != null && !tipoMovimientoOld.equals(tipoMovimientoNew)) {
                tipoMovimientoOld.getMovimientoHistoricoProductoCollection().remove(movimientoHistoricoProducto);
                tipoMovimientoOld = em.merge(tipoMovimientoOld);
            }
            if (tipoMovimientoNew != null && !tipoMovimientoNew.equals(tipoMovimientoOld)) {
                tipoMovimientoNew.getMovimientoHistoricoProductoCollection().add(movimientoHistoricoProducto);
                tipoMovimientoNew = em.merge(tipoMovimientoNew);
            }
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getMovimientoHistoricoProductoCollection().remove(movimientoHistoricoProducto);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getMovimientoHistoricoProductoCollection().add(movimientoHistoricoProducto);
                productoNew = em.merge(productoNew);
            }
            if (almacenOld != null && !almacenOld.equals(almacenNew)) {
                almacenOld.getMovimientoHistoricoProductoCollection().remove(movimientoHistoricoProducto);
                almacenOld = em.merge(almacenOld);
            }
            if (almacenNew != null && !almacenNew.equals(almacenOld)) {
                almacenNew.getMovimientoHistoricoProductoCollection().add(movimientoHistoricoProducto);
                almacenNew = em.merge(almacenNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getMovimientoHistoricoProductoCollection().remove(movimientoHistoricoProducto);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getMovimientoHistoricoProductoCollection().add(movimientoHistoricoProducto);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MovimientoHistoricoProductoPK id = movimientoHistoricoProducto.getMovimientoHistoricoProductoPK();
                if (findMovimientoHistoricoProducto(id) == null) {
                    throw new NonexistentEntityException("The movimientoHistoricoProducto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MovimientoHistoricoProductoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MovimientoHistoricoProducto movimientoHistoricoProducto;
            try {
                movimientoHistoricoProducto = em.getReference(MovimientoHistoricoProducto.class, id);
                movimientoHistoricoProducto.getMovimientoHistoricoProductoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The movimientoHistoricoProducto with id " + id + " no longer exists.", enfe);
            }
            TipoMovimiento tipoMovimiento = movimientoHistoricoProducto.getTipoMovimiento();
            if (tipoMovimiento != null) {
                tipoMovimiento.getMovimientoHistoricoProductoCollection().remove(movimientoHistoricoProducto);
                tipoMovimiento = em.merge(tipoMovimiento);
            }
            Producto producto = movimientoHistoricoProducto.getProducto();
            if (producto != null) {
                producto.getMovimientoHistoricoProductoCollection().remove(movimientoHistoricoProducto);
                producto = em.merge(producto);
            }
            Almacen almacen = movimientoHistoricoProducto.getAlmacen();
            if (almacen != null) {
                almacen.getMovimientoHistoricoProductoCollection().remove(movimientoHistoricoProducto);
                almacen = em.merge(almacen);
            }
            Usuario usuario = movimientoHistoricoProducto.getUsuario();
            if (usuario != null) {
                usuario.getMovimientoHistoricoProductoCollection().remove(movimientoHistoricoProducto);
                usuario = em.merge(usuario);
            }
            em.remove(movimientoHistoricoProducto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MovimientoHistoricoProducto> findMovimientoHistoricoProductoEntities() {
        return findMovimientoHistoricoProductoEntities(true, -1, -1);
    }

    public List<MovimientoHistoricoProducto> findMovimientoHistoricoProductoEntities(int maxResults, int firstResult) {
        return findMovimientoHistoricoProductoEntities(false, maxResults, firstResult);
    }

    private List<MovimientoHistoricoProducto> findMovimientoHistoricoProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from MovimientoHistoricoProducto as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public MovimientoHistoricoProducto findMovimientoHistoricoProducto(MovimientoHistoricoProductoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MovimientoHistoricoProducto.class, id);
        } finally {
            em.close();
        }
    }

    public int getMovimientoHistoricoProductoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from MovimientoHistoricoProducto as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
