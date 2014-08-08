/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.FuncaoDAO;
import entidade.Funcao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class FuncaoBO implements Serializable{
    
    private FuncaoDAO funcaoDAO;
    
    public FuncaoBO(){
        funcaoDAO = new FuncaoDAO();
    }
    
    public List<Funcao> findAll(){
        try { 
            return funcaoDAO.findFuncaoEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Funcao>();
    }
}
