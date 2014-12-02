/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.CitacaoHistorico;
import entidade.ProcessoJudicialHistorico;
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
public class CitacaoHistoricoDAO implements Serializable {

    public CitacaoHistoricoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CitacaoHistorico citacaoHistorico) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            ProcessoJudicialHistorico processoJudicialHistoricoFk = citacaoHistorico.getProcessoJudicialHistoricoFk();
            if (processoJudicialHistoricoFk != null) {
                processoJudicialHistoricoFk = em.getReference(processoJudicialHistoricoFk.getClass(), processoJudicialHistoricoFk.getId());
                citacaoHistorico.setProcessoJudicialHistoricoFk(processoJudicialHistoricoFk);
            }
            em.persist(citacaoHistorico);
            if (processoJudicialHistoricoFk != null) {
                processoJudicialHistoricoFk.getCitacaoHistoricoCollection().add(citacaoHistorico);
                processoJudicialHistoricoFk = em.merge(processoJudicialHistoricoFk);
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

    public void edit(CitacaoHistorico citacaoHistorico) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            CitacaoHistorico persistentCitacaoHistorico = em.find(CitacaoHistorico.class, citacaoHistorico.getId());
            ProcessoJudicialHistorico processoJudicialHistoricoFkOld = persistentCitacaoHistorico.getProcessoJudicialHistoricoFk();
            ProcessoJudicialHistorico processoJudicialHistoricoFkNew = citacaoHistorico.getProcessoJudicialHistoricoFk();
            if (processoJudicialHistoricoFkNew != null) {
                processoJudicialHistoricoFkNew = em.getReference(processoJudicialHistoricoFkNew.getClass(), processoJudicialHistoricoFkNew.getId());
                citacaoHistorico.setProcessoJudicialHistoricoFk(processoJudicialHistoricoFkNew);
            }
            citacaoHistorico = em.merge(citacaoHistorico);
            if (processoJudicialHistoricoFkOld != null && !processoJudicialHistoricoFkOld.equals(processoJudicialHistoricoFkNew)) {
                processoJudicialHistoricoFkOld.getCitacaoHistoricoCollection().remove(citacaoHistorico);
                processoJudicialHistoricoFkOld = em.merge(processoJudicialHistoricoFkOld);
            }
            if (processoJudicialHistoricoFkNew != null && !processoJudicialHistoricoFkNew.equals(processoJudicialHistoricoFkOld)) {
                processoJudicialHistoricoFkNew.getCitacaoHistoricoCollection().add(citacaoHistorico);
                processoJudicialHistoricoFkNew = em.merge(processoJudicialHistoricoFkNew);
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
                Integer id = citacaoHistorico.getId();
                if (findCitacaoHistorico(id) == null) {
                    throw new NonexistentEntityException("The citacaoHistorico with id " + id + " no longer exists.");
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
            CitacaoHistorico citacaoHistorico;
            try {
                citacaoHistorico = em.getReference(CitacaoHistorico.class, id);
                citacaoHistorico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The citacaoHistorico with id " + id + " no longer exists.", enfe);
            }
            ProcessoJudicialHistorico processoJudicialHistoricoFk = citacaoHistorico.getProcessoJudicialHistoricoFk();
            if (processoJudicialHistoricoFk != null) {
                processoJudicialHistoricoFk.getCitacaoHistoricoCollection().remove(citacaoHistorico);
                processoJudicialHistoricoFk = em.merge(processoJudicialHistoricoFk);
            }
            em.remove(citacaoHistorico);
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

    public List<CitacaoHistorico> findCitacaoHistoricoEntities() {
        return findCitacaoHistoricoEntities(true, -1, -1);
    }

    public List<CitacaoHistorico> findCitacaoHistoricoEntities(int maxResults, int firstResult) {
        return findCitacaoHistoricoEntities(false, maxResults, firstResult);
    }

    private List<CitacaoHistorico> findCitacaoHistoricoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CitacaoHistorico.class));
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

    public CitacaoHistorico findCitacaoHistorico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CitacaoHistorico.class, id);
        } finally {
            em.close();
        }
    }

    public int getCitacaoHistoricoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CitacaoHistorico> rt = cq.from(CitacaoHistorico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<CitacaoHistorico> findByPJUD(Integer id){
        EntityManager em = getEntityManager();
        try {
            List<CitacaoHistorico> processoJudicialList = (List<CitacaoHistorico>) em.createNativeQuery("select * from citacao_historico "
                    + "where processo_judicial_historico_fk = '"+id+"'", CitacaoHistorico.class).getResultList();
            return processoJudicialList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
