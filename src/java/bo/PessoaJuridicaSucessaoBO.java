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
    
    public PessoaJuridicaSucessao findDuplicates(PessoaJuridica pessoaJuridicaSucedida, PessoaJuridica pessoaJuridicaSucessora){
        try {
            return pessoaJuridicaSucessaoDAO.findDuplicates(pessoaJuridicaSucedida, pessoaJuridicaSucessora);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
