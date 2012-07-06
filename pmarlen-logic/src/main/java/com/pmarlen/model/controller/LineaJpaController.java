package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.Linea;
import com.pmarlen.model.controller.exceptions.IllegalOrphanException;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.Multimedio;
import java.util.ArrayList;
import java.util.Collection;
import com.pmarlen.model.beans.Marca;

/**
 * LineaJpaController
 */

@Repository("lineaJpaController")

public class LineaJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Linea linea) {
        if (linea.getMultimedioCollection() == null) {
            linea.setMultimedioCollection(new ArrayList<Multimedio>());
        }
        if (linea.getMarcaCollection() == null) {
            linea.setMarcaCollection(new ArrayList<Marca>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Multimedio> attachedMultimedioCollection = new ArrayList<Multimedio>();
            for (Multimedio multimedioCollectionMultimedioToAttach : linea.getMultimedioCollection()) {
                multimedioCollectionMultimedioToAttach = em.getReference(multimedioCollectionMultimedioToAttach.getClass(), multimedioCollectionMultimedioToAttach.getId());
                attachedMultimedioCollection.add(multimedioCollectionMultimedioToAttach);
            }
            linea.setMultimedioCollection(attachedMultimedioCollection);
            Collection<Marca> attachedMarcaCollection = new ArrayList<Marca>();
            for (Marca marcaCollectionMarcaToAttach : linea.getMarcaCollection()) {
                marcaCollectionMarcaToAttach = em.getReference(marcaCollectionMarcaToAttach.getClass(), marcaCollectionMarcaToAttach.getId());
                attachedMarcaCollection.add(marcaCollectionMarcaToAttach);
            }
            linea.setMarcaCollection(attachedMarcaCollection);
            em.persist(linea);
            for (Multimedio multimedioCollectionMultimedio : linea.getMultimedioCollection()) {
                multimedioCollectionMultimedio.getLineaCollection().add(linea);
                multimedioCollectionMultimedio = em.merge(multimedioCollectionMultimedio);
            }
            for (Marca marcaCollectionMarca : linea.getMarcaCollection()) {
                Linea oldLineaOfMarcaCollectionMarca = marcaCollectionMarca.getLinea();
                marcaCollectionMarca.setLinea(linea);
                marcaCollectionMarca = em.merge(marcaCollectionMarca);
                if (oldLineaOfMarcaCollectionMarca != null) {
                    oldLineaOfMarcaCollectionMarca.getMarcaCollection().remove(marcaCollectionMarca);
                    oldLineaOfMarcaCollectionMarca = em.merge(oldLineaOfMarcaCollectionMarca);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Linea linea) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Linea persistentLinea = em.find(Linea.class, linea.getId());
            Collection<Multimedio> multimedioCollectionOld = persistentLinea.getMultimedioCollection();
            Collection<Multimedio> multimedioCollectionNew = linea.getMultimedioCollection();
            Collection<Marca> marcaCollectionOld = persistentLinea.getMarcaCollection();
            Collection<Marca> marcaCollectionNew = linea.getMarcaCollection();
            List<String> illegalOrphanMessages = null;
            for (Marca marcaCollectionOldMarca : marcaCollectionOld) {
                if (!marcaCollectionNew.contains(marcaCollectionOldMarca)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Marca " + marcaCollectionOldMarca + " since its linea field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Multimedio> attachedMultimedioCollectionNew = new ArrayList<Multimedio>();
            for (Multimedio multimedioCollectionNewMultimedioToAttach : multimedioCollectionNew) {
                multimedioCollectionNewMultimedioToAttach = em.getReference(multimedioCollectionNewMultimedioToAttach.getClass(), multimedioCollectionNewMultimedioToAttach.getId());
                attachedMultimedioCollectionNew.add(multimedioCollectionNewMultimedioToAttach);
            }
            multimedioCollectionNew = attachedMultimedioCollectionNew;
            linea.setMultimedioCollection(multimedioCollectionNew);
            Collection<Marca> attachedMarcaCollectionNew = new ArrayList<Marca>();
            for (Marca marcaCollectionNewMarcaToAttach : marcaCollectionNew) {
                marcaCollectionNewMarcaToAttach = em.getReference(marcaCollectionNewMarcaToAttach.getClass(), marcaCollectionNewMarcaToAttach.getId());
                attachedMarcaCollectionNew.add(marcaCollectionNewMarcaToAttach);
            }
            marcaCollectionNew = attachedMarcaCollectionNew;
            linea.setMarcaCollection(marcaCollectionNew);
            linea = em.merge(linea);
            for (Multimedio multimedioCollectionOldMultimedio : multimedioCollectionOld) {
                if (!multimedioCollectionNew.contains(multimedioCollectionOldMultimedio)) {
                    multimedioCollectionOldMultimedio.getLineaCollection().remove(linea);
                    multimedioCollectionOldMultimedio = em.merge(multimedioCollectionOldMultimedio);
                }
            }
            for (Multimedio multimedioCollectionNewMultimedio : multimedioCollectionNew) {
                if (!multimedioCollectionOld.contains(multimedioCollectionNewMultimedio)) {
                    multimedioCollectionNewMultimedio.getLineaCollection().add(linea);
                    multimedioCollectionNewMultimedio = em.merge(multimedioCollectionNewMultimedio);
                }
            }
            for (Marca marcaCollectionNewMarca : marcaCollectionNew) {
                if (!marcaCollectionOld.contains(marcaCollectionNewMarca)) {
                    Linea oldLineaOfMarcaCollectionNewMarca = marcaCollectionNewMarca.getLinea();
                    marcaCollectionNewMarca.setLinea(linea);
                    marcaCollectionNewMarca = em.merge(marcaCollectionNewMarca);
                    if (oldLineaOfMarcaCollectionNewMarca != null && !oldLineaOfMarcaCollectionNewMarca.equals(linea)) {
                        oldLineaOfMarcaCollectionNewMarca.getMarcaCollection().remove(marcaCollectionNewMarca);
                        oldLineaOfMarcaCollectionNewMarca = em.merge(oldLineaOfMarcaCollectionNewMarca);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = linea.getId();
                if (findLinea(id) == null) {
                    throw new NonexistentEntityException("The linea with id " + id + " no longer exists.");
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
            Linea linea;
            try {
                linea = em.getReference(Linea.class, id);
                linea.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The linea with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Marca> marcaCollectionOrphanCheck = linea.getMarcaCollection();
            for (Marca marcaCollectionOrphanCheckMarca : marcaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Linea (" + linea + ") cannot be destroyed since the Marca " + marcaCollectionOrphanCheckMarca + " in its marcaCollection field has a non-nullable linea field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Multimedio> multimedioCollection = linea.getMultimedioCollection();
            for (Multimedio multimedioCollectionMultimedio : multimedioCollection) {
                multimedioCollectionMultimedio.getLineaCollection().remove(linea);
                multimedioCollectionMultimedio = em.merge(multimedioCollectionMultimedio);
            }
            em.remove(linea);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Linea> findLineaEntities() {
        return findLineaEntities(true, -1, -1);
    }

    public List<Linea> findLineaEntities(int maxResults, int firstResult) {
        return findLineaEntities(false, maxResults, firstResult);
    }

    private List<Linea> findLineaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Linea as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Linea> resultList = q.getResultList();
            for(Linea x: resultList) {
//                Collection<Marca> marcaCollection = x.getMarcaCollection();
//                for(Marca marca :marcaCollection) {
//                }
            }
            return resultList;
        } finally {
            em.close();
        }
    }

    public Linea findLinea(Integer id) {
        EntityManager em = getEntityManager();
        try {
            Linea x = em.find(Linea.class, id);
            if( x  != null ) {
                Collection<Marca> marcaCollection = x.getMarcaCollection();
                for(Marca marca :marcaCollection) {
                }
            }
            return x;
        } finally {
            em.close();
        }
    }


    public int getLineaCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Linea as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
