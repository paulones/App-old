/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.RedirecionamentoDAO;
import entidade.Redirecionamento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class RedirecionamentoBO implements Serializable{
    
    public static void create(Redirecionamento redirecionamento){
        try {
            RedirecionamentoDAO redirecionamentoDAO = new RedirecionamentoDAO();
            redirecionamentoDAO.create(redirecionamento);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(Redirecionamento redirecionamento){
        try {
            RedirecionamentoDAO redirecionamentoDAO = new RedirecionamentoDAO();
            redirecionamentoDAO.edit(redirecionamento);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroy(Redirecionamento redirecionamento){
        try {
            RedirecionamentoDAO redirecionamentoDAO = new RedirecionamentoDAO();
            redirecionamentoDAO.destroy(redirecionamento.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<Redirecionamento> findByPJUD(Integer id){
        try { 
            RedirecionamentoDAO redirecionamentoDAO = new RedirecionamentoDAO();
            return redirecionamentoDAO.findByPJUD(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Redirecionamento>();
    }
    
    public static void destroyByPJUD(Integer idPjud){
        try {
            RedirecionamentoDAO redirecionamentoDAO = new RedirecionamentoDAO();
            redirecionamentoDAO.destroyByPJUD(idPjud);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
