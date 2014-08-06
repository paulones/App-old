/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import bo.UsuarioBO;
import entidade.Usuario;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import util.Cookie;

/**
 *
 * @author ipti004
 */
@ManagedBean(name = "homeBean")
@SessionScoped
public class HomeBean implements Serializable{
    
    private Usuario usuario;
    private UsuarioBO usuarioBO;
    
    public void init(){
        if (!FacesContext.getCurrentInstance().isPostback()){
            usuarioBO = new UsuarioBO();
            usuario = usuarioBO.findUsuarioByCPF(Cookie.getCookie("usuario"));
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
}
