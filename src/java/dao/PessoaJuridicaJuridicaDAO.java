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
import entidade.PessoaJuridica;
import entidade.PessoaJuridicaJuridica;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.transaction.UserTransaction;

/**
 *
 * @author paulones
 */
public class PessoaJuridicaJuridicaDAO implements Serializable {

    public PessoaJuridicaJuridicaDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PessoaJuridicaJuridica pessoaJuridicaJuridica) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            PessoaJuridica pessoaJuridicaSocioAFk = pessoaJuridicaJuridica.getPessoaJuridicaSocioAFk();
            if (pessoaJuridicaSocioAFk != null) {
                pessoaJuridicaSocioAFk = em.getReference(pessoaJuridicaSocioAFk.getClass(), pessoaJuridicaSocioAFk.getId());
                pessoaJuridicaJuridica.setPessoaJuridicaSocioAFk(pessoaJuridicaSocioAFk);
            }
            PessoaJuridica pessoaJuridicaSocioBFk = pessoaJuridicaJuridica.getPessoaJuridicaSocioBFk();
            if (pessoaJuridicaSocioBFk != null) {
                pessoaJuridicaSocioBFk = em.getReference(pessoaJuridicaSocioBFk.getClass(), pessoaJuridicaSocioBFk.getId());
                pessoaJuridicaJuridica.setPessoaJuridicaSocioBFk(pessoaJuridicaSocioBFk);
            }
            em.persist(pessoaJuridicaJuridica);
            if (pessoaJuridicaSocioAFk != null) {
                pessoaJuridicaSocioAFk.getPessoaJuridicaJuridicaCollection().add(pessoaJuridicaJuridica);
                pessoaJuridicaSocioAFk = em.merge(pessoaJuridicaSocioAFk);
            }
            if (pessoaJuridicaSocioBFk != null) {
                pessoaJuridicaSocioBFk.getPessoaJuridicaJuridicaCollection().add(pessoaJuridicaJuridica);
                pessoaJuridicaSocioBFk = em.merge(pessoaJuridicaSocioBFk);
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

    public void edit(PessoaJuridicaJuridica pessoaJuridicaJuridica) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            PessoaJuridicaJuridica persistentPessoaJuridicaJuridica = em.find(PessoaJuridicaJuridica.class, pessoaJuridicaJuridica.getId());
            PessoaJuridica pessoaJuridicaSocioAFkOld = persistentPessoaJuridicaJuridica.getPessoaJuridicaSocioAFk();
            PessoaJuridica pessoaJuridicaSocioAFkNew = pessoaJuridicaJuridica.getPessoaJuridicaSocioAFk();
            PessoaJuridica pessoaJuridicaSocioBFkOld = persistentPessoaJuridicaJuridica.getPessoaJuridicaSocioBFk();
            PessoaJuridica pessoaJuridicaSocioBFkNew = pessoaJuridicaJuridica.getPessoaJuridicaSocioBFk();
            if (pessoaJuridicaSocioAFkNew != null) {
                pessoaJuridicaSocioAFkNew = em.getReference(pessoaJuridicaSocioAFkNew.getClass(), pessoaJuridicaSocioAFkNew.getId());
                pessoaJuridicaJuridica.setPessoaJuridicaSocioAFk(pessoaJuridicaSocioAFkNew);
            }
            if (pessoaJuridicaSocioBFkNew != null) {
                pessoaJuridicaSocioBFkNew = em.getReference(pessoaJuridicaSocioBFkNew.getClass(), pessoaJuridicaSocioBFkNew.getId());
                pessoaJuridicaJuridica.setPessoaJuridicaSocioBFk(pessoaJuridicaSocioBFkNew);
            }
            pessoaJuridicaJuridica = em.merge(pessoaJuridicaJuridica);
            if (pessoaJuridicaSocioAFkOld != null && !pessoaJuridicaSocioAFkOld.equals(pessoaJuridicaSocioAFkNew)) {
                pessoaJuridicaSocioAFkOld.getPessoaJuridicaJuridicaCollection().remove(pessoaJuridicaJuridica);
                pessoaJuridicaSocioAFkOld = em.merge(pessoaJuridicaSocioAFkOld);
            }
            if (pessoaJuridicaSocioAFkNew != null && !pessoaJuridicaSocioAFkNew.equals(pessoaJuridicaSocioAFkOld)) {
                pessoaJuridicaSocioAFkNew.getPessoaJuridicaJuridicaCollection().add(pessoaJuridicaJuridica);
                pessoaJuridicaSocioAFkNew = em.merge(pessoaJuridicaSocioAFkNew);
            }
            if (pessoaJuridicaSocioBFkOld != null && !pessoaJuridicaSocioBFkOld.equals(pessoaJuridicaSocioBFkNew)) {
                pessoaJuridicaSocioBFkOld.getPessoaJuridicaJuridicaCollection().remove(pessoaJuridicaJuridica);
                pessoaJuridicaSocioBFkOld = em.merge(pessoaJuridicaSocioBFkOld);
            }
            if (pessoaJuridicaSocioBFkNew != null && !pessoaJuridicaSocioBFkNew.equals(pessoaJuridicaSocioBFkOld)) {
                pessoaJuridicaSocioBFkNew.getPessoaJuridicaJuridicaCollection().add(pessoaJuridicaJuridica);
                pessoaJuridicaSocioBFkNew = em.merge(pessoaJuridicaSocioBFkNew);
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
                Integer id = pessoaJuridicaJuridica.getId();
                if (findPessoaJuridicaJuridica(id) == null) {
                    throw new NonexistentEntityException("The pessoaJuridicaJuridica with id " + id + " no longer exists.");
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
            em = getEntityManager();em.getTransaction().begin();
            PessoaJuridicaJuridica pessoaJuridicaJuridica;
            try {
                pessoaJuridicaJuridica = em.getReference(PessoaJuridicaJuridica.class, id);
                pessoaJuridicaJuridica.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pessoaJuridicaJuridica with id " + id + " no longer exists.", enfe);
            }
            PessoaJuridica pessoaJuridicaSocioAFk = pessoaJuridicaJuridica.getPessoaJuridicaSocioAFk();
            if (pessoaJuridicaSocioAFk != null) {
                pessoaJuridicaSocioAFk.getPessoaJuridicaJuridicaCollection().remove(pessoaJuridicaJuridica);
                pessoaJuridicaSocioAFk = em.merge(pessoaJuridicaSocioAFk);
            }
            PessoaJuridica pessoaJuridicaSocioBFk = pessoaJuridicaJuridica.getPessoaJuridicaSocioBFk();
            if (pessoaJuridicaSocioBFk != null) {
                pessoaJuridicaSocioBFk.getPessoaJuridicaJuridicaCollection().remove(pessoaJuridicaJuridica);
                pessoaJuridicaSocioBFk = em.merge(pessoaJuridicaSocioBFk);
            }
            em.remove(pessoaJuridicaJuridica);
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

    public List<PessoaJuridicaJuridica> findPessoaJuridicaJuridicaEntities() {
        return findPessoaJuridicaJuridicaEntities(true, -1, -1);
    }

    public List<PessoaJuridicaJuridica> findPessoaJuridicaJuridicaEntities(int maxResults, int firstResult) {
        return findPessoaJuridicaJuridicaEntities(false, maxResults, firstResult);
    }

    private List<PessoaJuridicaJuridica> findPessoaJuridicaJuridicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PessoaJuridicaJuridica.class));
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

    public PessoaJuridicaJuridica findPessoaJuridicaJuridica(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PessoaJuridicaJuridica.class, id);
        } finally {
            em.close();
        }
    }

    public int getPessoaJuridicaJuridicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PessoaJuridicaJuridica> rt = cq.from(PessoaJuridicaJuridica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public void destroyByPJA(Integer idPj){
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.createNativeQuery("delete from pessoa_juridica_juridica "
                    + "where pessoa_juridica_socio_a_fk = '" + idPj + "'").executeUpdate();
            em.getTransaction().commit();
        } catch (NoResultException e) {
        } finally {
            em.close();
        }
    }
    
    public void destroyByPJB(Integer idPj){
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.createNativeQuery("delete from pessoa_juridica_juridica "
                    + "where pessoa_juridica_socio_b_fk = '" + idPj + "'").executeUpdate();
            em.getTransaction().commit();
        } catch (NoResultException e) {
        } finally {
            em.close();
        }
    }
    
    public List<PessoaJuridicaJuridica> findAllByPJA(Integer id){
        EntityManager em = getEntityManager();
        try {
            List<PessoaJuridicaJuridica> pessoaJuridicaJuridicaList = (List<PessoaJuridicaJuridica>) em.createNativeQuery("select * from pessoa_juridica_juridica "
                        + "where pessoa_juridica_socio_a_fk = '"+id+"'", PessoaJuridicaJuridica.class).getResultList();
            return pessoaJuridicaJuridicaList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public List<PessoaJuridicaJuridica> findAllByPJB(Integer id){
        EntityManager em = getEntityManager();
        try {
            List<PessoaJuridicaJuridica> pessoaJuridicaJuridicaList = (List<PessoaJuridicaJuridica>) em.createNativeQuery("select * from pessoa_juridica_juridica "
                        + "where pessoa_juridica_socio_b_fk = '"+id+"'", PessoaJuridicaJuridica.class).getResultList();
            return pessoaJuridicaJuridicaList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public PessoaJuridicaJuridica findByPJAndPJ(Integer idPja, Integer idPjb){
        EntityManager em = getEntityManager();
        try {
            PessoaJuridicaJuridica pessoaJuridicaJuridica = (PessoaJuridicaJuridica) em.createNativeQuery("select * from pessoa_juridica_juridica "
                    + "where pessoa_juridica_socio_a_fk = '" + idPja + "' pessoa_juridica_socio_b_fk = '" + idPjb + "'", PessoaJuridicaJuridica.class).getSingleResult();
            return pessoaJuridicaJuridica;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
}
