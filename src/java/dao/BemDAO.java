/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.Bem;
import entidade.Penhora;
import entidade.PenhoraHistorico;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidade.ProcessoJudicial;
import entidade.TipoBem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.transaction.UserTransaction;

/**
 *
 * @author paulones
 */
public class BemDAO implements Serializable {

    public BemDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bem bem) throws RollbackFailureException, Exception {
        if (bem.getPenhoraHistoricoCollection() == null) {
            bem.setPenhoraHistoricoCollection(new ArrayList<PenhoraHistorico>());
        }
        if (bem.getPenhoraCollection() == null) {
            bem.setPenhoraCollection(new ArrayList<Penhora>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            TipoBem tipoBemFk = bem.getTipoBemFk();
            if (tipoBemFk != null) {
                tipoBemFk = em.getReference(tipoBemFk.getClass(), tipoBemFk.getId());
                bem.setTipoBemFk(tipoBemFk);
            }
            Collection<PenhoraHistorico> attachedPenhoraHistoricoCollection = new ArrayList<PenhoraHistorico>();
            for (PenhoraHistorico penhoraHistoricoCollectionPenhoraHistoricoToAttach : bem.getPenhoraHistoricoCollection()) {
                penhoraHistoricoCollectionPenhoraHistoricoToAttach = em.getReference(penhoraHistoricoCollectionPenhoraHistoricoToAttach.getClass(), penhoraHistoricoCollectionPenhoraHistoricoToAttach.getId());
                attachedPenhoraHistoricoCollection.add(penhoraHistoricoCollectionPenhoraHistoricoToAttach);
            }
            bem.setPenhoraHistoricoCollection(attachedPenhoraHistoricoCollection);
            Collection<Penhora> attachedPenhoraCollection = new ArrayList<Penhora>();
            for (Penhora penhoraCollectionPenhoraToAttach : bem.getPenhoraCollection()) {
                penhoraCollectionPenhoraToAttach = em.getReference(penhoraCollectionPenhoraToAttach.getClass(), penhoraCollectionPenhoraToAttach.getId());
                attachedPenhoraCollection.add(penhoraCollectionPenhoraToAttach);
            }
            bem.setPenhoraCollection(attachedPenhoraCollection);
            em.persist(bem);
            if (tipoBemFk != null) {
                tipoBemFk.getBemCollection().add(bem);
                tipoBemFk = em.merge(tipoBemFk);
            }
            for (PenhoraHistorico penhoraHistoricoCollectionPenhoraHistorico : bem.getPenhoraHistoricoCollection()) {
                Bem oldBemFkOfPenhoraHistoricoCollectionPenhoraHistorico = penhoraHistoricoCollectionPenhoraHistorico.getBemFk();
                penhoraHistoricoCollectionPenhoraHistorico.setBemFk(bem);
                penhoraHistoricoCollectionPenhoraHistorico = em.merge(penhoraHistoricoCollectionPenhoraHistorico);
                if (oldBemFkOfPenhoraHistoricoCollectionPenhoraHistorico != null) {
                    oldBemFkOfPenhoraHistoricoCollectionPenhoraHistorico.getPenhoraHistoricoCollection().remove(penhoraHistoricoCollectionPenhoraHistorico);
                    oldBemFkOfPenhoraHistoricoCollectionPenhoraHistorico = em.merge(oldBemFkOfPenhoraHistoricoCollectionPenhoraHistorico);
                }
            }
            for (Penhora penhoraCollectionPenhora : bem.getPenhoraCollection()) {
                Bem oldBemFkOfPenhoraCollectionPenhora = penhoraCollectionPenhora.getBemFk();
                penhoraCollectionPenhora.setBemFk(bem);
                penhoraCollectionPenhora = em.merge(penhoraCollectionPenhora);
                if (oldBemFkOfPenhoraCollectionPenhora != null) {
                    oldBemFkOfPenhoraCollectionPenhora.getPenhoraCollection().remove(penhoraCollectionPenhora);
                    oldBemFkOfPenhoraCollectionPenhora = em.merge(oldBemFkOfPenhoraCollectionPenhora);
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

    public void edit(Bem bem) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            Bem persistentBem = em.find(Bem.class, bem.getId());
            TipoBem tipoBemFkOld = persistentBem.getTipoBemFk();
            TipoBem tipoBemFkNew = bem.getTipoBemFk();
            Collection<PenhoraHistorico> penhoraHistoricoCollectionOld = persistentBem.getPenhoraHistoricoCollection();
            Collection<PenhoraHistorico> penhoraHistoricoCollectionNew = bem.getPenhoraHistoricoCollection();
            Collection<Penhora> penhoraCollectionOld = persistentBem.getPenhoraCollection();
            Collection<Penhora> penhoraCollectionNew = bem.getPenhoraCollection();
            if (tipoBemFkNew != null) {
                tipoBemFkNew = em.getReference(tipoBemFkNew.getClass(), tipoBemFkNew.getId());
                bem.setTipoBemFk(tipoBemFkNew);
            }
            Collection<PenhoraHistorico> attachedPenhoraHistoricoCollectionNew = new ArrayList<PenhoraHistorico>();
            for (PenhoraHistorico penhoraHistoricoCollectionNewPenhoraHistoricoToAttach : penhoraHistoricoCollectionNew) {
                penhoraHistoricoCollectionNewPenhoraHistoricoToAttach = em.getReference(penhoraHistoricoCollectionNewPenhoraHistoricoToAttach.getClass(), penhoraHistoricoCollectionNewPenhoraHistoricoToAttach.getId());
                attachedPenhoraHistoricoCollectionNew.add(penhoraHistoricoCollectionNewPenhoraHistoricoToAttach);
            }
            penhoraHistoricoCollectionNew = attachedPenhoraHistoricoCollectionNew;
            bem.setPenhoraHistoricoCollection(penhoraHistoricoCollectionNew);
            Collection<Penhora> attachedPenhoraCollectionNew = new ArrayList<Penhora>();
            for (Penhora penhoraCollectionNewPenhoraToAttach : penhoraCollectionNew) {
                penhoraCollectionNewPenhoraToAttach = em.getReference(penhoraCollectionNewPenhoraToAttach.getClass(), penhoraCollectionNewPenhoraToAttach.getId());
                attachedPenhoraCollectionNew.add(penhoraCollectionNewPenhoraToAttach);
            }
            penhoraCollectionNew = attachedPenhoraCollectionNew;
            bem.setPenhoraCollection(penhoraCollectionNew);
            bem = em.merge(bem);
            if (tipoBemFkOld != null && !tipoBemFkOld.equals(tipoBemFkNew)) {
                tipoBemFkOld.getBemCollection().remove(bem);
                tipoBemFkOld = em.merge(tipoBemFkOld);
            }
            if (tipoBemFkNew != null && !tipoBemFkNew.equals(tipoBemFkOld)) {
                tipoBemFkNew.getBemCollection().add(bem);
                tipoBemFkNew = em.merge(tipoBemFkNew);
            }
            for (PenhoraHistorico penhoraHistoricoCollectionOldPenhoraHistorico : penhoraHistoricoCollectionOld) {
                if (!penhoraHistoricoCollectionNew.contains(penhoraHistoricoCollectionOldPenhoraHistorico)) {
                    penhoraHistoricoCollectionOldPenhoraHistorico.setBemFk(null);
                    penhoraHistoricoCollectionOldPenhoraHistorico = em.merge(penhoraHistoricoCollectionOldPenhoraHistorico);
                }
            }
            for (PenhoraHistorico penhoraHistoricoCollectionNewPenhoraHistorico : penhoraHistoricoCollectionNew) {
                if (!penhoraHistoricoCollectionOld.contains(penhoraHistoricoCollectionNewPenhoraHistorico)) {
                    Bem oldBemFkOfPenhoraHistoricoCollectionNewPenhoraHistorico = penhoraHistoricoCollectionNewPenhoraHistorico.getBemFk();
                    penhoraHistoricoCollectionNewPenhoraHistorico.setBemFk(bem);
                    penhoraHistoricoCollectionNewPenhoraHistorico = em.merge(penhoraHistoricoCollectionNewPenhoraHistorico);
                    if (oldBemFkOfPenhoraHistoricoCollectionNewPenhoraHistorico != null && !oldBemFkOfPenhoraHistoricoCollectionNewPenhoraHistorico.equals(bem)) {
                        oldBemFkOfPenhoraHistoricoCollectionNewPenhoraHistorico.getPenhoraHistoricoCollection().remove(penhoraHistoricoCollectionNewPenhoraHistorico);
                        oldBemFkOfPenhoraHistoricoCollectionNewPenhoraHistorico = em.merge(oldBemFkOfPenhoraHistoricoCollectionNewPenhoraHistorico);
                    }
                }
            }
            for (Penhora penhoraCollectionOldPenhora : penhoraCollectionOld) {
                if (!penhoraCollectionNew.contains(penhoraCollectionOldPenhora)) {
                    penhoraCollectionOldPenhora.setBemFk(null);
                    penhoraCollectionOldPenhora = em.merge(penhoraCollectionOldPenhora);
                }
            }
            for (Penhora penhoraCollectionNewPenhora : penhoraCollectionNew) {
                if (!penhoraCollectionOld.contains(penhoraCollectionNewPenhora)) {
                    Bem oldBemFkOfPenhoraCollectionNewPenhora = penhoraCollectionNewPenhora.getBemFk();
                    penhoraCollectionNewPenhora.setBemFk(bem);
                    penhoraCollectionNewPenhora = em.merge(penhoraCollectionNewPenhora);
                    if (oldBemFkOfPenhoraCollectionNewPenhora != null && !oldBemFkOfPenhoraCollectionNewPenhora.equals(bem)) {
                        oldBemFkOfPenhoraCollectionNewPenhora.getPenhoraCollection().remove(penhoraCollectionNewPenhora);
                        oldBemFkOfPenhoraCollectionNewPenhora = em.merge(oldBemFkOfPenhoraCollectionNewPenhora);
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
                Integer id = bem.getId();
                if (findBem(id) == null) {
                    throw new NonexistentEntityException("The bem with id " + id + " no longer exists.");
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
            Bem bem;
            try {
                bem = em.getReference(Bem.class, id);
                bem.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bem with id " + id + " no longer exists.", enfe);
            }
            TipoBem tipoBemFk = bem.getTipoBemFk();
            if (tipoBemFk != null) {
                tipoBemFk.getBemCollection().remove(bem);
                tipoBemFk = em.merge(tipoBemFk);
            }
            Collection<PenhoraHistorico> penhoraHistoricoCollection = bem.getPenhoraHistoricoCollection();
            for (PenhoraHistorico penhoraHistoricoCollectionPenhoraHistorico : penhoraHistoricoCollection) {
                penhoraHistoricoCollectionPenhoraHistorico.setBemFk(null);
                penhoraHistoricoCollectionPenhoraHistorico = em.merge(penhoraHistoricoCollectionPenhoraHistorico);
            }
            Collection<Penhora> penhoraCollection = bem.getPenhoraCollection();
            for (Penhora penhoraCollectionPenhora : penhoraCollection) {
                penhoraCollectionPenhora.setBemFk(null);
                penhoraCollectionPenhora = em.merge(penhoraCollectionPenhora);
            }
            em.remove(bem);
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

    public List<Bem> findBemEntities() {
        return findBemEntities(true, -1, -1);
    }

    public List<Bem> findBemEntities(int maxResults, int firstResult) {
        return findBemEntities(false, maxResults, firstResult);
    }

    private List<Bem> findBemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bem.class));
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

    public Bem findBem(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bem.class, id);
        } finally {
            em.close();
        }
    }

    public int getBemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bem> rt = cq.from(Bem.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Bem> findPFBens(Integer id) {
        EntityManager em = getEntityManager();
        try {
            List<Bem> processoJudicialList = (List<Bem>) em.createNativeQuery("select * from bem "
                    + "where tipo = 'PF' and id_fk = '" + id + "' and status = 'A'", Bem.class).getResultList();
            return processoJudicialList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Bem> findPJBens(Integer id) {
        EntityManager em = getEntityManager();
        try {
            List<Bem> processoJudicialList = (List<Bem>) em.createNativeQuery("select * from bem "
                    + "where tipo = 'PJ' and id_fk = '" + id + "' and status = 'A'", Bem.class).getResultList();
            return processoJudicialList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public void destroyByPF(Integer idPf) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.createNativeQuery("delete from bem "
                    + "where tipo = 'PF' and id_fk = '" + idPf + "'").executeUpdate();
            em.getTransaction().commit();
        } catch (NoResultException e) {
        } finally {
            em.close();
        }
    }

    public void destroyByPJ(Integer idPj) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.createNativeQuery("delete from bem "
                    + "where tipo = 'PJ' and id_fk = '" + idPj + "'").executeUpdate();
            em.getTransaction().commit();
        } catch (NoResultException e) {
        } finally {
            em.close();
        }
    }

}
