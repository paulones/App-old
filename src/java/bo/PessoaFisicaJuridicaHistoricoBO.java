/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.PessoaFisicaJuridicaHistoricoDAO;
import entidade.PessoaFisicaJuridicaHistorico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class PessoaFisicaJuridicaHistoricoBO implements Serializable{
    
    private PessoaFisicaJuridicaHistoricoDAO pessoaFisicaJuridicaHistoricoDAO;
    
    public PessoaFisicaJuridicaHistoricoBO(){
        pessoaFisicaJuridicaHistoricoDAO = new PessoaFisicaJuridicaHistoricoDAO();
    }
    
    public void create(PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistorico){
        try {
            pessoaFisicaJuridicaHistoricoDAO.create(pessoaFisicaJuridicaHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void edit(PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistorico){
        try {
            pessoaFisicaJuridicaHistoricoDAO.edit(pessoaFisicaJuridicaHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<PessoaFisicaJuridicaHistorico> findAllByPF(Integer id){
        try { 
            return pessoaFisicaJuridicaHistoricoDAO.findAllByPF(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    public List<PessoaFisicaJuridicaHistorico> findAllByPJ(Integer id){
        try { 
            return pessoaFisicaJuridicaHistoricoDAO.findAllByPJ(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
