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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author paulones
 */
@Entity
@Table(name = "pessoa_fisica_juridica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PessoaFisicaJuridica.findAll", query = "SELECT p FROM PessoaFisicaJuridica p"),
    @NamedQuery(name = "PessoaFisicaJuridica.findById", query = "SELECT p FROM PessoaFisicaJuridica p WHERE p.id = :id"),
    @NamedQuery(name = "PessoaFisicaJuridica.findByCapitalDeParticipacao", query = "SELECT p FROM PessoaFisicaJuridica p WHERE p.capitalDeParticipacao = :capitalDeParticipacao"),
    @NamedQuery(name = "PessoaFisicaJuridica.findByDataDeInicio", query = "SELECT p FROM PessoaFisicaJuridica p WHERE p.dataDeInicio = :dataDeInicio"),
    @NamedQuery(name = "PessoaFisicaJuridica.findByDataDeTermino", query = "SELECT p FROM PessoaFisicaJuridica p WHERE p.dataDeTermino = :dataDeTermino")})
public class PessoaFisicaJuridica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "capital_de_participacao")
    private Float capitalDeParticipacao;
    @Size(max = 10)
    @Column(name = "data_de_inicio")
    private String dataDeInicio;
    @Size(max = 10)
    @Column(name = "data_de_termino")
    private String dataDeTermino;
    @JoinColumn(name = "funcao_fk", referencedColumnName = "id")
    @ManyToOne
    private Funcao funcaoFk;
    @JoinColumn(name = "pessoa_fisica_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PessoaFisica pessoaFisicaFk;
    @JoinColumn(name = "pessoa_juridica_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PessoaJuridica pessoaJuridicaFk;

    public PessoaFisicaJuridica() {
    }

    public PessoaFisicaJuridica(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getCapitalDeParticipacao() {
        return capitalDeParticipacao;
    }

    public void setCapitalDeParticipacao(Float capitalDeParticipacao) {
        this.capitalDeParticipacao = capitalDeParticipacao;
    }

    public String getDataDeInicio() {
        return dataDeInicio;
    }

    public void setDataDeInicio(String dataDeInicio) {
        this.dataDeInicio = dataDeInicio;
    }

    public String getDataDeTermino() {
        return dataDeTermino;
    }

    public void setDataDeTermino(String dataDeTermino) {
        this.dataDeTermino = dataDeTermino;
    }

    public Funcao getFuncaoFk() {
        return funcaoFk;
    }

    public void setFuncaoFk(Funcao funcaoFk) {
        this.funcaoFk = funcaoFk;
    }

    public PessoaFisica getPessoaFisicaFk() {
        return pessoaFisicaFk;
    }

    public void setPessoaFisicaFk(PessoaFisica pessoaFisicaFk) {
        this.pessoaFisicaFk = pessoaFisicaFk;
    }

    public PessoaJuridica getPessoaJuridicaFk() {
        return pessoaJuridicaFk;
    }

    public void setPessoaJuridicaFk(PessoaJuridica pessoaJuridicaFk) {
        this.pessoaJuridicaFk = pessoaJuridicaFk;
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
        if (!(object instanceof PessoaFisicaJuridica)) {
            return false;
        }
        PessoaFisicaJuridica other = (PessoaFisicaJuridica) object;
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
        final PessoaFisicaJuridica other = (PessoaFisicaJuridica) obj;
        if (!Objects.equals(this.capitalDeParticipacao, other.capitalDeParticipacao)) {
            return false;
        }
        if (!Objects.equals(this.dataDeInicio, other.dataDeInicio)) {
            return false;
        }
        if (!Objects.equals(this.dataDeTermino, other.dataDeTermino)) {
            return false;
        }
        if (!Objects.equals(this.funcaoFk, other.funcaoFk)) {
            return false;
        }
        if (!Objects.equals(this.pessoaFisicaFk, other.pessoaFisicaFk)) {
            return false;
        }
        if (!Objects.equals(this.pessoaJuridicaFk, other.pessoaJuridicaFk)) {
            return false;
        }
        return true;
    }

    
    @Override
    public String toString() {
        return "entidade.PessoaFisicaJuridica[ id=" + id + " ]";
    }
    
}
