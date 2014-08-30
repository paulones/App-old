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
import entidade.ProcessoJudicial;
import entidade.ProcessoJudicialHistorico;
import entidade.TipoRecurso;
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
public class TipoRecursoDAO implements Serializable {

    public TipoRecursoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoRecurso tipoRecurso) throws RollbackFailureException, Exception {
        if (tipoRecurso.getProcessoJudicialCollection() == null) {
            tipoRecurso.setProcessoJudicialCollection(new ArrayList<ProcessoJudicial>());
        }
        if (tipoRecurso.getProcessoJudicialHistoricoCollection() == null) {
            tipoRecurso.setProcessoJudicialHistoricoCollection(new ArrayList<ProcessoJudicialHistorico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            Collection<ProcessoJudicial> attachedProcessoJudicialCollection = new ArrayList<ProcessoJudicial>();
            for (ProcessoJudicial processoJudicialCollectionProcessoJudicialToAttach : tipoRecurso.getProcessoJudicialCollection()) {
                processoJudicialCollectionProcessoJudicialToAttach = em.getReference(processoJudicialCollectionProcessoJudicialToAttach.getClass(), processoJudicialCollectionProcessoJudicialToAttach.getId());
                attachedProcessoJudicialCollection.add(processoJudicialCollectionProcessoJudicialToAttach);
            }
            tipoRecurso.setProcessoJudicialCollection(attachedProcessoJudicialCollection);
            Collection<ProcessoJudicialHistorico> attachedProcessoJudicialHistoricoCollection = new ArrayList<ProcessoJudicialHistorico>();
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach : tipoRecurso.getProcessoJudicialHistoricoCollection()) {
                processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach = em.getReference(processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach.getClass(), processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach.getId());
                attachedProcessoJudicialHistoricoCollection.add(processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach);
            }
            tipoRecurso.setProcessoJudicialHistoricoCollection(attachedProcessoJudicialHistoricoCollection);
            em.persist(tipoRecurso);
            for (ProcessoJudicial processoJudicialCollectionProcessoJudicial : tipoRecurso.getProcessoJudicialCollection()) {
                TipoRecurso oldTipoDeRecursoFkOfProcessoJudicialCollectionProcessoJudicial = processoJudicialCollectionProcessoJudicial.getTipoDeRecursoFk();
                processoJudicialCollectionProcessoJudicial.setTipoDeRecursoFk(tipoRecurso);
                processoJudicialCollectionProcessoJudicial = em.merge(processoJudicialCollectionProcessoJudicial);
                if (oldTipoDeRecursoFkOfProcessoJudicialCollectionProcessoJudicial != null) {
                    oldTipoDeRecursoFkOfProcessoJudicialCollectionProcessoJudicial.getProcessoJudicialCollection().remove(processoJudicialCollectionProcessoJudicial);
                    oldTipoDeRecursoFkOfProcessoJudicialCollectionProcessoJudicial = em.merge(oldTipoDeRecursoFkOfProcessoJudicialCollectionProcessoJudicial);
                }
            }
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionProcessoJudicialHistorico : tipoRecurso.getProcessoJudicialHistoricoCollection()) {
                TipoRecurso oldTipoDeRecursoFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico = processoJudicialHistoricoCollectionProcessoJudicialHistorico.getTipoDeRecursoFk();
                processoJudicialHistoricoCollectionProcessoJudicialHistorico.setTipoDeRecursoFk(tipoRecurso);
                processoJudicialHistoricoCollectionProcessoJudicialHistorico = em.merge(processoJudicialHistoricoCollectionProcessoJudicialHistorico);
                if (oldTipoDeRecursoFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico != null) {
                    oldTipoDeRecursoFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico.getProcessoJudicialHistoricoCollection().remove(processoJudicialHistoricoCollectionProcessoJudicialHistorico);
                    oldTipoDeRecursoFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico = em.merge(oldTipoDeRecursoFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico);
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

    public void edit(TipoRecurso tipoRecurso) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            TipoRecurso persistentTipoRecurso = em.find(TipoRecurso.class, tipoRecurso.getId());
            Collection<ProcessoJudicial> processoJudicialCollectionOld = persistentTipoRecurso.getProcessoJudicialCollection();
            Collection<ProcessoJudicial> processoJudicialCollectionNew = tipoRecurso.getProcessoJudicialCollection();
            Collection<ProcessoJudicialHistorico> processoJudicialHistoricoCollectionOld = persistentTipoRecurso.getProcessoJudicialHistoricoCollection();
            Collection<ProcessoJudicialHistorico> processoJudicialHistoricoCollectionNew = tipoRecurso.getProcessoJudicialHistoricoCollection();
            Collection<ProcessoJudicial> attachedProcessoJudicialCollectionNew = new ArrayList<ProcessoJudicial>();
            for (ProcessoJudicial processoJudicialCollectionNewProcessoJudicialToAttach : processoJudicialCollectionNew) {
                processoJudicialCollectionNewProcessoJudicialToAttach = em.getReference(processoJudicialCollectionNewProcessoJudicialToAttach.getClass(), processoJudicialCollectionNewProcessoJudicialToAttach.getId());
                attachedProcessoJudicialCollectionNew.add(processoJudicialCollectionNewProcessoJudicialToAttach);
            }
            processoJudicialCollectionNew = attachedProcessoJudicialCollectionNew;
            tipoRecurso.setProcessoJudicialCollection(processoJudicialCollectionNew);
            Collection<ProcessoJudicialHistorico> attachedProcessoJudicialHistoricoCollectionNew = new ArrayList<ProcessoJudicialHistorico>();
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach : processoJudicialHistoricoCollectionNew) {
                processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach = em.getReference(processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach.getClass(), processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach.getId());
                attachedProcessoJudicialHistoricoCollectionNew.add(processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach);
            }
            processoJudicialHistoricoCollectionNew = attachedProcessoJudicialHistoricoCollectionNew;
            tipoRecurso.setProcessoJudicialHistoricoCollection(processoJudicialHistoricoCollectionNew);
            tipoRecurso = em.merge(tipoRecurso);
            for (ProcessoJudicial processoJudicialCollectionOldProcessoJudicial : processoJudicialCollectionOld) {
                if (!processoJudicialCollectionNew.contains(processoJudicialCollectionOldProcessoJudicial)) {
                    processoJudicialCollectionOldProcessoJudicial.setTipoDeRecursoFk(null);
                    processoJudicialCollectionOldProcessoJudicial = em.merge(processoJudicialCollectionOldProcessoJudicial);
                }
            }
            for (ProcessoJudicial processoJudicialCollectionNewProcessoJudicial : processoJudicialCollectionNew) {
                if (!processoJudicialCollectionOld.contains(processoJudicialCollectionNewProcessoJudicial)) {
                    TipoRecurso oldTipoDeRecursoFkOfProcessoJudicialCollectionNewProcessoJudicial = processoJudicialCollectionNewProcessoJudicial.getTipoDeRecursoFk();
                    processoJudicialCollectionNewProcessoJudicial.setTipoDeRecursoFk(tipoRecurso);
                    processoJudicialCollectionNewProcessoJudicial = em.merge(processoJudicialCollectionNewProcessoJudicial);
                    if (oldTipoDeRecursoFkOfProcessoJudicialCollectionNewProcessoJudicial != null && !oldTipoDeRecursoFkOfProcessoJudicialCollectionNewProcessoJudicial.equals(tipoRecurso)) {
                        oldTipoDeRecursoFkOfProcessoJudicialCollectionNewProcessoJudicial.getProcessoJudicialCollection().remove(processoJudicialCollectionNewProcessoJudicial);
                        oldTipoDeRecursoFkOfProcessoJudicialCollectionNewProcessoJudicial = em.merge(oldTipoDeRecursoFkOfProcessoJudicialCollectionNewProcessoJudicial);
                    }
                }
            }
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionOldProcessoJudicialHistorico : processoJudicialHistoricoCollectionOld) {
                if (!processoJudicialHistoricoCollectionNew.contains(processoJudicialHistoricoCollectionOldProcessoJudicialHistorico)) {
                    processoJudicialHistoricoCollectionOldProcessoJudicialHistorico.setTipoDeRecursoFk(null);
                    processoJudicialHistoricoCollectionOldProcessoJudicialHistorico = em.merge(processoJudicialHistoricoCollectionOldProcessoJudicialHistorico);
                }
            }
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionNewProcessoJudicialHistorico : processoJudicialHistoricoCollectionNew) {
                if (!processoJudicialHistoricoCollectionOld.contains(processoJudicialHistoricoCollectionNewProcessoJudicialHistorico)) {
                    TipoRecurso oldTipoDeRecursoFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico = processoJudicialHistoricoCollectionNewProcessoJudicialHistorico.getTipoDeRecursoFk();
                    processoJudicialHistoricoCollectionNewProcessoJudicialHistorico.setTipoDeRecursoFk(tipoRecurso);
                    processoJudicialHistoricoCollectionNewProcessoJudicialHistorico = em.merge(processoJudicialHistoricoCollectionNewProcessoJudicialHistorico);
                    if (oldTipoDeRecursoFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico != null && !oldTipoDeRecursoFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico.equals(tipoRecurso)) {
                        oldTipoDeRecursoFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico.getProcessoJudicialHistoricoCollection().remove(processoJudicialHistoricoCollectionNewProcessoJudicialHistorico);
                        oldTipoDeRecursoFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico = em.merge(oldTipoDeRecursoFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico);
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
                Integer id = tipoRecurso.getId();
                if (findTipoRecurso(id) == null) {
                    throw new NonexistentEntityException("The tipoRecurso with id " + id + " no longer exists.");
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
            TipoRecurso tipoRecurso;
            try {
                tipoRecurso = em.getReference(TipoRecurso.class, id);
                tipoRecurso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoRecurso with id " + id + " no longer exists.", enfe);
            }
            Collection<ProcessoJudicial> processoJudicialCollection = tipoRecurso.getProcessoJudicialCollection();
            for (ProcessoJudicial processoJudicialCollectionProcessoJudicial : processoJudicialCollection) {
                processoJudicialCollectionProcessoJudicial.setTipoDeRecursoFk(null);
                processoJudicialCollectionProcessoJudicial = em.merge(processoJudicialCollectionProcessoJudicial);
            }
            Collection<ProcessoJudicialHistorico> processoJudicialHistoricoCollection = tipoRecurso.getProcessoJudicialHistoricoCollection();
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionProcessoJudicialHistorico : processoJudicialHistoricoCollection) {
                processoJudicialHistoricoCollectionProcessoJudicialHistorico.setTipoDeRecursoFk(null);
                processoJudicialHistoricoCollectionProcessoJudicialHistorico = em.merge(processoJudicialHistoricoCollectionProcessoJudicialHistorico);
            }
            em.remove(tipoRecurso);
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

    public List<TipoRecurso> findTipoRecursoEntities() {
        return findTipoRecursoEntities(true, -1, -1);
    }

    public List<TipoRecurso> findTipoRecursoEntities(int maxResults, int firstResult) {
        return findTipoRecursoEntities(false, maxResults, firstResult);
    }

    private List<TipoRecurso> findTipoRecursoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoRecurso.class));
            Root<TipoRecurso> from = cq.from(TipoRecurso.class);
            cq.orderBy(em.getCriteriaBuilder().asc(from.get("tipo")));
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

    public TipoRecurso findTipoRecurso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoRecurso.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoRecursoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoRecurso> rt = cq.from(TipoRecurso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
