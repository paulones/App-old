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
@Table(name = "tipo_processo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoProcesso.findAll", query = "SELECT t FROM TipoProcesso t"),
    @NamedQuery(name = "TipoProcesso.findById", query = "SELECT t FROM TipoProcesso t WHERE t.id = :id"),
    @NamedQuery(name = "TipoProcesso.findByTipo", query = "SELECT t FROM TipoProcesso t WHERE t.tipo = :tipo")})
public class TipoProcesso implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoDeProcessoFk")
    private Collection<VinculoProcessualHistorico> vinculoProcessualHistoricoCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "tipo")
    private String tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoDeProcessoFk")
    private Collection<VinculoProcessual> vinculoProcessualCollection;

    public TipoProcesso() {
    }

    public TipoProcesso(Integer id) {
        this.id = id;
    }

    public TipoProcesso(Integer id, String tipo) {
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
    public Collection<VinculoProcessual> getVinculoProcessualCollection() {
        return vinculoProcessualCollection;
    }

    public void setVinculoProcessualCollection(Collection<VinculoProcessual> vinculoProcessualCollection) {
        this.vinculoProcessualCollection = vinculoProcessualCollection;
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
        if (!(object instanceof TipoProcesso)) {
            return false;
        }
        TipoProcesso other = (TipoProcesso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.TipoProcesso[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<VinculoProcessualHistorico> getVinculoProcessualHistoricoCollection() {
        return vinculoProcessualHistoricoCollection;
    }

    public void setVinculoProcessualHistoricoCollection(Collection<VinculoProcessualHistorico> vinculoProcessualHistoricoCollection) {
        this.vinculoProcessualHistoricoCollection = vinculoProcessualHistoricoCollection;
    }
    
}
