/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Entities.exceptions.NonexistentEntityException;
import Entities.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author dell
 */
public class UsersCartJpaController implements Serializable {

    public UsersCartJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsersCart usersCart) throws PreexistingEntityException, Exception {
        if (usersCart.getUsersCartPK() == null) {
            usersCart.setUsersCartPK(new UsersCartPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(usersCart);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsersCart(usersCart.getUsersCartPK()) != null) {
                throw new PreexistingEntityException("UsersCart " + usersCart + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsersCart usersCart) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            usersCart = em.merge(usersCart);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                UsersCartPK id = usersCart.getUsersCartPK();
                if (findUsersCart(id) == null) {
                    throw new NonexistentEntityException("The usersCart with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(UsersCartPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UsersCart usersCart;
            try {
                usersCart = em.getReference(UsersCart.class, id);
                usersCart.getUsersCartPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usersCart with id " + id + " no longer exists.", enfe);
            }
            em.remove(usersCart);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UsersCart> findUsersCartEntities() {
        return findUsersCartEntities(true, -1, -1);
    }

    public List<UsersCart> findUsersCartEntities(int maxResults, int firstResult) {
        return findUsersCartEntities(false, maxResults, firstResult);
    }

    private List<UsersCart> findUsersCartEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsersCart.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public UsersCart findUsersCart(UsersCartPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsersCart.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsersCartCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsersCart> rt = cq.from(UsersCart.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
