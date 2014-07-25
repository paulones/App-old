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
    
    private RecuperarSenhaDAO recuperarSenhaDAO;
    
    public RecuperarSenhaBO(){
        recuperarSenhaDAO = new RecuperarSenhaDAO();
    }

    public void create(RecuperarSenha recuperarSenha) {
        try {
            recuperarSenhaDAO.create(recuperarSenha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RecuperarSenha findRecuperarSenhaByCod(String cod) {
        try { 
            return recuperarSenhaDAO.findRecuperarSenhaByCod(cod);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RecuperarSenha();
    }

    public void edit(RecuperarSenha recuperarSenha) {
        try {
            recuperarSenhaDAO.edit(recuperarSenha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void destroy(RecuperarSenha recuperarSenha) {
        try {
            recuperarSenhaDAO.destroy(recuperarSenha.getUsuarioFk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
