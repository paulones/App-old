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
        PessoaJuridica pjSucedida = pessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(sucedida));
        PessoaJuridica pjSucessora = pessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(sucessora));
        if (pjSucedida.equals(pjSucessora)) {
            succeed = "fail";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione duas empresas diferentes.", null));
        } else {
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
            if (exists){
                pessoaJuridicaSucessaoBO.edit(pessoaJuridicaSucessao);
                GeradorLog.criar(pessoaJuridicaSucessao.getId(), "PJS", 'U');
            } else{
                pessoaJuridicaSucessaoBO.create(pessoaJuridicaSucessao);
                GeradorLog.criar(pessoaJuridicaSucessao.getId(), "PJS", 'C');
            }
            succeed = "success";
            sucedida = "";
            sucessora = "";
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
