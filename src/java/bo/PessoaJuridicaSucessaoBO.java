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
    
    private PessoaJuridicaSucessaoDAO pessoaJuridicaSucessaoDAO;
    
    public PessoaJuridicaSucessaoBO(){
        pessoaJuridicaSucessaoDAO = new PessoaJuridicaSucessaoDAO();
    }
    
    public void create(PessoaJuridicaSucessao pessoaJuridicaSucessao){
        try {
            pessoaJuridicaSucessaoDAO.create(pessoaJuridicaSucessao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void edit(PessoaJuridicaSucessao pessoaJuridicaSucessao){
        try {
            pessoaJuridicaSucessaoDAO.edit(pessoaJuridicaSucessao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void destroy(PessoaJuridicaSucessao pessoaJuridicaSucessao){
        try {
            pessoaJuridicaSucessaoDAO.destroy(pessoaJuridicaSucessao.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public PessoaJuridicaSucessao findPessoaJuridicaSucessao(Integer id){
        try {
            return pessoaJuridicaSucessaoDAO.findPessoaJuridicaSucessao(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public PessoaJuridicaSucessao findDuplicates(PessoaJuridica pessoaJuridicaSucedida, PessoaJuridica pessoaJuridicaSucessora){
        try {
            return pessoaJuridicaSucessaoDAO.findDuplicates(pessoaJuridicaSucedida, pessoaJuridicaSucessora);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<PessoaJuridicaSucessao> findSucessoras(Integer id){
        try { 
            return pessoaJuridicaSucessaoDAO.findSucessoras(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaJuridicaSucessao>();
    }
    
    public List<PessoaJuridicaSucessao> findSucedidas(Integer id){
        try { 
            return pessoaJuridicaSucessaoDAO.findSucedidas(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaJuridicaSucessao>();
    }
}
