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
import bo.EstadoCivilBO;
import bo.FuncaoBO;
import bo.NacionalidadeBO;
import bo.PaisBO;
import bo.PessoaFisicaBO;
import bo.PessoaFisicaHistoricoBO;
import bo.PessoaFisicaJuridicaBO;
import bo.PessoaFisicaJuridicaHistoricoBO;
import bo.PessoaJuridicaBO;
import bo.UsuarioBO;
import bo.UtilBO;
import entidade.Cidade;
import entidade.Endereco;
import entidade.EnderecoHistorico;
import entidade.EnderecoPessoa;
import entidade.Estado;
import entidade.EstadoCivil;
import entidade.Funcao;
import entidade.Nacionalidade;
import entidade.Pais;
import entidade.PessoaFisica;
import entidade.PessoaFisicaHistorico;
import entidade.PessoaFisicaJuridica;
import entidade.PessoaFisicaJuridicaHistorico;
import entidade.PessoaJuridica;
import entidade.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import util.Cookie;

/**
 *
 * @author paulones
 */
@SessionScoped
@ManagedBean(name = "pessoaFisicaBean")
public class PessoaFisicaBean implements Serializable {

    private PessoaFisica pessoaFisica;
    private Endereco endereco;
    private PessoaJuridica pessoaJuridica;
    private PessoaFisicaJuridica pessoaFisicaJuridica;
    
    private PessoaFisicaHistorico oldPessoaFisica;
    private EnderecoHistorico oldEndereco;
    private List<PessoaFisicaJuridicaHistorico> oldPessoaFisicaJuridicaList;

    private String register;
    private String redirect;
    private boolean edit;

    private List<Pais> paisList;
    private List<Estado> estadoList;
    private List<Cidade> cidadeNatList;
    private List<Cidade> cidadeEndList;
    private List<Nacionalidade> nacionalidadeList;
    private List<EstadoCivil> estadoCivilList;
    private List<PessoaFisica> pessoaFisicaList;
    private List<Endereco> enderecoList;
    private List<PessoaJuridica> pessoaJuridicaList;
    private List<PessoaFisicaJuridica> pessoaFisicaJuridicaList;
    private List<Funcao> funcaoList;
    private List<EnderecoPessoa> enderecoPessoaList;

    private PessoaFisicaBO pessoaFisicaBO;
    private PessoaJuridicaBO pessoaJuridicaBO;
    private PessoaFisicaJuridicaBO pessoaFisicaJuridicaBO;
    private NacionalidadeBO nacionalidadeBO;
    private EstadoCivilBO estadoCivilBO;
    private FuncaoBO funcaoBO;
    private EnderecoBO enderecoBO;
    private PaisBO paisBO;
    private EstadoBO estadoBO;
    private CidadeBO cidadeBO;
    private UsuarioBO usuarioBO;
    private PessoaFisicaHistoricoBO pessoaFisicaHistoricoBO;
    private EnderecoHistoricoBO enderecoHistoricoBO;
    private PessoaFisicaJuridicaHistoricoBO pessoaFisicaJuridicaHistoricoBO;

