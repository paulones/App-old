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
import bo.PessoaFisicaJuridicaBO;
import bo.PessoaJuridicaBO;
import entidade.Cidade;
import entidade.Endereco;
import entidade.Estado;
import entidade.EstadoCivil;
import entidade.Nacionalidade;
import entidade.Pais;
import entidade.PessoaFisica;
import entidade.PessoaFisicaJuridica;
import entidade.PessoaJuridica;
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
    private Endereco endereco;
    private PessoaJuridica pessoaJuridica;
    private PessoaFisicaJuridica pessoaFisicaJuridica;

    private boolean success;

    private List<Pais> paisList;
    private List<Estado> estadoList;
    private List<Cidade> cidadeNatList;
    private List<Cidade> cidadeEndList;
    private List<Nacionalidade> nacionalidadeList;
    private List<EstadoCivil> estadoCivilList;
    private List<PessoaJuridica> pessoaJuridicaList;
    private List<PessoaFisicaJuridica> pessoaFisicaJuridicaList;

    private PessoaFisicaBO pessoaFisicaBO;
    private PessoaJuridicaBO pessoaJuridicaBO;
    private PessoaFisicaJuridicaBO pessoaFisicaJuridicaBO;
    private NacionalidadeBO nacionalidadeBO;
    private EstadoCivilBO estadoCivilBO;
    private PaisBO paisBO;
    private EstadoBO estadoBO;
    private CidadeBO cidadeBO;

    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            pessoaFisica = new PessoaFisica();
            endereco = new Endereco();
            pessoaJuridica = new PessoaJuridica();
            pessoaFisicaJuridica = new PessoaFisicaJuridica();
            success = false;

            pessoaFisicaBO = new PessoaFisicaBO();
            pessoaJuridicaBO = new PessoaJuridicaBO();
            pessoaFisicaJuridicaBO = new PessoaFisicaJuridicaBO();
            nacionalidadeBO = new NacionalidadeBO();
            estadoCivilBO = new EstadoCivilBO();
            paisBO = new PaisBO();
            estadoBO = new EstadoBO();
            cidadeBO = new CidadeBO();

            cidadeNatList = new ArrayList<>();
            cidadeEndList = new ArrayList<>();
            pessoaFisicaJuridicaList = new ArrayList<>();

            loadForm();
        }
    }

    private void loadForm() {
        paisList = paisBO.findAll();
        estadoList = estadoBO.findAll();
        nacionalidadeList = nacionalidadeBO.findAll();
        estadoCivilList = estadoCivilBO.findAll();
        pessoaJuridicaList = pessoaJuridicaBO.findAll();
    }

    public void getCitiesByState() {
        if (pessoaFisica.getEstadoFk() != null) {
            cidadeNatList = cidadeBO.getByStateId(pessoaFisica.getEstadoFk().getId());
        } else {
            cidadeNatList.clear();
        }
        if (endereco.getEstadoFk() != null) {
            cidadeEndList = cidadeBO.getByStateId(endereco.getEstadoFk().getId());
        } else {
            cidadeEndList.clear();
        }
    }

    public void cadastrar() {
        success = true;
    }

    public void removerVinculo(int index) {
        pessoaFisicaJuridicaList.remove(index);
    }

    public void vincularPessoaJuridica() {
        if (pessoaJuridica != null) {
            pessoaFisicaJuridica.setPessoaJuridicaFk(pessoaJuridica);
            boolean exists = false;
            for (PessoaFisicaJuridica pfj : pessoaFisicaJuridicaList) {
                if (pfj.getPessoaJuridicaFk().getCnpj().equals(pessoaJuridica.getCnpj())) {
                    exists = true;
                }
            }
            if (!exists) {
                pessoaFisicaJuridicaList.add(pessoaFisicaJuridica);
                pessoaFisicaJuridica = new PessoaFisicaJuridica();
            }
        }
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

    public List<Cidade> getCidadeNatList() {
        return cidadeNatList;
    }

    public void setCidadeNatList(List<Cidade> cidadeNatList) {
        this.cidadeNatList = cidadeNatList;
    }

    public List<Cidade> getCidadeEndList() {
        return cidadeEndList;
    }

    public void setCidadeEndList(List<Cidade> cidadeEndList) {
        this.cidadeEndList = cidadeEndList;
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

    public List<EstadoCivil> getEstadoCivilList() {
        return estadoCivilList;
    }

    public List<PessoaJuridica> getPessoaJuridicaList() {
        return pessoaJuridicaList;
    }

    public List<PessoaFisicaJuridica> getPessoaFisicaJuridicaList() {
        return pessoaFisicaJuridicaList;
    }

    public void setPessoaFisicaJuridicaList(List<PessoaFisicaJuridica> pessoaFisicaJuridicaList) {
        this.pessoaFisicaJuridicaList = pessoaFisicaJuridicaList;
    }

    public void setPessoaJuridicaList(List<PessoaJuridica> pessoaJuridicaList) {
        this.pessoaJuridicaList = pessoaJuridicaList;
    }

    public void setEstadoCivilList(List<EstadoCivil> estadoCivilList) {
        this.estadoCivilList = estadoCivilList;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public PessoaJuridica getPessoaJuridica() {
        return pessoaJuridica;
    }

    public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }

    public PessoaFisicaJuridica getPessoaFisicaJuridica() {
        return pessoaFisicaJuridica;
    }

    public void setPessoaFisicaJuridica(PessoaFisicaJuridica pessoaFisicaJuridica) {
        this.pessoaFisicaJuridica = pessoaFisicaJuridica;
    }

}
