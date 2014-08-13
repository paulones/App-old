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
@Table(name = "pessoa_fisica_historico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PessoaFisicaHistorico.findAll", query = "SELECT p FROM PessoaFisicaHistorico p"),
    @NamedQuery(name = "PessoaFisicaHistorico.findById", query = "SELECT p FROM PessoaFisicaHistorico p WHERE p.id = :id"),
    @NamedQuery(name = "PessoaFisicaHistorico.findByNome", query = "SELECT p FROM PessoaFisicaHistorico p WHERE p.nome = :nome"),
    @NamedQuery(name = "PessoaFisicaHistorico.findByApelido", query = "SELECT p FROM PessoaFisicaHistorico p WHERE p.apelido = :apelido"),
    @NamedQuery(name = "PessoaFisicaHistorico.findBySexo", query = "SELECT p FROM PessoaFisicaHistorico p WHERE p.sexo = :sexo"),
    @NamedQuery(name = "PessoaFisicaHistorico.findByCpf", query = "SELECT p FROM PessoaFisicaHistorico p WHERE p.cpf = :cpf"),
    @NamedQuery(name = "PessoaFisicaHistorico.findByRg", query = "SELECT p FROM PessoaFisicaHistorico p WHERE p.rg = :rg"),
    @NamedQuery(name = "PessoaFisicaHistorico.findByRgOrgaoEmissor", query = "SELECT p FROM PessoaFisicaHistorico p WHERE p.rgOrgaoEmissor = :rgOrgaoEmissor"),
    @NamedQuery(name = "PessoaFisicaHistorico.findByTituloDeEleitor", query = "SELECT p FROM PessoaFisicaHistorico p WHERE p.tituloDeEleitor = :tituloDeEleitor"),
    @NamedQuery(name = "PessoaFisicaHistorico.findByInss", query = "SELECT p FROM PessoaFisicaHistorico p WHERE p.inss = :inss"),
    @NamedQuery(name = "PessoaFisicaHistorico.findByNomeDoPai", query = "SELECT p FROM PessoaFisicaHistorico p WHERE p.nomeDoPai = :nomeDoPai"),
    @NamedQuery(name = "PessoaFisicaHistorico.findByNomeDaMae", query = "SELECT p FROM PessoaFisicaHistorico p WHERE p.nomeDaMae = :nomeDaMae"),
    @NamedQuery(name = "PessoaFisicaHistorico.findByNomeDoConjuge", query = "SELECT p FROM PessoaFisicaHistorico p WHERE p.nomeDoConjuge = :nomeDoConjuge"),
    @NamedQuery(name = "PessoaFisicaHistorico.findByObservacoes", query = "SELECT p FROM PessoaFisicaHistorico p WHERE p.observacoes = :observacoes"),
    @NamedQuery(name = "PessoaFisicaHistorico.findByDataDeModificacao", query = "SELECT p FROM PessoaFisicaHistorico p WHERE p.dataDeModificacao = :dataDeModificacao")})
public class PessoaFisicaHistorico implements Serializable {
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
    @Basic(optional = false)
    @Column(name = "data_de_modificacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeModificacao;
    @JoinColumn(name = "cidade_fk", referencedColumnName = "id")
    @ManyToOne
    private Cidade cidadeFk;
    @JoinColumn(name = "rg_uf_fk", referencedColumnName = "id")
    @ManyToOne
    private Estado rgUfFk;
    @JoinColumn(name = "estado_fk", referencedColumnName = "id")
    @ManyToOne
    private Estado estadoFk;
    @JoinColumn(name = "estado_civil_fk", referencedColumnName = "id")
    @ManyToOne
    private EstadoCivil estadoCivilFk;
    @JoinColumn(name = "nacionalidade_fk", referencedColumnName = "id")
    @ManyToOne
    private Nacionalidade nacionalidadeFk;
    @JoinColumn(name = "pais_fk", referencedColumnName = "id")
    @ManyToOne
    private Pais paisFk;
    @JoinColumn(name = "pessoa_fisica_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PessoaFisica pessoaFisicaFk;
    @JoinColumn(name = "usuario_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioFk;

    public PessoaFisicaHistorico() {
    }

    public PessoaFisicaHistorico(Integer id) {
        this.id = id;
    }

    public PessoaFisicaHistorico(Integer id, String nome, Date dataDeModificacao) {
        this.id = id;
        this.nome = nome;
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

    public Date getDataDeModificacao() {
        return dataDeModificacao;
    }

    public void setDataDeModificacao(Date dataDeModificacao) {
        this.dataDeModificacao = dataDeModificacao;
    }

    public Cidade getCidadeFk() {
        return cidadeFk;
    }

    public void setCidadeFk(Cidade cidadeFk) {
        this.cidadeFk = cidadeFk;
    }

    public Estado getRgUfFk() {
        return rgUfFk;
    }

    public void setRgUfFk(Estado rgUfFk) {
        this.rgUfFk = rgUfFk;
    }

    public Estado getEstadoFk() {
        return estadoFk;
    }

    public void setEstadoFk(Estado estadoFk) {
        this.estadoFk = estadoFk;
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

    public PessoaFisica getPessoaFisicaFk() {
        return pessoaFisicaFk;
    }

    public void setPessoaFisicaFk(PessoaFisica pessoaFisicaFk) {
        this.pessoaFisicaFk = pessoaFisicaFk;
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
        if (!(object instanceof PessoaFisicaHistorico)) {
            return false;
        }
        PessoaFisicaHistorico other = (PessoaFisicaHistorico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.PessoaFisicaHistorico[ id=" + id + " ]";
    }
    
}
