/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.AquisicaoBem;
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
public class AquisicaoBemDAO implements Serializable {

    public AquisicaoBemDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AquisicaoBem aquisicaoBem) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            Bem bemFk = aquisicaoBem.getBemFk();
            if (bemFk != null) {
                bemFk = em.getReference(bemFk.getClass(), bemFk.getId());
                aquisicaoBem.setBemFk(bemFk);
            }
            TipoAquisicaoBem tipoAquisicaoBemFk = aquisicaoBem.getTipoAquisicaoBemFk();
            if (tipoAquisicaoBemFk != null) {
                tipoAquisicaoBemFk = em.getReference(tipoAquisicaoBemFk.getClass(), tipoAquisicaoBemFk.getId());
                aquisicaoBem.setTipoAquisicaoBemFk(tipoAquisicaoBemFk);
            }
            em.persist(aquisicaoBem);
            if (bemFk != null) {
                bemFk.getAquisicaoBemCollection().add(aquisicaoBem);
                bemFk = em.merge(bemFk);
            }
            if (tipoAquisicaoBemFk != null) {
                tipoAquisicaoBemFk.getAquisicaoBemCollection().add(aquisicaoBem);
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

    public void edit(AquisicaoBem aquisicaoBem) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            AquisicaoBem persistentAquisicaoBem = em.find(AquisicaoBem.class, aquisicaoBem.getId());
            Bem bemFkOld = persistentAquisicaoBem.getBemFk();
            Bem bemFkNew = aquisicaoBem.getBemFk();
            TipoAquisicaoBem tipoAquisicaoBemFkOld = persistentAquisicaoBem.getTipoAquisicaoBemFk();
            TipoAquisicaoBem tipoAquisicaoBemFkNew = aquisicaoBem.getTipoAquisicaoBemFk();
            if (bemFkNew != null) {
                bemFkNew = em.getReference(bemFkNew.getClass(), bemFkNew.getId());
                aquisicaoBem.setBemFk(bemFkNew);
            }
            if (tipoAquisicaoBemFkNew != null) {
                tipoAquisicaoBemFkNew = em.getReference(tipoAquisicaoBemFkNew.getClass(), tipoAquisicaoBemFkNew.getId());
                aquisicaoBem.setTipoAquisicaoBemFk(tipoAquisicaoBemFkNew);
            }
            aquisicaoBem = em.merge(aquisicaoBem);
            if (bemFkOld != null && !bemFkOld.equals(bemFkNew)) {
                bemFkOld.getAquisicaoBemCollection().remove(aquisicaoBem);
                bemFkOld = em.merge(bemFkOld);
            }
            if (bemFkNew != null && !bemFkNew.equals(bemFkOld)) {
                bemFkNew.getAquisicaoBemCollection().add(aquisicaoBem);
                bemFkNew = em.merge(bemFkNew);
            }
            if (tipoAquisicaoBemFkOld != null && !tipoAquisicaoBemFkOld.equals(tipoAquisicaoBemFkNew)) {
                tipoAquisicaoBemFkOld.getAquisicaoBemCollection().remove(aquisicaoBem);
                tipoAquisicaoBemFkOld = em.merge(tipoAquisicaoBemFkOld);
            }
            if (tipoAquisicaoBemFkNew != null && !tipoAquisicaoBemFkNew.equals(tipoAquisicaoBemFkOld)) {
                tipoAquisicaoBemFkNew.getAquisicaoBemCollection().add(aquisicaoBem);
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
                Integer id = aquisicaoBem.getId();
                if (findAquisicaoBem(id) == null) {
                    throw new NonexistentEntityException("The aquisicaoBem with id " + id + " no longer exists.");
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
            AquisicaoBem aquisicaoBem;
            try {
                aquisicaoBem = em.getReference(AquisicaoBem.class, id);
                aquisicaoBem.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aquisicaoBem with id " + id + " no longer exists.", enfe);
            }
            Bem bemFk = aquisicaoBem.getBemFk();
            if (bemFk != null) {
                bemFk.getAquisicaoBemCollection().remove(aquisicaoBem);
                bemFk = em.merge(bemFk);
            }
            TipoAquisicaoBem tipoAquisicaoBemFk = aquisicaoBem.getTipoAquisicaoBemFk();
            if (tipoAquisicaoBemFk != null) {
                tipoAquisicaoBemFk.getAquisicaoBemCollection().remove(aquisicaoBem);
                tipoAquisicaoBemFk = em.merge(tipoAquisicaoBemFk);
            }
            em.remove(aquisicaoBem);
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

    public List<AquisicaoBem> findAquisicaoBemEntities() {
        return findAquisicaoBemEntities(true, -1, -1);
    }

    public List<AquisicaoBem> findAquisicaoBemEntities(int maxResults, int firstResult) {
        return findAquisicaoBemEntities(false, maxResults, firstResult);
    }

    private List<AquisicaoBem> findAquisicaoBemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AquisicaoBem.class));
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

    public AquisicaoBem findAquisicaoBem(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AquisicaoBem.class, id);
        } finally {
            em.close();
        }
    }

    public int getAquisicaoBemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AquisicaoBem> rt = cq.from(AquisicaoBem.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
