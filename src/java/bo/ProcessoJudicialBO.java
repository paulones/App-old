/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.ProcessoJudicialDAO;
import entidade.ProcessoJudicial;
import java.io.Serializable;

/**
 *
 * @author paulones
 */
public class ProcessoJudicialBO implements Serializable{
    
    private ProcessoJudicialDAO processoJudicialDAO;
    
    public ProcessoJudicialBO(){
        processoJudicialDAO = new ProcessoJudicialDAO();
    }
    
    public void create(ProcessoJudicial processoJudicial){
        try {
            processoJudicialDAO.create(processoJudicial);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void edit(ProcessoJudicial processoJudicial){
        try {
            processoJudicialDAO.edit(processoJudicial);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void destroy(ProcessoJudicial processoJudicial){
        try {
            processoJudicialDAO.destroy(processoJudicial.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}