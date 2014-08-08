/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import bo.CidadeBO;
import bo.EstadoBO;
import bo.TipoEmpresarialBO;
import entidade.Cidade;
import entidade.Endereco;
import entidade.Estado;
import entidade.PessoaJuridica;
import entidade.TipoEmpresarial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Pedro
 */
@SessionScoped
@ManagedBean(name = "pessoaJuridicaBean")
public class PessoaJuridicaBean implements Serializable{
    
    private PessoaJuridica pessoaJuridica;
    private Endereco endereco;
    
    private TipoEmpresarialBO tipoEmpresarialBO;
    private EstadoBO estadoBO;
    private CidadeBO cidadeBO;
    
    private List<TipoEmpresarial> tipoEmpresarialList;
    private List<Estado> estadoList;
    private List<Cidade> cidadeEndList;
    
    public void init(){
        if (!FacesContext.getCurrentInstance().isPostback()) {
            pessoaJuridica = new PessoaJuridica();
            
            tipoEmpresarialBO = new TipoEmpresarialBO();
            estadoBO = new EstadoBO();
            cidadeBO = new CidadeBO();
            
            tipoEmpresarialList = new ArrayList<>();
            cidadeEndList = new ArrayList<>();
            
            loadForm();
        }
    }
    
    public void loadForm(){
        estadoList = estadoBO.findAll();
        tipoEmpresarialList = tipoEmpresarialBO.findAll();
    }
    
    public void getCitiesByState() {
        if (endereco.getEstadoFk() != null) {
            cidadeEndList = cidadeBO.getByStateId(endereco.getEstadoFk().getId());
        } else {
            cidadeEndList.clear();
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
    
    
}
