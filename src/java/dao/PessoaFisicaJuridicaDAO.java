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
import entidade.Funcao;
import entidade.PessoaFisica;
import entidade.PessoaFisicaJuridica;
import entidade.PessoaJuridica;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author paulones
 */
public class PessoaFisicaJuridicaDAO implements Serializable {

    public PessoaFisicaJuridicaDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PessoaFisicaJuridica pessoaFisicaJuridica) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcao funcaoFk = pessoaFisicaJuridica.getFuncaoFk();
            if (funcaoFk != null) {
                funcaoFk = em.getReference(funcaoFk.getClass(), funcaoFk.getId());
                pessoaFisicaJuridica.setFuncaoFk(funcaoFk);
            }
            PessoaFisica pessoaFisicaFk = pessoaFisicaJuridica.getPessoaFisicaFk();
            if (pessoaFisicaFk != null) {
                pessoaFisicaFk = em.getReference(pessoaFisicaFk.getClass(), pessoaFisicaFk.getId());
                pessoaFisicaJuridica.setPessoaFisicaFk(pessoaFisicaFk);
            }
            PessoaJuridica pessoaJuridicaFk = pessoaFisicaJuridica.getPessoaJuridicaFk();
            if (pessoaJuridicaFk != null) {
                pessoaJuridicaFk = em.getReference(pessoaJuridicaFk.getClass(), pessoaJuridicaFk.getId());
                pessoaFisicaJuridica.setPessoaJuridicaFk(pessoaJuridicaFk);
            }
            em.persist(pessoaFisicaJuridica);
            if (funcaoFk != null) {
                funcaoFk.getPessoaFisicaJuridicaCollection().add(pessoaFisicaJuridica);
                funcaoFk = em.merge(funcaoFk);
            }
            if (pessoaFisicaFk != null) {
                pessoaFisicaFk.getPessoaFisicaJuridicaCollection().add(pessoaFisicaJuridica);
                pessoaFisicaFk = em.merge(pessoaFisicaFk);
            }
            if (pessoaJuridicaFk != null) {
                pessoaJuridicaFk.getPessoaFisicaJuridicaCollection().add(pessoaFisicaJuridica);
                pessoaJuridicaFk = em.merge(pessoaJuridicaFk);
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

    public void edit(PessoaFisicaJuridica pessoaFisicaJuridica) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PessoaFisicaJuridica persistentPessoaFisicaJuridica = em.find(PessoaFisicaJuridica.class, pessoaFisicaJuridica.getId());
            Funcao funcaoFkOld = persistentPessoaFisicaJuridica.getFuncaoFk();
            Funcao funcaoFkNew = pessoaFisicaJuridica.getFuncaoFk();
            PessoaFisica pessoaFisicaFkOld = persistentPessoaFisicaJuridica.getPessoaFisicaFk();
            PessoaFisica pessoaFisicaFkNew = pessoaFisicaJuridica.getPessoaFisicaFk();
            PessoaJuridica pessoaJuridicaFkOld = persistentPessoaFisicaJuridica.getPessoaJuridicaFk();
            PessoaJuridica pessoaJuridicaFkNew = pessoaFisicaJuridica.getPessoaJuridicaFk();
            if (funcaoFkNew != null) {
                funcaoFkNew = em.getReference(funcaoFkNew.getClass(), funcaoFkNew.getId());
                pessoaFisicaJuridica.setFuncaoFk(funcaoFkNew);
            }
            if (pessoaFisicaFkNew != null) {
                pessoaFisicaFkNew = em.getReference(pessoaFisicaFkNew.getClass(), pessoaFisicaFkNew.getId());
                pessoaFisicaJuridica.setPessoaFisicaFk(pessoaFisicaFkNew);
            }
            if (pessoaJuridicaFkNew != null) {
                pessoaJuridicaFkNew = em.getReference(pessoaJuridicaFkNew.getClass(), pessoaJuridicaFkNew.getId());
                pessoaFisicaJuridica.setPessoaJuridicaFk(pessoaJuridicaFkNew);
            }
            pessoaFisicaJuridica = em.merge(pessoaFisicaJuridica);
            if (funcaoFkOld != null && !funcaoFkOld.equals(funcaoFkNew)) {
                funcaoFkOld.getPessoaFisicaJuridicaCollection().remove(pessoaFisicaJuridica);
                funcaoFkOld = em.merge(funcaoFkOld);
            }
            if (funcaoFkNew != null && !funcaoFkNew.equals(funcaoFkOld)) {
                funcaoFkNew.getPessoaFisicaJuridicaCollection().add(pessoaFisicaJuridica);
                funcaoFkNew = em.merge(funcaoFkNew);
            }
            if (pessoaFisicaFkOld != null && !pessoaFisicaFkOld.equals(pessoaFisicaFkNew)) {
                pessoaFisicaFkOld.getPessoaFisicaJuridicaCollection().remove(pessoaFisicaJuridica);
                pessoaFisicaFkOld = em.merge(pessoaFisicaFkOld);
            }
            if (pessoaFisicaFkNew != null && !pessoaFisicaFkNew.equals(pessoaFisicaFkOld)) {
                pessoaFisicaFkNew.getPessoaFisicaJuridicaCollection().add(pessoaFisicaJuridica);
                pessoaFisicaFkNew = em.merge(pessoaFisicaFkNew);
            }
            if (pessoaJuridicaFkOld != null && !pessoaJuridicaFkOld.equals(pessoaJuridicaFkNew)) {
                pessoaJuridicaFkOld.getPessoaFisicaJuridicaCollection().remove(pessoaFisicaJuridica);
                pessoaJuridicaFkOld = em.merge(pessoaJuridicaFkOld);
            }
            if (pessoaJuridicaFkNew != null && !pessoaJuridicaFkNew.equals(pessoaJuridicaFkOld)) {
                pessoaJuridicaFkNew.getPessoaFisicaJuridicaCollection().add(pessoaFisicaJuridica);
                pessoaJuridicaFkNew = em.merge(pessoaJuridicaFkNew);
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
                Integer id = pessoaFisicaJuridica.getId();
                if (findPessoaFisicaJuridica(id) == null) {
                    throw new NonexistentEntityException("The pessoaFisicaJuridica with id " + id + " no longer exists.");
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
            PessoaFisicaJuridica pessoaFisicaJuridica;
            try {
                pessoaFisicaJuridica = em.getReference(PessoaFisicaJuridica.class, id);
                pessoaFisicaJuridica.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pessoaFisicaJuridica with id " + id + " no longer exists.", enfe);
            }
            Funcao funcaoFk = pessoaFisicaJuridica.getFuncaoFk();
            if (funcaoFk != null) {
                funcaoFk.getPessoaFisicaJuridicaCollection().remove(pessoaFisicaJuridica);
                funcaoFk = em.merge(funcaoFk);
            }
            PessoaFisica pessoaFisicaFk = pessoaFisicaJuridica.getPessoaFisicaFk();
            if (pessoaFisicaFk != null) {
                pessoaFisicaFk.getPessoaFisicaJuridicaCollection().remove(pessoaFisicaJuridica);
                pessoaFisicaFk = em.merge(pessoaFisicaFk);
            }
            PessoaJuridica pessoaJuridicaFk = pessoaFisicaJuridica.getPessoaJuridicaFk();
            if (pessoaJuridicaFk != null) {
                pessoaJuridicaFk.getPessoaFisicaJuridicaCollection().remove(pessoaFisicaJuridica);
                pessoaJuridicaFk = em.merge(pessoaJuridicaFk);
            }
            em.remove(pessoaFisicaJuridica);
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

    public List<PessoaFisicaJuridica> findPessoaFisicaJuridicaEntities() {
        return findPessoaFisicaJuridicaEntities(true, -1, -1);
    }

    public List<PessoaFisicaJuridica> findPessoaFisicaJuridicaEntities(int maxResults, int firstResult) {
        return findPessoaFisicaJuridicaEntities(false, maxResults, firstResult);
    }

    private List<PessoaFisicaJuridica> findPessoaFisicaJuridicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PessoaFisicaJuridica.class));
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

    public PessoaFisicaJuridica findPessoaFisicaJuridica(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PessoaFisicaJuridica.class, id);
        } finally {
            em.close();
        }
    }

    public int getPessoaFisicaJuridicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PessoaFisicaJuridica> rt = cq.from(PessoaFisicaJuridica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
