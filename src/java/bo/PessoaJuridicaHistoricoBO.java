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
    
    public static void create(PessoaJuridicaHistorico pessoaJuridicaHistorico){
        try {
            PessoaJuridicaHistoricoDAO pessoaJuridicaHistoricoDAO = new PessoaJuridicaHistoricoDAO();
            pessoaJuridicaHistoricoDAO.create(pessoaJuridicaHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(PessoaJuridicaHistorico pessoaJuridicaHistorico){
        try {
            PessoaJuridicaHistoricoDAO pessoaJuridicaHistoricoDAO = new PessoaJuridicaHistoricoDAO();
            pessoaJuridicaHistoricoDAO.edit(pessoaJuridicaHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroy(PessoaJuridicaHistorico pessoaJuridicaHistorico){
        try {
            PessoaJuridicaHistoricoDAO pessoaJuridicaHistoricoDAO = new PessoaJuridicaHistoricoDAO();
            pessoaJuridicaHistoricoDAO.destroy(pessoaJuridicaHistorico.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<PessoaJuridicaHistorico> findAllByPJ(Integer id){
        try { 
            PessoaJuridicaHistoricoDAO pessoaJuridicaHistoricoDAO = new PessoaJuridicaHistoricoDAO();
            return pessoaJuridicaHistoricoDAO.findAllByPJ(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaJuridicaHistorico>();
    }
}
