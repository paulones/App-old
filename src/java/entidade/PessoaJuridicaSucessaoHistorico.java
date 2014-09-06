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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author paulones
 */
@Entity
@Table(name = "pessoa_juridica_sucessao_historico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PessoaJuridicaSucessaoHistorico.findAll", query = "SELECT p FROM PessoaJuridicaSucessaoHistorico p"),
    @NamedQuery(name = "PessoaJuridicaSucessaoHistorico.findById", query = "SELECT p FROM PessoaJuridicaSucessaoHistorico p WHERE p.id = :id"),
    @NamedQuery(name = "PessoaJuridicaSucessaoHistorico.findByDataDeSucessao", query = "SELECT p FROM PessoaJuridicaSucessaoHistorico p WHERE p.dataDeSucessao = :dataDeSucessao"),
    @NamedQuery(name = "PessoaJuridicaSucessaoHistorico.findByDataDeModificacao", query = "SELECT p FROM PessoaJuridicaSucessaoHistorico p WHERE p.dataDeModificacao = :dataDeModificacao")})
public class PessoaJuridicaSucessaoHistorico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_de_sucessao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeSucessao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_de_modificacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeModificacao;
    @JoinColumn(name = "pessoa_juridica_sucedida_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PessoaJuridica pessoaJuridicaSucedidaFk;
    @JoinColumn(name = "pessoa_juridica_sucessora_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PessoaJuridica pessoaJuridicaSucessoraFk;
    @JoinColumn(name = "pessoa_juridica_sucessao_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PessoaJuridicaSucessao pessoaJuridicaSucessaoFk;
    @JoinColumn(name = "usuario_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioFk;

    public PessoaJuridicaSucessaoHistorico() {
    }

    public PessoaJuridicaSucessaoHistorico(Integer id) {
        this.id = id;
    }

    public PessoaJuridicaSucessaoHistorico(Integer id, Date dataDeSucessao, Date dataDeModificacao) {
        this.id = id;
        this.dataDeSucessao = dataDeSucessao;
        this.dataDeModificacao = dataDeModificacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataDeSucessao() {
        return dataDeSucessao;
    }

    public void setDataDeSucessao(Date dataDeSucessao) {
        this.dataDeSucessao = dataDeSucessao;
    }

    public Date getDataDeModificacao() {
        return dataDeModificacao;
    }

    public void setDataDeModificacao(Date dataDeModificacao) {
        this.dataDeModificacao = dataDeModificacao;
    }

    public PessoaJuridica getPessoaJuridicaSucedidaFk() {
        return pessoaJuridicaSucedidaFk;
    }

    public void setPessoaJuridicaSucedidaFk(PessoaJuridica pessoaJuridicaSucedidaFk) {
        this.pessoaJuridicaSucedidaFk = pessoaJuridicaSucedidaFk;
    }

    public PessoaJuridica getPessoaJuridicaSucessoraFk() {
        return pessoaJuridicaSucessoraFk;
    }

    public void setPessoaJuridicaSucessoraFk(PessoaJuridica pessoaJuridicaSucessoraFk) {
        this.pessoaJuridicaSucessoraFk = pessoaJuridicaSucessoraFk;
    }

    public PessoaJuridicaSucessao getPessoaJuridicaSucessaoFk() {
        return pessoaJuridicaSucessaoFk;
    }

    public void setPessoaJuridicaSucessaoFk(PessoaJuridicaSucessao pessoaJuridicaSucessaoFk) {
        this.pessoaJuridicaSucessaoFk = pessoaJuridicaSucessaoFk;
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
        if (!(object instanceof PessoaJuridicaSucessaoHistorico)) {
            return false;
        }
        PessoaJuridicaSucessaoHistorico other = (PessoaJuridicaSucessaoHistorico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.PessoaJuridicaSucessaoHistorico[ id=" + id + " ]";
    }
    
}
