/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidade.Cidade;
import entidade.Estado;
import entidade.EstadoCivil;
import entidade.Nacionalidade;
import entidade.Pais;
import entidade.PessoaFisica;
import entidade.PessoaFisicaHistorico;
import entidade.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.transaction.UserTransaction;

/**
 *
 * @author paulones
 */
public class PessoaFisicaHistoricoDAO implements Serializable {

    public PessoaFisicaHistoricoDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PessoaFisicaHistorico pessoaFisicaHistorico) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cidade cidadeFk = pessoaFisicaHistorico.getCidadeFk();
            if (cidadeFk != null) {
                cidadeFk = em.getReference(cidadeFk.getClass(), cidadeFk.getId());
                pessoaFisicaHistorico.setCidadeFk(cidadeFk);
            }
            Estado rgUfFk = pessoaFisicaHistorico.getRgUfFk();
            if (rgUfFk != null) {
                rgUfFk = em.getReference(rgUfFk.getClass(), rgUfFk.getId());
                pessoaFisicaHistorico.setRgUfFk(rgUfFk);
            }
            Estado estadoFk = pessoaFisicaHistorico.getEstadoFk();
            if (estadoFk != null) {
                estadoFk = em.getReference(estadoFk.getClass(), estadoFk.getId());
                pessoaFisicaHistorico.setEstadoFk(estadoFk);
            }
            EstadoCivil estadoCivilFk = pessoaFisicaHistorico.getEstadoCivilFk();
            if (estadoCivilFk != null) {
                estadoCivilFk = em.getReference(estadoCivilFk.getClass(), estadoCivilFk.getId());
                pessoaFisicaHistorico.setEstadoCivilFk(estadoCivilFk);
            }
            Nacionalidade nacionalidadeFk = pessoaFisicaHistorico.getNacionalidadeFk();
            if (nacionalidadeFk != null) {
                nacionalidadeFk = em.getReference(nacionalidadeFk.getClass(), nacionalidadeFk.getId());
                pessoaFisicaHistorico.setNacionalidadeFk(nacionalidadeFk);
            }
            Pais paisFk = pessoaFisicaHistorico.getPaisFk();
            if (paisFk != null) {
                paisFk = em.getReference(paisFk.getClass(), paisFk.getId());
                pessoaFisicaHistorico.setPaisFk(paisFk);
            }
            PessoaFisica pessoaFisicaFk = pessoaFisicaHistorico.getPessoaFisicaFk();
            if (pessoaFisicaFk != null) {
                pessoaFisicaFk = em.getReference(pessoaFisicaFk.getClass(), pessoaFisicaFk.getId());
                pessoaFisicaHistorico.setPessoaFisicaFk(pessoaFisicaFk);
            }
            Usuario usuarioFk = pessoaFisicaHistorico.getUsuarioFk();
            if (usuarioFk != null) {
                usuarioFk = em.getReference(usuarioFk.getClass(), usuarioFk.getId());
                pessoaFisicaHistorico.setUsuarioFk(usuarioFk);
            }
            Cidade cidadeEleitoralFk = pessoaFisicaHistorico.getCidadeEleitoralFk();
            if (cidadeEleitoralFk != null) {
                cidadeEleitoralFk = em.getReference(cidadeEleitoralFk.getClass(), cidadeEleitoralFk.getId());
                pessoaFisicaHistorico.setCidadeEleitoralFk(cidadeEleitoralFk);
            }
            Estado estadoEleitoralFk = pessoaFisicaHistorico.getEstadoEleitoralFk();
            if (estadoEleitoralFk != null) {
                estadoEleitoralFk = em.getReference(estadoEleitoralFk.getClass(), estadoEleitoralFk.getId());
                pessoaFisicaHistorico.setEstadoEleitoralFk(estadoEleitoralFk);
            }
            em.persist(pessoaFisicaHistorico);
            if (cidadeFk != null) {
                cidadeFk.getPessoaFisicaHistoricoCollection().add(pessoaFisicaHistorico);
                cidadeFk = em.merge(cidadeFk);
            }
            if (rgUfFk != null) {
                rgUfFk.getPessoaFisicaHistoricoCollection().add(pessoaFisicaHistorico);
                rgUfFk = em.merge(rgUfFk);
            }
            if (estadoFk != null) {
                estadoFk.getPessoaFisicaHistoricoCollection().add(pessoaFisicaHistorico);
                estadoFk = em.merge(estadoFk);
            }
            if (estadoCivilFk != null) {
                estadoCivilFk.getPessoaFisicaHistoricoCollection().add(pessoaFisicaHistorico);
                estadoCivilFk = em.merge(estadoCivilFk);
            }
            if (nacionalidadeFk != null) {
                nacionalidadeFk.getPessoaFisicaHistoricoCollection().add(pessoaFisicaHistorico);
                nacionalidadeFk = em.merge(nacionalidadeFk);
            }
            if (paisFk != null) {
                paisFk.getPessoaFisicaHistoricoCollection().add(pessoaFisicaHistorico);
                paisFk = em.merge(paisFk);
            }
            if (pessoaFisicaFk != null) {
                pessoaFisicaFk.getPessoaFisicaHistoricoCollection().add(pessoaFisicaHistorico);
                pessoaFisicaFk = em.merge(pessoaFisicaFk);
            }
            if (usuarioFk != null) {
                usuarioFk.getPessoaFisicaHistoricoCollection().add(pessoaFisicaHistorico);
                usuarioFk = em.merge(usuarioFk);
            }
            if (cidadeEleitoralFk != null) {
                cidadeEleitoralFk.getPessoaFisicaHistoricoCollection().add(pessoaFisicaHistorico);
                cidadeEleitoralFk = em.merge(cidadeEleitoralFk);
            }
            if (estadoEleitoralFk != null) {
                estadoEleitoralFk.getPessoaFisicaHistoricoCollection().add(pessoaFisicaHistorico);
                estadoEleitoralFk = em.merge(estadoEleitoralFk);
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

    public void edit(PessoaFisicaHistorico pessoaFisicaHistorico) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PessoaFisicaHistorico persistentPessoaFisicaHistorico = em.find(PessoaFisicaHistorico.class, pessoaFisicaHistorico.getId());
            Cidade cidadeFkOld = persistentPessoaFisicaHistorico.getCidadeFk();
            Cidade cidadeFkNew = pessoaFisicaHistorico.getCidadeFk();
            Estado rgUfFkOld = persistentPessoaFisicaHistorico.getRgUfFk();
            Estado rgUfFkNew = pessoaFisicaHistorico.getRgUfFk();
            Estado estadoFkOld = persistentPessoaFisicaHistorico.getEstadoFk();
            Estado estadoFkNew = pessoaFisicaHistorico.getEstadoFk();
            EstadoCivil estadoCivilFkOld = persistentPessoaFisicaHistorico.getEstadoCivilFk();
            EstadoCivil estadoCivilFkNew = pessoaFisicaHistorico.getEstadoCivilFk();
            Nacionalidade nacionalidadeFkOld = persistentPessoaFisicaHistorico.getNacionalidadeFk();
            Nacionalidade nacionalidadeFkNew = pessoaFisicaHistorico.getNacionalidadeFk();
            Pais paisFkOld = persistentPessoaFisicaHistorico.getPaisFk();
            Pais paisFkNew = pessoaFisicaHistorico.getPaisFk();
            PessoaFisica pessoaFisicaFkOld = persistentPessoaFisicaHistorico.getPessoaFisicaFk();
            PessoaFisica pessoaFisicaFkNew = pessoaFisicaHistorico.getPessoaFisicaFk();
            Usuario usuarioFkOld = persistentPessoaFisicaHistorico.getUsuarioFk();
            Usuario usuarioFkNew = pessoaFisicaHistorico.getUsuarioFk();
            Cidade cidadeEleitoralFkOld = persistentPessoaFisicaHistorico.getCidadeEleitoralFk();
            Cidade cidadeEleitoralFkNew = pessoaFisicaHistorico.getCidadeEleitoralFk();
            Estado estadoEleitoralFkOld = persistentPessoaFisicaHistorico.getEstadoEleitoralFk();
            Estado estadoEleitoralFkNew = pessoaFisicaHistorico.getEstadoEleitoralFk();
            if (cidadeFkNew != null) {
                cidadeFkNew = em.getReference(cidadeFkNew.getClass(), cidadeFkNew.getId());
                pessoaFisicaHistorico.setCidadeFk(cidadeFkNew);
            }
            if (rgUfFkNew != null) {
                rgUfFkNew = em.getReference(rgUfFkNew.getClass(), rgUfFkNew.getId());
                pessoaFisicaHistorico.setRgUfFk(rgUfFkNew);
            }
            if (estadoFkNew != null) {
                estadoFkNew = em.getReference(estadoFkNew.getClass(), estadoFkNew.getId());
                pessoaFisicaHistorico.setEstadoFk(estadoFkNew);
            }
            if (estadoCivilFkNew != null) {
                estadoCivilFkNew = em.getReference(estadoCivilFkNew.getClass(), estadoCivilFkNew.getId());
                pessoaFisicaHistorico.setEstadoCivilFk(estadoCivilFkNew);
            }
            if (nacionalidadeFkNew != null) {
                nacionalidadeFkNew = em.getReference(nacionalidadeFkNew.getClass(), nacionalidadeFkNew.getId());
                pessoaFisicaHistorico.setNacionalidadeFk(nacionalidadeFkNew);
            }
            if (paisFkNew != null) {
                paisFkNew = em.getReference(paisFkNew.getClass(), paisFkNew.getId());
                pessoaFisicaHistorico.setPaisFk(paisFkNew);
            }
            if (pessoaFisicaFkNew != null) {
                pessoaFisicaFkNew = em.getReference(pessoaFisicaFkNew.getClass(), pessoaFisicaFkNew.getId());
                pessoaFisicaHistorico.setPessoaFisicaFk(pessoaFisicaFkNew);
            }
            if (usuarioFkNew != null) {
                usuarioFkNew = em.getReference(usuarioFkNew.getClass(), usuarioFkNew.getId());
                pessoaFisicaHistorico.setUsuarioFk(usuarioFkNew);
            }
            if (cidadeEleitoralFkNew != null) {
                cidadeEleitoralFkNew = em.getReference(cidadeEleitoralFkNew.getClass(), cidadeEleitoralFkNew.getId());
                pessoaFisicaHistorico.setCidadeEleitoralFk(cidadeEleitoralFkNew);
            }
            if (estadoEleitoralFkNew != null) {
                estadoEleitoralFkNew = em.getReference(estadoEleitoralFkNew.getClass(), estadoEleitoralFkNew.getId());
                pessoaFisicaHistorico.setEstadoEleitoralFk(estadoEleitoralFkNew);
            }
            pessoaFisicaHistorico = em.merge(pessoaFisicaHistorico);
            if (cidadeFkOld != null && !cidadeFkOld.equals(cidadeFkNew)) {
                cidadeFkOld.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistorico);
                cidadeFkOld = em.merge(cidadeFkOld);
            }
            if (cidadeFkNew != null && !cidadeFkNew.equals(cidadeFkOld)) {
                cidadeFkNew.getPessoaFisicaHistoricoCollection().add(pessoaFisicaHistorico);
                cidadeFkNew = em.merge(cidadeFkNew);
            }
            if (rgUfFkOld != null && !rgUfFkOld.equals(rgUfFkNew)) {
                rgUfFkOld.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistorico);
                rgUfFkOld = em.merge(rgUfFkOld);
            }
            if (rgUfFkNew != null && !rgUfFkNew.equals(rgUfFkOld)) {
                rgUfFkNew.getPessoaFisicaHistoricoCollection().add(pessoaFisicaHistorico);
                rgUfFkNew = em.merge(rgUfFkNew);
            }
            if (estadoFkOld != null && !estadoFkOld.equals(estadoFkNew)) {
                estadoFkOld.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistorico);
                estadoFkOld = em.merge(estadoFkOld);
            }
            if (estadoFkNew != null && !estadoFkNew.equals(estadoFkOld)) {
                estadoFkNew.getPessoaFisicaHistoricoCollection().add(pessoaFisicaHistorico);
                estadoFkNew = em.merge(estadoFkNew);
            }
            if (estadoCivilFkOld != null && !estadoCivilFkOld.equals(estadoCivilFkNew)) {
                estadoCivilFkOld.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistorico);
                estadoCivilFkOld = em.merge(estadoCivilFkOld);
            }
            if (estadoCivilFkNew != null && !estadoCivilFkNew.equals(estadoCivilFkOld)) {
                estadoCivilFkNew.getPessoaFisicaHistoricoCollection().add(pessoaFisicaHistorico);
                estadoCivilFkNew = em.merge(estadoCivilFkNew);
            }
            if (nacionalidadeFkOld != null && !nacionalidadeFkOld.equals(nacionalidadeFkNew)) {
                nacionalidadeFkOld.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistorico);
                nacionalidadeFkOld = em.merge(nacionalidadeFkOld);
            }
            if (nacionalidadeFkNew != null && !nacionalidadeFkNew.equals(nacionalidadeFkOld)) {
                nacionalidadeFkNew.getPessoaFisicaHistoricoCollection().add(pessoaFisicaHistorico);
                nacionalidadeFkNew = em.merge(nacionalidadeFkNew);
            }
            if (paisFkOld != null && !paisFkOld.equals(paisFkNew)) {
                paisFkOld.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistorico);
                paisFkOld = em.merge(paisFkOld);
            }
            if (paisFkNew != null && !paisFkNew.equals(paisFkOld)) {
                paisFkNew.getPessoaFisicaHistoricoCollection().add(pessoaFisicaHistorico);
                paisFkNew = em.merge(paisFkNew);
            }
            if (pessoaFisicaFkOld != null && !pessoaFisicaFkOld.equals(pessoaFisicaFkNew)) {
                pessoaFisicaFkOld.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistorico);
                pessoaFisicaFkOld = em.merge(pessoaFisicaFkOld);
            }
            if (pessoaFisicaFkNew != null && !pessoaFisicaFkNew.equals(pessoaFisicaFkOld)) {
                pessoaFisicaFkNew.getPessoaFisicaHistoricoCollection().add(pessoaFisicaHistorico);
                pessoaFisicaFkNew = em.merge(pessoaFisicaFkNew);
            }
            if (usuarioFkOld != null && !usuarioFkOld.equals(usuarioFkNew)) {
                usuarioFkOld.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistorico);
                usuarioFkOld = em.merge(usuarioFkOld);
            }
            if (usuarioFkNew != null && !usuarioFkNew.equals(usuarioFkOld)) {
                usuarioFkNew.getPessoaFisicaHistoricoCollection().add(pessoaFisicaHistorico);
                usuarioFkNew = em.merge(usuarioFkNew);
            }
            if (cidadeEleitoralFkOld != null && !cidadeEleitoralFkOld.equals(cidadeEleitoralFkNew)) {
                cidadeEleitoralFkOld.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistorico);
                cidadeEleitoralFkOld = em.merge(cidadeEleitoralFkOld);
            }
            if (cidadeEleitoralFkNew != null && !cidadeEleitoralFkNew.equals(cidadeEleitoralFkOld)) {
                cidadeEleitoralFkNew.getPessoaFisicaHistoricoCollection().add(pessoaFisicaHistorico);
                cidadeEleitoralFkNew = em.merge(cidadeEleitoralFkNew);
            }
            if (estadoEleitoralFkOld != null && !estadoEleitoralFkOld.equals(estadoEleitoralFkNew)) {
                estadoEleitoralFkOld.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistorico);
                estadoEleitoralFkOld = em.merge(estadoEleitoralFkOld);
            }
            if (estadoEleitoralFkNew != null && !estadoEleitoralFkNew.equals(estadoEleitoralFkOld)) {
                estadoEleitoralFkNew.getPessoaFisicaHistoricoCollection().add(pessoaFisicaHistorico);
                estadoEleitoralFkNew = em.merge(estadoEleitoralFkNew);
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
                Integer id = pessoaFisicaHistorico.getId();
                if (findPessoaFisicaHistorico(id) == null) {
                    throw new NonexistentEntityException("The pessoaFisicaHistorico with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PessoaFisicaHistorico pessoaFisicaHistorico;
            try {
                pessoaFisicaHistorico = em.getReference(PessoaFisicaHistorico.class, id);
                pessoaFisicaHistorico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pessoaFisicaHistorico with id " + id + " no longer exists.", enfe);
            }
            Cidade cidadeFk = pessoaFisicaHistorico.getCidadeFk();
            if (cidadeFk != null) {
                cidadeFk.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistorico);
                cidadeFk = em.merge(cidadeFk);
            }
            Estado rgUfFk = pessoaFisicaHistorico.getRgUfFk();
            if (rgUfFk != null) {
                rgUfFk.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistorico);
                rgUfFk = em.merge(rgUfFk);
            }
            Estado estadoFk = pessoaFisicaHistorico.getEstadoFk();
            if (estadoFk != null) {
                estadoFk.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistorico);
                estadoFk = em.merge(estadoFk);
            }
            EstadoCivil estadoCivilFk = pessoaFisicaHistorico.getEstadoCivilFk();
            if (estadoCivilFk != null) {
                estadoCivilFk.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistorico);
                estadoCivilFk = em.merge(estadoCivilFk);
            }
            Nacionalidade nacionalidadeFk = pessoaFisicaHistorico.getNacionalidadeFk();
            if (nacionalidadeFk != null) {
                nacionalidadeFk.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistorico);
                nacionalidadeFk = em.merge(nacionalidadeFk);
            }
            Pais paisFk = pessoaFisicaHistorico.getPaisFk();
            if (paisFk != null) {
                paisFk.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistorico);
                paisFk = em.merge(paisFk);
            }
            PessoaFisica pessoaFisicaFk = pessoaFisicaHistorico.getPessoaFisicaFk();
            if (pessoaFisicaFk != null) {
                pessoaFisicaFk.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistorico);
                pessoaFisicaFk = em.merge(pessoaFisicaFk);
            }
            Usuario usuarioFk = pessoaFisicaHistorico.getUsuarioFk();
            if (usuarioFk != null) {
                usuarioFk.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistorico);
                usuarioFk = em.merge(usuarioFk);
            }
            Cidade cidadeEleitoralFk = pessoaFisicaHistorico.getCidadeEleitoralFk();
            if (cidadeEleitoralFk != null) {
                cidadeEleitoralFk.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistorico);
                cidadeEleitoralFk = em.merge(cidadeEleitoralFk);
            }
            Estado estadoEleitoralFk = pessoaFisicaHistorico.getEstadoEleitoralFk();
            if (estadoEleitoralFk != null) {
                estadoEleitoralFk.getPessoaFisicaHistoricoCollection().remove(pessoaFisicaHistorico);
                estadoEleitoralFk = em.merge(estadoEleitoralFk);
            }
            em.remove(pessoaFisicaHistorico);
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

    public List<PessoaFisicaHistorico> findPessoaFisicaHistoricoEntities() {
        return findPessoaFisicaHistoricoEntities(true, -1, -1);
    }

    public List<PessoaFisicaHistorico> findPessoaFisicaHistoricoEntities(int maxResults, int firstResult) {
        return findPessoaFisicaHistoricoEntities(false, maxResults, firstResult);
    }

    private List<PessoaFisicaHistorico> findPessoaFisicaHistoricoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PessoaFisicaHistorico.class));
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

    public PessoaFisicaHistorico findPessoaFisicaHistorico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PessoaFisicaHistorico.class, id);
        } finally {
            em.close();
        }
    }

    public int getPessoaFisicaHistoricoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PessoaFisicaHistorico> rt = cq.from(PessoaFisicaHistorico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<PessoaFisicaHistorico> findAllByPF(Integer id){
        EntityManager em = getEntityManager();
        try {
            List<PessoaFisicaHistorico> pessoaFisicaHistoricoList = (List<PessoaFisicaHistorico>) em.createNativeQuery("select * from pessoa_fisica_historico "
                        + "where pessoa_fisica_fk = '"+id+"' order by data_de_modificacao desc", PessoaFisicaHistorico.class).getResultList();
            return pessoaFisicaHistoricoList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
}
