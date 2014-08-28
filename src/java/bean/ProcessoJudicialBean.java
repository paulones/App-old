/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bo.BemBO;
import bo.BemHistoricoBO;
import bo.EnderecoBO;
import bo.PessoaFisicaBO;
import bo.PessoaJuridicaBO;
import bo.ProcessoJudicialBO;
import bo.ProcessoJudicialHistoricoBO;
import bo.TipoProcessoBO;
import bo.TipoRecursoBO;
import bo.UsuarioBO;
import bo.UtilBO;
import bo.VinculoProcessualBO;
import bo.VinculoProcessualHistoricoBO;
import entidade.Bem;
import entidade.BemHistorico;
import entidade.Endereco;
import entidade.EnderecoPessoa;
import entidade.Executado;
import entidade.PessoaFisica;
import entidade.PessoaJuridica;
import entidade.ProcessoJudicial;
import entidade.ProcessoJudicialHistorico;
import entidade.TipoProcesso;
import entidade.TipoRecurso;
import entidade.VinculoProcessual;
import entidade.VinculoProcessualHistorico;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import util.Base64Crypt;
import util.Cookie;

/**
 *
 * @author paulones
 */
@SessionScoped
@ManagedBean(name = "processoJudicialBean")
public class ProcessoJudicialBean implements Serializable {

    private ProcessoJudicial processoJudicial;
    private Executado executado;
    private EnderecoPessoa enderecoPessoaFisica;
    private EnderecoPessoa enderecoPessoaJuridica;
    private EnderecoPessoa enderecoPessoaModalFisica;
    private EnderecoPessoa enderecoPessoaModalJuridica;
    private Bem bem;
    private VinculoProcessual vinculoProcessual;
    private ProcessoJudicial oldProcessoJudicial;
    private ProcessoJudicialHistorico processoJudicialHistorico;

    private List<PessoaFisica> pessoaFisicaList;
    private List<PessoaJuridica> pessoaJuridicaList;
    private List<Bem> bemList;
    private List<TipoRecurso> tipoDeRecursoList;
    private List<TipoProcesso> tipoDoProcessoList;
    private List<VinculoProcessual> vinculoProcessualList;
    private List<BemHistorico> bemHistoricoList;
    private List<VinculoProcessualHistorico> vinculoProcessualHistoricoList;

    private PessoaFisicaBO pessoaFisicaBO;
    private PessoaJuridicaBO pessoaJuridicaBO;
    private ProcessoJudicialBO processoJudicialBO;
    private TipoRecursoBO tipoRecursoBO;
    private TipoProcessoBO tipoProcessoBO;
    private VinculoProcessualBO vinculoProcessualBO;
    private EnderecoBO enderecoBO;
    private UsuarioBO usuarioBO;
    private BemBO bemBO;
    private ProcessoJudicialHistoricoBO processoJudicialHistoricoBO;
    private BemHistoricoBO bemHistoricoBO;
    private VinculoProcessualHistoricoBO vinculoProcessualHistoricoBO;

    private Integer bens;
    private Integer vinculos;
    private String executadoPF;
    private String executadoPJ;
    private String register;
    private String redirect;
    private String pjudId;
    private boolean edit;
    private boolean history;

