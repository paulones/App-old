/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.AquisicaoBem;
import entidade.Bem;
import entidade.Citacao;
import entidade.Instituicao;
import entidade.Penhora;
import entidade.ProcessoJudicial;
import entidade.ProcessoJudicialHistorico;
import entidade.Procurador;
import entidade.Redirecionamento;
import entidade.Situacao;
import entidade.TipoRecurso;
import entidade.Usuario;
import entidade.VinculoProcessual;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class ProcessoJudicialDAO implements Serializable {

    public ProcessoJudicialDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProcessoJudicial processoJudicial) throws RollbackFailureException, Exception {
        if (processoJudicial.getVinculoProcessualCollection() == null) {
            processoJudicial.setVinculoProcessualCollection(new ArrayList<VinculoProcessual>());
        }
        if (processoJudicial.getProcessoJudicialHistoricoCollection() == null) {
            processoJudicial.setProcessoJudicialHistoricoCollection(new ArrayList<ProcessoJudicialHistorico>());
        }
        if (processoJudicial.getCitacaoCollection() == null) {
            processoJudicial.setCitacaoCollection(new ArrayList<Citacao>());
        }
        if (processoJudicial.getRedirecionamentoCollection() == null) {
            processoJudicial.setRedirecionamentoCollection(new ArrayList<Redirecionamento>());
        }
        if (processoJudicial.getPenhoraCollection() == null) {
            processoJudicial.setPenhoraCollection(new ArrayList<Penhora>());
        }
        if (processoJudicial.getAquisicaoBemCollection() == null) {
            processoJudicial.setAquisicaoBemCollection(new ArrayList<AquisicaoBem>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            TipoRecurso tipoDeRecursoFk = processoJudicial.getTipoDeRecursoFk();
            if (tipoDeRecursoFk != null) {
                tipoDeRecursoFk = em.getReference(tipoDeRecursoFk.getClass(), tipoDeRecursoFk.getId());
                processoJudicial.setTipoDeRecursoFk(tipoDeRecursoFk);
            }
            Usuario usuarioFk = processoJudicial.getUsuarioFk();
            if (usuarioFk != null) {
                usuarioFk = em.getReference(usuarioFk.getClass(), usuarioFk.getId());
                processoJudicial.setUsuarioFk(usuarioFk);
            }
            Situacao situacaoFk = processoJudicial.getSituacaoFk();
            if (situacaoFk != null) {
                situacaoFk = em.getReference(situacaoFk.getClass(), situacaoFk.getId());
                processoJudicial.setSituacaoFk(situacaoFk);
            }
            Procurador procuradorFk = processoJudicial.getProcuradorFk();
            if (procuradorFk != null) {
                procuradorFk = em.getReference(procuradorFk.getClass(), procuradorFk.getId());
                processoJudicial.setProcuradorFk(procuradorFk);
            }
            Instituicao instituicaoFk = processoJudicial.getInstituicaoFk();
            if (instituicaoFk != null) {
                instituicaoFk = em.getReference(instituicaoFk.getClass(), instituicaoFk.getId());
                processoJudicial.setInstituicaoFk(instituicaoFk);
            }
            Collection<VinculoProcessual> attachedVinculoProcessualCollection = new ArrayList<VinculoProcessual>();
            for (VinculoProcessual vinculoProcessualCollectionVinculoProcessualToAttach : processoJudicial.getVinculoProcessualCollection()) {
                vinculoProcessualCollectionVinculoProcessualToAttach = em.getReference(vinculoProcessualCollectionVinculoProcessualToAttach.getClass(), vinculoProcessualCollectionVinculoProcessualToAttach.getId());
                attachedVinculoProcessualCollection.add(vinculoProcessualCollectionVinculoProcessualToAttach);
            }
            processoJudicial.setVinculoProcessualCollection(attachedVinculoProcessualCollection);
            Collection<ProcessoJudicialHistorico> attachedProcessoJudicialHistoricoCollection = new ArrayList<ProcessoJudicialHistorico>();
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach : processoJudicial.getProcessoJudicialHistoricoCollection()) {
                processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach = em.getReference(processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach.getClass(), processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach.getId());
                attachedProcessoJudicialHistoricoCollection.add(processoJudicialHistoricoCollectionProcessoJudicialHistoricoToAttach);
            }
            processoJudicial.setProcessoJudicialHistoricoCollection(attachedProcessoJudicialHistoricoCollection);
            Collection<Citacao> attachedCitacaoCollection = new ArrayList<Citacao>();
            for (Citacao citacaoCollectionCitacaoToAttach : processoJudicial.getCitacaoCollection()) {
                citacaoCollectionCitacaoToAttach = em.getReference(citacaoCollectionCitacaoToAttach.getClass(), citacaoCollectionCitacaoToAttach.getId());
                attachedCitacaoCollection.add(citacaoCollectionCitacaoToAttach);
            }
            processoJudicial.setCitacaoCollection(attachedCitacaoCollection);
            Collection<Redirecionamento> attachedRedirecionamentoCollection = new ArrayList<Redirecionamento>();
            for (Redirecionamento redirecionamentoCollectionRedirecionamentoToAttach : processoJudicial.getRedirecionamentoCollection()) {
                redirecionamentoCollectionRedirecionamentoToAttach = em.getReference(redirecionamentoCollectionRedirecionamentoToAttach.getClass(), redirecionamentoCollectionRedirecionamentoToAttach.getId());
                attachedRedirecionamentoCollection.add(redirecionamentoCollectionRedirecionamentoToAttach);
            }
            processoJudicial.setRedirecionamentoCollection(attachedRedirecionamentoCollection);
            Collection<Penhora> attachedPenhoraCollection = new ArrayList<Penhora>();
            for (Penhora penhoraCollectionPenhoraToAttach : processoJudicial.getPenhoraCollection()) {
                penhoraCollectionPenhoraToAttach = em.getReference(penhoraCollectionPenhoraToAttach.getClass(), penhoraCollectionPenhoraToAttach.getId());
                attachedPenhoraCollection.add(penhoraCollectionPenhoraToAttach);
            }
            processoJudicial.setPenhoraCollection(attachedPenhoraCollection);
            Collection<AquisicaoBem> attachedAquisicaoBemCollection = new ArrayList<AquisicaoBem>();
            for (AquisicaoBem aquisicaoBemCollectionAquisicaoBemToAttach : processoJudicial.getAquisicaoBemCollection()) {
                aquisicaoBemCollectionAquisicaoBemToAttach = em.getReference(aquisicaoBemCollectionAquisicaoBemToAttach.getClass(), aquisicaoBemCollectionAquisicaoBemToAttach.getId());
                attachedAquisicaoBemCollection.add(aquisicaoBemCollectionAquisicaoBemToAttach);
            }
            processoJudicial.setAquisicaoBemCollection(attachedAquisicaoBemCollection);
            em.persist(processoJudicial);
            if (tipoDeRecursoFk != null) {
                tipoDeRecursoFk.getProcessoJudicialCollection().add(processoJudicial);
                tipoDeRecursoFk = em.merge(tipoDeRecursoFk);
            }
            if (usuarioFk != null) {
                usuarioFk.getProcessoJudicialCollection().add(processoJudicial);
                usuarioFk = em.merge(usuarioFk);
            }
            if (situacaoFk != null) {
                situacaoFk.getProcessoJudicialCollection().add(processoJudicial);
                situacaoFk = em.merge(situacaoFk);
            }
            if (procuradorFk != null) {
                procuradorFk.getProcessoJudicialCollection().add(processoJudicial);
                procuradorFk = em.merge(procuradorFk);
            }
            if (instituicaoFk != null) {
                instituicaoFk.getProcessoJudicialCollection().add(processoJudicial);
                instituicaoFk = em.merge(instituicaoFk);
            }
            for (VinculoProcessual vinculoProcessualCollectionVinculoProcessual : processoJudicial.getVinculoProcessualCollection()) {
                ProcessoJudicial oldProcessoJudicialFkOfVinculoProcessualCollectionVinculoProcessual = vinculoProcessualCollectionVinculoProcessual.getProcessoJudicialFk();
                vinculoProcessualCollectionVinculoProcessual.setProcessoJudicialFk(processoJudicial);
                vinculoProcessualCollectionVinculoProcessual = em.merge(vinculoProcessualCollectionVinculoProcessual);
                if (oldProcessoJudicialFkOfVinculoProcessualCollectionVinculoProcessual != null) {
                    oldProcessoJudicialFkOfVinculoProcessualCollectionVinculoProcessual.getVinculoProcessualCollection().remove(vinculoProcessualCollectionVinculoProcessual);
                    oldProcessoJudicialFkOfVinculoProcessualCollectionVinculoProcessual = em.merge(oldProcessoJudicialFkOfVinculoProcessualCollectionVinculoProcessual);
                }
            }
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionProcessoJudicialHistorico : processoJudicial.getProcessoJudicialHistoricoCollection()) {
                ProcessoJudicial oldProcessoJudicialFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico = processoJudicialHistoricoCollectionProcessoJudicialHistorico.getProcessoJudicialFk();
                processoJudicialHistoricoCollectionProcessoJudicialHistorico.setProcessoJudicialFk(processoJudicial);
                processoJudicialHistoricoCollectionProcessoJudicialHistorico = em.merge(processoJudicialHistoricoCollectionProcessoJudicialHistorico);
                if (oldProcessoJudicialFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico != null) {
                    oldProcessoJudicialFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico.getProcessoJudicialHistoricoCollection().remove(processoJudicialHistoricoCollectionProcessoJudicialHistorico);
                    oldProcessoJudicialFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico = em.merge(oldProcessoJudicialFkOfProcessoJudicialHistoricoCollectionProcessoJudicialHistorico);
                }
            }
            for (Citacao citacaoCollectionCitacao : processoJudicial.getCitacaoCollection()) {
                ProcessoJudicial oldProcessoJudicialFkOfCitacaoCollectionCitacao = citacaoCollectionCitacao.getProcessoJudicialFk();
                citacaoCollectionCitacao.setProcessoJudicialFk(processoJudicial);
                citacaoCollectionCitacao = em.merge(citacaoCollectionCitacao);
                if (oldProcessoJudicialFkOfCitacaoCollectionCitacao != null) {
                    oldProcessoJudicialFkOfCitacaoCollectionCitacao.getCitacaoCollection().remove(citacaoCollectionCitacao);
                    oldProcessoJudicialFkOfCitacaoCollectionCitacao = em.merge(oldProcessoJudicialFkOfCitacaoCollectionCitacao);
                }
            }
            for (Redirecionamento redirecionamentoCollectionRedirecionamento : processoJudicial.getRedirecionamentoCollection()) {
                ProcessoJudicial oldProcessoJudicialFkOfRedirecionamentoCollectionRedirecionamento = redirecionamentoCollectionRedirecionamento.getProcessoJudicialFk();
                redirecionamentoCollectionRedirecionamento.setProcessoJudicialFk(processoJudicial);
                redirecionamentoCollectionRedirecionamento = em.merge(redirecionamentoCollectionRedirecionamento);
                if (oldProcessoJudicialFkOfRedirecionamentoCollectionRedirecionamento != null) {
                    oldProcessoJudicialFkOfRedirecionamentoCollectionRedirecionamento.getRedirecionamentoCollection().remove(redirecionamentoCollectionRedirecionamento);
                    oldProcessoJudicialFkOfRedirecionamentoCollectionRedirecionamento = em.merge(oldProcessoJudicialFkOfRedirecionamentoCollectionRedirecionamento);
                }
            }
            for (Penhora penhoraCollectionPenhora : processoJudicial.getPenhoraCollection()) {
                ProcessoJudicial oldProcessoJudicialFkOfPenhoraCollectionPenhora = penhoraCollectionPenhora.getProcessoJudicialFk();
                penhoraCollectionPenhora.setProcessoJudicialFk(processoJudicial);
                penhoraCollectionPenhora = em.merge(penhoraCollectionPenhora);
                if (oldProcessoJudicialFkOfPenhoraCollectionPenhora != null) {
                    oldProcessoJudicialFkOfPenhoraCollectionPenhora.getPenhoraCollection().remove(penhoraCollectionPenhora);
                    oldProcessoJudicialFkOfPenhoraCollectionPenhora = em.merge(oldProcessoJudicialFkOfPenhoraCollectionPenhora);
                }
            }
            for (AquisicaoBem aquisicaoBemCollectionAquisicaoBem : processoJudicial.getAquisicaoBemCollection()) {
                ProcessoJudicial oldProcessoJudicialFkOfAquisicaoBemCollectionAquisicaoBem = aquisicaoBemCollectionAquisicaoBem.getProcessoJudicialFk();
                aquisicaoBemCollectionAquisicaoBem.setProcessoJudicialFk(processoJudicial);
                aquisicaoBemCollectionAquisicaoBem = em.merge(aquisicaoBemCollectionAquisicaoBem);
                if (oldProcessoJudicialFkOfAquisicaoBemCollectionAquisicaoBem != null) {
                    oldProcessoJudicialFkOfAquisicaoBemCollectionAquisicaoBem.getAquisicaoBemCollection().remove(aquisicaoBemCollectionAquisicaoBem);
                    oldProcessoJudicialFkOfAquisicaoBemCollectionAquisicaoBem = em.merge(oldProcessoJudicialFkOfAquisicaoBemCollectionAquisicaoBem);
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

    public void edit(ProcessoJudicial processoJudicial) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            ProcessoJudicial persistentProcessoJudicial = em.find(ProcessoJudicial.class, processoJudicial.getId());
            TipoRecurso tipoDeRecursoFkOld = persistentProcessoJudicial.getTipoDeRecursoFk();
            TipoRecurso tipoDeRecursoFkNew = processoJudicial.getTipoDeRecursoFk();
            Usuario usuarioFkOld = persistentProcessoJudicial.getUsuarioFk();
            Usuario usuarioFkNew = processoJudicial.getUsuarioFk();
            Situacao situacaoFkOld = persistentProcessoJudicial.getSituacaoFk();
            Situacao situacaoFkNew = processoJudicial.getSituacaoFk();
            Procurador procuradorFkOld = persistentProcessoJudicial.getProcuradorFk();
            Procurador procuradorFkNew = processoJudicial.getProcuradorFk();
            Instituicao instituicaoFkOld = persistentProcessoJudicial.getInstituicaoFk();
            Instituicao instituicaoFkNew = processoJudicial.getInstituicaoFk();
            Collection<VinculoProcessual> vinculoProcessualCollectionOld = persistentProcessoJudicial.getVinculoProcessualCollection();
            Collection<VinculoProcessual> vinculoProcessualCollectionNew = processoJudicial.getVinculoProcessualCollection();
            Collection<ProcessoJudicialHistorico> processoJudicialHistoricoCollectionOld = persistentProcessoJudicial.getProcessoJudicialHistoricoCollection();
            Collection<ProcessoJudicialHistorico> processoJudicialHistoricoCollectionNew = processoJudicial.getProcessoJudicialHistoricoCollection();
            Collection<Citacao> citacaoCollectionOld = persistentProcessoJudicial.getCitacaoCollection();
            Collection<Citacao> citacaoCollectionNew = processoJudicial.getCitacaoCollection();
            Collection<Redirecionamento> redirecionamentoCollectionOld = persistentProcessoJudicial.getRedirecionamentoCollection();
            Collection<Redirecionamento> redirecionamentoCollectionNew = processoJudicial.getRedirecionamentoCollection();
            Collection<Penhora> penhoraCollectionOld = persistentProcessoJudicial.getPenhoraCollection();
            Collection<Penhora> penhoraCollectionNew = processoJudicial.getPenhoraCollection();
            Collection<AquisicaoBem> aquisicaoBemCollectionOld = persistentProcessoJudicial.getAquisicaoBemCollection();
            Collection<AquisicaoBem> aquisicaoBemCollectionNew = processoJudicial.getAquisicaoBemCollection();
            List<String> illegalOrphanMessages = null;
            for (VinculoProcessual vinculoProcessualCollectionOldVinculoProcessual : vinculoProcessualCollectionOld) {
                if (!vinculoProcessualCollectionNew.contains(vinculoProcessualCollectionOldVinculoProcessual)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain VinculoProcessual " + vinculoProcessualCollectionOldVinculoProcessual + " since its processoJudicialFk field is not nullable.");
                }
            }
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionOldProcessoJudicialHistorico : processoJudicialHistoricoCollectionOld) {
                if (!processoJudicialHistoricoCollectionNew.contains(processoJudicialHistoricoCollectionOldProcessoJudicialHistorico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProcessoJudicialHistorico " + processoJudicialHistoricoCollectionOldProcessoJudicialHistorico + " since its processoJudicialFk field is not nullable.");
                }
            }
            for (Citacao citacaoCollectionOldCitacao : citacaoCollectionOld) {
                if (!citacaoCollectionNew.contains(citacaoCollectionOldCitacao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Citacao " + citacaoCollectionOldCitacao + " since its processoJudicialFk field is not nullable.");
                }
            }
            for (Redirecionamento redirecionamentoCollectionOldRedirecionamento : redirecionamentoCollectionOld) {
                if (!redirecionamentoCollectionNew.contains(redirecionamentoCollectionOldRedirecionamento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Redirecionamento " + redirecionamentoCollectionOldRedirecionamento + " since its processoJudicialFk field is not nullable.");
                }
            }
            for (Penhora penhoraCollectionOldPenhora : penhoraCollectionOld) {
                if (!penhoraCollectionNew.contains(penhoraCollectionOldPenhora)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Penhora " + penhoraCollectionOldPenhora + " since its processoJudicialFk field is not nullable.");
                }
            }
            for (AquisicaoBem aquisicaoBemCollectionOldAquisicaoBem : aquisicaoBemCollectionOld) {
                if (!aquisicaoBemCollectionNew.contains(aquisicaoBemCollectionOldAquisicaoBem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AquisicaoBem " + aquisicaoBemCollectionOldAquisicaoBem + " since its processoJudicialFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tipoDeRecursoFkNew != null) {
                tipoDeRecursoFkNew = em.getReference(tipoDeRecursoFkNew.getClass(), tipoDeRecursoFkNew.getId());
                processoJudicial.setTipoDeRecursoFk(tipoDeRecursoFkNew);
            }
            if (usuarioFkNew != null) {
                usuarioFkNew = em.getReference(usuarioFkNew.getClass(), usuarioFkNew.getId());
                processoJudicial.setUsuarioFk(usuarioFkNew);
            }
            if (situacaoFkNew != null) {
                situacaoFkNew = em.getReference(situacaoFkNew.getClass(), situacaoFkNew.getId());
                processoJudicial.setSituacaoFk(situacaoFkNew);
            }
            if (procuradorFkNew != null) {
                procuradorFkNew = em.getReference(procuradorFkNew.getClass(), procuradorFkNew.getId());
                processoJudicial.setProcuradorFk(procuradorFkNew);
            }
            if (instituicaoFkNew != null) {
                instituicaoFkNew = em.getReference(instituicaoFkNew.getClass(), instituicaoFkNew.getId());
                processoJudicial.setInstituicaoFk(instituicaoFkNew);
            }
            Collection<VinculoProcessual> attachedVinculoProcessualCollectionNew = new ArrayList<VinculoProcessual>();
            for (VinculoProcessual vinculoProcessualCollectionNewVinculoProcessualToAttach : vinculoProcessualCollectionNew) {
                vinculoProcessualCollectionNewVinculoProcessualToAttach = em.getReference(vinculoProcessualCollectionNewVinculoProcessualToAttach.getClass(), vinculoProcessualCollectionNewVinculoProcessualToAttach.getId());
                attachedVinculoProcessualCollectionNew.add(vinculoProcessualCollectionNewVinculoProcessualToAttach);
            }
            vinculoProcessualCollectionNew = attachedVinculoProcessualCollectionNew;
            processoJudicial.setVinculoProcessualCollection(vinculoProcessualCollectionNew);
            Collection<ProcessoJudicialHistorico> attachedProcessoJudicialHistoricoCollectionNew = new ArrayList<ProcessoJudicialHistorico>();
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach : processoJudicialHistoricoCollectionNew) {
                processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach = em.getReference(processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach.getClass(), processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach.getId());
                attachedProcessoJudicialHistoricoCollectionNew.add(processoJudicialHistoricoCollectionNewProcessoJudicialHistoricoToAttach);
            }
            processoJudicialHistoricoCollectionNew = attachedProcessoJudicialHistoricoCollectionNew;
            processoJudicial.setProcessoJudicialHistoricoCollection(processoJudicialHistoricoCollectionNew);
            Collection<Citacao> attachedCitacaoCollectionNew = new ArrayList<Citacao>();
            for (Citacao citacaoCollectionNewCitacaoToAttach : citacaoCollectionNew) {
                citacaoCollectionNewCitacaoToAttach = em.getReference(citacaoCollectionNewCitacaoToAttach.getClass(), citacaoCollectionNewCitacaoToAttach.getId());
                attachedCitacaoCollectionNew.add(citacaoCollectionNewCitacaoToAttach);
            }
            citacaoCollectionNew = attachedCitacaoCollectionNew;
            processoJudicial.setCitacaoCollection(citacaoCollectionNew);
            Collection<Redirecionamento> attachedRedirecionamentoCollectionNew = new ArrayList<Redirecionamento>();
            for (Redirecionamento redirecionamentoCollectionNewRedirecionamentoToAttach : redirecionamentoCollectionNew) {
                redirecionamentoCollectionNewRedirecionamentoToAttach = em.getReference(redirecionamentoCollectionNewRedirecionamentoToAttach.getClass(), redirecionamentoCollectionNewRedirecionamentoToAttach.getId());
                attachedRedirecionamentoCollectionNew.add(redirecionamentoCollectionNewRedirecionamentoToAttach);
            }
            redirecionamentoCollectionNew = attachedRedirecionamentoCollectionNew;
            processoJudicial.setRedirecionamentoCollection(redirecionamentoCollectionNew);
            Collection<Penhora> attachedPenhoraCollectionNew = new ArrayList<Penhora>();
            for (Penhora penhoraCollectionNewPenhoraToAttach : penhoraCollectionNew) {
                penhoraCollectionNewPenhoraToAttach = em.getReference(penhoraCollectionNewPenhoraToAttach.getClass(), penhoraCollectionNewPenhoraToAttach.getId());
                attachedPenhoraCollectionNew.add(penhoraCollectionNewPenhoraToAttach);
            }
            penhoraCollectionNew = attachedPenhoraCollectionNew;
            processoJudicial.setPenhoraCollection(penhoraCollectionNew);
            Collection<AquisicaoBem> attachedAquisicaoBemCollectionNew = new ArrayList<AquisicaoBem>();
            for (AquisicaoBem aquisicaoBemCollectionNewAquisicaoBemToAttach : aquisicaoBemCollectionNew) {
                aquisicaoBemCollectionNewAquisicaoBemToAttach = em.getReference(aquisicaoBemCollectionNewAquisicaoBemToAttach.getClass(), aquisicaoBemCollectionNewAquisicaoBemToAttach.getId());
                attachedAquisicaoBemCollectionNew.add(aquisicaoBemCollectionNewAquisicaoBemToAttach);
            }
            aquisicaoBemCollectionNew = attachedAquisicaoBemCollectionNew;
            processoJudicial.setAquisicaoBemCollection(aquisicaoBemCollectionNew);
            processoJudicial = em.merge(processoJudicial);
            if (tipoDeRecursoFkOld != null && !tipoDeRecursoFkOld.equals(tipoDeRecursoFkNew)) {
                tipoDeRecursoFkOld.getProcessoJudicialCollection().remove(processoJudicial);
                tipoDeRecursoFkOld = em.merge(tipoDeRecursoFkOld);
            }
            if (tipoDeRecursoFkNew != null && !tipoDeRecursoFkNew.equals(tipoDeRecursoFkOld)) {
                tipoDeRecursoFkNew.getProcessoJudicialCollection().add(processoJudicial);
                tipoDeRecursoFkNew = em.merge(tipoDeRecursoFkNew);
            }
            if (usuarioFkOld != null && !usuarioFkOld.equals(usuarioFkNew)) {
                usuarioFkOld.getProcessoJudicialCollection().remove(processoJudicial);
                usuarioFkOld = em.merge(usuarioFkOld);
            }
            if (usuarioFkNew != null && !usuarioFkNew.equals(usuarioFkOld)) {
                usuarioFkNew.getProcessoJudicialCollection().add(processoJudicial);
                usuarioFkNew = em.merge(usuarioFkNew);
            }
            if (situacaoFkOld != null && !situacaoFkOld.equals(situacaoFkNew)) {
                situacaoFkOld.getProcessoJudicialCollection().remove(processoJudicial);
                situacaoFkOld = em.merge(situacaoFkOld);
            }
            if (situacaoFkNew != null && !situacaoFkNew.equals(situacaoFkOld)) {
                situacaoFkNew.getProcessoJudicialCollection().add(processoJudicial);
                situacaoFkNew = em.merge(situacaoFkNew);
            }
            if (procuradorFkOld != null && !procuradorFkOld.equals(procuradorFkNew)) {
                procuradorFkOld.getProcessoJudicialCollection().remove(processoJudicial);
                procuradorFkOld = em.merge(procuradorFkOld);
            }
            if (procuradorFkNew != null && !procuradorFkNew.equals(procuradorFkOld)) {
                procuradorFkNew.getProcessoJudicialCollection().add(processoJudicial);
                procuradorFkNew = em.merge(procuradorFkNew);
            }
            if (instituicaoFkOld != null && !instituicaoFkOld.equals(instituicaoFkNew)) {
                instituicaoFkOld.getProcessoJudicialCollection().remove(processoJudicial);
                instituicaoFkOld = em.merge(instituicaoFkOld);
            }
            if (instituicaoFkNew != null && !instituicaoFkNew.equals(instituicaoFkOld)) {
                instituicaoFkNew.getProcessoJudicialCollection().add(processoJudicial);
                instituicaoFkNew = em.merge(instituicaoFkNew);
            }
            for (VinculoProcessual vinculoProcessualCollectionNewVinculoProcessual : vinculoProcessualCollectionNew) {
                if (!vinculoProcessualCollectionOld.contains(vinculoProcessualCollectionNewVinculoProcessual)) {
                    ProcessoJudicial oldProcessoJudicialFkOfVinculoProcessualCollectionNewVinculoProcessual = vinculoProcessualCollectionNewVinculoProcessual.getProcessoJudicialFk();
                    vinculoProcessualCollectionNewVinculoProcessual.setProcessoJudicialFk(processoJudicial);
                    vinculoProcessualCollectionNewVinculoProcessual = em.merge(vinculoProcessualCollectionNewVinculoProcessual);
                    if (oldProcessoJudicialFkOfVinculoProcessualCollectionNewVinculoProcessual != null && !oldProcessoJudicialFkOfVinculoProcessualCollectionNewVinculoProcessual.equals(processoJudicial)) {
                        oldProcessoJudicialFkOfVinculoProcessualCollectionNewVinculoProcessual.getVinculoProcessualCollection().remove(vinculoProcessualCollectionNewVinculoProcessual);
                        oldProcessoJudicialFkOfVinculoProcessualCollectionNewVinculoProcessual = em.merge(oldProcessoJudicialFkOfVinculoProcessualCollectionNewVinculoProcessual);
                    }
                }
            }
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionNewProcessoJudicialHistorico : processoJudicialHistoricoCollectionNew) {
                if (!processoJudicialHistoricoCollectionOld.contains(processoJudicialHistoricoCollectionNewProcessoJudicialHistorico)) {
                    ProcessoJudicial oldProcessoJudicialFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico = processoJudicialHistoricoCollectionNewProcessoJudicialHistorico.getProcessoJudicialFk();
                    processoJudicialHistoricoCollectionNewProcessoJudicialHistorico.setProcessoJudicialFk(processoJudicial);
                    processoJudicialHistoricoCollectionNewProcessoJudicialHistorico = em.merge(processoJudicialHistoricoCollectionNewProcessoJudicialHistorico);
                    if (oldProcessoJudicialFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico != null && !oldProcessoJudicialFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico.equals(processoJudicial)) {
                        oldProcessoJudicialFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico.getProcessoJudicialHistoricoCollection().remove(processoJudicialHistoricoCollectionNewProcessoJudicialHistorico);
                        oldProcessoJudicialFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico = em.merge(oldProcessoJudicialFkOfProcessoJudicialHistoricoCollectionNewProcessoJudicialHistorico);
                    }
                }
            }
            for (Citacao citacaoCollectionNewCitacao : citacaoCollectionNew) {
                if (!citacaoCollectionOld.contains(citacaoCollectionNewCitacao)) {
                    ProcessoJudicial oldProcessoJudicialFkOfCitacaoCollectionNewCitacao = citacaoCollectionNewCitacao.getProcessoJudicialFk();
                    citacaoCollectionNewCitacao.setProcessoJudicialFk(processoJudicial);
                    citacaoCollectionNewCitacao = em.merge(citacaoCollectionNewCitacao);
                    if (oldProcessoJudicialFkOfCitacaoCollectionNewCitacao != null && !oldProcessoJudicialFkOfCitacaoCollectionNewCitacao.equals(processoJudicial)) {
                        oldProcessoJudicialFkOfCitacaoCollectionNewCitacao.getCitacaoCollection().remove(citacaoCollectionNewCitacao);
                        oldProcessoJudicialFkOfCitacaoCollectionNewCitacao = em.merge(oldProcessoJudicialFkOfCitacaoCollectionNewCitacao);
                    }
                }
            }
            for (Redirecionamento redirecionamentoCollectionNewRedirecionamento : redirecionamentoCollectionNew) {
                if (!redirecionamentoCollectionOld.contains(redirecionamentoCollectionNewRedirecionamento)) {
                    ProcessoJudicial oldProcessoJudicialFkOfRedirecionamentoCollectionNewRedirecionamento = redirecionamentoCollectionNewRedirecionamento.getProcessoJudicialFk();
                    redirecionamentoCollectionNewRedirecionamento.setProcessoJudicialFk(processoJudicial);
                    redirecionamentoCollectionNewRedirecionamento = em.merge(redirecionamentoCollectionNewRedirecionamento);
                    if (oldProcessoJudicialFkOfRedirecionamentoCollectionNewRedirecionamento != null && !oldProcessoJudicialFkOfRedirecionamentoCollectionNewRedirecionamento.equals(processoJudicial)) {
                        oldProcessoJudicialFkOfRedirecionamentoCollectionNewRedirecionamento.getRedirecionamentoCollection().remove(redirecionamentoCollectionNewRedirecionamento);
                        oldProcessoJudicialFkOfRedirecionamentoCollectionNewRedirecionamento = em.merge(oldProcessoJudicialFkOfRedirecionamentoCollectionNewRedirecionamento);
                    }
                }
            }
            for (Penhora penhoraCollectionNewPenhora : penhoraCollectionNew) {
                if (!penhoraCollectionOld.contains(penhoraCollectionNewPenhora)) {
                    ProcessoJudicial oldProcessoJudicialFkOfPenhoraCollectionNewPenhora = penhoraCollectionNewPenhora.getProcessoJudicialFk();
                    penhoraCollectionNewPenhora.setProcessoJudicialFk(processoJudicial);
                    penhoraCollectionNewPenhora = em.merge(penhoraCollectionNewPenhora);
                    if (oldProcessoJudicialFkOfPenhoraCollectionNewPenhora != null && !oldProcessoJudicialFkOfPenhoraCollectionNewPenhora.equals(processoJudicial)) {
                        oldProcessoJudicialFkOfPenhoraCollectionNewPenhora.getPenhoraCollection().remove(penhoraCollectionNewPenhora);
                        oldProcessoJudicialFkOfPenhoraCollectionNewPenhora = em.merge(oldProcessoJudicialFkOfPenhoraCollectionNewPenhora);
                    }
                }
            }
            for (AquisicaoBem aquisicaoBemCollectionNewAquisicaoBem : aquisicaoBemCollectionNew) {
                if (!aquisicaoBemCollectionOld.contains(aquisicaoBemCollectionNewAquisicaoBem)) {
                    ProcessoJudicial oldProcessoJudicialFkOfAquisicaoBemCollectionNewAquisicaoBem = aquisicaoBemCollectionNewAquisicaoBem.getProcessoJudicialFk();
                    aquisicaoBemCollectionNewAquisicaoBem.setProcessoJudicialFk(processoJudicial);
                    aquisicaoBemCollectionNewAquisicaoBem = em.merge(aquisicaoBemCollectionNewAquisicaoBem);
                    if (oldProcessoJudicialFkOfAquisicaoBemCollectionNewAquisicaoBem != null && !oldProcessoJudicialFkOfAquisicaoBemCollectionNewAquisicaoBem.equals(processoJudicial)) {
                        oldProcessoJudicialFkOfAquisicaoBemCollectionNewAquisicaoBem.getAquisicaoBemCollection().remove(aquisicaoBemCollectionNewAquisicaoBem);
                        oldProcessoJudicialFkOfAquisicaoBemCollectionNewAquisicaoBem = em.merge(oldProcessoJudicialFkOfAquisicaoBemCollectionNewAquisicaoBem);
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
                Integer id = processoJudicial.getId();
                if (findProcessoJudicial(id) == null) {
                    throw new NonexistentEntityException("The processoJudicial with id " + id + " no longer exists.");
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
            ProcessoJudicial processoJudicial;
            try {
                processoJudicial = em.getReference(ProcessoJudicial.class, id);
                processoJudicial.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The processoJudicial with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<VinculoProcessual> vinculoProcessualCollectionOrphanCheck = processoJudicial.getVinculoProcessualCollection();
            for (VinculoProcessual vinculoProcessualCollectionOrphanCheckVinculoProcessual : vinculoProcessualCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProcessoJudicial (" + processoJudicial + ") cannot be destroyed since the VinculoProcessual " + vinculoProcessualCollectionOrphanCheckVinculoProcessual + " in its vinculoProcessualCollection field has a non-nullable processoJudicialFk field.");
            }
            Collection<ProcessoJudicialHistorico> processoJudicialHistoricoCollectionOrphanCheck = processoJudicial.getProcessoJudicialHistoricoCollection();
            for (ProcessoJudicialHistorico processoJudicialHistoricoCollectionOrphanCheckProcessoJudicialHistorico : processoJudicialHistoricoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProcessoJudicial (" + processoJudicial + ") cannot be destroyed since the ProcessoJudicialHistorico " + processoJudicialHistoricoCollectionOrphanCheckProcessoJudicialHistorico + " in its processoJudicialHistoricoCollection field has a non-nullable processoJudicialFk field.");
            }
            Collection<Citacao> citacaoCollectionOrphanCheck = processoJudicial.getCitacaoCollection();
            for (Citacao citacaoCollectionOrphanCheckCitacao : citacaoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProcessoJudicial (" + processoJudicial + ") cannot be destroyed since the Citacao " + citacaoCollectionOrphanCheckCitacao + " in its citacaoCollection field has a non-nullable processoJudicialFk field.");
            }
            Collection<Redirecionamento> redirecionamentoCollectionOrphanCheck = processoJudicial.getRedirecionamentoCollection();
            for (Redirecionamento redirecionamentoCollectionOrphanCheckRedirecionamento : redirecionamentoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProcessoJudicial (" + processoJudicial + ") cannot be destroyed since the Redirecionamento " + redirecionamentoCollectionOrphanCheckRedirecionamento + " in its redirecionamentoCollection field has a non-nullable processoJudicialFk field.");
            }
            Collection<Penhora> penhoraCollectionOrphanCheck = processoJudicial.getPenhoraCollection();
            for (Penhora penhoraCollectionOrphanCheckPenhora : penhoraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProcessoJudicial (" + processoJudicial + ") cannot be destroyed since the Penhora " + penhoraCollectionOrphanCheckPenhora + " in its penhoraCollection field has a non-nullable processoJudicialFk field.");
            }
            Collection<AquisicaoBem> aquisicaoBemCollectionOrphanCheck = processoJudicial.getAquisicaoBemCollection();
            for (AquisicaoBem aquisicaoBemCollectionOrphanCheckAquisicaoBem : aquisicaoBemCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProcessoJudicial (" + processoJudicial + ") cannot be destroyed since the AquisicaoBem " + aquisicaoBemCollectionOrphanCheckAquisicaoBem + " in its aquisicaoBemCollection field has a non-nullable processoJudicialFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TipoRecurso tipoDeRecursoFk = processoJudicial.getTipoDeRecursoFk();
            if (tipoDeRecursoFk != null) {
                tipoDeRecursoFk.getProcessoJudicialCollection().remove(processoJudicial);
                tipoDeRecursoFk = em.merge(tipoDeRecursoFk);
            }
            Usuario usuarioFk = processoJudicial.getUsuarioFk();
            if (usuarioFk != null) {
                usuarioFk.getProcessoJudicialCollection().remove(processoJudicial);
                usuarioFk = em.merge(usuarioFk);
            }
            Situacao situacaoFk = processoJudicial.getSituacaoFk();
            if (situacaoFk != null) {
                situacaoFk.getProcessoJudicialCollection().remove(processoJudicial);
                situacaoFk = em.merge(situacaoFk);
            }
            Procurador procuradorFk = processoJudicial.getProcuradorFk();
            if (procuradorFk != null) {
                procuradorFk.getProcessoJudicialCollection().remove(processoJudicial);
                procuradorFk = em.merge(procuradorFk);
            }
            Instituicao instituicaoFk = processoJudicial.getInstituicaoFk();
            if (instituicaoFk != null) {
                instituicaoFk.getProcessoJudicialCollection().remove(processoJudicial);
                instituicaoFk = em.merge(instituicaoFk);
            }
            em.remove(processoJudicial);
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

    public List<ProcessoJudicial> findProcessoJudicialEntities() {
        return findProcessoJudicialEntities(true, -1, -1);
    }

    public List<ProcessoJudicial> findProcessoJudicialEntities(int maxResults, int firstResult) {
        return findProcessoJudicialEntities(false, maxResults, firstResult);
    }

    private List<ProcessoJudicial> findProcessoJudicialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProcessoJudicial.class));
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

    public ProcessoJudicial findProcessoJudicial(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProcessoJudicial.class, id);
        } finally {
            em.close();
        }
    }

    public int getProcessoJudicialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProcessoJudicial> rt = cq.from(ProcessoJudicial.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public ProcessoJudicial findByProcessNumberOrCDA(ProcessoJudicial processoJudicial) {
        EntityManager em = getEntityManager();
        try {
            ProcessoJudicial usuario = (ProcessoJudicial) em.createNativeQuery("select * from processo_judicial "
                    + "where numero_do_processo = '" + processoJudicial.getNumeroDoProcesso() + "' or numero_da_cda = '" + processoJudicial.getNumeroDaCda() + "'", ProcessoJudicial.class).getSingleResult();
            return usuario;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public ProcessoJudicial findByProcessNumber(String processNumber) {
        EntityManager em = getEntityManager();
        try {
            ProcessoJudicial usuario = (ProcessoJudicial) em.createNativeQuery("select * from processo_judicial "
                    + "where numero_do_processo = '" + processNumber + "'", ProcessoJudicial.class).getSingleResult();
            return usuario;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public ProcessoJudicial findByCDA(ProcessoJudicial processoJudicial) {
        EntityManager em = getEntityManager();
        try {
            ProcessoJudicial usuario = (ProcessoJudicial) em.createNativeQuery("select * from processo_judicial "
                    + "where numero_da_cda = '" + processoJudicial.getNumeroDaCda() + "'", ProcessoJudicial.class).getSingleResult();
            return usuario;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public List<ProcessoJudicial> findByExecutado(String executado, String tipoExecutado){
        EntityManager em = getEntityManager();
        try {
            List<ProcessoJudicial> processoJudicialList = (List<ProcessoJudicial>) em.createNativeQuery("select * from processo_judicial "
                    + "where status = 'A' and executado = '"+tipoExecutado+"' and executado_fk = '"+executado+"'", ProcessoJudicial.class).getResultList();
            return processoJudicialList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<ProcessoJudicial> findAllActive() {
        EntityManager em = getEntityManager();
        try {
            List<ProcessoJudicial> processoJudicialList = (List<ProcessoJudicial>) em.createNativeQuery("select * from processo_judicial "
                    + "where status = 'A' ", ProcessoJudicial.class).getResultList();
            return processoJudicialList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public Integer countPJUDByMonth(Integer ano, Integer mes, Instituicao instituicao) {
        EntityManager em = getEntityManager();
        try {
            Long count = (Long) em.createNativeQuery("select count(distinct pjud.id) from processo_judicial pjud, log l "
                    + "where l.tabela = 'PJUD' and l.id_fk= pjud.id and "
                    + "pjud.instituicao_fk = "+instituicao.getId()+" and "
                    + "pjud.status = 'A' and l.operacao = 'C' and "
                    + "DATE_PART('MONTH', l.data_de_criacao) = " + mes + " and "
                    + "DATE_PART('YEAR', l.data_de_criacao) = " + ano).getSingleResult();
            return count.intValue();
        } catch (NoResultException e) {
            return 0;
        } finally {
            em.close();
        }
    }

    public Double sumPJUDValueBeforeMonth(Integer ano, Integer mes, Instituicao instituicao) {
        EntityManager em = getEntityManager();
        try {
            BigDecimal count = (BigDecimal) em.createNativeQuery("select sum(valor_atualizado) from processo_judicial pj  "
                    + "where pj.instituicao_fk = "+instituicao.getId()+" and "
                    + "to_date(distribuicao_data_do_ato, 'DD/MM/YYYY') <= to_date('"+mes+"/"+ano+"', 'MM/YYYY') and "
                    + "pj.id not in (select spj.id from processo_judicial spj, log sl where spj.id = sl.id_fk and sl.tabela = 'PJUD' and sl.operacao = 'D') ").getSingleResult();
            if (count == null) {
                return 0.0;
            } else {
                return count.doubleValue();
            }
        } catch (NoResultException e) {
            return 0.0;
        } finally {
            em.close();
        }
    }
    
    public Double sumPJUDArrecadacaoBeforeMonth(Integer ano, Integer mes, Instituicao instituicao) {
        EntityManager em = getEntityManager();
        try {
            BigDecimal count = (BigDecimal) em.createNativeQuery("select sum(valor_arrecadado) from processo_judicial pj  "
                    + "where to_date(distribuicao_data_do_ato, 'DD/MM/YYYY') <= to_date('"+mes+"/"+ano+"', 'MM/YYYY') and "
                    + "pj.instituicao_fk = "+instituicao.getId()+" and "
                    + "pj.id not in (select spj.id from processo_judicial spj, log sl where spj.id = sl.id_fk and sl.tabela = 'PJUD' and sl.operacao = 'D') ").getSingleResult();
            if (count == null) {
                return 0.0;
            } else {
                return count.doubleValue();
            }
        } catch (NoResultException e) {
            return 0.0;
        } finally {
            em.close();
        }
    }

    public Integer countPJUDValueBeforeMonth(Integer ano, Integer mes, Instituicao instituicao) {
        EntityManager em = getEntityManager();
        try {
            Long count = (Long) em.createNativeQuery("select count(pj.id) from processo_judicial pj "
                    + "where to_date(distribuicao_data_do_ato, 'DD/MM/YYYY') <= to_date('"+mes+"/"+ano+"', 'MM/YYYY') and "
                    + "pj.instituicao_fk = "+instituicao.getId()+" and "
                    + "pj.id not in (select spj.id from processo_judicial spj, log sl where spj.id = sl.id_fk and sl.tabela = 'PJUD' and sl.operacao = 'D') ").getSingleResult();
            return count.intValue();
        } catch (NoResultException e) {
            return 0;
        } finally {
            em.close();
        }
    }
    
    public Integer getPJUDSituations(String situacao, Instituicao instituicao) {
        EntityManager em = getEntityManager();
        try {
            Long count = (Long) em.createNativeQuery("select count(*) from processo_judicial pj, situacao s "
                    + "where pj.situacao_fk = s.id and "
                    + "s.situacao = '"+situacao+"' and "
                    + "pj.instituicao_fk = "+instituicao.getId()+" "
                    + "group by pj.situacao_fk, s.situacao ").getSingleResult();
            if (count == null) {
                return 0;
            } else {
                return count.intValue();
            }
        } catch (NoResultException e) {
            return 0;
        } finally {
            em.close();
        }
    }
}
