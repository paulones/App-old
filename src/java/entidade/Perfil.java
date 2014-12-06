/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author casa
 */
@Entity
@Table(name = "perfil")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Perfil.findAll", query = "SELECT p FROM Perfil p"),
    @NamedQuery(name = "Perfil.findById", query = "SELECT p FROM Perfil p WHERE p.id = :id"),
    @NamedQuery(name = "Perfil.findByDescricao", query = "SELECT p FROM Perfil p WHERE p.descricao = :descricao"),
    @NamedQuery(name = "Perfil.findByPfVisualizar", query = "SELECT p FROM Perfil p WHERE p.pfVisualizar = :pfVisualizar"),
    @NamedQuery(name = "Perfil.findByPfInserir", query = "SELECT p FROM Perfil p WHERE p.pfInserir = :pfInserir"),
    @NamedQuery(name = "Perfil.findByPfAlterar", query = "SELECT p FROM Perfil p WHERE p.pfAlterar = :pfAlterar"),
    @NamedQuery(name = "Perfil.findByPfRemover", query = "SELECT p FROM Perfil p WHERE p.pfRemover = :pfRemover"),
    @NamedQuery(name = "Perfil.findByPjVisualizar", query = "SELECT p FROM Perfil p WHERE p.pjVisualizar = :pjVisualizar"),
    @NamedQuery(name = "Perfil.findByPjInserir", query = "SELECT p FROM Perfil p WHERE p.pjInserir = :pjInserir"),
    @NamedQuery(name = "Perfil.findByPjAlterar", query = "SELECT p FROM Perfil p WHERE p.pjAlterar = :pjAlterar"),
    @NamedQuery(name = "Perfil.findByPjRemover", query = "SELECT p FROM Perfil p WHERE p.pjRemover = :pjRemover"),
    @NamedQuery(name = "Perfil.findByPjudVisualizar", query = "SELECT p FROM Perfil p WHERE p.pjudVisualizar = :pjudVisualizar"),
    @NamedQuery(name = "Perfil.findByPjudInserir", query = "SELECT p FROM Perfil p WHERE p.pjudInserir = :pjudInserir"),
    @NamedQuery(name = "Perfil.findByPjudAlterar", query = "SELECT p FROM Perfil p WHERE p.pjudAlterar = :pjudAlterar"),
    @NamedQuery(name = "Perfil.findByPjudRemover", query = "SELECT p FROM Perfil p WHERE p.pjudRemover = :pjudRemover"),
    @NamedQuery(name = "Perfil.findByBemVisualizar", query = "SELECT p FROM Perfil p WHERE p.bemVisualizar = :bemVisualizar"),
    @NamedQuery(name = "Perfil.findByBemInserir", query = "SELECT p FROM Perfil p WHERE p.bemInserir = :bemInserir"),
    @NamedQuery(name = "Perfil.findByBemAlterar", query = "SELECT p FROM Perfil p WHERE p.bemAlterar = :bemAlterar"),
    @NamedQuery(name = "Perfil.findByBemRemover", query = "SELECT p FROM Perfil p WHERE p.bemRemover = :bemRemover")})
