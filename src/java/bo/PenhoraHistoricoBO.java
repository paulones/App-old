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
    
    private PenhoraHistoricoDAO penhoraHistoricoDAO;

    public PenhoraHistoricoBO(){
        penhoraHistoricoDAO = new PenhoraHistoricoDAO();
    }
    
    public void create(PenhoraHistorico penhoraHistorico){
        try {
            penhoraHistoricoDAO.create(penhoraHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void edit(PenhoraHistorico penhoraHistorico){
        try {
            penhoraHistoricoDAO.edit(penhoraHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void destroy(PenhoraHistorico penhoraHistorico){
        try {
            penhoraHistoricoDAO.destroy(penhoraHistorico.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<PenhoraHistorico> findByPJUD(Integer id){
        try { 
            return penhoraHistoricoDAO.findByPJUD(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PenhoraHistorico>();
    }
}