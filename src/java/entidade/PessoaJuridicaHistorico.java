/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author paulones
 */
@Entity
@Table(name = "pessoa_juridica_historico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PessoaJuridicaHistorico.findAll", query = "SELECT p FROM PessoaJuridicaHistorico p"),
    @NamedQuery(name = "PessoaJuridicaHistorico.findById", query = "SELECT p FROM PessoaJuridicaHistorico p WHERE p.id = :id"),
    @NamedQuery(name = "PessoaJuridicaHistorico.findByNome", query = "SELECT p FROM PessoaJuridicaHistorico p WHERE p.nome = :nome"),
    @NamedQuery(name = "PessoaJuridicaHistorico.findByNomeFantasia", query = "SELECT p FROM PessoaJuridicaHistorico p WHERE p.nomeFantasia = :nomeFantasia"),
    @NamedQuery(name = "PessoaJuridicaHistorico.findByCnpj", query = "SELECT p FROM PessoaJuridicaHistorico p WHERE p.cnpj = :cnpj"),
    @NamedQuery(name = "PessoaJuridicaHistorico.findByIncricaoEstadual", query = "SELECT p FROM PessoaJuridicaHistorico p WHERE p.incricaoEstadual = :incricaoEstadual"),
    @NamedQuery(name = "PessoaJuridicaHistorico.findByIncricaoMunicipal", query = "SELECT p FROM PessoaJuridicaHistorico p WHERE p.incricaoMunicipal = :incricaoMunicipal"),
    @NamedQuery(name = "PessoaJuridicaHistorico.findBySituacao", query = "SELECT p FROM PessoaJuridicaHistorico p WHERE p.situacao = :situacao"),
    @NamedQuery(name = "PessoaJuridicaHistorico.findByMotivoDaDesativacao", query = "SELECT p FROM PessoaJuridicaHistorico p WHERE p.motivoDaDesativacao = :motivoDaDesativacao"),
    @NamedQuery(name = "PessoaJuridicaHistorico.findByDataDeCriacao", query = "SELECT p FROM PessoaJuridicaHistorico p WHERE p.dataDeCriacao = :dataDeCriacao"),
    @NamedQuery(name = "PessoaJuridicaHistorico.findByGrupoEconomico", query = "SELECT p FROM PessoaJuridicaHistorico p WHERE p.grupoEconomico = :grupoEconomico"),
    @NamedQuery(name = "PessoaJuridicaHistorico.findByCnae", query = "SELECT p FROM PessoaJuridicaHistorico p WHERE p.cnae = :cnae"),
    @NamedQuery(name = "PessoaJuridicaHistorico.findByNire", query = "SELECT p FROM PessoaJuridicaHistorico p WHERE p.nire = :nire"),
    @NamedQuery(name = "PessoaJuridicaHistorico.findByAtividadePrincipal", query = "SELECT p FROM PessoaJuridicaHistorico p WHERE p.atividadePrincipal = :atividadePrincipal"),
    @NamedQuery(name = "PessoaJuridicaHistorico.findByAtividadeSecundaria", query = "SELECT p FROM PessoaJuridicaHistorico p WHERE p.atividadeSecundaria = :atividadeSecundaria"),
    @NamedQuery(name = "PessoaJuridicaHistorico.findByDataDeModificacao", query = "SELECT p FROM PessoaJuridicaHistorico p WHERE p.dataDeModificacao = :dataDeModificacao")})
public class PessoaJuridicaHistorico implements Serializable {
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
    @Size(max = 12)
    @Column(name = "incricao_estadual")
    private String incricaoEstadual;
    @Size(max = 30)
    @Column(name = "incricao_municipal")
    private String incricaoMunicipal;
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
    @Column(name = "data_de_modificacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeModificacao;
    @JoinColumn(name = "pessoa_juridica_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PessoaJuridica pessoaJuridicaFk;
    @JoinColumn(name = "sucessao_fk", referencedColumnName = "id")
    @ManyToOne
    private PessoaJuridica sucessaoFk;
    @JoinColumn(name = "tipo_empresarial_fk", referencedColumnName = "id")
    @ManyToOne
    private TipoEmpresarial tipoEmpresarialFk;
    @JoinColumn(name = "usuario_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioFk;

    public PessoaJuridicaHistorico() {
    }

    public PessoaJuridicaHistorico(Integer id) {
        this.id = id;
    }

    public PessoaJuridicaHistorico(Integer id, String nome, String cnpj, Date dataDeModificacao) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.dataDeModificacao = dataDeModificacao;
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

    public String getIncricaoEstadual() {
        return incricaoEstadual;
    }

    public void setIncricaoEstadual(String incricaoEstadual) {
        this.incricaoEstadual = incricaoEstadual;
    }

    public String getIncricaoMunicipal() {
        return incricaoMunicipal;
    }

    public void setIncricaoMunicipal(String incricaoMunicipal) {
        this.incricaoMunicipal = incricaoMunicipal;
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

    public Date getDataDeModificacao() {
        return dataDeModificacao;
    }

    public void setDataDeModificacao(Date dataDeModificacao) {
        this.dataDeModificacao = dataDeModificacao;
    }

    public PessoaJuridica getPessoaJuridicaFk() {
        return pessoaJuridicaFk;
    }

    public void setPessoaJuridicaFk(PessoaJuridica pessoaJuridicaFk) {
        this.pessoaJuridicaFk = pessoaJuridicaFk;
    }

    public PessoaJuridica getSucessaoFk() {
        return sucessaoFk;
    }

    public void setSucessaoFk(PessoaJuridica sucessaoFk) {
        this.sucessaoFk = sucessaoFk;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PessoaJuridicaHistorico)) {
            return false;
        }
        PessoaJuridicaHistorico other = (PessoaJuridicaHistorico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.PessoaJuridicaHistorico[ id=" + id + " ]";
    }
    
}
