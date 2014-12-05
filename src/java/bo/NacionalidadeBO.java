/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.NacionalidadeDAO;
import entidade.Nacionalidade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class NacionalidadeBO implements Serializable {
    
    public static List<Nacionalidade> findAll(){
        try { 
            NacionalidadeDAO nacionalidadeDAO = new NacionalidadeDAO();
            return nacionalidadeDAO.findNacionalidadeEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Nacionalidade>();
    }
}
