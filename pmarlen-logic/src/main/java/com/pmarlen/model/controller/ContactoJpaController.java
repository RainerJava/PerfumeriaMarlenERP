package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.Contacto;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.Poblacion;
import com.pmarlen.model.beans.Proveedor;
import java.util.ArrayList;
import java.util.Collection;
import com.pmarlen.model.beans.Cliente;

/**
 * ContactoJpaController
 */

@Repository("contactoJpaController")

public class ContactoJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contacto contacto) {
        if (contacto.getProveedorCollection() == null) {
            contacto.setProveedorCollection(new ArrayList<Proveedor>());
        }
        if (contacto.getClienteCollection() == null) {
            contacto.setClienteCollection(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Poblacion poblacion = contacto.getPoblacion();
            if (poblacion != null) {
                poblacion = em.getReference(poblacion.getClass(), poblacion.getId());
                contacto.setPoblacion(poblacion);
            }
            Collection<Proveedor> attachedProveedorCollection = new ArrayList<Proveedor>();
            for (Proveedor proveedorCollectionProveedorToAttach : contacto.getProveedorCollection()) {
                proveedorCollectionProveedorToAttach = em.getReference(proveedorCollectionProveedorToAttach.getClass(), proveedorCollectionProveedorToAttach.getId());
                attachedProveedorCollection.add(proveedorCollectionProveedorToAttach);
            }
            contacto.setProveedorCollection(attachedProveedorCollection);
            Collection<Cliente> attachedClienteCollection = new ArrayList<Cliente>();
            for (Cliente clienteCollectionClienteToAttach : contacto.getClienteCollection()) {
                clienteCollectionClienteToAttach = em.getReference(clienteCollectionClienteToAttach.getClass(), clienteCollectionClienteToAttach.getId());
                attachedClienteCollection.add(clienteCollectionClienteToAttach);
            }
            contacto.setClienteCollection(attachedClienteCollection);
            em.persist(contacto);
            if (poblacion != null) {
                poblacion.getContactoCollection().add(contacto);
                poblacion = em.merge(poblacion);
            }
            for (Proveedor proveedorCollectionProveedor : contacto.getProveedorCollection()) {
                proveedorCollectionProveedor.getContactoCollection().add(contacto);
                proveedorCollectionProveedor = em.merge(proveedorCollectionProveedor);
            }
            for (Cliente clienteCollectionCliente : contacto.getClienteCollection()) {
                clienteCollectionCliente.getContactoCollection().add(contacto);
                clienteCollectionCliente = em.merge(clienteCollectionCliente);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contacto contacto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contacto persistentContacto = em.find(Contacto.class, contacto.getId());
            Poblacion poblacionOld = persistentContacto.getPoblacion();
            Poblacion poblacionNew = contacto.getPoblacion();
            Collection<Proveedor> proveedorCollectionOld = persistentContacto.getProveedorCollection();
            Collection<Proveedor> proveedorCollectionNew = contacto.getProveedorCollection();
            Collection<Cliente> clienteCollectionOld = persistentContacto.getClienteCollection();
            Collection<Cliente> clienteCollectionNew = contacto.getClienteCollection();
            if (poblacionNew != null) {
                poblacionNew = em.getReference(poblacionNew.getClass(), poblacionNew.getId());
                contacto.setPoblacion(poblacionNew);
            }
            Collection<Proveedor> attachedProveedorCollectionNew = new ArrayList<Proveedor>();
            for (Proveedor proveedorCollectionNewProveedorToAttach : proveedorCollectionNew) {
                proveedorCollectionNewProveedorToAttach = em.getReference(proveedorCollectionNewProveedorToAttach.getClass(), proveedorCollectionNewProveedorToAttach.getId());
                attachedProveedorCollectionNew.add(proveedorCollectionNewProveedorToAttach);
            }
            proveedorCollectionNew = attachedProveedorCollectionNew;
            contacto.setProveedorCollection(proveedorCollectionNew);
            Collection<Cliente> attachedClienteCollectionNew = new ArrayList<Cliente>();
            for (Cliente clienteCollectionNewClienteToAttach : clienteCollectionNew) {
                clienteCollectionNewClienteToAttach = em.getReference(clienteCollectionNewClienteToAttach.getClass(), clienteCollectionNewClienteToAttach.getId());
                attachedClienteCollectionNew.add(clienteCollectionNewClienteToAttach);
            }
            clienteCollectionNew = attachedClienteCollectionNew;
            contacto.setClienteCollection(clienteCollectionNew);
            contacto = em.merge(contacto);
            if (poblacionOld != null && !poblacionOld.equals(poblacionNew)) {
                poblacionOld.getContactoCollection().remove(contacto);
                poblacionOld = em.merge(poblacionOld);
            }
            if (poblacionNew != null && !poblacionNew.equals(poblacionOld)) {
                poblacionNew.getContactoCollection().add(contacto);
                poblacionNew = em.merge(poblacionNew);
            }
            for (Proveedor proveedorCollectionOldProveedor : proveedorCollectionOld) {
                if (!proveedorCollectionNew.contains(proveedorCollectionOldProveedor)) {
                    proveedorCollectionOldProveedor.getContactoCollection().remove(contacto);
                    proveedorCollectionOldProveedor = em.merge(proveedorCollectionOldProveedor);
                }
            }
            for (Proveedor proveedorCollectionNewProveedor : proveedorCollectionNew) {
                if (!proveedorCollectionOld.contains(proveedorCollectionNewProveedor)) {
                    proveedorCollectionNewProveedor.getContactoCollection().add(contacto);
                    proveedorCollectionNewProveedor = em.merge(proveedorCollectionNewProveedor);
                }
            }
            for (Cliente clienteCollectionOldCliente : clienteCollectionOld) {
                if (!clienteCollectionNew.contains(clienteCollectionOldCliente)) {
                    clienteCollectionOldCliente.getContactoCollection().remove(contacto);
                    clienteCollectionOldCliente = em.merge(clienteCollectionOldCliente);
                }
            }
            for (Cliente clienteCollectionNewCliente : clienteCollectionNew) {
                if (!clienteCollectionOld.contains(clienteCollectionNewCliente)) {
                    clienteCollectionNewCliente.getContactoCollection().add(contacto);
                    clienteCollectionNewCliente = em.merge(clienteCollectionNewCliente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contacto.getId();
                if (findContacto(id) == null) {
                    throw new NonexistentEntityException("The contacto with id " + id + " no longer exists.");
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
            Contacto contacto;
            try {
                contacto = em.getReference(Contacto.class, id);
                contacto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contacto with id " + id + " no longer exists.", enfe);
            }
            Poblacion poblacion = contacto.getPoblacion();
            if (poblacion != null) {
                poblacion.getContactoCollection().remove(contacto);
                poblacion = em.merge(poblacion);
            }
            Collection<Proveedor> proveedorCollection = contacto.getProveedorCollection();
            for (Proveedor proveedorCollectionProveedor : proveedorCollection) {
                proveedorCollectionProveedor.getContactoCollection().remove(contacto);
                proveedorCollectionProveedor = em.merge(proveedorCollectionProveedor);
            }
            Collection<Cliente> clienteCollection = contacto.getClienteCollection();
            for (Cliente clienteCollectionCliente : clienteCollection) {
                clienteCollectionCliente.getContactoCollection().remove(contacto);
                clienteCollectionCliente = em.merge(clienteCollectionCliente);
            }
            em.remove(contacto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Contacto> findContactoEntities() {
        return findContactoEntities(true, -1, -1);
    }

    public List<Contacto> findContactoEntities(int maxResults, int firstResult) {
        return findContactoEntities(false, maxResults, firstResult);
    }

    private List<Contacto> findContactoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Contacto as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Contacto findContacto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contacto.class, id);
        } finally {
            em.close();
        }
    }

    public int getContactoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Contacto as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
