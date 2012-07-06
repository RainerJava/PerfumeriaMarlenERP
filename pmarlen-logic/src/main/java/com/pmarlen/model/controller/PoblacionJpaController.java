package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.Poblacion;
import com.pmarlen.model.controller.exceptions.IllegalOrphanException;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import com.pmarlen.model.controller.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.Contacto;
import java.util.ArrayList;
import java.util.Collection;
import com.pmarlen.model.beans.Proveedor;
import com.pmarlen.model.beans.Almacen;
import com.pmarlen.model.beans.Cliente;

/**
 * PoblacionJpaController
 */

@Repository("poblacionJpaController")

public class PoblacionJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Poblacion poblacion) throws PreexistingEntityException, Exception {
        if (poblacion.getContactoCollection() == null) {
            poblacion.setContactoCollection(new ArrayList<Contacto>());
        }
        if (poblacion.getProveedorCollection() == null) {
            poblacion.setProveedorCollection(new ArrayList<Proveedor>());
        }
        if (poblacion.getAlmacenCollection() == null) {
            poblacion.setAlmacenCollection(new ArrayList<Almacen>());
        }
        if (poblacion.getClienteCollection() == null) {
            poblacion.setClienteCollection(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
//            MunicipioODelegacion municipioODelegacion = poblacion.getMunicipioODelegacion();
//            if (municipioODelegacion != null) {
//                municipioODelegacion = em.getReference(municipioODelegacion.getClass(), municipioODelegacion.getId());
//                poblacion.setMunicipioODelegacion(municipioODelegacion);
//            }
            Collection<Contacto> attachedContactoCollection = new ArrayList<Contacto>();
            for (Contacto contactoCollectionContactoToAttach : poblacion.getContactoCollection()) {
                contactoCollectionContactoToAttach = em.getReference(contactoCollectionContactoToAttach.getClass(), contactoCollectionContactoToAttach.getId());
                attachedContactoCollection.add(contactoCollectionContactoToAttach);
            }
            poblacion.setContactoCollection(attachedContactoCollection);
            Collection<Proveedor> attachedProveedorCollection = new ArrayList<Proveedor>();
            for (Proveedor proveedorCollectionProveedorToAttach : poblacion.getProveedorCollection()) {
                proveedorCollectionProveedorToAttach = em.getReference(proveedorCollectionProveedorToAttach.getClass(), proveedorCollectionProveedorToAttach.getId());
                attachedProveedorCollection.add(proveedorCollectionProveedorToAttach);
            }
            poblacion.setProveedorCollection(attachedProveedorCollection);
            Collection<Almacen> attachedAlmacenCollection = new ArrayList<Almacen>();
            for (Almacen almacenCollectionAlmacenToAttach : poblacion.getAlmacenCollection()) {
                almacenCollectionAlmacenToAttach = em.getReference(almacenCollectionAlmacenToAttach.getClass(), almacenCollectionAlmacenToAttach.getId());
                attachedAlmacenCollection.add(almacenCollectionAlmacenToAttach);
            }
            poblacion.setAlmacenCollection(attachedAlmacenCollection);
            Collection<Cliente> attachedClienteCollection = new ArrayList<Cliente>();
            for (Cliente clienteCollectionClienteToAttach : poblacion.getClienteCollection()) {
                clienteCollectionClienteToAttach = em.getReference(clienteCollectionClienteToAttach.getClass(), clienteCollectionClienteToAttach.getId());
                attachedClienteCollection.add(clienteCollectionClienteToAttach);
            }
            poblacion.setClienteCollection(attachedClienteCollection);
            em.persist(poblacion);
//            if (municipioODelegacion != null) {
//                municipioODelegacion.getPoblacionCollection().add(poblacion);
//                municipioODelegacion = em.merge(municipioODelegacion);
//            }
            for (Contacto contactoCollectionContacto : poblacion.getContactoCollection()) {
                Poblacion oldPoblacionOfContactoCollectionContacto = contactoCollectionContacto.getPoblacion();
                contactoCollectionContacto.setPoblacion(poblacion);
                contactoCollectionContacto = em.merge(contactoCollectionContacto);
                if (oldPoblacionOfContactoCollectionContacto != null) {
                    oldPoblacionOfContactoCollectionContacto.getContactoCollection().remove(contactoCollectionContacto);
                    oldPoblacionOfContactoCollectionContacto = em.merge(oldPoblacionOfContactoCollectionContacto);
                }
            }
            for (Proveedor proveedorCollectionProveedor : poblacion.getProveedorCollection()) {
                Poblacion oldPoblacionOfProveedorCollectionProveedor = proveedorCollectionProveedor.getPoblacion();
                proveedorCollectionProveedor.setPoblacion(poblacion);
                proveedorCollectionProveedor = em.merge(proveedorCollectionProveedor);
                if (oldPoblacionOfProveedorCollectionProveedor != null) {
                    oldPoblacionOfProveedorCollectionProveedor.getProveedorCollection().remove(proveedorCollectionProveedor);
                    oldPoblacionOfProveedorCollectionProveedor = em.merge(oldPoblacionOfProveedorCollectionProveedor);
                }
            }
            for (Almacen almacenCollectionAlmacen : poblacion.getAlmacenCollection()) {
                Poblacion oldPoblacionOfAlmacenCollectionAlmacen = almacenCollectionAlmacen.getPoblacion();
                almacenCollectionAlmacen.setPoblacion(poblacion);
                almacenCollectionAlmacen = em.merge(almacenCollectionAlmacen);
                if (oldPoblacionOfAlmacenCollectionAlmacen != null) {
                    oldPoblacionOfAlmacenCollectionAlmacen.getAlmacenCollection().remove(almacenCollectionAlmacen);
                    oldPoblacionOfAlmacenCollectionAlmacen = em.merge(oldPoblacionOfAlmacenCollectionAlmacen);
                }
            }
            for (Cliente clienteCollectionCliente : poblacion.getClienteCollection()) {
                Poblacion oldPoblacionOfClienteCollectionCliente = clienteCollectionCliente.getPoblacion();
                clienteCollectionCliente.setPoblacion(poblacion);
                clienteCollectionCliente = em.merge(clienteCollectionCliente);
                if (oldPoblacionOfClienteCollectionCliente != null) {
                    oldPoblacionOfClienteCollectionCliente.getClienteCollection().remove(clienteCollectionCliente);
                    oldPoblacionOfClienteCollectionCliente = em.merge(oldPoblacionOfClienteCollectionCliente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPoblacion(poblacion.getId()) != null) {
                throw new PreexistingEntityException("Poblacion " + poblacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Poblacion poblacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Poblacion persistentPoblacion = em.find(Poblacion.class, poblacion.getId());
//            MunicipioODelegacion municipioODelegacionOld = persistentPoblacion.getMunicipioODelegacion();
//            MunicipioODelegacion municipioODelegacionNew = poblacion.getMunicipioODelegacion();
            Collection<Contacto> contactoCollectionOld = persistentPoblacion.getContactoCollection();
            Collection<Contacto> contactoCollectionNew = poblacion.getContactoCollection();
            Collection<Proveedor> proveedorCollectionOld = persistentPoblacion.getProveedorCollection();
            Collection<Proveedor> proveedorCollectionNew = poblacion.getProveedorCollection();
            Collection<Almacen> almacenCollectionOld = persistentPoblacion.getAlmacenCollection();
            Collection<Almacen> almacenCollectionNew = poblacion.getAlmacenCollection();
            Collection<Cliente> clienteCollectionOld = persistentPoblacion.getClienteCollection();
            Collection<Cliente> clienteCollectionNew = poblacion.getClienteCollection();
            List<String> illegalOrphanMessages = null;
            for (Proveedor proveedorCollectionOldProveedor : proveedorCollectionOld) {
                if (!proveedorCollectionNew.contains(proveedorCollectionOldProveedor)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Proveedor " + proveedorCollectionOldProveedor + " since its poblacion field is not nullable.");
                }
            }
            for (Almacen almacenCollectionOldAlmacen : almacenCollectionOld) {
                if (!almacenCollectionNew.contains(almacenCollectionOldAlmacen)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Almacen " + almacenCollectionOldAlmacen + " since its poblacion field is not nullable.");
                }
            }
            for (Cliente clienteCollectionOldCliente : clienteCollectionOld) {
                if (!clienteCollectionNew.contains(clienteCollectionOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteCollectionOldCliente + " since its poblacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
//            if (municipioODelegacionNew != null) {
//                municipioODelegacionNew = em.getReference(municipioODelegacionNew.getClass(), municipioODelegacionNew.getId());
//                poblacion.setMunicipioODelegacion(municipioODelegacionNew);
//            }
            Collection<Contacto> attachedContactoCollectionNew = new ArrayList<Contacto>();
            for (Contacto contactoCollectionNewContactoToAttach : contactoCollectionNew) {
                contactoCollectionNewContactoToAttach = em.getReference(contactoCollectionNewContactoToAttach.getClass(), contactoCollectionNewContactoToAttach.getId());
                attachedContactoCollectionNew.add(contactoCollectionNewContactoToAttach);
            }
            contactoCollectionNew = attachedContactoCollectionNew;
            poblacion.setContactoCollection(contactoCollectionNew);
            Collection<Proveedor> attachedProveedorCollectionNew = new ArrayList<Proveedor>();
            for (Proveedor proveedorCollectionNewProveedorToAttach : proveedorCollectionNew) {
                proveedorCollectionNewProveedorToAttach = em.getReference(proveedorCollectionNewProveedorToAttach.getClass(), proveedorCollectionNewProveedorToAttach.getId());
                attachedProveedorCollectionNew.add(proveedorCollectionNewProveedorToAttach);
            }
            proveedorCollectionNew = attachedProveedorCollectionNew;
            poblacion.setProveedorCollection(proveedorCollectionNew);
            Collection<Almacen> attachedAlmacenCollectionNew = new ArrayList<Almacen>();
            for (Almacen almacenCollectionNewAlmacenToAttach : almacenCollectionNew) {
                almacenCollectionNewAlmacenToAttach = em.getReference(almacenCollectionNewAlmacenToAttach.getClass(), almacenCollectionNewAlmacenToAttach.getId());
                attachedAlmacenCollectionNew.add(almacenCollectionNewAlmacenToAttach);
            }
            almacenCollectionNew = attachedAlmacenCollectionNew;
            poblacion.setAlmacenCollection(almacenCollectionNew);
            Collection<Cliente> attachedClienteCollectionNew = new ArrayList<Cliente>();
            for (Cliente clienteCollectionNewClienteToAttach : clienteCollectionNew) {
                clienteCollectionNewClienteToAttach = em.getReference(clienteCollectionNewClienteToAttach.getClass(), clienteCollectionNewClienteToAttach.getId());
                attachedClienteCollectionNew.add(clienteCollectionNewClienteToAttach);
            }
            clienteCollectionNew = attachedClienteCollectionNew;
            poblacion.setClienteCollection(clienteCollectionNew);
            poblacion = em.merge(poblacion);
//            if (municipioODelegacionOld != null && !municipioODelegacionOld.equals(municipioODelegacionNew)) {
//                municipioODelegacionOld.getPoblacionCollection().remove(poblacion);
//                municipioODelegacionOld = em.merge(municipioODelegacionOld);
//            }
//            if (municipioODelegacionNew != null && !municipioODelegacionNew.equals(municipioODelegacionOld)) {
//                municipioODelegacionNew.getPoblacionCollection().add(poblacion);
//                municipioODelegacionNew = em.merge(municipioODelegacionNew);
//            }
            for (Contacto contactoCollectionOldContacto : contactoCollectionOld) {
                if (!contactoCollectionNew.contains(contactoCollectionOldContacto)) {
                    contactoCollectionOldContacto.setPoblacion(null);
                    contactoCollectionOldContacto = em.merge(contactoCollectionOldContacto);
                }
            }
            for (Contacto contactoCollectionNewContacto : contactoCollectionNew) {
                if (!contactoCollectionOld.contains(contactoCollectionNewContacto)) {
                    Poblacion oldPoblacionOfContactoCollectionNewContacto = contactoCollectionNewContacto.getPoblacion();
                    contactoCollectionNewContacto.setPoblacion(poblacion);
                    contactoCollectionNewContacto = em.merge(contactoCollectionNewContacto);
                    if (oldPoblacionOfContactoCollectionNewContacto != null && !oldPoblacionOfContactoCollectionNewContacto.equals(poblacion)) {
                        oldPoblacionOfContactoCollectionNewContacto.getContactoCollection().remove(contactoCollectionNewContacto);
                        oldPoblacionOfContactoCollectionNewContacto = em.merge(oldPoblacionOfContactoCollectionNewContacto);
                    }
                }
            }
            for (Proveedor proveedorCollectionNewProveedor : proveedorCollectionNew) {
                if (!proveedorCollectionOld.contains(proveedorCollectionNewProveedor)) {
                    Poblacion oldPoblacionOfProveedorCollectionNewProveedor = proveedorCollectionNewProveedor.getPoblacion();
                    proveedorCollectionNewProveedor.setPoblacion(poblacion);
                    proveedorCollectionNewProveedor = em.merge(proveedorCollectionNewProveedor);
                    if (oldPoblacionOfProveedorCollectionNewProveedor != null && !oldPoblacionOfProveedorCollectionNewProveedor.equals(poblacion)) {
                        oldPoblacionOfProveedorCollectionNewProveedor.getProveedorCollection().remove(proveedorCollectionNewProveedor);
                        oldPoblacionOfProveedorCollectionNewProveedor = em.merge(oldPoblacionOfProveedorCollectionNewProveedor);
                    }
                }
            }
            for (Almacen almacenCollectionNewAlmacen : almacenCollectionNew) {
                if (!almacenCollectionOld.contains(almacenCollectionNewAlmacen)) {
                    Poblacion oldPoblacionOfAlmacenCollectionNewAlmacen = almacenCollectionNewAlmacen.getPoblacion();
                    almacenCollectionNewAlmacen.setPoblacion(poblacion);
                    almacenCollectionNewAlmacen = em.merge(almacenCollectionNewAlmacen);
                    if (oldPoblacionOfAlmacenCollectionNewAlmacen != null && !oldPoblacionOfAlmacenCollectionNewAlmacen.equals(poblacion)) {
                        oldPoblacionOfAlmacenCollectionNewAlmacen.getAlmacenCollection().remove(almacenCollectionNewAlmacen);
                        oldPoblacionOfAlmacenCollectionNewAlmacen = em.merge(oldPoblacionOfAlmacenCollectionNewAlmacen);
                    }
                }
            }
            for (Cliente clienteCollectionNewCliente : clienteCollectionNew) {
                if (!clienteCollectionOld.contains(clienteCollectionNewCliente)) {
                    Poblacion oldPoblacionOfClienteCollectionNewCliente = clienteCollectionNewCliente.getPoblacion();
                    clienteCollectionNewCliente.setPoblacion(poblacion);
                    clienteCollectionNewCliente = em.merge(clienteCollectionNewCliente);
                    if (oldPoblacionOfClienteCollectionNewCliente != null && !oldPoblacionOfClienteCollectionNewCliente.equals(poblacion)) {
                        oldPoblacionOfClienteCollectionNewCliente.getClienteCollection().remove(clienteCollectionNewCliente);
                        oldPoblacionOfClienteCollectionNewCliente = em.merge(oldPoblacionOfClienteCollectionNewCliente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = poblacion.getId();
                if (findPoblacion(id) == null) {
                    throw new NonexistentEntityException("The poblacion with id " + id + " no longer exists.");
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
            Poblacion poblacion;
            try {
                poblacion = em.getReference(Poblacion.class, id);
                poblacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The poblacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Proveedor> proveedorCollectionOrphanCheck = poblacion.getProveedorCollection();
            for (Proveedor proveedorCollectionOrphanCheckProveedor : proveedorCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Poblacion (" + poblacion + ") cannot be destroyed since the Proveedor " + proveedorCollectionOrphanCheckProveedor + " in its proveedorCollection field has a non-nullable poblacion field.");
            }
            Collection<Almacen> almacenCollectionOrphanCheck = poblacion.getAlmacenCollection();
            for (Almacen almacenCollectionOrphanCheckAlmacen : almacenCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Poblacion (" + poblacion + ") cannot be destroyed since the Almacen " + almacenCollectionOrphanCheckAlmacen + " in its almacenCollection field has a non-nullable poblacion field.");
            }
            Collection<Cliente> clienteCollectionOrphanCheck = poblacion.getClienteCollection();
            for (Cliente clienteCollectionOrphanCheckCliente : clienteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Poblacion (" + poblacion + ") cannot be destroyed since the Cliente " + clienteCollectionOrphanCheckCliente + " in its clienteCollection field has a non-nullable poblacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
//            MunicipioODelegacion municipioODelegacion = poblacion.getMunicipioODelegacion();
//            if (municipioODelegacion != null) {
//                municipioODelegacion.getPoblacionCollection().remove(poblacion);
//                municipioODelegacion = em.merge(municipioODelegacion);
//            }
            Collection<Contacto> contactoCollection = poblacion.getContactoCollection();
            for (Contacto contactoCollectionContacto : contactoCollection) {
                contactoCollectionContacto.setPoblacion(null);
                contactoCollectionContacto = em.merge(contactoCollectionContacto);
            }
            em.remove(poblacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Poblacion> findPoblacionEntities() {
        return findPoblacionEntities(true, -1, -1);
    }

    public List<Poblacion> findPoblacionEntities(int maxResults, int firstResult) {
        return findPoblacionEntities(false, maxResults, firstResult);
    }

    private List<Poblacion> findPoblacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Poblacion as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Poblacion findPoblacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Poblacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPoblacionCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Poblacion as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