public class Perfil implements Serializable {
    @OneToMany(mappedBy = "perfil")
    private Collection<Usuario> usuarioCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "descricao")
    private String descricao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pf_visualizar")
    private boolean pfVisualizar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pf_inserir")
    private boolean pfInserir;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pf_alterar")
    private boolean pfAlterar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pf_remover")
    private boolean pfRemover;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pj_visualizar")
    private boolean pjVisualizar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pj_inserir")
    private boolean pjInserir;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pj_alterar")
    private boolean pjAlterar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pj_remover")
    private boolean pjRemover;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pjud_visualizar")
    private boolean pjudVisualizar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pjud_inserir")
    private boolean pjudInserir;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pjud_alterar")
    private boolean pjudAlterar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pjud_remover")
    private boolean pjudRemover;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bem_visualizar")
    private boolean bemVisualizar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bem_inserir")
    private boolean bemInserir;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bem_alterar")
    private boolean bemAlterar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bem_remover")
    private boolean bemRemover;
    @JoinColumn(name = "instituicao_fk", referencedColumnName = "id")
    @ManyToOne
    private Instituicao instituicaoFk;

    public Perfil() {
    }

    public Perfil(Integer id) {
        this.id = id;
    }

    public Perfil(Integer id, String descricao, boolean pfVisualizar, boolean pfInserir, boolean pfAlterar, boolean pfRemover, boolean pjVisualizar, boolean pjInserir, boolean pjAlterar, boolean pjRemover, boolean pjudVisualizar, boolean pjudInserir, boolean pjudAlterar, boolean pjudRemover, boolean bemVisualizar, boolean bemInserir, boolean bemAlterar, boolean bemRemover) {
        this.id = id;
        this.descricao = descricao;
        this.pfVisualizar = pfVisualizar;
        this.pfInserir = pfInserir;
        this.pfAlterar = pfAlterar;
        this.pfRemover = pfRemover;
        this.pjVisualizar = pjVisualizar;
        this.pjInserir = pjInserir;
        this.pjAlterar = pjAlterar;
        this.pjRemover = pjRemover;
        this.pjudVisualizar = pjudVisualizar;
        this.pjudInserir = pjudInserir;
        this.pjudAlterar = pjudAlterar;
        this.pjudRemover = pjudRemover;
        this.bemVisualizar = bemVisualizar;
        this.bemInserir = bemInserir;
        this.bemAlterar = bemAlterar;
        this.bemRemover = bemRemover;
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

    public boolean getPfVisualizar() {
        return pfVisualizar;
    }

    public void setPfVisualizar(boolean pfVisualizar) {
        this.pfVisualizar = pfVisualizar;
    }

    public boolean getPfInserir() {
        return pfInserir;
    }

    public void setPfInserir(boolean pfInserir) {
        this.pfInserir = pfInserir;
    }

    public boolean getPfAlterar() {
        return pfAlterar;
    }

    public void setPfAlterar(boolean pfAlterar) {
        this.pfAlterar = pfAlterar;
    }

    public boolean getPfRemover() {
        return pfRemover;
    }

    public void setPfRemover(boolean pfRemover) {
        this.pfRemover = pfRemover;
    }

    public boolean getPjVisualizar() {
        return pjVisualizar;
    }

    public void setPjVisualizar(boolean pjVisualizar) {
        this.pjVisualizar = pjVisualizar;
    }

    public boolean getPjInserir() {
        return pjInserir;
    }

    public void setPjInserir(boolean pjInserir) {
        this.pjInserir = pjInserir;
    }

    public boolean getPjAlterar() {
        return pjAlterar;
    }

    public void setPjAlterar(boolean pjAlterar) {
        this.pjAlterar = pjAlterar;
    }

    public boolean getPjRemover() {
        return pjRemover;
    }

    public void setPjRemover(boolean pjRemover) {
        this.pjRemover = pjRemover;
    }

    public boolean getPjudVisualizar() {
        return pjudVisualizar;
    }

    public void setPjudVisualizar(boolean pjudVisualizar) {
        this.pjudVisualizar = pjudVisualizar;
    }

    public boolean getPjudInserir() {
        return pjudInserir;
    }

    public void setPjudInserir(boolean pjudInserir) {
        this.pjudInserir = pjudInserir;
    }

    public boolean getPjudAlterar() {
        return pjudAlterar;
    }

    public void setPjudAlterar(boolean pjudAlterar) {
        this.pjudAlterar = pjudAlterar;
    }

    public boolean getPjudRemover() {
        return pjudRemover;
    }

    public void setPjudRemover(boolean pjudRemover) {
        this.pjudRemover = pjudRemover;
    }

    public boolean getBemVisualizar() {
        return bemVisualizar;
    }

    public void setBemVisualizar(boolean bemVisualizar) {
        this.bemVisualizar = bemVisualizar;
    }

    public boolean getBemInserir() {
        return bemInserir;
    }

    public void setBemInserir(boolean bemInserir) {
        this.bemInserir = bemInserir;
    }

    public boolean getBemAlterar() {
        return bemAlterar;
    }

    public void setBemAlterar(boolean bemAlterar) {
        this.bemAlterar = bemAlterar;
    }

    public boolean getBemRemover() {
        return bemRemover;
    }

    public void setBemRemover(boolean bemRemover) {
        this.bemRemover = bemRemover;
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
        if (!(object instanceof Perfil)) {
            return false;
        }
        Perfil other = (Perfil) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.Perfil[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }
    
}
