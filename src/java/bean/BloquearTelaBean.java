/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bo.UsuarioBO;
import entidade.Usuario;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
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
public class BloquearTelaBean implements Serializable {

    private Usuario usuario;
    private String senha;
    private boolean senhaCorreta;
    private UsuarioBO usuarioBO;

    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            usuarioBO = new UsuarioBO();
            senha = "";
            senhaCorreta = true;
            Long cpf = Long.valueOf(Cookie.getCookie("usuario").replace(".", "").replace("-", ""));
            usuario = usuarioBO.findUsuario(cpf);
        }
    }

    public void login() throws IOException {
        senha = GeradorMD5.generate(senha);
        if (senha.equals(usuario.getSenha())) {
            String pagina_anterior = Cookie.getCookie("pagina_anterior");
            Cookie.eraseCookie("pagina_anterior");
            FacesContext.getCurrentInstance().getExternalContext().redirect(pagina_anterior);
        } else {
            senhaCorreta = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Senha incorreta.", null));
        }
    }

    public void voltarAoLogin() throws IOException {
        Cookie.eraseCookie("usuario");
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

    public boolean isSenhaCorreta() {
        return senhaCorreta;
    }

    public void setSenhaCorreta(boolean senhaCorreta) {
        this.senhaCorreta = senhaCorreta;
    }

}
