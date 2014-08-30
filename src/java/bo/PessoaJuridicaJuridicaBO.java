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
    
    private PessoaJuridicaJuridicaDAO pessoaJuridicaJuridicaDAO;
    
    public PessoaJuridicaJuridicaBO () {
        pessoaJuridicaJuridicaDAO = new PessoaJuridicaJuridicaDAO();
    }
    
    public void create(PessoaJuridicaJuridica pessoaJuridicaJuridica){
        try {
            pessoaJuridicaJuridicaDAO.create(pessoaJuridicaJuridica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void edit(PessoaJuridicaJuridica pessoaJuridicaJuridica){
        try {
            pessoaJuridicaJuridicaDAO.edit(pessoaJuridicaJuridica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void destroyByPJA(Integer idPj){
        try {
            pessoaJuridicaJuridicaDAO.destroyByPJA(idPj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void destroyByPJB(Integer idPj){
        try {
            pessoaJuridicaJuridicaDAO.destroyByPJB(idPj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public PessoaJuridicaJuridica findByPJAndPJ(Integer idPjA, Integer idPjB){
        try { 
            return pessoaJuridicaJuridicaDAO.findByPJAndPJ(idPjA, idPjB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<PessoaJuridicaJuridica> findAllByPJA(Integer id){
        try { 
            return pessoaJuridicaJuridicaDAO.findAllByPJA(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaJuridicaJuridica>();
    }
    
    public List<PessoaJuridicaJuridica> findAllByPJB(Integer id){
        try { 
            return pessoaJuridicaJuridicaDAO.findAllByPJB(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaJuridicaJuridica>();
    }
    
}
