/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.CitacaoDAO;
import entidade.Citacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class CitacaoBO implements Serializable{
    
    public static void create(Citacao citacao){
        try {
            CitacaoDAO citacaoDAO = new CitacaoDAO();
            citacaoDAO.create(citacao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(Citacao citacao){
        try {
            CitacaoDAO citacaoDAO = new CitacaoDAO();
            citacaoDAO.edit(citacao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroy(Citacao citacao){
        try {
            CitacaoDAO citacaoDAO = new CitacaoDAO();
            citacaoDAO.destroy(citacao.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<Citacao> findByPJUD(Integer id){
        try { 
            CitacaoDAO citacaoDAO = new CitacaoDAO();
            return citacaoDAO.findByPJUD(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Citacao>();
    }

    public static void destroyByPJUD(Integer idPjud){
        try {
            CitacaoDAO citacaoDAO = new CitacaoDAO();
            citacaoDAO.destroyByPJUD(idPjud);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
