/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Entities.Products;
import Entities.Stores;
import java.util.List;
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

    public int createStoreId() {
        em.createNamedQuery("Stores.findAll").getResultList().size();
        int numberOfStores = em.createNamedQuery("Stores.findAll").getResultList().size();
        for (int i = 1; i < numberOfStores + 1; i++) {
            int resultSize = em.createNamedQuery("Stores.findByStoreId").setParameter("storeId", i).getResultList().size();
            if (resultSize == 0) {
                return i;
            }
        }
        return numberOfStores + 1;
    }

    public int createProductId() {

        int numberOfProducts = em.createNamedQuery("Products.findAll").getResultList().size();
        for (int i = 1; i < numberOfProducts + 1; i++) {
            int resultSize = em.createNamedQuery("Products.findByProductId").setParameter("productId", i).getResultList().size();
            if (resultSize == 0) {
                return i;
            }
        }
        return numberOfProducts + 1;
    }

    public boolean save(Object entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        em.close();
        return true;
    }
}
