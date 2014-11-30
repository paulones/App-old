/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bo.BemBO;
import bo.CitacaoBO;
import bo.EnderecoBO;
import bo.PessoaFisicaBO;
import bo.PessoaFisicaJuridicaBO;
import bo.PessoaJuridicaBO;
import bo.PessoaJuridicaJuridicaBO;
import bo.ProcessoJudicialBO;
import bo.ProcessoJudicialHistoricoBO;
import bo.ProcuradorBO;
import bo.SituacaoBO;
import bo.TipoPenhoraBO;
import bo.TipoProcessoBO;
import bo.TipoRecursoBO;
import bo.UsuarioBO;
import bo.UtilBO;
import bo.VinculoProcessualBO;
import bo.VinculoProcessualHistoricoBO;
import entidade.Citacao;
import entidade.EnderecoPessoa;
import entidade.Executado;
import entidade.ExecutadoHistorico;
import entidade.Instituicao;
import entidade.PessoaFisica;
import entidade.PessoaFisicaJuridica;
import entidade.PessoaJuridica;
import entidade.PessoaJuridicaJuridica;
import entidade.ProcessoJudicial;
import entidade.ProcessoJudicialHistorico;
import entidade.Procurador;
import entidade.Redirecionamento;
import entidade.Situacao;
import entidade.SocioRedirecionamento;
import entidade.TipoPenhora;
import entidade.TipoProcesso;
import entidade.TipoRecurso;
import entidade.VinculoProcessual;
import entidade.VinculoProcessualHistorico;
import java.io.IOException;
import java.io.Serializable;
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

/**
 *
 * @author paulones
 */
@ViewScoped
@ManagedBean(name = "processoJudicialBean")
public class ProcessoJudicialBean implements Serializable {

    private ProcessoJudicial processoJudicial;
    private Executado executado;
    private EnderecoPessoa enderecoPessoaFisica;
    private EnderecoPessoa enderecoPessoaJuridica;
    private EnderecoPessoa enderecoPessoaModalFisica;
    private EnderecoPessoa enderecoPessoaModalJuridica;
    private VinculoProcessual vinculoProcessual;
    private ProcessoJudicial oldProcessoJudicial;
    private ProcessoJudicialHistorico processoJudicialHistorico;
    private ExecutadoHistorico executadoHistorico;
    private TipoPenhora tipoPenhora;

    private List<PessoaFisica> pessoaFisicaList;
    private List<PessoaJuridica> pessoaJuridicaList;
    private List<TipoRecurso> tipoDeRecursoList;
    private List<TipoProcesso> tipoDoProcessoList;
    private List<VinculoProcessual> vinculoProcessualList;
    private List<VinculoProcessualHistorico> vinculoProcessualHistoricoList;
    private List<ExecutadoHistorico> executadoHistoricoList;
    private List<Situacao> situacaoList;
    private List<Procurador> procuradorList;
    private List<ProcessoJudicial> executadoProcessoJudicialList;
    private List<Citacao> arList;
    private List<Citacao> oficialList;
    private List<Citacao> editalList;
    private List<Citacao> enderecoSocioList;
    private List<PessoaFisica> socioPFList;
    private List<PessoaJuridica> socioPJList;
    private List<SocioRedirecionamento> socioRedirecionamentoList;
    private List<TipoPenhora> tipoPenhoraList;

    private PessoaFisicaBO pessoaFisicaBO;
    private PessoaJuridicaBO pessoaJuridicaBO;
    private ProcessoJudicialBO processoJudicialBO;
    private TipoRecursoBO tipoRecursoBO;
    private TipoProcessoBO tipoProcessoBO;
    private VinculoProcessualBO vinculoProcessualBO;
    private EnderecoBO enderecoBO;
    private UsuarioBO usuarioBO;
    private ProcessoJudicialHistoricoBO processoJudicialHistoricoBO;
    private BemBO bemBO;
    private VinculoProcessualHistoricoBO vinculoProcessualHistoricoBO;
    private SituacaoBO situacaoBO;
    private ProcuradorBO procuradorBO;
    private CitacaoBO citacaoBO;
    private TipoPenhoraBO tipoPenhoraBO;

