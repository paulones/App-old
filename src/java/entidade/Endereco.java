/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author paulones
 */
@Entity
@Table(name = "endereco")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Endereco.findAll", query = "SELECT e FROM Endereco e"),
    @NamedQuery(name = "Endereco.findById", query = "SELECT e FROM Endereco e WHERE e.id = :id"),
    @NamedQuery(name = "Endereco.findByIdFk", query = "SELECT e FROM Endereco e WHERE e.idFk = :idFk"),
    @NamedQuery(name = "Endereco.findByTipo", query = "SELECT e FROM Endereco e WHERE e.tipo = :tipo"),
    @NamedQuery(name = "Endereco.findByEndereco", query = "SELECT e FROM Endereco e WHERE e.endereco = :endereco"),
    @NamedQuery(name = "Endereco.findByNumero", query = "SELECT e FROM Endereco e WHERE e.numero = :numero"),
    @NamedQuery(name = "Endereco.findByComplemento", query = "SELECT e FROM Endereco e WHERE e.complemento = :complemento"),
    @NamedQuery(name = "Endereco.findByBairro", query = "SELECT e FROM Endereco e WHERE e.bairro = :bairro"),
    @NamedQuery(name = "Endereco.findByCep", query = "SELECT e FROM Endereco e WHERE e.cep = :cep")})
public class Endereco implements Serializable {
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
    @JoinColumn(name = "cidade_fk", referencedColumnName = "id")
    @ManyToOne
    private Cidade cidadeFk;
    @JoinColumn(name = "estado_fk", referencedColumnName = "id")
    @ManyToOne
    private Estado estadoFk;

    public Endereco() {
    }

    public Endereco(Integer id) {
        this.id = id;
    }

    public Endereco(Integer id, int idFk, String tipo) {
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

    public Cidade getCidadeFk() {
        return cidadeFk;
    }

    public void setCidadeFk(Cidade cidadeFk) {
        this.cidadeFk = cidadeFk;
    }

    public Estado getEstadoFk() {
        return estadoFk;
    }

    public void setEstadoFk(Estado estadoFk) {
        this.estadoFk = estadoFk;
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
        if (!(object instanceof Endereco)) {
            return false;
        }
        Endereco other = (Endereco) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    public List<String> changedValues(Object obj) {
        List<String> list = new ArrayList<>();
        final Endereco other = (Endereco) obj;
        if (this.idFk != other.idFk) {
            list.add("idFk");
        }
        if (!Objects.equals(this.tipo, other.tipo)) {
            list.add("tipo");
        }
        if (!Objects.equals(this.endereco, other.endereco)) {
            list.add("endereco");
        }
        if (!Objects.equals(this.numero, other.numero)) {
            list.add("numero");
        }
        if (!Objects.equals(this.complemento, other.complemento)) {
            list.add("complemento");
        }
        if (!Objects.equals(this.bairro, other.bairro)) {
            list.add("bairro");
        }
        if (!Objects.equals(this.cep, other.cep)) {
            list.add("cep");
        }
        if (!Objects.equals(this.cidadeFk, other.cidadeFk)) {
            list.add("cidadeFk");
        }
        if (!Objects.equals(this.estadoFk, other.estadoFk)) {
            list.add("estadoFk");
        }
        return list;
    }

    

    @Override
    public String toString() {
        return "entidade.Endereco[ id=" + id + " ]";
    }
    
}
