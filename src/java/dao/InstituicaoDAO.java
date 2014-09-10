/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.Autorizacao;
import entidade.Instituicao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
public class InstituicaoDAO implements Serializable {

    public InstituicaoDAO() {
    }
    private EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Instituicao instituicao) throws RollbackFailureException, Exception {
        if (instituicao.getAutorizacaoCollection() == null) {
            instituicao.setAutorizacaoCollection(new ArrayList<Autorizacao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Autorizacao> attachedAutorizacaoCollection = new ArrayList<Autorizacao>();
            for (Autorizacao autorizacaoCollectionAutorizacaoToAttach : instituicao.getAutorizacaoCollection()) {
                autorizacaoCollectionAutorizacaoToAttach = em.getReference(autorizacaoCollectionAutorizacaoToAttach.getClass(), autorizacaoCollectionAutorizacaoToAttach.getId());
                attachedAutorizacaoCollection.add(autorizacaoCollectionAutorizacaoToAttach);
            }
            instituicao.setAutorizacaoCollection(attachedAutorizacaoCollection);
            em.persist(instituicao);
            for (Autorizacao autorizacaoCollectionAutorizacao : instituicao.getAutorizacaoCollection()) {
                Instituicao oldInstituicaoFkOfAutorizacaoCollectionAutorizacao = autorizacaoCollectionAutorizacao.getInstituicaoFk();
                autorizacaoCollectionAutorizacao.setInstituicaoFk(instituicao);
                autorizacaoCollectionAutorizacao = em.merge(autorizacaoCollectionAutorizacao);
                if (oldInstituicaoFkOfAutorizacaoCollectionAutorizacao != null) {
                    oldInstituicaoFkOfAutorizacaoCollectionAutorizacao.getAutorizacaoCollection().remove(autorizacaoCollectionAutorizacao);
                    oldInstituicaoFkOfAutorizacaoCollectionAutorizacao = em.merge(oldInstituicaoFkOfAutorizacaoCollectionAutorizacao);
                }
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

    public void edit(Instituicao instituicao) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Instituicao persistentInstituicao = em.find(Instituicao.class, instituicao.getId());
            Collection<Autorizacao> autorizacaoCollectionOld = persistentInstituicao.getAutorizacaoCollection();
            Collection<Autorizacao> autorizacaoCollectionNew = instituicao.getAutorizacaoCollection();
            List<String> illegalOrphanMessages = null;
            for (Autorizacao autorizacaoCollectionOldAutorizacao : autorizacaoCollectionOld) {
                if (!autorizacaoCollectionNew.contains(autorizacaoCollectionOldAutorizacao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Autorizacao " + autorizacaoCollectionOldAutorizacao + " since its instituicaoFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Autorizacao> attachedAutorizacaoCollectionNew = new ArrayList<Autorizacao>();
            for (Autorizacao autorizacaoCollectionNewAutorizacaoToAttach : autorizacaoCollectionNew) {
                autorizacaoCollectionNewAutorizacaoToAttach = em.getReference(autorizacaoCollectionNewAutorizacaoToAttach.getClass(), autorizacaoCollectionNewAutorizacaoToAttach.getId());
                attachedAutorizacaoCollectionNew.add(autorizacaoCollectionNewAutorizacaoToAttach);
            }
            autorizacaoCollectionNew = attachedAutorizacaoCollectionNew;
            instituicao.setAutorizacaoCollection(autorizacaoCollectionNew);
            instituicao = em.merge(instituicao);
            for (Autorizacao autorizacaoCollectionNewAutorizacao : autorizacaoCollectionNew) {
                if (!autorizacaoCollectionOld.contains(autorizacaoCollectionNewAutorizacao)) {
                    Instituicao oldInstituicaoFkOfAutorizacaoCollectionNewAutorizacao = autorizacaoCollectionNewAutorizacao.getInstituicaoFk();
                    autorizacaoCollectionNewAutorizacao.setInstituicaoFk(instituicao);
                    autorizacaoCollectionNewAutorizacao = em.merge(autorizacaoCollectionNewAutorizacao);
                    if (oldInstituicaoFkOfAutorizacaoCollectionNewAutorizacao != null && !oldInstituicaoFkOfAutorizacaoCollectionNewAutorizacao.equals(instituicao)) {
                        oldInstituicaoFkOfAutorizacaoCollectionNewAutorizacao.getAutorizacaoCollection().remove(autorizacaoCollectionNewAutorizacao);
                        oldInstituicaoFkOfAutorizacaoCollectionNewAutorizacao = em.merge(oldInstituicaoFkOfAutorizacaoCollectionNewAutorizacao);
                    }
                }
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
                Integer id = instituicao.getId();
                if (findInstituicao(id) == null) {
                    throw new NonexistentEntityException("The instituicao with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Instituicao instituicao;
            try {
                instituicao = em.getReference(Instituicao.class, id);
                instituicao.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The instituicao with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Autorizacao> autorizacaoCollectionOrphanCheck = instituicao.getAutorizacaoCollection();
            for (Autorizacao autorizacaoCollectionOrphanCheckAutorizacao : autorizacaoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Instituicao (" + instituicao + ") cannot be destroyed since the Autorizacao " + autorizacaoCollectionOrphanCheckAutorizacao + " in its autorizacaoCollection field has a non-nullable instituicaoFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(instituicao);
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

    public List<Instituicao> findInstituicaoEntities() {
        return findInstituicaoEntities(true, -1, -1);
    }

    public List<Instituicao> findInstituicaoEntities(int maxResults, int firstResult) {
        return findInstituicaoEntities(false, maxResults, firstResult);
    }

    private List<Instituicao> findInstituicaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Instituicao.class));
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

    public Instituicao findInstituicao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Instituicao.class, id);
        } finally {
            em.close();
        }
    }

    public int getInstituicaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Instituicao> rt = cq.from(Instituicao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Instituicao findInstituicaoByCNPJ(String cnpj) {
        EntityManager em = getEntityManager();
        try {
            Instituicao instituicao = (Instituicao) em.createNativeQuery("select * from instituicao "
                    + "where cnpj = '" + cnpj + "'", Instituicao.class).getSingleResult();
            return instituicao;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public Instituicao findInstituicaoByCPF(String cpf) {
        EntityManager em = getEntityManager();
        try {
            Instituicao instituicao = (Instituicao) em.createNativeQuery("select ins.* from autorizacao aut, instituicao ins "
                    + "where aut.instituicao_fk = ins.id and "
                    + "aut.cpf = '" + cpf + "'", Instituicao.class).getSingleResult();
            return instituicao;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
}
