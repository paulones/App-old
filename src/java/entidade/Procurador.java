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
@Table(name = "procurador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Procurador.findAll", query = "SELECT p FROM Procurador p"),
    @NamedQuery(name = "Procurador.findById", query = "SELECT p FROM Procurador p WHERE p.id = :id"),
    @NamedQuery(name = "Procurador.findByNome", query = "SELECT p FROM Procurador p WHERE p.nome = :nome")})
public class Procurador implements Serializable {
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
    @OneToMany(mappedBy = "procuradorFk")
    private Collection<ProcessoJudicial> processoJudicialCollection;
    @JoinColumn(name = "estado_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Estado estadoFk;
    @OneToMany(mappedBy = "procuradorFk")
    private Collection<ProcessoJudicialHistorico> processoJudicialHistoricoCollection;

    public Procurador() {
    }

    public Procurador(Integer id) {
        this.id = id;
    }

    public Procurador(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
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

    @XmlTransient
    public Collection<ProcessoJudicial> getProcessoJudicialCollection() {
        return processoJudicialCollection;
    }

    public void setProcessoJudicialCollection(Collection<ProcessoJudicial> processoJudicialCollection) {
        this.processoJudicialCollection = processoJudicialCollection;
    }

    public Estado getEstadoFk() {
        return estadoFk;
    }

    public void setEstadoFk(Estado estadoFk) {
        this.estadoFk = estadoFk;
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
        if (!(object instanceof Procurador)) {
            return false;
        }
        Procurador other = (Procurador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.Procurador[ id=" + id + " ]";
    }
    
}
