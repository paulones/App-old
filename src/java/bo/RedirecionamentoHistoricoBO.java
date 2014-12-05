/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.RedirecionamentoHistoricoDAO;
import entidade.RedirecionamentoHistorico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class RedirecionamentoHistoricoBO implements Serializable{
    
    public static void create(RedirecionamentoHistorico redirecionamentoHistorico){
        try {
            RedirecionamentoHistoricoDAO redirecionamentoHistoricoDAO = new RedirecionamentoHistoricoDAO();
            redirecionamentoHistoricoDAO.create(redirecionamentoHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(RedirecionamentoHistorico redirecionamentoHistorico){
        try {
            RedirecionamentoHistoricoDAO redirecionamentoHistoricoDAO = new RedirecionamentoHistoricoDAO();
            redirecionamentoHistoricoDAO.edit(redirecionamentoHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroy(RedirecionamentoHistorico redirecionamentoHistorico){
        try {
            RedirecionamentoHistoricoDAO redirecionamentoHistoricoDAO = new RedirecionamentoHistoricoDAO();
            redirecionamentoHistoricoDAO.destroy(redirecionamentoHistorico.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<RedirecionamentoHistorico> findByPJUD(Integer id){
        try { 
            RedirecionamentoHistoricoDAO redirecionamentoHistoricoDAO = new RedirecionamentoHistoricoDAO();
            return redirecionamentoHistoricoDAO.findByPJUD(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<RedirecionamentoHistorico>();
    }
}
