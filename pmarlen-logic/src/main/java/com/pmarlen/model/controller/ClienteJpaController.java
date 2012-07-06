package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.Cliente;
import com.pmarlen.model.controller.exceptions.IllegalOrphanException;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.Poblacion;
import com.pmarlen.model.beans.CuentaBancaria;
import java.util.ArrayList;
import java.util.Collection;
import com.pmarlen.model.beans.Contacto;
import com.pmarlen.model.beans.PedidoVenta;

/**
 * ClienteJpaController
 */

@Repository("clienteJpaController")

public class ClienteJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getCuentaBancariaCollection() == null) {
            cliente.setCuentaBancariaCollection(new ArrayList<CuentaBancaria>());
        }
        if (cliente.getContactoCollection() == null) {
            cliente.setContactoCollection(new ArrayList<Contacto>());
        }
        if (cliente.getPedidoVentaCollection() == null) {
            cliente.setPedidoVentaCollection(new ArrayList<PedidoVenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Poblacion poblacion = cliente.getPoblacion();
            if (poblacion != null) {
                poblacion = em.getReference(poblacion.getClass(), poblacion.getId());
                cliente.setPoblacion(poblacion);
            }
            Collection<CuentaBancaria> attachedCuentaBancariaCollection = new ArrayList<CuentaBancaria>();
            for (CuentaBancaria cuentaBancariaCollectionCuentaBancariaToAttach : cliente.getCuentaBancariaCollection()) {
                cuentaBancariaCollectionCuentaBancariaToAttach = em.getReference(cuentaBancariaCollectionCuentaBancariaToAttach.getClass(), cuentaBancariaCollectionCuentaBancariaToAttach.getId());
                attachedCuentaBancariaCollection.add(cuentaBancariaCollectionCuentaBancariaToAttach);
            }
            cliente.setCuentaBancariaCollection(attachedCuentaBancariaCollection);
            Collection<Contacto> attachedContactoCollection = new ArrayList<Contacto>();
            for (Contacto contactoCollectionContactoToAttach : cliente.getContactoCollection()) {
                contactoCollectionContactoToAttach = em.getReference(contactoCollectionContactoToAttach.getClass(), contactoCollectionContactoToAttach.getId());
                attachedContactoCollection.add(contactoCollectionContactoToAttach);
            }
            cliente.setContactoCollection(attachedContactoCollection);
            Collection<PedidoVenta> attachedPedidoVentaCollection = new ArrayList<PedidoVenta>();
            for (PedidoVenta pedidoVentaCollectionPedidoVentaToAttach : cliente.getPedidoVentaCollection()) {
                pedidoVentaCollectionPedidoVentaToAttach = em.getReference(pedidoVentaCollectionPedidoVentaToAttach.getClass(), pedidoVentaCollectionPedidoVentaToAttach.getId());
                attachedPedidoVentaCollection.add(pedidoVentaCollectionPedidoVentaToAttach);
            }
            cliente.setPedidoVentaCollection(attachedPedidoVentaCollection);
            em.persist(cliente);
            if (poblacion != null) {
                poblacion.getClienteCollection().add(cliente);
                poblacion = em.merge(poblacion);
            }
            for (CuentaBancaria cuentaBancariaCollectionCuentaBancaria : cliente.getCuentaBancariaCollection()) {
                cuentaBancariaCollectionCuentaBancaria.getClienteCollection().add(cliente);
                cuentaBancariaCollectionCuentaBancaria = em.merge(cuentaBancariaCollectionCuentaBancaria);
            }
            for (Contacto contactoCollectionContacto : cliente.getContactoCollection()) {
                contactoCollectionContacto.getClienteCollection().add(cliente);
                contactoCollectionContacto = em.merge(contactoCollectionContacto);
            }
            for (PedidoVenta pedidoVentaCollectionPedidoVenta : cliente.getPedidoVentaCollection()) {
                Cliente oldClienteOfPedidoVentaCollectionPedidoVenta = pedidoVentaCollectionPedidoVenta.getCliente();
                pedidoVentaCollectionPedidoVenta.setCliente(cliente);
                pedidoVentaCollectionPedidoVenta = em.merge(pedidoVentaCollectionPedidoVenta);
                if (oldClienteOfPedidoVentaCollectionPedidoVenta != null) {
                    oldClienteOfPedidoVentaCollectionPedidoVenta.getPedidoVentaCollection().remove(pedidoVentaCollectionPedidoVenta);
                    oldClienteOfPedidoVentaCollectionPedidoVenta = em.merge(oldClienteOfPedidoVentaCollectionPedidoVenta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getId());
            Poblacion poblacionOld = persistentCliente.getPoblacion();
            Poblacion poblacionNew = cliente.getPoblacion();
            Collection<CuentaBancaria> cuentaBancariaCollectionOld = persistentCliente.getCuentaBancariaCollection();
            Collection<CuentaBancaria> cuentaBancariaCollectionNew = cliente.getCuentaBancariaCollection();
            Collection<Contacto> contactoCollectionOld = persistentCliente.getContactoCollection();
            Collection<Contacto> contactoCollectionNew = cliente.getContactoCollection();
            Collection<PedidoVenta> pedidoVentaCollectionOld = persistentCliente.getPedidoVentaCollection();
            Collection<PedidoVenta> pedidoVentaCollectionNew = cliente.getPedidoVentaCollection();
            List<String> illegalOrphanMessages = null;
            for (PedidoVenta pedidoVentaCollectionOldPedidoVenta : pedidoVentaCollectionOld) {
                if (!pedidoVentaCollectionNew.contains(pedidoVentaCollectionOldPedidoVenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PedidoVenta " + pedidoVentaCollectionOldPedidoVenta + " since its cliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (poblacionNew != null) {
                poblacionNew = em.getReference(poblacionNew.getClass(), poblacionNew.getId());
                cliente.setPoblacion(poblacionNew);
            }
            Collection<CuentaBancaria> attachedCuentaBancariaCollectionNew = new ArrayList<CuentaBancaria>();
            for (CuentaBancaria cuentaBancariaCollectionNewCuentaBancariaToAttach : cuentaBancariaCollectionNew) {
                cuentaBancariaCollectionNewCuentaBancariaToAttach = em.getReference(cuentaBancariaCollectionNewCuentaBancariaToAttach.getClass(), cuentaBancariaCollectionNewCuentaBancariaToAttach.getId());
                attachedCuentaBancariaCollectionNew.add(cuentaBancariaCollectionNewCuentaBancariaToAttach);
            }
            cuentaBancariaCollectionNew = attachedCuentaBancariaCollectionNew;
            cliente.setCuentaBancariaCollection(cuentaBancariaCollectionNew);
            Collection<Contacto> attachedContactoCollectionNew = new ArrayList<Contacto>();
            for (Contacto contactoCollectionNewContactoToAttach : contactoCollectionNew) {
                contactoCollectionNewContactoToAttach = em.getReference(contactoCollectionNewContactoToAttach.getClass(), contactoCollectionNewContactoToAttach.getId());
                attachedContactoCollectionNew.add(contactoCollectionNewContactoToAttach);
            }
            contactoCollectionNew = attachedContactoCollectionNew;
            cliente.setContactoCollection(contactoCollectionNew);
            Collection<PedidoVenta> attachedPedidoVentaCollectionNew = new ArrayList<PedidoVenta>();
            for (PedidoVenta pedidoVentaCollectionNewPedidoVentaToAttach : pedidoVentaCollectionNew) {
                pedidoVentaCollectionNewPedidoVentaToAttach = em.getReference(pedidoVentaCollectionNewPedidoVentaToAttach.getClass(), pedidoVentaCollectionNewPedidoVentaToAttach.getId());
                attachedPedidoVentaCollectionNew.add(pedidoVentaCollectionNewPedidoVentaToAttach);
            }
            pedidoVentaCollectionNew = attachedPedidoVentaCollectionNew;
            cliente.setPedidoVentaCollection(pedidoVentaCollectionNew);
            cliente = em.merge(cliente);
            if (poblacionOld != null && !poblacionOld.equals(poblacionNew)) {
                poblacionOld.getClienteCollection().remove(cliente);
                poblacionOld = em.merge(poblacionOld);
            }
            if (poblacionNew != null && !poblacionNew.equals(poblacionOld)) {
                poblacionNew.getClienteCollection().add(cliente);
                poblacionNew = em.merge(poblacionNew);
            }
            for (CuentaBancaria cuentaBancariaCollectionOldCuentaBancaria : cuentaBancariaCollectionOld) {
                if (!cuentaBancariaCollectionNew.contains(cuentaBancariaCollectionOldCuentaBancaria)) {
                    cuentaBancariaCollectionOldCuentaBancaria.getClienteCollection().remove(cliente);
                    cuentaBancariaCollectionOldCuentaBancaria = em.merge(cuentaBancariaCollectionOldCuentaBancaria);
                }
            }
            for (CuentaBancaria cuentaBancariaCollectionNewCuentaBancaria : cuentaBancariaCollectionNew) {
                if (!cuentaBancariaCollectionOld.contains(cuentaBancariaCollectionNewCuentaBancaria)) {
                    cuentaBancariaCollectionNewCuentaBancaria.getClienteCollection().add(cliente);
                    cuentaBancariaCollectionNewCuentaBancaria = em.merge(cuentaBancariaCollectionNewCuentaBancaria);
                }
            }
            for (Contacto contactoCollectionOldContacto : contactoCollectionOld) {
                if (!contactoCollectionNew.contains(contactoCollectionOldContacto)) {
                    contactoCollectionOldContacto.getClienteCollection().remove(cliente);
                    contactoCollectionOldContacto = em.merge(contactoCollectionOldContacto);
                }
            }
            for (Contacto contactoCollectionNewContacto : contactoCollectionNew) {
                if (!contactoCollectionOld.contains(contactoCollectionNewContacto)) {
                    contactoCollectionNewContacto.getClienteCollection().add(cliente);
                    contactoCollectionNewContacto = em.merge(contactoCollectionNewContacto);
                }
            }
            for (PedidoVenta pedidoVentaCollectionNewPedidoVenta : pedidoVentaCollectionNew) {
                if (!pedidoVentaCollectionOld.contains(pedidoVentaCollectionNewPedidoVenta)) {
                    Cliente oldClienteOfPedidoVentaCollectionNewPedidoVenta = pedidoVentaCollectionNewPedidoVenta.getCliente();
                    pedidoVentaCollectionNewPedidoVenta.setCliente(cliente);
                    pedidoVentaCollectionNewPedidoVenta = em.merge(pedidoVentaCollectionNewPedidoVenta);
                    if (oldClienteOfPedidoVentaCollectionNewPedidoVenta != null && !oldClienteOfPedidoVentaCollectionNewPedidoVenta.equals(cliente)) {
                        oldClienteOfPedidoVentaCollectionNewPedidoVenta.getPedidoVentaCollection().remove(pedidoVentaCollectionNewPedidoVenta);
                        oldClienteOfPedidoVentaCollectionNewPedidoVenta = em.merge(oldClienteOfPedidoVentaCollectionNewPedidoVenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PedidoVenta> pedidoVentaCollectionOrphanCheck = cliente.getPedidoVentaCollection();
            for (PedidoVenta pedidoVentaCollectionOrphanCheckPedidoVenta : pedidoVentaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the PedidoVenta " + pedidoVentaCollectionOrphanCheckPedidoVenta + " in its pedidoVentaCollection field has a non-nullable cliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Poblacion poblacion = cliente.getPoblacion();
            if (poblacion != null) {
                poblacion.getClienteCollection().remove(cliente);
                poblacion = em.merge(poblacion);
            }
            Collection<CuentaBancaria> cuentaBancariaCollection = cliente.getCuentaBancariaCollection();
            for (CuentaBancaria cuentaBancariaCollectionCuentaBancaria : cuentaBancariaCollection) {
                cuentaBancariaCollectionCuentaBancaria.getClienteCollection().remove(cliente);
                cuentaBancariaCollectionCuentaBancaria = em.merge(cuentaBancariaCollectionCuentaBancaria);
            }
            Collection<Contacto> contactoCollection = cliente.getContactoCollection();
            for (Contacto contactoCollectionContacto : contactoCollection) {
                contactoCollectionContacto.getClienteCollection().remove(cliente);
                contactoCollectionContacto = em.merge(contactoCollectionContacto);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Cliente as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Cliente as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
