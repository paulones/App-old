/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.PessoaFisica;
import entidade.PessoaFisicaFisicaHistorico;
import entidade.PessoaFisicaHistorico;
import entidade.VinculoSocial;
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
public class PessoaFisicaFisicaHistoricoDAO implements Serializable {

    public PessoaFisicaFisicaHistoricoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PessoaFisicaFisicaHistorico pessoaFisicaFisicaHistorico) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            PessoaFisica pessoaFisicaPrimariaFk = pessoaFisicaFisicaHistorico.getPessoaFisicaPrimariaFk();
            if (pessoaFisicaPrimariaFk != null) {
                pessoaFisicaPrimariaFk = em.getReference(pessoaFisicaPrimariaFk.getClass(), pessoaFisicaPrimariaFk.getId());
                pessoaFisicaFisicaHistorico.setPessoaFisicaPrimariaFk(pessoaFisicaPrimariaFk);
            }
            PessoaFisica pessoaFisicaSecundariaFk = pessoaFisicaFisicaHistorico.getPessoaFisicaSecundariaFk();
            if (pessoaFisicaSecundariaFk != null) {
                pessoaFisicaSecundariaFk = em.getReference(pessoaFisicaSecundariaFk.getClass(), pessoaFisicaSecundariaFk.getId());
                pessoaFisicaFisicaHistorico.setPessoaFisicaSecundariaFk(pessoaFisicaSecundariaFk);
            }
            PessoaFisicaHistorico pessoaFisicaHistoricoFk = pessoaFisicaFisicaHistorico.getPessoaFisicaHistoricoFk();
            if (pessoaFisicaHistoricoFk != null) {
                pessoaFisicaHistoricoFk = em.getReference(pessoaFisicaHistoricoFk.getClass(), pessoaFisicaHistoricoFk.getId());
                pessoaFisicaFisicaHistorico.setPessoaFisicaHistoricoFk(pessoaFisicaHistoricoFk);
            }
            VinculoSocial vinculoSocialFk = pessoaFisicaFisicaHistorico.getVinculoSocialFk();
            if (vinculoSocialFk != null) {
                vinculoSocialFk = em.getReference(vinculoSocialFk.getClass(), vinculoSocialFk.getId());
                pessoaFisicaFisicaHistorico.setVinculoSocialFk(vinculoSocialFk);
            }
            em.persist(pessoaFisicaFisicaHistorico);
            if (pessoaFisicaPrimariaFk != null) {
                pessoaFisicaPrimariaFk.getPessoaFisicaFisicaHistoricoCollection().add(pessoaFisicaFisicaHistorico);
                pessoaFisicaPrimariaFk = em.merge(pessoaFisicaPrimariaFk);
            }
            if (pessoaFisicaSecundariaFk != null) {
                pessoaFisicaSecundariaFk.getPessoaFisicaFisicaHistoricoCollection().add(pessoaFisicaFisicaHistorico);
                pessoaFisicaSecundariaFk = em.merge(pessoaFisicaSecundariaFk);
            }
            if (pessoaFisicaHistoricoFk != null) {
                pessoaFisicaHistoricoFk.getPessoaFisicaFisicaHistoricoCollection().add(pessoaFisicaFisicaHistorico);
                pessoaFisicaHistoricoFk = em.merge(pessoaFisicaHistoricoFk);
            }
            if (vinculoSocialFk != null) {
                vinculoSocialFk.getPessoaFisicaFisicaHistoricoCollection().add(pessoaFisicaFisicaHistorico);
                vinculoSocialFk = em.merge(vinculoSocialFk);
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

    public void edit(PessoaFisicaFisicaHistorico pessoaFisicaFisicaHistorico) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            PessoaFisicaFisicaHistorico persistentPessoaFisicaFisicaHistorico = em.find(PessoaFisicaFisicaHistorico.class, pessoaFisicaFisicaHistorico.getId());
            PessoaFisica pessoaFisicaPrimariaFkOld = persistentPessoaFisicaFisicaHistorico.getPessoaFisicaPrimariaFk();
            PessoaFisica pessoaFisicaPrimariaFkNew = pessoaFisicaFisicaHistorico.getPessoaFisicaPrimariaFk();
            PessoaFisica pessoaFisicaSecundariaFkOld = persistentPessoaFisicaFisicaHistorico.getPessoaFisicaSecundariaFk();
            PessoaFisica pessoaFisicaSecundariaFkNew = pessoaFisicaFisicaHistorico.getPessoaFisicaSecundariaFk();
            PessoaFisicaHistorico pessoaFisicaHistoricoFkOld = persistentPessoaFisicaFisicaHistorico.getPessoaFisicaHistoricoFk();
            PessoaFisicaHistorico pessoaFisicaHistoricoFkNew = pessoaFisicaFisicaHistorico.getPessoaFisicaHistoricoFk();
            VinculoSocial vinculoSocialFkOld = persistentPessoaFisicaFisicaHistorico.getVinculoSocialFk();
            VinculoSocial vinculoSocialFkNew = pessoaFisicaFisicaHistorico.getVinculoSocialFk();
            if (pessoaFisicaPrimariaFkNew != null) {
                pessoaFisicaPrimariaFkNew = em.getReference(pessoaFisicaPrimariaFkNew.getClass(), pessoaFisicaPrimariaFkNew.getId());
                pessoaFisicaFisicaHistorico.setPessoaFisicaPrimariaFk(pessoaFisicaPrimariaFkNew);
            }
            if (pessoaFisicaSecundariaFkNew != null) {
                pessoaFisicaSecundariaFkNew = em.getReference(pessoaFisicaSecundariaFkNew.getClass(), pessoaFisicaSecundariaFkNew.getId());
                pessoaFisicaFisicaHistorico.setPessoaFisicaSecundariaFk(pessoaFisicaSecundariaFkNew);
            }
            if (pessoaFisicaHistoricoFkNew != null) {
                pessoaFisicaHistoricoFkNew = em.getReference(pessoaFisicaHistoricoFkNew.getClass(), pessoaFisicaHistoricoFkNew.getId());
                pessoaFisicaFisicaHistorico.setPessoaFisicaHistoricoFk(pessoaFisicaHistoricoFkNew);
            }
            if (vinculoSocialFkNew != null) {
                vinculoSocialFkNew = em.getReference(vinculoSocialFkNew.getClass(), vinculoSocialFkNew.getId());
                pessoaFisicaFisicaHistorico.setVinculoSocialFk(vinculoSocialFkNew);
            }
            pessoaFisicaFisicaHistorico = em.merge(pessoaFisicaFisicaHistorico);
            if (pessoaFisicaPrimariaFkOld != null && !pessoaFisicaPrimariaFkOld.equals(pessoaFisicaPrimariaFkNew)) {
                pessoaFisicaPrimariaFkOld.getPessoaFisicaFisicaHistoricoCollection().remove(pessoaFisicaFisicaHistorico);
                pessoaFisicaPrimariaFkOld = em.merge(pessoaFisicaPrimariaFkOld);
            }
            if (pessoaFisicaPrimariaFkNew != null && !pessoaFisicaPrimariaFkNew.equals(pessoaFisicaPrimariaFkOld)) {
                pessoaFisicaPrimariaFkNew.getPessoaFisicaFisicaHistoricoCollection().add(pessoaFisicaFisicaHistorico);
                pessoaFisicaPrimariaFkNew = em.merge(pessoaFisicaPrimariaFkNew);
            }
            if (pessoaFisicaSecundariaFkOld != null && !pessoaFisicaSecundariaFkOld.equals(pessoaFisicaSecundariaFkNew)) {
                pessoaFisicaSecundariaFkOld.getPessoaFisicaFisicaHistoricoCollection().remove(pessoaFisicaFisicaHistorico);
                pessoaFisicaSecundariaFkOld = em.merge(pessoaFisicaSecundariaFkOld);
            }
            if (pessoaFisicaSecundariaFkNew != null && !pessoaFisicaSecundariaFkNew.equals(pessoaFisicaSecundariaFkOld)) {
                pessoaFisicaSecundariaFkNew.getPessoaFisicaFisicaHistoricoCollection().add(pessoaFisicaFisicaHistorico);
                pessoaFisicaSecundariaFkNew = em.merge(pessoaFisicaSecundariaFkNew);
            }
            if (pessoaFisicaHistoricoFkOld != null && !pessoaFisicaHistoricoFkOld.equals(pessoaFisicaHistoricoFkNew)) {
                pessoaFisicaHistoricoFkOld.getPessoaFisicaFisicaHistoricoCollection().remove(pessoaFisicaFisicaHistorico);
                pessoaFisicaHistoricoFkOld = em.merge(pessoaFisicaHistoricoFkOld);
            }
            if (pessoaFisicaHistoricoFkNew != null && !pessoaFisicaHistoricoFkNew.equals(pessoaFisicaHistoricoFkOld)) {
                pessoaFisicaHistoricoFkNew.getPessoaFisicaFisicaHistoricoCollection().add(pessoaFisicaFisicaHistorico);
                pessoaFisicaHistoricoFkNew = em.merge(pessoaFisicaHistoricoFkNew);
            }
            if (vinculoSocialFkOld != null && !vinculoSocialFkOld.equals(vinculoSocialFkNew)) {
                vinculoSocialFkOld.getPessoaFisicaFisicaHistoricoCollection().remove(pessoaFisicaFisicaHistorico);
                vinculoSocialFkOld = em.merge(vinculoSocialFkOld);
            }
            if (vinculoSocialFkNew != null && !vinculoSocialFkNew.equals(vinculoSocialFkOld)) {
                vinculoSocialFkNew.getPessoaFisicaFisicaHistoricoCollection().add(pessoaFisicaFisicaHistorico);
                vinculoSocialFkNew = em.merge(vinculoSocialFkNew);
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
                Integer id = pessoaFisicaFisicaHistorico.getId();
                if (findPessoaFisicaFisicaHistorico(id) == null) {
                    throw new NonexistentEntityException("The pessoaFisicaFisicaHistorico with id " + id + " no longer exists.");
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
            PessoaFisicaFisicaHistorico pessoaFisicaFisicaHistorico;
            try {
                pessoaFisicaFisicaHistorico = em.getReference(PessoaFisicaFisicaHistorico.class, id);
                pessoaFisicaFisicaHistorico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pessoaFisicaFisicaHistorico with id " + id + " no longer exists.", enfe);
            }
            PessoaFisica pessoaFisicaPrimariaFk = pessoaFisicaFisicaHistorico.getPessoaFisicaPrimariaFk();
            if (pessoaFisicaPrimariaFk != null) {
                pessoaFisicaPrimariaFk.getPessoaFisicaFisicaHistoricoCollection().remove(pessoaFisicaFisicaHistorico);
                pessoaFisicaPrimariaFk = em.merge(pessoaFisicaPrimariaFk);
            }
            PessoaFisica pessoaFisicaSecundariaFk = pessoaFisicaFisicaHistorico.getPessoaFisicaSecundariaFk();
            if (pessoaFisicaSecundariaFk != null) {
                pessoaFisicaSecundariaFk.getPessoaFisicaFisicaHistoricoCollection().remove(pessoaFisicaFisicaHistorico);
                pessoaFisicaSecundariaFk = em.merge(pessoaFisicaSecundariaFk);
            }
            PessoaFisicaHistorico pessoaFisicaHistoricoFk = pessoaFisicaFisicaHistorico.getPessoaFisicaHistoricoFk();
            if (pessoaFisicaHistoricoFk != null) {
                pessoaFisicaHistoricoFk.getPessoaFisicaFisicaHistoricoCollection().remove(pessoaFisicaFisicaHistorico);
                pessoaFisicaHistoricoFk = em.merge(pessoaFisicaHistoricoFk);
            }
            VinculoSocial vinculoSocialFk = pessoaFisicaFisicaHistorico.getVinculoSocialFk();
            if (vinculoSocialFk != null) {
                vinculoSocialFk.getPessoaFisicaFisicaHistoricoCollection().remove(pessoaFisicaFisicaHistorico);
                vinculoSocialFk = em.merge(vinculoSocialFk);
            }
            em.remove(pessoaFisicaFisicaHistorico);
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

    public List<PessoaFisicaFisicaHistorico> findPessoaFisicaFisicaHistoricoEntities() {
        return findPessoaFisicaFisicaHistoricoEntities(true, -1, -1);
    }

    public List<PessoaFisicaFisicaHistorico> findPessoaFisicaFisicaHistoricoEntities(int maxResults, int firstResult) {
        return findPessoaFisicaFisicaHistoricoEntities(false, maxResults, firstResult);
    }

    private List<PessoaFisicaFisicaHistorico> findPessoaFisicaFisicaHistoricoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PessoaFisicaFisicaHistorico.class));
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

    public PessoaFisicaFisicaHistorico findPessoaFisicaFisicaHistorico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PessoaFisicaFisicaHistorico.class, id);
        } finally {
            em.close();
        }
    }

    public int getPessoaFisicaFisicaHistoricoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PessoaFisicaFisicaHistorico> rt = cq.from(PessoaFisicaFisicaHistorico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<PessoaFisicaFisicaHistorico> findAllByPFH(Integer id){
        EntityManager em = getEntityManager();
        try {
            List<PessoaFisicaFisicaHistorico> pessoaFisicaFisicaHistorico = (List<PessoaFisicaFisicaHistorico>) em.createNativeQuery("select pffh.* from pessoa_fisica_fisica_historico pffh "
                        + "where pffh.pessoa_fisica_historico_fk = '"+id+"'", PessoaFisicaFisicaHistorico.class).getResultList();
            return pessoaFisicaFisicaHistorico;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
}
