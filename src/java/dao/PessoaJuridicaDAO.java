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
import entidade.PessoaJuridica;
import entidade.TipoEmpresarial;
import java.util.ArrayList;
import java.util.Collection;
import entidade.PessoaFisicaJuridica;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author paulones
 */
public class PessoaJuridicaDAO implements Serializable {

    public PessoaJuridicaDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PessoaJuridica pessoaJuridica) throws RollbackFailureException, Exception {
        if (pessoaJuridica.getPessoaJuridicaCollection() == null) {
            pessoaJuridica.setPessoaJuridicaCollection(new ArrayList<PessoaJuridica>());
        }
        if (pessoaJuridica.getPessoaFisicaJuridicaCollection() == null) {
            pessoaJuridica.setPessoaFisicaJuridicaCollection(new ArrayList<PessoaFisicaJuridica>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PessoaJuridica sucessaoFk = pessoaJuridica.getSucessaoFk();
            if (sucessaoFk != null) {
                sucessaoFk = em.getReference(sucessaoFk.getClass(), sucessaoFk.getId());
                pessoaJuridica.setSucessaoFk(sucessaoFk);
            }
            TipoEmpresarial tipoEmpresarialFk = pessoaJuridica.getTipoEmpresarialFk();
            if (tipoEmpresarialFk != null) {
                tipoEmpresarialFk = em.getReference(tipoEmpresarialFk.getClass(), tipoEmpresarialFk.getId());
                pessoaJuridica.setTipoEmpresarialFk(tipoEmpresarialFk);
            }
            Collection<PessoaJuridica> attachedPessoaJuridicaCollection = new ArrayList<PessoaJuridica>();
            for (PessoaJuridica pessoaJuridicaCollectionPessoaJuridicaToAttach : pessoaJuridica.getPessoaJuridicaCollection()) {
                pessoaJuridicaCollectionPessoaJuridicaToAttach = em.getReference(pessoaJuridicaCollectionPessoaJuridicaToAttach.getClass(), pessoaJuridicaCollectionPessoaJuridicaToAttach.getId());
                attachedPessoaJuridicaCollection.add(pessoaJuridicaCollectionPessoaJuridicaToAttach);
            }
            pessoaJuridica.setPessoaJuridicaCollection(attachedPessoaJuridicaCollection);
            Collection<PessoaFisicaJuridica> attachedPessoaFisicaJuridicaCollection = new ArrayList<PessoaFisicaJuridica>();
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionPessoaFisicaJuridicaToAttach : pessoaJuridica.getPessoaFisicaJuridicaCollection()) {
                pessoaFisicaJuridicaCollectionPessoaFisicaJuridicaToAttach = em.getReference(pessoaFisicaJuridicaCollectionPessoaFisicaJuridicaToAttach.getClass(), pessoaFisicaJuridicaCollectionPessoaFisicaJuridicaToAttach.getId());
                attachedPessoaFisicaJuridicaCollection.add(pessoaFisicaJuridicaCollectionPessoaFisicaJuridicaToAttach);
            }
            pessoaJuridica.setPessoaFisicaJuridicaCollection(attachedPessoaFisicaJuridicaCollection);
            em.persist(pessoaJuridica);
            if (sucessaoFk != null) {
                sucessaoFk.getPessoaJuridicaCollection().add(pessoaJuridica);
                sucessaoFk = em.merge(sucessaoFk);
            }
            if (tipoEmpresarialFk != null) {
                tipoEmpresarialFk.getPessoaJuridicaCollection().add(pessoaJuridica);
                tipoEmpresarialFk = em.merge(tipoEmpresarialFk);
            }
            for (PessoaJuridica pessoaJuridicaCollectionPessoaJuridica : pessoaJuridica.getPessoaJuridicaCollection()) {
                PessoaJuridica oldSucessaoFkOfPessoaJuridicaCollectionPessoaJuridica = pessoaJuridicaCollectionPessoaJuridica.getSucessaoFk();
                pessoaJuridicaCollectionPessoaJuridica.setSucessaoFk(pessoaJuridica);
                pessoaJuridicaCollectionPessoaJuridica = em.merge(pessoaJuridicaCollectionPessoaJuridica);
                if (oldSucessaoFkOfPessoaJuridicaCollectionPessoaJuridica != null) {
                    oldSucessaoFkOfPessoaJuridicaCollectionPessoaJuridica.getPessoaJuridicaCollection().remove(pessoaJuridicaCollectionPessoaJuridica);
                    oldSucessaoFkOfPessoaJuridicaCollectionPessoaJuridica = em.merge(oldSucessaoFkOfPessoaJuridicaCollectionPessoaJuridica);
                }
            }
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionPessoaFisicaJuridica : pessoaJuridica.getPessoaFisicaJuridicaCollection()) {
                PessoaJuridica oldPessoaJuridicaFkOfPessoaFisicaJuridicaCollectionPessoaFisicaJuridica = pessoaFisicaJuridicaCollectionPessoaFisicaJuridica.getPessoaJuridicaFk();
                pessoaFisicaJuridicaCollectionPessoaFisicaJuridica.setPessoaJuridicaFk(pessoaJuridica);
                pessoaFisicaJuridicaCollectionPessoaFisicaJuridica = em.merge(pessoaFisicaJuridicaCollectionPessoaFisicaJuridica);
                if (oldPessoaJuridicaFkOfPessoaFisicaJuridicaCollectionPessoaFisicaJuridica != null) {
                    oldPessoaJuridicaFkOfPessoaFisicaJuridicaCollectionPessoaFisicaJuridica.getPessoaFisicaJuridicaCollection().remove(pessoaFisicaJuridicaCollectionPessoaFisicaJuridica);
                    oldPessoaJuridicaFkOfPessoaFisicaJuridicaCollectionPessoaFisicaJuridica = em.merge(oldPessoaJuridicaFkOfPessoaFisicaJuridicaCollectionPessoaFisicaJuridica);
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

    public void edit(PessoaJuridica pessoaJuridica) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PessoaJuridica persistentPessoaJuridica = em.find(PessoaJuridica.class, pessoaJuridica.getId());
            PessoaJuridica sucessaoFkOld = persistentPessoaJuridica.getSucessaoFk();
            PessoaJuridica sucessaoFkNew = pessoaJuridica.getSucessaoFk();
            TipoEmpresarial tipoEmpresarialFkOld = persistentPessoaJuridica.getTipoEmpresarialFk();
            TipoEmpresarial tipoEmpresarialFkNew = pessoaJuridica.getTipoEmpresarialFk();
            Collection<PessoaJuridica> pessoaJuridicaCollectionOld = persistentPessoaJuridica.getPessoaJuridicaCollection();
            Collection<PessoaJuridica> pessoaJuridicaCollectionNew = pessoaJuridica.getPessoaJuridicaCollection();
            Collection<PessoaFisicaJuridica> pessoaFisicaJuridicaCollectionOld = persistentPessoaJuridica.getPessoaFisicaJuridicaCollection();
            Collection<PessoaFisicaJuridica> pessoaFisicaJuridicaCollectionNew = pessoaJuridica.getPessoaFisicaJuridicaCollection();
            List<String> illegalOrphanMessages = null;
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionOldPessoaFisicaJuridica : pessoaFisicaJuridicaCollectionOld) {
                if (!pessoaFisicaJuridicaCollectionNew.contains(pessoaFisicaJuridicaCollectionOldPessoaFisicaJuridica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PessoaFisicaJuridica " + pessoaFisicaJuridicaCollectionOldPessoaFisicaJuridica + " since its pessoaJuridicaFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sucessaoFkNew != null) {
                sucessaoFkNew = em.getReference(sucessaoFkNew.getClass(), sucessaoFkNew.getId());
                pessoaJuridica.setSucessaoFk(sucessaoFkNew);
            }
            if (tipoEmpresarialFkNew != null) {
                tipoEmpresarialFkNew = em.getReference(tipoEmpresarialFkNew.getClass(), tipoEmpresarialFkNew.getId());
                pessoaJuridica.setTipoEmpresarialFk(tipoEmpresarialFkNew);
            }
            Collection<PessoaJuridica> attachedPessoaJuridicaCollectionNew = new ArrayList<PessoaJuridica>();
            for (PessoaJuridica pessoaJuridicaCollectionNewPessoaJuridicaToAttach : pessoaJuridicaCollectionNew) {
                pessoaJuridicaCollectionNewPessoaJuridicaToAttach = em.getReference(pessoaJuridicaCollectionNewPessoaJuridicaToAttach.getClass(), pessoaJuridicaCollectionNewPessoaJuridicaToAttach.getId());
                attachedPessoaJuridicaCollectionNew.add(pessoaJuridicaCollectionNewPessoaJuridicaToAttach);
            }
            pessoaJuridicaCollectionNew = attachedPessoaJuridicaCollectionNew;
            pessoaJuridica.setPessoaJuridicaCollection(pessoaJuridicaCollectionNew);
            Collection<PessoaFisicaJuridica> attachedPessoaFisicaJuridicaCollectionNew = new ArrayList<PessoaFisicaJuridica>();
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridicaToAttach : pessoaFisicaJuridicaCollectionNew) {
                pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridicaToAttach = em.getReference(pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridicaToAttach.getClass(), pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridicaToAttach.getId());
                attachedPessoaFisicaJuridicaCollectionNew.add(pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridicaToAttach);
            }
            pessoaFisicaJuridicaCollectionNew = attachedPessoaFisicaJuridicaCollectionNew;
            pessoaJuridica.setPessoaFisicaJuridicaCollection(pessoaFisicaJuridicaCollectionNew);
            pessoaJuridica = em.merge(pessoaJuridica);
            if (sucessaoFkOld != null && !sucessaoFkOld.equals(sucessaoFkNew)) {
                sucessaoFkOld.getPessoaJuridicaCollection().remove(pessoaJuridica);
                sucessaoFkOld = em.merge(sucessaoFkOld);
            }
            if (sucessaoFkNew != null && !sucessaoFkNew.equals(sucessaoFkOld)) {
                sucessaoFkNew.getPessoaJuridicaCollection().add(pessoaJuridica);
                sucessaoFkNew = em.merge(sucessaoFkNew);
            }
            if (tipoEmpresarialFkOld != null && !tipoEmpresarialFkOld.equals(tipoEmpresarialFkNew)) {
                tipoEmpresarialFkOld.getPessoaJuridicaCollection().remove(pessoaJuridica);
                tipoEmpresarialFkOld = em.merge(tipoEmpresarialFkOld);
            }
            if (tipoEmpresarialFkNew != null && !tipoEmpresarialFkNew.equals(tipoEmpresarialFkOld)) {
                tipoEmpresarialFkNew.getPessoaJuridicaCollection().add(pessoaJuridica);
                tipoEmpresarialFkNew = em.merge(tipoEmpresarialFkNew);
            }
            for (PessoaJuridica pessoaJuridicaCollectionOldPessoaJuridica : pessoaJuridicaCollectionOld) {
                if (!pessoaJuridicaCollectionNew.contains(pessoaJuridicaCollectionOldPessoaJuridica)) {
                    pessoaJuridicaCollectionOldPessoaJuridica.setSucessaoFk(null);
                    pessoaJuridicaCollectionOldPessoaJuridica = em.merge(pessoaJuridicaCollectionOldPessoaJuridica);
                }
            }
            for (PessoaJuridica pessoaJuridicaCollectionNewPessoaJuridica : pessoaJuridicaCollectionNew) {
                if (!pessoaJuridicaCollectionOld.contains(pessoaJuridicaCollectionNewPessoaJuridica)) {
                    PessoaJuridica oldSucessaoFkOfPessoaJuridicaCollectionNewPessoaJuridica = pessoaJuridicaCollectionNewPessoaJuridica.getSucessaoFk();
                    pessoaJuridicaCollectionNewPessoaJuridica.setSucessaoFk(pessoaJuridica);
                    pessoaJuridicaCollectionNewPessoaJuridica = em.merge(pessoaJuridicaCollectionNewPessoaJuridica);
                    if (oldSucessaoFkOfPessoaJuridicaCollectionNewPessoaJuridica != null && !oldSucessaoFkOfPessoaJuridicaCollectionNewPessoaJuridica.equals(pessoaJuridica)) {
                        oldSucessaoFkOfPessoaJuridicaCollectionNewPessoaJuridica.getPessoaJuridicaCollection().remove(pessoaJuridicaCollectionNewPessoaJuridica);
                        oldSucessaoFkOfPessoaJuridicaCollectionNewPessoaJuridica = em.merge(oldSucessaoFkOfPessoaJuridicaCollectionNewPessoaJuridica);
                    }
                }
            }
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica : pessoaFisicaJuridicaCollectionNew) {
                if (!pessoaFisicaJuridicaCollectionOld.contains(pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica)) {
                    PessoaJuridica oldPessoaJuridicaFkOfPessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica = pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica.getPessoaJuridicaFk();
                    pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica.setPessoaJuridicaFk(pessoaJuridica);
                    pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica = em.merge(pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica);
                    if (oldPessoaJuridicaFkOfPessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica != null && !oldPessoaJuridicaFkOfPessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica.equals(pessoaJuridica)) {
                        oldPessoaJuridicaFkOfPessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica.getPessoaFisicaJuridicaCollection().remove(pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica);
                        oldPessoaJuridicaFkOfPessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica = em.merge(oldPessoaJuridicaFkOfPessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica);
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
                Integer id = pessoaJuridica.getId();
                if (findPessoaJuridica(id) == null) {
                    throw new NonexistentEntityException("The pessoaJuridica with id " + id + " no longer exists.");
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
            PessoaJuridica pessoaJuridica;
            try {
                pessoaJuridica = em.getReference(PessoaJuridica.class, id);
                pessoaJuridica.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pessoaJuridica with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PessoaFisicaJuridica> pessoaFisicaJuridicaCollectionOrphanCheck = pessoaJuridica.getPessoaFisicaJuridicaCollection();
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionOrphanCheckPessoaFisicaJuridica : pessoaFisicaJuridicaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PessoaJuridica (" + pessoaJuridica + ") cannot be destroyed since the PessoaFisicaJuridica " + pessoaFisicaJuridicaCollectionOrphanCheckPessoaFisicaJuridica + " in its pessoaFisicaJuridicaCollection field has a non-nullable pessoaJuridicaFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            PessoaJuridica sucessaoFk = pessoaJuridica.getSucessaoFk();
            if (sucessaoFk != null) {
                sucessaoFk.getPessoaJuridicaCollection().remove(pessoaJuridica);
                sucessaoFk = em.merge(sucessaoFk);
            }
            TipoEmpresarial tipoEmpresarialFk = pessoaJuridica.getTipoEmpresarialFk();
            if (tipoEmpresarialFk != null) {
                tipoEmpresarialFk.getPessoaJuridicaCollection().remove(pessoaJuridica);
                tipoEmpresarialFk = em.merge(tipoEmpresarialFk);
            }
            Collection<PessoaJuridica> pessoaJuridicaCollection = pessoaJuridica.getPessoaJuridicaCollection();
            for (PessoaJuridica pessoaJuridicaCollectionPessoaJuridica : pessoaJuridicaCollection) {
                pessoaJuridicaCollectionPessoaJuridica.setSucessaoFk(null);
                pessoaJuridicaCollectionPessoaJuridica = em.merge(pessoaJuridicaCollectionPessoaJuridica);
            }
            em.remove(pessoaJuridica);
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

    public List<PessoaJuridica> findPessoaJuridicaEntities() {
        return findPessoaJuridicaEntities(true, -1, -1);
    }

    public List<PessoaJuridica> findPessoaJuridicaEntities(int maxResults, int firstResult) {
        return findPessoaJuridicaEntities(false, maxResults, firstResult);
    }

    private List<PessoaJuridica> findPessoaJuridicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PessoaJuridica.class));
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

    public PessoaJuridica findPessoaJuridica(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PessoaJuridica.class, id);
        } finally {
            em.close();
        }
    }

    public int getPessoaJuridicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PessoaJuridica> rt = cq.from(PessoaJuridica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
