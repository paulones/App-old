/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
    @NamedQuery(name = "Bem.findByIdFk", query = "SELECT b FROM Bem b WHERE b.idFk = :idFk"),
    @NamedQuery(name = "Bem.findByTipo", query = "SELECT b FROM Bem b WHERE b.tipo = :tipo"),
    @NamedQuery(name = "Bem.findByDescricao", query = "SELECT b FROM Bem b WHERE b.descricao = :descricao"),
    @NamedQuery(name = "Bem.findByDataDeAquisicao", query = "SELECT b FROM Bem b WHERE b.dataDeAquisicao = :dataDeAquisicao"),
    @NamedQuery(name = "Bem.findByDataDeTransferenciaOuExtincao", query = "SELECT b FROM Bem b WHERE b.dataDeTransferenciaOuExtincao = :dataDeTransferenciaOuExtincao"),
    @NamedQuery(name = "Bem.findByValor", query = "SELECT b FROM Bem b WHERE b.valor = :valor"),
    @NamedQuery(name = "Bem.findByEndereco", query = "SELECT b FROM Bem b WHERE b.endereco = :endereco")})
public class Bem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_fk")
    private int idFk;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "tipo")
    private String tipo;
    @Size(max = 300)
    @Column(name = "descricao")
    private String descricao;
    @Size(max = 10)
    @Column(name = "data_de_aquisicao")
    private String dataDeAquisicao;
    @Size(max = 10)
    @Column(name = "data_de_transferencia_ou_extincao")
    private String dataDeTransferenciaOuExtincao;
    @Column(name = "valor")
    private BigDecimal valor;
    @Size(max = 200)
    @Column(name = "endereco")
    private String endereco;
    @JoinColumn(name = "tipo_bem_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoBem tipoBemFk;

    public Bem() {
    }

    public Bem(Integer id) {
        this.id = id;
    }

    public Bem(Integer id, int idFk, String tipo) {
        this.id = id;
        this.idFk = idFk;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdFk() {
        return idFk;
    }

    public void setIdFk(int idFk) {
        this.idFk = idFk;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataDeAquisicao() {
        return dataDeAquisicao;
    }

    public void setDataDeAquisicao(String dataDeAquisicao) {
        this.dataDeAquisicao = dataDeAquisicao;
    }

    public String getDataDeTransferenciaOuExtincao() {
        return dataDeTransferenciaOuExtincao;
    }

    public void setDataDeTransferenciaOuExtincao(String dataDeTransferenciaOuExtincao) {
        this.dataDeTransferenciaOuExtincao = dataDeTransferenciaOuExtincao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public TipoBem getTipoBemFk() {
        return tipoBemFk;
    }

    public void setTipoBemFk(TipoBem tipoBemFk) {
        this.tipoBemFk = tipoBemFk;
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
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bem other = (Bem) obj;
        if (!Objects.equals(this.dataDeAquisicao, other.dataDeAquisicao)) {
            return false;
        }
        if (!Objects.equals(this.dataDeTransferenciaOuExtincao, other.dataDeTransferenciaOuExtincao)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.endereco, other.endereco)) {
            return false;
        }
        if (!Objects.equals(this.tipoBemFk, other.tipoBemFk)) {
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
