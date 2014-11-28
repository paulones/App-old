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
@Table(name = "redirecionamento_historico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RedirecionamentoHistorico.findAll", query = "SELECT r FROM RedirecionamentoHistorico r"),
    @NamedQuery(name = "RedirecionamentoHistorico.findById", query = "SELECT r FROM RedirecionamentoHistorico r WHERE r.id = :id"),
    @NamedQuery(name = "RedirecionamentoHistorico.findByRedirecionado", query = "SELECT r FROM RedirecionamentoHistorico r WHERE r.redirecionado = :redirecionado"),
    @NamedQuery(name = "RedirecionamentoHistorico.findByDataDeRedirecionamento", query = "SELECT r FROM RedirecionamentoHistorico r WHERE r.dataDeRedirecionamento = :dataDeRedirecionamento"),
    @NamedQuery(name = "RedirecionamentoHistorico.findBySocioFk", query = "SELECT r FROM RedirecionamentoHistorico r WHERE r.socioFk = :socioFk"),
    @NamedQuery(name = "RedirecionamentoHistorico.findBySocio", query = "SELECT r FROM RedirecionamentoHistorico r WHERE r.socio = :socio")})
public class RedirecionamentoHistorico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
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
    @JoinColumn(name = "processo_judicial_historico_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ProcessoJudicialHistorico processoJudicialHistoricoFk;

    public RedirecionamentoHistorico() {
    }

    public RedirecionamentoHistorico(Integer id) {
        this.id = id;
    }

    public RedirecionamentoHistorico(Integer id, Character redirecionado) {
        this.id = id;
        this.redirecionado = redirecionado;
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

    public ProcessoJudicialHistorico getProcessoJudicialHistoricoFk() {
        return processoJudicialHistoricoFk;
    }

    public void setProcessoJudicialHistoricoFk(ProcessoJudicialHistorico processoJudicialHistoricoFk) {
        this.processoJudicialHistoricoFk = processoJudicialHistoricoFk;
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
        if (!(object instanceof RedirecionamentoHistorico)) {
            return false;
        }
        RedirecionamentoHistorico other = (RedirecionamentoHistorico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RedirecionamentoHistorico[ id=" + id + " ]";
    }
    
}
