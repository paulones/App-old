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
@Table(name = "tipo_empresarial")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoEmpresarial.findAll", query = "SELECT t FROM TipoEmpresarial t"),
    @NamedQuery(name = "TipoEmpresarial.findById", query = "SELECT t FROM TipoEmpresarial t WHERE t.id = :id"),
    @NamedQuery(name = "TipoEmpresarial.findByTipo", query = "SELECT t FROM TipoEmpresarial t WHERE t.tipo = :tipo")})
public class TipoEmpresarial implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "tipo")
    private String tipo;
    @OneToMany(mappedBy = "tipoEmpresarialFk")
    private Collection<PessoaJuridica> pessoaJuridicaCollection;

    public TipoEmpresarial() {
    }

    public TipoEmpresarial(Integer id) {
        this.id = id;
    }

    public TipoEmpresarial(Integer id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public Collection<PessoaJuridica> getPessoaJuridicaCollection() {
        return pessoaJuridicaCollection;
    }

    public void setPessoaJuridicaCollection(Collection<PessoaJuridica> pessoaJuridicaCollection) {
        this.pessoaJuridicaCollection = pessoaJuridicaCollection;
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
        if (!(object instanceof TipoEmpresarial)) {
            return false;
        }
        TipoEmpresarial other = (TipoEmpresarial) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.TipoEmpresarial[ id=" + id + " ]";
    }
    
}
