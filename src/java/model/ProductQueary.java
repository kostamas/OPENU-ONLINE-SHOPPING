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

/**
 *
 * @author dell
 */
public class ProductQueary {

    EntityManagerFactory emf;
    EntityManager em;

    public ProductQueary() {
        emf = Persistence.createEntityManagerFactory("online_shoppingPU");
        em = emf.createEntityManager();
    }

    public List<Products> getProductsByStoreId(int storeId) {
        return em.createNamedQuery("Products.findByStoreId")
                .setParameter("storeId", storeId)
                .getResultList();
    }
}
