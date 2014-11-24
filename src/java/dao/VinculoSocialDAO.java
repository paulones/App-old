/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidade.PessoaFisicaFisica;
import java.util.ArrayList;
import java.util.Collection;
import entidade.PessoaFisicaFisicaHistorico;
import entidade.VinculoSocial;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author paulones
 */
public class VinculoSocialDAO implements Serializable {

    public VinculoSocialDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VinculoSocial vinculoSocial) throws RollbackFailureException, Exception {
        if (vinculoSocial.getPessoaFisicaFisicaCollection() == null) {
            vinculoSocial.setPessoaFisicaFisicaCollection(new ArrayList<PessoaFisicaFisica>());
        }
        if (vinculoSocial.getPessoaFisicaFisicaHistoricoCollection() == null) {
            vinculoSocial.setPessoaFisicaFisicaHistoricoCollection(new ArrayList<PessoaFisicaFisicaHistorico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            Collection<PessoaFisicaFisica> attachedPessoaFisicaFisicaCollection = new ArrayList<PessoaFisicaFisica>();
            for (PessoaFisicaFisica pessoaFisicaFisicaCollectionPessoaFisicaFisicaToAttach : vinculoSocial.getPessoaFisicaFisicaCollection()) {
                pessoaFisicaFisicaCollectionPessoaFisicaFisicaToAttach = em.getReference(pessoaFisicaFisicaCollectionPessoaFisicaFisicaToAttach.getClass(), pessoaFisicaFisicaCollectionPessoaFisicaFisicaToAttach.getId());
                attachedPessoaFisicaFisicaCollection.add(pessoaFisicaFisicaCollectionPessoaFisicaFisicaToAttach);
            }
            vinculoSocial.setPessoaFisicaFisicaCollection(attachedPessoaFisicaFisicaCollection);
            Collection<PessoaFisicaFisicaHistorico> attachedPessoaFisicaFisicaHistoricoCollection = new ArrayList<PessoaFisicaFisicaHistorico>();
            for (PessoaFisicaFisicaHistorico pessoaFisicaFisicaHistoricoCollectionPessoaFisicaFisicaHistoricoToAttach : vinculoSocial.getPessoaFisicaFisicaHistoricoCollection()) {
                pessoaFisicaFisicaHistoricoCollectionPessoaFisicaFisicaHistoricoToAttach = em.getReference(pessoaFisicaFisicaHistoricoCollectionPessoaFisicaFisicaHistoricoToAttach.getClass(), pessoaFisicaFisicaHistoricoCollectionPessoaFisicaFisicaHistoricoToAttach.getId());
                attachedPessoaFisicaFisicaHistoricoCollection.add(pessoaFisicaFisicaHistoricoCollectionPessoaFisicaFisicaHistoricoToAttach);
            }
            vinculoSocial.setPessoaFisicaFisicaHistoricoCollection(attachedPessoaFisicaFisicaHistoricoCollection);
            em.persist(vinculoSocial);
            for (PessoaFisicaFisica pessoaFisicaFisicaCollectionPessoaFisicaFisica : vinculoSocial.getPessoaFisicaFisicaCollection()) {
                VinculoSocial oldVinculoSocialFkOfPessoaFisicaFisicaCollectionPessoaFisicaFisica = pessoaFisicaFisicaCollectionPessoaFisicaFisica.getVinculoSocialFk();
                pessoaFisicaFisicaCollectionPessoaFisicaFisica.setVinculoSocialFk(vinculoSocial);
                pessoaFisicaFisicaCollectionPessoaFisicaFisica = em.merge(pessoaFisicaFisicaCollectionPessoaFisicaFisica);
                if (oldVinculoSocialFkOfPessoaFisicaFisicaCollectionPessoaFisicaFisica != null) {
                    oldVinculoSocialFkOfPessoaFisicaFisicaCollectionPessoaFisicaFisica.getPessoaFisicaFisicaCollection().remove(pessoaFisicaFisicaCollectionPessoaFisicaFisica);
                    oldVinculoSocialFkOfPessoaFisicaFisicaCollectionPessoaFisicaFisica = em.merge(oldVinculoSocialFkOfPessoaFisicaFisicaCollectionPessoaFisicaFisica);
                }
            }
            for (PessoaFisicaFisicaHistorico pessoaFisicaFisicaHistoricoCollectionPessoaFisicaFisicaHistorico : vinculoSocial.getPessoaFisicaFisicaHistoricoCollection()) {
                VinculoSocial oldVinculoSocialFkOfPessoaFisicaFisicaHistoricoCollectionPessoaFisicaFisicaHistorico = pessoaFisicaFisicaHistoricoCollectionPessoaFisicaFisicaHistorico.getVinculoSocialFk();
                pessoaFisicaFisicaHistoricoCollectionPessoaFisicaFisicaHistorico.setVinculoSocialFk(vinculoSocial);
                pessoaFisicaFisicaHistoricoCollectionPessoaFisicaFisicaHistorico = em.merge(pessoaFisicaFisicaHistoricoCollectionPessoaFisicaFisicaHistorico);
                if (oldVinculoSocialFkOfPessoaFisicaFisicaHistoricoCollectionPessoaFisicaFisicaHistorico != null) {
                    oldVinculoSocialFkOfPessoaFisicaFisicaHistoricoCollectionPessoaFisicaFisicaHistorico.getPessoaFisicaFisicaHistoricoCollection().remove(pessoaFisicaFisicaHistoricoCollectionPessoaFisicaFisicaHistorico);
                    oldVinculoSocialFkOfPessoaFisicaFisicaHistoricoCollectionPessoaFisicaFisicaHistorico = em.merge(oldVinculoSocialFkOfPessoaFisicaFisicaHistoricoCollectionPessoaFisicaFisicaHistorico);
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

    public void edit(VinculoSocial vinculoSocial) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            VinculoSocial persistentVinculoSocial = em.find(VinculoSocial.class, vinculoSocial.getId());
            Collection<PessoaFisicaFisica> pessoaFisicaFisicaCollectionOld = persistentVinculoSocial.getPessoaFisicaFisicaCollection();
            Collection<PessoaFisicaFisica> pessoaFisicaFisicaCollectionNew = vinculoSocial.getPessoaFisicaFisicaCollection();
            Collection<PessoaFisicaFisicaHistorico> pessoaFisicaFisicaHistoricoCollectionOld = persistentVinculoSocial.getPessoaFisicaFisicaHistoricoCollection();
            Collection<PessoaFisicaFisicaHistorico> pessoaFisicaFisicaHistoricoCollectionNew = vinculoSocial.getPessoaFisicaFisicaHistoricoCollection();
            List<String> illegalOrphanMessages = null;
            for (PessoaFisicaFisica pessoaFisicaFisicaCollectionOldPessoaFisicaFisica : pessoaFisicaFisicaCollectionOld) {
                if (!pessoaFisicaFisicaCollectionNew.contains(pessoaFisicaFisicaCollectionOldPessoaFisicaFisica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PessoaFisicaFisica " + pessoaFisicaFisicaCollectionOldPessoaFisicaFisica + " since its vinculoSocialFk field is not nullable.");
                }
            }
            for (PessoaFisicaFisicaHistorico pessoaFisicaFisicaHistoricoCollectionOldPessoaFisicaFisicaHistorico : pessoaFisicaFisicaHistoricoCollectionOld) {
                if (!pessoaFisicaFisicaHistoricoCollectionNew.contains(pessoaFisicaFisicaHistoricoCollectionOldPessoaFisicaFisicaHistorico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PessoaFisicaFisicaHistorico " + pessoaFisicaFisicaHistoricoCollectionOldPessoaFisicaFisicaHistorico + " since its vinculoSocialFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<PessoaFisicaFisica> attachedPessoaFisicaFisicaCollectionNew = new ArrayList<PessoaFisicaFisica>();
            for (PessoaFisicaFisica pessoaFisicaFisicaCollectionNewPessoaFisicaFisicaToAttach : pessoaFisicaFisicaCollectionNew) {
                pessoaFisicaFisicaCollectionNewPessoaFisicaFisicaToAttach = em.getReference(pessoaFisicaFisicaCollectionNewPessoaFisicaFisicaToAttach.getClass(), pessoaFisicaFisicaCollectionNewPessoaFisicaFisicaToAttach.getId());
                attachedPessoaFisicaFisicaCollectionNew.add(pessoaFisicaFisicaCollectionNewPessoaFisicaFisicaToAttach);
            }
            pessoaFisicaFisicaCollectionNew = attachedPessoaFisicaFisicaCollectionNew;
            vinculoSocial.setPessoaFisicaFisicaCollection(pessoaFisicaFisicaCollectionNew);
            Collection<PessoaFisicaFisicaHistorico> attachedPessoaFisicaFisicaHistoricoCollectionNew = new ArrayList<PessoaFisicaFisicaHistorico>();
            for (PessoaFisicaFisicaHistorico pessoaFisicaFisicaHistoricoCollectionNewPessoaFisicaFisicaHistoricoToAttach : pessoaFisicaFisicaHistoricoCollectionNew) {
                pessoaFisicaFisicaHistoricoCollectionNewPessoaFisicaFisicaHistoricoToAttach = em.getReference(pessoaFisicaFisicaHistoricoCollectionNewPessoaFisicaFisicaHistoricoToAttach.getClass(), pessoaFisicaFisicaHistoricoCollectionNewPessoaFisicaFisicaHistoricoToAttach.getId());
                attachedPessoaFisicaFisicaHistoricoCollectionNew.add(pessoaFisicaFisicaHistoricoCollectionNewPessoaFisicaFisicaHistoricoToAttach);
            }
            pessoaFisicaFisicaHistoricoCollectionNew = attachedPessoaFisicaFisicaHistoricoCollectionNew;
            vinculoSocial.setPessoaFisicaFisicaHistoricoCollection(pessoaFisicaFisicaHistoricoCollectionNew);
            vinculoSocial = em.merge(vinculoSocial);
            for (PessoaFisicaFisica pessoaFisicaFisicaCollectionNewPessoaFisicaFisica : pessoaFisicaFisicaCollectionNew) {
                if (!pessoaFisicaFisicaCollectionOld.contains(pessoaFisicaFisicaCollectionNewPessoaFisicaFisica)) {
                    VinculoSocial oldVinculoSocialFkOfPessoaFisicaFisicaCollectionNewPessoaFisicaFisica = pessoaFisicaFisicaCollectionNewPessoaFisicaFisica.getVinculoSocialFk();
                    pessoaFisicaFisicaCollectionNewPessoaFisicaFisica.setVinculoSocialFk(vinculoSocial);
                    pessoaFisicaFisicaCollectionNewPessoaFisicaFisica = em.merge(pessoaFisicaFisicaCollectionNewPessoaFisicaFisica);
                    if (oldVinculoSocialFkOfPessoaFisicaFisicaCollectionNewPessoaFisicaFisica != null && !oldVinculoSocialFkOfPessoaFisicaFisicaCollectionNewPessoaFisicaFisica.equals(vinculoSocial)) {
                        oldVinculoSocialFkOfPessoaFisicaFisicaCollectionNewPessoaFisicaFisica.getPessoaFisicaFisicaCollection().remove(pessoaFisicaFisicaCollectionNewPessoaFisicaFisica);
                        oldVinculoSocialFkOfPessoaFisicaFisicaCollectionNewPessoaFisicaFisica = em.merge(oldVinculoSocialFkOfPessoaFisicaFisicaCollectionNewPessoaFisicaFisica);
                    }
                }
            }
            for (PessoaFisicaFisicaHistorico pessoaFisicaFisicaHistoricoCollectionNewPessoaFisicaFisicaHistorico : pessoaFisicaFisicaHistoricoCollectionNew) {
                if (!pessoaFisicaFisicaHistoricoCollectionOld.contains(pessoaFisicaFisicaHistoricoCollectionNewPessoaFisicaFisicaHistorico)) {
                    VinculoSocial oldVinculoSocialFkOfPessoaFisicaFisicaHistoricoCollectionNewPessoaFisicaFisicaHistorico = pessoaFisicaFisicaHistoricoCollectionNewPessoaFisicaFisicaHistorico.getVinculoSocialFk();
                    pessoaFisicaFisicaHistoricoCollectionNewPessoaFisicaFisicaHistorico.setVinculoSocialFk(vinculoSocial);
                    pessoaFisicaFisicaHistoricoCollectionNewPessoaFisicaFisicaHistorico = em.merge(pessoaFisicaFisicaHistoricoCollectionNewPessoaFisicaFisicaHistorico);
                    if (oldVinculoSocialFkOfPessoaFisicaFisicaHistoricoCollectionNewPessoaFisicaFisicaHistorico != null && !oldVinculoSocialFkOfPessoaFisicaFisicaHistoricoCollectionNewPessoaFisicaFisicaHistorico.equals(vinculoSocial)) {
                        oldVinculoSocialFkOfPessoaFisicaFisicaHistoricoCollectionNewPessoaFisicaFisicaHistorico.getPessoaFisicaFisicaHistoricoCollection().remove(pessoaFisicaFisicaHistoricoCollectionNewPessoaFisicaFisicaHistorico);
                        oldVinculoSocialFkOfPessoaFisicaFisicaHistoricoCollectionNewPessoaFisicaFisicaHistorico = em.merge(oldVinculoSocialFkOfPessoaFisicaFisicaHistoricoCollectionNewPessoaFisicaFisicaHistorico);
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
                Integer id = vinculoSocial.getId();
                if (findVinculoSocial(id) == null) {
                    throw new NonexistentEntityException("The vinculoSocial with id " + id + " no longer exists.");
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
            VinculoSocial vinculoSocial;
            try {
                vinculoSocial = em.getReference(VinculoSocial.class, id);
                vinculoSocial.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vinculoSocial with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PessoaFisicaFisica> pessoaFisicaFisicaCollectionOrphanCheck = vinculoSocial.getPessoaFisicaFisicaCollection();
            for (PessoaFisicaFisica pessoaFisicaFisicaCollectionOrphanCheckPessoaFisicaFisica : pessoaFisicaFisicaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This VinculoSocial (" + vinculoSocial + ") cannot be destroyed since the PessoaFisicaFisica " + pessoaFisicaFisicaCollectionOrphanCheckPessoaFisicaFisica + " in its pessoaFisicaFisicaCollection field has a non-nullable vinculoSocialFk field.");
            }
            Collection<PessoaFisicaFisicaHistorico> pessoaFisicaFisicaHistoricoCollectionOrphanCheck = vinculoSocial.getPessoaFisicaFisicaHistoricoCollection();
            for (PessoaFisicaFisicaHistorico pessoaFisicaFisicaHistoricoCollectionOrphanCheckPessoaFisicaFisicaHistorico : pessoaFisicaFisicaHistoricoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This VinculoSocial (" + vinculoSocial + ") cannot be destroyed since the PessoaFisicaFisicaHistorico " + pessoaFisicaFisicaHistoricoCollectionOrphanCheckPessoaFisicaFisicaHistorico + " in its pessoaFisicaFisicaHistoricoCollection field has a non-nullable vinculoSocialFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(vinculoSocial);
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

    public List<VinculoSocial> findVinculoSocialEntities() {
        return findVinculoSocialEntities(true, -1, -1);
    }

    public List<VinculoSocial> findVinculoSocialEntities(int maxResults, int firstResult) {
        return findVinculoSocialEntities(false, maxResults, firstResult);
    }

    private List<VinculoSocial> findVinculoSocialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VinculoSocial.class));
            Root<VinculoSocial> from = cq.from(VinculoSocial.class);
            cq.orderBy(em.getCriteriaBuilder().asc(from.get("vinculo")));
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

    public VinculoSocial findVinculoSocial(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VinculoSocial.class, id);
        } finally {
            em.close();
        }
    }

    public int getVinculoSocialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VinculoSocial> rt = cq.from(VinculoSocial.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
