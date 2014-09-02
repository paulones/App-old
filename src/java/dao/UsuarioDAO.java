/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.EnderecoHistorico;
import entidade.PessoaFisica;
import entidade.PessoaFisicaHistorico;
import entidade.PessoaFisicaJuridicaHistorico;
import entidade.PessoaJuridica;
import entidade.PessoaJuridicaHistorico;
import entidade.PessoaJuridicaSucessao;
import entidade.ProcessoJudicial;
import entidade.ProcessoJudicialHistorico;
import entidade.RecuperarSenha;
import entidade.Usuario;
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
public class UsuarioDAO implements Serializable {

    public UsuarioDAO() {
    }

    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws RollbackFailureException, Exception {
        if (usuario.getPessoaFisicaHistoricoCollection() == null) {
            usuario.setPessoaFisicaHistoricoCollection(new ArrayList<PessoaFisicaHistorico>());
        }
        if (usuario.getPessoaJuridicaHistoricoCollection() == null) {
            usuario.setPessoaJuridicaHistoricoCollection(new ArrayList<PessoaJuridicaHistorico>());
        }
        if (usuario.getPessoaJuridicaCollection() == null) {
            usuario.setPessoaJuridicaCollection(new ArrayList<PessoaJuridica>());
        }
        if (usuario.getPessoaFisicaCollection() == null) {
            usuario.setPessoaFisicaCollection(new ArrayList<PessoaFisica>());
        }
        if (usuario.getProcessoJudicialCollection() == null) {
            usuario.setProcessoJudicialCollection(new ArrayList<ProcessoJudicial>());
        }
        if (usuario.getProcessoJudicialHistoricoCollection() == null) {
            usuario.setProcessoJudicialHistoricoCollection(new ArrayList<ProcessoJudicialHistorico>());
        }
        if (usuario.getPessoaJuridicaSucessaoCollection() == null) {
            usuario.setPessoaJuridicaSucessaoCollection(new ArrayList<PessoaJuridicaSucessao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            RecuperarSenha recuperarSenha = usuario.getRecuperarSenha();
            if (recuperarSenha != null) {
                recuperarSenha = em.getReference(recuperarSenha.getClass(), recuperarSenha.getId());
                usuario.setRecuperarSenha(recuperarSenha);
            }
            Collection<PessoaFisicaHistorico> attachedPessoaFisicaHistoricoCollection = new ArrayList<PessoaFisicaHistorico>();
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach : usuario.getPessoaFisicaHistoricoCollection()) {
                pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach = em.getReference(pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach.getClass(), pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach.getId());
                attachedPessoaFisicaHistoricoCollection.add(pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach);
            }
            usuario.setPessoaFisicaHistoricoCollection(attachedPessoaFisicaHistoricoCollection);
            Collection<PessoaJuridicaHistorico> attachedPessoaJuridicaHistoricoCollection = new ArrayList<PessoaJuridicaHistorico>();
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollectionPessoaJuridicaHistoricoToAttach : usuario.getPessoaJuridicaHistoricoCollection()) {
                pessoaJuridicaHistoricoCollectionPessoaJuridicaHistoricoToAttach = em.getReference(pessoaJuridicaHistoricoCollectionPessoaJuridicaHistoricoToAttach.getClass(), pessoaJuridicaHistoricoCollectionPessoaJuridicaHistoricoToAttach.getId());
                attachedPessoaJuridicaHistoricoCollection.add(pessoaJuridicaHistoricoCollectionPessoaJuridicaHistoricoToAttach);
            }
            usuario.setPessoaJuridicaHistoricoCollection(attachedPessoaJuridicaHistoricoCollection);
            Collection<PessoaJuridica> attachedPessoaJuridicaCollection = new ArrayList<PessoaJuridica>();
            for (PessoaJuridica pessoaJuridicaCollectionPessoaJuridicaToAttach : usuario.getPessoaJuridicaCollection()) {
                pessoaJuridicaCollectionPessoaJuridicaToAttach = em.getReference(pessoaJuridicaCollectionPessoaJuridicaToAttach.getClass(), pessoaJuridicaCollectionPessoaJuridicaToAttach.getId());
                attachedPessoaJuridicaCollection.add(pessoaJuridicaCollectionPessoaJuridicaToAttach);
            }
            usuario.setPessoaJuridicaCollection(attachedPessoaJuridicaCollection);
            Collection<PessoaFisica> attachedPessoaFisicaCollection = new ArrayList<PessoaFisica>();
            for (PessoaFisica pessoaFisicaCollectionPessoaFisicaToAttach : usuario.getPessoaFisicaCollection()) {
                pessoaFisicaCollectionPessoaFisicaToAttach = em.getReference(pessoaFisicaCollectionPessoaFisicaToAttach.getClass(), pessoaFisicaCollectionPessoaFisicaToAttach.getId());
                attachedPessoaFisicaCollection.add(pessoaFisicaCollectionPessoaFisicaToAttach);
            }
            usuario.setPessoaFisicaCollection(attachedPessoaFisicaCollection);
            Collection<ProcessoJudicial> attachedProcessoJudicialCollection = new ArrayList<ProcessoJudicial>();
            for (ProcessoJudicial processoJudicialCollectionProcessoJudicialToAttach : usuario.getProcessoJudicialCollection()) {
                processoJudicialCollectionProcessoJudicialToAttach = em.getReference(processoJudicialCollectionProcessoJudicialToAttach.getClass(), processoJudicialCollectionProcessoJudicialToAttach.getId());
                attachedProcessoJudicialCollection.add(processoJudicialCollectionProcessoJudicialToAttach);
            }
            usuario.setProcessoJudicialCollection(attachedProcessoJudicialCollection);
            Collection<ProcessoJudicialHistorico> attachedProcessoJudicialHistoricoCollection = new ArrayList<ProcessoJudicialHistorico>();
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach : usuario.getProcessoJudicialHistoricoCollection()) {
                processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach = em.getReference(processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach.getClass(), processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach.getId());
                attachedProcessoJudicialHistoricoCollection.add(processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach);
            }
            usuario.setProcessoJudicialHistoricoCollection(attachedProcessoJudicialHistoricoCollection);
            Collection<PessoaJuridicaSucessao> attachedPessoaJuridicaSucessaoCollection = new ArrayList<PessoaJuridicaSucessao>();
            for (PessoaJuridicaSucessao pessoaJuridicaSucessaoCollectionPessoaJuridicaSucessaoToAttach : usuario.getPessoaJuridicaSucessaoCollection()) {
                pessoaJuridicaSucessaoCollectionPessoaJuridicaSucessaoToAttach = em.getReference(pessoaJuridicaSucessaoCollectionPessoaJuridicaSucessaoToAttach.getClass(), pessoaJuridicaSucessaoCollectionPessoaJuridicaSucessaoToAttach.getId());
                attachedPessoaJuridicaSucessaoCollection.add(pessoaJuridicaSucessaoCollectionPessoaJuridicaSucessaoToAttach);
            }
            usuario.setPessoaJuridicaSucessaoCollection(attachedPessoaJuridicaSucessaoCollection);
            em.persist(usuario);
            if (recuperarSenha != null) {
                Usuario oldUsuarioOfRecuperarSenha = recuperarSenha.getUsuario();
                if (oldUsuarioOfRecuperarSenha != null) {
                    oldUsuarioOfRecuperarSenha.setRecuperarSenha(null);
                    oldUsuarioOfRecuperarSenha = em.merge(oldUsuarioOfRecuperarSenha);
                }
                recuperarSenha.setUsuario(usuario);
                recuperarSenha = em.merge(recuperarSenha);
            }
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionPessoaFisicaHistorico : usuario.getPessoaFisicaHistoricoCollection()) {
                Usuario oldUsuarioFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico = pessoaFisicaHistoricoCollectionPessoaFisicaHistorico.getUsuarioFk();
                pessoaFisicaHistoricoCollectionPessoaFisicaHistorico.setUsuarioFk(usuario);
                pessoaFisicaHistoricoCollectionPessoaFisicaHistorico = em.merge(pessoaFisicaHistoricoCollectionPessoaFisicaHistorico);
                if (oldUsuarioFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico != null) {
                    oldUsuarioFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistoricoCollectionPessoaFisicaHistorico);
                    oldUsuarioFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico = em.merge(oldUsuarioFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico);
                }
            }
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico : usuario.getPessoaJuridicaHistoricoCollection()) {
                Usuario oldUsuarioFkOfPessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico = pessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico.getUsuarioFk();
                pessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico.setUsuarioFk(usuario);
                pessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico = em.merge(pessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico);
                if (oldUsuarioFkOfPessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico != null) {
                    oldUsuarioFkOfPessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico.getPessoaJuridicaHistoricoCollection().remove(pessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico);
                    oldUsuarioFkOfPessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico = em.merge(oldUsuarioFkOfPessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico);
                }
            }
            for (PessoaJuridica pessoaJuridicaCollectionPessoaJuridica : usuario.getPessoaJuridicaCollection()) {
                Usuario oldUsuarioFkOfPessoaJuridicaCollectionPessoaJuridica = pessoaJuridicaCollectionPessoaJuridica.getUsuarioFk();
                pessoaJuridicaCollectionPessoaJuridica.setUsuarioFk(usuario);
                pessoaJuridicaCollectionPessoaJuridica = em.merge(pessoaJuridicaCollectionPessoaJuridica);
                if (oldUsuarioFkOfPessoaJuridicaCollectionPessoaJuridica != null) {
                    oldUsuarioFkOfPessoaJuridicaCollectionPessoaJuridica.getPessoaJuridicaCollection().remove(pessoaJuridicaCollectionPessoaJuridica);
                    oldUsuarioFkOfPessoaJuridicaCollectionPessoaJuridica = em.merge(oldUsuarioFkOfPessoaJuridicaCollectionPessoaJuridica);
                }
            }
            for (PessoaFisica pessoaFisicaCollectionPessoaFisica : usuario.getPessoaFisicaCollection()) {
                Usuario oldUsuarioFkOfPessoaFisicaCollectionPessoaFisica = pessoaFisicaCollectionPessoaFisica.getUsuarioFk();
                pessoaFisicaCollectionPessoaFisica.setUsuarioFk(usuario);
                pessoaFisicaCollectionPessoaFisica = em.merge(pessoaFisicaCollectionPessoaFisica);
                if (oldUsuarioFkOfPessoaFisicaCollectionPessoaFisica != null) {
                    oldUsuarioFkOfPessoaFisicaCollectionPessoaFisica.getPessoaFisicaCollection().remove(pessoaFisicaCollectionPessoaFisica);
                    oldUsuarioFkOfPessoaFisicaCollectionPessoaFisica = em.merge(oldUsuarioFkOfPessoaFisicaCollectionPessoaFisica);
                }
            }
            for (ProcessoJudicial processoJudicialCollectionProcessoJudicial : usuario.getProcessoJudicialCollection()) {
                Usuario oldUsuarioFkOfProcessoJudicialCollectionProcessoJudicial = processoJudicialCollectionProcessoJudicial.getUsuarioFk();
                processoJudicialCollectionProcessoJudicial.setUsuarioFk(usuario);
                processoJudicialCollectionProcessoJudicial = em.merge(processoJudicialCollectionProcessoJudicial);
                if (oldUsuarioFkOfProcessoJudicialCollectionProcessoJudicial != null) {
                    oldUsuarioFkOfProcessoJudicialCollectionProcessoJudicial.getProcessoJudicialCollection().remove(processoJudicialCollectionProcessoJudicial);
                    oldUsuarioFkOfProcessoJudicialCollectionProcessoJudicial = em.merge(oldUsuarioFkOfProcessoJudicialCollectionProcessoJudicial);
                }
            }
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionProcessoJudicialHistorico : usuario.getProcessoJudicialHistoricoCollection()) {
                Usuario oldUsuarioFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico = processoJudicialHistoricoCollectionProcessoJudicialHistorico.getUsuarioFk();
                processoJudicialHistoricoCollectionProcessoJudicialHistorico.setUsuarioFk(usuario);
                processoJudicialHistoricoCollectionProcessoJudicialHistorico = em.merge(processoJudicialHistoricoCollectionProcessoJudicialHistorico);
                if (oldUsuarioFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico != null) {
                    oldUsuarioFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico.getProcessoJudicialHistoricoCollection().remove(processoJudicialHistoricoCollectionProcessoJudicialHistorico);
                    oldUsuarioFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico = em.merge(oldUsuarioFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico);
                }
            }
            for (PessoaJuridicaSucessao pessoaJuridicaSucessaoCollectionPessoaJuridicaSucessao : usuario.getPessoaJuridicaSucessaoCollection()) {
                Usuario oldUsuarioFkOfPessoaJuridicaSucessaoCollectionPessoaJuridicaSucessao = pessoaJuridicaSucessaoCollectionPessoaJuridicaSucessao.getUsuarioFk();
                pessoaJuridicaSucessaoCollectionPessoaJuridicaSucessao.setUsuarioFk(usuario);
                pessoaJuridicaSucessaoCollectionPessoaJuridicaSucessao = em.merge(pessoaJuridicaSucessaoCollectionPessoaJuridicaSucessao);
                if (oldUsuarioFkOfPessoaJuridicaSucessaoCollectionPessoaJuridicaSucessao != null) {
                    oldUsuarioFkOfPessoaJuridicaSucessaoCollectionPessoaJuridicaSucessao.getPessoaJuridicaSucessaoCollection().remove(pessoaJuridicaSucessaoCollectionPessoaJuridicaSucessao);
                    oldUsuarioFkOfPessoaJuridicaSucessaoCollectionPessoaJuridicaSucessao = em.merge(oldUsuarioFkOfPessoaJuridicaSucessaoCollectionPessoaJuridicaSucessao);
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

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            RecuperarSenha recuperarSenhaOld = persistentUsuario.getRecuperarSenha();
            RecuperarSenha recuperarSenhaNew = usuario.getRecuperarSenha();
            Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollectionOld = persistentUsuario.getPessoaFisicaHistoricoCollection();
            Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollectionNew = usuario.getPessoaFisicaHistoricoCollection();
            Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollectionOld = persistentUsuario.getPessoaJuridicaHistoricoCollection();
            Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollectionNew = usuario.getPessoaJuridicaHistoricoCollection();
            Collection<PessoaJuridica> pessoaJuridicaCollectionOld = persistentUsuario.getPessoaJuridicaCollection();
            Collection<PessoaJuridica> pessoaJuridicaCollectionNew = usuario.getPessoaJuridicaCollection();
            Collection<PessoaFisica> pessoaFisicaCollectionOld = persistentUsuario.getPessoaFisicaCollection();
            Collection<PessoaFisica> pessoaFisicaCollectionNew = usuario.getPessoaFisicaCollection();
            Collection<ProcessoJudicial> processoJudicialCollectionOld = persistentUsuario.getProcessoJudicialCollection();
            Collection<ProcessoJudicial> processoJudicialCollectionNew = usuario.getProcessoJudicialCollection();
            Collection<ProcessoJudicialHistorico> processoJudicialHistoricoCollectionOld = persistentUsuario.getProcessoJudicialHistoricoCollection();
            Collection<ProcessoJudicialHistorico> processoJudicialHistoricoCollectionNew = usuario.getProcessoJudicialHistoricoCollection();
            Collection<PessoaJuridicaSucessao> pessoaJuridicaSucessaoCollectionOld = persistentUsuario.getPessoaJuridicaSucessaoCollection();
            Collection<PessoaJuridicaSucessao> pessoaJuridicaSucessaoCollectionNew = usuario.getPessoaJuridicaSucessaoCollection();
            List<String> illegalOrphanMessages = null;
            if (recuperarSenhaOld != null && !recuperarSenhaOld.equals(recuperarSenhaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain RecuperarSenha " + recuperarSenhaOld + " since its usuario field is not nullable.");
            }
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico : pessoaFisicaHistoricoCollectionOld) {
                if (!pessoaFisicaHistoricoCollectionNew.contains(pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PessoaFisicaHistorico " + pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico + " since its usuarioFk field is not nullable.");
                }
            }
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollectionOldPessoaJuridicaHistorico : pessoaJuridicaHistoricoCollectionOld) {
                if (!pessoaJuridicaHistoricoCollectionNew.contains(pessoaJuridicaHistoricoCollectionOldPessoaJuridicaHistorico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PessoaJuridicaHistorico " + pessoaJuridicaHistoricoCollectionOldPessoaJuridicaHistorico + " since its usuarioFk field is not nullable.");
                }
            }
            for (PessoaJuridica pessoaJuridicaCollectionOldPessoaJuridica : pessoaJuridicaCollectionOld) {
                if (!pessoaJuridicaCollectionNew.contains(pessoaJuridicaCollectionOldPessoaJuridica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PessoaJuridica " + pessoaJuridicaCollectionOldPessoaJuridica + " since its usuarioFk field is not nullable.");
                }
            }
            for (PessoaFisica pessoaFisicaCollectionOldPessoaFisica : pessoaFisicaCollectionOld) {
                if (!pessoaFisicaCollectionNew.contains(pessoaFisicaCollectionOldPessoaFisica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PessoaFisica " + pessoaFisicaCollectionOldPessoaFisica + " since its usuarioFk field is not nullable.");
                }
            }
            for (ProcessoJudicial processoJudicialCollectionOldProcessoJudicial : processoJudicialCollectionOld) {
                if (!processoJudicialCollectionNew.contains(processoJudicialCollectionOldProcessoJudicial)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProcessoJudicial " + processoJudicialCollectionOldProcessoJudicial + " since its usuarioFk field is not nullable.");
                }
            }
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionOldProcessoJudicialHistorico : processoJudicialHistoricoCollectionOld) {
                if (!processoJudicialHistoricoCollectionNew.contains(processoJudicialHistoricoCollectionOldProcessoJudicialHistorico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProcessoJudicialHistorico " + processoJudicialHistoricoCollectionOldProcessoJudicialHistorico + " since its usuarioFk field is not nullable.");
                }
            }
            for (PessoaJuridicaSucessao pessoaJuridicaSucessaoCollectionOldPessoaJuridicaSucessao : pessoaJuridicaSucessaoCollectionOld) {
                if (!pessoaJuridicaSucessaoCollectionNew.contains(pessoaJuridicaSucessaoCollectionOldPessoaJuridicaSucessao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PessoaJuridicaSucessao " + pessoaJuridicaSucessaoCollectionOldPessoaJuridicaSucessao + " since its usuarioFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (recuperarSenhaNew != null) {
                recuperarSenhaNew = em.getReference(recuperarSenhaNew.getClass(), recuperarSenhaNew.getId());
                usuario.setRecuperarSenha(recuperarSenhaNew);
            }
            Collection<PessoaFisicaHistorico> attachedPessoaFisicaHistoricoCollectionNew = new ArrayList<PessoaFisicaHistorico>();
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach : pessoaFisicaHistoricoCollectionNew) {
                pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach = em.getReference(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach.getClass(), pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach.getId());
                attachedPessoaFisicaHistoricoCollectionNew.add(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach);
            }
            pessoaFisicaHistoricoCollectionNew = attachedPessoaFisicaHistoricoCollectionNew;
            usuario.setPessoaFisicaHistoricoCollection(pessoaFisicaHistoricoCollectionNew);
            Collection<PessoaJuridicaHistorico> attachedPessoaJuridicaHistoricoCollectionNew = new ArrayList<PessoaJuridicaHistorico>();
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistoricoToAttach : pessoaJuridicaHistoricoCollectionNew) {
                pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistoricoToAttach = em.getReference(pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistoricoToAttach.getClass(), pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistoricoToAttach.getId());
                attachedPessoaJuridicaHistoricoCollectionNew.add(pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistoricoToAttach);
            }
            pessoaJuridicaHistoricoCollectionNew = attachedPessoaJuridicaHistoricoCollectionNew;
            usuario.setPessoaJuridicaHistoricoCollection(pessoaJuridicaHistoricoCollectionNew);
            Collection<PessoaJuridica> attachedPessoaJuridicaCollectionNew = new ArrayList<PessoaJuridica>();
            for (PessoaJuridica pessoaJuridicaCollectionNewPessoaJuridicaToAttach : pessoaJuridicaCollectionNew) {
                pessoaJuridicaCollectionNewPessoaJuridicaToAttach = em.getReference(pessoaJuridicaCollectionNewPessoaJuridicaToAttach.getClass(), pessoaJuridicaCollectionNewPessoaJuridicaToAttach.getId());
                attachedPessoaJuridicaCollectionNew.add(pessoaJuridicaCollectionNewPessoaJuridicaToAttach);
            }
            pessoaJuridicaCollectionNew = attachedPessoaJuridicaCollectionNew;
            usuario.setPessoaJuridicaCollection(pessoaJuridicaCollectionNew);
            Collection<PessoaFisica> attachedPessoaFisicaCollectionNew = new ArrayList<PessoaFisica>();
            for (PessoaFisica pessoaFisicaCollectionNewPessoaFisicaToAttach : pessoaFisicaCollectionNew) {
                pessoaFisicaCollectionNewPessoaFisicaToAttach = em.getReference(pessoaFisicaCollectionNewPessoaFisicaToAttach.getClass(), pessoaFisicaCollectionNewPessoaFisicaToAttach.getId());
                attachedPessoaFisicaCollectionNew.add(pessoaFisicaCollectionNewPessoaFisicaToAttach);
            }
            pessoaFisicaCollectionNew = attachedPessoaFisicaCollectionNew;
            usuario.setPessoaFisicaCollection(pessoaFisicaCollectionNew);
            Collection<ProcessoJudicial> attachedProcessoJudicialCollectionNew = new ArrayList<ProcessoJudicial>();
            for (ProcessoJudicial processoJudicialCollectionNewProcessoJudicialToAttach : processoJudicialCollectionNew) {
                processoJudicialCollectionNewProcessoJudicialToAttach = em.getReference(processoJudicialCollectionNewProcessoJudicialToAttach.getClass(), processoJudicialCollectionNewProcessoJudicialToAttach.getId());
                attachedProcessoJudicialCollectionNew.add(processoJudicialCollectionNewProcessoJudicialToAttach);
            }
            processoJudicialCollectionNew = attachedProcessoJudicialCollectionNew;
            usuario.setProcessoJudicialCollection(processoJudicialCollectionNew);
            Collection<ProcessoJudicialHistorico> attachedProcessoJudicialHistoricoCollectionNew = new ArrayList<ProcessoJudicialHistorico>();
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach : processoJudicialHistoricoCollectionNew) {
                processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach = em.getReference(processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach.getClass(), processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach.getId());
                attachedProcessoJudicialHistoricoCollectionNew.add(processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach);
            }
            processoJudicialHistoricoCollectionNew = attachedProcessoJudicialHistoricoCollectionNew;
            usuario.setProcessoJudicialHistoricoCollection(processoJudicialHistoricoCollectionNew);
            Collection<PessoaJuridicaSucessao> attachedPessoaJuridicaSucessaoCollectionNew = new ArrayList<PessoaJuridicaSucessao>();
            for (PessoaJuridicaSucessao pessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessaoToAttach : pessoaJuridicaSucessaoCollectionNew) {
                pessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessaoToAttach = em.getReference(pessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessaoToAttach.getClass(), pessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessaoToAttach.getId());
                attachedPessoaJuridicaSucessaoCollectionNew.add(pessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessaoToAttach);
            }
            pessoaJuridicaSucessaoCollectionNew = attachedPessoaJuridicaSucessaoCollectionNew;
            usuario.setPessoaJuridicaSucessaoCollection(pessoaJuridicaSucessaoCollectionNew);
            usuario = em.merge(usuario);
            if (recuperarSenhaNew != null && !recuperarSenhaNew.equals(recuperarSenhaOld)) {
                Usuario oldUsuarioOfRecuperarSenha = recuperarSenhaNew.getUsuario();
                if (oldUsuarioOfRecuperarSenha != null) {
                    oldUsuarioOfRecuperarSenha.setRecuperarSenha(null);
                    oldUsuarioOfRecuperarSenha = em.merge(oldUsuarioOfRecuperarSenha);
                }
                recuperarSenhaNew.setUsuario(usuario);
                recuperarSenhaNew = em.merge(recuperarSenhaNew);
            }
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico : pessoaFisicaHistoricoCollectionNew) {
                if (!pessoaFisicaHistoricoCollectionOld.contains(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico)) {
                    Usuario oldUsuarioFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico = pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico.getUsuarioFk();
                    pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico.setUsuarioFk(usuario);
                    pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico = em.merge(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico);
                    if (oldUsuarioFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico != null && !oldUsuarioFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico.equals(usuario)) {
                        oldUsuarioFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico);
                        oldUsuarioFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico = em.merge(oldUsuarioFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico);
                    }
                }
            }
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico : pessoaJuridicaHistoricoCollectionNew) {
                if (!pessoaJuridicaHistoricoCollectionOld.contains(pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico)) {
                    Usuario oldUsuarioFkOfPessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico = pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico.getUsuarioFk();
                    pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico.setUsuarioFk(usuario);
                    pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico = em.merge(pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico);
                    if (oldUsuarioFkOfPessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico != null && !oldUsuarioFkOfPessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico.equals(usuario)) {
                        oldUsuarioFkOfPessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico.getPessoaJuridicaHistoricoCollection().remove(pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico);
                        oldUsuarioFkOfPessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico = em.merge(oldUsuarioFkOfPessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico);
                    }
                }
            }
            for (PessoaJuridica pessoaJuridicaCollectionNewPessoaJuridica : pessoaJuridicaCollectionNew) {
                if (!pessoaJuridicaCollectionOld.contains(pessoaJuridicaCollectionNewPessoaJuridica)) {
                    Usuario oldUsuarioFkOfPessoaJuridicaCollectionNewPessoaJuridica = pessoaJuridicaCollectionNewPessoaJuridica.getUsuarioFk();
                    pessoaJuridicaCollectionNewPessoaJuridica.setUsuarioFk(usuario);
                    pessoaJuridicaCollectionNewPessoaJuridica = em.merge(pessoaJuridicaCollectionNewPessoaJuridica);
                    if (oldUsuarioFkOfPessoaJuridicaCollectionNewPessoaJuridica != null && !oldUsuarioFkOfPessoaJuridicaCollectionNewPessoaJuridica.equals(usuario)) {
                        oldUsuarioFkOfPessoaJuridicaCollectionNewPessoaJuridica.getPessoaJuridicaCollection().remove(pessoaJuridicaCollectionNewPessoaJuridica);
                        oldUsuarioFkOfPessoaJuridicaCollectionNewPessoaJuridica = em.merge(oldUsuarioFkOfPessoaJuridicaCollectionNewPessoaJuridica);
                    }
                }
            }
            for (PessoaFisica pessoaFisicaCollectionNewPessoaFisica : pessoaFisicaCollectionNew) {
                if (!pessoaFisicaCollectionOld.contains(pessoaFisicaCollectionNewPessoaFisica)) {
                    Usuario oldUsuarioFkOfPessoaFisicaCollectionNewPessoaFisica = pessoaFisicaCollectionNewPessoaFisica.getUsuarioFk();
                    pessoaFisicaCollectionNewPessoaFisica.setUsuarioFk(usuario);
                    pessoaFisicaCollectionNewPessoaFisica = em.merge(pessoaFisicaCollectionNewPessoaFisica);
                    if (oldUsuarioFkOfPessoaFisicaCollectionNewPessoaFisica != null && !oldUsuarioFkOfPessoaFisicaCollectionNewPessoaFisica.equals(usuario)) {
                        oldUsuarioFkOfPessoaFisicaCollectionNewPessoaFisica.getPessoaFisicaCollection().remove(pessoaFisicaCollectionNewPessoaFisica);
                        oldUsuarioFkOfPessoaFisicaCollectionNewPessoaFisica = em.merge(oldUsuarioFkOfPessoaFisicaCollectionNewPessoaFisica);
                    }
                }
            }
            for (ProcessoJudicial processoJudicialCollectionNewProcessoJudicial : processoJudicialCollectionNew) {
                if (!processoJudicialCollectionOld.contains(processoJudicialCollectionNewProcessoJudicial)) {
                    Usuario oldUsuarioFkOfProcessoJudicialCollectionNewProcessoJudicial = processoJudicialCollectionNewProcessoJudicial.getUsuarioFk();
                    processoJudicialCollectionNewProcessoJudicial.setUsuarioFk(usuario);
                    processoJudicialCollectionNewProcessoJudicial = em.merge(processoJudicialCollectionNewProcessoJudicial);
                    if (oldUsuarioFkOfProcessoJudicialCollectionNewProcessoJudicial != null && !oldUsuarioFkOfProcessoJudicialCollectionNewProcessoJudicial.equals(usuario)) {
                        oldUsuarioFkOfProcessoJudicialCollectionNewProcessoJudicial.getProcessoJudicialCollection().remove(processoJudicialCollectionNewProcessoJudicial);
                        oldUsuarioFkOfProcessoJudicialCollectionNewProcessoJudicial = em.merge(oldUsuarioFkOfProcessoJudicialCollectionNewProcessoJudicial);
                    }
                }
            }
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionNewProcessoJudicialHistorico : processoJudicialHistoricoCollectionNew) {
                if (!processoJudicialHistoricoCollectionOld.contains(processoJudicialHistoricoCollectionNewProcessoJudicialHistorico)) {
                    Usuario oldUsuarioFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico = processoJudicialHistoricoCollectionNewProcessoJudicialHistorico.getUsuarioFk();
                    processoJudicialHistoricoCollectionNewProcessoJudicialHistorico.setUsuarioFk(usuario);
                    processoJudicialHistoricoCollectionNewProcessoJudicialHistorico = em.merge(processoJudicialHistoricoCollectionNewProcessoJudicialHistorico);
                    if (oldUsuarioFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico != null && !oldUsuarioFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico.equals(usuario)) {
                        oldUsuarioFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico.getProcessoJudicialHistoricoCollection().remove(processoJudicialHistoricoCollectionNewProcessoJudicialHistorico);
                        oldUsuarioFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico = em.merge(oldUsuarioFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico);
                    }
                }
            }
            for (PessoaJuridicaSucessao pessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao : pessoaJuridicaSucessaoCollectionNew) {
                if (!pessoaJuridicaSucessaoCollectionOld.contains(pessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao)) {
                    Usuario oldUsuarioFkOfPessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao = pessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao.getUsuarioFk();
                    pessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao.setUsuarioFk(usuario);
                    pessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao = em.merge(pessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao);
                    if (oldUsuarioFkOfPessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao != null && !oldUsuarioFkOfPessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao.equals(usuario)) {
                        oldUsuarioFkOfPessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao.getPessoaJuridicaSucessaoCollection().remove(pessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao);
                        oldUsuarioFkOfPessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao = em.merge(oldUsuarioFkOfPessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao);
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
                Integer id = usuario.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            RecuperarSenha recuperarSenhaOrphanCheck = usuario.getRecuperarSenha();
            if (recuperarSenhaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the RecuperarSenha " + recuperarSenhaOrphanCheck + " in its recuperarSenha field has a non-nullable usuario field.");
            }
            Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollectionOrphanCheck = usuario.getPessoaFisicaHistoricoCollection();
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionOrphanCheckPessoaFisicaHistorico : pessoaFisicaHistoricoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the PessoaFisicaHistorico " + pessoaFisicaHistoricoCollectionOrphanCheckPessoaFisicaHistorico + " in its pessoaFisicaHistoricoCollection field has a non-nullable usuarioFk field.");
            }
            Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollectionOrphanCheck = usuario.getPessoaJuridicaHistoricoCollection();
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollectionOrphanCheckPessoaJuridicaHistorico : pessoaJuridicaHistoricoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the PessoaJuridicaHistorico " + pessoaJuridicaHistoricoCollectionOrphanCheckPessoaJuridicaHistorico + " in its pessoaJuridicaHistoricoCollection field has a non-nullable usuarioFk field.");
            }
            Collection<PessoaJuridica> pessoaJuridicaCollectionOrphanCheck = usuario.getPessoaJuridicaCollection();
            for (PessoaJuridica pessoaJuridicaCollectionOrphanCheckPessoaJuridica : pessoaJuridicaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the PessoaJuridica " + pessoaJuridicaCollectionOrphanCheckPessoaJuridica + " in its pessoaJuridicaCollection field has a non-nullable usuarioFk field.");
            }
            Collection<PessoaFisica> pessoaFisicaCollectionOrphanCheck = usuario.getPessoaFisicaCollection();
            for (PessoaFisica pessoaFisicaCollectionOrphanCheckPessoaFisica : pessoaFisicaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the PessoaFisica " + pessoaFisicaCollectionOrphanCheckPessoaFisica + " in its pessoaFisicaCollection field has a non-nullable usuarioFk field.");
            }
            Collection<ProcessoJudicial> processoJudicialCollectionOrphanCheck = usuario.getProcessoJudicialCollection();
            for (ProcessoJudicial processoJudicialCollectionOrphanCheckProcessoJudicial : processoJudicialCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the ProcessoJudicial " + processoJudicialCollectionOrphanCheckProcessoJudicial + " in its processoJudicialCollection field has a non-nullable usuarioFk field.");
            }
            Collection<ProcessoJudicialHistorico> processoJudicialHistoricoCollectionOrphanCheck = usuario.getProcessoJudicialHistoricoCollection();
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionOrphanCheckProcessoJudicialHistorico : processoJudicialHistoricoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the ProcessoJudicialHistorico " + processoJudicialHistoricoCollectionOrphanCheckProcessoJudicialHistorico + " in its processoJudicialHistoricoCollection field has a non-nullable usuarioFk field.");
            }
            Collection<PessoaJuridicaSucessao> pessoaJuridicaSucessaoCollectionOrphanCheck = usuario.getPessoaJuridicaSucessaoCollection();
            for (PessoaJuridicaSucessao pessoaJuridicaSucessaoCollectionOrphanCheckPessoaJuridicaSucessao : pessoaJuridicaSucessaoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the PessoaJuridicaSucessao " + pessoaJuridicaSucessaoCollectionOrphanCheckPessoaJuridicaSucessao + " in its pessoaJuridicaSucessaoCollection field has a non-nullable usuarioFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
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

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuarioByEmail(String email) {
        EntityManager em = getEntityManager();
        try {
            Usuario usuario = (Usuario) em.createNativeQuery("select * from usuario "
                    + "where email = '" + email + "'", Usuario.class).getSingleResult();
            return usuario;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public Usuario findUsuarioByCPF(String cpf) {
        EntityManager em = getEntityManager();
        try {
            Usuario usuario = (Usuario) em.createNativeQuery("select * from usuario "
                    + "where cpf = '" + cpf + "'", Usuario.class).getSingleResult();
            return usuario;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public Usuario findUsuarioByCNPJ(String cnpj) {
        EntityManager em = getEntityManager();
        try {
            Usuario usuario = (Usuario) em.createNativeQuery("select * from usuario "
                    + "where cnpj = '" + cnpj + "'", Usuario.class).getSingleResult();
            return usuario;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
