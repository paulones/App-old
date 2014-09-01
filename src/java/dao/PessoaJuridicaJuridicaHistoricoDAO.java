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
            PessoaJuridica pessoaJuridicaSocioAFk = pessoaJuridicaJuridicaHistorico.getPessoaJuridicaSocioAFk();
            if (pessoaJuridicaSocioAFk != null) {
                pessoaJuridicaSocioAFk = em.getReference(pessoaJuridicaSocioAFk.getClass(), pessoaJuridicaSocioAFk.getId());
                pessoaJuridicaJuridicaHistorico.setPessoaJuridicaSocioAFk(pessoaJuridicaSocioAFk);
            }
            PessoaJuridica pessoaJuridicaSocioBFk = pessoaJuridicaJuridicaHistorico.getPessoaJuridicaSocioBFk();
            if (pessoaJuridicaSocioBFk != null) {
                pessoaJuridicaSocioBFk = em.getReference(pessoaJuridicaSocioBFk.getClass(), pessoaJuridicaSocioBFk.getId());
                pessoaJuridicaJuridicaHistorico.setPessoaJuridicaSocioBFk(pessoaJuridicaSocioBFk);
            }
            PessoaJuridicaHistorico pessoaJuridicaHistoricoFk = pessoaJuridicaJuridicaHistorico.getPessoaJuridicaHistoricoFk();
            if (pessoaJuridicaHistoricoFk != null) {
                pessoaJuridicaHistoricoFk = em.getReference(pessoaJuridicaHistoricoFk.getClass(), pessoaJuridicaHistoricoFk.getId());
                pessoaJuridicaJuridicaHistorico.setPessoaJuridicaHistoricoFk(pessoaJuridicaHistoricoFk);
            }
            em.persist(pessoaJuridicaJuridicaHistorico);
            if (pessoaJuridicaSocioAFk != null) {
                pessoaJuridicaSocioAFk.getPessoaJuridicaJuridicaHistoricoCollection().add(pessoaJuridicaJuridicaHistorico);
                pessoaJuridicaSocioAFk = em.merge(pessoaJuridicaSocioAFk);
            }
            if (pessoaJuridicaSocioBFk != null) {
                pessoaJuridicaSocioBFk.getPessoaJuridicaJuridicaHistoricoCollection().add(pessoaJuridicaJuridicaHistorico);
                pessoaJuridicaSocioBFk = em.merge(pessoaJuridicaSocioBFk);
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
            PessoaJuridica pessoaJuridicaSocioAFkOld = persistentPessoaJuridicaJuridicaHistorico.getPessoaJuridicaSocioAFk();
            PessoaJuridica pessoaJuridicaSocioAFkNew = pessoaJuridicaJuridicaHistorico.getPessoaJuridicaSocioAFk();
            PessoaJuridica pessoaJuridicaSocioBFkOld = persistentPessoaJuridicaJuridicaHistorico.getPessoaJuridicaSocioBFk();
            PessoaJuridica pessoaJuridicaSocioBFkNew = pessoaJuridicaJuridicaHistorico.getPessoaJuridicaSocioBFk();
            PessoaJuridicaHistorico pessoaJuridicaHistoricoFkOld = persistentPessoaJuridicaJuridicaHistorico.getPessoaJuridicaHistoricoFk();
            PessoaJuridicaHistorico pessoaJuridicaHistoricoFkNew = pessoaJuridicaJuridicaHistorico.getPessoaJuridicaHistoricoFk();
            if (pessoaJuridicaSocioAFkNew != null) {
                pessoaJuridicaSocioAFkNew = em.getReference(pessoaJuridicaSocioAFkNew.getClass(), pessoaJuridicaSocioAFkNew.getId());
                pessoaJuridicaJuridicaHistorico.setPessoaJuridicaSocioAFk(pessoaJuridicaSocioAFkNew);
            }
            if (pessoaJuridicaSocioBFkNew != null) {
                pessoaJuridicaSocioBFkNew = em.getReference(pessoaJuridicaSocioBFkNew.getClass(), pessoaJuridicaSocioBFkNew.getId());
                pessoaJuridicaJuridicaHistorico.setPessoaJuridicaSocioBFk(pessoaJuridicaSocioBFkNew);
            }
            if (pessoaJuridicaHistoricoFkNew != null) {
                pessoaJuridicaHistoricoFkNew = em.getReference(pessoaJuridicaHistoricoFkNew.getClass(), pessoaJuridicaHistoricoFkNew.getId());
                pessoaJuridicaJuridicaHistorico.setPessoaJuridicaHistoricoFk(pessoaJuridicaHistoricoFkNew);
            }
            pessoaJuridicaJuridicaHistorico = em.merge(pessoaJuridicaJuridicaHistorico);
            if (pessoaJuridicaSocioAFkOld != null && !pessoaJuridicaSocioAFkOld.equals(pessoaJuridicaSocioAFkNew)) {
                pessoaJuridicaSocioAFkOld.getPessoaJuridicaJuridicaHistoricoCollection().remove(pessoaJuridicaJuridicaHistorico);
                pessoaJuridicaSocioAFkOld = em.merge(pessoaJuridicaSocioAFkOld);
            }
            if (pessoaJuridicaSocioAFkNew != null && !pessoaJuridicaSocioAFkNew.equals(pessoaJuridicaSocioAFkOld)) {
                pessoaJuridicaSocioAFkNew.getPessoaJuridicaJuridicaHistoricoCollection().add(pessoaJuridicaJuridicaHistorico);
                pessoaJuridicaSocioAFkNew = em.merge(pessoaJuridicaSocioAFkNew);
            }
            if (pessoaJuridicaSocioBFkOld != null && !pessoaJuridicaSocioBFkOld.equals(pessoaJuridicaSocioBFkNew)) {
                pessoaJuridicaSocioBFkOld.getPessoaJuridicaJuridicaHistoricoCollection().remove(pessoaJuridicaJuridicaHistorico);
                pessoaJuridicaSocioBFkOld = em.merge(pessoaJuridicaSocioBFkOld);
            }
            if (pessoaJuridicaSocioBFkNew != null && !pessoaJuridicaSocioBFkNew.equals(pessoaJuridicaSocioBFkOld)) {
                pessoaJuridicaSocioBFkNew.getPessoaJuridicaJuridicaHistoricoCollection().add(pessoaJuridicaJuridicaHistorico);
                pessoaJuridicaSocioBFkNew = em.merge(pessoaJuridicaSocioBFkNew);
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
            PessoaJuridica pessoaJuridicaSocioAFk = pessoaJuridicaJuridicaHistorico.getPessoaJuridicaSocioAFk();
            if (pessoaJuridicaSocioAFk != null) {
                pessoaJuridicaSocioAFk.getPessoaJuridicaJuridicaHistoricoCollection().remove(pessoaJuridicaJuridicaHistorico);
                pessoaJuridicaSocioAFk = em.merge(pessoaJuridicaSocioAFk);
            }
            PessoaJuridica pessoaJuridicaSocioBFk = pessoaJuridicaJuridicaHistorico.getPessoaJuridicaSocioBFk();
            if (pessoaJuridicaSocioBFk != null) {
                pessoaJuridicaSocioBFk.getPessoaJuridicaJuridicaHistoricoCollection().remove(pessoaJuridicaJuridicaHistorico);
                pessoaJuridicaSocioBFk = em.merge(pessoaJuridicaSocioBFk);
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
