/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.Bem;
import entidade.ProcessoJudicial;
import entidade.ProcessoJudicialHistorico;
import entidade.TipoRecurso;
import entidade.Usuario;
import entidade.VinculoProcessual;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
        if (processoJudicial.getProcessoJudicialHistoricoCollection() == null) {
            processoJudicial.setProcessoJudicialHistoricoCollection(new ArrayList<ProcessoJudicialHistorico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            TipoRecurso tipoDeRecursoFk = processoJudicial.getTipoDeRecursoFk();
            if (tipoDeRecursoFk != null) {
                tipoDeRecursoFk = em.getReference(tipoDeRecursoFk.getClass(), tipoDeRecursoFk.getId());
                processoJudicial.setTipoDeRecursoFk(tipoDeRecursoFk);
            }
            Usuario usuarioFk = processoJudicial.getUsuarioFk();
            if (usuarioFk != null) {
                usuarioFk = em.getReference(usuarioFk.getClass(), usuarioFk.getId());
                processoJudicial.setUsuarioFk(usuarioFk);
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
            Collection<ProcessoJudicialHistorico> attachedProcessoJudicialHistoricoCollection = new ArrayList<ProcessoJudicialHistorico>();
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach : processoJudicial.getProcessoJudicialHistoricoCollection()) {
                processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach = em.getReference(processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach.getClass(), processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach.getId());
                attachedProcessoJudicialHistoricoCollection.add(processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach);
            }
            processoJudicial.setProcessoJudicialHistoricoCollection(attachedProcessoJudicialHistoricoCollection);
            em.persist(processoJudicial);
            if (tipoDeRecursoFk != null) {
                tipoDeRecursoFk.getProcessoJudicialCollection().add(processoJudicial);
                tipoDeRecursoFk = em.merge(tipoDeRecursoFk);
            }
            if (usuarioFk != null) {
                usuarioFk.getProcessoJudicialCollection().add(processoJudicial);
                usuarioFk = em.merge(usuarioFk);
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
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionProcessoJudicialHistorico : processoJudicial.getProcessoJudicialHistoricoCollection()) {
                ProcessoJudicial oldProcessoJudicialFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico = processoJudicialHistoricoCollectionProcessoJudicialHistorico.getProcessoJudicialFk();
                processoJudicialHistoricoCollectionProcessoJudicialHistorico.setProcessoJudicialFk(processoJudicial);
                processoJudicialHistoricoCollectionProcessoJudicialHistorico = em.merge(processoJudicialHistoricoCollectionProcessoJudicialHistorico);
                if (oldProcessoJudicialFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico != null) {
                    oldProcessoJudicialFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico.getProcessoJudicialHistoricoCollection().remove(processoJudicialHistoricoCollectionProcessoJudicialHistorico);
                    oldProcessoJudicialFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico = em.merge(oldProcessoJudicialFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico);
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
            TipoRecurso tipoDeRecursoFkOld = persistentProcessoJudicial.getTipoDeRecursoFk();
            TipoRecurso tipoDeRecursoFkNew = processoJudicial.getTipoDeRecursoFk();
            Usuario usuarioFkOld = persistentProcessoJudicial.getUsuarioFk();
            Usuario usuarioFkNew = processoJudicial.getUsuarioFk();
            Collection<Bem> bemCollectionOld = persistentProcessoJudicial.getBemCollection();
            Collection<Bem> bemCollectionNew = processoJudicial.getBemCollection();
            Collection<VinculoProcessual> vinculoProcessualCollectionOld = persistentProcessoJudicial.getVinculoProcessualCollection();
            Collection<VinculoProcessual> vinculoProcessualCollectionNew = processoJudicial.getVinculoProcessualCollection();
            Collection<ProcessoJudicialHistorico> processoJudicialHistoricoCollectionOld = persistentProcessoJudicial.getProcessoJudicialHistoricoCollection();
            Collection<ProcessoJudicialHistorico> processoJudicialHistoricoCollectionNew = processoJudicial.getProcessoJudicialHistoricoCollection();
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
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionOldProcessoJudicialHistorico : processoJudicialHistoricoCollectionOld) {
                if (!processoJudicialHistoricoCollectionNew.contains(processoJudicialHistoricoCollectionOldProcessoJudicialHistorico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProcessoJudicialHistorico " + processoJudicialHistoricoCollectionOldProcessoJudicialHistorico + " since its processoJudicialFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tipoDeRecursoFkNew != null) {
                tipoDeRecursoFkNew = em.getReference(tipoDeRecursoFkNew.getClass(), tipoDeRecursoFkNew.getId());
                processoJudicial.setTipoDeRecursoFk(tipoDeRecursoFkNew);
            }
            if (usuarioFkNew != null) {
                usuarioFkNew = em.getReference(usuarioFkNew.getClass(), usuarioFkNew.getId());
                processoJudicial.setUsuarioFk(usuarioFkNew);
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
            Collection<ProcessoJudicialHistorico> attachedProcessoJudicialHistoricoCollectionNew = new ArrayList<ProcessoJudicialHistorico>();
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach : processoJudicialHistoricoCollectionNew) {
                processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach = em.getReference(processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach.getClass(), processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach.getId());
                attachedProcessoJudicialHistoricoCollectionNew.add(processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach);
            }
            processoJudicialHistoricoCollectionNew = attachedProcessoJudicialHistoricoCollectionNew;
            processoJudicial.setProcessoJudicialHistoricoCollection(processoJudicialHistoricoCollectionNew);
            processoJudicial = em.merge(processoJudicial);
            if (tipoDeRecursoFkOld != null && !tipoDeRecursoFkOld.equals(tipoDeRecursoFkNew)) {
                tipoDeRecursoFkOld.getProcessoJudicialCollection().remove(processoJudicial);
                tipoDeRecursoFkOld = em.merge(tipoDeRecursoFkOld);
            }
            if (tipoDeRecursoFkNew != null && !tipoDeRecursoFkNew.equals(tipoDeRecursoFkOld)) {
                tipoDeRecursoFkNew.getProcessoJudicialCollection().add(processoJudicial);
                tipoDeRecursoFkNew = em.merge(tipoDeRecursoFkNew);
            }
            if (usuarioFkOld != null && !usuarioFkOld.equals(usuarioFkNew)) {
                usuarioFkOld.getProcessoJudicialCollection().remove(processoJudicial);
                usuarioFkOld = em.merge(usuarioFkOld);
            }
            if (usuarioFkNew != null && !usuarioFkNew.equals(usuarioFkOld)) {
                usuarioFkNew.getProcessoJudicialCollection().add(processoJudicial);
                usuarioFkNew = em.merge(usuarioFkNew);
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
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionNewProcessoJudicialHistorico : processoJudicialHistoricoCollectionNew) {
                if (!processoJudicialHistoricoCollectionOld.contains(processoJudicialHistoricoCollectionNewProcessoJudicialHistorico)) {
                    ProcessoJudicial oldProcessoJudicialFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico = processoJudicialHistoricoCollectionNewProcessoJudicialHistorico.getProcessoJudicialFk();
                    processoJudicialHistoricoCollectionNewProcessoJudicialHistorico.setProcessoJudicialFk(processoJudicial);
                    processoJudicialHistoricoCollectionNewProcessoJudicialHistorico = em.merge(processoJudicialHistoricoCollectionNewProcessoJudicialHistorico);
                    if (oldProcessoJudicialFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico != null && !oldProcessoJudicialFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico.equals(processoJudicial)) {
                        oldProcessoJudicialFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico.getProcessoJudicialHistoricoCollection().remove(processoJudicialHistoricoCollectionNewProcessoJudicialHistorico);
                        oldProcessoJudicialFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico = em.merge(oldProcessoJudicialFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico);
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
            Collection<ProcessoJudicialHistorico> processoJudicialHistoricoCollectionOrphanCheck = processoJudicial.getProcessoJudicialHistoricoCollection();
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionOrphanCheckProcessoJudicialHistorico : processoJudicialHistoricoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProcessoJudicial (" + processoJudicial + ") cannot be destroyed since the ProcessoJudicialHistorico " + processoJudicialHistoricoCollectionOrphanCheckProcessoJudicialHistorico + " in its processoJudicialHistoricoCollection field has a non-nullable processoJudicialFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TipoRecurso tipoDeRecursoFk = processoJudicial.getTipoDeRecursoFk();
            if (tipoDeRecursoFk != null) {
                tipoDeRecursoFk.getProcessoJudicialCollection().remove(processoJudicial);
                tipoDeRecursoFk = em.merge(tipoDeRecursoFk);
            }
            Usuario usuarioFk = processoJudicial.getUsuarioFk();
            if (usuarioFk != null) {
                usuarioFk.getProcessoJudicialCollection().remove(processoJudicial);
                usuarioFk = em.merge(usuarioFk);
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
    
    public ProcessoJudicial findByProcessNumberOrCDA(ProcessoJudicial processoJudicial){
        EntityManager em = getEntityManager();
        try {
            ProcessoJudicial usuario = (ProcessoJudicial) em.createNativeQuery("select * from processo_judicial "
                    + "where numero_do_processo = '" + processoJudicial.getNumeroDoProcesso() + "' or numero_da_cda = '"+ processoJudicial.getNumeroDaCda() +"'", ProcessoJudicial.class).getSingleResult();
            return usuario;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public ProcessoJudicial findByProcessNumber(String processNumber){
        EntityManager em = getEntityManager();
        try {
            ProcessoJudicial usuario = (ProcessoJudicial) em.createNativeQuery("select * from processo_judicial "
                    + "where numero_do_processo = '" + processNumber + "'", ProcessoJudicial.class).getSingleResult();
            return usuario;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public ProcessoJudicial findByCDA(ProcessoJudicial processoJudicial){
        EntityManager em = getEntityManager();
        try {
            ProcessoJudicial usuario = (ProcessoJudicial) em.createNativeQuery("select * from processo_judicial "
                    + "where numero_da_cda = '"+ processoJudicial.getNumeroDaCda() +"'", ProcessoJudicial.class).getSingleResult();
            return usuario;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public List<ProcessoJudicial> findAllActive(){
        EntityManager em = getEntityManager();
        try {
            List<ProcessoJudicial> processoJudicialList = (List<ProcessoJudicial>) em.createNativeQuery("select * from processo_judicial "
                        + "where status = 'A' ", ProcessoJudicial.class).getResultList();
            return processoJudicialList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public Integer countPJUDByMonth(Integer ano, Integer mes){
        EntityManager em = getEntityManager();
        try {
            Integer count = (Integer) em.createNativeQuery("select count(distinct pjud.id) from processo_judicial pjud, log l "
                    + "where l.tabela = 'PJ' and l.id_fk= pjud.id and "
                    + "pjud.status = 'A' and l.operacao = 'C' and "
                    + "DATE_PART('MONTH', l.data_de_criacao) = "+mes+" and "
                    + "DATE_PART('YEAR', l.data_de_criacao) = "+ano, ProcessoJudicial.class).getSingleResult();
            return count;
        } catch (NoResultException e) {
            return 0;
        } finally {
            em.close();
        }
    }
}
