/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Entities.Stores;
import Entities.UsersCart;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UserCartQuary {

    EntityManagerFactory emf;
    EntityManager em;

    public UserCartQuary() {
        emf = Persistence.createEntityManagerFactory("online_shoppingPU");
        em = emf.createEntityManager();
    }

    public List<UsersCart> getUserProduct(String userName, int prodId) {
        return em.createNamedQuery("UsersCart.findUserCart")
                .setParameter("userName", userName)
                .setParameter("productId", prodId)
                .getResultList();
    }

    static public List<UsersCart> getUserCart(String userName) {
        EntityManagerFactory emfTemp;
        EntityManager emTemp;
        emfTemp = Persistence.createEntityManagerFactory("online_shoppingPU");
        emTemp = emfTemp.createEntityManager();
        return emTemp.createNamedQuery("UsersCart.findByUserName")
                .setParameter("userName", userName)
                .getResultList();
    }
    
      static public String getAdminName(int storeId) {
        EntityManagerFactory emfTemp;
        EntityManager emTemp;
        emfTemp = Persistence.createEntityManagerFactory("online_shoppingPU");
        emTemp = emfTemp.createEntityManager();
        List<Stores> store = emTemp.createNamedQuery("Stores.findByStoreId")
                .setParameter("storeId", storeId)
                .getResultList();
        return store.get(0).getStoreAdmin();
    }
}
