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
            PessoaJuridica pessoaJuridicaPrimariaFk = pessoaJuridicaJuridica.getPessoaJuridicaPrimariaFk();
            if (pessoaJuridicaPrimariaFk != null) {
                pessoaJuridicaPrimariaFk = em.getReference(pessoaJuridicaPrimariaFk.getClass(), pessoaJuridicaPrimariaFk.getId());
                pessoaJuridicaJuridica.setPessoaJuridicaPrimariaFk(pessoaJuridicaPrimariaFk);
            }
            PessoaJuridica pessoaJuridicaSecundariaFk = pessoaJuridicaJuridica.getPessoaJuridicaSecundariaFk();
            if (pessoaJuridicaSecundariaFk != null) {
                pessoaJuridicaSecundariaFk = em.getReference(pessoaJuridicaSecundariaFk.getClass(), pessoaJuridicaSecundariaFk.getId());
                pessoaJuridicaJuridica.setPessoaJuridicaSecundariaFk(pessoaJuridicaSecundariaFk);
            }
            em.persist(pessoaJuridicaJuridica);
            if (pessoaJuridicaPrimariaFk != null) {
                pessoaJuridicaPrimariaFk.getPessoaJuridicaJuridicaCollection().add(pessoaJuridicaJuridica);
                pessoaJuridicaPrimariaFk = em.merge(pessoaJuridicaPrimariaFk);
            }
            if (pessoaJuridicaSecundariaFk != null) {
                pessoaJuridicaSecundariaFk.getPessoaJuridicaJuridicaCollection().add(pessoaJuridicaJuridica);
                pessoaJuridicaSecundariaFk = em.merge(pessoaJuridicaSecundariaFk);
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
            PessoaJuridica pessoaJuridicaPrimariaFkOld = persistentPessoaJuridicaJuridica.getPessoaJuridicaPrimariaFk();
            PessoaJuridica pessoaJuridicaPrimariaFkNew = pessoaJuridicaJuridica.getPessoaJuridicaPrimariaFk();
            PessoaJuridica pessoaJuridicaSecundariaFkOld = persistentPessoaJuridicaJuridica.getPessoaJuridicaSecundariaFk();
            PessoaJuridica pessoaJuridicaSecundariaFkNew = pessoaJuridicaJuridica.getPessoaJuridicaSecundariaFk();
            if (pessoaJuridicaPrimariaFkNew != null) {
                pessoaJuridicaPrimariaFkNew = em.getReference(pessoaJuridicaPrimariaFkNew.getClass(), pessoaJuridicaPrimariaFkNew.getId());
                pessoaJuridicaJuridica.setPessoaJuridicaPrimariaFk(pessoaJuridicaPrimariaFkNew);
            }
            if (pessoaJuridicaSecundariaFkNew != null) {
                pessoaJuridicaSecundariaFkNew = em.getReference(pessoaJuridicaSecundariaFkNew.getClass(), pessoaJuridicaSecundariaFkNew.getId());
                pessoaJuridicaJuridica.setPessoaJuridicaSecundariaFk(pessoaJuridicaSecundariaFkNew);
            }
            pessoaJuridicaJuridica = em.merge(pessoaJuridicaJuridica);
            if (pessoaJuridicaPrimariaFkOld != null && !pessoaJuridicaPrimariaFkOld.equals(pessoaJuridicaPrimariaFkNew)) {
                pessoaJuridicaPrimariaFkOld.getPessoaJuridicaJuridicaCollection().remove(pessoaJuridicaJuridica);
                pessoaJuridicaPrimariaFkOld = em.merge(pessoaJuridicaPrimariaFkOld);
            }
            if (pessoaJuridicaPrimariaFkNew != null && !pessoaJuridicaPrimariaFkNew.equals(pessoaJuridicaPrimariaFkOld)) {
                pessoaJuridicaPrimariaFkNew.getPessoaJuridicaJuridicaCollection().add(pessoaJuridicaJuridica);
                pessoaJuridicaPrimariaFkNew = em.merge(pessoaJuridicaPrimariaFkNew);
            }
            if (pessoaJuridicaSecundariaFkOld != null && !pessoaJuridicaSecundariaFkOld.equals(pessoaJuridicaSecundariaFkNew)) {
                pessoaJuridicaSecundariaFkOld.getPessoaJuridicaJuridicaCollection().remove(pessoaJuridicaJuridica);
                pessoaJuridicaSecundariaFkOld = em.merge(pessoaJuridicaSecundariaFkOld);
            }
            if (pessoaJuridicaSecundariaFkNew != null && !pessoaJuridicaSecundariaFkNew.equals(pessoaJuridicaSecundariaFkOld)) {
                pessoaJuridicaSecundariaFkNew.getPessoaJuridicaJuridicaCollection().add(pessoaJuridicaJuridica);
                pessoaJuridicaSecundariaFkNew = em.merge(pessoaJuridicaSecundariaFkNew);
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
            PessoaJuridica pessoaJuridicaPrimariaFk = pessoaJuridicaJuridica.getPessoaJuridicaPrimariaFk();
            if (pessoaJuridicaPrimariaFk != null) {
                pessoaJuridicaPrimariaFk.getPessoaJuridicaJuridicaCollection().remove(pessoaJuridicaJuridica);
                pessoaJuridicaPrimariaFk = em.merge(pessoaJuridicaPrimariaFk);
            }
            PessoaJuridica pessoaJuridicaSecundariaFk = pessoaJuridicaJuridica.getPessoaJuridicaSecundariaFk();
            if (pessoaJuridicaSecundariaFk != null) {
                pessoaJuridicaSecundariaFk.getPessoaJuridicaJuridicaCollection().remove(pessoaJuridicaJuridica);
                pessoaJuridicaSecundariaFk = em.merge(pessoaJuridicaSecundariaFk);
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
    
    public void destroyByPJBOrPJA(Integer idPj){
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.createNativeQuery("delete from pessoa_juridica_juridica "
                    + "where pessoa_juridica_secundaria_fk = '" + idPj + "' or pessoa_juridica_primaria_fk = '" + idPj + "'").executeUpdate();
            em.getTransaction().commit();
        } catch (NoResultException e) {
        } finally {
            em.close();
        }
    }
    
    public List<PessoaJuridicaJuridica> findAllByPJAOrPJB(Integer id){
        EntityManager em = getEntityManager();
        try {
            List<PessoaJuridicaJuridica> pessoaJuridicaJuridicaList = (List<PessoaJuridicaJuridica>) em.createNativeQuery("select pjj.id, pjj.pessoa_juridica_primaria_fk, pjj.pessoa_juridica_secundaria_fk, pjj.capital_de_participacao, pjj.data_de_inicio, data_de_termino "
                        + "from pessoa_juridica_juridica pjj, pessoa_juridica pj where pj.id = '"+id+"' and "
                        + "(pjj.pessoa_juridica_primaria_fk = pj.id or pjj.pessoa_juridica_secundaria_fk = pj.id) "
                        + "group by pjj.id, pjj.pessoa_juridica_primaria_fk, pjj.pessoa_juridica_secundaria_fk, pjj.capital_de_participacao, pjj.data_de_inicio, data_de_termino", PessoaJuridicaJuridica.class).getResultList();
            return pessoaJuridicaJuridicaList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
