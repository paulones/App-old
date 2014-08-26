/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

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
import bo.TipoEmpresarialBO;
import bo.UsuarioBO;
import bo.UtilBO;
import entidade.Cidade;
import entidade.Endereco;
import entidade.EnderecoHistorico;
import entidade.EnderecoPessoa;
import entidade.EnderecoPessoaFisicaJuridicaHistorico;
import entidade.Estado;
import entidade.Funcao;
import entidade.PessoaFisica;
import entidade.PessoaFisicaJuridica;
import entidade.PessoaFisicaJuridicaHistorico;
import entidade.PessoaJuridica;
import entidade.PessoaJuridicaHistorico;
import entidade.TipoEmpresarial;
import entidade.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
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
 * @author Pedro
 */
@SessionScoped
@ManagedBean(name = "pessoaJuridicaBean")
public class PessoaJuridicaBean implements Serializable {

    private PessoaJuridica pessoaJuridica;
    private PessoaJuridica oldPessoaJuridica;
    private Endereco endereco;
    private Endereco oldEndereco;
    private PessoaFisica pessoaFisica;
    private PessoaFisicaJuridica pessoaFisicaJuridica;
    private EnderecoPessoa enderecoPessoa;
    private EnderecoPessoa enderecoPessoaModal;

    private PessoaJuridicaHistorico pessoaJuridicaHistorico;
    private EnderecoHistorico EnderecoHistorico;

    private String register;
    private String redirect;
    private boolean edit;
    private boolean history;
    private Integer pjId;
    private String pfId;

    private List<TipoEmpresarial> tipoEmpresarialList;
    private List<Estado> estadoList;
    private List<Cidade> cidadeEndList;
    private List<PessoaFisica> pessoaFisicaList;
    private List<PessoaJuridica> pessoaJuridicaList;
    private List<Funcao> funcaoList;
    private List<PessoaFisicaJuridica> pessoaFisicaJuridicaList;
    private List<PessoaFisicaJuridica> oldPessoaFisicaJuridicaList;
    private List<EnderecoPessoa> enderecoPessoaList;
    private List<Endereco> enderecoList;
    private List<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoList;
    private List<EnderecoPessoaFisicaJuridicaHistorico> enderecoPessoaFisicaJuridicaHistoricoList;
    private List<PessoaJuridicaHistorico> pessoaJuridicaHistoricoList;
    private List<EnderecoHistorico> enderecoHistoricoList;

    private TipoEmpresarialBO tipoEmpresarialBO;
    private PessoaFisicaBO pessoaFisicaBO;
    private PessoaJuridicaBO pessoaJuridicaBO;
    private PessoaFisicaJuridicaBO pessoaFisicaJuridicaBO;
    private EstadoBO estadoBO;
    private CidadeBO cidadeBO;
    private FuncaoBO funcaoBO;
    private UsuarioBO usuarioBO;
    private EnderecoBO enderecoBO;
    private PessoaJuridicaHistoricoBO pessoaJuridicaHistoricoBO;
    private EnderecoHistoricoBO enderecoHistoricoBO;
    private PessoaFisicaJuridicaHistoricoBO pessoaFisicaJuridicaHistoricoBO;

