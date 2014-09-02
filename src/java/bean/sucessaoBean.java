/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bo.PessoaJuridicaBO;
import bo.PessoaJuridicaSucessaoBO;
import bo.UsuarioBO;
import bo.UtilBO;
import entidade.PessoaJuridica;
import entidade.PessoaJuridicaSucessao;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.codec.binary.Base64;
import util.Base64Crypt;
import util.Cookie;

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

    private PessoaJuridicaSucessao pessoaJuridicaSucessao;

    private PessoaJuridicaBO pessoaJuridicaBO;
    private PessoaJuridicaSucessaoBO pessoaJuridicaSucessaoBO;
    private UsuarioBO usuarioBO;

    private Base64Crypt base64Crypt;

    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            sucedida = "";
            sucessora = "";
            succeed = "";
            pessoaJuridicaBO = new PessoaJuridicaBO();
            pessoaJuridicaSucessaoBO = new PessoaJuridicaSucessaoBO();
            usuarioBO = new UsuarioBO();

            pessoaJuridicaSucessao = new PessoaJuridicaSucessao();

            base64Crypt = new Base64Crypt();
        }
    }

    public void suceder() {
        if (sucedida != null && sucessora != null) {
            PessoaJuridica pjSucedida = pessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(base64Crypt.decrypt(sucedida)));
            PessoaJuridica pjSucessora = pessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(base64Crypt.decrypt(sucessora)));
            if (pjSucedida.equals(pjSucessora)) {
                succeed = "fail";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione duas empresas diferentes.", null));
            } else {
                UtilBO utilBO = new UtilBO();
                pessoaJuridicaSucessao = pessoaJuridicaSucessaoBO.findDuplicates(pjSucedida, pjSucessora);
                if (pessoaJuridicaSucessao == null) {
                    pessoaJuridicaSucessao = new PessoaJuridicaSucessao();
                    pessoaJuridicaSucessao.setUsuarioFk(usuarioBO.findUsuarioByCPF(Cookie.getCookie("usuario")));
                    pessoaJuridicaSucessao.setDataDeSucessao(utilBO.findServerTime());
                    pessoaJuridicaSucessao.setPessoaJuridicaSucedidaFk(pjSucedida);
                    pessoaJuridicaSucessao.setPessoaJuridicaSucessoraFk(pjSucessora);
                    pessoaJuridicaSucessaoBO.create(pessoaJuridicaSucessao);
                    pessoaJuridicaBO.edit(pjSucedida);
                    succeed = "success";
                    sucedida = "";
                    sucessora = "";
                } else {
                    succeed = "fail";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Já existe um vínculo entre as duas empresas selecionadas.", null));
                }
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
