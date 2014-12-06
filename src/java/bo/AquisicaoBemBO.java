/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.AquisicaoBemDAO;
import entidade.AquisicaoBem;
import java.io.Serializable;

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
}