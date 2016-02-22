/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Entities.Users;
import javax.persistence.*;

public class Auth {

    EntityManagerFactory emf;
    EntityManager em;

    public Auth() {
        emf = Persistence.createEntityManagerFactory("online_shoppingPU");
        em = emf.createEntityManager();

    }

    public boolean loginControl(String queryName, String firstQueryParameterName, String firstParameterValue, String secondQueryParameterName, String secondParameterValue) {
        if(isLoginExists(queryName, firstQueryParameterName, firstParameterValue, secondQueryParameterName, secondParameterValue)){
            return true;
        }
        return false;
    }

    public boolean registerControl(Object entity, String queryName, String queryParameterName, String parameterValue) {

        if (isRegisterExists(queryName, queryParameterName, parameterValue)) {
            return false;
        }

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        em.close();
        return true;
    }

    private boolean isRegisterExists(String queryName, String queryParameterName, String parameterValue) {
        int resultSize = em.createNamedQuery(queryName)
                .setParameter(queryParameterName, parameterValue)
                .getResultList().size();
        if (resultSize > 0) {
            return true;
        }
        return false;
    }
    // todo- check if can combine this two functions isRegisterExists & isLoginExists

    private boolean isLoginExists(String queryName, String firstQueryParameterName, String firstParameterValue, String secondQueryParameterName, String secondParameterValue) {   ///   ugly parameters !!!
        int resultSize = em.createNamedQuery(queryName)
                .setParameter(firstQueryParameterName, firstParameterValue)
                .setParameter(secondQueryParameterName, secondParameterValue)
                .getResultList().size();
        if (resultSize > 0) {
            return true;
        }
        return false;
    }
}