    public void init() throws IOException {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            boolean isRegisterPage = FacesContext.getCurrentInstance().getViewRoot().getViewId().lastIndexOf("cadastrar") > -1;
            boolean isSearchPage = FacesContext.getCurrentInstance().getViewRoot().getViewId().lastIndexOf("consultar") > -1;

            pessoaFisicaBO = new PessoaFisicaBO();
            enderecoBO = new EnderecoBO();
            paisBO = new PaisBO();
            estadoBO = new EstadoBO();
            cidadeBO = new CidadeBO();
            funcaoBO = new FuncaoBO();
            pessoaJuridicaBO = new PessoaJuridicaBO();
            pessoaFisicaJuridicaBO = new PessoaFisicaJuridicaBO();
            nacionalidadeBO = new NacionalidadeBO();
            estadoCivilBO = new EstadoCivilBO();

            endereco = new Endereco();
            pessoaJuridica = new PessoaJuridica();
            pessoaFisicaJuridica = new PessoaFisicaJuridica();

            edit = false;
            register = "";
            redirect = Cookie.getCookie("FacesMessage");
            Cookie.apagarCookie("FacesMessage");

            cidadeNatList = new ArrayList<>();
            cidadeEndList = new ArrayList<>();
            pessoaFisicaJuridicaList = new ArrayList<>();

            if (isRegisterPage) { //Tela de cadastro
                HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                pessoaFisica = new PessoaFisica();
                if (request.getParameter("id") == null) {
                    edit = false;
                    loadRegisterForm();
                } else {
                    Integer id = Integer.valueOf(request.getParameter("id"));
                    PessoaFisica pessoaFisica = pessoaFisicaBO.findPessoaFisica(id);
                    if (pessoaFisica == null) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("cadastrar.xhtml");
                    } else {
                        edit = true;
                        Endereco endereco = enderecoBO.findPFAddress(id);
                        pessoaFisicaJuridicaList = pessoaFisicaJuridicaBO.findAllByPF(id);

                        this.pessoaFisica = pessoaFisica;
                        this.endereco = endereco;
                        
                        prepareHistory(pessoaFisica, endereco, pessoaFisicaJuridicaList);

                        loadRegisterForm();
                        getCitiesByState();
                    }
                }
            } else if (isSearchPage) { //Tela de consulta
                pessoaFisicaList = pessoaFisicaBO.findAll();
                enderecoList = enderecoBO.findAllPFAddress();
                enderecoPessoaList = new ArrayList<>();
                for (PessoaFisica pf : pessoaFisicaList) {
                    for (Endereco end : enderecoList) {
                        if (pf.getId().equals(end.getIdFk())) {
                            EnderecoPessoa enderecoPessoa = new EnderecoPessoa(pf, end);
                            enderecoPessoaList.add(enderecoPessoa);
                        }

                    }
                }
            }
        }
    }

    private void loadRegisterForm() {
        paisList = paisBO.findAll();
        paisList.remove(paisBO.findBrasil());
        estadoList = estadoBO.findAll();
        nacionalidadeList = nacionalidadeBO.findAll();
        estadoCivilList = estadoCivilBO.findAll();
        pessoaJuridicaList = pessoaJuridicaBO.findAll();
        funcaoList = funcaoBO.findAll();
    }

    public void getCitiesByState() {
        if (pessoaFisica.getEstadoFk() != null) {
            cidadeNatList = cidadeBO.getByStateId(pessoaFisica.getEstadoFk().getId());
        } else {
            cidadeNatList.clear();
        }
        if (endereco.getEstadoFk() != null) {
            cidadeEndList = cidadeBO.getByStateId(endereco.getEstadoFk().getId());
        } else {
            cidadeEndList.clear();
        }
    }

    public void cadastrar() throws IOException {
        if (!edit) {
            PessoaFisica pfDB = pessoaFisicaBO.findDuplicates(pessoaFisica);
            if (pfDB == null || pessoaFisica.getCpf().isEmpty()) {
                if (pessoaFisica.getRgOrgaoEmissor() != null) {
                    pessoaFisica.setRgOrgaoEmissor(pessoaFisica.getRgOrgaoEmissor().toUpperCase());
                }
                if (pessoaFisica.getEstadoFk() != null) {
                    pessoaFisica.setPaisFk(paisBO.findBrasil());
                }
                pessoaFisicaBO.create(pessoaFisica);
                endereco.setTipo("PF");
                endereco.setIdFk(pessoaFisica.getId());
                enderecoBO.create(endereco);
                for (PessoaFisicaJuridica pfj : pessoaFisicaJuridicaList) {
                    pfj.setPessoaFisicaFk(pessoaFisica);
                    pessoaFisicaJuridicaBO.create(pfj);
                }
                register = "success";
                pessoaFisica = new PessoaFisica();
                endereco = new Endereco();
                pessoaFisicaJuridicaList = new ArrayList<>();
            } else {
                register = "fail";
                String cpf = pfDB.getCpf().substring(0, 3) + "." + pfDB.getCpf().substring(3, 6) + "." + pfDB.getCpf().substring(6, 9) + "-" + pfDB.getCpf().substring(9);
                String message = "Já existe usuário cadastrado com o CPF " + cpf;
                message += pfDB.getNome() != null ? "\nNome: " + pfDB.getNome() : "";
                message += pfDB.getRg() != null ? "\nRG: " + pfDB.getRg() : "";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            }
        } else {
            pessoaFisicaHistoricoBO = new PessoaFisicaHistoricoBO();
            enderecoHistoricoBO = new EnderecoHistoricoBO();
            pessoaFisicaJuridicaHistoricoBO = new PessoaFisicaJuridicaHistoricoBO();
            if (pessoaFisica.getRgOrgaoEmissor() != null) {
                pessoaFisica.setRgOrgaoEmissor(pessoaFisica.getRgOrgaoEmissor().toUpperCase());
            }
            if (pessoaFisica.getEstadoFk() != null) {
                pessoaFisica.setPaisFk(paisBO.findBrasil());
            }
            UtilBO utilBO = new UtilBO();
            Timestamp timestamp = utilBO.findServerTime();
            pessoaFisicaBO.edit(pessoaFisica);
            oldPessoaFisica.setDataDeModificacao(timestamp);
            pessoaFisicaHistoricoBO.create(oldPessoaFisica);
            enderecoBO.edit(endereco);
            oldEndereco.setDataDeModificacao(timestamp);
            enderecoHistoricoBO.create(oldEndereco);
            pessoaFisicaJuridicaBO.destroyByPF(pessoaFisica.getId());
            for (PessoaFisicaJuridica pfj : pessoaFisicaJuridicaList) {
                pfj.setPessoaFisicaFk(pessoaFisica);
                pessoaFisicaJuridicaBO.create(pfj);
            }
            for (PessoaFisicaJuridicaHistorico pfjh : oldPessoaFisicaJuridicaList){
                pfjh.setDataDeModificacao(timestamp);
                pessoaFisicaJuridicaHistoricoBO.create(pfjh);
            }
            register = "success";
            Cookie.addCookie("FacesMessage", "success", 10);
            FacesContext.getCurrentInstance().getExternalContext().redirect("consultar.xhtml");
        }
    }

    public void removerVinculo(int index) {
        pessoaFisicaJuridicaList.remove(index);
    }
    
    public void removerPessoaFisica(int index) {
        PessoaFisica pessoaFisica = (PessoaFisica) enderecoPessoaList.get(index).getPessoa();
        enderecoBO.destroy(enderecoPessoaList.get(index).getEndereco());
        pessoaFisicaJuridicaBO.destroyByPF(pessoaFisica.getId());
        pessoaFisicaBO.destroy(pessoaFisica);
        enderecoPessoaList.remove(index);
        register = "success";
    }

    public void vincularPessoaJuridica() {
        pessoaFisicaJuridica.setPessoaJuridicaFk(pessoaJuridica);
        boolean exists = false;
        for (PessoaFisicaJuridica pfj : pessoaFisicaJuridicaList) {
            if (pfj.getPessoaJuridicaFk().getCnpj().equals(pessoaJuridica.getCnpj())) {
                exists = true;
            }
        }
        if (!exists) {
            pessoaFisicaJuridicaList.add(pessoaFisicaJuridica);
            pessoaFisicaJuridica = new PessoaFisicaJuridica();
        }
    }
    
    public void prepareHistory(PessoaFisica pessoaFisica, Endereco endereco, List<PessoaFisicaJuridica> pessoaFisicaJuridicaList){
        usuarioBO = new UsuarioBO();
        Usuario usuario = usuarioBO.findUsuarioByCPF(Cookie.getCookie("usuario"));
        oldPessoaFisica = new PessoaFisicaHistorico();
        oldEndereco = new EnderecoHistorico();
        oldPessoaFisicaJuridicaList = new ArrayList<>();
        
        oldPessoaFisica.setApelido(pessoaFisica.getApelido());
        oldPessoaFisica.setCidadeFk(pessoaFisica.getCidadeFk());
        oldPessoaFisica.setCpf(pessoaFisica.getCpf());
        oldPessoaFisica.setEstadoCivilFk(pessoaFisica.getEstadoCivilFk());
        oldPessoaFisica.setEstadoFk(pessoaFisica.getEstadoFk());
        oldPessoaFisica.setInss(pessoaFisica.getInss());
        oldPessoaFisica.setNacionalidadeFk(pessoaFisica.getNacionalidadeFk());
        oldPessoaFisica.setNome(pessoaFisica.getNome());
        oldPessoaFisica.setNomeDaMae(pessoaFisica.getNomeDaMae());
        oldPessoaFisica.setNomeDoConjuge(pessoaFisica.getNomeDoConjuge());
        oldPessoaFisica.setNomeDoPai(pessoaFisica.getNomeDoPai());
        oldPessoaFisica.setObservacoes(pessoaFisica.getObservacoes());
        oldPessoaFisica.setPaisFk(pessoaFisica.getPaisFk());
        oldPessoaFisica.setPessoaFisicaFk(pessoaFisica);
        oldPessoaFisica.setRg(pessoaFisica.getRg());
        oldPessoaFisica.setRgOrgaoEmissor(pessoaFisica.getRgOrgaoEmissor());
        oldPessoaFisica.setRgUfFk(pessoaFisica.getRgUfFk());
        oldPessoaFisica.setSexo(pessoaFisica.getSexo());
        oldPessoaFisica.setTituloDeEleitor(pessoaFisica.getTituloDeEleitor());
        oldPessoaFisica.setUsuarioFk(usuario);
        
        oldEndereco.setBairro(endereco.getBairro());
        oldEndereco.setCep(endereco.getCep());
        oldEndereco.setCidadeFk(endereco.getCidadeFk());
        oldEndereco.setComplemento(endereco.getComplemento());
        oldEndereco.setEndereco(endereco.getEndereco());
        oldEndereco.setEnderecoFk(endereco);
        oldEndereco.setEstadoFk(endereco.getEstadoFk());
        oldEndereco.setIdFk(endereco.getIdFk());
        oldEndereco.setNumero(endereco.getNumero());
        oldEndereco.setTipo(endereco.getTipo());
        oldEndereco.setUsuarioFk(usuario);
        
        for (PessoaFisicaJuridica pfj: pessoaFisicaJuridicaList){
            PessoaFisicaJuridicaHistorico pfjh = new PessoaFisicaJuridicaHistorico();
            pfjh.setCapitalDeParticipacao(pfj.getCapitalDeParticipacao());
            pfjh.setDataDeInicio(pfj.getDataDeInicio());
            pfjh.setDataDeTermino(pfj.getDataDeTermino());
            pfjh.setFuncaoFk(pfj.getFuncaoFk());
            pfjh.setPessoaFisicaFk(pfj.getPessoaFisicaFk());
            pfjh.setPessoaFisicaJuridicaFk(pfj);
            pfjh.setPessoaJuridicaFk(pfj.getPessoaJuridicaFk());
            pfjh.setUsuarioFk(usuario);
            oldPessoaFisicaJuridicaList.add(pfjh);
        }
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

    public List<EnderecoPessoa> getEnderecoPessoaList() {
        return enderecoPessoaList;
    }

    public void setEnderecoPessoaList(List<EnderecoPessoa> enderecoPessoaList) {
        this.enderecoPessoaList = enderecoPessoaList;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public PessoaJuridica getPessoaJuridica() {
        return pessoaJuridica;
    }

    public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }

    public PessoaFisicaJuridica getPessoaFisicaJuridica() {
        return pessoaFisicaJuridica;
    }

    public void setPessoaFisicaJuridica(PessoaFisicaJuridica pessoaFisicaJuridica) {
        this.pessoaFisicaJuridica = pessoaFisicaJuridica;
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

}
