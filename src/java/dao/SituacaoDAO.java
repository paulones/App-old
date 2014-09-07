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
import entidade.ProcessoJudicial;
import java.util.ArrayList;
import java.util.Collection;
import entidade.ProcessoJudicialHistorico;
import entidade.Situacao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author paulones
 */
public class SituacaoDAO implements Serializable {

    public SituacaoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Situacao situacao) throws RollbackFailureException, Exception {
        if (situacao.getProcessoJudicialCollection() == null) {
            situacao.setProcessoJudicialCollection(new ArrayList<ProcessoJudicial>());
        }
        if (situacao.getProcessoJudicialHistoricoCollection() == null) {
            situacao.setProcessoJudicialHistoricoCollection(new ArrayList<ProcessoJudicialHistorico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            Collection<ProcessoJudicial> attachedProcessoJudicialCollection = new ArrayList<ProcessoJudicial>();
            for (ProcessoJudicial processoJudicialCollectionProcessoJudicialToAttach : situacao.getProcessoJudicialCollection()) {
                processoJudicialCollectionProcessoJudicialToAttach = em.getReference(processoJudicialCollectionProcessoJudicialToAttach.getClass(), processoJudicialCollectionProcessoJudicialToAttach.getId());
                attachedProcessoJudicialCollection.add(processoJudicialCollectionProcessoJudicialToAttach);
            }
            situacao.setProcessoJudicialCollection(attachedProcessoJudicialCollection);
            Collection<ProcessoJudicialHistorico> attachedProcessoJudicialHistoricoCollection = new ArrayList<ProcessoJudicialHistorico>();
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach : situacao.getProcessoJudicialHistoricoCollection()) {
                processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach = em.getReference(processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach.getClass(), processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach.getId());
                attachedProcessoJudicialHistoricoCollection.add(processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach);
            }
            situacao.setProcessoJudicialHistoricoCollection(attachedProcessoJudicialHistoricoCollection);
            em.persist(situacao);
            for (ProcessoJudicial processoJudicialCollectionProcessoJudicial : situacao.getProcessoJudicialCollection()) {
                Situacao oldSituacaoFkOfProcessoJudicialCollectionProcessoJudicial = processoJudicialCollectionProcessoJudicial.getSituacaoFk();
                processoJudicialCollectionProcessoJudicial.setSituacaoFk(situacao);
                processoJudicialCollectionProcessoJudicial = em.merge(processoJudicialCollectionProcessoJudicial);
                if (oldSituacaoFkOfProcessoJudicialCollectionProcessoJudicial != null) {
                    oldSituacaoFkOfProcessoJudicialCollectionProcessoJudicial.getProcessoJudicialCollection().remove(processoJudicialCollectionProcessoJudicial);
                    oldSituacaoFkOfProcessoJudicialCollectionProcessoJudicial = em.merge(oldSituacaoFkOfProcessoJudicialCollectionProcessoJudicial);
                }
            }
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionProcessoJudicialHistorico : situacao.getProcessoJudicialHistoricoCollection()) {
                Situacao oldSituacaoFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico = processoJudicialHistoricoCollectionProcessoJudicialHistorico.getSituacaoFk();
                processoJudicialHistoricoCollectionProcessoJudicialHistorico.setSituacaoFk(situacao);
                processoJudicialHistoricoCollectionProcessoJudicialHistorico = em.merge(processoJudicialHistoricoCollectionProcessoJudicialHistorico);
                if (oldSituacaoFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico != null) {
                    oldSituacaoFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico.getProcessoJudicialHistoricoCollection().remove(processoJudicialHistoricoCollectionProcessoJudicialHistorico);
                    oldSituacaoFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico = em.merge(oldSituacaoFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico);
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

    public void edit(Situacao situacao) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            Situacao persistentSituacao = em.find(Situacao.class, situacao.getId());
            Collection<ProcessoJudicial> processoJudicialCollectionOld = persistentSituacao.getProcessoJudicialCollection();
            Collection<ProcessoJudicial> processoJudicialCollectionNew = situacao.getProcessoJudicialCollection();
            Collection<ProcessoJudicialHistorico> processoJudicialHistoricoCollectionOld = persistentSituacao.getProcessoJudicialHistoricoCollection();
            Collection<ProcessoJudicialHistorico> processoJudicialHistoricoCollectionNew = situacao.getProcessoJudicialHistoricoCollection();
            List<String> illegalOrphanMessages = null;
            for (ProcessoJudicial processoJudicialCollectionOldProcessoJudicial : processoJudicialCollectionOld) {
                if (!processoJudicialCollectionNew.contains(processoJudicialCollectionOldProcessoJudicial)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProcessoJudicial " + processoJudicialCollectionOldProcessoJudicial + " since its situacaoFk field is not nullable.");
                }
            }
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionOldProcessoJudicialHistorico : processoJudicialHistoricoCollectionOld) {
                if (!processoJudicialHistoricoCollectionNew.contains(processoJudicialHistoricoCollectionOldProcessoJudicialHistorico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProcessoJudicialHistorico " + processoJudicialHistoricoCollectionOldProcessoJudicialHistorico + " since its situacaoFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<ProcessoJudicial> attachedProcessoJudicialCollectionNew = new ArrayList<ProcessoJudicial>();
            for (ProcessoJudicial processoJudicialCollectionNewProcessoJudicialToAttach : processoJudicialCollectionNew) {
                processoJudicialCollectionNewProcessoJudicialToAttach = em.getReference(processoJudicialCollectionNewProcessoJudicialToAttach.getClass(), processoJudicialCollectionNewProcessoJudicialToAttach.getId());
                attachedProcessoJudicialCollectionNew.add(processoJudicialCollectionNewProcessoJudicialToAttach);
            }
            processoJudicialCollectionNew = attachedProcessoJudicialCollectionNew;
            situacao.setProcessoJudicialCollection(processoJudicialCollectionNew);
            Collection<ProcessoJudicialHistorico> attachedProcessoJudicialHistoricoCollectionNew = new ArrayList<ProcessoJudicialHistorico>();
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach : processoJudicialHistoricoCollectionNew) {
                processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach = em.getReference(processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach.getClass(), processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach.getId());
                attachedProcessoJudicialHistoricoCollectionNew.add(processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach);
            }
            processoJudicialHistoricoCollectionNew = attachedProcessoJudicialHistoricoCollectionNew;
            situacao.setProcessoJudicialHistoricoCollection(processoJudicialHistoricoCollectionNew);
            situacao = em.merge(situacao);
            for (ProcessoJudicial processoJudicialCollectionNewProcessoJudicial : processoJudicialCollectionNew) {
                if (!processoJudicialCollectionOld.contains(processoJudicialCollectionNewProcessoJudicial)) {
                    Situacao oldSituacaoFkOfProcessoJudicialCollectionNewProcessoJudicial = processoJudicialCollectionNewProcessoJudicial.getSituacaoFk();
                    processoJudicialCollectionNewProcessoJudicial.setSituacaoFk(situacao);
                    processoJudicialCollectionNewProcessoJudicial = em.merge(processoJudicialCollectionNewProcessoJudicial);
                    if (oldSituacaoFkOfProcessoJudicialCollectionNewProcessoJudicial != null && !oldSituacaoFkOfProcessoJudicialCollectionNewProcessoJudicial.equals(situacao)) {
                        oldSituacaoFkOfProcessoJudicialCollectionNewProcessoJudicial.getProcessoJudicialCollection().remove(processoJudicialCollectionNewProcessoJudicial);
                        oldSituacaoFkOfProcessoJudicialCollectionNewProcessoJudicial = em.merge(oldSituacaoFkOfProcessoJudicialCollectionNewProcessoJudicial);
                    }
                }
            }
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionNewProcessoJudicialHistorico : processoJudicialHistoricoCollectionNew) {
                if (!processoJudicialHistoricoCollectionOld.contains(processoJudicialHistoricoCollectionNewProcessoJudicialHistorico)) {
                    Situacao oldSituacaoFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico = processoJudicialHistoricoCollectionNewProcessoJudicialHistorico.getSituacaoFk();
                    processoJudicialHistoricoCollectionNewProcessoJudicialHistorico.setSituacaoFk(situacao);
                    processoJudicialHistoricoCollectionNewProcessoJudicialHistorico = em.merge(processoJudicialHistoricoCollectionNewProcessoJudicialHistorico);
                    if (oldSituacaoFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico != null && !oldSituacaoFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico.equals(situacao)) {
                        oldSituacaoFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico.getProcessoJudicialHistoricoCollection().remove(processoJudicialHistoricoCollectionNewProcessoJudicialHistorico);
                        oldSituacaoFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico = em.merge(oldSituacaoFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico);
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
                Integer id = situacao.getId();
                if (findSituacao(id) == null) {
                    throw new NonexistentEntityException("The situacao with id " + id + " no longer exists.");
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
            Situacao situacao;
            try {
                situacao = em.getReference(Situacao.class, id);
                situacao.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The situacao with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ProcessoJudicial> processoJudicialCollectionOrphanCheck = situacao.getProcessoJudicialCollection();
            for (ProcessoJudicial processoJudicialCollectionOrphanCheckProcessoJudicial : processoJudicialCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Situacao (" + situacao + ") cannot be destroyed since the ProcessoJudicial " + processoJudicialCollectionOrphanCheckProcessoJudicial + " in its processoJudicialCollection field has a non-nullable situacaoFk field.");
            }
            Collection<ProcessoJudicialHistorico> processoJudicialHistoricoCollectionOrphanCheck = situacao.getProcessoJudicialHistoricoCollection();
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionOrphanCheckProcessoJudicialHistorico : processoJudicialHistoricoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Situacao (" + situacao + ") cannot be destroyed since the ProcessoJudicialHistorico " + processoJudicialHistoricoCollectionOrphanCheckProcessoJudicialHistorico + " in its processoJudicialHistoricoCollection field has a non-nullable situacaoFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(situacao);
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

    public List<Situacao> findSituacaoEntities() {
        return findSituacaoEntities(true, -1, -1);
    }

    public List<Situacao> findSituacaoEntities(int maxResults, int firstResult) {
        return findSituacaoEntities(false, maxResults, firstResult);
    }

    private List<Situacao> findSituacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Situacao.class));
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

    public Situacao findSituacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Situacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getSituacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Situacao> rt = cq.from(Situacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
