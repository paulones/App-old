/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import bo.PessoaJuridicaBO;
import entidade.PessoaJuridica;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Pedro
 */
@SessionScoped
@ManagedBean(name = "sucessaoBean")
public class sucessaoBean implements Serializable{
    
    private String succeed;
    private PessoaJuridica sucedida;
    private PessoaJuridica sucessora;
    
    private List<PessoaJuridica> pessoaJuridicaList;
    
    private PessoaJuridicaBO pessoaJuridicaBO;
    
    public void init(){
        succeed = "";
        pessoaJuridicaBO = new PessoaJuridicaBO();
        pessoaJuridicaList = pessoaJuridicaBO.findAll();
    }
    
    public void cadastrarSucedida(){
        
    }
    
    public void cadastrarSucessora(){
        
    }
    
    public void suceder(){
        
    }

    public String getSucceed() {
        return succeed;
    }

    public void setSucceed(String succeed) {
        this.succeed = succeed;
    }

    public PessoaJuridica getSucedida() {
        return sucedida;
    }

    public void setSucedida(PessoaJuridica sucedida) {
        this.sucedida = sucedida;
    }

    public PessoaJuridica getSucessora() {
        return sucessora;
    }

    public void setSucessora(PessoaJuridica sucessora) {
        this.sucessora = sucessora;
    }

    public List<PessoaJuridica> getPessoaJuridicaList() {
        return pessoaJuridicaList;
    }

    public void setPessoaJuridicaList(List<PessoaJuridica> pessoaJuridicaList) {
        this.pessoaJuridicaList = pessoaJuridicaList;
    }
    
}
