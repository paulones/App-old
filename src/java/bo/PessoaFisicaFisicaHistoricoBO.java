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
    
    public static void create(PessoaFisicaFisicaHistorico pessoaFisicaFisicaHistorico){
        try {
            PessoaFisicaFisicaHistoricoDAO pessoaFisicaFisicaHistoricoDAO = new PessoaFisicaFisicaHistoricoDAO();
            pessoaFisicaFisicaHistoricoDAO.create(pessoaFisicaFisicaHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(PessoaFisicaFisicaHistorico pessoaFisicaFisicaHistorico){
        try {
            PessoaFisicaFisicaHistoricoDAO pessoaFisicaFisicaHistoricoDAO = new PessoaFisicaFisicaHistoricoDAO();
            pessoaFisicaFisicaHistoricoDAO.edit(pessoaFisicaFisicaHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<PessoaFisicaFisicaHistorico> findAllByPFH(Integer id){
        try { 
            PessoaFisicaFisicaHistoricoDAO pessoaFisicaFisicaHistoricoDAO = new PessoaFisicaFisicaHistoricoDAO();
            return pessoaFisicaFisicaHistoricoDAO.findAllByPFH(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}