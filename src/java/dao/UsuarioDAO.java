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
import entidade.PessoaFisicaHistorico;
import entidade.PessoaFisicaJuridicaHistorico;
import entidade.PessoaJuridicaHistorico;
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
        if (usuario.getPessoaFisicaJuridicaHistoricoCollection() == null) {
            usuario.setPessoaFisicaJuridicaHistoricoCollection(new ArrayList<PessoaFisicaJuridicaHistorico>());
        }
        if (usuario.getEnderecoHistoricoCollection() == null) {
            usuario.setEnderecoHistoricoCollection(new ArrayList<EnderecoHistorico>());
        }
        if (usuario.getPessoaJuridicaHistoricoCollection() == null) {
            usuario.setPessoaJuridicaHistoricoCollection(new ArrayList<PessoaJuridicaHistorico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
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
            Collection<PessoaFisicaJuridicaHistorico> attachedPessoaFisicaJuridicaHistoricoCollection = new ArrayList<PessoaFisicaJuridicaHistorico>();
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistoricoToAttach : usuario.getPessoaFisicaJuridicaHistoricoCollection()) {
                pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistoricoToAttach = em.getReference(pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistoricoToAttach.getClass(), pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistoricoToAttach.getId());
                attachedPessoaFisicaJuridicaHistoricoCollection.add(pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistoricoToAttach);
            }
            usuario.setPessoaFisicaJuridicaHistoricoCollection(attachedPessoaFisicaJuridicaHistoricoCollection);
            Collection<EnderecoHistorico> attachedEnderecoHistoricoCollection = new ArrayList<EnderecoHistorico>();
            for (EnderecoHistorico enderecoHistoricoCollectionEnderecoHistoricoToAttach : usuario.getEnderecoHistoricoCollection()) {
                enderecoHistoricoCollectionEnderecoHistoricoToAttach = em.getReference(enderecoHistoricoCollectionEnderecoHistoricoToAttach.getClass(), enderecoHistoricoCollectionEnderecoHistoricoToAttach.getId());
                attachedEnderecoHistoricoCollection.add(enderecoHistoricoCollectionEnderecoHistoricoToAttach);
            }
            usuario.setEnderecoHistoricoCollection(attachedEnderecoHistoricoCollection);
            Collection<PessoaJuridicaHistorico> attachedPessoaJuridicaHistoricoCollection = new ArrayList<PessoaJuridicaHistorico>();
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollectionPessoaJuridicaHistoricoToAttach : usuario.getPessoaJuridicaHistoricoCollection()) {
                pessoaJuridicaHistoricoCollectionPessoaJuridicaHistoricoToAttach = em.getReference(pessoaJuridicaHistoricoCollectionPessoaJuridicaHistoricoToAttach.getClass(), pessoaJuridicaHistoricoCollectionPessoaJuridicaHistoricoToAttach.getId());
                attachedPessoaJuridicaHistoricoCollection.add(pessoaJuridicaHistoricoCollectionPessoaJuridicaHistoricoToAttach);
            }
            usuario.setPessoaJuridicaHistoricoCollection(attachedPessoaJuridicaHistoricoCollection);
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
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico : usuario.getPessoaFisicaJuridicaHistoricoCollection()) {
                Usuario oldUsuarioFkOfPessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico = pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico.getUsuarioFk();
                pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico.setUsuarioFk(usuario);
                pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico = em.merge(pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico);
                if (oldUsuarioFkOfPessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico != null) {
                    oldUsuarioFkOfPessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico.getPessoaFisicaJuridicaHistoricoCollection().remove(pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico);
                    oldUsuarioFkOfPessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico = em.merge(oldUsuarioFkOfPessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico);
                }
            }
            for (EnderecoHistorico enderecoHistoricoCollectionEnderecoHistorico : usuario.getEnderecoHistoricoCollection()) {
                Usuario oldUsuarioFkOfEnderecoHistoricoCollectionEnderecoHistorico = enderecoHistoricoCollectionEnderecoHistorico.getUsuarioFk();
                enderecoHistoricoCollectionEnderecoHistorico.setUsuarioFk(usuario);
                enderecoHistoricoCollectionEnderecoHistorico = em.merge(enderecoHistoricoCollectionEnderecoHistorico);
                if (oldUsuarioFkOfEnderecoHistoricoCollectionEnderecoHistorico != null) {
                    oldUsuarioFkOfEnderecoHistoricoCollectionEnderecoHistorico.getEnderecoHistoricoCollection().remove(enderecoHistoricoCollectionEnderecoHistorico);
                    oldUsuarioFkOfEnderecoHistoricoCollectionEnderecoHistorico = em.merge(oldUsuarioFkOfEnderecoHistoricoCollectionEnderecoHistorico);
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
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            RecuperarSenha recuperarSenhaOld = persistentUsuario.getRecuperarSenha();
            RecuperarSenha recuperarSenhaNew = usuario.getRecuperarSenha();
            Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollectionOld = persistentUsuario.getPessoaFisicaHistoricoCollection();
            Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollectionNew = usuario.getPessoaFisicaHistoricoCollection();
            Collection<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoCollectionOld = persistentUsuario.getPessoaFisicaJuridicaHistoricoCollection();
            Collection<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoCollectionNew = usuario.getPessoaFisicaJuridicaHistoricoCollection();
            Collection<EnderecoHistorico> enderecoHistoricoCollectionOld = persistentUsuario.getEnderecoHistoricoCollection();
            Collection<EnderecoHistorico> enderecoHistoricoCollectionNew = usuario.getEnderecoHistoricoCollection();
            Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollectionOld = persistentUsuario.getPessoaJuridicaHistoricoCollection();
            Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollectionNew = usuario.getPessoaJuridicaHistoricoCollection();
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
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionOldPessoaFisicaJuridicaHistorico : pessoaFisicaJuridicaHistoricoCollectionOld) {
                if (!pessoaFisicaJuridicaHistoricoCollectionNew.contains(pessoaFisicaJuridicaHistoricoCollectionOldPessoaFisicaJuridicaHistorico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PessoaFisicaJuridicaHistorico " + pessoaFisicaJuridicaHistoricoCollectionOldPessoaFisicaJuridicaHistorico + " since its usuarioFk field is not nullable.");
                }
            }
            for (EnderecoHistorico enderecoHistoricoCollectionOldEnderecoHistorico : enderecoHistoricoCollectionOld) {
                if (!enderecoHistoricoCollectionNew.contains(enderecoHistoricoCollectionOldEnderecoHistorico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EnderecoHistorico " + enderecoHistoricoCollectionOldEnderecoHistorico + " since its usuarioFk field is not nullable.");
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
            Collection<PessoaFisicaJuridicaHistorico> attachedPessoaFisicaJuridicaHistoricoCollectionNew = new ArrayList<PessoaFisicaJuridicaHistorico>();
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistoricoToAttach : pessoaFisicaJuridicaHistoricoCollectionNew) {
                pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistoricoToAttach = em.getReference(pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistoricoToAttach.getClass(), pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistoricoToAttach.getId());
                attachedPessoaFisicaJuridicaHistoricoCollectionNew.add(pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistoricoToAttach);
            }
            pessoaFisicaJuridicaHistoricoCollectionNew = attachedPessoaFisicaJuridicaHistoricoCollectionNew;
            usuario.setPessoaFisicaJuridicaHistoricoCollection(pessoaFisicaJuridicaHistoricoCollectionNew);
            Collection<EnderecoHistorico> attachedEnderecoHistoricoCollectionNew = new ArrayList<EnderecoHistorico>();
            for (EnderecoHistorico enderecoHistoricoCollectionNewEnderecoHistoricoToAttach : enderecoHistoricoCollectionNew) {
                enderecoHistoricoCollectionNewEnderecoHistoricoToAttach = em.getReference(enderecoHistoricoCollectionNewEnderecoHistoricoToAttach.getClass(), enderecoHistoricoCollectionNewEnderecoHistoricoToAttach.getId());
                attachedEnderecoHistoricoCollectionNew.add(enderecoHistoricoCollectionNewEnderecoHistoricoToAttach);
            }
            enderecoHistoricoCollectionNew = attachedEnderecoHistoricoCollectionNew;
            usuario.setEnderecoHistoricoCollection(enderecoHistoricoCollectionNew);
            Collection<PessoaJuridicaHistorico> attachedPessoaJuridicaHistoricoCollectionNew = new ArrayList<PessoaJuridicaHistorico>();
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistoricoToAttach : pessoaJuridicaHistoricoCollectionNew) {
                pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistoricoToAttach = em.getReference(pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistoricoToAttach.getClass(), pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistoricoToAttach.getId());
                attachedPessoaJuridicaHistoricoCollectionNew.add(pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistoricoToAttach);
            }
            pessoaJuridicaHistoricoCollectionNew = attachedPessoaJuridicaHistoricoCollectionNew;
            usuario.setPessoaJuridicaHistoricoCollection(pessoaJuridicaHistoricoCollectionNew);
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
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico : pessoaFisicaJuridicaHistoricoCollectionNew) {
                if (!pessoaFisicaJuridicaHistoricoCollectionOld.contains(pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico)) {
                    Usuario oldUsuarioFkOfPessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico = pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico.getUsuarioFk();
                    pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico.setUsuarioFk(usuario);
                    pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico = em.merge(pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico);
                    if (oldUsuarioFkOfPessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico != null && !oldUsuarioFkOfPessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico.equals(usuario)) {
                        oldUsuarioFkOfPessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico.getPessoaFisicaJuridicaHistoricoCollection().remove(pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico);
                        oldUsuarioFkOfPessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico = em.merge(oldUsuarioFkOfPessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico);
                    }
                }
            }
            for (EnderecoHistorico enderecoHistoricoCollectionNewEnderecoHistorico : enderecoHistoricoCollectionNew) {
                if (!enderecoHistoricoCollectionOld.contains(enderecoHistoricoCollectionNewEnderecoHistorico)) {
                    Usuario oldUsuarioFkOfEnderecoHistoricoCollectionNewEnderecoHistorico = enderecoHistoricoCollectionNewEnderecoHistorico.getUsuarioFk();
                    enderecoHistoricoCollectionNewEnderecoHistorico.setUsuarioFk(usuario);
                    enderecoHistoricoCollectionNewEnderecoHistorico = em.merge(enderecoHistoricoCollectionNewEnderecoHistorico);
                    if (oldUsuarioFkOfEnderecoHistoricoCollectionNewEnderecoHistorico != null && !oldUsuarioFkOfEnderecoHistoricoCollectionNewEnderecoHistorico.equals(usuario)) {
                        oldUsuarioFkOfEnderecoHistoricoCollectionNewEnderecoHistorico.getEnderecoHistoricoCollection().remove(enderecoHistoricoCollectionNewEnderecoHistorico);
                        oldUsuarioFkOfEnderecoHistoricoCollectionNewEnderecoHistorico = em.merge(oldUsuarioFkOfEnderecoHistoricoCollectionNewEnderecoHistorico);
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
            em = getEntityManager();
            em.getTransaction().begin();
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
            Collection<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoCollectionOrphanCheck = usuario.getPessoaFisicaJuridicaHistoricoCollection();
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionOrphanCheckPessoaFisicaJuridicaHistorico : pessoaFisicaJuridicaHistoricoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the PessoaFisicaJuridicaHistorico " + pessoaFisicaJuridicaHistoricoCollectionOrphanCheckPessoaFisicaJuridicaHistorico + " in its pessoaFisicaJuridicaHistoricoCollection field has a non-nullable usuarioFk field.");
            }
            Collection<EnderecoHistorico> enderecoHistoricoCollectionOrphanCheck = usuario.getEnderecoHistoricoCollection();
            for (EnderecoHistorico enderecoHistoricoCollectionOrphanCheckEnderecoHistorico : enderecoHistoricoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the EnderecoHistorico " + enderecoHistoricoCollectionOrphanCheckEnderecoHistorico + " in its enderecoHistoricoCollection field has a non-nullable usuarioFk field.");
            }
            Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollectionOrphanCheck = usuario.getPessoaJuridicaHistoricoCollection();
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollectionOrphanCheckPessoaJuridicaHistorico : pessoaJuridicaHistoricoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the PessoaJuridicaHistorico " + pessoaJuridicaHistoricoCollectionOrphanCheckPessoaJuridicaHistorico + " in its pessoaJuridicaHistoricoCollection field has a non-nullable usuarioFk field.");
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
}
