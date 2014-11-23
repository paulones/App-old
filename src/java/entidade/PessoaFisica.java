/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author paulones
 */
@Entity
@Table(name = "pessoa_fisica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PessoaFisica.findAll", query = "SELECT p FROM PessoaFisica p"),
    @NamedQuery(name = "PessoaFisica.findById", query = "SELECT p FROM PessoaFisica p WHERE p.id = :id"),
    @NamedQuery(name = "PessoaFisica.findByNome", query = "SELECT p FROM PessoaFisica p WHERE p.nome = :nome"),
    @NamedQuery(name = "PessoaFisica.findByApelido", query = "SELECT p FROM PessoaFisica p WHERE p.apelido = :apelido"),
    @NamedQuery(name = "PessoaFisica.findBySexo", query = "SELECT p FROM PessoaFisica p WHERE p.sexo = :sexo"),
    @NamedQuery(name = "PessoaFisica.findByCpf", query = "SELECT p FROM PessoaFisica p WHERE p.cpf = :cpf"),
    @NamedQuery(name = "PessoaFisica.findByRg", query = "SELECT p FROM PessoaFisica p WHERE p.rg = :rg"),
    @NamedQuery(name = "PessoaFisica.findByRgOrgaoEmissor", query = "SELECT p FROM PessoaFisica p WHERE p.rgOrgaoEmissor = :rgOrgaoEmissor"),
    @NamedQuery(name = "PessoaFisica.findByTituloDeEleitor", query = "SELECT p FROM PessoaFisica p WHERE p.tituloDeEleitor = :tituloDeEleitor"),
    @NamedQuery(name = "PessoaFisica.findByInss", query = "SELECT p FROM PessoaFisica p WHERE p.inss = :inss"),
    @NamedQuery(name = "PessoaFisica.findByNomeDoPai", query = "SELECT p FROM PessoaFisica p WHERE p.nomeDoPai = :nomeDoPai"),
    @NamedQuery(name = "PessoaFisica.findByNomeDaMae", query = "SELECT p FROM PessoaFisica p WHERE p.nomeDaMae = :nomeDaMae"),
    @NamedQuery(name = "PessoaFisica.findByNomeDoConjuge", query = "SELECT p FROM PessoaFisica p WHERE p.nomeDoConjuge = :nomeDoConjuge"),
    @NamedQuery(name = "PessoaFisica.findByObservacoes", query = "SELECT p FROM PessoaFisica p WHERE p.observacoes = :observacoes"),
    @NamedQuery(name = "PessoaFisica.findByStatus", query = "SELECT p FROM PessoaFisica p WHERE p.status = :status")})
