/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bo.EnderecoBO;
import bo.PessoaFisicaBO;
import bo.PessoaJuridicaBO;
import bo.TipoProcessoBO;
import bo.TipoRecursoBO;
import entidade.Bem;
import entidade.Endereco;
import entidade.EnderecoPessoa;
import entidade.Executado;
import entidade.PessoaFisica;
import entidade.PessoaJuridica;
import entidade.ProcessoJudicial;
import entidade.TipoProcesso;
import entidade.TipoRecurso;
import entidade.VinculoProcessual;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import util.Base64Crypt;

/**
 *
 * @author paulones
 */
@SessionScoped
@ManagedBean(name = "processoJudicialBean")
public class ProcessoJudicialBean implements Serializable {

    private ProcessoJudicial processoJudicial;
    private Executado executado;
    private EnderecoPessoa enderecoPessoaFisica;
    private EnderecoPessoa enderecoPessoaJuridica;
    private EnderecoPessoa enderecoPessoaModalFisica;
    private EnderecoPessoa enderecoPessoaModalJuridica;
    private Bem bem;
    private VinculoProcessual vinculoProcessual;

    private List<PessoaFisica> pessoaFisicaList;
    private List<PessoaJuridica> pessoaJuridicaList;
    private List<Bem> bemList;
    private List<TipoRecurso> tipoDeRecursoList;
    private List<TipoProcesso> tipoDoProcessoList;
    private List<VinculoProcessual> vinculoProcessualList;

    private PessoaFisicaBO pessoaFisicaBO;
    private PessoaJuridicaBO pessoaJuridicaBO;
    private TipoRecursoBO tipoRecursoBO;
    private TipoProcessoBO tipoProcessoBO;
    private EnderecoBO enderecoBO;

    private Integer bens;
    private Integer vinculos;
    private String tipoDoExecutado;
    private String executadoPF;
    private String executadoPJ;

    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            executado = new Executado();
            processoJudicial = new ProcessoJudicial();
            enderecoPessoaFisica = new EnderecoPessoa();
            enderecoPessoaJuridica = new EnderecoPessoa();
            enderecoPessoaModalFisica = new EnderecoPessoa();

            pessoaFisicaBO = new PessoaFisicaBO();
            pessoaJuridicaBO = new PessoaJuridicaBO();
            tipoRecursoBO = new TipoRecursoBO();
            tipoProcessoBO = new TipoProcessoBO();
            enderecoBO = new EnderecoBO();

            bens = 0;
            vinculos = 0;
            executadoPF = "";
            executadoPJ = "";

            bemList = new ArrayList<>();
            vinculoProcessualList = new ArrayList<>();

