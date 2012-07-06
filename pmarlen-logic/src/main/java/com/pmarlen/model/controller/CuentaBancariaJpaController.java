package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.CuentaBancaria;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import com.pmarlen.model.controller.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.Banco;
import com.pmarlen.model.beans.Proveedor;
import java.util.ArrayList;
import java.util.Collection;
import com.pmarlen.model.beans.Cliente;

/**
 * CuentaBancariaJpaController
 */

@Repository("cuentaBancariaJpaController")

public class CuentaBancariaJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CuentaBancaria cuentaBancaria) throws PreexistingEntityException, Exception {
        if (cuentaBancaria.getProveedorCollection() == null) {
            cuentaBancaria.setProveedorCollection(new ArrayList<Proveedor>());
        }
        if (cuentaBancaria.getClienteCollection() == null) {
            cuentaBancaria.setClienteCollection(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Banco banco = cuentaBancaria.getBanco();
            if (banco != null) {
                banco = em.getReference(banco.getClass(), banco.getId());
                cuentaBancaria.setBanco(banco);
            }
            Collection<Proveedor> attachedProveedorCollection = new ArrayList<Proveedor>();
            for (Proveedor proveedorCollectionProveedorToAttach : cuentaBancaria.getProveedorCollection()) {
                proveedorCollectionProveedorToAttach = em.getReference(proveedorCollectionProveedorToAttach.getClass(), proveedorCollectionProveedorToAttach.getId());
                attachedProveedorCollection.add(proveedorCollectionProveedorToAttach);
            }
            cuentaBancaria.setProveedorCollection(attachedProveedorCollection);
            Collection<Cliente> attachedClienteCollection = new ArrayList<Cliente>();
            for (Cliente clienteCollectionClienteToAttach : cuentaBancaria.getClienteCollection()) {
                clienteCollectionClienteToAttach = em.getReference(clienteCollectionClienteToAttach.getClass(), clienteCollectionClienteToAttach.getId());
                attachedClienteCollection.add(clienteCollectionClienteToAttach);
            }
            cuentaBancaria.setClienteCollection(attachedClienteCollection);
            em.persist(cuentaBancaria);
            if (banco != null) {
                banco.getCuentaBancariaCollection().add(cuentaBancaria);
                banco = em.merge(banco);
            }
            for (Proveedor proveedorCollectionProveedor : cuentaBancaria.getProveedorCollection()) {
                proveedorCollectionProveedor.getCuentaBancariaCollection().add(cuentaBancaria);
                proveedorCollectionProveedor = em.merge(proveedorCollectionProveedor);
            }
            for (Cliente clienteCollectionCliente : cuentaBancaria.getClienteCollection()) {
                clienteCollectionCliente.getCuentaBancariaCollection().add(cuentaBancaria);
                clienteCollectionCliente = em.merge(clienteCollectionCliente);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCuentaBancaria(cuentaBancaria.getId()) != null) {
                throw new PreexistingEntityException("CuentaBancaria " + cuentaBancaria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CuentaBancaria cuentaBancaria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CuentaBancaria persistentCuentaBancaria = em.find(CuentaBancaria.class, cuentaBancaria.getId());
            Banco bancoOld = persistentCuentaBancaria.getBanco();
            Banco bancoNew = cuentaBancaria.getBanco();
            Collection<Proveedor> proveedorCollectionOld = persistentCuentaBancaria.getProveedorCollection();
            Collection<Proveedor> proveedorCollectionNew = cuentaBancaria.getProveedorCollection();
            Collection<Cliente> clienteCollectionOld = persistentCuentaBancaria.getClienteCollection();
            Collection<Cliente> clienteCollectionNew = cuentaBancaria.getClienteCollection();
            if (bancoNew != null) {
                bancoNew = em.getReference(bancoNew.getClass(), bancoNew.getId());
                cuentaBancaria.setBanco(bancoNew);
            }
            Collection<Proveedor> attachedProveedorCollectionNew = new ArrayList<Proveedor>();
            for (Proveedor proveedorCollectionNewProveedorToAttach : proveedorCollectionNew) {
                proveedorCollectionNewProveedorToAttach = em.getReference(proveedorCollectionNewProveedorToAttach.getClass(), proveedorCollectionNewProveedorToAttach.getId());
                attachedProveedorCollectionNew.add(proveedorCollectionNewProveedorToAttach);
            }
            proveedorCollectionNew = attachedProveedorCollectionNew;
            cuentaBancaria.setProveedorCollection(proveedorCollectionNew);
            Collection<Cliente> attachedClienteCollectionNew = new ArrayList<Cliente>();
            for (Cliente clienteCollectionNewClienteToAttach : clienteCollectionNew) {
                clienteCollectionNewClienteToAttach = em.getReference(clienteCollectionNewClienteToAttach.getClass(), clienteCollectionNewClienteToAttach.getId());
                attachedClienteCollectionNew.add(clienteCollectionNewClienteToAttach);
            }
            clienteCollectionNew = attachedClienteCollectionNew;
            cuentaBancaria.setClienteCollection(clienteCollectionNew);
            cuentaBancaria = em.merge(cuentaBancaria);
            if (bancoOld != null && !bancoOld.equals(bancoNew)) {
                bancoOld.getCuentaBancariaCollection().remove(cuentaBancaria);
                bancoOld = em.merge(bancoOld);
            }
            if (bancoNew != null && !bancoNew.equals(bancoOld)) {
                bancoNew.getCuentaBancariaCollection().add(cuentaBancaria);
                bancoNew = em.merge(bancoNew);
            }
            for (Proveedor proveedorCollectionOldProveedor : proveedorCollectionOld) {
                if (!proveedorCollectionNew.contains(proveedorCollectionOldProveedor)) {
                    proveedorCollectionOldProveedor.getCuentaBancariaCollection().remove(cuentaBancaria);
                    proveedorCollectionOldProveedor = em.merge(proveedorCollectionOldProveedor);
                }
            }
            for (Proveedor proveedorCollectionNewProveedor : proveedorCollectionNew) {
                if (!proveedorCollectionOld.contains(proveedorCollectionNewProveedor)) {
                    proveedorCollectionNewProveedor.getCuentaBancariaCollection().add(cuentaBancaria);
                    proveedorCollectionNewProveedor = em.merge(proveedorCollectionNewProveedor);
                }
            }
            for (Cliente clienteCollectionOldCliente : clienteCollectionOld) {
                if (!clienteCollectionNew.contains(clienteCollectionOldCliente)) {
                    clienteCollectionOldCliente.getCuentaBancariaCollection().remove(cuentaBancaria);
                    clienteCollectionOldCliente = em.merge(clienteCollectionOldCliente);
                }
            }
            for (Cliente clienteCollectionNewCliente : clienteCollectionNew) {
                if (!clienteCollectionOld.contains(clienteCollectionNewCliente)) {
                    clienteCollectionNewCliente.getCuentaBancariaCollection().add(cuentaBancaria);
                    clienteCollectionNewCliente = em.merge(clienteCollectionNewCliente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = cuentaBancaria.getId();
                if (findCuentaBancaria(id) == null) {
                    throw new NonexistentEntityException("The cuentaBancaria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CuentaBancaria cuentaBancaria;
            try {
                cuentaBancaria = em.getReference(CuentaBancaria.class, id);
                cuentaBancaria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuentaBancaria with id " + id + " no longer exists.", enfe);
            }
            Banco banco = cuentaBancaria.getBanco();
            if (banco != null) {
                banco.getCuentaBancariaCollection().remove(cuentaBancaria);
                banco = em.merge(banco);
            }
            Collection<Proveedor> proveedorCollection = cuentaBancaria.getProveedorCollection();
            for (Proveedor proveedorCollectionProveedor : proveedorCollection) {
                proveedorCollectionProveedor.getCuentaBancariaCollection().remove(cuentaBancaria);
                proveedorCollectionProveedor = em.merge(proveedorCollectionProveedor);
            }
            Collection<Cliente> clienteCollection = cuentaBancaria.getClienteCollection();
            for (Cliente clienteCollectionCliente : clienteCollection) {
                clienteCollectionCliente.getCuentaBancariaCollection().remove(cuentaBancaria);
                clienteCollectionCliente = em.merge(clienteCollectionCliente);
            }
            em.remove(cuentaBancaria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CuentaBancaria> findCuentaBancariaEntities() {
        return findCuentaBancariaEntities(true, -1, -1);
    }

    public List<CuentaBancaria> findCuentaBancariaEntities(int maxResults, int firstResult) {
        return findCuentaBancariaEntities(false, maxResults, firstResult);
    }

    private List<CuentaBancaria> findCuentaBancariaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from CuentaBancaria as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CuentaBancaria findCuentaBancaria(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CuentaBancaria.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuentaBancariaCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from CuentaBancaria as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
