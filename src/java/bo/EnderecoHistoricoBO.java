/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.EnderecoHistoricoDAO;
import entidade.EnderecoHistorico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class EnderecoHistoricoBO implements Serializable{
    
    private EnderecoHistoricoDAO enderecoHistoricoDAO;
    
    public EnderecoHistoricoBO(){
        enderecoHistoricoDAO = new EnderecoHistoricoDAO();
    }
    
    public void create(EnderecoHistorico enderecoHistorico){
        try {
            enderecoHistoricoDAO.create(enderecoHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void edit(EnderecoHistorico enderecoHistorico){
        try {
            enderecoHistoricoDAO.edit(enderecoHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void destroy(EnderecoHistorico enderecoHistorico){
        try {
            enderecoHistoricoDAO.destroy(enderecoHistorico.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<EnderecoHistorico> findAllByPF(Integer id){
        try { 
            return enderecoHistoricoDAO.findAllByPF(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<EnderecoHistorico>();
    }
    
    public List<EnderecoHistorico> findAllByPJ(Integer id){
        try { 
            return enderecoHistoricoDAO.findAllByPJ(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<EnderecoHistorico>();
    }
}
