/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.BemDAO;
import entidade.Bem;
import entidade.ProcessoJudicial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class BemBO implements Serializable{
    
    private BemDAO bemDAO;
    
    public BemBO(){
        bemDAO = new BemDAO();
    }
    
    public void create(Bem bem){
        try {
            bemDAO.create(bem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void edit(Bem bem){
        try {
            bemDAO.edit(bem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void destroy(Bem bem){
        try {
            bemDAO.destroy(bem.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void destroyByPJUD(Integer idPjud){
        try {
            bemDAO.destroyByPJUD(idPjud);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<Bem> findAllByPJUD(ProcessoJudicial processoJudicial){
        try { 
            return bemDAO.findAllByPJUD(processoJudicial);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Bem>();
    }
    
}
