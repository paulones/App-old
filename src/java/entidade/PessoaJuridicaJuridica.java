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
@Table(name = "pessoa_juridica_juridica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PessoaJuridicaJuridica.findAll", query = "SELECT p FROM PessoaJuridicaJuridica p"),
    @NamedQuery(name = "PessoaJuridicaJuridica.findById", query = "SELECT p FROM PessoaJuridicaJuridica p WHERE p.id = :id"),
    @NamedQuery(name = "PessoaJuridicaJuridica.findByCapitalDeParticipacao", query = "SELECT p FROM PessoaJuridicaJuridica p WHERE p.capitalDeParticipacao = :capitalDeParticipacao"),
    @NamedQuery(name = "PessoaJuridicaJuridica.findByDataDeInicio", query = "SELECT p FROM PessoaJuridicaJuridica p WHERE p.dataDeInicio = :dataDeInicio"),
    @NamedQuery(name = "PessoaJuridicaJuridica.findByDataDeTermino", query = "SELECT p FROM PessoaJuridicaJuridica p WHERE p.dataDeTermino = :dataDeTermino")})
public class PessoaJuridicaJuridica implements Serializable {
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
    @JoinColumn(name = "pessoa_juridica_socio_a_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PessoaJuridica pessoaJuridicaSocioAFk;
    @JoinColumn(name = "pessoa_juridica_socio_b_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PessoaJuridica pessoaJuridicaSocioBFk;

    public PessoaJuridicaJuridica() {
    }

    public PessoaJuridicaJuridica(Integer id) {
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

    public PessoaJuridica getPessoaJuridicaSocioAFk() {
        return pessoaJuridicaSocioAFk;
    }

    public void setPessoaJuridicaSocioAFk(PessoaJuridica pessoaJuridicaSocioAFk) {
        this.pessoaJuridicaSocioAFk = pessoaJuridicaSocioAFk;
    }

    public PessoaJuridica getPessoaJuridicaSocioBFk() {
        return pessoaJuridicaSocioBFk;
    }

    public void setPessoaJuridicaSocioBFk(PessoaJuridica pessoaJuridicaSocioBFk) {
        this.pessoaJuridicaSocioBFk = pessoaJuridicaSocioBFk;
    }
    
    public List<String> changedValues(Object obj) {
        List<String> list = new ArrayList<>();
        final PessoaJuridicaJuridica other = (PessoaJuridicaJuridica) obj;
        if (!Objects.equals(this.capitalDeParticipacao, other.capitalDeParticipacao)) {
            list.add("capitalDeParticipacao");
        }
        if (!Objects.equals(this.dataDeInicio, other.dataDeInicio)) {
            list.add("dataDeInicio");
        }
        if (!Objects.equals(this.dataDeTermino, other.dataDeTermino)) {
            list.add("dataDeTermino");
        }
        if (!Objects.equals(this.pessoaJuridicaSocioBFk, other.pessoaJuridicaSocioBFk)) {
            list.add("pessoaFisicaFk");
        }
        if (!Objects.equals(this.pessoaJuridicaSocioAFk, other.pessoaJuridicaSocioAFk)) {
            list.add("pessoaJuridicaFk");
        }
        return list;
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
        if (!(object instanceof PessoaJuridicaJuridica)) {
            return false;
        }
        PessoaJuridicaJuridica other = (PessoaJuridicaJuridica) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.PessoaJuridicaJuridica[ id=" + id + " ]";
    }
    
}
