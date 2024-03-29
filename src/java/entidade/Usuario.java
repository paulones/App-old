/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author paulones
 */
@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findById", query = "SELECT u FROM Usuario u WHERE u.id = :id"),
    @NamedQuery(name = "Usuario.findByCpf", query = "SELECT u FROM Usuario u WHERE u.cpf = :cpf"),
    @NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email"),
    @NamedQuery(name = "Usuario.findByNome", query = "SELECT u FROM Usuario u WHERE u.nome = :nome"),
    @NamedQuery(name = "Usuario.findBySenha", query = "SELECT u FROM Usuario u WHERE u.senha = :senha")})
public class Usuario implements Serializable {
    @JoinColumn(name = "perfil", referencedColumnName = "id")
    @ManyToOne
    private Perfil perfil;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioFk")
    private Collection<PessoaJuridicaSucessaoHistorico> pessoaJuridicaSucessaoHistoricoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioFk")
    private Collection<PessoaJuridicaSucessao> pessoaJuridicaSucessaoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioFk")
    private Collection<ProcessoJudicialHistorico> processoJudicialHistoricoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioFk")
    private Collection<ProcessoJudicial> processoJudicialCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioFk")
    private Collection<PessoaJuridica> pessoaJuridicaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioFk")
    private Collection<PessoaFisica> pessoaFisicaCollection;
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
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "senha")
    private String senha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioFk")
    private Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioFk")
    private Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
    private RecuperarSenha recuperarSenha;

    public Usuario() {
    }

    public Usuario(Integer id) {
        this.id = id;
    }

    public Usuario(Integer id, String cpf, String email, String nome, String senha) {
        this.id = id;
        this.cpf = cpf;
        this.email = email;
        this.nome = nome;
        this.senha = senha;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @XmlTransient
    public Collection<PessoaFisicaHistorico> getPessoaFisicaHistoricoCollection() {
        return pessoaFisicaHistoricoCollection;
    }

    public void setPessoaFisicaHistoricoCollection(Collection<PessoaFisicaHistorico> pessoaFisicaHistoricoCollection) {
        this.pessoaFisicaHistoricoCollection = pessoaFisicaHistoricoCollection;
    }

    @XmlTransient
    public Collection<PessoaJuridicaHistorico> getPessoaJuridicaHistoricoCollection() {
        return pessoaJuridicaHistoricoCollection;
    }

    public void setPessoaJuridicaHistoricoCollection(Collection<PessoaJuridicaHistorico> pessoaJuridicaHistoricoCollection) {
        this.pessoaJuridicaHistoricoCollection = pessoaJuridicaHistoricoCollection;
    }

    public RecuperarSenha getRecuperarSenha() {
        return recuperarSenha;
    }

    public void setRecuperarSenha(RecuperarSenha recuperarSenha) {
        this.recuperarSenha = recuperarSenha;
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.Usuario[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<PessoaJuridica> getPessoaJuridicaCollection() {
        return pessoaJuridicaCollection;
    }

    public void setPessoaJuridicaCollection(Collection<PessoaJuridica> pessoaJuridicaCollection) {
        this.pessoaJuridicaCollection = pessoaJuridicaCollection;
    }

    @XmlTransient
    public Collection<PessoaFisica> getPessoaFisicaCollection() {
        return pessoaFisicaCollection;
    }

    public void setPessoaFisicaCollection(Collection<PessoaFisica> pessoaFisicaCollection) {
        this.pessoaFisicaCollection = pessoaFisicaCollection;
    }

    @XmlTransient
    public Collection<ProcessoJudicial> getProcessoJudicialCollection() {
        return processoJudicialCollection;
    }

    public void setProcessoJudicialCollection(Collection<ProcessoJudicial> processoJudicialCollection) {
        this.processoJudicialCollection = processoJudicialCollection;
    }

    @XmlTransient
    public Collection<ProcessoJudicialHistorico> getProcessoJudicialHistoricoCollection() {
        return processoJudicialHistoricoCollection;
    }

    public void setProcessoJudicialHistoricoCollection(Collection<ProcessoJudicialHistorico> processoJudicialHistoricoCollection) {
        this.processoJudicialHistoricoCollection = processoJudicialHistoricoCollection;
    }

    @XmlTransient
    public Collection<PessoaJuridicaSucessao> getPessoaJuridicaSucessaoCollection() {
        return pessoaJuridicaSucessaoCollection;
    }

    public void setPessoaJuridicaSucessaoCollection(Collection<PessoaJuridicaSucessao> pessoaJuridicaSucessaoCollection) {
        this.pessoaJuridicaSucessaoCollection = pessoaJuridicaSucessaoCollection;
    }

    @XmlTransient
    public Collection<PessoaJuridicaSucessaoHistorico> getPessoaJuridicaSucessaoHistoricoCollection() {
        return pessoaJuridicaSucessaoHistoricoCollection;
    }

    public void setPessoaJuridicaSucessaoHistoricoCollection(Collection<PessoaJuridicaSucessaoHistorico> pessoaJuridicaSucessaoHistoricoCollection) {
        this.pessoaJuridicaSucessaoHistoricoCollection = pessoaJuridicaSucessaoHistoricoCollection;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
    
}
