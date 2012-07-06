package com.pmarlen.model.controller;

import com.pmarlen.model.beans.Almacen;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * AlmacenJpaController
 */
@Transactional
@Repository
public class AlmacenJpaDao{

    @PersistenceContext
    private EntityManager em = null;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
    
    public Almacen findById(long id) {
        return em.find(Almacen.class, id);
    }

    
    public void save(Almacen Almacen) {
        System.err.println("========>>>AlmacenJpaDao Almacen persist ! ");
        em.persist(Almacen);
        System.err.println("========>>>AlmacenJpaDao Almacen persist ?? ");
    }

    
    public Almacen update(Almacen Almacen) {
        return em.merge(Almacen);
    }

    
    public void delete(Almacen Almacen) {
        em.remove(Almacen);
    }

}
