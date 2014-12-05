/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.AutorizacaoDAO;
import dao.UsuarioDAO;
import entidade.Autorizacao;
import entidade.Usuario;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author PRCC
 */
public class UsuarioBO implements Serializable {

    public static void create(Usuario usuario) {
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.create(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Usuario findUsuario(Integer id) {
        try { 
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            return usuarioDAO.findUsuario(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Usuario();
    }

    public static Usuario findUsuarioByCPF(String cpf) {
        try { 
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            return usuarioDAO.findUsuarioByCPF(cpf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Usuario();
    }
    
    public static Autorizacao findAutorizacaoByCPF(String cpf) {
        try { 
            AutorizacaoDAO autorizacaoDAO = new AutorizacaoDAO();
            return autorizacaoDAO.findAutorizacaoByCPF(cpf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Autorizacao();
    }
    
    public static Usuario findUsuarioByCNPJ(String cnpj) {
        try { 
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            return usuarioDAO.findUsuarioByCNPJ(cnpj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Usuario();
    }
    
    public static Usuario findUsuarioByEmail(String email) {
        try { 
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            return usuarioDAO.findUsuarioByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Usuario();
    }

    public static void edit(Usuario usuario) {
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.edit(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
