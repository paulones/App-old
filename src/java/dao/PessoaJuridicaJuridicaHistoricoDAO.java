/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.PessoaJuridica;
import entidade.PessoaJuridicaHistorico;
import entidade.PessoaJuridicaJuridicaHistorico;
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
public class PessoaJuridicaJuridicaHistoricoDAO implements Serializable {

    public PessoaJuridicaJuridicaHistoricoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PessoaJuridicaJuridicaHistorico pessoaJuridicaJuridicaHistorico) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            PessoaJuridica pessoaJuridicaPrimariaFk = pessoaJuridicaJuridicaHistorico.getPessoaJuridicaPrimariaFk();
            if (pessoaJuridicaPrimariaFk != null) {
                pessoaJuridicaPrimariaFk = em.getReference(pessoaJuridicaPrimariaFk.getClass(), pessoaJuridicaPrimariaFk.getId());
                pessoaJuridicaJuridicaHistorico.setPessoaJuridicaPrimariaFk(pessoaJuridicaPrimariaFk);
            }
            PessoaJuridica pessoaJuridicaSecundariaFk = pessoaJuridicaJuridicaHistorico.getPessoaJuridicaSecundariaFk();
            if (pessoaJuridicaSecundariaFk != null) {
                pessoaJuridicaSecundariaFk = em.getReference(pessoaJuridicaSecundariaFk.getClass(), pessoaJuridicaSecundariaFk.getId());
                pessoaJuridicaJuridicaHistorico.setPessoaJuridicaSecundariaFk(pessoaJuridicaSecundariaFk);
            }
            PessoaJuridicaHistorico pessoaJuridicaHistoricoFk = pessoaJuridicaJuridicaHistorico.getPessoaJuridicaHistoricoFk();
            if (pessoaJuridicaHistoricoFk != null) {
                pessoaJuridicaHistoricoFk = em.getReference(pessoaJuridicaHistoricoFk.getClass(), pessoaJuridicaHistoricoFk.getId());
                pessoaJuridicaJuridicaHistorico.setPessoaJuridicaHistoricoFk(pessoaJuridicaHistoricoFk);
            }
            em.persist(pessoaJuridicaJuridicaHistorico);
            if (pessoaJuridicaPrimariaFk != null) {
                pessoaJuridicaPrimariaFk.getPessoaJuridicaJuridicaHistoricoCollection().add(pessoaJuridicaJuridicaHistorico);
                pessoaJuridicaPrimariaFk = em.merge(pessoaJuridicaPrimariaFk);
            }
            if (pessoaJuridicaSecundariaFk != null) {
                pessoaJuridicaSecundariaFk.getPessoaJuridicaJuridicaHistoricoCollection().add(pessoaJuridicaJuridicaHistorico);
                pessoaJuridicaSecundariaFk = em.merge(pessoaJuridicaSecundariaFk);
            }
            if (pessoaJuridicaHistoricoFk != null) {
                pessoaJuridicaHistoricoFk.getPessoaJuridicaJuridicaHistoricoCollection().add(pessoaJuridicaJuridicaHistorico);
                pessoaJuridicaHistoricoFk = em.merge(pessoaJuridicaHistoricoFk);
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

    public void edit(PessoaJuridicaJuridicaHistorico pessoaJuridicaJuridicaHistorico) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            PessoaJuridicaJuridicaHistorico persistentPessoaJuridicaJuridicaHistorico = em.find(PessoaJuridicaJuridicaHistorico.class, pessoaJuridicaJuridicaHistorico.getId());
            PessoaJuridica pessoaJuridicaPrimariaFkOld = persistentPessoaJuridicaJuridicaHistorico.getPessoaJuridicaPrimariaFk();
            PessoaJuridica pessoaJuridicaPrimariaFkNew = pessoaJuridicaJuridicaHistorico.getPessoaJuridicaPrimariaFk();
            PessoaJuridica pessoaJuridicaSecundariaFkOld = persistentPessoaJuridicaJuridicaHistorico.getPessoaJuridicaSecundariaFk();
            PessoaJuridica pessoaJuridicaSecundariaFkNew = pessoaJuridicaJuridicaHistorico.getPessoaJuridicaSecundariaFk();
            PessoaJuridicaHistorico pessoaJuridicaHistoricoFkOld = persistentPessoaJuridicaJuridicaHistorico.getPessoaJuridicaHistoricoFk();
            PessoaJuridicaHistorico pessoaJuridicaHistoricoFkNew = pessoaJuridicaJuridicaHistorico.getPessoaJuridicaHistoricoFk();
            if (pessoaJuridicaPrimariaFkNew != null) {
                pessoaJuridicaPrimariaFkNew = em.getReference(pessoaJuridicaPrimariaFkNew.getClass(), pessoaJuridicaPrimariaFkNew.getId());
                pessoaJuridicaJuridicaHistorico.setPessoaJuridicaPrimariaFk(pessoaJuridicaPrimariaFkNew);
            }
            if (pessoaJuridicaSecundariaFkNew != null) {
                pessoaJuridicaSecundariaFkNew = em.getReference(pessoaJuridicaSecundariaFkNew.getClass(), pessoaJuridicaSecundariaFkNew.getId());
                pessoaJuridicaJuridicaHistorico.setPessoaJuridicaSecundariaFk(pessoaJuridicaSecundariaFkNew);
            }
            if (pessoaJuridicaHistoricoFkNew != null) {
                pessoaJuridicaHistoricoFkNew = em.getReference(pessoaJuridicaHistoricoFkNew.getClass(), pessoaJuridicaHistoricoFkNew.getId());
                pessoaJuridicaJuridicaHistorico.setPessoaJuridicaHistoricoFk(pessoaJuridicaHistoricoFkNew);
            }
            pessoaJuridicaJuridicaHistorico = em.merge(pessoaJuridicaJuridicaHistorico);
            if (pessoaJuridicaPrimariaFkOld != null && !pessoaJuridicaPrimariaFkOld.equals(pessoaJuridicaPrimariaFkNew)) {
                pessoaJuridicaPrimariaFkOld.getPessoaJuridicaJuridicaHistoricoCollection().remove(pessoaJuridicaJuridicaHistorico);
                pessoaJuridicaPrimariaFkOld = em.merge(pessoaJuridicaPrimariaFkOld);
            }
            if (pessoaJuridicaPrimariaFkNew != null && !pessoaJuridicaPrimariaFkNew.equals(pessoaJuridicaPrimariaFkOld)) {
                pessoaJuridicaPrimariaFkNew.getPessoaJuridicaJuridicaHistoricoCollection().add(pessoaJuridicaJuridicaHistorico);
                pessoaJuridicaPrimariaFkNew = em.merge(pessoaJuridicaPrimariaFkNew);
            }
            if (pessoaJuridicaSecundariaFkOld != null && !pessoaJuridicaSecundariaFkOld.equals(pessoaJuridicaSecundariaFkNew)) {
                pessoaJuridicaSecundariaFkOld.getPessoaJuridicaJuridicaHistoricoCollection().remove(pessoaJuridicaJuridicaHistorico);
                pessoaJuridicaSecundariaFkOld = em.merge(pessoaJuridicaSecundariaFkOld);
            }
            if (pessoaJuridicaSecundariaFkNew != null && !pessoaJuridicaSecundariaFkNew.equals(pessoaJuridicaSecundariaFkOld)) {
                pessoaJuridicaSecundariaFkNew.getPessoaJuridicaJuridicaHistoricoCollection().add(pessoaJuridicaJuridicaHistorico);
                pessoaJuridicaSecundariaFkNew = em.merge(pessoaJuridicaSecundariaFkNew);
            }
            if (pessoaJuridicaHistoricoFkOld != null && !pessoaJuridicaHistoricoFkOld.equals(pessoaJuridicaHistoricoFkNew)) {
                pessoaJuridicaHistoricoFkOld.getPessoaJuridicaJuridicaHistoricoCollection().remove(pessoaJuridicaJuridicaHistorico);
                pessoaJuridicaHistoricoFkOld = em.merge(pessoaJuridicaHistoricoFkOld);
            }
            if (pessoaJuridicaHistoricoFkNew != null && !pessoaJuridicaHistoricoFkNew.equals(pessoaJuridicaHistoricoFkOld)) {
                pessoaJuridicaHistoricoFkNew.getPessoaJuridicaJuridicaHistoricoCollection().add(pessoaJuridicaJuridicaHistorico);
                pessoaJuridicaHistoricoFkNew = em.merge(pessoaJuridicaHistoricoFkNew);
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
                Integer id = pessoaJuridicaJuridicaHistorico.getId();
                if (findPessoaJuridicaJuridicaHistorico(id) == null) {
                    throw new NonexistentEntityException("The pessoaJuridicaJuridicaHistorico with id " + id + " no longer exists.");
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
            PessoaJuridicaJuridicaHistorico pessoaJuridicaJuridicaHistorico;
            try {
                pessoaJuridicaJuridicaHistorico = em.getReference(PessoaJuridicaJuridicaHistorico.class, id);
                pessoaJuridicaJuridicaHistorico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pessoaJuridicaJuridicaHistorico with id " + id + " no longer exists.", enfe);
            }
            PessoaJuridica pessoaJuridicaPrimariaFk = pessoaJuridicaJuridicaHistorico.getPessoaJuridicaPrimariaFk();
            if (pessoaJuridicaPrimariaFk != null) {
                pessoaJuridicaPrimariaFk.getPessoaJuridicaJuridicaHistoricoCollection().remove(pessoaJuridicaJuridicaHistorico);
                pessoaJuridicaPrimariaFk = em.merge(pessoaJuridicaPrimariaFk);
            }
            PessoaJuridica pessoaJuridicaSecundariaFk = pessoaJuridicaJuridicaHistorico.getPessoaJuridicaSecundariaFk();
            if (pessoaJuridicaSecundariaFk != null) {
                pessoaJuridicaSecundariaFk.getPessoaJuridicaJuridicaHistoricoCollection().remove(pessoaJuridicaJuridicaHistorico);
                pessoaJuridicaSecundariaFk = em.merge(pessoaJuridicaSecundariaFk);
            }
            PessoaJuridicaHistorico pessoaJuridicaHistoricoFk = pessoaJuridicaJuridicaHistorico.getPessoaJuridicaHistoricoFk();
            if (pessoaJuridicaHistoricoFk != null) {
                pessoaJuridicaHistoricoFk.getPessoaJuridicaJuridicaHistoricoCollection().remove(pessoaJuridicaJuridicaHistorico);
                pessoaJuridicaHistoricoFk = em.merge(pessoaJuridicaHistoricoFk);
            }
            em.remove(pessoaJuridicaJuridicaHistorico);
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

    public List<PessoaJuridicaJuridicaHistorico> findPessoaJuridicaJuridicaHistoricoEntities() {
        return findPessoaJuridicaJuridicaHistoricoEntities(true, -1, -1);
    }

    public List<PessoaJuridicaJuridicaHistorico> findPessoaJuridicaJuridicaHistoricoEntities(int maxResults, int firstResult) {
        return findPessoaJuridicaJuridicaHistoricoEntities(false, maxResults, firstResult);
    }

    private List<PessoaJuridicaJuridicaHistorico> findPessoaJuridicaJuridicaHistoricoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PessoaJuridicaJuridicaHistorico.class));
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

    public PessoaJuridicaJuridicaHistorico findPessoaJuridicaJuridicaHistorico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PessoaJuridicaJuridicaHistorico.class, id);
        } finally {
            em.close();
        }
    }

    public int getPessoaJuridicaJuridicaHistoricoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PessoaJuridicaJuridicaHistorico> rt = cq.from(PessoaJuridicaJuridicaHistorico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<PessoaJuridicaJuridicaHistorico> findAllByPJH(Integer id){
        EntityManager em = getEntityManager();
        try {
            List<PessoaJuridicaJuridicaHistorico> pessoaJuridicaJuridicaHistorico = (List<PessoaJuridicaJuridicaHistorico>) em.createNativeQuery("select pjjh.* from pessoa_juridica_juridica_historico pjjh "
                        + "where pjjh.pessoa_juridica_historico_fk = '"+id+"'", PessoaJuridicaJuridicaHistorico.class).getResultList();
            return pessoaJuridicaJuridicaHistorico;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
}
