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
    
    private UsuarioDAO usuarioDAO;
    private AutorizacaoDAO autorizacaoDAO;
    
    public UsuarioBO(){
        usuarioDAO = new UsuarioDAO();
        autorizacaoDAO = new AutorizacaoDAO();
    }

    public void create(Usuario usuario) {
        try {
            usuarioDAO.create(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Usuario findUsuario(Integer id) {
        try { 
            return usuarioDAO.findUsuario(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Usuario();
    }

    public Usuario findUsuarioByCPF(String cpf) {
        try { 
            return usuarioDAO.findUsuarioByCPF(cpf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Usuario();
    }
    
    public Autorizacao findAutorizacaoByCPF(String cpf) {
        try { 
            return autorizacaoDAO.findAutorizacaoByCPF(cpf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Autorizacao();
    }
    
    public Usuario findUsuarioByCNPJ(String cnpj) {
        try { 
            return usuarioDAO.findUsuarioByCNPJ(cnpj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Usuario();
    }
    
    public Usuario findUsuarioByEmail(String email) {
        try { 
            return usuarioDAO.findUsuarioByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Usuario();
    }

    public void edit(Usuario usuario) {
        try {
            usuarioDAO.edit(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
