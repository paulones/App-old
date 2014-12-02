/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.Instituicao;
import entidade.Log;
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
public class LogDAO implements Serializable {

    public LogDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Log log) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Instituicao instituicaoFk = log.getInstituicaoFk();
            if (instituicaoFk != null) {
                instituicaoFk = em.getReference(instituicaoFk.getClass(), instituicaoFk.getId());
                log.setInstituicaoFk(instituicaoFk);
            }
            em.persist(log);
            if (instituicaoFk != null) {
                instituicaoFk.getLogCollection().add(log);
                instituicaoFk = em.merge(instituicaoFk);
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

    public void edit(Log log) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Log persistentLog = em.find(Log.class, log.getId());
            Instituicao instituicaoFkOld = persistentLog.getInstituicaoFk();
            Instituicao instituicaoFkNew = log.getInstituicaoFk();
            if (instituicaoFkNew != null) {
                instituicaoFkNew = em.getReference(instituicaoFkNew.getClass(), instituicaoFkNew.getId());
                log.setInstituicaoFk(instituicaoFkNew);
            }
            log = em.merge(log);
            if (instituicaoFkOld != null && !instituicaoFkOld.equals(instituicaoFkNew)) {
                instituicaoFkOld.getLogCollection().remove(log);
                instituicaoFkOld = em.merge(instituicaoFkOld);
            }
            if (instituicaoFkNew != null && !instituicaoFkNew.equals(instituicaoFkOld)) {
                instituicaoFkNew.getLogCollection().add(log);
                instituicaoFkNew = em.merge(instituicaoFkNew);
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
                Integer id = log.getId();
                if (findLog(id) == null) {
                    throw new NonexistentEntityException("The log with id " + id + " no longer exists.");
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
            em = getEntityManager();
            em.getTransaction().begin();
            Log log;
            try {
                log = em.getReference(Log.class, id);
                log.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The log with id " + id + " no longer exists.", enfe);
            }
            Instituicao instituicaoFk = log.getInstituicaoFk();
            if (instituicaoFk != null) {
                instituicaoFk.getLogCollection().remove(log);
                instituicaoFk = em.merge(instituicaoFk);
            }
            em.remove(log);
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

    public List<Log> findLogEntities() {
        return findLogEntities(true, -1, -1);
    }

    public List<Log> findLogEntities(int maxResults, int firstResult) {
        return findLogEntities(false, maxResults, firstResult);
    }

    private List<Log> findLogEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Log.class));
            Root<Log> from = cq.from(Log.class);
            cq.orderBy(em.getCriteriaBuilder().desc(from.get("dataDeCriacao")));
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
    
    public List<Log> findLogByInstituicao(int maxResults, Instituicao instituicao){
        EntityManager em = getEntityManager();
        try {
            List<Log> pessoaFisicaList = (List<Log>) 
                    em.createNativeQuery("select * from log "
                        +"where instituicao_fk = "+instituicao.getId()+" order by data_de_criacao desc "
                        +"limit "+maxResults, Log.class).getResultList();
            return pessoaFisicaList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public Log findLog(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Log.class, id);
        } finally {
            em.close();
        }
    }

    public int getLogCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Log> rt = cq.from(Log.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
