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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author paulones
 */
@Entity
@Table(name = "situacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Situacao.findAll", query = "SELECT s FROM Situacao s"),
    @NamedQuery(name = "Situacao.findById", query = "SELECT s FROM Situacao s WHERE s.id = :id"),
    @NamedQuery(name = "Situacao.findBySituacao", query = "SELECT s FROM Situacao s WHERE s.situacao = :situacao")})
public class Situacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 20)
    @Column(name = "situacao")
    private String situacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "situacaoFk")
    private Collection<ProcessoJudicial> processoJudicialCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "situacaoFk")
    private Collection<ProcessoJudicialHistorico> processoJudicialHistoricoCollection;

    public Situacao() {
    }

    public Situacao(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    @XmlTransient
    public Collection<ProcessoJudicial> getProcessoJudicialCollection() {
        return processoJudicialCollection;
    }

    public void setProcessoJudicialCollection(Collection<ProcessoJudicial> processoJudicialCollection) {
        this.processoJudicialCollection = processoJudicialCollection;
    }

    @XmlTransient
    public Collection<ProcessoJudicialHistorico> getProcessoJudicialHistoricoCollection() {
        return processoJudicialHistoricoCollection;
    }

    public void setProcessoJudicialHistoricoCollection(Collection<ProcessoJudicialHistorico> processoJudicialHistoricoCollection) {
        this.processoJudicialHistoricoCollection = processoJudicialHistoricoCollection;
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
        if (!(object instanceof Situacao)) {
            return false;
        }
        Situacao other = (Situacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.Situacao[ id=" + id + " ]";
    }
    
}
