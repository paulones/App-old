/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.Funcao;
import entidade.PessoaFisica;
import entidade.PessoaFisicaJuridica;
import entidade.PessoaFisicaJuridicaHistorico;
import entidade.PessoaJuridica;
import entidade.Usuario;
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
public class PessoaFisicaJuridicaHistoricoDAO implements Serializable {

    public PessoaFisicaJuridicaHistoricoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistorico) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            Funcao funcaoFk = pessoaFisicaJuridicaHistorico.getFuncaoFk();
            if (funcaoFk != null) {
                funcaoFk = em.getReference(funcaoFk.getClass(), funcaoFk.getId());
                pessoaFisicaJuridicaHistorico.setFuncaoFk(funcaoFk);
            }
            PessoaFisica pessoaFisicaFk = pessoaFisicaJuridicaHistorico.getPessoaFisicaFk();
            if (pessoaFisicaFk != null) {
                pessoaFisicaFk = em.getReference(pessoaFisicaFk.getClass(), pessoaFisicaFk.getId());
                pessoaFisicaJuridicaHistorico.setPessoaFisicaFk(pessoaFisicaFk);
            }
            PessoaJuridica pessoaJuridicaFk = pessoaFisicaJuridicaHistorico.getPessoaJuridicaFk();
            if (pessoaJuridicaFk != null) {
                pessoaJuridicaFk = em.getReference(pessoaJuridicaFk.getClass(), pessoaJuridicaFk.getId());
                pessoaFisicaJuridicaHistorico.setPessoaJuridicaFk(pessoaJuridicaFk);
            }
            em.persist(pessoaFisicaJuridicaHistorico);
            if (funcaoFk != null) {
                funcaoFk.getPessoaFisicaJuridicaHistoricoCollection().add(pessoaFisicaJuridicaHistorico);
                funcaoFk = em.merge(funcaoFk);
            }
            if (pessoaFisicaFk != null) {
                pessoaFisicaFk.getPessoaFisicaJuridicaHistoricoCollection().add(pessoaFisicaJuridicaHistorico);
                pessoaFisicaFk = em.merge(pessoaFisicaFk);
            }
            if (pessoaJuridicaFk != null) {
                pessoaJuridicaFk.getPessoaFisicaJuridicaHistoricoCollection().add(pessoaFisicaJuridicaHistorico);
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

    public void edit(PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistorico) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            PessoaFisicaJuridicaHistorico persistentPessoaFisicaJuridicaHistorico = em.find(PessoaFisicaJuridicaHistorico.class, pessoaFisicaJuridicaHistorico.getId());
            Funcao funcaoFkOld = persistentPessoaFisicaJuridicaHistorico.getFuncaoFk();
            Funcao funcaoFkNew = pessoaFisicaJuridicaHistorico.getFuncaoFk();
            PessoaFisica pessoaFisicaFkOld = persistentPessoaFisicaJuridicaHistorico.getPessoaFisicaFk();
            PessoaFisica pessoaFisicaFkNew = pessoaFisicaJuridicaHistorico.getPessoaFisicaFk();
            PessoaJuridica pessoaJuridicaFkOld = persistentPessoaFisicaJuridicaHistorico.getPessoaJuridicaFk();
            PessoaJuridica pessoaJuridicaFkNew = pessoaFisicaJuridicaHistorico.getPessoaJuridicaFk();
            if (funcaoFkNew != null) {
                funcaoFkNew = em.getReference(funcaoFkNew.getClass(), funcaoFkNew.getId());
                pessoaFisicaJuridicaHistorico.setFuncaoFk(funcaoFkNew);
            }
            if (pessoaFisicaFkNew != null) {
                pessoaFisicaFkNew = em.getReference(pessoaFisicaFkNew.getClass(), pessoaFisicaFkNew.getId());
                pessoaFisicaJuridicaHistorico.setPessoaFisicaFk(pessoaFisicaFkNew);
            }
            if (pessoaJuridicaFkNew != null) {
                pessoaJuridicaFkNew = em.getReference(pessoaJuridicaFkNew.getClass(), pessoaJuridicaFkNew.getId());
                pessoaFisicaJuridicaHistorico.setPessoaJuridicaFk(pessoaJuridicaFkNew);
            }
            pessoaFisicaJuridicaHistorico = em.merge(pessoaFisicaJuridicaHistorico);
            if (funcaoFkOld != null && !funcaoFkOld.equals(funcaoFkNew)) {
                funcaoFkOld.getPessoaFisicaJuridicaHistoricoCollection().remove(pessoaFisicaJuridicaHistorico);
                funcaoFkOld = em.merge(funcaoFkOld);
            }
            if (funcaoFkNew != null && !funcaoFkNew.equals(funcaoFkOld)) {
                funcaoFkNew.getPessoaFisicaJuridicaHistoricoCollection().add(pessoaFisicaJuridicaHistorico);
                funcaoFkNew = em.merge(funcaoFkNew);
            }
            if (pessoaFisicaFkOld != null && !pessoaFisicaFkOld.equals(pessoaFisicaFkNew)) {
                pessoaFisicaFkOld.getPessoaFisicaJuridicaHistoricoCollection().remove(pessoaFisicaJuridicaHistorico);
                pessoaFisicaFkOld = em.merge(pessoaFisicaFkOld);
            }
            if (pessoaFisicaFkNew != null && !pessoaFisicaFkNew.equals(pessoaFisicaFkOld)) {
                pessoaFisicaFkNew.getPessoaFisicaJuridicaHistoricoCollection().add(pessoaFisicaJuridicaHistorico);
                pessoaFisicaFkNew = em.merge(pessoaFisicaFkNew);
            }
            if (pessoaJuridicaFkOld != null && !pessoaJuridicaFkOld.equals(pessoaJuridicaFkNew)) {
                pessoaJuridicaFkOld.getPessoaFisicaJuridicaHistoricoCollection().remove(pessoaFisicaJuridicaHistorico);
                pessoaJuridicaFkOld = em.merge(pessoaJuridicaFkOld);
            }
            if (pessoaJuridicaFkNew != null && !pessoaJuridicaFkNew.equals(pessoaJuridicaFkOld)) {
                pessoaJuridicaFkNew.getPessoaFisicaJuridicaHistoricoCollection().add(pessoaFisicaJuridicaHistorico);
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
                Integer id = pessoaFisicaJuridicaHistorico.getId();
                if (findPessoaFisicaJuridicaHistorico(id) == null) {
                    throw new NonexistentEntityException("The pessoaFisicaJuridicaHistorico with id " + id + " no longer exists.");
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
            PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistorico;
            try {
                pessoaFisicaJuridicaHistorico = em.getReference(PessoaFisicaJuridicaHistorico.class, id);
                pessoaFisicaJuridicaHistorico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pessoaFisicaJuridicaHistorico with id " + id + " no longer exists.", enfe);
            }
            Funcao funcaoFk = pessoaFisicaJuridicaHistorico.getFuncaoFk();
            if (funcaoFk != null) {
                funcaoFk.getPessoaFisicaJuridicaHistoricoCollection().remove(pessoaFisicaJuridicaHistorico);
                funcaoFk = em.merge(funcaoFk);
            }
            PessoaFisica pessoaFisicaFk = pessoaFisicaJuridicaHistorico.getPessoaFisicaFk();
            if (pessoaFisicaFk != null) {
                pessoaFisicaFk.getPessoaFisicaJuridicaHistoricoCollection().remove(pessoaFisicaJuridicaHistorico);
                pessoaFisicaFk = em.merge(pessoaFisicaFk);
            }
            PessoaJuridica pessoaJuridicaFk = pessoaFisicaJuridicaHistorico.getPessoaJuridicaFk();
            if (pessoaJuridicaFk != null) {
                pessoaJuridicaFk.getPessoaFisicaJuridicaHistoricoCollection().remove(pessoaFisicaJuridicaHistorico);
                pessoaJuridicaFk = em.merge(pessoaJuridicaFk);
            }
            em.remove(pessoaFisicaJuridicaHistorico);
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

    public List<PessoaFisicaJuridicaHistorico> findPessoaFisicaJuridicaHistoricoEntities() {
        return findPessoaFisicaJuridicaHistoricoEntities(true, -1, -1);
    }

    public List<PessoaFisicaJuridicaHistorico> findPessoaFisicaJuridicaHistoricoEntities(int maxResults, int firstResult) {
        return findPessoaFisicaJuridicaHistoricoEntities(false, maxResults, firstResult);
    }

    private List<PessoaFisicaJuridicaHistorico> findPessoaFisicaJuridicaHistoricoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PessoaFisicaJuridicaHistorico.class));
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

    public PessoaFisicaJuridicaHistorico findPessoaFisicaJuridicaHistorico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PessoaFisicaJuridicaHistorico.class, id);
        } finally {
            em.close();
        }
    }

    public int getPessoaFisicaJuridicaHistoricoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PessoaFisicaJuridicaHistorico> rt = cq.from(PessoaFisicaJuridicaHistorico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<PessoaFisicaJuridicaHistorico> findAllByPF(Integer id){
        EntityManager em = getEntityManager();
        try {
            List<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoList = (List<PessoaFisicaJuridicaHistorico>) em.createNativeQuery("select pfjh.* from pessoa_fisica_juridica_historico pfjh "
                        + "join pessoa_fisica_historico pfh on pfh.id = pfjh.id_fk where pfjh.tipo = 'PF' and pfh.pessoa_fisica_fk = '"+id+"'", PessoaFisicaJuridicaHistorico.class).getResultList();
            return pessoaFisicaJuridicaHistoricoList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
