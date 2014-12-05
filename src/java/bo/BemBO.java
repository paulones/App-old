/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.BemDAO;
import entidade.Bem;
import entidade.ProcessoJudicial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class BemBO implements Serializable{
    
    public static void create(Bem bem){
        try {
            BemDAO bemDAO = new BemDAO();
            bemDAO.create(bem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(Bem bem){
        try {
            BemDAO bemDAO = new BemDAO();
            bemDAO.edit(bem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroy(Bem bem){
        try {
            BemDAO bemDAO = new BemDAO();
            bemDAO.destroy(bem.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<Bem> findPFBens(Integer id){
        try { 
            BemDAO bemDAO = new BemDAO();
            return bemDAO.findPFBens(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Bem>();
    }
    
    public static List<Bem> findPJBens(Integer id){
        try { 
            BemDAO bemDAO = new BemDAO();
            return bemDAO.findPJBens(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Bem>();
    }
    
    public static void destroyByPF(Integer id){
        try {
            BemDAO bemDAO = new BemDAO();
            bemDAO.destroyByPF(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroyByPJ(Integer id){
        try {
            BemDAO bemDAO = new BemDAO();
            bemDAO.destroyByPJ(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
