/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.PessoaJuridica;
import entidade.PessoaJuridicaSucessao;
import entidade.PessoaJuridicaSucessaoHistorico;
import entidade.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
public class PessoaJuridicaSucessaoDAO implements Serializable {

    public PessoaJuridicaSucessaoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PessoaJuridicaSucessao pessoaJuridicaSucessao) throws RollbackFailureException, Exception {
        if (pessoaJuridicaSucessao.getPessoaJuridicaSucessaoHistoricoCollection() == null) {
            pessoaJuridicaSucessao.setPessoaJuridicaSucessaoHistoricoCollection(new ArrayList<PessoaJuridicaSucessaoHistorico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            PessoaJuridica pessoaJuridicaSucedidaFk = pessoaJuridicaSucessao.getPessoaJuridicaSucedidaFk();
            if (pessoaJuridicaSucedidaFk != null) {
                pessoaJuridicaSucedidaFk = em.getReference(pessoaJuridicaSucedidaFk.getClass(), pessoaJuridicaSucedidaFk.getId());
                pessoaJuridicaSucessao.setPessoaJuridicaSucedidaFk(pessoaJuridicaSucedidaFk);
            }
            PessoaJuridica pessoaJuridicaSucessoraFk = pessoaJuridicaSucessao.getPessoaJuridicaSucessoraFk();
            if (pessoaJuridicaSucessoraFk != null) {
                pessoaJuridicaSucessoraFk = em.getReference(pessoaJuridicaSucessoraFk.getClass(), pessoaJuridicaSucessoraFk.getId());
                pessoaJuridicaSucessao.setPessoaJuridicaSucessoraFk(pessoaJuridicaSucessoraFk);
            }
            Usuario usuarioFk = pessoaJuridicaSucessao.getUsuarioFk();
            if (usuarioFk != null) {
                usuarioFk = em.getReference(usuarioFk.getClass(), usuarioFk.getId());
                pessoaJuridicaSucessao.setUsuarioFk(usuarioFk);
            }
            Collection<PessoaJuridicaSucessaoHistorico> attachedPessoaJuridicaSucessaoHistoricoCollection = new ArrayList<PessoaJuridicaSucessaoHistorico>();
            for (PessoaJuridicaSucessaoHistorico pessoaJuridicaSucessaoHistoricoCollectionPessoaJuridicaSucessaoHistoricoToAttach : pessoaJuridicaSucessao.getPessoaJuridicaSucessaoHistoricoCollection()) {
                pessoaJuridicaSucessaoHistoricoCollectionPessoaJuridicaSucessaoHistoricoToAttach = em.getReference(pessoaJuridicaSucessaoHistoricoCollectionPessoaJuridicaSucessaoHistoricoToAttach.getClass(), pessoaJuridicaSucessaoHistoricoCollectionPessoaJuridicaSucessaoHistoricoToAttach.getId());
                attachedPessoaJuridicaSucessaoHistoricoCollection.add(pessoaJuridicaSucessaoHistoricoCollectionPessoaJuridicaSucessaoHistoricoToAttach);
            }
            pessoaJuridicaSucessao.setPessoaJuridicaSucessaoHistoricoCollection(attachedPessoaJuridicaSucessaoHistoricoCollection);
            em.persist(pessoaJuridicaSucessao);
            if (pessoaJuridicaSucedidaFk != null) {
                pessoaJuridicaSucedidaFk.getPessoaJuridicaSucessaoCollection().add(pessoaJuridicaSucessao);
                pessoaJuridicaSucedidaFk = em.merge(pessoaJuridicaSucedidaFk);
            }
            if (pessoaJuridicaSucessoraFk != null) {
                pessoaJuridicaSucessoraFk.getPessoaJuridicaSucessaoCollection().add(pessoaJuridicaSucessao);
                pessoaJuridicaSucessoraFk = em.merge(pessoaJuridicaSucessoraFk);
            }
            if (usuarioFk != null) {
                usuarioFk.getPessoaJuridicaSucessaoCollection().add(pessoaJuridicaSucessao);
                usuarioFk = em.merge(usuarioFk);
            }
            for (PessoaJuridicaSucessaoHistorico pessoaJuridicaSucessaoHistoricoCollectionPessoaJuridicaSucessaoHistorico : pessoaJuridicaSucessao.getPessoaJuridicaSucessaoHistoricoCollection()) {
                PessoaJuridicaSucessao oldPessoaJuridicaSucessaoFkOfPessoaJuridicaSucessaoHistoricoCollectionPessoaJuridicaSucessaoHistorico = pessoaJuridicaSucessaoHistoricoCollectionPessoaJuridicaSucessaoHistorico.getPessoaJuridicaSucessaoFk();
                pessoaJuridicaSucessaoHistoricoCollectionPessoaJuridicaSucessaoHistorico.setPessoaJuridicaSucessaoFk(pessoaJuridicaSucessao);
                pessoaJuridicaSucessaoHistoricoCollectionPessoaJuridicaSucessaoHistorico = em.merge(pessoaJuridicaSucessaoHistoricoCollectionPessoaJuridicaSucessaoHistorico);
                if (oldPessoaJuridicaSucessaoFkOfPessoaJuridicaSucessaoHistoricoCollectionPessoaJuridicaSucessaoHistorico != null) {
                    oldPessoaJuridicaSucessaoFkOfPessoaJuridicaSucessaoHistoricoCollectionPessoaJuridicaSucessaoHistorico.getPessoaJuridicaSucessaoHistoricoCollection().remove(pessoaJuridicaSucessaoHistoricoCollectionPessoaJuridicaSucessaoHistorico);
                    oldPessoaJuridicaSucessaoFkOfPessoaJuridicaSucessaoHistoricoCollectionPessoaJuridicaSucessaoHistorico = em.merge(oldPessoaJuridicaSucessaoFkOfPessoaJuridicaSucessaoHistoricoCollectionPessoaJuridicaSucessaoHistorico);
                }
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

    public void edit(PessoaJuridicaSucessao pessoaJuridicaSucessao) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            PessoaJuridicaSucessao persistentPessoaJuridicaSucessao = em.find(PessoaJuridicaSucessao.class, pessoaJuridicaSucessao.getId());
            PessoaJuridica pessoaJuridicaSucedidaFkOld = persistentPessoaJuridicaSucessao.getPessoaJuridicaSucedidaFk();
            PessoaJuridica pessoaJuridicaSucedidaFkNew = pessoaJuridicaSucessao.getPessoaJuridicaSucedidaFk();
            PessoaJuridica pessoaJuridicaSucessoraFkOld = persistentPessoaJuridicaSucessao.getPessoaJuridicaSucessoraFk();
            PessoaJuridica pessoaJuridicaSucessoraFkNew = pessoaJuridicaSucessao.getPessoaJuridicaSucessoraFk();
            Usuario usuarioFkOld = persistentPessoaJuridicaSucessao.getUsuarioFk();
            Usuario usuarioFkNew = pessoaJuridicaSucessao.getUsuarioFk();
            Collection<PessoaJuridicaSucessaoHistorico> pessoaJuridicaSucessaoHistoricoCollectionOld = persistentPessoaJuridicaSucessao.getPessoaJuridicaSucessaoHistoricoCollection();
            Collection<PessoaJuridicaSucessaoHistorico> pessoaJuridicaSucessaoHistoricoCollectionNew = pessoaJuridicaSucessao.getPessoaJuridicaSucessaoHistoricoCollection();
            List<String> illegalOrphanMessages = null;
            for (PessoaJuridicaSucessaoHistorico pessoaJuridicaSucessaoHistoricoCollectionOldPessoaJuridicaSucessaoHistorico : pessoaJuridicaSucessaoHistoricoCollectionOld) {
                if (!pessoaJuridicaSucessaoHistoricoCollectionNew.contains(pessoaJuridicaSucessaoHistoricoCollectionOldPessoaJuridicaSucessaoHistorico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PessoaJuridicaSucessaoHistorico " + pessoaJuridicaSucessaoHistoricoCollectionOldPessoaJuridicaSucessaoHistorico + " since its pessoaJuridicaSucessaoFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pessoaJuridicaSucedidaFkNew != null) {
                pessoaJuridicaSucedidaFkNew = em.getReference(pessoaJuridicaSucedidaFkNew.getClass(), pessoaJuridicaSucedidaFkNew.getId());
                pessoaJuridicaSucessao.setPessoaJuridicaSucedidaFk(pessoaJuridicaSucedidaFkNew);
            }
            if (pessoaJuridicaSucessoraFkNew != null) {
                pessoaJuridicaSucessoraFkNew = em.getReference(pessoaJuridicaSucessoraFkNew.getClass(), pessoaJuridicaSucessoraFkNew.getId());
                pessoaJuridicaSucessao.setPessoaJuridicaSucessoraFk(pessoaJuridicaSucessoraFkNew);
            }
            if (usuarioFkNew != null) {
                usuarioFkNew = em.getReference(usuarioFkNew.getClass(), usuarioFkNew.getId());
                pessoaJuridicaSucessao.setUsuarioFk(usuarioFkNew);
            }
            Collection<PessoaJuridicaSucessaoHistorico> attachedPessoaJuridicaSucessaoHistoricoCollectionNew = new ArrayList<PessoaJuridicaSucessaoHistorico>();
            for (PessoaJuridicaSucessaoHistorico pessoaJuridicaSucessaoHistoricoCollectionNewPessoaJuridicaSucessaoHistoricoToAttach : pessoaJuridicaSucessaoHistoricoCollectionNew) {
                pessoaJuridicaSucessaoHistoricoCollectionNewPessoaJuridicaSucessaoHistoricoToAttach = em.getReference(pessoaJuridicaSucessaoHistoricoCollectionNewPessoaJuridicaSucessaoHistoricoToAttach.getClass(), pessoaJuridicaSucessaoHistoricoCollectionNewPessoaJuridicaSucessaoHistoricoToAttach.getId());
                attachedPessoaJuridicaSucessaoHistoricoCollectionNew.add(pessoaJuridicaSucessaoHistoricoCollectionNewPessoaJuridicaSucessaoHistoricoToAttach);
            }
            pessoaJuridicaSucessaoHistoricoCollectionNew = attachedPessoaJuridicaSucessaoHistoricoCollectionNew;
            pessoaJuridicaSucessao.setPessoaJuridicaSucessaoHistoricoCollection(pessoaJuridicaSucessaoHistoricoCollectionNew);
            pessoaJuridicaSucessao = em.merge(pessoaJuridicaSucessao);
            if (pessoaJuridicaSucedidaFkOld != null && !pessoaJuridicaSucedidaFkOld.equals(pessoaJuridicaSucedidaFkNew)) {
                pessoaJuridicaSucedidaFkOld.getPessoaJuridicaSucessaoCollection().remove(pessoaJuridicaSucessao);
                pessoaJuridicaSucedidaFkOld = em.merge(pessoaJuridicaSucedidaFkOld);
            }
            if (pessoaJuridicaSucedidaFkNew != null && !pessoaJuridicaSucedidaFkNew.equals(pessoaJuridicaSucedidaFkOld)) {
                pessoaJuridicaSucedidaFkNew.getPessoaJuridicaSucessaoCollection().add(pessoaJuridicaSucessao);
                pessoaJuridicaSucedidaFkNew = em.merge(pessoaJuridicaSucedidaFkNew);
            }
            if (pessoaJuridicaSucessoraFkOld != null && !pessoaJuridicaSucessoraFkOld.equals(pessoaJuridicaSucessoraFkNew)) {
                pessoaJuridicaSucessoraFkOld.getPessoaJuridicaSucessaoCollection().remove(pessoaJuridicaSucessao);
                pessoaJuridicaSucessoraFkOld = em.merge(pessoaJuridicaSucessoraFkOld);
            }
            if (pessoaJuridicaSucessoraFkNew != null && !pessoaJuridicaSucessoraFkNew.equals(pessoaJuridicaSucessoraFkOld)) {
                pessoaJuridicaSucessoraFkNew.getPessoaJuridicaSucessaoCollection().add(pessoaJuridicaSucessao);
                pessoaJuridicaSucessoraFkNew = em.merge(pessoaJuridicaSucessoraFkNew);
            }
            if (usuarioFkOld != null && !usuarioFkOld.equals(usuarioFkNew)) {
                usuarioFkOld.getPessoaJuridicaSucessaoCollection().remove(pessoaJuridicaSucessao);
                usuarioFkOld = em.merge(usuarioFkOld);
            }
            if (usuarioFkNew != null && !usuarioFkNew.equals(usuarioFkOld)) {
                usuarioFkNew.getPessoaJuridicaSucessaoCollection().add(pessoaJuridicaSucessao);
                usuarioFkNew = em.merge(usuarioFkNew);
            }
            for (PessoaJuridicaSucessaoHistorico pessoaJuridicaSucessaoHistoricoCollectionNewPessoaJuridicaSucessaoHistorico : pessoaJuridicaSucessaoHistoricoCollectionNew) {
                if (!pessoaJuridicaSucessaoHistoricoCollectionOld.contains(pessoaJuridicaSucessaoHistoricoCollectionNewPessoaJuridicaSucessaoHistorico)) {
                    PessoaJuridicaSucessao oldPessoaJuridicaSucessaoFkOfPessoaJuridicaSucessaoHistoricoCollectionNewPessoaJuridicaSucessaoHistorico = pessoaJuridicaSucessaoHistoricoCollectionNewPessoaJuridicaSucessaoHistorico.getPessoaJuridicaSucessaoFk();
                    pessoaJuridicaSucessaoHistoricoCollectionNewPessoaJuridicaSucessaoHistorico.setPessoaJuridicaSucessaoFk(pessoaJuridicaSucessao);
                    pessoaJuridicaSucessaoHistoricoCollectionNewPessoaJuridicaSucessaoHistorico = em.merge(pessoaJuridicaSucessaoHistoricoCollectionNewPessoaJuridicaSucessaoHistorico);
                    if (oldPessoaJuridicaSucessaoFkOfPessoaJuridicaSucessaoHistoricoCollectionNewPessoaJuridicaSucessaoHistorico != null && !oldPessoaJuridicaSucessaoFkOfPessoaJuridicaSucessaoHistoricoCollectionNewPessoaJuridicaSucessaoHistorico.equals(pessoaJuridicaSucessao)) {
                        oldPessoaJuridicaSucessaoFkOfPessoaJuridicaSucessaoHistoricoCollectionNewPessoaJuridicaSucessaoHistorico.getPessoaJuridicaSucessaoHistoricoCollection().remove(pessoaJuridicaSucessaoHistoricoCollectionNewPessoaJuridicaSucessaoHistorico);
                        oldPessoaJuridicaSucessaoFkOfPessoaJuridicaSucessaoHistoricoCollectionNewPessoaJuridicaSucessaoHistorico = em.merge(oldPessoaJuridicaSucessaoFkOfPessoaJuridicaSucessaoHistoricoCollectionNewPessoaJuridicaSucessaoHistorico);
                    }
                }
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
                Integer id = pessoaJuridicaSucessao.getId();
                if (findPessoaJuridicaSucessao(id) == null) {
                    throw new NonexistentEntityException("The pessoaJuridicaSucessao with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            PessoaJuridicaSucessao pessoaJuridicaSucessao;
            try {
                pessoaJuridicaSucessao = em.getReference(PessoaJuridicaSucessao.class, id);
                pessoaJuridicaSucessao.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pessoaJuridicaSucessao with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PessoaJuridicaSucessaoHistorico> pessoaJuridicaSucessaoHistoricoCollectionOrphanCheck = pessoaJuridicaSucessao.getPessoaJuridicaSucessaoHistoricoCollection();
            for (PessoaJuridicaSucessaoHistorico pessoaJuridicaSucessaoHistoricoCollectionOrphanCheckPessoaJuridicaSucessaoHistorico : pessoaJuridicaSucessaoHistoricoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PessoaJuridicaSucessao (" + pessoaJuridicaSucessao + ") cannot be destroyed since the PessoaJuridicaSucessaoHistorico " + pessoaJuridicaSucessaoHistoricoCollectionOrphanCheckPessoaJuridicaSucessaoHistorico + " in its pessoaJuridicaSucessaoHistoricoCollection field has a non-nullable pessoaJuridicaSucessaoFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            PessoaJuridica pessoaJuridicaSucedidaFk = pessoaJuridicaSucessao.getPessoaJuridicaSucedidaFk();
            if (pessoaJuridicaSucedidaFk != null) {
                pessoaJuridicaSucedidaFk.getPessoaJuridicaSucessaoCollection().remove(pessoaJuridicaSucessao);
                pessoaJuridicaSucedidaFk = em.merge(pessoaJuridicaSucedidaFk);
            }
            PessoaJuridica pessoaJuridicaSucessoraFk = pessoaJuridicaSucessao.getPessoaJuridicaSucessoraFk();
            if (pessoaJuridicaSucessoraFk != null) {
                pessoaJuridicaSucessoraFk.getPessoaJuridicaSucessaoCollection().remove(pessoaJuridicaSucessao);
                pessoaJuridicaSucessoraFk = em.merge(pessoaJuridicaSucessoraFk);
            }
            Usuario usuarioFk = pessoaJuridicaSucessao.getUsuarioFk();
            if (usuarioFk != null) {
                usuarioFk.getPessoaJuridicaSucessaoCollection().remove(pessoaJuridicaSucessao);
                usuarioFk = em.merge(usuarioFk);
            }
            em.remove(pessoaJuridicaSucessao);
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

    public List<PessoaJuridicaSucessao> findPessoaJuridicaSucessaoEntities() {
        return findPessoaJuridicaSucessaoEntities(true, -1, -1);
    }

    public List<PessoaJuridicaSucessao> findPessoaJuridicaSucessaoEntities(int maxResults, int firstResult) {
        return findPessoaJuridicaSucessaoEntities(false, maxResults, firstResult);
    }

    private List<PessoaJuridicaSucessao> findPessoaJuridicaSucessaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PessoaJuridicaSucessao.class));
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

    public PessoaJuridicaSucessao findPessoaJuridicaSucessao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PessoaJuridicaSucessao.class, id);
        } finally {
            em.close();
        }
    }

    public int getPessoaJuridicaSucessaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PessoaJuridicaSucessao> rt = cq.from(PessoaJuridicaSucessao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public PessoaJuridicaSucessao findDuplicates(PessoaJuridica pessoaJuridicaSucedida, PessoaJuridica pessoaJuridicaSucessora) {
        EntityManager em = getEntityManager();
        try {
            PessoaJuridicaSucessao pf = (PessoaJuridicaSucessao) em.createNativeQuery("select * from pessoa_juridica_sucessao "
                    + "where (pessoa_juridica_sucedida_fk = '" + pessoaJuridicaSucedida.getId()+ "' and pessoa_juridica_sucessora_fk = '" + pessoaJuridicaSucessora.getId() + "') "
                    + "or (pessoa_juridica_sucedida_fk = '" + pessoaJuridicaSucessora.getId() + "' and pessoa_juridica_sucessora_fk = '" + pessoaJuridicaSucedida.getId() + "')", PessoaJuridicaSucessao.class).getSingleResult();
            return pf;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public PessoaJuridicaSucessao findBySucedidaAndSucessora(Integer sucedida, Integer sucessora) {
        EntityManager em = getEntityManager();
        try {
            PessoaJuridicaSucessao pjs = (PessoaJuridicaSucessao) em.createNativeQuery("select * from pessoa_juridica_sucessao "
                    + "where pessoa_juridica_sucedida_fk = '" + sucedida + "' and pessoa_juridica_sucessora_fk = '" + sucessora + "'", PessoaJuridicaSucessao.class).getSingleResult();
            return pjs;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public List<PessoaJuridicaSucessao> findSucessoras(Integer id){
        EntityManager em = getEntityManager();
        try {
            List<PessoaJuridicaSucessao> pessoaJuridicaSucessaoList = (List<PessoaJuridicaSucessao>) em.createNativeQuery("select * from pessoa_juridica_sucessao "
                        + "where pessoa_juridica_sucedida_fk = '" + id + "' and status = 'A' order by data_de_sucessao desc", PessoaJuridicaSucessao.class).getResultList();
            return pessoaJuridicaSucessaoList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public List<PessoaJuridicaSucessao> findSucedidas(Integer id){
        EntityManager em = getEntityManager();
        try {
            List<PessoaJuridicaSucessao> pessoaJuridicaSucessaoList = (List<PessoaJuridicaSucessao>) em.createNativeQuery("select * from pessoa_juridica_sucessao "
                        + "where pessoa_juridica_sucessora_fk = '" + id + "' and status = 'A' order by data_de_sucessao asc", PessoaJuridicaSucessao.class).getResultList();
            return pessoaJuridicaSucessaoList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public List<PessoaJuridicaSucessao> findSucedidasAndSucessoras(Integer id){
        EntityManager em = getEntityManager();
        try {
            List<PessoaJuridicaSucessao> pessoaJuridicaSucessaoList = (List<PessoaJuridicaSucessao>) em.createNativeQuery("select * from pessoa_juridica_sucessao "
                        + "where (pessoa_juridica_sucessora_fk = '" + id + "' or pessoa_juridica_sucedida_fk = '" + id + "') and status = 'A' order by data_de_sucessao asc", PessoaJuridicaSucessao.class).getResultList();
            return pessoaJuridicaSucessaoList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
