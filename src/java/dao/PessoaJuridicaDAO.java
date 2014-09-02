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
import entidade.PessoaJuridicaJuridica;
import entidade.PessoaJuridicaJuridicaHistorico;
import entidade.PessoaJuridicaSucessao;
import entidade.TipoEmpresarial;
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
public class PessoaJuridicaDAO implements Serializable {

    public PessoaJuridicaDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PessoaJuridica pessoaJuridica) throws RollbackFailureException, Exception {
        if (pessoaJuridica.getPessoaFisicaJuridicaCollection() == null) {
            pessoaJuridica.setPessoaFisicaJuridicaCollection(new ArrayList<PessoaFisicaJuridica>());
        }
        if (pessoaJuridica.getPessoaJuridicaSucessaoCollection() == null) {
            pessoaJuridica.setPessoaJuridicaSucessaoCollection(new ArrayList<PessoaJuridicaSucessao>());
        }
        if (pessoaJuridica.getPessoaJuridicaSucessaoCollection1() == null) {
            pessoaJuridica.setPessoaJuridicaSucessaoCollection1(new ArrayList<PessoaJuridicaSucessao>());
        }
        if (pessoaJuridica.getPessoaJuridicaJuridicaHistoricoCollection() == null) {
            pessoaJuridica.setPessoaJuridicaJuridicaHistoricoCollection(new ArrayList<PessoaJuridicaJuridicaHistorico>());
        }
        if (pessoaJuridica.getPessoaJuridicaJuridicaHistoricoCollection1() == null) {
            pessoaJuridica.setPessoaJuridicaJuridicaHistoricoCollection1(new ArrayList<PessoaJuridicaJuridicaHistorico>());
        }
        if (pessoaJuridica.getPessoaJuridicaJuridicaCollection() == null) {
            pessoaJuridica.setPessoaJuridicaJuridicaCollection(new ArrayList<PessoaJuridicaJuridica>());
        }
        if (pessoaJuridica.getPessoaJuridicaJuridicaCollection1() == null) {
            pessoaJuridica.setPessoaJuridicaJuridicaCollection1(new ArrayList<PessoaJuridicaJuridica>());
        }
        if (pessoaJuridica.getPessoaJuridicaHistoricoCollection() == null) {
            pessoaJuridica.setPessoaJuridicaHistoricoCollection(new ArrayList<PessoaJuridicaHistorico>());
        }
        if (pessoaJuridica.getPessoaJuridicaHistoricoCollection1() == null) {
            pessoaJuridica.setPessoaJuridicaHistoricoCollection1(new ArrayList<PessoaJuridicaHistorico>());
        }
        if (pessoaJuridica.getPessoaFisicaJuridicaHistoricoCollection() == null) {
            pessoaJuridica.setPessoaFisicaJuridicaHistoricoCollection(new ArrayList<PessoaFisicaJuridicaHistorico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            TipoEmpresarial tipoEmpresarialFk = pessoaJuridica.getTipoEmpresarialFk();
            if (tipoEmpresarialFk != null) {
                tipoEmpresarialFk = em.getReference(tipoEmpresarialFk.getClass(), tipoEmpresarialFk.getId());
                pessoaJuridica.setTipoEmpresarialFk(tipoEmpresarialFk);
            }
            Usuario usuarioFk = pessoaJuridica.getUsuarioFk();
            if (usuarioFk != null) {
                usuarioFk = em.getReference(usuarioFk.getClass(), usuarioFk.getId());
                pessoaJuridica.setUsuarioFk(usuarioFk);
            }
            Collection<PessoaFisicaJuridica> attachedPessoaFisicaJuridicaCollection = new ArrayList<PessoaFisicaJuridica>();
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionPessoaFisicaJuridicaToAttach : pessoaJuridica.getPessoaFisicaJuridicaCollection()) {
                pessoaFisicaJuridicaCollectionPessoaFisicaJuridicaToAttach = em.getReference(pessoaFisicaJuridicaCollectionPessoaFisicaJuridicaToAttach.getClass(), pessoaFisicaJuridicaCollectionPessoaFisicaJuridicaToAttach.getId());
                attachedPessoaFisicaJuridicaCollection.add(pessoaFisicaJuridicaCollectionPessoaFisicaJuridicaToAttach);
            }
            pessoaJuridica.setPessoaFisicaJuridicaCollection(attachedPessoaFisicaJuridicaCollection);
            Collection<PessoaJuridicaSucessao> attachedPessoaJuridicaSucessaoCollection = new ArrayList<PessoaJuridicaSucessao>();
            for (PessoaJuridicaSucessao pessoaJuridicaSucessaoCollectionPessoaJuridicaSucessaoToAttach : pessoaJuridica.getPessoaJuridicaSucessaoCollection()) {
                pessoaJuridicaSucessaoCollectionPessoaJuridicaSucessaoToAttach = em.getReference(pessoaJuridicaSucessaoCollectionPessoaJuridicaSucessaoToAttach.getClass(), pessoaJuridicaSucessaoCollectionPessoaJuridicaSucessaoToAttach.getId());
                attachedPessoaJuridicaSucessaoCollection.add(pessoaJuridicaSucessaoCollectionPessoaJuridicaSucessaoToAttach);
            }
            pessoaJuridica.setPessoaJuridicaSucessaoCollection(attachedPessoaJuridicaSucessaoCollection);
            Collection<PessoaJuridicaSucessao> attachedPessoaJuridicaSucessaoCollection1 = new ArrayList<PessoaJuridicaSucessao>();
            for (PessoaJuridicaSucessao pessoaJuridicaSucessaoCollection1PessoaJuridicaSucessaoToAttach : pessoaJuridica.getPessoaJuridicaSucessaoCollection1()) {
                pessoaJuridicaSucessaoCollection1PessoaJuridicaSucessaoToAttach = em.getReference(pessoaJuridicaSucessaoCollection1PessoaJuridicaSucessaoToAttach.getClass(), pessoaJuridicaSucessaoCollection1PessoaJuridicaSucessaoToAttach.getId());
                attachedPessoaJuridicaSucessaoCollection1.add(pessoaJuridicaSucessaoCollection1PessoaJuridicaSucessaoToAttach);
            }
            pessoaJuridica.setPessoaJuridicaSucessaoCollection1(attachedPessoaJuridicaSucessaoCollection1);
            Collection<PessoaJuridicaJuridicaHistorico> attachedPessoaJuridicaJuridicaHistoricoCollection = new ArrayList<PessoaJuridicaJuridicaHistorico>();
            for (PessoaJuridicaJuridicaHistorico pessoaJuridicaJuridicaHistoricoCollectionPessoaJuridicaJuridicaHistoricoToAttach : pessoaJuridica.getPessoaJuridicaJuridicaHistoricoCollection()) {
                pessoaJuridicaJuridicaHistoricoCollectionPessoaJuridicaJuridicaHistoricoToAttach = em.getReference(pessoaJuridicaJuridicaHistoricoCollectionPessoaJuridicaJuridicaHistoricoToAttach.getClass(), pessoaJuridicaJuridicaHistoricoCollectionPessoaJuridicaJuridicaHistoricoToAttach.getId());
                attachedPessoaJuridicaJuridicaHistoricoCollection.add(pessoaJuridicaJuridicaHistoricoCollectionPessoaJuridicaJuridicaHistoricoToAttach);
            }
            pessoaJuridica.setPessoaJuridicaJuridicaHistoricoCollection(attachedPessoaJuridicaJuridicaHistoricoCollection);
            Collection<PessoaJuridicaJuridicaHistorico> attachedPessoaJuridicaJuridicaHistoricoCollection1 = new ArrayList<PessoaJuridicaJuridicaHistorico>();
            for (PessoaJuridicaJuridicaHistorico pessoaJuridicaJuridicaHistoricoCollection1PessoaJuridicaJuridicaHistoricoToAttach : pessoaJuridica.getPessoaJuridicaJuridicaHistoricoCollection1()) {
                pessoaJuridicaJuridicaHistoricoCollection1PessoaJuridicaJuridicaHistoricoToAttach = em.getReference(pessoaJuridicaJuridicaHistoricoCollection1PessoaJuridicaJuridicaHistoricoToAttach.getClass(), pessoaJuridicaJuridicaHistoricoCollection1PessoaJuridicaJuridicaHistoricoToAttach.getId());
                attachedPessoaJuridicaJuridicaHistoricoCollection1.add(pessoaJuridicaJuridicaHistoricoCollection1PessoaJuridicaJuridicaHistoricoToAttach);
            }
            pessoaJuridica.setPessoaJuridicaJuridicaHistoricoCollection1(attachedPessoaJuridicaJuridicaHistoricoCollection1);
            Collection<PessoaJuridicaJuridica> attachedPessoaJuridicaJuridicaCollection = new ArrayList<PessoaJuridicaJuridica>();
            for (PessoaJuridicaJuridica pessoaJuridicaJuridicaCollectionPessoaJuridicaJuridicaToAttach : pessoaJuridica.getPessoaJuridicaJuridicaCollection()) {
                pessoaJuridicaJuridicaCollectionPessoaJuridicaJuridicaToAttach = em.getReference(pessoaJuridicaJuridicaCollectionPessoaJuridicaJuridicaToAttach.getClass(), pessoaJuridicaJuridicaCollectionPessoaJuridicaJuridicaToAttach.getId());
                attachedPessoaJuridicaJuridicaCollection.add(pessoaJuridicaJuridicaCollectionPessoaJuridicaJuridicaToAttach);
            }
            pessoaJuridica.setPessoaJuridicaJuridicaCollection(attachedPessoaJuridicaJuridicaCollection);
            Collection<PessoaJuridicaJuridica> attachedPessoaJuridicaJuridicaCollection1 = new ArrayList<PessoaJuridicaJuridica>();
            for (PessoaJuridicaJuridica pessoaJuridicaJuridicaCollection1PessoaJuridicaJuridicaToAttach : pessoaJuridica.getPessoaJuridicaJuridicaCollection1()) {
                pessoaJuridicaJuridicaCollection1PessoaJuridicaJuridicaToAttach = em.getReference(pessoaJuridicaJuridicaCollection1PessoaJuridicaJuridicaToAttach.getClass(), pessoaJuridicaJuridicaCollection1PessoaJuridicaJuridicaToAttach.getId());
                attachedPessoaJuridicaJuridicaCollection1.add(pessoaJuridicaJuridicaCollection1PessoaJuridicaJuridicaToAttach);
            }
            pessoaJuridica.setPessoaJuridicaJuridicaCollection1(attachedPessoaJuridicaJuridicaCollection1);
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
            Collection<PessoaFisicaJuridicaHistorico> attachedPessoaFisicaJuridicaHistoricoCollection = new ArrayList<PessoaFisicaJuridicaHistorico>();
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistoricoToAttach : pessoaJuridica.getPessoaFisicaJuridicaHistoricoCollection()) {
                pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistoricoToAttach = em.getReference(pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistoricoToAttach.getClass(), pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistoricoToAttach.getId());
                attachedPessoaFisicaJuridicaHistoricoCollection.add(pessoaFisicaJuridicaHistoricoCollectionPessoaFisicaJuridicaHistoricoToAttach);
            }
            pessoaJuridica.setPessoaFisicaJuridicaHistoricoCollection(attachedPessoaFisicaJuridicaHistoricoCollection);
            em.persist(pessoaJuridica);
            if (tipoEmpresarialFk != null) {
                tipoEmpresarialFk.getPessoaJuridicaCollection().add(pessoaJuridica);
                tipoEmpresarialFk = em.merge(tipoEmpresarialFk);
            }
            if (usuarioFk != null) {
                usuarioFk.getPessoaJuridicaCollection().add(pessoaJuridica);
                usuarioFk = em.merge(usuarioFk);
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
            for (PessoaJuridicaSucessao pessoaJuridicaSucessaoCollectionPessoaJuridicaSucessao : pessoaJuridica.getPessoaJuridicaSucessaoCollection()) {
                PessoaJuridica oldPessoaJuridicaSucedidaFkOfPessoaJuridicaSucessaoCollectionPessoaJuridicaSucessao = pessoaJuridicaSucessaoCollectionPessoaJuridicaSucessao.getPessoaJuridicaSucedidaFk();
                pessoaJuridicaSucessaoCollectionPessoaJuridicaSucessao.setPessoaJuridicaSucedidaFk(pessoaJuridica);
                pessoaJuridicaSucessaoCollectionPessoaJuridicaSucessao = em.merge(pessoaJuridicaSucessaoCollectionPessoaJuridicaSucessao);
                if (oldPessoaJuridicaSucedidaFkOfPessoaJuridicaSucessaoCollectionPessoaJuridicaSucessao != null) {
                    oldPessoaJuridicaSucedidaFkOfPessoaJuridicaSucessaoCollectionPessoaJuridicaSucessao.getPessoaJuridicaSucessaoCollection().remove(pessoaJuridicaSucessaoCollectionPessoaJuridicaSucessao);
                    oldPessoaJuridicaSucedidaFkOfPessoaJuridicaSucessaoCollectionPessoaJuridicaSucessao = em.merge(oldPessoaJuridicaSucedidaFkOfPessoaJuridicaSucessaoCollectionPessoaJuridicaSucessao);
                }
            }
            for (PessoaJuridicaSucessao pessoaJuridicaSucessaoCollection1PessoaJuridicaSucessao : pessoaJuridica.getPessoaJuridicaSucessaoCollection1()) {
                PessoaJuridica oldPessoaJuridicaSucessoraFkOfPessoaJuridicaSucessaoCollection1PessoaJuridicaSucessao = pessoaJuridicaSucessaoCollection1PessoaJuridicaSucessao.getPessoaJuridicaSucessoraFk();
                pessoaJuridicaSucessaoCollection1PessoaJuridicaSucessao.setPessoaJuridicaSucessoraFk(pessoaJuridica);
                pessoaJuridicaSucessaoCollection1PessoaJuridicaSucessao = em.merge(pessoaJuridicaSucessaoCollection1PessoaJuridicaSucessao);
                if (oldPessoaJuridicaSucessoraFkOfPessoaJuridicaSucessaoCollection1PessoaJuridicaSucessao != null) {
                    oldPessoaJuridicaSucessoraFkOfPessoaJuridicaSucessaoCollection1PessoaJuridicaSucessao.getPessoaJuridicaSucessaoCollection1().remove(pessoaJuridicaSucessaoCollection1PessoaJuridicaSucessao);
                    oldPessoaJuridicaSucessoraFkOfPessoaJuridicaSucessaoCollection1PessoaJuridicaSucessao = em.merge(oldPessoaJuridicaSucessoraFkOfPessoaJuridicaSucessaoCollection1PessoaJuridicaSucessao);
                }
            }
            for (PessoaJuridicaJuridicaHistorico pessoaJuridicaJuridicaHistoricoCollectionPessoaJuridicaJuridicaHistorico : pessoaJuridica.getPessoaJuridicaJuridicaHistoricoCollection()) {
                PessoaJuridica oldPessoaJuridicaSocioAFkOfPessoaJuridicaJuridicaHistoricoCollectionPessoaJuridicaJuridicaHistorico = pessoaJuridicaJuridicaHistoricoCollectionPessoaJuridicaJuridicaHistorico.getPessoaJuridicaSocioAFk();
                pessoaJuridicaJuridicaHistoricoCollectionPessoaJuridicaJuridicaHistorico.setPessoaJuridicaSocioAFk(pessoaJuridica);
                pessoaJuridicaJuridicaHistoricoCollectionPessoaJuridicaJuridicaHistorico = em.merge(pessoaJuridicaJuridicaHistoricoCollectionPessoaJuridicaJuridicaHistorico);
                if (oldPessoaJuridicaSocioAFkOfPessoaJuridicaJuridicaHistoricoCollectionPessoaJuridicaJuridicaHistorico != null) {
                    oldPessoaJuridicaSocioAFkOfPessoaJuridicaJuridicaHistoricoCollectionPessoaJuridicaJuridicaHistorico.getPessoaJuridicaJuridicaHistoricoCollection().remove(pessoaJuridicaJuridicaHistoricoCollectionPessoaJuridicaJuridicaHistorico);
                    oldPessoaJuridicaSocioAFkOfPessoaJuridicaJuridicaHistoricoCollectionPessoaJuridicaJuridicaHistorico = em.merge(oldPessoaJuridicaSocioAFkOfPessoaJuridicaJuridicaHistoricoCollectionPessoaJuridicaJuridicaHistorico);
                }
            }
            for (PessoaJuridicaJuridicaHistorico pessoaJuridicaJuridicaHistoricoCollection1PessoaJuridicaJuridicaHistorico : pessoaJuridica.getPessoaJuridicaJuridicaHistoricoCollection1()) {
                PessoaJuridica oldPessoaJuridicaSocioBFkOfPessoaJuridicaJuridicaHistoricoCollection1PessoaJuridicaJuridicaHistorico = pessoaJuridicaJuridicaHistoricoCollection1PessoaJuridicaJuridicaHistorico.getPessoaJuridicaSocioBFk();
                pessoaJuridicaJuridicaHistoricoCollection1PessoaJuridicaJuridicaHistorico.setPessoaJuridicaSocioBFk(pessoaJuridica);
                pessoaJuridicaJuridicaHistoricoCollection1PessoaJuridicaJuridicaHistorico = em.merge(pessoaJuridicaJuridicaHistoricoCollection1PessoaJuridicaJuridicaHistorico);
                if (oldPessoaJuridicaSocioBFkOfPessoaJuridicaJuridicaHistoricoCollection1PessoaJuridicaJuridicaHistorico != null) {
                    oldPessoaJuridicaSocioBFkOfPessoaJuridicaJuridicaHistoricoCollection1PessoaJuridicaJuridicaHistorico.getPessoaJuridicaJuridicaHistoricoCollection1().remove(pessoaJuridicaJuridicaHistoricoCollection1PessoaJuridicaJuridicaHistorico);
                    oldPessoaJuridicaSocioBFkOfPessoaJuridicaJuridicaHistoricoCollection1PessoaJuridicaJuridicaHistorico = em.merge(oldPessoaJuridicaSocioBFkOfPessoaJuridicaJuridicaHistoricoCollection1PessoaJuridicaJuridicaHistorico);
                }
            }
            for (PessoaJuridicaJuridica pessoaJuridicaJuridicaCollectionPessoaJuridicaJuridica : pessoaJuridica.getPessoaJuridicaJuridicaCollection()) {
                PessoaJuridica oldPessoaJuridicaSocioAFkOfPessoaJuridicaJuridicaCollectionPessoaJuridicaJuridica = pessoaJuridicaJuridicaCollectionPessoaJuridicaJuridica.getPessoaJuridicaSocioAFk();
                pessoaJuridicaJuridicaCollectionPessoaJuridicaJuridica.setPessoaJuridicaSocioAFk(pessoaJuridica);
                pessoaJuridicaJuridicaCollectionPessoaJuridicaJuridica = em.merge(pessoaJuridicaJuridicaCollectionPessoaJuridicaJuridica);
                if (oldPessoaJuridicaSocioAFkOfPessoaJuridicaJuridicaCollectionPessoaJuridicaJuridica != null) {
                    oldPessoaJuridicaSocioAFkOfPessoaJuridicaJuridicaCollectionPessoaJuridicaJuridica.getPessoaJuridicaJuridicaCollection().remove(pessoaJuridicaJuridicaCollectionPessoaJuridicaJuridica);
                    oldPessoaJuridicaSocioAFkOfPessoaJuridicaJuridicaCollectionPessoaJuridicaJuridica = em.merge(oldPessoaJuridicaSocioAFkOfPessoaJuridicaJuridicaCollectionPessoaJuridicaJuridica);
                }
            }
            for (PessoaJuridicaJuridica pessoaJuridicaJuridicaCollection1PessoaJuridicaJuridica : pessoaJuridica.getPessoaJuridicaJuridicaCollection1()) {
                PessoaJuridica oldPessoaJuridicaSocioBFkOfPessoaJuridicaJuridicaCollection1PessoaJuridicaJuridica = pessoaJuridicaJuridicaCollection1PessoaJuridicaJuridica.getPessoaJuridicaSocioBFk();
                pessoaJuridicaJuridicaCollection1PessoaJuridicaJuridica.setPessoaJuridicaSocioBFk(pessoaJuridica);
                pessoaJuridicaJuridicaCollection1PessoaJuridicaJuridica = em.merge(pessoaJuridicaJuridicaCollection1PessoaJuridicaJuridica);
                if (oldPessoaJuridicaSocioBFkOfPessoaJuridicaJuridicaCollection1PessoaJuridicaJuridica != null) {
                    oldPessoaJuridicaSocioBFkOfPessoaJuridicaJuridicaCollection1PessoaJuridicaJuridica.getPessoaJuridicaJuridicaCollection1().remove(pessoaJuridicaJuridicaCollection1PessoaJuridicaJuridica);
                    oldPessoaJuridicaSocioBFkOfPessoaJuridicaJuridicaCollection1PessoaJuridicaJuridica = em.merge(oldPessoaJuridicaSocioBFkOfPessoaJuridicaJuridicaCollection1PessoaJuridicaJuridica);
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
            TipoEmpresarial tipoEmpresarialFkOld = persistentPessoaJuridica.getTipoEmpresarialFk();
            TipoEmpresarial tipoEmpresarialFkNew = pessoaJuridica.getTipoEmpresarialFk();
            Usuario usuarioFkOld = persistentPessoaJuridica.getUsuarioFk();
            Usuario usuarioFkNew = pessoaJuridica.getUsuarioFk();
            Collection<PessoaFisicaJuridica> pessoaFisicaJuridicaCollectionOld = persistentPessoaJuridica.getPessoaFisicaJuridicaCollection();
            Collection<PessoaFisicaJuridica> pessoaFisicaJuridicaCollectionNew = pessoaJuridica.getPessoaFisicaJuridicaCollection();
            Collection<PessoaJuridicaSucessao> pessoaJuridicaSucessaoCollectionOld = persistentPessoaJuridica.getPessoaJuridicaSucessaoCollection();
            Collection<PessoaJuridicaSucessao> pessoaJuridicaSucessaoCollectionNew = pessoaJuridica.getPessoaJuridicaSucessaoCollection();
            Collection<PessoaJuridicaSucessao> pessoaJuridicaSucessaoCollection1Old = persistentPessoaJuridica.getPessoaJuridicaSucessaoCollection1();
            Collection<PessoaJuridicaSucessao> pessoaJuridicaSucessaoCollection1New = pessoaJuridica.getPessoaJuridicaSucessaoCollection1();
            Collection<PessoaJuridicaJuridicaHistorico> pessoaJuridicaJuridicaHistoricoCollectionOld = persistentPessoaJuridica.getPessoaJuridicaJuridicaHistoricoCollection();
            Collection<PessoaJuridicaJuridicaHistorico> pessoaJuridicaJuridicaHistoricoCollectionNew = pessoaJuridica.getPessoaJuridicaJuridicaHistoricoCollection();
            Collection<PessoaJuridicaJuridicaHistorico> pessoaJuridicaJuridicaHistoricoCollection1Old = persistentPessoaJuridica.getPessoaJuridicaJuridicaHistoricoCollection1();
            Collection<PessoaJuridicaJuridicaHistorico> pessoaJuridicaJuridicaHistoricoCollection1New = pessoaJuridica.getPessoaJuridicaJuridicaHistoricoCollection1();
            Collection<PessoaJuridicaJuridica> pessoaJuridicaJuridicaCollectionOld = persistentPessoaJuridica.getPessoaJuridicaJuridicaCollection();
            Collection<PessoaJuridicaJuridica> pessoaJuridicaJuridicaCollectionNew = pessoaJuridica.getPessoaJuridicaJuridicaCollection();
            Collection<PessoaJuridicaJuridica> pessoaJuridicaJuridicaCollection1Old = persistentPessoaJuridica.getPessoaJuridicaJuridicaCollection1();
            Collection<PessoaJuridicaJuridica> pessoaJuridicaJuridicaCollection1New = pessoaJuridica.getPessoaJuridicaJuridicaCollection1();
            Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollectionOld = persistentPessoaJuridica.getPessoaJuridicaHistoricoCollection();
            Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollectionNew = pessoaJuridica.getPessoaJuridicaHistoricoCollection();
            Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollection1Old = persistentPessoaJuridica.getPessoaJuridicaHistoricoCollection1();
            Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollection1New = pessoaJuridica.getPessoaJuridicaHistoricoCollection1();
            Collection<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoCollectionOld = persistentPessoaJuridica.getPessoaFisicaJuridicaHistoricoCollection();
            Collection<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoCollectionNew = pessoaJuridica.getPessoaFisicaJuridicaHistoricoCollection();
            List<String> illegalOrphanMessages = null;
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionOldPessoaFisicaJuridica : pessoaFisicaJuridicaCollectionOld) {
                if (!pessoaFisicaJuridicaCollectionNew.contains(pessoaFisicaJuridicaCollectionOldPessoaFisicaJuridica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PessoaFisicaJuridica " + pessoaFisicaJuridicaCollectionOldPessoaFisicaJuridica + " since its pessoaJuridicaFk field is not nullable.");
                }
            }
            for (PessoaJuridicaSucessao pessoaJuridicaSucessaoCollectionOldPessoaJuridicaSucessao : pessoaJuridicaSucessaoCollectionOld) {
                if (!pessoaJuridicaSucessaoCollectionNew.contains(pessoaJuridicaSucessaoCollectionOldPessoaJuridicaSucessao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PessoaJuridicaSucessao " + pessoaJuridicaSucessaoCollectionOldPessoaJuridicaSucessao + " since its pessoaJuridicaSucedidaFk field is not nullable.");
                }
            }
            for (PessoaJuridicaSucessao pessoaJuridicaSucessaoCollection1OldPessoaJuridicaSucessao : pessoaJuridicaSucessaoCollection1Old) {
                if (!pessoaJuridicaSucessaoCollection1New.contains(pessoaJuridicaSucessaoCollection1OldPessoaJuridicaSucessao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PessoaJuridicaSucessao " + pessoaJuridicaSucessaoCollection1OldPessoaJuridicaSucessao + " since its pessoaJuridicaSucessoraFk field is not nullable.");
                }
            }
            for (PessoaJuridicaJuridicaHistorico pessoaJuridicaJuridicaHistoricoCollectionOldPessoaJuridicaJuridicaHistorico : pessoaJuridicaJuridicaHistoricoCollectionOld) {
                if (!pessoaJuridicaJuridicaHistoricoCollectionNew.contains(pessoaJuridicaJuridicaHistoricoCollectionOldPessoaJuridicaJuridicaHistorico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PessoaJuridicaJuridicaHistorico " + pessoaJuridicaJuridicaHistoricoCollectionOldPessoaJuridicaJuridicaHistorico + " since its pessoaJuridicaSocioAFk field is not nullable.");
                }
            }
            for (PessoaJuridicaJuridicaHistorico pessoaJuridicaJuridicaHistoricoCollection1OldPessoaJuridicaJuridicaHistorico : pessoaJuridicaJuridicaHistoricoCollection1Old) {
                if (!pessoaJuridicaJuridicaHistoricoCollection1New.contains(pessoaJuridicaJuridicaHistoricoCollection1OldPessoaJuridicaJuridicaHistorico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PessoaJuridicaJuridicaHistorico " + pessoaJuridicaJuridicaHistoricoCollection1OldPessoaJuridicaJuridicaHistorico + " since its pessoaJuridicaSocioBFk field is not nullable.");
                }
            }
            for (PessoaJuridicaJuridica pessoaJuridicaJuridicaCollectionOldPessoaJuridicaJuridica : pessoaJuridicaJuridicaCollectionOld) {
                if (!pessoaJuridicaJuridicaCollectionNew.contains(pessoaJuridicaJuridicaCollectionOldPessoaJuridicaJuridica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PessoaJuridicaJuridica " + pessoaJuridicaJuridicaCollectionOldPessoaJuridicaJuridica + " since its pessoaJuridicaSocioAFk field is not nullable.");
                }
            }
            for (PessoaJuridicaJuridica pessoaJuridicaJuridicaCollection1OldPessoaJuridicaJuridica : pessoaJuridicaJuridicaCollection1Old) {
                if (!pessoaJuridicaJuridicaCollection1New.contains(pessoaJuridicaJuridicaCollection1OldPessoaJuridicaJuridica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PessoaJuridicaJuridica " + pessoaJuridicaJuridicaCollection1OldPessoaJuridicaJuridica + " since its pessoaJuridicaSocioBFk field is not nullable.");
                }
            }
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollectionOldPessoaJuridicaHistorico : pessoaJuridicaHistoricoCollectionOld) {
                if (!pessoaJuridicaHistoricoCollectionNew.contains(pessoaJuridicaHistoricoCollectionOldPessoaJuridicaHistorico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PessoaJuridicaHistorico " + pessoaJuridicaHistoricoCollectionOldPessoaJuridicaHistorico + " since its pessoaJuridicaFk field is not nullable.");
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
            if (tipoEmpresarialFkNew != null) {
                tipoEmpresarialFkNew = em.getReference(tipoEmpresarialFkNew.getClass(), tipoEmpresarialFkNew.getId());
                pessoaJuridica.setTipoEmpresarialFk(tipoEmpresarialFkNew);
            }
            if (usuarioFkNew != null) {
                usuarioFkNew = em.getReference(usuarioFkNew.getClass(), usuarioFkNew.getId());
                pessoaJuridica.setUsuarioFk(usuarioFkNew);
            }
            Collection<PessoaFisicaJuridica> attachedPessoaFisicaJuridicaCollectionNew = new ArrayList<PessoaFisicaJuridica>();
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridicaToAttach : pessoaFisicaJuridicaCollectionNew) {
                pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridicaToAttach = em.getReference(pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridicaToAttach.getClass(), pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridicaToAttach.getId());
                attachedPessoaFisicaJuridicaCollectionNew.add(pessoaFisicaJuridicaCollectionNewPessoaFisicaJuridicaToAttach);
            }
            pessoaFisicaJuridicaCollectionNew = attachedPessoaFisicaJuridicaCollectionNew;
            pessoaJuridica.setPessoaFisicaJuridicaCollection(pessoaFisicaJuridicaCollectionNew);
            Collection<PessoaJuridicaSucessao> attachedPessoaJuridicaSucessaoCollectionNew = new ArrayList<PessoaJuridicaSucessao>();
            for (PessoaJuridicaSucessao pessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessaoToAttach : pessoaJuridicaSucessaoCollectionNew) {
                pessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessaoToAttach = em.getReference(pessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessaoToAttach.getClass(), pessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessaoToAttach.getId());
                attachedPessoaJuridicaSucessaoCollectionNew.add(pessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessaoToAttach);
            }
            pessoaJuridicaSucessaoCollectionNew = attachedPessoaJuridicaSucessaoCollectionNew;
            pessoaJuridica.setPessoaJuridicaSucessaoCollection(pessoaJuridicaSucessaoCollectionNew);
            Collection<PessoaJuridicaSucessao> attachedPessoaJuridicaSucessaoCollection1New = new ArrayList<PessoaJuridicaSucessao>();
            for (PessoaJuridicaSucessao pessoaJuridicaSucessaoCollection1NewPessoaJuridicaSucessaoToAttach : pessoaJuridicaSucessaoCollection1New) {
                pessoaJuridicaSucessaoCollection1NewPessoaJuridicaSucessaoToAttach = em.getReference(pessoaJuridicaSucessaoCollection1NewPessoaJuridicaSucessaoToAttach.getClass(), pessoaJuridicaSucessaoCollection1NewPessoaJuridicaSucessaoToAttach.getId());
                attachedPessoaJuridicaSucessaoCollection1New.add(pessoaJuridicaSucessaoCollection1NewPessoaJuridicaSucessaoToAttach);
            }
            pessoaJuridicaSucessaoCollection1New = attachedPessoaJuridicaSucessaoCollection1New;
            pessoaJuridica.setPessoaJuridicaSucessaoCollection1(pessoaJuridicaSucessaoCollection1New);
            Collection<PessoaJuridicaJuridicaHistorico> attachedPessoaJuridicaJuridicaHistoricoCollectionNew = new ArrayList<PessoaJuridicaJuridicaHistorico>();
            for (PessoaJuridicaJuridicaHistorico pessoaJuridicaJuridicaHistoricoCollectionNewPessoaJuridicaJuridicaHistoricoToAttach : pessoaJuridicaJuridicaHistoricoCollectionNew) {
                pessoaJuridicaJuridicaHistoricoCollectionNewPessoaJuridicaJuridicaHistoricoToAttach = em.getReference(pessoaJuridicaJuridicaHistoricoCollectionNewPessoaJuridicaJuridicaHistoricoToAttach.getClass(), pessoaJuridicaJuridicaHistoricoCollectionNewPessoaJuridicaJuridicaHistoricoToAttach.getId());
                attachedPessoaJuridicaJuridicaHistoricoCollectionNew.add(pessoaJuridicaJuridicaHistoricoCollectionNewPessoaJuridicaJuridicaHistoricoToAttach);
            }
            pessoaJuridicaJuridicaHistoricoCollectionNew = attachedPessoaJuridicaJuridicaHistoricoCollectionNew;
            pessoaJuridica.setPessoaJuridicaJuridicaHistoricoCollection(pessoaJuridicaJuridicaHistoricoCollectionNew);
            Collection<PessoaJuridicaJuridicaHistorico> attachedPessoaJuridicaJuridicaHistoricoCollection1New = new ArrayList<PessoaJuridicaJuridicaHistorico>();
            for (PessoaJuridicaJuridicaHistorico pessoaJuridicaJuridicaHistoricoCollection1NewPessoaJuridicaJuridicaHistoricoToAttach : pessoaJuridicaJuridicaHistoricoCollection1New) {
                pessoaJuridicaJuridicaHistoricoCollection1NewPessoaJuridicaJuridicaHistoricoToAttach = em.getReference(pessoaJuridicaJuridicaHistoricoCollection1NewPessoaJuridicaJuridicaHistoricoToAttach.getClass(), pessoaJuridicaJuridicaHistoricoCollection1NewPessoaJuridicaJuridicaHistoricoToAttach.getId());
                attachedPessoaJuridicaJuridicaHistoricoCollection1New.add(pessoaJuridicaJuridicaHistoricoCollection1NewPessoaJuridicaJuridicaHistoricoToAttach);
            }
            pessoaJuridicaJuridicaHistoricoCollection1New = attachedPessoaJuridicaJuridicaHistoricoCollection1New;
            pessoaJuridica.setPessoaJuridicaJuridicaHistoricoCollection1(pessoaJuridicaJuridicaHistoricoCollection1New);
            Collection<PessoaJuridicaJuridica> attachedPessoaJuridicaJuridicaCollectionNew = new ArrayList<PessoaJuridicaJuridica>();
            for (PessoaJuridicaJuridica pessoaJuridicaJuridicaCollectionNewPessoaJuridicaJuridicaToAttach : pessoaJuridicaJuridicaCollectionNew) {
                pessoaJuridicaJuridicaCollectionNewPessoaJuridicaJuridicaToAttach = em.getReference(pessoaJuridicaJuridicaCollectionNewPessoaJuridicaJuridicaToAttach.getClass(), pessoaJuridicaJuridicaCollectionNewPessoaJuridicaJuridicaToAttach.getId());
                attachedPessoaJuridicaJuridicaCollectionNew.add(pessoaJuridicaJuridicaCollectionNewPessoaJuridicaJuridicaToAttach);
            }
            pessoaJuridicaJuridicaCollectionNew = attachedPessoaJuridicaJuridicaCollectionNew;
            pessoaJuridica.setPessoaJuridicaJuridicaCollection(pessoaJuridicaJuridicaCollectionNew);
            Collection<PessoaJuridicaJuridica> attachedPessoaJuridicaJuridicaCollection1New = new ArrayList<PessoaJuridicaJuridica>();
            for (PessoaJuridicaJuridica pessoaJuridicaJuridicaCollection1NewPessoaJuridicaJuridicaToAttach : pessoaJuridicaJuridicaCollection1New) {
                pessoaJuridicaJuridicaCollection1NewPessoaJuridicaJuridicaToAttach = em.getReference(pessoaJuridicaJuridicaCollection1NewPessoaJuridicaJuridicaToAttach.getClass(), pessoaJuridicaJuridicaCollection1NewPessoaJuridicaJuridicaToAttach.getId());
                attachedPessoaJuridicaJuridicaCollection1New.add(pessoaJuridicaJuridicaCollection1NewPessoaJuridicaJuridicaToAttach);
            }
            pessoaJuridicaJuridicaCollection1New = attachedPessoaJuridicaJuridicaCollection1New;
            pessoaJuridica.setPessoaJuridicaJuridicaCollection1(pessoaJuridicaJuridicaCollection1New);
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
            Collection<PessoaFisicaJuridicaHistorico> attachedPessoaFisicaJuridicaHistoricoCollectionNew = new ArrayList<PessoaFisicaJuridicaHistorico>();
            for (PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistoricoToAttach : pessoaFisicaJuridicaHistoricoCollectionNew) {
                pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistoricoToAttach = em.getReference(pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistoricoToAttach.getClass(), pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistoricoToAttach.getId());
                attachedPessoaFisicaJuridicaHistoricoCollectionNew.add(pessoaFisicaJuridicaHistoricoCollectionNewPessoaFisicaJuridicaHistoricoToAttach);
            }
            pessoaFisicaJuridicaHistoricoCollectionNew = attachedPessoaFisicaJuridicaHistoricoCollectionNew;
            pessoaJuridica.setPessoaFisicaJuridicaHistoricoCollection(pessoaFisicaJuridicaHistoricoCollectionNew);
            pessoaJuridica = em.merge(pessoaJuridica);
            if (tipoEmpresarialFkOld != null && !tipoEmpresarialFkOld.equals(tipoEmpresarialFkNew)) {
                tipoEmpresarialFkOld.getPessoaJuridicaCollection().remove(pessoaJuridica);
                tipoEmpresarialFkOld = em.merge(tipoEmpresarialFkOld);
            }
            if (tipoEmpresarialFkNew != null && !tipoEmpresarialFkNew.equals(tipoEmpresarialFkOld)) {
                tipoEmpresarialFkNew.getPessoaJuridicaCollection().add(pessoaJuridica);
                tipoEmpresarialFkNew = em.merge(tipoEmpresarialFkNew);
            }
            if (usuarioFkOld != null && !usuarioFkOld.equals(usuarioFkNew)) {
                usuarioFkOld.getPessoaJuridicaCollection().remove(pessoaJuridica);
                usuarioFkOld = em.merge(usuarioFkOld);
            }
            if (usuarioFkNew != null && !usuarioFkNew.equals(usuarioFkOld)) {
                usuarioFkNew.getPessoaJuridicaCollection().add(pessoaJuridica);
                usuarioFkNew = em.merge(usuarioFkNew);
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
            for (PessoaJuridicaSucessao pessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao : pessoaJuridicaSucessaoCollectionNew) {
                if (!pessoaJuridicaSucessaoCollectionOld.contains(pessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao)) {
                    PessoaJuridica oldPessoaJuridicaSucedidaFkOfPessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao = pessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao.getPessoaJuridicaSucedidaFk();
                    pessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao.setPessoaJuridicaSucedidaFk(pessoaJuridica);
                    pessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao = em.merge(pessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao);
                    if (oldPessoaJuridicaSucedidaFkOfPessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao != null && !oldPessoaJuridicaSucedidaFkOfPessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao.equals(pessoaJuridica)) {
                        oldPessoaJuridicaSucedidaFkOfPessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao.getPessoaJuridicaSucessaoCollection().remove(pessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao);
                        oldPessoaJuridicaSucedidaFkOfPessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao = em.merge(oldPessoaJuridicaSucedidaFkOfPessoaJuridicaSucessaoCollectionNewPessoaJuridicaSucessao);
                    }
                }
            }
            for (PessoaJuridicaSucessao pessoaJuridicaSucessaoCollection1NewPessoaJuridicaSucessao : pessoaJuridicaSucessaoCollection1New) {
                if (!pessoaJuridicaSucessaoCollection1Old.contains(pessoaJuridicaSucessaoCollection1NewPessoaJuridicaSucessao)) {
                    PessoaJuridica oldPessoaJuridicaSucessoraFkOfPessoaJuridicaSucessaoCollection1NewPessoaJuridicaSucessao = pessoaJuridicaSucessaoCollection1NewPessoaJuridicaSucessao.getPessoaJuridicaSucessoraFk();
                    pessoaJuridicaSucessaoCollection1NewPessoaJuridicaSucessao.setPessoaJuridicaSucessoraFk(pessoaJuridica);
                    pessoaJuridicaSucessaoCollection1NewPessoaJuridicaSucessao = em.merge(pessoaJuridicaSucessaoCollection1NewPessoaJuridicaSucessao);
                    if (oldPessoaJuridicaSucessoraFkOfPessoaJuridicaSucessaoCollection1NewPessoaJuridicaSucessao != null && !oldPessoaJuridicaSucessoraFkOfPessoaJuridicaSucessaoCollection1NewPessoaJuridicaSucessao.equals(pessoaJuridica)) {
                        oldPessoaJuridicaSucessoraFkOfPessoaJuridicaSucessaoCollection1NewPessoaJuridicaSucessao.getPessoaJuridicaSucessaoCollection1().remove(pessoaJuridicaSucessaoCollection1NewPessoaJuridicaSucessao);
                        oldPessoaJuridicaSucessoraFkOfPessoaJuridicaSucessaoCollection1NewPessoaJuridicaSucessao = em.merge(oldPessoaJuridicaSucessoraFkOfPessoaJuridicaSucessaoCollection1NewPessoaJuridicaSucessao);
                    }
                }
            }
            for (PessoaJuridicaJuridicaHistorico pessoaJuridicaJuridicaHistoricoCollectionNewPessoaJuridicaJuridicaHistorico : pessoaJuridicaJuridicaHistoricoCollectionNew) {
                if (!pessoaJuridicaJuridicaHistoricoCollectionOld.contains(pessoaJuridicaJuridicaHistoricoCollectionNewPessoaJuridicaJuridicaHistorico)) {
                    PessoaJuridica oldPessoaJuridicaSocioAFkOfPessoaJuridicaJuridicaHistoricoCollectionNewPessoaJuridicaJuridicaHistorico = pessoaJuridicaJuridicaHistoricoCollectionNewPessoaJuridicaJuridicaHistorico.getPessoaJuridicaSocioAFk();
                    pessoaJuridicaJuridicaHistoricoCollectionNewPessoaJuridicaJuridicaHistorico.setPessoaJuridicaSocioAFk(pessoaJuridica);
                    pessoaJuridicaJuridicaHistoricoCollectionNewPessoaJuridicaJuridicaHistorico = em.merge(pessoaJuridicaJuridicaHistoricoCollectionNewPessoaJuridicaJuridicaHistorico);
                    if (oldPessoaJuridicaSocioAFkOfPessoaJuridicaJuridicaHistoricoCollectionNewPessoaJuridicaJuridicaHistorico != null && !oldPessoaJuridicaSocioAFkOfPessoaJuridicaJuridicaHistoricoCollectionNewPessoaJuridicaJuridicaHistorico.equals(pessoaJuridica)) {
                        oldPessoaJuridicaSocioAFkOfPessoaJuridicaJuridicaHistoricoCollectionNewPessoaJuridicaJuridicaHistorico.getPessoaJuridicaJuridicaHistoricoCollection().remove(pessoaJuridicaJuridicaHistoricoCollectionNewPessoaJuridicaJuridicaHistorico);
                        oldPessoaJuridicaSocioAFkOfPessoaJuridicaJuridicaHistoricoCollectionNewPessoaJuridicaJuridicaHistorico = em.merge(oldPessoaJuridicaSocioAFkOfPessoaJuridicaJuridicaHistoricoCollectionNewPessoaJuridicaJuridicaHistorico);
                    }
                }
            }
            for (PessoaJuridicaJuridicaHistorico pessoaJuridicaJuridicaHistoricoCollection1NewPessoaJuridicaJuridicaHistorico : pessoaJuridicaJuridicaHistoricoCollection1New) {
                if (!pessoaJuridicaJuridicaHistoricoCollection1Old.contains(pessoaJuridicaJuridicaHistoricoCollection1NewPessoaJuridicaJuridicaHistorico)) {
                    PessoaJuridica oldPessoaJuridicaSocioBFkOfPessoaJuridicaJuridicaHistoricoCollection1NewPessoaJuridicaJuridicaHistorico = pessoaJuridicaJuridicaHistoricoCollection1NewPessoaJuridicaJuridicaHistorico.getPessoaJuridicaSocioBFk();
                    pessoaJuridicaJuridicaHistoricoCollection1NewPessoaJuridicaJuridicaHistorico.setPessoaJuridicaSocioBFk(pessoaJuridica);
                    pessoaJuridicaJuridicaHistoricoCollection1NewPessoaJuridicaJuridicaHistorico = em.merge(pessoaJuridicaJuridicaHistoricoCollection1NewPessoaJuridicaJuridicaHistorico);
                    if (oldPessoaJuridicaSocioBFkOfPessoaJuridicaJuridicaHistoricoCollection1NewPessoaJuridicaJuridicaHistorico != null && !oldPessoaJuridicaSocioBFkOfPessoaJuridicaJuridicaHistoricoCollection1NewPessoaJuridicaJuridicaHistorico.equals(pessoaJuridica)) {
                        oldPessoaJuridicaSocioBFkOfPessoaJuridicaJuridicaHistoricoCollection1NewPessoaJuridicaJuridicaHistorico.getPessoaJuridicaJuridicaHistoricoCollection1().remove(pessoaJuridicaJuridicaHistoricoCollection1NewPessoaJuridicaJuridicaHistorico);
                        oldPessoaJuridicaSocioBFkOfPessoaJuridicaJuridicaHistoricoCollection1NewPessoaJuridicaJuridicaHistorico = em.merge(oldPessoaJuridicaSocioBFkOfPessoaJuridicaJuridicaHistoricoCollection1NewPessoaJuridicaJuridicaHistorico);
                    }
                }
            }
            for (PessoaJuridicaJuridica pessoaJuridicaJuridicaCollectionNewPessoaJuridicaJuridica : pessoaJuridicaJuridicaCollectionNew) {
                if (!pessoaJuridicaJuridicaCollectionOld.contains(pessoaJuridicaJuridicaCollectionNewPessoaJuridicaJuridica)) {
                    PessoaJuridica oldPessoaJuridicaSocioAFkOfPessoaJuridicaJuridicaCollectionNewPessoaJuridicaJuridica = pessoaJuridicaJuridicaCollectionNewPessoaJuridicaJuridica.getPessoaJuridicaSocioAFk();
                    pessoaJuridicaJuridicaCollectionNewPessoaJuridicaJuridica.setPessoaJuridicaSocioAFk(pessoaJuridica);
                    pessoaJuridicaJuridicaCollectionNewPessoaJuridicaJuridica = em.merge(pessoaJuridicaJuridicaCollectionNewPessoaJuridicaJuridica);
                    if (oldPessoaJuridicaSocioAFkOfPessoaJuridicaJuridicaCollectionNewPessoaJuridicaJuridica != null && !oldPessoaJuridicaSocioAFkOfPessoaJuridicaJuridicaCollectionNewPessoaJuridicaJuridica.equals(pessoaJuridica)) {
                        oldPessoaJuridicaSocioAFkOfPessoaJuridicaJuridicaCollectionNewPessoaJuridicaJuridica.getPessoaJuridicaJuridicaCollection().remove(pessoaJuridicaJuridicaCollectionNewPessoaJuridicaJuridica);
                        oldPessoaJuridicaSocioAFkOfPessoaJuridicaJuridicaCollectionNewPessoaJuridicaJuridica = em.merge(oldPessoaJuridicaSocioAFkOfPessoaJuridicaJuridicaCollectionNewPessoaJuridicaJuridica);
                    }
                }
            }
            for (PessoaJuridicaJuridica pessoaJuridicaJuridicaCollection1NewPessoaJuridicaJuridica : pessoaJuridicaJuridicaCollection1New) {
                if (!pessoaJuridicaJuridicaCollection1Old.contains(pessoaJuridicaJuridicaCollection1NewPessoaJuridicaJuridica)) {
                    PessoaJuridica oldPessoaJuridicaSocioBFkOfPessoaJuridicaJuridicaCollection1NewPessoaJuridicaJuridica = pessoaJuridicaJuridicaCollection1NewPessoaJuridicaJuridica.getPessoaJuridicaSocioBFk();
                    pessoaJuridicaJuridicaCollection1NewPessoaJuridicaJuridica.setPessoaJuridicaSocioBFk(pessoaJuridica);
                    pessoaJuridicaJuridicaCollection1NewPessoaJuridicaJuridica = em.merge(pessoaJuridicaJuridicaCollection1NewPessoaJuridicaJuridica);
                    if (oldPessoaJuridicaSocioBFkOfPessoaJuridicaJuridicaCollection1NewPessoaJuridicaJuridica != null && !oldPessoaJuridicaSocioBFkOfPessoaJuridicaJuridicaCollection1NewPessoaJuridicaJuridica.equals(pessoaJuridica)) {
                        oldPessoaJuridicaSocioBFkOfPessoaJuridicaJuridicaCollection1NewPessoaJuridicaJuridica.getPessoaJuridicaJuridicaCollection1().remove(pessoaJuridicaJuridicaCollection1NewPessoaJuridicaJuridica);
                        oldPessoaJuridicaSocioBFkOfPessoaJuridicaJuridicaCollection1NewPessoaJuridicaJuridica = em.merge(oldPessoaJuridicaSocioBFkOfPessoaJuridicaJuridicaCollection1NewPessoaJuridicaJuridica);
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
            Collection<PessoaFisicaJuridica> pessoaFisicaJuridicaCollectionOrphanCheck = pessoaJuridica.getPessoaFisicaJuridicaCollection();
            for (PessoaFisicaJuridica pessoaFisicaJuridicaCollectionOrphanCheckPessoaFisicaJuridica : pessoaFisicaJuridicaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PessoaJuridica (" + pessoaJuridica + ") cannot be destroyed since the PessoaFisicaJuridica " + pessoaFisicaJuridicaCollectionOrphanCheckPessoaFisicaJuridica + " in its pessoaFisicaJuridicaCollection field has a non-nullable pessoaJuridicaFk field.");
            }
            Collection<PessoaJuridicaSucessao> pessoaJuridicaSucessaoCollectionOrphanCheck = pessoaJuridica.getPessoaJuridicaSucessaoCollection();
            for (PessoaJuridicaSucessao pessoaJuridicaSucessaoCollectionOrphanCheckPessoaJuridicaSucessao : pessoaJuridicaSucessaoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PessoaJuridica (" + pessoaJuridica + ") cannot be destroyed since the PessoaJuridicaSucessao " + pessoaJuridicaSucessaoCollectionOrphanCheckPessoaJuridicaSucessao + " in its pessoaJuridicaSucessaoCollection field has a non-nullable pessoaJuridicaSucedidaFk field.");
            }
            Collection<PessoaJuridicaSucessao> pessoaJuridicaSucessaoCollection1OrphanCheck = pessoaJuridica.getPessoaJuridicaSucessaoCollection1();
            for (PessoaJuridicaSucessao pessoaJuridicaSucessaoCollection1OrphanCheckPessoaJuridicaSucessao : pessoaJuridicaSucessaoCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PessoaJuridica (" + pessoaJuridica + ") cannot be destroyed since the PessoaJuridicaSucessao " + pessoaJuridicaSucessaoCollection1OrphanCheckPessoaJuridicaSucessao + " in its pessoaJuridicaSucessaoCollection1 field has a non-nullable pessoaJuridicaSucessoraFk field.");
            }
            Collection<PessoaJuridicaJuridicaHistorico> pessoaJuridicaJuridicaHistoricoCollectionOrphanCheck = pessoaJuridica.getPessoaJuridicaJuridicaHistoricoCollection();
            for (PessoaJuridicaJuridicaHistorico pessoaJuridicaJuridicaHistoricoCollectionOrphanCheckPessoaJuridicaJuridicaHistorico : pessoaJuridicaJuridicaHistoricoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PessoaJuridica (" + pessoaJuridica + ") cannot be destroyed since the PessoaJuridicaJuridicaHistorico " + pessoaJuridicaJuridicaHistoricoCollectionOrphanCheckPessoaJuridicaJuridicaHistorico + " in its pessoaJuridicaJuridicaHistoricoCollection field has a non-nullable pessoaJuridicaSocioAFk field.");
            }
            Collection<PessoaJuridicaJuridicaHistorico> pessoaJuridicaJuridicaHistoricoCollection1OrphanCheck = pessoaJuridica.getPessoaJuridicaJuridicaHistoricoCollection1();
            for (PessoaJuridicaJuridicaHistorico pessoaJuridicaJuridicaHistoricoCollection1OrphanCheckPessoaJuridicaJuridicaHistorico : pessoaJuridicaJuridicaHistoricoCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PessoaJuridica (" + pessoaJuridica + ") cannot be destroyed since the PessoaJuridicaJuridicaHistorico " + pessoaJuridicaJuridicaHistoricoCollection1OrphanCheckPessoaJuridicaJuridicaHistorico + " in its pessoaJuridicaJuridicaHistoricoCollection1 field has a non-nullable pessoaJuridicaSocioBFk field.");
            }
            Collection<PessoaJuridicaJuridica> pessoaJuridicaJuridicaCollectionOrphanCheck = pessoaJuridica.getPessoaJuridicaJuridicaCollection();
            for (PessoaJuridicaJuridica pessoaJuridicaJuridicaCollectionOrphanCheckPessoaJuridicaJuridica : pessoaJuridicaJuridicaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PessoaJuridica (" + pessoaJuridica + ") cannot be destroyed since the PessoaJuridicaJuridica " + pessoaJuridicaJuridicaCollectionOrphanCheckPessoaJuridicaJuridica + " in its pessoaJuridicaJuridicaCollection field has a non-nullable pessoaJuridicaSocioAFk field.");
            }
            Collection<PessoaJuridicaJuridica> pessoaJuridicaJuridicaCollection1OrphanCheck = pessoaJuridica.getPessoaJuridicaJuridicaCollection1();
            for (PessoaJuridicaJuridica pessoaJuridicaJuridicaCollection1OrphanCheckPessoaJuridicaJuridica : pessoaJuridicaJuridicaCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PessoaJuridica (" + pessoaJuridica + ") cannot be destroyed since the PessoaJuridicaJuridica " + pessoaJuridicaJuridicaCollection1OrphanCheckPessoaJuridicaJuridica + " in its pessoaJuridicaJuridicaCollection1 field has a non-nullable pessoaJuridicaSocioBFk field.");
            }
            Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollectionOrphanCheck = pessoaJuridica.getPessoaJuridicaHistoricoCollection();
            for (PessoaJuridicaHistorico pessoaJuridicaHistoricoCollectionOrphanCheckPessoaJuridicaHistorico : pessoaJuridicaHistoricoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PessoaJuridica (" + pessoaJuridica + ") cannot be destroyed since the PessoaJuridicaHistorico " + pessoaJuridicaHistoricoCollectionOrphanCheckPessoaJuridicaHistorico + " in its pessoaJuridicaHistoricoCollection field has a non-nullable pessoaJuridicaFk field.");
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
            TipoEmpresarial tipoEmpresarialFk = pessoaJuridica.getTipoEmpresarialFk();
            if (tipoEmpresarialFk != null) {
                tipoEmpresarialFk.getPessoaJuridicaCollection().remove(pessoaJuridica);
                tipoEmpresarialFk = em.merge(tipoEmpresarialFk);
            }
            Usuario usuarioFk = pessoaJuridica.getUsuarioFk();
            if (usuarioFk != null) {
                usuarioFk.getPessoaJuridicaCollection().remove(pessoaJuridica);
                usuarioFk = em.merge(usuarioFk);
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
    
    public PessoaJuridica findByCNPJ(String cnpj) {
        EntityManager em = getEntityManager();
        try {
            PessoaJuridica pj = (PessoaJuridica) em.createNativeQuery("select * from pessoa_juridica "
                    + "where cnpj = '" + cnpj + "'", PessoaJuridica.class).getSingleResult();
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
