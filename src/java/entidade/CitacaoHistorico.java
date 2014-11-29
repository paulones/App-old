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
@Table(name = "citacao_historico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CitacaoHistorico.findAll", query = "SELECT c FROM CitacaoHistorico c"),
    @NamedQuery(name = "CitacaoHistorico.findById", query = "SELECT c FROM CitacaoHistorico c WHERE c.id = :id"),
    @NamedQuery(name = "CitacaoHistorico.findByTipoCitacao", query = "SELECT c FROM CitacaoHistorico c WHERE c.tipoCitacao = :tipoCitacao"),
    @NamedQuery(name = "CitacaoHistorico.findByCitado", query = "SELECT c FROM CitacaoHistorico c WHERE c.citado = :citado"),
    @NamedQuery(name = "CitacaoHistorico.findByMotivo", query = "SELECT c FROM CitacaoHistorico c WHERE c.motivo = :motivo"),
    @NamedQuery(name = "CitacaoHistorico.findByDataDaCitacao", query = "SELECT c FROM CitacaoHistorico c WHERE c.dataDaCitacao = :dataDaCitacao"),
    @NamedQuery(name = "CitacaoHistorico.findByEndereco", query = "SELECT c FROM CitacaoHistorico c WHERE c.endereco = :endereco"),
    @NamedQuery(name = "CitacaoHistorico.findBySocioFk", query = "SELECT c FROM CitacaoHistorico c WHERE c.socioFk = :socioFk"),
    @NamedQuery(name = "CitacaoHistorico.findBySocio", query = "SELECT c FROM CitacaoHistorico c WHERE c.socio = :socio")})
public class CitacaoHistorico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "tipo_citacao")
    private String tipoCitacao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "citado")
    private Character citado;
    @Size(max = 150)
    @Column(name = "motivo")
    private String motivo;
    @Size(max = 10)
    @Column(name = "data_da_citacao")
    private String dataDaCitacao;
    @Size(max = 150)
    @Column(name = "endereco")
    private String endereco;
    @Column(name = "socio_fk")
    private Integer socioFk;
    @Size(max = 2)
    @Column(name = "socio")
    private String socio;
    @JoinColumn(name = "processo_judicial_historico_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ProcessoJudicialHistorico processoJudicialHistoricoFk;

    public CitacaoHistorico() {
    }

    public CitacaoHistorico(Integer id) {
        this.id = id;
    }

    public CitacaoHistorico(Integer id, String tipoCitacao, Character citado) {
        this.id = id;
        this.tipoCitacao = tipoCitacao;
        this.citado = citado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoCitacao() {
        return tipoCitacao;
    }

    public void setTipoCitacao(String tipoCitacao) {
        this.tipoCitacao = tipoCitacao;
    }

    public Character getCitado() {
        return citado;
    }

    public void setCitado(Character citado) {
        this.citado = citado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDataDaCitacao() {
        return dataDaCitacao;
    }

    public void setDataDaCitacao(String dataDaCitacao) {
        this.dataDaCitacao = dataDaCitacao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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
        if (!(object instanceof CitacaoHistorico)) {
            return false;
        }
        CitacaoHistorico other = (CitacaoHistorico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.CitacaoHistorico[ id=" + id + " ]";
    }
    
}
