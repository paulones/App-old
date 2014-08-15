/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.PessoaJuridicaHistoricoDAO;
import entidade.PessoaJuridicaHistorico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class PessoaJuridicaHistoricoBO implements Serializable{
    
    private PessoaJuridicaHistoricoDAO pessoaJuridicaHistoricoDAO;
    
    public PessoaJuridicaHistoricoBO(){
        pessoaJuridicaHistoricoDAO = new PessoaJuridicaHistoricoDAO();
    }
    
    public void create(PessoaJuridicaHistorico pessoaJuridicaHistorico){
        try {
            pessoaJuridicaHistoricoDAO.create(pessoaJuridicaHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<PessoaJuridicaHistorico> findAllByPJ(Integer id){
        try { 
            return pessoaJuridicaHistoricoDAO.findAllByPJ(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaJuridicaHistorico>();
    }
}
