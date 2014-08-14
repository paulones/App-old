/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.PessoaFisicaJuridica;
import entidade.PessoaFisicaJuridicaHistorico;
import entidade.PessoaJuridica;
import entidade.PessoaJuridicaHistorico;
import entidade.TipoEmpresarial;
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
        if (pessoaJuridica.getPessoaJuridicaHistoricoCollection() == null) {
            pessoaJuridica.setPessoaJuridicaHistoricoCollection(new ArrayList<PessoaJuridicaHistorico>());
        }
        if (pessoaJuridica.getPessoaJuridicaHistoricoCollection1() == null) {
            pessoaJuridica.setPessoaJuridicaHistoricoCollection1(new ArrayList<PessoaJuridicaHistorico>());
        }
        if (pessoaJuridica.getPessoaFisicaJuridicaCollection() == null) {
            pessoaJuridica.setPessoaFisicaJuridicaCollection(new ArrayList<PessoaFisicaJuridica>());
        }
        if (pessoaJuridica.getPessoaFisicaJuridicaHistoricoCollection() == null) {
            pessoaJuridica.setPessoaFisicaJuridicaHistoricoCollection(new ArrayList<PessoaFisicaJuridicaHistorico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
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
            Collection<PessoaJuridicaHistorico> attachedPessoaJuridicaHistoricoCollection = new ArrayList<PessoaJuridicaHistorico>();
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollectionPessoaJuridicaHistoricoToAttach : pessoaJuridica.getPessoaJuridicaHistoricoCollection()) {
                pessoaJuridicaHistoricoCollectionPessoaJuridicaHistoricoToAttach = em.getReference(pessoaJuridicaHistoricoCollectionPessoaJuridicaHistoricoToAttach.getClass(), pessoaJuridicaHistoricoCollectionPessoaJuridicaHistoricoToAttach.getId());
                attachedPessoaJuridicaHistoricoCollection.add(pessoaJuridicaHistoricoCollectionPessoaJuridicaHistoricoToAttach);
            }
            pessoaJuridica.setPessoaJuridicaHistoricoCollection(attachedPessoaJuridicaHistoricoCollection);
            Collection<PessoaJuridicaHistorico> attachedPessoaJuridicaHistoricoCollection1 = new ArrayList<PessoaJuridicaHistorico>();
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollection1PessoaJuridicaHistoricoToAttach : pessoaJuridica.getPessoaJuridicaHistoricoCollection1()) {
                pessoaJuridicaHistoricoCollection1PessoaJuridicaHistoricoToAttach = em.getReference(pessoaJuridicaHistoricoCollection1PessoaJuridicaHistoricoToAttach.getClass(), pessoaJuridicaHistoricoCollection1PessoaJuridicaHistoricoToAttach.getId());
                attachedPessoaJuridicaHistoricoCollection1.add(pessoaJuridicaHistoricoCollection1PessoaJuridicaHistoricoToAttach);
            }
            pessoaJuridica.setPessoaJuridicaHistoricoCollection1(attachedPessoaJuridicaHistoricoCollection1);
            Collection<PessoaFisicaJuridica> attachedPessoaFisicaJuridicaCollection = new ArrayList<PessoaFisicaJuridica>();
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionPessoaFisicaJuridicaToAttach : pessoaJuridica.getPessoaFisicaJuridicaCollection()) {
                pessoaFisicaJuridicaCollectionPessoaFisicaJuridicaToAttach = em.getReference(pessoaFisicaJuridicaCollectionPessoaFisicaJuridicaToAttach.getClass(), pessoaFisicaJuridicaCollectionPessoaFisicaJuridicaToAttach.getId());
                attachedPessoaFisicaJuridicaCollection.add(pessoaFisicaJuridicaCollectionPessoaFisicaJuridicaToAttach);
            }
            pessoaJuridica.setPessoaFisicaJuridicaCollection(attachedPessoaFisicaJuridicaCollection);
            Collection<PessoaFisicaJuridicaHistorico> attachedPessoaFisicaJuridicaHistoricoCollection = new ArrayList<PessoaFisicaJuridicaHistorico>();
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistoricoToAttach : pessoaJuridica.getPessoaFisicaJuridicaHistoricoCollection()) {
                pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistoricoToAttach = em.getReference(pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistoricoToAttach.getClass(), pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistoricoToAttach.getId());
                attachedPessoaFisicaJuridicaHistoricoCollection.add(pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistoricoToAttach);
            }
            pessoaJuridica.setPessoaFisicaJuridicaHistoricoCollection(attachedPessoaFisicaJuridicaHistoricoCollection);
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
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico : pessoaJuridica.getPessoaJuridicaHistoricoCollection()) {
                PessoaJuridica oldPessoaJuridicaFkOfPessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico = pessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico.getPessoaJuridicaFk();
                pessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico.setPessoaJuridicaFk(pessoaJuridica);
                pessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico = em.merge(pessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico);
                if (oldPessoaJuridicaFkOfPessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico != null) {
                    oldPessoaJuridicaFkOfPessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico.getPessoaJuridicaHistoricoCollection().remove(pessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico);
                    oldPessoaJuridicaFkOfPessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico = em.merge(oldPessoaJuridicaFkOfPessoaJuridicaHistoricoCollectionPessoaJuridicaHistorico);
                }
            }
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollection1PessoaJuridicaHistorico : pessoaJuridica.getPessoaJuridicaHistoricoCollection1()) {
                PessoaJuridica oldSucessaoFkOfPessoaJuridicaHistoricoCollection1PessoaJuridicaHistorico = pessoaJuridicaHistoricoCollection1PessoaJuridicaHistorico.getSucessaoFk();
                pessoaJuridicaHistoricoCollection1PessoaJuridicaHistorico.setSucessaoFk(pessoaJuridica);
                pessoaJuridicaHistoricoCollection1PessoaJuridicaHistorico = em.merge(pessoaJuridicaHistoricoCollection1PessoaJuridicaHistorico);
                if (oldSucessaoFkOfPessoaJuridicaHistoricoCollection1PessoaJuridicaHistorico != null) {
                    oldSucessaoFkOfPessoaJuridicaHistoricoCollection1PessoaJuridicaHistorico.getPessoaJuridicaHistoricoCollection1().remove(pessoaJuridicaHistoricoCollection1PessoaJuridicaHistorico);
                    oldSucessaoFkOfPessoaJuridicaHistoricoCollection1PessoaJuridicaHistorico = em.merge(oldSucessaoFkOfPessoaJuridicaHistoricoCollection1PessoaJuridicaHistorico);
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
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico : pessoaJuridica.getPessoaFisicaJuridicaHistoricoCollection()) {
                PessoaJuridica oldPessoaJuridicaFkOfPessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico = pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico.getPessoaJuridicaFk();
                pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico.setPessoaJuridicaFk(pessoaJuridica);
                pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico = em.merge(pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico);
                if (oldPessoaJuridicaFkOfPessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico != null) {
                    oldPessoaJuridicaFkOfPessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico.getPessoaFisicaJuridicaHistoricoCollection().remove(pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico);
                    oldPessoaJuridicaFkOfPessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico = em.merge(oldPessoaJuridicaFkOfPessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistorico);
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
            em = getEntityManager();em.getTransaction().begin();
            PessoaJuridica persistentPessoaJuridica = em.find(PessoaJuridica.class, pessoaJuridica.getId());
            PessoaJuridica sucessaoFkOld = persistentPessoaJuridica.getSucessaoFk();
            PessoaJuridica sucessaoFkNew = pessoaJuridica.getSucessaoFk();
            TipoEmpresarial tipoEmpresarialFkOld = persistentPessoaJuridica.getTipoEmpresarialFk();
            TipoEmpresarial tipoEmpresarialFkNew = pessoaJuridica.getTipoEmpresarialFk();
            Collection<PessoaJuridica> pessoaJuridicaCollectionOld = persistentPessoaJuridica.getPessoaJuridicaCollection();
            Collection<PessoaJuridica> pessoaJuridicaCollectionNew = pessoaJuridica.getPessoaJuridicaCollection();
            Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollectionOld = persistentPessoaJuridica.getPessoaJuridicaHistoricoCollection();
            Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollectionNew = pessoaJuridica.getPessoaJuridicaHistoricoCollection();
            Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollection1Old = persistentPessoaJuridica.getPessoaJuridicaHistoricoCollection1();
            Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollection1New = pessoaJuridica.getPessoaJuridicaHistoricoCollection1();
            Collection<PessoaFisicaJuridica> pessoaFisicaJuridicaCollectionOld = persistentPessoaJuridica.getPessoaFisicaJuridicaCollection();
            Collection<PessoaFisicaJuridica> pessoaFisicaJuridicaCollectionNew = pessoaJuridica.getPessoaFisicaJuridicaCollection();
            Collection<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoCollectionOld = persistentPessoaJuridica.getPessoaFisicaJuridicaHistoricoCollection();
            Collection<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoCollectionNew = pessoaJuridica.getPessoaFisicaJuridicaHistoricoCollection();
            List<String> illegalOrphanMessages = null;
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollectionOldPessoaJuridicaHistorico : pessoaJuridicaHistoricoCollectionOld) {
                if (!pessoaJuridicaHistoricoCollectionNew.contains(pessoaJuridicaHistoricoCollectionOldPessoaJuridicaHistorico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PessoaJuridicaHistorico " + pessoaJuridicaHistoricoCollectionOldPessoaJuridicaHistorico + " since its pessoaJuridicaFk field is not nullable.");
                }
            }
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionOldPessoaFisicaJuridica : pessoaFisicaJuridicaCollectionOld) {
                if (!pessoaFisicaJuridicaCollectionNew.contains(pessoaFisicaJuridicaCollectionOldPessoaFisicaJuridica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PessoaFisicaJuridica " + pessoaFisicaJuridicaCollectionOldPessoaFisicaJuridica + " since its pessoaJuridicaFk field is not nullable.");
                }
            }
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionOldPessoaFisicaJuridicaHistorico : pessoaFisicaJuridicaHistoricoCollectionOld) {
                if (!pessoaFisicaJuridicaHistoricoCollectionNew.contains(pessoaFisicaJuridicaHistoricoCollectionOldPessoaFisicaJuridicaHistorico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PessoaFisicaJuridicaHistorico " + pessoaFisicaJuridicaHistoricoCollectionOldPessoaFisicaJuridicaHistorico + " since its pessoaJuridicaFk field is not nullable.");
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
            Collection<PessoaJuridicaHistorico> attachedPessoaJuridicaHistoricoCollectionNew = new ArrayList<PessoaJuridicaHistorico>();
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistoricoToAttach : pessoaJuridicaHistoricoCollectionNew) {
                pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistoricoToAttach = em.getReference(pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistoricoToAttach.getClass(), pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistoricoToAttach.getId());
                attachedPessoaJuridicaHistoricoCollectionNew.add(pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistoricoToAttach);
            }
            pessoaJuridicaHistoricoCollectionNew = attachedPessoaJuridicaHistoricoCollectionNew;
            pessoaJuridica.setPessoaJuridicaHistoricoCollection(pessoaJuridicaHistoricoCollectionNew);
            Collection<PessoaJuridicaHistorico> attachedPessoaJuridicaHistoricoCollection1New = new ArrayList<PessoaJuridicaHistorico>();
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollection1NewPessoaJuridicaHistoricoToAttach : pessoaJuridicaHistoricoCollection1New) {
                pessoaJuridicaHistoricoCollection1NewPessoaJuridicaHistoricoToAttach = em.getReference(pessoaJuridicaHistoricoCollection1NewPessoaJuridicaHistoricoToAttach.getClass(), pessoaJuridicaHistoricoCollection1NewPessoaJuridicaHistoricoToAttach.getId());
                attachedPessoaJuridicaHistoricoCollection1New.add(pessoaJuridicaHistoricoCollection1NewPessoaJuridicaHistoricoToAttach);
            }
            pessoaJuridicaHistoricoCollection1New = attachedPessoaJuridicaHistoricoCollection1New;
            pessoaJuridica.setPessoaJuridicaHistoricoCollection1(pessoaJuridicaHistoricoCollection1New);
            Collection<PessoaFisicaJuridica> attachedPessoaFisicaJuridicaCollectionNew = new ArrayList<PessoaFisicaJuridica>();
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridicaToAttach : pessoaFisicaJuridicaCollectionNew) {
                pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridicaToAttach = em.getReference(pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridicaToAttach.getClass(), pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridicaToAttach.getId());
                attachedPessoaFisicaJuridicaCollectionNew.add(pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridicaToAttach);
            }
            pessoaFisicaJuridicaCollectionNew = attachedPessoaFisicaJuridicaCollectionNew;
            pessoaJuridica.setPessoaFisicaJuridicaCollection(pessoaFisicaJuridicaCollectionNew);
            Collection<PessoaFisicaJuridicaHistorico> attachedPessoaFisicaJuridicaHistoricoCollectionNew = new ArrayList<PessoaFisicaJuridicaHistorico>();
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistoricoToAttach : pessoaFisicaJuridicaHistoricoCollectionNew) {
                pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistoricoToAttach = em.getReference(pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistoricoToAttach.getClass(), pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistoricoToAttach.getId());
                attachedPessoaFisicaJuridicaHistoricoCollectionNew.add(pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistoricoToAttach);
            }
            pessoaFisicaJuridicaHistoricoCollectionNew = attachedPessoaFisicaJuridicaHistoricoCollectionNew;
            pessoaJuridica.setPessoaFisicaJuridicaHistoricoCollection(pessoaFisicaJuridicaHistoricoCollectionNew);
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
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico : pessoaJuridicaHistoricoCollectionNew) {
                if (!pessoaJuridicaHistoricoCollectionOld.contains(pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico)) {
                    PessoaJuridica oldPessoaJuridicaFkOfPessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico = pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico.getPessoaJuridicaFk();
                    pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico.setPessoaJuridicaFk(pessoaJuridica);
                    pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico = em.merge(pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico);
                    if (oldPessoaJuridicaFkOfPessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico != null && !oldPessoaJuridicaFkOfPessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico.equals(pessoaJuridica)) {
                        oldPessoaJuridicaFkOfPessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico.getPessoaJuridicaHistoricoCollection().remove(pessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico);
                        oldPessoaJuridicaFkOfPessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico = em.merge(oldPessoaJuridicaFkOfPessoaJuridicaHistoricoCollectionNewPessoaJuridicaHistorico);
                    }
                }
            }
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollection1OldPessoaJuridicaHistorico : pessoaJuridicaHistoricoCollection1Old) {
                if (!pessoaJuridicaHistoricoCollection1New.contains(pessoaJuridicaHistoricoCollection1OldPessoaJuridicaHistorico)) {
                    pessoaJuridicaHistoricoCollection1OldPessoaJuridicaHistorico.setSucessaoFk(null);
                    pessoaJuridicaHistoricoCollection1OldPessoaJuridicaHistorico = em.merge(pessoaJuridicaHistoricoCollection1OldPessoaJuridicaHistorico);
                }
            }
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollection1NewPessoaJuridicaHistorico : pessoaJuridicaHistoricoCollection1New) {
                if (!pessoaJuridicaHistoricoCollection1Old.contains(pessoaJuridicaHistoricoCollection1NewPessoaJuridicaHistorico)) {
                    PessoaJuridica oldSucessaoFkOfPessoaJuridicaHistoricoCollection1NewPessoaJuridicaHistorico = pessoaJuridicaHistoricoCollection1NewPessoaJuridicaHistorico.getSucessaoFk();
                    pessoaJuridicaHistoricoCollection1NewPessoaJuridicaHistorico.setSucessaoFk(pessoaJuridica);
                    pessoaJuridicaHistoricoCollection1NewPessoaJuridicaHistorico = em.merge(pessoaJuridicaHistoricoCollection1NewPessoaJuridicaHistorico);
                    if (oldSucessaoFkOfPessoaJuridicaHistoricoCollection1NewPessoaJuridicaHistorico != null && !oldSucessaoFkOfPessoaJuridicaHistoricoCollection1NewPessoaJuridicaHistorico.equals(pessoaJuridica)) {
                        oldSucessaoFkOfPessoaJuridicaHistoricoCollection1NewPessoaJuridicaHistorico.getPessoaJuridicaHistoricoCollection1().remove(pessoaJuridicaHistoricoCollection1NewPessoaJuridicaHistorico);
                        oldSucessaoFkOfPessoaJuridicaHistoricoCollection1NewPessoaJuridicaHistorico = em.merge(oldSucessaoFkOfPessoaJuridicaHistoricoCollection1NewPessoaJuridicaHistorico);
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
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico : pessoaFisicaJuridicaHistoricoCollectionNew) {
                if (!pessoaFisicaJuridicaHistoricoCollectionOld.contains(pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico)) {
                    PessoaJuridica oldPessoaJuridicaFkOfPessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico = pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico.getPessoaJuridicaFk();
                    pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico.setPessoaJuridicaFk(pessoaJuridica);
                    pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico = em.merge(pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico);
                    if (oldPessoaJuridicaFkOfPessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico != null && !oldPessoaJuridicaFkOfPessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico.equals(pessoaJuridica)) {
                        oldPessoaJuridicaFkOfPessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico.getPessoaFisicaJuridicaHistoricoCollection().remove(pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico);
                        oldPessoaJuridicaFkOfPessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico = em.merge(oldPessoaJuridicaFkOfPessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistorico);
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
            em = getEntityManager();em.getTransaction().begin();
            PessoaJuridica pessoaJuridica;
            try {
                pessoaJuridica = em.getReference(PessoaJuridica.class, id);
                pessoaJuridica.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pessoaJuridica with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollectionOrphanCheck = pessoaJuridica.getPessoaJuridicaHistoricoCollection();
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollectionOrphanCheckPessoaJuridicaHistorico : pessoaJuridicaHistoricoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PessoaJuridica (" + pessoaJuridica + ") cannot be destroyed since the PessoaJuridicaHistorico " + pessoaJuridicaHistoricoCollectionOrphanCheckPessoaJuridicaHistorico + " in its pessoaJuridicaHistoricoCollection field has a non-nullable pessoaJuridicaFk field.");
            }
            Collection<PessoaFisicaJuridica> pessoaFisicaJuridicaCollectionOrphanCheck = pessoaJuridica.getPessoaFisicaJuridicaCollection();
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionOrphanCheckPessoaFisicaJuridica : pessoaFisicaJuridicaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PessoaJuridica (" + pessoaJuridica + ") cannot be destroyed since the PessoaFisicaJuridica " + pessoaFisicaJuridicaCollectionOrphanCheckPessoaFisicaJuridica + " in its pessoaFisicaJuridicaCollection field has a non-nullable pessoaJuridicaFk field.");
            }
            Collection<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoCollectionOrphanCheck = pessoaJuridica.getPessoaFisicaJuridicaHistoricoCollection();
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionOrphanCheckPessoaFisicaJuridicaHistorico : pessoaFisicaJuridicaHistoricoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PessoaJuridica (" + pessoaJuridica + ") cannot be destroyed since the PessoaFisicaJuridicaHistorico " + pessoaFisicaJuridicaHistoricoCollectionOrphanCheckPessoaFisicaJuridicaHistorico + " in its pessoaFisicaJuridicaHistoricoCollection field has a non-nullable pessoaJuridicaFk field.");
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
            Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollection1 = pessoaJuridica.getPessoaJuridicaHistoricoCollection1();
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollection1PessoaJuridicaHistorico : pessoaJuridicaHistoricoCollection1) {
                pessoaJuridicaHistoricoCollection1PessoaJuridicaHistorico.setSucessaoFk(null);
                pessoaJuridicaHistoricoCollection1PessoaJuridicaHistorico = em.merge(pessoaJuridicaHistoricoCollection1PessoaJuridicaHistorico);
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
    
    public PessoaJuridica findDuplicates(PessoaJuridica pessoaJuridica){
        EntityManager em = getEntityManager();
        try {
            PessoaJuridica pj = (PessoaJuridica) em.createNativeQuery("select * from pessoa_juridica "
                    + "where cnpj = '" + pessoaJuridica.getCnpj()+ "'", PessoaJuridica.class).getSingleResult();
            return pj;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public List<PessoaJuridica> findAllActive(){
        EntityManager em = getEntityManager();
        try {
            List<PessoaJuridica> pessoaFisicaList = (List<PessoaJuridica>) em.createNativeQuery("select * from pessoa_juridica "
                        + "where status = 'A'", PessoaJuridica.class).getResultList();
            return pessoaFisicaList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
