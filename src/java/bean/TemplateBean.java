/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import bo.CidadeBO;
import bo.EnderecoBO;
import bo.EstadoBO;
import bo.FuncaoBO;
import bo.PessoaFisicaBO;
import bo.PessoaJuridicaBO;
import bo.PessoaJuridicaSucessaoBO;
import bo.ProcessoJudicialBO;
import bo.TipoEmpresarialBO;
import bo.UsuarioBO;
import entidade.Cidade;
import entidade.Endereco;
import entidade.EnderecoPessoa;
import entidade.Estado;
import entidade.Executado;
import entidade.Funcao;
import entidade.PessoaFisica;
import entidade.PessoaJuridica;
import entidade.PessoaJuridicaSucessao;
import entidade.ProcessoJudicial;
import entidade.TipoEmpresarial;
import entidade.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
    private PessoaJuridicaSucessaoBO pessoaJuridicaSucessaoBO;
    private TipoEmpresarialBO tipoEmpresarialBO;
    private EstadoBO estadoBO;
    private CidadeBO cidadeBO;
    private FuncaoBO funcaoBO;
    
    private EnderecoPessoa enderecoPessoa;
    private Executado executado;
    private PessoaJuridicaSucessao pessoaJuridicaSucessao;
    
    private List<TipoEmpresarial> tipoEmpresarialList;
    private List<Estado> estadoList;
    private List<Cidade> cidadeEndList;
    private List<Funcao> funcaoList;
    
    private String register;
    private String idfk;
    private String tabela;
    private String searchValue;
    private String sucessaoId;
    
    public void init(){
        if (!FacesContext.getCurrentInstance().isPostback()){
            usuarioBO = new UsuarioBO();
            enderecoBO = new EnderecoBO();
            pessoaFisicaBO = new PessoaFisicaBO();
            pessoaJuridicaBO = new PessoaJuridicaBO();
            processoJudicialBO = new ProcessoJudicialBO();
            pessoaJuridicaSucessaoBO = new PessoaJuridicaSucessaoBO();
            estadoBO = new EstadoBO();
            tipoEmpresarialBO = new TipoEmpresarialBO();
            cidadeBO = new CidadeBO();
            funcaoBO = new FuncaoBO();
            usuario = usuarioBO.findUsuarioByCPF(Cookie.getCookie("usuario"));
            
            enderecoPessoa = new EnderecoPessoa(new PessoaJuridica(), new Endereco());
            executado = new Executado();
            pessoaJuridicaSucessao = new PessoaJuridicaSucessao();
            
            cidadeEndList = new ArrayList<>();
            
            searchValue = "";
            register = "";
            idfk = "";
            tabela = "";
        }
    }
    
    public void exibirModalNew(){
        if (tabela.equals("PF")) {
            
        } else if (tabela.equals("PJ")) {
            carregarFormularioModalPJ();
        }
    }
    
    public void carregarFormularioModalPJ() {
        estadoList = estadoBO.findAll();
        tipoEmpresarialList = tipoEmpresarialBO.findAll();
        funcaoList = funcaoBO.findAll();
    }
    
    public void getCidadesPeloEstado() { 
        if (enderecoPessoa.getEndereco().getEstadoFk() != null) {
            cidadeEndList = cidadeBO.getByStateId(enderecoPessoa.getEndereco().getEstadoFk().getId());
        } else {
            cidadeEndList.clear();
        }
    }
    
    public void cadastrarPJ(){
        System.out.println("ae");
    }
    
    public void exibirModalInfo() {
        if (tabela.equals("PF")) {
            PessoaFisica pessoaFisica = pessoaFisicaBO.findPessoaFisica(Integer.valueOf(idfk));
            enderecoPessoa = new EnderecoPessoa(pessoaFisica, enderecoBO.findPFAddress(pessoaFisica.getId()));
            executado = new Executado();
            pessoaJuridicaSucessao = new PessoaJuridicaSucessao();
        } else if (tabela.equals("PJ")) {
            PessoaJuridica pessoaJuridica = pessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(idfk));
            enderecoPessoa = new EnderecoPessoa(pessoaJuridica, enderecoBO.findPJAddress(pessoaJuridica.getId()));
            executado = new Executado();
            pessoaJuridicaSucessao = new PessoaJuridicaSucessao();
        } else if (tabela.equals("PJUD")){ 
            ProcessoJudicial pjud = processoJudicialBO.findProcessoJudicial(Integer.valueOf(idfk));
            if (pjud.getExecutado().equals("PF")){
                PessoaFisica pf = pessoaFisicaBO.findPessoaFisica(pjud.getExecutadoFk());
                executado = new Executado(pjud, new EnderecoPessoa(pf, enderecoBO.findPFAddress(pf.getId())));
            } else {
                PessoaJuridica pj = pessoaJuridicaBO.findPessoaJuridica(pjud.getExecutadoFk());
                executado = new Executado(pjud, new EnderecoPessoa(pj, enderecoBO.findPFAddress(pj.getId())));
            }
            pessoaJuridicaSucessao = new PessoaJuridicaSucessao();
            enderecoPessoa = new EnderecoPessoa();
        } else if (tabela.equals("PJS")){
            pessoaJuridicaSucessao = pessoaJuridicaSucessaoBO.findPessoaJuridicaSucessao(Integer.valueOf(idfk));
            executado = new Executado();
            enderecoPessoa = new EnderecoPessoa();
        }
    }
    
    public void exibirSucessao(){ 
        pessoaJuridicaSucessao = pessoaJuridicaSucessaoBO.findPessoaJuridicaSucessao(Integer.valueOf(sucessaoId));
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

    public PessoaJuridicaSucessao getPessoaJuridicaSucessao() {
        return pessoaJuridicaSucessao;
    }

    public void setPessoaJuridicaSucessao(PessoaJuridicaSucessao pessoaJuridicaSucessao) {
        this.pessoaJuridicaSucessao = pessoaJuridicaSucessao;
    }

    public String getSucessaoId() {
        return sucessaoId;
    }

    public void setSucessaoId(String sucessaoId) {
        this.sucessaoId = sucessaoId;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public List<TipoEmpresarial> getTipoEmpresarialList() {
        return tipoEmpresarialList;
    }

    public void setTipoEmpresarialList(List<TipoEmpresarial> tipoEmpresarialList) {
        this.tipoEmpresarialList = tipoEmpresarialList;
    }

    public List<Estado> getEstadoList() {
        return estadoList;
    }

    public void setEstadoList(List<Estado> estadoList) {
        this.estadoList = estadoList;
    }

    public List<Cidade> getCidadeEndList() {
        return cidadeEndList;
    }

    public void setCidadeEndList(List<Cidade> cidadeEndList) {
        this.cidadeEndList = cidadeEndList;
    }

    public List<Funcao> getFuncaoList() {
        return funcaoList;
    }

    public void setFuncaoList(List<Funcao> funcaoList) {
        this.funcaoList = funcaoList;
    }

}
