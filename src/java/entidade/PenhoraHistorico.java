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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author paulones
 */
@Entity
@Table(name = "penhora_historico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PenhoraHistorico.findAll", query = "SELECT p FROM PenhoraHistorico p"),
    @NamedQuery(name = "PenhoraHistorico.findById", query = "SELECT p FROM PenhoraHistorico p WHERE p.id = :id"),
    @NamedQuery(name = "PenhoraHistorico.findBySituacao", query = "SELECT p FROM PenhoraHistorico p WHERE p.situacao = :situacao"),
    @NamedQuery(name = "PenhoraHistorico.findByValor", query = "SELECT p FROM PenhoraHistorico p WHERE p.valor = :valor"),
    @NamedQuery(name = "PenhoraHistorico.findByDataDaPenhora", query = "SELECT p FROM PenhoraHistorico p WHERE p.dataDaPenhora = :dataDaPenhora"),
    @NamedQuery(name = "PenhoraHistorico.findBySocioFk", query = "SELECT p FROM PenhoraHistorico p WHERE p.socioFk = :socioFk"),
    @NamedQuery(name = "PenhoraHistorico.findBySocio", query = "SELECT p FROM PenhoraHistorico p WHERE p.socio = :socio"),
    @NamedQuery(name = "PenhoraHistorico.findByEndereco", query = "SELECT p FROM PenhoraHistorico p WHERE p.endereco = :endereco")})
public class PenhoraHistorico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "situacao")
    private Character situacao;
    @Column(name = "valor")
    private BigDecimal valor;
    @Size(max = 10)
    @Column(name = "data_da_penhora")
    private String dataDaPenhora;
    @Size(max = 200)
    @Column(name = "endereco")
    private String endereco;
    @Column(name = "socio_fk")
    private Integer socioFk;
    @Size(max = 2)
    @Column(name = "socio")
    private String socio;
    @JoinColumn(name = "bem_fk", referencedColumnName = "id")
    @ManyToOne
    private Bem bemFk;
    @JoinColumn(name = "processo_judicial_historico_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ProcessoJudicialHistorico processoJudicialHistoricoFk;
    @JoinColumn(name = "tipo_penhora_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoPenhora tipoPenhoraFk;

    public PenhoraHistorico() {
    }

    public PenhoraHistorico(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Character getSituacao() {
        return situacao;
    }

    public void setSituacao(Character situacao) {
        this.situacao = situacao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDataDaPenhora() {
        return dataDaPenhora;
    }

    public void setDataDaPenhora(String dataDaPenhora) {
        this.dataDaPenhora = dataDaPenhora;
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

    public Bem getBemFk() {
        return bemFk;
    }

    public void setBemFk(Bem bemFk) {
        this.bemFk = bemFk;
    }

    public ProcessoJudicialHistorico getProcessoJudicialHistoricoFk() {
        return processoJudicialHistoricoFk;
    }

    public void setProcessoJudicialHistoricoFk(ProcessoJudicialHistorico processoJudicialHistoricoFk) {
        this.processoJudicialHistoricoFk = processoJudicialHistoricoFk;
    }

    public TipoPenhora getTipoPenhoraFk() {
        return tipoPenhoraFk;
    }

    public void setTipoPenhoraFk(TipoPenhora tipoPenhoraFk) {
        this.tipoPenhoraFk = tipoPenhoraFk;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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
        if (!(object instanceof PenhoraHistorico)) {
            return false;
        }
        PenhoraHistorico other = (PenhoraHistorico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.PenhoraHistorico[ id=" + id + " ]";
    }
    
}
