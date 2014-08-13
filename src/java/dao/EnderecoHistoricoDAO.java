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
import entidade.Cidade;
import entidade.Endereco;
import entidade.EnderecoHistorico;
import entidade.Estado;
import entidade.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author paulones
 */
public class EnderecoHistoricoDAO implements Serializable {

    public EnderecoHistoricoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EnderecoHistorico enderecoHistorico) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cidade cidadeFk = enderecoHistorico.getCidadeFk();
            if (cidadeFk != null) {
                cidadeFk = em.getReference(cidadeFk.getClass(), cidadeFk.getId());
                enderecoHistorico.setCidadeFk(cidadeFk);
            }
            Estado estadoFk = enderecoHistorico.getEstadoFk();
            if (estadoFk != null) {
                estadoFk = em.getReference(estadoFk.getClass(), estadoFk.getId());
                enderecoHistorico.setEstadoFk(estadoFk);
            }
            em.persist(enderecoHistorico);
            if (cidadeFk != null) {
                cidadeFk.getEnderecoHistoricoCollection().add(enderecoHistorico);
                cidadeFk = em.merge(cidadeFk);
            }
            if (estadoFk != null) {
                estadoFk.getEnderecoHistoricoCollection().add(enderecoHistorico);
                estadoFk = em.merge(estadoFk);
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

    public void edit(EnderecoHistorico enderecoHistorico) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EnderecoHistorico persistentEnderecoHistorico = em.find(EnderecoHistorico.class, enderecoHistorico.getId());
            Cidade cidadeFkOld = persistentEnderecoHistorico.getCidadeFk();
            Cidade cidadeFkNew = enderecoHistorico.getCidadeFk();
            Estado estadoFkOld = persistentEnderecoHistorico.getEstadoFk();
            Estado estadoFkNew = enderecoHistorico.getEstadoFk();
            if (cidadeFkNew != null) {
                cidadeFkNew = em.getReference(cidadeFkNew.getClass(), cidadeFkNew.getId());
                enderecoHistorico.setCidadeFk(cidadeFkNew);
            }
            if (estadoFkNew != null) {
                estadoFkNew = em.getReference(estadoFkNew.getClass(), estadoFkNew.getId());
                enderecoHistorico.setEstadoFk(estadoFkNew);
            }
            enderecoHistorico = em.merge(enderecoHistorico);
            if (cidadeFkOld != null && !cidadeFkOld.equals(cidadeFkNew)) {
                cidadeFkOld.getEnderecoHistoricoCollection().remove(enderecoHistorico);
                cidadeFkOld = em.merge(cidadeFkOld);
            }
            if (cidadeFkNew != null && !cidadeFkNew.equals(cidadeFkOld)) {
                cidadeFkNew.getEnderecoHistoricoCollection().add(enderecoHistorico);
                cidadeFkNew = em.merge(cidadeFkNew);
            }
            if (estadoFkOld != null && !estadoFkOld.equals(estadoFkNew)) {
                estadoFkOld.getEnderecoHistoricoCollection().remove(enderecoHistorico);
                estadoFkOld = em.merge(estadoFkOld);
            }
            if (estadoFkNew != null && !estadoFkNew.equals(estadoFkOld)) {
                estadoFkNew.getEnderecoHistoricoCollection().add(enderecoHistorico);
                estadoFkNew = em.merge(estadoFkNew);
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
                Integer id = enderecoHistorico.getId();
                if (findEnderecoHistorico(id) == null) {
                    throw new NonexistentEntityException("The enderecoHistorico with id " + id + " no longer exists.");
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
            EnderecoHistorico enderecoHistorico;
            try {
                enderecoHistorico = em.getReference(EnderecoHistorico.class, id);
                enderecoHistorico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The enderecoHistorico with id " + id + " no longer exists.", enfe);
            }
            Cidade cidadeFk = enderecoHistorico.getCidadeFk();
            if (cidadeFk != null) {
                cidadeFk.getEnderecoHistoricoCollection().remove(enderecoHistorico);
                cidadeFk = em.merge(cidadeFk);
            }
            Estado estadoFk = enderecoHistorico.getEstadoFk();
            if (estadoFk != null) {
                estadoFk.getEnderecoHistoricoCollection().remove(enderecoHistorico);
                estadoFk = em.merge(estadoFk);
            }
            em.remove(enderecoHistorico);
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

    public List<EnderecoHistorico> findEnderecoHistoricoEntities() {
        return findEnderecoHistoricoEntities(true, -1, -1);
    }

    public List<EnderecoHistorico> findEnderecoHistoricoEntities(int maxResults, int firstResult) {
        return findEnderecoHistoricoEntities(false, maxResults, firstResult);
    }

    private List<EnderecoHistorico> findEnderecoHistoricoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EnderecoHistorico.class));
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

    public EnderecoHistorico findEnderecoHistorico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EnderecoHistorico.class, id);
        } finally {
            em.close();
        }
    }

    public int getEnderecoHistoricoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EnderecoHistorico> rt = cq.from(EnderecoHistorico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
