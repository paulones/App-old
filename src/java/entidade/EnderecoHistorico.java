/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author paulones
 */
@Entity
@Table(name = "endereco_historico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EnderecoHistorico.findAll", query = "SELECT e FROM EnderecoHistorico e"),
    @NamedQuery(name = "EnderecoHistorico.findById", query = "SELECT e FROM EnderecoHistorico e WHERE e.id = :id"),
    @NamedQuery(name = "EnderecoHistorico.findByIdFk", query = "SELECT e FROM EnderecoHistorico e WHERE e.idFk = :idFk"),
    @NamedQuery(name = "EnderecoHistorico.findByTipo", query = "SELECT e FROM EnderecoHistorico e WHERE e.tipo = :tipo"),
    @NamedQuery(name = "EnderecoHistorico.findByEndereco", query = "SELECT e FROM EnderecoHistorico e WHERE e.endereco = :endereco"),
    @NamedQuery(name = "EnderecoHistorico.findByNumero", query = "SELECT e FROM EnderecoHistorico e WHERE e.numero = :numero"),
    @NamedQuery(name = "EnderecoHistorico.findByComplemento", query = "SELECT e FROM EnderecoHistorico e WHERE e.complemento = :complemento"),
    @NamedQuery(name = "EnderecoHistorico.findByBairro", query = "SELECT e FROM EnderecoHistorico e WHERE e.bairro = :bairro"),
    @NamedQuery(name = "EnderecoHistorico.findByCep", query = "SELECT e FROM EnderecoHistorico e WHERE e.cep = :cep"),
    @NamedQuery(name = "EnderecoHistorico.findByDataDeModificacao", query = "SELECT e FROM EnderecoHistorico e WHERE e.dataDeModificacao = :dataDeModificacao")})
public class EnderecoHistorico implements Serializable {
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
    @Size(max = 100)
    @Column(name = "endereco")
    private String endereco;
    @Column(name = "numero")
    private Integer numero;
    @Size(max = 100)
    @Column(name = "complemento")
    private String complemento;
    @Size(max = 50)
    @Column(name = "bairro")
    private String bairro;
    @Size(max = 8)
    @Column(name = "cep")
    private String cep;
    @Basic(optional = false)
    @Column(name = "data_de_modificacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeModificacao;
    @JoinColumn(name = "cidade_fk", referencedColumnName = "id")
    @ManyToOne
    private Cidade cidadeFk;
    @JoinColumn(name = "endereco_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Endereco enderecoFk;
    @JoinColumn(name = "estado_fk", referencedColumnName = "id")
    @ManyToOne
    private Estado estadoFk;
    @JoinColumn(name = "usuario_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioFk;

    public EnderecoHistorico() {
    }

    public EnderecoHistorico(Integer id) {
        this.id = id;
    }

    public EnderecoHistorico(Integer id, int idFk, String tipo, Date dataDeModificacao) {
        this.id = id;
        this.idFk = idFk;
        this.tipo = tipo;
        this.dataDeModificacao = dataDeModificacao;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Date getDataDeModificacao() {
        return dataDeModificacao;
    }

    public void setDataDeModificacao(Date dataDeModificacao) {
        this.dataDeModificacao = dataDeModificacao;
    }

    public Cidade getCidadeFk() {
        return cidadeFk;
    }

    public void setCidadeFk(Cidade cidadeFk) {
        this.cidadeFk = cidadeFk;
    }

    public Endereco getEnderecoFk() {
        return enderecoFk;
    }

    public void setEnderecoFk(Endereco enderecoFk) {
        this.enderecoFk = enderecoFk;
    }

    public Estado getEstadoFk() {
        return estadoFk;
    }

    public void setEstadoFk(Estado estadoFk) {
        this.estadoFk = estadoFk;
    }

    public Usuario getUsuarioFk() {
        return usuarioFk;
    }

    public void setUsuarioFk(Usuario usuarioFk) {
        this.usuarioFk = usuarioFk;
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
        if (!(object instanceof EnderecoHistorico)) {
            return false;
        }
        EnderecoHistorico other = (EnderecoHistorico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.EnderecoHistorico[ id=" + id + " ]";
    }
    
}
