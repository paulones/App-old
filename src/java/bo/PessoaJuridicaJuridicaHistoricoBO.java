/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.PessoaJuridicaJuridicaHistoricoDAO;
import entidade.PessoaJuridicaJuridicaHistorico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class PessoaJuridicaJuridicaHistoricoBO implements Serializable{
    
    
    private PessoaJuridicaJuridicaHistoricoDAO pessoaJuridicaJuridicaHistoricoDAO;
    
    public PessoaJuridicaJuridicaHistoricoBO(){
        pessoaJuridicaJuridicaHistoricoDAO = new PessoaJuridicaJuridicaHistoricoDAO();
    }
    
    public void create(PessoaJuridicaJuridicaHistorico pessoaJuridicaJuridicaHistorico){
        try {
            pessoaJuridicaJuridicaHistoricoDAO.create(pessoaJuridicaJuridicaHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void edit(PessoaJuridicaJuridicaHistorico pessoaJuridicaJuridicaHistorico){
        try {
            pessoaJuridicaJuridicaHistoricoDAO.edit(pessoaJuridicaJuridicaHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<PessoaJuridicaJuridicaHistorico> findAllByPJH(Integer id){
        try { 
            return pessoaJuridicaJuridicaHistoricoDAO.findAllByPJH(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