    private Integer vinculos;
    private String executadoPF;
    private String executadoPJ;
    private String register;
    private String redirect;
    private String pjudId;
    private boolean edit;
    private boolean history;
    private Integer ars;
    private Integer oficiais;
    private Integer editais;
    private Integer enderecosSocios;
    private Character redirecionamento;

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
            tipoPenhora = new TipoPenhora();

            pessoaFisicaBO = new PessoaFisicaBO();
            pessoaJuridicaBO = new PessoaJuridicaBO();
            processoJudicialBO = new ProcessoJudicialBO();
            tipoRecursoBO = new TipoRecursoBO();
            tipoProcessoBO = new TipoProcessoBO();
            vinculoProcessualBO = new VinculoProcessualBO();
            enderecoBO = new EnderecoBO();
            usuarioBO = new UsuarioBO();
            processoJudicialHistoricoBO = new ProcessoJudicialHistoricoBO();
            bemBO = new BemBO();
            vinculoProcessualHistoricoBO = new VinculoProcessualHistoricoBO();
            situacaoBO = new SituacaoBO();
            procuradorBO = new ProcuradorBO();
            citacaoBO = new CitacaoBO();
            tipoPenhoraBO = new TipoPenhoraBO();

            vinculos = 0;
            ars = 0;
            oficiais = 0;
            editais = 0;
            enderecosSocios = 0;
            executadoPF = "";
            executadoPJ = "";
            register = "";
            redirect = Cookie.getCookie("FacesMessage");
            Cookie.apagarCookie("FacesMessage");

