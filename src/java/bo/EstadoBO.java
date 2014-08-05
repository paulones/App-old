/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.EstadoDAO;
import entidade.Estado;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class EstadoBO implements Serializable {
    
    private EstadoDAO estadoDAO;
    
    public EstadoBO(){
        estadoDAO = new EstadoDAO();
    }
    
    public List<Estado> findAll(){
        try { 
            return estadoDAO.findEstadoEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Estado>();
    }
}
