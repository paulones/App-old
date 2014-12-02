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
public class SocioRedirecionamentoHistorico implements Serializable{
    
    private Object pessoa;
    private RedirecionamentoHistorico redirecionamentoHistorico;

    public SocioRedirecionamentoHistorico(Object enderecoPessoa, RedirecionamentoHistorico redirecionamentoHistorico) {
        this.pessoa = enderecoPessoa;
        this.redirecionamentoHistorico = redirecionamentoHistorico;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.pessoa);
        hash = 41 * hash + Objects.hashCode(this.redirecionamentoHistorico);
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
        final SocioRedirecionamentoHistorico other = (SocioRedirecionamentoHistorico) obj;
        if (!Objects.equals(this.pessoa, other.pessoa)) {
            return false;
        }
        if (!Objects.equals(this.redirecionamentoHistorico, other.redirecionamentoHistorico)) {
            return false;
        }
        return true;
    }

    public Object getPessoa() {
        return pessoa;
    }

    public void setPessoa(Object pessoa) {
        this.pessoa = pessoa;
    }

    public RedirecionamentoHistorico getRedirecionamentoHistorico() {
        return redirecionamentoHistorico;
    }

    public void setRedirecionamentoHistorico(RedirecionamentoHistorico redirecionamentoHistorico) {
        this.redirecionamentoHistorico = redirecionamentoHistorico;
    }
    
    
}
