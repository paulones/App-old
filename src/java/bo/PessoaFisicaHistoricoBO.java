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
    
    public static void create(PessoaFisicaHistorico pessoaFisicaHistorico){
        try {
            PessoaFisicaHistoricoDAO pessoaFisicaHistoricoDAO = new PessoaFisicaHistoricoDAO();
            pessoaFisicaHistoricoDAO.create(pessoaFisicaHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(PessoaFisicaHistorico pessoaFisicaHistorico){
        try {
            PessoaFisicaHistoricoDAO pessoaFisicaHistoricoDAO = new PessoaFisicaHistoricoDAO();
            pessoaFisicaHistoricoDAO.edit(pessoaFisicaHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroy(PessoaFisicaHistorico pessoaFisicaHistorico){
        try {
            PessoaFisicaHistoricoDAO pessoaFisicaHistoricoDAO = new PessoaFisicaHistoricoDAO();
            pessoaFisicaHistoricoDAO.destroy(pessoaFisicaHistorico.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<PessoaFisicaHistorico> findAllByPF(Integer id){
        try { 
            PessoaFisicaHistoricoDAO pessoaFisicaHistoricoDAO = new PessoaFisicaHistoricoDAO();
            return pessoaFisicaHistoricoDAO.findAllByPF(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaFisicaHistorico>();
    }
}