    public void init() throws IOException {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            boolean isRegisterPage = FacesContext.getCurrentInstance().getViewRoot().getViewId().lastIndexOf("cadastrar") > -1;
            boolean isSearchPage = FacesContext.getCurrentInstance().getViewRoot().getViewId().lastIndexOf("consultar") > -1;

            executado = new Executado();
            processoJudicial = new ProcessoJudicial();
            enderecoPessoaFisica = new EnderecoPessoa();
            enderecoPessoaJuridica = new EnderecoPessoa();
            enderecoPessoaModalFisica = new EnderecoPessoa();
            oldProcessoJudicial = new ProcessoJudicial();

            pessoaFisicaBO = new PessoaFisicaBO();
            pessoaJuridicaBO = new PessoaJuridicaBO();
            processoJudicialBO = new ProcessoJudicialBO();
            tipoRecursoBO = new TipoRecursoBO();
            tipoProcessoBO = new TipoProcessoBO();
            vinculoProcessualBO = new VinculoProcessualBO();
            enderecoBO = new EnderecoBO();
            bemBO = new BemBO();
            usuarioBO = new UsuarioBO();
            processoJudicialHistoricoBO = new ProcessoJudicialHistoricoBO();
            bemHistoricoBO = new BemHistoricoBO();
            vinculoProcessualHistoricoBO = new VinculoProcessualHistoricoBO();

            bens = 0;
            vinculos = 0;
            executadoPF = "";
            executadoPJ = "";
            register = "";
            redirect = Cookie.getCookie("FacesMessage");
            Cookie.apagarCookie("FacesMessage");

            bemList = new ArrayList<>();
            vinculoProcessualList = new ArrayList<>();

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            if (isRegisterPage) {
                /*
                 Tela cadastro.xhtml. Se houver "id" na url, entra na condição de alteração.
                 Caso contrário, apenas carrega o formulário
                 */
                if (request.getParameter("id") == null) {   // Novo
                    edit = false;
                    carregarFormulario();
                } else {                                    // Alteração
                    Integer id = Integer.valueOf(request.getParameter("id"));
                    processoJudicial = processoJudicialBO.findProcessoJudicial(id);
                    if (processoJudicial == null) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("cadastrar.xhtml");
                    } else {
                        edit = true;
                        bemList = (List<Bem>) processoJudicial.getBemCollection();
                        vinculoProcessualList = (List<VinculoProcessual>) processoJudicial.getVinculoProcessualCollection();
                        vinculos = vinculoProcessualList.size();
                        bens = bemList.size();
                        if (processoJudicial.getExecutado().equals("PF")) {
                            executadoPF = Base64Crypt.encrypt(String.valueOf(processoJudicial.getExecutadoFk()));
                        } else {
                            executadoPJ = Base64Crypt.encrypt(String.valueOf(processoJudicial.getExecutadoFk()));
                        }

                        oldProcessoJudicial = processoJudicialBO.findProcessoJudicial(id);

                        prepararHistorico(processoJudicial);

                        carregarFormulario();
                    }
                }
            } else if (isSearchPage) {
                /*
                 Tela consulta.xhtml. Se houver "id" na url, entra na lista de alterações nos
                 dados da Pessoa Física.Caso contrário, acessa a consulta geral 
                 */
                if (request.getParameter("id") == null) {   // Consulta geral
                    history = false;
                } else {                                    // Consulta histórico
                    Integer id = Integer.valueOf(request.getParameter("id"));
                }
            }
        }
    }

    private void carregarFormulario() { // Carregar listas do formulário
        pessoaFisicaList = pessoaFisicaBO.findAllActive();
        pessoaJuridicaList = pessoaJuridicaBO.findAllActive();
        tipoDeRecursoList = tipoRecursoBO.findAll();
        tipoDoProcessoList = tipoProcessoBO.findAll();
    }

    public void adicionarBens() {
        bemList = new ArrayList<>();
        for (int i = 0; i < bens; i++) {
            bem = new Bem();
            bemList.add(bem);
        }
    }

    public void adicionarVinculosProcessuais() {
        vinculoProcessualList = new ArrayList<>();
        for (int i = 0; i < vinculos; i++) {
            vinculoProcessual = new VinculoProcessual();
            vinculoProcessualList.add(vinculoProcessual);
        }
    }

    public void exibirExecutado() {
        if (processoJudicial.getExecutado().equals("PF")) {
            PessoaFisica pessoaFisica = pessoaFisicaBO.findPessoaFisica(Integer.valueOf(Base64Crypt.decrypt(executadoPF)));
            enderecoPessoaFisica = new EnderecoPessoa(pessoaFisica, enderecoBO.findPFAddress(pessoaFisica.getId()));
        } else if (processoJudicial.getExecutado().equals("PJ")) {
            PessoaJuridica pessoaJuridica = pessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(Base64Crypt.decrypt(executadoPJ)));
            enderecoPessoaJuridica = new EnderecoPessoa(pessoaJuridica, enderecoBO.findPJAddress(pessoaJuridica.getId()));
        }
    }

    public void exibirInfo() {
        processoJudicial = processoJudicialBO.findProcessoJudicial(Integer.valueOf(Base64Crypt.decrypt(pjudId)));
        if (processoJudicial.getExecutado().equals("PF")) {
            PessoaFisica pessoaFisica = pessoaFisicaBO.findPessoaFisica(processoJudicial.getExecutadoFk());
            enderecoPessoaFisica = new EnderecoPessoa(pessoaFisica, enderecoBO.findPFAddress(pessoaFisica.getId()));
            executado = new Executado(processoJudicial, enderecoPessoaFisica);
        } else {
            PessoaJuridica pessoaJuridica = pessoaJuridicaBO.findPessoaJuridica(processoJudicial.getExecutadoFk());
            enderecoPessoaJuridica = new EnderecoPessoa(pessoaJuridica, enderecoBO.findPJAddress(pessoaJuridica.getId()));
            executado = new Executado(processoJudicial, enderecoPessoaJuridica);
        }
    }

    public void removerProcessoJudicial() {
        processoJudicial = processoJudicialBO.findProcessoJudicial(Integer.valueOf(Base64Crypt.decrypt(pjudId)));
        processoJudicial.setStatus('I');
        processoJudicialBO.edit(processoJudicial);
        redirect = "";
        register = "success";
    }

    public void cadastrar() throws IOException {
        boolean error = false;
        ProcessoJudicial pjudDBCDA = processoJudicialBO.findByCDA(processoJudicial);
        ProcessoJudicial pjudDBProcess = processoJudicialBO.findByProcessNumber(processoJudicial);
        if (!edit) {
            /*  
             Cadastrar novo Processo Judicial
             */
            if (pjudDBCDA == null && pjudDBProcess == null) { // Processo novo
                processoJudicial.setExecutadoFk(executadoPF != null ? Integer.valueOf(Base64Crypt.decrypt(executadoPF)) : Integer.valueOf(Base64Crypt.decrypt(executadoPJ)));
                processoJudicial.setUsuarioFk(usuarioBO.findUsuarioByCPF(Cookie.getCookie("usuario")));
                processoJudicial.setStatus('A');
                processoJudicialBO.create(processoJudicial);
                for (Bem bem : bemList) {
                    if (bem.getDescricao() != null || bem.getDataDoAto() != null) {
                        bem.setProcessoJudicialFk(processoJudicial);
                        bemBO.create(bem);
                    }
                }
                for (VinculoProcessual vinculoProcessual : vinculoProcessualList) {
                    vinculoProcessual.setProcessoJudicialFk(processoJudicial);
                    vinculoProcessualBO.create(vinculoProcessual);
                }
                register = "success";
                processoJudicial = new ProcessoJudicial();
                bemList = new ArrayList<>();
                vinculoProcessual = new VinculoProcessual();
                vinculos = 0;
                bens = 0;
            } else { // CDA ou Processo já cadastrado
                error = true;
            }
        } else {
            /*  
             Alterar Pessoa Física existente
             */
            if ((pjudDBCDA == null || processoJudicial.equals(pjudDBCDA)) && (pjudDBProcess == null || processoJudicial.equals(pjudDBProcess))) {
                boolean identical = true;
                processoJudicial.setBemCollection(bemList);
                processoJudicial.setVinculoProcessualCollection(vinculoProcessualList);
                if (oldProcessoJudicial.getBemCollection().size() != processoJudicial.getBemCollection().size()) {
                    identical = false;
                } else {
                    for (Bem bem : processoJudicial.getBemCollection()) {
                        for (Bem oldBem : oldProcessoJudicial.getBemCollection()) {
                            if (bem.getDescricao().equals(oldBem.getDescricao())) {
                                identical = true;
                                break;
                            } else {
                                identical = false;
                            }
                        }
                        if (!identical) {
                            break;
                        }
                    }
                }
                if (identical) {
                    if (oldProcessoJudicial.getVinculoProcessualCollection().size() != processoJudicial.getVinculoProcessualCollection().size()) {
                        identical = false;
                    } else {
                        for (VinculoProcessual vinculoProcessual : processoJudicial.getVinculoProcessualCollection()) {
                            for (VinculoProcessual oldVinculoProcessual : oldProcessoJudicial.getVinculoProcessualCollection()) {
                                if (vinculoProcessual.equalsValues(oldVinculoProcessual)) {
                                    identical = true;
                                    break;
                                } else {
                                    identical = false;
                                }
                            }
                            if (!identical) {
                                break;
                            }
                        }
                    }
                }
                if (oldProcessoJudicial.equalsValues(processoJudicial)
                        && identical) {
                    Cookie.addCookie("FacesMessage", "fail", 10);
                    FacesContext.getCurrentInstance().getExternalContext().redirect("consultar.xhtml");
                } else {
                    UtilBO utilBO = new UtilBO();
                    Timestamp timestamp = utilBO.findServerTime();
                    processoJudicial.setUsuarioFk(usuarioBO.findUsuarioByCPF(Cookie.getCookie("usuario")));
                    processoJudicialBO.edit(processoJudicial);
                    processoJudicialHistorico.setDataDeModificacao(timestamp);
                    processoJudicialHistoricoBO.create(processoJudicialHistorico);
                    bemBO.destroyByPJUD(processoJudicial.getId());
                    for (Bem bem : bemList) {
                        bemBO.create(bem);
                    }
                    vinculoProcessualBO.destroyByPJUD(processoJudicial.getId());
                    for (VinculoProcessual vinculoProcessual : vinculoProcessualList) {
                        vinculoProcessualBO.create(vinculoProcessual);
                    }
                    for (BemHistorico bh : bemHistoricoList) {
                        bh.setProcessoJudicialHistoricoFk(processoJudicialHistorico);
                        bemHistoricoBO.create(bh);
                    }
                    for (VinculoProcessualHistorico vph : vinculoProcessualHistoricoList) {
                        vph.setProcessoJudicialHistoricoFk(processoJudicialHistorico);
                        vinculoProcessualHistoricoBO.create(vph);
                    }
                    Cookie.addCookie("FacesMessage", "success", 10);
                    FacesContext.getCurrentInstance().getExternalContext().redirect("consultar.xhtml");
                }
            } else { // CDA ou Processo já cadastrado
                error = true;
            }
        }
        if (error) { // Exibição dinâmica de erros
            register = "fail";
            String message = "";
            if (!edit) {
                if (pjudDBProcess != null) {
                    message += "Já existe um processo cadastrado com o número " + pjudDBProcess.getNumeroDoProcesso();
                    message += "\nComarca: " + pjudDBProcess.getComarca();
                    message += "\nNº da CDA: " + pjudDBProcess.getNumeroDaCda();
                    if (pjudDBProcess.getExecutado().equals("PF")) {
                        PessoaFisica pf = pessoaFisicaBO.findPessoaFisica(pjudDBProcess.getExecutadoFk());
                        message += "\nExecutado: " + pf.getNome();
                        message += "\nCPF: " + (pf.getCpf() != null ? pf.getCpf().substring(0, 3) + "." + pf.getCpf().substring(3, 6) + "." + pf.getCpf().substring(6, 9) + "-" + pf.getCpf().substring(9) : "-");
                    } else {
                        PessoaJuridica pj = pessoaJuridicaBO.findPessoaJuridica(pjudDBProcess.getExecutadoFk());
                        message += "\nExecutado: " + pj.getNome();
                        message += "\nCNPJ: " + pj.getCnpj().substring(0, 3) + "." + pj.getCnpj().substring(3, 6) + "." + pj.getCnpj().substring(6, 9) + "/" + pj.getCnpj().substring(9, 13) + "-" + pj.getCnpj().substring(13);
                    }
                } else {
                    message += "Já existe um processo cadastrado com a CDA de número " + pjudDBCDA.getNumeroDaCda();
                    message += "\nComarca: " + pjudDBCDA.getComarca();
                    message += "\nNº do Processo: " + pjudDBCDA.getNumeroDoProcesso();
                    if (pjudDBCDA.getExecutado().equals("PF")) {
                        PessoaFisica pf = pessoaFisicaBO.findPessoaFisica(pjudDBCDA.getExecutadoFk());
                        message += "\nExecutado: " + pf.getNome();
                        message += "\nCPF: " + (pf.getCpf() != null ? pf.getCpf().substring(0, 3) + "." + pf.getCpf().substring(3, 6) + "." + pf.getCpf().substring(6, 9) + "-" + pf.getCpf().substring(9) : "-");
                    } else {
                        PessoaJuridica pj = pessoaJuridicaBO.findPessoaJuridica(pjudDBCDA.getExecutadoFk());
                        message += "\nExecutado: " + pj.getNome();
                        message += "\nCNPJ: " + pj.getCnpj().substring(0, 3) + "." + pj.getCnpj().substring(3, 6) + "." + pj.getCnpj().substring(6, 9) + "/" + pj.getCnpj().substring(9, 13) + "-" + pj.getCnpj().substring(13);
                    }
                }
            } else {
                if (processoJudicial.equals(pjudDBCDA)) {
                    message = "Já existe um processo cadastrado com número " + pjudDBProcess.getNumeroDoProcesso();
                    message += "\nComarca: " + pjudDBProcess.getComarca();
                    message += "\nNº da CDA: " + pjudDBProcess.getNumeroDaCda();
                    if (pjudDBProcess.getExecutado().equals("PF")) {
                        PessoaFisica pf = pessoaFisicaBO.findPessoaFisica(pjudDBProcess.getExecutadoFk());
                        message += "\nExecutado: " + pf.getNome();
                        message += "\nCPF: " + (pf.getCpf() != null ? pf.getCpf().substring(0, 3) + "." + pf.getCpf().substring(3, 6) + "." + pf.getCpf().substring(6, 9) + "-" + pf.getCpf().substring(9) : "-");
                    } else {
                        PessoaJuridica pj = pessoaJuridicaBO.findPessoaJuridica(pjudDBProcess.getExecutadoFk());
                        message += "\nExecutado: " + pj.getNome();
                        message += "\nCNPJ: " + pj.getCnpj().substring(0, 3) + "." + pj.getCnpj().substring(3, 6) + "." + pj.getCnpj().substring(6, 9) + "/" + pj.getCnpj().substring(9, 13) + "-" + pj.getCnpj().substring(13);
                    }
                } else if (processoJudicial.equals(pjudDBProcess)) {
                    message = "Já existe um processo cadastrado com a CDA de número " + pjudDBCDA.getNumeroDaCda();
                    message += "\nComarca: " + pjudDBCDA.getComarca();
                    message += "\nNº do Processo: " + pjudDBCDA.getNumeroDoProcesso();
                    if (pjudDBCDA.getExecutado().equals("PF")) {
                        PessoaFisica pf = pessoaFisicaBO.findPessoaFisica(pjudDBCDA.getExecutadoFk());
                        message += "\nExecutado: " + pf.getNome();
                        message += "\nCPF: " + (pf.getCpf() != null ? pf.getCpf().substring(0, 3) + "." + pf.getCpf().substring(3, 6) + "." + pf.getCpf().substring(6, 9) + "-" + pf.getCpf().substring(9) : "-");
                    } else {
                        PessoaJuridica pj = pessoaJuridicaBO.findPessoaJuridica(pjudDBCDA.getExecutadoFk());
                        message += "\nExecutado: " + pj.getNome();
                        message += "\nCNPJ: " + pj.getCnpj().substring(0, 3) + "." + pj.getCnpj().substring(3, 6) + "." + pj.getCnpj().substring(6, 9) + "/" + pj.getCnpj().substring(9, 13) + "-" + pj.getCnpj().substring(13);
                    }
                } else {
                    message = "Já existe um processo cadastrado com número do processo " + processoJudicial.getNumeroDoProcesso() + " e/ou CDA de número " + processoJudicial.getNumeroDaCda();
                }
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }
    }

    public void prepararHistorico(ProcessoJudicial processoJudicial) {
        /*
         Montar entidades dos históricos de alteração 
         */
        processoJudicialHistorico = new ProcessoJudicialHistorico();
        bemHistoricoList = new ArrayList<>();
        vinculoProcessualHistoricoList = new ArrayList<>();

        processoJudicialHistorico.setAtoProcessual(processoJudicial.getAtoProcessual());
        processoJudicialHistorico.setComarca(processoJudicial.getComarca());
        processoJudicialHistorico.setDataDeInscricao(processoJudicial.getDataDeInscricao());
        processoJudicialHistorico.setDecisaoDoJuiz(processoJudicial.getDecisaoDoJuiz());
        processoJudicialHistorico.setDecisaoDoJuizDataDoAto(processoJudicial.getDecisaoDoJuizDataDoAto());
        processoJudicialHistorico.setDespachoInicial(processoJudicial.getDespachoInicial());
        processoJudicialHistorico.setDespachoInicialDataDoAto(processoJudicial.getDespachoInicialDataDoAto());
        processoJudicialHistorico.setDiscriminacaoDoCreditoImposto(processoJudicial.getDiscriminacaoDoCreditoImposto());
        processoJudicialHistorico.setDiscriminacaoDoCreditoMulta(processoJudicial.getDiscriminacaoDoCreditoMulta());
        processoJudicialHistorico.setDistribuicao(processoJudicial.getDistribuicao());
        processoJudicialHistorico.setDistribuicaoDataDoAto(processoJudicial.getDistribuicaoDataDoAto());
        processoJudicialHistorico.setExecutado(processoJudicial.getExecutado());
        processoJudicialHistorico.setExecutadoFk(processoJudicial.getExecutadoFk());
        processoJudicialHistorico.setFatosGeradores(processoJudicial.getFatosGeradores());
        processoJudicialHistorico.setFundamentacao(processoJudicial.getFundamentacao());
        processoJudicialHistorico.setGrupoDeEspecializacao(processoJudicial.getGrupoDeEspecializacao());
        processoJudicialHistorico.setNotificacaoAdministrativa(processoJudicial.getNotificacaoAdministrativa());
        processoJudicialHistorico.setNotificacaoAdministrativaDataDoAto(processoJudicial.getNotificacaoAdministrativaDataDoAto());
        processoJudicialHistorico.setNumeroDaCda(processoJudicial.getNumeroDaCda());
        processoJudicialHistorico.setNumeroDoProcesso(processoJudicial.getNumeroDoProcesso());
        processoJudicialHistorico.setNumeroDoProcessoAnterior(processoJudicial.getNumeroDoProcessoAnterior());
        processoJudicialHistorico.setOutrasInformacoesAtoProcessual(processoJudicial.getOutrasInformacoesAtoProcessual());
        processoJudicialHistorico.setOutrasInformacoesBem(processoJudicial.getOutrasInformacoesBem());
        processoJudicialHistorico.setOutrasInformacoesExecutado(processoJudicial.getOutrasInformacoesExecutado());
        processoJudicialHistorico.setOutrasInformacoesProcesso(processoJudicial.getOutrasInformacoesProcesso());
        processoJudicialHistorico.setProcessoJudicialFk(processoJudicial);
        processoJudicialHistorico.setProcurador(processoJudicial.getProcurador());
        processoJudicialHistorico.setRecurso(processoJudicial.getRecurso());
        processoJudicialHistorico.setTipoDeRecursoFk(processoJudicial.getTipoDeRecursoFk());
        processoJudicialHistorico.setUsuarioFk(processoJudicial.getUsuarioFk());
        processoJudicialHistorico.setValorAtualizado(processoJudicial.getValorAtualizado());
        processoJudicialHistorico.setValorDaCausa(processoJudicial.getValorDaCausa());
        processoJudicialHistorico.setVara(processoJudicial.getVara());
        processoJudicialHistorico.setVaraAnterior(processoJudicial.getVaraAnterior());

        for (Bem bem : (List<Bem>) processoJudicial.getBemCollection()) {
            BemHistorico bemHistorico = new BemHistorico();
            bemHistorico.setDataDoAto(bem.getDataDoAto());
            bemHistorico.setDescricao(bem.getDescricao());
            bemHistoricoList.add(bemHistorico);
        }

        for (VinculoProcessual vinculoProcessual : (List<VinculoProcessual>) processoJudicial.getVinculoProcessualCollection()) {
            VinculoProcessualHistorico vinculoProcessualHistorico = new VinculoProcessualHistorico();
            vinculoProcessualHistorico.setProcesso(vinculoProcessual.getProcesso());
            vinculoProcessualHistorico.setTipoDeProcessoFk(vinculoProcessual.getTipoDeProcessoFk());
            vinculoProcessualHistoricoList.add(vinculoProcessualHistorico);
        }
    }

    public ProcessoJudicial getProcessoJudicial() {
        return processoJudicial;
    }

    public void setProcessoJudicial(ProcessoJudicial processoJudicial) {
        this.processoJudicial = processoJudicial;
    }

    public Executado getExecutado() {
        return executado;
    }

    public void setExecutado(Executado executado) {
        this.executado = executado;
    }

    public List<PessoaFisica> getPessoaFisicaList() {
        return pessoaFisicaList;
    }

    public void setPessoaFisicaList(List<PessoaFisica> pessoaFisicaList) {
        this.pessoaFisicaList = pessoaFisicaList;
    }

    public List<PessoaJuridica> getPessoaJuridicaList() {
        return pessoaJuridicaList;
    }

    public void setPessoaJuridicaList(List<PessoaJuridica> pessoaJuridicaList) {
        this.pessoaJuridicaList = pessoaJuridicaList;
    }

    public List<Bem> getBemList() {
        return bemList;
    }

    public void setBemList(List<Bem> bemList) {
        this.bemList = bemList;
    }

    public List<VinculoProcessual> getVinculoProcessualList() {
        return vinculoProcessualList;
    }

    public void setVinculoProcessualList(List<VinculoProcessual> vinculoProcessualList) {
        this.vinculoProcessualList = vinculoProcessualList;
    }

    public Integer getBens() {
        return bens;
    }

    public void setBens(Integer bens) {
        this.bens = bens;
    }

    public List<TipoRecurso> getTipoDeRecursoList() {
        return tipoDeRecursoList;
    }

    public void setTipoDeRecursoList(List<TipoRecurso> tipoDeRecursoList) {
        this.tipoDeRecursoList = tipoDeRecursoList;
    }

    public Integer getVinculos() {
        return vinculos;
    }

    public void setVinculos(Integer vinculos) {
        this.vinculos = vinculos;
    }

    public List<TipoProcesso> getTipoDoProcessoList() {
        return tipoDoProcessoList;
    }

    public void setTipoDoProcessoList(List<TipoProcesso> tipoDoProcessoList) {
        this.tipoDoProcessoList = tipoDoProcessoList;
    }

    public EnderecoPessoa getEnderecoPessoaFisica() {
        return enderecoPessoaFisica;
    }

    public void setEnderecoPessoaFisica(EnderecoPessoa enderecoPessoaFisica) {
        this.enderecoPessoaFisica = enderecoPessoaFisica;
    }

    public EnderecoPessoa getEnderecoPessoaJuridica() {
        return enderecoPessoaJuridica;
    }

    public void setEnderecoPessoaJuridica(EnderecoPessoa enderecoPessoaJuridica) {
        this.enderecoPessoaJuridica = enderecoPessoaJuridica;
    }

    public EnderecoPessoa getEnderecoPessoaModalFisica() {
        return enderecoPessoaModalFisica;
    }

    public void setEnderecoPessoaModalFisica(EnderecoPessoa enderecoPessoaModalFisica) {
        this.enderecoPessoaModalFisica = enderecoPessoaModalFisica;
    }

    public EnderecoPessoa getEnderecoPessoaModalJuridica() {
        return enderecoPessoaModalJuridica;
    }

    public void setEnderecoPessoaModalJuridica(EnderecoPessoa enderecoPessoaModalJuridica) {
        this.enderecoPessoaModalJuridica = enderecoPessoaModalJuridica;
    }

    public String getExecutadoPF() {
        return executadoPF;
    }

    public void setExecutadoPF(String executadoPF) {
        this.executadoPF = executadoPF;
    }

    public String getExecutadoPJ() {
        return executadoPJ;
    }

    public void setExecutadoPJ(String executadoPJ) {
        this.executadoPJ = executadoPJ;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public String getPjudId() {
        return pjudId;
    }

    public void setPjudId(String pjudId) {
        this.pjudId = pjudId;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public boolean isHistory() {
        return history;
    }

    public void setHistory(boolean history) {
        this.history = history;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

}
