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
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
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
    private String cpf;
    private String pagina_anterior;
    private boolean senhaCorreta;
    private UsuarioBO usuarioBO;
    private HttpServletRequest request;

    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            usuarioBO = new UsuarioBO();
            senha = "";
            senhaCorreta = true;
            cpf = Cookie.getCookie("usuario");
            pagina_anterior = Cookie.getCookie("pagina_anterior");
            usuario = usuarioBO.findUsuario(Long.valueOf(cpf));
        }
    }

    public void login() throws IOException {
        if (senha.equals(usuario.getSenha())) {
            Cookie.addCookie("usuario", cpf, 36000);
            if (pagina_anterior != null) {
                Cookie.apagarCookie("pagina_anterior");
                FacesContext.getCurrentInstance().getExternalContext().redirect(pagina_anterior);
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/home.xhtml");
            }
        } else {
            senhaCorreta = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Senha incorreta.", null));
        }
    }

    public void voltarAoLogin() throws IOException {
        Cookie.apagarCookie("usuario");
        FacesContext.getCurrentInstance().getExternalContext().redirect("/login.xhtml");
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
