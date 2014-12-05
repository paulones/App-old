/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.PaisDAO;
import entidade.Pais;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class PaisBO implements Serializable {
    
    public static List<Pais> findAll(){
        try { 
            PaisDAO paisDAO = new PaisDAO();
            return paisDAO.findPaisEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Pais>();
    }
    
    public static Pais findBrasil(){
        try { 
            PaisDAO paisDAO = new PaisDAO();
            return paisDAO.findBrasil();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Pais();
    }
}
