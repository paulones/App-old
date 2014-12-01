/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.ProcessoJudicial;
import entidade.Redirecionamento;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author paulones
 */
public class RedirecionamentoDAO implements Serializable {

    public RedirecionamentoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Redirecionamento redirecionamento) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            ProcessoJudicial processoJudicialFk = redirecionamento.getProcessoJudicialFk();
            if (processoJudicialFk != null) {
                processoJudicialFk = em.getReference(processoJudicialFk.getClass(), processoJudicialFk.getId());
                redirecionamento.setProcessoJudicialFk(processoJudicialFk);
            }
            em.persist(redirecionamento);
            if (processoJudicialFk != null) {
                processoJudicialFk.getRedirecionamentoCollection().add(redirecionamento);
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

    public void edit(Redirecionamento redirecionamento) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            Redirecionamento persistentRedirecionamento = em.find(Redirecionamento.class, redirecionamento.getId());
            ProcessoJudicial processoJudicialFkOld = persistentRedirecionamento.getProcessoJudicialFk();
            ProcessoJudicial processoJudicialFkNew = redirecionamento.getProcessoJudicialFk();
            if (processoJudicialFkNew != null) {
                processoJudicialFkNew = em.getReference(processoJudicialFkNew.getClass(), processoJudicialFkNew.getId());
                redirecionamento.setProcessoJudicialFk(processoJudicialFkNew);
            }
            redirecionamento = em.merge(redirecionamento);
            if (processoJudicialFkOld != null && !processoJudicialFkOld.equals(processoJudicialFkNew)) {
                processoJudicialFkOld.getRedirecionamentoCollection().remove(redirecionamento);
                processoJudicialFkOld = em.merge(processoJudicialFkOld);
            }
            if (processoJudicialFkNew != null && !processoJudicialFkNew.equals(processoJudicialFkOld)) {
                processoJudicialFkNew.getRedirecionamentoCollection().add(redirecionamento);
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
                Integer id = redirecionamento.getId();
                if (findRedirecionamento(id) == null) {
                    throw new NonexistentEntityException("The redirecionamento with id " + id + " no longer exists.");
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
            Redirecionamento redirecionamento;
            try {
                redirecionamento = em.getReference(Redirecionamento.class, id);
                redirecionamento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The redirecionamento with id " + id + " no longer exists.", enfe);
            }
            ProcessoJudicial processoJudicialFk = redirecionamento.getProcessoJudicialFk();
            if (processoJudicialFk != null) {
                processoJudicialFk.getRedirecionamentoCollection().remove(redirecionamento);
                processoJudicialFk = em.merge(processoJudicialFk);
            }
            em.remove(redirecionamento);
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

    public List<Redirecionamento> findRedirecionamentoEntities() {
        return findRedirecionamentoEntities(true, -1, -1);
    }

    public List<Redirecionamento> findRedirecionamentoEntities(int maxResults, int firstResult) {
        return findRedirecionamentoEntities(false, maxResults, firstResult);
    }

    private List<Redirecionamento> findRedirecionamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Redirecionamento.class));
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

    public Redirecionamento findRedirecionamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Redirecionamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getRedirecionamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Redirecionamento> rt = cq.from(Redirecionamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Redirecionamento> findByPJUD(Integer id){
        EntityManager em = getEntityManager();
        try {
            List<Redirecionamento> processoJudicialList = (List<Redirecionamento>) em.createNativeQuery("select * from redirecionamento "
                    + "where processo_judicial_fk = '"+id+"'", Redirecionamento.class).getResultList();
            return processoJudicialList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public void destroyByPJUD(Integer idPjud) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.createNativeQuery("delete from redirecionamento "
                    + "where processo_judicial_fk = '" + idPjud + "'").executeUpdate();
            em.getTransaction().commit();
        } catch (NoResultException e) {
        } finally {
            em.close();
        }
    }
}
