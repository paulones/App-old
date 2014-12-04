/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author paulones
 */
public class ExecutadoHistorico implements Serializable {
    
    private ProcessoJudicialHistorico processoJudicialHistorico;
    private EnderecoPessoa enderecoPessoaFisica;
    private EnderecoPessoa enderecoPessoaJuridica;
    private List<CitacaoHistorico> citacaoHistoricoList;
    private List<SocioRedirecionamentoHistorico> socioRedirecionamentoHistoricoList;
    private List<PenhoraHistorico> penhoraHistoricoList;

    public ExecutadoHistorico() {
    }

    public ExecutadoHistorico(ProcessoJudicialHistorico processoJudicialHistorico, EnderecoPessoa enderecoPessoaFisica, EnderecoPessoa enderecoPessoaJuridica, List<CitacaoHistorico> citacaoHistoricoList, List<SocioRedirecionamentoHistorico> socioRedirecionamentoHistoricoList, List<PenhoraHistorico> penhoraHistoricoList) {
        this.processoJudicialHistorico = processoJudicialHistorico;
        this.enderecoPessoaFisica = enderecoPessoaFisica;
        this.enderecoPessoaJuridica = enderecoPessoaJuridica;
        this.citacaoHistoricoList = citacaoHistoricoList;
        this.socioRedirecionamentoHistoricoList = socioRedirecionamentoHistoricoList;
        this.penhoraHistoricoList = penhoraHistoricoList;
    }   
    
    public ProcessoJudicialHistorico getProcessoJudicialHistorico() {
        return processoJudicialHistorico;
    }

    public void setProcessoJudicialHistorico(ProcessoJudicialHistorico processoJudicialHistorico) {
        this.processoJudicialHistorico = processoJudicialHistorico;
    }

    public EnderecoPessoa getEnderecoPessoaFisica() {
        return enderecoPessoaFisica;
    }

    public void setEnderecoPessoaFisica(EnderecoPessoa enderecoPessoaFisica) {
        this.enderecoPessoaFisica = enderecoPessoaFisica;
    }

    public EnderecoPessoa getEnderecoPessoaJuridica() {
        return enderecoPessoaJuridica;
    }

    public void setEnderecoPessoaJuridica(EnderecoPessoa enderecoPessoaJuridica) {
        this.enderecoPessoaJuridica = enderecoPessoaJuridica;
    }

    public List<CitacaoHistorico> getCitacaoHistoricoList() {
        return citacaoHistoricoList;
    }

    public void setCitacaoHistoricoList(List<CitacaoHistorico> citacaoHistoricoList) {
        this.citacaoHistoricoList = citacaoHistoricoList;
    }

    public List<SocioRedirecionamentoHistorico> getSocioRedirecionamentoHistoricoList() {
        return socioRedirecionamentoHistoricoList;
    }

    public void setSocioRedirecionamentoHistoricoList(List<SocioRedirecionamentoHistorico> socioRedirecionamentoHistoricoList) {
        this.socioRedirecionamentoHistoricoList = socioRedirecionamentoHistoricoList;
    }

    public List<PenhoraHistorico> getPenhoraHistoricoList() {
        return penhoraHistoricoList;
    }

    public void setPenhoraHistoricoList(List<PenhoraHistorico> penhoraHistoricoList) {
        this.penhoraHistoricoList = penhoraHistoricoList;
    }

}
