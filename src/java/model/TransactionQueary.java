/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Entities.ProductsSold;
import Entities.Transactions;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import static org.eclipse.persistence.sessions.SessionProfiler.Transaction;

/**
 *
 * @author dell
 */
public class TransactionQueary {

    EntityManagerFactory emf;
    EntityManager em;

    public TransactionQueary() {
        emf = Persistence.createEntityManagerFactory("online_shoppingPU");
    }

    public int createTransactionId() {
        em = emf.createEntityManager();
        int numberOfStores = em.createNamedQuery("Transactions.findAll").getResultList().size();
        for (int i = 1; i < numberOfStores + 1; i++) {
            int resultSize = em.createNamedQuery("Transactions.findByTransactionId").setParameter("transactionId", i).getResultList().size();
            if (resultSize == 0) {
                return i;
            }
        }
        return numberOfStores + 1;
    }

    public void saveTransaction(Transactions transaction) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(transaction);
        em.getTransaction().commit();
        em.close();
    }
    
    public void savSoldProduct(ProductsSold productSold){
     em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(productSold);
        em.getTransaction().commit();
        em.close();
    }
}