    public void init() throws IOException {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            boolean isRegisterPage = FacesContext.getCurrentInstance().getViewRoot().getViewId().lastIndexOf("cadastrar") > -1;
            boolean isSearchPage = FacesContext.getCurrentInstance().getViewRoot().getViewId().lastIndexOf("consultar") > -1;

            tipoEmpresarialBO = new TipoEmpresarialBO();
            pessoaFisicaBO = new PessoaFisicaBO();
            pessoaJuridicaBO = new PessoaJuridicaBO();
            pessoaFisicaJuridicaBO = new PessoaFisicaJuridicaBO();
            pessoaJuridicaHistoricoBO = new PessoaJuridicaHistoricoBO();
            enderecoHistoricoBO = new EnderecoHistoricoBO();
            pessoaFisicaJuridicaHistoricoBO = new PessoaFisicaJuridicaHistoricoBO();
            estadoBO = new EstadoBO();
            cidadeBO = new CidadeBO();
            funcaoBO = new FuncaoBO();
            enderecoBO = new EnderecoBO();

            endereco = new Endereco();
            pessoaFisica = new PessoaFisica();
            pessoaFisicaJuridica = new PessoaFisicaJuridica();

            register = "";
            redirect = Cookie.getCookie("FacesMessage");
            Cookie.apagarCookie("FacesMessage");

            tipoEmpresarialList = new ArrayList<>();
            cidadeEndList = new ArrayList<>();
            pessoaFisicaJuridicaList = new ArrayList<>();

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            pessoaJuridica = new PessoaJuridica();
            if (isRegisterPage) {
                /*
                 Tela cadastro.xhtml. Se houver "id" na url, entra na condição de alteração.
                 Caso contrário, apenas carrega o formulário
                 */
                pfId = "";
                if (request.getParameter("id") == null) { //Novo
                    edit = false;
                    carregarFormulario();
                } else {
                    Integer id = Integer.valueOf(request.getParameter("id"));
                    pessoaJuridica = pessoaJuridicaBO.findPessoaJuridica(id);
                    if (pessoaJuridica == null) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("cadastrar.xhtml");
                    } else {
                        edit = true;
                        endereco = enderecoBO.findPJAddress(id);
                        pessoaFisicaJuridicaList = pessoaFisicaJuridicaBO.findAllByPJ(id);

                        oldPessoaJuridica = pessoaJuridicaBO.findPessoaJuridica(id);
                        oldEndereco = enderecoBO.findPJAddress(id);
                        oldPessoaFisicaJuridicaList = pessoaFisicaJuridicaBO.findAllByPJ(id);
                        prepararHistorico(pessoaJuridica, endereco, pessoaFisicaJuridicaList);

                        carregarFormulario();
                        getCidadesPeloEstado();
                    }
                }
            } else if (isSearchPage) {
                /*
                 Tela consulta.xhtml. Se houver "id" na url, entra na lista de alterações nos
                 dados da Pessoa Jurídica. Caso contrário, acessa a consulta geral 
                 */
                pjId = 0;
                if (request.getParameter("id") == null) {   // Consulta geral
                    history = false;
                    pessoaJuridicaList = pessoaJuridicaBO.findAllActive();
                    enderecoList = enderecoBO.findAllPJAddress();
                    enderecoPessoaList = new ArrayList<>();
                    for (PessoaJuridica pj : pessoaJuridicaList) {
                        endereco = enderecoBO.findPJAddress(pj.getId());
                        enderecoPessoaList.add(new EnderecoPessoa(pj, endereco));
                    }
        } else { // Consulta histórico
            Integer id = Integer.valueOf(request.getParameter("id"));
            pessoaJuridica = pessoaJuridicaBO.findPessoaJuridica(id);
            if (pessoaJuridica == null) {
                history = false;
                FacesContext.getCurrentInstance().getExternalContext().redirect("consultar.xhtml");
            } else {
                history = true;
                endereco = enderecoBO.findPJAddress(id);
                pessoaFisicaJuridicaList = pessoaFisicaJuridicaBO.findAllByPJ(id);

                pessoaJuridicaHistoricoList = new ArrayList<>();
                enderecoHistoricoList = new ArrayList<>();
                pessoaFisicaJuridicaHistoricoList = new ArrayList<>();

                pessoaJuridicaHistoricoList = pessoaJuridicaHistoricoBO.findAllByPJ(id);
                enderecoHistoricoList = enderecoHistoricoBO.findAllByPJ(id);
                pessoaFisicaJuridicaHistoricoList = pessoaFisicaJuridicaHistoricoBO.findAllByPJ(id);

                enderecoPessoaFisicaJuridicaHistoricoList = new ArrayList<>();
                EnderecoPessoaFisicaJuridicaHistorico enderecoPessoaFisicaJuridicaHistorico = new EnderecoPessoaFisicaJuridicaHistorico();
                enderecoPessoaFisicaJuridicaHistorico = prepararRegistroAtual(pessoaJuridica, endereco, pessoaFisicaJuridicaList);
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
                            epfjh.setPessoaFisicaJuridicaHistoricoList(pfjhList);
                            enderecoPessoaFisicaJuridicaHistoricoList.add(epfjh);
                            break;
                        }
                    }
                }
            }
        }
    }
}
}
    
    public void carregarFormulario() {
        estadoList = estadoBO.findAll();
        tipoEmpresarialList = tipoEmpresarialBO.findAll();
        pessoaFisicaList = pessoaFisicaBO.findAllActive();
        funcaoList = funcaoBO.findAll();
    }

    public void cadastrar() throws IOException {
        UsuarioBO usuarioBO = new UsuarioBO();
        if (!edit) {
            /*  
             Cadastrar nova Pessoa Jurídica
             */
            PessoaJuridica pjDB = pessoaJuridicaBO.findDuplicates(pessoaJuridica);
            if (pjDB == null || pessoaJuridica.getCnpj().isEmpty()) { //CNPJ novo
                pessoaJuridica.setStatus('A');
                pessoaJuridica.setUsuarioFk(usuarioBO.findUsuarioByCPF(Cookie.getCookie("usuario")));
                pessoaJuridicaBO.create(pessoaJuridica);
                endereco.setTipo("PJ");
                endereco.setIdFk(pessoaJuridica.getId());
                enderecoBO.create(endereco);
                for (PessoaFisicaJuridica pfj : pessoaFisicaJuridicaList) {
                    pfj.setPessoaJuridicaFk(pessoaJuridica);
                    pessoaFisicaJuridicaBO.create(pfj);
                }
                register = "success";
                pfId = "";
                pessoaJuridica = new PessoaJuridica();
                endereco = new Endereco();
                pessoaFisicaJuridicaList = new ArrayList<>();
            } else { //CNPJ previamente cadastrado
                register = "fail";
                String cnpj = pjDB.getCnpj().substring(0, 3) + "." + pjDB.getCnpj().substring(3, 6) + "." + pjDB.getCnpj().substring(6, 9) + "/" + pjDB.getCnpj().substring(9, 13) + "-" + pjDB.getCnpj().substring(13);
                String message = "Já existe empresa cadastrada com o CNPJ " + cnpj;
                message += pjDB.getNome() != null ? "\nNome: " + pjDB.getNome() : "";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            }
        } else {
            /*  
             Alterar Pessoa Jurídica existente
             */
            boolean identical = true;
            if (oldPessoaFisicaJuridicaList.size() != pessoaFisicaJuridicaList.size()) {
                identical = false;
            } else {
                for (PessoaFisicaJuridica pfj : pessoaFisicaJuridicaList) {
                    for (PessoaFisicaJuridica oldPfj : oldPessoaFisicaJuridicaList) {
                        if (pfj.changedValues(oldPfj).isEmpty()) {
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
            if (oldPessoaJuridica.changedValues(pessoaJuridica).isEmpty()
                    && oldEndereco.changedValues(endereco).isEmpty()
                    && identical) {
                Cookie.addCookie("FacesMessage", "fail", 10);
                FacesContext.getCurrentInstance().getExternalContext().redirect("consultar.xhtml");
            } else {
                UtilBO utilBO = new UtilBO();
                Timestamp timestamp = utilBO.findServerTime();
                pessoaJuridica.setUsuarioFk(usuarioBO.findUsuarioByCPF(Cookie.getCookie("usuario")));
                pessoaJuridicaBO.edit(pessoaJuridica);
                pessoaJuridicaHistorico.setDataDeModificacao(timestamp);
                pessoaJuridicaHistoricoBO.create(pessoaJuridicaHistorico);
                enderecoBO.edit(endereco);
                EnderecoHistorico.setIdFk(pessoaJuridicaHistorico.getId());
                enderecoHistoricoBO.create(EnderecoHistorico);
                pessoaFisicaJuridicaBO.destroyByPJ(pessoaJuridica.getId());
                for (PessoaFisicaJuridica pfj : pessoaFisicaJuridicaList) {
                    pessoaFisicaJuridicaBO.create(pfj);
                }
                for (PessoaFisicaJuridicaHistorico pfjh : pessoaFisicaJuridicaHistoricoList) {
                    pfjh.setTipo("PJ");
                    pfjh.setIdFk(pessoaJuridicaHistorico.getId());
                    pessoaFisicaJuridicaHistoricoBO.create(pfjh);
                }
                Cookie.addCookie("FacesMessage", "success", 10);
                FacesContext.getCurrentInstance().getExternalContext().redirect("consultar.xhtml");
            }
        }
    }

    public void getCidadesPeloEstado() { // Renderizar cidades baseado no estado escolhido
        if (endereco.getEstadoFk() != null) {
            cidadeEndList = cidadeBO.getByStateId(endereco.getEstadoFk().getId());
        } else {
            cidadeEndList.clear();
        }
    }

    public void vincularPessoaFisica() {
        if (edit) {
            pessoaFisicaJuridica.setPessoaJuridicaFk(pessoaJuridica);
        }
        pessoaFisica = pessoaFisicaBO.findPessoaFisica(Integer.valueOf(Base64Crypt.decrypt(pfId))); 
        pessoaFisicaJuridica.setPessoaFisicaFk(pessoaFisica);
        boolean exists = false;
        for (PessoaFisicaJuridica pfj : pessoaFisicaJuridicaList) {
            if (pfj.getPessoaFisicaFk().getId().equals(pessoaFisica.getId())) { 
                exists = true;
            }
        }
        if (!exists) {
            pessoaFisicaJuridicaList.add(pessoaFisicaJuridica);
            pessoaFisicaJuridica = new PessoaFisicaJuridica();
        }
    }

    public void removerVinculo(int index) {
        pessoaFisicaJuridicaList.remove(index);
    }
    
    public void exibirInfo(){
        pessoaJuridica = pessoaJuridicaBO.findPessoaJuridica(pjId);
        endereco = enderecoBO.findPJAddress(pessoaJuridica.getId());
        enderecoPessoa = new EnderecoPessoa(pessoaJuridica, endereco);
    }

    public void removerPessoaJuridica() {
        pessoaJuridica = pessoaJuridicaBO.findPessoaJuridica(pjId);
        endereco = enderecoBO.findPJAddress(pjId);
        pessoaJuridica.setStatus('I');
        pessoaJuridicaBO.edit(pessoaJuridica);
        enderecoPessoaList.remove(new EnderecoPessoa(pessoaJuridica, endereco));
        redirect = "";
        register = "success";
    }

    public void prepararHistorico(PessoaJuridica pessoaJuridica, Endereco endereco, List<PessoaFisicaJuridica> pessoaFisicaJuridicaList) {
        /*
         Montar entidades dos históricos de alteração 
         */
        usuarioBO = new UsuarioBO();
        Usuario usuario = usuarioBO.findUsuarioByCPF(Cookie.getCookie("usuario"));
        pessoaJuridicaHistorico = new PessoaJuridicaHistorico();
        EnderecoHistorico = new EnderecoHistorico();
        pessoaFisicaJuridicaHistoricoList = new ArrayList<>();

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
    }

    private EnderecoPessoaFisicaJuridicaHistorico prepararRegistroAtual(PessoaJuridica pessoaJuridica, Endereco endereco, List<PessoaFisicaJuridica> pessoaFisicaJuridicaList) {
        /*
         Montar registro atual como uma entidade de histórico para facilitar o ui:repeat do form
         */
        EnderecoPessoaFisicaJuridicaHistorico enderecoPessoaFisicaJuridicaHistorico = new EnderecoPessoaFisicaJuridicaHistorico();
        PessoaJuridicaHistorico pessoaJuridicaHistorico = new PessoaJuridicaHistorico();
        EnderecoHistorico enderecoHistorico = new EnderecoHistorico();
        List<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoList = new ArrayList<>();

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

        enderecoPessoaFisicaJuridicaHistorico.setPessoaHistorico(pessoaJuridicaHistorico);
        enderecoPessoaFisicaJuridicaHistorico.setEnderecoHistorico(enderecoHistorico);
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
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
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

    public List<PessoaFisica> getPessoaFisicaList() {
        return pessoaFisicaList;
    }

    public void setPessoaFisicaList(List<PessoaFisica> pessoaFisicaList) {
        this.pessoaFisicaList = pessoaFisicaList;
    }

    public PessoaFisicaJuridica getPessoaFisicaJuridica() {
        return pessoaFisicaJuridica;
    }

    public void setPessoaFisicaJuridica(PessoaFisicaJuridica pessoaFisicaJuridica) {
        this.pessoaFisicaJuridica = pessoaFisicaJuridica;
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

    public boolean isHistory() {
        return history;
    }

    public void setHistory(boolean history) {
        this.history = history;
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

    public EnderecoPessoa getEnderecoPessoaModal() {
        return enderecoPessoaModal;
    }

    public void setEnderecoPessoaModal(EnderecoPessoa enderecoPessoaModal) {
        this.enderecoPessoaModal = enderecoPessoaModal;
    }

    public Integer getPjId() {
        return pjId;
    }

    public void setPjId(Integer pjId) {
        this.pjId = pjId;
    }

    public String getPfId() {
        return pfId;
    }

    public void setPfId(String pfId) {
        this.pfId = pfId;
    }

}
