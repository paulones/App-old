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
import entidade.Estado;
import entidade.EstadoCivil;
import entidade.Nacionalidade;
import entidade.Pais;
import entidade.PessoaFisica;
import entidade.PessoaFisicaHistorico;
import entidade.PessoaFisicaJuridica;
import entidade.PessoaFisicaJuridicaHistorico;
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
public class PessoaFisicaDAO implements Serializable {

    public PessoaFisicaDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PessoaFisica pessoaFisica) throws RollbackFailureException, Exception {
        if (pessoaFisica.getPessoaFisicaHistoricoCollection() == null) {
            pessoaFisica.setPessoaFisicaHistoricoCollection(new ArrayList<PessoaFisicaHistorico>());
        }
        if (pessoaFisica.getPessoaFisicaJuridicaCollection() == null) {
            pessoaFisica.setPessoaFisicaJuridicaCollection(new ArrayList<PessoaFisicaJuridica>());
        }
        if (pessoaFisica.getPessoaFisicaJuridicaHistoricoCollection() == null) {
            pessoaFisica.setPessoaFisicaJuridicaHistoricoCollection(new ArrayList<PessoaFisicaJuridicaHistorico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cidade cidadeFk = pessoaFisica.getCidadeFk();
            if (cidadeFk != null) {
                cidadeFk = em.getReference(cidadeFk.getClass(), cidadeFk.getId());
                pessoaFisica.setCidadeFk(cidadeFk);
            }
            Estado estadoFk = pessoaFisica.getEstadoFk();
            if (estadoFk != null) {
                estadoFk = em.getReference(estadoFk.getClass(), estadoFk.getId());
                pessoaFisica.setEstadoFk(estadoFk);
            }
            Estado rgUfFk = pessoaFisica.getRgUfFk();
            if (rgUfFk != null) {
                rgUfFk = em.getReference(rgUfFk.getClass(), rgUfFk.getId());
                pessoaFisica.setRgUfFk(rgUfFk);
            }
            EstadoCivil estadoCivilFk = pessoaFisica.getEstadoCivilFk();
            if (estadoCivilFk != null) {
                estadoCivilFk = em.getReference(estadoCivilFk.getClass(), estadoCivilFk.getId());
                pessoaFisica.setEstadoCivilFk(estadoCivilFk);
            }
            Nacionalidade nacionalidadeFk = pessoaFisica.getNacionalidadeFk();
            if (nacionalidadeFk != null) {
                nacionalidadeFk = em.getReference(nacionalidadeFk.getClass(), nacionalidadeFk.getId());
                pessoaFisica.setNacionalidadeFk(nacionalidadeFk);
            }
            Pais paisFk = pessoaFisica.getPaisFk();
            if (paisFk != null) {
                paisFk = em.getReference(paisFk.getClass(), paisFk.getId());
                pessoaFisica.setPaisFk(paisFk);
            }
            Usuario usuarioFk = pessoaFisica.getUsuarioFk();
            if (usuarioFk != null) {
                usuarioFk = em.getReference(usuarioFk.getClass(), usuarioFk.getId());
                pessoaFisica.setUsuarioFk(usuarioFk);
            }
            Cidade cidadeEleitoralFk = pessoaFisica.getCidadeEleitoralFk();
            if (cidadeEleitoralFk != null) {
                cidadeEleitoralFk = em.getReference(cidadeEleitoralFk.getClass(), cidadeEleitoralFk.getId());
                pessoaFisica.setCidadeEleitoralFk(cidadeEleitoralFk);
            }
            Estado estadoEleitoralFk = pessoaFisica.getEstadoEleitoralFk();
            if (estadoEleitoralFk != null) {
                estadoEleitoralFk = em.getReference(estadoEleitoralFk.getClass(), estadoEleitoralFk.getId());
                pessoaFisica.setEstadoEleitoralFk(estadoEleitoralFk);
            }
            Collection<PessoaFisicaHistorico> attachedPessoaFisicaHistoricoCollection = new ArrayList<PessoaFisicaHistorico>();
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach : pessoaFisica.getPessoaFisicaHistoricoCollection()) {
                pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach = em.getReference(pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach.getClass(), pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach.getId());
                attachedPessoaFisicaHistoricoCollection.add(pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach);
            }
            pessoaFisica.setPessoaFisicaHistoricoCollection(attachedPessoaFisicaHistoricoCollection);
            Collection<PessoaFisicaJuridica> attachedPessoaFisicaJuridicaCollection = new ArrayList<PessoaFisicaJuridica>();
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionPessoaFisicaJuridicaToAttach : pessoaFisica.getPessoaFisicaJuridicaCollection()) {
                pessoaFisicaJuridicaCollectionPessoaFisicaJuridicaToAttach = em.getReference(pessoaFisicaJuridicaCollectionPessoaFisicaJuridicaToAttach.getClass(), pessoaFisicaJuridicaCollectionPessoaFisicaJuridicaToAttach.getId());
                attachedPessoaFisicaJuridicaCollection.add(pessoaFisicaJuridicaCollectionPessoaFisicaJuridicaToAttach);
            }
            pessoaFisica.setPessoaFisicaJuridicaCollection(attachedPessoaFisicaJuridicaCollection);
            Collection<PessoaFisicaJuridicaHistorico> attachedPessoaFisicaJuridicaHistoricoCollection = new ArrayList<PessoaFisicaJuridicaHistorico>();
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistoricoToAttach : pessoaFisica.getPessoaFisicaJuridicaHistoricoCollection()) {
                pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistoricoToAttach = em.getReference(pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistoricoToAttach.getClass(), pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistoricoToAttach.getId());
                attachedPessoaFisicaJuridicaHistoricoCollection.add(pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistoricoToAttach);
            }
            pessoaFisica.setPessoaFisicaJuridicaHistoricoCollection(attachedPessoaFisicaJuridicaHistoricoCollection);
            em.persist(pessoaFisica);
            if (cidadeFk != null) {
                cidadeFk.getPessoaFisicaCollection().add(pessoaFisica);
                cidadeFk = em.merge(cidadeFk);
            }
            if (estadoFk != null) {
                estadoFk.getPessoaFisicaCollection().add(pessoaFisica);
                estadoFk = em.merge(estadoFk);
            }
            if (rgUfFk != null) {
                rgUfFk.getPessoaFisicaCollection().add(pessoaFisica);
                rgUfFk = em.merge(rgUfFk);
            }
            if (estadoCivilFk != null) {
                estadoCivilFk.getPessoaFisicaCollection().add(pessoaFisica);
                estadoCivilFk = em.merge(estadoCivilFk);
            }
            if (nacionalidadeFk != null) {
                nacionalidadeFk.getPessoaFisicaCollection().add(pessoaFisica);
                nacionalidadeFk = em.merge(nacionalidadeFk);
            }
            if (paisFk != null) {
                paisFk.getPessoaFisicaCollection().add(pessoaFisica);
                paisFk = em.merge(paisFk);
            }
            if (usuarioFk != null) {
                usuarioFk.getPessoaFisicaCollection().add(pessoaFisica);
                usuarioFk = em.merge(usuarioFk);
            }
            if (cidadeEleitoralFk != null) {
                cidadeEleitoralFk.getPessoaFisicaCollection().add(pessoaFisica);
                cidadeEleitoralFk = em.merge(cidadeEleitoralFk);
            }
            if (estadoEleitoralFk != null) {
                estadoEleitoralFk.getPessoaFisicaCollection().add(pessoaFisica);
                estadoEleitoralFk = em.merge(estadoEleitoralFk);
            }
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionPessoaFisicaHistorico : pessoaFisica.getPessoaFisicaHistoricoCollection()) {
                PessoaFisica oldPessoaFisicaFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico = pessoaFisicaHistoricoCollectionPessoaFisicaHistorico.getPessoaFisicaFk();
                pessoaFisicaHistoricoCollectionPessoaFisicaHistorico.setPessoaFisicaFk(pessoaFisica);
                pessoaFisicaHistoricoCollectionPessoaFisicaHistorico = em.merge(pessoaFisicaHistoricoCollectionPessoaFisicaHistorico);
                if (oldPessoaFisicaFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico != null) {
                    oldPessoaFisicaFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistoricoCollectionPessoaFisicaHistorico);
                    oldPessoaFisicaFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico = em.merge(oldPessoaFisicaFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico);
                }
            }
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionPessoaFisicaJuridica : pessoaFisica.getPessoaFisicaJuridicaCollection()) {
                PessoaFisica oldPessoaFisicaFkOfPessoaFisicaJuridicaCollectionPessoaFisicaJuridica = pessoaFisicaJuridicaCollectionPessoaFisicaJuridica.getPessoaFisicaFk();
                pessoaFisicaJuridicaCollectionPessoaFisicaJuridica.setPessoaFisicaFk(pessoaFisica);
                pessoaFisicaJuridicaCollectionPessoaFisicaJuridica = em.merge(pessoaFisicaJuridicaCollectionPessoaFisicaJuridica);
                if (oldPessoaFisicaFkOfPessoaFisicaJuridicaCollectionPessoaFisicaJuridica != null) {
                    oldPessoaFisicaFkOfPessoaFisicaJuridicaCollectionPessoaFisicaJuridica.getPessoaFisicaJuridicaCollection().remove(pessoaFisicaJuridicaCollectionPessoaFisicaJuridica);
                    oldPessoaFisicaFkOfPessoaFisicaJuridicaCollectionPessoaFisicaJuridica = em.merge(oldPessoaFisicaFkOfPessoaFisicaJuridicaCollectionPessoaFisicaJuridica);
                }
            }
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico : pessoaFisica.getPessoaFisicaJuridicaHistoricoCollection()) {
                PessoaFisica oldPessoaFisicaFkOfPessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico = pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico.getPessoaFisicaFk();
                pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico.setPessoaFisicaFk(pessoaFisica);
                pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico = em.merge(pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico);
                if (oldPessoaFisicaFkOfPessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico != null) {
                    oldPessoaFisicaFkOfPessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico.getPessoaFisicaJuridicaHistoricoCollection().remove(pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico);
                    oldPessoaFisicaFkOfPessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico = em.merge(oldPessoaFisicaFkOfPessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico);
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

    public void edit(PessoaFisica pessoaFisica) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PessoaFisica persistentPessoaFisica = em.find(PessoaFisica.class, pessoaFisica.getId());
            Cidade cidadeFkOld = persistentPessoaFisica.getCidadeFk();
            Cidade cidadeFkNew = pessoaFisica.getCidadeFk();
            Estado estadoFkOld = persistentPessoaFisica.getEstadoFk();
            Estado estadoFkNew = pessoaFisica.getEstadoFk();
            Estado rgUfFkOld = persistentPessoaFisica.getRgUfFk();
            Estado rgUfFkNew = pessoaFisica.getRgUfFk();
            EstadoCivil estadoCivilFkOld = persistentPessoaFisica.getEstadoCivilFk();
            EstadoCivil estadoCivilFkNew = pessoaFisica.getEstadoCivilFk();
            Nacionalidade nacionalidadeFkOld = persistentPessoaFisica.getNacionalidadeFk();
            Nacionalidade nacionalidadeFkNew = pessoaFisica.getNacionalidadeFk();
            Pais paisFkOld = persistentPessoaFisica.getPaisFk();
            Pais paisFkNew = pessoaFisica.getPaisFk();
            Usuario usuarioFkOld = persistentPessoaFisica.getUsuarioFk();
            Usuario usuarioFkNew = pessoaFisica.getUsuarioFk();
            Cidade cidadeEleitoralFkOld = persistentPessoaFisica.getCidadeEleitoralFk();
            Cidade cidadeEleitoralFkNew = pessoaFisica.getCidadeEleitoralFk();
            Estado estadoEleitoralFkOld = persistentPessoaFisica.getEstadoEleitoralFk();
            Estado estadoEleitoralFkNew = pessoaFisica.getEstadoEleitoralFk();
            Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollectionOld = persistentPessoaFisica.getPessoaFisicaHistoricoCollection();
            Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollectionNew = pessoaFisica.getPessoaFisicaHistoricoCollection();
            Collection<PessoaFisicaJuridica> pessoaFisicaJuridicaCollectionOld = persistentPessoaFisica.getPessoaFisicaJuridicaCollection();
            Collection<PessoaFisicaJuridica> pessoaFisicaJuridicaCollectionNew = pessoaFisica.getPessoaFisicaJuridicaCollection();
            Collection<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoCollectionOld = persistentPessoaFisica.getPessoaFisicaJuridicaHistoricoCollection();
            Collection<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoCollectionNew = pessoaFisica.getPessoaFisicaJuridicaHistoricoCollection();
            List<String> illegalOrphanMessages = null;
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico : pessoaFisicaHistoricoCollectionOld) {
                if (!pessoaFisicaHistoricoCollectionNew.contains(pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PessoaFisicaHistorico " + pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico + " since its pessoaFisicaFk field is not nullable.");
                }
            }
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionOldPessoaFisicaJuridica : pessoaFisicaJuridicaCollectionOld) {
                if (!pessoaFisicaJuridicaCollectionNew.contains(pessoaFisicaJuridicaCollectionOldPessoaFisicaJuridica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PessoaFisicaJuridica " + pessoaFisicaJuridicaCollectionOldPessoaFisicaJuridica + " since its pessoaFisicaFk field is not nullable.");
                }
            }
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionOldPessoaFisicaJuridicaHistorico : pessoaFisicaJuridicaHistoricoCollectionOld) {
                if (!pessoaFisicaJuridicaHistoricoCollectionNew.contains(pessoaFisicaJuridicaHistoricoCollectionOldPessoaFisicaJuridicaHistorico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PessoaFisicaJuridicaHistorico " + pessoaFisicaJuridicaHistoricoCollectionOldPessoaFisicaJuridicaHistorico + " since its pessoaFisicaFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cidadeFkNew != null) {
                cidadeFkNew = em.getReference(cidadeFkNew.getClass(), cidadeFkNew.getId());
                pessoaFisica.setCidadeFk(cidadeFkNew);
            }
            if (estadoFkNew != null) {
                estadoFkNew = em.getReference(estadoFkNew.getClass(), estadoFkNew.getId());
                pessoaFisica.setEstadoFk(estadoFkNew);
            }
            if (rgUfFkNew != null) {
                rgUfFkNew = em.getReference(rgUfFkNew.getClass(), rgUfFkNew.getId());
                pessoaFisica.setRgUfFk(rgUfFkNew);
            }
            if (estadoCivilFkNew != null) {
                estadoCivilFkNew = em.getReference(estadoCivilFkNew.getClass(), estadoCivilFkNew.getId());
                pessoaFisica.setEstadoCivilFk(estadoCivilFkNew);
            }
            if (nacionalidadeFkNew != null) {
                nacionalidadeFkNew = em.getReference(nacionalidadeFkNew.getClass(), nacionalidadeFkNew.getId());
                pessoaFisica.setNacionalidadeFk(nacionalidadeFkNew);
            }
            if (paisFkNew != null) {
                paisFkNew = em.getReference(paisFkNew.getClass(), paisFkNew.getId());
                pessoaFisica.setPaisFk(paisFkNew);
            }
            if (usuarioFkNew != null) {
                usuarioFkNew = em.getReference(usuarioFkNew.getClass(), usuarioFkNew.getId());
                pessoaFisica.setUsuarioFk(usuarioFkNew);
            }
            if (cidadeEleitoralFkNew != null) {
                cidadeEleitoralFkNew = em.getReference(cidadeEleitoralFkNew.getClass(), cidadeEleitoralFkNew.getId());
                pessoaFisica.setCidadeEleitoralFk(cidadeEleitoralFkNew);
            }
            if (estadoEleitoralFkNew != null) {
                estadoEleitoralFkNew = em.getReference(estadoEleitoralFkNew.getClass(), estadoEleitoralFkNew.getId());
                pessoaFisica.setEstadoEleitoralFk(estadoEleitoralFkNew);
            }
            Collection<PessoaFisicaHistorico> attachedPessoaFisicaHistoricoCollectionNew = new ArrayList<PessoaFisicaHistorico>();
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach : pessoaFisicaHistoricoCollectionNew) {
                pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach = em.getReference(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach.getClass(), pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach.getId());
                attachedPessoaFisicaHistoricoCollectionNew.add(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach);
            }
            pessoaFisicaHistoricoCollectionNew = attachedPessoaFisicaHistoricoCollectionNew;
            pessoaFisica.setPessoaFisicaHistoricoCollection(pessoaFisicaHistoricoCollectionNew);
            Collection<PessoaFisicaJuridica> attachedPessoaFisicaJuridicaCollectionNew = new ArrayList<PessoaFisicaJuridica>();
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridicaToAttach : pessoaFisicaJuridicaCollectionNew) {
                pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridicaToAttach = em.getReference(pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridicaToAttach.getClass(), pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridicaToAttach.getId());
                attachedPessoaFisicaJuridicaCollectionNew.add(pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridicaToAttach);
            }
            pessoaFisicaJuridicaCollectionNew = attachedPessoaFisicaJuridicaCollectionNew;
            pessoaFisica.setPessoaFisicaJuridicaCollection(pessoaFisicaJuridicaCollectionNew);
            Collection<PessoaFisicaJuridicaHistorico> attachedPessoaFisicaJuridicaHistoricoCollectionNew = new ArrayList<PessoaFisicaJuridicaHistorico>();
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistoricoToAttach : pessoaFisicaJuridicaHistoricoCollectionNew) {
                pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistoricoToAttach = em.getReference(pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistoricoToAttach.getClass(), pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistoricoToAttach.getId());
                attachedPessoaFisicaJuridicaHistoricoCollectionNew.add(pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistoricoToAttach);
            }
            pessoaFisicaJuridicaHistoricoCollectionNew = attachedPessoaFisicaJuridicaHistoricoCollectionNew;
            pessoaFisica.setPessoaFisicaJuridicaHistoricoCollection(pessoaFisicaJuridicaHistoricoCollectionNew);
            pessoaFisica = em.merge(pessoaFisica);
            if (cidadeFkOld != null && !cidadeFkOld.equals(cidadeFkNew)) {
                cidadeFkOld.getPessoaFisicaCollection().remove(pessoaFisica);
                cidadeFkOld = em.merge(cidadeFkOld);
            }
            if (cidadeFkNew != null && !cidadeFkNew.equals(cidadeFkOld)) {
                cidadeFkNew.getPessoaFisicaCollection().add(pessoaFisica);
                cidadeFkNew = em.merge(cidadeFkNew);
            }
            if (estadoFkOld != null && !estadoFkOld.equals(estadoFkNew)) {
                estadoFkOld.getPessoaFisicaCollection().remove(pessoaFisica);
                estadoFkOld = em.merge(estadoFkOld);
            }
            if (estadoFkNew != null && !estadoFkNew.equals(estadoFkOld)) {
                estadoFkNew.getPessoaFisicaCollection().add(pessoaFisica);
                estadoFkNew = em.merge(estadoFkNew);
            }
            if (rgUfFkOld != null && !rgUfFkOld.equals(rgUfFkNew)) {
                rgUfFkOld.getPessoaFisicaCollection().remove(pessoaFisica);
                rgUfFkOld = em.merge(rgUfFkOld);
            }
            if (rgUfFkNew != null && !rgUfFkNew.equals(rgUfFkOld)) {
                rgUfFkNew.getPessoaFisicaCollection().add(pessoaFisica);
                rgUfFkNew = em.merge(rgUfFkNew);
            }
            if (estadoCivilFkOld != null && !estadoCivilFkOld.equals(estadoCivilFkNew)) {
                estadoCivilFkOld.getPessoaFisicaCollection().remove(pessoaFisica);
                estadoCivilFkOld = em.merge(estadoCivilFkOld);
            }
            if (estadoCivilFkNew != null && !estadoCivilFkNew.equals(estadoCivilFkOld)) {
                estadoCivilFkNew.getPessoaFisicaCollection().add(pessoaFisica);
                estadoCivilFkNew = em.merge(estadoCivilFkNew);
            }
            if (nacionalidadeFkOld != null && !nacionalidadeFkOld.equals(nacionalidadeFkNew)) {
                nacionalidadeFkOld.getPessoaFisicaCollection().remove(pessoaFisica);
                nacionalidadeFkOld = em.merge(nacionalidadeFkOld);
            }
            if (nacionalidadeFkNew != null && !nacionalidadeFkNew.equals(nacionalidadeFkOld)) {
                nacionalidadeFkNew.getPessoaFisicaCollection().add(pessoaFisica);
                nacionalidadeFkNew = em.merge(nacionalidadeFkNew);
            }
            if (paisFkOld != null && !paisFkOld.equals(paisFkNew)) {
                paisFkOld.getPessoaFisicaCollection().remove(pessoaFisica);
                paisFkOld = em.merge(paisFkOld);
            }
            if (paisFkNew != null && !paisFkNew.equals(paisFkOld)) {
                paisFkNew.getPessoaFisicaCollection().add(pessoaFisica);
                paisFkNew = em.merge(paisFkNew);
            }
            if (usuarioFkOld != null && !usuarioFkOld.equals(usuarioFkNew)) {
                usuarioFkOld.getPessoaFisicaCollection().remove(pessoaFisica);
                usuarioFkOld = em.merge(usuarioFkOld);
            }
            if (usuarioFkNew != null && !usuarioFkNew.equals(usuarioFkOld)) {
                usuarioFkNew.getPessoaFisicaCollection().add(pessoaFisica);
                usuarioFkNew = em.merge(usuarioFkNew);
            }
            if (cidadeEleitoralFkOld != null && !cidadeEleitoralFkOld.equals(cidadeEleitoralFkNew)) {
                cidadeEleitoralFkOld.getPessoaFisicaCollection().remove(pessoaFisica);
                cidadeEleitoralFkOld = em.merge(cidadeEleitoralFkOld);
            }
            if (cidadeEleitoralFkNew != null && !cidadeEleitoralFkNew.equals(cidadeEleitoralFkOld)) {
                cidadeEleitoralFkNew.getPessoaFisicaCollection().add(pessoaFisica);
                cidadeEleitoralFkNew = em.merge(cidadeEleitoralFkNew);
            }
            if (estadoEleitoralFkOld != null && !estadoEleitoralFkOld.equals(estadoEleitoralFkNew)) {
                estadoEleitoralFkOld.getPessoaFisicaCollection().remove(pessoaFisica);
                estadoEleitoralFkOld = em.merge(estadoEleitoralFkOld);
            }
            if (estadoEleitoralFkNew != null && !estadoEleitoralFkNew.equals(estadoEleitoralFkOld)) {
                estadoEleitoralFkNew.getPessoaFisicaCollection().add(pessoaFisica);
                estadoEleitoralFkNew = em.merge(estadoEleitoralFkNew);
            }
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico : pessoaFisicaHistoricoCollectionNew) {
                if (!pessoaFisicaHistoricoCollectionOld.contains(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico)) {
                    PessoaFisica oldPessoaFisicaFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico = pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico.getPessoaFisicaFk();
                    pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico.setPessoaFisicaFk(pessoaFisica);
                    pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico = em.merge(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico);
                    if (oldPessoaFisicaFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico != null && !oldPessoaFisicaFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico.equals(pessoaFisica)) {
                        oldPessoaFisicaFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico);
                        oldPessoaFisicaFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico = em.merge(oldPessoaFisicaFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico);
                    }
                }
            }
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica : pessoaFisicaJuridicaCollectionNew) {
                if (!pessoaFisicaJuridicaCollectionOld.contains(pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica)) {
                    PessoaFisica oldPessoaFisicaFkOfPessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica = pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica.getPessoaFisicaFk();
                    pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica.setPessoaFisicaFk(pessoaFisica);
                    pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica = em.merge(pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica);
                    if (oldPessoaFisicaFkOfPessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica != null && !oldPessoaFisicaFkOfPessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica.equals(pessoaFisica)) {
                        oldPessoaFisicaFkOfPessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica.getPessoaFisicaJuridicaCollection().remove(pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica);
                        oldPessoaFisicaFkOfPessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica = em.merge(oldPessoaFisicaFkOfPessoaFisicaJuridicaCollectionNewPessoaFisicaJuridica);
                    }
                }
            }
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico : pessoaFisicaJuridicaHistoricoCollectionNew) {
                if (!pessoaFisicaJuridicaHistoricoCollectionOld.contains(pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico)) {
                    PessoaFisica oldPessoaFisicaFkOfPessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico = pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico.getPessoaFisicaFk();
                    pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico.setPessoaFisicaFk(pessoaFisica);
                    pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico = em.merge(pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico);
                    if (oldPessoaFisicaFkOfPessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico != null && !oldPessoaFisicaFkOfPessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico.equals(pessoaFisica)) {
                        oldPessoaFisicaFkOfPessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico.getPessoaFisicaJuridicaHistoricoCollection().remove(pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico);
                        oldPessoaFisicaFkOfPessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico = em.merge(oldPessoaFisicaFkOfPessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico);
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
                Integer id = pessoaFisica.getId();
                if (findPessoaFisica(id) == null) {
                    throw new NonexistentEntityException("The pessoaFisica with id " + id + " no longer exists.");
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
            PessoaFisica pessoaFisica;
            try {
                pessoaFisica = em.getReference(PessoaFisica.class, id);
                pessoaFisica.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pessoaFisica with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollectionOrphanCheck = pessoaFisica.getPessoaFisicaHistoricoCollection();
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionOrphanCheckPessoaFisicaHistorico : pessoaFisicaHistoricoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PessoaFisica (" + pessoaFisica + ") cannot be destroyed since the PessoaFisicaHistorico " + pessoaFisicaHistoricoCollectionOrphanCheckPessoaFisicaHistorico + " in its pessoaFisicaHistoricoCollection field has a non-nullable pessoaFisicaFk field.");
            }
            Collection<PessoaFisicaJuridica> pessoaFisicaJuridicaCollectionOrphanCheck = pessoaFisica.getPessoaFisicaJuridicaCollection();
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionOrphanCheckPessoaFisicaJuridica : pessoaFisicaJuridicaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PessoaFisica (" + pessoaFisica + ") cannot be destroyed since the PessoaFisicaJuridica " + pessoaFisicaJuridicaCollectionOrphanCheckPessoaFisicaJuridica + " in its pessoaFisicaJuridicaCollection field has a non-nullable pessoaFisicaFk field.");
            }
            Collection<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoCollectionOrphanCheck = pessoaFisica.getPessoaFisicaJuridicaHistoricoCollection();
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionOrphanCheckPessoaFisicaJuridicaHistorico : pessoaFisicaJuridicaHistoricoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PessoaFisica (" + pessoaFisica + ") cannot be destroyed since the PessoaFisicaJuridicaHistorico " + pessoaFisicaJuridicaHistoricoCollectionOrphanCheckPessoaFisicaJuridicaHistorico + " in its pessoaFisicaJuridicaHistoricoCollection field has a non-nullable pessoaFisicaFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cidade cidadeFk = pessoaFisica.getCidadeFk();
            if (cidadeFk != null) {
                cidadeFk.getPessoaFisicaCollection().remove(pessoaFisica);
                cidadeFk = em.merge(cidadeFk);
            }
            Estado estadoFk = pessoaFisica.getEstadoFk();
            if (estadoFk != null) {
                estadoFk.getPessoaFisicaCollection().remove(pessoaFisica);
                estadoFk = em.merge(estadoFk);
            }
            Estado rgUfFk = pessoaFisica.getRgUfFk();
            if (rgUfFk != null) {
                rgUfFk.getPessoaFisicaCollection().remove(pessoaFisica);
                rgUfFk = em.merge(rgUfFk);
            }
            EstadoCivil estadoCivilFk = pessoaFisica.getEstadoCivilFk();
            if (estadoCivilFk != null) {
                estadoCivilFk.getPessoaFisicaCollection().remove(pessoaFisica);
                estadoCivilFk = em.merge(estadoCivilFk);
            }
            Nacionalidade nacionalidadeFk = pessoaFisica.getNacionalidadeFk();
            if (nacionalidadeFk != null) {
                nacionalidadeFk.getPessoaFisicaCollection().remove(pessoaFisica);
                nacionalidadeFk = em.merge(nacionalidadeFk);
            }
            Pais paisFk = pessoaFisica.getPaisFk();
            if (paisFk != null) {
                paisFk.getPessoaFisicaCollection().remove(pessoaFisica);
                paisFk = em.merge(paisFk);
            }
            Usuario usuarioFk = pessoaFisica.getUsuarioFk();
            if (usuarioFk != null) {
                usuarioFk.getPessoaFisicaCollection().remove(pessoaFisica);
                usuarioFk = em.merge(usuarioFk);
            }
            Cidade cidadeEleitoralFk = pessoaFisica.getCidadeEleitoralFk();
            if (cidadeEleitoralFk != null) {
                cidadeEleitoralFk.getPessoaFisicaCollection().remove(pessoaFisica);
                cidadeEleitoralFk = em.merge(cidadeEleitoralFk);
            }
            Estado estadoEleitoralFk = pessoaFisica.getEstadoEleitoralFk();
            if (estadoEleitoralFk != null) {
                estadoEleitoralFk.getPessoaFisicaCollection().remove(pessoaFisica);
                estadoEleitoralFk = em.merge(estadoEleitoralFk);
            }
            em.remove(pessoaFisica);
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

    public List<PessoaFisica> findPessoaFisicaEntities() {
        return findPessoaFisicaEntities(true, -1, -1);
    }

    public List<PessoaFisica> findPessoaFisicaEntities(int maxResults, int firstResult) {
        return findPessoaFisicaEntities(false, maxResults, firstResult);
    }

    private List<PessoaFisica> findPessoaFisicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PessoaFisica.class));
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

    public PessoaFisica findPessoaFisica(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PessoaFisica.class, id);
        } finally {
            em.close();
        }
    }

    public int getPessoaFisicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PessoaFisica> rt = cq.from(PessoaFisica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public PessoaFisica findDuplicates(PessoaFisica pessoaFisica) {
        EntityManager em = getEntityManager();
        try {
            PessoaFisica pf = (PessoaFisica) em.createNativeQuery("select * from pessoa_fisica "
                    + "where cpf = '" + pessoaFisica.getCpf() + "'", PessoaFisica.class).getSingleResult();
            return pf;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public PessoaFisica findByCPF(String cpf) {
        EntityManager em = getEntityManager();
        try {
            PessoaFisica pf = (PessoaFisica) em.createNativeQuery("select * from pessoa_fisica "
                    + "where cpf = '" + cpf + "'", PessoaFisica.class).getSingleResult();
            return pf;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public List<PessoaFisica> findAllActive(){
        EntityManager em = getEntityManager();
        try {
            List<PessoaFisica> pessoaFisicaList = (List<PessoaFisica>) em.createNativeQuery("select * from pessoa_fisica "
                        + "where status = 'A' ", PessoaFisica.class).getResultList();
            return pessoaFisicaList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public List<PessoaFisica> findFirstTenActive(){
        EntityManager em = getEntityManager();
        try {
            List<PessoaFisica> pessoaFisicaList = (List<PessoaFisica>) em.createNativeQuery("select * from pessoa_fisica "
                        + "where status = 'A' order by nome limit 10", PessoaFisica.class).getResultList();
            return pessoaFisicaList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
}
