/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.Penhora;
import entidade.PenhoraHistorico;
import entidade.TipoPenhora;
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
public class TipoPenhoraDAO implements Serializable {

    public TipoPenhoraDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoPenhora tipoPenhora) throws RollbackFailureException, Exception {
        if (tipoPenhora.getPenhoraHistoricoCollection() == null) {
            tipoPenhora.setPenhoraHistoricoCollection(new ArrayList<PenhoraHistorico>());
        }
        if (tipoPenhora.getPenhoraCollection() == null) {
            tipoPenhora.setPenhoraCollection(new ArrayList<Penhora>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            Collection<PenhoraHistorico> attachedPenhoraHistoricoCollection = new ArrayList<PenhoraHistorico>();
            for (PenhoraHistorico penhoraHistoricoCollectionPenhoraHistoricoToAttach : tipoPenhora.getPenhoraHistoricoCollection()) {
                penhoraHistoricoCollectionPenhoraHistoricoToAttach = em.getReference(penhoraHistoricoCollectionPenhoraHistoricoToAttach.getClass(), penhoraHistoricoCollectionPenhoraHistoricoToAttach.getId());
                attachedPenhoraHistoricoCollection.add(penhoraHistoricoCollectionPenhoraHistoricoToAttach);
            }
            tipoPenhora.setPenhoraHistoricoCollection(attachedPenhoraHistoricoCollection);
            Collection<Penhora> attachedPenhoraCollection = new ArrayList<Penhora>();
            for (Penhora penhoraCollectionPenhoraToAttach : tipoPenhora.getPenhoraCollection()) {
                penhoraCollectionPenhoraToAttach = em.getReference(penhoraCollectionPenhoraToAttach.getClass(), penhoraCollectionPenhoraToAttach.getId());
                attachedPenhoraCollection.add(penhoraCollectionPenhoraToAttach);
            }
            tipoPenhora.setPenhoraCollection(attachedPenhoraCollection);
            em.persist(tipoPenhora);
            for (PenhoraHistorico penhoraHistoricoCollectionPenhoraHistorico : tipoPenhora.getPenhoraHistoricoCollection()) {
                TipoPenhora oldTipoPenhoraFkOfPenhoraHistoricoCollectionPenhoraHistorico = penhoraHistoricoCollectionPenhoraHistorico.getTipoPenhoraFk();
                penhoraHistoricoCollectionPenhoraHistorico.setTipoPenhoraFk(tipoPenhora);
                penhoraHistoricoCollectionPenhoraHistorico = em.merge(penhoraHistoricoCollectionPenhoraHistorico);
                if (oldTipoPenhoraFkOfPenhoraHistoricoCollectionPenhoraHistorico != null) {
                    oldTipoPenhoraFkOfPenhoraHistoricoCollectionPenhoraHistorico.getPenhoraHistoricoCollection().remove(penhoraHistoricoCollectionPenhoraHistorico);
                    oldTipoPenhoraFkOfPenhoraHistoricoCollectionPenhoraHistorico = em.merge(oldTipoPenhoraFkOfPenhoraHistoricoCollectionPenhoraHistorico);
                }
            }
            for (Penhora penhoraCollectionPenhora : tipoPenhora.getPenhoraCollection()) {
                TipoPenhora oldTipoPenhoraFkOfPenhoraCollectionPenhora = penhoraCollectionPenhora.getTipoPenhoraFk();
                penhoraCollectionPenhora.setTipoPenhoraFk(tipoPenhora);
                penhoraCollectionPenhora = em.merge(penhoraCollectionPenhora);
                if (oldTipoPenhoraFkOfPenhoraCollectionPenhora != null) {
                    oldTipoPenhoraFkOfPenhoraCollectionPenhora.getPenhoraCollection().remove(penhoraCollectionPenhora);
                    oldTipoPenhoraFkOfPenhoraCollectionPenhora = em.merge(oldTipoPenhoraFkOfPenhoraCollectionPenhora);
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

    public void edit(TipoPenhora tipoPenhora) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            TipoPenhora persistentTipoPenhora = em.find(TipoPenhora.class, tipoPenhora.getId());
            Collection<PenhoraHistorico> penhoraHistoricoCollectionOld = persistentTipoPenhora.getPenhoraHistoricoCollection();
            Collection<PenhoraHistorico> penhoraHistoricoCollectionNew = tipoPenhora.getPenhoraHistoricoCollection();
            Collection<Penhora> penhoraCollectionOld = persistentTipoPenhora.getPenhoraCollection();
            Collection<Penhora> penhoraCollectionNew = tipoPenhora.getPenhoraCollection();
            List<String> illegalOrphanMessages = null;
            for (PenhoraHistorico penhoraHistoricoCollectionOldPenhoraHistorico : penhoraHistoricoCollectionOld) {
                if (!penhoraHistoricoCollectionNew.contains(penhoraHistoricoCollectionOldPenhoraHistorico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PenhoraHistorico " + penhoraHistoricoCollectionOldPenhoraHistorico + " since its tipoPenhoraFk field is not nullable.");
                }
            }
            for (Penhora penhoraCollectionOldPenhora : penhoraCollectionOld) {
                if (!penhoraCollectionNew.contains(penhoraCollectionOldPenhora)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Penhora " + penhoraCollectionOldPenhora + " since its tipoPenhoraFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<PenhoraHistorico> attachedPenhoraHistoricoCollectionNew = new ArrayList<PenhoraHistorico>();
            for (PenhoraHistorico penhoraHistoricoCollectionNewPenhoraHistoricoToAttach : penhoraHistoricoCollectionNew) {
                penhoraHistoricoCollectionNewPenhoraHistoricoToAttach = em.getReference(penhoraHistoricoCollectionNewPenhoraHistoricoToAttach.getClass(), penhoraHistoricoCollectionNewPenhoraHistoricoToAttach.getId());
                attachedPenhoraHistoricoCollectionNew.add(penhoraHistoricoCollectionNewPenhoraHistoricoToAttach);
            }
            penhoraHistoricoCollectionNew = attachedPenhoraHistoricoCollectionNew;
            tipoPenhora.setPenhoraHistoricoCollection(penhoraHistoricoCollectionNew);
            Collection<Penhora> attachedPenhoraCollectionNew = new ArrayList<Penhora>();
            for (Penhora penhoraCollectionNewPenhoraToAttach : penhoraCollectionNew) {
                penhoraCollectionNewPenhoraToAttach = em.getReference(penhoraCollectionNewPenhoraToAttach.getClass(), penhoraCollectionNewPenhoraToAttach.getId());
                attachedPenhoraCollectionNew.add(penhoraCollectionNewPenhoraToAttach);
            }
            penhoraCollectionNew = attachedPenhoraCollectionNew;
            tipoPenhora.setPenhoraCollection(penhoraCollectionNew);
            tipoPenhora = em.merge(tipoPenhora);
            for (PenhoraHistorico penhoraHistoricoCollectionNewPenhoraHistorico : penhoraHistoricoCollectionNew) {
                if (!penhoraHistoricoCollectionOld.contains(penhoraHistoricoCollectionNewPenhoraHistorico)) {
                    TipoPenhora oldTipoPenhoraFkOfPenhoraHistoricoCollectionNewPenhoraHistorico = penhoraHistoricoCollectionNewPenhoraHistorico.getTipoPenhoraFk();
                    penhoraHistoricoCollectionNewPenhoraHistorico.setTipoPenhoraFk(tipoPenhora);
                    penhoraHistoricoCollectionNewPenhoraHistorico = em.merge(penhoraHistoricoCollectionNewPenhoraHistorico);
                    if (oldTipoPenhoraFkOfPenhoraHistoricoCollectionNewPenhoraHistorico != null && !oldTipoPenhoraFkOfPenhoraHistoricoCollectionNewPenhoraHistorico.equals(tipoPenhora)) {
                        oldTipoPenhoraFkOfPenhoraHistoricoCollectionNewPenhoraHistorico.getPenhoraHistoricoCollection().remove(penhoraHistoricoCollectionNewPenhoraHistorico);
                        oldTipoPenhoraFkOfPenhoraHistoricoCollectionNewPenhoraHistorico = em.merge(oldTipoPenhoraFkOfPenhoraHistoricoCollectionNewPenhoraHistorico);
                    }
                }
            }
            for (Penhora penhoraCollectionNewPenhora : penhoraCollectionNew) {
                if (!penhoraCollectionOld.contains(penhoraCollectionNewPenhora)) {
                    TipoPenhora oldTipoPenhoraFkOfPenhoraCollectionNewPenhora = penhoraCollectionNewPenhora.getTipoPenhoraFk();
                    penhoraCollectionNewPenhora.setTipoPenhoraFk(tipoPenhora);
                    penhoraCollectionNewPenhora = em.merge(penhoraCollectionNewPenhora);
                    if (oldTipoPenhoraFkOfPenhoraCollectionNewPenhora != null && !oldTipoPenhoraFkOfPenhoraCollectionNewPenhora.equals(tipoPenhora)) {
                        oldTipoPenhoraFkOfPenhoraCollectionNewPenhora.getPenhoraCollection().remove(penhoraCollectionNewPenhora);
                        oldTipoPenhoraFkOfPenhoraCollectionNewPenhora = em.merge(oldTipoPenhoraFkOfPenhoraCollectionNewPenhora);
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
                Integer id = tipoPenhora.getId();
                if (findTipoPenhora(id) == null) {
                    throw new NonexistentEntityException("The tipoPenhora with id " + id + " no longer exists.");
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
            TipoPenhora tipoPenhora;
            try {
                tipoPenhora = em.getReference(TipoPenhora.class, id);
                tipoPenhora.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoPenhora with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PenhoraHistorico> penhoraHistoricoCollectionOrphanCheck = tipoPenhora.getPenhoraHistoricoCollection();
            for (PenhoraHistorico penhoraHistoricoCollectionOrphanCheckPenhoraHistorico : penhoraHistoricoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoPenhora (" + tipoPenhora + ") cannot be destroyed since the PenhoraHistorico " + penhoraHistoricoCollectionOrphanCheckPenhoraHistorico + " in its penhoraHistoricoCollection field has a non-nullable tipoPenhoraFk field.");
            }
            Collection<Penhora> penhoraCollectionOrphanCheck = tipoPenhora.getPenhoraCollection();
            for (Penhora penhoraCollectionOrphanCheckPenhora : penhoraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoPenhora (" + tipoPenhora + ") cannot be destroyed since the Penhora " + penhoraCollectionOrphanCheckPenhora + " in its penhoraCollection field has a non-nullable tipoPenhoraFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoPenhora);
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

    public List<TipoPenhora> findTipoPenhoraEntities() {
        return findTipoPenhoraEntities(true, -1, -1);
    }

    public List<TipoPenhora> findTipoPenhoraEntities(int maxResults, int firstResult) {
        return findTipoPenhoraEntities(false, maxResults, firstResult);
    }

    private List<TipoPenhora> findTipoPenhoraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoPenhora.class));
            Root<TipoPenhora> from = cq.from(TipoPenhora.class);
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

    public TipoPenhora findTipoPenhora(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoPenhora.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoPenhoraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoPenhora> rt = cq.from(TipoPenhora.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<TipoPenhora> findPenhorasSemSocio() {
        EntityManager em = getEntityManager();
        try {
            List<TipoPenhora> tipoPenhoraList = (List<TipoPenhora>) em.createNativeQuery("select * from tipo_penhora "
                    + "where tipo not like '%Sócio%'", TipoPenhora.class).getResultList();
            return tipoPenhoraList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
}