public class PessoaFisica implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoaFisicaPrimariaFk")
    private Collection<PessoaFisicaFisica> pessoaFisicaFisicaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoaFisicaSecundariaFk")
    private Collection<PessoaFisicaFisica> pessoaFisicaFisicaCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoaFisicaPrimariaFk")
    private Collection<PessoaFisicaFisicaHistorico> pessoaFisicaFisicaHistoricoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoaFisicaSecundariaFk")
    private Collection<PessoaFisicaFisicaHistorico> pessoaFisicaFisicaHistoricoCollection1;
    @Column(name = "zona")
    private Integer zona;
    @Column(name = "secao")
    private Integer secao;
    @Size(max = 80)
    @Column(name = "local")
    private String local;
    @Size(max = 100)
    @Column(name = "endereco")
    private String endereco;
    @JoinColumn(name = "cidade_eleitoral_fk", referencedColumnName = "id")
    @ManyToOne
    private Cidade cidadeEleitoralFk;
    @JoinColumn(name = "estado_eleitoral_fk", referencedColumnName = "id")
    @ManyToOne
    private Estado estadoEleitoralFk;
    @JoinColumn(name = "usuario_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioFk;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nome")
    private String nome;
    @Size(max = 30)
    @Column(name = "apelido")
    private String apelido;
    @Column(name = "sexo")
    private Character sexo;
    @Size(max = 11)
    @Column(name = "cpf")
    private String cpf;
    @Size(max = 15)
    @Column(name = "rg")
    private String rg;
    @Size(max = 4)
    @Column(name = "rg_orgao_emissor")
    private String rgOrgaoEmissor;
    @Size(max = 13)
    @Column(name = "titulo_de_eleitor")
    private String tituloDeEleitor;
    @Size(max = 15)
    @Column(name = "inss")
    private String inss;
    @Size(max = 50)
    @Column(name = "nome_do_pai")
    private String nomeDoPai;
    @Size(max = 50)
    @Column(name = "nome_da_mae")
    private String nomeDaMae;
    @Size(max = 50)
    @Column(name = "nome_do_conjuge")
    private String nomeDoConjuge;
    @Size(max = 300)
    @Column(name = "observacoes")
    private String observacoes;
    @Column(name = "status")
    @NotNull
    private Character status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoaFisicaFk")
    private Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoaFisicaFk")
    private Collection<PessoaFisicaJuridica> pessoaFisicaJuridicaCollection;
    @JoinColumn(name = "cidade_fk", referencedColumnName = "id")
    @ManyToOne
    private Cidade cidadeFk;
    @JoinColumn(name = "estado_fk", referencedColumnName = "id")
    @ManyToOne
    private Estado estadoFk;
    @JoinColumn(name = "rg_uf_fk", referencedColumnName = "id")
    @ManyToOne
    private Estado rgUfFk;
    @JoinColumn(name = "estado_civil_fk", referencedColumnName = "id")
    @ManyToOne
    private EstadoCivil estadoCivilFk;
    @JoinColumn(name = "nacionalidade_fk", referencedColumnName = "id")
    @ManyToOne
    private Nacionalidade nacionalidadeFk;
    @JoinColumn(name = "pais_fk", referencedColumnName = "id")
    @ManyToOne
    private Pais paisFk;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoaFisicaFk")
    private Collection<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoCollection;

    public PessoaFisica() {
    }

    public PessoaFisica(Integer id) {
        this.id = id;
    }

    public PessoaFisica(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getRgOrgaoEmissor() {
        return rgOrgaoEmissor;
    }

    public void setRgOrgaoEmissor(String rgOrgaoEmissor) {
        this.rgOrgaoEmissor = rgOrgaoEmissor;
    }

    public String getTituloDeEleitor() {
        return tituloDeEleitor;
    }

    public void setTituloDeEleitor(String tituloDeEleitor) {
        this.tituloDeEleitor = tituloDeEleitor;
    }

    public String getInss() {
        return inss;
    }

    public void setInss(String inss) {
        this.inss = inss;
    }

    public String getNomeDoPai() {
        return nomeDoPai;
    }

    public void setNomeDoPai(String nomeDoPai) {
        this.nomeDoPai = nomeDoPai;
    }

    public String getNomeDaMae() {
        return nomeDaMae;
    }

    public void setNomeDaMae(String nomeDaMae) {
        this.nomeDaMae = nomeDaMae;
    }

    public String getNomeDoConjuge() {
        return nomeDoConjuge;
    }

    public void setNomeDoConjuge(String nomeDoConjuge) {
        this.nomeDoConjuge = nomeDoConjuge;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<PessoaFisicaHistorico> getPessoaFisicaHistoricoCollection() {
        return pessoaFisicaHistoricoCollection;
    }

    public void setPessoaFisicaHistoricoCollection(Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollection) {
        this.pessoaFisicaHistoricoCollection = pessoaFisicaHistoricoCollection;
    }

    @XmlTransient
    public Collection<PessoaFisicaJuridica> getPessoaFisicaJuridicaCollection() {
        return pessoaFisicaJuridicaCollection;
    }

    public void setPessoaFisicaJuridicaCollection(Collection<PessoaFisicaJuridica> pessoaFisicaJuridicaCollection) {
        this.pessoaFisicaJuridicaCollection = pessoaFisicaJuridicaCollection;
    }

    public Cidade getCidadeFk() {
        return cidadeFk;
    }

    public void setCidadeFk(Cidade cidadeFk) {
        this.cidadeFk = cidadeFk;
    }

    public Estado getEstadoFk() {
        return estadoFk;
    }

    public void setEstadoFk(Estado estadoFk) {
        this.estadoFk = estadoFk;
    }

    public Estado getRgUfFk() {
        return rgUfFk;
    }

    public void setRgUfFk(Estado rgUfFk) {
        this.rgUfFk = rgUfFk;
    }

    public EstadoCivil getEstadoCivilFk() {
        return estadoCivilFk;
    }

    public void setEstadoCivilFk(EstadoCivil estadoCivilFk) {
        this.estadoCivilFk = estadoCivilFk;
    }

    public Nacionalidade getNacionalidadeFk() {
        return nacionalidadeFk;
    }

    public void setNacionalidadeFk(Nacionalidade nacionalidadeFk) {
        this.nacionalidadeFk = nacionalidadeFk;
    }

    public Pais getPaisFk() {
        return paisFk;
    }

    public void setPaisFk(Pais paisFk) {
        this.paisFk = paisFk;
    }

    @XmlTransient
    public Collection<PessoaFisicaJuridicaHistorico> getPessoaFisicaJuridicaHistoricoCollection() {
        return pessoaFisicaJuridicaHistoricoCollection;
    }

    public void setPessoaFisicaJuridicaHistoricoCollection(Collection<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoCollection) {
        this.pessoaFisicaJuridicaHistoricoCollection = pessoaFisicaJuridicaHistoricoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PessoaFisica)) {
            return false;
        }
        PessoaFisica other = (PessoaFisica) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    public List<String> changedValues(Object obj) {
        List<String> list = new ArrayList<>();
        final PessoaFisica other = (PessoaFisica) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            list.add("nome");
        }
        if (!Objects.equals(this.apelido, other.apelido)) {
            list.add("apelido");
        }
        if (!Objects.equals(this.sexo, other.sexo)) {
            list.add("sexo");
        }
        if (!Objects.equals(this.cpf, other.cpf)) {
            list.add("cpf");
        }
        if (!Objects.equals(this.rg, other.rg)) {
            list.add("rg");
        }
        if (!Objects.equals(this.rgOrgaoEmissor, other.rgOrgaoEmissor)) {
            list.add("rgOrgaoEmissor");
        }
        if (!Objects.equals(this.tituloDeEleitor, other.tituloDeEleitor)) {
            list.add("tituloDeEleitor");
        }
        if (!Objects.equals(this.inss, other.inss)) {
            list.add("inss");
        }
        if (!Objects.equals(this.nomeDoPai, other.nomeDoPai)) {
            list.add("nomeDoPai");
        }
        if (!Objects.equals(this.nomeDaMae, other.nomeDaMae)) {
            list.add("nomeDaMae");
        }
        if (!Objects.equals(this.nomeDoConjuge, other.nomeDoConjuge)) {
            list.add("nomeDoConjuge");
        }
        if (!Objects.equals(this.observacoes, other.observacoes)) {
            list.add("observacoes");
        }
        if (!Objects.equals(this.cidadeFk, other.cidadeFk)) {
            list.add("cidadeFk");
        }
        if (!Objects.equals(this.estadoFk, other.estadoFk)) {
            list.add("estadoFk");
        }
        if (!Objects.equals(this.rgUfFk, other.rgUfFk)) {
            list.add("rgUfFk");
        }
        if (!Objects.equals(this.estadoCivilFk, other.estadoCivilFk)) {
            list.add("estadoCivilFk");
        }
        if (!Objects.equals(this.nacionalidadeFk, other.nacionalidadeFk)) {
            list.add("nacionalidadeFk");
        }
        if (!Objects.equals(this.paisFk, other.paisFk)) {
            list.add("paisFk");
        }
        if (!Objects.equals(this.cidadeEleitoralFk, other.cidadeEleitoralFk)) {
            list.add("cidadeEleitoralFk");
        }
        if (!Objects.equals(this.estadoEleitoralFk, other.estadoEleitoralFk)) {
            list.add("estadoEleitoralFk");
        }
        if (!Objects.equals(this.zona, other.zona)) {
            list.add("zona");
        }
        if (!Objects.equals(this.secao, other.secao)) {
            list.add("secao");
        }
        if (!Objects.equals(this.endereco, other.endereco)) {
            list.add("endereco");
        }
        if (!Objects.equals(this.local, other.local)) {
            list.add("local");
        }
        return list;
    }
    

    @Override
    public String toString() {
        return "entidade.PessoaFisica[ id=" + id + " ]";
    }

    public Usuario getUsuarioFk() {
        return usuarioFk;
    }

    public void setUsuarioFk(Usuario usuarioFk) {
        this.usuarioFk = usuarioFk;
    }

    public Integer getZona() {
        return zona;
    }

    public void setZona(Integer zona) {
        this.zona = zona;
    }

    public Integer getSecao() {
        return secao;
    }

    public void setSecao(Integer secao) {
        this.secao = secao;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Cidade getCidadeEleitoralFk() {
        return cidadeEleitoralFk;
    }

    public void setCidadeEleitoralFk(Cidade cidadeEleitoralFk) {
        this.cidadeEleitoralFk = cidadeEleitoralFk;
    }

    public Estado getEstadoEleitoralFk() {
        return estadoEleitoralFk;
    }

    public void setEstadoEleitoralFk(Estado estadoEleitoralFk) {
        this.estadoEleitoralFk = estadoEleitoralFk;
    }

    @XmlTransient
    public Collection<PessoaFisicaFisica> getPessoaFisicaFisicaCollection() {
        return pessoaFisicaFisicaCollection;
    }

    public void setPessoaFisicaFisicaCollection(Collection<PessoaFisicaFisica> pessoaFisicaFisicaCollection) {
        this.pessoaFisicaFisicaCollection = pessoaFisicaFisicaCollection;
    }

    @XmlTransient
    public Collection<PessoaFisicaFisica> getPessoaFisicaFisicaCollection1() {
        return pessoaFisicaFisicaCollection1;
    }

    public void setPessoaFisicaFisicaCollection1(Collection<PessoaFisicaFisica> pessoaFisicaFisicaCollection1) {
        this.pessoaFisicaFisicaCollection1 = pessoaFisicaFisicaCollection1;
    }

    @XmlTransient
    public Collection<PessoaFisicaFisicaHistorico> getPessoaFisicaFisicaHistoricoCollection() {
        return pessoaFisicaFisicaHistoricoCollection;
    }

    public void setPessoaFisicaFisicaHistoricoCollection(Collection<PessoaFisicaFisicaHistorico> pessoaFisicaFisicaHistoricoCollection) {
        this.pessoaFisicaFisicaHistoricoCollection = pessoaFisicaFisicaHistoricoCollection;
    }

    @XmlTransient
    public Collection<PessoaFisicaFisicaHistorico> getPessoaFisicaFisicaHistoricoCollection1() {
        return pessoaFisicaFisicaHistoricoCollection1;
    }

    public void setPessoaFisicaFisicaHistoricoCollection1(Collection<PessoaFisicaFisicaHistorico> pessoaFisicaFisicaHistoricoCollection1) {
        this.pessoaFisicaFisicaHistoricoCollection1 = pessoaFisicaFisicaHistoricoCollection1;
    }
    
}
