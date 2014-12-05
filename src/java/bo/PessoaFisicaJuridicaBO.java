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
    
    public static void create(PessoaFisicaJuridica pessoaFisicaJuridica){
        try {
            PessoaFisicaJuridicaDAO pessoaFisicaJuridicaDAO = new PessoaFisicaJuridicaDAO();
            pessoaFisicaJuridicaDAO.create(pessoaFisicaJuridica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(PessoaFisicaJuridica pessoaFisicaJuridica){
        try {
            PessoaFisicaJuridicaDAO pessoaFisicaJuridicaDAO = new PessoaFisicaJuridicaDAO();
            pessoaFisicaJuridicaDAO.edit(pessoaFisicaJuridica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroyByPF(Integer idPf){
        try {
            PessoaFisicaJuridicaDAO pessoaFisicaJuridicaDAO = new PessoaFisicaJuridicaDAO();
            pessoaFisicaJuridicaDAO.destroyByPF(idPf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroyByPJ(Integer idPj){
        try {
            PessoaFisicaJuridicaDAO pessoaFisicaJuridicaDAO = new PessoaFisicaJuridicaDAO();
            pessoaFisicaJuridicaDAO.destroyByPJ(idPj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static PessoaFisicaJuridica findByPFAndPJ(Integer idPf, Integer idPj){
        try { 
            PessoaFisicaJuridicaDAO pessoaFisicaJuridicaDAO = new PessoaFisicaJuridicaDAO();
            return pessoaFisicaJuridicaDAO.findByPFAndPJ(idPf, idPj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static List<PessoaFisicaJuridica> findAllByPF(Integer id){
        try { 
            PessoaFisicaJuridicaDAO pessoaFisicaJuridicaDAO = new PessoaFisicaJuridicaDAO();
            return pessoaFisicaJuridicaDAO.findAllByPF(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaFisicaJuridica>();
    }
    
    public static List<PessoaFisicaJuridica> findAllByPJ(Integer id){
        try { 
            PessoaFisicaJuridicaDAO pessoaFisicaJuridicaDAO = new PessoaFisicaJuridicaDAO();
            return pessoaFisicaJuridicaDAO.findAllByPJ(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaFisicaJuridica>();
    }
    
}
