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
import entidade.PessoaJuridica;
import entidade.PessoaJuridicaSucessao;
import entidade.PessoaJuridicaSucessaoHistorico;
import entidade.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.transaction.UserTransaction;

/**
 *
 * @author paulones
 */
public class PessoaJuridicaSucessaoHistoricoDAO implements Serializable {

    public PessoaJuridicaSucessaoHistoricoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PessoaJuridicaSucessaoHistorico pessoaJuridicaSucessaoHistorico) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            PessoaJuridica pessoaJuridicaSucedidaFk = pessoaJuridicaSucessaoHistorico.getPessoaJuridicaSucedidaFk();
            if (pessoaJuridicaSucedidaFk != null) {
                pessoaJuridicaSucedidaFk = em.getReference(pessoaJuridicaSucedidaFk.getClass(), pessoaJuridicaSucedidaFk.getId());
                pessoaJuridicaSucessaoHistorico.setPessoaJuridicaSucedidaFk(pessoaJuridicaSucedidaFk);
            }
            PessoaJuridica pessoaJuridicaSucessoraFk = pessoaJuridicaSucessaoHistorico.getPessoaJuridicaSucessoraFk();
            if (pessoaJuridicaSucessoraFk != null) {
                pessoaJuridicaSucessoraFk = em.getReference(pessoaJuridicaSucessoraFk.getClass(), pessoaJuridicaSucessoraFk.getId());
                pessoaJuridicaSucessaoHistorico.setPessoaJuridicaSucessoraFk(pessoaJuridicaSucessoraFk);
            }
            PessoaJuridicaSucessao pessoaJuridicaSucessaoFk = pessoaJuridicaSucessaoHistorico.getPessoaJuridicaSucessaoFk();
            if (pessoaJuridicaSucessaoFk != null) {
                pessoaJuridicaSucessaoFk = em.getReference(pessoaJuridicaSucessaoFk.getClass(), pessoaJuridicaSucessaoFk.getId());
                pessoaJuridicaSucessaoHistorico.setPessoaJuridicaSucessaoFk(pessoaJuridicaSucessaoFk);
            }
            Usuario usuarioFk = pessoaJuridicaSucessaoHistorico.getUsuarioFk();
            if (usuarioFk != null) {
                usuarioFk = em.getReference(usuarioFk.getClass(), usuarioFk.getId());
                pessoaJuridicaSucessaoHistorico.setUsuarioFk(usuarioFk);
            }
            em.persist(pessoaJuridicaSucessaoHistorico);
            if (pessoaJuridicaSucedidaFk != null) {
                pessoaJuridicaSucedidaFk.getPessoaJuridicaSucessaoHistoricoCollection().add(pessoaJuridicaSucessaoHistorico);
                pessoaJuridicaSucedidaFk = em.merge(pessoaJuridicaSucedidaFk);
            }
            if (pessoaJuridicaSucessoraFk != null) {
                pessoaJuridicaSucessoraFk.getPessoaJuridicaSucessaoHistoricoCollection().add(pessoaJuridicaSucessaoHistorico);
                pessoaJuridicaSucessoraFk = em.merge(pessoaJuridicaSucessoraFk);
            }
            if (pessoaJuridicaSucessaoFk != null) {
                pessoaJuridicaSucessaoFk.getPessoaJuridicaSucessaoHistoricoCollection().add(pessoaJuridicaSucessaoHistorico);
                pessoaJuridicaSucessaoFk = em.merge(pessoaJuridicaSucessaoFk);
            }
            if (usuarioFk != null) {
                usuarioFk.getPessoaJuridicaSucessaoHistoricoCollection().add(pessoaJuridicaSucessaoHistorico);
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

    public void edit(PessoaJuridicaSucessaoHistorico pessoaJuridicaSucessaoHistorico) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            PessoaJuridicaSucessaoHistorico persistentPessoaJuridicaSucessaoHistorico = em.find(PessoaJuridicaSucessaoHistorico.class, pessoaJuridicaSucessaoHistorico.getId());
            PessoaJuridica pessoaJuridicaSucedidaFkOld = persistentPessoaJuridicaSucessaoHistorico.getPessoaJuridicaSucedidaFk();
            PessoaJuridica pessoaJuridicaSucedidaFkNew = pessoaJuridicaSucessaoHistorico.getPessoaJuridicaSucedidaFk();
            PessoaJuridica pessoaJuridicaSucessoraFkOld = persistentPessoaJuridicaSucessaoHistorico.getPessoaJuridicaSucessoraFk();
            PessoaJuridica pessoaJuridicaSucessoraFkNew = pessoaJuridicaSucessaoHistorico.getPessoaJuridicaSucessoraFk();
            PessoaJuridicaSucessao pessoaJuridicaSucessaoFkOld = persistentPessoaJuridicaSucessaoHistorico.getPessoaJuridicaSucessaoFk();
            PessoaJuridicaSucessao pessoaJuridicaSucessaoFkNew = pessoaJuridicaSucessaoHistorico.getPessoaJuridicaSucessaoFk();
            Usuario usuarioFkOld = persistentPessoaJuridicaSucessaoHistorico.getUsuarioFk();
            Usuario usuarioFkNew = pessoaJuridicaSucessaoHistorico.getUsuarioFk();
            if (pessoaJuridicaSucedidaFkNew != null) {
                pessoaJuridicaSucedidaFkNew = em.getReference(pessoaJuridicaSucedidaFkNew.getClass(), pessoaJuridicaSucedidaFkNew.getId());
                pessoaJuridicaSucessaoHistorico.setPessoaJuridicaSucedidaFk(pessoaJuridicaSucedidaFkNew);
            }
            if (pessoaJuridicaSucessoraFkNew != null) {
                pessoaJuridicaSucessoraFkNew = em.getReference(pessoaJuridicaSucessoraFkNew.getClass(), pessoaJuridicaSucessoraFkNew.getId());
                pessoaJuridicaSucessaoHistorico.setPessoaJuridicaSucessoraFk(pessoaJuridicaSucessoraFkNew);
            }
            if (pessoaJuridicaSucessaoFkNew != null) {
                pessoaJuridicaSucessaoFkNew = em.getReference(pessoaJuridicaSucessaoFkNew.getClass(), pessoaJuridicaSucessaoFkNew.getId());
                pessoaJuridicaSucessaoHistorico.setPessoaJuridicaSucessaoFk(pessoaJuridicaSucessaoFkNew);
            }
            if (usuarioFkNew != null) {
                usuarioFkNew = em.getReference(usuarioFkNew.getClass(), usuarioFkNew.getId());
                pessoaJuridicaSucessaoHistorico.setUsuarioFk(usuarioFkNew);
            }
            pessoaJuridicaSucessaoHistorico = em.merge(pessoaJuridicaSucessaoHistorico);
            if (pessoaJuridicaSucedidaFkOld != null && !pessoaJuridicaSucedidaFkOld.equals(pessoaJuridicaSucedidaFkNew)) {
                pessoaJuridicaSucedidaFkOld.getPessoaJuridicaSucessaoHistoricoCollection().remove(pessoaJuridicaSucessaoHistorico);
                pessoaJuridicaSucedidaFkOld = em.merge(pessoaJuridicaSucedidaFkOld);
            }
            if (pessoaJuridicaSucedidaFkNew != null && !pessoaJuridicaSucedidaFkNew.equals(pessoaJuridicaSucedidaFkOld)) {
                pessoaJuridicaSucedidaFkNew.getPessoaJuridicaSucessaoHistoricoCollection().add(pessoaJuridicaSucessaoHistorico);
                pessoaJuridicaSucedidaFkNew = em.merge(pessoaJuridicaSucedidaFkNew);
            }
            if (pessoaJuridicaSucessoraFkOld != null && !pessoaJuridicaSucessoraFkOld.equals(pessoaJuridicaSucessoraFkNew)) {
                pessoaJuridicaSucessoraFkOld.getPessoaJuridicaSucessaoHistoricoCollection().remove(pessoaJuridicaSucessaoHistorico);
                pessoaJuridicaSucessoraFkOld = em.merge(pessoaJuridicaSucessoraFkOld);
            }
            if (pessoaJuridicaSucessoraFkNew != null && !pessoaJuridicaSucessoraFkNew.equals(pessoaJuridicaSucessoraFkOld)) {
                pessoaJuridicaSucessoraFkNew.getPessoaJuridicaSucessaoHistoricoCollection().add(pessoaJuridicaSucessaoHistorico);
                pessoaJuridicaSucessoraFkNew = em.merge(pessoaJuridicaSucessoraFkNew);
            }
            if (pessoaJuridicaSucessaoFkOld != null && !pessoaJuridicaSucessaoFkOld.equals(pessoaJuridicaSucessaoFkNew)) {
                pessoaJuridicaSucessaoFkOld.getPessoaJuridicaSucessaoHistoricoCollection().remove(pessoaJuridicaSucessaoHistorico);
                pessoaJuridicaSucessaoFkOld = em.merge(pessoaJuridicaSucessaoFkOld);
            }
            if (pessoaJuridicaSucessaoFkNew != null && !pessoaJuridicaSucessaoFkNew.equals(pessoaJuridicaSucessaoFkOld)) {
                pessoaJuridicaSucessaoFkNew.getPessoaJuridicaSucessaoHistoricoCollection().add(pessoaJuridicaSucessaoHistorico);
                pessoaJuridicaSucessaoFkNew = em.merge(pessoaJuridicaSucessaoFkNew);
            }
            if (usuarioFkOld != null && !usuarioFkOld.equals(usuarioFkNew)) {
                usuarioFkOld.getPessoaJuridicaSucessaoHistoricoCollection().remove(pessoaJuridicaSucessaoHistorico);
                usuarioFkOld = em.merge(usuarioFkOld);
            }
            if (usuarioFkNew != null && !usuarioFkNew.equals(usuarioFkOld)) {
                usuarioFkNew.getPessoaJuridicaSucessaoHistoricoCollection().add(pessoaJuridicaSucessaoHistorico);
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
                Integer id = pessoaJuridicaSucessaoHistorico.getId();
                if (findPessoaJuridicaSucessaoHistorico(id) == null) {
                    throw new NonexistentEntityException("The pessoaJuridicaSucessaoHistorico with id " + id + " no longer exists.");
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
            PessoaJuridicaSucessaoHistorico pessoaJuridicaSucessaoHistorico;
            try {
                pessoaJuridicaSucessaoHistorico = em.getReference(PessoaJuridicaSucessaoHistorico.class, id);
                pessoaJuridicaSucessaoHistorico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pessoaJuridicaSucessaoHistorico with id " + id + " no longer exists.", enfe);
            }
            PessoaJuridica pessoaJuridicaSucedidaFk = pessoaJuridicaSucessaoHistorico.getPessoaJuridicaSucedidaFk();
            if (pessoaJuridicaSucedidaFk != null) {
                pessoaJuridicaSucedidaFk.getPessoaJuridicaSucessaoHistoricoCollection().remove(pessoaJuridicaSucessaoHistorico);
                pessoaJuridicaSucedidaFk = em.merge(pessoaJuridicaSucedidaFk);
            }
            PessoaJuridica pessoaJuridicaSucessoraFk = pessoaJuridicaSucessaoHistorico.getPessoaJuridicaSucessoraFk();
            if (pessoaJuridicaSucessoraFk != null) {
                pessoaJuridicaSucessoraFk.getPessoaJuridicaSucessaoHistoricoCollection().remove(pessoaJuridicaSucessaoHistorico);
                pessoaJuridicaSucessoraFk = em.merge(pessoaJuridicaSucessoraFk);
            }
            PessoaJuridicaSucessao pessoaJuridicaSucessaoFk = pessoaJuridicaSucessaoHistorico.getPessoaJuridicaSucessaoFk();
            if (pessoaJuridicaSucessaoFk != null) {
                pessoaJuridicaSucessaoFk.getPessoaJuridicaSucessaoHistoricoCollection().remove(pessoaJuridicaSucessaoHistorico);
                pessoaJuridicaSucessaoFk = em.merge(pessoaJuridicaSucessaoFk);
            }
            Usuario usuarioFk = pessoaJuridicaSucessaoHistorico.getUsuarioFk();
            if (usuarioFk != null) {
                usuarioFk.getPessoaJuridicaSucessaoHistoricoCollection().remove(pessoaJuridicaSucessaoHistorico);
                usuarioFk = em.merge(usuarioFk);
            }
            em.remove(pessoaJuridicaSucessaoHistorico);
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

    public List<PessoaJuridicaSucessaoHistorico> findPessoaJuridicaSucessaoHistoricoEntities() {
        return findPessoaJuridicaSucessaoHistoricoEntities(true, -1, -1);
    }

    public List<PessoaJuridicaSucessaoHistorico> findPessoaJuridicaSucessaoHistoricoEntities(int maxResults, int firstResult) {
        return findPessoaJuridicaSucessaoHistoricoEntities(false, maxResults, firstResult);
    }

    private List<PessoaJuridicaSucessaoHistorico> findPessoaJuridicaSucessaoHistoricoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PessoaJuridicaSucessaoHistorico.class));
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

    public PessoaJuridicaSucessaoHistorico findPessoaJuridicaSucessaoHistorico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PessoaJuridicaSucessaoHistorico.class, id);
        } finally {
            em.close();
        }
    }

    public int getPessoaJuridicaSucessaoHistoricoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PessoaJuridicaSucessaoHistorico> rt = cq.from(PessoaJuridicaSucessaoHistorico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<PessoaJuridicaSucessaoHistorico> findByPJS(Integer id){
        EntityManager em = getEntityManager();
        try {
            List<PessoaJuridicaSucessaoHistorico> pessoaJuridicaSucessaoList = (List<PessoaJuridicaSucessaoHistorico>) em.createNativeQuery("select * from pessoa_juridica_sucessao_historico "
                        + "where pessoa_juridica_sucessao_fk = '" + id + "' order by data_de_modificacao desc", PessoaJuridicaSucessaoHistorico.class).getResultList();
            return pessoaJuridicaSucessaoList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
}
