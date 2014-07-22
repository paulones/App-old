/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import bo.UsuarioBO;
import entidade.Usuario;
import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import util.Cookie;
import util.GeradorMD5;

/**
 *
 * @author Paulo
 */
@ManagedBean(name = "bloquearTelaBean")
@SessionScoped
public class BloquearTelaBean {
    
    private Usuario usuario;
    private String senha;
    private UsuarioBO usuarioBO;
    
    public void init(){
        usuarioBO = new UsuarioBO();
        senha = "";
        Long cpf = Long.valueOf(Cookie.getCookie("usuario").replace(".", "").replace("-", ""));
        usuario = usuarioBO.findUsuario(cpf);
    }
    
    public void login() throws IOException{
        senha = GeradorMD5.generate(senha);
        if (senha.equals(usuario.getSenha())){
            String pagina_anterior = Cookie.getCookie("pagina_anterior");
            Cookie.eraseCookie("pagina_anterior");
            FacesContext.getCurrentInstance().getExternalContext().redirect(pagina_anterior);
        }
    }

    public void voltarAoLogin() throws IOException{
        Cookie.eraseCookies();
        FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
}
