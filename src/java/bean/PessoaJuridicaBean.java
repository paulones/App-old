/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bo.BemBO;
import bo.BemHistoricoBO;
import bo.CidadeBO;
import bo.EnderecoBO;
import bo.EnderecoHistoricoBO;
import bo.EstadoBO;
import bo.FuncaoBO;
import bo.PessoaFisicaBO;
import bo.PessoaFisicaJuridicaBO;
import bo.PessoaFisicaJuridicaHistoricoBO;
import bo.PessoaJuridicaBO;
import bo.PessoaJuridicaHistoricoBO;
import bo.PessoaJuridicaJuridicaBO;
import bo.PessoaJuridicaJuridicaHistoricoBO;
import bo.PessoaJuridicaSucessaoBO;
import bo.PessoaJuridicaSucessaoHistoricoBO;
import bo.ProcessoJudicialBO;
import bo.TipoBemBO;
import bo.TipoEmpresarialBO;
import bo.UsuarioBO;
import bo.UtilBO;
import entidade.Bem;
import entidade.BemHistorico;
import entidade.Cidade;
import entidade.Endereco;
import entidade.EnderecoHistorico;
import entidade.EnderecoPessoa;
import entidade.EnderecoPessoaFisicaJuridicaHistorico;
import entidade.Estado;
import entidade.Funcao;
import entidade.Instituicao;
import entidade.PessoaFisica;
import entidade.PessoaFisicaJuridica;
import entidade.PessoaFisicaJuridicaHistorico;
import entidade.PessoaJuridica;
import entidade.PessoaJuridicaHistorico;
import entidade.PessoaJuridicaJuridica;
import entidade.PessoaJuridicaJuridicaHistorico;
import entidade.PessoaJuridicaSucessao;
import entidade.PessoaJuridicaSucessaoHistorico;
import entidade.ProcessoJudicial;
import entidade.TipoBem;
import entidade.TipoEmpresarial;
import entidade.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import util.Cookie;
import util.GeradorLog;
import util.MetodosConvencionais;

/**
 *
 * @author Pedro
 */
@ViewScoped
@ManagedBean(name = "pessoaJuridicaBean")
public class PessoaJuridicaBean implements Serializable {

    private PessoaJuridica pessoaJuridica;
    private PessoaJuridica oldPessoaJuridica;
    private Endereco endereco;
    private Endereco oldEndereco;
    private PessoaFisica pessoaFisicaVinculo;
    private PessoaJuridica pessoaJuridicaVinculo;
    private EnderecoPessoa enderecoPessoa;
    private PessoaJuridicaHistorico pessoaJuridicaHistorico;
    private EnderecoHistorico EnderecoHistorico;
    private PessoaJuridicaSucessao pessoaJuridicaSucessao;
    private Bem bem;

    private String register;
    private String redirect;
    private boolean edit;
    private String pjId;
    private String pjsId;
    private String pfVId;
    private String pjVId;

    private List<TipoEmpresarial> tipoEmpresarialList;
    private List<Estado> estadoList;
    private List<Cidade> cidadeEndList;
    private List<Funcao> funcaoList;
    private List<Bem> bemList;
    private List<Bem> oldBemList;
    private List<PessoaFisicaJuridica> pessoaFisicaJuridicaList;
    private List<PessoaFisicaJuridica> oldPessoaFisicaJuridicaList;
    private List<PessoaJuridicaJuridica> pessoaJuridicaJuridicaList;
    private List<PessoaJuridicaJuridica> oldPessoaJuridicaJuridicaList;
    private List<EnderecoPessoa> enderecoPessoaList;
    private List<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoList;
    private List<PessoaJuridicaJuridicaHistorico> pessoaJuridicaJuridicaHistoricoList;
    private List<EnderecoPessoaFisicaJuridicaHistorico> enderecoPessoaFisicaJuridicaHistoricoList;
    private List<PessoaJuridicaHistorico> pessoaJuridicaHistoricoList;
    private List<EnderecoHistorico> enderecoHistoricoList;
    private List<PessoaJuridicaSucessaoHistorico> pessoaJuridicaSucessaoHistoricoList;
    private List<BemHistorico> bemHistoricoList;
    private List<ProcessoJudicial> processoJudicialList;
    private List<TipoBem> tipoBemList;

