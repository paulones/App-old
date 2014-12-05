/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.PessoaJuridicaSucessaoDAO;
import entidade.PessoaJuridica;
import entidade.PessoaJuridicaSucessao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class PessoaJuridicaSucessaoBO implements Serializable{
    
    public static void create(PessoaJuridicaSucessao pessoaJuridicaSucessao){
        try {
            PessoaJuridicaSucessaoDAO pessoaJuridicaSucessaoDAO = new PessoaJuridicaSucessaoDAO();
            pessoaJuridicaSucessaoDAO.create(pessoaJuridicaSucessao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(PessoaJuridicaSucessao pessoaJuridicaSucessao){
        try {
            PessoaJuridicaSucessaoDAO pessoaJuridicaSucessaoDAO = new PessoaJuridicaSucessaoDAO();
            pessoaJuridicaSucessaoDAO.edit(pessoaJuridicaSucessao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroy(Integer id){
        try {
            PessoaJuridicaSucessaoDAO pessoaJuridicaSucessaoDAO = new PessoaJuridicaSucessaoDAO();
            pessoaJuridicaSucessaoDAO.destroy(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static PessoaJuridicaSucessao findPessoaJuridicaSucessao(Integer id){
        try {
            PessoaJuridicaSucessaoDAO pessoaJuridicaSucessaoDAO = new PessoaJuridicaSucessaoDAO();
            return pessoaJuridicaSucessaoDAO.findPessoaJuridicaSucessao(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static PessoaJuridicaSucessao findDuplicates(PessoaJuridica pessoaJuridicaSucedida, PessoaJuridica pessoaJuridicaSucessora){
        try {
            PessoaJuridicaSucessaoDAO pessoaJuridicaSucessaoDAO = new PessoaJuridicaSucessaoDAO();
            return pessoaJuridicaSucessaoDAO.findDuplicates(pessoaJuridicaSucedida, pessoaJuridicaSucessora);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static PessoaJuridicaSucessao findBySucedida(PessoaJuridica pessoaJuridicaSucedida, PessoaJuridica pessoaJuridicaSucessora){
        try {
            PessoaJuridicaSucessaoDAO pessoaJuridicaSucessaoDAO = new PessoaJuridicaSucessaoDAO();
            return pessoaJuridicaSucessaoDAO.findDuplicates(pessoaJuridicaSucedida, pessoaJuridicaSucessora);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static PessoaJuridicaSucessao findBySucedidaAndSucessora(Integer sucedida, Integer sucessora){
        try {
            PessoaJuridicaSucessaoDAO pessoaJuridicaSucessaoDAO = new PessoaJuridicaSucessaoDAO();
            return pessoaJuridicaSucessaoDAO.findBySucedidaAndSucessora(sucedida, sucessora);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static List<PessoaJuridicaSucessao> findSucessoras(Integer id){
        try { 
            PessoaJuridicaSucessaoDAO pessoaJuridicaSucessaoDAO = new PessoaJuridicaSucessaoDAO();
            return pessoaJuridicaSucessaoDAO.findSucessoras(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaJuridicaSucessao>();
    }
    
    public static List<PessoaJuridicaSucessao> findSucedidas(Integer id){
        try { 
            PessoaJuridicaSucessaoDAO pessoaJuridicaSucessaoDAO = new PessoaJuridicaSucessaoDAO();
            return pessoaJuridicaSucessaoDAO.findSucedidas(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaJuridicaSucessao>();
    }
    
    public static List<PessoaJuridicaSucessao> findSucedidasAndSucessoras(Integer id){
        try { 
            PessoaJuridicaSucessaoDAO pessoaJuridicaSucessaoDAO = new PessoaJuridicaSucessaoDAO();
            return pessoaJuridicaSucessaoDAO.findSucedidasAndSucessoras(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaJuridicaSucessao>();
    }
}
