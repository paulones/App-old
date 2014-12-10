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
import entidade.ProcessoJudicial;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
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
            ProcessoJudicial processoJudicialFk = aquisicaoBem.getProcessoJudicialFk();
            if (processoJudicialFk != null) {
                processoJudicialFk = em.getReference(processoJudicialFk.getClass(), processoJudicialFk.getId());
                aquisicaoBem.setProcessoJudicialFk(processoJudicialFk);
            }
            em.persist(aquisicaoBem);
            if (bemFk != null) {
                bemFk.getAquisicaoBemCollection().add(aquisicaoBem);
                bemFk = em.merge(bemFk);
            }
            if (processoJudicialFk != null) {
                processoJudicialFk.getAquisicaoBemCollection().add(aquisicaoBem);
                processoJudicialFk = em.merge(processoJudicialFk);
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
            ProcessoJudicial processoJudicialFkOld = persistentAquisicaoBem.getProcessoJudicialFk();
            ProcessoJudicial processoJudicialFkNew = aquisicaoBem.getProcessoJudicialFk();
            if (bemFkNew != null) {
                bemFkNew = em.getReference(bemFkNew.getClass(), bemFkNew.getId());
                aquisicaoBem.setBemFk(bemFkNew);
            }
            if (processoJudicialFkNew != null) {
                processoJudicialFkNew = em.getReference(processoJudicialFkNew.getClass(), processoJudicialFkNew.getId());
                aquisicaoBem.setProcessoJudicialFk(processoJudicialFkNew);
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
            if (processoJudicialFkOld != null && !processoJudicialFkOld.equals(processoJudicialFkNew)) {
                processoJudicialFkOld.getAquisicaoBemCollection().remove(aquisicaoBem);
                processoJudicialFkOld = em.merge(processoJudicialFkOld);
            }
            if (processoJudicialFkNew != null && !processoJudicialFkNew.equals(processoJudicialFkOld)) {
                processoJudicialFkNew.getAquisicaoBemCollection().add(aquisicaoBem);
                processoJudicialFkNew = em.merge(processoJudicialFkNew);
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
            ProcessoJudicial processoJudicialFk = aquisicaoBem.getProcessoJudicialFk();
            if (processoJudicialFk != null) {
                processoJudicialFk.getAquisicaoBemCollection().remove(aquisicaoBem);
                processoJudicialFk = em.merge(processoJudicialFk);
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
    
    public List<AquisicaoBem> findByPJUD(Integer id){
        EntityManager em = getEntityManager();
        try {
            List<AquisicaoBem> aquisicaoBem = (List<AquisicaoBem>) em.createNativeQuery("select * from aquisicao_bem "
                    + "where processo_judicial_fk = '"+id+"'", AquisicaoBem.class).getResultList();
            return aquisicaoBem;
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
            em.createNativeQuery("delete from aquisicao_bem "
                    + "where processo_judicial_fk = '" + idPjud + "'").executeUpdate();
            em.getTransaction().commit();
        } catch (NoResultException e) {
        } finally {
            em.close();
        }
    }
}
