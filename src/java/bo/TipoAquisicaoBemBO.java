/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.TipoAquisicaoBemDAO;
import entidade.TipoAquisicaoBem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class TipoAquisicaoBemBO implements Serializable{
    
    public static List<TipoAquisicaoBem> findAll(){
        try { 
            TipoAquisicaoBemDAO tipoAquisicaoBemDAO = new TipoAquisicaoBemDAO();
            return tipoAquisicaoBemDAO.findTipoAquisicaoBemEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<TipoAquisicaoBem>();
    }
}
