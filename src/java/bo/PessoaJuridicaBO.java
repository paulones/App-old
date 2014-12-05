/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.PessoaJuridicaDAO;
import entidade.Instituicao;
import entidade.PessoaJuridica;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class PessoaJuridicaBO implements Serializable{
    
    public static void create(PessoaJuridica pessoaJuridica){
        try {
            PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();
            pessoaJuridicaDAO.create(pessoaJuridica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(PessoaJuridica pessoaJuridica){
        try {
            PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();
            pessoaJuridicaDAO.edit(pessoaJuridica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static PessoaJuridica findDuplicates(PessoaJuridica pessoaJuridica){
        try {
            PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();
            return pessoaJuridicaDAO.findDuplicates(pessoaJuridica);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static PessoaJuridica findByCNPJ (String cnpj){
        try {
            PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();
            return pessoaJuridicaDAO.findByCNPJ(cnpj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static PessoaJuridica findPessoaJuridica(Integer id){
        try { 
            PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();
            return pessoaJuridicaDAO.findPessoaJuridica(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static List<PessoaJuridica> findAll(){
        try { 
            PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();
            return pessoaJuridicaDAO.findPessoaJuridicaEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaJuridica>();
    }
    
    public static List<PessoaJuridica> findAllActive(Instituicao instituicao){
        try { 
            PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();
            return pessoaJuridicaDAO.findAllActive(instituicao);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaJuridica>();
    }
}
