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
import util.Cookie;
import util.EnviarEmail;
import util.GeradorMD5;

/**
 *
 * @author PRCC
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private String mensagem;
    private Usuario usuario;
    private String licenca;
    private String cod;

    public void init() throws IOException {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            usuario = new Usuario();
            mensagem = "";
            licenca = "";
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            cod = request.getParameter("cod");
            if (cod != null) {
                if (RecuperarSenhaBO.findRecuperarSenhaByCod(cod) == null) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/login.xhtml");
                } else {
                    mensagem = "passChange";
                }
            }
        }
    }

    public void login() throws IOException {
        Usuario usuarioBD = UsuarioBO.findUsuarioByCPF(usuario.getCpf());
        if (usuarioBD != null) {
            if (usuarioBD.getSenha().equals(usuario.getSenha())) {
                if (!LoginBO.expirado(usuario.getCpf())) {
                    Cookie.addCookie("usuario", usuario.getCpf(), 36000);
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/home.xhtml");
                } else {
                    mensagem = "licensingFail";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Tempo da licença expirou, adquira outra!", null));
                }
            } else {
                mensagem = "loginFail";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Senha incorreta.", null));
            }
        } else {
            mensagem = "loginFail";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não existe.", null));
        }
    }

    public void recuperarSenha() {
        try {
            cod = GeradorMD5.generate(usuario.getEmail() + "aabbccdd");
            RecuperarSenha rp = new RecuperarSenha();
            String accountActivation = "App - Ativar Conta";
            String mailtext = "Olá!\n\nObrigado pelo seu interesse em se registrar no App.\n\nPara concluir o processo, será preciso que você clique no link abaixo para ativar sua conta.\n\n";
            mailtext += "http://prcc.com.br/login.xhtml?cod=" + cod;
            mailtext += "\n\nAtenciosamente,\n\nPRCC - Gestão em TI e negócios.";
            EnviarEmail.enviar(mailtext, accountActivation, usuario.getEmail());
            rp.setId(UsuarioBO.findUsuarioByEmail(usuario.getEmail()).getId());
            rp.setCodigo(cod);
            RecuperarSenhaBO.create(rp);
            usuario.setEmail(null);
            mensagem = "loginSuccess";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitação enviada. Verifique seu e-mail.", null));
        } catch (EmailException e) {
            e.printStackTrace();
            mensagem = "loginFail";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ocorreu um erro ao tentar enviar e-mail de recuperação de senha. Tente novamente.", null));
        }
    }

    public void alterarSenha() {
        String senha = usuario.getSenha();
        RecuperarSenha rp = RecuperarSenhaBO.findRecuperarSenhaByCod(cod);
        usuario = UsuarioBO.findUsuario(rp.getUsuario().getId());
        usuario.setSenha(senha);
        UsuarioBO.edit(usuario);
        RecuperarSenhaBO.destroy(rp);
        mensagem = "loginSuccess";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Senha alterada com sucesso!", null));
    }

    public void registrar() {
        if (UsuarioBO.findAutorizacaoByCPF(usuario.getCpf()) != null) {
            if (UsuarioBO.findUsuarioByCPF(usuario.getCpf()) == null) {
                if (UsuarioBO.findUsuarioByEmail(usuario.getEmail()) == null) {
                    UsuarioBO.create(usuario);
                    mensagem = "loginSuccess";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário registrado com sucesso!", null));
                    usuario.setNome(null);
                    usuario.setEmail(null);
                    usuario.setCpf(null);
                } else {
                    mensagem = "registerFail";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Já existe um usuário com o e-mail \"" + usuario.getEmail() + "\" cadastrado.", null));
                    usuario.setEmail(null);
                }
            } else {
                mensagem = "registerFail";
                String cpf = String.format("%s.%s.%s-%s", usuario.getCpf().substring(0, 3), usuario.getCpf().substring(3, 6), usuario.getCpf().substring(6, 9), usuario.getCpf().substring(9));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Já existe um usuário com o CPF \"" + cpf + "\" cadastrado.", null));
                usuario.setCpf(null);
            }
        } else {
                mensagem = "registerFail";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não autorizado. Verifique sua permissão de acesso.", null));
                usuario.setCpf(null);
            }
    }

    public void licenciar() {
        if (LoginBO.licenciar(licenca)) {
            mensagem = "loginSuccess";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Licença registrada com sucesso!", null));
        } else {
            mensagem = "licensingFail";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Licença inválida!", null));
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

    public String getLicenca() {
        return licenca;
    }

    public void setLicenca(String licenca) {
        this.licenca = licenca;
    }
}
