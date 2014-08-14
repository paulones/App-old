/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.PessoaFisicaHistoricoDAO;
import entidade.PessoaFisicaHistorico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class PessoaFisicaHistoricoBO implements Serializable{
    
    private PessoaFisicaHistoricoDAO pessoaFisicaHistoricoDAO;
    
    public PessoaFisicaHistoricoBO(){
        pessoaFisicaHistoricoDAO = new PessoaFisicaHistoricoDAO();
    }
    
    public void create(PessoaFisicaHistorico pessoaFisicaHistorico){
        try {
            pessoaFisicaHistoricoDAO.create(pessoaFisicaHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void edit(PessoaFisicaHistorico pessoaFisicaHistorico){
        try {
            pessoaFisicaHistoricoDAO.edit(pessoaFisicaHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void destroy(PessoaFisicaHistorico pessoaFisicaHistorico){
        try {
            pessoaFisicaHistoricoDAO.destroy(pessoaFisicaHistorico.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<PessoaFisicaHistorico> findAllByPF(Integer id){
        try { 
            return pessoaFisicaHistoricoDAO.findAllByPF(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaFisicaHistorico>();
    }
}
