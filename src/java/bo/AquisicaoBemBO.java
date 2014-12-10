/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.AquisicaoBemDAO;
import entidade.AquisicaoBem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class AquisicaoBemBO implements Serializable{
    
    public static void create(AquisicaoBem aquisicaoBem){
        try {
            AquisicaoBemDAO aquisicaoBemDAO = new AquisicaoBemDAO();
            aquisicaoBemDAO.create(aquisicaoBem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(AquisicaoBem aquisicaoBem){
        try {
            AquisicaoBemDAO aquisicaoBemDAO = new AquisicaoBemDAO();
            aquisicaoBemDAO.edit(aquisicaoBem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroy(AquisicaoBem aquisicaoBem){
        try {
            AquisicaoBemDAO aquisicaoBemDAO = new AquisicaoBemDAO();
            aquisicaoBemDAO.destroy(aquisicaoBem.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<AquisicaoBem> findByPJUD(Integer id){
        try { 
            AquisicaoBemDAO aquisicaoBemDAO = new AquisicaoBemDAO();
            return aquisicaoBemDAO.findByPJUD(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<AquisicaoBem>();
    }
    
    public static void destroyByPJUD(Integer idPjud){
        try {
            AquisicaoBemDAO aquisicaoBemDAO = new AquisicaoBemDAO();
            aquisicaoBemDAO.destroyByPJUD(idPjud);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}