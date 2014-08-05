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
import entidade.Pais;
import entidade.Cidade;
import java.util.ArrayList;
import java.util.Collection;
import entidade.PessoaFisica;
import entidade.Endereco;
import entidade.Estado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author paulones
 */
public class EstadoDAO implements Serializable {

    public EstadoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estado estado) throws RollbackFailureException, Exception {
        if (estado.getCidadeCollection() == null) {
            estado.setCidadeCollection(new ArrayList<Cidade>());
        }
        if (estado.getPessoaFisicaCollection() == null) {
            estado.setPessoaFisicaCollection(new ArrayList<PessoaFisica>());
        }
        if (estado.getPessoaFisicaCollection1() == null) {
            estado.setPessoaFisicaCollection1(new ArrayList<PessoaFisica>());
        }
        if (estado.getEnderecoCollection() == null) {
            estado.setEnderecoCollection(new ArrayList<Endereco>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais paisFk = estado.getPaisFk();
            if (paisFk != null) {
                paisFk = em.getReference(paisFk.getClass(), paisFk.getId());
                estado.setPaisFk(paisFk);
            }
            Collection<Cidade> attachedCidadeCollection = new ArrayList<Cidade>();
            for (Cidade cidadeCollectionCidadeToAttach : estado.getCidadeCollection()) {
                cidadeCollectionCidadeToAttach = em.getReference(cidadeCollectionCidadeToAttach.getClass(), cidadeCollectionCidadeToAttach.getId());
                attachedCidadeCollection.add(cidadeCollectionCidadeToAttach);
            }
            estado.setCidadeCollection(attachedCidadeCollection);
            Collection<PessoaFisica> attachedPessoaFisicaCollection = new ArrayList<PessoaFisica>();
            for (PessoaFisica pessoaFisicaCollectionPessoaFisicaToAttach : estado.getPessoaFisicaCollection()) {
                pessoaFisicaCollectionPessoaFisicaToAttach = em.getReference(pessoaFisicaCollectionPessoaFisicaToAttach.getClass(), pessoaFisicaCollectionPessoaFisicaToAttach.getId());
                attachedPessoaFisicaCollection.add(pessoaFisicaCollectionPessoaFisicaToAttach);
            }
            estado.setPessoaFisicaCollection(attachedPessoaFisicaCollection);
            Collection<PessoaFisica> attachedPessoaFisicaCollection1 = new ArrayList<PessoaFisica>();
            for (PessoaFisica pessoaFisicaCollection1PessoaFisicaToAttach : estado.getPessoaFisicaCollection1()) {
                pessoaFisicaCollection1PessoaFisicaToAttach = em.getReference(pessoaFisicaCollection1PessoaFisicaToAttach.getClass(), pessoaFisicaCollection1PessoaFisicaToAttach.getId());
                attachedPessoaFisicaCollection1.add(pessoaFisicaCollection1PessoaFisicaToAttach);
            }
            estado.setPessoaFisicaCollection1(attachedPessoaFisicaCollection1);
            Collection<Endereco> attachedEnderecoCollection = new ArrayList<Endereco>();
            for (Endereco enderecoCollectionEnderecoToAttach : estado.getEnderecoCollection()) {
                enderecoCollectionEnderecoToAttach = em.getReference(enderecoCollectionEnderecoToAttach.getClass(), enderecoCollectionEnderecoToAttach.getId());
                attachedEnderecoCollection.add(enderecoCollectionEnderecoToAttach);
            }
            estado.setEnderecoCollection(attachedEnderecoCollection);
            em.persist(estado);
            if (paisFk != null) {
                paisFk.getEstadoCollection().add(estado);
                paisFk = em.merge(paisFk);
            }
            for (Cidade cidadeCollectionCidade : estado.getCidadeCollection()) {
                Estado oldEstadoFkOfCidadeCollectionCidade = cidadeCollectionCidade.getEstadoFk();
                cidadeCollectionCidade.setEstadoFk(estado);
                cidadeCollectionCidade = em.merge(cidadeCollectionCidade);
                if (oldEstadoFkOfCidadeCollectionCidade != null) {
                    oldEstadoFkOfCidadeCollectionCidade.getCidadeCollection().remove(cidadeCollectionCidade);
                    oldEstadoFkOfCidadeCollectionCidade = em.merge(oldEstadoFkOfCidadeCollectionCidade);
                }
            }
            for (PessoaFisica pessoaFisicaCollectionPessoaFisica : estado.getPessoaFisicaCollection()) {
                Estado oldRgUfFkOfPessoaFisicaCollectionPessoaFisica = pessoaFisicaCollectionPessoaFisica.getRgUfFk();
                pessoaFisicaCollectionPessoaFisica.setRgUfFk(estado);
                pessoaFisicaCollectionPessoaFisica = em.merge(pessoaFisicaCollectionPessoaFisica);
                if (oldRgUfFkOfPessoaFisicaCollectionPessoaFisica != null) {
                    oldRgUfFkOfPessoaFisicaCollectionPessoaFisica.getPessoaFisicaCollection().remove(pessoaFisicaCollectionPessoaFisica);
                    oldRgUfFkOfPessoaFisicaCollectionPessoaFisica = em.merge(oldRgUfFkOfPessoaFisicaCollectionPessoaFisica);
                }
            }
            for (PessoaFisica pessoaFisicaCollection1PessoaFisica : estado.getPessoaFisicaCollection1()) {
                Estado oldEstadoFkOfPessoaFisicaCollection1PessoaFisica = pessoaFisicaCollection1PessoaFisica.getEstadoFk();
                pessoaFisicaCollection1PessoaFisica.setEstadoFk(estado);
                pessoaFisicaCollection1PessoaFisica = em.merge(pessoaFisicaCollection1PessoaFisica);
                if (oldEstadoFkOfPessoaFisicaCollection1PessoaFisica != null) {
                    oldEstadoFkOfPessoaFisicaCollection1PessoaFisica.getPessoaFisicaCollection1().remove(pessoaFisicaCollection1PessoaFisica);
                    oldEstadoFkOfPessoaFisicaCollection1PessoaFisica = em.merge(oldEstadoFkOfPessoaFisicaCollection1PessoaFisica);
                }
            }
            for (Endereco enderecoCollectionEndereco : estado.getEnderecoCollection()) {
                Estado oldEstadoFkOfEnderecoCollectionEndereco = enderecoCollectionEndereco.getEstadoFk();
                enderecoCollectionEndereco.setEstadoFk(estado);
                enderecoCollectionEndereco = em.merge(enderecoCollectionEndereco);
                if (oldEstadoFkOfEnderecoCollectionEndereco != null) {
                    oldEstadoFkOfEnderecoCollectionEndereco.getEnderecoCollection().remove(enderecoCollectionEndereco);
                    oldEstadoFkOfEnderecoCollectionEndereco = em.merge(oldEstadoFkOfEnderecoCollectionEndereco);
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

    public void edit(Estado estado) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado persistentEstado = em.find(Estado.class, estado.getId());
            Pais paisFkOld = persistentEstado.getPaisFk();
            Pais paisFkNew = estado.getPaisFk();
            Collection<Cidade> cidadeCollectionOld = persistentEstado.getCidadeCollection();
            Collection<Cidade> cidadeCollectionNew = estado.getCidadeCollection();
            Collection<PessoaFisica> pessoaFisicaCollectionOld = persistentEstado.getPessoaFisicaCollection();
            Collection<PessoaFisica> pessoaFisicaCollectionNew = estado.getPessoaFisicaCollection();
            Collection<PessoaFisica> pessoaFisicaCollection1Old = persistentEstado.getPessoaFisicaCollection1();
            Collection<PessoaFisica> pessoaFisicaCollection1New = estado.getPessoaFisicaCollection1();
            Collection<Endereco> enderecoCollectionOld = persistentEstado.getEnderecoCollection();
            Collection<Endereco> enderecoCollectionNew = estado.getEnderecoCollection();
            List<String> illegalOrphanMessages = null;
            for (Cidade cidadeCollectionOldCidade : cidadeCollectionOld) {
                if (!cidadeCollectionNew.contains(cidadeCollectionOldCidade)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cidade " + cidadeCollectionOldCidade + " since its estadoFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (paisFkNew != null) {
                paisFkNew = em.getReference(paisFkNew.getClass(), paisFkNew.getId());
                estado.setPaisFk(paisFkNew);
            }
            Collection<Cidade> attachedCidadeCollectionNew = new ArrayList<Cidade>();
            for (Cidade cidadeCollectionNewCidadeToAttach : cidadeCollectionNew) {
                cidadeCollectionNewCidadeToAttach = em.getReference(cidadeCollectionNewCidadeToAttach.getClass(), cidadeCollectionNewCidadeToAttach.getId());
                attachedCidadeCollectionNew.add(cidadeCollectionNewCidadeToAttach);
            }
            cidadeCollectionNew = attachedCidadeCollectionNew;
            estado.setCidadeCollection(cidadeCollectionNew);
            Collection<PessoaFisica> attachedPessoaFisicaCollectionNew = new ArrayList<PessoaFisica>();
            for (PessoaFisica pessoaFisicaCollectionNewPessoaFisicaToAttach : pessoaFisicaCollectionNew) {
                pessoaFisicaCollectionNewPessoaFisicaToAttach = em.getReference(pessoaFisicaCollectionNewPessoaFisicaToAttach.getClass(), pessoaFisicaCollectionNewPessoaFisicaToAttach.getId());
                attachedPessoaFisicaCollectionNew.add(pessoaFisicaCollectionNewPessoaFisicaToAttach);
            }
            pessoaFisicaCollectionNew = attachedPessoaFisicaCollectionNew;
            estado.setPessoaFisicaCollection(pessoaFisicaCollectionNew);
            Collection<PessoaFisica> attachedPessoaFisicaCollection1New = new ArrayList<PessoaFisica>();
            for (PessoaFisica pessoaFisicaCollection1NewPessoaFisicaToAttach : pessoaFisicaCollection1New) {
                pessoaFisicaCollection1NewPessoaFisicaToAttach = em.getReference(pessoaFisicaCollection1NewPessoaFisicaToAttach.getClass(), pessoaFisicaCollection1NewPessoaFisicaToAttach.getId());
                attachedPessoaFisicaCollection1New.add(pessoaFisicaCollection1NewPessoaFisicaToAttach);
            }
            pessoaFisicaCollection1New = attachedPessoaFisicaCollection1New;
            estado.setPessoaFisicaCollection1(pessoaFisicaCollection1New);
            Collection<Endereco> attachedEnderecoCollectionNew = new ArrayList<Endereco>();
            for (Endereco enderecoCollectionNewEnderecoToAttach : enderecoCollectionNew) {
                enderecoCollectionNewEnderecoToAttach = em.getReference(enderecoCollectionNewEnderecoToAttach.getClass(), enderecoCollectionNewEnderecoToAttach.getId());
                attachedEnderecoCollectionNew.add(enderecoCollectionNewEnderecoToAttach);
            }
            enderecoCollectionNew = attachedEnderecoCollectionNew;
            estado.setEnderecoCollection(enderecoCollectionNew);
            estado = em.merge(estado);
            if (paisFkOld != null && !paisFkOld.equals(paisFkNew)) {
                paisFkOld.getEstadoCollection().remove(estado);
                paisFkOld = em.merge(paisFkOld);
            }
            if (paisFkNew != null && !paisFkNew.equals(paisFkOld)) {
                paisFkNew.getEstadoCollection().add(estado);
                paisFkNew = em.merge(paisFkNew);
            }
            for (Cidade cidadeCollectionNewCidade : cidadeCollectionNew) {
                if (!cidadeCollectionOld.contains(cidadeCollectionNewCidade)) {
                    Estado oldEstadoFkOfCidadeCollectionNewCidade = cidadeCollectionNewCidade.getEstadoFk();
                    cidadeCollectionNewCidade.setEstadoFk(estado);
                    cidadeCollectionNewCidade = em.merge(cidadeCollectionNewCidade);
                    if (oldEstadoFkOfCidadeCollectionNewCidade != null && !oldEstadoFkOfCidadeCollectionNewCidade.equals(estado)) {
                        oldEstadoFkOfCidadeCollectionNewCidade.getCidadeCollection().remove(cidadeCollectionNewCidade);
                        oldEstadoFkOfCidadeCollectionNewCidade = em.merge(oldEstadoFkOfCidadeCollectionNewCidade);
                    }
                }
            }
            for (PessoaFisica pessoaFisicaCollectionOldPessoaFisica : pessoaFisicaCollectionOld) {
                if (!pessoaFisicaCollectionNew.contains(pessoaFisicaCollectionOldPessoaFisica)) {
                    pessoaFisicaCollectionOldPessoaFisica.setRgUfFk(null);
                    pessoaFisicaCollectionOldPessoaFisica = em.merge(pessoaFisicaCollectionOldPessoaFisica);
                }
            }
            for (PessoaFisica pessoaFisicaCollectionNewPessoaFisica : pessoaFisicaCollectionNew) {
                if (!pessoaFisicaCollectionOld.contains(pessoaFisicaCollectionNewPessoaFisica)) {
                    Estado oldRgUfFkOfPessoaFisicaCollectionNewPessoaFisica = pessoaFisicaCollectionNewPessoaFisica.getRgUfFk();
                    pessoaFisicaCollectionNewPessoaFisica.setRgUfFk(estado);
                    pessoaFisicaCollectionNewPessoaFisica = em.merge(pessoaFisicaCollectionNewPessoaFisica);
                    if (oldRgUfFkOfPessoaFisicaCollectionNewPessoaFisica != null && !oldRgUfFkOfPessoaFisicaCollectionNewPessoaFisica.equals(estado)) {
                        oldRgUfFkOfPessoaFisicaCollectionNewPessoaFisica.getPessoaFisicaCollection().remove(pessoaFisicaCollectionNewPessoaFisica);
                        oldRgUfFkOfPessoaFisicaCollectionNewPessoaFisica = em.merge(oldRgUfFkOfPessoaFisicaCollectionNewPessoaFisica);
                    }
                }
            }
            for (PessoaFisica pessoaFisicaCollection1OldPessoaFisica : pessoaFisicaCollection1Old) {
                if (!pessoaFisicaCollection1New.contains(pessoaFisicaCollection1OldPessoaFisica)) {
                    pessoaFisicaCollection1OldPessoaFisica.setEstadoFk(null);
                    pessoaFisicaCollection1OldPessoaFisica = em.merge(pessoaFisicaCollection1OldPessoaFisica);
                }
            }
            for (PessoaFisica pessoaFisicaCollection1NewPessoaFisica : pessoaFisicaCollection1New) {
                if (!pessoaFisicaCollection1Old.contains(pessoaFisicaCollection1NewPessoaFisica)) {
                    Estado oldEstadoFkOfPessoaFisicaCollection1NewPessoaFisica = pessoaFisicaCollection1NewPessoaFisica.getEstadoFk();
                    pessoaFisicaCollection1NewPessoaFisica.setEstadoFk(estado);
                    pessoaFisicaCollection1NewPessoaFisica = em.merge(pessoaFisicaCollection1NewPessoaFisica);
                    if (oldEstadoFkOfPessoaFisicaCollection1NewPessoaFisica != null && !oldEstadoFkOfPessoaFisicaCollection1NewPessoaFisica.equals(estado)) {
                        oldEstadoFkOfPessoaFisicaCollection1NewPessoaFisica.getPessoaFisicaCollection1().remove(pessoaFisicaCollection1NewPessoaFisica);
                        oldEstadoFkOfPessoaFisicaCollection1NewPessoaFisica = em.merge(oldEstadoFkOfPessoaFisicaCollection1NewPessoaFisica);
                    }
                }
            }
            for (Endereco enderecoCollectionOldEndereco : enderecoCollectionOld) {
                if (!enderecoCollectionNew.contains(enderecoCollectionOldEndereco)) {
                    enderecoCollectionOldEndereco.setEstadoFk(null);
                    enderecoCollectionOldEndereco = em.merge(enderecoCollectionOldEndereco);
                }
            }
            for (Endereco enderecoCollectionNewEndereco : enderecoCollectionNew) {
                if (!enderecoCollectionOld.contains(enderecoCollectionNewEndereco)) {
                    Estado oldEstadoFkOfEnderecoCollectionNewEndereco = enderecoCollectionNewEndereco.getEstadoFk();
                    enderecoCollectionNewEndereco.setEstadoFk(estado);
                    enderecoCollectionNewEndereco = em.merge(enderecoCollectionNewEndereco);
                    if (oldEstadoFkOfEnderecoCollectionNewEndereco != null && !oldEstadoFkOfEnderecoCollectionNewEndereco.equals(estado)) {
                        oldEstadoFkOfEnderecoCollectionNewEndereco.getEnderecoCollection().remove(enderecoCollectionNewEndereco);
                        oldEstadoFkOfEnderecoCollectionNewEndereco = em.merge(oldEstadoFkOfEnderecoCollectionNewEndereco);
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
                Integer id = estado.getId();
                if (findEstado(id) == null) {
                    throw new NonexistentEntityException("The estado with id " + id + " no longer exists.");
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
            Estado estado;
            try {
                estado = em.getReference(Estado.class, id);
                estado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Cidade> cidadeCollectionOrphanCheck = estado.getCidadeCollection();
            for (Cidade cidadeCollectionOrphanCheckCidade : cidadeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estado (" + estado + ") cannot be destroyed since the Cidade " + cidadeCollectionOrphanCheckCidade + " in its cidadeCollection field has a non-nullable estadoFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pais paisFk = estado.getPaisFk();
            if (paisFk != null) {
                paisFk.getEstadoCollection().remove(estado);
                paisFk = em.merge(paisFk);
            }
            Collection<PessoaFisica> pessoaFisicaCollection = estado.getPessoaFisicaCollection();
            for (PessoaFisica pessoaFisicaCollectionPessoaFisica : pessoaFisicaCollection) {
                pessoaFisicaCollectionPessoaFisica.setRgUfFk(null);
                pessoaFisicaCollectionPessoaFisica = em.merge(pessoaFisicaCollectionPessoaFisica);
            }
            Collection<PessoaFisica> pessoaFisicaCollection1 = estado.getPessoaFisicaCollection1();
            for (PessoaFisica pessoaFisicaCollection1PessoaFisica : pessoaFisicaCollection1) {
                pessoaFisicaCollection1PessoaFisica.setEstadoFk(null);
                pessoaFisicaCollection1PessoaFisica = em.merge(pessoaFisicaCollection1PessoaFisica);
            }
            Collection<Endereco> enderecoCollection = estado.getEnderecoCollection();
            for (Endereco enderecoCollectionEndereco : enderecoCollection) {
                enderecoCollectionEndereco.setEstadoFk(null);
                enderecoCollectionEndereco = em.merge(enderecoCollectionEndereco);
            }
            em.remove(estado);
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

    public List<Estado> findEstadoEntities() {
        return findEstadoEntities(true, -1, -1);
    }

    public List<Estado> findEstadoEntities(int maxResults, int firstResult) {
        return findEstadoEntities(false, maxResults, firstResult);
    }

    private List<Estado> findEstadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estado.class));
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

    public Estado findEstado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estado> rt = cq.from(Estado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
