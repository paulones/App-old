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
import entidade.EnderecoHistorico;
import entidade.Estado;
import entidade.Instituicao;
import entidade.PessoaFisicaHistorico;
import entidade.Procurador;
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
        if (estado.getPessoaFisicaHistoricoCollection() == null) {
            estado.setPessoaFisicaHistoricoCollection(new ArrayList<PessoaFisicaHistorico>());
        }
        if (estado.getPessoaFisicaHistoricoCollection1() == null) {
            estado.setPessoaFisicaHistoricoCollection1(new ArrayList<PessoaFisicaHistorico>());
        }
        if (estado.getCidadeCollection() == null) {
            estado.setCidadeCollection(new ArrayList<Cidade>());
        }
        if (estado.getEnderecoCollection() == null) {
            estado.setEnderecoCollection(new ArrayList<Endereco>());
        }
        if (estado.getPessoaFisicaCollection() == null) {
            estado.setPessoaFisicaCollection(new ArrayList<PessoaFisica>());
        }
        if (estado.getPessoaFisicaCollection1() == null) {
            estado.setPessoaFisicaCollection1(new ArrayList<PessoaFisica>());
        }
        if (estado.getEnderecoHistoricoCollection() == null) {
            estado.setEnderecoHistoricoCollection(new ArrayList<EnderecoHistorico>());
        }
        if (estado.getProcuradorCollection() == null) {
            estado.setProcuradorCollection(new ArrayList<Procurador>());
        }
        if (estado.getInstituicaoCollection() == null) {
            estado.setInstituicaoCollection(new ArrayList<Instituicao>());
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
            Collection<PessoaFisicaHistorico> attachedPessoaFisicaHistoricoCollection = new ArrayList<PessoaFisicaHistorico>();
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach : estado.getPessoaFisicaHistoricoCollection()) {
                pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach = em.getReference(pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach.getClass(), pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach.getId());
                attachedPessoaFisicaHistoricoCollection.add(pessoaFisicaHistoricoCollectionPessoaFisicaHistoricoToAttach);
            }
            estado.setPessoaFisicaHistoricoCollection(attachedPessoaFisicaHistoricoCollection);
            Collection<PessoaFisicaHistorico> attachedPessoaFisicaHistoricoCollection1 = new ArrayList<PessoaFisicaHistorico>();
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollection1PessoaFisicaHistoricoToAttach : estado.getPessoaFisicaHistoricoCollection1()) {
                pessoaFisicaHistoricoCollection1PessoaFisicaHistoricoToAttach = em.getReference(pessoaFisicaHistoricoCollection1PessoaFisicaHistoricoToAttach.getClass(), pessoaFisicaHistoricoCollection1PessoaFisicaHistoricoToAttach.getId());
                attachedPessoaFisicaHistoricoCollection1.add(pessoaFisicaHistoricoCollection1PessoaFisicaHistoricoToAttach);
            }
            estado.setPessoaFisicaHistoricoCollection1(attachedPessoaFisicaHistoricoCollection1);
            Collection<Cidade> attachedCidadeCollection = new ArrayList<Cidade>();
            for (Cidade cidadeCollectionCidadeToAttach : estado.getCidadeCollection()) {
                cidadeCollectionCidadeToAttach = em.getReference(cidadeCollectionCidadeToAttach.getClass(), cidadeCollectionCidadeToAttach.getId());
                attachedCidadeCollection.add(cidadeCollectionCidadeToAttach);
            }
            estado.setCidadeCollection(attachedCidadeCollection);
            Collection<Endereco> attachedEnderecoCollection = new ArrayList<Endereco>();
            for (Endereco enderecoCollectionEnderecoToAttach : estado.getEnderecoCollection()) {
                enderecoCollectionEnderecoToAttach = em.getReference(enderecoCollectionEnderecoToAttach.getClass(), enderecoCollectionEnderecoToAttach.getId());
                attachedEnderecoCollection.add(enderecoCollectionEnderecoToAttach);
            }
            estado.setEnderecoCollection(attachedEnderecoCollection);
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
            Collection<EnderecoHistorico> attachedEnderecoHistoricoCollection = new ArrayList<EnderecoHistorico>();
            for (EnderecoHistorico enderecoHistoricoCollectionEnderecoHistoricoToAttach : estado.getEnderecoHistoricoCollection()) {
                enderecoHistoricoCollectionEnderecoHistoricoToAttach = em.getReference(enderecoHistoricoCollectionEnderecoHistoricoToAttach.getClass(), enderecoHistoricoCollectionEnderecoHistoricoToAttach.getId());
                attachedEnderecoHistoricoCollection.add(enderecoHistoricoCollectionEnderecoHistoricoToAttach);
            }
            estado.setEnderecoHistoricoCollection(attachedEnderecoHistoricoCollection);
            Collection<Procurador> attachedProcuradorCollection = new ArrayList<Procurador>();
            for (Procurador procuradorCollectionProcuradorToAttach : estado.getProcuradorCollection()) {
                procuradorCollectionProcuradorToAttach = em.getReference(procuradorCollectionProcuradorToAttach.getClass(), procuradorCollectionProcuradorToAttach.getId());
                attachedProcuradorCollection.add(procuradorCollectionProcuradorToAttach);
            }
            estado.setProcuradorCollection(attachedProcuradorCollection);
            Collection<Instituicao> attachedInstituicaoCollection = new ArrayList<Instituicao>();
            for (Instituicao instituicaoCollectionInstituicaoToAttach : estado.getInstituicaoCollection()) {
                instituicaoCollectionInstituicaoToAttach = em.getReference(instituicaoCollectionInstituicaoToAttach.getClass(), instituicaoCollectionInstituicaoToAttach.getId());
                attachedInstituicaoCollection.add(instituicaoCollectionInstituicaoToAttach);
            }
            estado.setInstituicaoCollection(attachedInstituicaoCollection);
            em.persist(estado);
            if (paisFk != null) {
                paisFk.getEstadoCollection().add(estado);
                paisFk = em.merge(paisFk);
            }
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionPessoaFisicaHistorico : estado.getPessoaFisicaHistoricoCollection()) {
                Estado oldRgUfFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico = pessoaFisicaHistoricoCollectionPessoaFisicaHistorico.getRgUfFk();
                pessoaFisicaHistoricoCollectionPessoaFisicaHistorico.setRgUfFk(estado);
                pessoaFisicaHistoricoCollectionPessoaFisicaHistorico = em.merge(pessoaFisicaHistoricoCollectionPessoaFisicaHistorico);
                if (oldRgUfFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico != null) {
                    oldRgUfFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistoricoCollectionPessoaFisicaHistorico);
                    oldRgUfFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico = em.merge(oldRgUfFkOfPessoaFisicaHistoricoCollectionPessoaFisicaHistorico);
                }
            }
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollection1PessoaFisicaHistorico : estado.getPessoaFisicaHistoricoCollection1()) {
                Estado oldEstadoFkOfPessoaFisicaHistoricoCollection1PessoaFisicaHistorico = pessoaFisicaHistoricoCollection1PessoaFisicaHistorico.getEstadoFk();
                pessoaFisicaHistoricoCollection1PessoaFisicaHistorico.setEstadoFk(estado);
                pessoaFisicaHistoricoCollection1PessoaFisicaHistorico = em.merge(pessoaFisicaHistoricoCollection1PessoaFisicaHistorico);
                if (oldEstadoFkOfPessoaFisicaHistoricoCollection1PessoaFisicaHistorico != null) {
                    oldEstadoFkOfPessoaFisicaHistoricoCollection1PessoaFisicaHistorico.getPessoaFisicaHistoricoCollection1().remove(pessoaFisicaHistoricoCollection1PessoaFisicaHistorico);
                    oldEstadoFkOfPessoaFisicaHistoricoCollection1PessoaFisicaHistorico = em.merge(oldEstadoFkOfPessoaFisicaHistoricoCollection1PessoaFisicaHistorico);
                }
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
            for (Endereco enderecoCollectionEndereco : estado.getEnderecoCollection()) {
                Estado oldEstadoFkOfEnderecoCollectionEndereco = enderecoCollectionEndereco.getEstadoFk();
                enderecoCollectionEndereco.setEstadoFk(estado);
                enderecoCollectionEndereco = em.merge(enderecoCollectionEndereco);
                if (oldEstadoFkOfEnderecoCollectionEndereco != null) {
                    oldEstadoFkOfEnderecoCollectionEndereco.getEnderecoCollection().remove(enderecoCollectionEndereco);
                    oldEstadoFkOfEnderecoCollectionEndereco = em.merge(oldEstadoFkOfEnderecoCollectionEndereco);
                }
            }
            for (PessoaFisica pessoaFisicaCollectionPessoaFisica : estado.getPessoaFisicaCollection()) {
                Estado oldEstadoFkOfPessoaFisicaCollectionPessoaFisica = pessoaFisicaCollectionPessoaFisica.getEstadoFk();
                pessoaFisicaCollectionPessoaFisica.setEstadoFk(estado);
                pessoaFisicaCollectionPessoaFisica = em.merge(pessoaFisicaCollectionPessoaFisica);
                if (oldEstadoFkOfPessoaFisicaCollectionPessoaFisica != null) {
                    oldEstadoFkOfPessoaFisicaCollectionPessoaFisica.getPessoaFisicaCollection().remove(pessoaFisicaCollectionPessoaFisica);
                    oldEstadoFkOfPessoaFisicaCollectionPessoaFisica = em.merge(oldEstadoFkOfPessoaFisicaCollectionPessoaFisica);
                }
            }
            for (PessoaFisica pessoaFisicaCollection1PessoaFisica : estado.getPessoaFisicaCollection1()) {
                Estado oldRgUfFkOfPessoaFisicaCollection1PessoaFisica = pessoaFisicaCollection1PessoaFisica.getRgUfFk();
                pessoaFisicaCollection1PessoaFisica.setRgUfFk(estado);
                pessoaFisicaCollection1PessoaFisica = em.merge(pessoaFisicaCollection1PessoaFisica);
                if (oldRgUfFkOfPessoaFisicaCollection1PessoaFisica != null) {
                    oldRgUfFkOfPessoaFisicaCollection1PessoaFisica.getPessoaFisicaCollection1().remove(pessoaFisicaCollection1PessoaFisica);
                    oldRgUfFkOfPessoaFisicaCollection1PessoaFisica = em.merge(oldRgUfFkOfPessoaFisicaCollection1PessoaFisica);
                }
            }
            for (EnderecoHistorico enderecoHistoricoCollectionEnderecoHistorico : estado.getEnderecoHistoricoCollection()) {
                Estado oldEstadoFkOfEnderecoHistoricoCollectionEnderecoHistorico = enderecoHistoricoCollectionEnderecoHistorico.getEstadoFk();
                enderecoHistoricoCollectionEnderecoHistorico.setEstadoFk(estado);
                enderecoHistoricoCollectionEnderecoHistorico = em.merge(enderecoHistoricoCollectionEnderecoHistorico);
                if (oldEstadoFkOfEnderecoHistoricoCollectionEnderecoHistorico != null) {
                    oldEstadoFkOfEnderecoHistoricoCollectionEnderecoHistorico.getEnderecoHistoricoCollection().remove(enderecoHistoricoCollectionEnderecoHistorico);
                    oldEstadoFkOfEnderecoHistoricoCollectionEnderecoHistorico = em.merge(oldEstadoFkOfEnderecoHistoricoCollectionEnderecoHistorico);
                }
            }
            for (Procurador procuradorCollectionProcurador : estado.getProcuradorCollection()) {
                Estado oldEstadoFkOfProcuradorCollectionProcurador = procuradorCollectionProcurador.getEstadoFk();
                procuradorCollectionProcurador.setEstadoFk(estado);
                procuradorCollectionProcurador = em.merge(procuradorCollectionProcurador);
                if (oldEstadoFkOfProcuradorCollectionProcurador != null) {
                    oldEstadoFkOfProcuradorCollectionProcurador.getProcuradorCollection().remove(procuradorCollectionProcurador);
                    oldEstadoFkOfProcuradorCollectionProcurador = em.merge(oldEstadoFkOfProcuradorCollectionProcurador);
                }
            }
            for (Instituicao instituicaoCollectionInstituicao : estado.getInstituicaoCollection()) {
                Estado oldEstadoFkOfInstituicaoCollectionInstituicao = instituicaoCollectionInstituicao.getEstadoFk();
                instituicaoCollectionInstituicao.setEstadoFk(estado);
                instituicaoCollectionInstituicao = em.merge(instituicaoCollectionInstituicao);
                if (oldEstadoFkOfInstituicaoCollectionInstituicao != null) {
                    oldEstadoFkOfInstituicaoCollectionInstituicao.getInstituicaoCollection().remove(instituicaoCollectionInstituicao);
                    oldEstadoFkOfInstituicaoCollectionInstituicao = em.merge(oldEstadoFkOfInstituicaoCollectionInstituicao);
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
            Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollectionOld = persistentEstado.getPessoaFisicaHistoricoCollection();
            Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollectionNew = estado.getPessoaFisicaHistoricoCollection();
            Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollection1Old = persistentEstado.getPessoaFisicaHistoricoCollection1();
            Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollection1New = estado.getPessoaFisicaHistoricoCollection1();
            Collection<Cidade> cidadeCollectionOld = persistentEstado.getCidadeCollection();
            Collection<Cidade> cidadeCollectionNew = estado.getCidadeCollection();
            Collection<Endereco> enderecoCollectionOld = persistentEstado.getEnderecoCollection();
            Collection<Endereco> enderecoCollectionNew = estado.getEnderecoCollection();
            Collection<PessoaFisica> pessoaFisicaCollectionOld = persistentEstado.getPessoaFisicaCollection();
            Collection<PessoaFisica> pessoaFisicaCollectionNew = estado.getPessoaFisicaCollection();
            Collection<PessoaFisica> pessoaFisicaCollection1Old = persistentEstado.getPessoaFisicaCollection1();
            Collection<PessoaFisica> pessoaFisicaCollection1New = estado.getPessoaFisicaCollection1();
            Collection<EnderecoHistorico> enderecoHistoricoCollectionOld = persistentEstado.getEnderecoHistoricoCollection();
            Collection<EnderecoHistorico> enderecoHistoricoCollectionNew = estado.getEnderecoHistoricoCollection();
            Collection<Procurador> procuradorCollectionOld = persistentEstado.getProcuradorCollection();
            Collection<Procurador> procuradorCollectionNew = estado.getProcuradorCollection();
            Collection<Instituicao> instituicaoCollectionOld = persistentEstado.getInstituicaoCollection();
            Collection<Instituicao> instituicaoCollectionNew = estado.getInstituicaoCollection();
            List<String> illegalOrphanMessages = null;
            for (Cidade cidadeCollectionOldCidade : cidadeCollectionOld) {
                if (!cidadeCollectionNew.contains(cidadeCollectionOldCidade)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cidade " + cidadeCollectionOldCidade + " since its estadoFk field is not nullable.");
                }
            }
            for (Procurador procuradorCollectionOldProcurador : procuradorCollectionOld) {
                if (!procuradorCollectionNew.contains(procuradorCollectionOldProcurador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Procurador " + procuradorCollectionOldProcurador + " since its estadoFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (paisFkNew != null) {
                paisFkNew = em.getReference(paisFkNew.getClass(), paisFkNew.getId());
                estado.setPaisFk(paisFkNew);
            }
            Collection<PessoaFisicaHistorico> attachedPessoaFisicaHistoricoCollectionNew = new ArrayList<PessoaFisicaHistorico>();
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach : pessoaFisicaHistoricoCollectionNew) {
                pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach = em.getReference(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach.getClass(), pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach.getId());
                attachedPessoaFisicaHistoricoCollectionNew.add(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistoricoToAttach);
            }
            pessoaFisicaHistoricoCollectionNew = attachedPessoaFisicaHistoricoCollectionNew;
            estado.setPessoaFisicaHistoricoCollection(pessoaFisicaHistoricoCollectionNew);
            Collection<PessoaFisicaHistorico> attachedPessoaFisicaHistoricoCollection1New = new ArrayList<PessoaFisicaHistorico>();
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollection1NewPessoaFisicaHistoricoToAttach : pessoaFisicaHistoricoCollection1New) {
                pessoaFisicaHistoricoCollection1NewPessoaFisicaHistoricoToAttach = em.getReference(pessoaFisicaHistoricoCollection1NewPessoaFisicaHistoricoToAttach.getClass(), pessoaFisicaHistoricoCollection1NewPessoaFisicaHistoricoToAttach.getId());
                attachedPessoaFisicaHistoricoCollection1New.add(pessoaFisicaHistoricoCollection1NewPessoaFisicaHistoricoToAttach);
            }
            pessoaFisicaHistoricoCollection1New = attachedPessoaFisicaHistoricoCollection1New;
            estado.setPessoaFisicaHistoricoCollection1(pessoaFisicaHistoricoCollection1New);
            Collection<Cidade> attachedCidadeCollectionNew = new ArrayList<Cidade>();
            for (Cidade cidadeCollectionNewCidadeToAttach : cidadeCollectionNew) {
                cidadeCollectionNewCidadeToAttach = em.getReference(cidadeCollectionNewCidadeToAttach.getClass(), cidadeCollectionNewCidadeToAttach.getId());
                attachedCidadeCollectionNew.add(cidadeCollectionNewCidadeToAttach);
            }
            cidadeCollectionNew = attachedCidadeCollectionNew;
            estado.setCidadeCollection(cidadeCollectionNew);
            Collection<Endereco> attachedEnderecoCollectionNew = new ArrayList<Endereco>();
            for (Endereco enderecoCollectionNewEnderecoToAttach : enderecoCollectionNew) {
                enderecoCollectionNewEnderecoToAttach = em.getReference(enderecoCollectionNewEnderecoToAttach.getClass(), enderecoCollectionNewEnderecoToAttach.getId());
                attachedEnderecoCollectionNew.add(enderecoCollectionNewEnderecoToAttach);
            }
            enderecoCollectionNew = attachedEnderecoCollectionNew;
            estado.setEnderecoCollection(enderecoCollectionNew);
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
            Collection<EnderecoHistorico> attachedEnderecoHistoricoCollectionNew = new ArrayList<EnderecoHistorico>();
            for (EnderecoHistorico enderecoHistoricoCollectionNewEnderecoHistoricoToAttach : enderecoHistoricoCollectionNew) {
                enderecoHistoricoCollectionNewEnderecoHistoricoToAttach = em.getReference(enderecoHistoricoCollectionNewEnderecoHistoricoToAttach.getClass(), enderecoHistoricoCollectionNewEnderecoHistoricoToAttach.getId());
                attachedEnderecoHistoricoCollectionNew.add(enderecoHistoricoCollectionNewEnderecoHistoricoToAttach);
            }
            enderecoHistoricoCollectionNew = attachedEnderecoHistoricoCollectionNew;
            estado.setEnderecoHistoricoCollection(enderecoHistoricoCollectionNew);
            Collection<Procurador> attachedProcuradorCollectionNew = new ArrayList<Procurador>();
            for (Procurador procuradorCollectionNewProcuradorToAttach : procuradorCollectionNew) {
                procuradorCollectionNewProcuradorToAttach = em.getReference(procuradorCollectionNewProcuradorToAttach.getClass(), procuradorCollectionNewProcuradorToAttach.getId());
                attachedProcuradorCollectionNew.add(procuradorCollectionNewProcuradorToAttach);
            }
            procuradorCollectionNew = attachedProcuradorCollectionNew;
            estado.setProcuradorCollection(procuradorCollectionNew);
            Collection<Instituicao> attachedInstituicaoCollectionNew = new ArrayList<Instituicao>();
            for (Instituicao instituicaoCollectionNewInstituicaoToAttach : instituicaoCollectionNew) {
                instituicaoCollectionNewInstituicaoToAttach = em.getReference(instituicaoCollectionNewInstituicaoToAttach.getClass(), instituicaoCollectionNewInstituicaoToAttach.getId());
                attachedInstituicaoCollectionNew.add(instituicaoCollectionNewInstituicaoToAttach);
            }
            instituicaoCollectionNew = attachedInstituicaoCollectionNew;
            estado.setInstituicaoCollection(instituicaoCollectionNew);
            estado = em.merge(estado);
            if (paisFkOld != null && !paisFkOld.equals(paisFkNew)) {
                paisFkOld.getEstadoCollection().remove(estado);
                paisFkOld = em.merge(paisFkOld);
            }
            if (paisFkNew != null && !paisFkNew.equals(paisFkOld)) {
                paisFkNew.getEstadoCollection().add(estado);
                paisFkNew = em.merge(paisFkNew);
            }
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico : pessoaFisicaHistoricoCollectionOld) {
                if (!pessoaFisicaHistoricoCollectionNew.contains(pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico)) {
                    pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico.setRgUfFk(null);
                    pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico = em.merge(pessoaFisicaHistoricoCollectionOldPessoaFisicaHistorico);
                }
            }
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico : pessoaFisicaHistoricoCollectionNew) {
                if (!pessoaFisicaHistoricoCollectionOld.contains(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico)) {
                    Estado oldRgUfFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico = pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico.getRgUfFk();
                    pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico.setRgUfFk(estado);
                    pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico = em.merge(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico);
                    if (oldRgUfFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico != null && !oldRgUfFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico.equals(estado)) {
                        oldRgUfFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico);
                        oldRgUfFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico = em.merge(oldRgUfFkOfPessoaFisicaHistoricoCollectionNewPessoaFisicaHistorico);
                    }
                }
            }
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollection1OldPessoaFisicaHistorico : pessoaFisicaHistoricoCollection1Old) {
                if (!pessoaFisicaHistoricoCollection1New.contains(pessoaFisicaHistoricoCollection1OldPessoaFisicaHistorico)) {
                    pessoaFisicaHistoricoCollection1OldPessoaFisicaHistorico.setEstadoFk(null);
                    pessoaFisicaHistoricoCollection1OldPessoaFisicaHistorico = em.merge(pessoaFisicaHistoricoCollection1OldPessoaFisicaHistorico);
                }
            }
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollection1NewPessoaFisicaHistorico : pessoaFisicaHistoricoCollection1New) {
                if (!pessoaFisicaHistoricoCollection1Old.contains(pessoaFisicaHistoricoCollection1NewPessoaFisicaHistorico)) {
                    Estado oldEstadoFkOfPessoaFisicaHistoricoCollection1NewPessoaFisicaHistorico = pessoaFisicaHistoricoCollection1NewPessoaFisicaHistorico.getEstadoFk();
                    pessoaFisicaHistoricoCollection1NewPessoaFisicaHistorico.setEstadoFk(estado);
                    pessoaFisicaHistoricoCollection1NewPessoaFisicaHistorico = em.merge(pessoaFisicaHistoricoCollection1NewPessoaFisicaHistorico);
                    if (oldEstadoFkOfPessoaFisicaHistoricoCollection1NewPessoaFisicaHistorico != null && !oldEstadoFkOfPessoaFisicaHistoricoCollection1NewPessoaFisicaHistorico.equals(estado)) {
                        oldEstadoFkOfPessoaFisicaHistoricoCollection1NewPessoaFisicaHistorico.getPessoaFisicaHistoricoCollection1().remove(pessoaFisicaHistoricoCollection1NewPessoaFisicaHistorico);
                        oldEstadoFkOfPessoaFisicaHistoricoCollection1NewPessoaFisicaHistorico = em.merge(oldEstadoFkOfPessoaFisicaHistoricoCollection1NewPessoaFisicaHistorico);
                    }
                }
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
            for (PessoaFisica pessoaFisicaCollectionOldPessoaFisica : pessoaFisicaCollectionOld) {
                if (!pessoaFisicaCollectionNew.contains(pessoaFisicaCollectionOldPessoaFisica)) {
                    pessoaFisicaCollectionOldPessoaFisica.setEstadoFk(null);
                    pessoaFisicaCollectionOldPessoaFisica = em.merge(pessoaFisicaCollectionOldPessoaFisica);
                }
            }
            for (PessoaFisica pessoaFisicaCollectionNewPessoaFisica : pessoaFisicaCollectionNew) {
                if (!pessoaFisicaCollectionOld.contains(pessoaFisicaCollectionNewPessoaFisica)) {
                    Estado oldEstadoFkOfPessoaFisicaCollectionNewPessoaFisica = pessoaFisicaCollectionNewPessoaFisica.getEstadoFk();
                    pessoaFisicaCollectionNewPessoaFisica.setEstadoFk(estado);
                    pessoaFisicaCollectionNewPessoaFisica = em.merge(pessoaFisicaCollectionNewPessoaFisica);
                    if (oldEstadoFkOfPessoaFisicaCollectionNewPessoaFisica != null && !oldEstadoFkOfPessoaFisicaCollectionNewPessoaFisica.equals(estado)) {
                        oldEstadoFkOfPessoaFisicaCollectionNewPessoaFisica.getPessoaFisicaCollection().remove(pessoaFisicaCollectionNewPessoaFisica);
                        oldEstadoFkOfPessoaFisicaCollectionNewPessoaFisica = em.merge(oldEstadoFkOfPessoaFisicaCollectionNewPessoaFisica);
                    }
                }
            }
            for (PessoaFisica pessoaFisicaCollection1OldPessoaFisica : pessoaFisicaCollection1Old) {
                if (!pessoaFisicaCollection1New.contains(pessoaFisicaCollection1OldPessoaFisica)) {
                    pessoaFisicaCollection1OldPessoaFisica.setRgUfFk(null);
                    pessoaFisicaCollection1OldPessoaFisica = em.merge(pessoaFisicaCollection1OldPessoaFisica);
                }
            }
            for (PessoaFisica pessoaFisicaCollection1NewPessoaFisica : pessoaFisicaCollection1New) {
                if (!pessoaFisicaCollection1Old.contains(pessoaFisicaCollection1NewPessoaFisica)) {
                    Estado oldRgUfFkOfPessoaFisicaCollection1NewPessoaFisica = pessoaFisicaCollection1NewPessoaFisica.getRgUfFk();
                    pessoaFisicaCollection1NewPessoaFisica.setRgUfFk(estado);
                    pessoaFisicaCollection1NewPessoaFisica = em.merge(pessoaFisicaCollection1NewPessoaFisica);
                    if (oldRgUfFkOfPessoaFisicaCollection1NewPessoaFisica != null && !oldRgUfFkOfPessoaFisicaCollection1NewPessoaFisica.equals(estado)) {
                        oldRgUfFkOfPessoaFisicaCollection1NewPessoaFisica.getPessoaFisicaCollection1().remove(pessoaFisicaCollection1NewPessoaFisica);
                        oldRgUfFkOfPessoaFisicaCollection1NewPessoaFisica = em.merge(oldRgUfFkOfPessoaFisicaCollection1NewPessoaFisica);
                    }
                }
            }
            for (EnderecoHistorico enderecoHistoricoCollectionOldEnderecoHistorico : enderecoHistoricoCollectionOld) {
                if (!enderecoHistoricoCollectionNew.contains(enderecoHistoricoCollectionOldEnderecoHistorico)) {
                    enderecoHistoricoCollectionOldEnderecoHistorico.setEstadoFk(null);
                    enderecoHistoricoCollectionOldEnderecoHistorico = em.merge(enderecoHistoricoCollectionOldEnderecoHistorico);
                }
            }
            for (EnderecoHistorico enderecoHistoricoCollectionNewEnderecoHistorico : enderecoHistoricoCollectionNew) {
                if (!enderecoHistoricoCollectionOld.contains(enderecoHistoricoCollectionNewEnderecoHistorico)) {
                    Estado oldEstadoFkOfEnderecoHistoricoCollectionNewEnderecoHistorico = enderecoHistoricoCollectionNewEnderecoHistorico.getEstadoFk();
                    enderecoHistoricoCollectionNewEnderecoHistorico.setEstadoFk(estado);
                    enderecoHistoricoCollectionNewEnderecoHistorico = em.merge(enderecoHistoricoCollectionNewEnderecoHistorico);
                    if (oldEstadoFkOfEnderecoHistoricoCollectionNewEnderecoHistorico != null && !oldEstadoFkOfEnderecoHistoricoCollectionNewEnderecoHistorico.equals(estado)) {
                        oldEstadoFkOfEnderecoHistoricoCollectionNewEnderecoHistorico.getEnderecoHistoricoCollection().remove(enderecoHistoricoCollectionNewEnderecoHistorico);
                        oldEstadoFkOfEnderecoHistoricoCollectionNewEnderecoHistorico = em.merge(oldEstadoFkOfEnderecoHistoricoCollectionNewEnderecoHistorico);
                    }
                }
            }
            for (Procurador procuradorCollectionNewProcurador : procuradorCollectionNew) {
                if (!procuradorCollectionOld.contains(procuradorCollectionNewProcurador)) {
                    Estado oldEstadoFkOfProcuradorCollectionNewProcurador = procuradorCollectionNewProcurador.getEstadoFk();
                    procuradorCollectionNewProcurador.setEstadoFk(estado);
                    procuradorCollectionNewProcurador = em.merge(procuradorCollectionNewProcurador);
                    if (oldEstadoFkOfProcuradorCollectionNewProcurador != null && !oldEstadoFkOfProcuradorCollectionNewProcurador.equals(estado)) {
                        oldEstadoFkOfProcuradorCollectionNewProcurador.getProcuradorCollection().remove(procuradorCollectionNewProcurador);
                        oldEstadoFkOfProcuradorCollectionNewProcurador = em.merge(oldEstadoFkOfProcuradorCollectionNewProcurador);
                    }
                }
            }
            for (Instituicao instituicaoCollectionOldInstituicao : instituicaoCollectionOld) {
                if (!instituicaoCollectionNew.contains(instituicaoCollectionOldInstituicao)) {
                    instituicaoCollectionOldInstituicao.setEstadoFk(null);
                    instituicaoCollectionOldInstituicao = em.merge(instituicaoCollectionOldInstituicao);
                }
            }
            for (Instituicao instituicaoCollectionNewInstituicao : instituicaoCollectionNew) {
                if (!instituicaoCollectionOld.contains(instituicaoCollectionNewInstituicao)) {
                    Estado oldEstadoFkOfInstituicaoCollectionNewInstituicao = instituicaoCollectionNewInstituicao.getEstadoFk();
                    instituicaoCollectionNewInstituicao.setEstadoFk(estado);
                    instituicaoCollectionNewInstituicao = em.merge(instituicaoCollectionNewInstituicao);
                    if (oldEstadoFkOfInstituicaoCollectionNewInstituicao != null && !oldEstadoFkOfInstituicaoCollectionNewInstituicao.equals(estado)) {
                        oldEstadoFkOfInstituicaoCollectionNewInstituicao.getInstituicaoCollection().remove(instituicaoCollectionNewInstituicao);
                        oldEstadoFkOfInstituicaoCollectionNewInstituicao = em.merge(oldEstadoFkOfInstituicaoCollectionNewInstituicao);
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
            Collection<Procurador> procuradorCollectionOrphanCheck = estado.getProcuradorCollection();
            for (Procurador procuradorCollectionOrphanCheckProcurador : procuradorCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estado (" + estado + ") cannot be destroyed since the Procurador " + procuradorCollectionOrphanCheckProcurador + " in its procuradorCollection field has a non-nullable estadoFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pais paisFk = estado.getPaisFk();
            if (paisFk != null) {
                paisFk.getEstadoCollection().remove(estado);
                paisFk = em.merge(paisFk);
            }
            Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollection = estado.getPessoaFisicaHistoricoCollection();
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollectionPessoaFisicaHistorico : pessoaFisicaHistoricoCollection) {
                pessoaFisicaHistoricoCollectionPessoaFisicaHistorico.setRgUfFk(null);
                pessoaFisicaHistoricoCollectionPessoaFisicaHistorico = em.merge(pessoaFisicaHistoricoCollectionPessoaFisicaHistorico);
            }
            Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollection1 = estado.getPessoaFisicaHistoricoCollection1();
            for (PessoaFisicaHistorico pessoaFisicaHistoricoCollection1PessoaFisicaHistorico : pessoaFisicaHistoricoCollection1) {
                pessoaFisicaHistoricoCollection1PessoaFisicaHistorico.setEstadoFk(null);
                pessoaFisicaHistoricoCollection1PessoaFisicaHistorico = em.merge(pessoaFisicaHistoricoCollection1PessoaFisicaHistorico);
            }
            Collection<Endereco> enderecoCollection = estado.getEnderecoCollection();
            for (Endereco enderecoCollectionEndereco : enderecoCollection) {
                enderecoCollectionEndereco.setEstadoFk(null);
                enderecoCollectionEndereco = em.merge(enderecoCollectionEndereco);
            }
            Collection<PessoaFisica> pessoaFisicaCollection = estado.getPessoaFisicaCollection();
            for (PessoaFisica pessoaFisicaCollectionPessoaFisica : pessoaFisicaCollection) {
                pessoaFisicaCollectionPessoaFisica.setEstadoFk(null);
                pessoaFisicaCollectionPessoaFisica = em.merge(pessoaFisicaCollectionPessoaFisica);
            }
            Collection<PessoaFisica> pessoaFisicaCollection1 = estado.getPessoaFisicaCollection1();
            for (PessoaFisica pessoaFisicaCollection1PessoaFisica : pessoaFisicaCollection1) {
                pessoaFisicaCollection1PessoaFisica.setRgUfFk(null);
                pessoaFisicaCollection1PessoaFisica = em.merge(pessoaFisicaCollection1PessoaFisica);
            }
            Collection<EnderecoHistorico> enderecoHistoricoCollection = estado.getEnderecoHistoricoCollection();
            for (EnderecoHistorico enderecoHistoricoCollectionEnderecoHistorico : enderecoHistoricoCollection) {
                enderecoHistoricoCollectionEnderecoHistorico.setEstadoFk(null);
                enderecoHistoricoCollectionEnderecoHistorico = em.merge(enderecoHistoricoCollectionEnderecoHistorico);
            }
            Collection<Instituicao> instituicaoCollection = estado.getInstituicaoCollection();
            for (Instituicao instituicaoCollectionInstituicao : instituicaoCollection) {
                instituicaoCollectionInstituicao.setEstadoFk(null);
                instituicaoCollectionInstituicao = em.merge(instituicaoCollectionInstituicao);
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
