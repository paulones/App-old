/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.Cidade;
import entidade.Endereco;
import entidade.EnderecoHistorico;
import entidade.Estado;
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
public class EnderecoDAO implements Serializable {

    public EnderecoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Endereco endereco) throws RollbackFailureException, Exception {
        if (endereco.getEnderecoHistoricoCollection() == null) {
            endereco.setEnderecoHistoricoCollection(new ArrayList<EnderecoHistorico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cidade cidadeFk = endereco.getCidadeFk();
            if (cidadeFk != null) {
                cidadeFk = em.getReference(cidadeFk.getClass(), cidadeFk.getId());
                endereco.setCidadeFk(cidadeFk);
            }
            Estado estadoFk = endereco.getEstadoFk();
            if (estadoFk != null) {
                estadoFk = em.getReference(estadoFk.getClass(), estadoFk.getId());
                endereco.setEstadoFk(estadoFk);
            }
            Collection<EnderecoHistorico> attachedEnderecoHistoricoCollection = new ArrayList<EnderecoHistorico>();
            for (EnderecoHistorico enderecoHistoricoCollectionEnderecoHistoricoToAttach : endereco.getEnderecoHistoricoCollection()) {
                enderecoHistoricoCollectionEnderecoHistoricoToAttach = em.getReference(enderecoHistoricoCollectionEnderecoHistoricoToAttach.getClass(), enderecoHistoricoCollectionEnderecoHistoricoToAttach.getId());
                attachedEnderecoHistoricoCollection.add(enderecoHistoricoCollectionEnderecoHistoricoToAttach);
            }
            endereco.setEnderecoHistoricoCollection(attachedEnderecoHistoricoCollection);
            em.persist(endereco);
            if (cidadeFk != null) {
                cidadeFk.getEnderecoCollection().add(endereco);
                cidadeFk = em.merge(cidadeFk);
            }
            if (estadoFk != null) {
                estadoFk.getEnderecoCollection().add(endereco);
                estadoFk = em.merge(estadoFk);
            }
            for (EnderecoHistorico enderecoHistoricoCollectionEnderecoHistorico : endereco.getEnderecoHistoricoCollection()) {
                Endereco oldEnderecoFkOfEnderecoHistoricoCollectionEnderecoHistorico = enderecoHistoricoCollectionEnderecoHistorico.getEnderecoFk();
                enderecoHistoricoCollectionEnderecoHistorico.setEnderecoFk(endereco);
                enderecoHistoricoCollectionEnderecoHistorico = em.merge(enderecoHistoricoCollectionEnderecoHistorico);
                if (oldEnderecoFkOfEnderecoHistoricoCollectionEnderecoHistorico != null) {
                    oldEnderecoFkOfEnderecoHistoricoCollectionEnderecoHistorico.getEnderecoHistoricoCollection().remove(enderecoHistoricoCollectionEnderecoHistorico);
                    oldEnderecoFkOfEnderecoHistoricoCollectionEnderecoHistorico = em.merge(oldEnderecoFkOfEnderecoHistoricoCollectionEnderecoHistorico);
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

    public void edit(Endereco endereco) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Endereco persistentEndereco = em.find(Endereco.class, endereco.getId());
            Cidade cidadeFkOld = persistentEndereco.getCidadeFk();
            Cidade cidadeFkNew = endereco.getCidadeFk();
            Estado estadoFkOld = persistentEndereco.getEstadoFk();
            Estado estadoFkNew = endereco.getEstadoFk();
            Collection<EnderecoHistorico> enderecoHistoricoCollectionOld = persistentEndereco.getEnderecoHistoricoCollection();
            Collection<EnderecoHistorico> enderecoHistoricoCollectionNew = endereco.getEnderecoHistoricoCollection();
            List<String> illegalOrphanMessages = null;
            for (EnderecoHistorico enderecoHistoricoCollectionOldEnderecoHistorico : enderecoHistoricoCollectionOld) {
                if (!enderecoHistoricoCollectionNew.contains(enderecoHistoricoCollectionOldEnderecoHistorico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EnderecoHistorico " + enderecoHistoricoCollectionOldEnderecoHistorico + " since its enderecoFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cidadeFkNew != null) {
                cidadeFkNew = em.getReference(cidadeFkNew.getClass(), cidadeFkNew.getId());
                endereco.setCidadeFk(cidadeFkNew);
            }
            if (estadoFkNew != null) {
                estadoFkNew = em.getReference(estadoFkNew.getClass(), estadoFkNew.getId());
                endereco.setEstadoFk(estadoFkNew);
            }
            Collection<EnderecoHistorico> attachedEnderecoHistoricoCollectionNew = new ArrayList<EnderecoHistorico>();
            for (EnderecoHistorico enderecoHistoricoCollectionNewEnderecoHistoricoToAttach : enderecoHistoricoCollectionNew) {
                enderecoHistoricoCollectionNewEnderecoHistoricoToAttach = em.getReference(enderecoHistoricoCollectionNewEnderecoHistoricoToAttach.getClass(), enderecoHistoricoCollectionNewEnderecoHistoricoToAttach.getId());
                attachedEnderecoHistoricoCollectionNew.add(enderecoHistoricoCollectionNewEnderecoHistoricoToAttach);
            }
            enderecoHistoricoCollectionNew = attachedEnderecoHistoricoCollectionNew;
            endereco.setEnderecoHistoricoCollection(enderecoHistoricoCollectionNew);
            endereco = em.merge(endereco);
            if (cidadeFkOld != null && !cidadeFkOld.equals(cidadeFkNew)) {
                cidadeFkOld.getEnderecoCollection().remove(endereco);
                cidadeFkOld = em.merge(cidadeFkOld);
            }
            if (cidadeFkNew != null && !cidadeFkNew.equals(cidadeFkOld)) {
                cidadeFkNew.getEnderecoCollection().add(endereco);
                cidadeFkNew = em.merge(cidadeFkNew);
            }
            if (estadoFkOld != null && !estadoFkOld.equals(estadoFkNew)) {
                estadoFkOld.getEnderecoCollection().remove(endereco);
                estadoFkOld = em.merge(estadoFkOld);
            }
            if (estadoFkNew != null && !estadoFkNew.equals(estadoFkOld)) {
                estadoFkNew.getEnderecoCollection().add(endereco);
                estadoFkNew = em.merge(estadoFkNew);
            }
            for (EnderecoHistorico enderecoHistoricoCollectionNewEnderecoHistorico : enderecoHistoricoCollectionNew) {
                if (!enderecoHistoricoCollectionOld.contains(enderecoHistoricoCollectionNewEnderecoHistorico)) {
                    Endereco oldEnderecoFkOfEnderecoHistoricoCollectionNewEnderecoHistorico = enderecoHistoricoCollectionNewEnderecoHistorico.getEnderecoFk();
                    enderecoHistoricoCollectionNewEnderecoHistorico.setEnderecoFk(endereco);
                    enderecoHistoricoCollectionNewEnderecoHistorico = em.merge(enderecoHistoricoCollectionNewEnderecoHistorico);
                    if (oldEnderecoFkOfEnderecoHistoricoCollectionNewEnderecoHistorico != null && !oldEnderecoFkOfEnderecoHistoricoCollectionNewEnderecoHistorico.equals(endereco)) {
                        oldEnderecoFkOfEnderecoHistoricoCollectionNewEnderecoHistorico.getEnderecoHistoricoCollection().remove(enderecoHistoricoCollectionNewEnderecoHistorico);
                        oldEnderecoFkOfEnderecoHistoricoCollectionNewEnderecoHistorico = em.merge(oldEnderecoFkOfEnderecoHistoricoCollectionNewEnderecoHistorico);
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
                Integer id = endereco.getId();
                if (findEndereco(id) == null) {
                    throw new NonexistentEntityException("The endereco with id " + id + " no longer exists.");
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
            Endereco endereco;
            try {
                endereco = em.getReference(Endereco.class, id);
                endereco.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The endereco with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<EnderecoHistorico> enderecoHistoricoCollectionOrphanCheck = endereco.getEnderecoHistoricoCollection();
            for (EnderecoHistorico enderecoHistoricoCollectionOrphanCheckEnderecoHistorico : enderecoHistoricoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Endereco (" + endereco + ") cannot be destroyed since the EnderecoHistorico " + enderecoHistoricoCollectionOrphanCheckEnderecoHistorico + " in its enderecoHistoricoCollection field has a non-nullable enderecoFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cidade cidadeFk = endereco.getCidadeFk();
            if (cidadeFk != null) {
                cidadeFk.getEnderecoCollection().remove(endereco);
                cidadeFk = em.merge(cidadeFk);
            }
            Estado estadoFk = endereco.getEstadoFk();
            if (estadoFk != null) {
                estadoFk.getEnderecoCollection().remove(endereco);
                estadoFk = em.merge(estadoFk);
            }
            em.remove(endereco);
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

    public List<Endereco> findEnderecoEntities() {
        return findEnderecoEntities(true, -1, -1);
    }

    public List<Endereco> findEnderecoEntities(int maxResults, int firstResult) {
        return findEnderecoEntities(false, maxResults, firstResult);
    }

    private List<Endereco> findEnderecoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Endereco.class));
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

    public Endereco findEndereco(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Endereco.class, id);
        } finally {
            em.close();
        }
    }

    public int getEnderecoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Endereco> rt = cq.from(Endereco.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Endereco findPFAddress(Integer id){
        EntityManager em = getEntityManager();
        try {
            Endereco endereco = (Endereco) em.createNativeQuery("select * from endereco "
                        + "where tipo = 'PF' and id_fk = '"+id+"'", Endereco.class).getSingleResult();
            return endereco;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public List<Endereco> findAllPFAddress(){
        EntityManager em = getEntityManager();
        try {
            List<Endereco> enderecoList = (List<Endereco>) em.createNativeQuery("select * from endereco "
                        + "where tipo = 'PF'", Endereco.class).getResultList();
            return enderecoList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
}
