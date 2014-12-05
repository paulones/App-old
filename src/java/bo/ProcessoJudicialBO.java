/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.ProcessoJudicialDAO;
import entidade.ProcessoJudicial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class ProcessoJudicialBO implements Serializable{
    
    public static void create(ProcessoJudicial processoJudicial){
        try {
            ProcessoJudicialDAO processoJudicialDAO = new ProcessoJudicialDAO();
            processoJudicialDAO.create(processoJudicial);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(ProcessoJudicial processoJudicial){
        try {
            ProcessoJudicialDAO processoJudicialDAO = new ProcessoJudicialDAO();
            processoJudicialDAO.edit(processoJudicial);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroy(ProcessoJudicial processoJudicial){
        try {
            ProcessoJudicialDAO processoJudicialDAO = new ProcessoJudicialDAO();
            processoJudicialDAO.destroy(processoJudicial.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static ProcessoJudicial findProcessoJudicial(Integer id){
        try { 
            ProcessoJudicialDAO processoJudicialDAO = new ProcessoJudicialDAO();
            return processoJudicialDAO.findProcessoJudicial(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ProcessoJudicial();
    }
    
    public static List<ProcessoJudicial> findAllActive(){
        try { 
            ProcessoJudicialDAO processoJudicialDAO = new ProcessoJudicialDAO();
            return processoJudicialDAO.findAllActive();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<ProcessoJudicial>();
    }
    
    public static ProcessoJudicial findByProcessNumberOrCDA(ProcessoJudicial processoJudicial){
        try { 
            ProcessoJudicialDAO processoJudicialDAO = new ProcessoJudicialDAO();
            return processoJudicialDAO.findByProcessNumberOrCDA(processoJudicial);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ProcessoJudicial();
    }
    
    public static ProcessoJudicial findByProcessNumber(String processNumber){
        try { 
            ProcessoJudicialDAO processoJudicialDAO = new ProcessoJudicialDAO();
            return processoJudicialDAO.findByProcessNumber(processNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ProcessoJudicial();
    }
    
    public static ProcessoJudicial findByCDA(ProcessoJudicial processoJudicial){
        try { 
            ProcessoJudicialDAO processoJudicialDAO = new ProcessoJudicialDAO();
            return processoJudicialDAO.findByCDA(processoJudicial);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ProcessoJudicial();
    }
    
    public static List<ProcessoJudicial> findByExecutado(String executado, String tipoExecutado){
        try { 
            ProcessoJudicialDAO processoJudicialDAO = new ProcessoJudicialDAO();
            return processoJudicialDAO.findByExecutado(executado, tipoExecutado);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<ProcessoJudicial>();
    }
}
