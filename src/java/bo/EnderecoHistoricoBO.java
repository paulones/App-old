/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.EnderecoHistoricoDAO;
import entidade.EnderecoHistorico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class EnderecoHistoricoBO implements Serializable{
    
    public static void create(EnderecoHistorico enderecoHistorico){
        try {
            EnderecoHistoricoDAO enderecoHistoricoDAO = new EnderecoHistoricoDAO();
            enderecoHistoricoDAO.create(enderecoHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(EnderecoHistorico enderecoHistorico){
        try {
            EnderecoHistoricoDAO enderecoHistoricoDAO = new EnderecoHistoricoDAO();
            enderecoHistoricoDAO.edit(enderecoHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroy(EnderecoHistorico enderecoHistorico){
        try {
            EnderecoHistoricoDAO enderecoHistoricoDAO = new EnderecoHistoricoDAO();
            enderecoHistoricoDAO.destroy(enderecoHistorico.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<EnderecoHistorico> findAllByPF(Integer id){
        try { 
            EnderecoHistoricoDAO enderecoHistoricoDAO = new EnderecoHistoricoDAO();
            return enderecoHistoricoDAO.findAllByPF(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<EnderecoHistorico>();
    }
    
    public static List<EnderecoHistorico> findAllByPJ(Integer id){
        try { 
            EnderecoHistoricoDAO enderecoHistoricoDAO = new EnderecoHistoricoDAO();
            return enderecoHistoricoDAO.findAllByPJ(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<EnderecoHistorico>();
    }
}
