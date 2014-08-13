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
@Table(name = "pessoa_fisica_juridica_historico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PessoaFisicaJuridicaHistorico.findAll", query = "SELECT p FROM PessoaFisicaJuridicaHistorico p"),
    @NamedQuery(name = "PessoaFisicaJuridicaHistorico.findById", query = "SELECT p FROM PessoaFisicaJuridicaHistorico p WHERE p.id = :id"),
    @NamedQuery(name = "PessoaFisicaJuridicaHistorico.findByCapitalDeParticipacao", query = "SELECT p FROM PessoaFisicaJuridicaHistorico p WHERE p.capitalDeParticipacao = :capitalDeParticipacao"),
    @NamedQuery(name = "PessoaFisicaJuridicaHistorico.findByDataDeInicio", query = "SELECT p FROM PessoaFisicaJuridicaHistorico p WHERE p.dataDeInicio = :dataDeInicio"),
    @NamedQuery(name = "PessoaFisicaJuridicaHistorico.findByDataDeTermino", query = "SELECT p FROM PessoaFisicaJuridicaHistorico p WHERE p.dataDeTermino = :dataDeTermino"),
    @NamedQuery(name = "PessoaFisicaJuridicaHistorico.findByDataDeModificacao", query = "SELECT p FROM PessoaFisicaJuridicaHistorico p WHERE p.dataDeModificacao = :dataDeModificacao")})
public class PessoaFisicaJuridicaHistorico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "capital_de_participacao")
    private Float capitalDeParticipacao;
    @Size(max = 10)
    @Column(name = "data_de_inicio")
    private String dataDeInicio;
    @Size(max = 10)
    @Column(name = "data_de_termino")
    private String dataDeTermino;
    @Basic(optional = false)
    @Column(name = "data_de_modificacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeModificacao;
    @JoinColumn(name = "funcao_fk", referencedColumnName = "id")
    @ManyToOne
    private Funcao funcaoFk;
    @JoinColumn(name = "pessoa_fisica_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PessoaFisica pessoaFisicaFk;
    @JoinColumn(name = "pessoa_fisica_juridica_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PessoaFisicaJuridica pessoaFisicaJuridicaFk;
    @JoinColumn(name = "pessoa_juridica_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PessoaJuridica pessoaJuridicaFk;
    @JoinColumn(name = "usuario_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioFk;

    public PessoaFisicaJuridicaHistorico() {
    }

    public PessoaFisicaJuridicaHistorico(Integer id) {
        this.id = id;
    }

    public PessoaFisicaJuridicaHistorico(Integer id, Date dataDeModificacao) {
        this.id = id;
        this.dataDeModificacao = dataDeModificacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getCapitalDeParticipacao() {
        return capitalDeParticipacao;
    }

    public void setCapitalDeParticipacao(Float capitalDeParticipacao) {
        this.capitalDeParticipacao = capitalDeParticipacao;
    }

    public String getDataDeInicio() {
        return dataDeInicio;
    }

    public void setDataDeInicio(String dataDeInicio) {
        this.dataDeInicio = dataDeInicio;
    }

    public String getDataDeTermino() {
        return dataDeTermino;
    }

    public void setDataDeTermino(String dataDeTermino) {
        this.dataDeTermino = dataDeTermino;
    }

    public Date getDataDeModificacao() {
        return dataDeModificacao;
    }

    public void setDataDeModificacao(Date dataDeModificacao) {
        this.dataDeModificacao = dataDeModificacao;
    }

    public Funcao getFuncaoFk() {
        return funcaoFk;
    }

    public void setFuncaoFk(Funcao funcaoFk) {
        this.funcaoFk = funcaoFk;
    }

    public PessoaFisica getPessoaFisicaFk() {
        return pessoaFisicaFk;
    }

    public void setPessoaFisicaFk(PessoaFisica pessoaFisicaFk) {
        this.pessoaFisicaFk = pessoaFisicaFk;
    }

    public PessoaFisicaJuridica getPessoaFisicaJuridicaFk() {
        return pessoaFisicaJuridicaFk;
    }

    public void setPessoaFisicaJuridicaFk(PessoaFisicaJuridica pessoaFisicaJuridicaFk) {
        this.pessoaFisicaJuridicaFk = pessoaFisicaJuridicaFk;
    }

    public PessoaJuridica getPessoaJuridicaFk() {
        return pessoaJuridicaFk;
    }

    public void setPessoaJuridicaFk(PessoaJuridica pessoaJuridicaFk) {
        this.pessoaJuridicaFk = pessoaJuridicaFk;
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
        if (!(object instanceof PessoaFisicaJuridicaHistorico)) {
            return false;
        }
        PessoaFisicaJuridicaHistorico other = (PessoaFisicaJuridicaHistorico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.PessoaFisicaJuridicaHistorico[ id=" + id + " ]";
    }
    
}
