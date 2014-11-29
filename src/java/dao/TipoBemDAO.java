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
import entidade.BemHistorico;
import java.util.ArrayList;
import java.util.Collection;
import entidade.Bem;
import entidade.TipoBem;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author paulones
 */
public class TipoBemDAO implements Serializable {

    public TipoBemDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoBem tipoBem) throws RollbackFailureException, Exception {
        if (tipoBem.getBemHistoricoCollection() == null) {
            tipoBem.setBemHistoricoCollection(new ArrayList<BemHistorico>());
        }
        if (tipoBem.getBemCollection() == null) {
            tipoBem.setBemCollection(new ArrayList<Bem>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            Collection<BemHistorico> attachedBemHistoricoCollection = new ArrayList<BemHistorico>();
            for (BemHistorico bemHistoricoCollectionBemHistoricoToAttach : tipoBem.getBemHistoricoCollection()) {
                bemHistoricoCollectionBemHistoricoToAttach = em.getReference(bemHistoricoCollectionBemHistoricoToAttach.getClass(), bemHistoricoCollectionBemHistoricoToAttach.getId());
                attachedBemHistoricoCollection.add(bemHistoricoCollectionBemHistoricoToAttach);
            }
            tipoBem.setBemHistoricoCollection(attachedBemHistoricoCollection);
            Collection<Bem> attachedBemCollection = new ArrayList<Bem>();
            for (Bem bemCollectionBemToAttach : tipoBem.getBemCollection()) {
                bemCollectionBemToAttach = em.getReference(bemCollectionBemToAttach.getClass(), bemCollectionBemToAttach.getId());
                attachedBemCollection.add(bemCollectionBemToAttach);
            }
            tipoBem.setBemCollection(attachedBemCollection);
            em.persist(tipoBem);
            for (BemHistorico bemHistoricoCollectionBemHistorico : tipoBem.getBemHistoricoCollection()) {
                TipoBem oldTipoBemFkOfBemHistoricoCollectionBemHistorico = bemHistoricoCollectionBemHistorico.getTipoBemFk();
                bemHistoricoCollectionBemHistorico.setTipoBemFk(tipoBem);
                bemHistoricoCollectionBemHistorico = em.merge(bemHistoricoCollectionBemHistorico);
                if (oldTipoBemFkOfBemHistoricoCollectionBemHistorico != null) {
                    oldTipoBemFkOfBemHistoricoCollectionBemHistorico.getBemHistoricoCollection().remove(bemHistoricoCollectionBemHistorico);
                    oldTipoBemFkOfBemHistoricoCollectionBemHistorico = em.merge(oldTipoBemFkOfBemHistoricoCollectionBemHistorico);
                }
            }
            for (Bem bemCollectionBem : tipoBem.getBemCollection()) {
                TipoBem oldTipoBemFkOfBemCollectionBem = bemCollectionBem.getTipoBemFk();
                bemCollectionBem.setTipoBemFk(tipoBem);
                bemCollectionBem = em.merge(bemCollectionBem);
                if (oldTipoBemFkOfBemCollectionBem != null) {
                    oldTipoBemFkOfBemCollectionBem.getBemCollection().remove(bemCollectionBem);
                    oldTipoBemFkOfBemCollectionBem = em.merge(oldTipoBemFkOfBemCollectionBem);
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

    public void edit(TipoBem tipoBem) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            TipoBem persistentTipoBem = em.find(TipoBem.class, tipoBem.getId());
            Collection<BemHistorico> bemHistoricoCollectionOld = persistentTipoBem.getBemHistoricoCollection();
            Collection<BemHistorico> bemHistoricoCollectionNew = tipoBem.getBemHistoricoCollection();
            Collection<Bem> bemCollectionOld = persistentTipoBem.getBemCollection();
            Collection<Bem> bemCollectionNew = tipoBem.getBemCollection();
            List<String> illegalOrphanMessages = null;
            for (BemHistorico bemHistoricoCollectionOldBemHistorico : bemHistoricoCollectionOld) {
                if (!bemHistoricoCollectionNew.contains(bemHistoricoCollectionOldBemHistorico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain BemHistorico " + bemHistoricoCollectionOldBemHistorico + " since its tipoBemFk field is not nullable.");
                }
            }
            for (Bem bemCollectionOldBem : bemCollectionOld) {
                if (!bemCollectionNew.contains(bemCollectionOldBem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bem " + bemCollectionOldBem + " since its tipoBemFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<BemHistorico> attachedBemHistoricoCollectionNew = new ArrayList<BemHistorico>();
            for (BemHistorico bemHistoricoCollectionNewBemHistoricoToAttach : bemHistoricoCollectionNew) {
                bemHistoricoCollectionNewBemHistoricoToAttach = em.getReference(bemHistoricoCollectionNewBemHistoricoToAttach.getClass(), bemHistoricoCollectionNewBemHistoricoToAttach.getId());
                attachedBemHistoricoCollectionNew.add(bemHistoricoCollectionNewBemHistoricoToAttach);
            }
            bemHistoricoCollectionNew = attachedBemHistoricoCollectionNew;
            tipoBem.setBemHistoricoCollection(bemHistoricoCollectionNew);
            Collection<Bem> attachedBemCollectionNew = new ArrayList<Bem>();
            for (Bem bemCollectionNewBemToAttach : bemCollectionNew) {
                bemCollectionNewBemToAttach = em.getReference(bemCollectionNewBemToAttach.getClass(), bemCollectionNewBemToAttach.getId());
                attachedBemCollectionNew.add(bemCollectionNewBemToAttach);
            }
            bemCollectionNew = attachedBemCollectionNew;
            tipoBem.setBemCollection(bemCollectionNew);
            tipoBem = em.merge(tipoBem);
            for (BemHistorico bemHistoricoCollectionNewBemHistorico : bemHistoricoCollectionNew) {
                if (!bemHistoricoCollectionOld.contains(bemHistoricoCollectionNewBemHistorico)) {
                    TipoBem oldTipoBemFkOfBemHistoricoCollectionNewBemHistorico = bemHistoricoCollectionNewBemHistorico.getTipoBemFk();
                    bemHistoricoCollectionNewBemHistorico.setTipoBemFk(tipoBem);
                    bemHistoricoCollectionNewBemHistorico = em.merge(bemHistoricoCollectionNewBemHistorico);
                    if (oldTipoBemFkOfBemHistoricoCollectionNewBemHistorico != null && !oldTipoBemFkOfBemHistoricoCollectionNewBemHistorico.equals(tipoBem)) {
                        oldTipoBemFkOfBemHistoricoCollectionNewBemHistorico.getBemHistoricoCollection().remove(bemHistoricoCollectionNewBemHistorico);
                        oldTipoBemFkOfBemHistoricoCollectionNewBemHistorico = em.merge(oldTipoBemFkOfBemHistoricoCollectionNewBemHistorico);
                    }
                }
            }
            for (Bem bemCollectionNewBem : bemCollectionNew) {
                if (!bemCollectionOld.contains(bemCollectionNewBem)) {
                    TipoBem oldTipoBemFkOfBemCollectionNewBem = bemCollectionNewBem.getTipoBemFk();
                    bemCollectionNewBem.setTipoBemFk(tipoBem);
                    bemCollectionNewBem = em.merge(bemCollectionNewBem);
                    if (oldTipoBemFkOfBemCollectionNewBem != null && !oldTipoBemFkOfBemCollectionNewBem.equals(tipoBem)) {
                        oldTipoBemFkOfBemCollectionNewBem.getBemCollection().remove(bemCollectionNewBem);
                        oldTipoBemFkOfBemCollectionNewBem = em.merge(oldTipoBemFkOfBemCollectionNewBem);
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
                Integer id = tipoBem.getId();
                if (findTipoBem(id) == null) {
                    throw new NonexistentEntityException("The tipoBem with id " + id + " no longer exists.");
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
            em = getEntityManager();em.getTransaction().begin();
            TipoBem tipoBem;
            try {
                tipoBem = em.getReference(TipoBem.class, id);
                tipoBem.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoBem with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<BemHistorico> bemHistoricoCollectionOrphanCheck = tipoBem.getBemHistoricoCollection();
            for (BemHistorico bemHistoricoCollectionOrphanCheckBemHistorico : bemHistoricoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoBem (" + tipoBem + ") cannot be destroyed since the BemHistorico " + bemHistoricoCollectionOrphanCheckBemHistorico + " in its bemHistoricoCollection field has a non-nullable tipoBemFk field.");
            }
            Collection<Bem> bemCollectionOrphanCheck = tipoBem.getBemCollection();
            for (Bem bemCollectionOrphanCheckBem : bemCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoBem (" + tipoBem + ") cannot be destroyed since the Bem " + bemCollectionOrphanCheckBem + " in its bemCollection field has a non-nullable tipoBemFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoBem);
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

    public List<TipoBem> findTipoBemEntities() {
        return findTipoBemEntities(true, -1, -1);
    }

    public List<TipoBem> findTipoBemEntities(int maxResults, int firstResult) {
        return findTipoBemEntities(false, maxResults, firstResult);
    }

    private List<TipoBem> findTipoBemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoBem.class));
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

    public TipoBem findTipoBem(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoBem.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoBemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoBem> rt = cq.from(TipoBem.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
