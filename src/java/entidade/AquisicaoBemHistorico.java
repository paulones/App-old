/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "aquisicao_bem_historico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AquisicaoBemHistorico.findAll", query = "SELECT a FROM AquisicaoBemHistorico a"),
    @NamedQuery(name = "AquisicaoBemHistorico.findById", query = "SELECT a FROM AquisicaoBemHistorico a WHERE a.id = :id"),
    @NamedQuery(name = "AquisicaoBemHistorico.findByExito", query = "SELECT a FROM AquisicaoBemHistorico a WHERE a.exito = :exito"),
    @NamedQuery(name = "AquisicaoBemHistorico.findByMotivo", query = "SELECT a FROM AquisicaoBemHistorico a WHERE a.motivo = :motivo"),
    @NamedQuery(name = "AquisicaoBemHistorico.findByDataDaAquisicao", query = "SELECT a FROM AquisicaoBemHistorico a WHERE a.dataDaAquisicao = :dataDaAquisicao"),
    @NamedQuery(name = "AquisicaoBemHistorico.findByValor", query = "SELECT a FROM AquisicaoBemHistorico a WHERE a.valor = :valor")})
public class AquisicaoBemHistorico implements Serializable {
    @Column(name = "valor")
    private BigDecimal valor;
    @JoinColumn(name = "processo_judicial_historico_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ProcessoJudicialHistorico processoJudicialHistoricoFk;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "tipo")
    private String tipo;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "exito")
    private Character exito;
    @Size(max = 150)
    @Column(name = "motivo")
    private String motivo;
    @Size(max = 10)
    @Column(name = "data_da_aquisicao")
    private String dataDaAquisicao;
    @JoinColumn(name = "bem_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Bem bemFk;

    public AquisicaoBemHistorico() {
    }

    public AquisicaoBemHistorico(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Character getExito() {
        return exito;
    }

    public void setExito(Character exito) {
        this.exito = exito;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDataDaAquisicao() {
        return dataDaAquisicao;
    }

    public void setDataDaAquisicao(String dataDaAquisicao) {
        this.dataDaAquisicao = dataDaAquisicao;
    }


    public Bem getBemFk() {
        return bemFk;
    }

    public void setBemFk(Bem bemFk) {
        this.bemFk = bemFk;
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
        if (!(object instanceof AquisicaoBemHistorico)) {
            return false;
        }
        AquisicaoBemHistorico other = (AquisicaoBemHistorico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AquisicaoBemHistorico[ id=" + id + " ]";
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public ProcessoJudicialHistorico getProcessoJudicialHistoricoFk() {
        return processoJudicialHistoricoFk;
    }

    public void setProcessoJudicialHistoricoFk(ProcessoJudicialHistorico processoJudicialHistoricoFk) {
        this.processoJudicialHistoricoFk = processoJudicialHistoricoFk;
    }
    
}
