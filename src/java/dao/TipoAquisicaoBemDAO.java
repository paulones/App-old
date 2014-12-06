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
import entidade.AquisicaoBem;
import java.util.ArrayList;
import java.util.Collection;
import entidade.AquisicaoBemHistorico;
import entidade.TipoAquisicaoBem;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author paulones
 */
public class TipoAquisicaoBemDAO implements Serializable {

    public TipoAquisicaoBemDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoAquisicaoBem tipoAquisicaoBem) throws RollbackFailureException, Exception {
        if (tipoAquisicaoBem.getAquisicaoBemCollection() == null) {
            tipoAquisicaoBem.setAquisicaoBemCollection(new ArrayList<AquisicaoBem>());
        }
        if (tipoAquisicaoBem.getAquisicaoBemHistoricoCollection() == null) {
            tipoAquisicaoBem.setAquisicaoBemHistoricoCollection(new ArrayList<AquisicaoBemHistorico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            Collection<AquisicaoBem> attachedAquisicaoBemCollection = new ArrayList<AquisicaoBem>();
            for (AquisicaoBem aquisicaoBemCollectionAquisicaoBemToAttach : tipoAquisicaoBem.getAquisicaoBemCollection()) {
                aquisicaoBemCollectionAquisicaoBemToAttach = em.getReference(aquisicaoBemCollectionAquisicaoBemToAttach.getClass(), aquisicaoBemCollectionAquisicaoBemToAttach.getId());
                attachedAquisicaoBemCollection.add(aquisicaoBemCollectionAquisicaoBemToAttach);
            }
            tipoAquisicaoBem.setAquisicaoBemCollection(attachedAquisicaoBemCollection);
            Collection<AquisicaoBemHistorico> attachedAquisicaoBemHistoricoCollection = new ArrayList<AquisicaoBemHistorico>();
            for (AquisicaoBemHistorico aquisicaoBemHistoricoCollectionAquisicaoBemHistoricoToAttach : tipoAquisicaoBem.getAquisicaoBemHistoricoCollection()) {
                aquisicaoBemHistoricoCollectionAquisicaoBemHistoricoToAttach = em.getReference(aquisicaoBemHistoricoCollectionAquisicaoBemHistoricoToAttach.getClass(), aquisicaoBemHistoricoCollectionAquisicaoBemHistoricoToAttach.getId());
                attachedAquisicaoBemHistoricoCollection.add(aquisicaoBemHistoricoCollectionAquisicaoBemHistoricoToAttach);
            }
            tipoAquisicaoBem.setAquisicaoBemHistoricoCollection(attachedAquisicaoBemHistoricoCollection);
            em.persist(tipoAquisicaoBem);
            for (AquisicaoBem aquisicaoBemCollectionAquisicaoBem : tipoAquisicaoBem.getAquisicaoBemCollection()) {
                TipoAquisicaoBem oldTipoAquisicaoBemFkOfAquisicaoBemCollectionAquisicaoBem = aquisicaoBemCollectionAquisicaoBem.getTipoAquisicaoBemFk();
                aquisicaoBemCollectionAquisicaoBem.setTipoAquisicaoBemFk(tipoAquisicaoBem);
                aquisicaoBemCollectionAquisicaoBem = em.merge(aquisicaoBemCollectionAquisicaoBem);
                if (oldTipoAquisicaoBemFkOfAquisicaoBemCollectionAquisicaoBem != null) {
                    oldTipoAquisicaoBemFkOfAquisicaoBemCollectionAquisicaoBem.getAquisicaoBemCollection().remove(aquisicaoBemCollectionAquisicaoBem);
                    oldTipoAquisicaoBemFkOfAquisicaoBemCollectionAquisicaoBem = em.merge(oldTipoAquisicaoBemFkOfAquisicaoBemCollectionAquisicaoBem);
                }
            }
            for (AquisicaoBemHistorico aquisicaoBemHistoricoCollectionAquisicaoBemHistorico : tipoAquisicaoBem.getAquisicaoBemHistoricoCollection()) {
                TipoAquisicaoBem oldTipoAquisicaoBemFkOfAquisicaoBemHistoricoCollectionAquisicaoBemHistorico = aquisicaoBemHistoricoCollectionAquisicaoBemHistorico.getTipoAquisicaoBemFk();
                aquisicaoBemHistoricoCollectionAquisicaoBemHistorico.setTipoAquisicaoBemFk(tipoAquisicaoBem);
                aquisicaoBemHistoricoCollectionAquisicaoBemHistorico = em.merge(aquisicaoBemHistoricoCollectionAquisicaoBemHistorico);
                if (oldTipoAquisicaoBemFkOfAquisicaoBemHistoricoCollectionAquisicaoBemHistorico != null) {
                    oldTipoAquisicaoBemFkOfAquisicaoBemHistoricoCollectionAquisicaoBemHistorico.getAquisicaoBemHistoricoCollection().remove(aquisicaoBemHistoricoCollectionAquisicaoBemHistorico);
                    oldTipoAquisicaoBemFkOfAquisicaoBemHistoricoCollectionAquisicaoBemHistorico = em.merge(oldTipoAquisicaoBemFkOfAquisicaoBemHistoricoCollectionAquisicaoBemHistorico);
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

    public void edit(TipoAquisicaoBem tipoAquisicaoBem) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            TipoAquisicaoBem persistentTipoAquisicaoBem = em.find(TipoAquisicaoBem.class, tipoAquisicaoBem.getId());
            Collection<AquisicaoBem> aquisicaoBemCollectionOld = persistentTipoAquisicaoBem.getAquisicaoBemCollection();
            Collection<AquisicaoBem> aquisicaoBemCollectionNew = tipoAquisicaoBem.getAquisicaoBemCollection();
            Collection<AquisicaoBemHistorico> aquisicaoBemHistoricoCollectionOld = persistentTipoAquisicaoBem.getAquisicaoBemHistoricoCollection();
            Collection<AquisicaoBemHistorico> aquisicaoBemHistoricoCollectionNew = tipoAquisicaoBem.getAquisicaoBemHistoricoCollection();
            List<String> illegalOrphanMessages = null;
            for (AquisicaoBem aquisicaoBemCollectionOldAquisicaoBem : aquisicaoBemCollectionOld) {
                if (!aquisicaoBemCollectionNew.contains(aquisicaoBemCollectionOldAquisicaoBem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AquisicaoBem " + aquisicaoBemCollectionOldAquisicaoBem + " since its tipoAquisicaoBemFk field is not nullable.");
                }
            }
            for (AquisicaoBemHistorico aquisicaoBemHistoricoCollectionOldAquisicaoBemHistorico : aquisicaoBemHistoricoCollectionOld) {
                if (!aquisicaoBemHistoricoCollectionNew.contains(aquisicaoBemHistoricoCollectionOldAquisicaoBemHistorico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AquisicaoBemHistorico " + aquisicaoBemHistoricoCollectionOldAquisicaoBemHistorico + " since its tipoAquisicaoBemFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<AquisicaoBem> attachedAquisicaoBemCollectionNew = new ArrayList<AquisicaoBem>();
            for (AquisicaoBem aquisicaoBemCollectionNewAquisicaoBemToAttach : aquisicaoBemCollectionNew) {
                aquisicaoBemCollectionNewAquisicaoBemToAttach = em.getReference(aquisicaoBemCollectionNewAquisicaoBemToAttach.getClass(), aquisicaoBemCollectionNewAquisicaoBemToAttach.getId());
                attachedAquisicaoBemCollectionNew.add(aquisicaoBemCollectionNewAquisicaoBemToAttach);
            }
            aquisicaoBemCollectionNew = attachedAquisicaoBemCollectionNew;
            tipoAquisicaoBem.setAquisicaoBemCollection(aquisicaoBemCollectionNew);
            Collection<AquisicaoBemHistorico> attachedAquisicaoBemHistoricoCollectionNew = new ArrayList<AquisicaoBemHistorico>();
            for (AquisicaoBemHistorico aquisicaoBemHistoricoCollectionNewAquisicaoBemHistoricoToAttach : aquisicaoBemHistoricoCollectionNew) {
                aquisicaoBemHistoricoCollectionNewAquisicaoBemHistoricoToAttach = em.getReference(aquisicaoBemHistoricoCollectionNewAquisicaoBemHistoricoToAttach.getClass(), aquisicaoBemHistoricoCollectionNewAquisicaoBemHistoricoToAttach.getId());
                attachedAquisicaoBemHistoricoCollectionNew.add(aquisicaoBemHistoricoCollectionNewAquisicaoBemHistoricoToAttach);
            }
            aquisicaoBemHistoricoCollectionNew = attachedAquisicaoBemHistoricoCollectionNew;
            tipoAquisicaoBem.setAquisicaoBemHistoricoCollection(aquisicaoBemHistoricoCollectionNew);
            tipoAquisicaoBem = em.merge(tipoAquisicaoBem);
            for (AquisicaoBem aquisicaoBemCollectionNewAquisicaoBem : aquisicaoBemCollectionNew) {
                if (!aquisicaoBemCollectionOld.contains(aquisicaoBemCollectionNewAquisicaoBem)) {
                    TipoAquisicaoBem oldTipoAquisicaoBemFkOfAquisicaoBemCollectionNewAquisicaoBem = aquisicaoBemCollectionNewAquisicaoBem.getTipoAquisicaoBemFk();
                    aquisicaoBemCollectionNewAquisicaoBem.setTipoAquisicaoBemFk(tipoAquisicaoBem);
                    aquisicaoBemCollectionNewAquisicaoBem = em.merge(aquisicaoBemCollectionNewAquisicaoBem);
                    if (oldTipoAquisicaoBemFkOfAquisicaoBemCollectionNewAquisicaoBem != null && !oldTipoAquisicaoBemFkOfAquisicaoBemCollectionNewAquisicaoBem.equals(tipoAquisicaoBem)) {
                        oldTipoAquisicaoBemFkOfAquisicaoBemCollectionNewAquisicaoBem.getAquisicaoBemCollection().remove(aquisicaoBemCollectionNewAquisicaoBem);
                        oldTipoAquisicaoBemFkOfAquisicaoBemCollectionNewAquisicaoBem = em.merge(oldTipoAquisicaoBemFkOfAquisicaoBemCollectionNewAquisicaoBem);
                    }
                }
            }
            for (AquisicaoBemHistorico aquisicaoBemHistoricoCollectionNewAquisicaoBemHistorico : aquisicaoBemHistoricoCollectionNew) {
                if (!aquisicaoBemHistoricoCollectionOld.contains(aquisicaoBemHistoricoCollectionNewAquisicaoBemHistorico)) {
                    TipoAquisicaoBem oldTipoAquisicaoBemFkOfAquisicaoBemHistoricoCollectionNewAquisicaoBemHistorico = aquisicaoBemHistoricoCollectionNewAquisicaoBemHistorico.getTipoAquisicaoBemFk();
                    aquisicaoBemHistoricoCollectionNewAquisicaoBemHistorico.setTipoAquisicaoBemFk(tipoAquisicaoBem);
                    aquisicaoBemHistoricoCollectionNewAquisicaoBemHistorico = em.merge(aquisicaoBemHistoricoCollectionNewAquisicaoBemHistorico);
                    if (oldTipoAquisicaoBemFkOfAquisicaoBemHistoricoCollectionNewAquisicaoBemHistorico != null && !oldTipoAquisicaoBemFkOfAquisicaoBemHistoricoCollectionNewAquisicaoBemHistorico.equals(tipoAquisicaoBem)) {
                        oldTipoAquisicaoBemFkOfAquisicaoBemHistoricoCollectionNewAquisicaoBemHistorico.getAquisicaoBemHistoricoCollection().remove(aquisicaoBemHistoricoCollectionNewAquisicaoBemHistorico);
                        oldTipoAquisicaoBemFkOfAquisicaoBemHistoricoCollectionNewAquisicaoBemHistorico = em.merge(oldTipoAquisicaoBemFkOfAquisicaoBemHistoricoCollectionNewAquisicaoBemHistorico);
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
                Integer id = tipoAquisicaoBem.getId();
                if (findTipoAquisicaoBem(id) == null) {
                    throw new NonexistentEntityException("The tipoAquisicaoBem with id " + id + " no longer exists.");
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
            TipoAquisicaoBem tipoAquisicaoBem;
            try {
                tipoAquisicaoBem = em.getReference(TipoAquisicaoBem.class, id);
                tipoAquisicaoBem.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoAquisicaoBem with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<AquisicaoBem> aquisicaoBemCollectionOrphanCheck = tipoAquisicaoBem.getAquisicaoBemCollection();
            for (AquisicaoBem aquisicaoBemCollectionOrphanCheckAquisicaoBem : aquisicaoBemCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoAquisicaoBem (" + tipoAquisicaoBem + ") cannot be destroyed since the AquisicaoBem " + aquisicaoBemCollectionOrphanCheckAquisicaoBem + " in its aquisicaoBemCollection field has a non-nullable tipoAquisicaoBemFk field.");
            }
            Collection<AquisicaoBemHistorico> aquisicaoBemHistoricoCollectionOrphanCheck = tipoAquisicaoBem.getAquisicaoBemHistoricoCollection();
            for (AquisicaoBemHistorico aquisicaoBemHistoricoCollectionOrphanCheckAquisicaoBemHistorico : aquisicaoBemHistoricoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoAquisicaoBem (" + tipoAquisicaoBem + ") cannot be destroyed since the AquisicaoBemHistorico " + aquisicaoBemHistoricoCollectionOrphanCheckAquisicaoBemHistorico + " in its aquisicaoBemHistoricoCollection field has a non-nullable tipoAquisicaoBemFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoAquisicaoBem);
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

    public List<TipoAquisicaoBem> findTipoAquisicaoBemEntities() {
        return findTipoAquisicaoBemEntities(true, -1, -1);
    }

    public List<TipoAquisicaoBem> findTipoAquisicaoBemEntities(int maxResults, int firstResult) {
        return findTipoAquisicaoBemEntities(false, maxResults, firstResult);
    }

    private List<TipoAquisicaoBem> findTipoAquisicaoBemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoAquisicaoBem.class));
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

    public TipoAquisicaoBem findTipoAquisicaoBem(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoAquisicaoBem.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoAquisicaoBemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoAquisicaoBem> rt = cq.from(TipoAquisicaoBem.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
