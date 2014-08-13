/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.Nacionalidade;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidade.PessoaFisica;
import entidade.PessoaFisicaHistorico;
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
public class NacionalidadeDAO implements Serializable {

    public NacionalidadeDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Nacionalidade nacionalidade) throws RollbackFailureException, Exception {
        if (nacionalidade.getPessoaFisicaHistoricoCollection() == null) {
            nacionalidade.setPessoaFisicaHistoricoCollection(new ArrayList<PessoaFisicaHistorico>());
        }
        if (nacionalidade.getPessoaFisicaCollection() == null) {
            nacionalidade.setPessoaFisicaCollection(new ArrayList<PessoaFisica>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            Collection<PessoaFisicaHistorico> attachedPessoaFisicaHistoricoCollection = new ArrayList<PessoaFisicaHistorico>();
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach : nacionalidade.getPessoaFisicaHistoricoCollection()) {
                pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach = em.getReference(pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach.getClass(), pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach.getId());
                attachedPessoaFisicaHistoricoCollection.add(pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach);
            }
            nacionalidade.setPessoaFisicaHistoricoCollection(attachedPessoaFisicaHistoricoCollection);
            Collection<PessoaFisica> attachedPessoaFisicaCollection = new ArrayList<PessoaFisica>();
            for (PessoaFisica pessoaFisicaCollectionPessoaFisicaToAttach : nacionalidade.getPessoaFisicaCollection()) {
                pessoaFisicaCollectionPessoaFisicaToAttach = em.getReference(pessoaFisicaCollectionPessoaFisicaToAttach.getClass(), pessoaFisicaCollectionPessoaFisicaToAttach.getId());
                attachedPessoaFisicaCollection.add(pessoaFisicaCollectionPessoaFisicaToAttach);
            }
            nacionalidade.setPessoaFisicaCollection(attachedPessoaFisicaCollection);
            em.persist(nacionalidade);
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionPessoaFisicaHistorico : nacionalidade.getPessoaFisicaHistoricoCollection()) {
                Nacionalidade oldNacionalidadeFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico = pessoaFisicaHistoricoCollectionPessoaFisicaHistorico.getNacionalidadeFk();
                pessoaFisicaHistoricoCollectionPessoaFisicaHistorico.setNacionalidadeFk(nacionalidade);
                pessoaFisicaHistoricoCollectionPessoaFisicaHistorico = em.merge(pessoaFisicaHistoricoCollectionPessoaFisicaHistorico);
                if (oldNacionalidadeFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico != null) {
                    oldNacionalidadeFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistoricoCollectionPessoaFisicaHistorico);
                    oldNacionalidadeFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico = em.merge(oldNacionalidadeFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico);
                }
            }
            for (PessoaFisica pessoaFisicaCollectionPessoaFisica : nacionalidade.getPessoaFisicaCollection()) {
                Nacionalidade oldNacionalidadeFkOfPessoaFisicaCollectionPessoaFisica = pessoaFisicaCollectionPessoaFisica.getNacionalidadeFk();
                pessoaFisicaCollectionPessoaFisica.setNacionalidadeFk(nacionalidade);
                pessoaFisicaCollectionPessoaFisica = em.merge(pessoaFisicaCollectionPessoaFisica);
                if (oldNacionalidadeFkOfPessoaFisicaCollectionPessoaFisica != null) {
                    oldNacionalidadeFkOfPessoaFisicaCollectionPessoaFisica.getPessoaFisicaCollection().remove(pessoaFisicaCollectionPessoaFisica);
                    oldNacionalidadeFkOfPessoaFisicaCollectionPessoaFisica = em.merge(oldNacionalidadeFkOfPessoaFisicaCollectionPessoaFisica);
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

    public void edit(Nacionalidade nacionalidade) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            Nacionalidade persistentNacionalidade = em.find(Nacionalidade.class, nacionalidade.getId());
            Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollectionOld = persistentNacionalidade.getPessoaFisicaHistoricoCollection();
            Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollectionNew = nacionalidade.getPessoaFisicaHistoricoCollection();
            Collection<PessoaFisica> pessoaFisicaCollectionOld = persistentNacionalidade.getPessoaFisicaCollection();
            Collection<PessoaFisica> pessoaFisicaCollectionNew = nacionalidade.getPessoaFisicaCollection();
            Collection<PessoaFisicaHistorico> attachedPessoaFisicaHistoricoCollectionNew = new ArrayList<PessoaFisicaHistorico>();
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach : pessoaFisicaHistoricoCollectionNew) {
                pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach = em.getReference(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach.getClass(), pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach.getId());
                attachedPessoaFisicaHistoricoCollectionNew.add(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach);
            }
            pessoaFisicaHistoricoCollectionNew = attachedPessoaFisicaHistoricoCollectionNew;
            nacionalidade.setPessoaFisicaHistoricoCollection(pessoaFisicaHistoricoCollectionNew);
            Collection<PessoaFisica> attachedPessoaFisicaCollectionNew = new ArrayList<PessoaFisica>();
            for (PessoaFisica pessoaFisicaCollectionNewPessoaFisicaToAttach : pessoaFisicaCollectionNew) {
                pessoaFisicaCollectionNewPessoaFisicaToAttach = em.getReference(pessoaFisicaCollectionNewPessoaFisicaToAttach.getClass(), pessoaFisicaCollectionNewPessoaFisicaToAttach.getId());
                attachedPessoaFisicaCollectionNew.add(pessoaFisicaCollectionNewPessoaFisicaToAttach);
            }
            pessoaFisicaCollectionNew = attachedPessoaFisicaCollectionNew;
            nacionalidade.setPessoaFisicaCollection(pessoaFisicaCollectionNew);
            nacionalidade = em.merge(nacionalidade);
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico : pessoaFisicaHistoricoCollectionOld) {
                if (!pessoaFisicaHistoricoCollectionNew.contains(pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico)) {
                    pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico.setNacionalidadeFk(null);
                    pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico = em.merge(pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico);
                }
            }
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico : pessoaFisicaHistoricoCollectionNew) {
                if (!pessoaFisicaHistoricoCollectionOld.contains(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico)) {
                    Nacionalidade oldNacionalidadeFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico = pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico.getNacionalidadeFk();
                    pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico.setNacionalidadeFk(nacionalidade);
                    pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico = em.merge(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico);
                    if (oldNacionalidadeFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico != null && !oldNacionalidadeFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico.equals(nacionalidade)) {
                        oldNacionalidadeFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico);
                        oldNacionalidadeFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico = em.merge(oldNacionalidadeFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico);
                    }
                }
            }
            for (PessoaFisica pessoaFisicaCollectionOldPessoaFisica : pessoaFisicaCollectionOld) {
                if (!pessoaFisicaCollectionNew.contains(pessoaFisicaCollectionOldPessoaFisica)) {
                    pessoaFisicaCollectionOldPessoaFisica.setNacionalidadeFk(null);
                    pessoaFisicaCollectionOldPessoaFisica = em.merge(pessoaFisicaCollectionOldPessoaFisica);
                }
            }
            for (PessoaFisica pessoaFisicaCollectionNewPessoaFisica : pessoaFisicaCollectionNew) {
                if (!pessoaFisicaCollectionOld.contains(pessoaFisicaCollectionNewPessoaFisica)) {
                    Nacionalidade oldNacionalidadeFkOfPessoaFisicaCollectionNewPessoaFisica = pessoaFisicaCollectionNewPessoaFisica.getNacionalidadeFk();
                    pessoaFisicaCollectionNewPessoaFisica.setNacionalidadeFk(nacionalidade);
                    pessoaFisicaCollectionNewPessoaFisica = em.merge(pessoaFisicaCollectionNewPessoaFisica);
                    if (oldNacionalidadeFkOfPessoaFisicaCollectionNewPessoaFisica != null && !oldNacionalidadeFkOfPessoaFisicaCollectionNewPessoaFisica.equals(nacionalidade)) {
                        oldNacionalidadeFkOfPessoaFisicaCollectionNewPessoaFisica.getPessoaFisicaCollection().remove(pessoaFisicaCollectionNewPessoaFisica);
                        oldNacionalidadeFkOfPessoaFisicaCollectionNewPessoaFisica = em.merge(oldNacionalidadeFkOfPessoaFisicaCollectionNewPessoaFisica);
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
                Integer id = nacionalidade.getId();
                if (findNacionalidade(id) == null) {
                    throw new NonexistentEntityException("The nacionalidade with id " + id + " no longer exists.");
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
            Nacionalidade nacionalidade;
            try {
                nacionalidade = em.getReference(Nacionalidade.class, id);
                nacionalidade.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nacionalidade with id " + id + " no longer exists.", enfe);
            }
            Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollection = nacionalidade.getPessoaFisicaHistoricoCollection();
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionPessoaFisicaHistorico : pessoaFisicaHistoricoCollection) {
                pessoaFisicaHistoricoCollectionPessoaFisicaHistorico.setNacionalidadeFk(null);
                pessoaFisicaHistoricoCollectionPessoaFisicaHistorico = em.merge(pessoaFisicaHistoricoCollectionPessoaFisicaHistorico);
            }
            Collection<PessoaFisica> pessoaFisicaCollection = nacionalidade.getPessoaFisicaCollection();
            for (PessoaFisica pessoaFisicaCollectionPessoaFisica : pessoaFisicaCollection) {
                pessoaFisicaCollectionPessoaFisica.setNacionalidadeFk(null);
                pessoaFisicaCollectionPessoaFisica = em.merge(pessoaFisicaCollectionPessoaFisica);
            }
            em.remove(nacionalidade);
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

    public List<Nacionalidade> findNacionalidadeEntities() {
        return findNacionalidadeEntities(true, -1, -1);
    }

    public List<Nacionalidade> findNacionalidadeEntities(int maxResults, int firstResult) {
        return findNacionalidadeEntities(false, maxResults, firstResult);
    }

    private List<Nacionalidade> findNacionalidadeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Nacionalidade.class));
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

    public Nacionalidade findNacionalidade(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Nacionalidade.class, id);
        } finally {
            em.close();
        }
    }

    public int getNacionalidadeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Nacionalidade> rt = cq.from(Nacionalidade.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
