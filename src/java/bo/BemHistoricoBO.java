/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.BemHistoricoDAO;
import entidade.BemHistorico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class BemHistoricoBO implements Serializable{
    
    private BemHistoricoDAO bemHistoricoDAO;
    
    public BemHistoricoBO(){
        bemHistoricoDAO = new BemHistoricoDAO();
    }
    
    public void create(BemHistorico bemHistorico){
        try {
            bemHistoricoDAO.create(bemHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void edit(BemHistorico bemHistorico){
        try {
            bemHistoricoDAO.edit(bemHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void destroy(BemHistorico bemHistorico){
        try {
            bemHistoricoDAO.destroy(bemHistorico.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<BemHistorico> findAllByPF(Integer id){
        try { 
            return bemHistoricoDAO.findAllByPF(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    public List<BemHistorico> findAllByPJ(Integer id){
        try { 
            return bemHistoricoDAO.findAllByPJ(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
