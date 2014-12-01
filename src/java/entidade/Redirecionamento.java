/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Objects;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author paulones
 */
@Entity
@Table(name = "redirecionamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Redirecionamento.findAll", query = "SELECT r FROM Redirecionamento r"),
    @NamedQuery(name = "Redirecionamento.findById", query = "SELECT r FROM Redirecionamento r WHERE r.id = :id"),
    @NamedQuery(name = "Redirecionamento.findByRedirecionado", query = "SELECT r FROM Redirecionamento r WHERE r.redirecionado = :redirecionado"),
    @NamedQuery(name = "Redirecionamento.findByDataDeRedirecionamento", query = "SELECT r FROM Redirecionamento r WHERE r.dataDeRedirecionamento = :dataDeRedirecionamento"),
    @NamedQuery(name = "Redirecionamento.findBySocioFk", query = "SELECT r FROM Redirecionamento r WHERE r.socioFk = :socioFk"),
    @NamedQuery(name = "Redirecionamento.findBySocio", query = "SELECT r FROM Redirecionamento r WHERE r.socio = :socio")})
public class Redirecionamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "redirecionado")
    private Character redirecionado;
    @Size(max = 10)
    @Column(name = "data_de_redirecionamento")
    private String dataDeRedirecionamento;
    @Column(name = "socio_fk")
    private Integer socioFk;
    @Size(max = 2)
    @Column(name = "socio")
    private String socio;
    @JoinColumn(name = "processo_judicial_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ProcessoJudicial processoJudicialFk;

    public Redirecionamento() {
    }

    public Redirecionamento(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Character getRedirecionado() {
        return redirecionado;
    }

    public void setRedirecionado(Character redirecionado) {
        this.redirecionado = redirecionado;
    }

    public String getDataDeRedirecionamento() {
        return dataDeRedirecionamento;
    }

    public void setDataDeRedirecionamento(String dataDeRedirecionamento) {
        this.dataDeRedirecionamento = dataDeRedirecionamento;
    }

    public Integer getSocioFk() {
        return socioFk;
    }

    public void setSocioFk(Integer socioFk) {
        this.socioFk = socioFk;
    }

    public String getSocio() {
        return socio;
    }

    public void setSocio(String socio) {
        this.socio = socio;
    }

    public ProcessoJudicial getProcessoJudicialFk() {
        return processoJudicialFk;
    }

    public void setProcessoJudicialFk(ProcessoJudicial processoJudicialFk) {
        this.processoJudicialFk = processoJudicialFk;
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
        if (!(object instanceof Redirecionamento)) {
            return false;
        }
        Redirecionamento other = (Redirecionamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public boolean equalsValues(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Redirecionamento other = (Redirecionamento) obj;
        if (!Objects.equals(this.dataDeRedirecionamento, other.dataDeRedirecionamento)) {
            return false;
        }
        if (!Objects.equals(this.processoJudicialFk, other.processoJudicialFk)) {
            return false;
        }
        if (!Objects.equals(this.redirecionado, other.redirecionado)) {
            return false;
        }
        if (!Objects.equals(this.socio, other.socio)) {
            return false;
        }
        if (!Objects.equals(this.socioFk, other.socioFk)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "entidade.Redirecionamento[ id=" + id + " ]";
    }
    
}
