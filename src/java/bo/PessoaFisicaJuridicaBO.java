/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.PessoaFisicaJuridicaDAO;
import entidade.PessoaFisicaJuridica;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    
    public void edit(PessoaFisicaJuridica pessoaFisicaJuridica){
        try {
            pessoaFisicaJuridicaDAO.edit(pessoaFisicaJuridica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void destroyByPF(Integer idPf){
        try {
            pessoaFisicaJuridicaDAO.destroyByPF(idPf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public PessoaFisicaJuridica findByPFAndPJ(Integer idPf, Integer idPj){
        try { 
            return pessoaFisicaJuridicaDAO.findByPFAndPJ(idPf, idPj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<PessoaFisicaJuridica> findAllByPF(Integer id){
        try { 
            return pessoaFisicaJuridicaDAO.findAllByPF(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaFisicaJuridica>();
    }
    
    public List<PessoaFisicaJuridica> findAllByPJ(Integer id){
        try { 
            return pessoaFisicaJuridicaDAO.findAllByPJ(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaFisicaJuridica>();
    }
    
}
