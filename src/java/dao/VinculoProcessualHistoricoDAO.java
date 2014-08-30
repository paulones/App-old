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
import entidade.ProcessoJudicialHistorico;
import entidade.TipoProcesso;
import entidade.VinculoProcessualHistorico;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author paulones
 */
public class VinculoProcessualHistoricoDAO implements Serializable {

    public VinculoProcessualHistoricoDAO() {
    }
    private EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VinculoProcessualHistorico vinculoProcessualHistorico) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            ProcessoJudicialHistorico processoJudicialHistoricoFk = vinculoProcessualHistorico.getProcessoJudicialHistoricoFk();
            if (processoJudicialHistoricoFk != null) {
                processoJudicialHistoricoFk = em.getReference(processoJudicialHistoricoFk.getClass(), processoJudicialHistoricoFk.getId());
                vinculoProcessualHistorico.setProcessoJudicialHistoricoFk(processoJudicialHistoricoFk);
            }
            TipoProcesso tipoDeProcessoFk = vinculoProcessualHistorico.getTipoDeProcessoFk();
            if (tipoDeProcessoFk != null) {
                tipoDeProcessoFk = em.getReference(tipoDeProcessoFk.getClass(), tipoDeProcessoFk.getId());
                vinculoProcessualHistorico.setTipoDeProcessoFk(tipoDeProcessoFk);
            }
            em.persist(vinculoProcessualHistorico);
            if (processoJudicialHistoricoFk != null) {
                processoJudicialHistoricoFk.getVinculoProcessualHistoricoCollection().add(vinculoProcessualHistorico);
                processoJudicialHistoricoFk = em.merge(processoJudicialHistoricoFk);
            }
            if (tipoDeProcessoFk != null) {
                tipoDeProcessoFk.getVinculoProcessualHistoricoCollection().add(vinculoProcessualHistorico);
                tipoDeProcessoFk = em.merge(tipoDeProcessoFk);
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

    public void edit(VinculoProcessualHistorico vinculoProcessualHistorico) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            VinculoProcessualHistorico persistentVinculoProcessualHistorico = em.find(VinculoProcessualHistorico.class, vinculoProcessualHistorico.getId());
            ProcessoJudicialHistorico processoJudicialHistoricoFkOld = persistentVinculoProcessualHistorico.getProcessoJudicialHistoricoFk();
            ProcessoJudicialHistorico processoJudicialHistoricoFkNew = vinculoProcessualHistorico.getProcessoJudicialHistoricoFk();
            TipoProcesso tipoDeProcessoFkOld = persistentVinculoProcessualHistorico.getTipoDeProcessoFk();
            TipoProcesso tipoDeProcessoFkNew = vinculoProcessualHistorico.getTipoDeProcessoFk();
            if (processoJudicialHistoricoFkNew != null) {
                processoJudicialHistoricoFkNew = em.getReference(processoJudicialHistoricoFkNew.getClass(), processoJudicialHistoricoFkNew.getId());
                vinculoProcessualHistorico.setProcessoJudicialHistoricoFk(processoJudicialHistoricoFkNew);
            }
            if (tipoDeProcessoFkNew != null) {
                tipoDeProcessoFkNew = em.getReference(tipoDeProcessoFkNew.getClass(), tipoDeProcessoFkNew.getId());
                vinculoProcessualHistorico.setTipoDeProcessoFk(tipoDeProcessoFkNew);
            }
            vinculoProcessualHistorico = em.merge(vinculoProcessualHistorico);
            if (processoJudicialHistoricoFkOld != null && !processoJudicialHistoricoFkOld.equals(processoJudicialHistoricoFkNew)) {
                processoJudicialHistoricoFkOld.getVinculoProcessualHistoricoCollection().remove(vinculoProcessualHistorico);
                processoJudicialHistoricoFkOld = em.merge(processoJudicialHistoricoFkOld);
            }
            if (processoJudicialHistoricoFkNew != null && !processoJudicialHistoricoFkNew.equals(processoJudicialHistoricoFkOld)) {
                processoJudicialHistoricoFkNew.getVinculoProcessualHistoricoCollection().add(vinculoProcessualHistorico);
                processoJudicialHistoricoFkNew = em.merge(processoJudicialHistoricoFkNew);
            }
            if (tipoDeProcessoFkOld != null && !tipoDeProcessoFkOld.equals(tipoDeProcessoFkNew)) {
                tipoDeProcessoFkOld.getVinculoProcessualHistoricoCollection().remove(vinculoProcessualHistorico);
                tipoDeProcessoFkOld = em.merge(tipoDeProcessoFkOld);
            }
            if (tipoDeProcessoFkNew != null && !tipoDeProcessoFkNew.equals(tipoDeProcessoFkOld)) {
                tipoDeProcessoFkNew.getVinculoProcessualHistoricoCollection().add(vinculoProcessualHistorico);
                tipoDeProcessoFkNew = em.merge(tipoDeProcessoFkNew);
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
                Integer id = vinculoProcessualHistorico.getId();
                if (findVinculoProcessualHistorico(id) == null) {
                    throw new NonexistentEntityException("The vinculoProcessualHistorico with id " + id + " no longer exists.");
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
            VinculoProcessualHistorico vinculoProcessualHistorico;
            try {
                vinculoProcessualHistorico = em.getReference(VinculoProcessualHistorico.class, id);
                vinculoProcessualHistorico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vinculoProcessualHistorico with id " + id + " no longer exists.", enfe);
            }
            ProcessoJudicialHistorico processoJudicialHistoricoFk = vinculoProcessualHistorico.getProcessoJudicialHistoricoFk();
            if (processoJudicialHistoricoFk != null) {
                processoJudicialHistoricoFk.getVinculoProcessualHistoricoCollection().remove(vinculoProcessualHistorico);
                processoJudicialHistoricoFk = em.merge(processoJudicialHistoricoFk);
            }
            TipoProcesso tipoDeProcessoFk = vinculoProcessualHistorico.getTipoDeProcessoFk();
            if (tipoDeProcessoFk != null) {
                tipoDeProcessoFk.getVinculoProcessualHistoricoCollection().remove(vinculoProcessualHistorico);
                tipoDeProcessoFk = em.merge(tipoDeProcessoFk);
            }
            em.remove(vinculoProcessualHistorico);
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

    public List<VinculoProcessualHistorico> findVinculoProcessualHistoricoEntities() {
        return findVinculoProcessualHistoricoEntities(true, -1, -1);
    }

    public List<VinculoProcessualHistorico> findVinculoProcessualHistoricoEntities(int maxResults, int firstResult) {
        return findVinculoProcessualHistoricoEntities(false, maxResults, firstResult);
    }

    private List<VinculoProcessualHistorico> findVinculoProcessualHistoricoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VinculoProcessualHistorico.class));
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

    public VinculoProcessualHistorico findVinculoProcessualHistorico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VinculoProcessualHistorico.class, id);
        } finally {
            em.close();
        }
    }

    public int getVinculoProcessualHistoricoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VinculoProcessualHistorico> rt = cq.from(VinculoProcessualHistorico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
