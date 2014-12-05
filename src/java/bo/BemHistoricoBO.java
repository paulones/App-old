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
    
    
    public static void create(BemHistorico bemHistorico){
        try {
            BemHistoricoDAO bemHistoricoDAO = new BemHistoricoDAO();
            bemHistoricoDAO.create(bemHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(BemHistorico bemHistorico){
        try {
            BemHistoricoDAO bemHistoricoDAO = new BemHistoricoDAO();
            bemHistoricoDAO.edit(bemHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroy(BemHistorico bemHistorico){
        try {
            BemHistoricoDAO bemHistoricoDAO = new BemHistoricoDAO();
            bemHistoricoDAO.destroy(bemHistorico.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<BemHistorico> findAllByPF(Integer id){
        try { 
            BemHistoricoDAO bemHistoricoDAO = new BemHistoricoDAO();
            return bemHistoricoDAO.findAllByPF(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    public static List<BemHistorico> findAllByPJ(Integer id){
        try { 
            BemHistoricoDAO bemHistoricoDAO = new BemHistoricoDAO();
            return bemHistoricoDAO.findAllByPJ(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
