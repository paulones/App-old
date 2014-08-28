/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.BemHistorico;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidade.ProcessoJudicialHistorico;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author paulones
 */
public class BemHistoricoDAO implements Serializable {

    public BemHistoricoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BemHistorico bemHistorico) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            ProcessoJudicialHistorico processoJudicialHistoricoFk = bemHistorico.getProcessoJudicialHistoricoFk();
            if (processoJudicialHistoricoFk != null) {
                processoJudicialHistoricoFk = em.getReference(processoJudicialHistoricoFk.getClass(), processoJudicialHistoricoFk.getId());
                bemHistorico.setProcessoJudicialHistoricoFk(processoJudicialHistoricoFk);
            }
            em.persist(bemHistorico);
            if (processoJudicialHistoricoFk != null) {
                processoJudicialHistoricoFk.getBemHistoricoCollection().add(bemHistorico);
                processoJudicialHistoricoFk = em.merge(processoJudicialHistoricoFk);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BemHistorico bemHistorico) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            BemHistorico persistentBemHistorico = em.find(BemHistorico.class, bemHistorico.getId());
            ProcessoJudicialHistorico processoJudicialHistoricoFkOld = persistentBemHistorico.getProcessoJudicialHistoricoFk();
            ProcessoJudicialHistorico processoJudicialHistoricoFkNew = bemHistorico.getProcessoJudicialHistoricoFk();
            if (processoJudicialHistoricoFkNew != null) {
                processoJudicialHistoricoFkNew = em.getReference(processoJudicialHistoricoFkNew.getClass(), processoJudicialHistoricoFkNew.getId());
                bemHistorico.setProcessoJudicialHistoricoFk(processoJudicialHistoricoFkNew);
            }
            bemHistorico = em.merge(bemHistorico);
            if (processoJudicialHistoricoFkOld != null && !processoJudicialHistoricoFkOld.equals(processoJudicialHistoricoFkNew)) {
                processoJudicialHistoricoFkOld.getBemHistoricoCollection().remove(bemHistorico);
                processoJudicialHistoricoFkOld = em.merge(processoJudicialHistoricoFkOld);
            }
            if (processoJudicialHistoricoFkNew != null && !processoJudicialHistoricoFkNew.equals(processoJudicialHistoricoFkOld)) {
                processoJudicialHistoricoFkNew.getBemHistoricoCollection().add(bemHistorico);
                processoJudicialHistoricoFkNew = em.merge(processoJudicialHistoricoFkNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bemHistorico.getId();
                if (findBemHistorico(id) == null) {
                    throw new NonexistentEntityException("The bemHistorico with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            BemHistorico bemHistorico;
            try {
                bemHistorico = em.getReference(BemHistorico.class, id);
                bemHistorico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bemHistorico with id " + id + " no longer exists.", enfe);
            }
            ProcessoJudicialHistorico processoJudicialHistoricoFk = bemHistorico.getProcessoJudicialHistoricoFk();
            if (processoJudicialHistoricoFk != null) {
                processoJudicialHistoricoFk.getBemHistoricoCollection().remove(bemHistorico);
                processoJudicialHistoricoFk = em.merge(processoJudicialHistoricoFk);
            }
            em.remove(bemHistorico);
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BemHistorico> findBemHistoricoEntities() {
        return findBemHistoricoEntities(true, -1, -1);
    }

    public List<BemHistorico> findBemHistoricoEntities(int maxResults, int firstResult) {
        return findBemHistoricoEntities(false, maxResults, firstResult);
    }

    private List<BemHistorico> findBemHistoricoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BemHistorico.class));
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

    public BemHistorico findBemHistorico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BemHistorico.class, id);
        } finally {
            em.close();
        }
    }

    public int getBemHistoricoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BemHistorico> rt = cq.from(BemHistorico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
