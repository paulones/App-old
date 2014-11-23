/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.PessoaFisicaFisicaHistoricoDAO;
import entidade.PessoaFisicaFisicaHistorico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class PessoaFisicaFisicaHistoricoBO implements Serializable{
    
    
    private PessoaFisicaFisicaHistoricoDAO pessoaFisicaFisicaHistoricoDAO;
    
    public PessoaFisicaFisicaHistoricoBO(){
        pessoaFisicaFisicaHistoricoDAO = new PessoaFisicaFisicaHistoricoDAO();
    }
    
    public void create(PessoaFisicaFisicaHistorico pessoaFisicaFisicaHistorico){
        try {
            pessoaFisicaFisicaHistoricoDAO.create(pessoaFisicaFisicaHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void edit(PessoaFisicaFisicaHistorico pessoaFisicaFisicaHistorico){
        try {
            pessoaFisicaFisicaHistoricoDAO.edit(pessoaFisicaFisicaHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<PessoaFisicaFisicaHistorico> findAllByPFH(Integer id){
        try { 
            return pessoaFisicaFisicaHistoricoDAO.findAllByPFH(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}