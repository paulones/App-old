/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.TipoPenhoraDAO;
import entidade.TipoPenhora;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class TipoPenhoraBO implements Serializable{
    
    public static List<TipoPenhora> findAll(){
        try { 
            TipoPenhoraDAO tipoPenhoraDAO = new TipoPenhoraDAO();
            return tipoPenhoraDAO.findTipoPenhoraEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<TipoPenhora>();
    }
    
    public static List<TipoPenhora> findPenhorasSemSocio(){
        try { 
            TipoPenhoraDAO tipoPenhoraDAO = new TipoPenhoraDAO();
            return tipoPenhoraDAO.findPenhorasSemSocio();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<TipoPenhora>();
    }
}
