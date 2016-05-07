/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Entities.Products;
import Entities.ProductsSold;
import Entities.Stores;
import Entities.Transactions;
import java.util.ArrayList;
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

    public List<Products> getProductByProductId(int prodId) {
        return em.createNamedQuery("Products.findByProductId")
                .setParameter("productId", prodId)
                .getResultList();
    }

    public List<Products> getProductsWithQuantity(int storeId) {
        return em.createNamedQuery("Products.findByProductIdWithQuantity")
                .setParameter("storeId", storeId)
                .getResultList();
    }

    public List<ProductsSold> getAdminHistory(String adminName) {   
        em = emf.createEntityManager();                         
   
        List<ProductsSold> productsSold = em.createNamedQuery("ProductsSold.findByAdminName")
                .getResultList();
        return productsSold;
    }
}
