/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bo.CidadeBO;
import bo.EstadoBO;
import bo.EstadoCivilBO;
import bo.NacionalidadeBO;
import bo.PaisBO;
import bo.PessoaFisicaBO;
import entidade.Cidade;
import entidade.Estado;
import entidade.Nacionalidade;
import entidade.PessoaFisica;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author paulones
 */
@SessionScoped
@ManagedBean(name = "pessoaFisicaBean")
public class PessoaFisicaBean implements Serializable {

    private PessoaFisica pessoaFisica;

    private String cpf;
    private String rg;
    private String inss;
    private Long cep;
    private boolean success;

    private List<Estado> estadoList;
    private List<Cidade> cidadeList;
    private List<Nacionalidade> nacionalidadeList;

    private PessoaFisicaBO pessoaFisicaBO;
    private NacionalidadeBO nacionalidadeBO;
    private EstadoCivilBO estadoCivilBO;
    private PaisBO paisBO;
    private EstadoBO estadoBO;
    private CidadeBO cidadeBO;

    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            pessoaFisica = new PessoaFisica();
            success = false;

            pessoaFisicaBO = new PessoaFisicaBO();
            nacionalidadeBO = new NacionalidadeBO();
            estadoCivilBO = new EstadoCivilBO();
            paisBO = new PaisBO();
            estadoBO = new EstadoBO();
            cidadeBO = new CidadeBO();

            estadoList = new ArrayList<>();
            cidadeList = new ArrayList<>();
            nacionalidadeList = new ArrayList<>();

            loadForm();
        }
    }

    private void loadForm() {
        estadoList = estadoBO.findAll();
        nacionalidadeList = nacionalidadeBO.findAll();
    }

    public void getCitiesByState() {
        if (pessoaFisica.getEstadoFk() != null) {
            cidadeList = cidadeBO.getByStateId(pessoaFisica.getEstadoFk().getId());
        }
    }

    public void cadastrar() {
        success = true;
    }

    public PessoaFisica getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getInss() {
        return inss;
    }

    public void setInss(String inss) {
        this.inss = inss;
    }

    public Long getCep() {
        return cep;
    }

    public void setCep(Long cep) {
        this.cep = cep;
    }

    public List<Estado> getEstadoList() {
        return estadoList;
    }

    public void setEstadoList(List<Estado> estadoList) {
        this.estadoList = estadoList;
    }

    public List<Cidade> getCidadeList() {
        return cidadeList;
    }

    public void setCidadeList(List<Cidade> cidadeList) {
        this.cidadeList = cidadeList;
    }

    public List<Nacionalidade> getNacionalidadeList() {
        return nacionalidadeList;
    }

    public void setNacionalidadeList(List<Nacionalidade> nacionalidadeList) {
        this.nacionalidadeList = nacionalidadeList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
