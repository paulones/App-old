/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.BemHistorico;
import entidade.CitacaoHistorico;
import entidade.ProcessoJudicial;
import entidade.ProcessoJudicialHistorico;
import entidade.Procurador;
import entidade.RedirecionamentoHistorico;
import entidade.Situacao;
import entidade.TipoRecurso;
import entidade.Usuario;
import entidade.VinculoProcessualHistorico;
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
public class ProcessoJudicialHistoricoDAO implements Serializable {

    public ProcessoJudicialHistoricoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProcessoJudicialHistorico processoJudicialHistorico) throws RollbackFailureException, Exception {
        if (processoJudicialHistorico.getVinculoProcessualHistoricoCollection() == null) {
            processoJudicialHistorico.setVinculoProcessualHistoricoCollection(new ArrayList<VinculoProcessualHistorico>());
        }
        if (processoJudicialHistorico.getCitacaoHistoricoCollection() == null) {
            processoJudicialHistorico.setCitacaoHistoricoCollection(new ArrayList<CitacaoHistorico>());
        }
        if (processoJudicialHistorico.getRedirecionamentoHistoricoCollection() == null) {
            processoJudicialHistorico.setRedirecionamentoHistoricoCollection(new ArrayList<RedirecionamentoHistorico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            ProcessoJudicial processoJudicialFk = processoJudicialHistorico.getProcessoJudicialFk();
            if (processoJudicialFk != null) {
                processoJudicialFk = em.getReference(processoJudicialFk.getClass(), processoJudicialFk.getId());
                processoJudicialHistorico.setProcessoJudicialFk(processoJudicialFk);
            }
            TipoRecurso tipoDeRecursoFk = processoJudicialHistorico.getTipoDeRecursoFk();
            if (tipoDeRecursoFk != null) {
                tipoDeRecursoFk = em.getReference(tipoDeRecursoFk.getClass(), tipoDeRecursoFk.getId());
                processoJudicialHistorico.setTipoDeRecursoFk(tipoDeRecursoFk);
            }
            Usuario usuarioFk = processoJudicialHistorico.getUsuarioFk();
            if (usuarioFk != null) {
                usuarioFk = em.getReference(usuarioFk.getClass(), usuarioFk.getId());
                processoJudicialHistorico.setUsuarioFk(usuarioFk);
            }
            Situacao situacaoFk = processoJudicialHistorico.getSituacaoFk();
            if (situacaoFk != null) {
                situacaoFk = em.getReference(situacaoFk.getClass(), situacaoFk.getId());
                processoJudicialHistorico.setSituacaoFk(situacaoFk);
            }
            Procurador procuradorFk = processoJudicialHistorico.getProcuradorFk();
            if (procuradorFk != null) {
                procuradorFk = em.getReference(procuradorFk.getClass(), procuradorFk.getId());
                processoJudicialHistorico.setProcuradorFk(procuradorFk);
            }
            Collection<VinculoProcessualHistorico> attachedVinculoProcessualHistoricoCollection = new ArrayList<VinculoProcessualHistorico>();
            for (VinculoProcessualHistorico vinculoProcessualHistoricoCollectionVinculoProcessualHistoricoToAttach : processoJudicialHistorico.getVinculoProcessualHistoricoCollection()) {
                vinculoProcessualHistoricoCollectionVinculoProcessualHistoricoToAttach = em.getReference(vinculoProcessualHistoricoCollectionVinculoProcessualHistoricoToAttach.getClass(), vinculoProcessualHistoricoCollectionVinculoProcessualHistoricoToAttach.getId());
                attachedVinculoProcessualHistoricoCollection.add(vinculoProcessualHistoricoCollectionVinculoProcessualHistoricoToAttach);
            }
            processoJudicialHistorico.setVinculoProcessualHistoricoCollection(attachedVinculoProcessualHistoricoCollection);
            Collection<CitacaoHistorico> attachedCitacaoHistoricoCollection = new ArrayList<CitacaoHistorico>();
            for (CitacaoHistorico citacaoHistoricoCollectionCitacaoHistoricoToAttach : processoJudicialHistorico.getCitacaoHistoricoCollection()) {
                citacaoHistoricoCollectionCitacaoHistoricoToAttach = em.getReference(citacaoHistoricoCollectionCitacaoHistoricoToAttach.getClass(), citacaoHistoricoCollectionCitacaoHistoricoToAttach.getId());
                attachedCitacaoHistoricoCollection.add(citacaoHistoricoCollectionCitacaoHistoricoToAttach);
            }
            processoJudicialHistorico.setCitacaoHistoricoCollection(attachedCitacaoHistoricoCollection);
            Collection<RedirecionamentoHistorico> attachedRedirecionamentoHistoricoCollection = new ArrayList<RedirecionamentoHistorico>();
            for (RedirecionamentoHistorico redirecionamentoHistoricoCollectionRedirecionamentoHistoricoToAttach : processoJudicialHistorico.getRedirecionamentoHistoricoCollection()) {
                redirecionamentoHistoricoCollectionRedirecionamentoHistoricoToAttach = em.getReference(redirecionamentoHistoricoCollectionRedirecionamentoHistoricoToAttach.getClass(), redirecionamentoHistoricoCollectionRedirecionamentoHistoricoToAttach.getId());
                attachedRedirecionamentoHistoricoCollection.add(redirecionamentoHistoricoCollectionRedirecionamentoHistoricoToAttach);
            }
            processoJudicialHistorico.setRedirecionamentoHistoricoCollection(attachedRedirecionamentoHistoricoCollection);
            em.persist(processoJudicialHistorico);
            if (processoJudicialFk != null) {
                processoJudicialFk.getProcessoJudicialHistoricoCollection().add(processoJudicialHistorico);
                processoJudicialFk = em.merge(processoJudicialFk);
            }
            if (tipoDeRecursoFk != null) {
                tipoDeRecursoFk.getProcessoJudicialHistoricoCollection().add(processoJudicialHistorico);
                tipoDeRecursoFk = em.merge(tipoDeRecursoFk);
            }
            if (usuarioFk != null) {
                usuarioFk.getProcessoJudicialHistoricoCollection().add(processoJudicialHistorico);
                usuarioFk = em.merge(usuarioFk);
            }
            if (situacaoFk != null) {
                situacaoFk.getProcessoJudicialHistoricoCollection().add(processoJudicialHistorico);
                situacaoFk = em.merge(situacaoFk);
            }
            if (procuradorFk != null) {
                procuradorFk.getProcessoJudicialHistoricoCollection().add(processoJudicialHistorico);
                procuradorFk = em.merge(procuradorFk);
            }
            for (VinculoProcessualHistorico vinculoProcessualHistoricoCollectionVinculoProcessualHistorico : processoJudicialHistorico.getVinculoProcessualHistoricoCollection()) {
                ProcessoJudicialHistorico oldProcessoJudicialHistoricoFkOfVinculoProcessualHistoricoCollectionVinculoProcessualHistorico = vinculoProcessualHistoricoCollectionVinculoProcessualHistorico.getProcessoJudicialHistoricoFk();
                vinculoProcessualHistoricoCollectionVinculoProcessualHistorico.setProcessoJudicialHistoricoFk(processoJudicialHistorico);
                vinculoProcessualHistoricoCollectionVinculoProcessualHistorico = em.merge(vinculoProcessualHistoricoCollectionVinculoProcessualHistorico);
                if (oldProcessoJudicialHistoricoFkOfVinculoProcessualHistoricoCollectionVinculoProcessualHistorico != null) {
                    oldProcessoJudicialHistoricoFkOfVinculoProcessualHistoricoCollectionVinculoProcessualHistorico.getVinculoProcessualHistoricoCollection().remove(vinculoProcessualHistoricoCollectionVinculoProcessualHistorico);
                    oldProcessoJudicialHistoricoFkOfVinculoProcessualHistoricoCollectionVinculoProcessualHistorico = em.merge(oldProcessoJudicialHistoricoFkOfVinculoProcessualHistoricoCollectionVinculoProcessualHistorico);
                }
            }
            for (CitacaoHistorico citacaoHistoricoCollectionCitacaoHistorico : processoJudicialHistorico.getCitacaoHistoricoCollection()) {
                ProcessoJudicialHistorico oldProcessoJudicialHistoricoFkOfCitacaoHistoricoCollectionCitacaoHistorico = citacaoHistoricoCollectionCitacaoHistorico.getProcessoJudicialHistoricoFk();
                citacaoHistoricoCollectionCitacaoHistorico.setProcessoJudicialHistoricoFk(processoJudicialHistorico);
                citacaoHistoricoCollectionCitacaoHistorico = em.merge(citacaoHistoricoCollectionCitacaoHistorico);
                if (oldProcessoJudicialHistoricoFkOfCitacaoHistoricoCollectionCitacaoHistorico != null) {
                    oldProcessoJudicialHistoricoFkOfCitacaoHistoricoCollectionCitacaoHistorico.getCitacaoHistoricoCollection().remove(citacaoHistoricoCollectionCitacaoHistorico);
                    oldProcessoJudicialHistoricoFkOfCitacaoHistoricoCollectionCitacaoHistorico = em.merge(oldProcessoJudicialHistoricoFkOfCitacaoHistoricoCollectionCitacaoHistorico);
                }
            }
            for (RedirecionamentoHistorico redirecionamentoHistoricoCollectionRedirecionamentoHistorico : processoJudicialHistorico.getRedirecionamentoHistoricoCollection()) {
                ProcessoJudicialHistorico oldProcessoJudicialHistoricoFkOfRedirecionamentoHistoricoCollectionRedirecionamentoHistorico = redirecionamentoHistoricoCollectionRedirecionamentoHistorico.getProcessoJudicialHistoricoFk();
                redirecionamentoHistoricoCollectionRedirecionamentoHistorico.setProcessoJudicialHistoricoFk(processoJudicialHistorico);
                redirecionamentoHistoricoCollectionRedirecionamentoHistorico = em.merge(redirecionamentoHistoricoCollectionRedirecionamentoHistorico);
                if (oldProcessoJudicialHistoricoFkOfRedirecionamentoHistoricoCollectionRedirecionamentoHistorico != null) {
                    oldProcessoJudicialHistoricoFkOfRedirecionamentoHistoricoCollectionRedirecionamentoHistorico.getRedirecionamentoHistoricoCollection().remove(redirecionamentoHistoricoCollectionRedirecionamentoHistorico);
                    oldProcessoJudicialHistoricoFkOfRedirecionamentoHistoricoCollectionRedirecionamentoHistorico = em.merge(oldProcessoJudicialHistoricoFkOfRedirecionamentoHistoricoCollectionRedirecionamentoHistorico);
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

    public void edit(ProcessoJudicialHistorico processoJudicialHistorico) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            ProcessoJudicialHistorico persistentProcessoJudicialHistorico = em.find(ProcessoJudicialHistorico.class, processoJudicialHistorico.getId());
            ProcessoJudicial processoJudicialFkOld = persistentProcessoJudicialHistorico.getProcessoJudicialFk();
            ProcessoJudicial processoJudicialFkNew = processoJudicialHistorico.getProcessoJudicialFk();
            TipoRecurso tipoDeRecursoFkOld = persistentProcessoJudicialHistorico.getTipoDeRecursoFk();
            TipoRecurso tipoDeRecursoFkNew = processoJudicialHistorico.getTipoDeRecursoFk();
            Usuario usuarioFkOld = persistentProcessoJudicialHistorico.getUsuarioFk();
            Usuario usuarioFkNew = processoJudicialHistorico.getUsuarioFk();
            Situacao situacaoFkOld = persistentProcessoJudicialHistorico.getSituacaoFk();
            Situacao situacaoFkNew = processoJudicialHistorico.getSituacaoFk();
            Procurador procuradorFkOld = persistentProcessoJudicialHistorico.getProcuradorFk();
            Procurador procuradorFkNew = processoJudicialHistorico.getProcuradorFk();
            Collection<VinculoProcessualHistorico> vinculoProcessualHistoricoCollectionOld = persistentProcessoJudicialHistorico.getVinculoProcessualHistoricoCollection();
            Collection<VinculoProcessualHistorico> vinculoProcessualHistoricoCollectionNew = processoJudicialHistorico.getVinculoProcessualHistoricoCollection();
            Collection<CitacaoHistorico> citacaoHistoricoCollectionOld = persistentProcessoJudicialHistorico.getCitacaoHistoricoCollection();
            Collection<CitacaoHistorico> citacaoHistoricoCollectionNew = processoJudicialHistorico.getCitacaoHistoricoCollection();
            Collection<RedirecionamentoHistorico> redirecionamentoHistoricoCollectionOld = persistentProcessoJudicialHistorico.getRedirecionamentoHistoricoCollection();
            Collection<RedirecionamentoHistorico> redirecionamentoHistoricoCollectionNew = processoJudicialHistorico.getRedirecionamentoHistoricoCollection();
            List<String> illegalOrphanMessages = null;
            for (VinculoProcessualHistorico vinculoProcessualHistoricoCollectionOldVinculoProcessualHistorico : vinculoProcessualHistoricoCollectionOld) {
                if (!vinculoProcessualHistoricoCollectionNew.contains(vinculoProcessualHistoricoCollectionOldVinculoProcessualHistorico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain VinculoProcessualHistorico " + vinculoProcessualHistoricoCollectionOldVinculoProcessualHistorico + " since its processoJudicialHistoricoFk field is not nullable.");
                }
            }
            for (CitacaoHistorico citacaoHistoricoCollectionOldCitacaoHistorico : citacaoHistoricoCollectionOld) {
                if (!citacaoHistoricoCollectionNew.contains(citacaoHistoricoCollectionOldCitacaoHistorico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CitacaoHistorico " + citacaoHistoricoCollectionOldCitacaoHistorico + " since its processoJudicialHistoricoFk field is not nullable.");
                }
            }
            for (RedirecionamentoHistorico redirecionamentoHistoricoCollectionOldRedirecionamentoHistorico : redirecionamentoHistoricoCollectionOld) {
                if (!redirecionamentoHistoricoCollectionNew.contains(redirecionamentoHistoricoCollectionOldRedirecionamentoHistorico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RedirecionamentoHistorico " + redirecionamentoHistoricoCollectionOldRedirecionamentoHistorico + " since its processoJudicialHistoricoFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (processoJudicialFkNew != null) {
                processoJudicialFkNew = em.getReference(processoJudicialFkNew.getClass(), processoJudicialFkNew.getId());
                processoJudicialHistorico.setProcessoJudicialFk(processoJudicialFkNew);
            }
            if (tipoDeRecursoFkNew != null) {
                tipoDeRecursoFkNew = em.getReference(tipoDeRecursoFkNew.getClass(), tipoDeRecursoFkNew.getId());
                processoJudicialHistorico.setTipoDeRecursoFk(tipoDeRecursoFkNew);
            }
            if (usuarioFkNew != null) {
                usuarioFkNew = em.getReference(usuarioFkNew.getClass(), usuarioFkNew.getId());
                processoJudicialHistorico.setUsuarioFk(usuarioFkNew);
            }
            if (situacaoFkNew != null) {
                situacaoFkNew = em.getReference(situacaoFkNew.getClass(), situacaoFkNew.getId());
                processoJudicialHistorico.setSituacaoFk(situacaoFkNew);
            }
            if (procuradorFkNew != null) {
                procuradorFkNew = em.getReference(procuradorFkNew.getClass(), procuradorFkNew.getId());
                processoJudicialHistorico.setProcuradorFk(procuradorFkNew);
            }
            Collection<VinculoProcessualHistorico> attachedVinculoProcessualHistoricoCollectionNew = new ArrayList<VinculoProcessualHistorico>();
            for (VinculoProcessualHistorico vinculoProcessualHistoricoCollectionNewVinculoProcessualHistoricoToAttach : vinculoProcessualHistoricoCollectionNew) {
                vinculoProcessualHistoricoCollectionNewVinculoProcessualHistoricoToAttach = em.getReference(vinculoProcessualHistoricoCollectionNewVinculoProcessualHistoricoToAttach.getClass(), vinculoProcessualHistoricoCollectionNewVinculoProcessualHistoricoToAttach.getId());
                attachedVinculoProcessualHistoricoCollectionNew.add(vinculoProcessualHistoricoCollectionNewVinculoProcessualHistoricoToAttach);
            }
            vinculoProcessualHistoricoCollectionNew = attachedVinculoProcessualHistoricoCollectionNew;
            processoJudicialHistorico.setVinculoProcessualHistoricoCollection(vinculoProcessualHistoricoCollectionNew);
            Collection<CitacaoHistorico> attachedCitacaoHistoricoCollectionNew = new ArrayList<CitacaoHistorico>();
            for (CitacaoHistorico citacaoHistoricoCollectionNewCitacaoHistoricoToAttach : citacaoHistoricoCollectionNew) {
                citacaoHistoricoCollectionNewCitacaoHistoricoToAttach = em.getReference(citacaoHistoricoCollectionNewCitacaoHistoricoToAttach.getClass(), citacaoHistoricoCollectionNewCitacaoHistoricoToAttach.getId());
                attachedCitacaoHistoricoCollectionNew.add(citacaoHistoricoCollectionNewCitacaoHistoricoToAttach);
            }
            citacaoHistoricoCollectionNew = attachedCitacaoHistoricoCollectionNew;
            processoJudicialHistorico.setCitacaoHistoricoCollection(citacaoHistoricoCollectionNew);
            Collection<RedirecionamentoHistorico> attachedRedirecionamentoHistoricoCollectionNew = new ArrayList<RedirecionamentoHistorico>();
            for (RedirecionamentoHistorico redirecionamentoHistoricoCollectionNewRedirecionamentoHistoricoToAttach : redirecionamentoHistoricoCollectionNew) {
                redirecionamentoHistoricoCollectionNewRedirecionamentoHistoricoToAttach = em.getReference(redirecionamentoHistoricoCollectionNewRedirecionamentoHistoricoToAttach.getClass(), redirecionamentoHistoricoCollectionNewRedirecionamentoHistoricoToAttach.getId());
                attachedRedirecionamentoHistoricoCollectionNew.add(redirecionamentoHistoricoCollectionNewRedirecionamentoHistoricoToAttach);
            }
            redirecionamentoHistoricoCollectionNew = attachedRedirecionamentoHistoricoCollectionNew;
            processoJudicialHistorico.setRedirecionamentoHistoricoCollection(redirecionamentoHistoricoCollectionNew);
            processoJudicialHistorico = em.merge(processoJudicialHistorico);
            if (processoJudicialFkOld != null && !processoJudicialFkOld.equals(processoJudicialFkNew)) {
                processoJudicialFkOld.getProcessoJudicialHistoricoCollection().remove(processoJudicialHistorico);
                processoJudicialFkOld = em.merge(processoJudicialFkOld);
            }
            if (processoJudicialFkNew != null && !processoJudicialFkNew.equals(processoJudicialFkOld)) {
                processoJudicialFkNew.getProcessoJudicialHistoricoCollection().add(processoJudicialHistorico);
                processoJudicialFkNew = em.merge(processoJudicialFkNew);
            }
            if (tipoDeRecursoFkOld != null && !tipoDeRecursoFkOld.equals(tipoDeRecursoFkNew)) {
                tipoDeRecursoFkOld.getProcessoJudicialHistoricoCollection().remove(processoJudicialHistorico);
                tipoDeRecursoFkOld = em.merge(tipoDeRecursoFkOld);
            }
            if (tipoDeRecursoFkNew != null && !tipoDeRecursoFkNew.equals(tipoDeRecursoFkOld)) {
                tipoDeRecursoFkNew.getProcessoJudicialHistoricoCollection().add(processoJudicialHistorico);
                tipoDeRecursoFkNew = em.merge(tipoDeRecursoFkNew);
            }
            if (usuarioFkOld != null && !usuarioFkOld.equals(usuarioFkNew)) {
                usuarioFkOld.getProcessoJudicialHistoricoCollection().remove(processoJudicialHistorico);
                usuarioFkOld = em.merge(usuarioFkOld);
            }
            if (usuarioFkNew != null && !usuarioFkNew.equals(usuarioFkOld)) {
                usuarioFkNew.getProcessoJudicialHistoricoCollection().add(processoJudicialHistorico);
                usuarioFkNew = em.merge(usuarioFkNew);
            }
            if (situacaoFkOld != null && !situacaoFkOld.equals(situacaoFkNew)) {
                situacaoFkOld.getProcessoJudicialHistoricoCollection().remove(processoJudicialHistorico);
                situacaoFkOld = em.merge(situacaoFkOld);
            }
            if (situacaoFkNew != null && !situacaoFkNew.equals(situacaoFkOld)) {
                situacaoFkNew.getProcessoJudicialHistoricoCollection().add(processoJudicialHistorico);
                situacaoFkNew = em.merge(situacaoFkNew);
            }
            if (procuradorFkOld != null && !procuradorFkOld.equals(procuradorFkNew)) {
                procuradorFkOld.getProcessoJudicialHistoricoCollection().remove(processoJudicialHistorico);
                procuradorFkOld = em.merge(procuradorFkOld);
            }
            if (procuradorFkNew != null && !procuradorFkNew.equals(procuradorFkOld)) {
                procuradorFkNew.getProcessoJudicialHistoricoCollection().add(processoJudicialHistorico);
                procuradorFkNew = em.merge(procuradorFkNew);
            }
            for (VinculoProcessualHistorico vinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico : vinculoProcessualHistoricoCollectionNew) {
                if (!vinculoProcessualHistoricoCollectionOld.contains(vinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico)) {
                    ProcessoJudicialHistorico oldProcessoJudicialHistoricoFkOfVinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico = vinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico.getProcessoJudicialHistoricoFk();
                    vinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico.setProcessoJudicialHistoricoFk(processoJudicialHistorico);
                    vinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico = em.merge(vinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico);
                    if (oldProcessoJudicialHistoricoFkOfVinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico != null && !oldProcessoJudicialHistoricoFkOfVinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico.equals(processoJudicialHistorico)) {
                        oldProcessoJudicialHistoricoFkOfVinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico.getVinculoProcessualHistoricoCollection().remove(vinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico);
                        oldProcessoJudicialHistoricoFkOfVinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico = em.merge(oldProcessoJudicialHistoricoFkOfVinculoProcessualHistoricoCollectionNewVinculoProcessualHistorico);
                    }
                }
            }
            for (CitacaoHistorico citacaoHistoricoCollectionNewCitacaoHistorico : citacaoHistoricoCollectionNew) {
                if (!citacaoHistoricoCollectionOld.contains(citacaoHistoricoCollectionNewCitacaoHistorico)) {
                    ProcessoJudicialHistorico oldProcessoJudicialHistoricoFkOfCitacaoHistoricoCollectionNewCitacaoHistorico = citacaoHistoricoCollectionNewCitacaoHistorico.getProcessoJudicialHistoricoFk();
                    citacaoHistoricoCollectionNewCitacaoHistorico.setProcessoJudicialHistoricoFk(processoJudicialHistorico);
                    citacaoHistoricoCollectionNewCitacaoHistorico = em.merge(citacaoHistoricoCollectionNewCitacaoHistorico);
                    if (oldProcessoJudicialHistoricoFkOfCitacaoHistoricoCollectionNewCitacaoHistorico != null && !oldProcessoJudicialHistoricoFkOfCitacaoHistoricoCollectionNewCitacaoHistorico.equals(processoJudicialHistorico)) {
                        oldProcessoJudicialHistoricoFkOfCitacaoHistoricoCollectionNewCitacaoHistorico.getCitacaoHistoricoCollection().remove(citacaoHistoricoCollectionNewCitacaoHistorico);
                        oldProcessoJudicialHistoricoFkOfCitacaoHistoricoCollectionNewCitacaoHistorico = em.merge(oldProcessoJudicialHistoricoFkOfCitacaoHistoricoCollectionNewCitacaoHistorico);
                    }
                }
            }
            for (RedirecionamentoHistorico redirecionamentoHistoricoCollectionNewRedirecionamentoHistorico : redirecionamentoHistoricoCollectionNew) {
                if (!redirecionamentoHistoricoCollectionOld.contains(redirecionamentoHistoricoCollectionNewRedirecionamentoHistorico)) {
                    ProcessoJudicialHistorico oldProcessoJudicialHistoricoFkOfRedirecionamentoHistoricoCollectionNewRedirecionamentoHistorico = redirecionamentoHistoricoCollectionNewRedirecionamentoHistorico.getProcessoJudicialHistoricoFk();
                    redirecionamentoHistoricoCollectionNewRedirecionamentoHistorico.setProcessoJudicialHistoricoFk(processoJudicialHistorico);
                    redirecionamentoHistoricoCollectionNewRedirecionamentoHistorico = em.merge(redirecionamentoHistoricoCollectionNewRedirecionamentoHistorico);
                    if (oldProcessoJudicialHistoricoFkOfRedirecionamentoHistoricoCollectionNewRedirecionamentoHistorico != null && !oldProcessoJudicialHistoricoFkOfRedirecionamentoHistoricoCollectionNewRedirecionamentoHistorico.equals(processoJudicialHistorico)) {
                        oldProcessoJudicialHistoricoFkOfRedirecionamentoHistoricoCollectionNewRedirecionamentoHistorico.getRedirecionamentoHistoricoCollection().remove(redirecionamentoHistoricoCollectionNewRedirecionamentoHistorico);
                        oldProcessoJudicialHistoricoFkOfRedirecionamentoHistoricoCollectionNewRedirecionamentoHistorico = em.merge(oldProcessoJudicialHistoricoFkOfRedirecionamentoHistoricoCollectionNewRedirecionamentoHistorico);
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
                Integer id = processoJudicialHistorico.getId();
                if (findProcessoJudicialHistorico(id) == null) {
                    throw new NonexistentEntityException("The processoJudicialHistorico with id " + id + " no longer exists.");
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
            ProcessoJudicialHistorico processoJudicialHistorico;
            try {
                processoJudicialHistorico = em.getReference(ProcessoJudicialHistorico.class, id);
                processoJudicialHistorico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The processoJudicialHistorico with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<VinculoProcessualHistorico> vinculoProcessualHistoricoCollectionOrphanCheck = processoJudicialHistorico.getVinculoProcessualHistoricoCollection();
            for (VinculoProcessualHistorico vinculoProcessualHistoricoCollectionOrphanCheckVinculoProcessualHistorico : vinculoProcessualHistoricoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProcessoJudicialHistorico (" + processoJudicialHistorico + ") cannot be destroyed since the VinculoProcessualHistorico " + vinculoProcessualHistoricoCollectionOrphanCheckVinculoProcessualHistorico + " in its vinculoProcessualHistoricoCollection field has a non-nullable processoJudicialHistoricoFk field.");
            }
            Collection<CitacaoHistorico> citacaoHistoricoCollectionOrphanCheck = processoJudicialHistorico.getCitacaoHistoricoCollection();
            for (CitacaoHistorico citacaoHistoricoCollectionOrphanCheckCitacaoHistorico : citacaoHistoricoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProcessoJudicialHistorico (" + processoJudicialHistorico + ") cannot be destroyed since the CitacaoHistorico " + citacaoHistoricoCollectionOrphanCheckCitacaoHistorico + " in its citacaoHistoricoCollection field has a non-nullable processoJudicialHistoricoFk field.");
            }
            Collection<RedirecionamentoHistorico> redirecionamentoHistoricoCollectionOrphanCheck = processoJudicialHistorico.getRedirecionamentoHistoricoCollection();
            for (RedirecionamentoHistorico redirecionamentoHistoricoCollectionOrphanCheckRedirecionamentoHistorico : redirecionamentoHistoricoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProcessoJudicialHistorico (" + processoJudicialHistorico + ") cannot be destroyed since the RedirecionamentoHistorico " + redirecionamentoHistoricoCollectionOrphanCheckRedirecionamentoHistorico + " in its redirecionamentoHistoricoCollection field has a non-nullable processoJudicialHistoricoFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            ProcessoJudicial processoJudicialFk = processoJudicialHistorico.getProcessoJudicialFk();
            if (processoJudicialFk != null) {
                processoJudicialFk.getProcessoJudicialHistoricoCollection().remove(processoJudicialHistorico);
                processoJudicialFk = em.merge(processoJudicialFk);
            }
            TipoRecurso tipoDeRecursoFk = processoJudicialHistorico.getTipoDeRecursoFk();
            if (tipoDeRecursoFk != null) {
                tipoDeRecursoFk.getProcessoJudicialHistoricoCollection().remove(processoJudicialHistorico);
                tipoDeRecursoFk = em.merge(tipoDeRecursoFk);
            }
            Usuario usuarioFk = processoJudicialHistorico.getUsuarioFk();
            if (usuarioFk != null) {
                usuarioFk.getProcessoJudicialHistoricoCollection().remove(processoJudicialHistorico);
                usuarioFk = em.merge(usuarioFk);
            }
            Situacao situacaoFk = processoJudicialHistorico.getSituacaoFk();
            if (situacaoFk != null) {
                situacaoFk.getProcessoJudicialHistoricoCollection().remove(processoJudicialHistorico);
                situacaoFk = em.merge(situacaoFk);
            }
            Procurador procuradorFk = processoJudicialHistorico.getProcuradorFk();
            if (procuradorFk != null) {
                procuradorFk.getProcessoJudicialHistoricoCollection().remove(processoJudicialHistorico);
                procuradorFk = em.merge(procuradorFk);
            }
            em.remove(processoJudicialHistorico);
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

    public List<ProcessoJudicialHistorico> findProcessoJudicialHistoricoEntities() {
        return findProcessoJudicialHistoricoEntities(true, -1, -1);
    }

    public List<ProcessoJudicialHistorico> findProcessoJudicialHistoricoEntities(int maxResults, int firstResult) {
        return findProcessoJudicialHistoricoEntities(false, maxResults, firstResult);
    }

    private List<ProcessoJudicialHistorico> findProcessoJudicialHistoricoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProcessoJudicialHistorico.class));
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

    public ProcessoJudicialHistorico findProcessoJudicialHistorico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProcessoJudicialHistorico.class, id);
        } finally {
            em.close();
        }
    }

    public int getProcessoJudicialHistoricoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProcessoJudicialHistorico> rt = cq.from(ProcessoJudicialHistorico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<ProcessoJudicialHistorico> findAllByPJUD(Integer id){
        EntityManager em = getEntityManager();
        try {
            List<ProcessoJudicialHistorico> processoJudicialHistoricoList = (List<ProcessoJudicialHistorico>) em.createNativeQuery("select * from processo_judicial_historico "
                        + "where processo_judicial_fk = '"+id+"' order by data_de_modificacao desc", ProcessoJudicialHistorico.class).getResultList();
            return processoJudicialHistoricoList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
