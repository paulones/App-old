/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.PessoaFisicaFisicaDAO;
import entidade.PessoaFisicaFisica;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class PessoaFisicaFisicaBO implements Serializable{
    
    public static void create(PessoaFisicaFisica pessoaFisicaFisica){
        try {
            PessoaFisicaFisicaDAO pessoaFisicaFisicaDAO = new PessoaFisicaFisicaDAO();
            pessoaFisicaFisicaDAO.create(pessoaFisicaFisica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(PessoaFisicaFisica pessoaFisicaFisica){
        try {
            PessoaFisicaFisicaDAO pessoaFisicaFisicaDAO = new PessoaFisicaFisicaDAO();
            pessoaFisicaFisicaDAO.edit(pessoaFisicaFisica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroyByPFBOrPFA(Integer idPj){
        try {
            PessoaFisicaFisicaDAO pessoaFisicaFisicaDAO = new PessoaFisicaFisicaDAO();
            pessoaFisicaFisicaDAO.destroyByPFBOrPFA(idPj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<PessoaFisicaFisica> findAllByPFAOrPFB(Integer id){
        try { 
            PessoaFisicaFisicaDAO pessoaFisicaFisicaDAO = new PessoaFisicaFisicaDAO();
            return pessoaFisicaFisicaDAO.findAllByPFAOrPFB(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaFisicaFisica>();
    }
    
}


