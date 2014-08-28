/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
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
@Table(name = "vinculo_processual_historico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VinculoProcessualHistorico.findAll", query = "SELECT v FROM VinculoProcessualHistorico v"),
    @NamedQuery(name = "VinculoProcessualHistorico.findById", query = "SELECT v FROM VinculoProcessualHistorico v WHERE v.id = :id"),
    @NamedQuery(name = "VinculoProcessualHistorico.findByProcesso", query = "SELECT v FROM VinculoProcessualHistorico v WHERE v.processo = :processo")})
public class VinculoProcessualHistorico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "processo")
    private String processo;
    @JoinColumn(name = "processo_judicial_historico_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ProcessoJudicialHistorico processoJudicialHistoricoFk;
    @JoinColumn(name = "tipo_de_processo_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoProcesso tipoDeProcessoFk;

    public VinculoProcessualHistorico() {
    }

    public VinculoProcessualHistorico(Integer id) {
        this.id = id;
    }

    public VinculoProcessualHistorico(Integer id, String processo) {
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

    public ProcessoJudicialHistorico getProcessoJudicialHistoricoFk() {
        return processoJudicialHistoricoFk;
    }

    public void setProcessoJudicialHistoricoFk(ProcessoJudicialHistorico processoJudicialHistoricoFk) {
        this.processoJudicialHistoricoFk = processoJudicialHistoricoFk;
    }

    public TipoProcesso getTipoDeProcessoFk() {
        return tipoDeProcessoFk;
    }

    public void setTipoDeProcessoFk(TipoProcesso tipoDeProcessoFk) {
        this.tipoDeProcessoFk = tipoDeProcessoFk;
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
        if (!(object instanceof VinculoProcessualHistorico)) {
            return false;
        }
        VinculoProcessualHistorico other = (VinculoProcessualHistorico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.VinculoProcessualHistorico[ id=" + id + " ]";
    }
    
}