    public void init() throws IOException {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            boolean isRegisterPage = FacesContext.getCurrentInstance().getViewRoot().getViewId().lastIndexOf("cadastrar") > -1;
            boolean isSearchPage = FacesContext.getCurrentInstance().getViewRoot().getViewId().lastIndexOf("consultar") > -1;

            endereco = new Endereco();
            pessoaFisicaVinculo = new PessoaFisica();
            enderecoPessoa = new EnderecoPessoa();
            bem = new Bem();

            register = "";
            redirect = Cookie.getCookie("FacesMessage");
            Cookie.apagarCookie("FacesMessage");

            tipoEmpresarialList = new ArrayList<>();
            cidadeEndList = new ArrayList<>();
            pessoaFisicaJuridicaList = new ArrayList<>();
            pessoaJuridicaJuridicaList = new ArrayList<>();
            bemList = new ArrayList<>();

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            pessoaJuridica = new PessoaJuridica();
            if (isRegisterPage) {
                /*
                 Tela cadastro.xhtml. Se houver "id" na url, entra na condição de alteração.
                 Caso contrário, apenas carrega o formulário
                 */
                pfVId = "";
                pjVId = "";
                if (request.getParameter("id") == null) { //Novo
                    edit = false;
                    carregarFormulario();
                } else {
                    try {
                        Integer id = Integer.valueOf(request.getParameter("id"));
                        pessoaJuridica = PessoaJuridicaBO.findPessoaJuridica(id);
                        if (pessoaJuridica == null) {
                            FacesContext.getCurrentInstance().getExternalContext().redirect("cadastrar.xhtml");
                        } else {
                            edit = true;
                            endereco = EnderecoBO.findPJAddress(id);
                            pessoaFisicaJuridicaList = PessoaFisicaJuridicaBO.findAllByPJ(id);
                            pessoaJuridicaJuridicaList = PessoaJuridicaJuridicaBO.findAllByPJAOrPJB(id);
                            bemList = BemBO.findPJBens(id);

                            oldPessoaJuridica = PessoaJuridicaBO.findPessoaJuridica(id);
                            oldEndereco = EnderecoBO.findPJAddress(id);
                            oldBemList = BemBO.findPJBens(id);
                            oldPessoaFisicaJuridicaList = PessoaFisicaJuridicaBO.findAllByPJ(id);
                            oldPessoaJuridicaJuridicaList = PessoaJuridicaJuridicaBO.findAllByPJAOrPJB(id);
                            prepararHistorico(pessoaJuridica, endereco, pessoaFisicaJuridicaList, pessoaJuridicaJuridicaList, bemList);

                            carregarFormulario();
                            getCidadesPeloEstado();
                        }
                    } catch (Exception e) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("cadastrar.xhtml");
                    }
                }
            } else if (isSearchPage) {
                pjId = "";
            }
        }
    }

    public void carregarHistorico(String idStr) {
        Integer id = Integer.valueOf(idStr);
        pessoaJuridica = PessoaJuridicaBO.findPessoaJuridica(id);
        endereco = EnderecoBO.findPJAddress(id);
        bemList = BemBO.findPJBens(id);
        pessoaFisicaJuridicaList = PessoaFisicaJuridicaBO.findAllByPJ(id);
        pessoaJuridicaJuridicaList = PessoaJuridicaJuridicaBO.findAllByPJAOrPJB(id);

        pessoaJuridicaHistoricoList = new ArrayList<>();
        enderecoHistoricoList = new ArrayList<>();
        bemHistoricoList = new ArrayList<>();
        pessoaFisicaJuridicaHistoricoList = new ArrayList<>();
        pessoaJuridicaJuridicaHistoricoList = new ArrayList<>();

        pessoaJuridicaHistoricoList = PessoaJuridicaHistoricoBO.findAllByPJ(id);
        enderecoHistoricoList = EnderecoHistoricoBO.findAllByPJ(id);
        bemHistoricoList = BemHistoricoBO.findAllByPJ(id);
        pessoaFisicaJuridicaHistoricoList = PessoaFisicaJuridicaHistoricoBO.findAllByPJ(id);

        enderecoPessoaFisicaJuridicaHistoricoList = new ArrayList<>();
        EnderecoPessoaFisicaJuridicaHistorico enderecoPessoaFisicaJuridicaHistorico = new EnderecoPessoaFisicaJuridicaHistorico();
        enderecoPessoaFisicaJuridicaHistorico = prepararRegistroAtual(pessoaJuridica, endereco, pessoaFisicaJuridicaList, pessoaJuridicaJuridicaList, bemList);
        enderecoPessoaFisicaJuridicaHistoricoList.add(enderecoPessoaFisicaJuridicaHistorico);

        for (PessoaJuridicaHistorico pjh : pessoaJuridicaHistoricoList) {
            for (EnderecoHistorico eh : enderecoHistoricoList) {
                if (pjh.getId() == eh.getIdFk()) {
                    EnderecoPessoaFisicaJuridicaHistorico epfjh = new EnderecoPessoaFisicaJuridicaHistorico(pjh, eh);
                    List<PessoaFisicaJuridicaHistorico> pfjhList = new ArrayList<>();
                    for (PessoaFisicaJuridicaHistorico pfjh : pessoaFisicaJuridicaHistoricoList) {
                        if (pjh.getId() == pfjh.getIdFk()) {
                            pfjhList.add(pfjh);
                        }
                    }
                    List<BemHistorico> bhList = new ArrayList<>();
                    for (BemHistorico bh : bemHistoricoList) {
                        if (pjh.getId() == bh.getIdFk()) {
                            bhList.add(bh);
                        }
                    }
                    epfjh.setBemHistoricoList(bhList);
                    epfjh.setPessoaFisicaJuridicaHistoricoList(pfjhList);
                    enderecoPessoaFisicaJuridicaHistoricoList.add(epfjh);
                    break;
                }
            }
        }
    }

    

    public void carregarFormulario() {
        estadoList = EstadoBO.findAll();
        tipoEmpresarialList = TipoEmpresarialBO.findAll();
        funcaoList = FuncaoBO.findAll();
        tipoBemList = TipoBemBO.findAll();
    }

    public void cadastrar() throws IOException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        UsuarioBO usuarioBO = new UsuarioBO();
        boolean error = false;
        PessoaJuridica pjDB = PessoaJuridicaBO.findDuplicates(pessoaJuridica);
        if (!edit) {
            /*  
             Cadastrar nova Pessoa Jurídica
             */
            if (pjDB == null || pessoaJuridica.getCnpj().isEmpty()) { //CNPJ novo
                pessoaJuridica.setStatus('A');
                pessoaJuridica.setUsuarioFk(usuarioBO.findUsuarioByCPF(Cookie.getCookie("usuario")));
                pessoaJuridica.setInstituicaoFk(usuarioBO.findAutorizacaoByCPF(Cookie.getCookie("usuario")).getInstituicaoFk());
                PessoaJuridicaBO.create(pessoaJuridica);
                endereco.setTipo("PJ");
                endereco.setIdFk(pessoaJuridica.getId());
                EnderecoBO.create(endereco);
                for (PessoaFisicaJuridica pfj : pessoaFisicaJuridicaList) {
                    pfj.setPessoaJuridicaFk(pessoaJuridica);
                    PessoaFisicaJuridicaBO.create(pfj);
                }
                for (PessoaJuridicaJuridica pjj : pessoaJuridicaJuridicaList) {
                    pjj.setPessoaJuridicaPrimariaFk(pessoaJuridica);
                    PessoaJuridicaJuridicaBO.create(pjj);
                }
                for (Bem bem : bemList) {
                    bem.setTipo("PJ");
                    bem.setIdFk(pessoaJuridica.getId());
                    BemBO.create(bem);
                }
                register = "success";
                pfVId = "";
                pjVId = "";
                GeradorLog.criar(pessoaJuridica.getId(), "PJ", 'C');
                pessoaJuridica = new PessoaJuridica();
                endereco = new Endereco();
                pessoaFisicaJuridicaList = new ArrayList<>();
                pessoaJuridicaJuridicaList = new ArrayList<>();
                bemList = new ArrayList<>();
            } else { //CNPJ previamente cadastrado
                error = true;
            }
        } else {
            /*  
             Alterar Pessoa Jurídica existente
             */
            if (pjDB == null || pessoaJuridica.equals(pjDB)) {

                boolean identicalpfj = MetodosConvencionais.checarListasIguais("PessoaFisicaJuridica", pessoaFisicaJuridicaList, oldPessoaFisicaJuridicaList);
                boolean identicalpjj = MetodosConvencionais.checarListasIguais("PessoaJuridicaJuridica", pessoaJuridicaJuridicaList, oldPessoaJuridicaJuridicaList);
                boolean identicalBem = MetodosConvencionais.checarListasIguais("Bem", bemList, oldBemList);
                if (oldPessoaJuridica.changedValues(pessoaJuridica).isEmpty()
                        && oldEndereco.changedValues(endereco).isEmpty()
                        && identicalpfj && identicalpjj && identicalBem) {
                    Cookie.addCookie("FacesMessage", "fail", 10);
                    FacesContext.getCurrentInstance().getExternalContext().redirect("consultar.xhtml");
                } else {
                    UtilBO utilBO = new UtilBO();
                    Timestamp timestamp = utilBO.findServerTime();
                    pessoaJuridica.setUsuarioFk(usuarioBO.findUsuarioByCPF(Cookie.getCookie("usuario")));
                    PessoaJuridicaBO.edit(pessoaJuridica);
                    pessoaJuridicaHistorico.setDataDeModificacao(timestamp);
                    PessoaJuridicaHistoricoBO.create(pessoaJuridicaHistorico);
                    EnderecoBO.edit(endereco);
                    EnderecoHistorico.setIdFk(pessoaJuridicaHistorico.getId());
                    EnderecoHistoricoBO.create(EnderecoHistorico);
                    PessoaFisicaJuridicaBO.destroyByPJ(pessoaJuridica.getId());
                    for (PessoaFisicaJuridica pfj : pessoaFisicaJuridicaList) {
                        PessoaFisicaJuridicaBO.create(pfj);
                    }
                    for (PessoaFisicaJuridicaHistorico pfjh : pessoaFisicaJuridicaHistoricoList) {
                        pfjh.setTipo("PJ");
                        pfjh.setIdFk(pessoaJuridicaHistorico.getId());
                        PessoaFisicaJuridicaHistoricoBO.create(pfjh);
                    }
                    PessoaJuridicaJuridicaBO.destroyByPJBOrPJA(pessoaJuridica.getId());
                    for (PessoaJuridicaJuridica pjj : pessoaJuridicaJuridicaList) {
                        PessoaJuridicaJuridicaBO.create(pjj);
                    }
                    for (PessoaJuridicaJuridicaHistorico pjjh : pessoaJuridicaJuridicaHistoricoList) {
                        pjjh.setPessoaJuridicaHistoricoFk(pessoaJuridicaHistorico);
                        PessoaJuridicaJuridicaHistoricoBO.create(pjjh);
                    }
                    for (Bem b : bemList) {
                        if (b.getId() == null) {
                            b.setTipo("PJ");
                            b.setIdFk(pessoaJuridica.getId());
                            BemBO.create(b);
                        } else {
                            BemBO.edit(b);
                        }
                    }
                    for (BemHistorico bh : bemHistoricoList) {
                        bh.setIdFk(pessoaJuridicaHistorico.getId());
                        BemHistoricoBO.create(bh);
                    }
                    GeradorLog.criar(pessoaJuridica.getId(), "PJ", 'U');
                    Cookie.addCookie("FacesMessage", "success", 10);
                    FacesContext.getCurrentInstance().getExternalContext().redirect("consultar.xhtml");
                }
            } else { // CNPJ previamente cadastrado
                error = true;
            }
        }
        if (error) {
            register = "fail";
            String cnpj = pjDB.getCnpj().substring(0, 2) + "." + pjDB.getCnpj().substring(2, 5) + "." + pjDB.getCnpj().substring(5, 8) + "/" + pjDB.getCnpj().substring(8, 12) + "-" + pjDB.getCnpj().substring(12);
            String message = "Já existe uma empresa cadastrada com o CNPJ " + cnpj;
            message += pjDB.getNome() != null ? "\nNome: " + pjDB.getNome() : "";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }
    }

    public void getCidadesPeloEstado() { // Renderizar cidades baseado no estado escolhido
        if (endereco.getEstadoFk() != null) {
            cidadeEndList = CidadeBO.getByStateId(endereco.getEstadoFk().getId());
        } else {
            cidadeEndList.clear();
        }
    }

    public void vincularPessoaFisica() {
        PessoaFisicaJuridica pessoaFisicaJuridica = new PessoaFisicaJuridica();
        if (edit) {
            pessoaFisicaJuridica.setPessoaJuridicaFk(pessoaJuridica);
        }
        pessoaFisicaVinculo = PessoaFisicaBO.findPessoaFisica(Integer.valueOf(pfVId));
        pessoaFisicaJuridica.setPessoaFisicaFk(pessoaFisicaVinculo);
        boolean exists = false;
        for (PessoaFisicaJuridica pfj : pessoaFisicaJuridicaList) {
            if (pfj.getPessoaFisicaFk().getId().equals(pessoaFisicaVinculo.getId())) {
                exists = true;
            }
        }
        if (!exists) {
            pessoaFisicaJuridicaList.add(pessoaFisicaJuridica);
            pessoaFisicaJuridica = new PessoaFisicaJuridica();
        }
    }

    public void vincularPessoaJuridica() {
        PessoaJuridicaJuridica pessoaJuridicaJuridica = new PessoaJuridicaJuridica();
        boolean exists = false;
        if (edit) {
            pessoaJuridicaJuridica.setPessoaJuridicaPrimariaFk(pessoaJuridica);
        }
        pessoaJuridicaVinculo = PessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(pjVId));
        pessoaJuridicaJuridica.setPessoaJuridicaSecundariaFk(pessoaJuridicaVinculo);
        for (PessoaJuridicaJuridica pjj : pessoaJuridicaJuridicaList) {
            if ((pjj.getPessoaJuridicaSecundariaFk() != null && pjj.getPessoaJuridicaSecundariaFk().getId().equals(pessoaJuridicaVinculo.getId()))) {
                exists = true;
            }
        }
        if (!exists) {
            pessoaJuridicaJuridicaList.add(pessoaJuridicaJuridica);
            pessoaJuridicaJuridica = new PessoaJuridicaJuridica();
        }
    }

    public void removerVinculoFisico(int index) {
        pessoaFisicaJuridicaList.remove(index);
    }

    public void removerVinculoJuridico(int index) {
        pessoaJuridicaJuridicaList.remove(index);
    }

    public void adicionarBem() {
        Boolean add = false;
        if (!bemList.isEmpty()) {
            for (Bem b : bemList) {
                if (!bem.equalsValues(b)) {
                    add = true;
                }
            }
        } else {
            add = true;
        }
        if (add) {
            bemList.add(bem);
            bem.setStatus('A');
            bem = new Bem();
        }
    }

    public void removerBem(int index) {
        if (edit) {
            bemList.get(index).setStatus('I');
        } else {
            bemList.remove(index);
        }
    }

    public void removerSucessao() {
        try {
            PessoaJuridicaSucessaoBO pessoaJuridicaSucessaoBO = new PessoaJuridicaSucessaoBO();
            PessoaJuridicaSucessao pjs = pessoaJuridicaSucessaoBO.findPessoaJuridicaSucessao(Integer.valueOf(pjsId));
            pjs.setStatus('I');
            pessoaJuridicaSucessaoBO.edit(pjs);
            GeradorLog.criar(pjs.getId(), "PJS", 'D');
            redirect = "";
            register = "success";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sucessão removida com sucesso!", null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removerPessoaJuridica() {
        pessoaJuridica = PessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(pjId));
        endereco = EnderecoBO.findPJAddress(pessoaJuridica.getId());
        pessoaJuridica.setStatus('I');
        PessoaJuridicaBO.edit(pessoaJuridica);
        GeradorLog.criar(pessoaJuridica.getId(), "PJ", 'D');
        redirect = "";
        register = "success";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cadastro removido com sucesso!", null));
    }

    public void exibirInfo() {
        pessoaJuridica = PessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(pjId));
        endereco = EnderecoBO.findPJAddress(pessoaJuridica.getId());
        bemList = BemBO.findPJBens(pessoaJuridica.getId());
        enderecoPessoa = new EnderecoPessoa(pessoaJuridica, endereco, bemList);
        processoJudicialList = ProcessoJudicialBO.findByExecutado(pjId, "PJ");
    }

    public void exibirHistoricoDaSucessao() {
        PessoaJuridicaSucessaoHistorico pessoaJuridicaSucessaoHistorico = new PessoaJuridicaSucessaoHistorico();
        pessoaJuridicaSucessaoHistoricoList = new ArrayList<>();

        pessoaJuridicaSucessao = PessoaJuridicaSucessaoBO.findPessoaJuridicaSucessao(Integer.valueOf(pjsId));
        pessoaJuridicaSucessaoHistorico.setDataDeSucessao(pessoaJuridicaSucessao.getDataDeSucessao());
        pessoaJuridicaSucessaoHistorico.setPessoaJuridicaSucedidaFk(pessoaJuridicaSucessao.getPessoaJuridicaSucedidaFk());
        pessoaJuridicaSucessaoHistorico.setPessoaJuridicaSucessoraFk(pessoaJuridicaSucessao.getPessoaJuridicaSucessoraFk());
        pessoaJuridicaSucessaoHistorico.setUsuarioFk(pessoaJuridicaSucessao.getUsuarioFk());

        pessoaJuridicaSucessaoHistoricoList.add(pessoaJuridicaSucessaoHistorico);
        pessoaJuridicaSucessaoHistoricoList.addAll(PessoaJuridicaSucessaoHistoricoBO.findByPJS(Integer.valueOf(pjsId)));

    }

    public void prepararHistorico(PessoaJuridica pessoaJuridica, Endereco endereco, List<PessoaFisicaJuridica> pessoaFisicaJuridicaList, List<PessoaJuridicaJuridica> pessoaJuridicaJuridicaList, List<Bem> bemList) {
        /*
         Montar entidades dos históricos de alteração 
         */
        Usuario usuario = UsuarioBO.findUsuarioByCPF(Cookie.getCookie("usuario"));
        pessoaJuridicaHistorico = new PessoaJuridicaHistorico();
        EnderecoHistorico = new EnderecoHistorico();
        bemHistoricoList = new ArrayList<>();
        pessoaFisicaJuridicaHistoricoList = new ArrayList<>();
        pessoaJuridicaJuridicaHistoricoList = new ArrayList<>();

        pessoaJuridicaHistorico.setNome(pessoaJuridica.getNome());
        pessoaJuridicaHistorico.setCnpj(pessoaJuridica.getCnpj());
        pessoaJuridicaHistorico.setNomeFantasia(pessoaJuridica.getNomeFantasia());
        pessoaJuridicaHistorico.setTipoEmpresarialFk(pessoaJuridica.getTipoEmpresarialFk());
        pessoaJuridicaHistorico.setInscricaoEstadual(pessoaJuridica.getInscricaoEstadual());
        pessoaJuridicaHistorico.setInscricaoMunicipal(pessoaJuridica.getInscricaoMunicipal());
        pessoaJuridicaHistorico.setSituacao(pessoaJuridica.getSituacao());
        pessoaJuridicaHistorico.setMotivoDaDesativacao(pessoaJuridica.getMotivoDaDesativacao());
        pessoaJuridicaHistorico.setDataDeCriacao(pessoaJuridica.getDataDeCriacao());
        pessoaJuridicaHistorico.setGrupoEconomico(pessoaJuridica.getGrupoEconomico());
        pessoaJuridicaHistorico.setCnae(pessoaJuridica.getCnae());
        pessoaJuridicaHistorico.setNire(pessoaJuridica.getNire());
        pessoaJuridicaHistorico.setAtividadePrincipal(pessoaJuridica.getAtividadePrincipal());
        pessoaJuridicaHistorico.setAtividadeSecundaria(pessoaJuridica.getAtividadeSecundaria());
        pessoaJuridicaHistorico.setPessoaJuridicaFk(pessoaJuridica);
        pessoaJuridicaHistorico.setUsuarioFk(usuario);

        EnderecoHistorico.setBairro(endereco.getBairro());
        EnderecoHistorico.setCep(endereco.getCep());
        EnderecoHistorico.setCidadeFk(endereco.getCidadeFk());
        EnderecoHistorico.setComplemento(endereco.getComplemento());
        EnderecoHistorico.setEndereco(endereco.getEndereco());
        EnderecoHistorico.setEstadoFk(endereco.getEstadoFk());
        EnderecoHistorico.setNumero(endereco.getNumero());
        EnderecoHistorico.setTipo(endereco.getTipo());

        for (PessoaFisicaJuridica pfj : pessoaFisicaJuridicaList) {
            PessoaFisicaJuridicaHistorico pfjh = new PessoaFisicaJuridicaHistorico();
            pfjh.setCapitalDeParticipacao(pfj.getCapitalDeParticipacao());
            pfjh.setDataDeInicio(pfj.getDataDeInicio());
            pfjh.setDataDeTermino(pfj.getDataDeTermino());
            pfjh.setFuncaoFk(pfj.getFuncaoFk());
            pfjh.setPessoaFisicaFk(pfj.getPessoaFisicaFk());
            pfjh.setPessoaJuridicaFk(pfj.getPessoaJuridicaFk());
            pessoaFisicaJuridicaHistoricoList.add(pfjh);
        }
        for (PessoaJuridicaJuridica pjj : pessoaJuridicaJuridicaList) {
            PessoaJuridicaJuridicaHistorico pjjh = new PessoaJuridicaJuridicaHistorico();
            pjjh.setCapitalDeParticipacao(pjj.getCapitalDeParticipacao());
            pjjh.setDataDeInicio(pjj.getDataDeInicio());
            pjjh.setDataDeTermino(pjj.getDataDeTermino());
            pjjh.setPessoaJuridicaSecundariaFk(pjj.getPessoaJuridicaSecundariaFk());
            pjjh.setPessoaJuridicaPrimariaFk(pjj.getPessoaJuridicaPrimariaFk());
            pessoaJuridicaJuridicaHistoricoList.add(pjjh);
        }
        for (Bem bem : bemList) {
            BemHistorico bh = new BemHistorico();
            bh.setDataDeAquisicao(bem.getDataDeAquisicao());
            bh.setDataDeTransferenciaOuExtincao(bem.getDataDeTransferenciaOuExtincao());
            bh.setDescricao(bem.getDescricao());
            bh.setEndereco(bem.getEndereco());
            bh.setValor(bem.getValor());
            bh.setTipoBemFk(bem.getTipoBemFk());
            bh.setTipo(bem.getTipo());
            bemHistoricoList.add(bh);
        }
    }

    private EnderecoPessoaFisicaJuridicaHistorico prepararRegistroAtual(PessoaJuridica pessoaJuridica, Endereco endereco, List<PessoaFisicaJuridica> pessoaFisicaJuridicaList, List<PessoaJuridicaJuridica> pessoaJuridicaJuridicaList, List<Bem> bemList) {
        /*
         Montar registro atual como uma entidade de histórico para facilitar o ui:repeat do form
         */
        EnderecoPessoaFisicaJuridicaHistorico enderecoPessoaFisicaJuridicaHistorico = new EnderecoPessoaFisicaJuridicaHistorico();
        PessoaJuridicaHistorico pessoaJuridicaHistorico = new PessoaJuridicaHistorico();
        EnderecoHistorico enderecoHistorico = new EnderecoHistorico();
        List<BemHistorico> bemHistoricoList = new ArrayList<>();
        List<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoList = new ArrayList<>();
        List<PessoaJuridicaJuridicaHistorico> pessoaJuridicaJuridicaHistoricoList = new ArrayList<>();

        pessoaJuridicaHistorico.setNome(pessoaJuridica.getNome());
        pessoaJuridicaHistorico.setCnpj(pessoaJuridica.getCnpj());
        pessoaJuridicaHistorico.setNomeFantasia(pessoaJuridica.getNomeFantasia());
        pessoaJuridicaHistorico.setTipoEmpresarialFk(pessoaJuridica.getTipoEmpresarialFk());
        pessoaJuridicaHistorico.setInscricaoEstadual(pessoaJuridica.getInscricaoEstadual());
        pessoaJuridicaHistorico.setInscricaoMunicipal(pessoaJuridica.getInscricaoMunicipal());
        pessoaJuridicaHistorico.setSituacao(pessoaJuridica.getSituacao());
        pessoaJuridicaHistorico.setMotivoDaDesativacao(pessoaJuridica.getMotivoDaDesativacao());
        pessoaJuridicaHistorico.setDataDeCriacao(pessoaJuridica.getDataDeCriacao());
        pessoaJuridicaHistorico.setGrupoEconomico(pessoaJuridica.getGrupoEconomico());
        pessoaJuridicaHistorico.setCnae(pessoaJuridica.getCnae());
        pessoaJuridicaHistorico.setNire(pessoaJuridica.getNire());
        pessoaJuridicaHistorico.setAtividadePrincipal(pessoaJuridica.getAtividadePrincipal());
        pessoaJuridicaHistorico.setAtividadeSecundaria(pessoaJuridica.getAtividadeSecundaria());
        pessoaJuridicaHistorico.setUsuarioFk(pessoaJuridica.getUsuarioFk());
        pessoaJuridicaHistorico.setPessoaJuridicaFk(pessoaJuridica);

        enderecoHistorico.setBairro(endereco.getBairro());
        enderecoHistorico.setCep(endereco.getCep());
        enderecoHistorico.setComplemento(endereco.getComplemento());
        enderecoHistorico.setEndereco(endereco.getEndereco());
        enderecoHistorico.setEstadoFk(endereco.getEstadoFk());
        enderecoHistorico.setCidadeFk(endereco.getCidadeFk());
        enderecoHistorico.setNumero(endereco.getNumero());

        for (PessoaFisicaJuridica pfj : pessoaFisicaJuridicaList) {
            PessoaFisicaJuridicaHistorico pfjh = new PessoaFisicaJuridicaHistorico();
            pfjh.setCapitalDeParticipacao(pfj.getCapitalDeParticipacao());
            pfjh.setDataDeInicio(pfj.getDataDeInicio());
            pfjh.setDataDeTermino(pfj.getDataDeTermino());
            pfjh.setFuncaoFk(pfj.getFuncaoFk());
            pfjh.setPessoaFisicaFk(pfj.getPessoaFisicaFk());
            pfjh.setPessoaJuridicaFk(pfj.getPessoaJuridicaFk());
            pessoaFisicaJuridicaHistoricoList.add(pfjh);
        }

        for (PessoaJuridicaJuridica pjj : pessoaJuridicaJuridicaList) {
            PessoaJuridicaJuridicaHistorico pjjh = new PessoaJuridicaJuridicaHistorico();
            pjjh.setCapitalDeParticipacao(pjj.getCapitalDeParticipacao());
            pjjh.setDataDeInicio(pjj.getDataDeInicio());
            pjjh.setDataDeTermino(pjj.getDataDeTermino());
            pjjh.setPessoaJuridicaSecundariaFk(pjj.getPessoaJuridicaSecundariaFk());
            pjjh.setPessoaJuridicaPrimariaFk(pjj.getPessoaJuridicaPrimariaFk());
            pessoaJuridicaJuridicaHistoricoList.add(pjjh);
        }
        for (Bem b : bemList) {
            BemHistorico bh = new BemHistorico();
            bh.setDataDeAquisicao(b.getDataDeAquisicao());
            bh.setDataDeTransferenciaOuExtincao(b.getDataDeTransferenciaOuExtincao());
            bh.setDescricao(b.getDescricao());
            bh.setEndereco(b.getEndereco());
            bh.setValor(b.getValor());
            bh.setTipoBemFk(b.getTipoBemFk());
            bemHistoricoList.add(bh);
        }
        pessoaJuridicaHistorico.setPessoaJuridicaJuridicaHistoricoCollection(pessoaJuridicaJuridicaHistoricoList);
        enderecoPessoaFisicaJuridicaHistorico.setPessoaHistorico(pessoaJuridicaHistorico);
        enderecoPessoaFisicaJuridicaHistorico.setEnderecoHistorico(enderecoHistorico);
        enderecoPessoaFisicaJuridicaHistorico.setBemHistoricoList(bemHistoricoList);
        enderecoPessoaFisicaJuridicaHistorico.setPessoaFisicaJuridicaHistoricoList(pessoaFisicaJuridicaHistoricoList);
        return enderecoPessoaFisicaJuridicaHistorico;
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
        return pessoaFisicaVinculo;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisicaVinculo = pessoaFisica;
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

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public List<EnderecoPessoaFisicaJuridicaHistorico> getEnderecoPessoaFisicaJuridicaHistoricoList() {
        return enderecoPessoaFisicaJuridicaHistoricoList;
    }

    public void setEnderecoPessoaFisicaJuridicaHistoricoList(List<EnderecoPessoaFisicaJuridicaHistorico> enderecoPessoaFisicaJuridicaHistoricoList) {
        this.enderecoPessoaFisicaJuridicaHistoricoList = enderecoPessoaFisicaJuridicaHistoricoList;
    }

    public List<EnderecoPessoa> getEnderecoPessoaList() {
        return enderecoPessoaList;
    }

    public void setEnderecoPessoaList(List<EnderecoPessoa> enderecoPessoaList) {
        this.enderecoPessoaList = enderecoPessoaList;
    }

    public EnderecoPessoa getEnderecoPessoa() {
        return enderecoPessoa;
    }

    public void setEnderecoPessoa(EnderecoPessoa enderecoPessoa) {
        this.enderecoPessoa = enderecoPessoa;
    }

    public String getPjId() {
        return pjId;
    }

    public void setPjId(String pjId) {
        this.pjId = pjId;
    }

    public String getPfVId() {
        return pfVId;
    }

    public void setPfVId(String pfVId) {
        this.pfVId = pfVId;
    }

    public String getPjVId() {
        return pjVId;
    }

    public void setPjVId(String pjVId) {
        this.pjVId = pjVId;
    }

    public List<PessoaJuridicaJuridica> getPessoaJuridicaJuridicaList() {
        return pessoaJuridicaJuridicaList;
    }

    public void setPessoaJuridicaJuridicaList(List<PessoaJuridicaJuridica> pessoaJuridicaJuridicaList) {
        this.pessoaJuridicaJuridicaList = pessoaJuridicaJuridicaList;
    }

    public String getPjsId() {
        return pjsId;
    }

    public void setPjsId(String pjsId) {
        this.pjsId = pjsId;
    }

    public List<PessoaJuridicaSucessaoHistorico> getPessoaJuridicaSucessaoHistoricoList() {
        return pessoaJuridicaSucessaoHistoricoList;
    }

    public void setPessoaJuridicaSucessaoHistoricoList(List<PessoaJuridicaSucessaoHistorico> pessoaJuridicaSucessaoHistoricoList) {
        this.pessoaJuridicaSucessaoHistoricoList = pessoaJuridicaSucessaoHistoricoList;
    }

    public PessoaJuridicaSucessao getPessoaJuridicaSucessao() {
        return pessoaJuridicaSucessao;
    }

    public void setPessoaJuridicaSucessao(PessoaJuridicaSucessao pessoaJuridicaSucessao) {
        this.pessoaJuridicaSucessao = pessoaJuridicaSucessao;
    }

    public List<ProcessoJudicial> getProcessoJudicialList() {
        return processoJudicialList;
    }

    public void setProcessoJudicialList(List<ProcessoJudicial> processoJudicialList) {
        this.processoJudicialList = processoJudicialList;
    }

    public Bem getBem() {
        return bem;
    }

    public void setBem(Bem bem) {
        this.bem = bem;
    }

    public List<Bem> getBemList() {
        return bemList;
    }

    public void setBemList(List<Bem> bemList) {
        this.bemList = bemList;
    }

    public List<BemHistorico> getBemHistoricoList() {
        return bemHistoricoList;
    }

    public void setBemHistoricoList(List<BemHistorico> bemHistoricoList) {
        this.bemHistoricoList = bemHistoricoList;
    }

    public List<TipoBem> getTipoBemList() {
        return tipoBemList;
    }

    public void setTipoBemList(List<TipoBem> tipoBemList) {
        this.tipoBemList = tipoBemList;
    }

}
