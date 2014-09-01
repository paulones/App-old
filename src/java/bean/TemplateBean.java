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
import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
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
    
    private EnderecoPessoa enderecoPessoa;
    private Executado executado;
    
    private String idfk;
    private String tabela;
    private String searchValue;
    
    public void init(){
        if (!FacesContext.getCurrentInstance().isPostback()){
            usuarioBO = new UsuarioBO();
            enderecoBO = new EnderecoBO();
            pessoaFisicaBO = new PessoaFisicaBO();
            pessoaJuridicaBO = new PessoaJuridicaBO();
            processoJudicialBO = new ProcessoJudicialBO();
            usuario = usuarioBO.findUsuarioByCPF(Cookie.getCookie("usuario"));
            
            enderecoPessoa = new EnderecoPessoa();
            executado = new Executado();
            
            searchValue = "";
        }
    }
    
    public void exibirModal(Object pessoa, String tipoPessoa) {
        if (tipoPessoa.equals("PF")) {
            PessoaFisica pessoaFisica = (PessoaFisica) pessoa;
            enderecoPessoa = new EnderecoPessoa(pessoa, enderecoBO.findPFAddress(pessoaFisica.getId()));
            tabela = "PF";
        } else if (tipoPessoa.equals("PJ")) {
            PessoaJuridica pessoaJuridica = (PessoaJuridica) pessoa;
            enderecoPessoa = new EnderecoPessoa(pessoa, enderecoBO.findPJAddress(pessoaJuridica.getId()));
            tabela = "PJ";
        }
    }
    
    public void exibirModalLog() {
        if (tabela.equals("PF")) {
            PessoaFisica pf = pessoaFisicaBO.findPessoaFisica(Integer.valueOf(Base64Crypt.decrypt(idfk)));
            enderecoPessoa = new EnderecoPessoa(pf, enderecoBO.findPFAddress(pf.getId()));
            executado = new Executado();
        } else if (tabela.equals("PJ")) {
            PessoaJuridica pj = pessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(Base64Crypt.decrypt(idfk)));
            enderecoPessoa = new EnderecoPessoa(pj, enderecoBO.findPJAddress(pj.getId()));
            executado = new Executado();
        } else if (tabela.equals("PJUD")){ 
            ProcessoJudicial pjud = processoJudicialBO.findProcessoJudicial(Integer.valueOf(Base64Crypt.decrypt(idfk)));
            if (pjud.getExecutado().equals("PF")){
                PessoaFisica pf = pessoaFisicaBO.findPessoaFisica(pjud.getExecutadoFk());
                executado = new Executado(pjud, new EnderecoPessoa(pf, enderecoBO.findPFAddress(pf.getId())));
            } else {
                PessoaJuridica pj = pessoaJuridicaBO.findPessoaJuridica(pjud.getExecutadoFk());
                executado = new Executado(pjud, new EnderecoPessoa(pj, enderecoBO.findPFAddress(pj.getId())));
            }
            enderecoPessoa = new EnderecoPessoa();
        }
    }
    
    public void redirecionar(String tela, String id) throws IOException{
        String queryString = "";
        queryString =  (id.equals("")) ? "" : "?id=" + Base64Crypt.encrypt(id);
        FacesContext.getCurrentInstance().getExternalContext().redirect(tela + ".xhtml" + queryString);
    }
    
    public void search() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("/procura-geral.xhtml?value="+searchValue);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public EnderecoPessoa getEnderecoPessoa() {
        return enderecoPessoa;
    }

    public void setEnderecoPessoa(EnderecoPessoa enderecoPessoa) {
        this.enderecoPessoa = enderecoPessoa;
    }

    public Executado getExecutado() {
        return executado;
    }

    public void setExecutado(Executado executado) {
        this.executado = executado;
    }

    public String getIdfk() {
        return idfk;
    }

    public void setIdfk(String idfk) {
        this.idfk = idfk;
    }

    public String getTabela() {
        return tabela;
    }

    public void setTabela(String tabela) {
        this.tabela = tabela;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }
    
}
