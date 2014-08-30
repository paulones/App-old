/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author paulones
 */
@Entity
@Table(name = "processo_judicial_historico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProcessoJudicialHistorico.findAll", query = "SELECT p FROM ProcessoJudicialHistorico p"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findById", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.id = :id"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByNumeroDoProcesso", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.numeroDoProcesso = :numeroDoProcesso"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByNumeroDoProcessoAnterior", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.numeroDoProcessoAnterior = :numeroDoProcessoAnterior"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByComarca", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.comarca = :comarca"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByProcurador", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.procurador = :procurador"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByVara", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.vara = :vara"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByVaraAnterior", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.varaAnterior = :varaAnterior"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByGrupoDeEspecializacao", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.grupoDeEspecializacao = :grupoDeEspecializacao"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByDistribuicao", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.distribuicao = :distribuicao"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByDistribuicaoDataDoAto", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.distribuicaoDataDoAto = :distribuicaoDataDoAto"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByDecisaoDoJuiz", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.decisaoDoJuiz = :decisaoDoJuiz"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByDecisaoDoJuizDataDoAto", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.decisaoDoJuizDataDoAto = :decisaoDoJuizDataDoAto"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByDespachoInicial", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.despachoInicial = :despachoInicial"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByDespachoInicialDataDoAto", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.despachoInicialDataDoAto = :despachoInicialDataDoAto"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByNumeroDaCda", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.numeroDaCda = :numeroDaCda"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByFatosGeradores", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.fatosGeradores = :fatosGeradores"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByFundamentacao", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.fundamentacao = :fundamentacao"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByDiscriminacaoDoCreditoImposto", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.discriminacaoDoCreditoImposto = :discriminacaoDoCreditoImposto"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByDiscriminacaoDoCreditoMulta", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.discriminacaoDoCreditoMulta = :discriminacaoDoCreditoMulta"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByDataDeInscricao", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.dataDeInscricao = :dataDeInscricao"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByValorDaCausa", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.valorDaCausa = :valorDaCausa"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByValorAtualizado", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.valorAtualizado = :valorAtualizado"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByNotificacaoAdministrativa", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.notificacaoAdministrativa = :notificacaoAdministrativa"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByNotificacaoAdministrativaDataDoAto", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.notificacaoAdministrativaDataDoAto = :notificacaoAdministrativaDataDoAto"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByExecutadoFk", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.executadoFk = :executadoFk"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByExecutado", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.executado = :executado"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByOutrasInformacoesProcesso", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.outrasInformacoesProcesso = :outrasInformacoesProcesso"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByOutrasInformacoesExecutado", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.outrasInformacoesExecutado = :outrasInformacoesExecutado"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByOutrasInformacoesBem", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.outrasInformacoesBem = :outrasInformacoesBem"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByRecurso", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.recurso = :recurso"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByAtoProcessual", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.atoProcessual = :atoProcessual"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByOutrasInformacoesAtoProcessual", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.outrasInformacoesAtoProcessual = :outrasInformacoesAtoProcessual"),
    @NamedQuery(name = "ProcessoJudicialHistorico.findByDataDeModificacao", query = "SELECT p FROM ProcessoJudicialHistorico p WHERE p.dataDeModificacao = :dataDeModificacao")})
