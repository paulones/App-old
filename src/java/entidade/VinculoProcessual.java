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
@Table(name = "vinculo_processual")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VinculoProcessual.findAll", query = "SELECT v FROM VinculoProcessual v"),
    @NamedQuery(name = "VinculoProcessual.findById", query = "SELECT v FROM VinculoProcessual v WHERE v.id = :id"),
    @NamedQuery(name = "VinculoProcessual.findByProcesso", query = "SELECT v FROM VinculoProcessual v WHERE v.processo = :processo")})
public class VinculoProcessual implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Size(min = 1, max = 50)
    @Column(name = "processo")
    private String processo;
    @JoinColumn(name = "processo_judicial_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ProcessoJudicial processoJudicialFk;
    @JoinColumn(name = "tipo_de_processo_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoProcesso tipoDeProcessoFk;
    private transient Integer processoJudicialTransientId;

    public VinculoProcessual() {
    }

    public VinculoProcessual(Integer id) {
        this.id = id;
    }

    public VinculoProcessual(Integer id, String processo) {
        this.id = id;
        this.processo = processo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }

    public ProcessoJudicial getProcessoJudicialFk() {
        return processoJudicialFk;
    }

    public void setProcessoJudicialFk(ProcessoJudicial processoJudicialFk) {
        this.processoJudicialFk = processoJudicialFk;
    }

    public TipoProcesso getTipoDeProcessoFk() {
        return tipoDeProcessoFk;
    }

    public void setTipoDeProcessoFk(TipoProcesso tipoDeProcessoFk) {
        this.tipoDeProcessoFk = tipoDeProcessoFk;
    }

    public Integer getProcessoJudicialTransientId() {
        return processoJudicialTransientId;
    }

    public void setProcessoJudicialTransientId(Integer processoJudicialTransientId) {
        this.processoJudicialTransientId = processoJudicialTransientId;
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
        if (!(object instanceof VinculoProcessual)) {
            return false;
        }
        VinculoProcessual other = (VinculoProcessual) object;
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
        final VinculoProcessual other = (VinculoProcessual) obj;
        if (!Objects.equals(this.processo, other.processo)) {
            return false;
        }
        if (!Objects.equals(this.tipoDeProcessoFk, other.tipoDeProcessoFk)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.VinculoProcessual[ id=" + id + " ]";
    }
    
}
