/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.PenhoraDAO;
import entidade.Penhora;
import java.io.Serializable;

/**
 *
 * @author paulones
 */
public class PenhoraBO implements Serializable{
    
    private PenhoraDAO penhoraDAO;

    public PenhoraBO(){
        penhoraDAO = new PenhoraDAO();
    }
    
    public void create(Penhora penhora){
        try {
            penhoraDAO.create(penhora);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void edit(Penhora penhora){
        try {
            penhoraDAO.edit(penhora);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void destroy(Penhora penhora){
        try {
            penhoraDAO.destroy(penhora.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
