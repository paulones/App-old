/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.TipoBemDAO;
import entidade.TipoBem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class TipoBemBO implements Serializable{
    
    public static List<TipoBem> findAll(){
        try { 
            TipoBemDAO tipoBemDAO = new TipoBemDAO();
            return tipoBemDAO.findTipoBemEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<TipoBem>();
    }
}
