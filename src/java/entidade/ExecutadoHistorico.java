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
    private EnderecoPessoa enderecoPessoa;

    public ExecutadoHistorico() {
    }

    public ExecutadoHistorico(ProcessoJudicialHistorico processoJudicialHistorico, EnderecoPessoa enderecoPessoa) {
        this.processoJudicialHistorico = processoJudicialHistorico;
        this.enderecoPessoa = enderecoPessoa;
    }
    
    public ProcessoJudicialHistorico getProcessoJudicialHistorico() {
        return processoJudicialHistorico;
    }

    public void setProcessoJudicialHistorico(ProcessoJudicialHistorico processoJudicialHistorico) {
        this.processoJudicialHistorico = processoJudicialHistorico;
    }

    public EnderecoPessoa getEnderecoPessoa() {
        return enderecoPessoa;
    }

    public void setEnderecoPessoa(EnderecoPessoa enderecoPessoa) {
        this.enderecoPessoa = enderecoPessoa;
    }
    
}
