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
import entidade.PessoaJuridicaHistorico;
import entidade.TipoEmpresarial;
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
public class TipoEmpresarialDAO implements Serializable {

    public TipoEmpresarialDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoEmpresarial tipoEmpresarial) throws RollbackFailureException, Exception {
        if (tipoEmpresarial.getPessoaJuridicaCollection() == null) {
            tipoEmpresarial.setPessoaJuridicaCollection(new ArrayList<PessoaJuridica>());
        }
        if (tipoEmpresarial.getPessoaJuridicaHistoricoCollection() == null) {
            tipoEmpresarial.setPessoaJuridicaHistoricoCollection(new ArrayList<PessoaJuridicaHistorico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<PessoaJuridica> attachedPessoaJuridicaCollection = new ArrayList<PessoaJuridica>();
            for (PessoaJuridica pessoaJuridicaCollectionPessoaJuridicaToAttach : tipoEmpresarial.getPessoaJuridicaCollection()) {
                pessoaJuridicaCollectionPessoaJuridicaToAttach = em.getReference(pessoaJuridicaCollectionPessoaJuridicaToAttach.getClass(), pessoaJuridicaCollectionPessoaJuridicaToAttach.getId());
                attachedPessoaJuridicaCollection.add(pessoaJuridicaCollectionPessoaJuridicaToAttach);
            }
            tipoEmpresarial.setPessoaJuridicaCollection(attachedPessoaJuridicaCollection);
            Collection<PessoaJuridicaHistorico> attachedPessoaJuridicaHistoricoCollection = new ArrayList<PessoaJuridicaHistorico>();
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollectionPessoaJuridicaHistoricoToAttach : tipoEmpresarial.getPessoaJuridicaHistoricoCollection()) {
                pessoaJuridicaHistoricoCollectionPessoaJuridicaHistoricoToAttach = em.getReference(pessoaJuridicaHistoricoCollectionPessoaJuridicaHistoricoToAttach.getClass(), pessoaJuridicaHistoricoCollectionPessoaJuridicaHistoricoToAttach.getId());
                attachedPessoaJuridicaHistoricoCollection.add(pessoaJuridicaHistoricoCollectionPessoaJuridicaHistoricoToAttach);
            }
            tipoEmpresarial.setPessoaJuridicaHistoricoCollection(attachedPessoaJuridicaHistoricoCollection);
            em.persist(tipoEmpresarial);
            for (PessoaJuridica pessoaJuridicaCollectionPessoaJuridica : tipoEmpresarial.getPessoaJuridicaCollection()) {
                TipoEmpresarial oldTipoEmpresarialFkOfPessoaJuridicaCollectionPessoaJuridica = pessoaJuridicaCollectionPessoaJuridica.getTipoEmpresarialFk();
                pessoaJuridicaCollectionPessoaJuridica.setTipoEmpresarialFk(tipoEmpresarial);
                pessoaJuridicaCollectionPessoaJuridica = em.merge(pessoaJuridicaCollectionPessoaJuridica);
                if (oldTipoEmpresarialFkOfPessoaJuridicaCollectionPessoaJuridica != null) {
                    oldTipoEmpresarialFkOfPessoaJuridicaCollectionPessoaJuridica.getPessoaJuridicaCollection().remove(pessoaJuridicaCollectionPessoaJuridica);
                    oldTipoEmpresarialFkOfPessoaJuridicaCollectionPessoaJuridica = em.merge(oldTipoEmpresarialFkOfPessoaJuridicaCollectionPessoaJuridica);
                }
            }
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico : tipoEmpresarial.getPessoaJuridicaHistoricoCollection()) {
                TipoEmpresarial oldTipoEmpresarialFkOfPessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico = pessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico.getTipoEmpresarialFk();
                pessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico.setTipoEmpresarialFk(tipoEmpresarial);
                pessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico = em.merge(pessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico);
                if (oldTipoEmpresarialFkOfPessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico != null) {
                    oldTipoEmpresarialFkOfPessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico.getPessoaJuridicaHistoricoCollection().remove(pessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico);
                    oldTipoEmpresarialFkOfPessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico = em.merge(oldTipoEmpresarialFkOfPessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico);
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

    public void edit(TipoEmpresarial tipoEmpresarial) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEmpresarial persistentTipoEmpresarial = em.find(TipoEmpresarial.class, tipoEmpresarial.getId());
            Collection<PessoaJuridica> pessoaJuridicaCollectionOld = persistentTipoEmpresarial.getPessoaJuridicaCollection();
            Collection<PessoaJuridica> pessoaJuridicaCollectionNew = tipoEmpresarial.getPessoaJuridicaCollection();
            Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollectionOld = persistentTipoEmpresarial.getPessoaJuridicaHistoricoCollection();
            Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollectionNew = tipoEmpresarial.getPessoaJuridicaHistoricoCollection();
            Collection<PessoaJuridica> attachedPessoaJuridicaCollectionNew = new ArrayList<PessoaJuridica>();
            for (PessoaJuridica pessoaJuridicaCollectionNewPessoaJuridicaToAttach : pessoaJuridicaCollectionNew) {
                pessoaJuridicaCollectionNewPessoaJuridicaToAttach = em.getReference(pessoaJuridicaCollectionNewPessoaJuridicaToAttach.getClass(), pessoaJuridicaCollectionNewPessoaJuridicaToAttach.getId());
                attachedPessoaJuridicaCollectionNew.add(pessoaJuridicaCollectionNewPessoaJuridicaToAttach);
            }
            pessoaJuridicaCollectionNew = attachedPessoaJuridicaCollectionNew;
            tipoEmpresarial.setPessoaJuridicaCollection(pessoaJuridicaCollectionNew);
            Collection<PessoaJuridicaHistorico> attachedPessoaJuridicaHistoricoCollectionNew = new ArrayList<PessoaJuridicaHistorico>();
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistoricoToAttach : pessoaJuridicaHistoricoCollectionNew) {
                pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistoricoToAttach = em.getReference(pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistoricoToAttach.getClass(), pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistoricoToAttach.getId());
                attachedPessoaJuridicaHistoricoCollectionNew.add(pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistoricoToAttach);
            }
            pessoaJuridicaHistoricoCollectionNew = attachedPessoaJuridicaHistoricoCollectionNew;
            tipoEmpresarial.setPessoaJuridicaHistoricoCollection(pessoaJuridicaHistoricoCollectionNew);
            tipoEmpresarial = em.merge(tipoEmpresarial);
            for (PessoaJuridica pessoaJuridicaCollectionOldPessoaJuridica : pessoaJuridicaCollectionOld) {
                if (!pessoaJuridicaCollectionNew.contains(pessoaJuridicaCollectionOldPessoaJuridica)) {
                    pessoaJuridicaCollectionOldPessoaJuridica.setTipoEmpresarialFk(null);
                    pessoaJuridicaCollectionOldPessoaJuridica = em.merge(pessoaJuridicaCollectionOldPessoaJuridica);
                }
            }
            for (PessoaJuridica pessoaJuridicaCollectionNewPessoaJuridica : pessoaJuridicaCollectionNew) {
                if (!pessoaJuridicaCollectionOld.contains(pessoaJuridicaCollectionNewPessoaJuridica)) {
                    TipoEmpresarial oldTipoEmpresarialFkOfPessoaJuridicaCollectionNewPessoaJuridica = pessoaJuridicaCollectionNewPessoaJuridica.getTipoEmpresarialFk();
                    pessoaJuridicaCollectionNewPessoaJuridica.setTipoEmpresarialFk(tipoEmpresarial);
                    pessoaJuridicaCollectionNewPessoaJuridica = em.merge(pessoaJuridicaCollectionNewPessoaJuridica);
                    if (oldTipoEmpresarialFkOfPessoaJuridicaCollectionNewPessoaJuridica != null && !oldTipoEmpresarialFkOfPessoaJuridicaCollectionNewPessoaJuridica.equals(tipoEmpresarial)) {
                        oldTipoEmpresarialFkOfPessoaJuridicaCollectionNewPessoaJuridica.getPessoaJuridicaCollection().remove(pessoaJuridicaCollectionNewPessoaJuridica);
                        oldTipoEmpresarialFkOfPessoaJuridicaCollectionNewPessoaJuridica = em.merge(oldTipoEmpresarialFkOfPessoaJuridicaCollectionNewPessoaJuridica);
                    }
                }
            }
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollectionOldPessoaJuridicaHistorico : pessoaJuridicaHistoricoCollectionOld) {
                if (!pessoaJuridicaHistoricoCollectionNew.contains(pessoaJuridicaHistoricoCollectionOldPessoaJuridicaHistorico)) {
                    pessoaJuridicaHistoricoCollectionOldPessoaJuridicaHistorico.setTipoEmpresarialFk(null);
                    pessoaJuridicaHistoricoCollectionOldPessoaJuridicaHistorico = em.merge(pessoaJuridicaHistoricoCollectionOldPessoaJuridicaHistorico);
                }
            }
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico : pessoaJuridicaHistoricoCollectionNew) {
                if (!pessoaJuridicaHistoricoCollectionOld.contains(pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico)) {
                    TipoEmpresarial oldTipoEmpresarialFkOfPessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico = pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico.getTipoEmpresarialFk();
                    pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico.setTipoEmpresarialFk(tipoEmpresarial);
                    pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico = em.merge(pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico);
                    if (oldTipoEmpresarialFkOfPessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico != null && !oldTipoEmpresarialFkOfPessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico.equals(tipoEmpresarial)) {
                        oldTipoEmpresarialFkOfPessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico.getPessoaJuridicaHistoricoCollection().remove(pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico);
                        oldTipoEmpresarialFkOfPessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico = em.merge(oldTipoEmpresarialFkOfPessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico);
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
                Integer id = tipoEmpresarial.getId();
                if (findTipoEmpresarial(id) == null) {
                    throw new NonexistentEntityException("The tipoEmpresarial with id " + id + " no longer exists.");
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
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEmpresarial tipoEmpresarial;
            try {
                tipoEmpresarial = em.getReference(TipoEmpresarial.class, id);
                tipoEmpresarial.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoEmpresarial with id " + id + " no longer exists.", enfe);
            }
            Collection<PessoaJuridica> pessoaJuridicaCollection = tipoEmpresarial.getPessoaJuridicaCollection();
            for (PessoaJuridica pessoaJuridicaCollectionPessoaJuridica : pessoaJuridicaCollection) {
                pessoaJuridicaCollectionPessoaJuridica.setTipoEmpresarialFk(null);
                pessoaJuridicaCollectionPessoaJuridica = em.merge(pessoaJuridicaCollectionPessoaJuridica);
            }
            Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollection = tipoEmpresarial.getPessoaJuridicaHistoricoCollection();
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico : pessoaJuridicaHistoricoCollection) {
                pessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico.setTipoEmpresarialFk(null);
                pessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico = em.merge(pessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico);
            }
            em.remove(tipoEmpresarial);
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

    public List<TipoEmpresarial> findTipoEmpresarialEntities() {
        return findTipoEmpresarialEntities(true, -1, -1);
    }

    public List<TipoEmpresarial> findTipoEmpresarialEntities(int maxResults, int firstResult) {
        return findTipoEmpresarialEntities(false, maxResults, firstResult);
    }

    private List<TipoEmpresarial> findTipoEmpresarialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoEmpresarial.class));
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

    public TipoEmpresarial findTipoEmpresarial(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoEmpresarial.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoEmpresarialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoEmpresarial> rt = cq.from(TipoEmpresarial.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
