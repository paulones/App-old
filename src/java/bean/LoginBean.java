/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bo.LoginBO;
import bo.RecuperarSenhaBO;
import bo.UsuarioBO;
import entidade.RecuperarSenha;
import entidade.Usuario;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.mail.EmailException;
import org.postgresql.util.PSQLException;
import util.Cookie;
import util.EnviarEmail;
import util.GeradorMD5;
import util.ValidaCPF;

/**
 *
 * @author PRCC
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private String nome;
    private String cpf;
    private String senha;
    private String email;
    private String mensagem;
    private Usuario usuario;
    private UsuarioBO usuarioBO;
    private RecuperarSenhaBO rpBO;
    private LoginBO loginBO;
    private String licenca;
    private String cod;

    public void init() throws IOException {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            usuarioBO = new UsuarioBO();
            rpBO = new RecuperarSenhaBO();
            loginBO = new LoginBO();
            usuario = new Usuario();
            mensagem = "";
            nome = "";
            email = "";
            cpf = "";
            licenca = "";
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            cod = request.getParameter("cod");
            if (cod != null) {
                if (rpBO.findRecuperarSenhaByCod(cod) == null) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
                } else {
                    mensagem = "passChange";
                }
            }
        }
    }

    public void login() throws IOException {
        cpf = this.cpf.replace(".", "").replace("-", "");
        if (loginBO.expirado()) {
            usuario = usuarioBO.findUsuario(Long.valueOf(cpf));
            if (usuario != null) {
                if (senha.equals(usuario.getSenha())) {
                    Cookie.addCookie("usuario", cpf, 36000);
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/home.xhtml");
                } else {
                    mensagem = "loginFail";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Senha incorreta.", null));
                }
            } else {
                mensagem = "loginFail";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não existe.", null));
            }
        } else {
            mensagem = "licensingFail";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Tempo da licença expirou, adquira outra!", null));
        }
    }

    public void recuperarSenha() {
        try {
            cod = GeradorMD5.generate(email + "aabbccdd");
            RecuperarSenha rp = new RecuperarSenha();
            rpBO = new RecuperarSenhaBO();
            String accountActivation = "App - Ativar Conta";
            String mailtext = "Olá!\n\nObrigado pelo seu interesse em se registrar no App.\n\nPara concluir o processo, será preciso que você clique no link abaixo para ativar sua conta.\n\n";
            mailtext += "http://prcc.com.br/login.xhtml?cod=" + cod;
            mailtext += "\n\nAtenciosamente,\n\nPRCC - Gestão em TI e negócios.";
            EnviarEmail.enviar(mailtext, accountActivation, email);
            rp.setUsuarioFk(usuarioBO.findUsuarioByEmail(email).getCpf());
            rp.setCodigo(cod);
            rpBO.create(rp);
            email = "";
            mensagem = "loginSuccess";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitação enviada. Verifique seu e-mail.", null));
        } catch (EmailException e) {
            e.printStackTrace();
            mensagem = "loginFail";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ocorreu um erro ao tentar enviar e-mail de recuperação de senha. Tente novamente.", null));
        }
    }

    public void alterarSenha() {
        RecuperarSenha rp = rpBO.findRecuperarSenhaByCod(cod);
        usuario = usuarioBO.findUsuario(rp.getUsuarioFk());
        usuario.setSenha(senha);
        usuarioBO.edit(usuario);
        rpBO.destroy(rp);
        mensagem = "loginSuccess";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Senha alterada com sucesso!", null));
    }

    public void registrar() {
        System.out.println(nome);
        cpf = this.cpf.replace(".", "").replace("-", "");
        if (ValidaCPF.isCPF(cpf)) {
            Long cpf = Long.valueOf(this.cpf);
            usuario.setCpf(cpf);
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setSenha(senha);
            if (usuarioBO.findUsuario(cpf) == null) {
                if (usuarioBO.findUsuarioByEmail(email) == null) {
                    usuarioBO.create(usuario);
                    mensagem = "loginSuccess";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário registrado com sucesso!", null));
                    nome = "";
                    email = "";
                    this.cpf = "";
                } else {
                    mensagem = "registerFail";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Já existe um usuário com o e-mail \"" + this.email + "\" cadastrado.", null));
                    this.email = "";
                }
            } else {
                mensagem = "registerFail";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Já existe um usuário com o CPF \"" + this.cpf + "\" cadastrado.", null));
                this.cpf = "";
            }
        } else {
            mensagem = "registerFail";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor, digite um CPF válido.", null));
            this.cpf = "";
        }
    }
    
    public void licenciar(){
        if (loginBO.licenciar(licenca)) {
            mensagem = "loginSuccess";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Licença registrada com sucesso!", null));
        } else {
            mensagem = "licensingFail";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Licença inserida inválida!", null));
        }
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLicenca() {
        return licenca;
    }

    public void setLicenca(String licenca) {
        this.licenca = licenca;
    }

}
