/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.PessoaJuridicaJuridicaDAO;
import entidade.PessoaJuridicaJuridica;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pedro
 */
public class PessoaJuridicaJuridicaBO implements Serializable{
    
    private PessoaJuridicaJuridicaDAO pessoaJuridicaJuridicaDAO;
    
    public PessoaJuridicaJuridicaBO () {
        pessoaJuridicaJuridicaDAO = new PessoaJuridicaJuridicaDAO();
    }
    
    public void create(PessoaJuridicaJuridica pessoaJuridicaJuridica){
        try {
            pessoaJuridicaJuridicaDAO.create(pessoaJuridicaJuridica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void edit(PessoaJuridicaJuridica pessoaJuridicaJuridica){
        try {
            pessoaJuridicaJuridicaDAO.edit(pessoaJuridicaJuridica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void destroyByPJBOrPJA(Integer idPj){
        try {
            pessoaJuridicaJuridicaDAO.destroyByPJBOrPJA(idPj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<PessoaJuridicaJuridica> findAllByPJAOrPJB(Integer id){
        try { 
            return pessoaJuridicaJuridicaDAO.findAllByPJAOrPJB(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaJuridicaJuridica>();
    }
    
}
