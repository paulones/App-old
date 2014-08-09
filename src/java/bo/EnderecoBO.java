/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.EnderecoDAO;
import entidade.Endereco;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class EnderecoBO implements Serializable{

    private EnderecoDAO enderecoDAO;
    
    public EnderecoBO(){
        enderecoDAO = new EnderecoDAO();
    }
    
    public void create(Endereco endereco){
        try {
            enderecoDAO.create(endereco);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<Endereco> findAllPFAddress(){
        try { 
            return enderecoDAO.findAllPFAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Endereco>();
    }
}
