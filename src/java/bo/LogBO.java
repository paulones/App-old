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
    
    public static void create(Log log){
        try {
            LogDAO logDAO = new LogDAO();
            logDAO.create(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<Log> findLogEntities(Integer quantidade, Integer indice){
        try { 
            LogDAO logDAO = new LogDAO();
            return logDAO.findLogEntities(quantidade, indice);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Log>();
    }
    
    public static List<Log> findLogByInstituicao(Integer quantidade, Instituicao instituicao){
        try { 
            LogDAO logDAO = new LogDAO();
            return logDAO.findLogByInstituicao(quantidade, instituicao);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Log>();
    }
    
}
