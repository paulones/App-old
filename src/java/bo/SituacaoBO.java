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
    
    private SituacaoDAO situacaoDAO;
    
    public SituacaoBO(){
        situacaoDAO = new SituacaoDAO();
    }
    
    public List<Situacao> findAll(){
        try { 
            return situacaoDAO.findSituacaoEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Situacao>();
    }
}
