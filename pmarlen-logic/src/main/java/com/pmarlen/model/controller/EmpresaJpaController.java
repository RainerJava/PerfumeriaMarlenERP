package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.Empresa;
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
 * EmpresaJpaController
 */

@Repository("empresaJpaController")

public class EmpresaJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empresa empresa) {
        if (empresa.getMultimedioCollection() == null) {
            empresa.setMultimedioCollection(new ArrayList<Multimedio>());
        }
        if (empresa.getMarcaCollection() == null) {
            empresa.setMarcaCollection(new ArrayList<Marca>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Multimedio> attachedMultimedioCollection = new ArrayList<Multimedio>();
            for (Multimedio multimedioCollectionMultimedioToAttach : empresa.getMultimedioCollection()) {
                multimedioCollectionMultimedioToAttach = em.getReference(multimedioCollectionMultimedioToAttach.getClass(), multimedioCollectionMultimedioToAttach.getId());
                attachedMultimedioCollection.add(multimedioCollectionMultimedioToAttach);
            }
            empresa.setMultimedioCollection(attachedMultimedioCollection);
            Collection<Marca> attachedMarcaCollection = new ArrayList<Marca>();
            for (Marca marcaCollectionMarcaToAttach : empresa.getMarcaCollection()) {
                marcaCollectionMarcaToAttach = em.getReference(marcaCollectionMarcaToAttach.getClass(), marcaCollectionMarcaToAttach.getId());
                attachedMarcaCollection.add(marcaCollectionMarcaToAttach);
            }
            empresa.setMarcaCollection(attachedMarcaCollection);
            em.persist(empresa);
            for (Multimedio multimedioCollectionMultimedio : empresa.getMultimedioCollection()) {
                multimedioCollectionMultimedio.getEmpresaCollection().add(empresa);
                multimedioCollectionMultimedio = em.merge(multimedioCollectionMultimedio);
            }
            for (Marca marcaCollectionMarca : empresa.getMarcaCollection()) {
                Empresa oldEmpresaOfMarcaCollectionMarca = marcaCollectionMarca.getEmpresa();
                marcaCollectionMarca.setEmpresa(empresa);
                marcaCollectionMarca = em.merge(marcaCollectionMarca);
                if (oldEmpresaOfMarcaCollectionMarca != null) {
                    oldEmpresaOfMarcaCollectionMarca.getMarcaCollection().remove(marcaCollectionMarca);
                    oldEmpresaOfMarcaCollectionMarca = em.merge(oldEmpresaOfMarcaCollectionMarca);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empresa empresa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa persistentEmpresa = em.find(Empresa.class, empresa.getId());
            Collection<Multimedio> multimedioCollectionOld = persistentEmpresa.getMultimedioCollection();
            Collection<Multimedio> multimedioCollectionNew = empresa.getMultimedioCollection();
            Collection<Marca> marcaCollectionOld = persistentEmpresa.getMarcaCollection();
            Collection<Marca> marcaCollectionNew = empresa.getMarcaCollection();
            List<String> illegalOrphanMessages = null;
            for (Marca marcaCollectionOldMarca : marcaCollectionOld) {
                if (!marcaCollectionNew.contains(marcaCollectionOldMarca)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Marca " + marcaCollectionOldMarca + " since its empresa field is not nullable.");
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
            empresa.setMultimedioCollection(multimedioCollectionNew);
            Collection<Marca> attachedMarcaCollectionNew = new ArrayList<Marca>();
            for (Marca marcaCollectionNewMarcaToAttach : marcaCollectionNew) {
                marcaCollectionNewMarcaToAttach = em.getReference(marcaCollectionNewMarcaToAttach.getClass(), marcaCollectionNewMarcaToAttach.getId());
                attachedMarcaCollectionNew.add(marcaCollectionNewMarcaToAttach);
            }
            marcaCollectionNew = attachedMarcaCollectionNew;
            empresa.setMarcaCollection(marcaCollectionNew);
            empresa = em.merge(empresa);
            for (Multimedio multimedioCollectionOldMultimedio : multimedioCollectionOld) {
                if (!multimedioCollectionNew.contains(multimedioCollectionOldMultimedio)) {
                    multimedioCollectionOldMultimedio.getEmpresaCollection().remove(empresa);
                    multimedioCollectionOldMultimedio = em.merge(multimedioCollectionOldMultimedio);
                }
            }
            for (Multimedio multimedioCollectionNewMultimedio : multimedioCollectionNew) {
                if (!multimedioCollectionOld.contains(multimedioCollectionNewMultimedio)) {
                    multimedioCollectionNewMultimedio.getEmpresaCollection().add(empresa);
                    multimedioCollectionNewMultimedio = em.merge(multimedioCollectionNewMultimedio);
                }
            }
            for (Marca marcaCollectionNewMarca : marcaCollectionNew) {
                if (!marcaCollectionOld.contains(marcaCollectionNewMarca)) {
                    Empresa oldEmpresaOfMarcaCollectionNewMarca = marcaCollectionNewMarca.getEmpresa();
                    marcaCollectionNewMarca.setEmpresa(empresa);
                    marcaCollectionNewMarca = em.merge(marcaCollectionNewMarca);
                    if (oldEmpresaOfMarcaCollectionNewMarca != null && !oldEmpresaOfMarcaCollectionNewMarca.equals(empresa)) {
                        oldEmpresaOfMarcaCollectionNewMarca.getMarcaCollection().remove(marcaCollectionNewMarca);
                        oldEmpresaOfMarcaCollectionNewMarca = em.merge(oldEmpresaOfMarcaCollectionNewMarca);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empresa.getId();
                if (findEmpresa(id) == null) {
                    throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.");
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
            Empresa empresa;
            try {
                empresa = em.getReference(Empresa.class, id);
                empresa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Marca> marcaCollectionOrphanCheck = empresa.getMarcaCollection();
            for (Marca marcaCollectionOrphanCheckMarca : marcaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresa (" + empresa + ") cannot be destroyed since the Marca " + marcaCollectionOrphanCheckMarca + " in its marcaCollection field has a non-nullable empresa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Multimedio> multimedioCollection = empresa.getMultimedioCollection();
            for (Multimedio multimedioCollectionMultimedio : multimedioCollection) {
                multimedioCollectionMultimedio.getEmpresaCollection().remove(empresa);
                multimedioCollectionMultimedio = em.merge(multimedioCollectionMultimedio);
            }
            em.remove(empresa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empresa> findEmpresaEntities() {
        return findEmpresaEntities(true, -1, -1);
    }

    public List<Empresa> findEmpresaEntities(int maxResults, int firstResult) {
        return findEmpresaEntities(false, maxResults, firstResult);
    }

    private List<Empresa> findEmpresaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Empresa as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Empresa> resultList = q.getResultList();
            for(Empresa x: resultList) {
//                Collection<Marca> marcaCollection = x.getMarcaCollection();
//                for(Marca marca :marcaCollection) {
//                }
            }
            return resultList;
        } finally {
            em.close();
        }
    }

    public Empresa findEmpresa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            Empresa x = em.find(Empresa.class, id);
            if(x != null ){
                Collection<Marca> marcaCollection = x.getMarcaCollection();
                for(Marca marca :marcaCollection) {
                }
            }
            return x;
        } finally {
            em.close();
        }
    }

    public int getEmpresaCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Empresa as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
