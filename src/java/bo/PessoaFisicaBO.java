/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.PessoaFisicaDAO;
import entidade.Instituicao;
import entidade.PessoaFisica;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class PessoaFisicaBO implements Serializable {
    
    public static void create(PessoaFisica pessoaFisica){
        try {
            PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
            pessoaFisicaDAO.create(pessoaFisica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(PessoaFisica pessoaFisica){
        try {
            PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
            pessoaFisicaDAO.edit(pessoaFisica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroy(PessoaFisica pessoaFisica){
        try {
            PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
            pessoaFisicaDAO.destroy(pessoaFisica.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static PessoaFisica findByCPF(String cpf){
        try {
            PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
            return pessoaFisicaDAO.findByCPF(cpf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static PessoaFisica findPessoaFisica(Integer id){
        try { 
            PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
            return pessoaFisicaDAO.findPessoaFisica(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static List<PessoaFisica> findAll(){
        try { 
            PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
            return pessoaFisicaDAO.findPessoaFisicaEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaFisica>();
    }
    
    public static List<PessoaFisica> findAllActive(Instituicao instituicao){
        try { 
            PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
            return pessoaFisicaDAO.findAllActive(instituicao);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaFisica>();
    }
    
    public static List<PessoaFisica> findFirstTenActive(){
        try { 
            PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
            return pessoaFisicaDAO.findFirstTenActive();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaFisica>();
    } 
    
    public static Integer getPessoaFisicaCount(){
        try {
            PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
            return pessoaFisicaDAO.getPessoaFisicaCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
