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
@Table(name = "tipo_bem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoBem.findAll", query = "SELECT t FROM TipoBem t"),
    @NamedQuery(name = "TipoBem.findById", query = "SELECT t FROM TipoBem t WHERE t.id = :id"),
    @NamedQuery(name = "TipoBem.findByTipo", query = "SELECT t FROM TipoBem t WHERE t.tipo = :tipo")})
public class TipoBem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "tipo")
    private String tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoBemFk")
    private Collection<BemHistorico> bemHistoricoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoBemFk")
    private Collection<Bem> bemCollection;

    public TipoBem() {
    }

    public TipoBem(Integer id) {
        this.id = id;
    }

    public TipoBem(Integer id, String tipo) {
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
    public Collection<BemHistorico> getBemHistoricoCollection() {
        return bemHistoricoCollection;
    }

    public void setBemHistoricoCollection(Collection<BemHistorico> bemHistoricoCollection) {
        this.bemHistoricoCollection = bemHistoricoCollection;
    }

    @XmlTransient
    public Collection<Bem> getBemCollection() {
        return bemCollection;
    }

    public void setBemCollection(Collection<Bem> bemCollection) {
        this.bemCollection = bemCollection;
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
        if (!(object instanceof TipoBem)) {
            return false;
        }
        TipoBem other = (TipoBem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.TipoBem[ id=" + id + " ]";
    }
    
}
