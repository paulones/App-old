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
public class SocioRedirecionamento implements Serializable{
    
    private Object pessoa;
    private Redirecionamento redirecionamento;

    public SocioRedirecionamento(Object enderecoPessoa, Redirecionamento redirecionamento) {
        this.pessoa = enderecoPessoa;
        this.redirecionamento = redirecionamento;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.pessoa);
        hash = 41 * hash + Objects.hashCode(this.redirecionamento);
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
        final SocioRedirecionamento other = (SocioRedirecionamento) obj;
        if (!Objects.equals(this.pessoa, other.pessoa)) {
            return false;
        }
        if (!Objects.equals(this.redirecionamento, other.redirecionamento)) {
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

    public Redirecionamento getRedirecionamento() {
        return redirecionamento;
    }

    public void setRedirecionamento(Redirecionamento redirecionamento) {
        this.redirecionamento = redirecionamento;
    }
    
    
}
