/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;
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
@Table(name = "processo_judicial")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProcessoJudicial.findAll", query = "SELECT p FROM ProcessoJudicial p"),
    @NamedQuery(name = "ProcessoJudicial.findById", query = "SELECT p FROM ProcessoJudicial p WHERE p.id = :id"),
    @NamedQuery(name = "ProcessoJudicial.findByNumeroDoProcesso", query = "SELECT p FROM ProcessoJudicial p WHERE p.numeroDoProcesso = :numeroDoProcesso"),
    @NamedQuery(name = "ProcessoJudicial.findByNumeroDoProcessoAnterior", query = "SELECT p FROM ProcessoJudicial p WHERE p.numeroDoProcessoAnterior = :numeroDoProcessoAnterior"),
    @NamedQuery(name = "ProcessoJudicial.findByComarca", query = "SELECT p FROM ProcessoJudicial p WHERE p.comarca = :comarca"),
    @NamedQuery(name = "ProcessoJudicial.findByProcurador", query = "SELECT p FROM ProcessoJudicial p WHERE p.procurador = :procurador"),
    @NamedQuery(name = "ProcessoJudicial.findByVara", query = "SELECT p FROM ProcessoJudicial p WHERE p.vara = :vara"),
    @NamedQuery(name = "ProcessoJudicial.findByVaraAnterior", query = "SELECT p FROM ProcessoJudicial p WHERE p.varaAnterior = :varaAnterior"),
    @NamedQuery(name = "ProcessoJudicial.findByGrupoDeEspecializacao", query = "SELECT p FROM ProcessoJudicial p WHERE p.grupoDeEspecializacao = :grupoDeEspecializacao"),
    @NamedQuery(name = "ProcessoJudicial.findByDistribuicao", query = "SELECT p FROM ProcessoJudicial p WHERE p.distribuicao = :distribuicao"),
    @NamedQuery(name = "ProcessoJudicial.findByDistribuicaoDataDoAto", query = "SELECT p FROM ProcessoJudicial p WHERE p.distribuicaoDataDoAto = :distribuicaoDataDoAto"),
    @NamedQuery(name = "ProcessoJudicial.findByDecisaoDoJuiz", query = "SELECT p FROM ProcessoJudicial p WHERE p.decisaoDoJuiz = :decisaoDoJuiz"),
    @NamedQuery(name = "ProcessoJudicial.findByDecisaoDoJuizDataDoAto", query = "SELECT p FROM ProcessoJudicial p WHERE p.decisaoDoJuizDataDoAto = :decisaoDoJuizDataDoAto"),
    @NamedQuery(name = "ProcessoJudicial.findByDespachoInicial", query = "SELECT p FROM ProcessoJudicial p WHERE p.despachoInicial = :despachoInicial"),
    @NamedQuery(name = "ProcessoJudicial.findByDespachoInicialDataDoAto", query = "SELECT p FROM ProcessoJudicial p WHERE p.despachoInicialDataDoAto = :despachoInicialDataDoAto"),
    @NamedQuery(name = "ProcessoJudicial.findByNumeroDaCda", query = "SELECT p FROM ProcessoJudicial p WHERE p.numeroDaCda = :numeroDaCda"),
    @NamedQuery(name = "ProcessoJudicial.findByFatosGeradores", query = "SELECT p FROM ProcessoJudicial p WHERE p.fatosGeradores = :fatosGeradores"),
    @NamedQuery(name = "ProcessoJudicial.findByFundamentacao", query = "SELECT p FROM ProcessoJudicial p WHERE p.fundamentacao = :fundamentacao"),
    @NamedQuery(name = "ProcessoJudicial.findByDiscriminacaoDoCreditoImposto", query = "SELECT p FROM ProcessoJudicial p WHERE p.discriminacaoDoCreditoImposto = :discriminacaoDoCreditoImposto"),
    @NamedQuery(name = "ProcessoJudicial.findByDiscriminacaoDoCreditoMulta", query = "SELECT p FROM ProcessoJudicial p WHERE p.discriminacaoDoCreditoMulta = :discriminacaoDoCreditoMulta"),
    @NamedQuery(name = "ProcessoJudicial.findByDataDeInscricao", query = "SELECT p FROM ProcessoJudicial p WHERE p.dataDeInscricao = :dataDeInscricao"),
    @NamedQuery(name = "ProcessoJudicial.findByValorDaCausa", query = "SELECT p FROM ProcessoJudicial p WHERE p.valorDaCausa = :valorDaCausa"),
    @NamedQuery(name = "ProcessoJudicial.findByValorAtualizado", query = "SELECT p FROM ProcessoJudicial p WHERE p.valorAtualizado = :valorAtualizado"),
    @NamedQuery(name = "ProcessoJudicial.findByNotificacaoAdministrativa", query = "SELECT p FROM ProcessoJudicial p WHERE p.notificacaoAdministrativa = :notificacaoAdministrativa"),
    @NamedQuery(name = "ProcessoJudicial.findByNotificacaoAdministrativaDataDoAto", query = "SELECT p FROM ProcessoJudicial p WHERE p.notificacaoAdministrativaDataDoAto = :notificacaoAdministrativaDataDoAto"),
    @NamedQuery(name = "ProcessoJudicial.findByExecutadoFk", query = "SELECT p FROM ProcessoJudicial p WHERE p.executadoFk = :executadoFk"),
    @NamedQuery(name = "ProcessoJudicial.findByExecutado", query = "SELECT p FROM ProcessoJudicial p WHERE p.executado = :executado"),
    @NamedQuery(name = "ProcessoJudicial.findByOutrasInformacoesProcesso", query = "SELECT p FROM ProcessoJudicial p WHERE p.outrasInformacoesProcesso = :outrasInformacoesProcesso"),
    @NamedQuery(name = "ProcessoJudicial.findByOutrasInformacoesExecutado", query = "SELECT p FROM ProcessoJudicial p WHERE p.outrasInformacoesExecutado = :outrasInformacoesExecutado"),
    @NamedQuery(name = "ProcessoJudicial.findByOutrasInformacoesBem", query = "SELECT p FROM ProcessoJudicial p WHERE p.outrasInformacoesBem = :outrasInformacoesBem")})
