/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.PessoaJuridicaDAO;
import entidade.PessoaJuridica;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class PessoaJuridicaBO implements Serializable{
    
    private PessoaJuridicaDAO pessoaJuridicaDAO;
    
    public PessoaJuridicaBO(){
        pessoaJuridicaDAO = new PessoaJuridicaDAO();
    }
    
    public void create(PessoaJuridica pessoaJuridica){
        try {
            pessoaJuridicaDAO.create(pessoaJuridica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public PessoaJuridica findDuplicates(PessoaJuridica pessoaJuridica){
        try {
            return pessoaJuridicaDAO.findDuplicates(pessoaJuridica);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<PessoaJuridica> findAll(){
        try { 
            return pessoaJuridicaDAO.findPessoaJuridicaEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaJuridica>();
    }
}
