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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author paulones
 */
@Entity
@Table(name = "pessoa_fisica_fisica_historico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PessoaFisicaFisicaHistorico.findAll", query = "SELECT p FROM PessoaFisicaFisicaHistorico p"),
    @NamedQuery(name = "PessoaFisicaFisicaHistorico.findById", query = "SELECT p FROM PessoaFisicaFisicaHistorico p WHERE p.id = :id")})
public class PessoaFisicaFisicaHistorico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "pessoa_fisica_primaria_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PessoaFisica pessoaFisicaPrimariaFk;
    @JoinColumn(name = "pessoa_fisica_secundaria_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PessoaFisica pessoaFisicaSecundariaFk;
    @JoinColumn(name = "pessoa_fisica_historico_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PessoaFisicaHistorico pessoaFisicaHistoricoFk;
    @JoinColumn(name = "vinculo_social_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private VinculoSocial vinculoSocialFk;

    public PessoaFisicaFisicaHistorico() {
    }

    public PessoaFisicaFisicaHistorico(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PessoaFisica getPessoaFisicaPrimariaFk() {
        return pessoaFisicaPrimariaFk;
    }

    public void setPessoaFisicaPrimariaFk(PessoaFisica pessoaFisicaPrimariaFk) {
        this.pessoaFisicaPrimariaFk = pessoaFisicaPrimariaFk;
    }

    public PessoaFisica getPessoaFisicaSecundariaFk() {
        return pessoaFisicaSecundariaFk;
    }

    public void setPessoaFisicaSecundariaFk(PessoaFisica pessoaFisicaSecundariaFk) {
        this.pessoaFisicaSecundariaFk = pessoaFisicaSecundariaFk;
    }

    public PessoaFisicaHistorico getPessoaFisicaHistoricoFk() {
        return pessoaFisicaHistoricoFk;
    }

    public void setPessoaFisicaHistoricoFk(PessoaFisicaHistorico pessoaFisicaHistoricoFk) {
        this.pessoaFisicaHistoricoFk = pessoaFisicaHistoricoFk;
    }

    public VinculoSocial getVinculoSocialFk() {
        return vinculoSocialFk;
    }

    public void setVinculoSocialFk(VinculoSocial vinculoSocialFk) {
        this.vinculoSocialFk = vinculoSocialFk;
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
        if (!(object instanceof PessoaFisicaFisicaHistorico)) {
            return false;
        }
        PessoaFisicaFisicaHistorico other = (PessoaFisicaFisicaHistorico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.PessoaFisicaFisicaHistorico[ id=" + id + " ]";
    }
    
}
