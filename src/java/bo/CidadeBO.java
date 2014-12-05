/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.CidadeDAO;
import entidade.Cidade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class CidadeBO implements Serializable {
    
    public static List<Cidade> findAll(){
        try { 
            CidadeDAO cidadeDAO = new CidadeDAO();
            return cidadeDAO.findCidadeEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Cidade>();
    }
    
    public static List<Cidade> getByStateId(Integer estadoId){
        try { 
            CidadeDAO cidadeDAO = new CidadeDAO();
            return cidadeDAO.getByStateId(estadoId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Cidade>();
    }
}
