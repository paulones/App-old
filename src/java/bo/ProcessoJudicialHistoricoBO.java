/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.ProcessoJudicialHistoricoDAO;
import entidade.ProcessoJudicialHistorico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class ProcessoJudicialHistoricoBO implements Serializable{
    
    private ProcessoJudicialHistoricoDAO processoJudicialHistoricoDAO;
    
    public ProcessoJudicialHistoricoBO(){
        processoJudicialHistoricoDAO = new ProcessoJudicialHistoricoDAO();
    }
    
    public void create(ProcessoJudicialHistorico processoJudicialHistorico){
        try {
            processoJudicialHistoricoDAO.create(processoJudicialHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void edit(ProcessoJudicialHistorico processoJudicialHistorico){
        try {
            processoJudicialHistoricoDAO.edit(processoJudicialHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void destroy(ProcessoJudicialHistorico processoJudicialHistorico){
        try {
            processoJudicialHistoricoDAO.destroy(processoJudicialHistorico.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<ProcessoJudicialHistorico> findAllByPJUD(Integer id){
        try { 
            return processoJudicialHistoricoDAO.findAllByPJUD(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<ProcessoJudicialHistorico>();
    }
}
