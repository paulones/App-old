/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author paulones
 */
public class Executado implements Serializable{
    
    private ProcessoJudicial processoJudicial;
    private EnderecoPessoa enderecoPessoa;

    public Executado() {
    }

    public Executado(ProcessoJudicial processoJudicial, EnderecoPessoa enderecoPessoa) {
        this.processoJudicial = processoJudicial;
        this.enderecoPessoa = enderecoPessoa;
    }

    public ProcessoJudicial getProcessoJudicial() {
        return processoJudicial;
    }

    public void setProcessoJudicial(ProcessoJudicial processoJudicial) {
        this.processoJudicial = processoJudicial;
    }

    public EnderecoPessoa getEnderecoPessoa() {
        return enderecoPessoa;
    }

    public void setEnderecoPessoa(EnderecoPessoa enderecoPessoa) {
        this.enderecoPessoa = enderecoPessoa;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.processoJudicial);
        hash = 11 * hash + Objects.hashCode(this.enderecoPessoa);
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
        final Executado other = (Executado) obj;
        if (!Objects.equals(this.processoJudicial, other.processoJudicial)) {
            return false;
        }
        if (!Objects.equals(this.enderecoPessoa, other.enderecoPessoa)) {
            return false;
        }
        return true;
    }
            
    
}
