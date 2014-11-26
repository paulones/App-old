/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.Autorizacao;
import entidade.Cidade;
import entidade.Estado;
import entidade.Instituicao;
import entidade.PessoaJuridica;
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
 * @author Pedro
 */
public class InstituicaoDAO implements Serializable {

    public InstituicaoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Instituicao instituicao) throws RollbackFailureException, Exception {
        if (instituicao.getAutorizacaoCollection() == null) {
            instituicao.setAutorizacaoCollection(new ArrayList<Autorizacao>());
        }
        if (instituicao.getPessoaJuridicaCollection() == null) {
            instituicao.setPessoaJuridicaCollection(new ArrayList<PessoaJuridica>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado estadoFk = instituicao.getEstadoFk();
            if (estadoFk != null) {
                estadoFk = em.getReference(estadoFk.getClass(), estadoFk.getId());
                instituicao.setEstadoFk(estadoFk);
            }
            Cidade cidadeFk = instituicao.getCidadeFk();
            if (cidadeFk != null) {
                cidadeFk = em.getReference(cidadeFk.getClass(), cidadeFk.getId());
                instituicao.setCidadeFk(cidadeFk);
            }
            Collection<Autorizacao> attachedAutorizacaoCollection = new ArrayList<Autorizacao>();
            for (Autorizacao autorizacaoCollectionAutorizacaoToAttach : instituicao.getAutorizacaoCollection()) {
                autorizacaoCollectionAutorizacaoToAttach = em.getReference(autorizacaoCollectionAutorizacaoToAttach.getClass(), autorizacaoCollectionAutorizacaoToAttach.getId());
                attachedAutorizacaoCollection.add(autorizacaoCollectionAutorizacaoToAttach);
            }
            instituicao.setAutorizacaoCollection(attachedAutorizacaoCollection);
            Collection<PessoaJuridica> attachedPessoaJuridicaCollection = new ArrayList<PessoaJuridica>();
            for (PessoaJuridica pessoaJuridicaCollectionPessoaJuridicaToAttach : instituicao.getPessoaJuridicaCollection()) {
                pessoaJuridicaCollectionPessoaJuridicaToAttach = em.getReference(pessoaJuridicaCollectionPessoaJuridicaToAttach.getClass(), pessoaJuridicaCollectionPessoaJuridicaToAttach.getId());
                attachedPessoaJuridicaCollection.add(pessoaJuridicaCollectionPessoaJuridicaToAttach);
            }
            instituicao.setPessoaJuridicaCollection(attachedPessoaJuridicaCollection);
            em.persist(instituicao);
            if (estadoFk != null) {
                estadoFk.getInstituicaoCollection().add(instituicao);
                estadoFk = em.merge(estadoFk);
            }
            if (cidadeFk != null) {
                cidadeFk.getInstituicaoCollection().add(instituicao);
                cidadeFk = em.merge(cidadeFk);
            }
            for (Autorizacao autorizacaoCollectionAutorizacao : instituicao.getAutorizacaoCollection()) {
                Instituicao oldInstituicaoFkOfAutorizacaoCollectionAutorizacao = autorizacaoCollectionAutorizacao.getInstituicaoFk();
                autorizacaoCollectionAutorizacao.setInstituicaoFk(instituicao);
                autorizacaoCollectionAutorizacao = em.merge(autorizacaoCollectionAutorizacao);
                if (oldInstituicaoFkOfAutorizacaoCollectionAutorizacao != null) {
                    oldInstituicaoFkOfAutorizacaoCollectionAutorizacao.getAutorizacaoCollection().remove(autorizacaoCollectionAutorizacao);
                    oldInstituicaoFkOfAutorizacaoCollectionAutorizacao = em.merge(oldInstituicaoFkOfAutorizacaoCollectionAutorizacao);
                }
            }
            for (PessoaJuridica pessoaJuridicaCollectionPessoaJuridica : instituicao.getPessoaJuridicaCollection()) {
                Instituicao oldInstituicaoFkOfPessoaJuridicaCollectionPessoaJuridica = pessoaJuridicaCollectionPessoaJuridica.getInstituicaoFk();
                pessoaJuridicaCollectionPessoaJuridica.setInstituicaoFk(instituicao);
                pessoaJuridicaCollectionPessoaJuridica = em.merge(pessoaJuridicaCollectionPessoaJuridica);
                if (oldInstituicaoFkOfPessoaJuridicaCollectionPessoaJuridica != null) {
                    oldInstituicaoFkOfPessoaJuridicaCollectionPessoaJuridica.getPessoaJuridicaCollection().remove(pessoaJuridicaCollectionPessoaJuridica);
                    oldInstituicaoFkOfPessoaJuridicaCollectionPessoaJuridica = em.merge(oldInstituicaoFkOfPessoaJuridicaCollectionPessoaJuridica);
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

    public void edit(Instituicao instituicao) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Instituicao persistentInstituicao = em.find(Instituicao.class, instituicao.getId());
            Estado estadoFkOld = persistentInstituicao.getEstadoFk();
            Estado estadoFkNew = instituicao.getEstadoFk();
            Cidade cidadeFkOld = persistentInstituicao.getCidadeFk();
            Cidade cidadeFkNew = instituicao.getCidadeFk();
            Collection<Autorizacao> autorizacaoCollectionOld = persistentInstituicao.getAutorizacaoCollection();
            Collection<Autorizacao> autorizacaoCollectionNew = instituicao.getAutorizacaoCollection();
            Collection<PessoaJuridica> pessoaJuridicaCollectionOld = persistentInstituicao.getPessoaJuridicaCollection();
            Collection<PessoaJuridica> pessoaJuridicaCollectionNew = instituicao.getPessoaJuridicaCollection();
            List<String> illegalOrphanMessages = null;
            for (Autorizacao autorizacaoCollectionOldAutorizacao : autorizacaoCollectionOld) {
                if (!autorizacaoCollectionNew.contains(autorizacaoCollectionOldAutorizacao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Autorizacao " + autorizacaoCollectionOldAutorizacao + " since its instituicaoFk field is not nullable.");
                }
            }
            for (PessoaJuridica pessoaJuridicaCollectionOldPessoaJuridica : pessoaJuridicaCollectionOld) {
                if (!pessoaJuridicaCollectionNew.contains(pessoaJuridicaCollectionOldPessoaJuridica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PessoaJuridica " + pessoaJuridicaCollectionOldPessoaJuridica + " since its instituicaoFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (estadoFkNew != null) {
                estadoFkNew = em.getReference(estadoFkNew.getClass(), estadoFkNew.getId());
                instituicao.setEstadoFk(estadoFkNew);
            }
            if (cidadeFkNew != null) {
                cidadeFkNew = em.getReference(cidadeFkNew.getClass(), cidadeFkNew.getId());
                instituicao.setCidadeFk(cidadeFkNew);
            }
            Collection<Autorizacao> attachedAutorizacaoCollectionNew = new ArrayList<Autorizacao>();
            for (Autorizacao autorizacaoCollectionNewAutorizacaoToAttach : autorizacaoCollectionNew) {
                autorizacaoCollectionNewAutorizacaoToAttach = em.getReference(autorizacaoCollectionNewAutorizacaoToAttach.getClass(), autorizacaoCollectionNewAutorizacaoToAttach.getId());
                attachedAutorizacaoCollectionNew.add(autorizacaoCollectionNewAutorizacaoToAttach);
            }
            autorizacaoCollectionNew = attachedAutorizacaoCollectionNew;
            instituicao.setAutorizacaoCollection(autorizacaoCollectionNew);
            Collection<PessoaJuridica> attachedPessoaJuridicaCollectionNew = new ArrayList<PessoaJuridica>();
            for (PessoaJuridica pessoaJuridicaCollectionNewPessoaJuridicaToAttach : pessoaJuridicaCollectionNew) {
                pessoaJuridicaCollectionNewPessoaJuridicaToAttach = em.getReference(pessoaJuridicaCollectionNewPessoaJuridicaToAttach.getClass(), pessoaJuridicaCollectionNewPessoaJuridicaToAttach.getId());
                attachedPessoaJuridicaCollectionNew.add(pessoaJuridicaCollectionNewPessoaJuridicaToAttach);
            }
            pessoaJuridicaCollectionNew = attachedPessoaJuridicaCollectionNew;
            instituicao.setPessoaJuridicaCollection(pessoaJuridicaCollectionNew);
            instituicao = em.merge(instituicao);
            if (estadoFkOld != null && !estadoFkOld.equals(estadoFkNew)) {
                estadoFkOld.getInstituicaoCollection().remove(instituicao);
                estadoFkOld = em.merge(estadoFkOld);
            }
            if (estadoFkNew != null && !estadoFkNew.equals(estadoFkOld)) {
                estadoFkNew.getInstituicaoCollection().add(instituicao);
                estadoFkNew = em.merge(estadoFkNew);
            }
            if (cidadeFkOld != null && !cidadeFkOld.equals(cidadeFkNew)) {
                cidadeFkOld.getInstituicaoCollection().remove(instituicao);
                cidadeFkOld = em.merge(cidadeFkOld);
            }
            if (cidadeFkNew != null && !cidadeFkNew.equals(cidadeFkOld)) {
                cidadeFkNew.getInstituicaoCollection().add(instituicao);
                cidadeFkNew = em.merge(cidadeFkNew);
            }
            for (Autorizacao autorizacaoCollectionNewAutorizacao : autorizacaoCollectionNew) {
                if (!autorizacaoCollectionOld.contains(autorizacaoCollectionNewAutorizacao)) {
                    Instituicao oldInstituicaoFkOfAutorizacaoCollectionNewAutorizacao = autorizacaoCollectionNewAutorizacao.getInstituicaoFk();
                    autorizacaoCollectionNewAutorizacao.setInstituicaoFk(instituicao);
                    autorizacaoCollectionNewAutorizacao = em.merge(autorizacaoCollectionNewAutorizacao);
                    if (oldInstituicaoFkOfAutorizacaoCollectionNewAutorizacao != null && !oldInstituicaoFkOfAutorizacaoCollectionNewAutorizacao.equals(instituicao)) {
                        oldInstituicaoFkOfAutorizacaoCollectionNewAutorizacao.getAutorizacaoCollection().remove(autorizacaoCollectionNewAutorizacao);
                        oldInstituicaoFkOfAutorizacaoCollectionNewAutorizacao = em.merge(oldInstituicaoFkOfAutorizacaoCollectionNewAutorizacao);
                    }
                }
            }
            for (PessoaJuridica pessoaJuridicaCollectionNewPessoaJuridica : pessoaJuridicaCollectionNew) {
                if (!pessoaJuridicaCollectionOld.contains(pessoaJuridicaCollectionNewPessoaJuridica)) {
                    Instituicao oldInstituicaoFkOfPessoaJuridicaCollectionNewPessoaJuridica = pessoaJuridicaCollectionNewPessoaJuridica.getInstituicaoFk();
                    pessoaJuridicaCollectionNewPessoaJuridica.setInstituicaoFk(instituicao);
                    pessoaJuridicaCollectionNewPessoaJuridica = em.merge(pessoaJuridicaCollectionNewPessoaJuridica);
                    if (oldInstituicaoFkOfPessoaJuridicaCollectionNewPessoaJuridica != null && !oldInstituicaoFkOfPessoaJuridicaCollectionNewPessoaJuridica.equals(instituicao)) {
                        oldInstituicaoFkOfPessoaJuridicaCollectionNewPessoaJuridica.getPessoaJuridicaCollection().remove(pessoaJuridicaCollectionNewPessoaJuridica);
                        oldInstituicaoFkOfPessoaJuridicaCollectionNewPessoaJuridica = em.merge(oldInstituicaoFkOfPessoaJuridicaCollectionNewPessoaJuridica);
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
                Integer id = instituicao.getId();
                if (findInstituicao(id) == null) {
                    throw new NonexistentEntityException("The instituicao with id " + id + " no longer exists.");
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
            Instituicao instituicao;
            try {
                instituicao = em.getReference(Instituicao.class, id);
                instituicao.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The instituicao with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Autorizacao> autorizacaoCollectionOrphanCheck = instituicao.getAutorizacaoCollection();
            for (Autorizacao autorizacaoCollectionOrphanCheckAutorizacao : autorizacaoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Instituicao (" + instituicao + ") cannot be destroyed since the Autorizacao " + autorizacaoCollectionOrphanCheckAutorizacao + " in its autorizacaoCollection field has a non-nullable instituicaoFk field.");
            }
            Collection<PessoaJuridica> pessoaJuridicaCollectionOrphanCheck = instituicao.getPessoaJuridicaCollection();
            for (PessoaJuridica pessoaJuridicaCollectionOrphanCheckPessoaJuridica : pessoaJuridicaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Instituicao (" + instituicao + ") cannot be destroyed since the PessoaJuridica " + pessoaJuridicaCollectionOrphanCheckPessoaJuridica + " in its pessoaJuridicaCollection field has a non-nullable instituicaoFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Estado estadoFk = instituicao.getEstadoFk();
            if (estadoFk != null) {
                estadoFk.getInstituicaoCollection().remove(instituicao);
                estadoFk = em.merge(estadoFk);
            }
            Cidade cidadeFk = instituicao.getCidadeFk();
            if (cidadeFk != null) {
                cidadeFk.getInstituicaoCollection().remove(instituicao);
                cidadeFk = em.merge(cidadeFk);
            }
            em.remove(instituicao);
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

    public List<Instituicao> findInstituicaoEntities() {
        return findInstituicaoEntities(true, -1, -1);
    }

    public List<Instituicao> findInstituicaoEntities(int maxResults, int firstResult) {
        return findInstituicaoEntities(false, maxResults, firstResult);
    }

    private List<Instituicao> findInstituicaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Instituicao.class));
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

    public Instituicao findInstituicao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Instituicao.class, id);
        } finally {
            em.close();
        }
    }

    public int getInstituicaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Instituicao> rt = cq.from(Instituicao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Instituicao findInstituicaoByCNPJ(String cnpj) {
        EntityManager em = getEntityManager();
        try {
            Instituicao instituicao = (Instituicao) em.createNativeQuery("select * from instituicao "
                    + "where cnpj = '" + cnpj + "'", Instituicao.class).getSingleResult();
            return instituicao;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public Instituicao findInstituicaoByCPF(String cpf) {
        EntityManager em = getEntityManager();
        try {
            Instituicao instituicao = (Instituicao) em.createNativeQuery("select ins.* from autorizacao aut, instituicao ins "
                    + "where aut.instituicao_fk = ins.id and "
                    + "aut.cpf = '" + cpf + "'", Instituicao.class).getSingleResult();
            return instituicao;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
}
