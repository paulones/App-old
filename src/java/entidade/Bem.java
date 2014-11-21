/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "bem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bem.findAll", query = "SELECT b FROM Bem b"),
    @NamedQuery(name = "Bem.findById", query = "SELECT b FROM Bem b WHERE b.id = :id"),
    @NamedQuery(name = "Bem.findByValor", query = "SELECT b FROM Bem b WHERE b.valor = :valor"),
    @NamedQuery(name = "Bem.findByDescricao", query = "SELECT b FROM Bem b WHERE b.descricao = :descricao"),
    @NamedQuery(name = "Bem.findByDataDoAto", query = "SELECT b FROM Bem b WHERE b.dataDoAto = :dataDoAto")})
public class Bem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Size(min = 1, max = 300)
    @Column(name = "descricao")
    private String descricao;
    @Size(max = 10)
    @Column(name = "data_do_ato")
    private String dataDoAto;
    @Column(name = "valor")
    private BigDecimal valor; 
    @JoinColumn(name = "processo_judicial_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ProcessoJudicial processoJudicialFk;

    public Bem() {
    }

    public Bem(Integer id) {
        this.id = id;
    }

    public Bem(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataDoAto() {
        return dataDoAto;
    }

    public void setDataDoAto(String dataDoAto) {
        this.dataDoAto = dataDoAto;
    }

    public ProcessoJudicial getProcessoJudicialFk() {
        return processoJudicialFk;
    }

    public void setProcessoJudicialFk(ProcessoJudicial processoJudicialFk) {
        this.processoJudicialFk = processoJudicialFk;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
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
        if (!(object instanceof Bem)) {
            return false;
        }
        Bem other = (Bem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public boolean equalsValues(Object obj) {
        final Bem other = (Bem) obj;
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.dataDoAto, other.dataDoAto)) {
            return false;
        }
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.Bem[ id=" + id + " ]";
    }
    
}
