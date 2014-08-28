/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.Bem;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidade.ProcessoJudicial;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.transaction.UserTransaction;

/**
 *
 * @author paulones
 */
public class BemDAO implements Serializable {

    public BemDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bem bem) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProcessoJudicial processoJudicialFk = bem.getProcessoJudicialFk();
            if (processoJudicialFk != null) {
                processoJudicialFk = em.getReference(processoJudicialFk.getClass(), processoJudicialFk.getId());
                bem.setProcessoJudicialFk(processoJudicialFk);
            }
            em.persist(bem);
            if (processoJudicialFk != null) {
                processoJudicialFk.getBemCollection().add(bem);
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

    public void edit(Bem bem) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bem persistentBem = em.find(Bem.class, bem.getId());
            ProcessoJudicial processoJudicialFkOld = persistentBem.getProcessoJudicialFk();
            ProcessoJudicial processoJudicialFkNew = bem.getProcessoJudicialFk();
            if (processoJudicialFkNew != null) {
                processoJudicialFkNew = em.getReference(processoJudicialFkNew.getClass(), processoJudicialFkNew.getId());
                bem.setProcessoJudicialFk(processoJudicialFkNew);
            }
            bem = em.merge(bem);
            if (processoJudicialFkOld != null && !processoJudicialFkOld.equals(processoJudicialFkNew)) {
                processoJudicialFkOld.getBemCollection().remove(bem);
                processoJudicialFkOld = em.merge(processoJudicialFkOld);
            }
            if (processoJudicialFkNew != null && !processoJudicialFkNew.equals(processoJudicialFkOld)) {
                processoJudicialFkNew.getBemCollection().add(bem);
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
                Integer id = bem.getId();
                if (findBem(id) == null) {
                    throw new NonexistentEntityException("The bem with id " + id + " no longer exists.");
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
            em = getEntityManager();
            em.getTransaction().begin();
            Bem bem;
            try {
                bem = em.getReference(Bem.class, id);
                bem.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bem with id " + id + " no longer exists.", enfe);
            }
            ProcessoJudicial processoJudicialFk = bem.getProcessoJudicialFk();
            if (processoJudicialFk != null) {
                processoJudicialFk.getBemCollection().remove(bem);
                processoJudicialFk = em.merge(processoJudicialFk);
            }
            em.remove(bem);
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

    public List<Bem> findBemEntities() {
        return findBemEntities(true, -1, -1);
    }

    public List<Bem> findBemEntities(int maxResults, int firstResult) {
        return findBemEntities(false, maxResults, firstResult);
    }

    private List<Bem> findBemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bem.class));
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

    public Bem findBem(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bem.class, id);
        } finally {
            em.close();
        }
    }

    public int getBemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bem> rt = cq.from(Bem.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Bem> findAllByPJUD(ProcessoJudicial processoJudicial){
        EntityManager em = getEntityManager();
        try {
            List<Bem> processoJudicialList = (List<Bem>) em.createNativeQuery("select * from bem "
                        + "where processo_judicial_fk = '"+processoJudicial.getId()+"' ", Bem.class).getResultList();
            return processoJudicialList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public void destroyByPJUD(Integer idPjud){
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.createNativeQuery("delete from bem "
                    + "where processo_judicial_fk = '" + idPjud + "'").executeUpdate();
            em.getTransaction().commit();
        } catch (NoResultException e) {
        } finally {
            em.close();
        }
    }
    
}
