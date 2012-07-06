package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.Multimedio;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.Marca;
import java.util.ArrayList;
import java.util.Collection;
import com.pmarlen.model.beans.Empresa;
import com.pmarlen.model.beans.Producto;
import com.pmarlen.model.beans.Linea;

/**
 * MultimedioJpaController
 */

@Repository("multimedioJpaController")

public class MultimedioJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Multimedio multimedio) {
        if (multimedio.getMarcaCollection() == null) {
            multimedio.setMarcaCollection(new ArrayList<Marca>());
        }
        if (multimedio.getEmpresaCollection() == null) {
            multimedio.setEmpresaCollection(new ArrayList<Empresa>());
        }
        if (multimedio.getProductoCollection() == null) {
            multimedio.setProductoCollection(new ArrayList<Producto>());
        }
        if (multimedio.getLineaCollection() == null) {
            multimedio.setLineaCollection(new ArrayList<Linea>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Marca> attachedMarcaCollection = new ArrayList<Marca>();
            for (Marca marcaCollectionMarcaToAttach : multimedio.getMarcaCollection()) {
                marcaCollectionMarcaToAttach = em.getReference(marcaCollectionMarcaToAttach.getClass(), marcaCollectionMarcaToAttach.getId());
                attachedMarcaCollection.add(marcaCollectionMarcaToAttach);
            }
            multimedio.setMarcaCollection(attachedMarcaCollection);
            Collection<Empresa> attachedEmpresaCollection = new ArrayList<Empresa>();
            for (Empresa empresaCollectionEmpresaToAttach : multimedio.getEmpresaCollection()) {
                empresaCollectionEmpresaToAttach = em.getReference(empresaCollectionEmpresaToAttach.getClass(), empresaCollectionEmpresaToAttach.getId());
                attachedEmpresaCollection.add(empresaCollectionEmpresaToAttach);
            }
            multimedio.setEmpresaCollection(attachedEmpresaCollection);
            Collection<Producto> attachedProductoCollection = new ArrayList<Producto>();
            for (Producto productoCollectionProductoToAttach : multimedio.getProductoCollection()) {
                productoCollectionProductoToAttach = em.getReference(productoCollectionProductoToAttach.getClass(), productoCollectionProductoToAttach.getId());
                attachedProductoCollection.add(productoCollectionProductoToAttach);
            }
            multimedio.setProductoCollection(attachedProductoCollection);
            Collection<Linea> attachedLineaCollection = new ArrayList<Linea>();
            for (Linea lineaCollectionLineaToAttach : multimedio.getLineaCollection()) {
                lineaCollectionLineaToAttach = em.getReference(lineaCollectionLineaToAttach.getClass(), lineaCollectionLineaToAttach.getId());
                attachedLineaCollection.add(lineaCollectionLineaToAttach);
            }
            multimedio.setLineaCollection(attachedLineaCollection);
            em.persist(multimedio);
            for (Marca marcaCollectionMarca : multimedio.getMarcaCollection()) {
                marcaCollectionMarca.getMultimedioCollection().add(multimedio);
                marcaCollectionMarca = em.merge(marcaCollectionMarca);
            }
            for (Empresa empresaCollectionEmpresa : multimedio.getEmpresaCollection()) {
                empresaCollectionEmpresa.getMultimedioCollection().add(multimedio);
                empresaCollectionEmpresa = em.merge(empresaCollectionEmpresa);
            }
            for (Producto productoCollectionProducto : multimedio.getProductoCollection()) {
                productoCollectionProducto.getMultimedioCollection().add(multimedio);
                productoCollectionProducto = em.merge(productoCollectionProducto);
            }
            for (Linea lineaCollectionLinea : multimedio.getLineaCollection()) {
                lineaCollectionLinea.getMultimedioCollection().add(multimedio);
                lineaCollectionLinea = em.merge(lineaCollectionLinea);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Multimedio multimedio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Multimedio persistentMultimedio = em.find(Multimedio.class, multimedio.getId());
            Collection<Marca> marcaCollectionOld = persistentMultimedio.getMarcaCollection();
            Collection<Marca> marcaCollectionNew = multimedio.getMarcaCollection();
            Collection<Empresa> empresaCollectionOld = persistentMultimedio.getEmpresaCollection();
            Collection<Empresa> empresaCollectionNew = multimedio.getEmpresaCollection();
            Collection<Producto> productoCollectionOld = persistentMultimedio.getProductoCollection();
            Collection<Producto> productoCollectionNew = multimedio.getProductoCollection();
            Collection<Linea> lineaCollectionOld = persistentMultimedio.getLineaCollection();
            Collection<Linea> lineaCollectionNew = multimedio.getLineaCollection();
            Collection<Marca> attachedMarcaCollectionNew = new ArrayList<Marca>();
            for (Marca marcaCollectionNewMarcaToAttach : marcaCollectionNew) {
                marcaCollectionNewMarcaToAttach = em.getReference(marcaCollectionNewMarcaToAttach.getClass(), marcaCollectionNewMarcaToAttach.getId());
                attachedMarcaCollectionNew.add(marcaCollectionNewMarcaToAttach);
            }
            marcaCollectionNew = attachedMarcaCollectionNew;
            multimedio.setMarcaCollection(marcaCollectionNew);
            Collection<Empresa> attachedEmpresaCollectionNew = new ArrayList<Empresa>();
            for (Empresa empresaCollectionNewEmpresaToAttach : empresaCollectionNew) {
                empresaCollectionNewEmpresaToAttach = em.getReference(empresaCollectionNewEmpresaToAttach.getClass(), empresaCollectionNewEmpresaToAttach.getId());
                attachedEmpresaCollectionNew.add(empresaCollectionNewEmpresaToAttach);
            }
            empresaCollectionNew = attachedEmpresaCollectionNew;
            multimedio.setEmpresaCollection(empresaCollectionNew);
            Collection<Producto> attachedProductoCollectionNew = new ArrayList<Producto>();
            for (Producto productoCollectionNewProductoToAttach : productoCollectionNew) {
                productoCollectionNewProductoToAttach = em.getReference(productoCollectionNewProductoToAttach.getClass(), productoCollectionNewProductoToAttach.getId());
                attachedProductoCollectionNew.add(productoCollectionNewProductoToAttach);
            }
            productoCollectionNew = attachedProductoCollectionNew;
            multimedio.setProductoCollection(productoCollectionNew);
            Collection<Linea> attachedLineaCollectionNew = new ArrayList<Linea>();
            for (Linea lineaCollectionNewLineaToAttach : lineaCollectionNew) {
                lineaCollectionNewLineaToAttach = em.getReference(lineaCollectionNewLineaToAttach.getClass(), lineaCollectionNewLineaToAttach.getId());
                attachedLineaCollectionNew.add(lineaCollectionNewLineaToAttach);
            }
            lineaCollectionNew = attachedLineaCollectionNew;
            multimedio.setLineaCollection(lineaCollectionNew);
            multimedio = em.merge(multimedio);
            for (Marca marcaCollectionOldMarca : marcaCollectionOld) {
                if (!marcaCollectionNew.contains(marcaCollectionOldMarca)) {
                    marcaCollectionOldMarca.getMultimedioCollection().remove(multimedio);
                    marcaCollectionOldMarca = em.merge(marcaCollectionOldMarca);
                }
            }
            for (Marca marcaCollectionNewMarca : marcaCollectionNew) {
                if (!marcaCollectionOld.contains(marcaCollectionNewMarca)) {
                    marcaCollectionNewMarca.getMultimedioCollection().add(multimedio);
                    marcaCollectionNewMarca = em.merge(marcaCollectionNewMarca);
                }
            }
            for (Empresa empresaCollectionOldEmpresa : empresaCollectionOld) {
                if (!empresaCollectionNew.contains(empresaCollectionOldEmpresa)) {
                    empresaCollectionOldEmpresa.getMultimedioCollection().remove(multimedio);
                    empresaCollectionOldEmpresa = em.merge(empresaCollectionOldEmpresa);
                }
            }
            for (Empresa empresaCollectionNewEmpresa : empresaCollectionNew) {
                if (!empresaCollectionOld.contains(empresaCollectionNewEmpresa)) {
                    empresaCollectionNewEmpresa.getMultimedioCollection().add(multimedio);
                    empresaCollectionNewEmpresa = em.merge(empresaCollectionNewEmpresa);
                }
            }
            for (Producto productoCollectionOldProducto : productoCollectionOld) {
                if (!productoCollectionNew.contains(productoCollectionOldProducto)) {
                    productoCollectionOldProducto.getMultimedioCollection().remove(multimedio);
                    productoCollectionOldProducto = em.merge(productoCollectionOldProducto);
                }
            }
            for (Producto productoCollectionNewProducto : productoCollectionNew) {
                if (!productoCollectionOld.contains(productoCollectionNewProducto)) {
                    productoCollectionNewProducto.getMultimedioCollection().add(multimedio);
                    productoCollectionNewProducto = em.merge(productoCollectionNewProducto);
                }
            }
            for (Linea lineaCollectionOldLinea : lineaCollectionOld) {
                if (!lineaCollectionNew.contains(lineaCollectionOldLinea)) {
                    lineaCollectionOldLinea.getMultimedioCollection().remove(multimedio);
                    lineaCollectionOldLinea = em.merge(lineaCollectionOldLinea);
                }
            }
            for (Linea lineaCollectionNewLinea : lineaCollectionNew) {
                if (!lineaCollectionOld.contains(lineaCollectionNewLinea)) {
                    lineaCollectionNewLinea.getMultimedioCollection().add(multimedio);
                    lineaCollectionNewLinea = em.merge(lineaCollectionNewLinea);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = multimedio.getId();
                if (findMultimedio(id) == null) {
                    throw new NonexistentEntityException("The multimedio with id " + id + " no longer exists.");
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
            Multimedio multimedio;
            try {
                multimedio = em.getReference(Multimedio.class, id);
                multimedio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The multimedio with id " + id + " no longer exists.", enfe);
            }
            Collection<Marca> marcaCollection = multimedio.getMarcaCollection();
            for (Marca marcaCollectionMarca : marcaCollection) {
                marcaCollectionMarca.getMultimedioCollection().remove(multimedio);
                marcaCollectionMarca = em.merge(marcaCollectionMarca);
            }
            Collection<Empresa> empresaCollection = multimedio.getEmpresaCollection();
            for (Empresa empresaCollectionEmpresa : empresaCollection) {
                empresaCollectionEmpresa.getMultimedioCollection().remove(multimedio);
                empresaCollectionEmpresa = em.merge(empresaCollectionEmpresa);
            }
            Collection<Producto> productoCollection = multimedio.getProductoCollection();
            for (Producto productoCollectionProducto : productoCollection) {
                productoCollectionProducto.getMultimedioCollection().remove(multimedio);
                productoCollectionProducto = em.merge(productoCollectionProducto);
            }
            Collection<Linea> lineaCollection = multimedio.getLineaCollection();
            for (Linea lineaCollectionLinea : lineaCollection) {
                lineaCollectionLinea.getMultimedioCollection().remove(multimedio);
                lineaCollectionLinea = em.merge(lineaCollectionLinea);
            }
            em.remove(multimedio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Multimedio> findMultimedioEntities() {
        return findMultimedioEntities(true, -1, -1);
    }

    public List<Multimedio> findMultimedioEntities(int maxResults, int firstResult) {
        return findMultimedioEntities(false, maxResults, firstResult);
    }

    private List<Multimedio> findMultimedioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Multimedio as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Multimedio findMultimedio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Multimedio.class, id);
        } finally {
            em.close();
        }
    }

    public int getMultimedioCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Multimedio as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
