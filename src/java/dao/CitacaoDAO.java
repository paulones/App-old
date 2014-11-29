/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.Citacao;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidade.ProcessoJudicial;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author paulones
 */
public class CitacaoDAO implements Serializable {

    public CitacaoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Citacao citacao) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            ProcessoJudicial processoJudicialFk = citacao.getProcessoJudicialFk();
            if (processoJudicialFk != null) {
                processoJudicialFk = em.getReference(processoJudicialFk.getClass(), processoJudicialFk.getId());
                citacao.setProcessoJudicialFk(processoJudicialFk);
            }
            em.persist(citacao);
            if (processoJudicialFk != null) {
                processoJudicialFk.getCitacaoCollection().add(citacao);
                processoJudicialFk = em.merge(processoJudicialFk);
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

    public void edit(Citacao citacao) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            Citacao persistentCitacao = em.find(Citacao.class, citacao.getId());
            ProcessoJudicial processoJudicialFkOld = persistentCitacao.getProcessoJudicialFk();
            ProcessoJudicial processoJudicialFkNew = citacao.getProcessoJudicialFk();
            if (processoJudicialFkNew != null) {
                processoJudicialFkNew = em.getReference(processoJudicialFkNew.getClass(), processoJudicialFkNew.getId());
                citacao.setProcessoJudicialFk(processoJudicialFkNew);
            }
            citacao = em.merge(citacao);
            if (processoJudicialFkOld != null && !processoJudicialFkOld.equals(processoJudicialFkNew)) {
                processoJudicialFkOld.getCitacaoCollection().remove(citacao);
                processoJudicialFkOld = em.merge(processoJudicialFkOld);
            }
            if (processoJudicialFkNew != null && !processoJudicialFkNew.equals(processoJudicialFkOld)) {
                processoJudicialFkNew.getCitacaoCollection().add(citacao);
                processoJudicialFkNew = em.merge(processoJudicialFkNew);
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
                Integer id = citacao.getId();
                if (findCitacao(id) == null) {
                    throw new NonexistentEntityException("The citacao with id " + id + " no longer exists.");
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
            Citacao citacao;
            try {
                citacao = em.getReference(Citacao.class, id);
                citacao.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The citacao with id " + id + " no longer exists.", enfe);
            }
            ProcessoJudicial processoJudicialFk = citacao.getProcessoJudicialFk();
            if (processoJudicialFk != null) {
                processoJudicialFk.getCitacaoCollection().remove(citacao);
                processoJudicialFk = em.merge(processoJudicialFk);
            }
            em.remove(citacao);
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

    public List<Citacao> findCitacaoEntities() {
        return findCitacaoEntities(true, -1, -1);
    }

    public List<Citacao> findCitacaoEntities(int maxResults, int firstResult) {
        return findCitacaoEntities(false, maxResults, firstResult);
    }

    private List<Citacao> findCitacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Citacao.class));
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

    public Citacao findCitacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Citacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getCitacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Citacao> rt = cq.from(Citacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
