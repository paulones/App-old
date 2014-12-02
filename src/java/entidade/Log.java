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
@Table(name = "log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Log.findAll", query = "SELECT l FROM Log l"),
    @NamedQuery(name = "Log.findById", query = "SELECT l FROM Log l WHERE l.id = :id"),
    @NamedQuery(name = "Log.findByIdFk", query = "SELECT l FROM Log l WHERE l.idFk = :idFk"),
    @NamedQuery(name = "Log.findByTabela", query = "SELECT l FROM Log l WHERE l.tabela = :tabela"),
    @NamedQuery(name = "Log.findByOperacao", query = "SELECT l FROM Log l WHERE l.operacao = :operacao"),
    @NamedQuery(name = "Log.findByDataDeCriacao", query = "SELECT l FROM Log l WHERE l.dataDeCriacao = :dataDeCriacao")})
public class Log implements Serializable {
    @JoinColumn(name = "instituicao_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Instituicao instituicaoFk;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_fk")
    private int idFk;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "tabela")
    private String tabela;
    @Basic(optional = false)
    @NotNull
    @Column(name = "operacao")
    private Character operacao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_de_criacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeCriacao;

    public Log() {
    }

    public Log(Integer id) {
        this.id = id;
    }

    public Log(Integer id, int idFk, String tabela, Character operacao, Date dataDeCriacao) {
        this.id = id;
        this.idFk = idFk;
        this.tabela = tabela;
        this.operacao = operacao;
        this.dataDeCriacao = dataDeCriacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdFk() {
        return idFk;
    }

    public void setIdFk(int idFk) {
        this.idFk = idFk;
    }

    public String getTabela() {
        return tabela;
    }

    public void setTabela(String tabela) {
        this.tabela = tabela;
    }

    public Character getOperacao() {
        return operacao;
    }

    public void setOperacao(Character operacao) {
        this.operacao = operacao;
    }

    public Date getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(Date dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
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
        if (!(object instanceof Log)) {
            return false;
        }
        Log other = (Log) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.Log[ id=" + id + " ]";
    }

    public Instituicao getInstituicaoFk() {
        return instituicaoFk;
    }

    public void setInstituicaoFk(Instituicao instituicaoFk) {
        this.instituicaoFk = instituicaoFk;
    }
    
}
