/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;

/**
 *
 * @author paulones
 */
public class ExecutadoHistorico implements Serializable {
    
    private ProcessoJudicialHistorico processoJudicialHistorico;
    private EnderecoPessoa enderecoPessoaFisica;
    private EnderecoPessoa enderecoPessoaJuridica;

    public ExecutadoHistorico() {
    }

    public ExecutadoHistorico(ProcessoJudicialHistorico processoJudicialHistorico, EnderecoPessoa enderecoPessoaFisica, EnderecoPessoa enderecoPessoaJuridica) {
        this.processoJudicialHistorico = processoJudicialHistorico;
        this.enderecoPessoaFisica = enderecoPessoaFisica;
        this.enderecoPessoaJuridica = enderecoPessoaJuridica;
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

    
    
}
