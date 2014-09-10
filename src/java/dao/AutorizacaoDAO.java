/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.Autorizacao;
import entidade.Instituicao;
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
 * @author Pedro
 */
public class AutorizacaoDAO implements Serializable {

    public AutorizacaoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Autorizacao autorizacao) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Instituicao instituicaoFk = autorizacao.getInstituicaoFk();
            if (instituicaoFk != null) {
                instituicaoFk = em.getReference(instituicaoFk.getClass(), instituicaoFk.getId());
                autorizacao.setInstituicaoFk(instituicaoFk);
            }
            em.persist(autorizacao);
            if (instituicaoFk != null) {
                instituicaoFk.getAutorizacaoCollection().add(autorizacao);
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

    public void edit(Autorizacao autorizacao) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Autorizacao persistentAutorizacao = em.find(Autorizacao.class, autorizacao.getId());
            Instituicao instituicaoFkOld = persistentAutorizacao.getInstituicaoFk();
            Instituicao instituicaoFkNew = autorizacao.getInstituicaoFk();
            if (instituicaoFkNew != null) {
                instituicaoFkNew = em.getReference(instituicaoFkNew.getClass(), instituicaoFkNew.getId());
                autorizacao.setInstituicaoFk(instituicaoFkNew);
            }
            autorizacao = em.merge(autorizacao);
            if (instituicaoFkOld != null && !instituicaoFkOld.equals(instituicaoFkNew)) {
                instituicaoFkOld.getAutorizacaoCollection().remove(autorizacao);
                instituicaoFkOld = em.merge(instituicaoFkOld);
            }
            if (instituicaoFkNew != null && !instituicaoFkNew.equals(instituicaoFkOld)) {
                instituicaoFkNew.getAutorizacaoCollection().add(autorizacao);
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
                Integer id = autorizacao.getId();
                if (findAutorizacao(id) == null) {
                    throw new NonexistentEntityException("The autorizacao with id " + id + " no longer exists.");
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
            Autorizacao autorizacao;
            try {
                autorizacao = em.getReference(Autorizacao.class, id);
                autorizacao.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The autorizacao with id " + id + " no longer exists.", enfe);
            }
            Instituicao instituicaoFk = autorizacao.getInstituicaoFk();
            if (instituicaoFk != null) {
                instituicaoFk.getAutorizacaoCollection().remove(autorizacao);
                instituicaoFk = em.merge(instituicaoFk);
            }
            em.remove(autorizacao);
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

    public List<Autorizacao> findAutorizacaoEntities() {
        return findAutorizacaoEntities(true, -1, -1);
    }

    public List<Autorizacao> findAutorizacaoEntities(int maxResults, int firstResult) {
        return findAutorizacaoEntities(false, maxResults, firstResult);
    }

    private List<Autorizacao> findAutorizacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Autorizacao.class));
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

    public Autorizacao findAutorizacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Autorizacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getAutorizacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Autorizacao> rt = cq.from(Autorizacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Autorizacao findAutorizacaoByCPF(String cpf) {
        EntityManager em = getEntityManager();
        try {
            Autorizacao autorizacao = (Autorizacao) em.createNativeQuery("select * from autorizacao "
                    + "where cpf = '" + cpf + "'", Autorizacao.class).getSingleResult();
            return autorizacao;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
}
