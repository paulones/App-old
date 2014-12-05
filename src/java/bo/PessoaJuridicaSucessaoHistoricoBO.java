/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.PessoaJuridicaSucessaoHistoricoDAO;
import entidade.PessoaJuridicaSucessaoHistorico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class PessoaJuridicaSucessaoHistoricoBO implements Serializable{
    
    public static void create(PessoaJuridicaSucessaoHistorico pessoaJuridicaSucessaoHistorico){
        try {
            PessoaJuridicaSucessaoHistoricoDAO pessoaJuridicaSucessaoHistoricoDAO = new PessoaJuridicaSucessaoHistoricoDAO();
            pessoaJuridicaSucessaoHistoricoDAO.create(pessoaJuridicaSucessaoHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<PessoaJuridicaSucessaoHistorico> findByPJS(Integer id){
        try { 
            PessoaJuridicaSucessaoHistoricoDAO pessoaJuridicaSucessaoHistoricoDAO = new PessoaJuridicaSucessaoHistoricoDAO();
            return pessoaJuridicaSucessaoHistoricoDAO.findByPJS(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PessoaJuridicaSucessaoHistorico>();
    }
    
}
