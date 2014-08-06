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
import entidade.Estado;
import entidade.PessoaFisica;
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
