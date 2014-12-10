/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.AquisicaoBemDAO;
import dao.AquisicaoBemHistoricoDAO;
import entidade.AquisicaoBemHistorico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class AquisicaoBemHistoricoBO implements Serializable{
    
    public static void create(AquisicaoBemHistorico aquisicaoBemHistorico){
        try {
            AquisicaoBemHistoricoDAO aquisicaoBemHistoricoDAO = new AquisicaoBemHistoricoDAO();
            aquisicaoBemHistoricoDAO.create(aquisicaoBemHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(AquisicaoBemHistorico aquisicaoBemHistorico){
        try {
            AquisicaoBemHistoricoDAO aquisicaoBemHistoricoDAO = new AquisicaoBemHistoricoDAO();
            aquisicaoBemHistoricoDAO.edit(aquisicaoBemHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroy(AquisicaoBemHistorico aquisicaoBemHistorico){
        try {
            AquisicaoBemHistoricoDAO aquisicaoBemHistoricoDAO = new AquisicaoBemHistoricoDAO();
            aquisicaoBemHistoricoDAO.destroy(aquisicaoBemHistorico.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<AquisicaoBemHistorico> findByPJUD(Integer id){
        try { 
            AquisicaoBemHistoricoDAO aquisicaoBemHistoricoDAO = new AquisicaoBemHistoricoDAO();
            return aquisicaoBemHistoricoDAO.findByPJUD(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<AquisicaoBemHistorico>();
    }
}
