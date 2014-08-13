/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "funcao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Funcao.findAll", query = "SELECT f FROM Funcao f"),
    @NamedQuery(name = "Funcao.findById", query = "SELECT f FROM Funcao f WHERE f.id = :id"),
    @NamedQuery(name = "Funcao.findByFuncao", query = "SELECT f FROM Funcao f WHERE f.funcao = :funcao")})
public class Funcao implements Serializable {
    @OneToMany(mappedBy = "funcaoFk")
    private Collection<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "funcao")
    private String funcao;
    @OneToMany(mappedBy = "funcaoFk")
    private Collection<PessoaFisicaJuridica> pessoaFisicaJuridicaCollection;

    public Funcao() {
    }

    public Funcao(Integer id) {
        this.id = id;
    }

    public Funcao(Integer id, String funcao) {
        this.id = id;
        this.funcao = funcao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    @XmlTransient
    public Collection<PessoaFisicaJuridica> getPessoaFisicaJuridicaCollection() {
        return pessoaFisicaJuridicaCollection;
    }

    public void setPessoaFisicaJuridicaCollection(Collection<PessoaFisicaJuridica> pessoaFisicaJuridicaCollection) {
        this.pessoaFisicaJuridicaCollection = pessoaFisicaJuridicaCollection;
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
        if (!(object instanceof Funcao)) {
            return false;
        }
        Funcao other = (Funcao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.Funcao[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<PessoaFisicaJuridicaHistorico> getPessoaFisicaJuridicaHistoricoCollection() {
        return pessoaFisicaJuridicaHistoricoCollection;
    }

    public void setPessoaFisicaJuridicaHistoricoCollection(Collection<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoCollection) {
        this.pessoaFisicaJuridicaHistoricoCollection = pessoaFisicaJuridicaHistoricoCollection;
    }
    
}
