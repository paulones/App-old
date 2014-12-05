/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author paulones
 */
@Entity
@Table(name = "penhora")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Penhora.findAll", query = "SELECT p FROM Penhora p"),
    @NamedQuery(name = "Penhora.findById", query = "SELECT p FROM Penhora p WHERE p.id = :id"),
    @NamedQuery(name = "Penhora.findBySituacao", query = "SELECT p FROM Penhora p WHERE p.situacao = :situacao"),
    @NamedQuery(name = "Penhora.findByValor", query = "SELECT p FROM Penhora p WHERE p.valor = :valor"),
    @NamedQuery(name = "Penhora.findByMotivo", query = "SELECT p FROM Penhora p WHERE p.motivo = :motivo"),
    @NamedQuery(name = "Penhora.findByDataDaPenhora", query = "SELECT p FROM Penhora p WHERE p.dataDaPenhora = :dataDaPenhora"),
    @NamedQuery(name = "Penhora.findBySocioFk", query = "SELECT p FROM Penhora p WHERE p.socioFk = :socioFk"),
    @NamedQuery(name = "Penhora.findBySocio", query = "SELECT p FROM Penhora p WHERE p.socio = :socio"),
    @NamedQuery(name = "Penhora.findByEndereco", query = "SELECT p FROM Penhora p WHERE p.endereco = :endereco")})
public class Penhora implements Serializable {
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
    @Size(max = 150)
    @Column(name = "motivo")
    private String motivo;
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
    @JoinColumn(name = "processo_judicial_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ProcessoJudicial processoJudicialFk;
    @JoinColumn(name = "tipo_penhora_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoPenhora tipoPenhoraFk;
    private transient String socioTipo;
    private transient List<Bem> bemList;
    private transient char condicao;

    public Penhora() {
    }

    public Penhora(Integer id) {
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

    public ProcessoJudicial getProcessoJudicialFk() {
        return processoJudicialFk;
    }

    public void setProcessoJudicialFk(ProcessoJudicial processoJudicialFk) {
        this.processoJudicialFk = processoJudicialFk;
    }

    public TipoPenhora getTipoPenhoraFk() {
        return tipoPenhoraFk;
    }

    public void setTipoPenhoraFk(TipoPenhora tipoPenhoraFk) {
        this.tipoPenhoraFk = tipoPenhoraFk;
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
        if (!(object instanceof Penhora)) {
            return false;
        }
        Penhora other = (Penhora) object;
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
        final Penhora other = (Penhora) obj;
        if (!Objects.equals(this.bemFk, other.bemFk)) {
            return false;
        }
        if (!Objects.equals(this.dataDaPenhora, other.dataDaPenhora)) {
            return false;
        }
        if (!Objects.equals(this.endereco, other.endereco)) {
            return false;
        }
        if (!Objects.equals(this.processoJudicialFk, other.processoJudicialFk)) {
            return false;
        }
        if (!Objects.equals(this.situacao, other.situacao)) {
            return false;
        }
        if (!Objects.equals(this.socio, other.socio)) {
            return false;
        }
        if (!Objects.equals(this.socioFk, other.socioFk)) {
            return false;
        }
        if (!Objects.equals(this.tipoPenhoraFk, other.tipoPenhoraFk)) {
            return false;
        }
        if (!Objects.equals(this.socioTipo, other.socioTipo)) {
            return false;
        }
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        if (!Objects.equals(this.motivo, other.motivo)) {
            return false;
        }
        if (!Objects.equals(this.condicao, other.condicao)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.Penhora[ id=" + id + " ]";
    }

    public String getSocioTipo() {
        return socioTipo;
    }

    public void setSocioTipo(String socioTipo) {
        this.socioTipo = socioTipo;
    }

    public List<Bem> getBemList() {
        return bemList;
    }

    public void setBemList(List<Bem> bemList) {
        this.bemList = bemList;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public char getCondicao() {
        return condicao;
    }

    public void setCondicao(char condicao) {
        this.condicao = condicao;
    }
    
}
