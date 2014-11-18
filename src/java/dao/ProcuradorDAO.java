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
import entidade.Estado;
import entidade.ProcessoJudicial;
import java.util.ArrayList;
import java.util.Collection;
import entidade.ProcessoJudicialHistorico;
import entidade.Procurador;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author paulones
 */
public class ProcuradorDAO implements Serializable {

    public ProcuradorDAO() {
    }
    
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Procurador procurador) throws RollbackFailureException, Exception {
        if (procurador.getProcessoJudicialCollection() == null) {
            procurador.setProcessoJudicialCollection(new ArrayList<ProcessoJudicial>());
        }
        if (procurador.getProcessoJudicialHistoricoCollection() == null) {
            procurador.setProcessoJudicialHistoricoCollection(new ArrayList<ProcessoJudicialHistorico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            Estado estadoFk = procurador.getEstadoFk();
            if (estadoFk != null) {
                estadoFk = em.getReference(estadoFk.getClass(), estadoFk.getId());
                procurador.setEstadoFk(estadoFk);
            }
            Collection<ProcessoJudicial> attachedProcessoJudicialCollection = new ArrayList<ProcessoJudicial>();
            for (ProcessoJudicial processoJudicialCollectionProcessoJudicialToAttach : procurador.getProcessoJudicialCollection()) {
                processoJudicialCollectionProcessoJudicialToAttach = em.getReference(processoJudicialCollectionProcessoJudicialToAttach.getClass(), processoJudicialCollectionProcessoJudicialToAttach.getId());
                attachedProcessoJudicialCollection.add(processoJudicialCollectionProcessoJudicialToAttach);
            }
            procurador.setProcessoJudicialCollection(attachedProcessoJudicialCollection);
            Collection<ProcessoJudicialHistorico> attachedProcessoJudicialHistoricoCollection = new ArrayList<ProcessoJudicialHistorico>();
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach : procurador.getProcessoJudicialHistoricoCollection()) {
                processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach = em.getReference(processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach.getClass(), processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach.getId());
                attachedProcessoJudicialHistoricoCollection.add(processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach);
            }
            procurador.setProcessoJudicialHistoricoCollection(attachedProcessoJudicialHistoricoCollection);
            em.persist(procurador);
            if (estadoFk != null) {
                estadoFk.getProcuradorCollection().add(procurador);
                estadoFk = em.merge(estadoFk);
            }
            for (ProcessoJudicial processoJudicialCollectionProcessoJudicial : procurador.getProcessoJudicialCollection()) {
                Procurador oldProcuradorFkOfProcessoJudicialCollectionProcessoJudicial = processoJudicialCollectionProcessoJudicial.getProcuradorFk();
                processoJudicialCollectionProcessoJudicial.setProcuradorFk(procurador);
                processoJudicialCollectionProcessoJudicial = em.merge(processoJudicialCollectionProcessoJudicial);
                if (oldProcuradorFkOfProcessoJudicialCollectionProcessoJudicial != null) {
                    oldProcuradorFkOfProcessoJudicialCollectionProcessoJudicial.getProcessoJudicialCollection().remove(processoJudicialCollectionProcessoJudicial);
                    oldProcuradorFkOfProcessoJudicialCollectionProcessoJudicial = em.merge(oldProcuradorFkOfProcessoJudicialCollectionProcessoJudicial);
                }
            }
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionProcessoJudicialHistorico : procurador.getProcessoJudicialHistoricoCollection()) {
                Procurador oldProcuradorFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico = processoJudicialHistoricoCollectionProcessoJudicialHistorico.getProcuradorFk();
                processoJudicialHistoricoCollectionProcessoJudicialHistorico.setProcuradorFk(procurador);
                processoJudicialHistoricoCollectionProcessoJudicialHistorico = em.merge(processoJudicialHistoricoCollectionProcessoJudicialHistorico);
                if (oldProcuradorFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico != null) {
                    oldProcuradorFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico.getProcessoJudicialHistoricoCollection().remove(processoJudicialHistoricoCollectionProcessoJudicialHistorico);
                    oldProcuradorFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico = em.merge(oldProcuradorFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico);
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

    public void edit(Procurador procurador) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            Procurador persistentProcurador = em.find(Procurador.class, procurador.getId());
            Estado estadoFkOld = persistentProcurador.getEstadoFk();
            Estado estadoFkNew = procurador.getEstadoFk();
            Collection<ProcessoJudicial> processoJudicialCollectionOld = persistentProcurador.getProcessoJudicialCollection();
            Collection<ProcessoJudicial> processoJudicialCollectionNew = procurador.getProcessoJudicialCollection();
            Collection<ProcessoJudicialHistorico> processoJudicialHistoricoCollectionOld = persistentProcurador.getProcessoJudicialHistoricoCollection();
            Collection<ProcessoJudicialHistorico> processoJudicialHistoricoCollectionNew = procurador.getProcessoJudicialHistoricoCollection();
            if (estadoFkNew != null) {
                estadoFkNew = em.getReference(estadoFkNew.getClass(), estadoFkNew.getId());
                procurador.setEstadoFk(estadoFkNew);
            }
            Collection<ProcessoJudicial> attachedProcessoJudicialCollectionNew = new ArrayList<ProcessoJudicial>();
            for (ProcessoJudicial processoJudicialCollectionNewProcessoJudicialToAttach : processoJudicialCollectionNew) {
                processoJudicialCollectionNewProcessoJudicialToAttach = em.getReference(processoJudicialCollectionNewProcessoJudicialToAttach.getClass(), processoJudicialCollectionNewProcessoJudicialToAttach.getId());
                attachedProcessoJudicialCollectionNew.add(processoJudicialCollectionNewProcessoJudicialToAttach);
            }
            processoJudicialCollectionNew = attachedProcessoJudicialCollectionNew;
            procurador.setProcessoJudicialCollection(processoJudicialCollectionNew);
            Collection<ProcessoJudicialHistorico> attachedProcessoJudicialHistoricoCollectionNew = new ArrayList<ProcessoJudicialHistorico>();
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach : processoJudicialHistoricoCollectionNew) {
                processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach = em.getReference(processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach.getClass(), processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach.getId());
                attachedProcessoJudicialHistoricoCollectionNew.add(processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach);
            }
            processoJudicialHistoricoCollectionNew = attachedProcessoJudicialHistoricoCollectionNew;
            procurador.setProcessoJudicialHistoricoCollection(processoJudicialHistoricoCollectionNew);
            procurador = em.merge(procurador);
            if (estadoFkOld != null && !estadoFkOld.equals(estadoFkNew)) {
                estadoFkOld.getProcuradorCollection().remove(procurador);
                estadoFkOld = em.merge(estadoFkOld);
            }
            if (estadoFkNew != null && !estadoFkNew.equals(estadoFkOld)) {
                estadoFkNew.getProcuradorCollection().add(procurador);
                estadoFkNew = em.merge(estadoFkNew);
            }
            for (ProcessoJudicial processoJudicialCollectionOldProcessoJudicial : processoJudicialCollectionOld) {
                if (!processoJudicialCollectionNew.contains(processoJudicialCollectionOldProcessoJudicial)) {
                    processoJudicialCollectionOldProcessoJudicial.setProcuradorFk(null);
                    processoJudicialCollectionOldProcessoJudicial = em.merge(processoJudicialCollectionOldProcessoJudicial);
                }
            }
            for (ProcessoJudicial processoJudicialCollectionNewProcessoJudicial : processoJudicialCollectionNew) {
                if (!processoJudicialCollectionOld.contains(processoJudicialCollectionNewProcessoJudicial)) {
                    Procurador oldProcuradorFkOfProcessoJudicialCollectionNewProcessoJudicial = processoJudicialCollectionNewProcessoJudicial.getProcuradorFk();
                    processoJudicialCollectionNewProcessoJudicial.setProcuradorFk(procurador);
                    processoJudicialCollectionNewProcessoJudicial = em.merge(processoJudicialCollectionNewProcessoJudicial);
                    if (oldProcuradorFkOfProcessoJudicialCollectionNewProcessoJudicial != null && !oldProcuradorFkOfProcessoJudicialCollectionNewProcessoJudicial.equals(procurador)) {
                        oldProcuradorFkOfProcessoJudicialCollectionNewProcessoJudicial.getProcessoJudicialCollection().remove(processoJudicialCollectionNewProcessoJudicial);
                        oldProcuradorFkOfProcessoJudicialCollectionNewProcessoJudicial = em.merge(oldProcuradorFkOfProcessoJudicialCollectionNewProcessoJudicial);
                    }
                }
            }
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionOldProcessoJudicialHistorico : processoJudicialHistoricoCollectionOld) {
                if (!processoJudicialHistoricoCollectionNew.contains(processoJudicialHistoricoCollectionOldProcessoJudicialHistorico)) {
                    processoJudicialHistoricoCollectionOldProcessoJudicialHistorico.setProcuradorFk(null);
                    processoJudicialHistoricoCollectionOldProcessoJudicialHistorico = em.merge(processoJudicialHistoricoCollectionOldProcessoJudicialHistorico);
                }
            }
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionNewProcessoJudicialHistorico : processoJudicialHistoricoCollectionNew) {
                if (!processoJudicialHistoricoCollectionOld.contains(processoJudicialHistoricoCollectionNewProcessoJudicialHistorico)) {
                    Procurador oldProcuradorFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico = processoJudicialHistoricoCollectionNewProcessoJudicialHistorico.getProcuradorFk();
                    processoJudicialHistoricoCollectionNewProcessoJudicialHistorico.setProcuradorFk(procurador);
                    processoJudicialHistoricoCollectionNewProcessoJudicialHistorico = em.merge(processoJudicialHistoricoCollectionNewProcessoJudicialHistorico);
                    if (oldProcuradorFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico != null && !oldProcuradorFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico.equals(procurador)) {
                        oldProcuradorFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico.getProcessoJudicialHistoricoCollection().remove(processoJudicialHistoricoCollectionNewProcessoJudicialHistorico);
                        oldProcuradorFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico = em.merge(oldProcuradorFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico);
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
                Integer id = procurador.getId();
                if (findProcurador(id) == null) {
                    throw new NonexistentEntityException("The procurador with id " + id + " no longer exists.");
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
            Procurador procurador;
            try {
                procurador = em.getReference(Procurador.class, id);
                procurador.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The procurador with id " + id + " no longer exists.", enfe);
            }
            Estado estadoFk = procurador.getEstadoFk();
            if (estadoFk != null) {
                estadoFk.getProcuradorCollection().remove(procurador);
                estadoFk = em.merge(estadoFk);
            }
            Collection<ProcessoJudicial> processoJudicialCollection = procurador.getProcessoJudicialCollection();
            for (ProcessoJudicial processoJudicialCollectionProcessoJudicial : processoJudicialCollection) {
                processoJudicialCollectionProcessoJudicial.setProcuradorFk(null);
                processoJudicialCollectionProcessoJudicial = em.merge(processoJudicialCollectionProcessoJudicial);
            }
            Collection<ProcessoJudicialHistorico> processoJudicialHistoricoCollection = procurador.getProcessoJudicialHistoricoCollection();
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionProcessoJudicialHistorico : processoJudicialHistoricoCollection) {
                processoJudicialHistoricoCollectionProcessoJudicialHistorico.setProcuradorFk(null);
                processoJudicialHistoricoCollectionProcessoJudicialHistorico = em.merge(processoJudicialHistoricoCollectionProcessoJudicialHistorico);
            }
            em.remove(procurador);
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

    public List<Procurador> findProcuradorEntities() {
        return findProcuradorEntities(true, -1, -1);
    }

    public List<Procurador> findProcuradorEntities(int maxResults, int firstResult) {
        return findProcuradorEntities(false, maxResults, firstResult);
    }

    private List<Procurador> findProcuradorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Procurador.class));
            Root<Procurador> from = cq.from(Procurador.class);
            cq.orderBy(em.getCriteriaBuilder().asc(from.get("nome")));
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

    public Procurador findProcurador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Procurador.class, id);
        } finally {
            em.close();
        }
    }

    public int getProcuradorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Procurador> rt = cq.from(Procurador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
