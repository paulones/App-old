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
    
    private RedirecionamentoHistoricoDAO redirecionamentoHistoricoDAO;

    public RedirecionamentoHistoricoBO() {
        redirecionamentoHistoricoDAO = new RedirecionamentoHistoricoDAO();
    }
    
    public void create(RedirecionamentoHistorico redirecionamentoHistorico){
        try {
            redirecionamentoHistoricoDAO.create(redirecionamentoHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void edit(RedirecionamentoHistorico redirecionamentoHistorico){
        try {
            redirecionamentoHistoricoDAO.edit(redirecionamentoHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void destroy(RedirecionamentoHistorico redirecionamentoHistorico){
        try {
            redirecionamentoHistoricoDAO.destroy(redirecionamentoHistorico.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<RedirecionamentoHistorico> findByPJUD(Integer id){
        try { 
            return redirecionamentoHistoricoDAO.findByPJUD(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<RedirecionamentoHistorico>();
    }
}