            carregarFormulario();
        }
    }

    private void carregarFormulario() { // Carregar listas do formulário
        pessoaFisicaList = pessoaFisicaBO.findAllActive();
        pessoaJuridicaList = pessoaJuridicaBO.findAllActive();
        tipoDeRecursoList = tipoRecursoBO.findAll();
        tipoDoProcessoList = tipoProcessoBO.findAll();
    }

    public void adicionarBens() {
        bemList = new ArrayList<>();
        for (int i = 0; i < bens; i++) {
            bem = new Bem();
            bemList.add(bem);
        }
    }

    public void adicionarVinculosProcessuais() {
        vinculoProcessualList = new ArrayList<>();
        for (int i = 0; i < vinculos; i++) {
            vinculoProcessual = new VinculoProcessual();
            vinculoProcessualList.add(vinculoProcessual);
        }
    }

    public void exibirExecutado() {
        if (processoJudicial.getExecutado().equals("PF")) {
            PessoaFisica pessoaFisica = pessoaFisicaBO.findPessoaFisica(Integer.valueOf(Base64Crypt.decrypt(executadoPF)));
            enderecoPessoaFisica = new EnderecoPessoa(pessoaFisica, enderecoBO.findPFAddress(pessoaFisica.getId()));
        } else if (processoJudicial.getExecutado().equals("PJ")) {
            PessoaJuridica pessoaJuridica = pessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(Base64Crypt.decrypt(executadoPJ)));
            enderecoPessoaJuridica = new EnderecoPessoa(pessoaJuridica, enderecoBO.findPJAddress(pessoaJuridica.getId()));
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

    public void cadastrar() {
        processoJudicial.setExecutadoFk(executadoPF != null ? Integer.valueOf(Base64Crypt.decrypt(executadoPF)) : Integer.valueOf(Base64Crypt.decrypt(executadoPJ)));
    }

    public ProcessoJudicial getProcessoJudicial() {
        return processoJudicial;
    }

    public void setProcessoJudicial(ProcessoJudicial processoJudicial) {
        this.processoJudicial = processoJudicial;
    }

    public Executado getExecutado() {
        return executado;
    }

    public void setExecutado(Executado executado) {
        this.executado = executado;
    }

    public List<PessoaFisica> getPessoaFisicaList() {
        return pessoaFisicaList;
    }

    public void setPessoaFisicaList(List<PessoaFisica> pessoaFisicaList) {
        this.pessoaFisicaList = pessoaFisicaList;
    }

    public List<PessoaJuridica> getPessoaJuridicaList() {
        return pessoaJuridicaList;
    }

    public void setPessoaJuridicaList(List<PessoaJuridica> pessoaJuridicaList) {
        this.pessoaJuridicaList = pessoaJuridicaList;
    }

    public List<Bem> getBemList() {
        return bemList;
    }

    public void setBemList(List<Bem> bemList) {
        this.bemList = bemList;
    }

    public List<VinculoProcessual> getVinculoProcessualList() {
        return vinculoProcessualList;
    }

    public void setVinculoProcessualList(List<VinculoProcessual> vinculoProcessualList) {
        this.vinculoProcessualList = vinculoProcessualList;
    }

    public Integer getBens() {
        return bens;
    }

    public void setBens(Integer bens) {
        this.bens = bens;
    }

    public List<TipoRecurso> getTipoDeRecursoList() {
        return tipoDeRecursoList;
    }

    public void setTipoDeRecursoList(List<TipoRecurso> tipoDeRecursoList) {
        this.tipoDeRecursoList = tipoDeRecursoList;
    }

    public Integer getVinculos() {
        return vinculos;
    }

    public void setVinculos(Integer vinculos) {
        this.vinculos = vinculos;
    }

    public List<TipoProcesso> getTipoDoProcessoList() {
        return tipoDoProcessoList;
    }

    public void setTipoDoProcessoList(List<TipoProcesso> tipoDoProcessoList) {
        this.tipoDoProcessoList = tipoDoProcessoList;
    }

    public EnderecoPessoa getEnderecoPessoaFisica() {
        return enderecoPessoaFisica;
    }

    public void setEnderecoPessoaFisica(EnderecoPessoa enderecoPessoaFisica) {
        this.enderecoPessoaFisica = enderecoPessoaFisica;
    }

    public EnderecoPessoa getEnderecoPessoaJuridica() {
        return enderecoPessoaJuridica;
    }

    public void setEnderecoPessoaJuridica(EnderecoPessoa enderecoPessoaJuridica) {
        this.enderecoPessoaJuridica = enderecoPessoaJuridica;
    }

    public String getTipoDoExecutado() {
        return tipoDoExecutado;
    }

    public void setTipoDoExecutado(String tipoDoExecutado) {
        this.tipoDoExecutado = tipoDoExecutado;
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

    public String getExecutadoPF() {
        return executadoPF;
    }

    public void setExecutadoPF(String executadoPF) {
        this.executadoPF = executadoPF;
    }

    public String getExecutadoPJ() {
        return executadoPJ;
    }

    public void setExecutadoPJ(String executadoPJ) {
        this.executadoPJ = executadoPJ;
    }

}
