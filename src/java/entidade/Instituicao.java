/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Pedro
 */
@Entity
@Table(name = "instituicao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Instituicao.findAll", query = "SELECT i FROM Instituicao i"),
    @NamedQuery(name = "Instituicao.findById", query = "SELECT i FROM Instituicao i WHERE i.id = :id"),
    @NamedQuery(name = "Instituicao.findByCnpj", query = "SELECT i FROM Instituicao i WHERE i.cnpj = :cnpj"),
    @NamedQuery(name = "Instituicao.findByChave", query = "SELECT i FROM Instituicao i WHERE i.chave = :chave"),
    @NamedQuery(name = "Instituicao.findByUltimoLogin", query = "SELECT i FROM Instituicao i WHERE i.ultimoLogin = :ultimoLogin"),
    @NamedQuery(name = "Instituicao.findByRazaoSocial", query = "SELECT i FROM Instituicao i WHERE i.razaoSocial = :razaoSocial")})
public class Instituicao implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instituicaoFk")
    private Collection<PessoaFisica> pessoaFisicaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instituicaoFk")
    private Collection<ProcessoJudicial> processoJudicialCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instituicaoFk")
    private Collection<PessoaJuridica> pessoaJuridicaCollection;
    @JoinColumn(name = "estado_fk", referencedColumnName = "id")
    @ManyToOne
    private Estado estadoFk;
    @JoinColumn(name = "cidade_fk", referencedColumnName = "id")
    @ManyToOne
    private Cidade cidadeFk;
    @Size(max = 50)
    @Column(name = "ultimo_login")
    private String ultimoLogin;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 14)
    @Column(name = "cnpj")
    private String cnpj;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "chave")
    private String chave;
    @Size(max = 150)
    @Column(name = "razao_social")
    private String razaoSocial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instituicaoFk")
    private Collection<Autorizacao> autorizacaoCollection;

    public Instituicao() {
    }

    public Instituicao(Integer id) {
        this.id = id;
    }

    public Instituicao(Integer id, String cnpj, String chave) {
        this.id = id;
        this.cnpj = cnpj;
        this.chave = chave;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    @XmlTransient
    public Collection<Autorizacao> getAutorizacaoCollection() {
        return autorizacaoCollection;
    }

    public void setAutorizacaoCollection(Collection<Autorizacao> autorizacaoCollection) {
        this.autorizacaoCollection = autorizacaoCollection;
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
        if (!(object instanceof Instituicao)) {
            return false;
        }
        Instituicao other = (Instituicao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.Instituicao[ id=" + id + " ]";
    }

    public String getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(String ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    public Estado getEstadoFk() {
        return estadoFk;
    }

    public void setEstadoFk(Estado estadoFk) {
        this.estadoFk = estadoFk;
    }

    public Cidade getCidadeFk() {
        return cidadeFk;
    }

    public void setCidadeFk(Cidade cidadeFk) {
        this.cidadeFk = cidadeFk;
    }

    @XmlTransient
    public Collection<PessoaJuridica> getPessoaJuridicaCollection() {
        return pessoaJuridicaCollection;
    }

    public void setPessoaJuridicaCollection(Collection<PessoaJuridica> pessoaJuridicaCollection) {
        this.pessoaJuridicaCollection = pessoaJuridicaCollection;
    }

    @XmlTransient
    public Collection<PessoaFisica> getPessoaFisicaCollection() {
        return pessoaFisicaCollection;
    }

    public void setPessoaFisicaCollection(Collection<PessoaFisica> pessoaFisicaCollection) {
        this.pessoaFisicaCollection = pessoaFisicaCollection;
    }

    @XmlTransient
    public Collection<ProcessoJudicial> getProcessoJudicialCollection() {
        return processoJudicialCollection;
    }

    public void setProcessoJudicialCollection(Collection<ProcessoJudicial> processoJudicialCollection) {
        this.processoJudicialCollection = processoJudicialCollection;
    }
    
}
