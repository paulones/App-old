/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

/**
 *
 * @author paulones
 */
public class UtilDAO implements Serializable {
    
    public UtilDAO() {
    }

    private transient EntityManagerFactory emf = JPAUtil.getEMF();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public Timestamp findServerTime() {
        EntityManager em = getEntityManager();
        try {
            Timestamp timestamp = (Timestamp) em.createNativeQuery("select current_timestamp").getSingleResult();
            return timestamp;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
}
