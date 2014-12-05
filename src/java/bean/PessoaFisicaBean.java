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
import bo.EstadoCivilBO;
import bo.FuncaoBO;
import bo.NacionalidadeBO;
import bo.PaisBO;
import bo.PessoaFisicaBO;
import bo.PessoaFisicaFisicaBO;
import bo.PessoaFisicaFisicaHistoricoBO;
import bo.PessoaFisicaHistoricoBO;
import bo.PessoaFisicaJuridicaBO;
import bo.PessoaFisicaJuridicaHistoricoBO;
import bo.PessoaJuridicaBO;
import bo.ProcessoJudicialBO;
import bo.TipoBemBO;
import bo.UsuarioBO;
import bo.UtilBO;
import bo.VinculoSocialBO;
import entidade.Bem;
import entidade.BemHistorico;
import entidade.Cidade;
import entidade.Endereco;
import entidade.EnderecoHistorico;
import entidade.EnderecoPessoa;
import entidade.EnderecoPessoaFisicaJuridicaHistorico;
import entidade.Estado;
import entidade.EstadoCivil;
import entidade.Funcao;
import entidade.Instituicao;
import entidade.Nacionalidade;
import entidade.Pais;
import entidade.PessoaFisica;
import entidade.PessoaFisicaFisica;
import entidade.PessoaFisicaFisicaHistorico;
import entidade.PessoaFisicaHistorico;
import entidade.PessoaFisicaJuridica;
import entidade.PessoaFisicaJuridicaHistorico;
import entidade.PessoaJuridica;
import entidade.ProcessoJudicial;
import entidade.TipoBem;
import entidade.VinculoSocial;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import util.Cookie;
import util.GeradorLog;
import util.MetodosConvencionais;

/**
 *
 * @author paulones
 */
@ViewScoped
@ManagedBean(name = "pessoaFisicaBean")
public class PessoaFisicaBean implements Serializable{

    private PessoaFisica pessoaFisica;
    private PessoaFisica oldPessoaFisica;
    private Endereco endereco;
    private Endereco oldEndereco;
    private EnderecoPessoa enderecoPessoa;
    private PessoaFisicaHistorico pessoaFisicaHistorico;
    private EnderecoHistorico EnderecoHistorico;
    private Bem bem; 

    private String register;
    private String redirect;
    private boolean edit;
    private boolean history;
    private String pfId;
    private String pjId;

    private List<Pais> paisList;
    private List<Estado> estadoList;
    private List<Cidade> cidadeNatList;
    private List<Cidade> cidadeEndList;
    private List<Cidade> cidadeEleList;
    private List<Nacionalidade> nacionalidadeList;
    private List<EstadoCivil> estadoCivilList;
    private List<PessoaJuridica> pessoaJuridicaList;
    private List<PessoaFisicaJuridica> pessoaFisicaJuridicaList;
    private List<Bem> bemList;
    private List<Funcao> funcaoList;
    private List<Bem> oldBemList;
    private List<PessoaFisicaJuridica> oldPessoaFisicaJuridicaList;
    private List<PessoaFisicaFisica> oldPessoaFisicaFisicaList;
    private List<PessoaFisicaHistorico> pessoaFisicaHistoricoList;
    private List<PessoaFisicaFisica> pessoaFisicaFisicaList;
    private List<PessoaFisicaFisicaHistorico> pessoaFisicaFisicaHistoricoList;
    private List<EnderecoHistorico> enderecoHistoricoList;
    private List<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoList;
    private List<EnderecoPessoaFisicaJuridicaHistorico> enderecoPessoaFisicaJuridicaHistoricoList;
    private List<BemHistorico> bemHistoricoList;
    private List<ProcessoJudicial> processoJudicialList;
    private List<VinculoSocial> vinculoSocialList;
    private List<TipoBem> tipoBemList;

