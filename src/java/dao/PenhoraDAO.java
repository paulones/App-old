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
import entidade.ProcessoJudicial;
import entidade.TipoPenhora;
import java.io.Serializable;
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
public class PenhoraDAO implements Serializable {

    public PenhoraDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Penhora penhora) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            Bem bemFk = penhora.getBemFk();
            if (bemFk != null) {
                bemFk = em.getReference(bemFk.getClass(), bemFk.getId());
                penhora.setBemFk(bemFk);
            }
            ProcessoJudicial processoJudicialFk = penhora.getProcessoJudicialFk();
            if (processoJudicialFk != null) {
                processoJudicialFk = em.getReference(processoJudicialFk.getClass(), processoJudicialFk.getId());
                penhora.setProcessoJudicialFk(processoJudicialFk);
            }
            TipoPenhora tipoPenhoraFk = penhora.getTipoPenhoraFk();
            if (tipoPenhoraFk != null) {
                tipoPenhoraFk = em.getReference(tipoPenhoraFk.getClass(), tipoPenhoraFk.getId());
                penhora.setTipoPenhoraFk(tipoPenhoraFk);
            }
            em.persist(penhora);
            if (bemFk != null) {
                bemFk.getPenhoraCollection().add(penhora);
                bemFk = em.merge(bemFk);
            }
            if (processoJudicialFk != null) {
                processoJudicialFk.getPenhoraCollection().add(penhora);
                processoJudicialFk = em.merge(processoJudicialFk);
            }
            if (tipoPenhoraFk != null) {
                tipoPenhoraFk.getPenhoraCollection().add(penhora);
                tipoPenhoraFk = em.merge(tipoPenhoraFk);
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

    public void edit(Penhora penhora) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            Penhora persistentPenhora = em.find(Penhora.class, penhora.getId());
            Bem bemFkOld = persistentPenhora.getBemFk();
            Bem bemFkNew = penhora.getBemFk();
            ProcessoJudicial processoJudicialFkOld = persistentPenhora.getProcessoJudicialFk();
            ProcessoJudicial processoJudicialFkNew = penhora.getProcessoJudicialFk();
            TipoPenhora tipoPenhoraFkOld = persistentPenhora.getTipoPenhoraFk();
            TipoPenhora tipoPenhoraFkNew = penhora.getTipoPenhoraFk();
            if (bemFkNew != null) {
                bemFkNew = em.getReference(bemFkNew.getClass(), bemFkNew.getId());
                penhora.setBemFk(bemFkNew);
            }
            if (processoJudicialFkNew != null) {
                processoJudicialFkNew = em.getReference(processoJudicialFkNew.getClass(), processoJudicialFkNew.getId());
                penhora.setProcessoJudicialFk(processoJudicialFkNew);
            }
            if (tipoPenhoraFkNew != null) {
                tipoPenhoraFkNew = em.getReference(tipoPenhoraFkNew.getClass(), tipoPenhoraFkNew.getId());
                penhora.setTipoPenhoraFk(tipoPenhoraFkNew);
            }
            penhora = em.merge(penhora);
            if (bemFkOld != null && !bemFkOld.equals(bemFkNew)) {
                bemFkOld.getPenhoraCollection().remove(penhora);
                bemFkOld = em.merge(bemFkOld);
            }
            if (bemFkNew != null && !bemFkNew.equals(bemFkOld)) {
                bemFkNew.getPenhoraCollection().add(penhora);
                bemFkNew = em.merge(bemFkNew);
            }
            if (processoJudicialFkOld != null && !processoJudicialFkOld.equals(processoJudicialFkNew)) {
                processoJudicialFkOld.getPenhoraCollection().remove(penhora);
                processoJudicialFkOld = em.merge(processoJudicialFkOld);
            }
            if (processoJudicialFkNew != null && !processoJudicialFkNew.equals(processoJudicialFkOld)) {
                processoJudicialFkNew.getPenhoraCollection().add(penhora);
                processoJudicialFkNew = em.merge(processoJudicialFkNew);
            }
            if (tipoPenhoraFkOld != null && !tipoPenhoraFkOld.equals(tipoPenhoraFkNew)) {
                tipoPenhoraFkOld.getPenhoraCollection().remove(penhora);
                tipoPenhoraFkOld = em.merge(tipoPenhoraFkOld);
            }
            if (tipoPenhoraFkNew != null && !tipoPenhoraFkNew.equals(tipoPenhoraFkOld)) {
                tipoPenhoraFkNew.getPenhoraCollection().add(penhora);
                tipoPenhoraFkNew = em.merge(tipoPenhoraFkNew);
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
                Integer id = penhora.getId();
                if (findPenhora(id) == null) {
                    throw new NonexistentEntityException("The penhora with id " + id + " no longer exists.");
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
            Penhora penhora;
            try {
                penhora = em.getReference(Penhora.class, id);
                penhora.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The penhora with id " + id + " no longer exists.", enfe);
            }
            Bem bemFk = penhora.getBemFk();
            if (bemFk != null) {
                bemFk.getPenhoraCollection().remove(penhora);
                bemFk = em.merge(bemFk);
            }
            ProcessoJudicial processoJudicialFk = penhora.getProcessoJudicialFk();
            if (processoJudicialFk != null) {
                processoJudicialFk.getPenhoraCollection().remove(penhora);
                processoJudicialFk = em.merge(processoJudicialFk);
            }
            TipoPenhora tipoPenhoraFk = penhora.getTipoPenhoraFk();
            if (tipoPenhoraFk != null) {
                tipoPenhoraFk.getPenhoraCollection().remove(penhora);
                tipoPenhoraFk = em.merge(tipoPenhoraFk);
            }
            em.remove(penhora);
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

    public List<Penhora> findPenhoraEntities() {
        return findPenhoraEntities(true, -1, -1);
    }

    public List<Penhora> findPenhoraEntities(int maxResults, int firstResult) {
        return findPenhoraEntities(false, maxResults, firstResult);
    }

    private List<Penhora> findPenhoraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Penhora.class));
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

    public Penhora findPenhora(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Penhora.class, id);
        } finally {
            em.close();
        }
    }

    public int getPenhoraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Penhora> rt = cq.from(Penhora.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Penhora> findByPJUD(Integer id){
        EntityManager em = getEntityManager();
        try {
            List<Penhora> penhoraList = (List<Penhora>) em.createNativeQuery("select * from penhora "
                    + "where processo_judicial_fk = '"+id+"'", Penhora.class).getResultList();
            return penhoraList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public void destroyByPJUD(Integer idPjud) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.createNativeQuery("delete from penhora "
                    + "where processo_judicial_fk = '" + idPjud + "'").executeUpdate();
            em.getTransaction().commit();
        } catch (NoResultException e) {
        } finally {
            em.close();
        }
    }
}
