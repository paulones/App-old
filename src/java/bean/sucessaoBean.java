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
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
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

    private List<PessoaJuridicaSucessao> pessoaJuridicaSucessaoList;

    private PessoaJuridicaBO pessoaJuridicaBO;
    private PessoaJuridicaSucessaoBO pessoaJuridicaSucessaoBO;
    private UsuarioBO usuarioBO;

    public void init() throws IOException {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            sucedida = "";
            sucessora = "";
            succeed = "";
            pessoaJuridicaBO = new PessoaJuridicaBO();
            pessoaJuridicaSucessaoBO = new PessoaJuridicaSucessaoBO();
            usuarioBO = new UsuarioBO();

            pessoaJuridicaSucessao = new PessoaJuridicaSucessao();

            pessoaJuridicaSucessaoList = new ArrayList<>();

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            if (request.getQueryString() != null) {
                try {
                    Integer id = Integer.valueOf(request.getParameter("id"));
                    if (pessoaJuridicaSucessaoBO.findSucedidasAndSucessoras(id).isEmpty()) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("sucessao.xhtml");
                    }
                } catch (Exception e) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("sucessao.xhtml");
                }

            }
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
            pessoaJuridicaSucessao.setPessoaJuridicaSucedidaFk(pjSucedida);
            pessoaJuridicaSucessao.setPessoaJuridicaSucessoraFk(pjSucessora);
            pessoaJuridicaSucessao.setStatus('A');
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
                pessoaJuridicaSucessao.setDataDeSucessao(utilBO.findServerTime());
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
            if (sucedida != null && sucessora != null) {
                PessoaJuridica pjSucedida = pessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(sucedida));
                PessoaJuridica pjSucessora = pessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(sucessora));
                pessoaJuridicaSucessao = pessoaJuridicaSucessaoBO.findDuplicates(pjSucedida, pjSucessora);
                if (pessoaJuridicaSucessao != null) {
                    if (pessoaJuridicaSucessao.getStatus().equals('I')) {
                        succeed = "warning";
                        if (pessoaJuridicaSucessao.getPessoaJuridicaSucedidaFk().equals(pjSucessora) && pessoaJuridicaSucessao.getPessoaJuridicaSucessoraFk().equals(pjSucedida)) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Existe uma sucessão invertida entre as empresas selecionadas, porém desativada. Caso opte em suceder, a sucessão anterior será substituída e reativada.", null));
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Esta sucessão foi desativada. Caso opte em suceder, a mesma será reativada.", null));
                        }
                    } else if (pessoaJuridicaSucessao.getPessoaJuridicaSucedidaFk().equals(pjSucessora) && pessoaJuridicaSucessao.getPessoaJuridicaSucessoraFk().equals(pjSucedida)) {
                        succeed = "warning";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Existe uma sucessão invertida entre as empresas selecionadas. Caso opte em suceder, a sucessão anterior será substituída.", null));
                    } else {
                        System.out.println("ativo e nada a ver");
                        succeed = "";
                    }
                } else {
                    succeed = "";
                }
            } else {
                succeed = "";
            }
        } else {
            succeed = "warning";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Escolha duas empresas diferentes.", null));
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
