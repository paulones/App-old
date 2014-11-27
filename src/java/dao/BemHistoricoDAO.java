/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.BemHistorico;
import entidade.ProcessoJudicialHistorico;
import entidade.TipoBem;
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
public class BemHistoricoDAO implements Serializable {

    public BemHistoricoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BemHistorico bemHistorico) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            TipoBem tipoBemFk = bemHistorico.getTipoBemFk();
            if (tipoBemFk != null) {
                tipoBemFk = em.getReference(tipoBemFk.getClass(), tipoBemFk.getId());
                bemHistorico.setTipoBemFk(tipoBemFk);
            }
            em.persist(bemHistorico);
            if (tipoBemFk != null) {
                tipoBemFk.getBemHistoricoCollection().add(bemHistorico);
                tipoBemFk = em.merge(tipoBemFk);
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

    public void edit(BemHistorico bemHistorico) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            BemHistorico persistentBemHistorico = em.find(BemHistorico.class, bemHistorico.getId());
            TipoBem tipoBemFkOld = persistentBemHistorico.getTipoBemFk();
            TipoBem tipoBemFkNew = bemHistorico.getTipoBemFk();
            if (tipoBemFkNew != null) {
                tipoBemFkNew = em.getReference(tipoBemFkNew.getClass(), tipoBemFkNew.getId());
                bemHistorico.setTipoBemFk(tipoBemFkNew);
            }
            bemHistorico = em.merge(bemHistorico);
            if (tipoBemFkOld != null && !tipoBemFkOld.equals(tipoBemFkNew)) {
                tipoBemFkOld.getBemHistoricoCollection().remove(bemHistorico);
                tipoBemFkOld = em.merge(tipoBemFkOld);
            }
            if (tipoBemFkNew != null && !tipoBemFkNew.equals(tipoBemFkOld)) {
                tipoBemFkNew.getBemHistoricoCollection().add(bemHistorico);
                tipoBemFkNew = em.merge(tipoBemFkNew);
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
                Integer id = bemHistorico.getId();
                if (findBemHistorico(id) == null) {
                    throw new NonexistentEntityException("The bemHistorico with id " + id + " no longer exists.");
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
            BemHistorico bemHistorico;
            try {
                bemHistorico = em.getReference(BemHistorico.class, id);
                bemHistorico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bemHistorico with id " + id + " no longer exists.", enfe);
            }
            TipoBem tipoBemFk = bemHistorico.getTipoBemFk();
            if (tipoBemFk != null) {
                tipoBemFk.getBemHistoricoCollection().remove(bemHistorico);
                tipoBemFk = em.merge(tipoBemFk);
            }
            em.remove(bemHistorico);
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

    public List<BemHistorico> findBemHistoricoEntities() {
        return findBemHistoricoEntities(true, -1, -1);
    }

    public List<BemHistorico> findBemHistoricoEntities(int maxResults, int firstResult) {
        return findBemHistoricoEntities(false, maxResults, firstResult);
    }

    private List<BemHistorico> findBemHistoricoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BemHistorico.class));
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

    public BemHistorico findBemHistorico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BemHistorico.class, id);
        } finally {
            em.close();
        }
    }

    public int getBemHistoricoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BemHistorico> rt = cq.from(BemHistorico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<BemHistorico> findAllByPF(Integer id){
        EntityManager em = getEntityManager();
        try {
            List<BemHistorico> enderecoHistoricoList = (List<BemHistorico>) em.createNativeQuery("select bh.* from bem_historico bh "
                        + "join pessoa_fisica_historico pfh on pfh.id = bh.id_fk where pfh.pessoa_fisica_fk = '"+id+"' order by data_de_modificacao desc", BemHistorico.class).getResultList();
            return enderecoHistoricoList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public List<BemHistorico> findAllByPJ(Integer id){
        EntityManager em = getEntityManager();
        try {
            List<BemHistorico> enderecoHistoricoList = (List<BemHistorico>) em.createNativeQuery("select bh.* from bem_historico bh "
                        + "join pessoa_juridica_historico pjh on pjh.id = bh.id_fk where pjh.pessoa_juridica_fk = '"+id+"' order by data_de_modificacao desc", BemHistorico.class).getResultList();
            return enderecoHistoricoList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
