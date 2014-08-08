/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import bo.TipoEmpresarialBO;
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
    
    private TipoEmpresarialBO tipoEmpresarialBO;
    
    private List<TipoEmpresarial> tipoEmpresarialList;
    
    public void init(){
        if (!FacesContext.getCurrentInstance().isPostback()) {
            pessoaJuridica = new PessoaJuridica();
            tipoEmpresarialBO = new TipoEmpresarialBO();
            tipoEmpresarialList = new ArrayList<>();
            
            loadForm();
        }
    }
    
    public void loadForm(){
        tipoEmpresarialList = tipoEmpresarialBO.findAll();
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
    
    
}
