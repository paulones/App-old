/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.Cidade;
import entidade.Endereco;
import entidade.EnderecoHistorico;
import entidade.Estado;
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
public class CidadeDAO implements Serializable {

    public CidadeDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cidade cidade) throws RollbackFailureException, Exception {
        if (cidade.getPessoaFisicaCollection() == null) {
            cidade.setPessoaFisicaCollection(new ArrayList<PessoaFisica>());
        }
        if (cidade.getEnderecoCollection() == null) {
            cidade.setEnderecoCollection(new ArrayList<Endereco>());
        }
        if (cidade.getPessoaFisicaHistoricoCollection() == null) {
            cidade.setPessoaFisicaHistoricoCollection(new ArrayList<PessoaFisicaHistorico>());
        }
        if (cidade.getEnderecoHistoricoCollection() == null) {
            cidade.setEnderecoHistoricoCollection(new ArrayList<EnderecoHistorico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado estadoFk = cidade.getEstadoFk();
            if (estadoFk != null) {
                estadoFk = em.getReference(estadoFk.getClass(), estadoFk.getId());
                cidade.setEstadoFk(estadoFk);
            }
            Collection<PessoaFisica> attachedPessoaFisicaCollection = new ArrayList<PessoaFisica>();
            for (PessoaFisica pessoaFisicaCollectionPessoaFisicaToAttach : cidade.getPessoaFisicaCollection()) {
                pessoaFisicaCollectionPessoaFisicaToAttach = em.getReference(pessoaFisicaCollectionPessoaFisicaToAttach.getClass(), pessoaFisicaCollectionPessoaFisicaToAttach.getId());
                attachedPessoaFisicaCollection.add(pessoaFisicaCollectionPessoaFisicaToAttach);
            }
            cidade.setPessoaFisicaCollection(attachedPessoaFisicaCollection);
            Collection<Endereco> attachedEnderecoCollection = new ArrayList<Endereco>();
            for (Endereco enderecoCollectionEnderecoToAttach : cidade.getEnderecoCollection()) {
                enderecoCollectionEnderecoToAttach = em.getReference(enderecoCollectionEnderecoToAttach.getClass(), enderecoCollectionEnderecoToAttach.getId());
                attachedEnderecoCollection.add(enderecoCollectionEnderecoToAttach);
            }
            cidade.setEnderecoCollection(attachedEnderecoCollection);
            Collection<PessoaFisicaHistorico> attachedPessoaFisicaHistoricoCollection = new ArrayList<PessoaFisicaHistorico>();
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach : cidade.getPessoaFisicaHistoricoCollection()) {
                pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach = em.getReference(pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach.getClass(), pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach.getId());
                attachedPessoaFisicaHistoricoCollection.add(pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach);
            }
            cidade.setPessoaFisicaHistoricoCollection(attachedPessoaFisicaHistoricoCollection);
            Collection<EnderecoHistorico> attachedEnderecoHistoricoCollection = new ArrayList<EnderecoHistorico>();
            for (EnderecoHistorico enderecoHistoricoCollectionEnderecoHistoricoToAttach : cidade.getEnderecoHistoricoCollection()) {
                enderecoHistoricoCollectionEnderecoHistoricoToAttach = em.getReference(enderecoHistoricoCollectionEnderecoHistoricoToAttach.getClass(), enderecoHistoricoCollectionEnderecoHistoricoToAttach.getId());
                attachedEnderecoHistoricoCollection.add(enderecoHistoricoCollectionEnderecoHistoricoToAttach);
            }
            cidade.setEnderecoHistoricoCollection(attachedEnderecoHistoricoCollection);
            em.persist(cidade);
            if (estadoFk != null) {
                estadoFk.getCidadeCollection().add(cidade);
                estadoFk = em.merge(estadoFk);
            }
            for (PessoaFisica pessoaFisicaCollectionPessoaFisica : cidade.getPessoaFisicaCollection()) {
                Cidade oldCidadeFkOfPessoaFisicaCollectionPessoaFisica = pessoaFisicaCollectionPessoaFisica.getCidadeFk();
                pessoaFisicaCollectionPessoaFisica.setCidadeFk(cidade);
                pessoaFisicaCollectionPessoaFisica = em.merge(pessoaFisicaCollectionPessoaFisica);
                if (oldCidadeFkOfPessoaFisicaCollectionPessoaFisica != null) {
                    oldCidadeFkOfPessoaFisicaCollectionPessoaFisica.getPessoaFisicaCollection().remove(pessoaFisicaCollectionPessoaFisica);
                    oldCidadeFkOfPessoaFisicaCollectionPessoaFisica = em.merge(oldCidadeFkOfPessoaFisicaCollectionPessoaFisica);
                }
            }
            for (Endereco enderecoCollectionEndereco : cidade.getEnderecoCollection()) {
                Cidade oldCidadeFkOfEnderecoCollectionEndereco = enderecoCollectionEndereco.getCidadeFk();
                enderecoCollectionEndereco.setCidadeFk(cidade);
                enderecoCollectionEndereco = em.merge(enderecoCollectionEndereco);
                if (oldCidadeFkOfEnderecoCollectionEndereco != null) {
                    oldCidadeFkOfEnderecoCollectionEndereco.getEnderecoCollection().remove(enderecoCollectionEndereco);
                    oldCidadeFkOfEnderecoCollectionEndereco = em.merge(oldCidadeFkOfEnderecoCollectionEndereco);
                }
            }
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionPessoaFisicaHistorico : cidade.getPessoaFisicaHistoricoCollection()) {
                Cidade oldCidadeFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico = pessoaFisicaHistoricoCollectionPessoaFisicaHistorico.getCidadeFk();
                pessoaFisicaHistoricoCollectionPessoaFisicaHistorico.setCidadeFk(cidade);
                pessoaFisicaHistoricoCollectionPessoaFisicaHistorico = em.merge(pessoaFisicaHistoricoCollectionPessoaFisicaHistorico);
                if (oldCidadeFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico != null) {
                    oldCidadeFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistoricoCollectionPessoaFisicaHistorico);
                    oldCidadeFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico = em.merge(oldCidadeFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico);
                }
            }
            for (EnderecoHistorico enderecoHistoricoCollectionEnderecoHistorico : cidade.getEnderecoHistoricoCollection()) {
                Cidade oldCidadeFkOfEnderecoHistoricoCollectionEnderecoHistorico = enderecoHistoricoCollectionEnderecoHistorico.getCidadeFk();
                enderecoHistoricoCollectionEnderecoHistorico.setCidadeFk(cidade);
                enderecoHistoricoCollectionEnderecoHistorico = em.merge(enderecoHistoricoCollectionEnderecoHistorico);
                if (oldCidadeFkOfEnderecoHistoricoCollectionEnderecoHistorico != null) {
                    oldCidadeFkOfEnderecoHistoricoCollectionEnderecoHistorico.getEnderecoHistoricoCollection().remove(enderecoHistoricoCollectionEnderecoHistorico);
                    oldCidadeFkOfEnderecoHistoricoCollectionEnderecoHistorico = em.merge(oldCidadeFkOfEnderecoHistoricoCollectionEnderecoHistorico);
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

    public void edit(Cidade cidade) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cidade persistentCidade = em.find(Cidade.class, cidade.getId());
            Estado estadoFkOld = persistentCidade.getEstadoFk();
            Estado estadoFkNew = cidade.getEstadoFk();
            Collection<PessoaFisica> pessoaFisicaCollectionOld = persistentCidade.getPessoaFisicaCollection();
            Collection<PessoaFisica> pessoaFisicaCollectionNew = cidade.getPessoaFisicaCollection();
            Collection<Endereco> enderecoCollectionOld = persistentCidade.getEnderecoCollection();
            Collection<Endereco> enderecoCollectionNew = cidade.getEnderecoCollection();
            Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollectionOld = persistentCidade.getPessoaFisicaHistoricoCollection();
            Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollectionNew = cidade.getPessoaFisicaHistoricoCollection();
            Collection<EnderecoHistorico> enderecoHistoricoCollectionOld = persistentCidade.getEnderecoHistoricoCollection();
            Collection<EnderecoHistorico> enderecoHistoricoCollectionNew = cidade.getEnderecoHistoricoCollection();
            if (estadoFkNew != null) {
                estadoFkNew = em.getReference(estadoFkNew.getClass(), estadoFkNew.getId());
                cidade.setEstadoFk(estadoFkNew);
            }
            Collection<PessoaFisica> attachedPessoaFisicaCollectionNew = new ArrayList<PessoaFisica>();
            for (PessoaFisica pessoaFisicaCollectionNewPessoaFisicaToAttach : pessoaFisicaCollectionNew) {
                pessoaFisicaCollectionNewPessoaFisicaToAttach = em.getReference(pessoaFisicaCollectionNewPessoaFisicaToAttach.getClass(), pessoaFisicaCollectionNewPessoaFisicaToAttach.getId());
                attachedPessoaFisicaCollectionNew.add(pessoaFisicaCollectionNewPessoaFisicaToAttach);
            }
            pessoaFisicaCollectionNew = attachedPessoaFisicaCollectionNew;
            cidade.setPessoaFisicaCollection(pessoaFisicaCollectionNew);
            Collection<Endereco> attachedEnderecoCollectionNew = new ArrayList<Endereco>();
            for (Endereco enderecoCollectionNewEnderecoToAttach : enderecoCollectionNew) {
                enderecoCollectionNewEnderecoToAttach = em.getReference(enderecoCollectionNewEnderecoToAttach.getClass(), enderecoCollectionNewEnderecoToAttach.getId());
                attachedEnderecoCollectionNew.add(enderecoCollectionNewEnderecoToAttach);
            }
            enderecoCollectionNew = attachedEnderecoCollectionNew;
            cidade.setEnderecoCollection(enderecoCollectionNew);
            Collection<PessoaFisicaHistorico> attachedPessoaFisicaHistoricoCollectionNew = new ArrayList<PessoaFisicaHistorico>();
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach : pessoaFisicaHistoricoCollectionNew) {
                pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach = em.getReference(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach.getClass(), pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach.getId());
                attachedPessoaFisicaHistoricoCollectionNew.add(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach);
            }
            pessoaFisicaHistoricoCollectionNew = attachedPessoaFisicaHistoricoCollectionNew;
            cidade.setPessoaFisicaHistoricoCollection(pessoaFisicaHistoricoCollectionNew);
            Collection<EnderecoHistorico> attachedEnderecoHistoricoCollectionNew = new ArrayList<EnderecoHistorico>();
            for (EnderecoHistorico enderecoHistoricoCollectionNewEnderecoHistoricoToAttach : enderecoHistoricoCollectionNew) {
                enderecoHistoricoCollectionNewEnderecoHistoricoToAttach = em.getReference(enderecoHistoricoCollectionNewEnderecoHistoricoToAttach.getClass(), enderecoHistoricoCollectionNewEnderecoHistoricoToAttach.getId());
                attachedEnderecoHistoricoCollectionNew.add(enderecoHistoricoCollectionNewEnderecoHistoricoToAttach);
            }
            enderecoHistoricoCollectionNew = attachedEnderecoHistoricoCollectionNew;
            cidade.setEnderecoHistoricoCollection(enderecoHistoricoCollectionNew);
            cidade = em.merge(cidade);
            if (estadoFkOld != null && !estadoFkOld.equals(estadoFkNew)) {
                estadoFkOld.getCidadeCollection().remove(cidade);
                estadoFkOld = em.merge(estadoFkOld);
            }
            if (estadoFkNew != null && !estadoFkNew.equals(estadoFkOld)) {
                estadoFkNew.getCidadeCollection().add(cidade);
                estadoFkNew = em.merge(estadoFkNew);
            }
            for (PessoaFisica pessoaFisicaCollectionOldPessoaFisica : pessoaFisicaCollectionOld) {
                if (!pessoaFisicaCollectionNew.contains(pessoaFisicaCollectionOldPessoaFisica)) {
                    pessoaFisicaCollectionOldPessoaFisica.setCidadeFk(null);
                    pessoaFisicaCollectionOldPessoaFisica = em.merge(pessoaFisicaCollectionOldPessoaFisica);
                }
            }
            for (PessoaFisica pessoaFisicaCollectionNewPessoaFisica : pessoaFisicaCollectionNew) {
                if (!pessoaFisicaCollectionOld.contains(pessoaFisicaCollectionNewPessoaFisica)) {
                    Cidade oldCidadeFkOfPessoaFisicaCollectionNewPessoaFisica = pessoaFisicaCollectionNewPessoaFisica.getCidadeFk();
                    pessoaFisicaCollectionNewPessoaFisica.setCidadeFk(cidade);
                    pessoaFisicaCollectionNewPessoaFisica = em.merge(pessoaFisicaCollectionNewPessoaFisica);
                    if (oldCidadeFkOfPessoaFisicaCollectionNewPessoaFisica != null && !oldCidadeFkOfPessoaFisicaCollectionNewPessoaFisica.equals(cidade)) {
                        oldCidadeFkOfPessoaFisicaCollectionNewPessoaFisica.getPessoaFisicaCollection().remove(pessoaFisicaCollectionNewPessoaFisica);
                        oldCidadeFkOfPessoaFisicaCollectionNewPessoaFisica = em.merge(oldCidadeFkOfPessoaFisicaCollectionNewPessoaFisica);
                    }
                }
            }
            for (Endereco enderecoCollectionOldEndereco : enderecoCollectionOld) {
                if (!enderecoCollectionNew.contains(enderecoCollectionOldEndereco)) {
                    enderecoCollectionOldEndereco.setCidadeFk(null);
                    enderecoCollectionOldEndereco = em.merge(enderecoCollectionOldEndereco);
                }
            }
            for (Endereco enderecoCollectionNewEndereco : enderecoCollectionNew) {
                if (!enderecoCollectionOld.contains(enderecoCollectionNewEndereco)) {
                    Cidade oldCidadeFkOfEnderecoCollectionNewEndereco = enderecoCollectionNewEndereco.getCidadeFk();
                    enderecoCollectionNewEndereco.setCidadeFk(cidade);
                    enderecoCollectionNewEndereco = em.merge(enderecoCollectionNewEndereco);
                    if (oldCidadeFkOfEnderecoCollectionNewEndereco != null && !oldCidadeFkOfEnderecoCollectionNewEndereco.equals(cidade)) {
                        oldCidadeFkOfEnderecoCollectionNewEndereco.getEnderecoCollection().remove(enderecoCollectionNewEndereco);
                        oldCidadeFkOfEnderecoCollectionNewEndereco = em.merge(oldCidadeFkOfEnderecoCollectionNewEndereco);
                    }
                }
            }
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico : pessoaFisicaHistoricoCollectionOld) {
                if (!pessoaFisicaHistoricoCollectionNew.contains(pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico)) {
                    pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico.setCidadeFk(null);
                    pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico = em.merge(pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico);
                }
            }
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico : pessoaFisicaHistoricoCollectionNew) {
                if (!pessoaFisicaHistoricoCollectionOld.contains(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico)) {
                    Cidade oldCidadeFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico = pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico.getCidadeFk();
                    pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico.setCidadeFk(cidade);
                    pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico = em.merge(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico);
                    if (oldCidadeFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico != null && !oldCidadeFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico.equals(cidade)) {
                        oldCidadeFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico);
                        oldCidadeFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico = em.merge(oldCidadeFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico);
                    }
                }
            }
            for (EnderecoHistorico enderecoHistoricoCollectionOldEnderecoHistorico : enderecoHistoricoCollectionOld) {
                if (!enderecoHistoricoCollectionNew.contains(enderecoHistoricoCollectionOldEnderecoHistorico)) {
                    enderecoHistoricoCollectionOldEnderecoHistorico.setCidadeFk(null);
                    enderecoHistoricoCollectionOldEnderecoHistorico = em.merge(enderecoHistoricoCollectionOldEnderecoHistorico);
                }
            }
            for (EnderecoHistorico enderecoHistoricoCollectionNewEnderecoHistorico : enderecoHistoricoCollectionNew) {
                if (!enderecoHistoricoCollectionOld.contains(enderecoHistoricoCollectionNewEnderecoHistorico)) {
                    Cidade oldCidadeFkOfEnderecoHistoricoCollectionNewEnderecoHistorico = enderecoHistoricoCollectionNewEnderecoHistorico.getCidadeFk();
                    enderecoHistoricoCollectionNewEnderecoHistorico.setCidadeFk(cidade);
                    enderecoHistoricoCollectionNewEnderecoHistorico = em.merge(enderecoHistoricoCollectionNewEnderecoHistorico);
                    if (oldCidadeFkOfEnderecoHistoricoCollectionNewEnderecoHistorico != null && !oldCidadeFkOfEnderecoHistoricoCollectionNewEnderecoHistorico.equals(cidade)) {
                        oldCidadeFkOfEnderecoHistoricoCollectionNewEnderecoHistorico.getEnderecoHistoricoCollection().remove(enderecoHistoricoCollectionNewEnderecoHistorico);
                        oldCidadeFkOfEnderecoHistoricoCollectionNewEnderecoHistorico = em.merge(oldCidadeFkOfEnderecoHistoricoCollectionNewEnderecoHistorico);
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
                Integer id = cidade.getId();
                if (findCidade(id) == null) {
                    throw new NonexistentEntityException("The cidade with id " + id + " no longer exists.");
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
            Cidade cidade;
            try {
                cidade = em.getReference(Cidade.class, id);
                cidade.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cidade with id " + id + " no longer exists.", enfe);
            }
            Estado estadoFk = cidade.getEstadoFk();
            if (estadoFk != null) {
                estadoFk.getCidadeCollection().remove(cidade);
                estadoFk = em.merge(estadoFk);
            }
            Collection<PessoaFisica> pessoaFisicaCollection = cidade.getPessoaFisicaCollection();
            for (PessoaFisica pessoaFisicaCollectionPessoaFisica : pessoaFisicaCollection) {
                pessoaFisicaCollectionPessoaFisica.setCidadeFk(null);
                pessoaFisicaCollectionPessoaFisica = em.merge(pessoaFisicaCollectionPessoaFisica);
            }
            Collection<Endereco> enderecoCollection = cidade.getEnderecoCollection();
            for (Endereco enderecoCollectionEndereco : enderecoCollection) {
                enderecoCollectionEndereco.setCidadeFk(null);
                enderecoCollectionEndereco = em.merge(enderecoCollectionEndereco);
            }
            Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollection = cidade.getPessoaFisicaHistoricoCollection();
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionPessoaFisicaHistorico : pessoaFisicaHistoricoCollection) {
                pessoaFisicaHistoricoCollectionPessoaFisicaHistorico.setCidadeFk(null);
                pessoaFisicaHistoricoCollectionPessoaFisicaHistorico = em.merge(pessoaFisicaHistoricoCollectionPessoaFisicaHistorico);
            }
            Collection<EnderecoHistorico> enderecoHistoricoCollection = cidade.getEnderecoHistoricoCollection();
            for (EnderecoHistorico enderecoHistoricoCollectionEnderecoHistorico : enderecoHistoricoCollection) {
                enderecoHistoricoCollectionEnderecoHistorico.setCidadeFk(null);
                enderecoHistoricoCollectionEnderecoHistorico = em.merge(enderecoHistoricoCollectionEnderecoHistorico);
            }
            em.remove(cidade);
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

    public List<Cidade> findCidadeEntities() {
        return findCidadeEntities(true, -1, -1);
    }

    public List<Cidade> findCidadeEntities(int maxResults, int firstResult) {
        return findCidadeEntities(false, maxResults, firstResult);
    }

    private List<Cidade> findCidadeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cidade.class));
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

    public Cidade findCidade(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cidade.class, id);
        } finally {
            em.close();
        }
    }

    public int getCidadeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cidade> rt = cq.from(Cidade.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Cidade> getByStateId(Integer estadoId) {
        EntityManager em = getEntityManager();
        try {
            List<Cidade> cidadeList = (List<Cidade>) em.createNativeQuery("select * from cidade "
                    + "where estado_fk = '" + estadoId + "'", Cidade.class).getResultList();
            return cidadeList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

}
