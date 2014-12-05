/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.TipoProcessoDAO;
import entidade.TipoProcesso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class TipoProcessoBO implements Serializable{
    
    public static List<TipoProcesso> findAll(){
        try { 
            TipoProcessoDAO tipoProcessoDAO = new TipoProcessoDAO();
            return tipoProcessoDAO.findTipoProcessoEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<TipoProcesso>();
    }
}
