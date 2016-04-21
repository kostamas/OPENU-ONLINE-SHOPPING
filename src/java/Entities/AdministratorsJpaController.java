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
public class AdministratorsJpaController implements Serializable {

    public AdministratorsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Administrators administrators) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(administrators);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAdministrators(administrators.getAdminName()) != null) {
                throw new PreexistingEntityException("Administrators " + administrators + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Administrators administrators) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            administrators = em.merge(administrators);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = administrators.getAdminName();
                if (findAdministrators(id) == null) {
                    throw new NonexistentEntityException("The administrators with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Administrators administrators;
            try {
                administrators = em.getReference(Administrators.class, id);
                administrators.getAdminName();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The administrators with id " + id + " no longer exists.", enfe);
            }
            em.remove(administrators);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Administrators> findAdministratorsEntities() {
        return findAdministratorsEntities(true, -1, -1);
    }

    public List<Administrators> findAdministratorsEntities(int maxResults, int firstResult) {
        return findAdministratorsEntities(false, maxResults, firstResult);
    }

    private List<Administrators> findAdministratorsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Administrators.class));
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

    public Administrators findAdministrators(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Administrators.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdministratorsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Administrators> rt = cq.from(Administrators.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
