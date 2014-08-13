/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.Estado;
import entidade.Pais;
import entidade.PessoaFisica;
import entidade.PessoaFisicaHistorico;
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
public class PaisDAO implements Serializable {

    public PaisDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pais pais) throws RollbackFailureException, Exception {
        if (pais.getEstadoCollection() == null) {
            pais.setEstadoCollection(new ArrayList<Estado>());
        }
        if (pais.getPessoaFisicaCollection() == null) {
            pais.setPessoaFisicaCollection(new ArrayList<PessoaFisica>());
        }
        if (pais.getPessoaFisicaHistoricoCollection() == null) {
            pais.setPessoaFisicaHistoricoCollection(new ArrayList<PessoaFisicaHistorico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Estado> attachedEstadoCollection = new ArrayList<Estado>();
            for (Estado estadoCollectionEstadoToAttach : pais.getEstadoCollection()) {
                estadoCollectionEstadoToAttach = em.getReference(estadoCollectionEstadoToAttach.getClass(), estadoCollectionEstadoToAttach.getId());
                attachedEstadoCollection.add(estadoCollectionEstadoToAttach);
            }
            pais.setEstadoCollection(attachedEstadoCollection);
            Collection<PessoaFisica> attachedPessoaFisicaCollection = new ArrayList<PessoaFisica>();
            for (PessoaFisica pessoaFisicaCollectionPessoaFisicaToAttach : pais.getPessoaFisicaCollection()) {
                pessoaFisicaCollectionPessoaFisicaToAttach = em.getReference(pessoaFisicaCollectionPessoaFisicaToAttach.getClass(), pessoaFisicaCollectionPessoaFisicaToAttach.getId());
                attachedPessoaFisicaCollection.add(pessoaFisicaCollectionPessoaFisicaToAttach);
            }
            pais.setPessoaFisicaCollection(attachedPessoaFisicaCollection);
            Collection<PessoaFisicaHistorico> attachedPessoaFisicaHistoricoCollection = new ArrayList<PessoaFisicaHistorico>();
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach : pais.getPessoaFisicaHistoricoCollection()) {
                pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach = em.getReference(pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach.getClass(), pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach.getId());
                attachedPessoaFisicaHistoricoCollection.add(pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach);
            }
            pais.setPessoaFisicaHistoricoCollection(attachedPessoaFisicaHistoricoCollection);
            em.persist(pais);
            for (Estado estadoCollectionEstado : pais.getEstadoCollection()) {
                Pais oldPaisFkOfEstadoCollectionEstado = estadoCollectionEstado.getPaisFk();
                estadoCollectionEstado.setPaisFk(pais);
                estadoCollectionEstado = em.merge(estadoCollectionEstado);
                if (oldPaisFkOfEstadoCollectionEstado != null) {
                    oldPaisFkOfEstadoCollectionEstado.getEstadoCollection().remove(estadoCollectionEstado);
                    oldPaisFkOfEstadoCollectionEstado = em.merge(oldPaisFkOfEstadoCollectionEstado);
                }
            }
            for (PessoaFisica pessoaFisicaCollectionPessoaFisica : pais.getPessoaFisicaCollection()) {
                Pais oldPaisFkOfPessoaFisicaCollectionPessoaFisica = pessoaFisicaCollectionPessoaFisica.getPaisFk();
                pessoaFisicaCollectionPessoaFisica.setPaisFk(pais);
                pessoaFisicaCollectionPessoaFisica = em.merge(pessoaFisicaCollectionPessoaFisica);
                if (oldPaisFkOfPessoaFisicaCollectionPessoaFisica != null) {
                    oldPaisFkOfPessoaFisicaCollectionPessoaFisica.getPessoaFisicaCollection().remove(pessoaFisicaCollectionPessoaFisica);
                    oldPaisFkOfPessoaFisicaCollectionPessoaFisica = em.merge(oldPaisFkOfPessoaFisicaCollectionPessoaFisica);
                }
            }
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionPessoaFisicaHistorico : pais.getPessoaFisicaHistoricoCollection()) {
                Pais oldPaisFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico = pessoaFisicaHistoricoCollectionPessoaFisicaHistorico.getPaisFk();
                pessoaFisicaHistoricoCollectionPessoaFisicaHistorico.setPaisFk(pais);
                pessoaFisicaHistoricoCollectionPessoaFisicaHistorico = em.merge(pessoaFisicaHistoricoCollectionPessoaFisicaHistorico);
                if (oldPaisFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico != null) {
                    oldPaisFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistoricoCollectionPessoaFisicaHistorico);
                    oldPaisFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico = em.merge(oldPaisFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico);
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

    public void edit(Pais pais) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais persistentPais = em.find(Pais.class, pais.getId());
            Collection<Estado> estadoCollectionOld = persistentPais.getEstadoCollection();
            Collection<Estado> estadoCollectionNew = pais.getEstadoCollection();
            Collection<PessoaFisica> pessoaFisicaCollectionOld = persistentPais.getPessoaFisicaCollection();
            Collection<PessoaFisica> pessoaFisicaCollectionNew = pais.getPessoaFisicaCollection();
            Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollectionOld = persistentPais.getPessoaFisicaHistoricoCollection();
            Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollectionNew = pais.getPessoaFisicaHistoricoCollection();
            List<String> illegalOrphanMessages = null;
            for (Estado estadoCollectionOldEstado : estadoCollectionOld) {
                if (!estadoCollectionNew.contains(estadoCollectionOldEstado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Estado " + estadoCollectionOldEstado + " since its paisFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Estado> attachedEstadoCollectionNew = new ArrayList<Estado>();
            for (Estado estadoCollectionNewEstadoToAttach : estadoCollectionNew) {
                estadoCollectionNewEstadoToAttach = em.getReference(estadoCollectionNewEstadoToAttach.getClass(), estadoCollectionNewEstadoToAttach.getId());
                attachedEstadoCollectionNew.add(estadoCollectionNewEstadoToAttach);
            }
            estadoCollectionNew = attachedEstadoCollectionNew;
            pais.setEstadoCollection(estadoCollectionNew);
            Collection<PessoaFisica> attachedPessoaFisicaCollectionNew = new ArrayList<PessoaFisica>();
            for (PessoaFisica pessoaFisicaCollectionNewPessoaFisicaToAttach : pessoaFisicaCollectionNew) {
                pessoaFisicaCollectionNewPessoaFisicaToAttach = em.getReference(pessoaFisicaCollectionNewPessoaFisicaToAttach.getClass(), pessoaFisicaCollectionNewPessoaFisicaToAttach.getId());
                attachedPessoaFisicaCollectionNew.add(pessoaFisicaCollectionNewPessoaFisicaToAttach);
            }
            pessoaFisicaCollectionNew = attachedPessoaFisicaCollectionNew;
            pais.setPessoaFisicaCollection(pessoaFisicaCollectionNew);
            Collection<PessoaFisicaHistorico> attachedPessoaFisicaHistoricoCollectionNew = new ArrayList<PessoaFisicaHistorico>();
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach : pessoaFisicaHistoricoCollectionNew) {
                pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach = em.getReference(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach.getClass(), pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach.getId());
                attachedPessoaFisicaHistoricoCollectionNew.add(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach);
            }
            pessoaFisicaHistoricoCollectionNew = attachedPessoaFisicaHistoricoCollectionNew;
            pais.setPessoaFisicaHistoricoCollection(pessoaFisicaHistoricoCollectionNew);
            pais = em.merge(pais);
            for (Estado estadoCollectionNewEstado : estadoCollectionNew) {
                if (!estadoCollectionOld.contains(estadoCollectionNewEstado)) {
                    Pais oldPaisFkOfEstadoCollectionNewEstado = estadoCollectionNewEstado.getPaisFk();
                    estadoCollectionNewEstado.setPaisFk(pais);
                    estadoCollectionNewEstado = em.merge(estadoCollectionNewEstado);
                    if (oldPaisFkOfEstadoCollectionNewEstado != null && !oldPaisFkOfEstadoCollectionNewEstado.equals(pais)) {
                        oldPaisFkOfEstadoCollectionNewEstado.getEstadoCollection().remove(estadoCollectionNewEstado);
                        oldPaisFkOfEstadoCollectionNewEstado = em.merge(oldPaisFkOfEstadoCollectionNewEstado);
                    }
                }
            }
            for (PessoaFisica pessoaFisicaCollectionOldPessoaFisica : pessoaFisicaCollectionOld) {
                if (!pessoaFisicaCollectionNew.contains(pessoaFisicaCollectionOldPessoaFisica)) {
                    pessoaFisicaCollectionOldPessoaFisica.setPaisFk(null);
                    pessoaFisicaCollectionOldPessoaFisica = em.merge(pessoaFisicaCollectionOldPessoaFisica);
                }
            }
            for (PessoaFisica pessoaFisicaCollectionNewPessoaFisica : pessoaFisicaCollectionNew) {
                if (!pessoaFisicaCollectionOld.contains(pessoaFisicaCollectionNewPessoaFisica)) {
                    Pais oldPaisFkOfPessoaFisicaCollectionNewPessoaFisica = pessoaFisicaCollectionNewPessoaFisica.getPaisFk();
                    pessoaFisicaCollectionNewPessoaFisica.setPaisFk(pais);
                    pessoaFisicaCollectionNewPessoaFisica = em.merge(pessoaFisicaCollectionNewPessoaFisica);
                    if (oldPaisFkOfPessoaFisicaCollectionNewPessoaFisica != null && !oldPaisFkOfPessoaFisicaCollectionNewPessoaFisica.equals(pais)) {
                        oldPaisFkOfPessoaFisicaCollectionNewPessoaFisica.getPessoaFisicaCollection().remove(pessoaFisicaCollectionNewPessoaFisica);
                        oldPaisFkOfPessoaFisicaCollectionNewPessoaFisica = em.merge(oldPaisFkOfPessoaFisicaCollectionNewPessoaFisica);
                    }
                }
            }
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico : pessoaFisicaHistoricoCollectionOld) {
                if (!pessoaFisicaHistoricoCollectionNew.contains(pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico)) {
                    pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico.setPaisFk(null);
                    pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico = em.merge(pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico);
                }
            }
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico : pessoaFisicaHistoricoCollectionNew) {
                if (!pessoaFisicaHistoricoCollectionOld.contains(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico)) {
                    Pais oldPaisFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico = pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico.getPaisFk();
                    pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico.setPaisFk(pais);
                    pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico = em.merge(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico);
                    if (oldPaisFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico != null && !oldPaisFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico.equals(pais)) {
                        oldPaisFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico);
                        oldPaisFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico = em.merge(oldPaisFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico);
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
                Integer id = pais.getId();
                if (findPais(id) == null) {
                    throw new NonexistentEntityException("The pais with id " + id + " no longer exists.");
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
            em = getEntityManager();
            em.getTransaction().begin();
            Pais pais;
            try {
                pais = em.getReference(Pais.class, id);
                pais.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pais with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Estado> estadoCollectionOrphanCheck = pais.getEstadoCollection();
            for (Estado estadoCollectionOrphanCheckEstado : estadoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pais (" + pais + ") cannot be destroyed since the Estado " + estadoCollectionOrphanCheckEstado + " in its estadoCollection field has a non-nullable paisFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<PessoaFisica> pessoaFisicaCollection = pais.getPessoaFisicaCollection();
            for (PessoaFisica pessoaFisicaCollectionPessoaFisica : pessoaFisicaCollection) {
                pessoaFisicaCollectionPessoaFisica.setPaisFk(null);
                pessoaFisicaCollectionPessoaFisica = em.merge(pessoaFisicaCollectionPessoaFisica);
            }
            Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollection = pais.getPessoaFisicaHistoricoCollection();
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionPessoaFisicaHistorico : pessoaFisicaHistoricoCollection) {
                pessoaFisicaHistoricoCollectionPessoaFisicaHistorico.setPaisFk(null);
                pessoaFisicaHistoricoCollectionPessoaFisicaHistorico = em.merge(pessoaFisicaHistoricoCollectionPessoaFisicaHistorico);
            }
            em.remove(pais);
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

    public List<Pais> findPaisEntities() {
        return findPaisEntities(true, -1, -1);
    }

    public List<Pais> findPaisEntities(int maxResults, int firstResult) {
        return findPaisEntities(false, maxResults, firstResult);
    }

    private List<Pais> findPaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pais.class));
            Root<Pais> from = cq.from(Pais.class);
            cq.orderBy(em.getCriteriaBuilder().asc(from.get("nome")));
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

    public Pais findPais(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pais.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pais> rt = cq.from(Pais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Pais findBrasil(){
        EntityManager em = getEntityManager();
        try {
            Pais pais = (Pais) em.createNativeQuery("select * from pais "
                    + "where nome = 'Brasil'", Pais.class).getSingleResult();
            return pais;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
}