public class ProcessoJudicialHistorico implements Serializable {
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
    @Size(max = 20)
    @Column(name = "discriminacao_do_credito_imposto")
    private String discriminacaoDoCreditoImposto;
    @Size(max = 20)
    @Column(name = "discriminacao_do_credito_multa")
    private String discriminacaoDoCreditoMulta;
    @Size(max = 10)
    @Column(name = "data_de_inscricao")
    private String dataDeInscricao;
    @Size(max = 20)
    @Column(name = "valor_da_causa")
    private String valorDaCausa;
    @Size(max = 20)
    @Column(name = "valor_atualizado")
    private String valorAtualizado;
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
    @Size(max = 50)
    @Column(name = "recurso")
    private String recurso;
    @Size(max = 2147483647)
    @Column(name = "ato_processual")
    private String atoProcessual;
    @Size(max = 300)
    @Column(name = "outras_informacoes_ato_processual")
    private String outrasInformacoesAtoProcessual;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_de_modificacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeModificacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "processoJudicialHistoricoFk")
    private Collection<BemHistorico> bemHistoricoCollection;
    @JoinColumn(name = "processo_judicial_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ProcessoJudicial processoJudicialFk;
    @JoinColumn(name = "tipo_de_recurso_fk", referencedColumnName = "id")
    @ManyToOne
    private TipoRecurso tipoDeRecursoFk;
    @JoinColumn(name = "usuario_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioFk;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "processoJudicialHistoricoFk")
    private Collection<VinculoProcessualHistorico> vinculoProcessualHistoricoCollection;

    public ProcessoJudicialHistorico() {
    }

    public ProcessoJudicialHistorico(Integer id) {
        this.id = id;
    }

    public ProcessoJudicialHistorico(Integer id, String numeroDoProcesso, String comarca, String numeroDaCda, int executadoFk, String executado, Date dataDeModificacao) {
        this.id = id;
        this.numeroDoProcesso = numeroDoProcesso;
        this.comarca = comarca;
        this.numeroDaCda = numeroDaCda;
        this.executadoFk = executadoFk;
        this.executado = executado;
        this.dataDeModificacao = dataDeModificacao;
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

    public String getDiscriminacaoDoCreditoImposto() {
        return discriminacaoDoCreditoImposto;
    }

    public void setDiscriminacaoDoCreditoImposto(String discriminacaoDoCreditoImposto) {
        this.discriminacaoDoCreditoImposto = discriminacaoDoCreditoImposto;
    }

    public String getDiscriminacaoDoCreditoMulta() {
        return discriminacaoDoCreditoMulta;
    }

    public void setDiscriminacaoDoCreditoMulta(String discriminacaoDoCreditoMulta) {
        this.discriminacaoDoCreditoMulta = discriminacaoDoCreditoMulta;
    }

    public String getDataDeInscricao() {
        return dataDeInscricao;
    }

    public void setDataDeInscricao(String dataDeInscricao) {
        this.dataDeInscricao = dataDeInscricao;
    }

    public String getValorDaCausa() {
        return valorDaCausa;
    }

    public void setValorDaCausa(String valorDaCausa) {
        this.valorDaCausa = valorDaCausa;
    }

    public String getValorAtualizado() {
        return valorAtualizado;
    }

    public void setValorAtualizado(String valorAtualizado) {
        this.valorAtualizado = valorAtualizado;
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

    public String getRecurso() {
        return recurso;
    }

    public void setRecurso(String recurso) {
        this.recurso = recurso;
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

    public Date getDataDeModificacao() {
        return dataDeModificacao;
    }

    public void setDataDeModificacao(Date dataDeModificacao) {
        this.dataDeModificacao = dataDeModificacao;
    }

    @XmlTransient
    public Collection<BemHistorico> getBemHistoricoCollection() {
        return bemHistoricoCollection;
    }

    public void setBemHistoricoCollection(Collection<BemHistorico> bemHistoricoCollection) {
        this.bemHistoricoCollection = bemHistoricoCollection;
    }

    public ProcessoJudicial getProcessoJudicialFk() {
        return processoJudicialFk;
    }

    public void setProcessoJudicialFk(ProcessoJudicial processoJudicialFk) {
        this.processoJudicialFk = processoJudicialFk;
    }

    public TipoRecurso getTipoDeRecursoFk() {
        return tipoDeRecursoFk;
    }

    public void setTipoDeRecursoFk(TipoRecurso tipoDeRecursoFk) {
        this.tipoDeRecursoFk = tipoDeRecursoFk;
    }

    public Usuario getUsuarioFk() {
        return usuarioFk;
    }

    public void setUsuarioFk(Usuario usuarioFk) {
        this.usuarioFk = usuarioFk;
    }

    @XmlTransient
    public Collection<VinculoProcessualHistorico> getVinculoProcessualHistoricoCollection() {
        return vinculoProcessualHistoricoCollection;
    }

    public void setVinculoProcessualHistoricoCollection(Collection<VinculoProcessualHistorico> vinculoProcessualHistoricoCollection) {
        this.vinculoProcessualHistoricoCollection = vinculoProcessualHistoricoCollection;
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
        if (!(object instanceof ProcessoJudicialHistorico)) {
            return false;
        }
        ProcessoJudicialHistorico other = (ProcessoJudicialHistorico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.ProcessoJudicialHistorico[ id=" + id + " ]";
    }
    
}