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
    
    public static void create(PessoaJuridicaJuridicaHistorico pessoaJuridicaJuridicaHistorico){
        try {
            PessoaJuridicaJuridicaHistoricoDAO pessoaJuridicaJuridicaHistoricoDAO = new PessoaJuridicaJuridicaHistoricoDAO();
            pessoaJuridicaJuridicaHistoricoDAO.create(pessoaJuridicaJuridicaHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(PessoaJuridicaJuridicaHistorico pessoaJuridicaJuridicaHistorico){
        try {
            PessoaJuridicaJuridicaHistoricoDAO pessoaJuridicaJuridicaHistoricoDAO = new PessoaJuridicaJuridicaHistoricoDAO();
            pessoaJuridicaJuridicaHistoricoDAO.edit(pessoaJuridicaJuridicaHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<PessoaJuridicaJuridicaHistorico> findAllByPJH(Integer id){
        try { 
            PessoaJuridicaJuridicaHistoricoDAO pessoaJuridicaJuridicaHistoricoDAO = new PessoaJuridicaJuridicaHistoricoDAO();
            return pessoaJuridicaJuridicaHistoricoDAO.findAllByPJH(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
