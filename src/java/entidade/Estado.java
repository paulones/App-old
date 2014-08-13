/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Collection;
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
@Table(name = "estado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estado.findAll", query = "SELECT e FROM Estado e"),
    @NamedQuery(name = "Estado.findById", query = "SELECT e FROM Estado e WHERE e.id = :id"),
    @NamedQuery(name = "Estado.findByNome", query = "SELECT e FROM Estado e WHERE e.nome = :nome"),
    @NamedQuery(name = "Estado.findByUf", query = "SELECT e FROM Estado e WHERE e.uf = :uf")})
public class Estado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "uf")
    private String uf;
    @OneToMany(mappedBy = "rgUfFk")
    private Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollection;
    @OneToMany(mappedBy = "estadoFk")
    private Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoFk")
    private Collection<Cidade> cidadeCollection;
    @JoinColumn(name = "pais_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Pais paisFk;
    @OneToMany(mappedBy = "estadoFk")
    private Collection<Endereco> enderecoCollection;
    @OneToMany(mappedBy = "estadoFk")
    private Collection<PessoaFisica> pessoaFisicaCollection;
    @OneToMany(mappedBy = "rgUfFk")
    private Collection<PessoaFisica> pessoaFisicaCollection1;
    @OneToMany(mappedBy = "estadoFk")
    private Collection<EnderecoHistorico> enderecoHistoricoCollection;

    public Estado() {
    }

    public Estado(Integer id) {
        this.id = id;
    }

    public Estado(Integer id, String nome, String uf) {
        this.id = id;
        this.nome = nome;
        this.uf = uf;
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

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @XmlTransient
    public Collection<PessoaFisicaHistorico> getPessoaFisicaHistoricoCollection() {
        return pessoaFisicaHistoricoCollection;
    }

    public void setPessoaFisicaHistoricoCollection(Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollection) {
        this.pessoaFisicaHistoricoCollection = pessoaFisicaHistoricoCollection;
    }

    @XmlTransient
    public Collection<PessoaFisicaHistorico> getPessoaFisicaHistoricoCollection1() {
        return pessoaFisicaHistoricoCollection1;
    }

    public void setPessoaFisicaHistoricoCollection1(Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollection1) {
        this.pessoaFisicaHistoricoCollection1 = pessoaFisicaHistoricoCollection1;
    }

    @XmlTransient
    public Collection<Cidade> getCidadeCollection() {
        return cidadeCollection;
    }

    public void setCidadeCollection(Collection<Cidade> cidadeCollection) {
        this.cidadeCollection = cidadeCollection;
    }

    public Pais getPaisFk() {
        return paisFk;
    }

    public void setPaisFk(Pais paisFk) {
        this.paisFk = paisFk;
    }

    @XmlTransient
    public Collection<Endereco> getEnderecoCollection() {
        return enderecoCollection;
    }

    public void setEnderecoCollection(Collection<Endereco> enderecoCollection) {
        this.enderecoCollection = enderecoCollection;
    }

    @XmlTransient
    public Collection<PessoaFisica> getPessoaFisicaCollection() {
        return pessoaFisicaCollection;
    }

    public void setPessoaFisicaCollection(Collection<PessoaFisica> pessoaFisicaCollection) {
        this.pessoaFisicaCollection = pessoaFisicaCollection;
    }

    @XmlTransient
    public Collection<PessoaFisica> getPessoaFisicaCollection1() {
        return pessoaFisicaCollection1;
    }

    public void setPessoaFisicaCollection1(Collection<PessoaFisica> pessoaFisicaCollection1) {
        this.pessoaFisicaCollection1 = pessoaFisicaCollection1;
    }

    @XmlTransient
    public Collection<EnderecoHistorico> getEnderecoHistoricoCollection() {
        return enderecoHistoricoCollection;
    }

    public void setEnderecoHistoricoCollection(Collection<EnderecoHistorico> enderecoHistoricoCollection) {
        this.enderecoHistoricoCollection = enderecoHistoricoCollection;
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
        if (!(object instanceof Estado)) {
            return false;
        }
        Estado other = (Estado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.Estado[ id=" + id + " ]";
    }
    
}
