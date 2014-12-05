/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.PessoaJuridicaJuridicaDAO;
import entidade.PessoaJuridicaJuridica;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pedro
 */
public class PessoaJuridicaJuridicaBO implements Serializable{
    
    public static void create(PessoaJuridicaJuridica pessoaJuridicaJuridica){
        try {
            PessoaJuridicaJuridicaDAO pessoaJuridicaJuridicaDAO = new PessoaJuridicaJuridicaDAO();
            pessoaJuridicaJuridicaDAO.create(pessoaJuridicaJuridica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(PessoaJuridicaJuridica pessoaJuridicaJuridica){
        try {
            PessoaJuridicaJuridicaDAO pessoaJuridicaJuridicaDAO = new PessoaJuridicaJuridicaDAO();
            pessoaJuridicaJuridicaDAO.edit(pessoaJuridicaJuridica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroyByPJBOrPJA(Integer idPj){
        try {
            PessoaJuridicaJuridicaDAO pessoaJuridicaJuridicaDAO = new PessoaJuridicaJuridicaDAO();
            pessoaJuridicaJuridicaDAO.destroyByPJBOrPJA(idPj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<PessoaJuridicaJuridica> findAllByPJAOrPJB(Integer id){
        try { 
            PessoaJuridicaJuridicaDAO pessoaJuridicaJuridicaDAO = new PessoaJuridicaJuridicaDAO();
            return pessoaJuridicaJuridicaDAO.findAllByPJAOrPJB(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaJuridicaJuridica>();
    }
    
}
