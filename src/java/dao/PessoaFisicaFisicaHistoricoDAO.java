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
            PessoaFisica pessoaFisicaAFk = pessoaFisicaFisicaHistorico.getPessoaFisicaAFk();
            if (pessoaFisicaAFk != null) {
                pessoaFisicaAFk = em.getReference(pessoaFisicaAFk.getClass(), pessoaFisicaAFk.getId());
                pessoaFisicaFisicaHistorico.setPessoaFisicaAFk(pessoaFisicaAFk);
            }
            PessoaFisica pessoaFisicaBFk = pessoaFisicaFisicaHistorico.getPessoaFisicaBFk();
            if (pessoaFisicaBFk != null) {
                pessoaFisicaBFk = em.getReference(pessoaFisicaBFk.getClass(), pessoaFisicaBFk.getId());
                pessoaFisicaFisicaHistorico.setPessoaFisicaBFk(pessoaFisicaBFk);
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
            if (pessoaFisicaAFk != null) {
                pessoaFisicaAFk.getPessoaFisicaFisicaHistoricoCollection().add(pessoaFisicaFisicaHistorico);
                pessoaFisicaAFk = em.merge(pessoaFisicaAFk);
            }
            if (pessoaFisicaBFk != null) {
                pessoaFisicaBFk.getPessoaFisicaFisicaHistoricoCollection().add(pessoaFisicaFisicaHistorico);
                pessoaFisicaBFk = em.merge(pessoaFisicaBFk);
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
            PessoaFisica pessoaFisicaAFkOld = persistentPessoaFisicaFisicaHistorico.getPessoaFisicaAFk();
            PessoaFisica pessoaFisicaAFkNew = pessoaFisicaFisicaHistorico.getPessoaFisicaAFk();
            PessoaFisica pessoaFisicaBFkOld = persistentPessoaFisicaFisicaHistorico.getPessoaFisicaBFk();
            PessoaFisica pessoaFisicaBFkNew = pessoaFisicaFisicaHistorico.getPessoaFisicaBFk();
            PessoaFisicaHistorico pessoaFisicaHistoricoFkOld = persistentPessoaFisicaFisicaHistorico.getPessoaFisicaHistoricoFk();
            PessoaFisicaHistorico pessoaFisicaHistoricoFkNew = pessoaFisicaFisicaHistorico.getPessoaFisicaHistoricoFk();
            VinculoSocial vinculoSocialFkOld = persistentPessoaFisicaFisicaHistorico.getVinculoSocialFk();
            VinculoSocial vinculoSocialFkNew = pessoaFisicaFisicaHistorico.getVinculoSocialFk();
            if (pessoaFisicaAFkNew != null) {
                pessoaFisicaAFkNew = em.getReference(pessoaFisicaAFkNew.getClass(), pessoaFisicaAFkNew.getId());
                pessoaFisicaFisicaHistorico.setPessoaFisicaAFk(pessoaFisicaAFkNew);
            }
            if (pessoaFisicaBFkNew != null) {
                pessoaFisicaBFkNew = em.getReference(pessoaFisicaBFkNew.getClass(), pessoaFisicaBFkNew.getId());
                pessoaFisicaFisicaHistorico.setPessoaFisicaBFk(pessoaFisicaBFkNew);
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
            if (pessoaFisicaAFkOld != null && !pessoaFisicaAFkOld.equals(pessoaFisicaAFkNew)) {
                pessoaFisicaAFkOld.getPessoaFisicaFisicaHistoricoCollection().remove(pessoaFisicaFisicaHistorico);
                pessoaFisicaAFkOld = em.merge(pessoaFisicaAFkOld);
            }
            if (pessoaFisicaAFkNew != null && !pessoaFisicaAFkNew.equals(pessoaFisicaAFkOld)) {
                pessoaFisicaAFkNew.getPessoaFisicaFisicaHistoricoCollection().add(pessoaFisicaFisicaHistorico);
                pessoaFisicaAFkNew = em.merge(pessoaFisicaAFkNew);
            }
            if (pessoaFisicaBFkOld != null && !pessoaFisicaBFkOld.equals(pessoaFisicaBFkNew)) {
                pessoaFisicaBFkOld.getPessoaFisicaFisicaHistoricoCollection().remove(pessoaFisicaFisicaHistorico);
                pessoaFisicaBFkOld = em.merge(pessoaFisicaBFkOld);
            }
            if (pessoaFisicaBFkNew != null && !pessoaFisicaBFkNew.equals(pessoaFisicaBFkOld)) {
                pessoaFisicaBFkNew.getPessoaFisicaFisicaHistoricoCollection().add(pessoaFisicaFisicaHistorico);
                pessoaFisicaBFkNew = em.merge(pessoaFisicaBFkNew);
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
            PessoaFisica pessoaFisicaAFk = pessoaFisicaFisicaHistorico.getPessoaFisicaAFk();
            if (pessoaFisicaAFk != null) {
                pessoaFisicaAFk.getPessoaFisicaFisicaHistoricoCollection().remove(pessoaFisicaFisicaHistorico);
                pessoaFisicaAFk = em.merge(pessoaFisicaAFk);
            }
            PessoaFisica pessoaFisicaBFk = pessoaFisicaFisicaHistorico.getPessoaFisicaBFk();
            if (pessoaFisicaBFk != null) {
                pessoaFisicaBFk.getPessoaFisicaFisicaHistoricoCollection().remove(pessoaFisicaFisicaHistorico);
                pessoaFisicaBFk = em.merge(pessoaFisicaBFk);
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
