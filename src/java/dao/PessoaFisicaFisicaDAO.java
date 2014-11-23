/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.PessoaFisica;
import entidade.PessoaFisicaFisica;
import entidade.VinculoSocial;
import java.io.Serializable;
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
public class PessoaFisicaFisicaDAO implements Serializable {

    public PessoaFisicaFisicaDAO() {
    }
    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PessoaFisicaFisica pessoaFisicaFisica) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            PessoaFisica pessoaFisicaAFk = pessoaFisicaFisica.getPessoaFisicaAFk();
            if (pessoaFisicaAFk != null) {
                pessoaFisicaAFk = em.getReference(pessoaFisicaAFk.getClass(), pessoaFisicaAFk.getId());
                pessoaFisicaFisica.setPessoaFisicaAFk(pessoaFisicaAFk);
            }
            PessoaFisica pessoaFisicaBFk = pessoaFisicaFisica.getPessoaFisicaBFk();
            if (pessoaFisicaBFk != null) {
                pessoaFisicaBFk = em.getReference(pessoaFisicaBFk.getClass(), pessoaFisicaBFk.getId());
                pessoaFisicaFisica.setPessoaFisicaBFk(pessoaFisicaBFk);
            }
            VinculoSocial vinculoSocialFk = pessoaFisicaFisica.getVinculoSocialFk();
            if (vinculoSocialFk != null) {
                vinculoSocialFk = em.getReference(vinculoSocialFk.getClass(), vinculoSocialFk.getId());
                pessoaFisicaFisica.setVinculoSocialFk(vinculoSocialFk);
            }
            em.persist(pessoaFisicaFisica);
            if (pessoaFisicaAFk != null) {
                pessoaFisicaAFk.getPessoaFisicaFisicaCollection().add(pessoaFisicaFisica);
                pessoaFisicaAFk = em.merge(pessoaFisicaAFk);
            }
            if (pessoaFisicaBFk != null) {
                pessoaFisicaBFk.getPessoaFisicaFisicaCollection().add(pessoaFisicaFisica);
                pessoaFisicaBFk = em.merge(pessoaFisicaBFk);
            }
            if (vinculoSocialFk != null) {
                vinculoSocialFk.getPessoaFisicaFisicaCollection().add(pessoaFisicaFisica);
                vinculoSocialFk = em.merge(vinculoSocialFk);
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

    public void edit(PessoaFisicaFisica pessoaFisicaFisica) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();em.getTransaction().begin();
            PessoaFisicaFisica persistentPessoaFisicaFisica = em.find(PessoaFisicaFisica.class, pessoaFisicaFisica.getId());
            PessoaFisica pessoaFisicaAFkOld = persistentPessoaFisicaFisica.getPessoaFisicaAFk();
            PessoaFisica pessoaFisicaAFkNew = pessoaFisicaFisica.getPessoaFisicaAFk();
            PessoaFisica pessoaFisicaBFkOld = persistentPessoaFisicaFisica.getPessoaFisicaBFk();
            PessoaFisica pessoaFisicaBFkNew = pessoaFisicaFisica.getPessoaFisicaBFk();
            VinculoSocial vinculoSocialFkOld = persistentPessoaFisicaFisica.getVinculoSocialFk();
            VinculoSocial vinculoSocialFkNew = pessoaFisicaFisica.getVinculoSocialFk();
            if (pessoaFisicaAFkNew != null) {
                pessoaFisicaAFkNew = em.getReference(pessoaFisicaAFkNew.getClass(), pessoaFisicaAFkNew.getId());
                pessoaFisicaFisica.setPessoaFisicaAFk(pessoaFisicaAFkNew);
            }
            if (pessoaFisicaBFkNew != null) {
                pessoaFisicaBFkNew = em.getReference(pessoaFisicaBFkNew.getClass(), pessoaFisicaBFkNew.getId());
                pessoaFisicaFisica.setPessoaFisicaBFk(pessoaFisicaBFkNew);
            }
            if (vinculoSocialFkNew != null) {
                vinculoSocialFkNew = em.getReference(vinculoSocialFkNew.getClass(), vinculoSocialFkNew.getId());
                pessoaFisicaFisica.setVinculoSocialFk(vinculoSocialFkNew);
            }
            pessoaFisicaFisica = em.merge(pessoaFisicaFisica);
            if (pessoaFisicaAFkOld != null && !pessoaFisicaAFkOld.equals(pessoaFisicaAFkNew)) {
                pessoaFisicaAFkOld.getPessoaFisicaFisicaCollection().remove(pessoaFisicaFisica);
                pessoaFisicaAFkOld = em.merge(pessoaFisicaAFkOld);
            }
            if (pessoaFisicaAFkNew != null && !pessoaFisicaAFkNew.equals(pessoaFisicaAFkOld)) {
                pessoaFisicaAFkNew.getPessoaFisicaFisicaCollection().add(pessoaFisicaFisica);
                pessoaFisicaAFkNew = em.merge(pessoaFisicaAFkNew);
            }
            if (pessoaFisicaBFkOld != null && !pessoaFisicaBFkOld.equals(pessoaFisicaBFkNew)) {
                pessoaFisicaBFkOld.getPessoaFisicaFisicaCollection().remove(pessoaFisicaFisica);
                pessoaFisicaBFkOld = em.merge(pessoaFisicaBFkOld);
            }
            if (pessoaFisicaBFkNew != null && !pessoaFisicaBFkNew.equals(pessoaFisicaBFkOld)) {
                pessoaFisicaBFkNew.getPessoaFisicaFisicaCollection().add(pessoaFisicaFisica);
                pessoaFisicaBFkNew = em.merge(pessoaFisicaBFkNew);
            }
            if (vinculoSocialFkOld != null && !vinculoSocialFkOld.equals(vinculoSocialFkNew)) {
                vinculoSocialFkOld.getPessoaFisicaFisicaCollection().remove(pessoaFisicaFisica);
                vinculoSocialFkOld = em.merge(vinculoSocialFkOld);
            }
            if (vinculoSocialFkNew != null && !vinculoSocialFkNew.equals(vinculoSocialFkOld)) {
                vinculoSocialFkNew.getPessoaFisicaFisicaCollection().add(pessoaFisicaFisica);
                vinculoSocialFkNew = em.merge(vinculoSocialFkNew);
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
                Integer id = pessoaFisicaFisica.getId();
                if (findPessoaFisicaFisica(id) == null) {
                    throw new NonexistentEntityException("The pessoaFisicaFisica with id " + id + " no longer exists.");
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
            PessoaFisicaFisica pessoaFisicaFisica;
            try {
                pessoaFisicaFisica = em.getReference(PessoaFisicaFisica.class, id);
                pessoaFisicaFisica.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pessoaFisicaFisica with id " + id + " no longer exists.", enfe);
            }
            PessoaFisica pessoaFisicaAFk = pessoaFisicaFisica.getPessoaFisicaAFk();
            if (pessoaFisicaAFk != null) {
                pessoaFisicaAFk.getPessoaFisicaFisicaCollection().remove(pessoaFisicaFisica);
                pessoaFisicaAFk = em.merge(pessoaFisicaAFk);
            }
            PessoaFisica pessoaFisicaBFk = pessoaFisicaFisica.getPessoaFisicaBFk();
            if (pessoaFisicaBFk != null) {
                pessoaFisicaBFk.getPessoaFisicaFisicaCollection().remove(pessoaFisicaFisica);
                pessoaFisicaBFk = em.merge(pessoaFisicaBFk);
            }
            VinculoSocial vinculoSocialFk = pessoaFisicaFisica.getVinculoSocialFk();
            if (vinculoSocialFk != null) {
                vinculoSocialFk.getPessoaFisicaFisicaCollection().remove(pessoaFisicaFisica);
                vinculoSocialFk = em.merge(vinculoSocialFk);
            }
            em.remove(pessoaFisicaFisica);
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

    public List<PessoaFisicaFisica> findPessoaFisicaFisicaEntities() {
        return findPessoaFisicaFisicaEntities(true, -1, -1);
    }

    public List<PessoaFisicaFisica> findPessoaFisicaFisicaEntities(int maxResults, int firstResult) {
        return findPessoaFisicaFisicaEntities(false, maxResults, firstResult);
    }

    private List<PessoaFisicaFisica> findPessoaFisicaFisicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PessoaFisicaFisica.class));
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

    public PessoaFisicaFisica findPessoaFisicaFisica(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PessoaFisicaFisica.class, id);
        } finally {
            em.close();
        }
    }

    public int getPessoaFisicaFisicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PessoaFisicaFisica> rt = cq.from(PessoaFisicaFisica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public void destroyByPFBOrPFA(Integer idPf){
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.createNativeQuery("delete from pessoa_fisica_fisica "
                    + "where pessoa_fisica_b_fk = '" + idPf + "' or pessoa_fisica_a_fk = '" + idPf + "'").executeUpdate();
            em.getTransaction().commit();
        } catch (NoResultException e) {
        } finally {
            em.close();
        }
    }
    
    public List<PessoaFisicaFisica> findAllByPFAOrPFB(Integer id){
        EntityManager em = getEntityManager();
        try {
            List<PessoaFisicaFisica> pessoaFisicaFisicaList = (List<PessoaFisicaFisica>) em.createNativeQuery("select pff.id, pff.pessoa_fisica_a_fk, pff.pessoa_fisica_b_fk, pff.vinculo_social_fk "
                        + "from pessoa_fisica_fisica pff, pessoa_fisica pf where pf.id = '"+id+"' and "
                        + "(pff.pessoa_fisica_a_fk = pf.id or pff.pessoa_fisica_b_fk = pf.id) "
                        + "group by pff.id, pff.pessoa_fisica_a_fk, pff.pessoa_fisica_b_fk, pff.vinculo_social_fk", PessoaFisicaFisica.class).getResultList();
            return pessoaFisicaFisicaList;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
}
