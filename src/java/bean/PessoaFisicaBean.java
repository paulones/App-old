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
import entidade.Pais;
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

    private boolean success;

    private List<Pais> paisList;
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

            paisList = new ArrayList<>();
            estadoList = new ArrayList<>();
            cidadeList = new ArrayList<>();
            nacionalidadeList = new ArrayList<>();

            loadForm();
        }
    }

    private void loadForm() {
        paisList = paisBO.findAll();
        estadoList = estadoBO.findAll();
        nacionalidadeList = nacionalidadeBO.findAll();
    }

    public void getCitiesByState() {
        if (pessoaFisica.getEstadoFk() != null) {
            cidadeList = cidadeBO.getByStateId(pessoaFisica.getEstadoFk().getId());
        } else{
            cidadeList.clear();
        }
    }

    public void cadastrar() {
        System.out.println(pessoaFisica.getCpf());
        System.out.println(pessoaFisica.getRg());
        success = true;
    }

    public PessoaFisica getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
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

    public List<Pais> getPaisList() {
        return paisList;
    }

    public void setPaisList(List<Pais> paisList) {
        this.paisList = paisList;
    }

}
