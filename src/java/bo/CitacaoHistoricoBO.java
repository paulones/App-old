/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.CitacaoHistoricoDAO;
import entidade.CitacaoHistorico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class CitacaoHistoricoBO implements Serializable{
    
    public static void create(CitacaoHistorico citacaoHistorico){
        try {
            CitacaoHistoricoDAO citacaoHistoricoDAO = new CitacaoHistoricoDAO();
            citacaoHistoricoDAO.create(citacaoHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(CitacaoHistorico citacaoHistorico){
        try {
            CitacaoHistoricoDAO citacaoHistoricoDAO = new CitacaoHistoricoDAO();
            citacaoHistoricoDAO.edit(citacaoHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroy(CitacaoHistorico citacaoHistorico){
        try {
            CitacaoHistoricoDAO citacaoHistoricoDAO = new CitacaoHistoricoDAO();
            citacaoHistoricoDAO.destroy(citacaoHistorico.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<CitacaoHistorico> findByPJUD(Integer id){
        try { 
            CitacaoHistoricoDAO citacaoHistoricoDAO = new CitacaoHistoricoDAO();
            return citacaoHistoricoDAO.findByPJUD(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<CitacaoHistorico>();
    }
    
}
