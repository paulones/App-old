/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.PessoaFisicaJuridicaDAO;
import entidade.PessoaFisicaJuridica;
import java.io.Serializable;

/**
 *
 * @author paulones
 */
public class PessoaFisicaJuridicaBO implements Serializable{
    
    private PessoaFisicaJuridicaDAO pessoaFisicaJuridicaDAO;
    
    public PessoaFisicaJuridicaBO(){
        pessoaFisicaJuridicaDAO = new PessoaFisicaJuridicaDAO();
    }
    
    public void create(PessoaFisicaJuridica pessoaFisicaJuridica){
        try {
            pessoaFisicaJuridicaDAO.create(pessoaFisicaJuridica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
