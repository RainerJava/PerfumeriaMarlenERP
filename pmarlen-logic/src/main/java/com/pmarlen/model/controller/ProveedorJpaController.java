package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.Proveedor;
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
import com.pmarlen.model.beans.ProveedorProducto;
import com.pmarlen.model.beans.PedidoCompra;

/**
 * ProveedorJpaController
 */

@Repository("proveedorJpaController")

public class ProveedorJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proveedor proveedor) {
        if (proveedor.getCuentaBancariaCollection() == null) {
            proveedor.setCuentaBancariaCollection(new ArrayList<CuentaBancaria>());
        }
        if (proveedor.getContactoCollection() == null) {
            proveedor.setContactoCollection(new ArrayList<Contacto>());
        }
        if (proveedor.getProveedorProductoCollection() == null) {
            proveedor.setProveedorProductoCollection(new ArrayList<ProveedorProducto>());
        }
        if (proveedor.getPedidoCompraCollection() == null) {
            proveedor.setPedidoCompraCollection(new ArrayList<PedidoCompra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Poblacion poblacion = proveedor.getPoblacion();
            if (poblacion != null) {
                poblacion = em.getReference(poblacion.getClass(), poblacion.getId());
                proveedor.setPoblacion(poblacion);
            }
            Collection<CuentaBancaria> attachedCuentaBancariaCollection = new ArrayList<CuentaBancaria>();
            for (CuentaBancaria cuentaBancariaCollectionCuentaBancariaToAttach : proveedor.getCuentaBancariaCollection()) {
                cuentaBancariaCollectionCuentaBancariaToAttach = em.getReference(cuentaBancariaCollectionCuentaBancariaToAttach.getClass(), cuentaBancariaCollectionCuentaBancariaToAttach.getId());
                attachedCuentaBancariaCollection.add(cuentaBancariaCollectionCuentaBancariaToAttach);
            }
            proveedor.setCuentaBancariaCollection(attachedCuentaBancariaCollection);
            Collection<Contacto> attachedContactoCollection = new ArrayList<Contacto>();
            for (Contacto contactoCollectionContactoToAttach : proveedor.getContactoCollection()) {
                contactoCollectionContactoToAttach = em.getReference(contactoCollectionContactoToAttach.getClass(), contactoCollectionContactoToAttach.getId());
                attachedContactoCollection.add(contactoCollectionContactoToAttach);
            }
            proveedor.setContactoCollection(attachedContactoCollection);
            Collection<ProveedorProducto> attachedProveedorProductoCollection = new ArrayList<ProveedorProducto>();
            for (ProveedorProducto proveedorProductoCollectionProveedorProductoToAttach : proveedor.getProveedorProductoCollection()) {
                proveedorProductoCollectionProveedorProductoToAttach = em.getReference(proveedorProductoCollectionProveedorProductoToAttach.getClass(), proveedorProductoCollectionProveedorProductoToAttach.getProveedorProductoPK());
                attachedProveedorProductoCollection.add(proveedorProductoCollectionProveedorProductoToAttach);
            }
            proveedor.setProveedorProductoCollection(attachedProveedorProductoCollection);
            Collection<PedidoCompra> attachedPedidoCompraCollection = new ArrayList<PedidoCompra>();
            for (PedidoCompra pedidoCompraCollectionPedidoCompraToAttach : proveedor.getPedidoCompraCollection()) {
                pedidoCompraCollectionPedidoCompraToAttach = em.getReference(pedidoCompraCollectionPedidoCompraToAttach.getClass(), pedidoCompraCollectionPedidoCompraToAttach.getId());
                attachedPedidoCompraCollection.add(pedidoCompraCollectionPedidoCompraToAttach);
            }
            proveedor.setPedidoCompraCollection(attachedPedidoCompraCollection);
            em.persist(proveedor);
            if (poblacion != null) {
                poblacion.getProveedorCollection().add(proveedor);
                poblacion = em.merge(poblacion);
            }
            for (CuentaBancaria cuentaBancariaCollectionCuentaBancaria : proveedor.getCuentaBancariaCollection()) {
                cuentaBancariaCollectionCuentaBancaria.getProveedorCollection().add(proveedor);
                cuentaBancariaCollectionCuentaBancaria = em.merge(cuentaBancariaCollectionCuentaBancaria);
            }
            for (Contacto contactoCollectionContacto : proveedor.getContactoCollection()) {
                contactoCollectionContacto.getProveedorCollection().add(proveedor);
                contactoCollectionContacto = em.merge(contactoCollectionContacto);
            }
            for (ProveedorProducto proveedorProductoCollectionProveedorProducto : proveedor.getProveedorProductoCollection()) {
                Proveedor oldProveedorOfProveedorProductoCollectionProveedorProducto = proveedorProductoCollectionProveedorProducto.getProveedor();
                proveedorProductoCollectionProveedorProducto.setProveedor(proveedor);
                proveedorProductoCollectionProveedorProducto = em.merge(proveedorProductoCollectionProveedorProducto);
                if (oldProveedorOfProveedorProductoCollectionProveedorProducto != null) {
                    oldProveedorOfProveedorProductoCollectionProveedorProducto.getProveedorProductoCollection().remove(proveedorProductoCollectionProveedorProducto);
                    oldProveedorOfProveedorProductoCollectionProveedorProducto = em.merge(oldProveedorOfProveedorProductoCollectionProveedorProducto);
                }
            }
            for (PedidoCompra pedidoCompraCollectionPedidoCompra : proveedor.getPedidoCompraCollection()) {
                Proveedor oldProveedorOfPedidoCompraCollectionPedidoCompra = pedidoCompraCollectionPedidoCompra.getProveedor();
                pedidoCompraCollectionPedidoCompra.setProveedor(proveedor);
                pedidoCompraCollectionPedidoCompra = em.merge(pedidoCompraCollectionPedidoCompra);
                if (oldProveedorOfPedidoCompraCollectionPedidoCompra != null) {
                    oldProveedorOfPedidoCompraCollectionPedidoCompra.getPedidoCompraCollection().remove(pedidoCompraCollectionPedidoCompra);
                    oldProveedorOfPedidoCompraCollectionPedidoCompra = em.merge(oldProveedorOfPedidoCompraCollectionPedidoCompra);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proveedor proveedor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor persistentProveedor = em.find(Proveedor.class, proveedor.getId());
            Poblacion poblacionOld = persistentProveedor.getPoblacion();
            Poblacion poblacionNew = proveedor.getPoblacion();
            Collection<CuentaBancaria> cuentaBancariaCollectionOld = persistentProveedor.getCuentaBancariaCollection();
            Collection<CuentaBancaria> cuentaBancariaCollectionNew = proveedor.getCuentaBancariaCollection();
            Collection<Contacto> contactoCollectionOld = persistentProveedor.getContactoCollection();
            Collection<Contacto> contactoCollectionNew = proveedor.getContactoCollection();
            Collection<ProveedorProducto> proveedorProductoCollectionOld = persistentProveedor.getProveedorProductoCollection();
            Collection<ProveedorProducto> proveedorProductoCollectionNew = proveedor.getProveedorProductoCollection();
            Collection<PedidoCompra> pedidoCompraCollectionOld = persistentProveedor.getPedidoCompraCollection();
            Collection<PedidoCompra> pedidoCompraCollectionNew = proveedor.getPedidoCompraCollection();
            List<String> illegalOrphanMessages = null;
            for (ProveedorProducto proveedorProductoCollectionOldProveedorProducto : proveedorProductoCollectionOld) {
                if (!proveedorProductoCollectionNew.contains(proveedorProductoCollectionOldProveedorProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProveedorProducto " + proveedorProductoCollectionOldProveedorProducto + " since its proveedor field is not nullable.");
                }
            }
            for (PedidoCompra pedidoCompraCollectionOldPedidoCompra : pedidoCompraCollectionOld) {
                if (!pedidoCompraCollectionNew.contains(pedidoCompraCollectionOldPedidoCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PedidoCompra " + pedidoCompraCollectionOldPedidoCompra + " since its proveedor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (poblacionNew != null) {
                poblacionNew = em.getReference(poblacionNew.getClass(), poblacionNew.getId());
                proveedor.setPoblacion(poblacionNew);
            }
            Collection<CuentaBancaria> attachedCuentaBancariaCollectionNew = new ArrayList<CuentaBancaria>();
            for (CuentaBancaria cuentaBancariaCollectionNewCuentaBancariaToAttach : cuentaBancariaCollectionNew) {
                cuentaBancariaCollectionNewCuentaBancariaToAttach = em.getReference(cuentaBancariaCollectionNewCuentaBancariaToAttach.getClass(), cuentaBancariaCollectionNewCuentaBancariaToAttach.getId());
                attachedCuentaBancariaCollectionNew.add(cuentaBancariaCollectionNewCuentaBancariaToAttach);
            }
            cuentaBancariaCollectionNew = attachedCuentaBancariaCollectionNew;
            proveedor.setCuentaBancariaCollection(cuentaBancariaCollectionNew);
            Collection<Contacto> attachedContactoCollectionNew = new ArrayList<Contacto>();
            for (Contacto contactoCollectionNewContactoToAttach : contactoCollectionNew) {
                contactoCollectionNewContactoToAttach = em.getReference(contactoCollectionNewContactoToAttach.getClass(), contactoCollectionNewContactoToAttach.getId());
                attachedContactoCollectionNew.add(contactoCollectionNewContactoToAttach);
            }
            contactoCollectionNew = attachedContactoCollectionNew;
            proveedor.setContactoCollection(contactoCollectionNew);
            Collection<ProveedorProducto> attachedProveedorProductoCollectionNew = new ArrayList<ProveedorProducto>();
            for (ProveedorProducto proveedorProductoCollectionNewProveedorProductoToAttach : proveedorProductoCollectionNew) {
                proveedorProductoCollectionNewProveedorProductoToAttach = em.getReference(proveedorProductoCollectionNewProveedorProductoToAttach.getClass(), proveedorProductoCollectionNewProveedorProductoToAttach.getProveedorProductoPK());
                attachedProveedorProductoCollectionNew.add(proveedorProductoCollectionNewProveedorProductoToAttach);
            }
            proveedorProductoCollectionNew = attachedProveedorProductoCollectionNew;
            proveedor.setProveedorProductoCollection(proveedorProductoCollectionNew);
            Collection<PedidoCompra> attachedPedidoCompraCollectionNew = new ArrayList<PedidoCompra>();
            for (PedidoCompra pedidoCompraCollectionNewPedidoCompraToAttach : pedidoCompraCollectionNew) {
                pedidoCompraCollectionNewPedidoCompraToAttach = em.getReference(pedidoCompraCollectionNewPedidoCompraToAttach.getClass(), pedidoCompraCollectionNewPedidoCompraToAttach.getId());
                attachedPedidoCompraCollectionNew.add(pedidoCompraCollectionNewPedidoCompraToAttach);
            }
            pedidoCompraCollectionNew = attachedPedidoCompraCollectionNew;
            proveedor.setPedidoCompraCollection(pedidoCompraCollectionNew);
            proveedor = em.merge(proveedor);
            if (poblacionOld != null && !poblacionOld.equals(poblacionNew)) {
                poblacionOld.getProveedorCollection().remove(proveedor);
                poblacionOld = em.merge(poblacionOld);
            }
            if (poblacionNew != null && !poblacionNew.equals(poblacionOld)) {
                poblacionNew.getProveedorCollection().add(proveedor);
                poblacionNew = em.merge(poblacionNew);
            }
            for (CuentaBancaria cuentaBancariaCollectionOldCuentaBancaria : cuentaBancariaCollectionOld) {
                if (!cuentaBancariaCollectionNew.contains(cuentaBancariaCollectionOldCuentaBancaria)) {
                    cuentaBancariaCollectionOldCuentaBancaria.getProveedorCollection().remove(proveedor);
                    cuentaBancariaCollectionOldCuentaBancaria = em.merge(cuentaBancariaCollectionOldCuentaBancaria);
                }
            }
            for (CuentaBancaria cuentaBancariaCollectionNewCuentaBancaria : cuentaBancariaCollectionNew) {
                if (!cuentaBancariaCollectionOld.contains(cuentaBancariaCollectionNewCuentaBancaria)) {
                    cuentaBancariaCollectionNewCuentaBancaria.getProveedorCollection().add(proveedor);
                    cuentaBancariaCollectionNewCuentaBancaria = em.merge(cuentaBancariaCollectionNewCuentaBancaria);
                }
            }
            for (Contacto contactoCollectionOldContacto : contactoCollectionOld) {
                if (!contactoCollectionNew.contains(contactoCollectionOldContacto)) {
                    contactoCollectionOldContacto.getProveedorCollection().remove(proveedor);
                    contactoCollectionOldContacto = em.merge(contactoCollectionOldContacto);
                }
            }
            for (Contacto contactoCollectionNewContacto : contactoCollectionNew) {
                if (!contactoCollectionOld.contains(contactoCollectionNewContacto)) {
                    contactoCollectionNewContacto.getProveedorCollection().add(proveedor);
                    contactoCollectionNewContacto = em.merge(contactoCollectionNewContacto);
                }
            }
            for (ProveedorProducto proveedorProductoCollectionNewProveedorProducto : proveedorProductoCollectionNew) {
                if (!proveedorProductoCollectionOld.contains(proveedorProductoCollectionNewProveedorProducto)) {
                    Proveedor oldProveedorOfProveedorProductoCollectionNewProveedorProducto = proveedorProductoCollectionNewProveedorProducto.getProveedor();
                    proveedorProductoCollectionNewProveedorProducto.setProveedor(proveedor);
                    proveedorProductoCollectionNewProveedorProducto = em.merge(proveedorProductoCollectionNewProveedorProducto);
                    if (oldProveedorOfProveedorProductoCollectionNewProveedorProducto != null && !oldProveedorOfProveedorProductoCollectionNewProveedorProducto.equals(proveedor)) {
                        oldProveedorOfProveedorProductoCollectionNewProveedorProducto.getProveedorProductoCollection().remove(proveedorProductoCollectionNewProveedorProducto);
                        oldProveedorOfProveedorProductoCollectionNewProveedorProducto = em.merge(oldProveedorOfProveedorProductoCollectionNewProveedorProducto);
                    }
                }
            }
            for (PedidoCompra pedidoCompraCollectionNewPedidoCompra : pedidoCompraCollectionNew) {
                if (!pedidoCompraCollectionOld.contains(pedidoCompraCollectionNewPedidoCompra)) {
                    Proveedor oldProveedorOfPedidoCompraCollectionNewPedidoCompra = pedidoCompraCollectionNewPedidoCompra.getProveedor();
                    pedidoCompraCollectionNewPedidoCompra.setProveedor(proveedor);
                    pedidoCompraCollectionNewPedidoCompra = em.merge(pedidoCompraCollectionNewPedidoCompra);
                    if (oldProveedorOfPedidoCompraCollectionNewPedidoCompra != null && !oldProveedorOfPedidoCompraCollectionNewPedidoCompra.equals(proveedor)) {
                        oldProveedorOfPedidoCompraCollectionNewPedidoCompra.getPedidoCompraCollection().remove(pedidoCompraCollectionNewPedidoCompra);
                        oldProveedorOfPedidoCompraCollectionNewPedidoCompra = em.merge(oldProveedorOfPedidoCompraCollectionNewPedidoCompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proveedor.getId();
                if (findProveedor(id) == null) {
                    throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.");
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
            Proveedor proveedor;
            try {
                proveedor = em.getReference(Proveedor.class, id);
                proveedor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ProveedorProducto> proveedorProductoCollectionOrphanCheck = proveedor.getProveedorProductoCollection();
            for (ProveedorProducto proveedorProductoCollectionOrphanCheckProveedorProducto : proveedorProductoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proveedor (" + proveedor + ") cannot be destroyed since the ProveedorProducto " + proveedorProductoCollectionOrphanCheckProveedorProducto + " in its proveedorProductoCollection field has a non-nullable proveedor field.");
            }
            Collection<PedidoCompra> pedidoCompraCollectionOrphanCheck = proveedor.getPedidoCompraCollection();
            for (PedidoCompra pedidoCompraCollectionOrphanCheckPedidoCompra : pedidoCompraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proveedor (" + proveedor + ") cannot be destroyed since the PedidoCompra " + pedidoCompraCollectionOrphanCheckPedidoCompra + " in its pedidoCompraCollection field has a non-nullable proveedor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Poblacion poblacion = proveedor.getPoblacion();
            if (poblacion != null) {
                poblacion.getProveedorCollection().remove(proveedor);
                poblacion = em.merge(poblacion);
            }
            Collection<CuentaBancaria> cuentaBancariaCollection = proveedor.getCuentaBancariaCollection();
            for (CuentaBancaria cuentaBancariaCollectionCuentaBancaria : cuentaBancariaCollection) {
                cuentaBancariaCollectionCuentaBancaria.getProveedorCollection().remove(proveedor);
                cuentaBancariaCollectionCuentaBancaria = em.merge(cuentaBancariaCollectionCuentaBancaria);
            }
            Collection<Contacto> contactoCollection = proveedor.getContactoCollection();
            for (Contacto contactoCollectionContacto : contactoCollection) {
                contactoCollectionContacto.getProveedorCollection().remove(proveedor);
                contactoCollectionContacto = em.merge(contactoCollectionContacto);
            }
            em.remove(proveedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proveedor> findProveedorEntities() {
        return findProveedorEntities(true, -1, -1);
    }

    public List<Proveedor> findProveedorEntities(int maxResults, int firstResult) {
        return findProveedorEntities(false, maxResults, firstResult);
    }

    private List<Proveedor> findProveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Proveedor as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Proveedor findProveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getProveedorCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Proveedor as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
