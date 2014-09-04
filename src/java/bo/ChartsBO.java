/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.PessoaFisicaDAO;
import dao.PessoaJuridicaDAO;
import dao.ProcessoJudicialDAO;
import java.io.Serializable;

/**
 *
 * @author Pedro
 */
public class ChartsBO implements Serializable{
    
    private PessoaFisicaDAO pessoaFisicaDAO;
    private PessoaJuridicaDAO pessoaJuridicaDAO;
    private ProcessoJudicialDAO processoJudicialDAO;
    
    public ChartsBO (){
        pessoaFisicaDAO = new PessoaFisicaDAO();
        pessoaJuridicaDAO = new PessoaJuridicaDAO();
        processoJudicialDAO = new ProcessoJudicialDAO();
    }
     
    public Integer countPFByMonth(Integer ano, Integer mes){
        try {
            return pessoaFisicaDAO.countPFByMonth(ano, mes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Integer countPJByMonth(Integer ano, Integer mes){
        try {
            return pessoaJuridicaDAO.countPJByMonth(ano, mes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Integer countPJUDByMonth(Integer ano, Integer mes){
        try {
            return processoJudicialDAO.countPJUDByMonth(ano, mes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
