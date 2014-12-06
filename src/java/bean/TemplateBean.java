/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bo.BemBO;
import bo.CidadeBO;
import bo.CitacaoBO;
import bo.EnderecoBO;
import bo.EstadoBO;
import bo.EstadoCivilBO;
import bo.FuncaoBO;
import bo.NacionalidadeBO;
import bo.PaisBO;
import bo.PenhoraBO;
import bo.PessoaFisicaBO;
import bo.PessoaFisicaFisicaBO;
import bo.PessoaFisicaJuridicaBO;
import bo.PessoaJuridicaBO;
import bo.PessoaJuridicaJuridicaBO;
import bo.PessoaJuridicaSucessaoBO;
import bo.ProcessoJudicialBO;
import bo.RedirecionamentoBO;
import bo.TipoBemBO;
import bo.TipoEmpresarialBO;
import bo.UsuarioBO;
import bo.VinculoSocialBO;
import entidade.Bem;
import entidade.Cidade;
import entidade.Citacao;
import entidade.Endereco;
import entidade.EnderecoPessoa;
import entidade.Estado;
import entidade.EstadoCivil;
import entidade.Executado;
import entidade.Funcao;
import entidade.Nacionalidade;
import entidade.Pais;
import entidade.PessoaFisica;
import entidade.PessoaFisicaFisica;
import entidade.PessoaFisicaJuridica;
import entidade.PessoaJuridica;
import entidade.PessoaJuridicaJuridica;
import entidade.PessoaJuridicaSucessao;
import entidade.ProcessoJudicial;
import entidade.Redirecionamento;
import entidade.SocioRedirecionamento;
import entidade.TipoBem;
import entidade.TipoEmpresarial;
import entidade.Usuario;
import entidade.VinculoSocial;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import util.Cookie;
import util.GeradorLog;

/**
 *
 * @author ipti004
 */
@ManagedBean(name = "templateBean")
@ViewScoped
public class TemplateBean implements Serializable {

    private Usuario usuario;
    private EnderecoPessoa enderecoPessoaFisica;
    private EnderecoPessoa enderecoPessoaJuridica;
    private Executado executado;
    private PessoaJuridicaSucessao pessoaJuridicaSucessao;
    private Bem bem;
 
    private List<TipoEmpresarial> tipoEmpresarialList;
    private List<Pais> paisList;
    private List<Nacionalidade> nacionalidadeList;
    private List<EstadoCivil> estadoCivilList;
    private List<Estado> estadoList;
    private List<Cidade> cidadeEndList;
    private List<Cidade> cidadeEleList;
    private List<Cidade> cidadeNatList;
    private List<Funcao> funcaoList;
    private List<VinculoSocial> vinculoSocialList;
    private List<PessoaFisicaJuridica> pessoaFisicaJuridicaList;
    private List<PessoaFisicaFisica> pessoaFisicaFisicaList;
    private List<PessoaJuridicaJuridica> pessoaJuridicaJuridicaList;
    private List<Bem> bemList;
    private List<TipoBem> tipoBemList; 

    private String pfVId;
    private String pjVId;
    private String register;
    private String idfk;
    private String tabela;
    private String searchValue;
    private String sucessaoId;
    private boolean bemRepetido;

    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            usuario = UsuarioBO.findUsuarioByCPF(Cookie.getCookie("usuario")); 
 
            enderecoPessoaFisica = new EnderecoPessoa(new PessoaFisica(), new Endereco(), new ArrayList<Bem>());
            enderecoPessoaJuridica = new EnderecoPessoa(new PessoaJuridica(), new Endereco(), new ArrayList<Bem>());
            executado = new Executado();
            pessoaJuridicaSucessao = new PessoaJuridicaSucessao();
            bem = new Bem();

            cidadeEndList = new ArrayList<>();
            cidadeEleList = new ArrayList<>();
            cidadeNatList = new ArrayList<>();

