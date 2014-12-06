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
import entidade.Instituicao;
import entidade.Perfil;
import entidade.Usuario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author casa
 */
public class PerfilDAO implements Serializable {

    public PerfilDAO() {
        this.emf = emf;
    }
    private EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Perfil perfil) throws RollbackFailureException, Exception {
        if (perfil.getUsuarioCollection() == null) {
            perfil.setUsuarioCollection(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Instituicao instituicaoFk = perfil.getInstituicaoFk();
            if (instituicaoFk != null) {
                instituicaoFk = em.getReference(instituicaoFk.getClass(), instituicaoFk.getId());
                perfil.setInstituicaoFk(instituicaoFk);
            }
            Collection<Usuario> attachedUsuarioCollection = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionUsuarioToAttach : perfil.getUsuarioCollection()) {
                usuarioCollectionUsuarioToAttach = em.getReference(usuarioCollectionUsuarioToAttach.getClass(), usuarioCollectionUsuarioToAttach.getId());
                attachedUsuarioCollection.add(usuarioCollectionUsuarioToAttach);
            }
            perfil.setUsuarioCollection(attachedUsuarioCollection);
            em.persist(perfil);
            if (instituicaoFk != null) {
                instituicaoFk.getPerfilCollection().add(perfil);
                instituicaoFk = em.merge(instituicaoFk);
            }
            for (Usuario usuarioCollectionUsuario : perfil.getUsuarioCollection()) {
                Perfil oldPerfilOfUsuarioCollectionUsuario = usuarioCollectionUsuario.getPerfil();
                usuarioCollectionUsuario.setPerfil(perfil);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
                if (oldPerfilOfUsuarioCollectionUsuario != null) {
                    oldPerfilOfUsuarioCollectionUsuario.getUsuarioCollection().remove(usuarioCollectionUsuario);
                    oldPerfilOfUsuarioCollectionUsuario = em.merge(oldPerfilOfUsuarioCollectionUsuario);
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

    public void edit(Perfil perfil) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Perfil persistentPerfil = em.find(Perfil.class, perfil.getId());
            Instituicao instituicaoFkOld = persistentPerfil.getInstituicaoFk();
            Instituicao instituicaoFkNew = perfil.getInstituicaoFk();
            Collection<Usuario> usuarioCollectionOld = persistentPerfil.getUsuarioCollection();
            Collection<Usuario> usuarioCollectionNew = perfil.getUsuarioCollection();
            if (instituicaoFkNew != null) {
                instituicaoFkNew = em.getReference(instituicaoFkNew.getClass(), instituicaoFkNew.getId());
                perfil.setInstituicaoFk(instituicaoFkNew);
            }
            Collection<Usuario> attachedUsuarioCollectionNew = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionNewUsuarioToAttach : usuarioCollectionNew) {
                usuarioCollectionNewUsuarioToAttach = em.getReference(usuarioCollectionNewUsuarioToAttach.getClass(), usuarioCollectionNewUsuarioToAttach.getId());
                attachedUsuarioCollectionNew.add(usuarioCollectionNewUsuarioToAttach);
            }
            usuarioCollectionNew = attachedUsuarioCollectionNew;
            perfil.setUsuarioCollection(usuarioCollectionNew);
            perfil = em.merge(perfil);
            if (instituicaoFkOld != null && !instituicaoFkOld.equals(instituicaoFkNew)) {
                instituicaoFkOld.getPerfilCollection().remove(perfil);
                instituicaoFkOld = em.merge(instituicaoFkOld);
            }
            if (instituicaoFkNew != null && !instituicaoFkNew.equals(instituicaoFkOld)) {
                instituicaoFkNew.getPerfilCollection().add(perfil);
                instituicaoFkNew = em.merge(instituicaoFkNew);
            }
            for (Usuario usuarioCollectionOldUsuario : usuarioCollectionOld) {
                if (!usuarioCollectionNew.contains(usuarioCollectionOldUsuario)) {
                    usuarioCollectionOldUsuario.setPerfil(null);
                    usuarioCollectionOldUsuario = em.merge(usuarioCollectionOldUsuario);
                }
            }
            for (Usuario usuarioCollectionNewUsuario : usuarioCollectionNew) {
                if (!usuarioCollectionOld.contains(usuarioCollectionNewUsuario)) {
                    Perfil oldPerfilOfUsuarioCollectionNewUsuario = usuarioCollectionNewUsuario.getPerfil();
                    usuarioCollectionNewUsuario.setPerfil(perfil);
                    usuarioCollectionNewUsuario = em.merge(usuarioCollectionNewUsuario);
                    if (oldPerfilOfUsuarioCollectionNewUsuario != null && !oldPerfilOfUsuarioCollectionNewUsuario.equals(perfil)) {
                        oldPerfilOfUsuarioCollectionNewUsuario.getUsuarioCollection().remove(usuarioCollectionNewUsuario);
                        oldPerfilOfUsuarioCollectionNewUsuario = em.merge(oldPerfilOfUsuarioCollectionNewUsuario);
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
                Integer id = perfil.getId();
                if (findPerfil(id) == null) {
                    throw new NonexistentEntityException("The perfil with id " + id + " no longer exists.");
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
            Perfil perfil;
            try {
                perfil = em.getReference(Perfil.class, id);
                perfil.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The perfil with id " + id + " no longer exists.", enfe);
            }
            Instituicao instituicaoFk = perfil.getInstituicaoFk();
            if (instituicaoFk != null) {
                instituicaoFk.getPerfilCollection().remove(perfil);
                instituicaoFk = em.merge(instituicaoFk);
            }
            Collection<Usuario> usuarioCollection = perfil.getUsuarioCollection();
            for (Usuario usuarioCollectionUsuario : usuarioCollection) {
                usuarioCollectionUsuario.setPerfil(null);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
            }
            em.remove(perfil);
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

    public List<Perfil> findPerfilEntities() {
        return findPerfilEntities(true, -1, -1);
    }

    public List<Perfil> findPerfilEntities(int maxResults, int firstResult) {
        return findPerfilEntities(false, maxResults, firstResult);
    }

    private List<Perfil> findPerfilEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Perfil.class));
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

    public Perfil findPerfil(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Perfil.class, id);
        } finally {
            em.close();
        }
    }

    public int getPerfilCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Perfil> rt = cq.from(Perfil.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
