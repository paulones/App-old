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
import entidade.Estado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author paulones
 */
public class EnderecoDAO implements Serializable {

    public EnderecoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Endereco endereco) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cidade cidadeFk = endereco.getCidadeFk();
            if (cidadeFk != null) {
                cidadeFk = em.getReference(cidadeFk.getClass(), cidadeFk.getId());
                endereco.setCidadeFk(cidadeFk);
            }
            Estado estadoFk = endereco.getEstadoFk();
            if (estadoFk != null) {
                estadoFk = em.getReference(estadoFk.getClass(), estadoFk.getId());
                endereco.setEstadoFk(estadoFk);
            }
            em.persist(endereco);
            if (cidadeFk != null) {
                cidadeFk.getEnderecoCollection().add(endereco);
                cidadeFk = em.merge(cidadeFk);
            }
            if (estadoFk != null) {
                estadoFk.getEnderecoCollection().add(endereco);
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

    public void edit(Endereco endereco) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Endereco persistentEndereco = em.find(Endereco.class, endereco.getId());
            Cidade cidadeFkOld = persistentEndereco.getCidadeFk();
            Cidade cidadeFkNew = endereco.getCidadeFk();
            Estado estadoFkOld = persistentEndereco.getEstadoFk();
            Estado estadoFkNew = endereco.getEstadoFk();
            if (cidadeFkNew != null) {
                cidadeFkNew = em.getReference(cidadeFkNew.getClass(), cidadeFkNew.getId());
                endereco.setCidadeFk(cidadeFkNew);
            }
            if (estadoFkNew != null) {
                estadoFkNew = em.getReference(estadoFkNew.getClass(), estadoFkNew.getId());
                endereco.setEstadoFk(estadoFkNew);
            }
            endereco = em.merge(endereco);
            if (cidadeFkOld != null && !cidadeFkOld.equals(cidadeFkNew)) {
                cidadeFkOld.getEnderecoCollection().remove(endereco);
                cidadeFkOld = em.merge(cidadeFkOld);
            }
            if (cidadeFkNew != null && !cidadeFkNew.equals(cidadeFkOld)) {
                cidadeFkNew.getEnderecoCollection().add(endereco);
                cidadeFkNew = em.merge(cidadeFkNew);
            }
            if (estadoFkOld != null && !estadoFkOld.equals(estadoFkNew)) {
                estadoFkOld.getEnderecoCollection().remove(endereco);
                estadoFkOld = em.merge(estadoFkOld);
            }
            if (estadoFkNew != null && !estadoFkNew.equals(estadoFkOld)) {
                estadoFkNew.getEnderecoCollection().add(endereco);
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
                Integer id = endereco.getId();
                if (findEndereco(id) == null) {
                    throw new NonexistentEntityException("The endereco with id " + id + " no longer exists.");
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
            Endereco endereco;
            try {
                endereco = em.getReference(Endereco.class, id);
                endereco.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The endereco with id " + id + " no longer exists.", enfe);
            }
            Cidade cidadeFk = endereco.getCidadeFk();
            if (cidadeFk != null) {
                cidadeFk.getEnderecoCollection().remove(endereco);
                cidadeFk = em.merge(cidadeFk);
            }
            Estado estadoFk = endereco.getEstadoFk();
            if (estadoFk != null) {
                estadoFk.getEnderecoCollection().remove(endereco);
                estadoFk = em.merge(estadoFk);
            }
            em.remove(endereco);
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

    public List<Endereco> findEnderecoEntities() {
        return findEnderecoEntities(true, -1, -1);
    }

    public List<Endereco> findEnderecoEntities(int maxResults, int firstResult) {
        return findEnderecoEntities(false, maxResults, firstResult);
    }

    private List<Endereco> findEnderecoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Endereco.class));
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

    public Endereco findEndereco(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Endereco.class, id);
        } finally {
            em.close();
        }
    }

    public int getEnderecoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Endereco> rt = cq.from(Endereco.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
