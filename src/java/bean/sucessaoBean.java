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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.codec.binary.Base64;
import util.Base64Crypt;

/**
 *
 * @author Pedro
 */
@SessionScoped
@ManagedBean(name = "sucessaoBean")
public class sucessaoBean implements Serializable {

    private String succeed;
    private String sucedida;
    private String sucessora;

    private PessoaJuridicaBO pessoaJuridicaBO;

    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            sucedida = "";
            sucessora = "";
            succeed = "";
            pessoaJuridicaBO = new PessoaJuridicaBO();
        }
    }

    public void suceder() {
        if (sucedida != null && sucessora != null) {
            PessoaJuridica pjSucedida = pessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(Base64Crypt.decrypt(sucedida)));
            PessoaJuridica pjSucessora = pessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(Base64Crypt.decrypt(sucessora)));
            if (pjSucessora.getSucessaoFk() != null && pjSucessora.getSucessaoFk().getId().equals(pjSucedida.getId())) {
                succeed = "fail";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "A empresa escolhida já possui um vínculo de Sucessão Empresarial.", null));
            } else {
                pjSucedida.setSucessaoFk(pjSucessora);
                pessoaJuridicaBO.edit(pjSucedida);
                succeed = "success";
                sucedida = "";
                sucessora = "";
            }
        } else {
            succeed = "fail";
            String message = (sucessora != null) ? "Selecione a empresa sucedida." : (sucedida != null) ? "Selecione a empresa sucessora." : "Selecione uma empresa sucedida e sucessora.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }

    }

    public String getSucceed() {
        return succeed;
    }

    public void setSucceed(String succeed) {
        this.succeed = succeed;
    }

    public String getSucedida() {
        return sucedida;
    }

    public void setSucedida(String sucedida) {
        this.sucedida = sucedida;
    }

    public String getSucessora() {
        return sucessora;
    }

    public void setSucessora(String sucessora) {
        this.sucessora = sucessora;
    }
}
