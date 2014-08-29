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
import entidade.ExecutadoHistorico;
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
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import util.Base64Crypt;
import util.Cookie;
import util.GeradorLog;

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
    private ExecutadoHistorico executadoHistorico;

    private List<PessoaFisica> pessoaFisicaList;
    private List<PessoaJuridica> pessoaJuridicaList;
    private List<Bem> bemList;
    private List<TipoRecurso> tipoDeRecursoList;
    private List<TipoProcesso> tipoDoProcessoList;
    private List<VinculoProcessual> vinculoProcessualList;
    private List<BemHistorico> bemHistoricoList;
    private List<VinculoProcessualHistorico> vinculoProcessualHistoricoList;
    private List<ExecutadoHistorico> executadoHistoricoList;

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
                        for (Bem bem : processoJudicial.getBemCollection()){
                            bemList.add(bem);
                        }
                        for (VinculoProcessual vinculoProcessual : processoJudicial.getVinculoProcessualCollection()){
                            vinculoProcessualList.add(vinculoProcessual);
                        }
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
                    processoJudicial = processoJudicialBO.findProcessoJudicial(id);
                    if (processoJudicial == null) {
                        history = false;
                        FacesContext.getCurrentInstance().getExternalContext().redirect("consultar.xhtml");
                    } else {
                        history = true;
                        executadoHistoricoList = new ArrayList<>();
                        EnderecoPessoa enderecoPessoa = new EnderecoPessoa();
                        // Inserção do registro atual
                        if (processoJudicial.getExecutado().equals("PF")) {
                            PessoaFisica pessoaFisica = pessoaFisicaBO.findPessoaFisica(processoJudicial.getExecutadoFk());
                            enderecoPessoa = new EnderecoPessoa(pessoaFisica, enderecoBO.findPFAddress(pessoaFisica.getId()));
                        } else if (processoJudicial.getExecutado().equals("PJ")) {
                            PessoaJuridica pessoaJuridica = pessoaJuridicaBO.findPessoaJuridica(processoJudicial.getExecutadoFk());
                            enderecoPessoa = new EnderecoPessoa(pessoaJuridica, enderecoBO.findPJAddress(pessoaJuridica.getId()));
                        }
                        executadoHistorico = prepararRegistroAtual(processoJudicial, enderecoPessoa);
                        executadoHistoricoList.add(executadoHistorico);
                        // Inserção dos históricos
                        List<ProcessoJudicialHistorico> processoJudicialHistoricoList = processoJudicialHistoricoBO.findAllByPJUD(id);
                        for (ProcessoJudicialHistorico pjh : processoJudicialHistoricoList) {
                            if (pjh.getExecutado().equals("PF")) {
                                PessoaFisica pessoaFisica = pessoaFisicaBO.findPessoaFisica(pjh.getExecutadoFk());
                                enderecoPessoa = new EnderecoPessoa(pessoaFisica, enderecoBO.findPFAddress(pessoaFisica.getId()));
                            } else if (pjh.getExecutado().equals("PJ")) {
                                PessoaJuridica pessoaJuridica = pessoaJuridicaBO.findPessoaJuridica(pjh.getExecutadoFk());
                                enderecoPessoa = new EnderecoPessoa(pessoaJuridica, enderecoBO.findPJAddress(pessoaJuridica.getId()));
                            }
                            executadoHistorico = new ExecutadoHistorico(pjh, enderecoPessoa);
                            executadoHistoricoList.add(executadoHistorico);
                        }
                    }
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
        if (bens > bemList.size()) {
            for (int i = 0; i < bens - bemList.size(); i++) {
                bem = new Bem();
                bemList.add(bem);
            }
        } else if (bens < bemList.size()) {
            while (bemList.size() > bens){
                bemList.remove(bemList.size()-1);
            }
        }

    }

    public void adicionarVinculosProcessuais() {
        if (vinculos > vinculoProcessualList.size()) {
            for (int i = 0; i < vinculos - vinculoProcessualList.size(); i++) {
                vinculoProcessual = new VinculoProcessual();
                vinculoProcessualList.add(vinculoProcessual);
            }
        } else if (vinculos < vinculoProcessualList.size()) {
            while (vinculoProcessualList.size() > vinculos){
                vinculoProcessualList.remove(vinculoProcessualList.size()-1);
            }
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
        GeradorLog.criar(processoJudicial.getId(), "PJUD", 'D');
        redirect = "";
        register = "success";
    }

    public void cadastrar() throws IOException {
        boolean error = false;
        ProcessoJudicial pjudDBCDA = processoJudicialBO.findByCDA(processoJudicial);
        ProcessoJudicial pjudDBProcess = processoJudicialBO.findByProcessNumber(processoJudicial);
        processoJudicial.setExecutadoFk(executadoPF != null ? Integer.valueOf(Base64Crypt.decrypt(executadoPF)) : Integer.valueOf(Base64Crypt.decrypt(executadoPJ)));
        if (!edit) {
            /*  
             Cadastrar novo Processo Judicial
             */
            if (pjudDBCDA == null && pjudDBProcess == null) { // Processo novo
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
                GeradorLog.criar(processoJudicial.getId(), "PJUD", 'C');
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
                for (Iterator<Bem> iterator = bemList.iterator(); iterator.hasNext();) {
                    Bem bem = iterator.next();
                    if (bem.getDescricao() == null && bem.getDataDoAto() == null) {
                        iterator.remove();
                    }
                }
                if (oldProcessoJudicial.getBemCollection().size() != bemList.size()) {
                    identical = false;
                } else {
                    for (Bem bem : bemList) {
                        for (Bem oldBem : oldProcessoJudicial.getBemCollection()) {
                            if (bem.equalsValues(oldBem)) {
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
                    if (oldProcessoJudicial.getVinculoProcessualCollection().size() != vinculoProcessualList.size()) {
                        identical = false;
                    } else {
                        for (VinculoProcessual vinculoProcessual : vinculoProcessualList) {
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
                        bem.setProcessoJudicialFk(processoJudicial);
                        bemBO.create(bem);
                    }
                    vinculoProcessualBO.destroyByPJUD(processoJudicial.getId());
                    for (VinculoProcessual vinculoProcessual : vinculoProcessualList) {
                        vinculoProcessual.setProcessoJudicialFk(processoJudicial);
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
                    GeradorLog.criar(processoJudicial.getId(), "PJUD", 'U');
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
            if ((pjudDBProcess != null && pjudDBCDA == null) || (edit && processoJudicial.equals(pjudDBCDA))) {
                message += "Já existe um Processo Judicial cadastrado com o número " + pjudDBProcess.getNumeroDoProcesso();
                message += "\nNº da CDA: " + pjudDBProcess.getNumeroDaCda();
                message += prepararMensagemDeErro(pjudDBProcess);
            } else if ((pjudDBCDA != null && pjudDBProcess == null) || (edit && processoJudicial.equals(pjudDBProcess))) {
                message += "Já existe um Processo Judicial cadastrado com CDA de número " + pjudDBCDA.getNumeroDaCda();
                message += "\nNº do Processo: " + pjudDBCDA.getNumeroDoProcesso();
                message += prepararMensagemDeErro(pjudDBCDA);
            } else if (pjudDBCDA.equals(pjudDBProcess)) {
                message += "Já existe um Processo Judicial cadastrado com o número " + pjudDBProcess.getNumeroDoProcesso() + " e CDA de número " + pjudDBProcess.getNumeroDaCda();
                message += prepararMensagemDeErro(pjudDBProcess);
            } else {
                message += "Já existem Processos Judiciais cadastrados com número " + processoJudicial.getNumeroDoProcesso() + " e CDA de número " + processoJudicial.getNumeroDaCda();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }
    }

    private String prepararMensagemDeErro(ProcessoJudicial processoJudicial) {
        String mensagem = "\nComarca: " + processoJudicial.getComarca();
        if (processoJudicial.getExecutado().equals("PF")) {
            PessoaFisica pf = pessoaFisicaBO.findPessoaFisica(processoJudicial.getExecutadoFk());
            mensagem += "\nExecutado: " + pf.getNome();
            mensagem += "\nCPF: " + (pf.getCpf() != null ? pf.getCpf().substring(0, 3) + "." + pf.getCpf().substring(3, 6) + "." + pf.getCpf().substring(6, 9) + "-" + pf.getCpf().substring(9) : "-");
        } else {
            PessoaJuridica pj = pessoaJuridicaBO.findPessoaJuridica(processoJudicial.getExecutadoFk());
            mensagem += "\nExecutado: " + pj.getNome();
            mensagem += "\nCNPJ: " + pj.getCnpj().substring(0, 3) + "." + pj.getCnpj().substring(3, 6) + "." + pj.getCnpj().substring(6, 9) + "/" + pj.getCnpj().substring(9, 13) + "-" + pj.getCnpj().substring(13);
        }
        return mensagem;
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

    private ExecutadoHistorico prepararRegistroAtual(ProcessoJudicial processoJudicial, EnderecoPessoa enderecoPessoa) {
        /*
         Montar registro atual como uma entidade de histórico para facilitar o ui:repeat do form
         */
        ExecutadoHistorico executadoHistorico = new ExecutadoHistorico();
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
        processoJudicialHistorico.setBemHistoricoCollection(bemHistoricoList);

        for (VinculoProcessual vinculoProcessual : (List<VinculoProcessual>) processoJudicial.getVinculoProcessualCollection()) {
            VinculoProcessualHistorico vinculoProcessualHistorico = new VinculoProcessualHistorico();
            vinculoProcessualHistorico.setProcesso(vinculoProcessual.getProcesso());
            vinculoProcessualHistorico.setTipoDeProcessoFk(vinculoProcessual.getTipoDeProcessoFk());
            vinculoProcessualHistoricoList.add(vinculoProcessualHistorico);
        }
        processoJudicialHistorico.setVinculoProcessualHistoricoCollection(vinculoProcessualHistoricoList);

        executadoHistorico.setProcessoJudicialHistorico(processoJudicialHistorico);
        executadoHistorico.setEnderecoPessoa(enderecoPessoa);

        return executadoHistorico;
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

    public List<ExecutadoHistorico> getExecutadoHistoricoList() {
        return executadoHistoricoList;
    }

    public void setExecutadoHistoricoList(List<ExecutadoHistorico> executadoHistoricoList) {
        this.executadoHistoricoList = executadoHistoricoList;
    }

}