public class ProcessoJudicial implements Serializable {
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "discriminacao_do_credito_imposto")
    private BigDecimal discriminacaoDoCreditoImposto; 
    @Column(name = "discriminacao_do_credito_multa")
    private BigDecimal discriminacaoDoCreditoMulta;
    @Column(name = "valor_da_causa")
    private BigDecimal valorDaCausa;
    @Column(name = "valor_atualizado")
    private BigDecimal valorAtualizado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "processoJudicialFk")
    private Collection<ProcessoJudicialHistorico> processoJudicialHistoricoCollection;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private Character status;
    @JoinColumn(name = "usuario_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioFk;
    @Size(max = 300)
    @Column(name = "outras_informacoes_ato_processual")
    private String outrasInformacoesAtoProcessual;
    @Size(max = 2147483647)
    @Column(name = "ato_processual")
    private String atoProcessual;
    @JoinColumn(name = "tipo_de_recurso_fk", referencedColumnName = "id")
    @ManyToOne
    private TipoRecurso tipoDeRecursoFk;
    @Size(max = 50)
    @Column(name = "recurso")
    private String recurso;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "processoJudicialFk")
    private Collection<VinculoProcessual> vinculoProcessualCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "numero_do_processo")
    private String numeroDoProcesso;
    @Size(max = 50)
    @Column(name = "numero_do_processo_anterior")
    private String numeroDoProcessoAnterior;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "comarca")
    private String comarca;
    @Size(max = 50)
    @Column(name = "procurador")
    private String procurador;
    @Size(max = 50)
    @Column(name = "vara")
    private String vara;
    @Size(max = 50)
    @Column(name = "vara_anterior")
    private String varaAnterior;
    @Size(max = 50)
    @Column(name = "grupo_de_especializacao")
    private String grupoDeEspecializacao;
    @Size(max = 100)
    @Column(name = "distribuicao")
    private String distribuicao;
    @Size(max = 10)
    @Column(name = "distribuicao_data_do_ato")
    private String distribuicaoDataDoAto;
    @Size(max = 100)
    @Column(name = "decisao_do_juiz")
    private String decisaoDoJuiz;
    @Size(max = 10)
    @Column(name = "decisao_do_juiz_data_do_ato")
    private String decisaoDoJuizDataDoAto;
    @Size(max = 100)
    @Column(name = "despacho_inicial")
    private String despachoInicial;
    @Size(max = 10)
    @Column(name = "despacho_inicial_data_do_ato")
    private String despachoInicialDataDoAto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "numero_da_cda")
    private String numeroDaCda;
    @Size(max = 200)
    @Column(name = "fatos_geradores")
    private String fatosGeradores;
    @Size(max = 150)
    @Column(name = "fundamentacao")
    private String fundamentacao;
    @Size(max = 10)
    @Column(name = "data_de_inscricao")
    private String dataDeInscricao;
    @Size(max = 50)
    @Column(name = "notificacao_administrativa")
    private String notificacaoAdministrativa;
    @Size(max = 10)
    @Column(name = "notificacao_administrativa_data_do_ato")
    private String notificacaoAdministrativaDataDoAto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "executado_fk")
    private int executadoFk;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "executado")
    private String executado;
    @Size(max = 300)
    @Column(name = "outras_informacoes_processo")
    private String outrasInformacoesProcesso;
    @Size(max = 300)
    @Column(name = "outras_informacoes_executado")
    private String outrasInformacoesExecutado;
    @Size(max = 300)
    @Column(name = "outras_informacoes_bem")
    private String outrasInformacoesBem;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "processoJudicialFk")
    private Collection<Bem> bemCollection;

    public ProcessoJudicial() {
    }

    public ProcessoJudicial(Integer id) {
        this.id = id;
    }

    public ProcessoJudicial(Integer id, String numeroDoProcesso, String comarca, String numeroDaCda, int executadoFk, String executado) {
        this.id = id;
        this.numeroDoProcesso = numeroDoProcesso;
        this.comarca = comarca;
        this.numeroDaCda = numeroDaCda;
        this.executadoFk = executadoFk;
        this.executado = executado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroDoProcesso() {
        return numeroDoProcesso;
    }

    public void setNumeroDoProcesso(String numeroDoProcesso) {
        this.numeroDoProcesso = numeroDoProcesso;
    }

    public String getNumeroDoProcessoAnterior() {
        return numeroDoProcessoAnterior;
    }

    public void setNumeroDoProcessoAnterior(String numeroDoProcessoAnterior) {
        this.numeroDoProcessoAnterior = numeroDoProcessoAnterior;
    }

    public String getComarca() {
        return comarca;
    }

    public void setComarca(String comarca) {
        this.comarca = comarca;
    }

    public String getProcurador() {
        return procurador;
    }

    public void setProcurador(String procurador) {
        this.procurador = procurador;
    }

    public String getVara() {
        return vara;
    }

    public void setVara(String vara) {
        this.vara = vara;
    }

    public String getVaraAnterior() {
        return varaAnterior;
    }

    public void setVaraAnterior(String varaAnterior) {
        this.varaAnterior = varaAnterior;
    }

    public String getGrupoDeEspecializacao() {
        return grupoDeEspecializacao;
    }

    public void setGrupoDeEspecializacao(String grupoDeEspecializacao) {
        this.grupoDeEspecializacao = grupoDeEspecializacao;
    }

    public String getDistribuicao() {
        return distribuicao;
    }

    public void setDistribuicao(String distribuicao) {
        this.distribuicao = distribuicao;
    }

    public String getDistribuicaoDataDoAto() {
        return distribuicaoDataDoAto;
    }

    public void setDistribuicaoDataDoAto(String distribuicaoDataDoAto) {
        this.distribuicaoDataDoAto = distribuicaoDataDoAto;
    }

    public String getDecisaoDoJuiz() {
        return decisaoDoJuiz;
    }

    public void setDecisaoDoJuiz(String decisaoDoJuiz) {
        this.decisaoDoJuiz = decisaoDoJuiz;
    }

    public String getDecisaoDoJuizDataDoAto() {
        return decisaoDoJuizDataDoAto;
    }

    public void setDecisaoDoJuizDataDoAto(String decisaoDoJuizDataDoAto) {
        this.decisaoDoJuizDataDoAto = decisaoDoJuizDataDoAto;
    }

    public String getDespachoInicial() {
        return despachoInicial;
    }

    public void setDespachoInicial(String despachoInicial) {
        this.despachoInicial = despachoInicial;
    }

    public String getDespachoInicialDataDoAto() {
        return despachoInicialDataDoAto;
    }

    public void setDespachoInicialDataDoAto(String despachoInicialDataDoAto) {
        this.despachoInicialDataDoAto = despachoInicialDataDoAto;
    }

    public String getNumeroDaCda() {
        return numeroDaCda;
    }

    public void setNumeroDaCda(String numeroDaCda) {
        this.numeroDaCda = numeroDaCda;
    }

    public String getFatosGeradores() {
        return fatosGeradores;
    }

    public void setFatosGeradores(String fatosGeradores) {
        this.fatosGeradores = fatosGeradores;
    }

    public String getFundamentacao() {
        return fundamentacao;
    }

    public void setFundamentacao(String fundamentacao) {
        this.fundamentacao = fundamentacao;
    }

    public String getDataDeInscricao() {
        return dataDeInscricao;
    }

    public void setDataDeInscricao(String dataDeInscricao) {
        this.dataDeInscricao = dataDeInscricao;
    }

    public String getNotificacaoAdministrativa() {
        return notificacaoAdministrativa;
    }

    public void setNotificacaoAdministrativa(String notificacaoAdministrativa) {
        this.notificacaoAdministrativa = notificacaoAdministrativa;
    }

    public String getNotificacaoAdministrativaDataDoAto() {
        return notificacaoAdministrativaDataDoAto;
    }

    public void setNotificacaoAdministrativaDataDoAto(String notificacaoAdministrativaDataDoAto) {
        this.notificacaoAdministrativaDataDoAto = notificacaoAdministrativaDataDoAto;
    }

    public int getExecutadoFk() {
        return executadoFk;
    }

    public void setExecutadoFk(int executadoFk) {
        this.executadoFk = executadoFk;
    }

    public String getExecutado() {
        return executado;
    }

    public void setExecutado(String executado) {
        this.executado = executado;
    }

    public String getOutrasInformacoesProcesso() {
        return outrasInformacoesProcesso;
    }

    public void setOutrasInformacoesProcesso(String outrasInformacoesProcesso) {
        this.outrasInformacoesProcesso = outrasInformacoesProcesso;
    }

    public String getOutrasInformacoesExecutado() {
        return outrasInformacoesExecutado;
    }

    public void setOutrasInformacoesExecutado(String outrasInformacoesExecutado) {
        this.outrasInformacoesExecutado = outrasInformacoesExecutado;
    }

    public String getOutrasInformacoesBem() {
        return outrasInformacoesBem;
    }

    public void setOutrasInformacoesBem(String outrasInformacoesBem) {
        this.outrasInformacoesBem = outrasInformacoesBem;
    }

    @XmlTransient
    public Collection<Bem> getBemCollection() {
        return bemCollection;
    }

    public void setBemCollection(Collection<Bem> bemCollection) {
        this.bemCollection = bemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProcessoJudicial other = (ProcessoJudicial) obj;
        if (!Objects.equals(this.id, other.id)) {
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
        final ProcessoJudicial other = (ProcessoJudicial) obj;
        if (!Objects.equals(this.outrasInformacoesAtoProcessual, other.outrasInformacoesAtoProcessual)) {
            return false;
        }
        if (!Objects.equals(this.atoProcessual, other.atoProcessual)) {
            return false;
        }
        if (!Objects.equals(this.tipoDeRecursoFk, other.tipoDeRecursoFk)) {
            return false;
        }
        if (!Objects.equals(this.recurso, other.recurso)) {
            return false;
        }
        if (!Objects.equals(this.numeroDoProcesso, other.numeroDoProcesso)) {
            return false;
        }
        if (!Objects.equals(this.numeroDoProcessoAnterior, other.numeroDoProcessoAnterior)) {
            return false;
        }
        if (!Objects.equals(this.comarca, other.comarca)) {
            return false;
        }
        if (!Objects.equals(this.procurador, other.procurador)) {
            return false;
        }
        if (!Objects.equals(this.vara, other.vara)) {
            return false;
        }
        if (!Objects.equals(this.varaAnterior, other.varaAnterior)) {
            return false;
        }
        if (!Objects.equals(this.grupoDeEspecializacao, other.grupoDeEspecializacao)) {
            return false;
        }
        if (!Objects.equals(this.distribuicao, other.distribuicao)) {
            return false;
        }
        if (!Objects.equals(this.distribuicaoDataDoAto, other.distribuicaoDataDoAto)) {
            return false;
        }
        if (!Objects.equals(this.decisaoDoJuiz, other.decisaoDoJuiz)) {
            return false;
        }
        if (!Objects.equals(this.decisaoDoJuizDataDoAto, other.decisaoDoJuizDataDoAto)) {
            return false;
        }
        if (!Objects.equals(this.despachoInicial, other.despachoInicial)) {
            return false;
        }
        if (!Objects.equals(this.despachoInicialDataDoAto, other.despachoInicialDataDoAto)) {
            return false;
        }
        if (!Objects.equals(this.numeroDaCda, other.numeroDaCda)) {
            return false;
        }
        if (!Objects.equals(this.fatosGeradores, other.fatosGeradores)) {
            return false;
        }
        if (!Objects.equals(this.fundamentacao, other.fundamentacao)) {
            return false;
        }
        if (!Objects.equals(this.discriminacaoDoCreditoImposto, other.discriminacaoDoCreditoImposto)) {
            return false;
        }
        if (!Objects.equals(this.discriminacaoDoCreditoMulta, other.discriminacaoDoCreditoMulta)) {
            return false;
        }
        if (!Objects.equals(this.dataDeInscricao, other.dataDeInscricao)) {
            return false;
        }
        if (!Objects.equals(this.valorDaCausa, other.valorDaCausa)) {
            return false;
        }
        if (!Objects.equals(this.valorAtualizado, other.valorAtualizado)) {
            return false;
        }
        if (!Objects.equals(this.notificacaoAdministrativa, other.notificacaoAdministrativa)) {
            return false;
        }
        if (!Objects.equals(this.notificacaoAdministrativaDataDoAto, other.notificacaoAdministrativaDataDoAto)) {
            return false;
        }
        if (!Objects.equals(this.executadoFk, other.executadoFk)) {
            return false;
        }
        if (!Objects.equals(this.executado, other.executado)) {
            return false;
        }
        if (!Objects.equals(this.outrasInformacoesProcesso, other.outrasInformacoesProcesso)) {
            return false;
        }
        if (!Objects.equals(this.outrasInformacoesExecutado, other.outrasInformacoesExecutado)) {
            return false;
        }
        if (!Objects.equals(this.outrasInformacoesBem, other.outrasInformacoesBem)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "entidade.ProcessoJudicial[ id=" + id + " ]";
    }

    public String getRecurso() {
        return recurso;
    }

    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }

    @XmlTransient
    public Collection<VinculoProcessual> getVinculoProcessualCollection() {
        return vinculoProcessualCollection;
    }

    public void setVinculoProcessualCollection(Collection<VinculoProcessual> vinculoProcessualCollection) {
        this.vinculoProcessualCollection = vinculoProcessualCollection;
    }

    public TipoRecurso getTipoDeRecursoFk() {
        return tipoDeRecursoFk;
    }

    public void setTipoDeRecursoFk(TipoRecurso tipoDeRecursoFk) {
        this.tipoDeRecursoFk = tipoDeRecursoFk;
    }

    public String getAtoProcessual() {
        return atoProcessual;
    }

    public void setAtoProcessual(String atoProcessual) {
        this.atoProcessual = atoProcessual;
    }

    public String getOutrasInformacoesAtoProcessual() {
        return outrasInformacoesAtoProcessual;
    }

    public void setOutrasInformacoesAtoProcessual(String outrasInformacoesAtoProcessual) {
        this.outrasInformacoesAtoProcessual = outrasInformacoesAtoProcessual;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public Usuario getUsuarioFk() {
        return usuarioFk;
    }

    public void setUsuarioFk(Usuario usuarioFk) {
        this.usuarioFk = usuarioFk;
    }

    @XmlTransient
    public Collection<ProcessoJudicialHistorico> getProcessoJudicialHistoricoCollection() {
        return processoJudicialHistoricoCollection;
    }

    public void setProcessoJudicialHistoricoCollection(Collection<ProcessoJudicialHistorico> processoJudicialHistoricoCollection) {
        this.processoJudicialHistoricoCollection = processoJudicialHistoricoCollection;
    }

    public BigDecimal getDiscriminacaoDoCreditoImposto() {
        return discriminacaoDoCreditoImposto;
    }

    public void setDiscriminacaoDoCreditoImposto(BigDecimal discriminacaoDoCreditoImposto) {
        this.discriminacaoDoCreditoImposto = discriminacaoDoCreditoImposto;
    }

    public BigDecimal getDiscriminacaoDoCreditoMulta() {
        return discriminacaoDoCreditoMulta;
    }

    public void setDiscriminacaoDoCreditoMulta(BigDecimal discriminacaoDoCreditoMulta) {
        this.discriminacaoDoCreditoMulta = discriminacaoDoCreditoMulta;
    }

    public BigDecimal getValorDaCausa() {
        return valorDaCausa;
    }

    public void setValorDaCausa(BigDecimal valorDaCausa) {
        this.valorDaCausa = valorDaCausa;
    }

    public BigDecimal getValorAtualizado() {
        return valorAtualizado;
    }

    public void setValorAtualizado(BigDecimal valorAtualizado) {
        this.valorAtualizado = valorAtualizado;
    }

}
