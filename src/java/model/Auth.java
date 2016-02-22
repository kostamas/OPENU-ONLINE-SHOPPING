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

    public boolean loginControl(String username, String password) {
        Login l = em.createNamedQuery("Login.control", Login.class)
                .setParameter("username", username).
                setParameter("password", password)
                .getSingleResult();

        if (l != null) {
            return true;
        }
        return false;
    }

    public boolean registerControl(Object entity, String queryName, String queryParameterName, String parameterValue) {
      
        if(isExists(queryName, queryParameterName, parameterValue)){
            return false;
        }
      
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    private boolean isExists(String queryName, String queryParameterName, String parameterValue){
        int resultSize = em.createNamedQuery(queryName, Users.class)
                            .setParameter(queryParameterName, parameterValue)
                            .getResultList().size();
        if(resultSize > 0){
            return true;
        }
        return false;
    }
}
