package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.Marca;
import com.pmarlen.model.controller.exceptions.IllegalOrphanException;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.Linea;
import com.pmarlen.model.beans.Empresa;
import com.pmarlen.model.beans.Multimedio;
import java.util.ArrayList;
import java.util.Collection;
import com.pmarlen.model.beans.Producto;

/**
 * MarcaJpaController
 */

@Repository("marcaJpaController")

public class MarcaJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Marca marca) {
        if (marca.getMultimedioCollection() == null) {
            marca.setMultimedioCollection(new ArrayList<Multimedio>());
        }
        if (marca.getProductoCollection() == null) {
            marca.setProductoCollection(new ArrayList<Producto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Linea linea = marca.getLinea();
            if (linea != null) {
                linea = em.getReference(linea.getClass(), linea.getId());
                marca.setLinea(linea);
            }
            Empresa empresa = marca.getEmpresa();
            if (empresa != null) {
                empresa = em.getReference(empresa.getClass(), empresa.getId());
                marca.setEmpresa(empresa);
            }
            Collection<Multimedio> attachedMultimedioCollection = new ArrayList<Multimedio>();
            for (Multimedio multimedioCollectionMultimedioToAttach : marca.getMultimedioCollection()) {
                multimedioCollectionMultimedioToAttach = em.getReference(multimedioCollectionMultimedioToAttach.getClass(), multimedioCollectionMultimedioToAttach.getId());
                attachedMultimedioCollection.add(multimedioCollectionMultimedioToAttach);
            }
            marca.setMultimedioCollection(attachedMultimedioCollection);
            Collection<Producto> attachedProductoCollection = new ArrayList<Producto>();
            for (Producto productoCollectionProductoToAttach : marca.getProductoCollection()) {
                productoCollectionProductoToAttach = em.getReference(productoCollectionProductoToAttach.getClass(), productoCollectionProductoToAttach.getId());
                attachedProductoCollection.add(productoCollectionProductoToAttach);
            }
            marca.setProductoCollection(attachedProductoCollection);
            em.persist(marca);
            if (linea != null) {
                linea.getMarcaCollection().add(marca);
                linea = em.merge(linea);
            }
            if (empresa != null) {
                empresa.getMarcaCollection().add(marca);
                empresa = em.merge(empresa);
            }
            for (Multimedio multimedioCollectionMultimedio : marca.getMultimedioCollection()) {
                multimedioCollectionMultimedio.getMarcaCollection().add(marca);
                multimedioCollectionMultimedio = em.merge(multimedioCollectionMultimedio);
            }
            for (Producto productoCollectionProducto : marca.getProductoCollection()) {
                Marca oldMarcaOfProductoCollectionProducto = productoCollectionProducto.getMarca();
                productoCollectionProducto.setMarca(marca);
                productoCollectionProducto = em.merge(productoCollectionProducto);
                if (oldMarcaOfProductoCollectionProducto != null) {
                    oldMarcaOfProductoCollectionProducto.getProductoCollection().remove(productoCollectionProducto);
                    oldMarcaOfProductoCollectionProducto = em.merge(oldMarcaOfProductoCollectionProducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Marca marca) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Marca persistentMarca = em.find(Marca.class, marca.getId());
            Linea lineaOld = persistentMarca.getLinea();
            Linea lineaNew = marca.getLinea();
            Empresa empresaOld = persistentMarca.getEmpresa();
            Empresa empresaNew = marca.getEmpresa();
            Collection<Multimedio> multimedioCollectionOld = persistentMarca.getMultimedioCollection();
            Collection<Multimedio> multimedioCollectionNew = marca.getMultimedioCollection();
            Collection<Producto> productoCollectionOld = persistentMarca.getProductoCollection();
            Collection<Producto> productoCollectionNew = marca.getProductoCollection();
            List<String> illegalOrphanMessages = null;
            for (Producto productoCollectionOldProducto : productoCollectionOld) {
                if (!productoCollectionNew.contains(productoCollectionOldProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Producto " + productoCollectionOldProducto + " since its marca field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (lineaNew != null) {
                lineaNew = em.getReference(lineaNew.getClass(), lineaNew.getId());
                marca.setLinea(lineaNew);
            }
            if (empresaNew != null) {
                empresaNew = em.getReference(empresaNew.getClass(), empresaNew.getId());
                marca.setEmpresa(empresaNew);
            }
            Collection<Multimedio> attachedMultimedioCollectionNew = new ArrayList<Multimedio>();
            for (Multimedio multimedioCollectionNewMultimedioToAttach : multimedioCollectionNew) {
                multimedioCollectionNewMultimedioToAttach = em.getReference(multimedioCollectionNewMultimedioToAttach.getClass(), multimedioCollectionNewMultimedioToAttach.getId());
                attachedMultimedioCollectionNew.add(multimedioCollectionNewMultimedioToAttach);
            }
            multimedioCollectionNew = attachedMultimedioCollectionNew;
            marca.setMultimedioCollection(multimedioCollectionNew);
            Collection<Producto> attachedProductoCollectionNew = new ArrayList<Producto>();
            for (Producto productoCollectionNewProductoToAttach : productoCollectionNew) {
                productoCollectionNewProductoToAttach = em.getReference(productoCollectionNewProductoToAttach.getClass(), productoCollectionNewProductoToAttach.getId());
                attachedProductoCollectionNew.add(productoCollectionNewProductoToAttach);
            }
            productoCollectionNew = attachedProductoCollectionNew;
            marca.setProductoCollection(productoCollectionNew);
            marca = em.merge(marca);
            if (lineaOld != null && !lineaOld.equals(lineaNew)) {
                lineaOld.getMarcaCollection().remove(marca);
                lineaOld = em.merge(lineaOld);
            }
            if (lineaNew != null && !lineaNew.equals(lineaOld)) {
                lineaNew.getMarcaCollection().add(marca);
                lineaNew = em.merge(lineaNew);
            }
            if (empresaOld != null && !empresaOld.equals(empresaNew)) {
                empresaOld.getMarcaCollection().remove(marca);
                empresaOld = em.merge(empresaOld);
            }
            if (empresaNew != null && !empresaNew.equals(empresaOld)) {
                empresaNew.getMarcaCollection().add(marca);
                empresaNew = em.merge(empresaNew);
            }
            for (Multimedio multimedioCollectionOldMultimedio : multimedioCollectionOld) {
                if (!multimedioCollectionNew.contains(multimedioCollectionOldMultimedio)) {
                    multimedioCollectionOldMultimedio.getMarcaCollection().remove(marca);
                    multimedioCollectionOldMultimedio = em.merge(multimedioCollectionOldMultimedio);
                }
            }
            for (Multimedio multimedioCollectionNewMultimedio : multimedioCollectionNew) {
                if (!multimedioCollectionOld.contains(multimedioCollectionNewMultimedio)) {
                    multimedioCollectionNewMultimedio.getMarcaCollection().add(marca);
                    multimedioCollectionNewMultimedio = em.merge(multimedioCollectionNewMultimedio);
                }
            }
            for (Producto productoCollectionNewProducto : productoCollectionNew) {
                if (!productoCollectionOld.contains(productoCollectionNewProducto)) {
                    Marca oldMarcaOfProductoCollectionNewProducto = productoCollectionNewProducto.getMarca();
                    productoCollectionNewProducto.setMarca(marca);
                    productoCollectionNewProducto = em.merge(productoCollectionNewProducto);
                    if (oldMarcaOfProductoCollectionNewProducto != null && !oldMarcaOfProductoCollectionNewProducto.equals(marca)) {
                        oldMarcaOfProductoCollectionNewProducto.getProductoCollection().remove(productoCollectionNewProducto);
                        oldMarcaOfProductoCollectionNewProducto = em.merge(oldMarcaOfProductoCollectionNewProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = marca.getId();
                if (findMarca(id) == null) {
                    throw new NonexistentEntityException("The marca with id " + id + " no longer exists.");
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
            Marca marca;
            try {
                marca = em.getReference(Marca.class, id);
                marca.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The marca with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Producto> productoCollectionOrphanCheck = marca.getProductoCollection();
            for (Producto productoCollectionOrphanCheckProducto : productoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Marca (" + marca + ") cannot be destroyed since the Producto " + productoCollectionOrphanCheckProducto + " in its productoCollection field has a non-nullable marca field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Linea linea = marca.getLinea();
            if (linea != null) {
                linea.getMarcaCollection().remove(marca);
                linea = em.merge(linea);
            }
            Empresa empresa = marca.getEmpresa();
            if (empresa != null) {
                empresa.getMarcaCollection().remove(marca);
                empresa = em.merge(empresa);
            }
            Collection<Multimedio> multimedioCollection = marca.getMultimedioCollection();
            for (Multimedio multimedioCollectionMultimedio : multimedioCollection) {
                multimedioCollectionMultimedio.getMarcaCollection().remove(marca);
                multimedioCollectionMultimedio = em.merge(multimedioCollectionMultimedio);
            }
            em.remove(marca);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Marca> findMarcaEntities() {
        return findMarcaEntities(true, -1, -1);
    }

    public List<Marca> findMarcaEntities(int maxResults, int firstResult) {
        return findMarcaEntities(false, maxResults, firstResult);
    }

    private List<Marca> findMarcaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Marca as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Marca> resultList = q.getResultList();
            for(Marca x: resultList) {
                Collection<Producto> productoCollection = x.getProductoCollection();
                for(Producto producto: productoCollection){
                }
            }

            return resultList;
        } finally {
            em.close();
        }
    }

    public List<Marca> findMarcaEntitiesByEmpresa(Empresa empresa) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Marca as o where o.empresa.id = :empresaId");

            q.setParameter("empresaId", empresa.getId());

            List<Marca> resultList = q.getResultList();
//            for(Marca x: resultList) {
//                Collection<Producto> productoCollection = x.getProductoCollection();
//                for(Producto producto: productoCollection){
//                }
//            }

            return resultList;
        } finally {
            em.close();
        }
    }

    public List<Marca> findMarcaEntitiesByLinea(Linea linea) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Marca as o where o.linea.id = :lineaId");

            q.setParameter("lineaId", linea.getId());

            List<Marca> resultList = q.getResultList();
//            for(Marca x: resultList) {
//                Collection<Producto> productoCollection = x.getProductoCollection();
//                for(Producto producto: productoCollection){
//                }
//            }

            return resultList;
        } finally {
            em.close();
        }
    }

    public Marca findMarca(Integer id) {
        EntityManager em = getEntityManager();
        try {
            Marca x = em.find(Marca.class, id);
            if( x != null ) {
                Collection<Producto> productoCollection = x.getProductoCollection();
                for(Producto producto: productoCollection){
                }
            }
            return x;
        } finally {
            em.close();
        }
    }

    public int getMarcaCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Marca as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
