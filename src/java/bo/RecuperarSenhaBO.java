/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.RecuperarSenhaDAO;
import entidade.RecuperarSenha;
import java.io.Serializable;

/**
 *
 * @author ipti004
 */
public class RecuperarSenhaBO implements Serializable{

    public static void create(RecuperarSenha recuperarSenha) {
        try {
            RecuperarSenhaDAO recuperarSenhaDAO = new RecuperarSenhaDAO();
            recuperarSenhaDAO.create(recuperarSenha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RecuperarSenha findRecuperarSenhaByCod(String cod) {
        try { 
            RecuperarSenhaDAO recuperarSenhaDAO = new RecuperarSenhaDAO();
            return recuperarSenhaDAO.findRecuperarSenhaByCod(cod);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RecuperarSenha();
    }

    public static void edit(RecuperarSenha recuperarSenha) {
        try {
            RecuperarSenhaDAO recuperarSenhaDAO = new RecuperarSenhaDAO();
            recuperarSenhaDAO.edit(recuperarSenha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroy(RecuperarSenha recuperarSenha) {
        try {
            RecuperarSenhaDAO recuperarSenhaDAO = new RecuperarSenhaDAO();
            recuperarSenhaDAO.destroy(recuperarSenha.getUsuario().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