            vinculoProcessualList = new ArrayList<>();
            arList = new ArrayList<>();
            oficialList = new ArrayList<>();
            editalList = new ArrayList<>();
            enderecoSocioList = new ArrayList<>();
            socioPFList = new ArrayList<>();
            socioPJList = new ArrayList<>();
            socioRedirecionamentoList = new ArrayList<>();
            
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
                    try {
                        Integer id = Integer.valueOf(request.getParameter("id"));
                        processoJudicial = processoJudicialBO.findProcessoJudicial(id);
                        if (processoJudicial == null) {
                            FacesContext.getCurrentInstance().getExternalContext().redirect("cadastrar.xhtml");
                        } else {
                            edit = true;
                            for (VinculoProcessual vinculoProcessual : processoJudicial.getVinculoProcessualCollection()) {
                                vinculoProcessualList.add(vinculoProcessual);
                            }
                            vinculos = vinculoProcessualList.size();
                            if (processoJudicial.getExecutado().equals("PF")) {
                                executadoPF = String.valueOf(processoJudicial.getExecutadoFk());
                            } else {
                                executadoPJ = String.valueOf(processoJudicial.getExecutadoFk());
                            }

                            oldProcessoJudicial = processoJudicialBO.findProcessoJudicial(id);

                            prepararHistorico(processoJudicial);

                            carregarFormulario();
                        }
                    } catch (Exception e) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("cadastrar.xhtml");
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
                    try {
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
                                enderecoPessoa = new EnderecoPessoa(pessoaFisica, enderecoBO.findPFAddress(pessoaFisica.getId()), bemBO.findPFBens(pessoaFisica.getId()));
                            } else if (processoJudicial.getExecutado().equals("PJ")) {
                                PessoaJuridica pessoaJuridica = pessoaJuridicaBO.findPessoaJuridica(processoJudicial.getExecutadoFk());
                                enderecoPessoa = new EnderecoPessoa(pessoaJuridica, enderecoBO.findPJAddress(pessoaJuridica.getId()), bemBO.findPJBens(pessoaJuridica.getId()));
                            }
                            executadoHistorico = prepararRegistroAtual(processoJudicial, enderecoPessoa);
                            executadoHistoricoList.add(executadoHistorico);
                            // Inserção dos históricos
                            List<ProcessoJudicialHistorico> processoJudicialHistoricoList = processoJudicialHistoricoBO.findAllByPJUD(id);
                            for (ProcessoJudicialHistorico pjh : processoJudicialHistoricoList) {
                                if (pjh.getExecutado().equals("PF")) {
                                    PessoaFisica pessoaFisica = pessoaFisicaBO.findPessoaFisica(pjh.getExecutadoFk());
                                    enderecoPessoa = new EnderecoPessoa(pessoaFisica, enderecoBO.findPFAddress(pessoaFisica.getId()), bemBO.findPFBens(pessoaFisica.getId()));
                                    executadoHistorico = new ExecutadoHistorico(pjh, enderecoPessoa, null);
                                } else if (pjh.getExecutado().equals("PJ")) {
                                    PessoaJuridica pessoaJuridica = pessoaJuridicaBO.findPessoaJuridica(pjh.getExecutadoFk());
                                    enderecoPessoa = new EnderecoPessoa(pessoaJuridica, enderecoBO.findPJAddress(pessoaJuridica.getId()), bemBO.findPJBens(pessoaJuridica.getId()));
                                    executadoHistorico = new ExecutadoHistorico(pjh, null, enderecoPessoa);
                                }
                                executadoHistoricoList.add(executadoHistorico);
                            }
                        }
                    } catch (Exception e) {
                        history = false;
                        FacesContext.getCurrentInstance().getExternalContext().redirect("consultar.xhtml");
                    }
                }
            }
        }
    }

    private void carregarFormulario() { // Carregar listas do formulário
        UsuarioBO usuarioBO = new UsuarioBO();
        Instituicao instituicao = usuarioBO.findAutorizacaoByCPF(Cookie.getCookie("usuario")).getInstituicaoFk();
        pessoaFisicaList = pessoaFisicaBO.findAllActive(instituicao);
        pessoaJuridicaList = pessoaJuridicaBO.findAllActive(instituicao);
        tipoDeRecursoList = tipoRecursoBO.findAll();
        tipoDoProcessoList = tipoProcessoBO.findAll();
        situacaoList = situacaoBO.findAll();
        procuradorList = procuradorBO.findAll();
        tipoPenhoraList = tipoPenhoraBO.findAll();
    }

    public void adicionarVinculosProcessuais() {
        if (vinculos > vinculoProcessualList.size()) {
            vinculos = vinculoProcessualList.isEmpty() ? vinculos : vinculos - vinculoProcessualList.size();
            for (int i = 0; i < vinculos; i++) {
                vinculoProcessual = new VinculoProcessual();
                vinculoProcessualList.add(vinculoProcessual);
            }
        } else if (vinculos < vinculoProcessualList.size()) {
            while (vinculoProcessualList.size() > vinculos) {
                vinculoProcessualList.remove(vinculoProcessualList.size() - 1);
            }
        }
    }
    
    public void adicionarQuantidadeDeCitacoes(String tipo){
        if (tipo.equals("AR")){
            arList = adicionarCitacoes(arList, ars, tipo);
        } else if (tipo.equals("OJ")){
            oficialList = adicionarCitacoes(oficialList, oficiais, tipo);
        } else if (tipo.equals("ED")){
            editalList = adicionarCitacoes(editalList, editais, tipo);
        } else if (tipo.equals("ES")){
            enderecoSocioList = adicionarCitacoes(enderecoSocioList, enderecosSocios, tipo);
        }
    }
    
    public List<Citacao> adicionarCitacoes(List<Citacao> citacaoList, Integer quantidade, String tipo){
        if (quantidade > citacaoList.size()) {
            quantidade = citacaoList.isEmpty() ? quantidade : quantidade - citacaoList.size();
            for (int i = 0; i < quantidade; i++) {
                Citacao citacao = new Citacao();
                citacao.setTipoCitacao(tipo);
                citacao.setCitado('N');
                citacaoList.add(citacao);
            }
        } else if (quantidade < citacaoList.size()) {
            while (citacaoList.size() > quantidade) {
                citacaoList.remove(citacaoList.size() - 1);
            }
        }
        return citacaoList;
    }
    
    public void listarSocios(String tipo){
        PessoaFisicaJuridicaBO pfjBO = new PessoaFisicaJuridicaBO();
        PessoaJuridicaJuridicaBO pjjBO = new PessoaJuridicaJuridicaBO();
        if (tipo.equals("PJ") && executadoPJ != null){
            List<PessoaFisicaJuridica> pfjList = pfjBO.findAllByPJ(Integer.valueOf(executadoPJ));
            for (PessoaFisicaJuridica pfj : pfjList){
                socioPFList.add(pfj.getPessoaFisicaFk());
                SocioRedirecionamento socioRedirecionamento = new SocioRedirecionamento((PessoaFisica) pfj.getPessoaFisicaFk(), new Redirecionamento()); 
                socioRedirecionamentoList.add(socioRedirecionamento);
            }
            List<PessoaJuridicaJuridica> pjjList = pjjBO.findAllByPJAOrPJB(Integer.valueOf(executadoPJ));
            for (PessoaJuridicaJuridica pjj : pjjList){
                if(pjj.getPessoaJuridicaPrimariaFk().getId().equals(Integer.valueOf(executadoPJ))){
                    socioPJList.add(pjj.getPessoaJuridicaSecundariaFk());
                    SocioRedirecionamento socioRedirecionamento = new SocioRedirecionamento((PessoaJuridica) pjj.getPessoaJuridicaSecundariaFk(), new Redirecionamento()); 
                    socioRedirecionamentoList.add(socioRedirecionamento);
                }
            }
        }
    }

    public void exibirExecutado() {
        if (processoJudicial.getExecutado().equals("PF")) {
            PessoaFisica pessoaFisica = pessoaFisicaBO.findPessoaFisica(Integer.valueOf(executadoPF));
            enderecoPessoaFisica = new EnderecoPessoa(pessoaFisica, enderecoBO.findPFAddress(pessoaFisica.getId()), bemBO.findPFBens(pessoaFisica.getId()));
            executadoProcessoJudicialList = processoJudicialBO.findByExecutado(executadoPF, "PF");
        } else if (processoJudicial.getExecutado().equals("PJ")) {
            PessoaJuridica pessoaJuridica = pessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(executadoPJ));
            enderecoPessoaJuridica = new EnderecoPessoa(pessoaJuridica, enderecoBO.findPJAddress(pessoaJuridica.getId()), bemBO.findPJBens(pessoaJuridica.getId()));
            executadoProcessoJudicialList = processoJudicialBO.findByExecutado(executadoPJ, "PJ");
        }
    }

    public void exibirInfo() {
        processoJudicial = processoJudicialBO.findProcessoJudicial(Integer.valueOf(pjudId));
        if (processoJudicial.getExecutado().equals("PF")) {
            PessoaFisica pessoaFisica = pessoaFisicaBO.findPessoaFisica(processoJudicial.getExecutadoFk());
            enderecoPessoaFisica = new EnderecoPessoa(pessoaFisica, enderecoBO.findPFAddress(pessoaFisica.getId()), bemBO.findPFBens(pessoaFisica.getId()));
            executado = new Executado(processoJudicial, enderecoPessoaFisica);
        } else {
            PessoaJuridica pessoaJuridica = pessoaJuridicaBO.findPessoaJuridica(processoJudicial.getExecutadoFk());
            enderecoPessoaJuridica = new EnderecoPessoa(pessoaJuridica, enderecoBO.findPJAddress(pessoaJuridica.getId()), bemBO.findPJBens(pessoaJuridica.getId()));
            executado = new Executado(processoJudicial, enderecoPessoaJuridica);
        }
    }

    public void removerProcessoJudicial() {
        processoJudicial = processoJudicialBO.findProcessoJudicial(Integer.valueOf(pjudId));
        processoJudicial.setStatus('I');
        processoJudicialBO.edit(processoJudicial);
        GeradorLog.criar(processoJudicial.getId(), "PJUD", 'D');
        redirect = "";
        register = "success";
    }

    public void evitarAvisosIndevidosNoForm() throws IOException {
        register = "none";
        FacesContext.getCurrentInstance().getExternalContext().redirect("cadastrar.xhtml");
    }

    public void cadastrar() throws IOException {
        boolean error = false;
        ProcessoJudicial pjudDBCDA = processoJudicialBO.findByCDA(processoJudicial);
        ProcessoJudicial pjudDBProcess = processoJudicialBO.findByProcessNumber(processoJudicial.getNumeroDoProcesso());
        processoJudicial.setExecutadoFk(executadoPF != null ? Integer.valueOf(executadoPF) : Integer.valueOf(executadoPJ));
        if (!edit) {
            /*  
             Cadastrar novo Processo Judicial
             */
            if (pjudDBCDA == null && pjudDBProcess == null) { // Processo novo
                processoJudicial.setUsuarioFk(usuarioBO.findUsuarioByCPF(Cookie.getCookie("usuario")));
                processoJudicial.setInstituicaoFk(usuarioBO.findAutorizacaoByCPF(Cookie.getCookie("usuario")).getInstituicaoFk());
                processoJudicial.setStatus('A');
                if (processoJudicial.getValorAtualizado() == null && processoJudicial.getValorDaCausa() != null) {
                    processoJudicial.setValorAtualizado(processoJudicial.getValorDaCausa());
                }
                processoJudicialBO.create(processoJudicial);
                for (VinculoProcessual vinculoProcessual : vinculoProcessualList) {
                    vinculoProcessual.setProcessoJudicialFk(processoJudicial);
                    vinculoProcessualBO.create(vinculoProcessual);
                }
                List<Citacao> citacaoList = new ArrayList<>();
                citacaoList.addAll(arList);
                citacaoList.addAll(oficialList);
                citacaoList.addAll(editalList);
                citacaoList.addAll(enderecoSocioList);
                for(Citacao c : citacaoList){
                    c.setProcessoJudicialFk(processoJudicial);
                    citacaoBO.create(c);
                }
                register = "success";
                GeradorLog.criar(processoJudicial.getId(), "PJUD", 'C');
                processoJudicial = new ProcessoJudicial();
                vinculoProcessual = new VinculoProcessual();
                vinculos = 0;
            } else { // CDA ou Processo já cadastrado
                error = true;
            }
        } else {
            /*  
             Alterar Pessoa Física existente
             */
            if ((pjudDBCDA == null || processoJudicial.equals(pjudDBCDA)) && (pjudDBProcess == null || processoJudicial.equals(pjudDBProcess))) {
                boolean identical = true;
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
                    if (processoJudicial.getValorAtualizado() == null && processoJudicial.getValorDaCausa() != null) {
                        processoJudicial.setValorAtualizado(processoJudicial.getValorDaCausa());
                    }
                    processoJudicialBO.edit(processoJudicial);
                    processoJudicialHistorico.setDataDeModificacao(timestamp);
                    processoJudicialHistoricoBO.create(processoJudicialHistorico);
                    vinculoProcessualBO.destroyByPJUD(processoJudicial.getId());
                    for (VinculoProcessual vinculoProcessual : vinculoProcessualList) {
                        vinculoProcessual.setProcessoJudicialFk(processoJudicial);
                        vinculoProcessualBO.create(vinculoProcessual);
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
        vinculoProcessualHistoricoList = new ArrayList<>();

        processoJudicialHistorico.setAtoProcessual(processoJudicial.getAtoProcessual());
        processoJudicialHistorico.setComarca(processoJudicial.getComarca());
        processoJudicialHistorico.setDataDeInscricao(processoJudicial.getDataDeInscricao());
        processoJudicialHistorico.setDespachoInicial(processoJudicial.getDespachoInicial());
        processoJudicialHistorico.setDespachoInicialDataDoAto(processoJudicial.getDespachoInicialDataDoAto());
        processoJudicialHistorico.setDiscriminacaoDoCreditoImposto(processoJudicial.getDiscriminacaoDoCreditoImposto());
        processoJudicialHistorico.setDiscriminacaoDoCreditoMulta(processoJudicial.getDiscriminacaoDoCreditoMulta());
        processoJudicialHistorico.setDistribuicao(processoJudicial.getDistribuicao());
        processoJudicialHistorico.setDistribuicaoDataDoAto(processoJudicial.getDistribuicaoDataDoAto());
        processoJudicialHistorico.setExecutado(processoJudicial.getExecutado());
        processoJudicialHistorico.setExecutadoFk(processoJudicial.getExecutadoFk());
        processoJudicialHistorico.setFatosGeradores(processoJudicial.getFatosGeradores());
        processoJudicialHistorico.setFonteDaArrecadacao(processoJudicial.getFonteDaArrecadacao());
        processoJudicialHistorico.setFundamentacao(processoJudicial.getFundamentacao());
        processoJudicialHistorico.setGrupoDeEspecializacao(processoJudicial.getGrupoDeEspecializacao());
        processoJudicialHistorico.setNotificacaoAdministrativa(processoJudicial.getNotificacaoAdministrativa());
        processoJudicialHistorico.setNotificacaoAdministrativaDataDoAto(processoJudicial.getNotificacaoAdministrativaDataDoAto());
        processoJudicialHistorico.setNumeroDaCda(processoJudicial.getNumeroDaCda());
        processoJudicialHistorico.setNumeroDoProcesso(processoJudicial.getNumeroDoProcesso());
        processoJudicialHistorico.setNumeroDoProcessoAnterior(processoJudicial.getNumeroDoProcessoAnterior());
        processoJudicialHistorico.setOutrasInformacoesAtoProcessual(processoJudicial.getOutrasInformacoesAtoProcessual());
        processoJudicialHistorico.setOutrasInformacoesExecutado(processoJudicial.getOutrasInformacoesExecutado());
        processoJudicialHistorico.setOutrasInformacoesProcesso(processoJudicial.getOutrasInformacoesProcesso());
        processoJudicialHistorico.setProcessoJudicialFk(processoJudicial);
        processoJudicialHistorico.setProcuradorFk(processoJudicial.getProcuradorFk());
        processoJudicialHistorico.setRecurso(processoJudicial.getRecurso());
        processoJudicialHistorico.setSituacaoFk(processoJudicial.getSituacaoFk());
        processoJudicialHistorico.setTipoDeRecursoFk(processoJudicial.getTipoDeRecursoFk());
        processoJudicialHistorico.setUsuarioFk(processoJudicial.getUsuarioFk());
        processoJudicialHistorico.setValorArrecadado(processoJudicial.getValorArrecadado());
        processoJudicialHistorico.setValorAtualizado(processoJudicial.getValorAtualizado());
        processoJudicialHistorico.setValorDaCausa(processoJudicial.getValorDaCausa());
        processoJudicialHistorico.setVara(processoJudicial.getVara());
        processoJudicialHistorico.setVaraAnterior(processoJudicial.getVaraAnterior());

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
        vinculoProcessualHistoricoList = new ArrayList<>();

        processoJudicialHistorico.setAtoProcessual(processoJudicial.getAtoProcessual());
        processoJudicialHistorico.setComarca(processoJudicial.getComarca());
        processoJudicialHistorico.setDataDeInscricao(processoJudicial.getDataDeInscricao());
        processoJudicialHistorico.setDespachoInicial(processoJudicial.getDespachoInicial());
        processoJudicialHistorico.setDespachoInicialDataDoAto(processoJudicial.getDespachoInicialDataDoAto());
        processoJudicialHistorico.setDiscriminacaoDoCreditoImposto(processoJudicial.getDiscriminacaoDoCreditoImposto());
        processoJudicialHistorico.setDiscriminacaoDoCreditoMulta(processoJudicial.getDiscriminacaoDoCreditoMulta());
        processoJudicialHistorico.setDistribuicao(processoJudicial.getDistribuicao());
        processoJudicialHistorico.setDistribuicaoDataDoAto(processoJudicial.getDistribuicaoDataDoAto());
        processoJudicialHistorico.setExecutado(processoJudicial.getExecutado());
        processoJudicialHistorico.setExecutadoFk(processoJudicial.getExecutadoFk());
        processoJudicialHistorico.setFatosGeradores(processoJudicial.getFatosGeradores());
        processoJudicialHistorico.setFonteDaArrecadacao(processoJudicial.getFonteDaArrecadacao());
        processoJudicialHistorico.setFundamentacao(processoJudicial.getFundamentacao());
        processoJudicialHistorico.setGrupoDeEspecializacao(processoJudicial.getGrupoDeEspecializacao());
        processoJudicialHistorico.setNotificacaoAdministrativa(processoJudicial.getNotificacaoAdministrativa());
        processoJudicialHistorico.setNotificacaoAdministrativaDataDoAto(processoJudicial.getNotificacaoAdministrativaDataDoAto());
        processoJudicialHistorico.setNumeroDaCda(processoJudicial.getNumeroDaCda());
        processoJudicialHistorico.setNumeroDoProcesso(processoJudicial.getNumeroDoProcesso());
        processoJudicialHistorico.setNumeroDoProcessoAnterior(processoJudicial.getNumeroDoProcessoAnterior());
        processoJudicialHistorico.setOutrasInformacoesAtoProcessual(processoJudicial.getOutrasInformacoesAtoProcessual());
        processoJudicialHistorico.setOutrasInformacoesExecutado(processoJudicial.getOutrasInformacoesExecutado());
        processoJudicialHistorico.setOutrasInformacoesProcesso(processoJudicial.getOutrasInformacoesProcesso());
        processoJudicialHistorico.setProcessoJudicialFk(processoJudicial);
        processoJudicialHistorico.setProcuradorFk(processoJudicial.getProcuradorFk());
        processoJudicialHistorico.setRecurso(processoJudicial.getRecurso());
        processoJudicialHistorico.setSituacaoFk(processoJudicial.getSituacaoFk());
        processoJudicialHistorico.setTipoDeRecursoFk(processoJudicial.getTipoDeRecursoFk());
        processoJudicialHistorico.setUsuarioFk(processoJudicial.getUsuarioFk());
        processoJudicialHistorico.setValorArrecadado(processoJudicial.getValorArrecadado());
        processoJudicialHistorico.setValorAtualizado(processoJudicial.getValorAtualizado());
        processoJudicialHistorico.setValorDaCausa(processoJudicial.getValorDaCausa());
        processoJudicialHistorico.setVara(processoJudicial.getVara());
        processoJudicialHistorico.setVaraAnterior(processoJudicial.getVaraAnterior());

        for (VinculoProcessual vinculoProcessual : (List<VinculoProcessual>) processoJudicial.getVinculoProcessualCollection()) {
            VinculoProcessualHistorico vinculoProcessualHistorico = new VinculoProcessualHistorico();
            vinculoProcessualHistorico.setProcesso(vinculoProcessual.getProcesso());
            vinculoProcessualHistorico.setTipoDeProcessoFk(vinculoProcessual.getTipoDeProcessoFk());
            vinculoProcessualHistoricoList.add(vinculoProcessualHistorico);
        }
        processoJudicialHistorico.setVinculoProcessualHistoricoCollection(vinculoProcessualHistoricoList);

        executadoHistorico.setProcessoJudicialHistorico(processoJudicialHistorico);
        if (processoJudicial.getExecutado().equals("PF")) {
            executadoHistorico.setEnderecoPessoaFisica(enderecoPessoa);
        } else {
            executadoHistorico.setEnderecoPessoaJuridica(enderecoPessoa);
        }
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

    public List<VinculoProcessual> getVinculoProcessualList() {
        return vinculoProcessualList;
    }

    public void setVinculoProcessualList(List<VinculoProcessual> vinculoProcessualList) {
        this.vinculoProcessualList = vinculoProcessualList;
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

    public List<Situacao> getSituacaoList() {
        return situacaoList;
    }

    public void setSituacaoList(List<Situacao> situacaoList) {
        this.situacaoList = situacaoList;
    }

    public List<Procurador> getProcuradorList() {
        return procuradorList;
    }

    public void setProcuradorList(List<Procurador> procuradorList) {
        this.procuradorList = procuradorList;
    }

    public List<ProcessoJudicial> getExecutadoProcessoJudicialList() {
        return executadoProcessoJudicialList;
    }

    public void setExecutadoProcessoJudicialList(List<ProcessoJudicial> executadoProcessoJudicialList) {
        this.executadoProcessoJudicialList = executadoProcessoJudicialList;
    }

    public List<Citacao> getArList() {
        return arList;
    }

    public void setArList(List<Citacao> arList) {
        this.arList = arList;
    }

    public List<Citacao> getOficialList() {
        return oficialList;
    }

    public void setOficialList(List<Citacao> oficialList) {
        this.oficialList = oficialList;
    }

    public List<Citacao> getEditalList() {
        return editalList;
    }

    public void setEditalList(List<Citacao> editalList) {
        this.editalList = editalList;
    }

    public List<Citacao> getEnderecoSocioList() {
        return enderecoSocioList;
    }

    public void setEnderecoSocioList(List<Citacao> enderecoSocioList) {
        this.enderecoSocioList = enderecoSocioList;
    }

    public Integer getArs() {
        return ars;
    }

    public void setArs(Integer ars) {
        this.ars = ars;
    }

    public Integer getOficiais() {
        return oficiais;
    }

    public void setOficiais(Integer oficiais) {
        this.oficiais = oficiais;
    }

    public Integer getEditais() {
        return editais;
    }

    public void setEditais(Integer editais) {
        this.editais = editais;
    }

    public Integer getEnderecosSocios() {
        return enderecosSocios;
    }

    public void setEnderecosSocios(Integer enderecosSocios) {
        this.enderecosSocios = enderecosSocios;
    }

    public List<PessoaFisica> getSocioPFList() {
        return socioPFList;
    }

    public void setSocioPFList(List<PessoaFisica> socioPFList) {
        this.socioPFList = socioPFList;
    }

    public List<PessoaJuridica> getSocioPJList() {
        return socioPJList;
    }

    public void setSocioPJList(List<PessoaJuridica> socioPJList) {
        this.socioPJList = socioPJList;
    }

    public Character getRedirecionamento() {
        return redirecionamento;
    }

    public void setRedirecionamento(Character redirecionamento) {
        this.redirecionamento = redirecionamento;
    }

    public List<SocioRedirecionamento> getSocioRedirecionamentoList() {
        return socioRedirecionamentoList;
    }

    public void setSocioRedirecionamentoList(List<SocioRedirecionamento> socioRedirecionamentoList) {
        this.socioRedirecionamentoList = socioRedirecionamentoList;
    }

    public List<TipoPenhora> getTipoPenhoraList() {
        return tipoPenhoraList;
    }

    public void setTipoPenhoraList(List<TipoPenhora> tipoPenhoraList) {
        this.tipoPenhoraList = tipoPenhoraList;
    }

    public TipoPenhora getTipoPenhora() {
        return tipoPenhora;
    }

    public void setTipoPenhora(TipoPenhora tipoPenhora) {
        this.tipoPenhora = tipoPenhora;
    }
    
}
