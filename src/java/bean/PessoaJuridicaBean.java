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
import bo.PessoaFisicaJuridicaBO;
import bo.PessoaJuridicaBO;
import bo.TipoEmpresarialBO;
import bo.UsuarioBO;
import entidade.Cidade;
import entidade.Endereco;
import entidade.Estado;
import entidade.Funcao;
import entidade.PessoaFisica;
import entidade.PessoaFisicaJuridica;
import entidade.PessoaJuridica;
import entidade.TipoEmpresarial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import util.Cookie;

/**
 *
 * @author Pedro
 */
@SessionScoped
@ManagedBean(name = "pessoaJuridicaBean")
public class PessoaJuridicaBean implements Serializable{
    
    private PessoaJuridica pessoaJuridica;
    private Endereco endereco;
    private PessoaFisica pessoaFisica;
    private PessoaFisicaJuridica pessoaFisicaJuridica;
    
    private String register;
    
    private TipoEmpresarialBO tipoEmpresarialBO;
    private PessoaFisicaBO pessoaFisicaBO;
    private PessoaJuridicaBO pessoaJuridicaBO;
    private PessoaFisicaJuridicaBO pessoaFisicaJuridicaBO;
    private EstadoBO estadoBO;
    private CidadeBO cidadeBO;
    private FuncaoBO funcaoBO;
    private EnderecoBO enderecoBO;
    
    private List<TipoEmpresarial> tipoEmpresarialList;
    private List<Estado> estadoList;
    private List<Cidade> cidadeEndList;
    private List<PessoaFisica> pessoaFisicaList;
    private List<PessoaFisicaJuridica> pessoaFisicaJuridicaList;
    private List<Funcao> funcaoList;
    
    public void init(){
        if (!FacesContext.getCurrentInstance().isPostback()) {
            pessoaJuridica = new PessoaJuridica();
            endereco = new Endereco();
            pessoaFisica = new PessoaFisica();
            pessoaFisicaJuridica = new PessoaFisicaJuridica();
            
            register = "";
            
            tipoEmpresarialBO = new TipoEmpresarialBO();
            pessoaFisicaBO = new PessoaFisicaBO();
            pessoaJuridicaBO = new PessoaJuridicaBO();
            pessoaFisicaJuridicaBO = new PessoaFisicaJuridicaBO();
            estadoBO = new EstadoBO();
            cidadeBO = new CidadeBO();
            funcaoBO = new FuncaoBO();
            enderecoBO = new EnderecoBO();
            
            tipoEmpresarialList = new ArrayList<>();
            cidadeEndList = new ArrayList<>();
            pessoaFisicaJuridicaList = new ArrayList<>();
            
            loadForm();
        }
    }
    
    public void loadForm(){
        estadoList = estadoBO.findAll();
        tipoEmpresarialList = tipoEmpresarialBO.findAll();
        pessoaFisicaList = pessoaFisicaBO.findAll();
        funcaoList = funcaoBO.findAll();
    }
    
    public void getCitiesByState() {
        if (endereco.getEstadoFk() != null) {
            cidadeEndList = cidadeBO.getByStateId(endereco.getEstadoFk().getId());
        } else {
            cidadeEndList.clear();
        }
    }
    
    public void vincularPessoaFisica() {
        pessoaFisicaJuridica.setPessoaFisicaFk(pessoaFisica);
        boolean exists = false;
        for (PessoaFisicaJuridica pfj : pessoaFisicaJuridicaList) {
            if (pfj.getPessoaFisicaFk().getCpf().equals(pessoaFisica.getCpf())) {
                exists = true;
            }
        }
        if (!exists) {
            pessoaFisicaJuridicaList.add(pessoaFisicaJuridica);
            pessoaFisicaJuridica = new PessoaFisicaJuridica();
        }
    }
    
    public void removerVinculo(int index) {
        pessoaFisicaJuridicaList.remove(index);
    }
    
    public void cadastrar() {
        PessoaJuridica pjDB = pessoaJuridicaBO.findDuplicates(pessoaJuridica);
        if (pjDB == null || pessoaJuridica.getCnpj().isEmpty()) {
            pessoaJuridica.setStatus('A');
            UsuarioBO usuarioBO = new UsuarioBO();
            pessoaJuridica.setUsuarioFk(usuarioBO.findUsuarioByCPF(Cookie.getCookie("usuario")));
            pessoaJuridicaBO.create(pessoaJuridica);
            endereco.setTipo("PJ");
            endereco.setIdFk(pessoaJuridica.getId());
            enderecoBO.create(endereco);
            for (PessoaFisicaJuridica pfj : pessoaFisicaJuridicaList) {
                pfj.setPessoaJuridicaFk(pessoaJuridica);
                pessoaFisicaJuridicaBO.create(pfj);
            }
            register = "success";
            pessoaJuridica = new PessoaJuridica();
            endereco = new Endereco();
            pessoaFisicaJuridicaList = new ArrayList<>();
        }else{
            register = "fail";
            String cnpj = pjDB.getCnpj().substring(0,3)+"."+pjDB.getCnpj().substring(3,6)+"."+pjDB.getCnpj().substring(6,9)+"/"+pjDB.getCnpj().substring(9,13)+"-"+pjDB.getCnpj().substring(13);
            String message = "Já existe empresa cadastrada com o CNPJ "+cnpj;
            message += pjDB.getNome()!= null ? "\nNome: " + pjDB.getNome():"";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }
    }

    public PessoaJuridica getPessoaJuridica() {
        return pessoaJuridica;
    }

    public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }

    public List<TipoEmpresarial> getTipoEmpresarialList() {
        return tipoEmpresarialList;
    }

    public void setTipoEmpresarialList(List<TipoEmpresarial> tipoEmpresarialList) {
        this.tipoEmpresarialList = tipoEmpresarialList;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Cidade> getCidadeEndList() {
        return cidadeEndList;
    }

    public void setCidadeEndList(List<Cidade> cidadeEndList) {
        this.cidadeEndList = cidadeEndList;
    }

    public List<Estado> getEstadoList() {
        return estadoList;
    }

    public void setEstadoList(List<Estado> estadoList) {
        this.estadoList = estadoList;
    }

    public PessoaFisica getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public List<PessoaFisicaJuridica> getPessoaFisicaJuridicaList() {
        return pessoaFisicaJuridicaList;
    }

    public void setPessoaFisicaJuridicaList(List<PessoaFisicaJuridica> pessoaFisicaJuridicaList) {
        this.pessoaFisicaJuridicaList = pessoaFisicaJuridicaList;
    }

    public List<Funcao> getFuncaoList() {
        return funcaoList;
    }

    public void setFuncaoList(List<Funcao> funcaoList) {
        this.funcaoList = funcaoList;
    }

    public List<PessoaFisica> getPessoaFisicaList() {
        return pessoaFisicaList;
    }

    public void setPessoaFisicaList(List<PessoaFisica> pessoaFisicaList) {
        this.pessoaFisicaList = pessoaFisicaList;
    }

    public PessoaFisicaJuridica getPessoaFisicaJuridica() {
        return pessoaFisicaJuridica;
    }

    public void setPessoaFisicaJuridica(PessoaFisicaJuridica pessoaFisicaJuridica) {
        this.pessoaFisicaJuridica = pessoaFisicaJuridica;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }
    
}
