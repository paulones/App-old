/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.Bem;
import entidade.PenhoraHistorico;
import entidade.ProcessoJudicialHistorico;
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
public class PenhoraHistoricoDAO implements Serializable {

    public PenhoraHistoricoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PenhoraHistorico penhoraHistorico) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            Bem bemFk = penhoraHistorico.getBemFk();
            if (bemFk != null) {
                bemFk = em.getReference(bemFk.getClass(), bemFk.getId());
                penhoraHistorico.setBemFk(bemFk);
            }
            ProcessoJudicialHistorico processoJudicialHistoricoFk = penhoraHistorico.getProcessoJudicialHistoricoFk();
            if (processoJudicialHistoricoFk != null) {
                processoJudicialHistoricoFk = em.getReference(processoJudicialHistoricoFk.getClass(), processoJudicialHistoricoFk.getId());
                penhoraHistorico.setProcessoJudicialHistoricoFk(processoJudicialHistoricoFk);
            }
            TipoPenhora tipoPenhoraFk = penhoraHistorico.getTipoPenhoraFk();
            if (tipoPenhoraFk != null) {
                tipoPenhoraFk = em.getReference(tipoPenhoraFk.getClass(), tipoPenhoraFk.getId());
                penhoraHistorico.setTipoPenhoraFk(tipoPenhoraFk);
            }
            em.persist(penhoraHistorico);
            if (bemFk != null) {
                bemFk.getPenhoraHistoricoCollection().add(penhoraHistorico);
                bemFk = em.merge(bemFk);
            }
            if (processoJudicialHistoricoFk != null) {
                processoJudicialHistoricoFk.getPenhoraHistoricoCollection().add(penhoraHistorico);
                processoJudicialHistoricoFk = em.merge(processoJudicialHistoricoFk);
            }
            if (tipoPenhoraFk != null) {
                tipoPenhoraFk.getPenhoraHistoricoCollection().add(penhoraHistorico);
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

    public void edit(PenhoraHistorico penhoraHistorico) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            PenhoraHistorico persistentPenhoraHistorico = em.find(PenhoraHistorico.class, penhoraHistorico.getId());
            Bem bemFkOld = persistentPenhoraHistorico.getBemFk();
            Bem bemFkNew = penhoraHistorico.getBemFk();
            ProcessoJudicialHistorico processoJudicialHistoricoFkOld = persistentPenhoraHistorico.getProcessoJudicialHistoricoFk();
            ProcessoJudicialHistorico processoJudicialHistoricoFkNew = penhoraHistorico.getProcessoJudicialHistoricoFk();
            TipoPenhora tipoPenhoraFkOld = persistentPenhoraHistorico.getTipoPenhoraFk();
            TipoPenhora tipoPenhoraFkNew = penhoraHistorico.getTipoPenhoraFk();
            if (bemFkNew != null) {
                bemFkNew = em.getReference(bemFkNew.getClass(), bemFkNew.getId());
                penhoraHistorico.setBemFk(bemFkNew);
            }
            if (processoJudicialHistoricoFkNew != null) {
                processoJudicialHistoricoFkNew = em.getReference(processoJudicialHistoricoFkNew.getClass(), processoJudicialHistoricoFkNew.getId());
                penhoraHistorico.setProcessoJudicialHistoricoFk(processoJudicialHistoricoFkNew);
            }
            if (tipoPenhoraFkNew != null) {
                tipoPenhoraFkNew = em.getReference(tipoPenhoraFkNew.getClass(), tipoPenhoraFkNew.getId());
                penhoraHistorico.setTipoPenhoraFk(tipoPenhoraFkNew);
            }
            penhoraHistorico = em.merge(penhoraHistorico);
            if (bemFkOld != null && !bemFkOld.equals(bemFkNew)) {
                bemFkOld.getPenhoraHistoricoCollection().remove(penhoraHistorico);
                bemFkOld = em.merge(bemFkOld);
            }
            if (bemFkNew != null && !bemFkNew.equals(bemFkOld)) {
                bemFkNew.getPenhoraHistoricoCollection().add(penhoraHistorico);
                bemFkNew = em.merge(bemFkNew);
            }
            if (processoJudicialHistoricoFkOld != null && !processoJudicialHistoricoFkOld.equals(processoJudicialHistoricoFkNew)) {
                processoJudicialHistoricoFkOld.getPenhoraHistoricoCollection().remove(penhoraHistorico);
                processoJudicialHistoricoFkOld = em.merge(processoJudicialHistoricoFkOld);
            }
            if (processoJudicialHistoricoFkNew != null && !processoJudicialHistoricoFkNew.equals(processoJudicialHistoricoFkOld)) {
                processoJudicialHistoricoFkNew.getPenhoraHistoricoCollection().add(penhoraHistorico);
                processoJudicialHistoricoFkNew = em.merge(processoJudicialHistoricoFkNew);
            }
            if (tipoPenhoraFkOld != null && !tipoPenhoraFkOld.equals(tipoPenhoraFkNew)) {
                tipoPenhoraFkOld.getPenhoraHistoricoCollection().remove(penhoraHistorico);
                tipoPenhoraFkOld = em.merge(tipoPenhoraFkOld);
            }
            if (tipoPenhoraFkNew != null && !tipoPenhoraFkNew.equals(tipoPenhoraFkOld)) {
                tipoPenhoraFkNew.getPenhoraHistoricoCollection().add(penhoraHistorico);
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
                Integer id = penhoraHistorico.getId();
                if (findPenhoraHistorico(id) == null) {
                    throw new NonexistentEntityException("The penhoraHistorico with id " + id + " no longer exists.");
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
            PenhoraHistorico penhoraHistorico;
            try {
                penhoraHistorico = em.getReference(PenhoraHistorico.class, id);
                penhoraHistorico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The penhoraHistorico with id " + id + " no longer exists.", enfe);
            }
            Bem bemFk = penhoraHistorico.getBemFk();
            if (bemFk != null) {
                bemFk.getPenhoraHistoricoCollection().remove(penhoraHistorico);
                bemFk = em.merge(bemFk);
            }
            ProcessoJudicialHistorico processoJudicialHistoricoFk = penhoraHistorico.getProcessoJudicialHistoricoFk();
            if (processoJudicialHistoricoFk != null) {
                processoJudicialHistoricoFk.getPenhoraHistoricoCollection().remove(penhoraHistorico);
                processoJudicialHistoricoFk = em.merge(processoJudicialHistoricoFk);
            }
            TipoPenhora tipoPenhoraFk = penhoraHistorico.getTipoPenhoraFk();
            if (tipoPenhoraFk != null) {
                tipoPenhoraFk.getPenhoraHistoricoCollection().remove(penhoraHistorico);
                tipoPenhoraFk = em.merge(tipoPenhoraFk);
            }
            em.remove(penhoraHistorico);
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

    public List<PenhoraHistorico> findPenhoraHistoricoEntities() {
        return findPenhoraHistoricoEntities(true, -1, -1);
    }

    public List<PenhoraHistorico> findPenhoraHistoricoEntities(int maxResults, int firstResult) {
        return findPenhoraHistoricoEntities(false, maxResults, firstResult);
    }

    private List<PenhoraHistorico> findPenhoraHistoricoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PenhoraHistorico.class));
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

    public PenhoraHistorico findPenhoraHistorico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PenhoraHistorico.class, id);
        } finally {
            em.close();
        }
    }

    public int getPenhoraHistoricoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PenhoraHistorico> rt = cq.from(PenhoraHistorico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<PenhoraHistorico> findByPJUD(Integer id){
        EntityManager em = getEntityManager();
        try {
            List<PenhoraHistorico> penhoraHistoricoList = (List<PenhoraHistorico>) em.createNativeQuery("select * from penhora_historico "
                    + "where processo_judicial_historico_fk = '"+id+"'", PenhoraHistorico.class).getResultList();
            return penhoraHistoricoList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
}
