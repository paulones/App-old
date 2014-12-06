/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.AquisicaoBemHistorico;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidade.Bem;
import entidade.TipoAquisicaoBem;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author paulones
 */
public class AquisicaoBemHistoricoDAO implements Serializable {

    public AquisicaoBemHistoricoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AquisicaoBemHistorico aquisicaoBemHistorico) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            Bem bemFk = aquisicaoBemHistorico.getBemFk();
            if (bemFk != null) {
                bemFk = em.getReference(bemFk.getClass(), bemFk.getId());
                aquisicaoBemHistorico.setBemFk(bemFk);
            }
            TipoAquisicaoBem tipoAquisicaoBemFk = aquisicaoBemHistorico.getTipoAquisicaoBemFk();
            if (tipoAquisicaoBemFk != null) {
                tipoAquisicaoBemFk = em.getReference(tipoAquisicaoBemFk.getClass(), tipoAquisicaoBemFk.getId());
                aquisicaoBemHistorico.setTipoAquisicaoBemFk(tipoAquisicaoBemFk);
            }
            em.persist(aquisicaoBemHistorico);
            if (bemFk != null) {
                bemFk.getAquisicaoBemHistoricoCollection().add(aquisicaoBemHistorico);
                bemFk = em.merge(bemFk);
            }
            if (tipoAquisicaoBemFk != null) {
                tipoAquisicaoBemFk.getAquisicaoBemHistoricoCollection().add(aquisicaoBemHistorico);
                tipoAquisicaoBemFk = em.merge(tipoAquisicaoBemFk);
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

    public void edit(AquisicaoBemHistorico aquisicaoBemHistorico) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            AquisicaoBemHistorico persistentAquisicaoBemHistorico = em.find(AquisicaoBemHistorico.class, aquisicaoBemHistorico.getId());
            Bem bemFkOld = persistentAquisicaoBemHistorico.getBemFk();
            Bem bemFkNew = aquisicaoBemHistorico.getBemFk();
            TipoAquisicaoBem tipoAquisicaoBemFkOld = persistentAquisicaoBemHistorico.getTipoAquisicaoBemFk();
            TipoAquisicaoBem tipoAquisicaoBemFkNew = aquisicaoBemHistorico.getTipoAquisicaoBemFk();
            if (bemFkNew != null) {
                bemFkNew = em.getReference(bemFkNew.getClass(), bemFkNew.getId());
                aquisicaoBemHistorico.setBemFk(bemFkNew);
            }
            if (tipoAquisicaoBemFkNew != null) {
                tipoAquisicaoBemFkNew = em.getReference(tipoAquisicaoBemFkNew.getClass(), tipoAquisicaoBemFkNew.getId());
                aquisicaoBemHistorico.setTipoAquisicaoBemFk(tipoAquisicaoBemFkNew);
            }
            aquisicaoBemHistorico = em.merge(aquisicaoBemHistorico);
            if (bemFkOld != null && !bemFkOld.equals(bemFkNew)) {
                bemFkOld.getAquisicaoBemHistoricoCollection().remove(aquisicaoBemHistorico);
                bemFkOld = em.merge(bemFkOld);
            }
            if (bemFkNew != null && !bemFkNew.equals(bemFkOld)) {
                bemFkNew.getAquisicaoBemHistoricoCollection().add(aquisicaoBemHistorico);
                bemFkNew = em.merge(bemFkNew);
            }
            if (tipoAquisicaoBemFkOld != null && !tipoAquisicaoBemFkOld.equals(tipoAquisicaoBemFkNew)) {
                tipoAquisicaoBemFkOld.getAquisicaoBemHistoricoCollection().remove(aquisicaoBemHistorico);
                tipoAquisicaoBemFkOld = em.merge(tipoAquisicaoBemFkOld);
            }
            if (tipoAquisicaoBemFkNew != null && !tipoAquisicaoBemFkNew.equals(tipoAquisicaoBemFkOld)) {
                tipoAquisicaoBemFkNew.getAquisicaoBemHistoricoCollection().add(aquisicaoBemHistorico);
                tipoAquisicaoBemFkNew = em.merge(tipoAquisicaoBemFkNew);
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
                Integer id = aquisicaoBemHistorico.getId();
                if (findAquisicaoBemHistorico(id) == null) {
                    throw new NonexistentEntityException("The aquisicaoBemHistorico with id " + id + " no longer exists.");
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
            AquisicaoBemHistorico aquisicaoBemHistorico;
            try {
                aquisicaoBemHistorico = em.getReference(AquisicaoBemHistorico.class, id);
                aquisicaoBemHistorico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aquisicaoBemHistorico with id " + id + " no longer exists.", enfe);
            }
            Bem bemFk = aquisicaoBemHistorico.getBemFk();
            if (bemFk != null) {
                bemFk.getAquisicaoBemHistoricoCollection().remove(aquisicaoBemHistorico);
                bemFk = em.merge(bemFk);
            }
            TipoAquisicaoBem tipoAquisicaoBemFk = aquisicaoBemHistorico.getTipoAquisicaoBemFk();
            if (tipoAquisicaoBemFk != null) {
                tipoAquisicaoBemFk.getAquisicaoBemHistoricoCollection().remove(aquisicaoBemHistorico);
                tipoAquisicaoBemFk = em.merge(tipoAquisicaoBemFk);
            }
            em.remove(aquisicaoBemHistorico);
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

    public List<AquisicaoBemHistorico> findAquisicaoBemHistoricoEntities() {
        return findAquisicaoBemHistoricoEntities(true, -1, -1);
    }

    public List<AquisicaoBemHistorico> findAquisicaoBemHistoricoEntities(int maxResults, int firstResult) {
        return findAquisicaoBemHistoricoEntities(false, maxResults, firstResult);
    }

    private List<AquisicaoBemHistorico> findAquisicaoBemHistoricoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AquisicaoBemHistorico.class));
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

    public AquisicaoBemHistorico findAquisicaoBemHistorico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AquisicaoBemHistorico.class, id);
        } finally {
            em.close();
        }
    }

    public int getAquisicaoBemHistoricoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AquisicaoBemHistorico> rt = cq.from(AquisicaoBemHistorico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
