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
import entidade.TipoRecurso;
import entidade.VinculoProcessual;
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
        if (processoJudicial.getVinculoProcessualCollection() == null) {
            processoJudicial.setVinculoProcessualCollection(new ArrayList<VinculoProcessual>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            TipoRecurso tipoDeRecurso = processoJudicial.getTipoDeRecursoFk();
            if (tipoDeRecurso != null) {
                tipoDeRecurso = em.getReference(tipoDeRecurso.getClass(), tipoDeRecurso.getId());
                processoJudicial.setTipoDeRecursoFk(tipoDeRecurso);
            }
            Collection<Bem> attachedBemCollection = new ArrayList<Bem>();
            for (Bem bemCollectionBemToAttach : processoJudicial.getBemCollection()) {
                bemCollectionBemToAttach = em.getReference(bemCollectionBemToAttach.getClass(), bemCollectionBemToAttach.getId());
                attachedBemCollection.add(bemCollectionBemToAttach);
            }
            processoJudicial.setBemCollection(attachedBemCollection);
            Collection<VinculoProcessual> attachedVinculoProcessualCollection = new ArrayList<VinculoProcessual>();
            for (VinculoProcessual vinculoProcessualCollectionVinculoProcessualToAttach : processoJudicial.getVinculoProcessualCollection()) {
                vinculoProcessualCollectionVinculoProcessualToAttach = em.getReference(vinculoProcessualCollectionVinculoProcessualToAttach.getClass(), vinculoProcessualCollectionVinculoProcessualToAttach.getId());
                attachedVinculoProcessualCollection.add(vinculoProcessualCollectionVinculoProcessualToAttach);
            }
            processoJudicial.setVinculoProcessualCollection(attachedVinculoProcessualCollection);
            em.persist(processoJudicial);
            if (tipoDeRecurso != null) {
                tipoDeRecurso.getProcessoJudicialCollection().add(processoJudicial);
                tipoDeRecurso = em.merge(tipoDeRecurso);
            }
            for (Bem bemCollectionBem : processoJudicial.getBemCollection()) {
                ProcessoJudicial oldProcessoJudicialFkOfBemCollectionBem = bemCollectionBem.getProcessoJudicialFk();
                bemCollectionBem.setProcessoJudicialFk(processoJudicial);
                bemCollectionBem = em.merge(bemCollectionBem);
                if (oldProcessoJudicialFkOfBemCollectionBem != null) {
                    oldProcessoJudicialFkOfBemCollectionBem.getBemCollection().remove(bemCollectionBem);
                    oldProcessoJudicialFkOfBemCollectionBem = em.merge(oldProcessoJudicialFkOfBemCollectionBem);
                }
            }
            for (VinculoProcessual vinculoProcessualCollectionVinculoProcessual : processoJudicial.getVinculoProcessualCollection()) {
                ProcessoJudicial oldProcessoJudicialFkOfVinculoProcessualCollectionVinculoProcessual = vinculoProcessualCollectionVinculoProcessual.getProcessoJudicialFk();
                vinculoProcessualCollectionVinculoProcessual.setProcessoJudicialFk(processoJudicial);
                vinculoProcessualCollectionVinculoProcessual = em.merge(vinculoProcessualCollectionVinculoProcessual);
                if (oldProcessoJudicialFkOfVinculoProcessualCollectionVinculoProcessual != null) {
                    oldProcessoJudicialFkOfVinculoProcessualCollectionVinculoProcessual.getVinculoProcessualCollection().remove(vinculoProcessualCollectionVinculoProcessual);
                    oldProcessoJudicialFkOfVinculoProcessualCollectionVinculoProcessual = em.merge(oldProcessoJudicialFkOfVinculoProcessualCollectionVinculoProcessual);
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
            em = getEntityManager();em.getTransaction().begin();
            ProcessoJudicial persistentProcessoJudicial = em.find(ProcessoJudicial.class, processoJudicial.getId());
            TipoRecurso tipoDeRecursoOld = persistentProcessoJudicial.getTipoDeRecursoFk();
            TipoRecurso tipoDeRecursoNew = processoJudicial.getTipoDeRecursoFk();
            Collection<Bem> bemCollectionOld = persistentProcessoJudicial.getBemCollection();
            Collection<Bem> bemCollectionNew = processoJudicial.getBemCollection();
            Collection<VinculoProcessual> vinculoProcessualCollectionOld = persistentProcessoJudicial.getVinculoProcessualCollection();
            Collection<VinculoProcessual> vinculoProcessualCollectionNew = processoJudicial.getVinculoProcessualCollection();
            List<String> illegalOrphanMessages = null;
            for (Bem bemCollectionOldBem : bemCollectionOld) {
                if (!bemCollectionNew.contains(bemCollectionOldBem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bem " + bemCollectionOldBem + " since its processoJudicialFk field is not nullable.");
                }
            }
            for (VinculoProcessual vinculoProcessualCollectionOldVinculoProcessual : vinculoProcessualCollectionOld) {
                if (!vinculoProcessualCollectionNew.contains(vinculoProcessualCollectionOldVinculoProcessual)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain VinculoProcessual " + vinculoProcessualCollectionOldVinculoProcessual + " since its processoJudicialFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tipoDeRecursoNew != null) {
                tipoDeRecursoNew = em.getReference(tipoDeRecursoNew.getClass(), tipoDeRecursoNew.getId());
                processoJudicial.setTipoDeRecursoFk(tipoDeRecursoNew);
            }
            Collection<Bem> attachedBemCollectionNew = new ArrayList<Bem>();
            for (Bem bemCollectionNewBemToAttach : bemCollectionNew) {
                bemCollectionNewBemToAttach = em.getReference(bemCollectionNewBemToAttach.getClass(), bemCollectionNewBemToAttach.getId());
                attachedBemCollectionNew.add(bemCollectionNewBemToAttach);
            }
            bemCollectionNew = attachedBemCollectionNew;
            processoJudicial.setBemCollection(bemCollectionNew);
            Collection<VinculoProcessual> attachedVinculoProcessualCollectionNew = new ArrayList<VinculoProcessual>();
            for (VinculoProcessual vinculoProcessualCollectionNewVinculoProcessualToAttach : vinculoProcessualCollectionNew) {
                vinculoProcessualCollectionNewVinculoProcessualToAttach = em.getReference(vinculoProcessualCollectionNewVinculoProcessualToAttach.getClass(), vinculoProcessualCollectionNewVinculoProcessualToAttach.getId());
                attachedVinculoProcessualCollectionNew.add(vinculoProcessualCollectionNewVinculoProcessualToAttach);
            }
            vinculoProcessualCollectionNew = attachedVinculoProcessualCollectionNew;
            processoJudicial.setVinculoProcessualCollection(vinculoProcessualCollectionNew);
            processoJudicial = em.merge(processoJudicial);
            if (tipoDeRecursoOld != null && !tipoDeRecursoOld.equals(tipoDeRecursoNew)) {
                tipoDeRecursoOld.getProcessoJudicialCollection().remove(processoJudicial);
                tipoDeRecursoOld = em.merge(tipoDeRecursoOld);
            }
            if (tipoDeRecursoNew != null && !tipoDeRecursoNew.equals(tipoDeRecursoOld)) {
                tipoDeRecursoNew.getProcessoJudicialCollection().add(processoJudicial);
                tipoDeRecursoNew = em.merge(tipoDeRecursoNew);
            }
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
            for (VinculoProcessual vinculoProcessualCollectionNewVinculoProcessual : vinculoProcessualCollectionNew) {
                if (!vinculoProcessualCollectionOld.contains(vinculoProcessualCollectionNewVinculoProcessual)) {
                    ProcessoJudicial oldProcessoJudicialFkOfVinculoProcessualCollectionNewVinculoProcessual = vinculoProcessualCollectionNewVinculoProcessual.getProcessoJudicialFk();
                    vinculoProcessualCollectionNewVinculoProcessual.setProcessoJudicialFk(processoJudicial);
                    vinculoProcessualCollectionNewVinculoProcessual = em.merge(vinculoProcessualCollectionNewVinculoProcessual);
                    if (oldProcessoJudicialFkOfVinculoProcessualCollectionNewVinculoProcessual != null && !oldProcessoJudicialFkOfVinculoProcessualCollectionNewVinculoProcessual.equals(processoJudicial)) {
                        oldProcessoJudicialFkOfVinculoProcessualCollectionNewVinculoProcessual.getVinculoProcessualCollection().remove(vinculoProcessualCollectionNewVinculoProcessual);
                        oldProcessoJudicialFkOfVinculoProcessualCollectionNewVinculoProcessual = em.merge(oldProcessoJudicialFkOfVinculoProcessualCollectionNewVinculoProcessual);
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
            em = getEntityManager();em.getTransaction().begin();
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
            Collection<VinculoProcessual> vinculoProcessualCollectionOrphanCheck = processoJudicial.getVinculoProcessualCollection();
            for (VinculoProcessual vinculoProcessualCollectionOrphanCheckVinculoProcessual : vinculoProcessualCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProcessoJudicial (" + processoJudicial + ") cannot be destroyed since the VinculoProcessual " + vinculoProcessualCollectionOrphanCheckVinculoProcessual + " in its vinculoProcessualCollection field has a non-nullable processoJudicialFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TipoRecurso tipoDeRecurso = processoJudicial.getTipoDeRecursoFk();
            if (tipoDeRecurso != null) {
                tipoDeRecurso.getProcessoJudicialCollection().remove(processoJudicial);
                tipoDeRecurso = em.merge(tipoDeRecurso);
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
