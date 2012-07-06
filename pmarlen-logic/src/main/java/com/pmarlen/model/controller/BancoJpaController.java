package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.Banco;
import com.pmarlen.model.controller.exceptions.IllegalOrphanException;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.CuentaBancaria;
import java.util.ArrayList;
import java.util.Collection;

/**
 * BancoJpaController
 */

@Repository("bancoJpaController")

public class BancoJpaController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Banco banco) {
        if (banco.getCuentaBancariaCollection() == null) {
            banco.setCuentaBancariaCollection(new ArrayList<CuentaBancaria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<CuentaBancaria> attachedCuentaBancariaCollection = new ArrayList<CuentaBancaria>();
            for (CuentaBancaria cuentaBancariaCollectionCuentaBancariaToAttach : banco.getCuentaBancariaCollection()) {
                cuentaBancariaCollectionCuentaBancariaToAttach = em.getReference(cuentaBancariaCollectionCuentaBancariaToAttach.getClass(), cuentaBancariaCollectionCuentaBancariaToAttach.getId());
                attachedCuentaBancariaCollection.add(cuentaBancariaCollectionCuentaBancariaToAttach);
            }
            banco.setCuentaBancariaCollection(attachedCuentaBancariaCollection);
            em.persist(banco);
            for (CuentaBancaria cuentaBancariaCollectionCuentaBancaria : banco.getCuentaBancariaCollection()) {
                Banco oldBancoOfCuentaBancariaCollectionCuentaBancaria = cuentaBancariaCollectionCuentaBancaria.getBanco();
                cuentaBancariaCollectionCuentaBancaria.setBanco(banco);
                cuentaBancariaCollectionCuentaBancaria = em.merge(cuentaBancariaCollectionCuentaBancaria);
                if (oldBancoOfCuentaBancariaCollectionCuentaBancaria != null) {
                    oldBancoOfCuentaBancariaCollectionCuentaBancaria.getCuentaBancariaCollection().remove(cuentaBancariaCollectionCuentaBancaria);
                    oldBancoOfCuentaBancariaCollectionCuentaBancaria = em.merge(oldBancoOfCuentaBancariaCollectionCuentaBancaria);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Banco banco) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Banco persistentBanco = em.find(Banco.class, banco.getId());
            Collection<CuentaBancaria> cuentaBancariaCollectionOld = persistentBanco.getCuentaBancariaCollection();
            Collection<CuentaBancaria> cuentaBancariaCollectionNew = banco.getCuentaBancariaCollection();
            List<String> illegalOrphanMessages = null;
            for (CuentaBancaria cuentaBancariaCollectionOldCuentaBancaria : cuentaBancariaCollectionOld) {
                if (!cuentaBancariaCollectionNew.contains(cuentaBancariaCollectionOldCuentaBancaria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CuentaBancaria " + cuentaBancariaCollectionOldCuentaBancaria + " since its banco field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<CuentaBancaria> attachedCuentaBancariaCollectionNew = new ArrayList<CuentaBancaria>();
            for (CuentaBancaria cuentaBancariaCollectionNewCuentaBancariaToAttach : cuentaBancariaCollectionNew) {
                cuentaBancariaCollectionNewCuentaBancariaToAttach = em.getReference(cuentaBancariaCollectionNewCuentaBancariaToAttach.getClass(), cuentaBancariaCollectionNewCuentaBancariaToAttach.getId());
                attachedCuentaBancariaCollectionNew.add(cuentaBancariaCollectionNewCuentaBancariaToAttach);
            }
            cuentaBancariaCollectionNew = attachedCuentaBancariaCollectionNew;
            banco.setCuentaBancariaCollection(cuentaBancariaCollectionNew);
            banco = em.merge(banco);
            for (CuentaBancaria cuentaBancariaCollectionNewCuentaBancaria : cuentaBancariaCollectionNew) {
                if (!cuentaBancariaCollectionOld.contains(cuentaBancariaCollectionNewCuentaBancaria)) {
                    Banco oldBancoOfCuentaBancariaCollectionNewCuentaBancaria = cuentaBancariaCollectionNewCuentaBancaria.getBanco();
                    cuentaBancariaCollectionNewCuentaBancaria.setBanco(banco);
                    cuentaBancariaCollectionNewCuentaBancaria = em.merge(cuentaBancariaCollectionNewCuentaBancaria);
                    if (oldBancoOfCuentaBancariaCollectionNewCuentaBancaria != null && !oldBancoOfCuentaBancariaCollectionNewCuentaBancaria.equals(banco)) {
                        oldBancoOfCuentaBancariaCollectionNewCuentaBancaria.getCuentaBancariaCollection().remove(cuentaBancariaCollectionNewCuentaBancaria);
                        oldBancoOfCuentaBancariaCollectionNewCuentaBancaria = em.merge(oldBancoOfCuentaBancariaCollectionNewCuentaBancaria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = banco.getId();
                if (findBanco(id) == null) {
                    throw new NonexistentEntityException("The banco with id " + id + " no longer exists.");
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
            Banco banco;
            try {
                banco = em.getReference(Banco.class, id);
                banco.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The banco with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CuentaBancaria> cuentaBancariaCollectionOrphanCheck = banco.getCuentaBancariaCollection();
            for (CuentaBancaria cuentaBancariaCollectionOrphanCheckCuentaBancaria : cuentaBancariaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Banco (" + banco + ") cannot be destroyed since the CuentaBancaria " + cuentaBancariaCollectionOrphanCheckCuentaBancaria + " in its cuentaBancariaCollection field has a non-nullable banco field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(banco);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Banco> findBancoEntities() {
        return findBancoEntities(true, -1, -1);
    }

    public List<Banco> findBancoEntities(int maxResults, int firstResult) {
        return findBancoEntities(false, maxResults, firstResult);
    }

    private List<Banco> findBancoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Banco as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Banco findBanco(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Banco.class, id);
        } finally {
            em.close();
        }
    }

    public int getBancoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Banco as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
