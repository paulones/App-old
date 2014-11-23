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
@Table(name = "vinculo_social")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VinculoSocial.findAll", query = "SELECT v FROM VinculoSocial v"),
    @NamedQuery(name = "VinculoSocial.findById", query = "SELECT v FROM VinculoSocial v WHERE v.id = :id"),
    @NamedQuery(name = "VinculoSocial.findByVinculo", query = "SELECT v FROM VinculoSocial v WHERE v.vinculo = :vinculo")})
public class VinculoSocial implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "vinculo")
    private String vinculo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vinculoSocialFk")
    private Collection<PessoaFisicaFisica> pessoaFisicaFisicaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vinculoSocialFk")
    private Collection<PessoaFisicaFisicaHistorico> pessoaFisicaFisicaHistoricoCollection;

    public VinculoSocial() {
    }

    public VinculoSocial(Integer id) {
        this.id = id;
    }

    public VinculoSocial(Integer id, String vinculo) {
        this.id = id;
        this.vinculo = vinculo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVinculo() {
        return vinculo;
    }

    public void setVinculo(String vinculo) {
        this.vinculo = vinculo;
    }

    @XmlTransient
    public Collection<PessoaFisicaFisica> getPessoaFisicaFisicaCollection() {
        return pessoaFisicaFisicaCollection;
    }

    public void setPessoaFisicaFisicaCollection(Collection<PessoaFisicaFisica> pessoaFisicaFisicaCollection) {
        this.pessoaFisicaFisicaCollection = pessoaFisicaFisicaCollection;
    }

    @XmlTransient
    public Collection<PessoaFisicaFisicaHistorico> getPessoaFisicaFisicaHistoricoCollection() {
        return pessoaFisicaFisicaHistoricoCollection;
    }

    public void setPessoaFisicaFisicaHistoricoCollection(Collection<PessoaFisicaFisicaHistorico> pessoaFisicaFisicaHistoricoCollection) {
        this.pessoaFisicaFisicaHistoricoCollection = pessoaFisicaFisicaHistoricoCollection;
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
        if (!(object instanceof VinculoSocial)) {
            return false;
        }
        VinculoSocial other = (VinculoSocial) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.VinculoSocial[ id=" + id + " ]";
    }
    
}
