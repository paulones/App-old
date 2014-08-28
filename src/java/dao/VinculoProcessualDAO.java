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
import entidade.ProcessoJudicial;
import entidade.TipoProcesso;
import entidade.VinculoProcessual;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.transaction.UserTransaction;

/**
 *
 * @author paulones
 */
public class VinculoProcessualDAO implements Serializable {

    public VinculoProcessualDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VinculoProcessual vinculoProcessual) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            ProcessoJudicial processoJudicialFk = vinculoProcessual.getProcessoJudicialFk();
            if (processoJudicialFk != null) {
                processoJudicialFk = em.getReference(processoJudicialFk.getClass(), processoJudicialFk.getId());
                vinculoProcessual.setProcessoJudicialFk(processoJudicialFk);
            }
            TipoProcesso tipoDeProcessoFk = vinculoProcessual.getTipoDeProcessoFk();
            if (tipoDeProcessoFk != null) {
                tipoDeProcessoFk = em.getReference(tipoDeProcessoFk.getClass(), tipoDeProcessoFk.getId());
                vinculoProcessual.setTipoDeProcessoFk(tipoDeProcessoFk);
            }
            em.persist(vinculoProcessual);
            if (processoJudicialFk != null) {
                processoJudicialFk.getVinculoProcessualCollection().add(vinculoProcessual);
                processoJudicialFk = em.merge(processoJudicialFk);
            }
            if (tipoDeProcessoFk != null) {
                tipoDeProcessoFk.getVinculoProcessualCollection().add(vinculoProcessual);
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

    public void edit(VinculoProcessual vinculoProcessual) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            VinculoProcessual persistentVinculoProcessual = em.find(VinculoProcessual.class, vinculoProcessual.getId());
            ProcessoJudicial processoJudicialFkOld = persistentVinculoProcessual.getProcessoJudicialFk();
            ProcessoJudicial processoJudicialFkNew = vinculoProcessual.getProcessoJudicialFk();
            TipoProcesso tipoDeProcessoFkOld = persistentVinculoProcessual.getTipoDeProcessoFk();
            TipoProcesso tipoDeProcessoFkNew = vinculoProcessual.getTipoDeProcessoFk();
            if (processoJudicialFkNew != null) {
                processoJudicialFkNew = em.getReference(processoJudicialFkNew.getClass(), processoJudicialFkNew.getId());
                vinculoProcessual.setProcessoJudicialFk(processoJudicialFkNew);
            }
            if (tipoDeProcessoFkNew != null) {
                tipoDeProcessoFkNew = em.getReference(tipoDeProcessoFkNew.getClass(), tipoDeProcessoFkNew.getId());
                vinculoProcessual.setTipoDeProcessoFk(tipoDeProcessoFkNew);
            }
            vinculoProcessual = em.merge(vinculoProcessual);
            if (processoJudicialFkOld != null && !processoJudicialFkOld.equals(processoJudicialFkNew)) {
                processoJudicialFkOld.getVinculoProcessualCollection().remove(vinculoProcessual);
                processoJudicialFkOld = em.merge(processoJudicialFkOld);
            }
            if (processoJudicialFkNew != null && !processoJudicialFkNew.equals(processoJudicialFkOld)) {
                processoJudicialFkNew.getVinculoProcessualCollection().add(vinculoProcessual);
                processoJudicialFkNew = em.merge(processoJudicialFkNew);
            }
            if (tipoDeProcessoFkOld != null && !tipoDeProcessoFkOld.equals(tipoDeProcessoFkNew)) {
                tipoDeProcessoFkOld.getVinculoProcessualCollection().remove(vinculoProcessual);
                tipoDeProcessoFkOld = em.merge(tipoDeProcessoFkOld);
            }
            if (tipoDeProcessoFkNew != null && !tipoDeProcessoFkNew.equals(tipoDeProcessoFkOld)) {
                tipoDeProcessoFkNew.getVinculoProcessualCollection().add(vinculoProcessual);
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
                Integer id = vinculoProcessual.getId();
                if (findVinculoProcessual(id) == null) {
                    throw new NonexistentEntityException("The vinculoProcessual with id " + id + " no longer exists.");
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
            VinculoProcessual vinculoProcessual;
            try {
                vinculoProcessual = em.getReference(VinculoProcessual.class, id);
                vinculoProcessual.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vinculoProcessual with id " + id + " no longer exists.", enfe);
            }
            ProcessoJudicial processoJudicialFk = vinculoProcessual.getProcessoJudicialFk();
            if (processoJudicialFk != null) {
                processoJudicialFk.getVinculoProcessualCollection().remove(vinculoProcessual);
                processoJudicialFk = em.merge(processoJudicialFk);
            }
            TipoProcesso tipoDeProcessoFk = vinculoProcessual.getTipoDeProcessoFk();
            if (tipoDeProcessoFk != null) {
                tipoDeProcessoFk.getVinculoProcessualCollection().remove(vinculoProcessual);
                tipoDeProcessoFk = em.merge(tipoDeProcessoFk);
            }
            em.remove(vinculoProcessual);
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

    public List<VinculoProcessual> findVinculoProcessualEntities() {
        return findVinculoProcessualEntities(true, -1, -1);
    }

    public List<VinculoProcessual> findVinculoProcessualEntities(int maxResults, int firstResult) {
        return findVinculoProcessualEntities(false, maxResults, firstResult);
    }

    private List<VinculoProcessual> findVinculoProcessualEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VinculoProcessual.class));
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

    public VinculoProcessual findVinculoProcessual(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VinculoProcessual.class, id);
        } finally {
            em.close();
        }
    }

    public int getVinculoProcessualCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VinculoProcessual> rt = cq.from(VinculoProcessual.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public void destroyByPJUD(Integer idPjud){
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.createNativeQuery("delete from vinculo_processual "
                    + "where processo_judicial_fk = '" + idPjud + "'").executeUpdate();
            em.getTransaction().commit();
        } catch (NoResultException e) {
        } finally {
            em.close();
        }
    }
}