            searchValue = "";
            register = "";
            idfk = "";
            tabela = "";
            bemRepetido = false;
        }
    }

    public void exibirModalNew() {
        bemList = new ArrayList<>();
        pessoaFisicaFisicaList = new ArrayList<>();
        pessoaFisicaJuridicaList = new ArrayList<>();
        pessoaJuridicaJuridicaList = new ArrayList<>();
        pfVId = "";
        pjVId = "";
        bem = new Bem();
        if (tabela.equals("PF")) {
            enderecoPessoaFisica = new EnderecoPessoa(new PessoaFisica(), new Endereco(), new ArrayList<Bem>());
            carregarFormularioModalPF();
        } else if (tabela.equals("PJ")) {
            enderecoPessoaJuridica = new EnderecoPessoa(new PessoaJuridica(), new Endereco(), new ArrayList<Bem>());
            carregarFormularioModalPJ();
        } else if (tabela.equals("BEM")) {
            bem.setTipo(idfk.substring(0, 2));
            bem.setIdFk(Integer.valueOf(idfk.substring(2)));
            bem.setStatus('A');
            tipoBemList = TipoBemBO.findAll();
        }
    }

    public void carregarFormularioModalPF() {
        paisList = PaisBO.findAll();
        paisList.remove(PaisBO.findBrasil());
        estadoList = EstadoBO.findAll();
        nacionalidadeList = NacionalidadeBO.findAll();
        estadoCivilList = EstadoCivilBO.findAll();
        funcaoList = FuncaoBO.findAll();
        vinculoSocialList = VinculoSocialBO.findAll();
        tipoBemList = TipoBemBO.findAll();
    }

    public void carregarFormularioModalPJ() {
        estadoList = EstadoBO.findAll();
        tipoEmpresarialList = TipoEmpresarialBO.findAll();
        funcaoList = FuncaoBO.findAll();
        tipoBemList = TipoBemBO.findAll();
    }

    public void getCidadesPeloEstado() {
        if (tabela.equals("PF")) {
            PessoaFisica pessoaFisica = (PessoaFisica) enderecoPessoaFisica.getPessoa();
            if (pessoaFisica.getEstadoFk() != null) {
                cidadeNatList = CidadeBO.getByStateId(pessoaFisica.getEstadoFk().getId());
            } else {
                cidadeNatList.clear();
            }
            if (enderecoPessoaFisica.getEndereco().getEstadoFk() != null) {
                cidadeEndList = CidadeBO.getByStateId(enderecoPessoaFisica.getEndereco().getEstadoFk().getId());
            } else {
                cidadeEndList.clear();
            }
            if (pessoaFisica.getEstadoEleitoralFk() != null) {
                cidadeEleList = CidadeBO.getByStateId(pessoaFisica.getEstadoEleitoralFk().getId());
            } else {
                cidadeEleList.clear();
            }
        } else if (tabela.equals("PJ")) {
            if (enderecoPessoaJuridica.getEndereco().getEstadoFk() != null) {
                cidadeEndList = CidadeBO.getByStateId(enderecoPessoaJuridica.getEndereco().getEstadoFk().getId());
            } else {
                cidadeEndList.clear();
            }
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
        bemList.remove(index);
    }

    public void vincularPessoaFisica() {
        PessoaFisica pessoaFisica = PessoaFisicaBO.findPessoaFisica(Integer.valueOf(pfVId));
        if (tabela.equals("PF")) {
            PessoaFisicaFisica pessoaFisicaFisica = new PessoaFisicaFisica();
            pessoaFisicaFisica.setPessoaFisicaSecundariaFk(pessoaFisica);
            boolean exists = false;
            for (PessoaFisicaFisica pff : pessoaFisicaFisicaList) {
                if (pff.getPessoaFisicaSecundariaFk().getId().equals(pessoaFisica.getId())) {
                    exists = true;
                }
            }
            if (!exists) {
                pessoaFisicaFisicaList.add(pessoaFisicaFisica);
            }
        } else if (tabela.equals("PJ")) {
            PessoaFisicaJuridica pessoaFisicaJuridica = new PessoaFisicaJuridica();
            pessoaFisicaJuridica.setPessoaFisicaFk(pessoaFisica);
            boolean exists = false;
            for (PessoaFisicaJuridica pfj : pessoaFisicaJuridicaList) {
                if (pfj.getPessoaFisicaFk().getId().equals(pessoaFisica.getId())) {
                    exists = true;
                }
            }
            if (!exists) {
                pessoaFisicaJuridicaList.add(pessoaFisicaJuridica);
            }
        }

    }

    public void removerVinculoFisicoSocial(int index) {
        pessoaFisicaFisicaList.remove(index);
    }

    public void removerVinculoFisico(int index) {
        pessoaFisicaJuridicaList.remove(index);
    }

    public void vincularPessoaJuridica() {
        PessoaJuridica pessoaJuridica = PessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(pjVId));
        if (tabela.equals("PF")) {
            PessoaFisicaJuridica pessoaFisicaJuridica = new PessoaFisicaJuridica();
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
        } else if (tabela.equals("PJ")) {
            PessoaJuridicaJuridica pessoaJuridicaJuridica = new PessoaJuridicaJuridica();
            pessoaJuridicaJuridica.setPessoaJuridicaSecundariaFk(pessoaJuridica);
            boolean exists = false;
            for (PessoaJuridicaJuridica pjj : pessoaJuridicaJuridicaList) {
                if (pjj.getPessoaJuridicaSecundariaFk().getId().equals(pessoaJuridica.getId())) {
                    exists = true;
                }
            }
            if (!exists) {
                pessoaJuridicaJuridicaList.add(pessoaJuridicaJuridica);
            }
        }
    }

    public void removerVinculoJuridico(int index) {
        pessoaJuridicaJuridicaList.remove(index);
    }

    public void cadastrarBem() {
        bemRepetido = false;
        List<Bem> bemList = new ArrayList<>();
        if (bem.getTipo().equals("PF")) {
            bemList = BemBO.findPFBens(bem.getIdFk());
        } else if (bem.getTipo().equals("PJ")) {
            bemList = BemBO.findPJBens(bem.getIdFk());
        }
        for (Bem bem : bemList) {
            if (this.bem.equalsValues(bem)) {
                bemRepetido = true;
                break;
            }
        }
        if (!bemRepetido) {
            if (bem.getDataDeAquisicao() != null || bem.getDataDeTransferenciaOuExtincao() != null
                    || bem.getDescricao() != null || bem.getEndereco() != null || bem.getValor() != null
                    || bem.getTipoBemFk() != null) {
                BemBO.create(bem);
            }
        }
    }

    public void cadastrarPF() {
        PessoaFisica pessoaFisica = (PessoaFisica) enderecoPessoaFisica.getPessoa();
        if (pessoaFisica.getRgOrgaoEmissor() != null) {
            pessoaFisica.setRgOrgaoEmissor(pessoaFisica.getRgOrgaoEmissor().toUpperCase());
        }
        if (pessoaFisica.getEstadoFk() != null) {
            pessoaFisica.setPaisFk(PaisBO.findBrasil());
        }
        pessoaFisica.setStatus('A');
        pessoaFisica.setUsuarioFk(UsuarioBO.findUsuarioByCPF(Cookie.getCookie("usuario")));
        pessoaFisica.setInstituicaoFk(UsuarioBO.findAutorizacaoByCPF(Cookie.getCookie("usuario")).getInstituicaoFk());
        PessoaFisicaBO.create(pessoaFisica);
        Endereco endereco = enderecoPessoaFisica.getEndereco();
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
        for (Bem b : bemList) {
            b.setTipo("PF");
            b.setIdFk(pessoaFisica.getId());
            BemBO.create(b);
        }
        GeradorLog.criar(pessoaFisica.getId(), "PF", 'C');
    }

    public void cadastrarPJ() {
        PessoaJuridica pessoaJuridica = (PessoaJuridica) enderecoPessoaJuridica.getPessoa();
        pessoaJuridica.setStatus('A');
        pessoaJuridica.setUsuarioFk(UsuarioBO.findUsuarioByCPF(Cookie.getCookie("usuario")));
        pessoaJuridica.setInstituicaoFk(UsuarioBO.findAutorizacaoByCPF(Cookie.getCookie("usuario")).getInstituicaoFk());
        PessoaJuridicaBO.create(pessoaJuridica);
        Endereco endereco = enderecoPessoaJuridica.getEndereco();
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
        for (Bem b : bemList) {
            b.setTipo("PJ");
            b.setIdFk(pessoaJuridica.getId());
            BemBO.create(b);
        }
        GeradorLog.criar(pessoaJuridica.getId(), "PJ", 'C');
    }

    public void exibirModalInfo() {
        if (tabela.equals("PF")) {
            PessoaFisica pessoaFisica = PessoaFisicaBO.findPessoaFisica(Integer.valueOf(idfk));
            enderecoPessoaFisica = new EnderecoPessoa(pessoaFisica, EnderecoBO.findPFAddress(pessoaFisica.getId()), BemBO.findPFBens(pessoaFisica.getId()));
            executado = new Executado();
            pessoaJuridicaSucessao = new PessoaJuridicaSucessao();
        } else if (tabela.equals("PJ")) {
            PessoaJuridica pessoaJuridica = PessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(idfk));
            enderecoPessoaJuridica = new EnderecoPessoa(pessoaJuridica, EnderecoBO.findPJAddress(pessoaJuridica.getId()), BemBO.findPJBens(pessoaJuridica.getId()));
            executado = new Executado();
            pessoaJuridicaSucessao = new PessoaJuridicaSucessao();
        } else if (tabela.equals("PJUD")) {
            CitacaoBO citacaoBO = new CitacaoBO();
            RedirecionamentoBO redirecionamentoBO = new RedirecionamentoBO();
            ProcessoJudicial pjud = ProcessoJudicialBO.findProcessoJudicial(Integer.valueOf(idfk));
            if (pjud.getExecutado().equals("PF")) {
                PessoaFisica pf = PessoaFisicaBO.findPessoaFisica(pjud.getExecutadoFk());
                executado = new Executado(pjud, new EnderecoPessoa(pf, EnderecoBO.findPFAddress(pf.getId()), BemBO.findPFBens(pf.getId())), citacaoBO.findByPJUD(pjud.getId()), carregarSocioRedirecionamento(redirecionamentoBO.findByPJUD(pjud.getId())), PenhoraBO.findByPJUD(pjud.getId()));
            } else {
                PessoaJuridica pj = PessoaJuridicaBO.findPessoaJuridica(pjud.getExecutadoFk());
                executado = new Executado(pjud, new EnderecoPessoa(pj, EnderecoBO.findPFAddress(pj.getId()), BemBO.findPJBens(pj.getId())), citacaoBO.findByPJUD(pjud.getId()), carregarSocioRedirecionamento(redirecionamentoBO.findByPJUD(pjud.getId())), PenhoraBO.findByPJUD(pjud.getId()));
            }
            pessoaJuridicaSucessao = new PessoaJuridicaSucessao();
        } else if (tabela.equals("PJS")) {
            pessoaJuridicaSucessao = PessoaJuridicaSucessaoBO.findPessoaJuridicaSucessao(Integer.valueOf(idfk));
            executado = new Executado();
        }
    }

    public int checkCitacaoVazia(List<Citacao> citacaoList, String tipo) {
        int quantidade = 0;
        if (citacaoList != null) {
            for (Citacao citacao : citacaoList) {
                quantidade = citacao.getTipoCitacao().equals(tipo) ? quantidade + 1 : quantidade;
            }
        }
        return quantidade;
    }

    public String loadSocio(String tipo, String id) {
        if (tipo != null) {
            if (tipo.equals("PF")) {
                PessoaFisica pf = PessoaFisicaBO.findPessoaFisica(Integer.valueOf(id));
                String cpf = (pf.getCpf() == null ? "Sem CPF" : pf.getCpf().substring(0, 3) + "." + pf.getCpf().substring(3, 6) + "." + pf.getCpf().substring(6, 9) + "-" + pf.getCpf().substring(9));
                return cpf + " - " + pf.getNome();
            } else {
                PessoaJuridica pj = PessoaJuridicaBO.findPessoaJuridica(Integer.valueOf(id));
                String cnpj = pj.getCnpj().substring(0, 2) + "." + pj.getCnpj().substring(2, 5) + "." + pj.getCnpj().substring(5, 8) + "/" + pj.getCnpj().substring(8, 12) + "-" + pj.getCnpj().substring(12);
                return cnpj + " - " + pj.getNome();
            }
        }
        return "-";
    }

    public List<SocioRedirecionamento> carregarSocioRedirecionamento(List<Redirecionamento> redirecionamentoList) {
        List<SocioRedirecionamento> socioRedirecionamentoList = new ArrayList<>();
        for (Redirecionamento redirecionamento : redirecionamentoList) {
            if (redirecionamento.getSocio().equals("PF")) {
                socioRedirecionamentoList.add(new SocioRedirecionamento(PessoaFisicaBO.findPessoaFisica(redirecionamento.getSocioFk()), redirecionamento));
            } else {
                socioRedirecionamentoList.add(new SocioRedirecionamento(PessoaJuridicaBO.findPessoaJuridica(redirecionamento.getSocioFk()), redirecionamento));
            }
        }
        return socioRedirecionamentoList;
    }

    public void exibirSucessao() {
        pessoaJuridicaSucessao = PessoaJuridicaSucessaoBO.findPessoaJuridicaSucessao(Integer.valueOf(sucessaoId));
    }

    public void search() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/procura-geral.xhtml?value=" + searchValue);
    }

    public String setInversao(String vinculo) {
        switch (vinculo) {
            case "Irmão/Irmã":
                return "Irmão/Irmã";
            case "Pai/Mãe":
                return "Filho/Filha";
            case "Tio/Tia":
                return "Sobrinho/Sobrinha";
            case "Avô/Avó":
                return "Neto/Neta";
            case "Bisavô/Bisavó":
                return "Bisneto/Bisneta";
            case "Trisavô/Trisavó":
                return "Trineto/Trineta";
            case "Sogro/Sogra":
                return "Genro/Nora";
            case "Cônjuge":
                return "Cônjuge";
            case "Filho/Filha":
                return "Pai/Mãe";
            case "Primo/Prima":
                return "Primo/Prima";
            case "Cunhado/Cunhada":
                return "Cunhado/Cunhada";
            case "Genro/Nora":
                return "Sogro/Sogra";
            case "Neto/Neta":
                return "Avô/Avó";
            case "Bisneto/Bisneta":
                return "Bisavô/Bisavó";
            case "Trineto/Trineta":
                return "Trisavô/Trisavó";
            case "Sobrinho/Sobrinha":
                return "Tio/Tia";
            case "Empregado":
                return "Empregador";
            case "Empregador":
                return "Empregado";
            case "Empregado doméstico":
                return "Empregador";
            case "Estagiário":
                return "Empregador";
            case "Enteado/Enteada":
                return "Padrasto/Madrasta";
            case "Padrasto/Madrasta":
                return "Enteado/Enteada";
            default:
                return vinculo;
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Executado getExecutado() {
        return executado;
    }

    public void setExecutado(Executado executado) {
        this.executado = executado;
    }

    public String getIdfk() {
        return idfk;
    }

    public void setIdfk(String idfk) {
        this.idfk = idfk;
    }

    public String getTabela() {
        return tabela;
    }

    public void setTabela(String tabela) {
        this.tabela = tabela;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public PessoaJuridicaSucessao getPessoaJuridicaSucessao() {
        return pessoaJuridicaSucessao;
    }

    public void setPessoaJuridicaSucessao(PessoaJuridicaSucessao pessoaJuridicaSucessao) {
        this.pessoaJuridicaSucessao = pessoaJuridicaSucessao;
    }

    public String getSucessaoId() {
        return sucessaoId;
    }

    public void setSucessaoId(String sucessaoId) {
        this.sucessaoId = sucessaoId;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public List<TipoEmpresarial> getTipoEmpresarialList() {
        return tipoEmpresarialList;
    }

    public void setTipoEmpresarialList(List<TipoEmpresarial> tipoEmpresarialList) {
        this.tipoEmpresarialList = tipoEmpresarialList;
    }

    public List<Estado> getEstadoList() {
        return estadoList;
    }

    public void setEstadoList(List<Estado> estadoList) {
        this.estadoList = estadoList;
    }

    public List<Cidade> getCidadeEndList() {
        return cidadeEndList;
    }

    public void setCidadeEndList(List<Cidade> cidadeEndList) {
        this.cidadeEndList = cidadeEndList;
    }

    public List<Funcao> getFuncaoList() {
        return funcaoList;
    }

    public void setFuncaoList(List<Funcao> funcaoList) {
        this.funcaoList = funcaoList;
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

    public List<PessoaFisicaJuridica> getPessoaFisicaJuridicaList() {
        return pessoaFisicaJuridicaList;
    }

    public void setPessoaFisicaJuridicaList(List<PessoaFisicaJuridica> pessoaFisicaJuridicaList) {
        this.pessoaFisicaJuridicaList = pessoaFisicaJuridicaList;
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

    public List<Pais> getPaisList() {
        return paisList;
    }

    public void setPaisList(List<Pais> paisList) {
        this.paisList = paisList;
    }

    public List<Nacionalidade> getNacionalidadeList() {
        return nacionalidadeList;
    }

    public void setNacionalidadeList(List<Nacionalidade> nacionalidadeList) {
        this.nacionalidadeList = nacionalidadeList;
    }

    public List<EstadoCivil> getEstadoCivilList() {
        return estadoCivilList;
    }

    public void setEstadoCivilList(List<EstadoCivil> estadoCivilList) {
        this.estadoCivilList = estadoCivilList;
    }

    public List<Cidade> getCidadeEleList() {
        return cidadeEleList;
    }

    public void setCidadeEleList(List<Cidade> cidadeEleList) {
        this.cidadeEleList = cidadeEleList;
    }

    public List<Cidade> getCidadeNatList() {
        return cidadeNatList;
    }

    public void setCidadeNatList(List<Cidade> cidadeNatList) {
        this.cidadeNatList = cidadeNatList;
    }

    public List<VinculoSocial> getVinculoSocialList() {
        return vinculoSocialList;
    }

    public void setVinculoSocialList(List<VinculoSocial> vinculoSocialList) {
        this.vinculoSocialList = vinculoSocialList;
    }

    public List<PessoaFisicaFisica> getPessoaFisicaFisicaList() {
        return pessoaFisicaFisicaList;
    }

    public void setPessoaFisicaFisicaList(List<PessoaFisicaFisica> pessoaFisicaFisicaList) {
        this.pessoaFisicaFisicaList = pessoaFisicaFisicaList;
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
