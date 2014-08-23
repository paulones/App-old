/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidade.Bem;
import entidade.ProcessoJudicial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author paulones
 */
public class ProcessoJudicialDAO implements Serializable {

    public ProcessoJudicialDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProcessoJudicial processoJudicial) throws RollbackFailureException, Exception {
        if (processoJudicial.getBemCollection() == null) {
            processoJudicial.setBemCollection(new ArrayList<Bem>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Bem> attachedBemCollection = new ArrayList<Bem>();
            for (Bem bemCollectionBemToAttach : processoJudicial.getBemCollection()) {
                bemCollectionBemToAttach = em.getReference(bemCollectionBemToAttach.getClass(), bemCollectionBemToAttach.getId());
                attachedBemCollection.add(bemCollectionBemToAttach);
            }
            processoJudicial.setBemCollection(attachedBemCollection);
            em.persist(processoJudicial);
            for (Bem bemCollectionBem : processoJudicial.getBemCollection()) {
                ProcessoJudicial oldProcessoJudicialFkOfBemCollectionBem = bemCollectionBem.getProcessoJudicialFk();
                bemCollectionBem.setProcessoJudicialFk(processoJudicial);
                bemCollectionBem = em.merge(bemCollectionBem);
                if (oldProcessoJudicialFkOfBemCollectionBem != null) {
                    oldProcessoJudicialFkOfBemCollectionBem.getBemCollection().remove(bemCollectionBem);
                    oldProcessoJudicialFkOfBemCollectionBem = em.merge(oldProcessoJudicialFkOfBemCollectionBem);
                }
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

    public void edit(ProcessoJudicial processoJudicial) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProcessoJudicial persistentProcessoJudicial = em.find(ProcessoJudicial.class, processoJudicial.getId());
            Collection<Bem> bemCollectionOld = persistentProcessoJudicial.getBemCollection();
            Collection<Bem> bemCollectionNew = processoJudicial.getBemCollection();
            List<String> illegalOrphanMessages = null;
            for (Bem bemCollectionOldBem : bemCollectionOld) {
                if (!bemCollectionNew.contains(bemCollectionOldBem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bem " + bemCollectionOldBem + " since its processoJudicialFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Bem> attachedBemCollectionNew = new ArrayList<Bem>();
            for (Bem bemCollectionNewBemToAttach : bemCollectionNew) {
                bemCollectionNewBemToAttach = em.getReference(bemCollectionNewBemToAttach.getClass(), bemCollectionNewBemToAttach.getId());
                attachedBemCollectionNew.add(bemCollectionNewBemToAttach);
            }
            bemCollectionNew = attachedBemCollectionNew;
            processoJudicial.setBemCollection(bemCollectionNew);
            processoJudicial = em.merge(processoJudicial);
            for (Bem bemCollectionNewBem : bemCollectionNew) {
                if (!bemCollectionOld.contains(bemCollectionNewBem)) {
                    ProcessoJudicial oldProcessoJudicialFkOfBemCollectionNewBem = bemCollectionNewBem.getProcessoJudicialFk();
                    bemCollectionNewBem.setProcessoJudicialFk(processoJudicial);
                    bemCollectionNewBem = em.merge(bemCollectionNewBem);
                    if (oldProcessoJudicialFkOfBemCollectionNewBem != null && !oldProcessoJudicialFkOfBemCollectionNewBem.equals(processoJudicial)) {
                        oldProcessoJudicialFkOfBemCollectionNewBem.getBemCollection().remove(bemCollectionNewBem);
                        oldProcessoJudicialFkOfBemCollectionNewBem = em.merge(oldProcessoJudicialFkOfBemCollectionNewBem);
                    }
                }
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
                Integer id = processoJudicial.getId();
                if (findProcessoJudicial(id) == null) {
                    throw new NonexistentEntityException("The processoJudicial with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProcessoJudicial processoJudicial;
            try {
                processoJudicial = em.getReference(ProcessoJudicial.class, id);
                processoJudicial.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The processoJudicial with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Bem> bemCollectionOrphanCheck = processoJudicial.getBemCollection();
            for (Bem bemCollectionOrphanCheckBem : bemCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProcessoJudicial (" + processoJudicial + ") cannot be destroyed since the Bem " + bemCollectionOrphanCheckBem + " in its bemCollection field has a non-nullable processoJudicialFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(processoJudicial);
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

    public List<ProcessoJudicial> findProcessoJudicialEntities() {
        return findProcessoJudicialEntities(true, -1, -1);
    }

    public List<ProcessoJudicial> findProcessoJudicialEntities(int maxResults, int firstResult) {
        return findProcessoJudicialEntities(false, maxResults, firstResult);
    }

    private List<ProcessoJudicial> findProcessoJudicialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProcessoJudicial.class));
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

    public ProcessoJudicial findProcessoJudicial(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProcessoJudicial.class, id);
        } finally {
            em.close();
        }
    }

    public int getProcessoJudicialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProcessoJudicial> rt = cq.from(ProcessoJudicial.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public ProcessoJudicial findByProcessNumber(ProcessoJudicial processoJudicial){
        return new ProcessoJudicial();
    }
    
}
