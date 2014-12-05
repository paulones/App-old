/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.PessoaFisicaDAO;
import dao.PessoaJuridicaDAO;
import dao.ProcessoJudicialDAO;
import entidade.Instituicao;
import java.io.Serializable;

/**
 *
 * @author Pedro
 */
public class ChartsBO implements Serializable{
     
    public static Integer countPFByMonth(Integer ano, Integer mes, Instituicao instituicao){
        try {
            PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
            return pessoaFisicaDAO.countPFByMonth(ano, mes, instituicao);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Integer countPJByMonth(Integer ano, Integer mes, Instituicao instituicao){
        try {
            PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();
            return pessoaJuridicaDAO.countPJByMonth(ano, mes, instituicao);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Integer countPJUDByMonth(Integer ano, Integer mes, Instituicao instituicao){
        try {
            ProcessoJudicialDAO processoJudicialDAO = new ProcessoJudicialDAO();
            return processoJudicialDAO.countPJUDByMonth(ano, mes, instituicao);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Double sumPJUDValueBeforeMonth(Integer ano, Integer mes, Instituicao instituicao){
        try {
            ProcessoJudicialDAO processoJudicialDAO = new ProcessoJudicialDAO();
            return processoJudicialDAO.sumPJUDValueBeforeMonth(ano, mes, instituicao);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Double sumPJUDArrecadacaoBeforeMonth(Integer ano, Integer mes, Instituicao instituicao){
        try {
            ProcessoJudicialDAO processoJudicialDAO = new ProcessoJudicialDAO();
            return processoJudicialDAO.sumPJUDArrecadacaoBeforeMonth(ano, mes, instituicao);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Integer countPJUDValueBeforeMonth(Integer ano, Integer mes, Instituicao instituicao){
        try {
            ProcessoJudicialDAO processoJudicialDAO = new ProcessoJudicialDAO();
            return processoJudicialDAO.countPJUDValueBeforeMonth(ano, mes, instituicao);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Integer getPJUDSituations(String situacao, Instituicao instituicao){
        try {
            ProcessoJudicialDAO processoJudicialDAO = new ProcessoJudicialDAO();
            return processoJudicialDAO.getPJUDSituations(situacao, instituicao);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
