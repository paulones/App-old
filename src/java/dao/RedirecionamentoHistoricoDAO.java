/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidade.ProcessoJudicialHistorico;
import entidade.RedirecionamentoHistorico;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author paulones
 */
public class RedirecionamentoHistoricoDAO implements Serializable {

    public RedirecionamentoHistoricoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RedirecionamentoHistorico redirecionamentoHistorico) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            ProcessoJudicialHistorico processoJudicialHistoricoFk = redirecionamentoHistorico.getProcessoJudicialHistoricoFk();
            if (processoJudicialHistoricoFk != null) {
                processoJudicialHistoricoFk = em.getReference(processoJudicialHistoricoFk.getClass(), processoJudicialHistoricoFk.getId());
                redirecionamentoHistorico.setProcessoJudicialHistoricoFk(processoJudicialHistoricoFk);
            }
            em.persist(redirecionamentoHistorico);
            if (processoJudicialHistoricoFk != null) {
                processoJudicialHistoricoFk.getRedirecionamentoHistoricoCollection().add(redirecionamentoHistorico);
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

    public void edit(RedirecionamentoHistorico redirecionamentoHistorico) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            RedirecionamentoHistorico persistentRedirecionamentoHistorico = em.find(RedirecionamentoHistorico.class, redirecionamentoHistorico.getId());
            ProcessoJudicialHistorico processoJudicialHistoricoFkOld = persistentRedirecionamentoHistorico.getProcessoJudicialHistoricoFk();
            ProcessoJudicialHistorico processoJudicialHistoricoFkNew = redirecionamentoHistorico.getProcessoJudicialHistoricoFk();
            if (processoJudicialHistoricoFkNew != null) {
                processoJudicialHistoricoFkNew = em.getReference(processoJudicialHistoricoFkNew.getClass(), processoJudicialHistoricoFkNew.getId());
                redirecionamentoHistorico.setProcessoJudicialHistoricoFk(processoJudicialHistoricoFkNew);
            }
            redirecionamentoHistorico = em.merge(redirecionamentoHistorico);
            if (processoJudicialHistoricoFkOld != null && !processoJudicialHistoricoFkOld.equals(processoJudicialHistoricoFkNew)) {
                processoJudicialHistoricoFkOld.getRedirecionamentoHistoricoCollection().remove(redirecionamentoHistorico);
                processoJudicialHistoricoFkOld = em.merge(processoJudicialHistoricoFkOld);
            }
            if (processoJudicialHistoricoFkNew != null && !processoJudicialHistoricoFkNew.equals(processoJudicialHistoricoFkOld)) {
                processoJudicialHistoricoFkNew.getRedirecionamentoHistoricoCollection().add(redirecionamentoHistorico);
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
                Integer id = redirecionamentoHistorico.getId();
                if (findRedirecionamentoHistorico(id) == null) {
                    throw new NonexistentEntityException("The redirecionamentoHistorico with id " + id + " no longer exists.");
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
            RedirecionamentoHistorico redirecionamentoHistorico;
            try {
                redirecionamentoHistorico = em.getReference(RedirecionamentoHistorico.class, id);
                redirecionamentoHistorico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The redirecionamentoHistorico with id " + id + " no longer exists.", enfe);
            }
            ProcessoJudicialHistorico processoJudicialHistoricoFk = redirecionamentoHistorico.getProcessoJudicialHistoricoFk();
            if (processoJudicialHistoricoFk != null) {
                processoJudicialHistoricoFk.getRedirecionamentoHistoricoCollection().remove(redirecionamentoHistorico);
                processoJudicialHistoricoFk = em.merge(processoJudicialHistoricoFk);
            }
            em.remove(redirecionamentoHistorico);
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

    public List<RedirecionamentoHistorico> findRedirecionamentoHistoricoEntities() {
        return findRedirecionamentoHistoricoEntities(true, -1, -1);
    }

    public List<RedirecionamentoHistorico> findRedirecionamentoHistoricoEntities(int maxResults, int firstResult) {
        return findRedirecionamentoHistoricoEntities(false, maxResults, firstResult);
    }

    private List<RedirecionamentoHistorico> findRedirecionamentoHistoricoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RedirecionamentoHistorico.class));
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

    public RedirecionamentoHistorico findRedirecionamentoHistorico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RedirecionamentoHistorico.class, id);
        } finally {
            em.close();
        }
    }

    public int getRedirecionamentoHistoricoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RedirecionamentoHistorico> rt = cq.from(RedirecionamentoHistorico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
