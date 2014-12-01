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
@Table(name = "tipo_penhora")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoPenhora.findAll", query = "SELECT t FROM TipoPenhora t"),
    @NamedQuery(name = "TipoPenhora.findById", query = "SELECT t FROM TipoPenhora t WHERE t.id = :id"),
    @NamedQuery(name = "TipoPenhora.findByTipo", query = "SELECT t FROM TipoPenhora t WHERE t.tipo = :tipo")})
public class TipoPenhora implements Serializable {
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoPenhoraFk")
    private Collection<PenhoraHistorico> penhoraHistoricoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoPenhoraFk")
    private Collection<Penhora> penhoraCollection;

    public TipoPenhora() {
    }

    public TipoPenhora(Integer id) {
        this.id = id;
    }

    public TipoPenhora(Integer id, String tipo) {
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
    public Collection<PenhoraHistorico> getPenhoraHistoricoCollection() {
        return penhoraHistoricoCollection;
    }

    public void setPenhoraHistoricoCollection(Collection<PenhoraHistorico> penhoraHistoricoCollection) {
        this.penhoraHistoricoCollection = penhoraHistoricoCollection;
    }

    @XmlTransient
    public Collection<Penhora> getPenhoraCollection() {
        return penhoraCollection;
    }

    public void setPenhoraCollection(Collection<Penhora> penhoraCollection) {
        this.penhoraCollection = penhoraCollection;
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
        if (!(object instanceof TipoPenhora)) {
            return false;
        }
        TipoPenhora other = (TipoPenhora) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.TipoPenhora[ id=" + id + " ]";
    }
    
}
