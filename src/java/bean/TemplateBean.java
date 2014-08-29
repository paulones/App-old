/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import bo.EnderecoBO;
import bo.PessoaFisicaBO;
import bo.PessoaJuridicaBO;
import bo.ProcessoJudicialBO;
import bo.UsuarioBO;
import entidade.EnderecoPessoa;
import entidade.Executado;
import entidade.PessoaFisica;
import entidade.PessoaJuridica;
import entidade.ProcessoJudicial;
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
    private PessoaFisicaBO pessoaFisicaBO;
    private PessoaJuridicaBO pessoaJuridicaBO;
    private ProcessoJudicialBO processoJudicialBO;
    
    private EnderecoPessoa enderecoPessoaModalFisica;
    private EnderecoPessoa enderecoPessoaModalJuridica;
    private Executado executado;
    
    public void init(){
        if (!FacesContext.getCurrentInstance().isPostback()){
            usuarioBO = new UsuarioBO();
            enderecoBO = new EnderecoBO();
            pessoaFisicaBO = new PessoaFisicaBO();
            pessoaJuridicaBO = new PessoaJuridicaBO();
            processoJudicialBO = new ProcessoJudicialBO();
            usuario = usuarioBO.findUsuarioByCPF(Cookie.getCookie("usuario"));
            
            enderecoPessoaModalFisica = new EnderecoPessoa();
            enderecoPessoaModalJuridica = new EnderecoPessoa();
            executado = new Executado();
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
    
    public void exibirModal(String id, String tabela) {
        if (tabela.equals("PF")) {
            PessoaFisica pf = pessoaFisicaBO.findPessoaFisica(Integer.valueOf(Base64Crypt.decrypt(id)));
            enderecoPessoaModalFisica = new EnderecoPessoa(pf, enderecoBO.findPFAddress(pf.getId()));
        } else if (tabela.equals("PJ")) {
            PessoaJuridica pj = pessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(Base64Crypt.decrypt(id)));
            enderecoPessoaModalJuridica = new EnderecoPessoa(pj, enderecoBO.findPJAddress(pj.getId()));
        } else if (tabela.equals("PJUD")){
            ProcessoJudicial pjud = processoJudicialBO.findProcessoJudicial(Integer.valueOf(Base64Crypt.decrypt(id)));
            if (pjud.getExecutado().equals("PF")){
                PessoaFisica pf = pessoaFisicaBO.findPessoaFisica(pjud.getExecutadoFk());
                executado = new Executado(pjud, new EnderecoPessoa(pf, enderecoBO.findPFAddress(pf.getId())));
            } else {
                PessoaJuridica pj = pessoaJuridicaBO.findPessoaJuridica(pjud.getExecutadoFk());
                executado = new Executado(pjud, new EnderecoPessoa(pj, enderecoBO.findPFAddress(pj.getId())));
            }
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
