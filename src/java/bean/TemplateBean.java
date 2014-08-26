/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import bo.EnderecoBO;
import bo.UsuarioBO;
import entidade.EnderecoPessoa;
import entidade.PessoaFisica;
import entidade.PessoaJuridica;
import entidade.Usuario;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import util.Base64Crypt;
import util.Cookie;

/**
 *
 * @author ipti004
 */
@ManagedBean(name = "templateBean")
@SessionScoped
public class TemplateBean implements Serializable{
    
    private Usuario usuario;
    private UsuarioBO usuarioBO;
    private EnderecoBO enderecoBO;
    
    private EnderecoPessoa enderecoPessoaModalFisica;
    private EnderecoPessoa enderecoPessoaModalJuridica;
    
    public void init(){
        if (!FacesContext.getCurrentInstance().isPostback()){
            usuarioBO = new UsuarioBO();
            enderecoBO = new EnderecoBO();
            usuario = usuarioBO.findUsuarioByCPF(Cookie.getCookie("usuario"));
            
            enderecoPessoaModalFisica = new EnderecoPessoa();
            enderecoPessoaModalJuridica = new EnderecoPessoa();
        }
    }
    
    public void exibirModal(Object pessoa, String tipoPessoa) {
        if (tipoPessoa.equals("PF")) {
            PessoaFisica pessoaFisica = (PessoaFisica) pessoa;
            enderecoPessoaModalFisica = new EnderecoPessoa(pessoa, enderecoBO.findPFAddress(pessoaFisica.getId()));
        } else if (tipoPessoa.equals("PJ")) {
            PessoaJuridica pessoaJuridica = (PessoaJuridica) pessoa;
            enderecoPessoaModalJuridica = new EnderecoPessoa(pessoa, enderecoBO.findPJAddress(pessoaJuridica.getId()));
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public EnderecoPessoa getEnderecoPessoaModalFisica() {
        return enderecoPessoaModalFisica;
    }

    public void setEnderecoPessoaModalFisica(EnderecoPessoa enderecoPessoaModalFisica) {
        this.enderecoPessoaModalFisica = enderecoPessoaModalFisica;
    }

    public EnderecoPessoa getEnderecoPessoaModalJuridica() {
        return enderecoPessoaModalJuridica;
    }

    public void setEnderecoPessoaModalJuridica(EnderecoPessoa enderecoPessoaModalJuridica) {
        this.enderecoPessoaModalJuridica = enderecoPessoaModalJuridica;
    }

}
