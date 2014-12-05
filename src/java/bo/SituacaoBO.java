/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import entidade.Situacao;
import dao.SituacaoDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class SituacaoBO implements Serializable{
    
    public static List<Situacao> findAll(){
        try { 
            SituacaoDAO situacaoDAO = new SituacaoDAO();
            return situacaoDAO.findSituacaoEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Situacao>();
    }
}
