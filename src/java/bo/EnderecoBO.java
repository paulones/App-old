/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.EnderecoDAO;
import entidade.Endereco;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class EnderecoBO implements Serializable{
    
    public static void create(Endereco endereco){
        try {
            EnderecoDAO enderecoDAO = new EnderecoDAO();
            enderecoDAO.create(endereco);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(Endereco endereco){
        try {
            EnderecoDAO enderecoDAO = new EnderecoDAO();
            enderecoDAO.edit(endereco);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroy(Endereco endereco){
        try {
            EnderecoDAO enderecoDAO = new EnderecoDAO();
            enderecoDAO.destroy(endereco.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<Endereco> findAllPFAddress(){
        try { 
            EnderecoDAO enderecoDAO = new EnderecoDAO();
            return enderecoDAO.findAllPFAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Endereco>();
    }
    
    public static List<Endereco> findAllPJAddress(){
        try { 
            EnderecoDAO enderecoDAO = new EnderecoDAO();
            return enderecoDAO.findAllPJAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Endereco>();
    }
    
    public static Endereco findPFAddress(Integer id){
        try { 
            EnderecoDAO enderecoDAO = new EnderecoDAO();
            return enderecoDAO.findPFAddress(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Endereco findPJAddress(Integer id){
        try { 
            EnderecoDAO enderecoDAO = new EnderecoDAO();
            return enderecoDAO.findPJAddress(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
