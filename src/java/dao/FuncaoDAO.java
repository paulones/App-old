/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.Funcao;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidade.PessoaFisicaJuridica;
import entidade.PessoaFisicaJuridicaHistorico;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author paulones
 */
public class FuncaoDAO implements Serializable {

    public FuncaoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Funcao funcao) throws RollbackFailureException, Exception {
        if (funcao.getPessoaFisicaJuridicaCollection() == null) {
            funcao.setPessoaFisicaJuridicaCollection(new ArrayList<PessoaFisicaJuridica>());
        }
        if (funcao.getPessoaFisicaJuridicaHistoricoCollection() == null) {
            funcao.setPessoaFisicaJuridicaHistoricoCollection(new ArrayList<PessoaFisicaJuridicaHistorico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            Collection<PessoaFisicaJuridica> attachedPessoaFisicaJuridicaCollection = new ArrayList<PessoaFisicaJuridica>();
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionPessoaFisicaJuridicaToAttach : funcao.getPessoaFisicaJuridicaCollection()) {
                pessoaFisicaJuridicaCollectionPessoaFisicaJuridicaToAttach = em.getReference(pessoaFisicaJuridicaCollectionPessoaFisicaJuridicaToAttach.getClass(), pessoaFisicaJuridicaCollectionPessoaFisicaJuridicaToAttach.getId());
                attachedPessoaFisicaJuridicaCollection.add(pessoaFisicaJuridicaCollectionPessoaFisicaJuridicaToAttach);
            }
            funcao.setPessoaFisicaJuridicaCollection(attachedPessoaFisicaJuridicaCollection);
            Collection<PessoaFisicaJuridicaHistorico> attachedPessoaFisicaJuridicaHistoricoCollection = new ArrayList<PessoaFisicaJuridicaHistorico>();
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistoricoToAttach : funcao.getPessoaFisicaJuridicaHistoricoCollection()) {
                pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistoricoToAttach = em.getReference(pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistoricoToAttach.getClass(), pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistoricoToAttach.getId());
                attachedPessoaFisicaJuridicaHistoricoCollection.add(pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistoricoToAttach);
            }
            funcao.setPessoaFisicaJuridicaHistoricoCollection(attachedPessoaFisicaJuridicaHistoricoCollection);
            em.persist(funcao);
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionPessoaFisicaJuridica : funcao.getPessoaFisicaJuridicaCollection()) {
                Funcao oldFuncaoFkOfPessoaFisicaJuridicaCollectionPessoaFisicaJuridica = pessoaFisicaJuridicaCollectionPessoaFisicaJuridica.getFuncaoFk();
                pessoaFisicaJuridicaCollectionPessoaFisicaJuridica.setFuncaoFk(funcao);
                pessoaFisicaJuridicaCollectionPessoaFisicaJuridica = em.merge(pessoaFisicaJuridicaCollectionPessoaFisicaJuridica);
                if (oldFuncaoFkOfPessoaFisicaJuridicaCollectionPessoaFisicaJuridica != null) {
                    oldFuncaoFkOfPessoaFisicaJuridicaCollectionPessoaFisicaJuridica.getPessoaFisicaJuridicaCollection().remove(pessoaFisicaJuridicaCollectionPessoaFisicaJuridica);
                    oldFuncaoFkOfPessoaFisicaJuridicaCollectionPessoaFisicaJuridica = em.merge(oldFuncaoFkOfPessoaFisicaJuridicaCollectionPessoaFisicaJuridica);
                }
            }
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico : funcao.getPessoaFisicaJuridicaHistoricoCollection()) {
                Funcao oldFuncaoFkOfPessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico = pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico.getFuncaoFk();
                pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico.setFuncaoFk(funcao);
                pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico = em.merge(pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico);
                if (oldFuncaoFkOfPessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico != null) {
                    oldFuncaoFkOfPessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico.getPessoaFisicaJuridicaHistoricoCollection().remove(pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico);
                    oldFuncaoFkOfPessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico = em.merge(oldFuncaoFkOfPessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico);
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

    public void edit(Funcao funcao) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            Funcao persistentFuncao = em.find(Funcao.class, funcao.getId());
            Collection<PessoaFisicaJuridica> pessoaFisicaJuridicaCollectionOld = persistentFuncao.getPessoaFisicaJuridicaCollection();
            Collection<PessoaFisicaJuridica> pessoaFisicaJuridicaCollectionNew = funcao.getPessoaFisicaJuridicaCollection();
            Collection<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoCollectionOld = persistentFuncao.getPessoaFisicaJuridicaHistoricoCollection();
            Collection<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoCollectionNew = funcao.getPessoaFisicaJuridicaHistoricoCollection();
            Collection<PessoaFisicaJuridica> attachedPessoaFisicaJuridicaCollectionNew = new ArrayList<PessoaFisicaJuridica>();
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridicaToAttach : pessoaFisicaJuridicaCollectionNew) {
                pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridicaToAttach = em.getReference(pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridicaToAttach.getClass(), pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridicaToAttach.getId());
                attachedPessoaFisicaJuridicaCollectionNew.add(pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridicaToAttach);
            }
            pessoaFisicaJuridicaCollectionNew = attachedPessoaFisicaJuridicaCollectionNew;
            funcao.setPessoaFisicaJuridicaCollection(pessoaFisicaJuridicaCollectionNew);
            Collection<PessoaFisicaJuridicaHistorico> attachedPessoaFisicaJuridicaHistoricoCollectionNew = new ArrayList<PessoaFisicaJuridicaHistorico>();
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistoricoToAttach : pessoaFisicaJuridicaHistoricoCollectionNew) {
                pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistoricoToAttach = em.getReference(pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistoricoToAttach.getClass(), pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistoricoToAttach.getId());
                attachedPessoaFisicaJuridicaHistoricoCollectionNew.add(pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistoricoToAttach);
            }
            pessoaFisicaJuridicaHistoricoCollectionNew = attachedPessoaFisicaJuridicaHistoricoCollectionNew;
            funcao.setPessoaFisicaJuridicaHistoricoCollection(pessoaFisicaJuridicaHistoricoCollectionNew);
            funcao = em.merge(funcao);
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionOldPessoaFisicaJuridica : pessoaFisicaJuridicaCollectionOld) {
                if (!pessoaFisicaJuridicaCollectionNew.contains(pessoaFisicaJuridicaCollectionOldPessoaFisicaJuridica)) {
                    pessoaFisicaJuridicaCollectionOldPessoaFisicaJuridica.setFuncaoFk(null);
                    pessoaFisicaJuridicaCollectionOldPessoaFisicaJuridica = em.merge(pessoaFisicaJuridicaCollectionOldPessoaFisicaJuridica);
                }
            }
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica : pessoaFisicaJuridicaCollectionNew) {
                if (!pessoaFisicaJuridicaCollectionOld.contains(pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica)) {
                    Funcao oldFuncaoFkOfPessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica = pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica.getFuncaoFk();
                    pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica.setFuncaoFk(funcao);
                    pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica = em.merge(pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica);
                    if (oldFuncaoFkOfPessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica != null && !oldFuncaoFkOfPessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica.equals(funcao)) {
                        oldFuncaoFkOfPessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica.getPessoaFisicaJuridicaCollection().remove(pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica);
                        oldFuncaoFkOfPessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica = em.merge(oldFuncaoFkOfPessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica);
                    }
                }
            }
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionOldPessoaFisicaJuridicaHistorico : pessoaFisicaJuridicaHistoricoCollectionOld) {
                if (!pessoaFisicaJuridicaHistoricoCollectionNew.contains(pessoaFisicaJuridicaHistoricoCollectionOldPessoaFisicaJuridicaHistorico)) {
                    pessoaFisicaJuridicaHistoricoCollectionOldPessoaFisicaJuridicaHistorico.setFuncaoFk(null);
                    pessoaFisicaJuridicaHistoricoCollectionOldPessoaFisicaJuridicaHistorico = em.merge(pessoaFisicaJuridicaHistoricoCollectionOldPessoaFisicaJuridicaHistorico);
                }
            }
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico : pessoaFisicaJuridicaHistoricoCollectionNew) {
                if (!pessoaFisicaJuridicaHistoricoCollectionOld.contains(pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico)) {
                    Funcao oldFuncaoFkOfPessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico = pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico.getFuncaoFk();
                    pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico.setFuncaoFk(funcao);
                    pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico = em.merge(pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico);
                    if (oldFuncaoFkOfPessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico != null && !oldFuncaoFkOfPessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico.equals(funcao)) {
                        oldFuncaoFkOfPessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico.getPessoaFisicaJuridicaHistoricoCollection().remove(pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico);
                        oldFuncaoFkOfPessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico = em.merge(oldFuncaoFkOfPessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico);
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
                Integer id = funcao.getId();
                if (findFuncao(id) == null) {
                    throw new NonexistentEntityException("The funcao with id " + id + " no longer exists.");
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
            Funcao funcao;
            try {
                funcao = em.getReference(Funcao.class, id);
                funcao.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The funcao with id " + id + " no longer exists.", enfe);
            }
            Collection<PessoaFisicaJuridica> pessoaFisicaJuridicaCollection = funcao.getPessoaFisicaJuridicaCollection();
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionPessoaFisicaJuridica : pessoaFisicaJuridicaCollection) {
                pessoaFisicaJuridicaCollectionPessoaFisicaJuridica.setFuncaoFk(null);
                pessoaFisicaJuridicaCollectionPessoaFisicaJuridica = em.merge(pessoaFisicaJuridicaCollectionPessoaFisicaJuridica);
            }
            Collection<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoCollection = funcao.getPessoaFisicaJuridicaHistoricoCollection();
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico : pessoaFisicaJuridicaHistoricoCollection) {
                pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico.setFuncaoFk(null);
                pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico = em.merge(pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico);
            }
            em.remove(funcao);
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

    public List<Funcao> findFuncaoEntities() {
        return findFuncaoEntities(true, -1, -1);
    }

    public List<Funcao> findFuncaoEntities(int maxResults, int firstResult) {
        return findFuncaoEntities(false, maxResults, firstResult);
    }

    private List<Funcao> findFuncaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Funcao.class));
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

    public Funcao findFuncao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Funcao.class, id);
        } finally {
            em.close();
        }
    }

    public int getFuncaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Funcao> rt = cq.from(Funcao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
