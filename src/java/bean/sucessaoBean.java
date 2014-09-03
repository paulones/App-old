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
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import util.Cookie;
import util.GeradorLog;

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

    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            sucedida = "";
            sucessora = "";
            succeed = "";
            pessoaJuridicaBO = new PessoaJuridicaBO();
            pessoaJuridicaSucessaoBO = new PessoaJuridicaSucessaoBO();
            usuarioBO = new UsuarioBO();

            pessoaJuridicaSucessao = new PessoaJuridicaSucessao();

        }
    }

    public void suceder() {
        if (!sucedida.equals(sucessora)) {
            PessoaJuridica pjSucedida = pessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(sucedida));
            PessoaJuridica pjSucessora = pessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(sucessora));
            UtilBO utilBO = new UtilBO();
            boolean exists = true;
            pessoaJuridicaSucessao = pessoaJuridicaSucessaoBO.findDuplicates(pjSucedida, pjSucessora);
            if (pessoaJuridicaSucessao == null) {
                pessoaJuridicaSucessao = new PessoaJuridicaSucessao();
                exists = false;
            }
            pessoaJuridicaSucessao.setUsuarioFk(usuarioBO.findUsuarioByCPF(Cookie.getCookie("usuario")));
            pessoaJuridicaSucessao.setDataDeSucessao(utilBO.findServerTime());
            pessoaJuridicaSucessao.setPessoaJuridicaSucedidaFk(pjSucedida);
            pessoaJuridicaSucessao.setPessoaJuridicaSucessoraFk(pjSucessora);
            if (exists) {
                if (!pessoaJuridicaSucessao.equalsValues(pessoaJuridicaSucessaoBO.findDuplicates(pjSucedida, pjSucessora))) {
                    pessoaJuridicaSucessaoBO.edit(pessoaJuridicaSucessao);
                    GeradorLog.criar(pessoaJuridicaSucessao.getId(), "PJS", 'U');
                    succeed = "success";
                    sucedida = "";
                    sucessora = "";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sucessão alterada com sucesso!", null));
                } else {
                    succeed = "info";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nenhum campo foi alterado.", null));
                }
            } else {
                pessoaJuridicaSucessaoBO.create(pessoaJuridicaSucessao);
                GeradorLog.criar(pessoaJuridicaSucessao.getId(), "PJS", 'C');
                succeed = "success";
                sucedida = "";
                sucessora = "";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sucessão realizada com sucesso!", null));
            }
        } else {
            succeed = "fail";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione duas empresas diferentes.", null));
        }
    }

    public void checkSucessoes() {
        if (!sucedida.equals(sucessora)) {
            PessoaJuridica pjSucedida = pessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(sucedida));
            PessoaJuridica pjSucessora = pessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(sucessora));
            pessoaJuridicaSucessao = pessoaJuridicaSucessaoBO.findDuplicates(pjSucedida, pjSucessora);
            if (pessoaJuridicaSucessao.getPessoaJuridicaSucedidaFk().equals(pjSucessora) && pessoaJuridicaSucessao.getPessoaJuridicaSucessoraFk().equals(pjSucedida)) {
                succeed = "warning";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Existe uma sucessão invertida entre as empresas selecionadas. Caso opte em suceder, a sucessão anterior será substituída.", null));
            } else {
                succeed = "";
            }
        } else {
            succeed = "";
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
