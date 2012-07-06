package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.PedidoCompra;
import com.pmarlen.model.controller.exceptions.IllegalOrphanException;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import com.pmarlen.model.controller.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.FormaDePago;
import com.pmarlen.model.beans.Proveedor;
import com.pmarlen.model.beans.Usuario;
import com.pmarlen.model.beans.FacturaProveedor;
import java.util.ArrayList;
import java.util.Collection;
import com.pmarlen.model.beans.PedidoCompraEstado;
import com.pmarlen.model.beans.PedidoCompraDetalle;

/**
 * PedidoCompraJpaController
 */

@Repository("pedidoCompraJpaController")

public class PedidoCompraJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PedidoCompra pedidoCompra) throws PreexistingEntityException, Exception {
        if (pedidoCompra.getFacturaProveedorCollection() == null) {
            pedidoCompra.setFacturaProveedorCollection(new ArrayList<FacturaProveedor>());
        }
        if (pedidoCompra.getPedidoCompraEstadoCollection() == null) {
            pedidoCompra.setPedidoCompraEstadoCollection(new ArrayList<PedidoCompraEstado>());
        }
        if (pedidoCompra.getPedidoCompraDetalleCollection() == null) {
            pedidoCompra.setPedidoCompraDetalleCollection(new ArrayList<PedidoCompraDetalle>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FormaDePago formaDePago = pedidoCompra.getFormaDePago();
            if (formaDePago != null) {
                formaDePago = em.getReference(formaDePago.getClass(), formaDePago.getId());
                pedidoCompra.setFormaDePago(formaDePago);
            }
            Proveedor proveedor = pedidoCompra.getProveedor();
            if (proveedor != null) {
                proveedor = em.getReference(proveedor.getClass(), proveedor.getId());
                pedidoCompra.setProveedor(proveedor);
            }
            Usuario usuario = pedidoCompra.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getUsuarioId());
                pedidoCompra.setUsuario(usuario);
            }
            Collection<FacturaProveedor> attachedFacturaProveedorCollection = new ArrayList<FacturaProveedor>();
            for (FacturaProveedor facturaProveedorCollectionFacturaProveedorToAttach : pedidoCompra.getFacturaProveedorCollection()) {
                facturaProveedorCollectionFacturaProveedorToAttach = em.getReference(facturaProveedorCollectionFacturaProveedorToAttach.getClass(), facturaProveedorCollectionFacturaProveedorToAttach.getId());
                attachedFacturaProveedorCollection.add(facturaProveedorCollectionFacturaProveedorToAttach);
            }
            pedidoCompra.setFacturaProveedorCollection(attachedFacturaProveedorCollection);
            Collection<PedidoCompraEstado> attachedPedidoCompraEstadoCollection = new ArrayList<PedidoCompraEstado>();
            for (PedidoCompraEstado pedidoCompraEstadoCollectionPedidoCompraEstadoToAttach : pedidoCompra.getPedidoCompraEstadoCollection()) {
                pedidoCompraEstadoCollectionPedidoCompraEstadoToAttach = em.getReference(pedidoCompraEstadoCollectionPedidoCompraEstadoToAttach.getClass(), pedidoCompraEstadoCollectionPedidoCompraEstadoToAttach.getPedidoCompraEstadoPK());
                attachedPedidoCompraEstadoCollection.add(pedidoCompraEstadoCollectionPedidoCompraEstadoToAttach);
            }
            pedidoCompra.setPedidoCompraEstadoCollection(attachedPedidoCompraEstadoCollection);
            Collection<PedidoCompraDetalle> attachedPedidoCompraDetalleCollection = new ArrayList<PedidoCompraDetalle>();
            for (PedidoCompraDetalle pedidoCompraDetalleCollectionPedidoCompraDetalleToAttach : pedidoCompra.getPedidoCompraDetalleCollection()) {
                pedidoCompraDetalleCollectionPedidoCompraDetalleToAttach = em.getReference(pedidoCompraDetalleCollectionPedidoCompraDetalleToAttach.getClass(), pedidoCompraDetalleCollectionPedidoCompraDetalleToAttach.getPedidoCompraDetallePK());
                attachedPedidoCompraDetalleCollection.add(pedidoCompraDetalleCollectionPedidoCompraDetalleToAttach);
            }
            pedidoCompra.setPedidoCompraDetalleCollection(attachedPedidoCompraDetalleCollection);
            em.persist(pedidoCompra);
            if (formaDePago != null) {
                formaDePago.getPedidoCompraCollection().add(pedidoCompra);
                formaDePago = em.merge(formaDePago);
            }
            if (proveedor != null) {
                proveedor.getPedidoCompraCollection().add(pedidoCompra);
                proveedor = em.merge(proveedor);
            }
            if (usuario != null) {
                usuario.getPedidoCompraCollection().add(pedidoCompra);
                usuario = em.merge(usuario);
            }
            for (FacturaProveedor facturaProveedorCollectionFacturaProveedor : pedidoCompra.getFacturaProveedorCollection()) {
                PedidoCompra oldPedidoCompraOfFacturaProveedorCollectionFacturaProveedor = facturaProveedorCollectionFacturaProveedor.getPedidoCompra();
                facturaProveedorCollectionFacturaProveedor.setPedidoCompra(pedidoCompra);
                facturaProveedorCollectionFacturaProveedor = em.merge(facturaProveedorCollectionFacturaProveedor);
                if (oldPedidoCompraOfFacturaProveedorCollectionFacturaProveedor != null) {
                    oldPedidoCompraOfFacturaProveedorCollectionFacturaProveedor.getFacturaProveedorCollection().remove(facturaProveedorCollectionFacturaProveedor);
                    oldPedidoCompraOfFacturaProveedorCollectionFacturaProveedor = em.merge(oldPedidoCompraOfFacturaProveedorCollectionFacturaProveedor);
                }
            }
            for (PedidoCompraEstado pedidoCompraEstadoCollectionPedidoCompraEstado : pedidoCompra.getPedidoCompraEstadoCollection()) {
                PedidoCompra oldPedidoCompraOfPedidoCompraEstadoCollectionPedidoCompraEstado = pedidoCompraEstadoCollectionPedidoCompraEstado.getPedidoCompra();
                pedidoCompraEstadoCollectionPedidoCompraEstado.setPedidoCompra(pedidoCompra);
                pedidoCompraEstadoCollectionPedidoCompraEstado = em.merge(pedidoCompraEstadoCollectionPedidoCompraEstado);
                if (oldPedidoCompraOfPedidoCompraEstadoCollectionPedidoCompraEstado != null) {
                    oldPedidoCompraOfPedidoCompraEstadoCollectionPedidoCompraEstado.getPedidoCompraEstadoCollection().remove(pedidoCompraEstadoCollectionPedidoCompraEstado);
                    oldPedidoCompraOfPedidoCompraEstadoCollectionPedidoCompraEstado = em.merge(oldPedidoCompraOfPedidoCompraEstadoCollectionPedidoCompraEstado);
                }
            }
            for (PedidoCompraDetalle pedidoCompraDetalleCollectionPedidoCompraDetalle : pedidoCompra.getPedidoCompraDetalleCollection()) {
                PedidoCompra oldPedidoCompraOfPedidoCompraDetalleCollectionPedidoCompraDetalle = pedidoCompraDetalleCollectionPedidoCompraDetalle.getPedidoCompra();
                pedidoCompraDetalleCollectionPedidoCompraDetalle.setPedidoCompra(pedidoCompra);
                pedidoCompraDetalleCollectionPedidoCompraDetalle = em.merge(pedidoCompraDetalleCollectionPedidoCompraDetalle);
                if (oldPedidoCompraOfPedidoCompraDetalleCollectionPedidoCompraDetalle != null) {
                    oldPedidoCompraOfPedidoCompraDetalleCollectionPedidoCompraDetalle.getPedidoCompraDetalleCollection().remove(pedidoCompraDetalleCollectionPedidoCompraDetalle);
                    oldPedidoCompraOfPedidoCompraDetalleCollectionPedidoCompraDetalle = em.merge(oldPedidoCompraOfPedidoCompraDetalleCollectionPedidoCompraDetalle);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPedidoCompra(pedidoCompra.getId()) != null) {
                throw new PreexistingEntityException("PedidoCompra " + pedidoCompra + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PedidoCompra pedidoCompra) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PedidoCompra persistentPedidoCompra = em.find(PedidoCompra.class, pedidoCompra.getId());
            FormaDePago formaDePagoOld = persistentPedidoCompra.getFormaDePago();
            FormaDePago formaDePagoNew = pedidoCompra.getFormaDePago();
            Proveedor proveedorOld = persistentPedidoCompra.getProveedor();
            Proveedor proveedorNew = pedidoCompra.getProveedor();
            Usuario usuarioOld = persistentPedidoCompra.getUsuario();
            Usuario usuarioNew = pedidoCompra.getUsuario();
            Collection<FacturaProveedor> facturaProveedorCollectionOld = persistentPedidoCompra.getFacturaProveedorCollection();
            Collection<FacturaProveedor> facturaProveedorCollectionNew = pedidoCompra.getFacturaProveedorCollection();
            Collection<PedidoCompraEstado> pedidoCompraEstadoCollectionOld = persistentPedidoCompra.getPedidoCompraEstadoCollection();
            Collection<PedidoCompraEstado> pedidoCompraEstadoCollectionNew = pedidoCompra.getPedidoCompraEstadoCollection();
            Collection<PedidoCompraDetalle> pedidoCompraDetalleCollectionOld = persistentPedidoCompra.getPedidoCompraDetalleCollection();
            Collection<PedidoCompraDetalle> pedidoCompraDetalleCollectionNew = pedidoCompra.getPedidoCompraDetalleCollection();
            List<String> illegalOrphanMessages = null;
            for (FacturaProveedor facturaProveedorCollectionOldFacturaProveedor : facturaProveedorCollectionOld) {
                if (!facturaProveedorCollectionNew.contains(facturaProveedorCollectionOldFacturaProveedor)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FacturaProveedor " + facturaProveedorCollectionOldFacturaProveedor + " since its pedidoCompra field is not nullable.");
                }
            }
            for (PedidoCompraEstado pedidoCompraEstadoCollectionOldPedidoCompraEstado : pedidoCompraEstadoCollectionOld) {
                if (!pedidoCompraEstadoCollectionNew.contains(pedidoCompraEstadoCollectionOldPedidoCompraEstado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PedidoCompraEstado " + pedidoCompraEstadoCollectionOldPedidoCompraEstado + " since its pedidoCompra field is not nullable.");
                }
            }
            for (PedidoCompraDetalle pedidoCompraDetalleCollectionOldPedidoCompraDetalle : pedidoCompraDetalleCollectionOld) {
                if (!pedidoCompraDetalleCollectionNew.contains(pedidoCompraDetalleCollectionOldPedidoCompraDetalle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PedidoCompraDetalle " + pedidoCompraDetalleCollectionOldPedidoCompraDetalle + " since its pedidoCompra field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (formaDePagoNew != null) {
                formaDePagoNew = em.getReference(formaDePagoNew.getClass(), formaDePagoNew.getId());
                pedidoCompra.setFormaDePago(formaDePagoNew);
            }
            if (proveedorNew != null) {
                proveedorNew = em.getReference(proveedorNew.getClass(), proveedorNew.getId());
                pedidoCompra.setProveedor(proveedorNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getUsuarioId());
                pedidoCompra.setUsuario(usuarioNew);
            }
            Collection<FacturaProveedor> attachedFacturaProveedorCollectionNew = new ArrayList<FacturaProveedor>();
            for (FacturaProveedor facturaProveedorCollectionNewFacturaProveedorToAttach : facturaProveedorCollectionNew) {
                facturaProveedorCollectionNewFacturaProveedorToAttach = em.getReference(facturaProveedorCollectionNewFacturaProveedorToAttach.getClass(), facturaProveedorCollectionNewFacturaProveedorToAttach.getId());
                attachedFacturaProveedorCollectionNew.add(facturaProveedorCollectionNewFacturaProveedorToAttach);
            }
            facturaProveedorCollectionNew = attachedFacturaProveedorCollectionNew;
            pedidoCompra.setFacturaProveedorCollection(facturaProveedorCollectionNew);
            Collection<PedidoCompraEstado> attachedPedidoCompraEstadoCollectionNew = new ArrayList<PedidoCompraEstado>();
            for (PedidoCompraEstado pedidoCompraEstadoCollectionNewPedidoCompraEstadoToAttach : pedidoCompraEstadoCollectionNew) {
                pedidoCompraEstadoCollectionNewPedidoCompraEstadoToAttach = em.getReference(pedidoCompraEstadoCollectionNewPedidoCompraEstadoToAttach.getClass(), pedidoCompraEstadoCollectionNewPedidoCompraEstadoToAttach.getPedidoCompraEstadoPK());
                attachedPedidoCompraEstadoCollectionNew.add(pedidoCompraEstadoCollectionNewPedidoCompraEstadoToAttach);
            }
            pedidoCompraEstadoCollectionNew = attachedPedidoCompraEstadoCollectionNew;
            pedidoCompra.setPedidoCompraEstadoCollection(pedidoCompraEstadoCollectionNew);
            Collection<PedidoCompraDetalle> attachedPedidoCompraDetalleCollectionNew = new ArrayList<PedidoCompraDetalle>();
            for (PedidoCompraDetalle pedidoCompraDetalleCollectionNewPedidoCompraDetalleToAttach : pedidoCompraDetalleCollectionNew) {
                pedidoCompraDetalleCollectionNewPedidoCompraDetalleToAttach = em.getReference(pedidoCompraDetalleCollectionNewPedidoCompraDetalleToAttach.getClass(), pedidoCompraDetalleCollectionNewPedidoCompraDetalleToAttach.getPedidoCompraDetallePK());
                attachedPedidoCompraDetalleCollectionNew.add(pedidoCompraDetalleCollectionNewPedidoCompraDetalleToAttach);
            }
            pedidoCompraDetalleCollectionNew = attachedPedidoCompraDetalleCollectionNew;
            pedidoCompra.setPedidoCompraDetalleCollection(pedidoCompraDetalleCollectionNew);
            pedidoCompra = em.merge(pedidoCompra);
            if (formaDePagoOld != null && !formaDePagoOld.equals(formaDePagoNew)) {
                formaDePagoOld.getPedidoCompraCollection().remove(pedidoCompra);
                formaDePagoOld = em.merge(formaDePagoOld);
            }
            if (formaDePagoNew != null && !formaDePagoNew.equals(formaDePagoOld)) {
                formaDePagoNew.getPedidoCompraCollection().add(pedidoCompra);
                formaDePagoNew = em.merge(formaDePagoNew);
            }
            if (proveedorOld != null && !proveedorOld.equals(proveedorNew)) {
                proveedorOld.getPedidoCompraCollection().remove(pedidoCompra);
                proveedorOld = em.merge(proveedorOld);
            }
            if (proveedorNew != null && !proveedorNew.equals(proveedorOld)) {
                proveedorNew.getPedidoCompraCollection().add(pedidoCompra);
                proveedorNew = em.merge(proveedorNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getPedidoCompraCollection().remove(pedidoCompra);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getPedidoCompraCollection().add(pedidoCompra);
                usuarioNew = em.merge(usuarioNew);
            }
            for (FacturaProveedor facturaProveedorCollectionNewFacturaProveedor : facturaProveedorCollectionNew) {
                if (!facturaProveedorCollectionOld.contains(facturaProveedorCollectionNewFacturaProveedor)) {
                    PedidoCompra oldPedidoCompraOfFacturaProveedorCollectionNewFacturaProveedor = facturaProveedorCollectionNewFacturaProveedor.getPedidoCompra();
                    facturaProveedorCollectionNewFacturaProveedor.setPedidoCompra(pedidoCompra);
                    facturaProveedorCollectionNewFacturaProveedor = em.merge(facturaProveedorCollectionNewFacturaProveedor);
                    if (oldPedidoCompraOfFacturaProveedorCollectionNewFacturaProveedor != null && !oldPedidoCompraOfFacturaProveedorCollectionNewFacturaProveedor.equals(pedidoCompra)) {
                        oldPedidoCompraOfFacturaProveedorCollectionNewFacturaProveedor.getFacturaProveedorCollection().remove(facturaProveedorCollectionNewFacturaProveedor);
                        oldPedidoCompraOfFacturaProveedorCollectionNewFacturaProveedor = em.merge(oldPedidoCompraOfFacturaProveedorCollectionNewFacturaProveedor);
                    }
                }
            }
            for (PedidoCompraEstado pedidoCompraEstadoCollectionNewPedidoCompraEstado : pedidoCompraEstadoCollectionNew) {
                if (!pedidoCompraEstadoCollectionOld.contains(pedidoCompraEstadoCollectionNewPedidoCompraEstado)) {
                    PedidoCompra oldPedidoCompraOfPedidoCompraEstadoCollectionNewPedidoCompraEstado = pedidoCompraEstadoCollectionNewPedidoCompraEstado.getPedidoCompra();
                    pedidoCompraEstadoCollectionNewPedidoCompraEstado.setPedidoCompra(pedidoCompra);
                    pedidoCompraEstadoCollectionNewPedidoCompraEstado = em.merge(pedidoCompraEstadoCollectionNewPedidoCompraEstado);
                    if (oldPedidoCompraOfPedidoCompraEstadoCollectionNewPedidoCompraEstado != null && !oldPedidoCompraOfPedidoCompraEstadoCollectionNewPedidoCompraEstado.equals(pedidoCompra)) {
                        oldPedidoCompraOfPedidoCompraEstadoCollectionNewPedidoCompraEstado.getPedidoCompraEstadoCollection().remove(pedidoCompraEstadoCollectionNewPedidoCompraEstado);
                        oldPedidoCompraOfPedidoCompraEstadoCollectionNewPedidoCompraEstado = em.merge(oldPedidoCompraOfPedidoCompraEstadoCollectionNewPedidoCompraEstado);
                    }
                }
            }
            for (PedidoCompraDetalle pedidoCompraDetalleCollectionNewPedidoCompraDetalle : pedidoCompraDetalleCollectionNew) {
                if (!pedidoCompraDetalleCollectionOld.contains(pedidoCompraDetalleCollectionNewPedidoCompraDetalle)) {
                    PedidoCompra oldPedidoCompraOfPedidoCompraDetalleCollectionNewPedidoCompraDetalle = pedidoCompraDetalleCollectionNewPedidoCompraDetalle.getPedidoCompra();
                    pedidoCompraDetalleCollectionNewPedidoCompraDetalle.setPedidoCompra(pedidoCompra);
                    pedidoCompraDetalleCollectionNewPedidoCompraDetalle = em.merge(pedidoCompraDetalleCollectionNewPedidoCompraDetalle);
                    if (oldPedidoCompraOfPedidoCompraDetalleCollectionNewPedidoCompraDetalle != null && !oldPedidoCompraOfPedidoCompraDetalleCollectionNewPedidoCompraDetalle.equals(pedidoCompra)) {
                        oldPedidoCompraOfPedidoCompraDetalleCollectionNewPedidoCompraDetalle.getPedidoCompraDetalleCollection().remove(pedidoCompraDetalleCollectionNewPedidoCompraDetalle);
                        oldPedidoCompraOfPedidoCompraDetalleCollectionNewPedidoCompraDetalle = em.merge(oldPedidoCompraOfPedidoCompraDetalleCollectionNewPedidoCompraDetalle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pedidoCompra.getId();
                if (findPedidoCompra(id) == null) {
                    throw new NonexistentEntityException("The pedidoCompra with id " + id + " no longer exists.");
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
            PedidoCompra pedidoCompra;
            try {
                pedidoCompra = em.getReference(PedidoCompra.class, id);
                pedidoCompra.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedidoCompra with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<FacturaProveedor> facturaProveedorCollectionOrphanCheck = pedidoCompra.getFacturaProveedorCollection();
            for (FacturaProveedor facturaProveedorCollectionOrphanCheckFacturaProveedor : facturaProveedorCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PedidoCompra (" + pedidoCompra + ") cannot be destroyed since the FacturaProveedor " + facturaProveedorCollectionOrphanCheckFacturaProveedor + " in its facturaProveedorCollection field has a non-nullable pedidoCompra field.");
            }
            Collection<PedidoCompraEstado> pedidoCompraEstadoCollectionOrphanCheck = pedidoCompra.getPedidoCompraEstadoCollection();
            for (PedidoCompraEstado pedidoCompraEstadoCollectionOrphanCheckPedidoCompraEstado : pedidoCompraEstadoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PedidoCompra (" + pedidoCompra + ") cannot be destroyed since the PedidoCompraEstado " + pedidoCompraEstadoCollectionOrphanCheckPedidoCompraEstado + " in its pedidoCompraEstadoCollection field has a non-nullable pedidoCompra field.");
            }
            Collection<PedidoCompraDetalle> pedidoCompraDetalleCollectionOrphanCheck = pedidoCompra.getPedidoCompraDetalleCollection();
            for (PedidoCompraDetalle pedidoCompraDetalleCollectionOrphanCheckPedidoCompraDetalle : pedidoCompraDetalleCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PedidoCompra (" + pedidoCompra + ") cannot be destroyed since the PedidoCompraDetalle " + pedidoCompraDetalleCollectionOrphanCheckPedidoCompraDetalle + " in its pedidoCompraDetalleCollection field has a non-nullable pedidoCompra field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            FormaDePago formaDePago = pedidoCompra.getFormaDePago();
            if (formaDePago != null) {
                formaDePago.getPedidoCompraCollection().remove(pedidoCompra);
                formaDePago = em.merge(formaDePago);
            }
            Proveedor proveedor = pedidoCompra.getProveedor();
            if (proveedor != null) {
                proveedor.getPedidoCompraCollection().remove(pedidoCompra);
                proveedor = em.merge(proveedor);
            }
            Usuario usuario = pedidoCompra.getUsuario();
            if (usuario != null) {
                usuario.getPedidoCompraCollection().remove(pedidoCompra);
                usuario = em.merge(usuario);
            }
            em.remove(pedidoCompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PedidoCompra> findPedidoCompraEntities() {
        return findPedidoCompraEntities(true, -1, -1);
    }

    public List<PedidoCompra> findPedidoCompraEntities(int maxResults, int firstResult) {
        return findPedidoCompraEntities(false, maxResults, firstResult);
    }

    private List<PedidoCompra> findPedidoCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from PedidoCompra as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public PedidoCompra findPedidoCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PedidoCompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getPedidoCompraCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from PedidoCompra as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
