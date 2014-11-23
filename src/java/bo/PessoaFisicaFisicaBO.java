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
    
    private PessoaFisicaFisicaDAO pessoaFisicaFisicaDAO;
    
    public PessoaFisicaFisicaBO () {
        pessoaFisicaFisicaDAO = new PessoaFisicaFisicaDAO();
    }
    
    public void create(PessoaFisicaFisica pessoaFisicaFisica){
        try {
            pessoaFisicaFisicaDAO.create(pessoaFisicaFisica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void edit(PessoaFisicaFisica pessoaFisicaFisica){
        try {
            pessoaFisicaFisicaDAO.edit(pessoaFisicaFisica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void destroyByPFBOrPFA(Integer idPj){
        try {
            pessoaFisicaFisicaDAO.destroyByPFBOrPFA(idPj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<PessoaFisicaFisica> findAllByPFAOrPFB(Integer id){
        try { 
            return pessoaFisicaFisicaDAO.findAllByPFAOrPFB(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaFisicaFisica>();
    }
    
}


