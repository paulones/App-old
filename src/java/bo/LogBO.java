/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.LogDAO;
import entidade.Instituicao;
import entidade.Log;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class LogBO implements Serializable{
    
    private LogDAO logDAO;
    
    public LogBO(){
        logDAO = new LogDAO();
    }
    
    public void create(Log log){
        try {
            logDAO.create(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<Log> findLogEntities(Integer quantidade, Integer indice){
        try { 
            return logDAO.findLogEntities(quantidade, indice);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Log>();
    }
    
    public List<Log> findLogByInstituicao(Integer quantidade, Instituicao instituicao){
        try { 
            return logDAO.findLogByInstituicao(quantidade, instituicao);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Log>();
    }
    
}
