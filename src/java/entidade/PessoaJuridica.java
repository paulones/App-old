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
@Table(name = "pessoa_juridica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PessoaJuridica.findAll", query = "SELECT p FROM PessoaJuridica p"),
    @NamedQuery(name = "PessoaJuridica.findById", query = "SELECT p FROM PessoaJuridica p WHERE p.id = :id"),
    @NamedQuery(name = "PessoaJuridica.findByNome", query = "SELECT p FROM PessoaJuridica p WHERE p.nome = :nome"),
    @NamedQuery(name = "PessoaJuridica.findByNomeFantasia", query = "SELECT p FROM PessoaJuridica p WHERE p.nomeFantasia = :nomeFantasia"),
    @NamedQuery(name = "PessoaJuridica.findByCnpj", query = "SELECT p FROM PessoaJuridica p WHERE p.cnpj = :cnpj"),
    @NamedQuery(name = "PessoaJuridica.findByInscricaoEstadual", query = "SELECT p FROM PessoaJuridica p WHERE p.inscricaoEstadual = :inscricaoEstadual"),
    @NamedQuery(name = "PessoaJuridica.findByInscricaoMunicipal", query = "SELECT p FROM PessoaJuridica p WHERE p.inscricaoMunicipal = :inscricaoMunicipal"),
    @NamedQuery(name = "PessoaJuridica.findBySituacao", query = "SELECT p FROM PessoaJuridica p WHERE p.situacao = :situacao"),
    @NamedQuery(name = "PessoaJuridica.findByMotivoDaDesativacao", query = "SELECT p FROM PessoaJuridica p WHERE p.motivoDaDesativacao = :motivoDaDesativacao"),
    @NamedQuery(name = "PessoaJuridica.findByDataDeCriacao", query = "SELECT p FROM PessoaJuridica p WHERE p.dataDeCriacao = :dataDeCriacao"),
    @NamedQuery(name = "PessoaJuridica.findByGrupoEconomico", query = "SELECT p FROM PessoaJuridica p WHERE p.grupoEconomico = :grupoEconomico"),
    @NamedQuery(name = "PessoaJuridica.findByCnae", query = "SELECT p FROM PessoaJuridica p WHERE p.cnae = :cnae"),
    @NamedQuery(name = "PessoaJuridica.findByNire", query = "SELECT p FROM PessoaJuridica p WHERE p.nire = :nire"),
    @NamedQuery(name = "PessoaJuridica.findByAtividadePrincipal", query = "SELECT p FROM PessoaJuridica p WHERE p.atividadePrincipal = :atividadePrincipal"),
    @NamedQuery(name = "PessoaJuridica.findByAtividadeSecundaria", query = "SELECT p FROM PessoaJuridica p WHERE p.atividadeSecundaria = :atividadeSecundaria"),
    @NamedQuery(name = "PessoaJuridica.findByStatus", query = "SELECT p FROM PessoaJuridica p WHERE p.status = :status")})
