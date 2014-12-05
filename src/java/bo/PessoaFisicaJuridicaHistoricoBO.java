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
    
    public static void create(PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistorico){
        try {
            PessoaFisicaJuridicaHistoricoDAO pessoaFisicaJuridicaHistoricoDAO = new PessoaFisicaJuridicaHistoricoDAO();
            pessoaFisicaJuridicaHistoricoDAO.create(pessoaFisicaJuridicaHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(PessoaFisicaJuridicaHistorico pessoaFisicaJuridicaHistorico){
        try {
            PessoaFisicaJuridicaHistoricoDAO pessoaFisicaJuridicaHistoricoDAO = new PessoaFisicaJuridicaHistoricoDAO();
            pessoaFisicaJuridicaHistoricoDAO.edit(pessoaFisicaJuridicaHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<PessoaFisicaJuridicaHistorico> findAllByPF(Integer id){
        try { 
            PessoaFisicaJuridicaHistoricoDAO pessoaFisicaJuridicaHistoricoDAO = new PessoaFisicaJuridicaHistoricoDAO();
            return pessoaFisicaJuridicaHistoricoDAO.findAllByPF(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    public static List<PessoaFisicaJuridicaHistorico> findAllByPJ(Integer id){
        try { 
            PessoaFisicaJuridicaHistoricoDAO pessoaFisicaJuridicaHistoricoDAO = new PessoaFisicaJuridicaHistoricoDAO();
            return pessoaFisicaJuridicaHistoricoDAO.findAllByPJ(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
