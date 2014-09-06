/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author paulones
 */
@Entity
@Table(name = "pessoa_juridica_sucessao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PessoaJuridicaSucessao.findAll", query = "SELECT p FROM PessoaJuridicaSucessao p"),
    @NamedQuery(name = "PessoaJuridicaSucessao.findById", query = "SELECT p FROM PessoaJuridicaSucessao p WHERE p.id = :id"),
    @NamedQuery(name = "PessoaJuridicaSucessao.findByDataDeSucessao", query = "SELECT p FROM PessoaJuridicaSucessao p WHERE p.dataDeSucessao = :dataDeSucessao")})
public class PessoaJuridicaSucessao implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private Character status;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_de_sucessao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeSucessao;
    @JoinColumn(name = "pessoa_juridica_sucedida_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PessoaJuridica pessoaJuridicaSucedidaFk;
    @JoinColumn(name = "pessoa_juridica_sucessora_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PessoaJuridica pessoaJuridicaSucessoraFk;
    @JoinColumn(name = "usuario_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioFk;

    public PessoaJuridicaSucessao() {
    }

    public PessoaJuridicaSucessao(Integer id) {
        this.id = id;
    }

    public PessoaJuridicaSucessao(Integer id, Date dataDeSucessao) {
        this.id = id;
        this.dataDeSucessao = dataDeSucessao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataDeSucessao() {
        return dataDeSucessao;
    }

    public void setDataDeSucessao(Date dataDeSucessao) {
        this.dataDeSucessao = dataDeSucessao;
    }

    public PessoaJuridica getPessoaJuridicaSucedidaFk() {
        return pessoaJuridicaSucedidaFk;
    }

    public void setPessoaJuridicaSucedidaFk(PessoaJuridica pessoaJuridicaSucedidaFk) {
        this.pessoaJuridicaSucedidaFk = pessoaJuridicaSucedidaFk;
    }

    public PessoaJuridica getPessoaJuridicaSucessoraFk() {
        return pessoaJuridicaSucessoraFk;
    }

    public void setPessoaJuridicaSucessoraFk(PessoaJuridica pessoaJuridicaSucessoraFk) {
        this.pessoaJuridicaSucessoraFk = pessoaJuridicaSucessoraFk;
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
        if (!(object instanceof PessoaJuridicaSucessao)) {
            return false;
        }
        PessoaJuridicaSucessao other = (PessoaJuridicaSucessao) object;
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
        final PessoaJuridicaSucessao other = (PessoaJuridicaSucessao) obj;
        if (!Objects.equals(this.pessoaJuridicaSucedidaFk, other.pessoaJuridicaSucedidaFk)) {
            return false;
        }
        if (!Objects.equals(this.pessoaJuridicaSucessoraFk, other.pessoaJuridicaSucessoraFk)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        return true;
    }
    

    @Override
    public String toString() {
        return "entidade.PessoaJuridicaSucessao[ id=" + id + " ]";
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }
    
}
