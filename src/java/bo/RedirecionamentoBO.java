/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.RedirecionamentoDAO;
import entidade.Redirecionamento;
import java.io.Serializable;

/**
 *
 * @author paulones
 */
public class RedirecionamentoBO implements Serializable{
    
    private RedirecionamentoDAO redirecionamentoDAO;

    public RedirecionamentoBO() {
        redirecionamentoDAO = new RedirecionamentoDAO();
    }
    
    public void create(Redirecionamento redirecionamento){
        try {
            redirecionamentoDAO.create(redirecionamento);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void edit(Redirecionamento redirecionamento){
        try {
            redirecionamentoDAO.edit(redirecionamento);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void destroy(Redirecionamento redirecionamento){
        try {
            redirecionamentoDAO.destroy(redirecionamento.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
