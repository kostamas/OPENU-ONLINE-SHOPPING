/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Entities.Stores;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author dell
 */
public class StoreBuilder {

    EntityManagerFactory emf;
    EntityManager em;

    public StoreBuilder() {
        emf = Persistence.createEntityManagerFactory("online_shoppingPU");
        em = emf.createEntityManager();
    }

    public int createStoreId(){
         return em.createNamedQuery("Stores.findAll").getResultList().size();
    }
    
    public int createProductId(){
     return em.createNamedQuery("Products.findAll").getResultList().size();
    }
    
    public boolean save(Object entity){
       em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        em.close();
        return true;
    }
}
