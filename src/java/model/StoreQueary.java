/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Entities.Administrators;
import Entities.Stores;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author dell
 */
public class StoreQueary {

    EntityManagerFactory emf;
    EntityManager em;

    public StoreQueary() {
        emf = Persistence.createEntityManagerFactory("online_shoppingPU");
        em = emf.createEntityManager();
    }

    public List<Stores> getStoresByAdmin(String adminName) {
        return em.createNamedQuery("Stores.findByAdmin")
                .setParameter("storeAdmin", adminName)
                .getResultList();
    }

    public List<Stores> getAllStores() {
        return em.createNamedQuery("Stores.findAll")
                .getResultList();
    }
    
    public List<Administrators> getAdmin(String adminName){
        return em.createNamedQuery("Administrators.findByAdminName")
                .setParameter("adminName", adminName)
                .getResultList();
    }
    
    public int getAdminCreditCard(String AdminName){
        List<Administrators> admin = getAdmin(AdminName);
        return admin.get(0).getCredit();
    }
}