    public void init() throws IOException {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            boolean isRegisterPage = FacesContext.getCurrentInstance().getViewRoot().getViewId().lastIndexOf("cadastrar") > -1;
            boolean isSearchPage = FacesContext.getCurrentInstance().getViewRoot().getViewId().lastIndexOf("consultar") > -1;

            endereco = new Endereco();
            bem = new Bem();
            enderecoPessoa = new EnderecoPessoa();

            register = "";
            redirect = Cookie.getCookie("FacesMessage");
            Cookie.apagarCookie("FacesMessage");

            cidadeNatList = new ArrayList<>();
            cidadeEndList = new ArrayList<>();
            cidadeEleList = new ArrayList<>();
            pessoaFisicaJuridicaList = new ArrayList<>();
            pessoaFisicaFisicaList = new ArrayList<>();
            bemList = new ArrayList<>();

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            pessoaFisica = new PessoaFisica();
            if (isRegisterPage) {
                /*
                 Tela cadastro.xhtml. Se houver "id" na url, entra na condição de alteração.
                 Caso contrário, apenas carrega o formulário
                 */
                pjId = "";
                if (request.getParameter("id") == null) {   // Novo
                    edit = false;
                    carregarFormulario();
                } else {                                    // Alteração
                    try {
                        Integer id = Integer.valueOf(request.getParameter("id"));
                        pessoaFisica = PessoaFisicaBO.findPessoaFisica(id);
                        if (pessoaFisica == null) {
                            FacesContext.getCurrentInstance().getExternalContext().redirect("cadastrar.xhtml");
                        } else {
                            edit = true;
                            endereco = EnderecoBO.findPFAddress(id);
                            pessoaFisicaFisicaList = PessoaFisicaFisicaBO.findAllByPFAOrPFB(id);
                            pessoaFisicaJuridicaList = PessoaFisicaJuridicaBO.findAllByPF(id);
                            bemList = BemBO.findPFBens(id);

                            oldPessoaFisica = PessoaFisicaBO.findPessoaFisica(id);
                            oldEndereco = EnderecoBO.findPFAddress(id);
                            oldBemList = BemBO.findPFBens(id);
                            oldPessoaFisicaJuridicaList = PessoaFisicaJuridicaBO.findAllByPF(id);
                            oldPessoaFisicaFisicaList = PessoaFisicaFisicaBO.findAllByPFAOrPFB(id);
                            prepararHistorico(pessoaFisica, endereco, pessoaFisicaJuridicaList, pessoaFisicaFisicaList, bemList);

                            carregarFormulario();
                            getCidadesPeloEstado();
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
                    pfId = "";
                    history = false;
                } else {                                    // Consulta histórico
                    try {
                        Integer id = Integer.valueOf(request.getParameter("id"));
                        pessoaFisica = PessoaFisicaBO.findPessoaFisica(id);
                        if (pessoaFisica == null) {
                            history = false;
                            FacesContext.getCurrentInstance().getExternalContext().redirect("consultar.xhtml");
                        } else {
                            history = true;
                            endereco = EnderecoBO.findPFAddress(id);
                            bemList = BemBO.findPFBens(id);
                            pessoaFisicaFisicaList = PessoaFisicaFisicaBO.findAllByPFAOrPFB(id);
                            pessoaFisicaJuridicaList = PessoaFisicaJuridicaBO.findAllByPF(id);

                            pessoaFisicaHistoricoList = new ArrayList<>();
                            enderecoHistoricoList = new ArrayList<>();
                            bemHistoricoList = new ArrayList<>();
                            pessoaFisicaFisicaHistoricoList = new ArrayList<>();
                            pessoaFisicaJuridicaHistoricoList = new ArrayList<>();

                            pessoaFisicaHistoricoList = PessoaFisicaHistoricoBO.findAllByPF(id);
                            enderecoHistoricoList = EnderecoHistoricoBO.findAllByPF(id);
                            bemHistoricoList = BemHistoricoBO.findAllByPF(id);
                            pessoaFisicaJuridicaHistoricoList = PessoaFisicaJuridicaHistoricoBO.findAllByPF(id);

                            enderecoPessoaFisicaJuridicaHistoricoList = new ArrayList<>();
                            EnderecoPessoaFisicaJuridicaHistorico enderecoPessoaFisicaJuridicaHistorico = new EnderecoPessoaFisicaJuridicaHistorico();
                            enderecoPessoaFisicaJuridicaHistorico = prepararRegistroAtual(pessoaFisica, endereco, pessoaFisicaJuridicaList, pessoaFisicaFisicaList, bemList);
                            enderecoPessoaFisicaJuridicaHistoricoList.add(enderecoPessoaFisicaJuridicaHistorico);
                            for (PessoaFisicaHistorico pfh : pessoaFisicaHistoricoList) {
                                for (EnderecoHistorico eh : enderecoHistoricoList) {
                                    if (pfh.getId() == eh.getIdFk()) {
                                        EnderecoPessoaFisicaJuridicaHistorico epfjh = new EnderecoPessoaFisicaJuridicaHistorico(pfh, eh);
                                        List<PessoaFisicaJuridicaHistorico> pfjhList = new ArrayList<>();
                                        for (PessoaFisicaJuridicaHistorico pfjh : pessoaFisicaJuridicaHistoricoList) {
                                            if (pfh.getId() == pfjh.getIdFk()) {
                                                pfjhList.add(pfjh);
                                            }
                                        }
                                        List<BemHistorico> bhList = new ArrayList<>();
                                        for (BemHistorico bh : bemHistoricoList) {
                                            if (pfh.getId() == bh.getIdFk()) {
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
                    } catch (Exception e) {
                        history = false;
                        FacesContext.getCurrentInstance().getExternalContext().redirect("consultar.xhtml");
                    }
                }
            }
        }
    }

    private void carregarFormulario() { // Carregar listas do formulário
        paisList = PaisBO.findAll();
        paisList.remove(PaisBO.findBrasil());
        estadoList = EstadoBO.findAll();
        nacionalidadeList = NacionalidadeBO.findAll();
        estadoCivilList = EstadoCivilBO.findAll();
        UsuarioBO usuarioBO = new UsuarioBO();
        Instituicao instituicao = usuarioBO.findAutorizacaoByCPF(Cookie.getCookie("usuario")).getInstituicaoFk();
        pessoaJuridicaList = PessoaJuridicaBO.findAllActive(instituicao);
        funcaoList = FuncaoBO.findAll();
        vinculoSocialList = VinculoSocialBO.findAll();
        tipoBemList = TipoBemBO.findAll();
    }

    public void getCidadesPeloEstado() { // Renderizar cidades baseado no estado escolhido
        if (pessoaFisica.getEstadoFk() != null) {
            cidadeNatList = CidadeBO.getByStateId(pessoaFisica.getEstadoFk().getId());
        } else {
            cidadeNatList.clear();
        }
        if (endereco.getEstadoFk() != null) {
            cidadeEndList = CidadeBO.getByStateId(endereco.getEstadoFk().getId());
        } else {
            cidadeEndList.clear();
        }
        if (pessoaFisica.getEstadoEleitoralFk() != null) {
            cidadeEleList = CidadeBO.getByStateId(pessoaFisica.getEstadoEleitoralFk().getId());
        } else {
            cidadeEleList.clear();
        }
    }

    public void cadastrar() throws IOException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, IllegalArgumentException, NoSuchMethodException {
        UsuarioBO usuarioBO = new UsuarioBO();
        boolean error = false;
        PessoaFisica pfDB = PessoaFisicaBO.findByCPF(pessoaFisica.getCpf());
        if (!edit) {
            /*  
             Cadastrar nova Pessoa Física
             */
            if (pfDB == null || pessoaFisica.getCpf().isEmpty()) { // CPF novo
                if (pessoaFisica.getRgOrgaoEmissor() != null) {
                    pessoaFisica.setRgOrgaoEmissor(pessoaFisica.getRgOrgaoEmissor().toUpperCase());
                }
                if (pessoaFisica.getEstadoFk() != null) {
                    pessoaFisica.setPaisFk(PaisBO.findBrasil());
                }
                pessoaFisica.setStatus('A');
                pessoaFisica.setUsuarioFk(usuarioBO.findUsuarioByCPF(Cookie.getCookie("usuario")));
                pessoaFisica.setInstituicaoFk(usuarioBO.findAutorizacaoByCPF(Cookie.getCookie("usuario")).getInstituicaoFk());
                PessoaFisicaBO.create(pessoaFisica);
                endereco.setTipo("PF");
                endereco.setIdFk(pessoaFisica.getId());
                EnderecoBO.create(endereco);
                for (PessoaFisicaJuridica pfj : pessoaFisicaJuridicaList) {
                    pfj.setPessoaFisicaFk(pessoaFisica);
                    PessoaFisicaJuridicaBO.create(pfj);
                }
                for (PessoaFisicaFisica pff : pessoaFisicaFisicaList) {
                    pff.setPessoaFisicaPrimariaFk(pessoaFisica);
                    PessoaFisicaFisicaBO.create(pff);
                }
                for (Bem bem : bemList) {
                    bem.setTipo("PF");
                    bem.setIdFk(pessoaFisica.getId());
                    BemBO.create(bem);
                }
                register = "success";
                pjId = "";
                GeradorLog.criar(pessoaFisica.getId(), "PF", 'C');
                pessoaFisica = new PessoaFisica();
                endereco = new Endereco();
                pessoaFisicaJuridicaList = new ArrayList<>();
                pessoaFisicaFisicaList = new ArrayList<>();
                bemList = new ArrayList<>();
            } else { // CPF previamente cadastrado
                error = true;
            }
        } else {
            /*  
             Alterar Pessoa Física existente
             */
            if (pfDB == null || pessoaFisica.equals(pfDB)) {
                if (pessoaFisica.getRgOrgaoEmissor() != null) {
                    pessoaFisica.setRgOrgaoEmissor(pessoaFisica.getRgOrgaoEmissor().toUpperCase());
                }
                if (pessoaFisica.getEstadoFk() != null) {
                    pessoaFisica.setPaisFk(PaisBO.findBrasil());
                }
                boolean identicalPfj = MetodosConvencionais.checarListasIguais("PessoaFisicaJuridica", pessoaFisicaJuridicaList, oldPessoaFisicaJuridicaList);
                boolean identicalPff = MetodosConvencionais.checarListasIguais("PessoaFisicaFisica", pessoaFisicaFisicaList, oldPessoaFisicaFisicaList);
                boolean identicalBem = MetodosConvencionais.checarListasIguais("Bem", bemList, oldBemList);
                if (oldPessoaFisica.changedValues(pessoaFisica).isEmpty()
                        && oldEndereco.changedValues(endereco).isEmpty()
                        && identicalPfj && identicalPff && identicalBem) {
                    Cookie.addCookie("FacesMessage", "fail", 10);
                    FacesContext.getCurrentInstance().getExternalContext().redirect("consultar.xhtml");
                } else {
                    UtilBO utilBO = new UtilBO();
                    Timestamp timestamp = utilBO.findServerTime();
                    pessoaFisica.setUsuarioFk(usuarioBO.findUsuarioByCPF(Cookie.getCookie("usuario")));
                    PessoaFisicaBO.edit(pessoaFisica);
                    pessoaFisicaHistorico.setDataDeModificacao(timestamp);
                    PessoaFisicaHistoricoBO.create(pessoaFisicaHistorico);
                    EnderecoBO.edit(endereco);
                    EnderecoHistorico.setIdFk(pessoaFisicaHistorico.getId());
                    EnderecoHistoricoBO.create(EnderecoHistorico);
                    PessoaFisicaJuridicaBO.destroyByPF(pessoaFisica.getId());
                    for (PessoaFisicaJuridica pfj : pessoaFisicaJuridicaList) {
                        PessoaFisicaJuridicaBO.create(pfj);
                    }
                    PessoaFisicaFisicaBO.destroyByPFBOrPFA(pessoaFisica.getId());
                    for (PessoaFisicaFisica pff : pessoaFisicaFisicaList) {
                        PessoaFisicaFisicaBO.create(pff);
                    }
                    for (Bem b : bemList) {
                        if (b.getId() == null) {
                            b.setTipo("PF");
                            b.setIdFk(pessoaFisica.getId());
                            BemBO.create(b);
                        } else {
                            BemBO.edit(b);
                        }
                    }
                    for (PessoaFisicaJuridicaHistorico pfjh : pessoaFisicaJuridicaHistoricoList) {
                        pfjh.setTipo("PF");
                        pfjh.setIdFk(pessoaFisicaHistorico.getId());
                        PessoaFisicaJuridicaHistoricoBO.create(pfjh);
                    }
                    for (PessoaFisicaFisicaHistorico pffh : pessoaFisicaFisicaHistoricoList) {
                        pffh.setPessoaFisicaHistoricoFk(pessoaFisicaHistorico);
                        PessoaFisicaFisicaHistoricoBO.create(pffh);
                    }
                    for (BemHistorico bh : bemHistoricoList) {
                        bh.setIdFk(pessoaFisicaHistorico.getId());
                        BemHistoricoBO.create(bh);
                    }
                    GeradorLog.criar(pessoaFisica.getId(), "PF", 'U');
                    Cookie.addCookie("FacesMessage", "success", 10);
                    FacesContext.getCurrentInstance().getExternalContext().redirect("consultar.xhtml");
                }
            } else { // CPF previamente cadastrado
                error = true;
            }
        }
        if (error) {
            register = "fail";
            String cpf = pfDB.getCpf().substring(0, 3) + "." + pfDB.getCpf().substring(3, 6) + "." + pfDB.getCpf().substring(6, 9) + "-" + pfDB.getCpf().substring(9);
            String message = "Já existe usuário cadastrado com o CPF " + cpf;
            message += pfDB.getNome() != null ? "\nNome: " + pfDB.getNome() : "";
            message += pfDB.getRg() != null ? "\nRG: " + pfDB.getRg() : "";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }
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

    public void exibirInfo() {
        pessoaFisica = PessoaFisicaBO.findPessoaFisica(Integer.valueOf(pfId));
        endereco = EnderecoBO.findPFAddress(pessoaFisica.getId());
        bemList = BemBO.findPFBens(pessoaFisica.getId());
        enderecoPessoa = new EnderecoPessoa(pessoaFisica, endereco, bemList);
        processoJudicialList = ProcessoJudicialBO.findByExecutado(pfId, "PF");
    }

    public void removerVinculo(int index) {
        pessoaFisicaJuridicaList.remove(index);
    }

    public void removerVinculoSocial(int index) {
        pessoaFisicaFisicaList.remove(index);
    }

    public void removerPessoaFisica() {
        pessoaFisica = PessoaFisicaBO.findPessoaFisica(Integer.valueOf(pfId));
        endereco = EnderecoBO.findPFAddress(pessoaFisica.getId());
        pessoaFisica.setStatus('I');
        PessoaFisicaBO.edit(pessoaFisica);
        GeradorLog.criar(pessoaFisica.getId(), "PF", 'D');
        redirect = "";
        register = "success";
    }

    public void vincularPessoaJuridica() {
        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        PessoaFisicaJuridica pessoaFisicaJuridica = new PessoaFisicaJuridica();
        if (edit) {
            pessoaFisicaJuridica.setPessoaFisicaFk(pessoaFisica);
        }
        pessoaJuridica = PessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(pjId));
        pessoaFisicaJuridica.setPessoaJuridicaFk(pessoaJuridica);
        boolean exists = false;
        for (PessoaFisicaJuridica pfj : pessoaFisicaJuridicaList) {
            if (pfj.getPessoaJuridicaFk().getCnpj().equals(pessoaJuridica.getCnpj())) {
                exists = true;
            }
        }
        if (!exists) {
            pessoaFisicaJuridicaList.add(pessoaFisicaJuridica);
        }
    }

    public void vincularPessoaFisica() {
        PessoaFisicaFisica pessoaFisicaFisica = new PessoaFisicaFisica();
        if (edit) {
            pessoaFisicaFisica.setPessoaFisicaPrimariaFk(pessoaFisica);
        }
        PessoaFisica pessoaFisicaVinculo = PessoaFisicaBO.findPessoaFisica(Integer.valueOf(pfId));
        pessoaFisicaFisica.setPessoaFisicaSecundariaFk(pessoaFisicaVinculo);
        boolean exists = false;
        for (PessoaFisicaFisica pff : pessoaFisicaFisicaList) {
            if ((pff.getPessoaFisicaSecundariaFk() != null && pff.getPessoaFisicaSecundariaFk().getId().equals(pessoaFisicaVinculo.getId()))
                    || (pff.getPessoaFisicaPrimariaFk() != null && pff.getPessoaFisicaPrimariaFk().getId().equals(pessoaFisicaVinculo.getId()))) {
                exists = true;
            }
        }
        if (!exists) {
            pessoaFisicaFisicaList.add(pessoaFisicaFisica);
        }
    }

    public void prepararHistorico(PessoaFisica pessoaFisica, Endereco endereco, List<PessoaFisicaJuridica> pessoaFisicaJuridicaList, List<PessoaFisicaFisica> pessoaFisicaFisicaList, List<Bem> bemList) {
        /*
         Montar entidades dos históricos de alteração 
         */
        pessoaFisicaHistorico = new PessoaFisicaHistorico();
        EnderecoHistorico = new EnderecoHistorico();
        pessoaFisicaFisicaHistoricoList = new ArrayList<>();
        pessoaFisicaJuridicaHistoricoList = new ArrayList<>();
        bemHistoricoList = new ArrayList<>();

        pessoaFisicaHistorico.setApelido(pessoaFisica.getApelido());
        pessoaFisicaHistorico.setCidadeFk(pessoaFisica.getCidadeFk());
        pessoaFisicaHistorico.setCpf(pessoaFisica.getCpf());
        pessoaFisicaHistorico.setEstadoCivilFk(pessoaFisica.getEstadoCivilFk());
        pessoaFisicaHistorico.setEstadoFk(pessoaFisica.getEstadoFk());
        pessoaFisicaHistorico.setInss(pessoaFisica.getInss());
        pessoaFisicaHistorico.setNacionalidadeFk(pessoaFisica.getNacionalidadeFk());
        pessoaFisicaHistorico.setNome(pessoaFisica.getNome());
        pessoaFisicaHistorico.setNomeDaMae(pessoaFisica.getNomeDaMae());
        pessoaFisicaHistorico.setNomeDoConjuge(pessoaFisica.getNomeDoConjuge());
        pessoaFisicaHistorico.setNomeDoPai(pessoaFisica.getNomeDoPai());
        pessoaFisicaHistorico.setObservacoes(pessoaFisica.getObservacoes());
        pessoaFisicaHistorico.setPaisFk(pessoaFisica.getPaisFk());
        pessoaFisicaHistorico.setPessoaFisicaFk(pessoaFisica);
        pessoaFisicaHistorico.setRg(pessoaFisica.getRg());
        pessoaFisicaHistorico.setRgOrgaoEmissor(pessoaFisica.getRgOrgaoEmissor());
        pessoaFisicaHistorico.setRgUfFk(pessoaFisica.getRgUfFk());
        pessoaFisicaHistorico.setSexo(pessoaFisica.getSexo());
        pessoaFisicaHistorico.setTituloDeEleitor(pessoaFisica.getTituloDeEleitor());
        pessoaFisicaHistorico.setUsuarioFk(pessoaFisica.getUsuarioFk());
        pessoaFisicaHistorico.setZona(pessoaFisica.getZona());
        pessoaFisicaHistorico.setSecao(pessoaFisica.getSecao());
        pessoaFisicaHistorico.setLocal(pessoaFisica.getLocal());
        pessoaFisicaHistorico.setCidadeEleitoralFk(pessoaFisica.getCidadeEleitoralFk());
        pessoaFisicaHistorico.setEstadoEleitoralFk(pessoaFisica.getEstadoEleitoralFk());
        pessoaFisicaHistorico.setEndereco(pessoaFisica.getEndereco());

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

        for (PessoaFisicaFisica pff : pessoaFisicaFisicaList) {
            PessoaFisicaFisicaHistorico pffh = new PessoaFisicaFisicaHistorico();
            pffh.setPessoaFisicaPrimariaFk(pff.getPessoaFisicaPrimariaFk());
            pffh.setPessoaFisicaSecundariaFk(pff.getPessoaFisicaSecundariaFk());
            pffh.setVinculoSocialFk(pff.getVinculoSocialFk());
            pessoaFisicaFisicaHistoricoList.add(pffh);
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

    private EnderecoPessoaFisicaJuridicaHistorico prepararRegistroAtual(PessoaFisica pessoaFisica, Endereco endereco, List<PessoaFisicaJuridica> pessoaFisicaJuridicaList, List<PessoaFisicaFisica> pessoaFisicaFisicaList, List<Bem> bemList) {
        /*
         Montar registro atual como uma entidade de histórico para facilitar o ui:repeat do form
         */
        EnderecoPessoaFisicaJuridicaHistorico enderecoPessoaFisicaJuridicaHistorico = new EnderecoPessoaFisicaJuridicaHistorico();
        PessoaFisicaHistorico pessoaFisicaHistorico = new PessoaFisicaHistorico();
        EnderecoHistorico enderecoHistorico = new EnderecoHistorico();
        List<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoList = new ArrayList<>();
        List<PessoaFisicaFisicaHistorico> pessoaFisicaFisicaHistoricoList = new ArrayList<>();
        List<BemHistorico> bemHistoricoList = new ArrayList<>();

        pessoaFisicaHistorico.setApelido(pessoaFisica.getApelido());
        pessoaFisicaHistorico.setCidadeFk(pessoaFisica.getCidadeFk());
        pessoaFisicaHistorico.setCpf(pessoaFisica.getCpf());
        pessoaFisicaHistorico.setEstadoCivilFk(pessoaFisica.getEstadoCivilFk());
        pessoaFisicaHistorico.setEstadoFk(pessoaFisica.getEstadoFk());
        pessoaFisicaHistorico.setInss(pessoaFisica.getInss());
        pessoaFisicaHistorico.setNacionalidadeFk(pessoaFisica.getNacionalidadeFk());
        pessoaFisicaHistorico.setNome(pessoaFisica.getNome());
        pessoaFisicaHistorico.setNomeDaMae(pessoaFisica.getNomeDaMae());
        pessoaFisicaHistorico.setNomeDoConjuge(pessoaFisica.getNomeDoConjuge());
        pessoaFisicaHistorico.setNomeDoPai(pessoaFisica.getNomeDoPai());
        pessoaFisicaHistorico.setObservacoes(pessoaFisica.getObservacoes());
        pessoaFisicaHistorico.setPaisFk(pessoaFisica.getPaisFk());
        pessoaFisicaHistorico.setRg(pessoaFisica.getRg());
        pessoaFisicaHistorico.setRgOrgaoEmissor(pessoaFisica.getRgOrgaoEmissor());
        pessoaFisicaHistorico.setRgUfFk(pessoaFisica.getRgUfFk());
        pessoaFisicaHistorico.setSexo(pessoaFisica.getSexo());
        pessoaFisicaHistorico.setTituloDeEleitor(pessoaFisica.getTituloDeEleitor());
        pessoaFisicaHistorico.setUsuarioFk(pessoaFisica.getUsuarioFk());
        pessoaFisicaHistorico.setZona(pessoaFisica.getZona());
        pessoaFisicaHistorico.setSecao(pessoaFisica.getSecao());
        pessoaFisicaHistorico.setLocal(pessoaFisica.getLocal());
        pessoaFisicaHistorico.setCidadeEleitoralFk(pessoaFisica.getCidadeEleitoralFk());
        pessoaFisicaHistorico.setEstadoEleitoralFk(pessoaFisica.getEstadoEleitoralFk());
        pessoaFisicaHistorico.setEndereco(pessoaFisica.getEndereco());
        pessoaFisicaHistorico.setPessoaFisicaFk(pessoaFisica);

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

        for (PessoaFisicaFisica pff : pessoaFisicaFisicaList) {
            PessoaFisicaFisicaHistorico pffh = new PessoaFisicaFisicaHistorico();
            pffh.setPessoaFisicaPrimariaFk(pff.getPessoaFisicaPrimariaFk());
            pffh.setPessoaFisicaSecundariaFk(pff.getPessoaFisicaSecundariaFk());
            pffh.setVinculoSocialFk(pff.getVinculoSocialFk());
            pessoaFisicaFisicaHistoricoList.add(pffh);
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

        pessoaFisicaHistorico.setPessoaFisicaFisicaHistoricoCollection(pessoaFisicaFisicaHistoricoList);
        enderecoPessoaFisicaJuridicaHistorico.setPessoaHistorico(pessoaFisicaHistorico);
        enderecoPessoaFisicaJuridicaHistorico.setEnderecoHistorico(enderecoHistorico);
        enderecoPessoaFisicaJuridicaHistorico.setBemHistoricoList(bemHistoricoList);
        enderecoPessoaFisicaJuridicaHistorico.setPessoaFisicaJuridicaHistoricoList(pessoaFisicaJuridicaHistoricoList);
        return enderecoPessoaFisicaJuridicaHistorico;
    }

    public PessoaFisica getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public List<Estado> getEstadoList() {
        return estadoList;
    }

    public void setEstadoList(List<Estado> estadoList) {
        this.estadoList = estadoList;
    }

    public List<Cidade> getCidadeNatList() {
        return cidadeNatList;
    }

    public void setCidadeNatList(List<Cidade> cidadeNatList) {
        this.cidadeNatList = cidadeNatList;
    }

    public List<Cidade> getCidadeEndList() {
        return cidadeEndList;
    }

    public void setCidadeEndList(List<Cidade> cidadeEndList) {
        this.cidadeEndList = cidadeEndList;
    }

    public List<Cidade> getCidadeEleList() {
        return cidadeEleList;
    }

    public void setCidadeEleList(List<Cidade> cidadeEleList) {
        this.cidadeEleList = cidadeEleList;
    }

    public List<Nacionalidade> getNacionalidadeList() {
        return nacionalidadeList;
    }

    public void setNacionalidadeList(List<Nacionalidade> nacionalidadeList) {
        this.nacionalidadeList = nacionalidadeList;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public List<Pais> getPaisList() {
        return paisList;
    }

    public void setPaisList(List<Pais> paisList) {
        this.paisList = paisList;
    }

    public List<EstadoCivil> getEstadoCivilList() {
        return estadoCivilList;
    }

    public List<PessoaJuridica> getPessoaJuridicaList() {
        return pessoaJuridicaList;
    }

    public List<PessoaFisicaJuridica> getPessoaFisicaJuridicaList() {
        return pessoaFisicaJuridicaList;
    }

    public List<Funcao> getFuncaoList() {
        return funcaoList;
    }

    public void setFuncaoList(List<Funcao> funcaoList) {
        this.funcaoList = funcaoList;
    }

    public void setPessoaFisicaJuridicaList(List<PessoaFisicaJuridica> pessoaFisicaJuridicaList) {
        this.pessoaFisicaJuridicaList = pessoaFisicaJuridicaList;
    }

    public void setPessoaJuridicaList(List<PessoaJuridica> pessoaJuridicaList) {
        this.pessoaJuridicaList = pessoaJuridicaList;
    }

    public void setEstadoCivilList(List<EstadoCivil> estadoCivilList) {
        this.estadoCivilList = estadoCivilList;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<EnderecoPessoaFisicaJuridicaHistorico> getEnderecoPessoaFisicaJuridicaHistoricoList() {
        return enderecoPessoaFisicaJuridicaHistoricoList;
    }

    public void setEnderecoPessoaFisicaJuridicaHistoricoList(List<EnderecoPessoaFisicaJuridicaHistorico> enderecoPessoaFisicaJuridicaHistoricoList) {
        this.enderecoPessoaFisicaJuridicaHistoricoList = enderecoPessoaFisicaJuridicaHistoricoList;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public EnderecoPessoa getEnderecoPessoa() {
        return enderecoPessoa;
    }

    public void setEnderecoPessoa(EnderecoPessoa enderecoPessoa) {
        this.enderecoPessoa = enderecoPessoa;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public boolean isHistory() {
        return history;
    }

    public void setHistory(boolean history) {
        this.history = history;
    }

    public String getPfId() {
        return pfId;
    }

    public void setPfId(String pfId) {
        this.pfId = pfId;
    }

    public String getPjId() {
        return pjId;
    }

    public void setPjId(String pjId) {
        this.pjId = pjId;
    }

    public List<ProcessoJudicial> getProcessoJudicialList() {
        return processoJudicialList;
    }

    public void setProcessoJudicialList(List<ProcessoJudicial> processoJudicialList) {
        this.processoJudicialList = processoJudicialList;
    }

    public List<PessoaFisicaFisica> getPessoaFisicaFisicaList() {
        return pessoaFisicaFisicaList;
    }

    public void setPessoaFisicaFisicaList(List<PessoaFisicaFisica> pessoaFisicaFisicaList) {
        this.pessoaFisicaFisicaList = pessoaFisicaFisicaList;
    }

    public List<VinculoSocial> getVinculoSocialList() {
        return vinculoSocialList;
    }

    public void setVinculoSocialList(List<VinculoSocial> vinculoSocialList) {
        this.vinculoSocialList = vinculoSocialList;
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

    public List<TipoBem> getTipoBemList() {
        return tipoBemList;
    }

    public void setTipoBemList(List<TipoBem> tipoBemList) {
        this.tipoBemList = tipoBemList;
    }

}
