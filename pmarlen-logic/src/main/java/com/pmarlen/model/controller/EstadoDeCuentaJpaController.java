package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.EstadoDeCuenta;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import com.pmarlen.model.controller.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.EstadoDeCuentaClientes;
import com.pmarlen.model.beans.EstadoDeCuentaProveedores;
import com.pmarlen.model.beans.Usuario;

/**
 * EstadoDeCuentaJpaController
 */

@Repository("estadoDeCuentaJpaController")

public class EstadoDeCuentaJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoDeCuenta estadoDeCuenta) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoDeCuentaClientes estadoDeCuentaClientes = estadoDeCuenta.getEstadoDeCuentaClientes();
            if (estadoDeCuentaClientes != null) {
                estadoDeCuentaClientes = em.getReference(estadoDeCuentaClientes.getClass(), estadoDeCuentaClientes.getId());
                estadoDeCuenta.setEstadoDeCuentaClientes(estadoDeCuentaClientes);
            }
            EstadoDeCuentaProveedores estadoDeCuentaProveedores = estadoDeCuenta.getEstadoDeCuentaProveedores();
            if (estadoDeCuentaProveedores != null) {
                estadoDeCuentaProveedores = em.getReference(estadoDeCuentaProveedores.getClass(), estadoDeCuentaProveedores.getId());
                estadoDeCuenta.setEstadoDeCuentaProveedores(estadoDeCuentaProveedores);
            }
            Usuario usuario = estadoDeCuenta.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getUsuarioId());
                estadoDeCuenta.setUsuario(usuario);
            }
            em.persist(estadoDeCuenta);
            if (estadoDeCuentaClientes != null) {
                estadoDeCuentaClientes.getEstadoDeCuentaCollection().add(estadoDeCuenta);
                estadoDeCuentaClientes = em.merge(estadoDeCuentaClientes);
            }
            if (estadoDeCuentaProveedores != null) {
                estadoDeCuentaProveedores.getEstadoDeCuentaCollection().add(estadoDeCuenta);
                estadoDeCuentaProveedores = em.merge(estadoDeCuentaProveedores);
            }
            if (usuario != null) {
                usuario.getEstadoDeCuentaCollection().add(estadoDeCuenta);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstadoDeCuenta(estadoDeCuenta.getId()) != null) {
                throw new PreexistingEntityException("EstadoDeCuenta " + estadoDeCuenta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoDeCuenta estadoDeCuenta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoDeCuenta persistentEstadoDeCuenta = em.find(EstadoDeCuenta.class, estadoDeCuenta.getId());
            EstadoDeCuentaClientes estadoDeCuentaClientesOld = persistentEstadoDeCuenta.getEstadoDeCuentaClientes();
            EstadoDeCuentaClientes estadoDeCuentaClientesNew = estadoDeCuenta.getEstadoDeCuentaClientes();
            EstadoDeCuentaProveedores estadoDeCuentaProveedoresOld = persistentEstadoDeCuenta.getEstadoDeCuentaProveedores();
            EstadoDeCuentaProveedores estadoDeCuentaProveedoresNew = estadoDeCuenta.getEstadoDeCuentaProveedores();
            Usuario usuarioOld = persistentEstadoDeCuenta.getUsuario();
            Usuario usuarioNew = estadoDeCuenta.getUsuario();
            if (estadoDeCuentaClientesNew != null) {
                estadoDeCuentaClientesNew = em.getReference(estadoDeCuentaClientesNew.getClass(), estadoDeCuentaClientesNew.getId());
                estadoDeCuenta.setEstadoDeCuentaClientes(estadoDeCuentaClientesNew);
            }
            if (estadoDeCuentaProveedoresNew != null) {
                estadoDeCuentaProveedoresNew = em.getReference(estadoDeCuentaProveedoresNew.getClass(), estadoDeCuentaProveedoresNew.getId());
                estadoDeCuenta.setEstadoDeCuentaProveedores(estadoDeCuentaProveedoresNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getUsuarioId());
                estadoDeCuenta.setUsuario(usuarioNew);
            }
            estadoDeCuenta = em.merge(estadoDeCuenta);
            if (estadoDeCuentaClientesOld != null && !estadoDeCuentaClientesOld.equals(estadoDeCuentaClientesNew)) {
                estadoDeCuentaClientesOld.getEstadoDeCuentaCollection().remove(estadoDeCuenta);
                estadoDeCuentaClientesOld = em.merge(estadoDeCuentaClientesOld);
            }
            if (estadoDeCuentaClientesNew != null && !estadoDeCuentaClientesNew.equals(estadoDeCuentaClientesOld)) {
                estadoDeCuentaClientesNew.getEstadoDeCuentaCollection().add(estadoDeCuenta);
                estadoDeCuentaClientesNew = em.merge(estadoDeCuentaClientesNew);
            }
            if (estadoDeCuentaProveedoresOld != null && !estadoDeCuentaProveedoresOld.equals(estadoDeCuentaProveedoresNew)) {
                estadoDeCuentaProveedoresOld.getEstadoDeCuentaCollection().remove(estadoDeCuenta);
                estadoDeCuentaProveedoresOld = em.merge(estadoDeCuentaProveedoresOld);
            }
            if (estadoDeCuentaProveedoresNew != null && !estadoDeCuentaProveedoresNew.equals(estadoDeCuentaProveedoresOld)) {
                estadoDeCuentaProveedoresNew.getEstadoDeCuentaCollection().add(estadoDeCuenta);
                estadoDeCuentaProveedoresNew = em.merge(estadoDeCuentaProveedoresNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getEstadoDeCuentaCollection().remove(estadoDeCuenta);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getEstadoDeCuentaCollection().add(estadoDeCuenta);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estadoDeCuenta.getId();
                if (findEstadoDeCuenta(id) == null) {
                    throw new NonexistentEntityException("The estadoDeCuenta with id " + id + " no longer exists.");
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
            EstadoDeCuenta estadoDeCuenta;
            try {
                estadoDeCuenta = em.getReference(EstadoDeCuenta.class, id);
                estadoDeCuenta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoDeCuenta with id " + id + " no longer exists.", enfe);
            }
            EstadoDeCuentaClientes estadoDeCuentaClientes = estadoDeCuenta.getEstadoDeCuentaClientes();
            if (estadoDeCuentaClientes != null) {
                estadoDeCuentaClientes.getEstadoDeCuentaCollection().remove(estadoDeCuenta);
                estadoDeCuentaClientes = em.merge(estadoDeCuentaClientes);
            }
            EstadoDeCuentaProveedores estadoDeCuentaProveedores = estadoDeCuenta.getEstadoDeCuentaProveedores();
            if (estadoDeCuentaProveedores != null) {
                estadoDeCuentaProveedores.getEstadoDeCuentaCollection().remove(estadoDeCuenta);
                estadoDeCuentaProveedores = em.merge(estadoDeCuentaProveedores);
            }
            Usuario usuario = estadoDeCuenta.getUsuario();
            if (usuario != null) {
                usuario.getEstadoDeCuentaCollection().remove(estadoDeCuenta);
                usuario = em.merge(usuario);
            }
            em.remove(estadoDeCuenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoDeCuenta> findEstadoDeCuentaEntities() {
        return findEstadoDeCuentaEntities(true, -1, -1);
    }

    public List<EstadoDeCuenta> findEstadoDeCuentaEntities(int maxResults, int firstResult) {
        return findEstadoDeCuentaEntities(false, maxResults, firstResult);
    }

    private List<EstadoDeCuenta> findEstadoDeCuentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from EstadoDeCuenta as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public EstadoDeCuenta findEstadoDeCuenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoDeCuenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoDeCuentaCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from EstadoDeCuenta as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
