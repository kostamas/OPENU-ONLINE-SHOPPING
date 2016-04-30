/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Entities.ProductsSold;
import Entities.Transactions;
import controller.TransactionsHistory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import static org.eclipse.persistence.sessions.SessionProfiler.Transaction;

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

    public void savSoldProduct(ProductsSold productSold) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(productSold);
        em.getTransaction().commit();
        em.close();
    }

    public List<TransactionsHistory> getUserHistory(String userName) {
        em = emf.createEntityManager();
        List<Transactions> transactionList = em.createNamedQuery("Transactions.findByUserName")
                .setParameter("userName", userName)
                .getResultList();
        List<ProductsSold> productsSold = em.createNamedQuery("ProductsSold.findAll")
                .getResultList();
        List<TransactionsHistory> userHistory = new ArrayList<TransactionsHistory>();
        List<ProductsSold> tempProductsSold = new ArrayList<ProductsSold>();

        for (Transactions transaction : transactionList) {
            for (ProductsSold product : productsSold) {
                if (product.getProductsSoldPK().getTransactionId() == transaction.getTransactionId()) {
                    tempProductsSold.add(product);
                }
            }
            String date = transaction.getTransactionDate();
            String transactionUserName = transaction.getUserName();
            userHistory.add(new TransactionsHistory(tempProductsSold, date, transactionUserName));
            tempProductsSold = new ArrayList<ProductsSold>();
        }

        return userHistory;
    }

    public List<TransactionsHistory> getAdminHistory(String adminName) {
        em = emf.createEntityManager();
        List<ProductsSold> productsSoldList = em.createNamedQuery("ProductsSold.findByAdminName")
                .setParameter("adminName", adminName)
                .getResultList();

        List<Transactions> transactionsList = em.createNamedQuery("Transactions.findAll")
                .getResultList();

        Map<Integer, TransactionsHistory> historyMap = new HashMap<Integer, TransactionsHistory>();

        for (ProductsSold product : productsSoldList) {
            for (Transactions transaction : transactionsList) {
                int transactionId = product.getProductsSoldPK().getTransactionId();
                if (transactionId == transaction.getTransactionId()) {
                    if(historyMap.containsKey(transactionId)){
                        historyMap.get(transactionId).addProductSold(product);
                    } else {
                        ArrayList<ProductsSold> tempProductsSold = new ArrayList<ProductsSold>();
                        tempProductsSold.add(product);
                        historyMap.put(transactionId, new TransactionsHistory(tempProductsSold, transaction.getTransactionDate(),transaction.getUserName()));
                    }
                }
            }
        }
        return  new ArrayList<TransactionsHistory>(historyMap.values());
    }
}
