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
 * @author Pedro
 */
@Entity
@Table(name = "autorizacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Autorizacao.findAll", query = "SELECT a FROM Autorizacao a"),
    @NamedQuery(name = "Autorizacao.findById", query = "SELECT a FROM Autorizacao a WHERE a.id = :id"),
    @NamedQuery(name = "Autorizacao.findByCpf", query = "SELECT a FROM Autorizacao a WHERE a.cpf = :cpf")})
public class Autorizacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "cpf")
    private String cpf;
    @JoinColumn(name = "instituicao_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Instituicao instituicaoFk;

    public Autorizacao() {
    }

    public Autorizacao(Integer id) {
        this.id = id;
    }

    public Autorizacao(Integer id, String cpf) {
        this.id = id;
        this.cpf = cpf;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Instituicao getInstituicaoFk() {
        return instituicaoFk;
    }

    public void setInstituicaoFk(Instituicao instituicaoFk) {
        this.instituicaoFk = instituicaoFk;
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
        if (!(object instanceof Autorizacao)) {
            return false;
        }
        Autorizacao other = (Autorizacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.Autorizacao[ id=" + id + " ]";
    }
    
}
