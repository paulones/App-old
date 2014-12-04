/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bo.BemBO;
import bo.CitacaoBO;
import bo.CitacaoHistoricoBO;
import bo.EnderecoBO;
import bo.PenhoraBO;
import bo.PenhoraHistoricoBO;
import bo.PessoaFisicaBO;
import bo.PessoaFisicaJuridicaBO;
import bo.PessoaJuridicaBO;
import bo.PessoaJuridicaJuridicaBO;
import bo.ProcessoJudicialBO;
import bo.ProcessoJudicialHistoricoBO;
import bo.ProcuradorBO;
import bo.RedirecionamentoBO;
import bo.RedirecionamentoHistoricoBO;
import bo.SituacaoBO;
import bo.TipoPenhoraBO;
import bo.TipoProcessoBO;
import bo.TipoRecursoBO;
import bo.UsuarioBO;
import bo.UtilBO;
import bo.VinculoProcessualBO;
import bo.VinculoProcessualHistoricoBO;
import entidade.Bem;
import entidade.Citacao;
import entidade.CitacaoHistorico;
import entidade.EnderecoPessoa;
import entidade.Executado;
import entidade.ExecutadoHistorico;
import entidade.Instituicao;
import entidade.Penhora;
import entidade.PenhoraHistorico;
import entidade.PessoaFisica;
import entidade.PessoaFisicaJuridica;
import entidade.PessoaJuridica;
import entidade.PessoaJuridicaJuridica;
import entidade.ProcessoJudicial;
import entidade.ProcessoJudicialHistorico;
import entidade.Procurador;
import entidade.Redirecionamento;
import entidade.RedirecionamentoHistorico;
import entidade.Situacao;
import entidade.SocioRedirecionamento;
import entidade.SocioRedirecionamentoHistorico;
import entidade.TipoPenhora;
import entidade.TipoProcesso;
import entidade.TipoRecurso;
import entidade.VinculoProcessual;
import entidade.VinculoProcessualHistorico;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
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
    private List<Object> socioList;
    private List<SocioRedirecionamento> socioRedirecionamentoList;
    private List<TipoPenhora> tipoPenhoraList;
    private List<Citacao> oldCitacaoList;
    private List<SocioRedirecionamento> oldSocioRedirecionamentoList;
    private List<CitacaoHistorico> citacaoHistoricoList;
    private List<RedirecionamentoHistorico> redirecionamentoHistoricoList;
    private List<Penhora> penhoraList;
    private List<PenhoraHistorico> penhoraHistoricoList;
    private List<Penhora> oldPenhoraList;
    private List<Bem> bemList;

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
    private RedirecionamentoBO redirecionamentoBO;
    private CitacaoHistoricoBO citacaoHistoricoBO;
    private RedirecionamentoHistoricoBO redirecionamentoHistoricoBO;
    private PenhoraBO penhoraBO;
    private PenhoraHistoricoBO penhoraHistoricoBO;

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
    private boolean initialLoad;

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
            redirecionamentoBO = new RedirecionamentoBO();
            citacaoHistoricoBO = new CitacaoHistoricoBO();
            redirecionamentoHistoricoBO = new RedirecionamentoHistoricoBO();
            penhoraBO = new PenhoraBO();
            penhoraHistoricoBO = new PenhoraHistoricoBO();

            vinculos = 0;
            ars = 0;
            oficiais = 0;
            editais = 0;
            enderecosSocios = 0;
            executadoPF = null;
            executadoPJ = null;
            register = "";
            redirect = Cookie.getCookie("FacesMessage");
            Cookie.apagarCookie("FacesMessage");

            vinculoProcessualList = new ArrayList<>();
            arList = new ArrayList<>();
            oficialList = new ArrayList<>();
            editalList = new ArrayList<>();
            enderecoSocioList = new ArrayList<>();
            socioList = new ArrayList<>();
            socioRedirecionamentoList = new ArrayList<>();
            penhoraList = new ArrayList<>();

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
                            List<Citacao> citacaoList = citacaoBO.findByPJUD(id);
                            for (Citacao citacao : citacaoList) {
                                if (citacao.getTipoCitacao().equals("AR")) {
                                    arList.add(citacao);
                                    ars++;
                                } else if (citacao.getTipoCitacao().equals("OJ")) {
                                    oficialList.add(citacao);
                                    oficiais++;
                                } else if (citacao.getTipoCitacao().equals("ED")) {
                                    editalList.add(citacao);
                                    editais++;
                                } else if (citacao.getTipoCitacao().equals("ES")) {
                                    if (citacao.getSocio() != null && citacao.getSocioFk() != null) {
                                        citacao.setSocioTipo(citacao.getSocio() + citacao.getSocioFk());
                                    }
                                    enderecoSocioList.add(citacao);
                                    enderecosSocios++;
                                }
                            }
                            socioRedirecionamentoList = carregarSocioRedirecionamento(redirecionamentoBO.findByPJUD(id));
                            initialLoad = !socioRedirecionamentoList.isEmpty();

                            vinculos = vinculoProcessualList.size();
                            if (processoJudicial.getExecutado().equals("PF")) {
                                executadoPF = String.valueOf(processoJudicial.getExecutadoFk());
                            } else {
                                executadoPJ = String.valueOf(processoJudicial.getExecutadoFk());
                            }

                            oldCitacaoList = citacaoBO.findByPJUD(id);
                            for (Citacao citacao : oldCitacaoList) {
                                if (citacao.getTipoCitacao().equals("ES")) {
                                    if (citacao.getSocio() != null && citacao.getSocioFk() != null) {
                                        citacao.setSocioTipo(citacao.getSocio() + citacao.getSocioFk());
                                    }
                                }
                            }
                            oldPenhoraList = penhoraBO.findByPJUD(id);
                            for (Penhora penhora : oldPenhoraList) {
                                if (penhora.getSocio() != null && penhora.getSocioFk() != null) {
                                    penhora.setSocioTipo(penhora.getSocio() + penhora.getSocioFk());
                                }
                            }
                            oldSocioRedirecionamentoList = new ArrayList<>();
                            oldSocioRedirecionamentoList = carregarSocioRedirecionamento(redirecionamentoBO.findByPJUD(id));
                            oldProcessoJudicial = processoJudicialBO.findProcessoJudicial(id);

                            prepararHistorico(processoJudicial, citacaoList, socioRedirecionamentoList, penhoraList);

                            carregarFormulario();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
                            executadoHistorico = prepararRegistroAtual(processoJudicial, enderecoPessoa, citacaoBO.findByPJUD(processoJudicial.getId()), carregarSocioRedirecionamento(redirecionamentoBO.findByPJUD(processoJudicial.getId())), penhoraBO.findByPJUD(processoJudicial.getId()));
                            executadoHistoricoList.add(executadoHistorico);
                            // Inserção dos históricos
                            List<ProcessoJudicialHistorico> processoJudicialHistoricoList = processoJudicialHistoricoBO.findAllByPJUD(id);
                            for (ProcessoJudicialHistorico pjh : processoJudicialHistoricoList) {
                                if (pjh.getExecutado().equals("PF")) {
                                    PessoaFisica pessoaFisica = pessoaFisicaBO.findPessoaFisica(pjh.getExecutadoFk());
                                    enderecoPessoa = new EnderecoPessoa(pessoaFisica, enderecoBO.findPFAddress(pessoaFisica.getId()), bemBO.findPFBens(pessoaFisica.getId()));
                                    executadoHistorico = new ExecutadoHistorico(pjh, enderecoPessoa, null, citacaoHistoricoBO.findByPJUD(pjh.getId()), carregarSocioRedirecionamentoHistorico(redirecionamentoHistoricoBO.findByPJUD(pjh.getId())), penhoraHistoricoBO.findByPJUD(pjh.getId()));
                                } else if (pjh.getExecutado().equals("PJ")) {
                                    PessoaJuridica pessoaJuridica = pessoaJuridicaBO.findPessoaJuridica(pjh.getExecutadoFk());
                                    enderecoPessoa = new EnderecoPessoa(pessoaJuridica, enderecoBO.findPJAddress(pessoaJuridica.getId()), bemBO.findPJBens(pessoaJuridica.getId()));
                                    executadoHistorico = new ExecutadoHistorico(pjh, null, enderecoPessoa, citacaoHistoricoBO.findByPJUD(pjh.getId()), carregarSocioRedirecionamentoHistorico(redirecionamentoHistoricoBO.findByPJUD(pjh.getId())), penhoraHistoricoBO.findByPJUD(pjh.getId()));
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

    public void distribuirCitacoesPorListas(String tipo) {
        if (tipo.equals("AR")) {
            arList = adicionarCitacoes(arList, ars, tipo);
        } else if (tipo.equals("OJ")) {
            oficialList = adicionarCitacoes(oficialList, oficiais, tipo);
        } else if (tipo.equals("ED")) {
            editalList = adicionarCitacoes(editalList, editais, tipo);
        } else if (tipo.equals("ES")) {
            enderecoSocioList = adicionarCitacoes(enderecoSocioList, enderecosSocios, tipo);
        }
    }

    public void adicionarPenhora() {
        if (tipoPenhora != null) {
            Penhora penhora = new Penhora();
            penhora.setTipoPenhoraFk(tipoPenhora);
            penhoraList.add(penhora);
        }
    }

    public void removerPenhora(int index) {
        penhoraList.remove(index);
    }

    public List<Citacao> adicionarCitacoes(List<Citacao> citacaoList, Integer quantidade, String tipo) {
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

    public void refreshListaDeBens(Integer index) {
        if (index == null) {
            if (executadoPJ != null) {
                bemList = bemBO.findPJBens(Integer.valueOf(executadoPJ));
            } else if (executadoPF != null) {
                bemList = bemBO.findPFBens(Integer.valueOf(executadoPF));
            }
            for (Penhora penhora : penhoraList) {
                if (penhora.getSocioTipo() != null) {
                    String tabela = penhora.getSocioTipo().substring(0, 2);
                    Integer id = Integer.valueOf(penhora.getSocioTipo().substring(2));
                    if (tabela.equals("PF")) {
                        penhora.setBemList(bemBO.findPFBens(id));
                    } else if (tabela.equals("PJ")) {
                        penhora.setBemList(bemBO.findPJBens(id));
                    }
                }
            }
        } else if (index != null) {
            if (penhoraList.get(index).getSocioTipo() != null) {
                String tabela = penhoraList.get(index).getSocioTipo().substring(0, 2);
                Integer id = Integer.valueOf(penhoraList.get(index).getSocioTipo().substring(2));
                if (tabela.equals("PF")) {
                    penhoraList.get(index).setBemList(bemBO.findPFBens(id));
                } else if (tabela.equals("PJ")) {
                    penhoraList.get(index).setBemList(bemBO.findPJBens(id));
                }
            }
        }
    }

    public void listarSocios(String tipo) {
        PessoaFisicaJuridicaBO pfjBO = new PessoaFisicaJuridicaBO();
        PessoaJuridicaJuridicaBO pjjBO = new PessoaJuridicaJuridicaBO();
        socioList = new ArrayList<>();
        if (!initialLoad) {
            socioRedirecionamentoList = new ArrayList<>();
            oldSocioRedirecionamentoList = new ArrayList<>();
        }
        if (tipo.equals("PJ") && executadoPJ != null) {
            bemList = bemBO.findPJBens(Integer.valueOf(executadoPJ));
            List<PessoaFisicaJuridica> pfjList = pfjBO.findAllByPJ(Integer.valueOf(executadoPJ));
            for (PessoaFisicaJuridica pfj : pfjList) {
                socioList.add(pfj.getPessoaFisicaFk());
                if (!initialLoad) {
                    SocioRedirecionamento socioRedirecionamento = new SocioRedirecionamento((PessoaFisica) pfj.getPessoaFisicaFk(), new Redirecionamento());
                    socioRedirecionamentoList.add(socioRedirecionamento);
                    SocioRedirecionamento oldSocioRedirecionamento = new SocioRedirecionamento((PessoaFisica) pfj.getPessoaFisicaFk(), new Redirecionamento());
                    oldSocioRedirecionamentoList.add(oldSocioRedirecionamento);
                }
            }
            List<PessoaJuridicaJuridica> pjjList = pjjBO.findAllByPJAOrPJB(Integer.valueOf(executadoPJ));
            for (PessoaJuridicaJuridica pjj : pjjList) {
                if (pjj.getPessoaJuridicaPrimariaFk().getId().equals(Integer.valueOf(executadoPJ))) {
                    socioList.add(pjj.getPessoaJuridicaSecundariaFk());
                    if (!initialLoad) {
                        SocioRedirecionamento socioRedirecionamento = new SocioRedirecionamento((PessoaJuridica) pjj.getPessoaJuridicaSecundariaFk(), new Redirecionamento());
                        socioRedirecionamentoList.add(socioRedirecionamento);
                        SocioRedirecionamento oldSocioRedirecionamento = new SocioRedirecionamento((PessoaJuridica) pjj.getPessoaJuridicaSecundariaFk(), new Redirecionamento());
                        oldSocioRedirecionamentoList.add(oldSocioRedirecionamento);
                    }
                }
            }
        } else if (tipo.equals("PF") && executadoPF != null) {
            bemList = bemBO.findPFBens(Integer.valueOf(executadoPF));
            enderecosSocios = 0;
            enderecoSocioList = new ArrayList<>();
        }
        if (edit) {
            penhoraList = penhoraBO.findByPJUD(processoJudicial.getId());
            for (Penhora penhora : penhoraList) {
                if (penhora.getSocio() != null && penhora.getSocioFk() != null) {
                    penhora.setSocioTipo(penhora.getSocio() + penhora.getSocioFk());
                }
            }
        }
        if (!socioList.isEmpty()) {
            tipoPenhoraList = tipoPenhoraBO.findAll();
        } else {
            redirecionamento = null;
            tipoPenhoraList = tipoPenhoraBO.findPenhorasSemSocio();
            Iterator<Penhora> i = penhoraList.iterator();
            while (i.hasNext()) {
                Penhora penhora = i.next();
                if (penhora.getTipoPenhoraFk().getTipo().contains("Sócio")) {
                    i.remove();
                }
            }
        }
        initialLoad = false;
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
            executado = new Executado(processoJudicial, enderecoPessoaFisica, citacaoBO.findByPJUD(processoJudicial.getId()), carregarSocioRedirecionamento(redirecionamentoBO.findByPJUD(processoJudicial.getId())), penhoraBO.findByPJUD(processoJudicial.getId()));
        } else {
            PessoaJuridica pessoaJuridica = pessoaJuridicaBO.findPessoaJuridica(processoJudicial.getExecutadoFk());
            enderecoPessoaJuridica = new EnderecoPessoa(pessoaJuridica, enderecoBO.findPJAddress(pessoaJuridica.getId()), bemBO.findPJBens(pessoaJuridica.getId()));
            executado = new Executado(processoJudicial, enderecoPessoaJuridica, citacaoBO.findByPJUD(processoJudicial.getId()), carregarSocioRedirecionamento(redirecionamentoBO.findByPJUD(processoJudicial.getId())), penhoraBO.findByPJUD(processoJudicial.getId()));
        }
    }

    public String loadSocio(String tipo, String id) {
        if (tipo != null) {
            if (tipo.equals("PF")) {
                PessoaFisica pf = pessoaFisicaBO.findPessoaFisica(Integer.valueOf(id));
                String cpf = (pf.getCpf() == null ? "Sem CPF" : pf.getCpf().substring(0, 3) + "." + pf.getCpf().substring(3, 6) + "." + pf.getCpf().substring(6, 9) + "-" + pf.getCpf().substring(9));
                return cpf + " - " + pf.getNome();
            } else {
                PessoaJuridica pj = pessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(id));
                String cnpj = pj.getCnpj().substring(0, 2) + "." + pj.getCnpj().substring(2, 5) + "." + pj.getCnpj().substring(5, 8) + "/" + pj.getCnpj().substring(8, 12) + "-" + pj.getCnpj().substring(12);
                return cnpj + " - " + pj.getNome();
            }
        }
        return "-";
    }

    public int checkCitacaoVazia(List<Object> citacaoList, String tipo) {
        int quantidade = 0;
        for (Object citacao : citacaoList) {
            if (citacao instanceof Citacao) {
                Citacao c = (Citacao) citacao;
                quantidade = c.getTipoCitacao().equals(tipo) ? quantidade + 1 : quantidade;
            } else {
                CitacaoHistorico c = (CitacaoHistorico) citacao;
                quantidade = c.getTipoCitacao().equals(tipo) ? quantidade + 1 : quantidade;
            }
        }
        return quantidade;
    }

    public List<SocioRedirecionamento> carregarSocioRedirecionamento(List<Redirecionamento> redirecionamentoList) {
        redirecionamento = null;
        List<SocioRedirecionamento> socioRedirecionamentoList = new ArrayList<>();
        for (Redirecionamento redirecionamento : redirecionamentoList) {
            this.redirecionamento = redirecionamento.getRedirecionado();
            if (redirecionamento.getSocio().equals("PF")) {
                socioRedirecionamentoList.add(new SocioRedirecionamento(pessoaFisicaBO.findPessoaFisica(redirecionamento.getSocioFk()), redirecionamento));
            } else {
                socioRedirecionamentoList.add(new SocioRedirecionamento(pessoaJuridicaBO.findPessoaJuridica(redirecionamento.getSocioFk()), redirecionamento));
            }
        }
        return socioRedirecionamentoList;
    }

    public List<SocioRedirecionamentoHistorico> carregarSocioRedirecionamentoHistorico(List<RedirecionamentoHistorico> redirecionamentoHistoricoList) {
        List<SocioRedirecionamentoHistorico> socioRedirecionamentoHistoricoList = new ArrayList<>();
        for (RedirecionamentoHistorico redirecionamentoHistorico : redirecionamentoHistoricoList) {
            if (redirecionamentoHistorico.getSocio().equals("PF")) {
                socioRedirecionamentoHistoricoList.add(new SocioRedirecionamentoHistorico(pessoaFisicaBO.findPessoaFisica(redirecionamentoHistorico.getSocioFk()), redirecionamentoHistorico));
            } else {
                socioRedirecionamentoHistoricoList.add(new SocioRedirecionamentoHistorico(pessoaJuridicaBO.findPessoaJuridica(redirecionamentoHistorico.getSocioFk()), redirecionamentoHistorico));
            }
        }
        return socioRedirecionamentoHistoricoList;
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
        List<Citacao> citacaoList = new ArrayList<>();
        citacaoList.addAll(arList);
        citacaoList.addAll(oficialList);
        citacaoList.addAll(editalList);
        citacaoList.addAll(enderecoSocioList);
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
                for (Citacao c : citacaoList) {
                    if (c.getSocioTipo() != null) {
                        c.setSocio(c.getSocioTipo().substring(0, 2));
                        c.setSocioFk(Integer.valueOf(c.getSocioTipo().substring(2)));
                    }
                    c.setProcessoJudicialFk(processoJudicial);
                    citacaoBO.create(c);
                }
                for (SocioRedirecionamento sr : socioRedirecionamentoList) {
                    if (redirecionamento != null) {
                        sr.getRedirecionamento().setProcessoJudicialFk(processoJudicial);
                        sr.getRedirecionamento().setRedirecionado(redirecionamento);
                        if (sr.getPessoa() instanceof PessoaFisica) {
                            PessoaFisica pessoaFisica = (PessoaFisica) sr.getPessoa();
                            sr.getRedirecionamento().setSocioFk(pessoaFisica.getId());
                            sr.getRedirecionamento().setSocio("PF");
                        } else {
                            PessoaJuridica pessoaJuridica = (PessoaJuridica) sr.getPessoa();
                            sr.getRedirecionamento().setSocioFk(pessoaJuridica.getId());
                            sr.getRedirecionamento().setSocio("PJ");
                        }
                        redirecionamentoBO.create(sr.getRedirecionamento());
                    }
                }
                for (Penhora penhora : penhoraList) {
                    if (penhora.getSocioTipo() != null) {
                        penhora.setSocioFk(Integer.valueOf(penhora.getSocioTipo().substring(2)));
                        penhora.setSocio(penhora.getSocioTipo().substring(0, 2));
                    }
                    penhora.setProcessoJudicialFk(processoJudicial);
                    penhoraBO.create(penhora);
                }
                register = "success";
                GeradorLog.criar(processoJudicial.getId(), "PJUD", 'C');
                processoJudicial = new ProcessoJudicial();
                vinculoProcessual = new VinculoProcessual();
                vinculos = 0;
                socioRedirecionamentoList = new ArrayList<>();
                socioList = new ArrayList<>();
                arList = new ArrayList<>();
                oficialList = new ArrayList<>();
                editalList = new ArrayList<>();
                enderecoSocioList = new ArrayList<>();
                penhoraList = new ArrayList<>();
                tipoPenhora = new TipoPenhora();
                ars = 0;
                oficiais = 0;
                editais = 0;
                enderecosSocios = 0;
                executadoPF = "";
                executadoPJ = "";
                redirecionamento = null;
            } else { // CDA ou Processo já cadastrado
                error = true;
            }
        } else {
            /*  
             Alterar Pessoa Física existente
             */
            if ((pjudDBCDA == null || processoJudicial.equals(pjudDBCDA)) && (pjudDBProcess == null || processoJudicial.equals(pjudDBProcess))) {
                boolean identicalVP = true;
                if (identicalVP) {
                    if (oldProcessoJudicial.getVinculoProcessualCollection().size() != vinculoProcessualList.size()) {
                        identicalVP = false;
                    } else {
                        for (VinculoProcessual vinculoProcessual : vinculoProcessualList) {
                            for (VinculoProcessual oldVinculoProcessual : oldProcessoJudicial.getVinculoProcessualCollection()) {
                                if (vinculoProcessual.equalsValues(oldVinculoProcessual)) {
                                    identicalVP = true;
                                    break;
                                } else {
                                    identicalVP = false;
                                }
                            }
                            if (!identicalVP) {
                                break;
                            }
                        }
                    }
                }
                boolean identicalCitacao = true;
                if (identicalCitacao) {
                    if (oldCitacaoList.size() != citacaoList.size()) {
                        identicalCitacao = false;
                    } else {
                        for (Citacao citacao : citacaoList) {
                            for (Citacao oldCitacao : oldCitacaoList) {
                                if (citacao.equalsValues(oldCitacao)) {
                                    identicalCitacao = true;
                                    break;
                                } else {
                                    identicalCitacao = false;
                                }
                            }
                            if (!identicalCitacao) {
                                break;
                            }
                        }
                    }
                }
                if (redirecionamento != null) {
                    for (SocioRedirecionamento sr : socioRedirecionamentoList) {
                        sr.getRedirecionamento().setRedirecionado(redirecionamento);
                    }
                }
                boolean identicalRedirecionamento = true;
                if (identicalRedirecionamento) {
                    if (oldSocioRedirecionamentoList.size() != socioRedirecionamentoList.size()) {
                        identicalRedirecionamento = false;
                    } else {
                        for (SocioRedirecionamento SocioRedirecionamento : socioRedirecionamentoList) {
                            for (SocioRedirecionamento oldSocioRedirecionamento : oldSocioRedirecionamentoList) {
                                if (SocioRedirecionamento.equalsValues(oldSocioRedirecionamento)) {
                                    identicalRedirecionamento = true;
                                    break;
                                } else {
                                    identicalRedirecionamento = false;
                                }
                            }
                            if (!identicalRedirecionamento) {
                                break;
                            }
                        }
                    }
                }
                boolean identicalPenhora = true;
                if (identicalPenhora) {
                    if (oldPenhoraList.size() != penhoraList.size()) {
                        identicalPenhora = false;
                    } else {
                        for (Penhora penhora : penhoraList) {
                            for (Penhora oldPenhora : oldPenhoraList) {
                                if (penhora.equalsValues(oldPenhora)) {
                                    identicalPenhora = true;
                                    break;
                                } else {
                                    identicalPenhora = false;
                                }
                            }
                            if (!identicalPenhora) {
                                break;
                            }
                        }
                    }
                }
                if (oldProcessoJudicial.equalsValues(processoJudicial)
                        && identicalVP && identicalCitacao && identicalRedirecionamento && identicalPenhora) {
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
                    citacaoBO.destroyByPJUD(processoJudicial.getId());
                    for (Citacao citacao : citacaoList) {
                        if (citacao.getSocioTipo() != null) {
                            citacao.setSocio(citacao.getSocioTipo().substring(0, 2));
                            citacao.setSocioFk(Integer.valueOf(citacao.getSocioTipo().substring(2)));
                        }
                        citacao.setProcessoJudicialFk(processoJudicial);
                        citacaoBO.create(citacao);
                    }
                    redirecionamentoBO.destroyByPJUD(processoJudicial.getId());
                    for (SocioRedirecionamento sr : socioRedirecionamentoList) {
                        if (redirecionamento != null) {
                            sr.getRedirecionamento().setProcessoJudicialFk(processoJudicial);
                            if (sr.getPessoa() instanceof PessoaFisica) {
                                PessoaFisica pessoaFisica = (PessoaFisica) sr.getPessoa();
                                sr.getRedirecionamento().setSocioFk(pessoaFisica.getId());
                                sr.getRedirecionamento().setSocio("PF");
                            } else {
                                PessoaJuridica pessoaJuridica = (PessoaJuridica) sr.getPessoa();
                                sr.getRedirecionamento().setSocioFk(pessoaJuridica.getId());
                                sr.getRedirecionamento().setSocio("PJ");
                            }
                            redirecionamentoBO.create(sr.getRedirecionamento());
                        }
                    }
                    penhoraBO.destroyByPJUD(processoJudicial.getId());
                    for (Penhora penhora : penhoraList) {
                        if (penhora.getSocioTipo() != null) {
                            penhora.setSocio(penhora.getSocioTipo().substring(0, 2));
                            penhora.setSocioFk(Integer.valueOf(penhora.getSocioTipo().substring(2)));
                        }
                        penhora.setProcessoJudicialFk(processoJudicial);
                        penhoraBO.create(penhora);
                    }
                    for (VinculoProcessualHistorico vph : vinculoProcessualHistoricoList) {
                        vph.setProcessoJudicialHistoricoFk(processoJudicialHistorico);
                        vinculoProcessualHistoricoBO.create(vph);
                    }
                    for (CitacaoHistorico ch : citacaoHistoricoList) {
                        ch.setProcessoJudicialHistoricoFk(processoJudicialHistorico);
                        citacaoHistoricoBO.create(ch);
                    }
                    for (RedirecionamentoHistorico rh : redirecionamentoHistoricoList) {
                        rh.setProcessoJudicialHistoricoFk(processoJudicialHistorico);
                        redirecionamentoHistoricoBO.create(rh);
                    }
                    for (PenhoraHistorico ph : penhoraHistoricoList) {
                        ph.setProcessoJudicialHistoricoFk(processoJudicialHistorico);
                        penhoraHistoricoBO.create(ph);
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

    public void prepararHistorico(ProcessoJudicial processoJudicial, List<Citacao> citacaoList, List<SocioRedirecionamento> socioRedirecionamentoList, List<Penhora> penhoraList) {
        /*
         Montar entidades dos históricos de alteração 
         */
        processoJudicialHistorico = new ProcessoJudicialHistorico();
        vinculoProcessualHistoricoList = new ArrayList<>();
        citacaoHistoricoList = new ArrayList<>();
        redirecionamentoHistoricoList = new ArrayList<>();
        penhoraHistoricoList = new ArrayList<>();

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

        for (Citacao citacao : citacaoList) {
            CitacaoHistorico ch = new CitacaoHistorico();
            ch.setCitado(citacao.getCitado());
            ch.setDataDaCitacao(citacao.getDataDaCitacao());
            ch.setEndereco(citacao.getEndereco());
            ch.setMotivo(citacao.getMotivo());
            ch.setSocio(citacao.getSocio());
            ch.setSocioFk(citacao.getSocioFk());
            ch.setTipoCitacao(citacao.getTipoCitacao());
            citacaoHistoricoList.add(ch);
        }

        for (SocioRedirecionamento socioRedirecionamento : socioRedirecionamentoList) {
            RedirecionamentoHistorico rh = new RedirecionamentoHistorico();
            rh.setDataDeRedirecionamento(socioRedirecionamento.getRedirecionamento().getDataDeRedirecionamento());
            rh.setRedirecionado(socioRedirecionamento.getRedirecionamento().getRedirecionado());
            rh.setSocio(socioRedirecionamento.getRedirecionamento().getSocio());
            rh.setSocioFk(socioRedirecionamento.getRedirecionamento().getSocioFk());
            redirecionamentoHistoricoList.add(rh);
        }

        for (Penhora penhora : penhoraList) {
            PenhoraHistorico ph = new PenhoraHistorico();
            ph.setBemFk(penhora.getBemFk());
            ph.setDataDaPenhora(penhora.getDataDaPenhora());
            ph.setEndereco(penhora.getEndereco());
            ph.setSituacao(penhora.getSituacao());
            ph.setSocio(penhora.getSocio());
            ph.setSocioFk(penhora.getSocioFk());
            ph.setTipoPenhoraFk(penhora.getTipoPenhoraFk());
            ph.setValor(penhora.getValor());
            penhoraHistoricoList.add(ph);
        }
    }

    private ExecutadoHistorico prepararRegistroAtual(ProcessoJudicial processoJudicial, EnderecoPessoa enderecoPessoa, List<Citacao> citacaoList, List<SocioRedirecionamento> socioRedirecionamentoList, List<Penhora> penhoraList) {
        /*
         Montar registro atual como uma entidade de histórico para facilitar o ui:repeat do form
         */
        ExecutadoHistorico executadoHistorico = new ExecutadoHistorico();
        processoJudicialHistorico = new ProcessoJudicialHistorico();
        vinculoProcessualHistoricoList = new ArrayList<>();
        citacaoHistoricoList = new ArrayList<>();
        List<SocioRedirecionamentoHistorico> socioRedirecionamentoHistoricoList = new ArrayList<>();
        penhoraHistoricoList = new ArrayList<>();

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

        for (Citacao citacao : citacaoList) {
            CitacaoHistorico ch = new CitacaoHistorico();
            ch.setCitado(citacao.getCitado());
            ch.setDataDaCitacao(citacao.getDataDaCitacao());
            ch.setEndereco(citacao.getEndereco());
            ch.setMotivo(citacao.getMotivo());
            ch.setSocio(citacao.getSocio());
            ch.setSocioFk(citacao.getSocioFk());
            ch.setTipoCitacao(citacao.getTipoCitacao());
            citacaoHistoricoList.add(ch);
        }

        for (SocioRedirecionamento socioRedirecionamento : socioRedirecionamentoList) {
            RedirecionamentoHistorico rh = new RedirecionamentoHistorico();
            rh.setDataDeRedirecionamento(socioRedirecionamento.getRedirecionamento().getDataDeRedirecionamento());
            rh.setRedirecionado(socioRedirecionamento.getRedirecionamento().getRedirecionado());
            rh.setSocio(socioRedirecionamento.getRedirecionamento().getSocio());
            rh.setSocioFk(socioRedirecionamento.getRedirecionamento().getSocioFk());
            socioRedirecionamentoHistoricoList.add(new SocioRedirecionamentoHistorico(socioRedirecionamento.getPessoa(), rh));
        }

        for (Penhora penhora : penhoraList) {
            PenhoraHistorico ph = new PenhoraHistorico();
            ph.setBemFk(penhora.getBemFk());
            ph.setDataDaPenhora(penhora.getDataDaPenhora());
            ph.setEndereco(penhora.getEndereco());
            ph.setSituacao(penhora.getSituacao());
            ph.setSocio(penhora.getSocio());
            ph.setSocioFk(penhora.getSocioFk());
            ph.setTipoPenhoraFk(penhora.getTipoPenhoraFk());
            ph.setValor(penhora.getValor());
            penhoraHistoricoList.add(ph);
        }

        executadoHistorico.setProcessoJudicialHistorico(processoJudicialHistorico);
        if (processoJudicial.getExecutado().equals("PF")) {
            executadoHistorico.setEnderecoPessoaFisica(enderecoPessoa);
        } else {
            executadoHistorico.setEnderecoPessoaJuridica(enderecoPessoa);
        }
        executadoHistorico.setPenhoraHistoricoList(penhoraHistoricoList);
        executadoHistorico.setCitacaoHistoricoList(citacaoHistoricoList);
        executadoHistorico.setSocioRedirecionamentoHistoricoList(socioRedirecionamentoHistoricoList);
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

    public List<Object> getSocioList() {
        return socioList;
    }

    public void setSocioList(List<Object> socioList) {
        this.socioList = socioList;
    }

    public List<Penhora> getPenhoraList() {
        return penhoraList;
    }

    public void setPenhoraList(List<Penhora> penhoraList) {
        this.penhoraList = penhoraList;
    }

    public List<Bem> getBemList() {
        return bemList;
    }

    public void setBemList(List<Bem> bemList) {
        this.bemList = bemList;
    }

}
