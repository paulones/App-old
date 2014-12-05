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
    
    public static void create(ProcessoJudicialHistorico processoJudicialHistorico){
        try {
            ProcessoJudicialHistoricoDAO processoJudicialHistoricoDAO = new ProcessoJudicialHistoricoDAO();
            processoJudicialHistoricoDAO.create(processoJudicialHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(ProcessoJudicialHistorico processoJudicialHistorico){
        try {
            ProcessoJudicialHistoricoDAO processoJudicialHistoricoDAO = new ProcessoJudicialHistoricoDAO();
            processoJudicialHistoricoDAO.edit(processoJudicialHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroy(ProcessoJudicialHistorico processoJudicialHistorico){
        try {
            ProcessoJudicialHistoricoDAO processoJudicialHistoricoDAO = new ProcessoJudicialHistoricoDAO();
            processoJudicialHistoricoDAO.destroy(processoJudicialHistorico.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<ProcessoJudicialHistorico> findAllByPJUD(Integer id){
        try { 
            ProcessoJudicialHistoricoDAO processoJudicialHistoricoDAO = new ProcessoJudicialHistoricoDAO();
            return processoJudicialHistoricoDAO.findAllByPJUD(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<ProcessoJudicialHistorico>();
    }
}
