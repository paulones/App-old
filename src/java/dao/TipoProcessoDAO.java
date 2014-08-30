/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.TipoProcesso;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidade.VinculoProcessual;
import entidade.VinculoProcessualHistorico;
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
public class TipoProcessoDAO implements Serializable {

    public TipoProcessoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoProcesso tipoProcesso) throws RollbackFailureException, Exception {
        if (tipoProcesso.getVinculoProcessualCollection() == null) {
            tipoProcesso.setVinculoProcessualCollection(new ArrayList<VinculoProcessual>());
        }
        if (tipoProcesso.getVinculoProcessualHistoricoCollection() == null) {
            tipoProcesso.setVinculoProcessualHistoricoCollection(new ArrayList<VinculoProcessualHistorico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            Collection<VinculoProcessual> attachedVinculoProcessualCollection = new ArrayList<VinculoProcessual>();
            for (VinculoProcessual vinculoProcessualCollectionVinculoProcessualToAttach : tipoProcesso.getVinculoProcessualCollection()) {
                vinculoProcessualCollectionVinculoProcessualToAttach = em.getReference(vinculoProcessualCollectionVinculoProcessualToAttach.getClass(), vinculoProcessualCollectionVinculoProcessualToAttach.getId());
                attachedVinculoProcessualCollection.add(vinculoProcessualCollectionVinculoProcessualToAttach);
            }
            tipoProcesso.setVinculoProcessualCollection(attachedVinculoProcessualCollection);
            Collection<VinculoProcessualHistorico> attachedVinculoProcessualHistoricoCollection = new ArrayList<VinculoProcessualHistorico>();
            for (VinculoProcessualHistorico vinculoProcessualHistoricoCollectionVinculoProcessualHistoricoToAttach : tipoProcesso.getVinculoProcessualHistoricoCollection()) {
                vinculoProcessualHistoricoCollectionVinculoProcessualHistoricoToAttach = em.getReference(vinculoProcessualHistoricoCollectionVinculoProcessualHistoricoToAttach.getClass(), vinculoProcessualHistoricoCollectionVinculoProcessualHistoricoToAttach.getId());
                attachedVinculoProcessualHistoricoCollection.add(vinculoProcessualHistoricoCollectionVinculoProcessualHistoricoToAttach);
            }
            tipoProcesso.setVinculoProcessualHistoricoCollection(attachedVinculoProcessualHistoricoCollection);
            em.persist(tipoProcesso);
            for (VinculoProcessual vinculoProcessualCollectionVinculoProcessual : tipoProcesso.getVinculoProcessualCollection()) {
                TipoProcesso oldTipoDeProcessoFkOfVinculoProcessualCollectionVinculoProcessual = vinculoProcessualCollectionVinculoProcessual.getTipoDeProcessoFk();
                vinculoProcessualCollectionVinculoProcessual.setTipoDeProcessoFk(tipoProcesso);
                vinculoProcessualCollectionVinculoProcessual = em.merge(vinculoProcessualCollectionVinculoProcessual);
                if (oldTipoDeProcessoFkOfVinculoProcessualCollectionVinculoProcessual != null) {
                    oldTipoDeProcessoFkOfVinculoProcessualCollectionVinculoProcessual.getVinculoProcessualCollection().remove(vinculoProcessualCollectionVinculoProcessual);
                    oldTipoDeProcessoFkOfVinculoProcessualCollectionVinculoProcessual = em.merge(oldTipoDeProcessoFkOfVinculoProcessualCollectionVinculoProcessual);
                }
            }
            for (VinculoProcessualHistorico vinculoProcessualHistoricoCollectionVinculoProcessualHistorico : tipoProcesso.getVinculoProcessualHistoricoCollection()) {
                TipoProcesso oldTipoDeProcessoFkOfVinculoProcessualHistoricoCollectionVinculoProcessualHistorico = vinculoProcessualHistoricoCollectionVinculoProcessualHistorico.getTipoDeProcessoFk();
                vinculoProcessualHistoricoCollectionVinculoProcessualHistorico.setTipoDeProcessoFk(tipoProcesso);
                vinculoProcessualHistoricoCollectionVinculoProcessualHistorico = em.merge(vinculoProcessualHistoricoCollectionVinculoProcessualHistorico);
                if (oldTipoDeProcessoFkOfVinculoProcessualHistoricoCollectionVinculoProcessualHistorico != null) {
                    oldTipoDeProcessoFkOfVinculoProcessualHistoricoCollectionVinculoProcessualHistorico.getVinculoProcessualHistoricoCollection().remove(vinculoProcessualHistoricoCollectionVinculoProcessualHistorico);
                    oldTipoDeProcessoFkOfVinculoProcessualHistoricoCollectionVinculoProcessualHistorico = em.merge(oldTipoDeProcessoFkOfVinculoProcessualHistoricoCollectionVinculoProcessualHistorico);
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

    public void edit(TipoProcesso tipoProcesso) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            TipoProcesso persistentTipoProcesso = em.find(TipoProcesso.class, tipoProcesso.getId());
            Collection<VinculoProcessual> vinculoProcessualCollectionOld = persistentTipoProcesso.getVinculoProcessualCollection();
            Collection<VinculoProcessual> vinculoProcessualCollectionNew = tipoProcesso.getVinculoProcessualCollection();
            Collection<VinculoProcessualHistorico> vinculoProcessualHistoricoCollectionOld = persistentTipoProcesso.getVinculoProcessualHistoricoCollection();
            Collection<VinculoProcessualHistorico> vinculoProcessualHistoricoCollectionNew = tipoProcesso.getVinculoProcessualHistoricoCollection();
            List<String> illegalOrphanMessages = null;
            for (VinculoProcessual vinculoProcessualCollectionOldVinculoProcessual : vinculoProcessualCollectionOld) {
                if (!vinculoProcessualCollectionNew.contains(vinculoProcessualCollectionOldVinculoProcessual)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain VinculoProcessual " + vinculoProcessualCollectionOldVinculoProcessual + " since its tipoDeProcessoFk field is not nullable.");
                }
            }
            for (VinculoProcessualHistorico vinculoProcessualHistoricoCollectionOldVinculoProcessualHistorico : vinculoProcessualHistoricoCollectionOld) {
                if (!vinculoProcessualHistoricoCollectionNew.contains(vinculoProcessualHistoricoCollectionOldVinculoProcessualHistorico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain VinculoProcessualHistorico " + vinculoProcessualHistoricoCollectionOldVinculoProcessualHistorico + " since its tipoDeProcessoFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<VinculoProcessual> attachedVinculoProcessualCollectionNew = new ArrayList<VinculoProcessual>();
            for (VinculoProcessual vinculoProcessualCollectionNewVinculoProcessualToAttach : vinculoProcessualCollectionNew) {
                vinculoProcessualCollectionNewVinculoProcessualToAttach = em.getReference(vinculoProcessualCollectionNewVinculoProcessualToAttach.getClass(), vinculoProcessualCollectionNewVinculoProcessualToAttach.getId());
                attachedVinculoProcessualCollectionNew.add(vinculoProcessualCollectionNewVinculoProcessualToAttach);
            }
            vinculoProcessualCollectionNew = attachedVinculoProcessualCollectionNew;
            tipoProcesso.setVinculoProcessualCollection(vinculoProcessualCollectionNew);
            Collection<VinculoProcessualHistorico> attachedVinculoProcessualHistoricoCollectionNew = new ArrayList<VinculoProcessualHistorico>();
            for (VinculoProcessualHistorico vinculoProcessualHistoricoCollectionNewVinculoProcessualHistoricoToAttach : vinculoProcessualHistoricoCollectionNew) {
                vinculoProcessualHistoricoCollectionNewVinculoProcessualHistoricoToAttach = em.getReference(vinculoProcessualHistoricoCollectionNewVinculoProcessualHistoricoToAttach.getClass(), vinculoProcessualHistoricoCollectionNewVinculoProcessualHistoricoToAttach.getId());
                attachedVinculoProcessualHistoricoCollectionNew.add(vinculoProcessualHistoricoCollectionNewVinculoProcessualHistoricoToAttach);
            }
            vinculoProcessualHistoricoCollectionNew = attachedVinculoProcessualHistoricoCollectionNew;
            tipoProcesso.setVinculoProcessualHistoricoCollection(vinculoProcessualHistoricoCollectionNew);
            tipoProcesso = em.merge(tipoProcesso);
            for (VinculoProcessual vinculoProcessualCollectionNewVinculoProcessual : vinculoProcessualCollectionNew) {
                if (!vinculoProcessualCollectionOld.contains(vinculoProcessualCollectionNewVinculoProcessual)) {
                    TipoProcesso oldTipoDeProcessoFkOfVinculoProcessualCollectionNewVinculoProcessual = vinculoProcessualCollectionNewVinculoProcessual.getTipoDeProcessoFk();
                    vinculoProcessualCollectionNewVinculoProcessual.setTipoDeProcessoFk(tipoProcesso);
                    vinculoProcessualCollectionNewVinculoProcessual = em.merge(vinculoProcessualCollectionNewVinculoProcessual);
                    if (oldTipoDeProcessoFkOfVinculoProcessualCollectionNewVinculoProcessual != null && !oldTipoDeProcessoFkOfVinculoProcessualCollectionNewVinculoProcessual.equals(tipoProcesso)) {
                        oldTipoDeProcessoFkOfVinculoProcessualCollectionNewVinculoProcessual.getVinculoProcessualCollection().remove(vinculoProcessualCollectionNewVinculoProcessual);
                        oldTipoDeProcessoFkOfVinculoProcessualCollectionNewVinculoProcessual = em.merge(oldTipoDeProcessoFkOfVinculoProcessualCollectionNewVinculoProcessual);
                    }
                }
            }
            for (VinculoProcessualHistorico vinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico : vinculoProcessualHistoricoCollectionNew) {
                if (!vinculoProcessualHistoricoCollectionOld.contains(vinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico)) {
                    TipoProcesso oldTipoDeProcessoFkOfVinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico = vinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico.getTipoDeProcessoFk();
                    vinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico.setTipoDeProcessoFk(tipoProcesso);
                    vinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico = em.merge(vinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico);
                    if (oldTipoDeProcessoFkOfVinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico != null && !oldTipoDeProcessoFkOfVinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico.equals(tipoProcesso)) {
                        oldTipoDeProcessoFkOfVinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico.getVinculoProcessualHistoricoCollection().remove(vinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico);
                        oldTipoDeProcessoFkOfVinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico = em.merge(oldTipoDeProcessoFkOfVinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico);
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
                Integer id = tipoProcesso.getId();
                if (findTipoProcesso(id) == null) {
                    throw new NonexistentEntityException("The tipoProcesso with id " + id + " no longer exists.");
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
            TipoProcesso tipoProcesso;
            try {
                tipoProcesso = em.getReference(TipoProcesso.class, id);
                tipoProcesso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoProcesso with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<VinculoProcessual> vinculoProcessualCollectionOrphanCheck = tipoProcesso.getVinculoProcessualCollection();
            for (VinculoProcessual vinculoProcessualCollectionOrphanCheckVinculoProcessual : vinculoProcessualCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoProcesso (" + tipoProcesso + ") cannot be destroyed since the VinculoProcessual " + vinculoProcessualCollectionOrphanCheckVinculoProcessual + " in its vinculoProcessualCollection field has a non-nullable tipoDeProcessoFk field.");
            }
            Collection<VinculoProcessualHistorico> vinculoProcessualHistoricoCollectionOrphanCheck = tipoProcesso.getVinculoProcessualHistoricoCollection();
            for (VinculoProcessualHistorico vinculoProcessualHistoricoCollectionOrphanCheckVinculoProcessualHistorico : vinculoProcessualHistoricoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoProcesso (" + tipoProcesso + ") cannot be destroyed since the VinculoProcessualHistorico " + vinculoProcessualHistoricoCollectionOrphanCheckVinculoProcessualHistorico + " in its vinculoProcessualHistoricoCollection field has a non-nullable tipoDeProcessoFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoProcesso);
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

    public List<TipoProcesso> findTipoProcessoEntities() {
        return findTipoProcessoEntities(true, -1, -1);
    }

    public List<TipoProcesso> findTipoProcessoEntities(int maxResults, int firstResult) {
        return findTipoProcessoEntities(false, maxResults, firstResult);
    }

    private List<TipoProcesso> findTipoProcessoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoProcesso.class));
            Root<TipoProcesso> from = cq.from(TipoProcesso.class);
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

    public TipoProcesso findTipoProcesso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoProcesso.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoProcessoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoProcesso> rt = cq.from(TipoProcesso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
