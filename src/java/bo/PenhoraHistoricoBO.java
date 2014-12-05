/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.PenhoraHistoricoDAO;
import entidade.PenhoraHistorico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class PenhoraHistoricoBO implements Serializable{
    
    public static void create(PenhoraHistorico penhoraHistorico){
        try {
            PenhoraHistoricoDAO penhoraHistoricoDAO = new PenhoraHistoricoDAO();
            penhoraHistoricoDAO.create(penhoraHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(PenhoraHistorico penhoraHistorico){
        try {
            PenhoraHistoricoDAO penhoraHistoricoDAO = new PenhoraHistoricoDAO();
            penhoraHistoricoDAO.edit(penhoraHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroy(PenhoraHistorico penhoraHistorico){
        try {
            PenhoraHistoricoDAO penhoraHistoricoDAO = new PenhoraHistoricoDAO();
            penhoraHistoricoDAO.destroy(penhoraHistorico.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<PenhoraHistorico> findByPJUD(Integer id){
        try { 
            PenhoraHistoricoDAO penhoraHistoricoDAO = new PenhoraHistoricoDAO();
            return penhoraHistoricoDAO.findByPJUD(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PenhoraHistorico>();
    }
}