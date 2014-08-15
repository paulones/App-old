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
import entidade.TipoEmpresarial;
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
public class PessoaJuridicaHistoricoDAO implements Serializable {

    public PessoaJuridicaHistoricoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PessoaJuridicaHistorico pessoaJuridicaHistorico) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            PessoaJuridica pessoaJuridicaFk = pessoaJuridicaHistorico.getPessoaJuridicaFk();
            if (pessoaJuridicaFk != null) {
                pessoaJuridicaFk = em.getReference(pessoaJuridicaFk.getClass(), pessoaJuridicaFk.getId());
                pessoaJuridicaHistorico.setPessoaJuridicaFk(pessoaJuridicaFk);
            }
            PessoaJuridica sucessaoFk = pessoaJuridicaHistorico.getSucessaoFk();
            if (sucessaoFk != null) {
                sucessaoFk = em.getReference(sucessaoFk.getClass(), sucessaoFk.getId());
                pessoaJuridicaHistorico.setSucessaoFk(sucessaoFk);
            }
            TipoEmpresarial tipoEmpresarialFk = pessoaJuridicaHistorico.getTipoEmpresarialFk();
            if (tipoEmpresarialFk != null) {
                tipoEmpresarialFk = em.getReference(tipoEmpresarialFk.getClass(), tipoEmpresarialFk.getId());
                pessoaJuridicaHistorico.setTipoEmpresarialFk(tipoEmpresarialFk);
            }
            Usuario usuarioFk = pessoaJuridicaHistorico.getUsuarioFk();
            if (usuarioFk != null) {
                usuarioFk = em.getReference(usuarioFk.getClass(), usuarioFk.getId());
                pessoaJuridicaHistorico.setUsuarioFk(usuarioFk);
            }
            em.persist(pessoaJuridicaHistorico);
            if (pessoaJuridicaFk != null) {
                pessoaJuridicaFk.getPessoaJuridicaHistoricoCollection().add(pessoaJuridicaHistorico);
                pessoaJuridicaFk = em.merge(pessoaJuridicaFk);
            }
            if (sucessaoFk != null) {
                sucessaoFk.getPessoaJuridicaHistoricoCollection().add(pessoaJuridicaHistorico);
                sucessaoFk = em.merge(sucessaoFk);
            }
            if (tipoEmpresarialFk != null) {
                tipoEmpresarialFk.getPessoaJuridicaHistoricoCollection().add(pessoaJuridicaHistorico);
                tipoEmpresarialFk = em.merge(tipoEmpresarialFk);
            }
            if (usuarioFk != null) {
                usuarioFk.getPessoaJuridicaHistoricoCollection().add(pessoaJuridicaHistorico);
                usuarioFk = em.merge(usuarioFk);
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

    public void edit(PessoaJuridicaHistorico pessoaJuridicaHistorico) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            PessoaJuridicaHistorico persistentPessoaJuridicaHistorico = em.find(PessoaJuridicaHistorico.class, pessoaJuridicaHistorico.getId());
            PessoaJuridica pessoaJuridicaFkOld = persistentPessoaJuridicaHistorico.getPessoaJuridicaFk();
            PessoaJuridica pessoaJuridicaFkNew = pessoaJuridicaHistorico.getPessoaJuridicaFk();
            PessoaJuridica sucessaoFkOld = persistentPessoaJuridicaHistorico.getSucessaoFk();
            PessoaJuridica sucessaoFkNew = pessoaJuridicaHistorico.getSucessaoFk();
            TipoEmpresarial tipoEmpresarialFkOld = persistentPessoaJuridicaHistorico.getTipoEmpresarialFk();
            TipoEmpresarial tipoEmpresarialFkNew = pessoaJuridicaHistorico.getTipoEmpresarialFk();
            Usuario usuarioFkOld = persistentPessoaJuridicaHistorico.getUsuarioFk();
            Usuario usuarioFkNew = pessoaJuridicaHistorico.getUsuarioFk();
            if (pessoaJuridicaFkNew != null) {
                pessoaJuridicaFkNew = em.getReference(pessoaJuridicaFkNew.getClass(), pessoaJuridicaFkNew.getId());
                pessoaJuridicaHistorico.setPessoaJuridicaFk(pessoaJuridicaFkNew);
            }
            if (sucessaoFkNew != null) {
                sucessaoFkNew = em.getReference(sucessaoFkNew.getClass(), sucessaoFkNew.getId());
                pessoaJuridicaHistorico.setSucessaoFk(sucessaoFkNew);
            }
            if (tipoEmpresarialFkNew != null) {
                tipoEmpresarialFkNew = em.getReference(tipoEmpresarialFkNew.getClass(), tipoEmpresarialFkNew.getId());
                pessoaJuridicaHistorico.setTipoEmpresarialFk(tipoEmpresarialFkNew);
            }
            if (usuarioFkNew != null) {
                usuarioFkNew = em.getReference(usuarioFkNew.getClass(), usuarioFkNew.getId());
                pessoaJuridicaHistorico.setUsuarioFk(usuarioFkNew);
            }
            pessoaJuridicaHistorico = em.merge(pessoaJuridicaHistorico);
            if (pessoaJuridicaFkOld != null && !pessoaJuridicaFkOld.equals(pessoaJuridicaFkNew)) {
                pessoaJuridicaFkOld.getPessoaJuridicaHistoricoCollection().remove(pessoaJuridicaHistorico);
                pessoaJuridicaFkOld = em.merge(pessoaJuridicaFkOld);
            }
            if (pessoaJuridicaFkNew != null && !pessoaJuridicaFkNew.equals(pessoaJuridicaFkOld)) {
                pessoaJuridicaFkNew.getPessoaJuridicaHistoricoCollection().add(pessoaJuridicaHistorico);
                pessoaJuridicaFkNew = em.merge(pessoaJuridicaFkNew);
            }
            if (sucessaoFkOld != null && !sucessaoFkOld.equals(sucessaoFkNew)) {
                sucessaoFkOld.getPessoaJuridicaHistoricoCollection().remove(pessoaJuridicaHistorico);
                sucessaoFkOld = em.merge(sucessaoFkOld);
            }
            if (sucessaoFkNew != null && !sucessaoFkNew.equals(sucessaoFkOld)) {
                sucessaoFkNew.getPessoaJuridicaHistoricoCollection().add(pessoaJuridicaHistorico);
                sucessaoFkNew = em.merge(sucessaoFkNew);
            }
            if (tipoEmpresarialFkOld != null && !tipoEmpresarialFkOld.equals(tipoEmpresarialFkNew)) {
                tipoEmpresarialFkOld.getPessoaJuridicaHistoricoCollection().remove(pessoaJuridicaHistorico);
                tipoEmpresarialFkOld = em.merge(tipoEmpresarialFkOld);
            }
            if (tipoEmpresarialFkNew != null && !tipoEmpresarialFkNew.equals(tipoEmpresarialFkOld)) {
                tipoEmpresarialFkNew.getPessoaJuridicaHistoricoCollection().add(pessoaJuridicaHistorico);
                tipoEmpresarialFkNew = em.merge(tipoEmpresarialFkNew);
            }
            if (usuarioFkOld != null && !usuarioFkOld.equals(usuarioFkNew)) {
                usuarioFkOld.getPessoaJuridicaHistoricoCollection().remove(pessoaJuridicaHistorico);
                usuarioFkOld = em.merge(usuarioFkOld);
            }
            if (usuarioFkNew != null && !usuarioFkNew.equals(usuarioFkOld)) {
                usuarioFkNew.getPessoaJuridicaHistoricoCollection().add(pessoaJuridicaHistorico);
                usuarioFkNew = em.merge(usuarioFkNew);
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
                Integer id = pessoaJuridicaHistorico.getId();
                if (findPessoaJuridicaHistorico(id) == null) {
                    throw new NonexistentEntityException("The pessoaJuridicaHistorico with id " + id + " no longer exists.");
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
            PessoaJuridicaHistorico pessoaJuridicaHistorico;
            try {
                pessoaJuridicaHistorico = em.getReference(PessoaJuridicaHistorico.class, id);
                pessoaJuridicaHistorico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pessoaJuridicaHistorico with id " + id + " no longer exists.", enfe);
            }
            PessoaJuridica pessoaJuridicaFk = pessoaJuridicaHistorico.getPessoaJuridicaFk();
            if (pessoaJuridicaFk != null) {
                pessoaJuridicaFk.getPessoaJuridicaHistoricoCollection().remove(pessoaJuridicaHistorico);
                pessoaJuridicaFk = em.merge(pessoaJuridicaFk);
            }
            PessoaJuridica sucessaoFk = pessoaJuridicaHistorico.getSucessaoFk();
            if (sucessaoFk != null) {
                sucessaoFk.getPessoaJuridicaHistoricoCollection().remove(pessoaJuridicaHistorico);
                sucessaoFk = em.merge(sucessaoFk);
            }
            TipoEmpresarial tipoEmpresarialFk = pessoaJuridicaHistorico.getTipoEmpresarialFk();
            if (tipoEmpresarialFk != null) {
                tipoEmpresarialFk.getPessoaJuridicaHistoricoCollection().remove(pessoaJuridicaHistorico);
                tipoEmpresarialFk = em.merge(tipoEmpresarialFk);
            }
            Usuario usuarioFk = pessoaJuridicaHistorico.getUsuarioFk();
            if (usuarioFk != null) {
                usuarioFk.getPessoaJuridicaHistoricoCollection().remove(pessoaJuridicaHistorico);
                usuarioFk = em.merge(usuarioFk);
            }
            em.remove(pessoaJuridicaHistorico);
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

    public List<PessoaJuridicaHistorico> findPessoaJuridicaHistoricoEntities() {
        return findPessoaJuridicaHistoricoEntities(true, -1, -1);
    }

    public List<PessoaJuridicaHistorico> findPessoaJuridicaHistoricoEntities(int maxResults, int firstResult) {
        return findPessoaJuridicaHistoricoEntities(false, maxResults, firstResult);
    }

    private List<PessoaJuridicaHistorico> findPessoaJuridicaHistoricoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PessoaJuridicaHistorico.class));
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

    public PessoaJuridicaHistorico findPessoaJuridicaHistorico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PessoaJuridicaHistorico.class, id);
        } finally {
            em.close();
        }
    }

    public int getPessoaJuridicaHistoricoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PessoaJuridicaHistorico> rt = cq.from(PessoaJuridicaHistorico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<PessoaJuridicaHistorico> findAllByPJ(Integer id){
        EntityManager em = getEntityManager();
        try {
            List<PessoaJuridicaHistorico> pessoaFisicaHistoricoList = (List<PessoaJuridicaHistorico>) em.createNativeQuery("select * from pessoa_juridica_historico "
                        + "where pessoa_juridica_fk = '"+id+"' order by data_de_modificacao desc", PessoaJuridicaHistorico.class).getResultList();
            return pessoaFisicaHistoricoList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
}
