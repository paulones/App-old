/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.TipoRecursoDAO;
import entidade.TipoRecurso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class TipoRecursoBO implements Serializable{
    
    private TipoRecursoDAO tipoRecursoDAO;
    
    public TipoRecursoBO(){
        tipoRecursoDAO = new TipoRecursoDAO();
    }
    
    public List<TipoRecurso> findAll(){
        try { 
            return tipoRecursoDAO.findTipoRecursoEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<TipoRecurso>();
    }
}
