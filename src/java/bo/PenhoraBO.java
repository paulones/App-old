/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.PenhoraDAO;
import entidade.Penhora;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class PenhoraBO implements Serializable{
    
    public static void create(Penhora penhora){
        try {
            PenhoraDAO penhoraDAO = new PenhoraDAO();
            penhoraDAO.create(penhora);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(Penhora penhora){
        try {
            PenhoraDAO penhoraDAO = new PenhoraDAO();
            penhoraDAO.edit(penhora);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroy(Penhora penhora){
        try {
            PenhoraDAO penhoraDAO = new PenhoraDAO();
            penhoraDAO.destroy(penhora.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<Penhora> findByPJUD(Integer id){
        try { 
            PenhoraDAO penhoraDAO = new PenhoraDAO();
            return penhoraDAO.findByPJUD(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Penhora>();
    }

    public static void destroyByPJUD(Integer idPjud){
        try {
            PenhoraDAO penhoraDAO = new PenhoraDAO();
            penhoraDAO.destroyByPJUD(idPjud);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