public class PessoaJuridica implements Serializable {
    @JoinColumn(name = "instituicao_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Instituicao instituicaoFk;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoaJuridicaSucedidaFk")
    private Collection<PessoaJuridicaSucessaoHistorico> pessoaJuridicaSucessaoHistoricoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoaJuridicaSucessoraFk")
    private Collection<PessoaJuridicaSucessaoHistorico> pessoaJuridicaSucessaoHistoricoCollection1;
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
    @Size(max = 50)
    @Column(name = "nome_fantasia")
    private String nomeFantasia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 14)
    @Column(name = "cnpj")
    private String cnpj;
    @Size(max = 15)
    @Column(name = "inscricao_estadual")
    private String inscricaoEstadual;
    @Size(max = 30)
    @Column(name = "inscricao_municipal")
    private String inscricaoMunicipal;
    @Column(name = "situacao")
    private Character situacao;
    @Size(max = 300)
    @Column(name = "motivo_da_desativacao")
    private String motivoDaDesativacao;
    @Size(max = 10)
    @Column(name = "data_de_criacao")
    private String dataDeCriacao;
    @Size(max = 50)
    @Column(name = "grupo_economico")
    private String grupoEconomico;
    @Size(max = 7)
    @Column(name = "cnae")
    private String cnae;
    @Size(max = 11)
    @Column(name = "nire")
    private String nire;
    @Size(max = 300)
    @Column(name = "atividade_principal")
    private String atividadePrincipal;
    @Size(max = 300)
    @Column(name = "atividade_secundaria")
    private String atividadeSecundaria;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private Character status;
    @JoinColumn(name = "tipo_empresarial_fk", referencedColumnName = "id")
    @ManyToOne
    private TipoEmpresarial tipoEmpresarialFk;
    @JoinColumn(name = "usuario_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioFk;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoaJuridicaFk")
    private Collection<PessoaFisicaJuridica> pessoaFisicaJuridicaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoaJuridicaSucedidaFk")
    private Collection<PessoaJuridicaSucessao> pessoaJuridicaSucessaoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoaJuridicaSucessoraFk")
    private Collection<PessoaJuridicaSucessao> pessoaJuridicaSucessaoCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoaJuridicaPrimariaFk")
    private Collection<PessoaJuridicaJuridicaHistorico> pessoaJuridicaJuridicaHistoricoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoaJuridicaSecundariaFk")
    private Collection<PessoaJuridicaJuridicaHistorico> pessoaJuridicaJuridicaHistoricoCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoaJuridicaPrimariaFk")
    private Collection<PessoaJuridicaJuridica> pessoaJuridicaJuridicaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoaJuridicaSecundariaFk")
    private Collection<PessoaJuridicaJuridica> pessoaJuridicaJuridicaCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoaJuridicaFk")
    private Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollection;
    @OneToMany(mappedBy = "sucessaoFk")
    private Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoaJuridicaFk")
    private Collection<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoCollection;

    public PessoaJuridica() {
    }

    public PessoaJuridica(Integer id) {
        this.id = id;
    }

    public PessoaJuridica(Integer id, String nome, String cnpj, Character status) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.status = status;
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

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }

    public void setInscricaoMunicipal(String inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    public Character getSituacao() {
        return situacao;
    }

    public void setSituacao(Character situacao) {
        this.situacao = situacao;
    }

    public String getMotivoDaDesativacao() {
        return motivoDaDesativacao;
    }

    public void setMotivoDaDesativacao(String motivoDaDesativacao) {
        this.motivoDaDesativacao = motivoDaDesativacao;
    }

    public String getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(String dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }

    public String getGrupoEconomico() {
        return grupoEconomico;
    }

    public void setGrupoEconomico(String grupoEconomico) {
        this.grupoEconomico = grupoEconomico;
    }

    public String getCnae() {
        return cnae;
    }

    public void setCnae(String cnae) {
        this.cnae = cnae;
    }

    public String getNire() {
        return nire;
    }

    public void setNire(String nire) {
        this.nire = nire;
    }

    public String getAtividadePrincipal() {
        return atividadePrincipal;
    }

    public void setAtividadePrincipal(String atividadePrincipal) {
        this.atividadePrincipal = atividadePrincipal;
    }

    public String getAtividadeSecundaria() {
        return atividadeSecundaria;
    }

    public void setAtividadeSecundaria(String atividadeSecundaria) {
        this.atividadeSecundaria = atividadeSecundaria;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public TipoEmpresarial getTipoEmpresarialFk() {
        return tipoEmpresarialFk;
    }

    public void setTipoEmpresarialFk(TipoEmpresarial tipoEmpresarialFk) {
        this.tipoEmpresarialFk = tipoEmpresarialFk;
    }

    public Usuario getUsuarioFk() {
        return usuarioFk;
    }

    public void setUsuarioFk(Usuario usuarioFk) {
        this.usuarioFk = usuarioFk;
    }

    @XmlTransient
    public Collection<PessoaFisicaJuridica> getPessoaFisicaJuridicaCollection() {
        return pessoaFisicaJuridicaCollection;
    }

    public void setPessoaFisicaJuridicaCollection(Collection<PessoaFisicaJuridica> pessoaFisicaJuridicaCollection) {
        this.pessoaFisicaJuridicaCollection = pessoaFisicaJuridicaCollection;
    }

    @XmlTransient
    public Collection<PessoaJuridicaSucessao> getPessoaJuridicaSucessaoCollection() {
        return pessoaJuridicaSucessaoCollection;
    }

    public void setPessoaJuridicaSucessaoCollection(Collection<PessoaJuridicaSucessao> pessoaJuridicaSucessaoCollection) {
        this.pessoaJuridicaSucessaoCollection = pessoaJuridicaSucessaoCollection;
    }

    @XmlTransient
    public Collection<PessoaJuridicaSucessao> getPessoaJuridicaSucessaoCollection1() {
        return pessoaJuridicaSucessaoCollection1;
    }

    public void setPessoaJuridicaSucessaoCollection1(Collection<PessoaJuridicaSucessao> pessoaJuridicaSucessaoCollection1) {
        this.pessoaJuridicaSucessaoCollection1 = pessoaJuridicaSucessaoCollection1;
    }

    @XmlTransient
    public Collection<PessoaJuridicaJuridicaHistorico> getPessoaJuridicaJuridicaHistoricoCollection() {
        return pessoaJuridicaJuridicaHistoricoCollection;
    }

    public void setPessoaJuridicaJuridicaHistoricoCollection(Collection<PessoaJuridicaJuridicaHistorico> pessoaJuridicaJuridicaHistoricoCollection) {
        this.pessoaJuridicaJuridicaHistoricoCollection = pessoaJuridicaJuridicaHistoricoCollection;
    }

    @XmlTransient
    public Collection<PessoaJuridicaJuridicaHistorico> getPessoaJuridicaJuridicaHistoricoCollection1() {
        return pessoaJuridicaJuridicaHistoricoCollection1;
    }

    public void setPessoaJuridicaJuridicaHistoricoCollection1(Collection<PessoaJuridicaJuridicaHistorico> pessoaJuridicaJuridicaHistoricoCollection1) {
        this.pessoaJuridicaJuridicaHistoricoCollection1 = pessoaJuridicaJuridicaHistoricoCollection1;
    }

    @XmlTransient
    public Collection<PessoaJuridicaJuridica> getPessoaJuridicaJuridicaCollection() {
        return pessoaJuridicaJuridicaCollection;
    }

    public void setPessoaJuridicaJuridicaCollection(Collection<PessoaJuridicaJuridica> pessoaJuridicaJuridicaCollection) {
        this.pessoaJuridicaJuridicaCollection = pessoaJuridicaJuridicaCollection;
    }

    @XmlTransient
    public Collection<PessoaJuridicaJuridica> getPessoaJuridicaJuridicaCollection1() {
        return pessoaJuridicaJuridicaCollection1;
    }

    public void setPessoaJuridicaJuridicaCollection1(Collection<PessoaJuridicaJuridica> pessoaJuridicaJuridicaCollection1) {
        this.pessoaJuridicaJuridicaCollection1 = pessoaJuridicaJuridicaCollection1;
    }

    @XmlTransient
    public Collection<PessoaJuridicaHistorico> getPessoaJuridicaHistoricoCollection() {
        return pessoaJuridicaHistoricoCollection;
    }

    public void setPessoaJuridicaHistoricoCollection(Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollection) {
        this.pessoaJuridicaHistoricoCollection = pessoaJuridicaHistoricoCollection;
    }

    @XmlTransient
    public Collection<PessoaJuridicaHistorico> getPessoaJuridicaHistoricoCollection1() {
        return pessoaJuridicaHistoricoCollection1;
    }

    public void setPessoaJuridicaHistoricoCollection1(Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollection1) {
        this.pessoaJuridicaHistoricoCollection1 = pessoaJuridicaHistoricoCollection1;
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
        if (!(object instanceof PessoaJuridica)) {
            return false;
        }
        PessoaJuridica other = (PessoaJuridica) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public List<String> changedValues(Object obj) {
        List<String> list = new ArrayList<>();
        final PessoaJuridica other = (PessoaJuridica) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            list.add("nome");
        }
        if (!Objects.equals(this.nomeFantasia, other.nomeFantasia)) {
            list.add("nomeFantasia");
        }
        if (!Objects.equals(this.cnpj, other.cnpj)) {
            list.add("cnpj");
        }
        if (!Objects.equals(this.inscricaoEstadual, other.inscricaoEstadual)) {
            list.add("inscricaoEstadual");
        }
        if (!Objects.equals(this.inscricaoMunicipal, other.inscricaoMunicipal)) {
            list.add("inscricaoMunicipal");
        }
        if (!Objects.equals(this.situacao, other.situacao)) {
            list.add("situacao");
        }
        if (!Objects.equals(this.motivoDaDesativacao, other.motivoDaDesativacao)) {
            list.add("motivoDaDesativacao");
        }
        if (!Objects.equals(this.dataDeCriacao, other.dataDeCriacao)) {
            list.add("dataDeCriacao");
        }
        if (!Objects.equals(this.grupoEconomico, other.grupoEconomico)) {
            list.add("grupoEconomico");
        }
        if (!Objects.equals(this.cnae, other.cnae)) {
            list.add("cnae");
        }
        if (!Objects.equals(this.nire, other.nire)) {
            list.add("nire");
        }
        if (!Objects.equals(this.atividadePrincipal, other.atividadePrincipal)) {
            list.add("atividadePrincipal");
        }
        if (!Objects.equals(this.atividadeSecundaria, other.atividadeSecundaria)) {
            list.add("atividadeSecundaria");
        }
        if (!Objects.equals(this.tipoEmpresarialFk, other.tipoEmpresarialFk)) {
            list.add("tipoEmpresarialFk");
        }
        return list;
    }

    @Override
    public String toString() {
        return "entidade.PessoaJuridica[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<PessoaJuridicaSucessaoHistorico> getPessoaJuridicaSucessaoHistoricoCollection() {
        return pessoaJuridicaSucessaoHistoricoCollection;
    }

    public void setPessoaJuridicaSucessaoHistoricoCollection(Collection<PessoaJuridicaSucessaoHistorico> pessoaJuridicaSucessaoHistoricoCollection) {
        this.pessoaJuridicaSucessaoHistoricoCollection = pessoaJuridicaSucessaoHistoricoCollection;
    }

    @XmlTransient
    public Collection<PessoaJuridicaSucessaoHistorico> getPessoaJuridicaSucessaoHistoricoCollection1() {
        return pessoaJuridicaSucessaoHistoricoCollection1;
    }

    public void setPessoaJuridicaSucessaoHistoricoCollection1(Collection<PessoaJuridicaSucessaoHistorico> pessoaJuridicaSucessaoHistoricoCollection1) {
        this.pessoaJuridicaSucessaoHistoricoCollection1 = pessoaJuridicaSucessaoHistoricoCollection1;
    }

    public Instituicao getInstituicaoFk() {
        return instituicaoFk;
    }

    public void setInstituicaoFk(Instituicao instituicaoFk) {
        this.instituicaoFk = instituicaoFk;
    }
    
}
