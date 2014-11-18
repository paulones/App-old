/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.ProcuradorDAO;
import entidade.Procurador;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class ProcuradorBO implements Serializable{
    
    private ProcuradorDAO procuradorDAO;
    
    public ProcuradorBO(){
        procuradorDAO = new ProcuradorDAO();
    }
    
    public List<Procurador> findAll(){
        try { 
            return procuradorDAO.findProcuradorEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Procurador>();
    }
}
