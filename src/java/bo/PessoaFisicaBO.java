/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.PessoaFisicaDAO;
import entidade.PessoaFisica;
import java.io.Serializable;

/**
 *
 * @author paulones
 */
public class PessoaFisicaBO implements Serializable {
    
    private PessoaFisicaDAO pessoaFisicaDAO;
    
    public PessoaFisicaBO(){
        pessoaFisicaDAO = new PessoaFisicaDAO();
    }
    
    public void create(PessoaFisica pessoaFisica){
        try {
            pessoaFisicaDAO.create(pessoaFisica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public PessoaFisica findDuplicates(PessoaFisica pessoaFisica){
        try {
            return pessoaFisicaDAO.findDuplicates(pessoaFisica);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
